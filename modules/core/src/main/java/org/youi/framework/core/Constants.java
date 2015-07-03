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
package org.youi.framework.core;

import org.youi.framework.core.context.Config;
import org.youi.framework.util.StringUtils;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jan 17, 2010
 */
public abstract class Constants {
	private final static String DEFAULT_WEB_ROOT_KEY = "youi.root";

	public static final String APP_PATH = null;
	
	public static String WEB_ROOT = null;//web根路径
	
	public final static String SESSION_DECORATOR="SESSION_DECORATOR";//
	
	public final static String SESSION_THEME="SESSION_THEME";//样式
	
	public final static String PARAMETER_PAGE_ID="_pageId";//页面ID参数名称
	
	
	public final static String SYS_MENU_TYPE_H="SYS_MENU_TYPE_H";//系统菜单类型:水平菜单
	public final static String SYS_MENU_TYPE_V="SYS_MENU_TYPE_V";//系统菜单类型:垂直菜单
	
	//异常代码
	public static final String SUCCESS_CODE="000000";//访问成功
	
	public static final String ACCESS_DENIED_CODE="111111";//拒绝访问

	public static final String ERROR_DOMAIN_VALIDATOR="111112";//domain对象校验不通过
	
	public static final String ERROR_DEFAULT_CODE="999999";//默认错误代码
	
	/************************* begin 配置项名称*************************/
	public static final String PROP_CONVERTSERVICE_BEAN = "convertService.bean";//转换服务配置服务名称（spring）
	public static final String PROP_TRANSLOG_BEAN = "transLog.bean";//日记服务名称（spring）
	
	public static final String PROP_ACTIVE_MODULE="module.active";//超级管理员启用配置
	public static final String PROP_SUPER_PWD="module.pwd";//超级管理员密码配置
	
	public static final String PROP_TRACE_ERROR="trace.log";//输出异常轨迹
	public static final String PROP_LOGIN_RANDCODE="login.randCode";//校验码启用配置
	
	/************************* end   配置项名称*************************/
	
	/*系统角色*/
	public static final String ROLE_MODULE = "ROLE_MODULE";//代码开发人员
	
	public static final String MENU_ALLOW = "1";//允许访问菜单
	public static final String MENU_NOT_ALLOW = "0";//不允许访问菜单
	
	/******************************* URL后缀 *******************************/
	public static final String PAGE_URL_POSTFIX = "html";
	public static final String DATA_URL_POSTFIX = "json";
	
	public static final String PROPERTY_VALUE_NULL="PROPERTY_VALUE_NULL";
	
	/******************************* 流程相关 *******************************/
	public static final String WORKFLOW_STATUS_START="start";//流程启动状态
	/**
	 * 得到当前环境的根目录
	 * 	首先从环境变量中查找
	 * 	未找到则使用静态变量WEB_ROOT
	 * @return
	 */
	public static String getWebRoot(){
		if(WEB_ROOT!=null){
			return WEB_ROOT;
		}
		
		String path = System.getProperty(getWebRootKey());//从环境变量中查找
		WEB_ROOT = path;
		return WEB_ROOT;
	}
	
	/**
	 * 设置根路径
	 * @param webRoot
	 */
	public static void setWebRoot(String webRoot){
		WEB_ROOT = webRoot;
	}
	
	private static String getWebRootKey(){
		String webRootkey = 
			Config.getInstance().getProperty("webroot.key");
		return StringUtils.isEmpty(webRootkey)
			?DEFAULT_WEB_ROOT_KEY:webRootkey;
	}
}
