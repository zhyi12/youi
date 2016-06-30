/**
 * 
 */
package org.youi.framework.services.invoke.method.support;

import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;

/**
 * @author zhyi_12
 *
 */
public interface ServiceMethodArgumentResolver {

	/**
	 * @param parameter
	 * @return
	 */
	boolean supportsParameter(MethodParameter parameter);
	
	/**
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	Object resolveArgument(MethodParameter parameter,
			Map<String,List<Object>> params,Object... providedArgs) throws Exception;
}
