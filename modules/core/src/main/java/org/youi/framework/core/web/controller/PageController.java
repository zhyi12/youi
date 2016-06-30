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
package org.youi.framework.core.web.controller;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.web.IPageModel;
import org.youi.framework.core.web.annotation.DataInDesc;
import org.youi.framework.util.RequestUtils;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:页面控制器</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 上午10:03:43</p>
 */

@Controller
@RequestMapping("/page")
public class PageController extends BaseController{
	
	public static final String ATTR_PAGE_PATH = "page:path";
	
	public static final String ATTR_PAGE_PAGEID = "page:pageId";
	/**
	 * 
	 * @param pagePath
	 * @param pageId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/{pagePath}/{pageId}.html")
	public ModelAndView index(
			@PathVariable("pagePath") String  pagePath,
			@PathVariable("pageId") String  pageId,
			HttpServletRequest request,
    		HttpServletResponse response){
		ModelAndView model = new ModelAndView();
		
		//TODO security ApplicationDispatcher
		//公共参数填充
		String path = pagePath.replace(".", "/"); 
		model.setViewName(path+"/"+pageId);
		model.addObject(ATTR_PAGE_PATH,path);
		model.addObject(ATTR_PAGE_PAGEID,pageId);
		
		//页面填充参数
		ModelMap pageModel = this.findPageModelMap(request, pagePath, pageId);
		if(pageModel!=null){
			model.addAllObjects(pageModel);
		}
		return model;
	} 
	
	/**
	 * pageMode
	 * @param request
	 * @param pagePath
	 * @param pageId
	 * @return
	 */
	private ModelMap findPageModelMap(HttpServletRequest request,String pagePath,String pageId){
		ModelMap modelMap = new ModelMap();
		WebApplicationContext context = RequestUtils.getApplicationContext(request);
		
		IPageModel pageAttribute = null;
		try {
			pageAttribute = 
				context.getBean(pagePath, IPageModel.class);
		} catch (BeansException e) {
			if(logger.isDebugEnabled()){
				logger.debug(pagePath+"未找到匹配的IPageModel对象实例！");
			}
			return modelMap;
		}
		
		if(pageAttribute!=null){
			Class<?>[] types = {HttpServletRequest.class,DataIn.class};
			Method method = ReflectionUtils.findMethod(pageAttribute.getClass(), pageId,types );
			if(method!=null){
				Object[] args = {
						request,//mvc数据
						new RequestDataIn<Domain>(request, method.getAnnotation(DataInDesc.class))//页面请求数据对象
				};
				Object resultMap = ReflectionUtils.invokeMethod(method, pageAttribute, args);
				if(resultMap instanceof ModelMap){
					return (ModelMap)resultMap;
				}
			}else{
				if(logger.isDebugEnabled()){
					logger.debug(pageAttribute.getClass()+"，未找到方法："+pageId);
				}
			}
			
		}
		return modelMap;
	}

	@Override
	String getUrlType() {
		return BaseController.URL_TYPE_PAGE;
	}
	
	
}
