/**
 * 
 */
package org.youi.framework.security;

/**
 * @author zhyi_12
 *
 */
public interface HostStrategy {

	/**
	 * 跳过校验码检查的用户地址
	 * @param host
	 * @return
	 */
	public boolean skipVerification(String username,String host);
	
	/**
	 * 运行访问校验
	 * @param host
	 * @return
	 */
	public boolean  allowAccess(String username,String host);

}
