/**
 * 
 */
package org.youi.framework.ui.tree;

import org.youi.framework.ui.AbstractUiTag;

/**
 * @author zhyi_12
 *
 */
public class TreeNodeTag extends AbstractUiTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6071674126083727992L;
	
	private boolean leaf;

	/* (non-Javadoc)
	 * @see com.gsoft.framework.ui.AbstractUiTag#initUi()
	 */
	@Override
	public void initUi() {
		
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.ui.AbstractUiTag#uiContentHtml()
	 */
	@Override
	public String uiContentHtml() {
		return "";
	}

	@Override
	public String uiStartHtml() {
		StringBuffer htmls = new StringBuffer();
		htmls.append(super.uiStartHtml());
		if(!leaf){
			htmls.append("<div class=\"tree-trigger\"></div>");
		}
		htmls.append("<span  class=\"tree-span"+(leaf?"":" expandable expanded")+"\">")
			.append("<a class=\"tree-a\">")
			.append(this.caption)
			.append("</a>")
			.append("</span>");
		htmls.append("<ul>");
		return htmls.toString();
	}

	@Override
	public String uiEndHtml() {
		StringBuffer htmls = new StringBuffer();
		
		htmls.append("</ul>");
		htmls.append(super.uiEndHtml());
		return htmls.toString();
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.ui.AbstractUiTag#uiStyles()
	 */
	@Override
	protected String uiStyles() {
		return "treeNode"+(leaf?"":" expandable expanded");
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.ui.AbstractUiTag#uiScripts()
	 */
	@Override
	protected String uiScripts() {
		return "";
	}

	@Override
	protected String tagElementName() {
		return "li";
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

}
