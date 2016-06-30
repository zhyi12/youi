/**
 * 
 */
package org.youi.framework.ui.field.model;


/**
 * @author Administrator
 *
 */
public class FieldCalendar extends AbstractField {
	private String format;//数据格式
	
	private String textFormat;//展示文本格式
	
	private String defaultHour;
	private String defaultMinute;
	private String defaultSecond;
	
	/* (non-Javadoc)
	 * @see org.youi.common.web.taglib.field.model.AbstractField#addonProperies()
	 */
	public String[] addonProperies() {
		String[] addonProperies  ={
			stringProperty("format", format),
			stringProperty("textFormat", textFormat),
			stringProperty("defaultHour", defaultHour),
			stringProperty("defaultMinute", defaultMinute),
			stringProperty("defaultSecond", defaultSecond)
		};
		return addonProperies;
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
	
	public String uiContentHtml() {
		StringBuffer htmls = new StringBuffer();
		int iconWidth = 22;
		int fieldWidth = parseWidth();
		htmls.append("<table cellpadding=\"0\" cellspacing=\"0\" width=\""+fieldWidth+"\"><tbody><tr>");
		htmls.append(	"<td valign=\"top\" width=\""+(fieldWidth-iconWidth)+"px\"><input readonly=\"readonly\" name=\""+getProperty()+"\" style=\"width:"+(fieldWidth-iconWidth)+"px;\" type=\"text\" class=\"textInput\"></input><input type=\"hidden\" class=\"value\"/></td>");
		htmls.append(	"<td class=\"youi-icon select-down\" width=\""+iconWidth+"px\"></td>");
		htmls.append("</tr></tbody></table>");
		return htmls.toString();
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

}
