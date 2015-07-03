/**
 * 
 */
package org.youi.framework.core.orm.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.youi.framework.core.orm.Condition;


/**
 * hibernate 查询条件
 * @author Administrator
 *
 */
public class HibernateCondition implements Condition {
	protected String property;
	protected String operator;
	protected Object value;
	
	/**
	 * 构造函数
	 * @param property
	 * @param operator
	 * @param value
	 */
	public HibernateCondition(String property, String operator,
			Object  value) {
		this.property   = property;
		this.operator   = operator;
		this.value = value;
	}
	/**
	 * 生成查询表达式
	 * @param alias
	 * @return
	 */
	public Criterion generateExpression(String alias){
		if(value!=null){
			if(this.operator.equals(Condition.EQUALS)){//等于
				return Restrictions.eq(property, value);
			}if(this.operator.equals(Condition.NOT_EQUALS)){//不等于
				return Restrictions.ne(property, value);//like
			}else if(this.operator.equals(Condition.LIKE)){//
				return Restrictions.like(property, value.toString(),MatchMode.ANYWHERE);
			}else if(this.operator.equals(Condition.END)){//
				return Restrictions.like(property, value.toString(),MatchMode.END);
			}else if(this.operator.equals(Condition.START)){//
				return Restrictions.like(property, value.toString(),MatchMode.START);
			}else if(this.operator.equals(Condition.BETWEEN)){//
				String[] betweenArray = value.toString().split(Condition.BETWEEN_SPLIT);
				if(betweenArray.length<2)return null;//
				return generateBetween(betweenArray[0], betweenArray[1]);
			}else if(this.operator.equals(Condition.IN)){//in
				if(value instanceof Object[]){
					return Restrictions.in(property, (Object[])value);
				}
			}else if(this.operator.equals(Condition.NOT_IN)){//not in
				if(value instanceof Object[]){
					return Restrictions.not(Restrictions.in(property, (Object[])value));
				}
			}else if(this.operator.equals(Condition.LEFT)){//小于
				return Restrictions.lt(property, value);
			}else if(this.operator.equals(Condition.RIGHT)){//大于
				return Restrictions.gt(property, value);
			}else if(this.operator.equals(Condition.LEFT_EQ)){//小于等于
				return Restrictions.le(property, value);
			}else if(this.operator.equals(Condition.RIGHT_EQ)){//大于等于
				return Restrictions.ge(property, value);
			}
		}
		
		if(this.operator.equals(Condition.IS_NULL)){
			return Restrictions.isNull(property);
		}else if(this.operator.equals(Condition.IS_NOT_NULL)){
			return Restrictions.isNotNull(property);
		}
		
		return null;
	}

	public Criterion generateExpression() {
		return generateExpression(null);
	}
	
	private Criterion generateBetween(String begin,String end){
		return Restrictions.between(this.property, begin, end);//Expression.between();
	}
	
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
	public String toString(){
		return property + " " + operator + " " + value;
	}
}
