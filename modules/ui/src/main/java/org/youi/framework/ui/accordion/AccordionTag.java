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
package org.youi.framework.ui.accordion;

import org.youi.framework.ui.AbstractUiTag;
import org.youi.framework.ui.util.JsUtils;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 上午11:58:38</p>
 */
public class AccordionTag extends AbstractUiTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6935262838400319295L;

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#initUi()
	 */
	@Override
	public void initUi() {
//		if(itemHeight==0)itemHeight = 300;
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#uiContentHtml()
	 */
	@Override
	public String uiContentHtml() {
		StringBuffer htmls = new StringBuffer();
		return htmls.toString();
	}
	

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#uiStyles()
	 */
	@Override
	protected String uiStyles() {
		return "panel-group youi-accordion";
	}
	
	

	@Override
	protected String[][] uiAttrs() {
		//role="tablist" aria-multiselectable="true"
		return new String[][]{
			new String[]{"role","tablist"},
			new String[]{"aria-multiselectable","true"}
		};
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#uiScripts()
	 */
	@Override
	protected String uiScripts() {
		StringBuffer scripts = new StringBuffer();
		scripts.append("$('#"+id+"').accordion({");
		scripts.append(JsUtils.propertyValue("height", height, JsUtils.JSON_PROP_INT));
		scripts.append(	"	initHtml:false");
		scripts.append(	"});");
		return this.wrapScripts(scripts.toString());
	}


	
}
