package org.youi.framework.esb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceParam {
	String name();
	
	String pubProperty() default "";
	
	boolean isTokenParam() default false;
}
