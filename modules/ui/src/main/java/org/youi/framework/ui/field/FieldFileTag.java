/*
 * Copyright (c) 2009 zhyi_12.  All rights reserved. 
 * This software was developed by zhyi_12 and is provided under the terms 
 * of the GNU Lesser General Public License, Version 2.1. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.gnu.org/licenses/lgpl-2.1.txt. The Original Code is Pentaho 
 * youi component.  The Initial Developer is zhyi_12.
 *
 * Software distributed under the GNU Lesser Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
*/



package org.youi.framework.ui.field;

import org.youi.framework.ui.field.model.AbstractField;
import org.youi.framework.ui.field.model.FieldFile;

public class FieldFileTag extends AbstractFieldTag {
	
	private String size;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8566211877987828059L;

	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.AbstractUiTag#uiContentHtml()
	 */
	public String uiContentHtml() {
		StringBuffer htmls = new StringBuffer();
		htmls.append("<input name=\""+getFieldFormName()+"\" type=\"file\" size=\""+size+"\" class=\"value\"></input>");
		return htmls.toString();
	}
	
	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.field.AbstractFieldTag#createField()
	 */
	protected AbstractField createField() {
		FieldFile field = new FieldFile();
		field.setSize(size);
		return field;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}
	
}
