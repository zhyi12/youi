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
package org.youi.framework.security;

import java.util.List;

import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import org.youi.framework.core.dataobj.tree.HtmlTreeNode;
import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.core.web.menu.IMenu;
import org.youi.framework.core.web.menu.MenuProvider;
import org.youi.framework.util.SecurityUtils;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 上午09:37:21</p>
 */
public class SecurityMenuProvider implements MenuProvider {
	
	private static final Log logger = LogFactory.getLog(SecurityMenuProvider.class);//日记
	
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	@Deprecated
	public void loadSystemMenu(ResourceLoader resourceLoader) {
		//全局的与用户无关的菜单加载
		logger.info("load sys menu.");
	}

	@Override
	public String buildMenuHtml(PageContext pageContext, String menuType) {
		AccountPrincipal account = SecurityUtils.getAccount();
		StringBuffer htmls = new StringBuffer();
		if(account!=null&&account instanceof IUser){
			HtmlTreeNode menuTree = userService.getCachedMenuTree((IUser)account);
			if(menuTree!=null){
				List<TreeNode> children = menuTree.getChildren();
				for(TreeNode child:children){
					htmls.append(buildMenuBarAndMenu(child));
				} 
			}
		}
		return htmls.toString();
	}
	/**
	 * 生成menuBar和menu的html
	 * @param child
	 * @return
	 */
	private String buildMenuBarAndMenu(TreeNode child) {
		StringBuffer htmls = new StringBuffer();
		
		if(child!=null){//如果节点不为空
			//先创建菜单头
			//<h1 class="type"><a href="javascript:void(0)">网站常规管理</a></h1>
			htmls.append("<h1 id=\""+child.getId()+"\" class=\"")
				 .append(buildStyle(child,"menu-bar-title"))
				 .append("\"><a href=\"javascript:void(0)\"><div class=\"icon-text\"><span class=\"youi-icon menubar left\"></span><span class=\"span-text\">")
				 .append(child.getText())
				 .append("</span></div></a></h1>");
			List<TreeNode> menuItems = child.getChildren();
			//有子菜单时，创建菜单项树
			if(menuItems.size()>0){
				htmls.append("<div class=\"menu-bar-content\"><ul class=\"menu-content-ul\">");
				for(TreeNode menuItem:menuItems){
					menuItem.setGroup(buildStyle(menuItem,"menu-item"));
					htmls.append(menuItem.toString());
				}
				htmls.append("</ul></div>");
			}
		}
		return htmls.toString();
	}
//	
	private String buildStyle(TreeNode treeNode,String fiexdStyle){
		StringBuffer styleBuf = new StringBuffer(fiexdStyle);
		if(treeNode.getDomain() instanceof IMenu){
			IMenu menu = (IMenu) treeNode.getDomain();
			if(!StringUtils.isEmpty((menu.getMenuStyle()))){
				styleBuf.append(" "+menu.getMenuStyle());
			}
		}
		return styleBuf.toString();
	}
}
