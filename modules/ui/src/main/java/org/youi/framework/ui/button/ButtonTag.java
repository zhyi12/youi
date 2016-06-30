/**
 * 
 */
package org.youi.framework.ui.button;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.youi.framework.ui.AbstractUiTag;
import org.youi.framework.ui.IButtonContainerTag;
import org.youi.framework.ui.util.GiuiHtmlUtils;


/**
 * @author Administrator
 *
 */
public class ButtonTag extends AbstractUiTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8390559643188882036L;

	private String icon;
	
	private String name;
	
	private String action;
	
	private String method;
	
	private int active;
	
	private String disableProperty;//
	
	private String enableProperty;//
	
	private int order;
	
	private String submitAction;//
	
	private String submitType="1";//提交完成后回调方法
	
	private String submitConfirmMsg;//提交确认信息
	
	private String sysmenu;//系统菜单编号
	
	private String submitProperty;//提交属性
	
	private String submitValue;//提交属性值


	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.AbstractUiTag#initUi()
	 */
	@Override
	public void initUi() {
		this.tagWriter = createTagWriter();//创建tagWriter 
		id = "button_"+name;
	}
	
	public int doStartTagInternal() throws JspException {
		if(!hasPermission(getAuthCode())){
			return SKIP_PAGE;
		}
		
		initUi();
		
		Tag tag  = findAncestorWithClass(this, IButtonContainerTag.class);
		if(tag==null){
			return SKIP_BODY;
		}else{
			((IButtonContainerTag)tag).addButton(createButton());
		}
		return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException {
		Tag tag  = findAncestorWithClass(this, IButtonContainerTag.class);
		if(tag==null){
			this.tagWriter.appendHtml(this.uiContentHtml());
		}
		return EVAL_PAGE;
	}
	

	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.AbstractUiTag#uiStartHtml()
	 */
	@Override
	public String uiStartHtml() {
		return "";
	}
	
	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.AbstractUiTag#uiEndHtml()
	 */
	@Override
	public String uiEndHtml() {
		return "";
	}

	public String getDisableProperty() {
		return disableProperty;
	}

	public void setDisableProperty(String disableProperty) {
		this.disableProperty = disableProperty;
	}

	public String getEnableProperty() {
		return enableProperty;
	}

	public void setEnableProperty(String enableProperty) {
		this.enableProperty = enableProperty;
	}

	/**
	 * 创建按钮模型,由上级的实现了IButtonContainerTag接口的标签使用
	 * @return
	 */
	private Button createButton(){
		Button button = new Button();
		button.setActive(active);
		button.setAction(getUrlPath(action));
		button.setEnableProperty(enableProperty);
		button.setDisableProperty(disableProperty);
		
		button.setCaption(caption);
		button.setName(name);
		button.setIcon(icon);
		button.setOrder(order);
		button.setSubmitAction(getActionPath(submitAction));
		button.setSubmitConfirmMsg(submitConfirmMsg);
		button.setSubmitType(submitType);
		button.setSysmenu(sysmenu);
		button.setMethod(method);
		
		button.setSubmitProperty(submitProperty);
		button.setSubmitValue(submitValue);
		return button;
	}
	
	public String getSysmenu() {
		return sysmenu;
	}

	public void setSysmenu(String sysmenu) {
		this.sysmenu = sysmenu;
	}

	/* 
	 * 生成按钮的html内容
	 */
	@Override
	public String uiContentHtml() {
		return GiuiHtmlUtils.generateButtonHtml(createButton() );
	}

	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.AbstractUiTag#uiScripts()
	 */
	@Override
	protected String uiScripts() {
		return "";
	}

	/*
	 * 按钮html生成规则和UI组件不一致
	 * 该处无须返回样式
	 */
	@Override
	protected String uiStyles() {
		return "";
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the active
	 */
	public int getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(int active) {
		this.active = active;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * @return the submitAction
	 */
	public String getSubmitAction() {
		return submitAction;
	}

	/**
	 * @param submitAction the submitAction to set
	 */
	public void setSubmitAction(String submitAction) {
		this.submitAction = submitAction;
	}

	/**
	 * @return the submitType
	 */
	public String getSubmitType() {
		return submitType;
	}

	/**
	 * @param submitType the submitType to set
	 */
	public void setSubmitType(String submitType) {
		this.submitType = submitType;
	}

	/**
	 * @return the submitConfirmMsg
	 */
	public String getSubmitConfirmMsg() {
		return submitConfirmMsg;
	}

	/**
	 * @param submitConfirmMsg the submitConfirmMsg to set
	 */
	public void setSubmitConfirmMsg(String submitConfirmMsg) {
		this.submitConfirmMsg = submitConfirmMsg;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getSubmitProperty() {
		return submitProperty;
	}

	public void setSubmitProperty(String submitProperty) {
		this.submitProperty = submitProperty;
	}

	public String getSubmitValue() {
		return submitValue;
	}

	public void setSubmitValue(String submitValue) {
		this.submitValue = submitValue;
	}

	@Override
	public void doFinally() {
		super.doFinally();
		this.action = null;
		this.actionPrefix = null;
		this.active = 0;
		this.disableProperty = null;
		this.enableProperty = null;
		this.method = null;
		this.icon = null;
		this.name = null;
		this.order = 0;
		this.submitAction = null;
		this.submitType = null;
		
		this.submitProperty = null;
		this.submitValue = null;
	}

}
