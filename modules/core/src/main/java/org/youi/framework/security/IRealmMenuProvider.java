package org.youi.framework.security;

import org.youi.framework.core.web.menu.MenuProvider;


public interface IRealmMenuProvider extends MenuProvider{

	@SuppressWarnings("rawtypes")
	public void loadMenu(IRealmUserInfo realmUserInfo, IUserAdapter provider);

}
