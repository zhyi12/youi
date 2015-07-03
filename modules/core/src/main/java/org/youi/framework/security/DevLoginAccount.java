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

import org.youi.framework.util.PasswordUtils;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午08:11:53</p>
 */
public class DevLoginAccount extends LoginAccount {

	public static final String ROLE_DEV = "ROLE_DEV";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7021452529668039736L;

	public DevLoginAccount(final String username, String password) {
		super(new DevPrincipal(username), PasswordUtils.md5Password(password));
		this.addRole(ROLE_DEV);
	}

	public String toString(){
		return this.getPrincipals().toString();
	}

	@Override
	public String getUsername() {
		return ((DevPrincipal)getPrincipals().getPrimaryPrincipal()).getLoginName();
	}
}