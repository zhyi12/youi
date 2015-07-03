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
package org.youi.framework.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Dec 21, 2010
 */
public class RequestUtils {

	/**
	 * 根据参数名称从queryString中获取参数值
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getQueryParameter(HttpServletRequest request,
			String name){
		String queryString = request.getQueryString();
		
		if(StringUtils.isEmpty(queryString)){
			return null;
		}
		
		try {
			queryString = URLDecoder.decode(queryString, "GBK");
		} catch (UnsupportedEncodingException e) {
			return request.getParameter(name);
		}
		
		String[] keyValues = queryString.split("&");
		for(String keyValue:keyValues){
			String[] aKeyValue = keyValue.split("=");
			
			if(aKeyValue.length==2&&name.equals(aKeyValue[0])){
				return aKeyValue[1];
			}
		}
		return request.getParameter(name);
	}
	public static String getParameterValue(HttpServletRequest request,
			String name) {
		return request.getParameter(name);
	}

	public static String[] getParameterValues(HttpServletRequest request,
			String name) {
		return request.getParameterValues(name);
	}
	
	/**
	 * 获得当前spring上下文
	 * @param request
	 * @return
	 */
	public static WebApplicationContext getApplicationContext(HttpServletRequest request){
		return WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
	}
	
	/**
	 * 从request中查找当前环境指定路径下的物理路径
	 * @param request
	 * @param path
	 * @return
	 */
	public static String getRealPath(HttpServletRequest request,String path){
		WebApplicationContext context = getApplicationContext(request);
		Assert.notNull(context, "spring WebApplicationContext未在request中找到！");
		URL url = null;
		try {
			//使用WebApplicationContext的getResource查找
			Resource resource = context.getResource(path);
			url = resource.getURL();
			
			/*
			 * 判断从resource中返回的URL协议是否为file
			 * 1：用maven测试环境运行时协议为jndi
			 * 2：war模式部署返回的为file
			 */
			if(!"file".equals(url.getProtocol())){
				url = resource.getFile().toURI().toURL();
			}
		} catch (IOException e) {
			String errorMsg = "路径："+path+"未找到！";
			//抛出异常
			throw new RuntimeException(errorMsg,e);
		}
		return url!=null?url.getPath():null;
	}
	/**
	 * @param request
	 * @return
	 */
	public static Locale getLocale(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return Locale.getDefault();
	}

}
