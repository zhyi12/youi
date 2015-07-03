package org.youi.framework.esb.annotation;

import org.youi.framework.core.orm.Condition;

public @interface PubCondition {
	
	String property();

	String operator() default Condition.EQUALS;
	
	String pubProperty();
}
