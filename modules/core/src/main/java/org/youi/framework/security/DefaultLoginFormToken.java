/**
 * 
 */
package org.youi.framework.security;

import org.apache.shiro.authc.UsernamePasswordToken;

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
public class DefaultLoginFormToken extends UsernamePasswordToken implements IRealmUserToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8866503207510464030L;
	
	private boolean verification = true;//校验码校验结果
	
	private String dynamicCode;//动态码
	
	private boolean isDynamicCheck;//是否开启动态码校验
	
	private String contextPath;//上下文路径
	
	private String authParam;//其他登录参数

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public DefaultLoginFormToken(String username, String password,
			boolean rememberMe, String host) {
		super(username, password, rememberMe, host);
	}

	

	/**
	 * @return the verificationFlag
	 */
	public boolean isVerification() {
		return verification;
	}

	/**
	 * @param verificationFlag the verificationFlag to set
	 */
	public void setVerification(boolean verification) {
		this.verification = verification;
	}

	public String getDynamicCode() {
		return dynamicCode;
	}

	public void setDynamicCode(String dynamicCode) {
		this.dynamicCode = dynamicCode;
	}

	public boolean isDynamicCheck() {
		return isDynamicCheck;
	}

	public void setDynamicCheck(boolean isDynamicCheck) {
		this.isDynamicCheck = isDynamicCheck;
	}

	/**
	 * @return the authParam
	 */
	public String getAuthParam() {
		return authParam;
	}

	/**
	 * @param authParam the authParam to set
	 */
	public void setAuthParam(String authParam) {
		this.authParam = authParam;
	}

}
