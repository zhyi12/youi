/*
 * YOUI框架
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.youi.framework.core.web.controller;


/**
 * @功能描述  URL资源
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Dec 25, 2010
 */
public class UrlResource {
	//public 
	
	private String httpMethod;
	
	private String url;
	
	private String type;//访问类型 page 或者 data
	
	private String urlId;//唯一编号
	
	private String urlText;//描述
	
	private String[] subPages;//子页面
	
	private String[] datas;//交易数据ID

	public UrlResource(String urlId,String httpMethod, String url) {
		super();
		this.urlId = urlId;
		this.httpMethod = httpMethod;
		this.url = url;
	}

	
	/**
	 * @return the httpMethod
	 */
	public String getHttpMethod() {
		return httpMethod;
	}


	/**
	 * @param httpMethod the httpMethod to set
	 */
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}


	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the urlId
	 */
	public String getUrlId() {
		return urlId;
	}

	/**
	 * @param urlId the urlId to set
	 */
	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}

	/**
	 * @return the subPages
	 */
	public String[] getSubPages() {
		return subPages;
	}

	/**
	 * @param subPages the subPages to set
	 */
	public void setSubPages(String[] subPages) {
		this.subPages = subPages;
	}

	/**
	 * @return the datas
	 */
	public String[] getDatas() {
		return datas;
	}

	/**
	 * @param datas the datas to set
	 */
	public void setDatas(String[] datas) {
		this.datas = datas;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((httpMethod == null) ? 0 : httpMethod.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final UrlResource other = (UrlResource) obj;
		if (httpMethod == null) {
			if (other.httpMethod != null)
				return false;
		} else if (!httpMethod.equals(other.httpMethod))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append(urlText==null?"":(urlText + " "))
			.append(urlId+" ")
			.append(httpMethod+" ")
			.append(url);
		return buffer.toString();
	}

	public void setUrlText(String urlText) {
		this.urlText = urlText;
	}

	/**
	 * @return the urlText
	 */
	public String getUrlText() {
		return urlText;
	}

	public boolean contains(String httpMethod, String url) {
//		if (this.httpMethod == null) {
//			if (httpMethod != null)
//				return false;
//		} else if (!this.httpMethod.equals(httpMethod))
//			return false;
		if (this.url == null) {
			if (url != null)
				return false;
		} else if (!this.url.equals(url))
			return false;
		return true;
	}
	
}
