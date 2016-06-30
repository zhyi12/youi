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
package org.youi.framework.ui.field.model;

import org.youi.framework.ui.util.JsUtils;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 上午09:17:42</p>
 */
public class FieldCustom extends AbstractField {
	
	private String custom;
	
	private String customOptions;
	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.field.model.AbstractField#addonProperies()
	 */
	@Override
	public String[] addonProperies() {
		return new String[]{
			stringProperty("custom",custom),
			JsUtils.propertyValue("customOptions", customOptions, JsUtils.JSON_PROP_INT)
		};
	}
	/**
	 * @return the custom
	 */
	public String getCustom() {
		return custom;
	}
	/**
	 * @param custom the custom to set
	 */
	public void setCustom(String custom) {
		this.custom = custom;
	}
	/**
	 * @return the customOptions
	 */
	public String getCustomOptions() {
		return customOptions;
	}
	/**
	 * @param customOptions the customOptions to set
	 */
	public void setCustomOptions(String customOptions) {
		this.customOptions = customOptions;
	}

	
}
