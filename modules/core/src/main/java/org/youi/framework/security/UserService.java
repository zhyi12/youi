package org.youi.framework.security;

import org.youi.framework.core.dataobj.tree.HtmlTreeNode;

public interface UserService {

//	/**
//	 * 根据用户名查找用户
//	 * @param username
//	 * @return
//	 */
//	public IUser getUser(String username);
	
	/**
	 * @param user
	 * @return
	 */
	public IRealmUserInfo getRealmUserInfo(IUser user);
	
	/**
	 * 根据登录表单提交的信息获取shiro登录用户信息
	 * @param token
	 * @return
	 */
	public IRealmUserInfo getRealmUserInfo(IRealmUserToken token,boolean loadMenus);
	
	/**
	 * 根据用户信息查询菜单
	 * @param userInfo
	 * @return
	 */
	public HtmlTreeNode getCachedMenuTree(IUser user);
	
	/**
	 * @param name
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public IUserAdapter getUserAdapter(String beanName);
}
