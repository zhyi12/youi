/**
 * 
 */
package org.youi.framework.core.orm;

import org.youi.framework.core.orm.hibernate.HibernateCondition;
import org.youi.framework.core.orm.hibernate.HibernateOrder;

/**
 * @author Administrator
 *
 */
public class ConditionFactory {
	private static ConditionFactory conditionFactory = null;
	
	private ConditionFactory(){
		
	}
	
	public static ConditionFactory getInstance(){
		if(conditionFactory==null){
			conditionFactory = new ConditionFactory();
		}
		return conditionFactory;
	}
	
	public Condition getCondition(String property,String operator,Object value){
		return new HibernateCondition(property, operator, value);
	}
	
	public Order getOrder(String propertyName, boolean ascending){
		return new HibernateOrder(propertyName,ascending);
	}
}
