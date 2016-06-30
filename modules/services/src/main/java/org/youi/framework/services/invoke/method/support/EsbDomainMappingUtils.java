/**
 * 
 */
package org.youi.framework.services.invoke.method.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.PubCondition;
import org.youi.framework.services.data.PubContext;
import org.youi.framework.services.invoke.ServiceDataBinder;
import org.youi.framework.util.BeanUtils;


/**
 * @author zhyi_12
 *
 */
public class EsbDomainMappingUtils {
	
	private final static String DOMAIN_PROP_SPLIT_KEY = "].";

	/**
	 * @param domainClazz
	 * @param params
	 * @param esbServiceMapping
	 * @param providedArgs
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ServiceDataBinder getEsbMappingDomainBinder(Class domainClazz,
			Map<String, List<Object>> params,
			EsbServiceMapping esbServiceMapping,
			Object... providedArgs){
		Object target = BeanUtils.instantiateClass(domainClazz);
		ServiceDataBinder dataBinder = new ServiceDataBinder(target);
		
		Map<String,String[]> pValues = new HashMap<String,String[]>();
		for(Map.Entry<String, List<Object>> entry:params.entrySet()){
			try {
				pValues.put(entry.getKey(), entry.getValue().toArray(new String[entry.getValue().size()]));
			} catch (Exception e) {
				System.out.println(entry.getKey()+" = "+entry.getValue());
			}
		}
		
		//通过公共头注解设置
		if(esbServiceMapping!=null){
			PubContext putContext = null;
			for(Object providedArg:providedArgs){
				//取出公共头信息对象
				if(providedArg instanceof PubContext){
					putContext = (PubContext)providedArg;
				}
			}
			//根据公共头映射设置自动写对象值
			if(putContext!=null&&putContext.getParams()!=null&&esbServiceMapping.pubConditions().length>0){
				for(PubCondition pubCondition:esbServiceMapping.pubConditions()){
					Object value = putContext.getParams().get(pubCondition.pubProperty());
					if(value!=null){
						pValues.put(pubCondition.property(), 
								new String[]{value.toString()});
					}
				}
			}
		}
		
		PropertyValues pvs = new MutablePropertyValues(pValues);
		dataBinder.bind(pvs);
		return dataBinder;
	}

	/**
	 * 
	 * @param propName
	 * @param domainClazz
	 * @param params
	 * @param esbServiceMapping
	 * @param providedArgs
	 * @return
	 */
	public static Object getEsbMappingDomainsBinder(String propName,
			Class<? extends Domain> domainClazz,
			Map<String, List<Object>> params,
			EsbServiceMapping esbServiceMapping, Object[] providedArgs) {
		
		Map<String,DomainMapping> domainMappings = new HashMap<String,DomainMapping>();
		
		Collection<Object> records = new ArrayList<Object>();
		
		for(Map.Entry<String, List<Object>> entry:params.entrySet()){
			String paramName = entry.getKey();
			int splitIndex = paramName.indexOf(DOMAIN_PROP_SPLIT_KEY);
			if(splitIndex>0&&paramName.startsWith(propName+"[")){
				String key = paramName.substring(0,splitIndex+DOMAIN_PROP_SPLIT_KEY.length());
				String recordPropName = paramName.substring(splitIndex+DOMAIN_PROP_SPLIT_KEY.length());
				
				DomainMapping domainMapping;
				if(domainMappings.containsKey(key)){
					domainMapping = domainMappings.get(key);
				}else{
					domainMapping = new DomainMapping();
				}
				
				domainMapping.addPropValue(recordPropName, entry.getValue());
				domainMappings.put(key, domainMapping);
			}
		}
		
		if(domainMappings.size()>0){
			Object record;
			for(DomainMapping domainMapping:domainMappings.values()){
				record = getEsbMappingDomainBinder(domainClazz, domainMapping.getParams(), esbServiceMapping, providedArgs).getTarget();
				if(record!=null){
					records.add(record);
				}
				record = null;
			}
		}
		
		return records;
	}
	
	private static class DomainMapping{
		
		private Map<String, List<Object>> params = new HashMap<String,List<Object>>();
		
		void addPropValue(String propName,List<Object> values){
			params.put(propName, values);
		}
		
		public Map<String, List<Object>> getParams(){
			return params;
		}
	}
}
