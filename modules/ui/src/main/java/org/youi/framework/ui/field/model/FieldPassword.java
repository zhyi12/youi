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
 * @创建时间 Jul 20, 2010
 */
public class FieldPassword extends AbstractField {

	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.field.model.AbstractField#addonProperies()
	 */
	@Override
	public String[] addonProperies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String innerHtml() {
		StringBuffer htmls = new StringBuffer();
		int inputWidth = parseWidth() - 16;
		htmls.append("<input name=\""+getProperty()+"\" style=\"width:"+(inputWidth )+"px;\" type=\"password\" class=\"textInput value\"></input>");
		htmls.append("<div class=\"field-invalid\"></div>");
		return htmls.toString();
	}
	
}
