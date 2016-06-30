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
package org.youi.framework.ui;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.youi.framework.security.PageAuthFactory;
import org.youi.framework.util.StringUtils;



/**
 * <p>@系统描述:youi标签组件库</p>
 * <p>@功能描述:grid标签组件</p>
 * <p>@作者  zhouyi</p>
 * <p>@版本 1.0.0</p>
 * <p>@创建时间 下午06:03:36</p>
 */
public abstract class AbstractUiTag extends AbstractTag {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -388785925016324383L;

	protected String caption;
	
	protected static int UI_UUID = 0;
	
	protected String styleClass;//样式
	
	//TODO 支持百分比
	protected String width;//宽度 
	
	protected int height;//高度
	
	protected boolean script = true;
	
	protected String authCode;//授权代码
	
	protected IPageTag pageTag;
	
	protected String orgId;//ord id
	
	public int doStartTagInternal() throws JspException {
		if(!hasPermission(getAuthCode())){
			return SKIP_PAGE;
		}
		
		Tag findedPageTag = findAncestorWithClass(this, IPageTag.class);
		if(findedPageTag!=null){
			pageTag = (IPageTag)findedPageTag;
		}
		
		orgId = id;//cache org id
		//初始化动作
		id = createWidgetId();//
		
		this.tagWriter = createTagWriter();//创建tagWriter 
		initUi();
		//
		StringBuffer htmls = new StringBuffer();
		htmls.append(innerPrefixHtml());
		htmls.append(uiStartHtml());
		
		tagWriter.appendHtml(htmls.toString());
		return EVAL_BODY_INCLUDE;
	}
	/**
	 * 
	 * @return
	 */
	private String createWidgetId(){
		String widgetId = id;
		if(StringUtils.isEmpty(id)){
			widgetId = widgetUUID();
		}
		return wrapWithPageId(widgetId);
	}
	
	/**
	 * 判断授权码在当前环境中是否已经授权
	 * @param authCode
	 * @return
	 */
	protected boolean hasPermission(String authCode) {
		if(StringUtils.isNotEmpty(authCode)){
			PageAuthFactory pageAuthFactory = null;
			try {
				pageAuthFactory = this.getWebContext().getBean(
						this.getMessage(UiConstants.PAGE_AUTH_FACTORY_BEAN), PageAuthFactory.class);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			if(pageAuthFactory!=null){
				return pageAuthFactory.hasPermission(this.getPageId(), authCode);
			}
		}
		return true;
	}
	
	public int doEndTag() throws JspException {
		StringBuffer htmls = new StringBuffer();
		htmls.append(uiContentHtml());
		htmls.append(uiEndHtml());
		htmls.append(innerPostfixHtml());
		if(script){
			if(pageTag!=null){
				pageTag.addPageScript(uiScripts());
			}else{
				htmls.append(uiScripts());
			}
		}
		this.tagWriter.appendHtml(htmls.toString());
		id = null;
		return EVAL_PAGE;
	}
	/**
	 * ui组件起始html
	 * @return
	 */
	public String uiStartHtml(){
		StringBuffer htmls = new StringBuffer();
		htmls.append("<"+tagElementName()+" ");
		htmls.append(attrHtml("id", id));
		htmls.append(attrHtml("style", attrStyle()));
		htmls.append(attrHtml("class", attrClass()));
		htmls.append(uiAttrsHtml());
		htmls.append(">");
		return htmls.toString();
	}
	
	/**
	 * @return
	 */
	private String uiAttrsHtml(){
		StringBuffer htmls = new StringBuffer();
		String[][] uiAttrs = uiAttrs();
		if(uiAttrs!=null){
			for(String[] uiAttr:uiAttrs){
				if(uiAttr.length==2)htmls.append(attrHtml(uiAttr[0],uiAttr[1]));
			}
		}
		return htmls.toString();
	}
	
	/**
	 * @return
	 */
	protected String[][] uiAttrs(){
		return null;
	}
	/**
	 * html dom节点style属性的内容
	 * @return
	 */
	protected String attrStyle() {
		StringBuffer styleBuf = new StringBuffer();
		if(width!=null){
			styleBuf.append("width:"+width+(width.endsWith("%")?"":"px")+";");
		}
		if(height>0){
			styleBuf.append("height:"+height+"px;");
		}
		return styleBuf.toString();
	}
	/**
	 * html dom 节点的class样式
	 * @return
	 */
	private String attrClass() {
		String attrClass = uiStyles();
		if(styleClass!=null){
			attrClass+=(" "+styleClass);
		}
		return attrClass;
	}

	/**
	 * UI init
	 */
	public abstract void initUi();
	
	/**
	 * ui组件内容html
	 * @return
	 */
	public abstract String uiContentHtml();
	
	/**
	 * 组件html内容前缀，使用panel样式时需要用到
	 * @return
	 */
	protected String innerPrefixHtml() {
		return "";
	}
	/**
	 * 组件html内容后缀
	 * @return
	 */
	protected String innerPostfixHtml() {
		return "";
	}

	/**
	 * ui组件结束html
	 * @return
	 */
	public String uiEndHtml(){
		return "</"+tagElementName()+">";
	}
	/**
	 * ui的样式
	 * @return
	 */
	protected abstract String uiStyles();
	/**
	 * ui的script
	 * @return
	 */
	protected abstract String uiScripts();
	
	protected String wrapScripts(String script){
		StringBuffer scripts = new StringBuffer();
		 
		if(pageTag!=null){
			scripts.append(script);
		}else{
			scripts.append("<script>");
			scripts.append("	$(function(){");
			scripts.append(			script);
			scripts.append("	});");
			scripts.append("</script>");
		}
		
		return scripts.toString();
	}

	protected String widgetUUID() {
		return "gen_"+UI_UUID++;
	}


	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * @return the width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * 组件名称
	 * @return
	 */
	protected String widgetName(){
		String widgetName = this.getClass().getSimpleName();
		widgetName = widgetName.substring(0,1).toLowerCase()+widgetName.substring(1).replace("Tag", "");
		return widgetName;
	}
	
	public boolean isScript() {
		return script;
	}

	public void setScript(boolean script) {
		this.script = script;
	}

	/**
	 * 标签的dom元素名
	 * @return
	 */
	protected String tagElementName(){
		return "div";
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public void doFinally(){
		super.doFinally();
		this.id = null;
		this.caption = null;
		this.authCode = null;
		this.height = 0;
	}
	
}
