/**
 * 
 */
package org.youi.framework.core.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Administrator
 *
 */
public class DefaultTransLogService implements TransLogService {
	
	private  static final Log logger = LogFactory.getLog(DefaultTransLogService.class);
	
	/* (non-Javadoc)
	 * @see org.youi.common.log.TransLogService#writeLog(java.lang.String, java.lang.String, java.lang.Object, long, java.lang.String)
	 */
	public void writeLog(String tranId, String tranName, Object details,
			long time, String ip) {
		StringBuffer logBuf = new StringBuffer();
		logBuf
			.append("交易ID:").append(tranId)
			.append(",交易名称:").append(tranName)
			.append(",详细信息:").append(details!=null?details:"无")
			.append(",访问耗时:").append(time).append("毫秒")
			.append(",访问IP:").append(ip);
		logger.info(logBuf.toString());

	}

}
