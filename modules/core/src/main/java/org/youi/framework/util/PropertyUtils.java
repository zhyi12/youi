/*

* @(#)PropertyUtils.java  1.0.0 下午04:08:36

* Copyright 2013 gicom, Inc. All rights reserved.

*/
package org.youi.framework.util;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @功能描述 
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jan 23, 2010
 */
public class PropertyUtils {

	private final static Log logger = LogFactory.getLog(PropertyUtils.class);
	
	private static final IntParser INT_PARSER = new IntParser();

	private static final LongParser LONG_PARSER = new LongParser();

	private static final FloatParser FLOAT_PARSER = new FloatParser();

	private static final DoubleParser DOUBLE_PARSER = new DoubleParser();

	private static final BooleanParser BOOLEAN_PARSER = new BooleanParser();

	
	/**
	 * @param bean
	 * @param property
	 * @param value
	 */
	public static void setSimplePropertyValue(Object bean,String property,Object value){
		Object convertValue = value;
		Field field = FieldUtils.getField(bean.getClass(), property);
		if(field!=null){
			Class<?> fieldType =field.getType();
			if(fieldType!=String.class&&(value==null||value instanceof String)){
				convertValue = getConvertValue(value,fieldType);
			}
			
			try {
				org.apache.commons.beanutils.PropertyUtils.setSimpleProperty(bean, property, convertValue);
			} catch (IllegalAccessException e) {
				logger.warn(e.getMessage());
			} catch (InvocationTargetException e) {
				logger.warn(e.getMessage());
			} catch (NoSuchMethodException e) {
				logger.warn(e.getMessage());
			}
		}
	}
	
	private static Object getConvertValue(Object value, Class<?> type) {
		String strValue = null;
		if(value!=null){
			strValue = value.toString();
		}
		if(type==int.class){
			return StringUtils.isEmpty(strValue)?0:INT_PARSER.doParse(strValue);
		}else if(type==long.class){
			return StringUtils.isEmpty(strValue)?0l:LONG_PARSER.doParse(strValue);
		}else if(type==float.class){
			return StringUtils.isEmpty(strValue)?0f:FLOAT_PARSER.doParse(strValue);
		}else if(type==double.class){
			return StringUtils.isEmpty(strValue)?0d:DOUBLE_PARSER.doParse(strValue);
		}else if(type==boolean.class){
			return StringUtils.isEmpty(strValue)?false:BOOLEAN_PARSER.doParse(strValue);
		}
		return null;
	}
	/**
	 * 设置属性值
	 * @param bean
	 * @param property
	 * @param value
	 */
	public static void setPropertyValue(Object bean,String property,Object value){
		String[] properties = property.split("\\.");
		if(properties.length>1){
			Object objBean = bean;
			Object valueBean = null;
			for(int i=0;i<properties.length;i++){
				if(i<properties.length-1){
					valueBean = createObject(objBean,properties[i]);
					if(valueBean==null)break;
					setPropertyValue(objBean,properties[i], valueBean);
					objBean = valueBean;
				}else{
					//最后一级
					setSimplePropertyValue(objBean,properties[i], value);
				}
			}
		}else{
			setSimplePropertyValue(bean,property, value);
		}
	}
	/**
	 * 获取简单属性值
	 * @param bean
	 * @param property
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object getSimplePropertyValue(Object bean,String property){
		
		if(Map.class.isAssignableFrom(bean.getClass())){
			return ((Map)bean).get(property);
		}
		try {
			if(ReflectionUtils.findField( bean.getClass(), property)==null){
				return null;
			}
			return org.apache.commons.beanutils.PropertyUtils.getSimpleProperty(bean, property);
		} catch (IllegalAccessException e) {
			logger.warn("【"+property+"】IllegalAccessException："+e.getMessage());
		} catch (IllegalStateException e) {
			logger.warn("【"+property+"】IllegalStateException："+e.getMessage());
		} catch (InvocationTargetException e) {
			logger.warn("【"+property+"】InvocationTargetException："+e.getMessage());
		} catch (NoSuchMethodException e) {
			logger.debug("【"+property+"】NoSuchMethodException："+e.getMessage());
		} 
		return null;
	}
	
	/**
	 * 获取属性值
	 * @param bean
	 * @param property
	 * @return
	 */
	public static Object getPropertyValue(Object bean, String property) {
		String[] properties = property.split("\\.");
		if(properties.length>1){
			Object objBean = bean;
			for(int i=0;i<properties.length;i++){
				if(i<properties.length-1){
					objBean = getSimplePropertyValue(objBean,properties[i]);
				}else{
					//最后一级
					return getSimplePropertyValue(objBean,properties[i]);
				}
			}
		}else{
			return getSimplePropertyValue(bean,property);
		}
		return null;
	}
	
	/**
	 * 创建属性对象
	 * @param objBean
	 * @param property
	 * @return
	 */
	private static Object createObject(Object objBean,String property){
		PropertyDescriptor pd =
			BeanUtils.getPropertyDescriptor(objBean.getClass(), property);
		if(pd!=null){
			Class<?> valueClass = pd.getPropertyType();
			Object valueBean = null;
			try {
				valueBean = valueClass.newInstance();
			} catch (InstantiationException e) {
				logger.error("创建对象异常【InstantiationException】:"+e.getMessage());
			} catch (IllegalAccessException e) {
				logger.error("创建对象异常【IllegalAccessException】:"+e.getMessage());
			}
			return valueBean;
		}
		return null;
	}
	


	private abstract static class ParameterParser<T> {

		protected abstract String getType();

		protected abstract T doParse(String parameter) throws NumberFormatException;
	}


	private static class IntParser extends ParameterParser<Integer> {

		@Override
		protected String getType() {
			return "int";
		}

		@Override
		protected Integer doParse(String s) throws NumberFormatException {
			return Integer.valueOf(s);
		}
	}


	private static class LongParser extends ParameterParser<Long> {

		@Override
		protected String getType() {
			return "long";
		}

		@Override
		protected Long doParse(String parameter) throws NumberFormatException {
			return Long.valueOf(parameter);
		}

	}


	private static class FloatParser extends ParameterParser<Float> {

		@Override
		protected String getType() {
			return "float";
		}

		@Override
		protected Float doParse(String parameter) throws NumberFormatException {
			return Float.valueOf(parameter);
		}

	}


	private static class DoubleParser extends ParameterParser<Double> {

		@Override
		protected String getType() {
			return "double";
		}

		@Override
		protected Double doParse(String parameter) throws NumberFormatException {
			return Double.valueOf(parameter);
		}
	}


	private static class BooleanParser extends ParameterParser<Boolean> {

		@Override
		protected String getType() {
			return "boolean";
		}

		@Override
		protected Boolean doParse(String parameter) throws NumberFormatException {
			return (parameter.equalsIgnoreCase("true") || parameter.equalsIgnoreCase("on") ||
					parameter.equalsIgnoreCase("yes") || parameter.equals("1"));
		}
	}
}

