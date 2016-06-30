/**
 * 
 */
package org.youi.framework.security;

import org.youi.framework.core.dataobj.SecurityRecord;

/**
 * @author zhyi_12
 *
 */
public interface EsbSecurityManager {

	/**
	 * 解密安全信息
	 * @return
	 */
	public SecurityRecord decryptSecurityInfo(String authorization);
	
	/**
	 * 加密安全信息
	 * @return
	 */
	public String encryptSecurityInfo(SecurityRecord record);
	
	
	/**
	 * 获取公钥
	 * @return
	 */
	public String getSecurityKey();
}
