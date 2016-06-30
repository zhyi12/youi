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
package org.youi.framework.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.youi.framework.ui.util.JsUtils;


/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午05:31:18</p>
 */
public abstract class AbstractUiModel {
	protected final Log log = LogFactory.getLog(getClass());
	
	public abstract String toJson();
	
	protected String stringProperty(String property,String value){
		return JsUtils.propertyValue(property,value,JsUtils.JSON_PROP_STR);
	}
	
	protected String booleanProperty(String property,boolean value){
		return JsUtils.propertyValue(property,new Boolean(value),JsUtils.JSON_PROP_INT);
	}
	
	protected String intProperty(String property,int value){
		return JsUtils.propertyValue(property,new Integer(value),JsUtils.JSON_PROP_INT);
	}
	
	protected String arrayProperty(String property,String[] values){
		if(property==null||values==null||values.length==0)return "";
		StringBuffer jsons = new StringBuffer();
		jsons.append(property+":[");
		for(int i=0;i<values.length;i++){
			if(i>0){
				jsons.append(",");
			}
			jsons.append("'");
			jsons.append(values[i]);
			jsons.append("'");
		}
		jsons.append("],");
		return jsons.toString();
	}
}
