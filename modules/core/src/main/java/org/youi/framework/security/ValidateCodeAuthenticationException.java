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
package org.youi.framework.security;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @功能描述 验证码校验异常
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jul 24, 2010
 */
public class ValidateCodeAuthenticationException extends AuthenticationException {

	/**
	 * 
	 */
	
	private String message;
	private static final long serialVersionUID = 5985064315187847360L;

	public ValidateCodeAuthenticationException(String msg) {
		super(msg);
		message = msg;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	

}
