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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.core.io.ResourceLoader;
import org.youi.framework.core.convert.IConvert;
import org.youi.framework.core.convert.IConvertProviderFactory;


/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午04:08:16</p>
 */
public class ConvertProviderFactory implements IConvertProviderFactory{
	
	List<ConvertProvider> providers;
	
	private ResourceLoader resourceLoader;

	
	/**
	 * @param providers the providers to set
	 */
	public void setProviders(List<ConvertProvider> providers) {
		this.providers = providers;
	}
	
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public IConvert getConvert(String name,Locale locale){
		IConvert convert = null;
		if(providers==null){
			providers = new ArrayList<ConvertProvider>();
			providers.add(new DefaultConvertProvider());
		}
		
		for(ConvertProvider provider:providers){
			if(provider instanceof DefaultConvertProvider){
				((DefaultConvertProvider)provider).setResourceLoader(resourceLoader);
			}
			convert = provider.getConvert(name,locale);
			if(convert!=null){
				return convert;//
			}
		}
		return null;
	}

}
