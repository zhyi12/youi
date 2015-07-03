/**
 * 
 */
package org.youi.framework.core.web.view;

/**
 * @author Administrator
 *
 */
public class PassedMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1838714533099774309L;
	private boolean passed;
	
	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	public PassedMessage(String code, String info,boolean passed) {
		super(code, info);
		this.passed = passed;
	}

}
