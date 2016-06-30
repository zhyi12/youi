/**
 * 
 */
package org.youi.framework.services;

/**
 * @author zhyi_12
 *
 */
public class ServicesConstants {
	
	/**
	 * esb-console
	 * esb-center 服务接收的公共参数
	 * 如果没有头参数，则从pub开头的参数中获取公共变量
	 */
	public static final String PUB_PARAM_PREFIX = "esbpub.";
	
	//错误码
	public static final String ESB_ERROR_DO_SERVICE =  "960001";//服务调用异常
	
	public static final String ESB_ERROR_CHECK_TOKEN =  "960002";//token校验失败
	
	public static final String ESB_ERROR_SERVICE_NOTFOUNT =  "960003";//未找到服务
	
	public static final String  ESB_ERROR_SERVICE_NOT_ENOUGH_PARAM ="960004";//缺少服务参数

	
	/******************************公共头参数************************************/
	public static final String HEADER_SYS_CODE = "syscode";//访问系统标志
	
	public static final String HEADER_CHANNEL = "channel";//通道名
	
	public static final String HEADER_SERVICE_GROUP = "interfaceName";//接口名
	
	public static final String HEADER_SERVICE = "serviceName";//服务名
	
	public static final String HEADER_AUTHORIZATION = "authorization";//校验码
	
	public static final String HEADER_MD5 = "md5";//数据防篡改校验结果
	
	public static final String HEADER_INSTANCE_ID = "instanceId";
	
	public static final String HEADER_REMOTE_IP = "remoteIp";//用户客户端IP
	
	public static final String HEADER_FLOW_NO = "flowno";/*流水号*/
	
	public static final String HEADER_RESULT_MODE = "resultMode";/*返回值模式*/
	
	//全部的公共头参数
	public static final String[] ESB_PUB_HEADER ={
		HEADER_SYS_CODE,
		HEADER_SERVICE_GROUP,
		HEADER_SERVICE,
		HEADER_AUTHORIZATION,
		HEADER_MD5,
		HEADER_INSTANCE_ID,
		HEADER_FLOW_NO,
		HEADER_RESULT_MODE
	};
	
	public static final String RESULT_MODE_ESB = "esb";//esb分布式总线调用模式
		
	/*********/
	public static final String PARAM_WS_REQ_CONFIG_BEAN = "EP_wsReqConfigBean";
	
	public static final String ESB_HEADER_PARAM_TOKEN = "token";//token 参数名
	
	
	
	/************       日志级别      ****************/
	public static final int LOG_LEVEL_DEBUG = 1;
	public static final int LOG_LEVEL_INFO = 2;
	public static final int LOG_LEVEL_WARN = 3;
	public static final int LOG_LEVEL_ERROR = 4;
	
	/***********************  *********************/
	public static final String FLOW_STEP_START = "start";
	
	public static final String FLOW_STEP_NODE_START = "nodeStart";
	
	public static final String FLOW_STEP_NODE_End = "nodeEnd";
	
	public static final String FLOW_STEP_END = "end";
	
	
	/***************************************esb bean配置 **************************************/
	public static final String ESB_BEAN_BeanFactoryChannelResolver = "esbChannelResolver";
	public static final String ESB_BEAN_MessagingTemplate = "esbMessagingTemplate";
	public static final String ESB_BEAN_ExceptionTransformer = "esbExceptionTransformer";
	public static final String ESB_BEAN_EsbExporterCaller = "esbExporterCaller";
	
	public static final String ESB_BEAN_EsbWebSecurityRequestRouter = "esbWebSecurityRequestRouter";
	
	/***********************使用的缓存主键配置*******************/
	
	public static final String ESB_USER_TOKEN_CACHE_NAME = "com.gsoft.framework.esb.router.SecurityRequestRouter";
	
	
	
}
