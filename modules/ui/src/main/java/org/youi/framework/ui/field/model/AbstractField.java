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

import org.youi.framework.ui.AbstractUiModel;
import org.youi.framework.ui.UiConstants;
import org.youi.framework.util.StringUtils;

public abstract class AbstractField extends AbstractUiModel {
	private String id;//id
	
	private String caption;//描述
	
	private String property;//form 字段标识
	
	private String html;//html内容
	
	private int column;//占位
	
	private boolean notNull;//是否允许为空
	
	private String defaultValue;//默认值
	
	private String prefix;//id前缀
	
	private String width;//宽度
	
	protected boolean notVisible;
	
	protected boolean readonly;
	
	protected String tooltips;//
	
	public String getTooltips() {
		return tooltips;
	}

	public void setTooltips(String tooltips) {
		this.tooltips = tooltips;
	}

	private static final int defaultFieldWidth = UiConstants.DEFAULT_FIELD_WIDTH;
	/**
	 * 返回field类型
	 * @return
	 */
	public String fieldType() {
		String className = this.getClass().getSimpleName();
		String fieldType;
		try {
			fieldType = className.substring(0,1).toLowerCase()+className.substring(1);
		} catch (RuntimeException e) {
			fieldType = "fieldCustom";
		}
		return fieldType;
	}
	
	public String getHtml(){
		return html;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @param html the html to set
	 */
	public void setHtml(String html) {
		this.html = html;
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

	public boolean isNotNull() {
		return notNull;
	}
	
	public void setNotNull(boolean notNull){
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

	/**
	 * @return the width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	public String toJson(){
		StringBuffer jsons = new StringBuffer();
		jsons.append("{");
		jsons.append(stringProperty("id",id));
		jsons.append(stringProperty("prefix",prefix));
		jsons.append(stringProperty("property",property));
		jsons.append(stringProperty("caption",caption));
		// 2013-10-11 zhouayng 处理换行符
		jsons.append(stringProperty("defaultValue",defaultValue!=null?defaultValue.replaceAll("\\n", "\\\\n").replaceAll("\\r", ""):null));
		jsons.append(stringProperty("width",width));
		jsons.append(intProperty("column",column));
		jsons.append(booleanProperty("readonly", readonly));
		jsons.append(stringProperty("type",fieldType()));
		if(notNull)jsons.append(booleanProperty("notNull",notNull));
		String[] addonProperies = addonProperies();
		if(addonProperies!=null&&addonProperies.length>0){
			for(int i=0;i<addonProperies.length;i++ ){
				StringBuilder str = new StringBuilder(addonProperies[i]);
				if(str.indexOf("renderer") != -1){//如果是renderer表示是函数,去掉单引号
					str = str.deleteCharAt(str.indexOf("'"));//去掉第一个和最后一个单引号
					str = str.deleteCharAt(str.lastIndexOf("'"));
				}
				jsons.append(str);
			}
		}
		jsons.append("initHtml:false");
		jsons.append("}");
		return jsons.toString();
	}
	
	public abstract String[] addonProperies();

	public boolean isLabelTop() {
		return false;
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
	 * @return
	 */
	public String innerHtml(){
		return "";
	}
	
	/**
	 * 
	 * @return
	 */
	protected int parseWidth(){
		String width = this.getWidth();
		
		if(StringUtils.isEmpty(width)){
			return defaultFieldWidth;
		}
		try {
			return Integer.parseInt(width);
		} catch (NumberFormatException e) {
			return defaultFieldWidth;
		}
	}
}
