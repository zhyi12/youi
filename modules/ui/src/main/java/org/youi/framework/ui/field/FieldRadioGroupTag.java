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
package org.youi.framework.ui.field;

import org.youi.framework.ui.field.model.AbstractSourceField;
import org.youi.framework.ui.field.model.FieldRadioGroup;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jul 14, 2010
 */
public class FieldRadioGroupTag extends AbstractFieldSourceTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7829652444220908912L;

	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.field.AbstractFieldSourceTag#createSourceField()
	 */
	@Override
	protected AbstractSourceField createSourceField() {
		AbstractSourceField field = new FieldRadioGroup();
		return field;
	}

	@Override
	public String uiContentHtml() {
		StringBuffer htmls = new StringBuffer();
		htmls.append("<div class=\"content\" id=\"content_"+this.id+"\"></div>");
		return htmls.toString();
	}
}
