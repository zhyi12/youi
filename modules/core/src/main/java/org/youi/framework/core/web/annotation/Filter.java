/**
 * 
 */
package org.youi.framework.core.web.annotation;

/**
 * 用于描述DataDispatchController类
 * 控制映射的DataInDomain里属性的数据权限
 * @author Administrator
 */
public @interface Filter {

	String name();//页面参数

	String operator();
	
	String mapProperty() default "";//映射到实体的属性
	
	Class<? extends java.io.Serializable> valueClazz() default String.class;

}
