/*
 * YOUI框架
 * Copyright 2010 the original author or authors.
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
package org.youi.framework.core.web.controller;

import org.youi.framework.core.Constants;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Dec 21, 2010
 */
public class BaseDataController extends BaseController{
	protected static final String SUCCESS_CODE=Constants.SUCCESS_CODE;//访问成功
	
	protected static final String ACCESS_DENIED_CODE=Constants.ACCESS_DENIED_CODE;//拒绝访问
	
	@Override
	public String getUrlType() {
		return BaseController.URL_TYPE_DATA;
	}
}
