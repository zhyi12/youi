package org.youi.framework.services.router;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.youi.framework.security.AccountPrincipal;
import org.youi.framework.services.ServicesConstants;
import org.youi.framework.services.data.ReqContext;
import org.youi.framework.util.Assert;
import org.youi.framework.util.SecurityUtils;
import org.youi.framework.util.StringUtils;

/*
 * AbstractMessageRouter
 */
@Component("webSecurityRequestRouter")
public class WebSecurityRequestRouter extends AbstractMessageRouter{
	
	private Map<String,String> channelMapping;//channel映射
	
//	@Autowired
//	private SequenceService sequenceService;//交易流水号生成器
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void handleMessageInternal(Message<?> message) {
		Object payload = message.getPayload();
		if(payload instanceof LinkedMultiValueMap){
			//构造请求
			ReqContext<Object> reqContext = new ReqContext<Object>();
			reqContext.putAll((LinkedMultiValueMap<String,Object>)payload);
			
			Map<String,Object> headers = new HashMap<String,Object>();
			
			//加入客户端用户IP地址
			headers.put(ServicesConstants.HEADER_REMOTE_IP, getRemoteIp());
			//增加交易流水号
			
			//esbpub.flowno
			if(!((LinkedMultiValueMap) payload).containsKey(ServicesConstants.PUB_PARAM_PREFIX+ServicesConstants.HEADER_FLOW_NO)){
//				headers.put(ServicesConstants.HEADER_FLOW_NO, esbSequenceService.generate());
				
				Object export = reqContext.getFirst("pager:export");
				if(export!=null){
					//导出标识
					headers.put("export", export.toString());
					headers.put("exportTitle", reqContext.getFirst("pager:exportTitle"));
					headers.put("exportProperties", reqContext.get("pager:property"));
					headers.put("exportHeaders",reqContext.get("pager:header"));
					headers.put("exportConverts", reqContext.get("pager:convert"));
				}
				//标注返回jsonp格式数据
				Object jsonp = reqContext.getFirst("data:jsonp");
				if(jsonp!=null){
					headers.put("jsonp", jsonp);
				}
			}
			
//			headers.put(ServicesConstants.HEADER_SYS_CODE, this.getSyscode());//getSyscode()
			//加入其他头信息
			headers.putAll(message.getHeaders());
			
			//处理Authorization的大小写，tomcat为authorization，weblogic中为Authorization
			if(headers.containsKey("Authorization")&&!headers.containsKey(ServicesConstants.HEADER_AUTHORIZATION)){
				headers.put(ServicesConstants.HEADER_AUTHORIZATION, headers.get("Authorization"));
			}else if(headers.containsKey(ServicesConstants.HEADER_AUTHORIZATION)&&!headers.containsKey("Authorization")){
				headers.put("Authorization", headers.get(ServicesConstants.HEADER_AUTHORIZATION));
			}
			
			//未登录时需要校验token
//			if(SecurityUtils.getAccount()==null){
//				//
//				Object authorization = headers.get(ServicesConstants.HEADER_AUTHORIZATION);
//				
//				esbTokenManager.parseToken(authorization, headers.get(ServicesConstants.HEADER_SERVICE_GROUP).toString(), headers.get(ServicesConstants.HEADER_SERVICE).toString());
//			}
			
			//从登录对象中获取公共交易参数设置到reqContext中
			addPubContextFromAccount(reqContext);
			
			message =  MessageBuilder.withPayload(reqContext).copyHeaders(headers).build();
			
//			EsbLoggerService loggerService = this.getEsbLoggerService();
//			
//			if(loggerService!=null){
//				//记录调用交易日志
//				loggerService.writeLog(message,"WEB请求交易",null, System.currentTimeMillis());
//			}
		}
		
		super.handleMessageInternal(message);
	}

	@Override
	protected Collection<MessageChannel> determineTargetChannels(
			Message<?> message) {
		
		MessageChannel channel;
		try {
			channel = getBeanFactory().getBean(getChannelName(message),MessageChannel.class);
		} catch (BeansException e) {
			//TODO 抛出新的异常 SerializingHttpMessageConverter
			throw e;
		}
		
		Collection<MessageChannel> channels = new ArrayList<MessageChannel>();
		channels.add(channel);
		
		return channels;
	}
	
	protected String getChannelName(Message<?> message){
		String channelName = this.getHeaderChannel(message);
		String channel;
		if(channelMapping!=null&&channelMapping.containsKey(channelName)){
			channel = channelMapping.get(channelName);
		}else{
			channel = channelName;
		}
		return channel;
	}
	
	private String getHeaderChannel(Message<?> message) {
		String channelName = message.getHeaders().get(ServicesConstants.HEADER_CHANNEL,String.class);//
		Assert.notNull(channelName, "请定义头中的channle属性！");
		return channelName;
	}

	public void setChannelMapping(Map<String, String> channelMapping) {
		this.channelMapping = channelMapping;
	}
	
	
	/**
	 * 增加公共头信息
	 * @param reqContext
	 */
	private void addPubContextFromAccount(ReqContext<Object> reqContext) {
		AccountPrincipal account = SecurityUtils.getAccount();
		if(account!=null&&account.getPrincipalConfig()!=null){
			for(Map.Entry<String, String> entry:account.getPrincipalConfig().entrySet()){
				reqContext.add(ServicesConstants.PUB_PARAM_PREFIX+entry.getKey(), entry.getValue());
			}
			
//			if(IdUser.class.isAssignableFrom(account.getClass())){
//				reqContext.add(ServicesConstants.PUB_PARAM_PREFIX+"userId", ((IdUser)account).getUserId());
//			}
			//角色ID
			if(account.roleIds()!=null){
				reqContext.add(ServicesConstants.PUB_PARAM_PREFIX+"roleIds",StringUtils.arrayToCommaDelimitedString(account.roleIds().toArray()));
			}
		}
	}

	/**
	 * @return
	 */
	private String getRemoteIp() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		String remoteIp  = "";
		if(requestAttributes instanceof ServletRequestAttributes){
			remoteIp = ((ServletRequestAttributes)requestAttributes).getRequest().getRemoteAddr();
			
		}
		return remoteIp;
	}

}
