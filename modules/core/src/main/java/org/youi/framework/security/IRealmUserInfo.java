/**
 * 
 */
package org.youi.framework.security;

import org.apache.shiro.authc.Account;
import org.apache.shiro.authc.MergableAuthenticationInfo;
import org.apache.shiro.authc.SaltedAuthenticationInfo;

/**
 * @author zhyi_12
 *
 */
public interface IRealmUserInfo extends Account,MergableAuthenticationInfo,SaltedAuthenticationInfo{

	public IUser getUser();

}
