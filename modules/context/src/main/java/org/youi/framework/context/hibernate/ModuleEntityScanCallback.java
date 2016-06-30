package org.youi.framework.context.hibernate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.MappingException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;
import org.youi.framework.context.ModuleScanCallback;
import org.youi.framework.context.annotation.Module;


/**
 * 
 * @author Administrator
 *
 */
public class ModuleEntityScanCallback implements ModuleScanCallback {
	
	private final static Log logger = LogFactory.getLog(ModuleEntityScanCallback.class);
	
	private ResourcePatternResolver moduleResourcePatternResolver;
	
	@SuppressWarnings("rawtypes")
	List<Class> annotatedClasses = new ArrayList<Class>();
	
//	private Configuration config;
	
	public ModuleEntityScanCallback(
			ResourcePatternResolver moduleResourcePatternResolver) {
		super();
		this.moduleResourcePatternResolver = moduleResourcePatternResolver;
	}

	private TypeFilter[] moduleEntityTypeFilters = {
			new AnnotationTypeFilter(javax.persistence.Entity.class, false),
			new AnnotationTypeFilter(Embeddable.class, false),
			new AnnotationTypeFilter(MappedSuperclass.class, false)};
	
	@Override
	public void doScan(Module moduleConfig,String moduleBasePackage) {
		String[] scanPackages = moduleConfig.entityPackages();
		for(String scanPackage:scanPackages){
			scanEntity(moduleBasePackage+"."+scanPackage);
		}
	}
	
	private void scanEntity(String scanModulePackage){
		Set<String> entites = new HashSet<String>();
		try {
			String pattern = "classpath*:"
					+ ClassUtils.convertClassNameToResourcePath(scanModulePackage)
					+ "/**/*.class";
			Resource[] resources = this.moduleResourcePatternResolver.getResources(pattern);
			MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(
					this.moduleResourcePatternResolver);
			for (Resource resource : resources) {
				if (resource.isReadable()) {
					MetadataReader reader = readerFactory.getMetadataReader(resource);
					String className = reader.getClassMetadata().getClassName();
					if (this.matchesModuleFilter(reader, readerFactory)) {
						annotatedClasses.add(this.moduleResourcePatternResolver
								.getClassLoader().loadClass(className));
						entites.add(className);
					}
				}
			}
			if(logger.isDebugEnabled()){
				logger.debug("加载实体对象:"+entites);
			}
			//打印所有加载的模块实体 对象
		} catch (IOException ex) {
			throw new MappingException(
					"Failed to scan classpath for unlisted classes", ex);
		} catch (ClassNotFoundException ex) {
			throw new MappingException(
					"Failed to load annotated classes from classpath", ex);
		}
	}
	
	private boolean matchesModuleFilter(MetadataReader reader,
			MetadataReaderFactory readerFactory) throws IOException {
		if (this.moduleEntityTypeFilters != null) {
			for (TypeFilter filter : this.moduleEntityTypeFilters) {
				if (filter.match(reader, readerFactory)) {
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public List<Class> getAnnotatedClasses() {
		return annotatedClasses;
	}
	
}
