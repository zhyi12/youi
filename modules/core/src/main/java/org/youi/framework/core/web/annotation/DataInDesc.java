/**
 * 
 */
package org.youi.framework.core.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @功能描述 用于描述控制器的数据输入信息
 * 输入信息分为
 * 1、查询输入(query)
 * 
 * 2、对象输入(record)
 * 
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jan 24, 2010
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataInDesc {

	String type() default "query";// query:表示查询类型的输入， record表示对象类型的输入
	
	DataInCollection[] lists() default {};
	
	Filter[] filters() default {};//过滤条件描述
	
	DataAccess[] dataAccesses()default {};//数据权限注释
	
	PrincipalCondition[] principalConditions() default {};//登录条件过滤
}
