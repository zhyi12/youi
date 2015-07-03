/**
 * 
 */
package org.youi.framework.core.web.view;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.validator.InvalidValue;
import org.youi.framework.core.Constants;

/**
 * @author Administrator
 *
 */
public class InvalidMessage extends Message {
	/**
	 * PagerRecords
	 */
	private static final long serialVersionUID = -1302692808682355608L;
	private Map<String,String> invalidMessages = new HashMap<String,String>();

	public InvalidMessage(InvalidValue[] invalidValues) {
		super(Constants.ERROR_DOMAIN_VALIDATOR, "");
		this.info = buildInvalidMessage(invalidValues);
	}

	private String buildInvalidMessage(InvalidValue[] invalidValues) {
		StringBuffer messages = new StringBuffer();
		for(InvalidValue invalidValue:invalidValues){
			if(messages.length()>0){
				messages.append("\n");
			}
			
			messages.append(invalidValue.getBeanClass().getName()+"."+invalidValue.getPropertyName())
				.append(invalidValue.getMessage())
				.append(" ,实际值[").append(invalidValue.getValue()+",length="+invalidValue.getValue().toString().length()+"]");
			
			invalidMessages.put(invalidValue.getPropertyName(), 
					invalidValue.getMessage());
		}
		return messages.toString();
	}

	public Map<String, String> getInvalidMessages() {
		return invalidMessages;
	}

	public void setInvalidMessages(Map<String, String> invalidMessages) {
		this.invalidMessages = invalidMessages;
	}
}
