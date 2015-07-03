/**
 * 
 */
package org.youi.framework.core.dataobj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Administrator
 *
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Attribute {
	String value() default "";//属性名称
	
	boolean rtexprvalue() default false;//el表达式标识
	
	boolean notNull() default false;//不可为空标识
	
	String fieldType() default "fieldText";
	
	String caption() default "";
	
	String defaultvalue() default "";//默认值
	
	String description() default "";//备注
	
	
}
