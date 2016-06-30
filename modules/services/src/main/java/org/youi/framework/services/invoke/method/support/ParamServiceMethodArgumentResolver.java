/**
 * 
 */
package org.youi.framework.services.invoke.method.support;

import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.framework.services.data.PubContext;
import org.youi.framework.util.StringUtils;

/**
 * @author zhyi_12
 *
 */
public class ParamServiceMethodArgumentResolver implements
		ServiceMethodArgumentResolver {

	/* (non-Javadoc)
	 * @see com.gsoft.framework.esb.method.support.ServiceMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
	 */
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(ServiceParam.class)!=null;
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.esb.method.support.ServiceMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter)
	 */
	public Object resolveArgument(MethodParameter parameter,Map<String,List<Object>> params
			,Object... providedArgs) throws Exception {
		ServiceParam serviceParam = parameter.getParameterAnnotation(ServiceParam.class);
		String paramName = serviceParam.name();
		List<Object> values = params.get(paramName);
		
		//如果为空,
		if(values==null&&StringUtils.isNotEmpty(serviceParam.pubProperty())){
			PubContext pubContext = null;
			for(Object providedArg:providedArgs){
				//取出公共头信息对象
				if(providedArg instanceof PubContext){
					pubContext = (PubContext)providedArg;
				} 
			}
			if(pubContext!=null&&pubContext.getParams()!=null){
				return pubContext.getParams().get(serviceParam.pubProperty());
			}
		}
		
		Class<?> pType = parameter.getParameterType();
		if(List.class.isAssignableFrom(pType)){
			return values;
		}else if(pType.isArray()&&values!=null){
			return values.toArray(new String[values.size()]);
		}
		return values==null||values.size()==0?null:values.get(0);
	}

}
