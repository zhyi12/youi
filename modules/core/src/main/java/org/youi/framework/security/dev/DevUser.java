/**
 * 
 */
package org.youi.framework.security.dev;

import java.util.ArrayList;
import java.util.List;

import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.security.IUser;
import org.youi.framework.security.PrincipalConfig;


/**
 * @author zhyi_12
 *
 */
public class DevUser implements IUser,Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7216960982949006450L;
	
	private static List<String> devRoleIds ;
	
	static{
		devRoleIds = new ArrayList<String>();
		devRoleIds.add("ROLE_MODULE");
	}
	
	private String loginName;
	/* (non-Javadoc)
	 * @see com.gsoft.framework.security.IUser#getRoleIds()
	 */
	@Override
	public List<String> roleIds() {
		return devRoleIds;
	}
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Override
	public PrincipalConfig getPrincipalConfig() {
		return new PrincipalConfig();
	}

	@Override
	public String toString() {
		return this.loginName;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
