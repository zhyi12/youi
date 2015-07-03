/*
 * YOUI框架
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.youi.framework.core.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.youi.framework.core.Constants;
import org.youi.framework.core.web.view.Message;
import org.youi.framework.util.StringUtils;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jan 31, 2010
 */
public class BusException extends NestedRuntimeException implements ExceptionMessage{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6646424463408177264L;
	
	private String code;
	
	private String[] args;
	
	private String i18nKey;
	
	/**
	 * @param msg
	 */
	public BusException(String msg) {
		this(Constants.ERROR_DEFAULT_CODE,msg);
	}
	
	/**
	 * @param i18nKey
	 * @param args
	 */
	public BusException(String i18nKey,String[] args){
		this(Constants.ERROR_DEFAULT_CODE,i18nKey,args);
	}
	
	/**
	 * @param code
	 * @param i18nKey
	 * @param args
	 */
	public BusException(String code,String i18nKey,String[] args){
		this(code,i18nKey);
		this.args = args;
		this.i18nKey = i18nKey;
	}
	
	/**
	 * @param code
	 * @param msg
	 */
	public BusException(String code,String msg) {
		super(msg);
		this.code = code;
	}

	/**
	 * @param msg
	 * @param cause
	 */
	public BusException(String msg, Throwable cause){
		this(Constants.ERROR_DEFAULT_CODE,msg,cause);
	}
	
	/**
	 * @param code
	 * @param msg
	 * @param cause
	 */
	public BusException(String code,String msg, Throwable cause){
		super(msg,cause);
		this.code = code;
	}

	public Message getExceptionMessage() {
		return new Message(code,this.getMessage());
	}
	
	public Message getExceptionMessage(MessageSource messageSource,Locale locale){
		String msg = this.getMessage();
		if(StringUtils.isNotEmpty(i18nKey)){
			try {
				msg = messageSource.getMessage(i18nKey, args, locale);
			} catch (NoSuchMessageException e) {
				msg = i18nKey;
			}
		}
		return new Message(code,msg);
	}

	/**
	 * @return the args
	 */
	public String[] getArgs() {
		return args;
	}

	/**
	 * @param args the args to set
	 */
	public void setArgs(String[] args) {
		this.args = args;
	}
}
