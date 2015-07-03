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

import org.apache.shiro.authc.Account;
import org.apache.shiro.authc.SimpleAccount;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述: 登录帐户</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午07:59:36</p>
 */
public abstract class LoginAccount extends SimpleAccount implements Account {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7838112537801755591L;
	
	protected String authParam;
	
	private AccountPrincipal principal;
	
	/**
	 * @param username
	 * @param password
	 */
	public LoginAccount(AccountPrincipal principal,String password){
		super(principal,password,"loginAccount");
		this.principal = principal;
	}

	public String getUsername(){
		return principal.getLoginName();
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
