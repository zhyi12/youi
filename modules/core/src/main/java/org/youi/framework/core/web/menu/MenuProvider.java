/*

* @(#)MenuProvider.java  1.0.0 下午11:15:07

* Copyright 2013 gicom, Inc. All rights reserved.

*/
package org.youi.framework.core.web.menu;

import javax.servlet.jsp.PageContext;

import org.springframework.core.io.ResourceLoader;

/**
 * <p></p>
 * @author 
 * @version 1.0.0
 * @see    
 * @since 
 */
public interface MenuProvider {
	public static final String MENU_TYPE_V = "v";//横向菜单
	
	public static final String MENU_TYPE_H = "h";//纵向菜单
	/**
	 * 加载系统菜单
	 */
	void loadSystemMenu(ResourceLoader resourceLoader);

	/**
	 * 生成系统菜单的html
	 * @param pageContext
	 * @param menuType 菜单类型：横向和纵向
	 * @return
	 */
	String buildMenuHtml(PageContext pageContext,String menuType);

}
