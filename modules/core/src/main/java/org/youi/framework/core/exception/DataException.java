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
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Mar 14, 2010
 */
public class DataException extends NestedRuntimeException {

	private String traces;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2935261162254416210L;

	public DataException(String msg, Throwable cause) {
		super(msg, cause);
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		PrintStream sw = new PrintStream(byteStream);
		cause.printStackTrace(sw);
		
		traces = byteStream.toString();
		logger.error("异常详细信息："+traces);
	}

	/**
	 * @return the stackTrace
	 */
	public String getTraces() {
		return traces;
	}

	/**
	 * @param stackTrace the stackTrace to set
	 */
	public void setTraces(String traces) {
		this.traces = traces;
	}
	
	

}
