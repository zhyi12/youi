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

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.youi.framework.core.i18n.I18nKey;

/**
 * @功能描述
 * @作者 zhyi_12
 * @版本 1.0.0
 * @创建时间 Dec 21, 2010
 */
public abstract class BaseController {
	public static final String URL_TYPE_PAGE = "page";
	public static final String URL_TYPE_DATA = "data";

	protected final Log logger = LogFactory.getLog(getClass());
	
	protected MessageSource messageSource;//国际化资源
	
	
	@Autowired
	public void init(MessageSource messageSource){
		this.messageSource = messageSource;
		//URL注册
		//registerControllerUrl();
	}
	/**
	 * 获取URL类型
	 * 
	 * @param method
	 * @return
	 */
	abstract String getUrlType();
	
	
	
	/**
	 * 参见 springmvc 中 messageSource的配置
	 * <bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
        <property name="basename" value="messages"/>
     * </bean>
	 * 获取国际化文本
	 * @param name
	 * @return
	 */
	protected String getI18Message(String name,String[] args){
		try {
			Locale local = Locale.getDefault();//
			return messageSource.getMessage(name, args,local );
		} catch (NoSuchMessageException e) {
			logger.warn("messages文件中【"+name+"】没有找到！");
			return null;
		}
	}
	protected String getI18Message(String name){
		return getI18Message(name,null);
	}
	
	protected String getI18Message(I18nKey i18nKey){
		return getI18Message(i18nKey.getKey(),i18nKey.getArgs());
	}
}
