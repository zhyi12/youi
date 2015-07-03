/**
 * 
 */
package org.youi.framework.security.dev;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.youi.framework.security.AbstractFormUserAdapter;
import org.youi.framework.security.IAgency;
import org.youi.framework.security.IRealmUserInfo;
import org.youi.framework.security.IRealmUserToken;
import org.youi.framework.security.IUser;

import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.core.web.menu.IMenu;
import org.youi.framework.core.web.menu.XmlMenuLoader;
import org.youi.framework.util.PasswordUtils;
import org.youi.framework.util.StringUtils;

/**
 * @author zhyi_12
 *
 */
public class DevUserAdapter extends AbstractFormUserAdapter<DevUser> implements ApplicationContextAware{
	private List<IMenu> providerMenus;
	
	private ApplicationContext applicationContext;
	
	private String menuResource = "WEB-INF/configs/menu.xml";//菜单资源文件
	/* 
	 * 
	 * 验证表单用户
	 * @see com.gsoft.framework.security.IUserAdapter#supports(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	public boolean supports(IRealmUserToken token) {
		return  super.supports(token)&&token.getUsername().startsWith("demo-");
	}

	@Override
	public IRealmUserInfo getRealmUserInfo(IRealmUserToken token) {
		DevUser user = new DevUser();
		user.setLoginName(token.getUsername());
		
		DevUserInfo  realmUserInfo = new DevUserInfo(user,PasswordUtils.md5Password("123456"));
		
		return realmUserInfo;
	}

//	@Override
	public IRealmUserInfo getRealmUserInfo(DevUser user) {
		DevUserInfo  realmUserInfo = new DevUserInfo(user,PasswordUtils.md5Password("123456"));
		
		return realmUserInfo;
	}

	@Override
	public List<IMenu> getProviderMenus() {
		XmlMenuLoader xmlMenuLoader = new XmlMenuLoader(applicationContext);
		return xmlMenuLoader.getMenus(menuResource);
	}

	@Override
	public List<String> getAccountMenus(DevUser account) {
		List<String> accountMenus = new ArrayList<String>();
		if(providerMenus==null){
			providerMenus = this.getProviderMenus();
		}
		
		String username = account.getLoginName();
		String filterName = null;
		if(username.startsWith("demo-")){
			filterName = username.substring("demo-".length());
		}
		
		for(IMenu menu:providerMenus){
			if(StringUtils.isNotEmpty(filterName)&&StringUtils.isNotEmpty(menu.getTarget())
					&&(" "+menu.getTarget()).indexOf(" "+filterName)==-1){
				continue;
			}
			accountMenus.add(menu.getMenuId());
		}
		return accountMenus;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public TreeNode getAgencyTree() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IAgency> getAgencyByParent(String parentAgencyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supports(IUser user) {
		return DevUser.class.isAssignableFrom(user.getClass());
	}

}
