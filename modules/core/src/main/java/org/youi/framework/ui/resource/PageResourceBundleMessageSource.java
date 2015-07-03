/*
 * YOUI框架
 * Copyright 2012 the original author or authors.
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
package org.youi.framework.ui.resource;

import java.util.Locale;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午06:04:33</p>
 */
public class PageResourceBundleMessageSource extends
		ReloadableResourceBundleMessageSource {
	
	/**
	 * @param filename
	 * @param key
	 * @return
	 */
	public String getMessage(String filename,String key,String[] args,Locale locale){
		String message = this.getProperties(filename).getProperty(key);
		if(message!=null){
			return this.formatMessage(message, args, locale);
		}
		return null;
	}
}
