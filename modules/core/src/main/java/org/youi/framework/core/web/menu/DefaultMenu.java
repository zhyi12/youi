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
package org.youi.framework.core.web.menu;

import org.youi.framework.core.dataobj.tree.TreeAttribute;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Dec 23, 2010
 */
public class DefaultMenu implements IMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6256436543642454970L;

	private String menuStyle;
	
	private String menuId;
	
	private String menuSrc;
	
	private String target;
	
	private String parentMenuId;
	
	private String menuCaption;

	/**
	 * @return the menuStyle
	 */
	public String getMenuStyle() {
		return menuStyle;
	}

	/**
	 * @param menuIconStyle the menuStyle to set
	 */
	public void setMenuStyle(String menuStyle) {
		this.menuStyle = menuStyle;
	}

	/**
	 * @return the menuId
	 */
	@TreeAttribute(value = "id")
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the src
	 */
	@TreeAttribute(value = "href")
	public String getMenuSrc() {
		return menuSrc;
	}

	/**
	 * @param src the src to set
	 */
	public void setMenuSrc(String src) {
		this.menuSrc = src;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	@TreeAttribute(value = "target")
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the parentMenuId
	 */
	@TreeAttribute(value = "parentId")
	public String getParentMenuId() {
		return parentMenuId;
	}

	/**
	 * @param parentMenuId the parentMenuId to set
	 */
	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	/**
	 * @return the menuName
	 */
	@TreeAttribute(value = "text")
	public String getMenuCaption() {
		return menuCaption;
	}

	/**
	 * @param menuName the menuName to set
	 */
	public void setMenuCaption(String menuCaption) {
		this.menuCaption = menuCaption;
	}

	public String toString(){
		return menuCaption+ " " +menuId;
	}
	

}
