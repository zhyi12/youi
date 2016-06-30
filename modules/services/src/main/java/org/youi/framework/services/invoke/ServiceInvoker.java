/**
 * 
 */
package org.youi.framework.services.invoke;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils.MethodFilter;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.dataobj.Record;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.core.web.view.Message;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.InitializeProperty;
import org.youi.framework.esb.annotation.ResultMessage;
import org.youi.framework.services.ServicesConstants;
import org.youi.framework.services.data.PubContext;
import org.youi.framework.services.data.ResContext;
import org.youi.framework.services.invoke.method.support.InvocableSeriveMethod;
import org.youi.framework.services.invoke.method.support.ServiceMethodSelector;
import org.youi.framework.ui.convert.Convert;
import org.youi.framework.util.PropertyUtils;
import org.youi.framework.util.StringUtils;


/**
 * @author zhyi_12
 * 
 */
@Transactional
@Service("serviceInvoker")
public class ServiceInvoker implements ApplicationContextAware {

	private static final Log logger = LogFactory.getLog(ServiceInvoker.class);
	
	public static final String SERVICE_ERROR_CODE  = "999999";
	
	
	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	// 因为本方法为声明式服务，不能截取RuntimeException，所以要将异常处理放到RmiServiceFactory处理。
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResContext invoke(String beanName, String methodName,
			Map<String, List<Object>> params,PubContext pubContext) throws Exception {
		ResContext res = new ResContext();
		
		// 组织参数
		if (beanName != null && methodName != null) {
			
			Object bean = null;
			try {
				bean = applicationContext.getBean(beanName);
			} catch (BeansException e) {
				logger.error("从上下文中获取实例"+beanName+"失败");
				throw e;
			}
			//获取服务方法
			Method serviceMethod = this.findServiceMethod(bean, methodName);
			
			String defaultTranCode = beanName+"."+methodName;
			if(serviceMethod==null){
				res.setMessage(new Message(ServicesConstants.ESB_ERROR_SERVICE_NOTFOUNT,"未找到服务["+defaultTranCode+"]."));
			}else{
				Object result = this.doInvokeMethod(params,bean,serviceMethod,pubContext);
				
				EsbServiceMapping mapping = serviceMethod.getAnnotation(EsbServiceMapping.class);
				//设置交易参数
				String trancode = StringUtils.isNotEmpty(mapping.trancode())?mapping.trancode():defaultTranCode;
				
				res.setTrancode(trancode);
				
				if(result==null){
					return res;
				}
				Class<?> resultType = result.getClass();
				
				if (List.class.isAssignableFrom(resultType)) {
					//集合类型的返回结果
					res.setRecords((List) result);
				}else if (PagerRecords.class.isAssignableFrom(resultType)) {
					//分页对象的返回结果
					PagerRecords pagerRecords = (PagerRecords)result;
					res.setRecords(pagerRecords.getRecords());
					res.setTotalCount(pagerRecords.getTotalCount());
				} else if (Domain.class.isAssignableFrom(resultType)) {
					//实体对象的返回结果
					res.setRecord((Domain) result);
				} else if (Message.class.isAssignableFrom(resultType)) {
					//消息类型
					res.setMessage((Message) result);
				}else if(ResContext.class.isAssignableFrom(resultType)){
					res = (ResContext)result;
				}else if(String.class.isAssignableFrom(resultType)){
					Record record = new Record();
					record.put("html", result);
					res.setRecord(record);
				}else if(resultType.isArray()){//数组处理
					Record record = new Record();
					record.put("items", result);
					res.setRecord(record); 
				}else if(Convert.class.isAssignableFrom(resultType)){
					Record record = new Record();
					record.putAll((Convert)result);
					res.setRecord(record);
				}
			}
		}else{
			res.setMessage(new Message(ServicesConstants.ESB_ERROR_SERVICE_NOT_ENOUGH_PARAM,"缺少服务参数."));
		}
		
		return res;
	}
	
	/**
	 * 查找服务方法
	 * @param beanName
	 * @param methodName
	 * @return
	 */
	private Method findServiceMethod(Object bean,final String methodName){
		
		final Class<?> beanClass = getTargetClass(bean);
		
		boolean isInterfaceMethod = false;
		for(Class<?> beanInterfaceClazz:beanClass.getInterfaces()){
			if(ClassUtils.getMethodCountForName(beanInterfaceClazz, methodName)>0){
				isInterfaceMethod = true;
				break;
			}
		}
		//如果不是接口方法，返回错误提示信息
		if(isInterfaceMethod){
			Set<Method> methods = ServiceMethodSelector.selectMethods(beanClass,
					new MethodFilter() {
						public boolean matches(Method method) {
							return getMappingForMethod(method, beanClass,
									methodName) != null;
						}
					});
			if (methods.size() > 0) {
				return methods.toArray(new Method[methods.size()])[0];
			}
		}else{
			logger.error(beanClass.getName()+":"+methodName+"方法不存在或不是接口方法。");
		}
		
		return null;
	}

	private Object doInvokeMethod(Map<String, List<Object>> params,Object bean,Method method ,PubContext pubContext) throws Exception {
		Object result = null;
		
		//
		if (method!=null) {
			InvocableSeriveMethod serviceMethod = new InvocableSeriveMethod(bean, method);
			// 因为本方法为声明式服务，不能截取RuntimeException，所以要将异常处理放到ServiceFactory处理。
			result = serviceMethod.invokeForRequest(params,pubContext);
			
			ResultMessage resultMessage = method.getAnnotation(ResultMessage.class);
			
			if (resultMessage != null) {
				Message message;
				if (result!=null&& Message.class.isAssignableFrom(result.getClass())) {
					return result;// 直接返回
				} else {
					String code = resultMessage.code();
					String info = resultMessage.info();
					if (info == null || "".equals(info)) {
						info = result.toString();
					}
					message = new Message(code, info);
					return message;
				}
			}else{
				EsbServiceMapping mapping = method.getAnnotation(EsbServiceMapping.class);
				//强制接口
				try {
					if(result!=null){
						focreInitializeProperties(result,mapping.initializeProperties());
					}
				} catch (Exception e) {
					logger.warn("强制加载延迟数据失败.");
				}
			}
		}
		//
		return result;
	}
	
	/**
	 * 强制处理延迟加载对象
	 * @param result
	 * @param initializeProperties
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void focreInitializeProperties(Object result,
			InitializeProperty[] initializeProperties) {
		if(initializeProperties!=null&&initializeProperties.length>0){
			//
			Class<?> resultType = result.getClass();
			List entityList = null;
			if (List.class.isAssignableFrom(resultType)) {
				//集合类型
				entityList = (List)result;
			}else if (PagerRecords.class.isAssignableFrom(resultType)) {
				//分页对象
				entityList = ((PagerRecords)result).getRecords();
			} else if (Domain.class.isAssignableFrom(resultType)) {
				//普通实体
				entityList = new ArrayList<Domain>();
				entityList.add(result);
			} 
			if(entityList!=null){
				for(Object entity:entityList){
					focreDomainInitializeProperties(entity,initializeProperties);
				}
			}
		}
	}

	/**
	 * @param entity
	 * @param initializeProperties
	 */
	private void focreDomainInitializeProperties(Object entity,
			InitializeProperty[] initializeProperties) {
		for(InitializeProperty property:initializeProperties){
			Object result = PropertyUtils.getSimplePropertyValue(entity, property.value());
			if(result!=null){
				Hibernate.initialize(result);
			}
		}
	}

	private Class<?> getTargetClass(Object bean){
		return AopUtils.getTargetClass(bean);
	}

	/**
	 * @param method
	 * @param beanClass
	 * @param methodName
	 * @return
	 */
	private Method getMappingForMethod(Method method, Class<?> beanClass,
			String methodName) {
		if (method.getAnnotation(EsbServiceMapping.class) != null
				&& methodName.equals(method.getName())) {
			return method;
		}
		return null;
	}

}
