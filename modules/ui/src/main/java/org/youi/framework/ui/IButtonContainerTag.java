/**
 * 
 */
package org.youi.framework.ui;

import javax.servlet.jsp.tagext.Tag;

import org.youi.framework.ui.button.Button;


/**
 * @author Administrator
 * 
 */
public interface IButtonContainerTag extends Tag {
	/**
	 * 为button容器标签添加button
	 * @param button
	 */
	public void addButton(Button button);
}
