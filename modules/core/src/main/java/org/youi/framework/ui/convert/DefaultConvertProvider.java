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
package org.youi.framework.ui.convert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.io.ResourceLoader;
import org.youi.framework.core.convert.IConvert;
import org.youi.framework.util.StringUtils;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午04:05:17</p>
 */
public class DefaultConvertProvider  extends ApplicationObjectSupport implements ConvertProvider{
	
	private String location = "WEB-INF/configs/converts/common";
	
	private ResourceLoader resourceLoader;

	private ConvertResourceBundleMessageSource resourceBundle = null;
	
	@SuppressWarnings("rawtypes")
	private  Map<String, IConvert> converts = Collections.synchronizedMap(new HashMap<String,IConvert>());
	
//	private 
	
	/**
	 * @param resourceLoader the resourceLoader to set
	 */
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	/**
	 * @param resource the resource to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	public IConvert<?> getConvert(String name,Locale locale) {
		if(converts!=null){
			IConvert<?> convert = converts.get(name);
			if(convert!=null){
				return convert;
			}
		}
		if(resourceBundle==null){
			this.loadConvert(name,locale);
		}
		return resourceBundle.getConvert(name);
	}

	/**
	 * 
	 */
	public void loadConvert(String name,Locale locale){
		if(resourceBundle==null){
			resourceBundle = new ConvertResourceBundleMessageSource();
			resourceBundle.setResourceLoader(resourceLoader);
		}
		resourceBundle.loadConverts(location,locale);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void initApplicationContext(ApplicationContext context) throws BeansException {
		super.initApplicationContext(context);
		Map<String, IConvert> converts = BeanFactoryUtils.beansOfTypeIncludingAncestors(context,IConvert.class,true,false);
		for(Map.Entry<String, IConvert> entry:converts.entrySet()){
			String name = StringUtils.findNotEmpty(entry.getValue().getName(),entry.getKey());
			this.converts.put(name, entry.getValue());
		}
	}
}
