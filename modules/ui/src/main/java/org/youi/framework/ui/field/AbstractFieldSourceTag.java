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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.youi.framework.ui.field.model.AbstractField;
import org.youi.framework.ui.field.model.AbstractSourceField;
import org.youi.framework.ui.field.model.FieldOption;
import org.youi.framework.ui.util.HtmlUtils;
import org.youi.framework.util.StringUtils;


public abstract class AbstractFieldSourceTag extends AbstractFieldTag {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7255249442314135062L;

	protected String src;//源路径
	
	protected String srcDataType;//源类型
	
	protected String show;//显示字段名
	
	protected String code;//值字段名

	protected String parentCode;//父值字段名

	protected boolean mixed;//是否混合显示
	
	protected String parents;//父field
	
	protected String parentsAlias;//父field参数别名
	
	protected String convert;
	//
	protected List<?> items;
	
	protected String itemText;
	
	protected String itemValue;
	
	protected String showProperty;
	
	protected boolean popup = true;
	
	private String valueMode = "string";
	
	private String emptyItemCaption;//空值名称
	
	//其他属性
	
	protected Collection<FieldOption> options = new ArrayList<FieldOption>();
	
	protected void initDefaultShow(){
		if(defaultShow==null){
			//从http提交参数取值
			if(showProperty!=null){
//				defaultShow = 
//					RequestUtils.getQueryParameter((HttpServletRequest) pageContext.getRequest(), "property:"+showProperty);
			}
			//从参数中取值
			if(defaultShow==null&&showProperty!=null){
				Object attrShow = this.pageContext.getRequest().getAttribute(showProperty);
				if(attrShow!=null){
					defaultShow = attrShow.toString();
				}
			}
		}
	}
	/* 
	 * @see org.youi.common.web.taglib.field.AbstractFieldTag#createField()
	 */
	protected AbstractField createField() {
		AbstractSourceField field = createSourceField();
		field.setSrc(getActionPath(src));
		field.setSrcDataType(srcDataType);
		field.setShow(show);
		field.setCode(code);
		field.setParentCode(parentCode);
		field.setMixed(mixed);
		field.setConvert(convert);
		field.setPopup(popup);
		field.setDefaultShow(defaultShow);
		field.setValueMode(valueMode);
		field.setEmptyItemCaption(emptyItemCaption);
		//
		field.setShowProperty(showProperty);
		
		if(parents!=null)field.setParents(parents.split(","));
		if(parentsAlias!=null)field.setParentsAlias(parentsAlias.split(","));
		if(convert!=null){
			this.addPageConvert(convert);
		}
		return field;
	}
	
	/**
	 * 生成数据下拉类型的组件的显示内容
	 */
	public String uiContentHtml() {
		StringBuilder htmls = new StringBuilder();
		htmls.append("<a href=\"###\"  "+(StringUtils.isNotEmpty(showProperty)?" showProperty=\""+showProperty+"\" ":"")+" class=\"form-control textInput\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\"/>")
		.append("<input type=\"hidden\" class=\"value\" name=\""+getFieldFormName()+"\"/>")
		.append("<div class=\"input-group-addon\" data-toggle=\"dropdown\" >")
		.append("	<span class=\"caret\"></span>")
		.append("</div><ul class=\"dropdown-menu\"></ul>");
		return HtmlUtils.generateFieldInnerHtml(htmls.toString(), 0,0, true);
	}
	
	protected boolean skipBody(){
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.AbstractUiTag#uiEndHtml()
	 */
	@Override
	public String uiEndHtml() {
		StringBuffer htmls = new StringBuffer();
		htmls.append(super.uiEndHtml());
		if(items!=null||options.size()>0){
			htmls.append(getPopPanelHtml());
			options.clear();//清空options
		}
		
		defaultShow = null;
		return htmls.toString();
	}

	protected String getPopPanelHtml(){
		return "";
	}
	/**
	 * 创建source类型的field模型
	 * @return
	 */
	protected abstract AbstractSourceField createSourceField();
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
	public String getParents() {
		return parents;
	}
	/**
	 * @param parents the parents to set
	 */
	public void setParents(String parents) {
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
	 * @return the items
	 */
	public List<?> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<?> items) {
		this.items = items;
	}

	/**
	 * @return the itemText
	 */
	public String getItemText() {
		return itemText;
	}

	/**
	 * @param itemText the itemText to set
	 */
	public void setItemText(String itemText) {
		this.itemText = itemText;
	}

	/**
	 * @return the itemValue
	 */
	public String getItemValue() {
		return itemValue;
	}

	/**
	 * @param itemValue the itemValue to set
	 */
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	/**
	 * @return the parentsAlias
	 */
	public String getParentsAlias() {
		return parentsAlias;
	}

	/**
	 * @param parentsAlias the parentsAlias to set
	 */
	public void setParentsAlias(String parentsAlias) {
		this.parentsAlias = parentsAlias;
	}
	

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
	
	public boolean isPopup() {
		return popup;
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
	public void setPopup(boolean popup) {
		this.popup = popup;
	}

	public void addOption(FieldOption option) {
		options.add(option);
	}

	protected String getPopPanelId(){
		return "field-pop-"+this.id.replace("_field_", "_"+(prefix==null?"field":prefix)+"_");//+prefix+"_"+this.property.replace("\\.", "_");
	}
	
	@Override
	protected String uiStyles() {
		return "dropdown input-group "+super.uiStyles();
	}
	
	public String getEmptyItemCaption() {
		return emptyItemCaption;
	}
	public void setEmptyItemCaption(String emptyItemCaption) {
		this.emptyItemCaption = emptyItemCaption;
	}
}
