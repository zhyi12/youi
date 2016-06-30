/**
 * 
 */
package org.youi.framework.context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.MappingException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.youi.framework.context.annotation.Module;

/**
 * @author Administrator
 *
 */
public class ModuleScanHelper {
	private final static Log logger = LogFactory.getLog(ModuleScanHelper.class);
	
	private static ResourcePatternResolver moduleResourcePatternResolver = 
		new PathMatchingResourcePatternResolver();

	private static TypeFilter moduleConfigTypeFilter = 
		new AnnotationTypeFilter(Module.class);
	
	public static List<Module> scanModuleConfig( ModuleScanCallback callback){
		List<Module> modules = new ArrayList<Module>();
		//扫描模块配置类
		try{
			Resource[] resources = getScanResource();
			MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(
				moduleResourcePatternResolver);
			for (Resource resource : resources) {
				if (resource.isReadable()) {
					
					MetadataReader reader = readerFactory
							.getMetadataReader(resource);
					String className = reader.getClassMetadata().getClassName();
					if (moduleConfigTypeFilter.match(reader, readerFactory)) {
						if(logger.isDebugEnabled()){
							logger.debug("loadClass:"+className);
						}
						
						Class<?> configClass = moduleResourcePatternResolver.getClassLoader().loadClass(className);
						Module moduleConfig = configClass.getAnnotation(Module.class);
						modules.add(moduleConfig);
						callback.doScan(moduleConfig,configClass.getPackage().getName());
					}
				}
			}
		}catch (IOException ex) {
			throw new MappingException(
					"Failed to scan classpath for unlisted classes", ex);
		} catch (ClassNotFoundException ex) {
			throw new MappingException(
					"Failed to load annotated classes from classpath", ex);
		}
		
		return modules;
	}
	
	private static Resource[] getScanResource(){
		String orgPattern = "classpath*:org/**/ModuleConfig.class";
		String comPattern = "classpath*:com/**/ModuleConfig.class";
		List<Resource> resources = new ArrayList<Resource>();
		try {
			Resource[] orgResources = moduleResourcePatternResolver.getResources(orgPattern);
			Resource[] comResources = moduleResourcePatternResolver.getResources(comPattern);
			
			for(Resource resource:orgResources){
				resources.add(resource);
			}
			for(Resource resource:comResources){
				resources.add(resource);
			}
		} catch (IOException ex) {
			throw new MappingException(
					"Failed to scan classpath for unlisted classes", ex);
		}
		return resources.toArray(new Resource[resources.size()]);
	}
}
