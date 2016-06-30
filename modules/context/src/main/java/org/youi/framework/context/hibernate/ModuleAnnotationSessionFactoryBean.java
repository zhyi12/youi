/**
 * 
 */
package org.youi.framework.context.hibernate;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.youi.framework.context.ModuleScanHelper;

/**
 * 
 * 根据annotation加载hibernate 实体
 * @author Administrator
 * 
 */
public class ModuleAnnotationSessionFactoryBean extends LocalSessionFactoryBean {

	private ResourcePatternResolver moduleResourcePatternResolver = 
		new PathMatchingResourcePatternResolver();
	
	public void setResourceLoader(ResourceLoader resourceLoader) {
		super.setResourceLoader(resourceLoader);
		ResourceLoader moduleLoader = new ClassRelativeResourceLoader(ResourceLoader.class);
		this.moduleResourcePatternResolver = ResourcePatternUtils
				.getResourcePatternResolver(moduleLoader);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void afterPropertiesSet() throws IOException {
		ModuleEntityScanCallback scanCallback = 	new ModuleEntityScanCallback(moduleResourcePatternResolver);
		ModuleScanHelper.scanModuleConfig(scanCallback);
		List<Class> scas = scanCallback.getAnnotatedClasses();
		this.setAnnotatedClasses(scas.toArray(new Class[scas.size()]));
		super.afterPropertiesSet();
	}
}
