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

import java.util.ArrayList;
import java.util.List;

import org.youi.framework.ui.AbstractUiTag;
import org.youi.framework.ui.util.JsUtils;
import org.youi.framework.util.StringUtils;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 上午11:58:38</p>
 */
public class TabsTag extends AbstractUiTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6935262838400319295L;
	
	private int itemHeight;
	
	private String itemSrc;//tabItem加载时调用的页面地址
	
	private boolean pageHash;//
	
	private List<TabItem> tabItems;

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#initUi()
	 */
	@Override
	public void initUi() {
		tabItems = new ArrayList<TabItem>();
//		if(itemHeight==0)itemHeight = 300;
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#uiContentHtml()
	 */
	@Override
	public String uiContentHtml() {
		StringBuffer htmls = new StringBuffer();
		htmls.append("</div><ul id=\""+id+"\" class=\"nav nav-tabs "+StringUtils.null2Empty(this.styleClass)+"\""+(pageHash?" data-hash":"")+" role=\"tablist\">");
		for(TabItem tabItem:tabItems){
			
			htmls.append("<li "+(StringUtils.isNotEmpty(tabItem.getUrl())?"data-src=\""+tabItem.getUrl()+"\"":"")+"  id=\""+tabItem.getId()+"\" class=\"tabs-item\">");
			htmls.append("<a data-id=\""+tabItem.getOrgId()+"\"  class=\"tabs-item-text\" data-toggle=\"tab\" href=\""+buildHref(tabItem)+"\">"+tabItem.getCaption()+"</a>");
			htmls.append("</li>");
		}
		htmls.append("</ul>");
		//<a href="#tab_020100_panel" data-toggle="tab">服务综合管理<span class="youi-icon tab-close glyphicon glyphicon-remove"></span></a>
		return htmls.toString();
	}

	@Override
	public String uiStartHtml() {
		return "<div class=\"tab-content\">";
	}
	
	@Override
	public String uiEndHtml() {
		return "";
	}
	
	/**
	 * @param tabItem
	 * @return
	 */
	private String buildHref(TabItem tabItem) {
		return "#"+tabItem.getId()+"_panel";
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#uiStyles()
	 */
	@Override
	protected String uiStyles() {
		return "tab-content";
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#uiScripts()
	 */
	@Override
	protected String uiScripts() {
		StringBuffer scripts = new StringBuffer();
		scripts.append("$('#"+id+"').tabs({");
		scripts.append(JsUtils.propertyValue("itemHeight", itemHeight, JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("itemSrc", getActionPath(itemSrc), JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("pageHash", pageHash, JsUtils.JSON_PROP_INT));
		
		scripts.append(	"	initHtml:false");
		scripts.append(	"});");
		return this.wrapScripts(scripts.toString());
	}

	/**
	 * @return the itemHeight
	 */
	public int getItemHeight() {
		return itemHeight;
	}

	/**
	 * @param itemHeight the itemHeight to set
	 */
	public void setItemHeight(int itemHeight) {
		this.itemHeight = itemHeight;
	}

	/**
	 * @param tabItem
	 */
	public void addTabItem(TabItem tabItem) {
		tabItems.add(tabItem);
	}
	
	/**
	 * @return the itemSrc
	 */
	public String getItemSrc() {
		return itemSrc;
	}

	/**
	 * @param itemSrc the itemSrc to set
	 */
	public void setItemSrc(String itemSrc) {
		this.itemSrc = itemSrc;
	}

	public boolean getPageHash() {
		return pageHash;
	}

	public void setPageHash(boolean pageHash) {
		this.pageHash = pageHash;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.tags.RequestContextAwareTag#doFinally()
	 */
	@Override
	public void release() {
		super.release();
		tabItems = new ArrayList<TabItem>();
		this.pageHash = false;
	}
	
}
