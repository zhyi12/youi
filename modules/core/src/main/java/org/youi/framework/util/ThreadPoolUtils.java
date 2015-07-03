/**
 * 
 */
package org.youi.framework.util;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 线程池辅助工具类
 * @author Administrator
 *
 */
public class ThreadPoolUtils {
	private final static Log logger = LogFactory.getLog(ThreadPoolUtils.class);
	
	public final static int MAX_BATCH_COMMANDS = 50000;//
	/**
	 * 执行批量命令
	 * @param <T>
	 * @param commands
	 * @param poolSize
	 */
	public static <T> void executeCommands(List<Callable<T>> commands,
			boolean waitComplete,
			int poolSize){
		double commandCount = commands.size();
		if(commandCount>MAX_BATCH_COMMANDS){
			double batchCount = Math.ceil(commandCount/MAX_BATCH_COMMANDS) ;//
			for(int i=0;i<batchCount;i++){
				executeCommands(
					commands.subList(i*MAX_BATCH_COMMANDS, Math.min((i+1)*MAX_BATCH_COMMANDS, commands.size())),
					waitComplete,
					poolSize);
			}
		}else{
			//创建线程池
			ThreadPoolExecutor executorService 
				= (ThreadPoolExecutor) Executors.newFixedThreadPool(poolSize);
			if(logger.isDebugEnabled()){
				logger.debug("创建线程池【"+poolSize+"】.");
			}
			try {
				for(Callable<T> command:commands){
					executorService.execute(new FutureTask<T>(command));
				}
				if(waitComplete){//等待执行完成
					while(executorService.getCompletedTaskCount()!=executorService.getTaskCount()){
						Thread.sleep(100);//线程等待100毫秒
					}
					if(logger.isDebugEnabled()){
						logger.debug("所有线程执行成功.");
					}
				}
			}catch (Exception e) {
				throw new RuntimeException("线程执行遇到异常【"+e.getMessage()+"】.",e);
			}finally{
				executorService.shutdown();
			}
		}
	}
}
