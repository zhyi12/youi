/*
 * YOUI框架
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.youi.framework.ui.util;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午09:02:31</p>
 */
public class JsUtils {
	
	public static final int JSON_PROP_INT=0;
	
	public static final int JSON_PROP_STR=1;
	/**
	 * 生成JavaScript对象内的属性和值对，以“,”号结束，最后一个属性不应调用或者手动删除最后的“,”号
	 * @param property
	 * @param value
	 * @param type
	 * @return
	 */
	public static String propertyValue(String property,Object value,int type){
		if(property==null||value==null||"".equals(value))return "";
		
		String valueWrap="";
		switch(type){
			case JSON_PROP_STR:
				valueWrap="'";
				break;
			default:
				;
		}
		StringBuffer jsons = new StringBuffer();
		jsons.append(property);
		jsons.append(":");
		jsons.append(valueWrap);
		jsons.append(value);
		jsons.append(valueWrap);
		jsons.append(",");
		return jsons.toString();
	}
}

