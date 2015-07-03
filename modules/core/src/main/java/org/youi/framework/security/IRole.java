/**
 * 
 */
package org.youi.framework.security;

/**
 * 角色接口
 * @author zhyi_12
 */
public interface IRole {
	public String getRoleId();

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId);

	public String getRoleCaption();

	/**
	 * @param roleCaption the roleCaption to set
	 */
	public void setRoleCaption(String roleCaption);
}
