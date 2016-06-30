/**
 * 
 */
package org.youi.framework.security;

/**
 * @author zhyi_12
 *
 */
public interface IPasswordService {

	/**
	 * @param loginName 用户登录名
	 * @param password 新密码
	 * @param confirmPassword 确认新密码
	 * @param oldPassword 旧密码
	 * @return
	 */
	public void modifyPassword(String loginName, String password,
			String confirmPassword, String oldPassword);

	/**
	 * 重置密码
	 * @param loginName
	 * @return
	 */
	public void resetPassword(String loginName);

}
