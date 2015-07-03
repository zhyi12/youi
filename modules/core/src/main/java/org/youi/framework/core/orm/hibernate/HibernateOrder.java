package org.youi.framework.core.orm.hibernate;

import org.hibernate.criterion.Order;

public class HibernateOrder extends Order implements
	org.youi.framework.core.orm.Order {
	
	private String propertyName;
	/**
	 * 
	 */
	private static final long serialVersionUID = 6022774683096569626L;

	public HibernateOrder(String propertyName, boolean ascending) {
		super(propertyName, ascending);
		this.propertyName = propertyName;
	}

	public String getProperty() {
		return propertyName;
	}

}
