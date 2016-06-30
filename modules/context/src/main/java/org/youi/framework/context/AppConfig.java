/**
 * 
 */
package org.youi.framework.context;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


/**
 * @author zhyi_12
 *
 */

@Configuration
@ImportResource("WEB-INF/configs/services/*.xml")
@ComponentScan(basePackages={"org.youi.framework.services"})
public class AppConfig {

	@Bean(initMethod="init",name="cacheManager")
	public CacheManager cacheManagerBean(){
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManagerConfigFile("classpath:encache-youi.xml");
		return cacheManager;
	}
}
