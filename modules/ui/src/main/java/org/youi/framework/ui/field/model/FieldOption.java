/**
 * 
 */
package org.youi.framework.ui.field.model;

/**
 * @author Administrator
 *
 */
public class FieldOption {
	private String value;
	
	private String text;


	public FieldOption(String value, String text) {
		super();
		this.value = value;
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
