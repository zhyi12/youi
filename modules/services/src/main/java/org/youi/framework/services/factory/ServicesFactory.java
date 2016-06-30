/**
 * 
 */
package org.youi.framework.services.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.dataobj.Record;
import org.youi.framework.core.exception.BusException;
import org.youi.framework.services.ServicesConstants;
import org.youi.framework.services.data.ExceptionResContext;
import org.youi.framework.services.data.PubContext;
import org.youi.framework.services.data.ReqContext;
import org.youi.framework.services.data.ResContext;
import org.youi.framework.services.invoke.ServiceInvoker;
import org.youi.framework.util.PojoMapper;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zhyi_12
 *
 */
@Component("servicesFactory")
public class ServicesFactory {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private static ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private ServiceInvoker serviceInvoker;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResContext exchange(Message message) throws Exception {
//		long startTime = System.currentTimeMillis();
		Object payload = message.getPayload();
		
		if (serviceInvoker != null
				&& Map.class.isAssignableFrom(payload.getClass())) {
			
			Message<ReqContext> reqMessage = null;
			
			if(message.getPayload() instanceof ReqContext){
				reqMessage = message;
			}else if(payload instanceof LinkedMultiValueMap){
				//把参数中的公共头信息移到message的header中
				reqMessage = buildNewMessage((LinkedMultiValueMap)payload);
			}else{
				//抛出异常
				throw new RuntimeException("不支持的ESB请求参数类型!");
			}
			
			ReqContext req = reqMessage.getPayload();
			
			String beanName = reqMessage.getHeaders().get(ServicesConstants.HEADER_SERVICE_GROUP, String.class);
			String methodName = reqMessage.getHeaders().get(ServicesConstants.HEADER_SERVICE, String.class);
			
			ResContext res;
			try {
				PubContext pubContext = hederToPubContext(reqMessage.getHeaders(),beanName,methodName);
				fillPubContext(pubContext,req);
				
				res = serviceInvoker.invoke(beanName,
							methodName, req,
							pubContext);
				//res转换，把domain对象转换为LinkedMultiValueMap对象
				
				if(ServicesConstants.RESULT_MODE_ESB.equals(reqMessage.getHeaders().get(ServicesConstants.HEADER_RESULT_MODE))){
					res = convertRes(res);
				}
				
			} catch (BusException e) {
				res = new ExceptionResContext(e);
				logger.error("交易异常："+e.getMessage());
				logger.error("异常信息："+res.getMessage().getInfo());
			}catch (Throwable e) {
				e.printStackTrace();
				res = new ExceptionResContext(e);
				logger.error("交易异常："+e.getMessage());
				logger.error("异常信息："+res.getMessage().getInfo());
			}
			//消息唯一编号
			res.setInstanceId(req.getInstanceId());
			
			//写交易日志
//			if(esbLoggerService!=null&&StringUtils.isNotEmpty(res.getTrancode())){
//				//输出交易调用日志  
//				esbLoggerService.writeLog(message,"交易调用完成",res,startTime);
//			}
			
			return res;
		}else{
			logger.error("交易调用异常:"+message);
		}
		return null;
	}
	
	/**
	 * @param messageHeader
	 * @return
	 */
	private PubContext hederToPubContext(MessageHeaders messageHeader,String beanName,String methodName){
		PubContext pubContext = new PubContext();
//		SecurityRecord securityRecord = esbTokenManager.parseToken(messageHeader.get(ServicesConstants.HEADER_AUTHORIZATION),beanName,methodName);
		//用户名
//		pubContext.setUsername(securityRecord.getLoginName());
//		pubContext.addParams(securityRecord);
		//
		return pubContext;
	}
	
	/**
	 * 填充公共参数
	 * @param pubContext
	 * @param req
	 */
	private void fillPubContext(PubContext pubContext, ReqContext<Object> req) {
		for(Map.Entry<String, List<Object>> entry:req.entrySet()){
			if(entry.getKey().startsWith(ServicesConstants.PUB_PARAM_PREFIX)){
				String paramName = entry.getKey().substring(ServicesConstants.PUB_PARAM_PREFIX.length());
				pubContext.addParam(paramName, req.getFirst(entry.getKey()));
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ResContext convertRes(ResContext res) {
		
		Domain record = res.getRecord();
		
		if(record!=null&&!Map.class.isAssignableFrom(record.getClass())){
			res.setRecord(convertToRecord(record));
		}
		
		List<? extends Domain> records = res.getRecords();
		
		if(records!=null){
			List<Domain> newRecords = convertToRecords(records);
			res.setRecords(newRecords);
		}
		
		return res;
	}

	/**
	 * @param records
	 * @return
	 */
	private List<Domain> convertToRecords(List<? extends Domain> records) {
		List<Domain> newRecords = new ArrayList<Domain>();
		for(Domain record:records){
			if(record!=null&&!Map.class.isAssignableFrom(record.getClass())){
				newRecords.add(convertToRecord(record));
			}
		}
		return newRecords;
	}

	private Record convertToRecord(Domain domain) {
		Record record = new Record();
		//
		String json = PojoMapper.toJson(domain, false);
		try {
			record = objectMapper.readValue(json, Record.class);
		} catch (Exception e) {
			logger.error(domain+"对象转换错误.");
		}
		return record;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Message<ReqContext> buildNewMessage(LinkedMultiValueMap params) {
		
		ReqContext reqContext = new ReqContext();
		Map<String, Object> headersToCopy = new HashMap<String,Object>();
		//添加全部参数
		reqContext.putAll(params);
		
		for(String headerParam: ServicesConstants.ESB_PUB_HEADER){
			String pubName = ServicesConstants.PUB_PARAM_PREFIX+headerParam;
			if(params.containsKey(pubName)){
				reqContext.put(pubName, params.get(pubName));
				headersToCopy.put(headerParam, params.getFirst(pubName));
				//
				reqContext.remove(headerParam);
			}
		}
		
		return MessageBuilder.withPayload(reqContext).copyHeaders(headersToCopy).build();
	}
}
