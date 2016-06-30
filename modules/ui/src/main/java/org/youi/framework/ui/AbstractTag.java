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

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.tags.HtmlEscapingAwareTag;
import org.youi.framework.ui.convert.IConvertContainer;
import org.youi.framework.ui.tag.UiTagWriter;
import org.youi.framework.ui.util.HtmlUtils;
import org.youi.framework.util.RequestUtils;
import org.youi.framework.util.StringUtils;


/**
 * <p>@系统描述:</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午04:12:16</p>
 */
public class AbstractTag extends HtmlEscapingAwareTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7958850348314371573L;
	/** 
	 * 
	 */
	protected final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * i18n前缀
	 */
	private static final String I18N_PREFIX = "i18n.";
	/**
	 * 数组类型属性的切割符号
	 */
	private static final String TAG_ARRAY_SPLIT = ",";
	
	
	protected String i18nArg = null;//i18n参数
	
	protected String[] i18nArgs;
	
	protected UiTagWriter tagWriter;
	
	protected String actionPrefix = "";
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.tags.RequestContextAwareTag#doStartTagInternal()
	 */
	@Override
	protected int doStartTagInternal() throws JspException {
		if(StringUtils.isNotEmpty(i18nArg)){//i18n变量参数处理
			i18nArgs = i18nArg.split(TAG_ARRAY_SPLIT);
		}
		this.tagWriter = this.createTagWriter();
		return EVAL_BODY_INCLUDE;
	}
	
	
	/**
	 * 创建writer
	 * @return
	 */
	protected UiTagWriter createTagWriter() {
		return new UiTagWriter(this.pageContext);
	}
	/**
	 * 获取i18n配置信息
	 * @param key
	 * @return
	 */
	public String getI18nMessage(String key){
		if(key!=null&&key.startsWith(I18N_PREFIX)){//约定使用固定的前缀使用i18n
			//格式 i18n.user.username -> user.username
			String i18nKey = key.substring(I18N_PREFIX.length());
			//在当前页面的国际化配置文件中查找
			String value = this.getPageI18nMessage(i18nKey, i18nArgs);
			if(StringUtils.isEmpty(value)){
				//从全局的国际化配置文件中查找
				try {
					value = this.getRequestContext().getMessage(i18nKey, this.i18nArgs);
				} catch (NoSuchMessageException e) {
					value = i18nKey;
				}
			}
			if(logger.isDebugEnabled()){
				//youi.tag.parseI18nMsg=组件【{0},{1}】，国际化解析:{2}={3}
				logger.debug(this.getMessage("youi.tag.parseI18nMsg",
						new String[]{this.getClass().getName(),id,i18nKey,value}));
			}
			//输出从i18配置中找到的对应值
			if(StringUtils.isNotEmpty(value)){
				return value;
			}else{//从当前页面环境中查找
				
			}
		}
		//默认直接返回输入值
		return key;
	}
	
	/**
	 * @param key
	 * @param args
	 * @return
	 */
	String getPageI18nMessage(String key,String[] args){
		I18nContainerTag htmlTag = getAncestorHtmlTag();
		if(htmlTag!=null){
			return htmlTag.getPageI18nMessage(key,args);
		}
		return null;
	}
	
	/**
	 * 
	 * @param convertName
	 */
	public void addPageConvert(String convertName){
		Tag tag = findAncestorWithClass(this,IConvertContainer.class);
		if(tag!=null){
			((IConvertContainer)tag).addPageConvert(convertName);
		}
	}
	
	/**
	 * 获取htmlTag
	 * @return
	 */
	protected  I18nContainerTag getAncestorHtmlTag(){
		Tag i18nContainerTag = findAncestorWithClass(this, I18nContainerTag.class);
		if(i18nContainerTag!=null&&i18nContainerTag instanceof I18nContainerTag){
			return (I18nContainerTag)i18nContainerTag;
		}
		return null;
	}
	/**
	 * 处理可国际化的属性
	 * @param attrName
	 * @param attrValue
	 * @param useDefault 是否启用默认值
	 * @return
	 */
	protected String dealI18nAttr(String attrName,String attrValue,boolean useDefault){
		String value;
		//如果允许使用默认值，并且实际值为空，根据默认值规则取值
		if(useDefault&&StringUtils.isEmpty(attrValue)){
			value = this.getMessage("youi.tag."+attrName);
		}else{
			//i18n模式取值
			value = this.getI18nMessage(attrValue);
		}
		return value;
	}


	public String getMessage(String key,String[] args){
		return this.getRequestContext().getMessage(key, args);
	}
	
	public String getMessage(String key){
		return this.getRequestContext().getMessage(key);
	}
	
	public int getIntMessage(String key){
		String value = this.getMessage(key);
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	/**
	 * 返回html属性的键值对文本
	 * @param attrName
	 * @param attrValue
	 * @return
	 */
	protected String attrHtml(String attrName,String attrValue){
		return HtmlUtils.attrHtml(attrName, attrValue);
	}
	/**
	 * 返回字符类型json属性键值对文本
	 * @param attrName
	 * @param attrValue
	 * @param type
	 * @return
	 */
	protected String sAttrJson(String attrName,Object attrValue){
		return HtmlUtils.attrJson(attrName, attrValue, HtmlUtils.HTML_ATTR_STR);
	}
	/**
	 * 返回数字类型json属性键值对文本
	 * @param attrName
	 * @param attrValue
	 * @param type
	 * @return
	 */
	protected String iAttrJson(String attrName,Object attrValue){
		return HtmlUtils.attrJson(attrName, attrValue, HtmlUtils.HTML_ATTR_INT);
	}
	
	/**
	 * 返回项目当前上下文路径
	 * @return
	 */
	protected String getContextPath(){
		HttpServletRequest  request  = (HttpServletRequest) pageContext.getRequest();
		return request.getContextPath();
	}

	protected String getPageId(){
		IPageTag pageTag = getAncestorPageTag();
		if(pageTag!=null){
			return pageTag.getPageId();
		}
		return null;
	}
	/**
	 * 组装ID
	 * @param orgId
	 * @return
	 */
	protected String wrapWithPageId(String orgId){
		String widgetId = orgId;
		String pageId = getPageId();
		if(!StringUtils.isEmpty(pageId)){
			widgetId = "P_"+pageId+"_"+widgetId;
		}
		return widgetId;
	}
	
	/**
	 * 获取page标签组件
	 * @return
	 */
	protected IPageTag getAncestorPageTag(){
		Tag pageTag = findAncestorWithClass(this, IPageTag.class);
		if(pageTag!=null&&pageTag instanceof IPageTag){
			return (IPageTag)pageTag;
		}
		return null;
	}
	/**
	 * 国际化
	 * @return
	 */
	protected Locale getLocale(){
		return RequestUtils.getLocale((HttpServletRequest)pageContext.getRequest());
	}
	
	public String getI18nArg() {
		return i18nArg;
	}

	public void setI18nArg(String i18nArg) {
		this.i18nArg = i18nArg;
	}

	/**
	 * @param url
	 * @return
	 */
	protected String getActionPath(String url){
		if(url==null)return "";
		if(url.startsWith("/")){
			
			if(url.startsWith("/local/")){
				return getUrlPath(url.substring("/local".length()));
			}else if(url.startsWith("/esb/")){
				return getUrlPath(url);
			}
			
			String actionPrefixPath = getContextPath();
			
			if(StringUtils.isEmpty(this.actionPrefix)){
				this.actionPrefix = this.getMessage(UiConstants.MENU_ACTION_PREFIX);
			}
			
			if(StringUtils.isNotEmpty(this.actionPrefix)){
				actionPrefixPath += this.actionPrefix;
			}
			return  actionPrefixPath+url;
		}else{
			
//			try {
//				TagUrlTransformer transformer = this.getRequestContext().getWebApplicationContext().getBean(TagUrlTransformer.class);
//				
//				return this.getUrlPath(transformer.transform(url));
//			} catch (BeansException e) {
//				// 
//			}
		}
		//TODO code mapping
		return url;
	}
	
	protected String getUrlPath(String url){
		if(url==null)return "";
		if(url.startsWith("/")){
			return  getContextPath()+url;
		}
		return url;
	}
	
	/**
	 * @return
	 */
	protected WebApplicationContext getWebContext(){
		return this.getRequestContext().getWebApplicationContext();
	}
	

	public void doFinally(){
		super.doFinally();
	}
}
