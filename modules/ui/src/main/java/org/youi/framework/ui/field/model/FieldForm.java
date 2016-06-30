/**
 * 
 */
package org.youi.framework.ui.field.model;

/**
 * @author zhyi_12
 *
 */
public class FieldForm extends AbstractField {
	
	private String formId;

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.field.model.AbstractField#addonProperies()
	 */
	@Override
	public String[] addonProperies() {
		return new String[]{stringProperty("formId", formId)};
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

}
