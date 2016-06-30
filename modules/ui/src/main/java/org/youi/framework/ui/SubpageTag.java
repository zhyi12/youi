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

import java.util.ArrayList;
import java.util.List;

import org.youi.framework.ui.button.Button;
import org.youi.framework.ui.util.JsUtils;
import org.youi.framework.util.PojoMapper;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:子页面标签</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午04:22:07</p>
 */
public class SubpageTag extends AbstractUiTag  implements IButtonContainerTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1001227636984538259L;

	private String src;//页面地址
	
	private String editSrc;//通过后台查询数据，组装record
	
	private String idKeys;//主键
	
	private String type;//子页面类型  dialog  window
	
	private String subpageId;//子页面ID
	
	private String formAction;//
	private String formSubmit = "提 交";//
	private String formConfirm = "";//提交前提示
	
	/****************************************************/
	private List<Button> buttons; 
	
	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#initUi()
	 */
	@Override
	public void initUi() {
		//使用subpageId属性生成dom id
		this.id =this.wrapWithPageId("subpage_"+this.subpageId);
		
		buttons = new ArrayList<Button>();
		if(height==0)height=360; 
		 
//		if(StringUtils.isNotEmpty(formAction)){ 
//			buttons.add(new Button("submit",formSubmit,0,"submit",-1));
//			buttons.add(new Button("closeDialog","关闭",0,"close",-1));
//		}
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#uiContentHtml()
	 */
	@Override
	public String uiContentHtml() {
		StringBuffer htmls = new StringBuffer();
		int contentHeight = height - (buttons.size()>0?32:0);
		htmls.append("<div style=\"height:"+contentHeight+"px;\" class=\"subpage-content\"></div>");
//		if(buttons.size()>0){
//			htmls.append("<div class=\"subpage-buttons\">");
//			htmls.append(HtmlUtils.generateButtonsHtml(buttons));
//			htmls.append("</div>");
//		}
		return htmls.toString();
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#uiStyles()
	 */
	@Override
	protected String uiStyles() {
		return "youi-subpage";
	}

	/* (non-Javadoc)
	 * @see com.gsoft.framework.taglib.AbstractUiTag#uiScripts()
	 */
	@Override
	protected String uiScripts() {
		
		StringBuffer scripts = new StringBuffer();
		scripts.append("$('#"+id+"').subpage({");
		scripts.append(JsUtils.propertyValue("id", id, JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("type", type, JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("subpageId", getSubpageIdPath(), JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("caption", caption, JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("height", height, JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("width", width, JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("src", getFullSrc(), JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("editSrc", getActionPath(editSrc), JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("idKeys", idKeys, JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("formAction", getActionPath(formAction), JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("formConfirm", formConfirm, JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("formSubmit", formSubmit, JsUtils.JSON_PROP_STR));
		//表格按钮动作
		if(buttons!=null&&buttons.size()>0){
			
			StringBuilder buttonsScript = new StringBuilder();
			
			scripts.append(	"buttonActions:{");
			int bIndex = 0;
			for(Button button:buttons){
				if(bIndex>0){
					scripts.append(",");
					buttonsScript.append(",");
				}
				
				buttonsScript.append(PojoMapper.toJson(button,false));
				
				scripts.append(	button.getName());
				scripts.append( ":");
				scripts.append(	"'"+wrapWithPageId("func_subpage_"+button.getName())+"'");
				bIndex++;
			}
			scripts.append(	"},");
			
			
			scripts.append(	"buttons:[").append(buttonsScript).append("],");
			
		}
		
		scripts.append(	"	initHtml:false");
		scripts.append( "});");
		return wrapScripts(scripts.toString());
	}

	private String getFullSrc() {
		return this.src;// + (this.src.indexOf('?') > 0 ? "&":"?");// + "page:pageId=" + this.getSubpageIdPath();
	}

	private String getSubpageIdPath() {
		return pageContext.getRequest().getParameter("page:pageId") + "_" +  this.subpageId;
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

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	public void addButton(Button button) {
		if("close".equals(button.getName())){
			button.setIcon("close");
		}
		if(button!=null)buttons.add(button);
	}

	public String getEditSrc() {
		return editSrc;
	}

	public void setEditSrc(String editSrc) {
		this.editSrc = editSrc;
	}

	

	public String getIdKeys() {
		return idKeys;
	}

	public void setIdKeys(String idKeys) {
		this.idKeys = idKeys;
	}

	public String getFormConfirm() {
		return formConfirm;
	}

	public void setFormConfirm(String formConfirm) {
		this.formConfirm = formConfirm;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.tags.RequestContextAwareTag#doFinally()
	 */
	@Override
	public void doFinally() {
		super.doFinally();
		this.src = null;
		this.type = null;
		this.buttons = null;
		this.editSrc = null;
		this.idKeys = null;
		this.subpageId = null;
		this.formConfirm = null;
	}

	/**
	 * @return the pageId
	 */
	public String getSubpageId() {
		return subpageId;
	}

	/**
	 * @param pageId the pageId to set
	 */
	public void setSubpageId(String subpageId) {
		this.subpageId = subpageId;
	}

	/**
	 * @return the formAction
	 */
	public String getFormAction() {
		return formAction;
	}

	/**
	 * @param formAction the formAction to set
	 */
	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}

	/**
	 * @return the formSubmit
	 */
	public String getFormSubmit() {
		return formSubmit;
	}

	/**
	 * @param formSubmit the formSubmit to set
	 */
	public void setFormSubmit(String formSubmit) {
		this.formSubmit = formSubmit;
	}
	
	
}
