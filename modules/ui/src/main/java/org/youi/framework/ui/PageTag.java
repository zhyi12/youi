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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.youi.framework.core.convert.IConvert;
import org.youi.framework.core.web.PageScriptFactory;
import org.youi.framework.ui.convert.ConvertProviderFactory;
import org.youi.framework.ui.convert.IConvertContainer;
import org.youi.framework.util.StringUtils;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午08:40:33</p>
 */
public class PageTag extends I18nContainerTag implements IConvertContainer,IPageTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5351981602718196055L;
	
	
	private String pageId;//页面唯一编码
	
	private String tranCode;//交易编码
	
	private String caption;//页面描述
	
	private String dataSrc;//页面初始化数据
	
	private boolean autoLoadData = true;//是否自动加载数据
	
	private String styleClass;//页面样式
	
	private Boolean savable;//是否为可保存的页面
	
	
	
	List<IConvert<?>> pageIConverts ;
	
	List<String> staticScriptSrcs = new ArrayList<String>();//静态脚本
	
	List<String> pageScripts = new ArrayList<String>();//页面脚本
	
	private ConvertProviderFactory	providerFactory ;
	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractTag#doStartTagInternal()
	 */
	@Override
	protected int doStartTagInternal() throws JspException {
		this.initI18n();
		pageIConverts = new ArrayList<IConvert<?>>();
		pageScripts = new ArrayList<String>();
		staticScriptSrcs = new ArrayList<String>();
		
		super.doStartTagInternal();
		
		Object paramPageId = this.pageContext.getRequest().getParameter("page:pageId");
		if(paramPageId!=null){
			this.pageId = paramPageId.toString();
		}
		
		if(StringUtils.isEmpty(this.pageId)){
			this.pageId = System.currentTimeMillis()+"1"+new Double(Math.random()*1000).intValue();
		}
		
		this.tagWriter.appendHtml("<div data-caption=\""+StringUtils.null2Empty(caption)+"\" id=\"P_"+pageId+"\" class=\"youi-page"+(StringUtils.isNotEmpty(styleClass)?" "+styleClass:"")+"\">");
		//
		this.tagWriter.appendHtml("<div class=\"sys-page-navigator\"></div>");
		
		return EVAL_BODY_INCLUDE;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		
		HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
		String sessionId = request.getSession().getId();
		this.tagWriter.appendHtml("</div>");
		
		PageScriptFactory pageScriptFactory = null;
		
		try {
			pageScriptFactory =	getWebContext().getBean("pageScriptFactory", PageScriptFactory.class);
		} catch (BeansException e) {
			logger.warn(e.getMessage());
		}
		
		if(StringUtils.isNotEmpty(pageId)){
			if(pageScriptFactory!=null){
				String pageScript = pageScriptFactory.getPageScript(sessionId, pageId);
				
				//if(StringUtils.isEmpty(pageScript)){//如果缓存中不存在，则生成并添加到缓存中
					pageScript = buildPageScript();
					pageScriptFactory.addPageScript(sessionId,pageId, pageScript);
				//}
				this.tagWriter.appendHtml("<script>");
				this.tagWriter.appendHtml(pageScript);
				this.tagWriter.appendHtml("</script>");
			}
		}
		
		return super.doEndTag();
	}
	
	private String buildPageScript() {
		StringBuffer scriptBuf = new StringBuffer();
		
		scriptBuf.append("$(function(){");
		
		//静态脚本加载
		//staticScriptSrcs
		if(pageIConverts.size()>0){
			for(IConvert<?> pageIConvert:pageIConverts){
				if(StringUtils.isNotEmpty(pageIConvert.getName())){
					scriptBuf.append("$.extend($.youi.serverConfig.convertArray,{"+pageIConvert.getName()+":")
					.append(pageIConvert.toJson())
					.append("});");
				}
			}
		}
		
		for(String pageScript:pageScripts){
			scriptBuf.append(pageScript);
			scriptBuf.append("\n");
		}
		
		scriptBuf.append("$('#P_"+this.pageId+"').page({")
			.append(this.sAttrJson("pageId", this.pageId));
		
		if(autoLoadData){
			scriptBuf.append(",").append(this.sAttrJson("dataSrc", this.getActionPath(this.dataSrc)));
		}
		
		if(this.savable!=null){
			scriptBuf.append(",").append(this.sAttrJson("savable",this.savable.toString()));
		}
		scriptBuf.append(",staticScripts:[")
			.append(StringUtils.arrayToDelimitedString(
					staticScriptSrcs.toArray(new String[staticScriptSrcs.size()]), ","))
			.append("]});\n");
	
		scriptBuf.append("});");
		return scriptBuf.toString();
	}

	public void addPageConvert(String convertName){
		WebApplicationContext webContext = getWebContext();
		if(webContext!=null&&providerFactory==null){
			providerFactory  = webContext.getBean(ConvertProviderFactory.class);
			providerFactory.setResourceLoader(webContext);
		}
		
		IConvert<?> IConvert = providerFactory.getConvert(convertName ,getLocale());
		if(IConvert!=null&&!pageIConverts.contains(IConvert)){
			pageIConverts.add(IConvert);
		}
	}
	
	/**
	 * @param script
	 */
	public void addPageScript(String script){
		if(StringUtils.isNotEmpty(script)){
			pageScripts.add(script);
		}
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public Boolean getSavable() {
		return savable;
	}

	public void setSavable(Boolean savable) {
		this.savable = savable;
	}

	/**
	 * @param scriptSrc
	 */
	public void addStaticScript(String scriptSrc) {
		if(StringUtils.isNotEmpty(scriptSrc)){
			staticScriptSrcs.add("'"+scriptSrc+"'");
		}
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getDataSrc() {
		return dataSrc;
	}

	public void setDataSrc(String dataSrc) {
		this.dataSrc = dataSrc;
	}
	
	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public boolean getAutoLoadData() {
		return autoLoadData;
	}

	public void setAutoLoadData(boolean autoLoadData) {
		this.autoLoadData = autoLoadData;
	}

	@Override
	public void doFinally() {
		super.doFinally();
		this.dataSrc = null;
		this.caption = null;
		this.styleClass = null;
		this.autoLoadData = true;
		
		this.pageId = null;
	}
	
}
