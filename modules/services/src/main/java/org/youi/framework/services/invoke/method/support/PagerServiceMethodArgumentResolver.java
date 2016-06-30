package org.youi.framework.services.invoke.method.support;

import java.util.List;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.youi.framework.core.orm.Pager;


public class PagerServiceMethodArgumentResolver  implements ServiceMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return Pager.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			Map<String, List<Object>> params,Object... providedArgs) throws Exception {
		String pageSize = getString(params,"pager:pageSize");
		String pageIndex = getString(params,"pager:pageIndex");
		String pageType = getString(params,"pager:pageType");
		
		return new Pager(pageSize, pageIndex, pageType);
	}
	
	public String getString(Map<String, List<Object>> params,String paramName){
		List<Object> values = params.get(paramName);
		if(values==null||values.size()==0){
			return "";
		}
		return values.get(0).toString();
		
	}
}
