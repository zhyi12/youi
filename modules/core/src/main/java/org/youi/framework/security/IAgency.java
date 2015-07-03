package org.youi.framework.security;

/**
 * 机构接口
 * @author zhyi_12
 *
 */
public interface IAgency {

	/**
	 * 获取机构ID
	 * @return
	 */
	public String getAgencyId();
	
	/**
	 * 获取父机构ID
	 * @return
	 */
	public String getParentAgencyId();
	
	/**
	 * 获取机构名称
	 * @param agencyCaption
	 */
	public void setAgencyCaption(String agencyCaption);
}
