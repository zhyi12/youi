package org.youi.framework.services.invoke.method.support;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;

import org.springframework.core.MethodParameter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.web.annotation.Filter;
import org.youi.framework.esb.annotation.ConditionCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.PubCondition;
import org.youi.framework.services.invoke.ServiceDataBinder;
import org.youi.framework.util.ConditionUtils;
import org.youi.framework.util.PropertyUtils;
import org.youi.framework.util.StringUtils;


/**
 * @author zhyi_12
 *
 */
public class ConditionsServiceMethodArgumentResolver implements
		ServiceMethodArgumentResolver {

	public static final int MAX_CASCADE_LEVEL=2;//级联对象属性最大解析层次
	
	private Map<String,Set<String>> cachedDomainFieldNames = 
		Collections.synchronizedMap(new HashMap<String,Set<String>>());
	
	private GenericConversionService conversionService =
		new GenericConversionService();
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return Collection.class.isAssignableFrom(parameter.getParameterType())&&
			parameter.hasParameterAnnotation(ConditionCollection.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			Map<String, List<Object>> params,Object... providedArgs) throws Exception {
		ConditionCollection conditionCollection = 
			parameter.getParameterAnnotation(ConditionCollection.class);
		EsbServiceMapping esbServiceMapping = 
			parameter.getMethodAnnotation(EsbServiceMapping.class);
			
		//映射的实体
		Class<?> domainClazz = conditionCollection.domainClazz();
		//对象映射,string类型的参数到对象属性值类型转换，里面包含公共头信息到对象属性的映射.
		ServiceDataBinder dataBinder = EsbDomainMappingUtils.getEsbMappingDomainBinder(
				domainClazz, params, esbServiceMapping, providedArgs);
		Object domainBean = dataBinder.getTarget();
		
		//自定义的参数名称
		//定义操作扩展
		Collection<Condition> conditions = new ArrayList<Condition>();
		
		//存储查询条件变量
		Set<String>  paramNames = getDomainParamNames(domainClazz);
		//加入自定义参数名
		for(String customParam:conditionCollection.customParams()){
			paramNames.add(customParam);
		}
		
		
		for(Map.Entry<String, List<Object>> param:params.entrySet()){
			String beanProperty = getConditionProperty(param.getKey(),esbServiceMapping);
			
			if(paramNames.contains(beanProperty)){//如果存在该参数，自动设置到查询条件集合中
				//从映射对象中取值
				Object value = null;
				if(beanProperty.equals(param.getKey())){//从对象中获取
					value = PropertyUtils.getPropertyValue(domainBean,beanProperty);
				}else{
					value = param.getValue().size()>0?param.getValue().get(0):null;
					value = this.convertValue(param.getKey(),value,esbServiceMapping);
				}
				
				if(value!=null){
					Object operator = null;
					String operatorKey = "operator:"+param.getKey();
					
					if(params.containsKey(operatorKey)&&params.get(operatorKey).size()>0){
						//从参数中根据规则获取数据比较符
						operator = params.get(operatorKey).get(0);
					}else{
						//从配置中获取数据比较符
						operator = getOperator(param.getKey(), esbServiceMapping);
					}
					
					if(operator==null){
						//默认使用equals数据比较符
						operator = Condition.EQUALS;
					}
					
					conditions.add(ConditionUtils.getCondition(
							beanProperty,
							operator.toString(),
							value));
				}
				beanProperty = null;
				value = null;
			}
		}
		
		//公共头属性添加到查询条件
		if(esbServiceMapping!=null&&esbServiceMapping.pubConditions().length>0){
			conditions.addAll(this.createPubConditions(esbServiceMapping.pubConditions(), domainBean));
		}
		return conditions;
	}
	
	/**
	 * 获取操作符
	 * @param paramKeym
	 * @param esbServiceMapping
	 * @return
	 */
	private String getOperator(String paramKey,EsbServiceMapping esbServiceMapping){
		for(Filter filter:esbServiceMapping.filters()){
			if(paramKey.equals(filter.name())){
				return filter.operator();
			}
		}
		return Condition.EQUALS;
	}
	
	/**
	 * 查询条件属性从输入到实际对象的映射
	 * @param paramKey
	 * @param esbServiceMapping
	 * @return
	 */
	private String getConditionProperty(String paramKey,EsbServiceMapping esbServiceMapping){
		for(Filter filter:esbServiceMapping.filters()){
			if(paramKey.equals(filter.name())&&StringUtils.isNotEmpty(filter.mapProperty())){
				return filter.mapProperty();
			}
		}
		return paramKey;
	}
	
	/**
	 * 值转换
	 * @param paramKey
	 * @param value
	 * @param esbServiceMapping
	 * @return
	 */
	private Object convertValue(String paramKey,Object value,EsbServiceMapping esbServiceMapping){
		for(Filter filter:esbServiceMapping.filters()){
			if(paramKey.equals(filter.name())&&!paramKey.equals(filter.mapProperty())){
				//当页面的属性和对象的属性存在映射关系的时候，需要指定参数类型
				return conversionService.convert(value, filter.valueClazz());
			}
		}
		return value;
	}
	
	/**
	 * 根据实体对象获取对象的属性字段
	 * 优先从缓存中获取，缓存中没有则重新构建并放入缓存。
	 * @param domainClazz
	 * @return
	 */
	private Set<String>  getDomainParamNames(Class<?> domainClazz){
		String domainClazzName = domainClazz.getName();
		//存储查询条件变量
		Set<String>  paramNames = new HashSet<String>();
		if(cachedDomainFieldNames.containsKey(domainClazzName)){
			paramNames = cachedDomainFieldNames.get(domainClazzName);
		}else{
			//获取对象的字段集合
			ConditionFieldCallback conditionFieldCallback = new ConditionFieldCallback("");
			ReflectionUtils.doWithFields(domainClazz,conditionFieldCallback);
			
			//存储查询条件变量
			//加入Domain对象属性
			paramNames.addAll(conditionFieldCallback.getFieldNames());
			cachedDomainFieldNames.put(domainClazzName, paramNames);
		}
		return paramNames;
	}
	/**
	 * 
	 * 根据注解解析公共对象到查询条件中
	 * @param pubConditions
	 * @param putContext
	 * @return
	 */
	private Collection<Condition> createPubConditions(PubCondition[] pubConditions,Object domainBean){
		Collection<Condition> conditions = new ArrayList<Condition>();
		//公共对象属性
		Object value = null;
		for(PubCondition pubCondition:pubConditions){
			value = PropertyUtils.getPropertyValue(domainBean,pubCondition.property());
			if(value!=null){
				conditions.add(ConditionUtils.getCondition(
						pubCondition.property(),
						pubCondition.operator(), 
						value));
			}
			value = null;
		}
		return conditions;
	}
	
	
	/**
	 * 
	 * @author zhyi_12
	 *
	 */
	class ConditionFieldCallback implements FieldCallback{
		
		private List<String> fieldNames = new ArrayList<String>();
		
		private String prefix = "";//默认值
		
		public ConditionFieldCallback(String prefix){
			this.prefix = prefix==null?"":prefix;
		}
		
		@Override
		public void doWith(Field field) throws IllegalArgumentException,
				IllegalAccessException {
			String fieldName = field.getName();
			
			Column column = field.getAnnotation(Column.class);
			if(column!=null){
				fieldNames.add(prefix+fieldName);
			}else{
				Class<?> fieldClass = field.getType();
				//级联对象处理
				if(Domain.class.isAssignableFrom(fieldClass)){
					//prefix最大允许二层对象嵌套：示例：  User对象：用户对象的地址的时区的时区ID，   address.zone.zoneId
					if(prefix.split("\\.").length>MAX_CASCADE_LEVEL+1){
						return;
					}
					
					ConditionFieldCallback conditionFieldCallback = 
						new ConditionFieldCallback(this.prefix+field.getName()+".");
					ReflectionUtils.doWithFields(fieldClass,conditionFieldCallback);
					fieldNames.addAll(conditionFieldCallback.getFieldNames());
				}
			}
		}
		
		public List<String> getFieldNames(){
			return this.fieldNames;
		}
	}

}
