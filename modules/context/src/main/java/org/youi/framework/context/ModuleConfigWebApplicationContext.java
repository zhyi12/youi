/**
 * 
 */
package org.youi.framework.context;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * @author zhyi_12
 *
 */
public class ModuleConfigWebApplicationContext extends AnnotationConfigWebApplicationContext{

	@Override
	public void refresh() {
		//按模块配置扫描加载上下文
		
		ModuleComponentScanCallback moduleComponentScanCallback = 
				new ModuleComponentScanCallback();
		//
		ModuleScanHelper.scanModuleConfig(moduleComponentScanCallback);
		
		String[] modulePackages = 
				moduleComponentScanCallback.getModuleBasePackage().split(",");
		
		this.scan(modulePackages);
		super.refresh();
	}
	
	
}
