/**
 * 
 */
package org.youi.framework.core.web.annotation;

/**
 * 用于描述DataDispatchController类
 * 控制映射的DataInDomain里属性的数据权限
 * @author Administrator
 */
public @interface DataAccess {

	String resource();

	String property();

}
