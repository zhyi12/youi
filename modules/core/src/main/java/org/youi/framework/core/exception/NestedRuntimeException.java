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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @功能描述 异常类
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jan 17, 2010
 */
public abstract class  NestedRuntimeException extends org.springframework.core.NestedRuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8884403198190623424L;
	protected final Log logger = LogFactory.getLog(getClass());//日记
	
	public NestedRuntimeException(String msg) {
		super(msg);
		logTrace();
	}

	public NestedRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
		logTrace();
	}
	
	/**
	 * 输出错误日记
	 */
	protected void logTrace(){
		ExceptionUtils.logTrace(getCause());
	}
}
