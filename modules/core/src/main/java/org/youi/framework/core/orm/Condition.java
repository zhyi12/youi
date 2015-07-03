/**
 * 
 */
package org.youi.framework.core.orm;


/**
 * @author Administrator
 *
 */
public interface Condition {
	public static final String BETWEEN = "BETWEEN";
//	public static final String BETWEEN_AND_EQUALS = "BETWEEN_AND_EQUALS";
//	public static final String GREATNESS = "GREATNESS";
//	public static final String SMALLNESS = "SMALLNESS";
//	public static final String GREATNESS_AND_EQUALS = "GREATNESS_AND_EQUALS";
//	public static final String SMALLNESS_AND_EQUALS = "SMALLNESS_AND_EQUALS";
	public static final String LIKE = "LIKE";
	public static final String START = "START";
	public static final String END = "END";
	public static final String EQUALS = "EQUALS";
	public static final String IS_NOT_NULL = "NOT_NULL";
	public static final String IS_NULL = "IS_NULL";
	public static final String OR = "OR";
	public static final String IN = "IN";
	
	public static final String NOT_IN = "NOT_IN";
	
	public static final String NOT_EQUALS = "NOT_EQUALS";//不等于
	
	public static final String LEFT = "LEFT";// 小于  <
	public static final String RIGHT = "RIGHT";//大于 >
	
	public static final String LEFT_EQ = "LEFT_EQ";//小于等于 <=
	public static final String RIGHT_EQ = "RIGHT_EQ";//大于等于 >=
	
	public static final String BETWEEN_SPLIT = "-BTW-";
	
	public String getProperty();
	
	public String getOperator();
	
	public Object getValue();

}
