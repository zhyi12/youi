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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import org.youi.framework.core.dataobj.tree.HtmlTreeNode;
import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.core.exception.BusException;
import org.youi.framework.core.web.menu.IMenu;
import org.youi.framework.core.web.menu.XmlMenuLoader;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午08:11:02</p>
 */
public class DevAccountProvider implements LoginAccountProvider<DevLoginAccount>,ApplicationContextAware {

	private Map<String,String> users;//用户集合
	
	private List<IMenu> providerMenus;
	
	private ApplicationContext applicationContext;
	
	private String menuResource = "WEB-INF/configs/menu.xml";//菜单资源文件
	
	/**
	 * @param menuResource the menuResource to set
	 */
	public void setMenuResource(String menuResource) {
		this.menuResource = menuResource;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(Map<String, String> users) {
		this.users = users;
	}

	@Override
	public DevLoginAccount getAccount(String username) {
		if(users==null){
			return new DevLoginAccount("module","123456");
		}
		
		if(users.containsKey(username)){
			return new DevLoginAccount(username,users.get(username));
		}
		
		return null;
	}

	@Override
	public List<IMenu> getProviderMenus() {
		//if(providerMenus==null){
			XmlMenuLoader xmlMenuLoader = new XmlMenuLoader(applicationContext);
			providerMenus = xmlMenuLoader.getMenus(menuResource);
		//}
		return providerMenus;
	}

	@Override
	public List<String> getAccountMenus(DevLoginAccount account) {
		List<String> accountMenus = new ArrayList<String>();
		if(providerMenus!=null){
			for(IMenu menu:providerMenus){
				accountMenus.add(menu.getMenuId());
			}
		}
		return accountMenus;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	/**
	 * 修改用户密码
	 * @param id
	 * @param password
	 * @param confirmPassword
	 * @param oldPassword
	 * @throws BusException
	 */
	public void modifyPassword(
			String username,
			String password,
			String confirmPassword,
			String oldPassword){
		throw new BusException("开发用户密码不可修改！");
	}

	@Override
	public boolean supports(Object object) {
		return true;
	}

	@Override
	public TreeNode getAgencyTree() {
		TreeNode tree = new HtmlTreeNode("ROOT_DEV_AGENTY","开发用户");
		return tree;
	}

	@Override
	public List<IAgency> getAgencyByParent(String parentAgencyId) {
		return null;
	}
}
