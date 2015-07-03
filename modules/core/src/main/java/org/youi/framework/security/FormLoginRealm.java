/**
 * 
 */
package org.youi.framework.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

/**
 * @author zhyi_12
 *
 */
public class FormLoginRealm extends AuthorizingRealm implements ApplicationContextAware{

	/**
	 * 用户服务
	 */
	private UserService userService;
	
//	private IRealmMenuProvider realmMenuProvider;//
//	
//	@SuppressWarnings("rawtypes")
//	public void setProviders(List<IRealmProvider> providers) {
//		this.providers = providers;
//	}
//
//	public void setRealmMenuProvider(IRealmMenuProvider realmMenuProvider) {
//		this.realmMenuProvider = realmMenuProvider;
//	}

	/* 
	 * 登录
	 * (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//校验
		DefaultLoginFormToken formToken = (DefaultLoginFormToken)token;
		
		if(formToken.isVerification()==false){
			throw new VerificationCodeException();//抛出校验码出错异常
		}
		
		if(StringUtils.isEmpty(formToken.getPassword())){
			throw new CredentialsException("密码不能为空!");
		}
		
		//获取用户信息，并且加载匹配用户的全局菜单
		IRealmUserInfo userInfo = userService.getRealmUserInfo(formToken,true);
		
		if(userInfo==null){
			throw new UnknownAccountException("用户"+token.getPrincipal()+"不存在!");
		}
		
		return userInfo;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/*
	 * 登录后 
	 * (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Object principal = principals.getPrimaryPrincipal();
		//授权，包括角色和访问授权相关的信息加入
		if(principal instanceof IUser){
			return userService.getRealmUserInfo((IUser)principal);
//			IRealmUser user = (IRealmUser)principal;
//			if(providers!=null){
//				for(IRealmProvider provider:providers){
//					if(provider.supports(user)){
//						return provider.getAuthorizationInfo(user);
//					}
//				}
//			}
		}
		return new SimpleAuthorizationInfo();
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.initApplication(applicationContext);
	}

	private void initApplication(ApplicationContext context) {
		setAuthenticationTokenClass(DefaultLoginFormToken.class);
	}

//	@SuppressWarnings("rawtypes")
//	private void initRealmProviders(ApplicationContext context) {
//		if(providers==null){
//			Map<String, IRealmProvider> matchingBeans = 
//				BeanFactoryUtils.beansOfTypeIncludingAncestors(context, IRealmProvider.class, true, false);
//			if(matchingBeans!=null){
//				providers = new ArrayList<IRealmProvider>(matchingBeans.values());
//	            Collections.sort(providers, new OrderComparator());
//			}
//		}
//	}

}
