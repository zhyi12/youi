package org.youi.framework.security;

import org.apache.shiro.authc.AuthenticationToken;

public interface IRealmUserToken extends AuthenticationToken{

	/**
	 * 用户名
	 * @return
	 */
	public String getUsername();
	
}
