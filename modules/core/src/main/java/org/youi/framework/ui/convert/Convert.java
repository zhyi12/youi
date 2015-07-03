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
package org.youi.framework.ui.convert;

import java.util.LinkedHashMap;

import org.youi.framework.core.convert.IConvert;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午03:54:42</p>
 */
public class Convert<T> extends LinkedHashMap<String, T> implements IConvert<T>{

	/**
	 * {"a":"value-a","b":"value-b"}
	 */
	private static final long serialVersionUID = 8592585220347877766L;

	private String name;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String toJson() {
		StringBuffer jsonBuf = new StringBuffer("{");
		
		for(String key:this.keySet()){
			jsonBuf.append("\"")
					.append(key)
					.append("\":\"")
					.append(this.get(key))
					.append("\",");
		}
		if(jsonBuf.length()>1){
			jsonBuf.deleteCharAt(jsonBuf.length()-1);
		}
		jsonBuf.append("}");
		return jsonBuf.toString();
	}
	

	
	
	
}
