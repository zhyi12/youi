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
import org.youi.framework.ui.field.model.FieldArea;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jul 12, 2010
 */
public class FieldAreaTag extends AbstractFieldTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = -852355024888542092L;

	private int rows;
	
	private int cols;
	
	private int maxLength;
	
	private int minLength;
	
	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.field.AbstractFieldTag#createField()
	 */
	@Override
	protected AbstractField createField() {
		FieldArea field = new FieldArea();
		field.setCols(cols);
		field.setRows(rows);
		field.setMinLength(minLength);
		field.setMaxLength(maxLength);
		
		this.defaultValue = null;
		return field;
	}

	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.AbstractUiTag#uiContentHtml()
	 */
	@Override
	public String uiContentHtml() {
		StringBuffer htmls = new StringBuffer();
//		int inputWidth = Integer.parseInt(width) - 16;
		htmls.append("<textarea ");
		if(readonly){
			htmls.append(" readonly=\""+readonly+"\" ");
		}
		if(rows>0){
			htmls.append(" rows=\""+rows+"\" ");
		}
		if(cols>0){
			htmls.append(" cols=\""+cols+"\" ");
		}
		
		if(height>0){
			htmls.append(" style=\"height:"+height+"px;\" ");
		}
		
		htmls.append("name=\""+getFieldFormName()+"\"  type=\"text\" class=\"textInput value form-control\">");
		
		//从参数中取值
//		String text;
//		text = this.pageContext.getRequest().getParameter(this.property);
//		//从页面变量中取值
//		if(StringUtils.isEmpty(text)){
//			Object attrProperty = this.pageContext.getRequest().getAttribute(property);
//			if(attrProperty!=null){
//				text = attrProperty.toString();
//			}
//		}
//		if(StringUtils.isNotEmpty(text)){
//			htmls.append(text);
//		}
		
		htmls.append("</textarea>");
		htmls.append("<div class=\"field-invalid\"></div>");
		return htmls.toString();
	}

	/**
	 * @return the rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * @return the cols
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * @param cols the cols to set
	 */
	public void setCols(int cols) {
		this.cols = cols;
	}
	
	/**
	 * @return the maxLength
	 */
	public int getMaxLength() {
		return maxLength;
	}

	/**
	 * @param maxLength the maxLength to set
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * @return the minLength
	 */
	public int getMinLength() {
		return minLength;
	}

	/**
	 * @param minLength the minLength to set
	 */
	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}


}
