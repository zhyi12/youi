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
package org.youi.framework.ui.field.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jul 26, 2010
 */
public class FieldTree extends AbstractSourceField {

	private boolean onlyLeaf;
	
	private boolean simple;
	
	private boolean check;

	private String iteratorParentAttr;//迭代属性
	
	private String sourceStyle;

	/**
	 * @return the iteratorParentAttr
	 */
	public String getIteratorParentAttr() {
		return iteratorParentAttr;
	}

	/**
	 * @param iteratorParentAttr the iteratorParentAttr to set
	 */
	public void setIteratorParentAttr(String iteratorParentAttr) {
		this.iteratorParentAttr = iteratorParentAttr;
	}

	/**
	 * @return the onlyLeaf
	 */
	public boolean isOnlyLeaf() {
		return onlyLeaf;
	}

	/**
	 * @param onlyLeaf the onlyLeaf to set
	 */
	public void setOnlyLeaf(boolean onlyLeaf) {
		this.onlyLeaf = onlyLeaf;
	}
	
	/**
	 * @return the simple
	 */
	public boolean isSimple() {
		return simple;
	}

	/**
	 * @param simple the simple to set
	 */
	public void setSimple(boolean simple) {
		this.simple = simple;
	}

	public String[] addonProperies() {
		String[] addonProperies = super.addonProperies();
		List<String> addOns = new ArrayList<String>();
		for(String addon:addonProperies){
			addOns.add(addon);
		}
		addOns.add(booleanProperty("onlyLeaf",onlyLeaf));
		addOns.add(booleanProperty("simple",simple));
		addOns.add(booleanProperty("check",check));
		addOns.add(stringProperty("iteratorParentAttr",iteratorParentAttr));
		addOns.add(stringProperty("sourceStyle",sourceStyle));
		
		return addOns.toArray(new String[addOns.size()]);
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	@Override
	public boolean isLabelTop() {
		return !popup;
	}

	public String getSourceStyle() {
		return sourceStyle;
	}

	public void setSourceStyle(String sourceStyle) {
		this.sourceStyle = sourceStyle;
	}
	
}
