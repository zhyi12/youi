/**
 * 
 */
package org.youi.framework.security;

import java.util.List;

import org.apache.shiro.authc.AuthenticationToken;
import org.youi.framework.core.web.menu.IMenu;

/**
 * @author zhyi_12
 *
 */
public interface IUserAdapter2<T extends IUser,K extends AuthenticationToken> extends IUserAdapter<T,K> {

	public List<IMenu> getProviderMenus(IUser user);
	
	@Deprecated
	public List<IMenu> getProviderMenus();
	
}
