/**
 * 
 */
package org.youi.framework.core.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.youi.framework.core.Constants;
import org.youi.framework.core.context.Config;
import org.youi.framework.core.web.view.Message;

/**
 * @author Administrator
 *
 */
public class ExceptionUtils {
	private static final Log logger = LogFactory.getLog(ExceptionUtils.class);//日记
	
	/**
	 * @param ex
	 * @return
	 */
	public static Message getErrorMessage(Exception ex,MessageSource messageSource,Locale locale) {
		logTrace(ex);
		String msg;
		String msgCode;
		if(ex instanceof ExceptionMessage){
			return ((ExceptionMessage)ex).getExceptionMessage(messageSource,locale);
		}
//		else if(ex instanceof org.hibernate.validator.InvalidStateException){
//			return new InvalidMessage(((org.hibernate.validator.InvalidStateException) ex).getInvalidValues());
//		}
		else{
			Throwable cause = ex.getCause();
			while(cause!=null){
				if(cause instanceof ExceptionMessage ){
					return ((ExceptionMessage)cause).getExceptionMessage(messageSource,locale);
				}
				cause = cause.getCause();
			}
			msg = "系统异常:"+ex.getMessage();//其他系统异常
			msgCode = Constants.ERROR_DEFAULT_CODE;
			ex.printStackTrace();
		}
		//TODO 其他类型的错误分类
		
		return new Message(msgCode,msg);
	}
	
	public static void logTrace(Throwable cause){
		String  traceError = Config.getInstance().getProperty(Constants.PROP_TRACE_ERROR);
		if("true".equals(traceError)&&cause!=null){
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			PrintStream sw = new PrintStream(byteStream);
			cause.printStackTrace(sw);
			String traces = byteStream.toString();
			logger.error(traces);
		}
	}

}
