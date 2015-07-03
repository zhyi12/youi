/**
 * 
 */
package org.youi.framework.security;

import org.apache.shiro.authc.AuthenticationException;

/**
 * <p>Title:</p>
 *
 * <p>Description:</p>
 *
 * <p>Copyright:Copyright (c) 2011.9</p>
 *
 * <p>Company:iSoftstone</p>
 *
 * @author Administrator
 * @version 1.0
 */
public class VerificationCodeException extends
		AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2124786214467526929L;
	
	public VerificationCodeException(){
		super("验证码错误！");
	}

}
