/*
 * Copyright (c) 2009 zhyi_12.  All rights reserved. 
 * This software was developed by zhyi_12 and is provided under the terms 
 * of the GNU Lesser General Public License, Version 2.1. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.gnu.org/licenses/lgpl-2.1.txt. The Original Code is Pentaho 
 * youi component.  The Initial Developer is zhyi_12.
 *
 * Software distributed under the GNU Lesser Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
*/



package org.youi.framework.ui.field.model;


public class FieldLabel extends AbstractField {

	private String convert;
	
	private String mixed;
	
	private String format;//值格式
	
	private String textFormat;//文本格式
	
	public String[] addonProperies() {
		return new String[]{
			stringProperty("convert", convert),
			stringProperty("mixed", mixed),
			stringProperty("format",format),
			stringProperty("textFormat",textFormat)
		};
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return the convert
	 */
	public String getConvert() {
		return convert;
	}

	/**
	 * @param convert the convert to set
	 */
	public void setConvert(String convert) {
		this.convert = convert;
	}

	/**
	 * @return the mixed
	 */
	public String getMixed() {
		return mixed;
	}

	/**
	 * @param mixed the mixed to set
	 */
	public void setMixed(String mixed) {
		this.mixed = mixed;
	}
	
	public String getTextFormat() {
		return textFormat;
	}

	public void setTextFormat(String textFormat) {
		this.textFormat = textFormat;
	}

	@Override
	public String innerHtml() {
		StringBuffer htmls = new StringBuffer();
		htmls.append("<a class=\"textShow\" onclick=\"return false;\" href=\"#\"></a>");
		htmls.append("<input name=\""+getProperty()+"\" type=\"hidden\" class=\"value\"></input>");
		return htmls.toString();
	}
	
}
