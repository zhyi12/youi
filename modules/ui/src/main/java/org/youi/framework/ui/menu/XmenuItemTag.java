/**
 * 
 */
package org.youi.framework.ui.menu;

import org.apache.commons.lang.StringUtils;
import org.youi.framework.ui.AbstractUiTag;

/**
 * @author zhyi_12
 *
 */
public class XmenuItemTag extends AbstractUiTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1049353094700698559L;
	
	private String name;
	
	private String icon;
	
	private String action;
	
	private String groups;//分组

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#initUi()
	 */
	@Override
	public void initUi() {
		this.setId(name);
	}

	@Override
	protected String tagElementName() {
		return "li";
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#uiContentHtml()
	 */
	@Override
	public String uiContentHtml() {
		return "";
	}

	@Override
	protected String[][] uiAttrs() {
		return new String[][]{
				new String[]{"data-name",name},
				new String[]{"data-groups",groups}
		};
	}

	@Override
	public String uiStartHtml() {
		StringBuffer htmls = new StringBuffer();
		htmls.append(super.uiStartHtml());
		//<a data-command="menuCommand" href="#"><span class="glyphicon fontawesome-add"></span>新增</a>
		htmls.append("<a data-command=\"menuCommand\" href=\"#\">");
		if(StringUtils.isNotEmpty(icon)){
			htmls.append("<span class=\"youi-icon icon-"+icon+"\"></span>");
		}
		
		//
		String text = this.getCaption()!=null?this.getCaption():"";
		
		text = text.replaceAll("\\{[0-9]*\\}", "<span class=\"value\"></span>");
		
		htmls.append(text+"</a>");
		htmls.append("<ul>");
		return htmls.toString();
	}

	@Override
	public String uiEndHtml() {
		StringBuffer htmls = new StringBuffer("</ul>");
		htmls.append(super.uiEndHtml());
		return htmls.toString();
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#uiStyles()
	 */
	@Override
	protected String uiStyles() {
		return "youi-xmenuitem xmenu-item";
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#uiScripts()
	 */
	@Override
	protected String uiScripts() {
		return "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	@Override
	public void doFinally() {
		super.doFinally();
		this.action = null;
		this.name = null;
		this.icon = null;
		this.groups = null;
	}

}
