/**
 * 
 */
package org.youi.framework.ui.field;

import org.youi.framework.ui.field.model.AbstractSourceField;
import org.youi.framework.ui.field.model.FieldAutocomplete;
import org.youi.framework.ui.util.HtmlUtils;

/**
 * 自动填充组件
 * @author Administrator
 *
 */
public class FieldAutocompleteTag extends AbstractFieldSourceTag {
	
	/**
	 * 
	 */ 
	private static final long serialVersionUID = -9009668570307593328L;
	
	private String params;
	
	private String alias;
	
	private String propValueProperty;

	public String getPropValueProperty() {
		return propValueProperty;
	}

	public void setPropValueProperty(String propValueProperty) {
		this.propValueProperty = propValueProperty;
	}

	public String uiContentHtml() {
		StringBuffer htmls = new StringBuffer();
		
		htmls.append("<input type=\"text\" class=\"textInput form-control\"></input>");
		htmls.append("<input type=\"hidden\" class=\"value\"></input>");
		
		if(this.styleClass!=null&&this.styleClass.contains("addon-search")){
			htmls.append("<div class=\"input-group-addon\"><span class=\"glyphicon glyphicon-search\"/></div>");
		}
		return HtmlUtils.generateFieldInnerHtml(htmls.toString(), 0,0, false);
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	protected AbstractSourceField createSourceField() {
		FieldAutocomplete field = new FieldAutocomplete();
		field.setParams(params);
		field.setPropValueProperty(propValueProperty);
		return field;
	}
}
