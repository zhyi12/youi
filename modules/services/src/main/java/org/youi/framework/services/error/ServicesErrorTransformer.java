package org.youi.framework.services.error;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;


@Component("servicesErrorTransformer")
public class ServicesErrorTransformer {
	
	public static final Log logger = LogFactory.getLog(ServicesErrorTransformer.class);
	
	public Message<?> transformError(ErrorMessage errorMessage){
		Message<?> errorResult = errorMessage;
		if(errorMessage.getPayload() instanceof MessageHandlingException){
			
		}else{
			
		}
		return errorResult;
	}
}
