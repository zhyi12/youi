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
package org.youi.framework.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jan 16, 2010
 */
public class ReflectionUtils extends org.springframework.util.ReflectionUtils {
	
	
	@SuppressWarnings({"unchecked","rawtypes"})
	public static List<Method> annotationedGetterMethod(Class targetClass,final Class annotationClass){
		final List<Method> methods = new ArrayList<Method>();
		ReflectionUtils.doWithMethods(targetClass, new MethodCallback(){
			public void doWith(Method method) throws IllegalArgumentException,
					IllegalAccessException {
				 Annotation annotation = method.getAnnotation(annotationClass);
				 if(annotation!=null&&method.getName().startsWith("get"))
					 methods.add(method);
			}
		});
		return methods;
	}
	
	@SuppressWarnings({"unchecked","rawtypes"})
	public static List<Field> annotationedField(Class targetClass,final Class annotationClass){
		final List<Field> fields = new ArrayList<Field>();
		ReflectionUtils.doWithFields(targetClass, new FieldCallback(){

			public void doWith(Field field) throws IllegalArgumentException,
					IllegalAccessException {
				Annotation annotation = field.getAnnotation(annotationClass);
				if(annotation!=null&&!fields.contains(fields)){
					fields.add(field);
				}
			}
		});
		return fields;
	}
	/**
	 * 反射私有变量值
	 * @param bean
	 * @param fieldName
	 * @return
	 */
	public static Object getPrivateFieldValue(Object bean,String fieldName){
		try {
			Field field = bean.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);//
			return field.get(bean);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
