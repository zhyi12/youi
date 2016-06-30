/**
 * 
 */
package org.youi.framework.ui.field.model;

import org.apache.commons.lang.ArrayUtils;


//import org.youi.common.util.StringUtils;

/**
 * @author Administrator
 *
 */
public class FieldAutocomplete extends AbstractSourceField {
	
	private String params;//参数
	
//	private String alias;//别名
	
	private String propValueProperty;//

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getPropValueProperty() {
		return propValueProperty;
	}

	public void setPropValueProperty(String propValueProperty) {
		this.propValueProperty = propValueProperty;
	}

	@Override
	public String[] addonProperies() {
//		;
//		return StringUtils.mergeStringArrays(super.addonProperies(), new String[]{
//			arrayProperty("params",params==null?null:params.split(",")),
//			stringProperty("alias",alias)
//		});
		return (String[])ArrayUtils.addAll(super.addonProperies(), new String[]{
			arrayProperty("params",params==null?null:params.split(",")),
			stringProperty("propValueProperty",propValueProperty)
		});
	}
	
	public String innerHtml() {
		StringBuffer htmls = new StringBuffer();
		int fieldWidth = parseWidth()-16;
		
		htmls.append("<input style=\"width:"+(fieldWidth)+"px;\" type=\"text\" class=\"textInput\"></input>");
		htmls.append("<input type=\"hidden\" class=\"value\"></input>");
		htmls.append("<div class=\"field-invalid\"></div>");
		
		return htmls.toString();
	}
}
