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
package org.youi.framework.ui.field.model;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 May 18, 2010
 */
public class FieldList extends AbstractSourceField {
	
	public static final int DEFAULT_LIST_HEIGHT = 200;//list默认的高度
	
	private int  height;
	
	public boolean isLabelTop() {
		return true;
	}
	
	
	@Override
	public String innerHtml() {
		StringBuffer htmls = new StringBuffer();
		this.height = this.height>0?this.height:DEFAULT_LIST_HEIGHT;
		htmls.append("<div style=\"height:"+this.height+"\" id=\"content_"+this.getId()+"\"></div>");
		return htmls.toString();
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
