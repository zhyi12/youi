package org.youi.framework.core.web.controller;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Collection;

import org.springframework.validation.BindingResult;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;

public interface DataIn<T extends Domain>{
	/**
	 * 获取分页条件
	 * @return
	 */
	public Pager getPager();
	
	/**
	 * 获取查询条件
	 * @param bean
	 * @param result
	 * @return
	 */
	public Collection<Condition> getConditions(Domain bean, 
			BindingResult result);
	
	/**
	 * 获取对象
	 * @param bean
	 * @param result
	 * @return
	 */
	public T getDomain(T bean, 
			BindingResult result);

	/**
	 * 获取排序集合
	 * @return
	 */
	public Collection<Order> getOrders();
	
	//TODO public byte[] getByteProperty();
	
	/**
	 * @param property
	 * @return
	 */
	public byte[] getByteProperty(String property); 
	
	/**
	 * 获得上传文件的输出流
	 * @param property
	 * @return
	 */
	public OutputStream getOutputStreamProperty(String property); 
	

	/**
	 * 获得上传文件的bytes
	 * @param propertyName
	 * @return
	 */
	public String[] getPropertyValues(String propertyName);
	/**
	 *  获得上传文件的输入流
	 * @param propertyName
	 * @return
	 */
	public InputStream getInputStreamProperty(String propertyName);
	
	/**
	 * @param propertyName
	 * @return
	 */
	public InputStreamReader getInputStreamReaderProperty(String propertyName);
	
	/**
	 * @param propertyName
	 * @return
	 */
	public String getPropertyValue(String propertyName);
	
}
