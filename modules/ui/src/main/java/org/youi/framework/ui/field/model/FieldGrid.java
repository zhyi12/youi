/**
 * 
 */
package org.youi.framework.ui.field.model;


/**
 * <p>Title:UI组件</p>
 *
 * <p>Description:</p>
 *
 * <p>Copyright:Copyright (c) 2011.11</p>
 *
 * <p></p>
 *
 * @author zhouyi
 * @version 1.0.0
 */
public class FieldGrid extends AbstractSourceField {
	private String gridId;
	
	private String mode;
	
	private String idAttr;
	
	private String textAttr;

	public String getGridId() {
		return gridId;
	}

	public void setGridId(String gridId) {
		this.gridId = gridId;
	}
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getIdAttr() {
		return idAttr;
	}

	public void setIdAttr(String idAttr) {
		this.idAttr = idAttr;
	}

	public String getTextAttr() {
		return textAttr;
	}

	public void setTextAttr(String textAttr) {
		this.textAttr = textAttr;
	}

	public String[] addonProperies() {
		String[] addonProperies = {
				stringProperty("gridId",gridId),
				stringProperty("mode",mode),
				arrayProperty("parents",parents),
				arrayProperty("parentsAlias",parentsAlias),
				stringProperty("idAttr",idAttr),
				stringProperty("textAttr",textAttr)
			};
			return addonProperies;
	}
}
