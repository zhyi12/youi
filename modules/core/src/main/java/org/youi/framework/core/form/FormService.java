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
package org.youi.framework.core.form;

import java.util.Collection;
import java.util.List;

import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午07:23:47</p>
 */
public interface FormService<T extends IForm> {

	/**
	 * 查询表单
	 * @param conditions
	 * @param object
	 * @return
	 */
	List<T> getForms(Collection<Condition> conditions, Collection<Order> orders);

	/**
	 * 创建jsp
	 * @param form
	 */
	public void buildJsp(T form);

	/**
	 * 保存表单
	 * @param form
	 * @return
	 */
	public IForm saveForm(T form);
	
	/**
	 * 获取form实现类
	 * @return
	 */
	public Class<T> getFormClazz();

	/**
	 * 
	 * @param formPath
	 * @return
	 */
	public T getFormByFindPath(String formPath);
	
	/**
	 * 删除表单
	 * @param id
	 */
	public void removeForm(String id);

}
