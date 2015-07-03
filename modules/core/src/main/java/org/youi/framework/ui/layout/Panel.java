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
package org.youi.framework.ui.layout;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Dec 26, 2010
 */
public class Panel {
	private String jsp;
	
	private String width;

	/**
	 * @return the jsp
	 */
	public String getJsp() {
		return jsp;
	}

	/**
	 * @param jsp the jsp to set
	 */
	public void setJsp(String jsp) {
		this.jsp = jsp;
	}

	public String getWidth() {
		return width;
	}
	
	public void setWidth(String width){
		this.width = width;
	}
}
