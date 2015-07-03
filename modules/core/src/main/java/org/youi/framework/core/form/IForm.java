package org.youi.framework.core.form;

import org.youi.framework.core.dataobj.Domain;

public interface IForm extends Domain{

	public String getFormCode();

	public void setFormPath(String string);

	public String getFormId();

	public String getFormCaption();

	public void setFormCode(String formKey);

	public void setFormCaption(String formCaption);

	public void setFindPath(String formPath);

}
