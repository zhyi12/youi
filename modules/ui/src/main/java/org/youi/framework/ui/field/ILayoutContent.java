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

import java.util.List;


public interface ILayoutContent {
	/**
	 * 生成content布局内容
	 * @return
	 */
	public String getContent();
	/**
	 * 设置field集合
	 * @param fields
	 */
	public void setFields(List<Object> fields);
	
	public void setColumns(int columns);
	
	public void setShowBorder(boolean showBorder);
	
	public void setShowLabel(boolean showLabel);
	
	public void setLabelWidths(String labelWidths);
	
	public void setShowTooltips(boolean showTooltips);
}
