package org.youi.framework.core.web.view;

import javax.annotation.Resource;

import org.youi.framework.core.log.DefaultTransLogService;
import org.youi.framework.core.log.TransLogService;


public class AbstractTransLogger {

	@Resource(name="transLogService")
	private TransLogService transLogService;//交易日记
	
	/**
	 * @param transLogService the transLogService to set
	 */
	public void setTransLogService(TransLogService transLogService) {
		this.transLogService = transLogService;
	}

	protected void writeLog(String tranId,String tranCaption,
			String details,
			long useTime,
			String userIP){
		if(transLogService==null){
			transLogService = new DefaultTransLogService();
		}
		transLogService.writeLog(
				tranId,
				tranCaption, 
				details, 
				useTime, 
				userIP);
	}
}
