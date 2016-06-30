/**
 * 
 */
package org.youi.framework.ui.field;

import java.util.List;

/**
 * @author zhyi_12
 *
 */
public abstract class AbstractLayoutContent implements ILayoutContent{
	private List<Object> fields;
	
	private int columns = 2;//列数
	
	private boolean showBorder;
	
	private boolean showLabel;
	
	private boolean showTooltips;//是否显示tooltip
	
	private String labelWidths;
	
	protected static final String DEFAULT_LABEL_WIDTH="100";
	

	public List<Object> getFields() {
		return fields;
	}


	public void setFields(List<Object> fields) {
		this.fields = fields;
	}


	public int getColumns() {
		return columns;
	}


	public void setColumns(int columns) {
		this.columns = columns;
	}


	public boolean isShowBorder() {
		return showBorder;
	}


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


	public String getLabelWidths() {
		return labelWidths;
	}


	public void setLabelWidths(String labelWidths) {
		this.labelWidths = labelWidths;
	}
}
