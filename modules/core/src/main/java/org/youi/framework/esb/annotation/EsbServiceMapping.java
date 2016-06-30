/**
 * 
 */
package org.youi.framework.esb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.youi.framework.core.web.annotation.Filter;

/**
 * @author zhyi_12
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EsbServiceMapping {
	InitializeProperty[] initializeProperties() default {};//延迟加载属性配置
	
	PubCondition[] pubConditions() default {};
	
	Filter[] filters() default {};
	
	String caption() default "";//交易描述
	
	String trancode() default "";//交易代码
}
