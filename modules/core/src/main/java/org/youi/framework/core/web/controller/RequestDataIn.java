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
package org.youi.framework.core.web.controller;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.validation.BindingResult;
import org.youi.framework.core.bind.DomainDataBinder;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.exception.DomainValidatorException;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.web.annotation.DataInDesc;
import org.youi.framework.core.web.annotation.Filter;
import org.youi.framework.util.ConditionUtils;
import org.youi.framework.util.StringUtils;


/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Dec 21, 2010
 */
public class RequestDataIn<T extends Domain> implements DataIn<T>{
	private static final Log logger = LogFactory.getLog(RequestDataIn.class);
	
	public static final String PREFIX_PROPERTY="";//domain对象属性参数前缀
	
	public static final String PREFIX_ARRAY_PROPERTY="list:";//数组类型属性参数前缀
	
	public static final String PREFIX_ORDERBY_DESC="desc:";//排序
	
	public static final String PREFIX_ORDERBY_ASC="asc:";//排序
	
	public static final String PREFIX_OPERATOR="operator:";//自定义条件
	
	private HttpServletRequest webRequest;
	
	private DataInDesc dataInDesc;
	
	private Map<String,String[]> parameterMap;//参数集合
	
	private Map<String,DiskFileItem> uploadFiles;
	
	public RequestDataIn(){
		
	}
	
	public RequestDataIn(HttpServletRequest webRequest, DataInDesc dataInDesc) {
		this.webRequest = webRequest;
		this.dataInDesc = dataInDesc;
		
		uploadFiles = new HashMap<String,DiskFileItem>();
		this.parseParameterMapFromRequest();
	}
	
	/**
	 * 从request中解析参数集合parameterMap
	 * 初始化时执行
	 */
	private void parseParameterMapFromRequest(){
		if(ServletFileUpload.isMultipartContent(webRequest)){
			parameterMap = parseParameterMapFromMultipartRequest();//文件上传类型
		}else{
			parameterMap = webRequest.getParameterMap();
		}
	}
	
	
	/**
	 * 带文件上传的form
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String[]> parseParameterMapFromMultipartRequest() {
		Map<String,String[]> parsedParameterMap = new HashMap<String,String[]>();//新建参数集合
		FileItemFactory factory = new DiskFileItemFactory();
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<DiskFileItem> items;
		String parameterName;// 参数名称
		
		try {
			items = upload.parseRequest(webRequest);// Parse the request
			for(DiskFileItem diskFile:items){
				parameterName = diskFile.getFieldName();
				try {
					Integer.parseInt(parameterName);
					continue;
				} catch (NumberFormatException e) {
					//
				}
				if(parameterName==null)continue;
				if(diskFile.isFormField()){//判断form的field
					
					List<String> values = new ArrayList<String>();
					if(parsedParameterMap.containsKey(parameterName)){//多值参数
						values.addAll(Arrays.asList(parsedParameterMap.get(parameterName)));
					}
					
					String value = diskFile.getString("UTF-8");
					if(StringUtils.isNotEmpty(value)){
						values.add(value);
					}
					
					parsedParameterMap.put(parameterName, values.toArray(new String[values.size()]));
				}else{//文件字段
					uploadFiles.put(parameterName, diskFile);
				}
			}
		} catch (FileUploadException e) {
			logger.error("文件上传出现异常："+e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error("不支持的编码："+e.getMessage());
		} 
		return parsedParameterMap;
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.core.web.controller.DataIn#getConditions(com.gsoft.framework.core.dataobj.Domain, org.springframework.validation.BindingResult)
	 */
	public Collection<Condition> getConditions(Domain bean, BindingResult result) {
		
		Map<String,String> filterMap = annotionedFilterMap();
		
		//从parameterMap中取
		String operatorProperty;
		for(Map.Entry<String, String[]> entry:parameterMap.entrySet()){
			//
			operatorProperty = entry.getKey();
			if(operatorProperty.startsWith(PREFIX_OPERATOR)&&
					!operatorProperty.equals(PREFIX_OPERATOR)){
				String[] values = parameterMap.get(operatorProperty);
				if(values!=null&&values.length>0){
					filterMap.put(operatorProperty.substring(PREFIX_OPERATOR.length()),values[0]);
				}
			}
			operatorProperty = null;
		}
		
		Collection<Condition> conditions = 
			ConditionUtils.getConditions(null, bean, result,filterMap,parameterMap);
		if(dataInDesc==null)return conditions;
		
		return conditions;
	}
	/**
	 * @return
	 */
	private Map<String,String> annotionedFilterMap(){
		Map<String,String> filterMap = new HashMap<String,String>();
		if(dataInDesc==null)return filterMap;
		Filter[] filters = dataInDesc.filters();
		if(filters!=null){
			for(Filter filter:filters){
				filterMap.put(filter.name(), filter.operator());
			}
		}
		return filterMap;
	}
	
	/* (non-Javadoc)
	 * @see org.youi.common.web.controller.DataIn#getDomain(org.youi.common.domain.Domain, org.springframework.validation.BindingResult)
	 */
	@SuppressWarnings({ "rawtypes"})
	public T getDomain(T bean, BindingResult result) {
		
		if(ServletFileUpload.isMultipartContent(webRequest)){
			//如果是文件上传类型请求，重新组织bean对象
			DomainDataBinder dataBinder = new DomainDataBinder(bean);
			PropertyValues pvs = new MutablePropertyValues(parameterMap);
			dataBinder.bind(pvs);
			
			BeanUtils.copyProperties(dataBinder.getTarget(), bean);
		}
		
		//hibernate domain 对象校验
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		
		Set<ConstraintViolation<T>> constraintViolations = 
				factory.getValidator().validate(bean, bean.getClass());
		
		//抛出对象校验异常
		if(constraintViolations!=null && constraintViolations.size()>0){
			List<String> violations = new ArrayList<String>();
			for(ConstraintViolation constraintViolation:constraintViolations){
				violations.add(constraintViolation.getMessage());
			}
			throw new DomainValidatorException(violations);
		}
		//设置数据权限相关的条件
		if(dataInDesc!=null){
			//TODO
		}
		
		return bean;
	}

	/* (non-Javadoc)
	 * @see org.youi.common.web.controller.DataIn#getPager()
	 */
	public Pager getPager() {
		String pageSize = getParameterValue("pager:pageSize");
		String pageIndex = getParameterValue("pager:pageIndex");
		String pageType = getParameterValue("pager:pageType");
		String pageExport = getParameterValue("pager:export");
		String[] pagerProperties = getParameterValues("pager:property");
		Pager pager = new Pager(pageSize,pageIndex,pageType);
		
		pager.setPagerProperties(pagerProperties);
		if(pageExport!=null
				&&(pageExport.equals(Pager.EXPORT_TYPE_XLS)
						||pageExport.equals(Pager.EXPORT_TYPE_PDF)
						||pageExport.equals(Pager.EXPORT_TYPE_PRINT))){
			pager.setExport(pageExport);//支持导出为xls和pdf
			
			String[] pageHeaders = getParameterValues("pager:header");
			pager.setExportHeaders(pageHeaders);
			
			pager.setExportProperties(getParameterValues("pager:property"));
		}
		return pager;
	}
	/* 
	 * 查询类型的排序集合
	 * @see org.youi.common.web.data.DataIn#getOrders()
	 */
	public Collection<Order> getOrders() {
		Collection<Order> orders = new ArrayList<Order>();
		String[] orderBys  = getParameterValues("orderBy");
		
		if(orderBys!=null){
			for(String propertyName:orderBys){
				if(propertyName.startsWith(PREFIX_ORDERBY_ASC)){//正序
					orders.add(ConditionUtils.getOrder(propertyName.substring(PREFIX_ORDERBY_ASC.length()), true));
				}else if(propertyName.startsWith(PREFIX_ORDERBY_DESC)){//逆序
					orders.add(ConditionUtils.getOrder(propertyName.substring(PREFIX_ORDERBY_DESC.length()), false));
				}
			}
		}
		return orders;
	}
	
	/**
	 * @param name
	 * @return
	 */
	public String getParameterValue(String name){
		String[] values = getParameterValues(name);
		return values!=null&&values.length>0?values[0]:null;
	}
	
	/**
	 * @param name
	 * @return
	 */
	public String[] getParameterValues(String name){
		return parameterMap.get(name);
	}

	/* (non-Javadoc)
	 * @see org.youi.common.web.data.DataIn#getPropertyValues(java.lang.String)
	 */
	public String[] getPropertyValues(String propertyName) {
		return getParameterValues(PREFIX_PROPERTY+propertyName);
	}

	/* (non-Javadoc)
	 * @see org.youi.common.web.data.DataIn#getPropertyValue(java.lang.String)
	 */
	public String getPropertyValue(String propertyName) {
		String[] values = getPropertyValues(propertyName);
		return values!=null&&values.length>0?values[0].trim():null;
	}

	public byte[] getByteProperty(String propertyName) {
		if(uploadFiles!=null&&uploadFiles.containsKey(propertyName)){
			return uploadFiles.get(propertyName).get();
		}
		return new byte[]{};
	}

	public OutputStream getOutputStreamProperty(String propertyName) {
		if(uploadFiles!=null&&uploadFiles.containsKey(propertyName)){
			try {
				return uploadFiles.get(propertyName).getOutputStream();
			} catch (IOException e) {
				logger.info(""+e.getMessage());
			}
		}
		return null;
	}
	
	public InputStream getInputStreamProperty(String propertyName) {
		if(uploadFiles!=null&&uploadFiles.containsKey(propertyName)){
			try {
				return uploadFiles.get(propertyName).getInputStream();
			} catch (IOException e) {
				logger.info(""+e.getMessage());
			}
		}
		return null;
	}
	
	public InputStreamReader getInputStreamReaderProperty(String propertyName){
		if(uploadFiles!=null&&uploadFiles.containsKey(propertyName)){
			try {
				DiskFileItem item = uploadFiles.get(propertyName);
				InputStream in = item.getInputStream();
				String charSet = item.getCharSet();
				if(charSet==null){
//					Charset dCharSet = detector.detectCodepage(in,new Long(item.getSize()).intValue());
//					if(dCharSet!=null&&!"void".equals(dCharSet.name())){
//						charSet = dCharSet.name();
//					}else{
						charSet = "GBK";//本地文件编码
//					}
				}
				logger.info("上传文件编码:"+charSet);
				return new InputStreamReader(in,charSet);
			} catch (IOException e) {
				logger.warn("文件上传警告："+e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				logger.warn("文件上传警告："+e.getMessage());
			}
		}
		return null;
	}
}
