/**
 * 
 */
package org.youi.framework.core.dataobj;

import java.util.HashMap;
import java.util.Map;

import org.youi.framework.util.StringUtils;

/**
 * @author zhyi_12
 *
 */
public class PubContext implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8278975025549567107L;
	
	public static final String PROPERTY_PARAM_PREFIX="param.";
	
	
	private String username;
	
	private String roles;
	
	private Map<String,Object> params = new HashMap<String,Object>();
	
	public void addParam(String paramName,Object paramValue){
		if(StringUtils.isNotEmpty(paramName)&&paramValue!=null){
			params.put(paramName, paramValue);
		}
	}
	
	public void addParams(Map<String,Object> params){
		if(params!=null){
			this.params.putAll(params);
		}
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
}
