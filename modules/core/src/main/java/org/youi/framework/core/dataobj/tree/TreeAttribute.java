/**
 * 
 */
package org.youi.framework.core.dataobj.tree;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于描述Domain实体映射树属性
 * 
 * @author Administrator
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TreeAttribute {
	public static final String TREE_ATTR_ID="id";//ID
	
	public static final String TREE_ATTR_CODE="code";//编码
	
	public static final String TREE_ATTR_PARENT="parent";//父对象
	
	public static final String TREE_ATTR_PID="parentId";//父ID
	
	public static final String TREE_ATTR_TEXT="text";//显示文本
	
	public static final String TREE_ATTR_GOURP="group";//分组

	public static final String TREE_ATTR_SRC="src";//分组
	
	String value();
}
