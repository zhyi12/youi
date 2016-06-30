/**
 * 
 */
package org.youi.framework.context.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Administrator
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Module {
	
	String name();//模块名
	
	String caption();//模块描述
	
	String[] entityPackages() default {"entity"};//模块实体包
	
	String[] scanPackages() default {"nls","dao","service","web.attr"};//自动扫描的包
	
}
