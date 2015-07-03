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
package org.youi.framework.core.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Id;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.youi.framework.core.dao.Dao;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.exception.YouiException;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.core.orm.annotation.IdSort;
import org.youi.framework.core.orm.hibernate.HibernateCondition;
import org.youi.framework.core.orm.hibernate.HibernateOrder;
import org.youi.framework.util.CollectionUtils;
import org.youi.framework.util.ReflectionUtils;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jan 16, 2010
 */
public abstract class BaseDaoHibernate<T, PK extends Serializable> extends HibernateDaoSupport implements Dao<T, PK> {
	protected final Log logger = LogFactory.getLog(getClass());
	/*
	 * 在约定的初始化方法中注入设置sessionFactory
	 */
	@Autowired
	public void init(SessionFactory sessionFactory){
		this.setSessionFactory(sessionFactory);
	}
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> getAll() {
        return (List<T>) super.getHibernateTemplate().loadAll(this.getModelClazz());
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"unchecked","rawtypes"})
    @Override
    public List<T> getAllDistinct() {
        Collection result = new LinkedHashSet(getAll());
        return new ArrayList(result);
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public T get(PK id) {
        T entity = (T) super.getHibernateTemplate().get(this.getModelClazz(), id);
        
        if (entity == null) {
        	logger.warn("Uh oh, '" + this.getModelClazz() + "' object with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(this.getModelClazz(), id);
        }
        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public boolean exists(PK id) {
        T entity = (T) super.getHibernateTemplate().get(this.getModelClazz(), id);
        return entity != null;
    }
    
    @Override
    public boolean exists(String propertyName,Object value){
    	T entity = (T)this.getObjectByUniqueProperty(propertyName, value);
    	return entity != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T save(T object) {
    	//
        return (T) super.getHibernateTemplate().merge(object);
    }
    
    @Override
    public void save(List<T> list){
    	getHibernateTemplate().saveOrUpdateAll(list);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(PK id) {
        super.getHibernateTemplate().delete(this.get(id));
    }
    
    @SuppressWarnings({"unchecked","rawtypes"})
    @Override
	public void remove(final String property,final Object value){
    	super.getHibernateTemplate().execute(new HibernateCallback(){
    		
    		@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery("delete from "+getModelClazz().getName()
				+ " as model where model." + property + "= ?");
				query.setParameter(0, value);
				return query.executeUpdate();
			}
    		
    	});
    }
    
    /**
     * @param propName
     */
    @Override
    public void mergeMappedByCollection(T bean,String foreignPropName,Collection<? extends Domain> oldList,Collection<? extends Domain> newList){
    	if(oldList!=null){
    		for(Object old:oldList){
    			if(newList==null||!CollectionUtils.containsInstance(newList, old)){
    				this.getHibernateTemplate().delete(old);
    			}
    		}
    	}
    	//设置关联对象
    	if(newList!=null){
    		for(Domain newBean:newList){
        		org.youi.framework.util.PropertyUtils.setSimplePropertyValue(newBean, foreignPropName, bean);
        	}
    	}
    }
    
    @Override
    public void flush(){
    	getHibernateTemplate().flush();
    }
   /** 
    * {@inheritDoc}
    */
   @SuppressWarnings({"unchecked"})
   @Override
   public List<T> findByNamedQuery(
       String queryName, 
       Map<String, Object> queryParams) {
       String []params = new String[queryParams.size()];
       Object []values = new Object[queryParams.size()];
       int index = 0;
       Iterator<String> i = queryParams.keySet().iterator();
       while (i.hasNext()) {
           String key = i.next();
           params[index] = key;
           values[index++] = queryParams.get(key);
       }
       return getHibernateTemplate().findByNamedQueryAndNamedParam(
           queryName, 
           params, 
           values);
   }
   
	@SuppressWarnings("unchecked")
	 @Override
	public List<T> findStartByTermOnAOrB(final String propertyA, final String propertyB,final String term) {
		return (List<T>)getHibernateTemplate().execute(new HibernateCallback<List<T>>() {
			@Override
			public List<T> doInHibernate(Session session)
					throws HibernateException, SQLException {
				String queryString = "from " + getModelClazz().getName()
				+ " as model where model." + propertyA + " like ? or model."+propertyB+" like ? order by model."+propertyA;
				Query queryObject = getSession().createQuery(queryString);
				
				queryObject.setParameter(0, term+"%");
				queryObject.setParameter(1, "%"+term+"%");
				
				List<T> list = queryObject.list();
				
				return list;
			}
		});
	}
	/**
	 * 根据唯一属性查询对象（不进行延迟加载）
	 * @param propertyName
	 * @param value
	 * @return
	 */
	 @Override
	public T getObjectByUniqueProperty(String propertyName,Object value) {
		return getObjectByUniqueProperty(propertyName,value,null);
	}
	/**
	 * 根据唯一属性查询对象，根据指定的加载集合进行延迟加载
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @param initializeCollections 延迟加载对象的集合属性名称数组
	 * @return
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
	public T getObjectByUniqueProperty(final String propertyName, final Object value,
			final String[] cAttributes) {
		return (T)getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String queryString = "from " + getModelClazz().getName()
				+ " as model where model." + propertyName + "= ?";
				Query queryObject = getSession().createQuery(queryString);
				queryObject.setParameter(0, value);
				List<?> list = queryObject.list();
				logger.info("getObjectByUniqueProperty:"+queryString);
				
				if (list == null || list.isEmpty()) {
					logger.warn("根据属性" + propertyName + "未能查找到对象！");
					return null;
				}
				Object obj = list.get(0);
				initializeObjectCollections(obj,cAttributes);
				return obj;
			}
		});
	}
	/**
	 * 为单对象加载延迟加载数据
	 * 
	 * @param id
	 * @param clazz
	 * @param initializeCollections
	 * @return
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
	public T getInitializeObject(final PK id,final String[] cAttributes) {
		return (T)this.getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Object initializeObject = null;
				initializeObject = session.get(getModelClazz(),id);
				initializeObjectCollections(initializeObject,cAttributes);
				return initializeObject;
			}
		});
	}
	/**
	 * 
	 * @param initializeObject 需要延迟加载的对象
	 * @param initializeCollections 延迟加载对象的集合属性名称数组
	 */
	private void initializeObjectCollections(Object initializeObject,
			String[] cAttributes){
		if(cAttributes!=null){
			for (String initializeCollection : cAttributes) {
				try{	
					initializeObjectCollection(initializeObject,initializeCollection);
				} catch (Exception e) {
					logger.warn("属性【" + initializeCollection + "】延迟加载失败："+ e.getMessage());
				}
			}
		}
	}
	/**
	 * 强制加载对象的延迟加载集合
	 * 
	 * @param initializeObject
	 * @param initializeCollection
	 * @throws YouiException
	 */
	private void initializeObjectCollection(Object initializeObject,
			String initializeCollection) {
		Object obj = null;
		if (initializeCollection == null || "".equals(initializeCollection))
			return;
		try {
			obj = PropertyUtils.getProperty(initializeObject, initializeCollection);
			Hibernate.initialize(obj);
		}catch (IllegalAccessException e) {
			logger.warn(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.warn(e.getMessage());
		} catch (NoSuchMethodException e) {
			logger.warn(e.getMessage());
		}catch (HibernateException e) {
			logger.warn(e.getMessage());
		} 
	}
	
	@SuppressWarnings({"unchecked"})
	@Override
	public List<T> getList(String property,String value){
		String queryString = "from " + getModelClazz().getName()
		+ " as model where model." + property + "= ?";
		return this.getHibernateTemplate().find(queryString, value);
	}
	
	@SuppressWarnings({"unchecked"})
	@Override
	public List<T> getList(String[] properties,Object[] values){
		if(properties==null||values==null||properties.length!=values.length){
			return null;
		}
		StringBuilder sqlBuf = new StringBuilder();
		sqlBuf.append("from " + getModelClazz().getName()).append(" as model where 1=1 ");
		
		for(String property:properties){
			sqlBuf.append(" and model."+property).append("=? ");
		}
		
		return this.getHibernateTemplate().find(sqlBuf.toString(), values);
	}
	/**
	 * 设置所有数据的属性数据值为 newValue
	 * @param propertyName
	 * @param newValue
	 */
	@Override
	public void update(final String propertyName,final Object newValue){
		update(propertyName,null,null,newValue);
	}
	
	/**
	 *  设置值为oldValue的属性数据值为 newValue
	 * @param propertyName
	 * @param oldValue
	 * @param newValue
	 */
	@Override
	public void update(final String propertyName,final Object oldValue,
			final Object newValue){
		update(propertyName,oldValue,"=",newValue);
	}
	
	/**
	 * @param propertyName
	 * @param oldValue
	 * @param oldValueOperator
	 * @param newValue
	 */
	@Override
	public void update(final String propertyName,final Object oldValue,final String oldValueOperator,
			final Object newValue){
		getHibernateTemplate().execute(new HibernateCallback<Object>(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				StringBuilder queryBuf = new StringBuilder();
				queryBuf.append("update ").append(getModelClazz().getName()).append(" as model ")
						.append(" set model.").append(propertyName).append("=:newValue");
				if(oldValue!=null){
					queryBuf.append(" where model.").append(propertyName)
						.append(" ").append(oldValueOperator).append(" :oldValue");
				}
				
				Query query = getSession().createQuery(queryBuf.toString())
					.setParameter("newValue", newValue);
				if(oldValue!=null){
					query.setParameter("oldValue", oldValue);
				}
				return query.executeUpdate();
			}
		});
	}
	/**
	 * 通用查询
	 * 
	 * @param conditions
	 *            条件集合
	 * @param aliasMap
	 *            外键条件
	 * @param orders
	 *            排序字段
	 * @return
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
	public List<T> commonQuery(final Collection<Condition> conditions,
			final Collection<Order> orders) {
		final DetachedCriteria detachedCriteria = this
				.getDetachedCriteria(getModelClazz());
		HibernateCallback hibernateCallback = new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = detachedCriteria
						.getExecutableCriteria(session);// 从session中获得criteria对象
				Object[] result = generateQueryExpression(conditions, criteria);// 生成查询条件
				criteria = (Criteria)result[0];
				//处理排序条件
				criteria = generateOrderExpression(orders, result);// 生成排序
				return criteria.list();
			}
		};
		
		List list = (List) getHibernateTemplate().execute(hibernateCallback);
		return this.getResultList(list);
	}
	/**
	 * 通用分页查询分页
	 * 用于单表条件或者数据量较小的多表关联查询
	 * @param pager
	 * @param conditions
	 * @param orders
	 * @return
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
	public PagerRecords findByPager(final Pager pager,//分页条件
					final Collection<Condition> conditions,//查询条件
					final Collection<Order> orders){//排序条件
		//分页查询
		final DetachedCriteria detachedCriteria = getDetachedCriteria(getModelClazz());
		HibernateCallback hibernateCallback = new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);// 从session中获得criteria对象
				int pageType = pager.getPageType(),
					totalCount = 0;
				List list = new ArrayList();
				//处理查询条件
				Object[] result = generateQueryExpression(conditions, criteria);// 生成查询条件
				criteria = (Criteria)result[0];
				
				//查询记录总数
				if(pageType==Pager.QUERY_TYPE_ALL||pageType==Pager.QUERY_TYPE_COUNT){
					try {
						totalCount = ((Integer) criteria.setProjection(
								Projections.rowCount()).uniqueResult()).intValue();// 计算总数
					} catch (RuntimeException e) {
						logger.error(e.getMessage());
					}
					criteria.setProjection(null);
					pager.setCounts(totalCount);//设置记录总数
				}
				
				//查询结果集
				if(pageType==Pager.QUERY_TYPE_ALL||pageType==Pager.QUERY_TYPE_LIST){
					criteria = generateOrderExpression(orders, result);// 生成排序
					if(pager.getPageSize()!=-1)
						criteria = criteria.setFirstResult(pager.getStartIndex()).setMaxResults(
								pager.getPageSize());// 设置分页参数
					list = criteria.list();
				}
				//处理排序条件
				
				return getCriteriaResult(list,totalCount,pager);
			}
		};
		return (PagerRecords)getHibernateTemplate().execute(hibernateCallback);
	}
	/*
	 * 获得分页查询的结果
	 */
	@SuppressWarnings({"rawtypes"})
	private PagerRecords getCriteriaResult(List list, int totalCount, Pager pager) {
		List items = getResultList(list);
		PagerRecords ps = new PagerRecords(items, totalCount);// 分页输出对象
		ps.setPager(pager);//设置当前数据的分页信息
		return ps;
	}
	/**
	 * 从list集中取出需要的结果集
	 * @param list
	 * @return
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	private List getResultList(List list){
		List items = new ArrayList();
		boolean isObjectArray = false;// 用于判断返回集合中元素的类型是否为数组
		if (!list.isEmpty() && list.get(0).getClass().isArray()) {
			isObjectArray = true;// 判断返回集合中元素的类型是否为数组
		}
		for (int i = 0; i < list.size(); i++) {
			Object[] objectArray;
			if (isObjectArray == true) {
				objectArray = (Object[]) list.get(i);
			} else {
				objectArray = new Object[] { list.get(i) };
			}
			items.add(objectArray[objectArray.length - 1]);// 把需要的对象加入到输出集合中
		}
		return items;
	}
	/**
	 * 生成查询表达式
	 * @param conditions
	 * @param criteria
	 * @return
	 */
	private Object[] generateQueryExpression(
			Collection<Condition> conditions, Criteria criteria) {
		Object[] result = new Object[2];
		
		if (conditions == null || conditions.size() == 0){
			result[0] = criteria;
			return result;
		}
		String alias;
		Set<String> aliasSet = new HashSet<String>();//记录多对多关联名
		for(Condition condition:conditions){
			if(condition==null)continue;
			Criterion conditionCriterion = (Criterion) ((HibernateCondition)condition).generateExpression();
			if (conditionCriterion != null){
				alias = getConditionAlias(condition);
				if(alias!=null&&!aliasSet.contains(alias)){//关联查询
					criteria.createAlias(alias,alias);
					aliasSet.add(alias);
				}
				criteria.add(conditionCriterion);
				alias = null;
			}
		}
		result[0] = criteria;
		result[1] = aliasSet;
		return result;
	}
	
	/**
	 * 获取多对多属性alias名称
	 * @param condition
	 * @return
	 */
	private String getConditionAlias(Condition condition){
		String propertyName = condition.getProperty();
		String alias = null;
		if(propertyName.indexOf(".")!=-1){//处理多对多关系
			alias = propertyName.split("\\.")[0];
		}
		return alias;
	}
	/**
	 * @param orders
	 *            排序对象数组<Order>
	 * @param criteria
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	private Criteria generateOrderExpression(Collection<Order> orders,
			Object[] result) throws HibernateException {
		Criteria criteria = (Criteria)result[0];
		if (orders == null||orders.isEmpty()){
			orders = new ArrayList<Order>();
			Map<String,Boolean> idOrders = new HashMap<String,Boolean>();
			//添加默认的ID排序
			List<Method> idGetterMethods = 
				ReflectionUtils.annotationedGetterMethod(getModelClazz(), Id.class);
			//
			List<Field> idAnnotationedFields 
				= ReflectionUtils.annotationedField(getModelClazz(), Id.class);
			
			IdSort idSort = null; 
			for(Method method:idGetterMethods){
				idSort = idGetterMethods.get(0).getAnnotation(IdSort.class);
				String methodName = method.getName();
				idOrders.put(methodName, new Boolean(idSort!=null?idSort.desc():true));
				idSort = null;
			}
			
			for(Field field:idAnnotationedFields){
				idSort = field.getAnnotation(IdSort.class);
				idOrders.put(field.getName(), new Boolean(idSort!=null?idSort.desc():true));
				idSort = null;
			}
			
			if(!idOrders.isEmpty()){
				Set<String> idProperties = idOrders.keySet();
				for(String idProperty:idProperties){
					orders.add(new HibernateOrder(idProperty,idOrders.get(idProperty)));
				}
			}
		}
		Set<String> aliasSet = new HashSet<String>();//记录多对多关联名
		if(result[1]!=null){
			aliasSet = (Set)result[1];
		}
		String propertyName,
		   	   alias;
		for(Order order:orders){
			propertyName = order.getProperty();
			if(propertyName.indexOf(".")!=-1){//处理多对多关系
				alias = propertyName.split("\\.")[0];
				if(!aliasSet.contains(alias)){//关联查询
					criteria = criteria.createAlias(alias,alias);
					aliasSet.add(alias);
				}
			}
			criteria.addOrder((org.hibernate.criterion.Order)order);
		}
		return criteria;
	}

	protected DetachedCriteria getDetachedCriteria(Class<?> clazz) {
		return DetachedCriteria.forClass(clazz);
	}

	protected String getInPreSql(int length){
		StringBuilder buf  = new StringBuilder();
		for(int i=0;i<length;i++){
			if(i>0){
				buf.append(",");
			}
			buf.append("?");
		}
		return buf.toString();
	}
	/**
	 * 获得对应实体模型的类
	 * 
	 * @return
	 */
	public abstract Class<?> getModelClazz();
}
