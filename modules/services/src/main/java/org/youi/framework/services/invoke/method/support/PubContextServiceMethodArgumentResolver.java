/**
 * 
 */
package org.youi.framework.services.invoke.method.support;

import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.youi.framework.services.data.PubContext;


/**
 * @author zhyi_12
 *
 */
public class PubContextServiceMethodArgumentResolver implements ServiceMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return PubContext.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			Map<String, List<Object>> params, Object... providedArgs)
			throws Exception {
		for(Object providedArg:providedArgs){
			if(providedArg instanceof PubContext){
				return (PubContext)providedArg;
			}
		}
		return null;
	}

}
