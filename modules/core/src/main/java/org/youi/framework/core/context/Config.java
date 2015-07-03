/*

* @(#)Config.java  1.0.0 下午04:13:25

* Copyright 2013 gicom, Inc. All rights reserved.

*/
package org.youi.framework.core.context;

import java.util.Properties;
import java.util.Set;


/**
 * <p>平台公共通用参数配置类</p>
 * @author zhouyi
 * @version 1.0.0
 * @see    
 * @since 1.0.0
 */
public class Config {
	private static Config instance = null;
	
	public static final String CONIFG_PAGE_SIZE = "pager.pageSize";//分页
	
	public static final String CONIFG_LAYOUT_DEFAULT="layout.decorator";//默认的布局

	public static final String CONIFG_MENU_HTML_CLASS = "menu.html.class";//菜单html生成类
	
	public static final String URL_DATA_POSTFIX = ".json";//数据和操作URL后缀
	
	private Properties properties;//属性
	
	private Properties decorators;//布局
	
	private String defaultLayout ="default";//默认布局

	private Config(){
		
	}
	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}
	
	public static Config getInstance(){
		if(instance==null){
			instance = new Config();
		}
		return instance;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * @return the decorators
	 */
	public Properties getDecorators() {
		return decorators;
	}

	/**
	 * @param decorators the decorators to set
	 */
	public void setDecorators(Properties decorators) {
		this.decorators = decorators;
	}
	
	/**
	 * @return the defaultLayout
	 */
	public String getDefaultLayout() {
		return defaultLayout;
	}
	/**
	 * @param defaultLayout the defaultLayout to set
	 */
	public void setDefaultLayout(String defaultLayout) {
		this.defaultLayout = defaultLayout;
	}
	/**
	 * 获取配置属性值
	 * @param name
	 * @return
	 */
	public String getProperty(String name){
		if(properties==null)return null;
		return properties.getProperty(name);
	}
	/**
	 * 获取布局的HTML
	 * @return
	 */
	public String getDecoratorsHtml(){
		if(decorators ==null)return "";
		StringBuffer htmls = new StringBuffer();
		htmls.append("<div class=\"system-decorators\">");
		
		Set<Object> keys = decorators.keySet();
		for(Object key:keys){
			htmls.append("<div class=\"decorator-")
				.append(key)
				.append("\"")
				.append(" onclick=\"changeDecorator('").append(key).append("')\"")
				.append(">")
				.append(decorators.get(key))
				.append("</div>");
		}
		htmls.append("</div>");
		return htmls.toString();
	}

	static Config init() {
		instance = new Config();
		return instance;
	}
	public int getIntProperty(String name) {
		String value = properties.getProperty(name);
		return Integer.parseInt(value);
	}
}
