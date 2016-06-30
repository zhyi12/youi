package org.youi.framework.ui;

import javax.servlet.jsp.tagext.Tag;

public interface IPageTag extends Tag{

	public String getPageId();

	public void addPageScript(String uiScripts);

}
