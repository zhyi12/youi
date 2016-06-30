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
import java.util.List;

import org.youi.framework.ui.AbstractUiTag;
import org.youi.framework.ui.IFieldContainerTag;
import org.youi.framework.ui.field.model.AbstractField;

public class FieldLayoutTag extends AbstractUiTag implements IFieldContainerTag{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7533559047445773224L;
	
	private int columns = 2;
	
	private String prefix = "field";
	
	private List<Object> fields;//field集合
	
	private boolean showBorder = true;
	
	private boolean showLabel = true;//显示field 的label
	
	private boolean showTooltips = false;//显示field 的tooltips列
	
	private String labelWidths;
	
	private String type = "bootstrap"; 
	/**
	 * UI初始化
	 * fileds设置为初始集合
	 */
	public void initUi(){
		fields = new ArrayList<Object>();
	}
	
	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.IFieldContainerTag#addField(org.youi.common.web.taglib.field.model.AbstractField)
	 */
	public void addField(AbstractField field){
		field.setPrefix(prefix);
		fields.add(field);
	}
	
	public String uiContentHtml() {
		ILayoutContent layoutContent = new BootstrapLayoutContent();
		
		layoutContent.setColumns(columns);
		layoutContent.setFields(fields);
		layoutContent.setShowBorder(showBorder);
		layoutContent.setShowLabel(showLabel);
		layoutContent.setLabelWidths(labelWidths);
		layoutContent.setShowTooltips(showTooltips);
		
		StringBuffer htmls = new StringBuffer();
		
		//caption 显示
		htmls.append(layoutContent.getContent());
		return htmls.toString();
	}

	protected String uiScripts() {
		AbstractField field;
		StringBuffer scripts = new StringBuffer();
		scripts.append("$('#"+id+"').fieldLayout({");
		if(prefix!=null){
			scripts.append(		"prefix:'"+prefix+"',");
		}
		if(width!=null){
			scripts.append(		"width:'"+width+"',");
		}
		if(fields!=null&&fields.size()>0){
			scripts.append("fields:[");
			int prefixLength = scripts.length();
			for(int i=0;i<fields.size();i++){
				field = (AbstractField)fields.get(i);
//				if("fieldCustom".equals(field.fieldType())){
//					continue;
//				}
				if(prefixLength<scripts.length()){
					scripts.append(",");
				}
				scripts.append(field.toJson());
				field = null;
			}
			scripts.append("],");
		}
		scripts.append(	"	initHtml:false");
		scripts.append(	"});");
		return wrapScripts(scripts.toString());
	}

	protected String uiStyles() {
		return "youi-fieldLayout form-horizontal youi-bgcolor col-sm-12";//UiConstants.STYLE_FIELDLAYOUT;
	}

	/**
	 * @return the columns
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * @param columns the columns to set
	 */
	public void setColumns(int columns) {
		this.columns = columns;
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

	/**
	 * @return the showBorder
	 */
	public boolean isShowBorder() {
		return showBorder;
	}

	/**
	 * @param showBorder the showBorder to set
	 */
	public void setShowBorder(boolean showBorder) {
		this.showBorder = showBorder;
	}
	

	public boolean isShowLabel() {
		return showLabel;
	}

	public void setShowLabel(boolean showLabel) {
		this.showLabel = showLabel;
	}

	public boolean isShowTooltips() {
		return showTooltips;
	}

	public void setShowTooltips(boolean showTooltips) {
		this.showTooltips = showTooltips;
	}

	/**
	 * @return the labelWidths
	 */
	public String getLabelWidths() {
		return labelWidths;
	}

	/**
	 * @param labelWidths the labelWidths to set
	 */
	public void setLabelWidths(String labelWidths) {
		this.labelWidths = labelWidths;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void doFinally() {
		super.doFinally();
		showLabel = true;
		this.type = null;
	}

}
