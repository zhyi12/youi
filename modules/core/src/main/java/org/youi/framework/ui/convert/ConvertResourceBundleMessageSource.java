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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午04:39:32</p>
 */
public class ConvertResourceBundleMessageSource extends
		ReloadableResourceBundleMessageSource {

	private static final String CONVERT_PROP_PREFIX = "converts.";
	
	private final Map<String, Convert<String>> 
		cachedConverts = new HashMap<String, Convert<String>>();

	public Convert<String> getConvert(String name){
		return cachedConverts.get(name);
	}
	
	public void loadConverts(String filename,Locale locale){
//		this.clearCache();
		PropertiesHolder propertiesHolder = this.getProperties(filename);
		Properties properties = propertiesHolder.getProperties();
		if(properties!=null){
			processConvert(findConverts(properties),properties,locale);
		}
	}
	
	private Set<String> findConverts(Properties properties){
		Set<String> converts = new HashSet<String>();
		for(Object key:properties.keySet()){
			if(key.toString().startsWith(CONVERT_PROP_PREFIX)){
				converts.add(key.toString().substring(CONVERT_PROP_PREFIX.length()));
			}
		}
		return converts;
	}

	private  void processConvert(Set<String> converts,Properties properties,Locale locale){
		
		for(Object itemKey:properties.keySet()){
			//
			for(String convertName:converts){
				Convert<String> convert = cachedConverts.get(convertName);
				if(convert==null){
					convert = new Convert<String>();
					convert.setName(convertName);
				}
				if(itemKey.toString().startsWith(convertName+".")){
					convert.put(itemKey.toString().substring((convertName+".").length()), 
							properties.getProperty(itemKey.toString()));
				}
				cachedConverts.put(convertName, convert);
			}
		}
	}
}
