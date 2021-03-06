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
package org.youi.framework.core.web;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午09:17:12</p>
 */
public class PageScriptFactory {
	
	@Autowired
	private CacheManager cacheManager;

	/**
	 * @param cacheManager the cacheManager to set
	 */
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public String getPageScript(String sessionId,String pageId) {
		Cache<String, String> scriptCache = 
			cacheManager.getCache("org.youi.common.PageScriptFactory");
		
		return scriptCache.get(pageId+sessionId);
	}
	
	public void addPageScript(String sessionId,String pageId,String pageScript){
		Cache<String, String> scriptCache = 
			cacheManager.getCache("org.youi.common.PageScriptFactory");
		scriptCache.put(pageId+sessionId, pageScript);
	}

}
