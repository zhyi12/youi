/*
 * YOUI框架
 * Copyright 2012 the original author or authors.
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
package org.youi.framework.ui.grid;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.youi.framework.ui.AbstractTag;
import org.youi.framework.ui.IButtonContainerTag;
import org.youi.framework.ui.button.Button;
import org.youi.framework.util.StringUtils;

/**
 * <p>@系统描述:</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午04:14:17</p>
 */
public class GridColTag extends AbstractTag implements IButtonContainerTag {
	
	private String id;//id
	
	private String caption;//描述
	
	private String property;//form 字段标识
	
	private String showProperty;//显示用属性
	
	private String defaultValue;//默认值
	
	private String width = "80";//宽度
	
	private String type;//列类型
	
	private String href;
	
	private String params;
	
	private String target;
	
	private String align;
	
	private String convert;
	
	private String notConvertValue;
	
	private String orderBy;//排序，值为 asc 和 desc
	
	private String sortProperty;//排序属性
	
	private String editor;//单元格编辑  fieldText fieldSelect
	
	private String editorOptions;//单元格编辑属性
	
	private boolean fixed;//固定列标志
	
	private String renderer;
	
	private String highlight;//高亮函数内容
	
	private List<Button> buttons;
	
	private int colNum = 1;
	
	private String format;//数据格式
	
	private String textFormat;//展示文本格式
	/**
	 * 
	 */
	private static final long serialVersionUID = -837325863622560122L;

	/* 
	 * col组件不生成html，只把列模型加入上级grid组件中
	 * (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	public int doStartTagInternal() throws JspException {
		this.caption = this.getI18nMessage(caption);
		if("button".equals(type)){
			buttons = new ArrayList<Button>();
			return EVAL_PAGE;
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		Tag tag  = findAncestorWithClass(this, IColContainer.class);
		if(tag!=null){
			((IColContainer)tag).addCol(createCol());
			if(convert != null){
				this.addPageConvert(convert);
			}
		}
		if("button".equals(type)){
			return EVAL_PAGE;
		}
		return SKIP_BODY;
	}

	private GridCol createCol() {
		GridCol gridCol = new GridCol();
		gridCol.setCaption(caption);
		gridCol.setProperty(property);
		gridCol.setDefaultValue(defaultValue);
		gridCol.setId(id);
		gridCol.setWidth(width);
		gridCol.setType(type);
		gridCol.setHref(href);
		gridCol.setParams(params);
		gridCol.setTarget(target);
		gridCol.setAlign(align);
		gridCol.setConvert(convert);
		gridCol.setNotConvertValue(notConvertValue);
		gridCol.setOrderBy(orderBy);
		gridCol.setColNum(colNum);
		gridCol.setSortProperty(sortProperty);
		gridCol.setButtons(buttons);
		gridCol.setEditor(editor);
		
		if(renderer!=null){
			if(renderer.startsWith("'")||renderer.startsWith("function")){
				gridCol.setRenderer(renderer);
			}else{
				gridCol.setRenderer(this.wrapWithPageId(renderer));
			}
		}
		
		gridCol.setFormat(format);
		gridCol.setTextFormat(textFormat);
		
		if(StringUtils.isNotEmpty(editorOptions)){
			gridCol.setEditorOptions(editorOptions.replace("\\", "\\\\"));
		}
		gridCol.setFixed(fixed);
		gridCol.setShowProperty(showProperty);
		
		if(StringUtils.isNotEmpty(highlight)){
			gridCol.setHighlight(highlight);
		}
		return gridCol;
	}

	/**
	 * @return the highlight
	 */
	public String getHighlight() {
		return highlight;
	}

	/**
	 * @param highlight the highlight to set
	 */
	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
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

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * @return the params
	 */
	public String getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(String params) {
		this.params = params;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the align
	 */
	public String getAlign() {
		return align;
	}

	/**
	 * @param align the align to set
	 */
	public void setAlign(String align) {
		this.align = align;
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
	 * @return the notConvertValue
	 */
	public String getNotConvertValue() {
		return notConvertValue;
	}

	/**
	 * @param notConvertValue the notConvertValue to set
	 */
	public void setNotConvertValue(String notConvertValue) {
		this.notConvertValue = notConvertValue;
	}

	public String getSortProperty() {
		return sortProperty;
	}

	public void setSortProperty(String sortProperty) {
		this.sortProperty = sortProperty;
	}

	public void addButton(Button button) {
		buttons.add(button);
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getEditorOptions() {
		return editorOptions;
	}

	public void setEditorOptions(String editorOptions) {
		this.editorOptions = editorOptions;
	}

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	/**
	 * @return the renderer
	 */
	public String getRenderer() {
		return renderer;
	}

	/**
	 * @param renderer the renderer to set
	 */
	public void setRenderer(String renderer) {
		this.renderer = renderer;
	}

	public String getShowProperty() {
		return showProperty;
	}

	public void setShowProperty(String showProperty) {
		this.showProperty = showProperty;
	}

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
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
	 * @return the textFormat
	 */
	public String getTextFormat() {
		return textFormat;
	}

	/**
	 * @param textFormat the textFormat to set
	 */
	public void setTextFormat(String textFormat) {
		this.textFormat = textFormat;
	}

}
