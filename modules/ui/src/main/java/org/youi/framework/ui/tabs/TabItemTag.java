/*
 * YOUI框架
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.youi.framework.ui.tabs;

import javax.servlet.jsp.tagext.Tag;

import org.youi.framework.ui.AbstractUiTag;


/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午12:00:00</p>
 */
public class TabItemTag extends AbstractUiTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3213560742117012954L;
	
	private String url;

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#initUi()
	 */
	@Override
	public void initUi() {
		Tag tabsTag = findAncestorWithClass(this, TabsTag.class);
		if(tabsTag!=null){
			((TabsTag)tabsTag).addTabItem(createTabItem());
//			this.height = ((TabsTag)tabsTag).getItemHeight();
		}
//		if(this.height==0)this.height = 240;
		this.id = id+"_panel";
	}

	private TabItem createTabItem(){
		TabItem tabItem = new TabItem();
		tabItem.setId(id);
		tabItem.setOrgId(orgId);
		tabItem.setCaption(caption);
		tabItem.setUrl(this.getUrlPath(url));
		return tabItem;
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
		return new String[][]{new String[]{"data-id",orgId}};
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#uiStyles()
	 */
	@Override
	protected String uiStyles() {
		return "tab-pane";
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#uiScripts()
	 */
	@Override
	protected String uiScripts() {
		return "";
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public void doFinally() {
		super.doFinally();
		this.url = null;
	}

}
