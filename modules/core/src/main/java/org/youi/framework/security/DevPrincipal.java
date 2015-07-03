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

import java.util.ArrayList;
import java.util.List;

import org.youi.framework.core.dataobj.Domain;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午03:13:57</p>
 */
public class DevPrincipal implements AccountPrincipal,Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8724133789203095014L;
	
	private String loginName;
	public DevPrincipal(String loginName){
		this.loginName = loginName;
	}
	/* (non-Javadoc)
	 * @see org.youi.common.security.AccountPrincipal#getLoginName()
	 */
	@Override
	public String getLoginName() {
		return loginName;
	}

	public String toString(){
		return loginName;
	}
	
	@Override
	public PrincipalConfig getPrincipalConfig() {
		return null;
	}
	@Override
	public List<String> roleIds() {
		List<String> roleIds = new ArrayList<String>();
		roleIds.add("ROLE_MODULE");
		return roleIds;
	}
}
