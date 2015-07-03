/*
 * YOUI框架
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.youi.framework.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import org.youi.framework.core.Constants;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午03:15:15</p>
 */
public class DefaultFormAuthenticationFilter extends FormAuthenticationFilter{
	public static final String FORM_DYNAMIC_CODE = "FORM_DYNAMIC_CODE";
	
	public static final String DEFAULT_DYNAMICCODE_PARAM = "dynamicCode";
    public static final String DEFAULT_VERIFICATIONCODE_PARAM = "verificationCode";
    private static final String DEFAULT_AGENCYID_PARAM = "agencyId";
	
    private boolean dynamicCheck = false;//动态校验
    
    private boolean vcodeCheck = false;//校验码校验
    
    private String authParam = DEFAULT_AGENCYID_PARAM;
    
	@Override
	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) {
		
		DefaultLoginFormToken token = 
			new DefaultLoginFormToken(getUsername(request),
					getPassword(request),
					this.isRememberMe(request),
					this.getHost(request));
		token.setDynamicCode(getDynamicCode(request));
//		token.setVerificationCode();
		token.setDynamicCheck(dynamicCheck);
		token.setAuthParam(getAuthParam(request));
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		token.setContextPath(httpRequest.getContextPath());
		
		//
		if(vcodeCheck){
			String vCode = getVerificationCode(request);
			Object sessionRandCode = 
				httpRequest.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			if(sessionRandCode==null||!sessionRandCode.equals(vCode)){
				token.setVerification(false);
			}
		}
		return token;
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onLoginSuccess(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.subject.Subject, javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		String isJson = request.getParameter("json");
		//如果是json类型登录请求，则返回json输出信息
		if(Boolean.valueOf(isJson)==true){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("principal",token.getPrincipal());
			params.put("username", getUsername(request));
			WebUtils.issueRedirect(request, response, "/common/loginSuccess.json",params);
			return true;
		}
		return super.onLoginSuccess(token, subject, request, response);
	}
	

	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		String isJson = request.getParameter("json");
		//如果是json类型登录请求，则返回json输出信息
		if(Boolean.valueOf(isJson)==true){
			this.setFailureAttribute(request, e);
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("error",request.getAttribute("error"));
			params.put("username", getUsername(request));
			try {
				WebUtils.issueRedirect(request, response, "/common/loginFailed.json",params);
				return true;
			} catch (IOException ioe) {
				
			}
		}
		return super.onLoginFailure(token, e, request, response);
	}


	private String getVerificationCode(ServletRequest request) {
		return WebUtils.getCleanParam(request, DEFAULT_VERIFICATIONCODE_PARAM);
	}

	private String getDynamicCode(ServletRequest request) {
		return WebUtils.getCleanParam(request, DEFAULT_DYNAMICCODE_PARAM);
	}
	
	private String getAuthParam(ServletRequest request) {
		return WebUtils.getCleanParam(request, this.getAuthParam());
	}
	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#setFailureAttribute(javax.servlet.ServletRequest, org.apache.shiro.authc.AuthenticationException)
	 */
	@Override
	protected void setFailureAttribute(ServletRequest request,
			AuthenticationException ae) {
		super.setFailureAttribute(request, ae);
		String message;
		String username = this.getUsername(request);
		if(ae instanceof IncorrectCredentialsException){
			message = "密码不正确！";
		}else if(ae instanceof CredentialsException){
			message = ae.getMessage();
		}else if(ae instanceof UnknownAccountException){
			message = "帐号["+username+"]不存在！";
		}else if(ae instanceof org.apache.shiro.authc.AuthenticationException){
			message = "帐号["+username+"]不可登录！";
		}else{
			message = ae.getMessage();
		}
		//ae.printStackTrace();
		request.setAttribute("error", message);
	}

	/**
	 * @param dynamicCheck the dynamicCheck to set
	 */
	public void setDynamicCheck(boolean dynamicCheck) {
		this.dynamicCheck = dynamicCheck;
	}

	/**
	 * @param vcodeCheck the vcodeCheck to set
	 */
	public void setVcodeCheck(boolean vcodeCheck) {
		this.vcodeCheck = vcodeCheck;
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

	/* (non-Javadoc)
	 * @see org.apache.shiro.web.filter.AccessControlFilter#redirectToLogin(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected void redirectToLogin(ServletRequest request,
			ServletResponse response) throws IOException {
		if(((HttpServletRequest) request).getRequestURI().endsWith(Constants.DATA_URL_POSTFIX)){//数据访问异常
			//如果是json格式的访问，返回json格式的登录提示
			WebUtils.issueRedirect(request, response, "/common/accessDenied.json");
		}else{
			super.redirectToLogin(request, response);
		}
	}

}
