package org.youi.framework.util;

import org.youi.framework.security.AccountPrincipal;

public class SecurityUtils {

	
	/**
	 * @return
	 */
	public static Object getPrincipal(){
		return  org.apache.shiro.SecurityUtils.getSubject().getPrincipal();
	}
	
	/**
	 * @return
	 */
	public static AccountPrincipal getAccount(){
		Object principal;
		try {
			principal = getPrincipal();
		} catch (Exception e) {
			principal = null;
		}
		if(principal!=null&&AccountPrincipal.class.isAssignableFrom(principal.getClass())){
			return (AccountPrincipal)principal;
		}
		return null;
	}
}
