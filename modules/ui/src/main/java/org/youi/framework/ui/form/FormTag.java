/*
 * Copyright (c) 2009 zhyi_12.  All rights reserved. 
 * This software was developed by zhyi_12 and is provided under the terms 
 * of the GNU Lesser General Public License, Version 2.1. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.gnu.org/licenses/lgpl-2.1.txt. The Original Code is Pentaho 
 * youi component.  The Initial Developer is zhyi_12.
 *
 * Software distributed under the GNU Lesser Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
*/



package org.youi.framework.ui.form;


import java.util.ArrayList;
import java.util.List;

import org.youi.framework.ui.AbstractUiTag;
import org.youi.framework.ui.IButtonContainerTag;
import org.youi.framework.ui.button.Button;
import org.youi.framework.ui.util.GiuiHtmlUtils;
import org.youi.framework.ui.util.HtmlUtils;
import org.youi.framework.ui.util.JsUtils;
import org.youi.framework.util.PojoMapper;
import org.youi.framework.util.StringUtils;


public class FormTag extends AbstractUiTag  implements IButtonContainerTag{
	private String action;
	
	private String method = "POST";
	
	private boolean dialog = false;//是否使用dialog
	
	private String submit = "提 交";
	
	private String reset = "重 置";
	
	private String close = "关闭";
	
	private String remove = "删除";
	
	private List<Button> buttons;//按钮集合
	
	private boolean panel = true;
	
	private String idKeys;//主键field
	
	private String findAction;//查找数据对应的action
	
	private String removeAction;//删除数据对应的action
	
	private String confirmMessage;//确认信息
	
	private int zIndex = 1001;//层布局属性
	
	private String dataType = "json";//
	/**
	 * 
	 */
	private static final long serialVersionUID = 7779387710253033655L;
	

	/* 
	 * form 标签初始化
	 * (non-Javadoc)
	 * @see org.youi.common.web.taglib.AbstractUiTag#initUi()
	 */
	public void initUi() {
		buttons = new ArrayList<Button>();
		
		//form标题属性国际化处理
		caption = this.getI18nMessage(caption);
		//提交(保存，确认等)按钮文本
		submit = dealI18nAttr("save",submit,true);
		//重置按钮文本
		reset = dealI18nAttr("reset",reset,true);
		//关闭按钮文本
		close = dealI18nAttr("close",close,true);
		//删除按钮文本
		remove = dealI18nAttr("remove",remove,true);
		//提交确认信息
		confirmMessage = getI18nMessage(confirmMessage);
	}
	
	public String uiContentHtml() {
		List<Button> htmlButtons = new ArrayList<Button>();
		htmlButtons.addAll(buttons);
		if(!submit.equals("NOT"))htmlButtons.add(createSubmitButton());
		//删除按钮
		if(idKeys!=null&&removeAction!=null)htmlButtons.add(createRemoveButton());
		
		if(!reset.equals("NOT"))htmlButtons.add(createResetButton());
		if(dialog&&!close.equals("NOT"))htmlButtons.add(createCloseButton());
		StringBuffer htmls = new StringBuffer();
		
//		if(!this.dialog){
			htmls.append("<div align=\"center\" class=\"form-buttons\">");
			htmls.append(	GiuiHtmlUtils.generateButtonsHtml(htmlButtons));
			htmls.append("</div>");
//		}
	
		return htmls.toString();
	}
	
	/**
	 * 使用panel包装grid组件 - 开始部分
	 */
	@Override
	protected String innerPrefixHtml() {
		if(dialog||!panel)return "";
		return HtmlUtils.panelPrefix(caption,styleClass);
	}

	/**
	 * 使用panel包装grid组件 - 结束部分
	 */
	@Override
	protected String innerPostfixHtml() {
		if(dialog||!panel)return "";
		return HtmlUtils.panelPostfix();
	}

	protected String uiScripts() {
		
		StringBuffer scripts = new StringBuffer();
		scripts.append("$('#"+id+"').form({");
		scripts.append(JsUtils.propertyValue("width",width,JsUtils.JSON_PROP_INT));
		scripts.append(JsUtils.propertyValue("id", id, JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("caption", caption, JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("confirmMessage", confirmMessage, JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("dialog", dialog, JsUtils.JSON_PROP_INT));
		scripts.append(JsUtils.propertyValue("zIndex", zIndex, JsUtils.JSON_PROP_INT));
		scripts.append(JsUtils.propertyValue("idKeys", idKeys, JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("removeAction", getActionPath(removeAction), JsUtils.JSON_PROP_STR));
		scripts.append(JsUtils.propertyValue("findAction", getActionPath(findAction), JsUtils.JSON_PROP_STR));
		//form按钮动作
		if(buttons!=null&&buttons.size()>0){
			scripts.append(	"buttonActions:{");
			int bIndex = 0;
			for(Button button:buttons){
				if(bIndex>0)scripts.append(",");
				scripts.append(	button.getName());
				scripts.append( ":");//
				if(StringUtils.isNotEmpty(button.getSubmitAction())){
					scripts.append("function(){");
					if(StringUtils.isNotEmpty(button.getSubmitConfirmMsg())){
						scripts.append("if(!window.confirm('"+button.getSubmitConfirmMsg()+"'))return;");
					}
					scripts.append("$(this).parents('.youi-form:first').form('submitAction','"+button.getSubmitAction()+"');}");
				}else{
					scripts.append(	"'"+wrapWithPageId("func_form_"+button.getName())+"'");
				}
				bIndex++;
			}
			scripts.append(	"},");
			
			if(this.dialog==true){
				scripts.append("buttons:").append(PojoMapper.toJson(buttons, false)).append(",");
			}
		}
//		if(this.action!=null&&this.action.endsWith("html")){
//			scripts.append(JsUtils.propertyValue("dataType", "html", JsUtils.JSON_PROP_STR));
//		}
		
		if("html".endsWith(dataType)){
			scripts.append(JsUtils.propertyValue("dataType", "html", JsUtils.JSON_PROP_STR));
		}
		
		scripts.append(	"	initHtml:false");
		scripts.append(	"});");
		return wrapScripts(scripts.toString());
	}

	protected String uiStyles() {
		return "youi-form field-block-container youi-bgcolor "+(dialog?"youi-dialog":"");
	}
	
	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.AbstractUiTag#uiAttrs()
	 */
	protected String[][] uiAttrs(){
		String actionValue = action!=null&&action.endsWith("json")? getActionPath(action):getUrlPath(action);
		String[][] uiAttrs = {
			{"action",actionValue},
			{"method",method}
		};
		return uiAttrs;
	}
	/**
	 * 标签的dom元素名
	 * @return
	 */
	protected String tagElementName(){
		return "form";
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	public int getzIndex() {
		return zIndex;
	}

	public void setzIndex(int zIndex) {
		this.zIndex = zIndex;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/* 
	 * 添加按钮
	 * @see org.youi.common.web.taglib.IButtonContainerTag#addButton(org.youi.common.web.taglib.button.Button)
	 */
	public void addButton(Button button) {
		if(button!=null&&!buttons.contains(button)){
			buttons.add(button);
		}
	}
	
	/**
	 * 重置按钮
	 * @return
	 */
	private Button createResetButton() {
		Button button = new Button();
		button.setName("reset");
		button.setCaption(reset);
		button.setIcon("reset");
		return button;
	}
	/**
	 * 提交按钮
	 * @return
	 */
	private Button createSubmitButton() {
		Button button = new Button();
		button.setName("submit");
		button.setCaption(submit);
		button.setIcon("submit btn-primary");	// btn-primary为bootstrap添加支持
		return button;
	}
	
	private Button createCloseButton(){
		Button button = new Button();
		button.setName("close");
		button.setCaption(close);
		button.setIcon("close");
		return button;
	}
	
	private Button createRemoveButton(){
		Button button = new Button();
		button.setName("remove");
		button.setCaption(remove);
		button.setIcon("remove");
		return button;
	}

	/**
	 * @return the panel
	 */
	public boolean isDialog() {
		return dialog;
	}

	/**
	 * @param panel the panel to set
	 */
	public void setDialog(boolean dialog) {
		this.dialog = dialog;
	}

	/**
	 * @return the submit
	 */
	public String getSubmit() {
		return submit;
	}

	/**
	 * @param submit the submit to set
	 */
	public void setSubmit(String submit) {
		this.submit = submit;
	}

	/**
	 * @return the reset
	 */
	public String getReset() {
		return reset;
	}

	/**
	 * @param reset the reset to set
	 */
	public void setReset(String reset) {
		this.reset = reset;
	}

	/**
	 * @return the panel
	 */
	public boolean isPanel() {
		return panel;
	}

	/**
	 * @param panel the panel to set
	 */
	public void setPanel(boolean panel) {
		this.panel = panel;
	}

	/**
	 * @return the close
	 */
	public String getClose() {
		return close;
	}

	/**
	 * @param close the close to set
	 */
	public void setClose(String close) {
		this.close = close;
	}

	public String getIdKeys() {
		return idKeys;
	}

	public void setIdKeys(String idKeys) {
		this.idKeys = idKeys;
	}

	public String getRemoveAction() {
		return removeAction;
	}

	public void setRemoveAction(String removeAction) {
		this.removeAction = removeAction;
	}

	public String getRemove() {
		return remove;
	}

	public void setRemove(String remove) {
		this.remove = remove;
	}

	/**
	 * @return the findAction
	 */
	public String getFindAction() {
		return findAction;
	}

	/**
	 * @param findAction the findAction to set
	 */
	public void setFindAction(String findAction) {
		this.findAction = findAction;
	}

	/**
	 * @return the confirmMessage
	 */
	public String getConfirmMessage() {
		return confirmMessage;
	}

	/**
	 * @param confirmMessage the confirmMessage to set
	 */
	public void setConfirmMessage(String confirmMessage) {
		this.confirmMessage = confirmMessage;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.tags.RequestContextAwareTag#doFinally()
	 */
	@Override
	public void doFinally() {
		super.doFinally();
		this.action = null;
		this.authCode = null;
		this.caption = null;
		this.confirmMessage = null;
		this.zIndex = 001;
	}
}
