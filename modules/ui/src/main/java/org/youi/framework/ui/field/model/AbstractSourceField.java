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

public abstract class AbstractSourceField extends AbstractField {
	protected String src;//源路径
	
	protected String srcDataType;//源类型
	
	protected String show;//显示字段名
	
	protected String code;//值字段名
	
	protected boolean mixed;//是否混合显示
	
	protected String parentCode;//父值字段名
	
	protected String[] parents;
	
	protected String[] parentsAlias;
	
	protected String convert;
	
	protected String showProperty;
	
	protected String defaultShow;
	
	protected boolean popup;
	

	protected String valueMode;
	
	protected String emptyItemCaption;

	/**
	 * @return the showProperty
	 */
	public String getShowProperty() {
		return showProperty;
	}

	/**
	 * @param showProperty the showProperty to set
	 */
	public void setShowProperty(String showProperty) {
		this.showProperty = showProperty;
	}

	public String getDefaultShow() {
		return defaultShow;
	}

	public void setDefaultShow(String defaultShow) {
		this.defaultShow = defaultShow;
	}

	/**
	 * @return the src
	 */
	public String getSrc() {
		return src;
	}

	/**
	 * @param src the src to set
	 */
	public void setSrc(String src) {
		this.src = src;
	}

	/**
	 * @return the srcDataType
	 */
	public String getSrcDataType() {
		return srcDataType;
	}

	/**
	 * @param srcDataType the srcDataType to set
	 */
	public void setSrcDataType(String srcDataType) {
		this.srcDataType = srcDataType;
	}

	/**
	 * @return the show
	 */
	public String getShow() {
		return show;
	}

	/**
	 * @param show the show to set
	 */
	public void setShow(String show) {
		this.show = show;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the mixed
	 */
	public boolean isMixed() {
		return mixed;
	}

	/**
	 * @param mixed the mixed to set
	 */
	public void setMixed(boolean mixed) {
		this.mixed = mixed;
	}

	/**
	 * @return the parentCode
	 */
	public String getParentCode() {
		return parentCode;
	}

	/**
	 * @param parentCode the parentCode to set
	 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	/**
	 * @return the parents
	 */
	public String[] getParents() {
		return parents;
	}

	/**
	 * @param parents the parents to set
	 */
	public void setParents(String[] parents) {
		this.parents = parents;
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
	 * @return the parentsAlias
	 */
	public String[] getParentsAlias() {
		return parentsAlias;
	}

	/**
	 * @param parentsAlias the parentsAlias to set
	 */
	public void setParentsAlias(String[] parentsAlias) {
		this.parentsAlias = parentsAlias;
	}

	public boolean isPopup() {
		return popup;
	}

	public void setPopup(boolean popup) {
		this.popup = popup;
	}


	/**
	 * @return the valueMode
	 */
	public String getValueMode() {
		return valueMode;
	}

	/**
	 * @param valueMode the valueMode to set
	 */
	public void setValueMode(String valueMode) {
		this.valueMode = valueMode;
	}

	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.field.model.AbstractField#addonProperies()
	 */
	public String[] addonProperies() {
		String[] addonProperies = {
			stringProperty("src",src),
			stringProperty("srcDataType",srcDataType),
			stringProperty("code",code),
			stringProperty("show",show),
			stringProperty("convert",convert),
			stringProperty("showProperty",showProperty),
			stringProperty("defaultShow",defaultShow),
			booleanProperty("mixed",mixed),
			booleanProperty("popup",popup),
			arrayProperty("parents",parents),
			arrayProperty("parentsAlias",parentsAlias),
			stringProperty("valueMode",valueMode),
			stringProperty("emptyItemCaption", emptyItemCaption)
		};
		return addonProperies;
	}
	//
	public String innnerHtml(){
		StringBuffer htmls = new StringBuffer();
		int iconWidth = 16;
		int fieldWidth = parseWidth();
		htmls.append("<table cellpadding=\"0\" cellspacing=\"0\" width=\""+fieldWidth+"\"><tbody><tr>");
		htmls.append(	"<td valign=\"top\" width=\""+(fieldWidth-iconWidth-6)+"\"><input style=\"width:"+(fieldWidth-iconWidth-6)+"px;\" type=\"text\" ").append(showProperty==null?"":("showProperty=\""+showProperty+"\""))
		.append(" readonly=\"1\" class=\"textInput\"/>")
		.append("<input name=\""+getProperty()+"\" type=\"hidden\" class=\"value\"/></td>");
		htmls.append(	"<td class=\"youi-icon select-down\"></td>");
		htmls.append("</tr></tbody></table>");
		
		return htmls.toString();
	}

	public String getEmptyItemCaption() {
		return emptyItemCaption;
	}

	public void setEmptyItemCaption(String emptyItemCaption) {
		this.emptyItemCaption = emptyItemCaption;
	}
	
	
}
