/**
 * 
 */
package org.youi.framework.security;

/**
 * @author zhyi_12
 *
 */
public abstract class AbstractFormUserAdapter<T extends IUser> implements IUserAdapter<T,IRealmUserToken> {

	@Override
	public boolean supports(IRealmUserToken token) {
		return DefaultLoginFormToken.class.isAssignableFrom(token.getClass());
	}
	
}
