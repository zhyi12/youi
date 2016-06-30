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
 * @创建时间 Jul 12, 2010
 */
public class FieldArea extends AbstractField {
	private int rows;
	
	private int cols;
	
	private int maxLength;
	
	private int minLength;
	
	@Override
	public String[] addonProperies() {
		String[] addonProperies = {
				intProperty("maxLength",maxLength),
				intProperty("minLength",minLength)
			};
			return addonProperies;
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

	public boolean isLabelTop() {
		return true;
	}
	
	@Override
	public String innerHtml() {
		StringBuffer htmls = new StringBuffer();
		int inputWidth = parseWidth() - 16;
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
		htmls.append("name=\""+getProperty()+"\" style=\"width:"+(inputWidth )+"px;\" type=\"text\" class=\"textInput value\"></textarea>");
		htmls.append("<div class=\"field-invalid\"></div>");
		return htmls.toString();
	}
}
