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
package org.youi.framework.core.web.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.util.PojoMapper;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Dec 22, 2010
 */
public class DataModelAndView extends BaseModelAndView {
	private final static String MODELANDVIEW_PATH="data/json";
	
	public DataModelAndView(String html){
		super(MODELANDVIEW_PATH);
		Map<String,String> record = new HashMap<String,String> ();
		record.put("html", html);
		output2Json(record);
	}
	
	/**
	 * @param domain
	 */
	public DataModelAndView(Domain domain) {
		super(MODELANDVIEW_PATH);
		output2Json(domain);
	}
	
	/**
	 * @param domain
	 */
	public DataModelAndView(Domain domain,boolean ignoreColl) {
		super(MODELANDVIEW_PATH);
		output2Json(domain);
	}
	
	public DataModelAndView(Domain domain,String[] notIgnoreColls) {
		super(MODELANDVIEW_PATH);
		//通过获取属性加载延迟数据
		try {
			for(String notIgnoreColl:notIgnoreColls){
				Object value = PropertyUtils.getSimpleProperty(domain, notIgnoreColl);
				if(value instanceof Collection){
					((Collection<?>)value).size();
				}
			}
		} catch (Exception e) {
			//
		}
		output2Json(domain);
	}
	
	//agencys

	/**
	 * 输出集合对象为json格式
	 * @param list
	 */
	public DataModelAndView(List<? extends Domain> list) {
		super(MODELANDVIEW_PATH);
		if(list == null){//处理为空list
			list = new ArrayList<Domain>();
		}
		Map<String,Object> result = new HashMap<String,Object> ();
		result.put("totalCount", list.size());
		result.put("records", list);
		
		this.addObject("json",PojoMapper.toJson(result, false));
	}

	/**
	 * @param records
	 */
	public DataModelAndView(Object[] records){
		super(MODELANDVIEW_PATH);
		this.addObject("json",PojoMapper.toJson(records, false));
	}
	/**
	 * 输出分页对象为json格式
	 * @param pagerRecords
	 */
	public DataModelAndView(PagerRecords pagerRecords,String[] ignoreFields) {
		super(MODELANDVIEW_PATH);
		Pager pager = pagerRecords.getPager();
		List<?> records = pagerRecords.getRecords();
		if(pager.getExport()!=null){//
			addObject("pagerRecords",pagerRecords);
			
			this.setViewName("forward:/webexport/"+pager.getExport()+".html");
		}else{
			Map<String,Object> result = new HashMap<String,Object> ();
			result.put("totalCount", pagerRecords.getTotalCount());
			result.put("records", records);
			this.addObject("json",PojoMapper.toJson(result, false));
		}
	}
	
	public DataModelAndView(PagerRecords pagerRecords) {
		this(pagerRecords,null);
	}

	/**
	 * 输出信息对象为json格式
	 * @param message
	 */
	public DataModelAndView(Message message) {
		super(MODELANDVIEW_PATH);
		Map<String,Object> result = new HashMap<String,Object> ();
		result.put("message", message);
		this.addObject("json",PojoMapper.toJson(result, false));
	}

	public DataModelAndView(String[] ids) {
		super(MODELANDVIEW_PATH);
		Map<String,Object> result = new HashMap<String,Object> ();
		result.put("ids", ids);
		this.addObject("json",PojoMapper.toJson(result, false));
	}

	/**
	 * @param record
	 */
	private void output2Json(Object record){
		String jsonStr = "";
		if(record==null)record = "";
		if(Domain.class.isAssignableFrom(record.getClass())){
			jsonStr = PojoMapper.domainToJson((Domain)record);
		}else{
			jsonStr = PojoMapper.toJson(record, false);
		}
		StringBuffer jsonBuf = new StringBuffer();
		jsonBuf.append("{\"record\":").append(jsonStr).append("}");
		this.addObject("json",jsonBuf.toString());
	}
	
}
