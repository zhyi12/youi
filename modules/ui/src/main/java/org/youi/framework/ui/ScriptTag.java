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
package org.youi.framework.ui;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 上午10:47:49</p>
 */
public class ScriptTag  extends AbstractTag{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1514069663270727153L;
	
	private String src;
	
	@Override
	public int doStartTagInternal() throws JspException {
		super.doStartTagInternal();
		Tag pageTag = findAncestorWithClass(this, PageTag.class);
		
		String scriptSrc = this.getUrlPath(src);
		StringBuffer scriptBuf = new StringBuffer();
		if(pageTag!=null){
			((PageTag)pageTag).addStaticScript(scriptSrc);
		}else{
			scriptBuf.append("<script ");
			scriptBuf.append(" type=\"text/javascript\"");
			scriptBuf.append(" src=\""+scriptSrc+"\"");
			scriptBuf.append("></script>");
			
			this.tagWriter.appendHtml(scriptBuf.toString());
		}
		return SKIP_PAGE;
	}

	/**
	 * @return the src
	 */
	public String getSrc() {
		return src;
	}

	/**
	 * @param src the src to set
	 */
	public void setSrc(String src) {
		this.src = src;
	}
}
