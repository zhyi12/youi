/**
 * 
 */
package org.youi.framework.core.web.view;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * @author zhyi_12
 *
 */
public class WebDataRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter{
	
	@Resource(name="dataModelAndViewReturnValueHandler")
	private DataModelAndViewReturnValueHandler dataModelAndViewReturnValueHandler;
	
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		
		List<HandlerMethodReturnValueHandler> returnValueHandlers = 
			new ArrayList<HandlerMethodReturnValueHandler>();
		returnValueHandlers.add(dataModelAndViewReturnValueHandler);
		returnValueHandlers.addAll(getReturnValueHandlers().getHandlers());
		this.setReturnValueHandlers(returnValueHandlers);
	}
}
