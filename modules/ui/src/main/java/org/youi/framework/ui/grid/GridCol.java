/**
 * 
 */
package org.youi.framework.ui.grid;

import java.util.List;

import org.youi.framework.ui.button.Button;
import org.youi.framework.ui.field.model.AbstractField;
import org.youi.framework.ui.util.JsUtils;
import org.youi.framework.util.PojoMapper;


/**
 * @author Administrator
 *
 */
public class GridCol extends AbstractField {
private String type = "text";
	
	private String href;
	
	private String params;
	
	private String target;
	
	private String align;
	
	private String convert;
	
	private String notConvertValue;//为找到转换时匹配的值
	
	private String orderBy;//排序
	
	private String sortProperty;//排序属性
	
	private String editor;//单元格编辑  fieldText fieldSelect
	
	private String editorOptions;//单元格编辑属性
	
	private boolean fixed;//固定列标志
	
	private String renderer;
	
	private String showProperty;
	
	private List<Button> buttons;
	
	private int colNum = 1;
	
	private String highlight;//高亮显示
	
	private String format;//数据格式
	
	private String textFormat;//展示文本格式
	
	public GridCol(){
		
	}
	
	public GridCol(String property,String caption,String width,
			String align,String type,boolean fixed){
		this.setProperty(property);
		this.setCaption(caption);
		this.setWidth(width);
		this.setAlign(align);
		this.setType(type);
		this.fixed = fixed;
	}
	
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
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
	/* (non-Javadoc)
	 * 
	 * editorOptions
	 * @see org.youi.common.web.taglib.field.model.AbstractField#addonProperies()
	 */
	public String[] addonProperies() {
		return new String[]{
			stringProperty("type",type),
			stringProperty("href",href),
			stringProperty("params",params),
			stringProperty("target",target),
			stringProperty("align",align),
			stringProperty("convert",convert),
			stringProperty("orderBy",orderBy),
			stringProperty("renderer", renderer),
			stringProperty("notConvertValue",notConvertValue),
			stringProperty("sortProperty",sortProperty),
			booleanProperty("fixed",fixed),
			intProperty("colNum", colNum),
			stringProperty("editor",editor),
			stringProperty("highlight",highlight),
			stringProperty("format",format),
			stringProperty("textFormat",textFormat),
			JsUtils.propertyValue("editorOptions", editorOptions, JsUtils.JSON_PROP_INT),
			JsUtils.propertyValue("buttons", buildButtonOptions(), JsUtils.JSON_PROP_INT)
		};
	}
	
	private String buildButtonOptions(){
		if(buttons!=null&&buttons.size()>0){
			return PojoMapper.toJson(buttons, false).toString();
		}
		return null;
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
	public String getSortProperty() {
		return sortProperty;
	}
	public void setSortProperty(String sortProperty) {
		this.sortProperty = sortProperty;
	}
	public List<Button> getButtons() {
		return buttons;
	}
	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
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
