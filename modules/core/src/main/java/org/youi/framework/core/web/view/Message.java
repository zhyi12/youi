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
package org.youi.framework.core.web.view;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0 
 * @创建时间 Jan 20, 2010
 */
public class Message implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -694991823003131043L;

	public String info;//信息信息
	
	public String code;//消息代码
	
	/**
	 * @param code
	 * @param info
	 */
	public Message(String code,String info) {
		this.code = code;
		this.info = info;
	}

	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

}
