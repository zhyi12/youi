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

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.youi.framework.core.Constants;
import org.youi.framework.core.web.view.Message;

/**
 * @功能描述 对象校验异常
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jan 6, 2011
 */
public class DomainValidatorException extends YouiException implements ExceptionMessage{
	
//	private List<String> constraintViolations;

	public DomainValidatorException(List<String> constraintViolations) {
		super(constraintViolations.toString());
//		this.constraintViolations = constraintViolations;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1979382516757606789L;

	@Override
	public Message getExceptionMessage() {
		return new Message(Constants.ERROR_DOMAIN_VALIDATOR,this.getMessage());
	}

	@Override
	public Message getExceptionMessage(MessageSource messageSource, Locale locale) {
		return new Message(Constants.ERROR_DOMAIN_VALIDATOR,this.getMessage());
	}
	
}
