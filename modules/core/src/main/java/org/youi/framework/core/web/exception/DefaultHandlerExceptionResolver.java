/**
 * 
 */
package org.youi.framework.core.web.exception;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.youi.framework.core.Constants;
import org.youi.framework.core.exception.ExceptionUtils;
import org.youi.framework.core.web.view.DataModelAndView;
import org.youi.framework.core.web.view.Message;
import org.youi.framework.util.RequestUtils;

/**
 * 通用异常处理
 * @author Administrator
 *
 */
public class DefaultHandlerExceptionResolver extends
		SimpleMappingExceptionResolver {
	
	private MessageSource messageSource;
	
	/**
	 * @param messageSource the messageSource to set
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}


	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver#doResolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		Locale locale = RequestUtils.getLocale(request);
		Message errorMessage = ExceptionUtils.getErrorMessage(ex,messageSource,locale);
		request.setAttribute("errorMessage", errorMessage);
		//
//		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		//PrintStream sw = new PrintStream(byteStream);
		//ex.printStackTrace(sw);
		//logger.error(byteStream.toString());
		
		if(request.getRequestURI().endsWith(Constants.DATA_URL_POSTFIX)){//数据访问异常
			return new DataModelAndView(errorMessage);
		}
		return	super.doResolveException(request, response, handler, ex);
	}
}
