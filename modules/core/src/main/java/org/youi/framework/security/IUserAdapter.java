package org.youi.framework.security;

import java.util.List;

import org.apache.shiro.authc.AuthenticationToken;

import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.core.web.menu.IMenu;

public interface IUserAdapter<T extends IUser,K extends AuthenticationToken> {

	public boolean supports(IRealmUserToken token);

	/**
	 * 获取登录信息
	 * @param token 
	 * @return
	 */
	public IRealmUserInfo getRealmUserInfo(K token);

	/**
	 * 获取登录信息
	 * @param user
	 * @return
	 */
	public IRealmUserInfo getRealmUserInfo(T user);

	/**
	 * 获取全部菜单
	 * @return
	 */
	public List<IMenu> getProviderMenus();
	
	/**
	 * @param user
	 * @return
	 */
	List<String> getAccountMenus(T user);
	
	/**
	 * 获取机构树
	 * @return
	 */
	public TreeNode getAgencyTree();
//	
	/**
	 * 分层获取机构 
	 * @param parentAgencyId
	 * @return
	 */
	public List<IAgency> getAgencyByParent(String parentAgencyId);

	public boolean supports(IUser user);

}
