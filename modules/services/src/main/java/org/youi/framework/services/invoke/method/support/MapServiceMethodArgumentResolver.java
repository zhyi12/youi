/**
 * 
 */
package org.youi.framework.services.invoke.method.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;

/**
 * @author zhyi_12
 *
 */
public class MapServiceMethodArgumentResolver implements
		ServiceMethodArgumentResolver {

	/* (non-Javadoc)
	 * @see com.gsoft.framework.esb.method.support.ServiceMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return Map.class.isAssignableFrom(parameter.getParameterType());
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.esb.method.support.ServiceMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, java.util.Map, java.lang.Object[])
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter,
			Map<String, List<Object>> params, Object... providedArgs)
			throws Exception {
		
		Map<String,Object> mapParams = new HashMap<String,Object>();
		
		for(Map.Entry<String, List<Object>> entry:params.entrySet()){
			if(entry.getValue()==null){
				continue;
			}else if(entry.getValue().size()==1){
				mapParams.put(entry.getKey(), entry.getValue().get(0));
			}else{
				mapParams.put(entry.getKey(),entry.getValue());
			}
		}
		//移除头参数
//		mapParams.remove(EsbConstants.PARAM_BEAN_NAME);
//		mapParams.remove(EsbConstants.PARAM_METHOD_NAME);
//		mapParams.remove(EsbConstants.HEADER_SYS_CODE);
		
		return mapParams;
	}

}
