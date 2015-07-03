package org.youi.framework.core.dataobj;

public class TermRecord extends Record{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3705507322252941933L;

	private String label;
	
	private String value;
	
	private String code;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
