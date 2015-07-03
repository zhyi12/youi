/**
 * 
 */
package org.youi.framework.core.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.youi.framework.core.web.view.Message;


/**
 * @author Administrator
 *
 */
public interface ExceptionMessage {
	/**
	 * 获取异常输出信息
	 * @return
	 */
	public Message getExceptionMessage();
	
	/**
	 * 获取异常输出信息
	 * @return
	 */
	public Message getExceptionMessage(MessageSource messageSource,Locale locale);
}
