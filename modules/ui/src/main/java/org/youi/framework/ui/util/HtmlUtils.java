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


/**
 * 
 */
package org.youi.framework.ui.util;

import java.util.Collections;
import java.util.List;

import org.youi.framework.ui.SortComparator;
import org.youi.framework.ui.button.Button;
import org.youi.framework.util.StringUtils;


/*
 * StringBuffer htmls = new StringBuffer();
		htmls.append("");
		
		return htmls.toString(); 
 */
public class HtmlUtils {
	public static final int HTML_ATTR_INT=0;
	
	public static final int HTML_ATTR_STR=1;
	/**
	 * 返回html属性键值对文本
	 * @param attrName
	 * @param attrValue
	 * @return
	 */
	public static String attrHtml(String attrName,String attrValue){
		if(StringUtils.isEmpty(attrName)||StringUtils.isEmpty(attrValue))return "";
		String valueWrap="\"";
		return " "+attrName+"="+valueWrap+attrValue+valueWrap;
	}
	/**
	 * 返回json属性键值对文本
	 * @param attrName
	 * @param attrValue
	 * @param type
	 * @return
	 */
	public static String attrJson(String attrName,Object attrValue,int type){
		if(attrName==null||attrName.equals("")||attrValue==null)return "";
		String valueWrap="";
		switch(type){
			case HTML_ATTR_STR:
				valueWrap="'";
				break;
			default:
				;
		}
		return attrName+":"+valueWrap+attrValue+valueWrap;
	}
	
	/**
	 * @param domName
	 * @return
	 */
	public static String domHtmlPrefix(String domName){
		StringBuffer htmls = new StringBuffer();
		
		return htmls.toString();
	}
	
	/**
	 * 生成按钮的html内容
	 * @param button
	 * @return
	 */
	public static String generateButtonHtml(Button button){
		String icon = button.getIcon();
		if(StringUtils.isEmpty(icon)){
			icon = button.getName();
		}
		int active = button.getActive();
		
		StringBuffer htmls =new StringBuffer();
		StringBuffer styles =new StringBuffer();
		
		styles.append("youi-button btn btn-default").append((" active-"+active));
		
		if("submit".equals(button.getName())){
			styles.append(" btn-primary");
		}
		
//		if("dropdown")
		
		htmls.append("<button type=\"button\" ");
		//if(options.action){
//		htmls.append( "id=\""+button.getId()+"\" ");
		//
		if("dropdown".equals(icon)){
			styles.append(" dropdown-toggle");
			htmls.append( " data-toggle=\"dropdown\" aria-expanded=\"true\"");
		}else{
			htmls.append( "data-name=\""+button.getName()+"\" ");
		}
		
		if(StringUtils.isNotEmpty(button.getEnableProperty())){
			htmls.append( "data-enable-property=\""+button.getEnableProperty()+"\" ");
			styles.append(" enable-property");
		}else if(StringUtils.isNotEmpty(button.getDisableProperty())){
			htmls.append( "data-enable-property=\""+button.getDisableProperty()+"\" ");
			styles.append(" enable-property disable-property");
		}
		
		if(!StringUtils.isEmpty(button.getCommand())&&!"dropdown".equals(icon)){
			htmls.append( "data-command=\""+button.getCommand()+"\" ");
		}
		
		htmls.append(" class=\""+styles.toString()+"\">");
		if(!StringUtils.isEmpty(button.getIcon())){
			htmls.append("<span class=\"youi-icon fa fa-"+button.getIcon()+"\"></span> ");
		}
		htmls.append(button.getCaption());
		htmls.append("</button>");
		return htmls.toString();
	}
	
	/**
	 * @param buttons
	 * @return
	 */
	public static String generateButtonsHtml(List<Button> buttons){
		return generateButtonsHtml(buttons,null);
	}
	
	public static String generateButtonsHtml(List<Button> buttons,String align){
		Collections.sort(buttons, new SortComparator());
		
		StringBuffer htmls =new StringBuffer();
		
		htmls.append("<div class=\"btn-group btn-group-sm"+(align!=null?" pull-"+align:"")+"\">");
		for(Button button:buttons){
			htmls.append(generateButtonHtml(button));
		}
		htmls.append("</div>");
		
//		var groupStyle ='btn-group '+ (groupStyle||'btn-group-sm');
		
//		htmls.append("<table align=\"right\"><tr>");
//		for(Button button:buttons){
//			htmls.append("<td>")
//				.append(generateButtonHtml(button))
//				.append("</td>");
//		}
//		if(StringUtils.isNotEmpty(lastTdContent)){
//			htmls.append("<td>")
//				.append(lastTdContent)
//				.append("</td>");
//		}
//		htmls.append("</tr></table>");
		return htmls.toString();
	}
	/**
	 * @param buttons
	 * @return
	 */
	public static String generateVerticalButtonsHtml(List<Button> buttons){
		StringBuffer htmls =new StringBuffer();
		htmls.append("<table>");
		for(Button button:buttons){
			htmls.append("<tr><td>");
			htmls.append(generateButtonHtml(button));
			htmls.append("</td></tr>");
		}
		htmls.append("</table>");
		return htmls.toString();
	}
	
	/**
	 * 使用panel包装html
	 * @param title
	 * @param html
	 * @return
	 */
	public static String wrapWithPanel(String title,String html,String styleClasses){
		StringBuffer htmls = new StringBuffer();
		htmls.append(panelPrefix(title,styleClasses));
		htmls.append(html);
		htmls.append(panelPostfix());
		return htmls.toString();
	}
	
	/**
	 * 使用panel包装html - 开始部分
	 * @param title
	 * @return
	 */
	public static String panelPrefix(String title,String styleClasses){
		title = title==null?"":title;
		StringBuffer htmls = new StringBuffer();
		htmls.append("<div class=\"youi-panel panel panel-default "+StringUtils.null2Empty(styleClasses)+"\">");
		htmls.append(	"<div class=\"panel-header panel-heading\">");
		htmls.append(	"	<div class=\"panel-header-title\">"+title+"</div>");
		htmls.append(	"</div>");
		htmls.append(	"<div class=\"panel-content\">");
		return htmls.toString();
	}
	
	/**
	 * 使用panel包装html - 结束部分
	 * @return
	 */
	public static String panelPostfix(){
		StringBuffer htmls = new StringBuffer();
		htmls.append(	"</div>");
		htmls.append("</div>");
		return htmls.toString();
	}
	
	/**
     * Check whether the value contains valid characters for the
     * "Accept-Language" header according to RFC 2616 (section 14.4).
     *
     * @param value The value to check
     * @return <code>true</code> if valid, otherwise <code>false</code>
     */
    public static boolean isValidRfc2616(String value) {
        if (value == null || value.length() == 0) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            
            if (!(Character.isLetter(c) || c == '-')) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 
     * @param inputHtml
     * @param fieldWidth
     * @param isPop
     * @return
     */
    public static String generateFieldInnerHtml(String inputHtml ,int fieldWidth,int inputWidth,boolean isPop){
    	StringBuffer htmls = new StringBuffer();
//		int iconWidth = UiConstants.FIELD_ICON_WIDTH;
//		int invalidWidth = UiConstants.FIELD_INVALID_WIDTH;
//		String filedInputStyle = isPop?"field-input":"field-input-full";
//		htmls.append("<table cellpadding=\"0\" cellspacing=\"0\" width=\""+fieldWidth+"px\"><tbody><tr>");
//		htmls.append(	"<td class=\""+filedInputStyle+"\" valign=\"top\" width=\""+inputWidth+"px\">");
//		htmls.append(inputHtml);
//		htmls.append(	"</td>");
//		if(isPop){
//			htmls.append("<td class=\"youi-icon select-down\" width=\""+iconWidth+"px\"></td>");
//		}
//		htmls.append(	"<td class=\"field-invalid\" width=\""+invalidWidth+"px\"></td>");
//		htmls.append("</tr></tbody></table>");
		
		htmls.append(inputHtml);
		return htmls.toString();
    }
    
    public static int calInputWidth(int fieldWidth,int invalidWidth,int iconWidth){
    	return fieldWidth - invalidWidth - iconWidth;
    }
}
