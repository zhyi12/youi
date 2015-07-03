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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * @功能描述 平台异常
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Nov 5, 2010
 */
public class YouiException extends NestedRuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -620521102514596635L;

	public YouiException(String msg) {
		super(msg);
		logMessage(msg);
	}
	
	public YouiException(Throwable cause){
		super(cause.getMessage());
		logCause(cause,cause.getMessage());
	}
	
	public YouiException(String msg, Throwable cause) {
		super(msg, cause);
		logCause(cause,msg);
	}
	/**
	 * 记录异常信息到日记
	 * @param cause
	 */
	private void logCause(Throwable cause,String message){
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		PrintStream sw = new PrintStream(byteStream);
		cause.printStackTrace(sw);
		logMessage(message);
		if(logger.isDebugEnabled()){//调试模式,打印异常的轨迹
			logger.debug("平台异常轨迹:"+byteStream.toString());
		}
	}
	
	private void logMessage(String message){
		logger.error("平台异常信息："+message+"!");
	}
}
