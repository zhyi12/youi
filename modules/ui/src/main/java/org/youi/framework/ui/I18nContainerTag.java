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
package org.youi.framework.ui;

import org.springframework.web.context.WebApplicationContext;
import org.youi.framework.ui.resource.PageResourceBundleMessageSource;
import org.youi.framework.util.StringUtils;


/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述: </p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午05:43:18</p>
 */
public abstract class I18nContainerTag extends AbstractTag{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4997098752393534448L;
	
	protected PageResourceBundleMessageSource pageResourceBundleMessageSource;
	
	/**
	 * 
	 */
	protected String i18n;//
	
	
	/**
     * 初始化i18n相关
     */
    protected void initI18n(){
    	if(StringUtils.isEmpty(i18n))return;
    	//初始化i18n资源类
    	if(this.getRequestContext()!=null&&pageResourceBundleMessageSource==null){
    		WebApplicationContext context = this.getRequestContext().getWebApplicationContext();
    		pageResourceBundleMessageSource = context.getBean(PageResourceBundleMessageSource.class);
    		pageResourceBundleMessageSource.setResourceLoader(context);
    	}
    }

    public String getPageI18nMessage(String key,String[] args){
    	String message = null;
    	if(StringUtils.isNotEmpty(i18n)){
    		String filename = "classpath:"+i18n.replace(".", "/");
    		message = pageResourceBundleMessageSource.getMessage(filename,key,args,getLocale());
    	}
		return message;
	}
	/**
	 * @return the i18n
	 */
	public String getI18n() {
		return i18n;
	}

	/**
	 * @param i18n the i18n to set
	 */
	public void setI18n(String i18n) {
		this.i18n = i18n;
	}
}
