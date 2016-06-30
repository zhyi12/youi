/**
 * 
 */
package org.youi.framework.services.invoke.method.support;

import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.services.invoke.ServiceDataBinder;

/**
 * @author zhyi_12
 *
 */
public class DomainServiceMethodArgumentResolver implements
		ServiceMethodArgumentResolver {

	/* (non-Javadoc)
	 * @see com.gsoft.framework.esb.method.support.ServiceMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
	 */
	public boolean supportsParameter(MethodParameter parameter) {
		return Domain.class.isAssignableFrom(parameter.getParameterType());
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.esb.method.support.ServiceMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, java.util.Map)
	 */
	public Object resolveArgument(MethodParameter parameter,
			Map<String, List<Object>> params,Object... providedArgs) throws Exception {
		
		ServiceDataBinder  dataBinder = EsbDomainMappingUtils.getEsbMappingDomainBinder(
				parameter.getParameterType(), 
				params, 
				parameter.getMethodAnnotation(EsbServiceMapping.class), 
				providedArgs);
		
		return dataBinder.getTarget();
	}

}
