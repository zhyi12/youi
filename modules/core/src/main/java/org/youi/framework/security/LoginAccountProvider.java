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

import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.core.exception.BusException;
import org.youi.framework.core.web.menu.IMenu;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午08:04:25</p>
 */
public interface LoginAccountProvider<T extends LoginAccount> {

	/**
	 * @param username
	 * @return
	 */
	public T getAccount(String username);

	/**
	 * 获取当前用户类型的菜单全集
	 * @return
	 */
	public List<IMenu> getProviderMenus();
	
	/**
	 * 获取机构树
	 * @return
	 */
	public TreeNode getAgencyTree();
	
	/**
	 * 获取父节点查询机构
	 * @return
	 */
	public List<IAgency> getAgencyByParent(String parentAgencyId);

	/**
	 * 获取当前登录用户的菜单ID集合
	 * @param account
	 * @return
	 */
	public List<String> getAccountMenus(T account);
	
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
			String oldPassword);
	
	public boolean supports(Object object);
}
