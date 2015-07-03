/**
 * 
 */
package org.youi.framework.core.dataobj;

/**
 * @author zhyi_12
 *
 */
public class TermProperties implements Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8856134487275173156L;
	
	private String codeProperty;//条件匹配为以输入的term开始
	
	private String textProperty;//条件匹配任意
	
	public TermProperties(String codeProperty, String textProperty) {
		super();
		this.codeProperty = codeProperty;
		this.textProperty = textProperty;
	}

	public String getCodeProperty() {
		return codeProperty;
	}

	public void setCodeProperty(String codeProperty) {
		this.codeProperty = codeProperty;
	}

	public String getTextProperty() {
		return textProperty;
	}

	public void setTextProperty(String textProperty) {
		this.textProperty = textProperty;
	}
	
	
}
