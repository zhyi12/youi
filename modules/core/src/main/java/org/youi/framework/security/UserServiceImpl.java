package org.youi.framework.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.util.StringUtils;
import org.youi.framework.core.dataobj.tree.HtmlTreeNode;
import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.core.dataobj.tree.TreeUtils;
import org.youi.framework.core.web.menu.IMenu;


public class UserServiceImpl implements UserService,ApplicationContextAware{
	
	private static final Log logger = LogFactory.getLog(UserServiceImpl.class);//日记

	@SuppressWarnings("rawtypes")
	private List<IUserAdapter> userAdapters;
	
	@SuppressWarnings("rawtypes")
	private Map<String, IUserAdapter> userAdapterBeans;
	
	/**
	 * 缓存服务
	 */
	private CacheManager cacheManager;
	
	/**
	 * 菜单缓存, 30分钟过期
	 */
	private final static String MENU_CACHE = "com.gsoft.framework.security.UserService_menuCache";
	
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IRealmUserInfo getRealmUserInfo(IUser user) {
		if(userAdapters!=null){
			for(IUserAdapter adapter:userAdapters){
				if(adapter.supports(user)){
					IRealmUserInfo info = adapter.getRealmUserInfo(user);
					if(info!=null){
						return info;
					}
				}
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public IRealmUserInfo getRealmUserInfo(IRealmUserToken token,
			boolean loadMenus) {
		if(userAdapters!=null){
			for(IUserAdapter adapter:userAdapters){
				if(adapter.supports(token)){
					IRealmUserInfo userInfo =  adapter.getRealmUserInfo(token);
					if(userInfo!=null){
						if(loadMenus){
							this.loadMenuTreeToCache(adapter,userInfo.getUser());
						}
						return userInfo;
					}
				}
			}
		}
		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.initRealmProviders(applicationContext);
	}

	@SuppressWarnings("rawtypes")
	private void initRealmProviders(ApplicationContext context) {
		if(userAdapters==null){
			userAdapterBeans = 
				BeanFactoryUtils.beansOfTypeIncludingAncestors(context, IUserAdapter.class, true, false);
			if(userAdapterBeans!=null){
				userAdapters = new ArrayList<IUserAdapter>(userAdapterBeans.values());
	            Collections.sort(userAdapters, new OrderComparator());
			}
		}
	}
	
	/**
	 * 加载用户角色对应的菜单树到缓存中
	 * @param userAdapter
	 * @param userInfo
	 */
	@SuppressWarnings("rawtypes")
	private HtmlTreeNode loadMenuTreeToCache(IUserAdapter userAdapter,IUser user){
		Cache<String, HtmlTreeNode> menuCache = this.getMenuCache();
		if(menuCache!=null){
			String menuKey = this.getMenuKey(user);
			HtmlTreeNode treeNode = menuCache.get(menuKey);
			if(treeNode==null){//如果缓存中没有，则加载菜单
				treeNode = buildMenuTree(userAdapter,user);
				menuCache.put(menuKey, treeNode);
			}
			return treeNode;
		}
		return null;
	}
	
	
	/**
	 * 构建用户菜单树
	 * @param userAdapter
	 * @param userInfo
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private HtmlTreeNode buildMenuTree(IUserAdapter userAdapter,
			IUser user) {
		//当前帐号提供类的全部菜单
		//获取全部的菜单
		List<IMenu> allMenus;
		
		if(IUserAdapter2.class.isAssignableFrom(userAdapter.getClass())){
			allMenus = ((IUserAdapter2)userAdapter).getProviderMenus(user);
		}else{
			allMenus = userAdapter.getProviderMenus();
		}
		
		//获取当前用户的菜单
		List<String> menuIds = userAdapter.getAccountMenus(user);
		
		List<IMenu> userMenus = new ArrayList<IMenu>();
		
		if(allMenus!=null){
			for(IMenu menu:allMenus){
				if(menuIds.contains(menu.getMenuId())){
					userMenus.add(menu);
				}
			}
		}
		HtmlTreeNode tree = TreeUtils.listToHtmlTree(userMenus, null, "系统菜单");
		tree.setId("TREE_SYS_MENUID");
		return tree;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HtmlTreeNode getCachedMenuTree(IUser user){
		Cache<String, HtmlTreeNode> menuCache = this.getMenuCache();
		//开发角色时，每次重新加载菜单
		if(user.roleIds().contains("ROLE_MODULE")){
			this.clearMenuCache();
		}
		
		HtmlTreeNode treeNode = null;
		if(menuCache!=null){
			String menuKey = getMenuKey(user);
			treeNode = menuCache.get(menuKey);
			if(treeNode==null){
				if(userAdapters!=null){
					for(IUserAdapter adapter:userAdapters){
						if(adapter.supports(user)){
							IRealmUserInfo info = adapter.getRealmUserInfo(user);
							if(info!=null){
								treeNode = this.loadMenuTreeToCache(adapter, user);
								menuCache.put(menuKey, treeNode);
								break;
							}
						}
					}
				}
			}
		}
		return treeNode;
	}

	private Cache<String, HtmlTreeNode> getMenuCache(){
		if(cacheManager==null){
			return null;
		}
		return cacheManager.getCache(MENU_CACHE);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String getMenuKey(IUser user){
		List<String> roles = new ArrayList(user.roleIds());
		Collections.sort(roles);
		return StringUtils.collectionToCommaDelimitedString(roles);
	}
	
	/**
	 * 清除用户菜单缓存
	 */
	public void clearMenuCache(){
		Cache<String, HtmlTreeNode> menuCache = this.getMenuCache();
		if(menuCache!=null){
			//清理菜单缓存，用户开发时菜单变更，实际的菜单变更除当前操作的机器外，需要30分钟后生效
			menuCache.clear();
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public IUserAdapter getUserAdapter(String beanName) {
		if(StringUtils.isEmpty(beanName)) return null;
		return userAdapterBeans.get(beanName);
	}
	
	/**
	 * 修改密码服务
	 * @param account
	 * @param password
	 * @param confirmPassword
	 * @param oldPassword
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean modifyPassword(
			IUser account,
			String password,
			String confirmPassword,
			String oldPassword){
		if(userAdapters!=null){
			for(IUserAdapter adapter:userAdapters){
				if(adapter.supports(account)){
					if(adapter instanceof IPasswordService){
						((IPasswordService)adapter).modifyPassword(account.getLoginName(),password,confirmPassword,oldPassword);
					}else{
						logger.warn(adapter.getClass()+"未实现PasswordService接口.");
					}
					break;
				}
			}
		}
		return true;
	}
	
	/**
	 * 重置密码
	 * @param account
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean resetPassword(
			IUser account){
		if(userAdapters!=null){
			for(IUserAdapter adapter:userAdapters){
				if(adapter.supports(account)&&adapter.getClass().isAssignableFrom(IPasswordService.class)){
					((IPasswordService)adapter).resetPassword(account.getLoginName());
					break;
				}
			}
		}
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	public TreeNode getAgencyTree(IUser user){
		if(userAdapters!=null){
			for(IUserAdapter adapter:userAdapters){
				if(adapter.supports(user)){
					return adapter.getAgencyTree();
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<IAgency> getAgencyByParent(IUser user,String parentAgencyId){
		if(userAdapters!=null){
			for(IUserAdapter adapter:userAdapters){
				if(adapter.supports(user)){
					return adapter.getAgencyByParent(parentAgencyId);
				}
			}
		}
		return null;
	}
}
