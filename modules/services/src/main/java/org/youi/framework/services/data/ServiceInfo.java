/**
 * 
 */
package org.youi.framework.services.data;

import org.youi.framework.core.dataobj.Domain;

/**
 * @author zhyi_12
 * 
 */
public class ServiceInfo implements Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7734435296573969294L;

	private String trancode;// 交易码
	
	private String caption;// 交易描述
	
	private String beanName;// 接口名
	
	private String methodName;// 方法名

	public String getTrancode() {
		return trancode;
	}

	public void setTrancode(String trancode) {
		this.trancode = trancode;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

}
