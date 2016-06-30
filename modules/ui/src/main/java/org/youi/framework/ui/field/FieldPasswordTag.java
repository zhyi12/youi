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

import org.youi.framework.ui.field.model.AbstractField;
import org.youi.framework.ui.field.model.FieldPassword;
import org.youi.framework.ui.util.HtmlUtils;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jul 20, 2010
 */
public class FieldPasswordTag extends AbstractFieldTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 855361136463358626L;

	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.field.AbstractFieldTag#createField()
	 */
	@Override
	protected AbstractField createField() {
		FieldPassword field = new FieldPassword();
		return field;
	}

	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.AbstractUiTag#uiContentHtml()
	 */
	@Override
	public String uiContentHtml() {
		StringBuffer htmls = new StringBuffer();
		htmls.append("<input name=\""+getFieldFormName()+"\" type=\"password\" class=\"form-control textInput value\"></input>");
		return HtmlUtils.generateFieldInnerHtml(htmls.toString(), 0,0, false);
	}

}
