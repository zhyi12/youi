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

import org.hibernate.validator.InvalidValue;
import org.springframework.context.MessageSource;
import org.youi.framework.core.web.view.InvalidMessage;
import org.youi.framework.core.web.view.Message;

/**
 * @功能描述 对象校验异常
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jan 6, 2011
 */
public class DomainValidatorException extends YouiException implements ExceptionMessage{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1979382516757606789L;
	
	private InvalidValue[] invalidValues;

	public DomainValidatorException(InvalidValue[] invalidValues) {
		super(new InvalidMessage(invalidValues).info);
		this.invalidValues = invalidValues;
	}
	
	@Override
	public String getMessage(){
		return getExceptionMessage().getInfo();
	}
	
	public Message getExceptionMessage() {
		return new InvalidMessage(invalidValues);
	}

	@Override
	public Message getExceptionMessage(MessageSource messageSource,
			Locale locale) {
		return new InvalidMessage(invalidValues);
	}
}
