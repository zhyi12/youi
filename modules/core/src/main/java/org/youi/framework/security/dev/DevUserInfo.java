/**
 * 
 */
package org.youi.framework.security.dev;

import org.youi.framework.security.AbstractFormUserInfo;
import org.youi.framework.security.IRealmUserInfo;

/**
 * @author zhyi_12
 *
 */
public class DevUserInfo extends AbstractFormUserInfo implements IRealmUserInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1868802600203187837L;

	public DevUserInfo(DevUser user, String password) {
		super(user,password);
	}


}
