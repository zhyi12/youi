/**
 * 
 */
package org.youi.framework.ui.field;

import org.youi.framework.ui.field.model.AbstractField;
import org.youi.framework.ui.field.model.FieldCalendar;

/**
 * @author Administrator
 *
 */
public class FieldCalendarTag extends AbstractFieldTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9184218237495668890L;
	private String format;//数据格式
	
	private String textFormat;//展示文本格式
	
	private String defaultHour;
	private String defaultMinute;
	private String defaultSecond;
	/**
	 * @return the textFormat
	 */
	public String getTextFormat() {
		return textFormat;
	}

	/**
	 * @param textFormat the textFormat to set
	 */
	public void setTextFormat(String textFormat) {
		this.textFormat = textFormat;
	}

	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.field.AbstractFieldTag#createField()
	 */
	protected AbstractField createField() {
		FieldCalendar field = new FieldCalendar();
		field.setFormat(format);
		field.setTextFormat(textFormat);
		field.setDefaultHour(defaultHour);
		field.setDefaultMinute(defaultMinute);
		field.setDefaultSecond(defaultSecond);
		return field;
	}

	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.AbstractUiTag#uiContentHtml()
	 */
	public String uiContentHtml() {
		StringBuilder htmls = new StringBuilder();
			htmls.append("<span class=\"form-control textInput\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\"/>")
			.append("<input type=\"hidden\" class=\"value\" name=\""+getFieldFormName()+"\"/>")
			.append("<div class=\"input-group-addon\" data-toggle=\"dropdown\" >")
			.append("	<span class=\"youi-icon icon-calendar\"></span>")
			.append("</div><div class=\"dropdown-menu\"></div>");
		return htmls.toString();
	}
	
	@Override
	protected String uiStyles() {
		return "input-group "+super.uiStyles();
	}

	public String getDefaultHour() {
		return defaultHour;
	}

	public void setDefaultHour(String defaultHour) {
		this.defaultHour = defaultHour;
	}

	public String getDefaultMinute() {
		return defaultMinute;
	}

	public void setDefaultMinute(String defaultMinute) {
		this.defaultMinute = defaultMinute;
	}

	public String getDefaultSecond() {
		return defaultSecond;
	}

	public void setDefaultSecond(String defaultSecond) {
		this.defaultSecond = defaultSecond;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}
}
