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
package org.youi.framework.core.web.data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.youi.framework.core.Constants;
import org.youi.framework.core.web.controller.BaseDataController;
import org.youi.framework.core.web.view.DataModelAndView;
import org.youi.framework.core.web.view.Message;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Dec 28, 2010
 */
@Controller
@RequestMapping("/common")
public class CommonData extends BaseDataController{
	/**
	 * 变更页面布局
	 */
	@RequestMapping(value="/changeDecorator.json")
	public DataModelAndView changeDecorator(
    		HttpServletRequest request,
    		HttpServletResponse response,
    		@RequestParam("decorator") String decorator){
		
		request.getSession().getServletContext().setAttribute(Constants.SESSION_DECORATOR, decorator);
		request.getSession().getServletContext().setAttribute(Constants.SESSION_THEME, decorator);
		
		return new DataModelAndView(new Message(SUCCESS_CODE,""));
	}
	
	/**
	 * 拒绝访问(json),未登录
	 */
	@RequestMapping(value="/accessDenied.json")
	public DataModelAndView accessDenied(
    		HttpServletRequest request,
    		HttpServletResponse response){
		return new DataModelAndView(new Message(ACCESS_DENIED_CODE,"您的会话已经过期!"));
	}
	
	/**
	 * 登录成功
	 */
	@RequestMapping(value="/loginSuccess.json")
	public DataModelAndView loginSuccess(
    		HttpServletRequest request,
    		HttpServletResponse response){
		String principal = request.getParameter("principal");
		return new DataModelAndView(new Message(SUCCESS_CODE,"用户【"+principal+"】登录成功!"));
	}
	
	/**
	 * 登录失败
	 */
	@RequestMapping(value="/loginFailed.json")
	public DataModelAndView loginFailed(
    		HttpServletRequest request,
    		HttpServletResponse response){
		String error = request.getParameter("error");
		
//		String msg = "{\"message\":{\"code\":\"000000\",\"info\":\"info\"}}";
//		try {
//			response.getWriter().append(msg);
//			
//			response.getOutputStream().flush();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		response.setHeader("Content-Type", "application/json");
//		return null;
		return new DataModelAndView(new Message(Constants.ERROR_DEFAULT_CODE,error));
	}
	
}
