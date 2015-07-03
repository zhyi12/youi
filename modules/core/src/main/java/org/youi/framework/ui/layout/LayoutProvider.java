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
package org.youi.framework.ui.layout;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.youi.framework.util.StringUtils;


/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Dec 23, 2010
 */
public class LayoutProvider {
	private static final Log logger = LogFactory.getLog(LayoutProvider.class);
	
	/**
	 * 
	 */
	private Map<String, String> decorators;//
	private static HashMap<String, AbstractLayout> layouts;//
	
	public LayoutProvider(){
		layouts = new HashMap<String,AbstractLayout>();
	}
	//AbstractLayout
	
	/**
	 * @param resourceLoader
	 * @param decorator
	 * @return
	 */
	private AbstractLayout getLayout(ResourceLoader resourceLoader,String decorator){
		AbstractLayout layout = layouts.get(decorator);
		if(layout==null){
			layout = createLayout(resourceLoader,decorator);
			layouts.put(decorator, layout);
		}
		return layout;
	}
	
	
	/**
	 * 
	 * @param resourceLoader
	 * @param decorator
	 * @return
	 */
	@SuppressWarnings({ "rawtypes"})
	private AbstractLayout createLayout(ResourceLoader resourceLoader,
			String decorator) {
		if(StringUtils.isEmpty(decorator)){
			decorator = "youi";
		}
		
		String className = this.decorators.get(decorator);
		Document document = null;
		String path = "decorators/"+decorator+"/layout.xml";
		
		Resource resource = resourceLoader.getResource(path);
		Assert.notNull(resource, "decorator resource is null!");
		
		SAXReader saxReader = new SAXReader();
		try {
			document = saxReader.read(resource.getInputStream());
		} catch (DocumentException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		AbstractLayout layout = null;
		if(document!=null){
			Class[] parameterTypes = {String.class,Document.class};
			Object[] args ={decorator,document};
			Class<?> clazz = null;
			try {
				clazz = ClassUtils.forName(className, resourceLoader.getClassLoader());
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage());
			} catch (LinkageError e) {
				logger.error(e.getMessage());
			}
			Constructor<?> 
				constructor = ClassUtils.getConstructorIfAvailable(clazz, parameterTypes);
			layout = (AbstractLayout) BeanUtils.instantiateClass(constructor, args);
		}
		return layout;
	}
	
	public Map<String, String> getDecorators() {
		return decorators;
	}

	public void setDecorators(Map<String, String> decorators) {
		this.decorators = decorators;
	}

//	
	public String getStartHtml(PageContext pageContext,ResourceLoader resourceLoader,String decorator) {
		AbstractLayout layout = this.getLayout(resourceLoader, decorator);
		if(layout==null){
			return "";
		}
		return layout.getStartHtml(pageContext);
	}
//	
//	
	public String getEndHtml(PageContext pageContext,ResourceLoader resourceLoader,String decorator) {
		AbstractLayout layout = this.getLayout(resourceLoader, decorator);
		if(layout==null){
			return "";
		}
		return layout.getEndHtml(pageContext);
	}
}
