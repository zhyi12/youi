package org.youi.framework.security;

import java.util.List;

public interface IUser extends AccountPrincipal{

	/**
	 * 获取角色集合
	 * @return
	 */
	public List<String> roleIds();
	
	/**
	 * 获取登录名
	 * @return
	 */
	public String getLoginName();
	
	public String getPassword();

}
