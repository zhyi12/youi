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
package org.youi.framework.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jan 16, 2010
 */
public interface Dao<T, PK extends Serializable> {

	/**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     * @return List of populated objects
     */
    List<T> getAll();

    /**
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the identifier (primary key) of the object to get
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    T get(PK id);

    /**
     * Checks for existence of an object of type T using the id arg.
     * @param id the id of the entity
     * @return - true if it exists, false if it doesn't
     */
    boolean exists(PK id);
    
    /**
     * 检查属性值是否已经存在
     * @param property 实体属性
     * @param value 待检查的属性值
     * @return - true if it exists, false if it doesn't
     */
    boolean exists(String property, Object value);

    /**
     * Generic method to save an object - handles both update and insert.
     * @param object the object to save
     * @return the persisted object
     */
    T save(T object);

    /**
     * Generic method to delete an object based on class and id
     * @param id the identifier (primary key) of the object to remove
     */
    void remove(PK id);
    
    /**
     * 根据单一属性删除
     * @param property 属性名称
     * @param value 属性值
     */
    void remove(String property,Object value);
    
    /**
     * Gets all records without duplicates.
     * <p>Note that if you use this method, it is imperative that your model
     * classes correctly implement the hashcode/equals methods</p>
     * @return List of populated objects
     */
    List<T> getAllDistinct();
    
    void flush();

    /**
     * Find a list of records by using a named query
     * @param queryName query name of the named query
     * @param queryParams a map of the query names and the values
     * @return a list of the records found
     */
    List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams);
    
    /**
     * 
     * @param propertyA
     * @param propertyB
     * @param term
     * @return
     */
    public List<T> findStartByTermOnAOrB(final String propertyA, final String propertyB,final String term);
    /**
     * 通用查询
     * @param conditions 查询过滤条件
     * @param orders 结果排序条件
     * @return 根据查询条件返回的按排序条件排序的结果集
     */
    public List<T> commonQuery(final Collection<Condition> conditions,
			final Collection<Order> orders);
    /**
	 * 通用分页查询分页
	 * 用于单表条件或者数据量较小的多表关联查询
	 * @param pager
	 * @param conditions
	 * @param orders
	 * @return
	 */
	public PagerRecords findByPager(final Pager pager,//分页条件
					final Collection<Condition> conditions,//查询条件
					final Collection<Order> orders);
	
	/**
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public T getObjectByUniqueProperty(String propertyName,Object value);
	
	/**
	 * @param propertyName
	 * @param value
	 * @param cAttributes
	 * @return
	 */
	public T getObjectByUniqueProperty(final String propertyName, final Object value,
			final String[] cAttributes);
	
	/**
	 * @param id
	 * @param cAttributes
	 * @return
	 */
	public T getInitializeObject(final PK id,final String[] cAttributes);
	
	/**
	 * 更加属性查询列表
	 * @param property
	 * @param value
	 * @return
	 */
	public List<T> getList(String property,String value);
	
	/**
	 * @param properties
	 * @param values
	 * @return
	 */
	public List<T> getList(String[] properties,Object[] values);
	
	/**
	 * @param propertyName 属性
	 * @param newValue 更新值
	 */
	public void update(String propertyName,Object newValue);

	/**
	 * @param propertyName 属性
	 * @param oldValue 更新依据值
	 * @param newValue 更新值
	 */
	public void update(String propertyName,Object oldValue,
			Object newValue);
	
	/**
	 * @param propertyName 属性
	 * @param oldValue 更新依据值
	 * @param oldValueOperator 查询条件 = 或者 like 
	 * @param newValue 更新值
	 */
	public void update(String propertyName,
			Object oldValue,
			String oldValueOperator,
			Object newValue);
	
	/**
	 * 合并mappedBy配置集合
	 * @param oldList
	 * @param newList
	 */
	public void mergeMappedByCollection(T bean,String foreignPropName,Collection<? extends Domain> oldList,Collection<? extends Domain> newList);
}
