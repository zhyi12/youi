package org.youi.framework.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.youi.framework.context.annotation.Module;


/**
 * 
 * 模块扫描回调类
 * @author Administrator
 *
 */
public class ModuleComponentScanCallback implements ModuleScanCallback {
	
	private final static Log logger = LogFactory.getLog(ModuleComponentScanCallback.class);
	
	private StringBuffer moduleBasePackage = new StringBuffer();
	
	private String connectPackageScan(String basePackage ,String[] scanPackages){
		StringBuffer packageBuf = new StringBuffer();
		for(String scanPackage:scanPackages){
			packageBuf.append(",");
			packageBuf.append(basePackage+"."+scanPackage);
		}
		return packageBuf.toString();
	}
	
	@Override
	public void doScan(Module module,String basePackage) {
		
		moduleBasePackage.append(connectPackageScan(basePackage, module.scanPackages()));
		// 
		
		ServicesModuleConfig config = new ServicesModuleConfig();
		config.setCaption(module.caption());
		config.setCode(module.name());
		config.setBasePackage(basePackage);
		//config.setMenuTree(menuTree);
		if(logger.isDebugEnabled()){
			logger.debug("scan module - "+module.name());
		}
//		ModuleFactory.getInstance().regesiterModule(config);
	}
	
	//
	public String getModuleBasePackage() {
		if(moduleBasePackage.length()==0)return "";
		return moduleBasePackage.substring(1);
	}
}
