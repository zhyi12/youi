package org.youi.framework.security;

import java.util.List;

import org.youi.framework.core.dataobj.tree.HtmlTreeNode;
import org.youi.framework.core.dataobj.tree.TreeNode;


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
	
	
	/**
	 * 修改密码
	 * @param account
	 * @param password
	 * @param confirmPassword
	 * @param oldPassword
	 * @return
	 */
	public boolean modifyPassword(
			IUser account,
			String password,
			String confirmPassword,
			String oldPassword);
	
	/**
	 * 重置密码
	 * @param account
	 * @return
	 */
	public boolean resetPassword(IUser account);
	
	/**
	 * 获取机构树
	 * @param user
	 * @return
	 */
	public TreeNode getAgencyTree(IUser user);
	
	/**
	 * 分级获取机构树
	 * @param user
	 * @param parentAgencyId
	 * @return
	 */
	public List<IAgency> getAgencyByParent(IUser user,String parentAgencyId);
}
