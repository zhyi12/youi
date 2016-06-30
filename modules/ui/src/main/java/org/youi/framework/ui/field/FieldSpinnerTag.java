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
import org.youi.framework.ui.field.model.FieldSpinner;
import org.youi.framework.ui.util.HtmlUtils;

public class FieldSpinnerTag extends AbstractFieldTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7466089216331473577L;
	
	private Integer incremental;
	private Integer max;
	private Integer min;
	private String numberFormat;
	private Integer page;
	private Integer step;
	
	/**
	 * 创建fieldText组件的html内容
	 */
	public String uiContentHtml() {
		StringBuffer htmls = new StringBuffer();
		
		htmls.append("<input ");
		if(readonly){
			htmls.append(" readonly=\"readonly\" ");
		}
		htmls.append(" name=\""+getFieldFormName()+"\"  type=\"text\" class=\"form-control value textInput\"></input>")
			.append("<div class=\"input-group-addon youi-spinner-toggle\">")
			.append(	"<div class=\"spinner-handler dropup\"><span class=\"caret\"></span></div>")
			.append(	"<div class=\"spinner-handler\"><span class=\"caret\"></span></div>")
			.append("</div>");
		
		return HtmlUtils.generateFieldInnerHtml(htmls.toString(), 0,0, false);
	}
	
	@Override
	protected String uiStyles() {
		return "input-group "+super.uiStyles();
	}
	
	protected AbstractField createField() {
		FieldSpinner field = new FieldSpinner();
		
		field.setIncremental(incremental);
		field.setMax(max);
		field.setMin(min);
		field.setStep(step);
		field.setPage(page);
		field.setNumberFormat(numberFormat);
		
		return field;
	}

	public Integer getIncremental() {
		return incremental;
	}

	public void setIncremental(Integer incremental) {
		this.incremental = incremental;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public String getNumberFormat() {
		return numberFormat;
	}

	public void setNumberFormat(String numberFormat) {
		this.numberFormat = numberFormat;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	
}
