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
package org.youi.framework.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.youi.framework.core.dao.Dao;
import org.youi.framework.core.dataobj.Record;
import org.youi.framework.core.dataobj.TermProperties;
import org.youi.framework.core.service.BaseManager;
import org.youi.framework.util.PropertyUtils;


/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jan 20, 2010
 */
public class BaseManagerImpl implements BaseManager{
	protected final Log logger = LogFactory.getLog(getClass());//日记
	
	
	/**
	 * 
	 * @param dao
	 * @param valueProperty
	 * @param labelProperty
	 * @param term
	 * @return
	 */
	protected Record[] findRecordsByTerm(@SuppressWarnings("rawtypes") Dao dao,
			String valueProperty,String labelProperty,
			String term){
		return this.findRecordsByTerm(dao, new TermProperties(valueProperty,labelProperty),
				valueProperty, labelProperty, valueProperty, term,true);
	}
	
	/**
	 * 根据termProperties参数配置的两个对象属性模糊查询数据
	 * 数据使用record（Map）对象返回，固定
	 * @param dao
	 * @param valueProperty
	 * @param nameProperty
	 * @param term
	 * @return
	 */
	protected Record[] findRecordsByTerm(@SuppressWarnings("rawtypes") Dao dao,
			TermProperties termProperties,
			String valueProperty,String labelProperty,String codeProperty,
			String term){
		return this.findRecordsByTerm(dao, termProperties,
				valueProperty, labelProperty,codeProperty, term,true);
	}
	
	/**
	 * @param dao 实体持久对象
	 * @param termProperties 查询条件属性
	 * @param valueProperty 返回值属性
	 * @param labelProperty 返回显示属性
	 * @param term 查询值
	 * @param mixed 是否混合显示
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Record[] findRecordsByTerm(Dao dao,
			TermProperties termProperties,
			String valueProperty,
			String labelProperty,
			String codeProperty,
			String term,
			boolean mixed){
		List<Object> queryResults = dao.findStartByTermOnAOrB(termProperties.getCodeProperty(), 
				termProperties.getTextProperty(), term);
		
		List<Record> records = new ArrayList<Record>();
		for(Object queryResult:queryResults){
			records.add(queryResult2Record(queryResult,valueProperty,labelProperty,codeProperty,mixed));
		}
		return records.toArray(new Record[records.size()]);
	}

	/**
	 * @param queryResult
	 * @param valueProperty
	 * @param nameProperty
	 * @return
	 */
	protected Record queryResult2Record(Object queryResult,
			String valueProperty,
			String labelProperty,
			String codeProperty,
			boolean mixed) {
		Record record = new Record();
		
		Object label = PropertyUtils.getPropertyValue(queryResult, labelProperty),
			   value = PropertyUtils.getPropertyValue(queryResult, valueProperty),
			   code = PropertyUtils.getPropertyValue(queryResult, codeProperty);
		
		if(label!=null&&value!=null){
			record.put("label", mixed&&!label.equals(value)?(value+" "+label):label);
			record.put("value", value);//用于autocomplete组件的value属性值
			record.put("propertyValue", code);
		}
		
		return record;
	}
}
