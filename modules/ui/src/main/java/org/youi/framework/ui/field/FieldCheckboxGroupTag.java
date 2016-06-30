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
import org.youi.framework.ui.field.model.FieldCheckboxGroup;
import org.youi.framework.ui.field.model.FieldOption;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jul 14, 2010
 */
public class FieldCheckboxGroupTag extends AbstractFieldSourceTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2116089233496488039L;
	
	private boolean showCheckAll;

	
	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.field.AbstractFieldSourceTag#createSourceField()
	 */
	@Override
	protected AbstractSourceField createSourceField() {
		AbstractSourceField field = new FieldCheckboxGroup();
		if(showCheckAll){
			field.setEmptyItemCaption(this.dealI18nAttr("showCheckAll", null, true));
		}
		return field;
	}

	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.field.AbstractFieldSourceTag#uiContentHtml()
	 */
	@Override
	public String uiContentHtml() {
		StringBuilder htmls = new StringBuilder();
		htmls.append("<div class=\"content\" id=\"content_"+this.id+"\">");
		if(this.options!=null){
			for(FieldOption option:this.options){
				htmls.append("<div class=\"checkbox-item pull-left\"><input type=\"checkbox\" value=\"")
				.append(option.getValue()).append("\" name=\"")
				.append("\">"+option.getText()+"</div>");
			}
		}
		htmls.append("</div>");
		return htmls.toString();
	}
	
	public boolean isShowCheckAll() {
		return showCheckAll;
	}

	public void setShowCheckAll(boolean showCheckAll) {
		this.showCheckAll = showCheckAll;
	}

}
