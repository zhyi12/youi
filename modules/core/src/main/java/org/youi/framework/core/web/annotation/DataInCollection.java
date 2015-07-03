/**
 * 
 */
package org.youi.framework.core.web.annotation;

/**
 * @author zhouyi
 *
 */
public @interface DataInCollection {
	String name();//
	
	@SuppressWarnings({"rawtypes"})
	Class collectionClass();//
}
