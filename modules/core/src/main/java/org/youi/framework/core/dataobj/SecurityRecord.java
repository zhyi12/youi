/**
 * 
 */
package org.youi.framework.core.dataobj;

/**
 * @author zhyi_12
 *
 */
public class SecurityRecord extends Record {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2271343783534676742L;
	
	public String getLoginName() {
		return get("loginName")!=null?get("loginName").toString():"";
	}

	public void setLoginName(String loginName) {
		this.put("loginName", loginName);
	}

	public String getSyscode() {
		return get("syscode")!=null?get("syscode").toString():"";
	}

	public void setSyscode(String syscode) {
		this.put("syscode", syscode);
	}

}
