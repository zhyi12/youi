package org.youi.framework.services.data;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.youi.framework.core.Constants;
import org.youi.framework.core.web.view.Message;

@SuppressWarnings({ "serial", "rawtypes" })
public class ExceptionResContext extends ResContext {

	public ExceptionResContext(Throwable e) {
		StringBuilder errorMessageBuilder = new StringBuilder();
		if(ConstraintViolationException.class.isAssignableFrom(e.getClass())){
			Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException)e).getConstraintViolations();
			for(ConstraintViolation<?> constraintViolation:constraintViolations){
				errorMessageBuilder.append("{"+constraintViolation.getPropertyPath()+"}")
					.append(constraintViolation.getMessage()).append(" ");
			}
		}else{
			errorMessageBuilder.append("error");
		}
		this.setMessage(new Message(Constants.ERROR_DEFAULT_CODE,errorMessageBuilder.toString()));
	}

}
