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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.youi.framework.ui.AbstractUiTag;
import org.youi.framework.ui.IFieldContainerTag;
import org.youi.framework.ui.field.model.AbstractField;
import org.youi.framework.util.StringUtils;



/**
 * @author Administrator
 *
 */
public abstract class AbstractFieldTag extends AbstractUiTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7050633664940936163L;

	protected String property;//form 字段标识
	
	protected String defaultValue;//默认值
	
	protected String defaultShow;//默认显示值
	
	protected int column = 1;//占位
	
	protected boolean notNull;//是否允许为空
	
	protected String prefix;//id前缀 field组件的id由prefix+property组成
	
	protected boolean notVisible;
	
	protected boolean readonly;
	
	protected String principalProperty; //用于覆盖defaultValue
	
	protected String operator;//
	
	protected String tooltips;//
	
	//
	protected AbstractField field;
	/**
	 * ui初始化
	 */
	public void initUi(){
		//ui初始化
//		if(width==null){
//			width = ""+(UiConstants.DEFAULT_FIELD_WIDTH+UiConstants.FIELD_INVALID_WIDTH);
//		}else if("0".equals(width)||"100%".equals(width)){
//			
//		}else{
//			//增加校验TD的宽度
//			width = (Integer.parseInt(width) + UiConstants.FIELD_INVALID_WIDTH)+"";
//		}
		
		this.width = null;
		
		caption = this.getI18nMessage(caption);
		initDefaultVlaue();
		initDefaultShow();
	}
	
	private void initDefaultVlaue(){
		String principalValue = this.parsePrincipalValue();
		if(principalValue!=null){//从用户对象中取值
			defaultValue = principalValue;
		}
		//从参数中取值
		if(StringUtils.isEmpty(defaultValue)){
			defaultValue = this.pageContext.getRequest().getParameter(this.property);
		}
		//从页面变量中取值
		if(StringUtils.isEmpty(defaultValue)){
			Object attrProperty = this.pageContext.getRequest().getAttribute(property);
			if(attrProperty!=null){
				defaultValue = attrProperty.toString();
			}
		}
		
//		if(StringUtils.isNotEmpty(defaultValue)){
//			try {
//				defaultValue = URLEncoder.encode(defaultValue, "UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	protected void initDefaultShow(){
		if(defaultShow==null){
			defaultShow = defaultValue;
		}
	}
	/**
	 * 
	 */
	public int doStartTagInternal() throws JspException {
		Tag tag  = findAncestorWithClass(this, IFieldContainerTag.class);
		initUi();
		
		if(tag!=null){
			this.prefix = ((IFieldContainerTag)tag).getPrefix();
		}
		
		if(this.prefix==null){
			this.prefix = "field";
		}
		
		if(this.id==null){
			this.id = widgetUUID();
		}
		
		if(tag==null){
			super.doStartTagInternal();
		}
		return skipBody()?SKIP_BODY:EVAL_BODY_INCLUDE;
	}
	
	protected boolean skipBody(){
		return true;
	}
	/**
	 * @return
	 */
	protected String uiHtmls() {
		StringBuffer htmls = new StringBuffer();
		htmls.append(uiStartHtml());
		htmls.append(uiContentHtml());
		htmls.append(uiEndHtml());
		return htmls.toString();
	}
	/**
	 * 创建field模型
	 */
	protected abstract AbstractField createField();
	/**
	 * 
	 */
	public int doEndTag() throws JspException {
		Tag tag  = findAncestorWithClass(this, IFieldContainerTag.class);
		this.field = createField();
		field.setCaption(caption);
		field.setColumn(column);
		field.setProperty(property);
		field.setDefaultValue(getDefaultValue());
		field.setNotNull(notNull);
		field.setPrefix(prefix==null?"field":prefix);
		field.setWidth(width);
		field.setReadonly(readonly);
		
		field.setNotVisible(notVisible);
		field.setId(id);
		field.setPrefix(prefix);
		field.setTooltips(tooltips==null?"":tooltips);
		if(tag==null){
			super.doEndTag();
		}else{
			field.setHtml(uiHtmls());
			((IFieldContainerTag)tag).addField(field);//加入模型到上级的field容器中
		}
		
		id = null;
		prefix = null;
		defaultValue = null;
		return EVAL_PAGE;
	}

	protected String uiScripts() {
		StringBuffer scripts = new StringBuffer();
		scripts.append("$('#"+id+"')."+getWidgetName()+"(");
		scripts.append(field.toJson());
		scripts.append(");");
		return wrapScripts(scripts.toString());
	}
	
	private String getWidgetName(){
		String name = this.field.getClass().getSimpleName();
		
		return StringUtils.lowerCaseFirstLetter(name);
	}

	protected String uiStyles() {
		return "youi-field "+widgetName()+(readonly?" readonly":"");
	}

	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @param property the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * @return the notNull
	 */
	public boolean isNotNull() {
		return notNull;
	}

	/**
	 * @param notNull the notNull to set
	 */
	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}
	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}
	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}
	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.AbstractUiTag#widgetUUID()
	 */
	protected String widgetUUID() {
		return this.wrapWithPageId(prefix+"_"+property.replace(".", "_"));
	}
	
	/**
	 * 获取field在form提交时的名称
	 * @return
	 */
	protected String getFieldFormName(){
		return ""+property;
	}
	/**
	 * @return the notVisible
	 */
	public boolean isNotVisible() {
		return notVisible;
	}
	/**
	 * @param notVisible the notVisible to set
	 */
	public void setNotVisible(boolean notVisible) {
		this.notVisible = notVisible;
	}
	/**
	 * @return the readonly
	 */
	public boolean isReadonly() {
		return readonly;
	}
	/**
	 * @param readonly the readonly to set
	 */
	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}
	/**
	 * @return the principalProperty
	 */
	public String getPrincipalProperty() {
		return principalProperty;
	}
	/**
	 * @param principalProperty the principalProperty to set
	 */
	public void setPrincipalProperty(String principalProperty) {
		this.principalProperty = principalProperty;
	}
	
	
	private String parsePrincipalValue(){
//		if(principalProperty!=null){
//			return PrincipalUtils.getPrincipalValue(principalProperty);
//		}
		return null;
	}

	@Override
	public void doFinally() {
		super.doFinally();
		this.id = null;
		this.prefix = null;
		this.defaultValue = null;
		this.field = null;
		this.width = null;
		this.property = null;
	}
	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.AbstractUiTag#uiContentHtml()
	 */
	@Override
	public String uiContentHtml() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getDefaultShow() {
		return defaultShow;
	}
	public void setDefaultShow(String defaultShow) {
		this.defaultShow = defaultShow;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	protected String[][] uiAttrs(){
		return new String[][]{{"operator",operator},{"data-property",property}};
	}

	public String getTooltips() {
		return tooltips;
	}

	public void setTooltips(String tooltips) {
		this.tooltips = tooltips;
	}
	
}
