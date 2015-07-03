/**
 * 
 */
package org.youi.framework.core.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhyi_12
 *
 */
public abstract class AbstractCacher<T> {
	
	@Autowired
	private CacheManager cacheManager;
	
	protected String getCacheKey(){
		return "cache_"+this.getClass().getName();
	}
	
	protected CacheManager getCacheManager(){
		return this.cacheManager;
	}
	
	protected Cache<String,T> getCache(){
		return this.cacheManager.getCache(getCacheKey());
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
}
