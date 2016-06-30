package org.youi.framework.security;

import java.util.List;

import org.apache.shiro.authc.AuthenticationToken;
import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.core.web.menu.IMenu;


public interface IUserAdapter<T extends IUser,K extends AuthenticationToken> {

	/**
	 * 根据登录的token信息判断是否使用该用户处理类
	 * @param token
	 * @return
	 */
	public boolean supports(IRealmUserToken token);

	/**
	 * 用于登录后doGetAuthorizationInfo中获取AuthorizationInfo的判断
	 * @param user
	 * @return
	 */
	public boolean supports(IUser user);

	/**
	 * 获取登录信息
	 * @param token 
	 * @return
	 */
	public IRealmUserInfo getRealmUserInfo(K token);

	/**
	 * 根据登录的principal对象获取登录信息，已经登录的情况下使用。
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
	 * 获取用户菜单
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

}
