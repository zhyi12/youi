package org.youi.framework.context;

import org.youi.framework.context.annotation.Module;

public interface ModuleScanCallback {

	void doScan(Module moduleConfig,String moduleBasePackage);

}
