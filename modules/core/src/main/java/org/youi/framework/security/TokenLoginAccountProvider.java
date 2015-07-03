/**
 * 
 */
package org.youi.framework.security;

/**
 * @author zhyi_12
 *
 */
public interface TokenLoginAccountProvider<T extends LoginAccount> extends LoginAccountProvider<T> {
	/**
	 * @param username
	 * @return
	 */
	public T getAccountByToken(DefaultLoginFormToken token);
}
