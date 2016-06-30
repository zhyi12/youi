/**
 * 
 */
package org.youi.framework.ui.button;

import org.youi.framework.ui.AbstractUiModel;
import org.youi.framework.ui.ISort;

/**
 * @author Administrator
 *
 */
public class Button extends AbstractUiModel implements ISort{
	
	private String caption;
	
	private int active;
	
	private String enableProperty;//
	
	private String disableProperty;
	
	private String action;
	
	private String name;
	
	private String icon;//图标样式
	
	private String method;//方法名
	
	private int order;//
	
	private String submitAction;//grid 关联
	
	private String submitType;//提交完成后回调方法
	
	private String submitConfirmMsg;//提交确认信息
	
	private String command;//所属命令组
	
	private String sysmenu;//系统菜单
	
	private String submitProperty;//提交属性
	
	private String submitValue;//提交属性值
	
	public Button(){
		
	}
	
	
	public Button(String name,String caption, int active,String icon,int order) {
		super();
		this.caption = caption;
		this.active = active;
		this.name = name;
		this.icon = icon;
		this.order = order;
	}


	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}


	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
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


	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.AbstractUiModel#toJson()
	 */
	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
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

	public String getSysmenu() {
		return sysmenu;
	}

	public void setSysmenu(String sysmenu) {
		this.sysmenu = sysmenu;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
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

	public String getCommand() {
		return command;
	}


	public void setCommand(String command) {
		this.command = command;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Button other = (Button) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public int getOrder(){
		return order;
	}
	
	public void setOrder(int order){
		this.order = order;
	}


	public String getEnableProperty() {
		return enableProperty;
	}


	public void setEnableProperty(String enableProperty) {
		this.enableProperty = enableProperty;
	}


	public String getDisableProperty() {
		return disableProperty;
	}

	public void setDisableProperty(String disableProperty) {
		this.disableProperty = disableProperty;
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


}
