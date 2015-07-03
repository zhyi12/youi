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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.youi.framework.core.web.PageScriptFactory;
import org.youi.framework.util.PojoMapper;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午08:30:02</p>
 */
@Controller
@RequestMapping("/scripts/page")
public class ScriptController{
	
	@Autowired
	private PageScriptFactory pageScriptFactory;
	
	@RequestMapping(value="/{pageId}.html")
	public ModelAndView index(
			HttpServletRequest request,
    		HttpServletResponse response,
			@PathVariable(value="pageId") String pageId){
		
		String sessionId = request.getSession().getId();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", pageScriptFactory.getPageScript(sessionId,pageId));
		modelAndView.setViewName("data/json");
		return modelAndView;
	}
	
	/**
	 * 生成测试数据  /scripts/page/genTestPagerRecords.json
	 * @param request
	 * @param response
	 * @param pageId
	 * @return
	 */
	@RequestMapping(value="/genTestPagerRecords.json")
	public ModelAndView genTestPagerRecords(
			HttpServletRequest request,
    		HttpServletResponse response){
		
		ModelAndView modelAndView = new ModelAndView();
		
		String[] props = request.getParameterValues("pager:property");
		String pageSize = request.getParameter("pager:pageSize");
		
		Map<String,Object> result = new HashMap<String,Object> ();
		
		result.put("totalCount", pageSize);
		result.put("records", genPagerRecords(props,pageSize));
		
		modelAndView.addObject("json", PojoMapper.toJson(result, true));
		modelAndView.setViewName("data/json");
		return modelAndView;
	}
	
	/**
	 * 生成测试数据集合
	 * @param props
	 * @param pageSize
	 * @return
	 */
	private List<Map<String,String>> genPagerRecords(String[] props,String pageSize){
		int intPageSize;
		try {
			intPageSize = Integer.parseInt(pageSize);
		} catch (NumberFormatException e) {
			intPageSize = 10;
		}
		
		List<Map<String,String>> records = new ArrayList<Map<String,String>>();
		
		for(int i=0;i<intPageSize;i++){
			records.add(genRecord(props,i));
		}
		return records;
	}

	/**
	 * 生成单条测试数据
	 * @param props
	 * @param i
	 * @return
	 */
	private Map<String, String> genRecord(String[] props, int i) {
		Map<String,String> record = new HashMap<String,String>();
		for(String prop : props ){
			record.put(prop, prop+"_"+i);
		}
		return record;
	}
}
