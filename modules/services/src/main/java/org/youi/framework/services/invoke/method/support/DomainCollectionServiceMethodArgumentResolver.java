/**
 * 
 */
package org.youi.framework.services.invoke.method.support;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.youi.framework.esb.annotation.DomainCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;

/**
 * @author zhyi_12
 *
 */
public class DomainCollectionServiceMethodArgumentResolver implements
		ServiceMethodArgumentResolver {

	/* (non-Javadoc)
	 * @see com.gsoft.framework.esb.method.support.ServiceMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		
		return Collection.class.isAssignableFrom(parameter.getParameterType())
			&&parameter.getParameterAnnotation(DomainCollection.class)!=null;
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.esb.method.support.ServiceMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, java.util.Map, java.lang.Object[])
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter,
			Map<String, List<Object>> params, Object... providedArgs)
			throws Exception {
		
		DomainCollection domainCollection = parameter.getParameterAnnotation(DomainCollection.class);
		
		if(domainCollection==null){
			return null;
		}
		
		return EsbDomainMappingUtils.getEsbMappingDomainsBinder(
				domainCollection.name(),
				domainCollection.domainClazz(), 
				params, 
				parameter.getMethodAnnotation(EsbServiceMapping.class), 
				providedArgs);
	}

}
