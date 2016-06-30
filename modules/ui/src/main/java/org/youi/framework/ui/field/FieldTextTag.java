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



package org.youi.framework.ui.field;


import org.youi.framework.ui.field.model.AbstractField;
import org.youi.framework.ui.field.model.FieldText;
import org.youi.framework.ui.util.HtmlUtils;
import org.youi.framework.util.StringUtils;


public class FieldTextTag extends AbstractFieldTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7466089216331473577L;

	private String dataType;//数据格式
	
	private int fractionLength;//小数点位数
	
	private String expression;//校验表达式
	
	private String expressionMessage;//校验表达式提示信息
	
	private String validateSrc;//即时校验地址
	
	private int minLength;//最小长度
	
	private int maxLength;//最大长度
	
	private String format;//格式化串
	
	private boolean escapeSpecial;//特殊字符校验
	/**
	 * 创建fieldText组件的html内容
	 */
	public String uiContentHtml() {
		StringBuffer htmls = new StringBuffer();
		
		String valueHtml = "";
		if(StringUtils.isNotEmpty(defaultValue)){
			valueHtml = " value=\""+defaultValue+"\" ";
		}
		
		htmls.append("<input ");
		if(readonly){
			htmls.append(" readonly=\"readonly\" ");
		}
		htmls.append(" type=\"text\" class=\"form-control textInput value\""+valueHtml+"></input>");
//		htmls.append("<input  name=\""+getFieldFormName()+"\" type=\"hidden\" class=\"value\""+valueHtml+"/>");
		
		return HtmlUtils.generateFieldInnerHtml(htmls.toString(), 0,0, false);
	}
	
	protected AbstractField createField() {
		FieldText field = new FieldText();
		field.setDataType(dataType);
		field.setFractionLength(fractionLength);
		field.setExpressionMessage(expressionMessage);
		field.setExpression(expression);
		field.setValidateSrc(validateSrc);
		field.setMaxLength(maxLength);
		field.setMinLength(minLength);
		field.setFormat(format);
		field.setEscapeSpecial(escapeSpecial);
		return field;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * @param expression the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * @return the fractionLength
	 */
	public int getFractionLength() {
		return fractionLength;
	}

	/**
	 * @param fractionLength the fractionLength to set
	 */
	public void setFractionLength(int fractionLength) {
		this.fractionLength = fractionLength;
	}

	/**
	 * @return the expressionMessage
	 */
	public String getExpressionMessage() {
		return expressionMessage;
	}

	/**
	 * @param expressionMessage the expressionMessage to set
	 */
	public void setExpressionMessage(String expressionMessage) {
		this.expressionMessage = expressionMessage;
	}

	/**
	 * @return the validateSrc
	 */
	public String getValidateSrc() {
		return validateSrc;
	}

	/**
	 * @param validateSrc the validateSrc to set
	 */
	public void setValidateSrc(String validateSrc) {
		this.validateSrc = validateSrc;
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

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public boolean isEscapeSpecial() {
		return escapeSpecial;
	}

	public void setEscapeSpecial(boolean escapeSpecial) {
		this.escapeSpecial = escapeSpecial;
	}
	
}
