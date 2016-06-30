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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;

import org.youi.framework.ui.layout.LayoutProvider;


/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午02:41:18</p>
 */
public class BodyTag extends AbstractTag implements DynamicAttributes{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2812877104173564430L;
	
	private LayoutProvider layoutProvider;
	
	private String decorator;//
	
	private Map<String,String> attrs = new HashMap<String, String>(); 

	@Override
	protected int doStartTagInternal() throws JspException {
		super.doStartTagInternal();
		
		if(layoutProvider==null){
			layoutProvider = 
				this.getWebContext().getBean(LayoutProvider.class);
		}
		
		StringBuffer htmls = new StringBuffer();
		//
		htmls.append("<body").append(getAttrHtml()).append(">")
		 	 .append(layoutProvider.getStartHtml(pageContext, getWebContext(), decorator));
		
		this.tagWriter.appendHtml(htmls.toString());
		return EVAL_BODY_INCLUDE;
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
//		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		StringBuffer htmls = new StringBuffer();
		if(layoutProvider!=null){
			htmls.append(layoutProvider.getEndHtml(pageContext, getWebContext(),decorator));
		}
		
		htmls.append("</body>");
		
		this.tagWriter.appendHtml(htmls.toString());
		return EVAL_BODY_INCLUDE;
	}

	public String getDecorator() {
		return decorator;
	}

	public void setDecorator(String decorator) {
		this.decorator = decorator;
	}
	
	private String getAttrHtml(){
		String attr = "";
		for (Entry<String,String> entry : attrs.entrySet()) {
			attr += " " + entry.getKey() + "=\"" + entry.getValue() + "\"";
		}
		return attr;
	}

	@Override
	public void setDynamicAttribute(String uri, String localName, Object value)
			throws JspException {
		if("decorator".equals(localName)){
			this.decorator = (String) value;
		}
		else{
			attrs.put(localName, (String) value);
		}
	}

}
