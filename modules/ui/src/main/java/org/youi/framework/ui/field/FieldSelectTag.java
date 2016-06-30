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

import org.youi.framework.ui.field.model.AbstractSourceField;
import org.youi.framework.ui.field.model.FieldSelect;

/**
 * fieldSelect组件标签
 * @author Administrator
 *
 */
public class FieldSelectTag extends AbstractFieldSourceTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1269371421605381868L;
	
	private boolean multiple;

	public boolean getMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
	
	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.field.AbstractFieldSourceTag#createSourceField()
	 */
	protected AbstractSourceField createSourceField() {
		FieldSelect field = new FieldSelect();
		field.setMultiple(multiple);
		return field;
	}
	
	protected String getPopPanelHtml(){
		StringBuilder htmls = new StringBuilder();
		htmls.append("<ul class=\"dropdown-menu\"></ul>");
		return htmls.toString();
	}

	@Override
	public void doFinally() {
		super.doFinally();
		this.multiple = false;
	}
	
}
