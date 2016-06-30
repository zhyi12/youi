/**
 * 
 */
package org.youi.framework.services.data;

import java.util.List;

import org.springframework.util.LinkedMultiValueMap;
import org.youi.framework.services.ServicesConstants;


/**
 * @author zhyi_12
 *
 */
public class ReqContext<V> extends LinkedMultiValueMap<String,Object>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6862991049418613486L;
	
	private String authorization;//授权码
	
	public ReqContext(){
		
	}
	
	public ReqContext(String interfaceName,String serviceName){
		add(ServicesConstants.PUB_PARAM_PREFIX+ServicesConstants.HEADER_SERVICE_GROUP, interfaceName);
		add(ServicesConstants.PUB_PARAM_PREFIX+ServicesConstants.HEADER_SERVICE, serviceName);
	}
	
	/**
	 * 从上下文中获取接口名
	 * @return
	 */
	public String getInterfaceName(){
		return getString(ServicesConstants.PUB_PARAM_PREFIX+ServicesConstants.HEADER_SERVICE_GROUP);
	}
	
	/**
	 * 获取服务名
	 * @return
	 */
	public String getServiceName(){
		return getString(ServicesConstants.PUB_PARAM_PREFIX+ServicesConstants.HEADER_SERVICE);
	}

	public String getInstanceId() {
		return getString(ServicesConstants.PUB_PARAM_PREFIX+ServicesConstants.HEADER_INSTANCE_ID);
	}
	/**
	 * @param key
	 * @return
	 */
	private String getString(String key){
		List<?> values = this.get(key);
		return values==null||values.size()==0?null:values.get(0).toString();
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

}
