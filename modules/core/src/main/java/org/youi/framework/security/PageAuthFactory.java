package org.youi.framework.security;

public interface PageAuthFactory {
	
	/**
	 * 
	 * @param pageId 页面唯一标志
	 * @param authCode 页面元素权限校验码
	 * @return
	 */
	public boolean hasPermission(String pageId,String authCode);
}
