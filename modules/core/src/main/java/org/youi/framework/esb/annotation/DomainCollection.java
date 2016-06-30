package org.youi.framework.esb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.youi.framework.core.dataobj.Domain;


@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DomainCollection {

	String name() default "records";
	
	Class<? extends Domain> domainClazz() default Domain.class;
	
}
