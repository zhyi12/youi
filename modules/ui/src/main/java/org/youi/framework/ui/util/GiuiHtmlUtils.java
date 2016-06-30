package org.youi.framework.ui.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.youi.framework.ui.SortComparator;
import org.youi.framework.ui.button.Button;
import org.youi.framework.util.StringUtils;

public class GiuiHtmlUtils {

	/**
	 * 生成按钮的html内容
	 * @param button
	 * @return
	 */
	public static String generateButtonHtml(Button button){
		
		Map<String,String> datas = new HashMap<String,String>();
		
		if(StringUtils.isNotEmpty(button.getSubmitProperty())&&StringUtils.isNotEmpty(button.getSubmitValue())){
			datas.put("property", button.getSubmitProperty());
			datas.put("value", button.getSubmitValue());
		}
		
		if(StringUtils.isNotEmpty(button.getSubmitConfirmMsg())){
			datas.put("confirm", button.getSubmitConfirmMsg());
		}
		
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
		
		if(!StringUtils.isEmpty(button.getCommand())&&!"dropdown".equals(icon)){
			datas.put("command",button.getCommand());
		}
		
		htmls.append("<button type=\"button\" ");
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
		
		if(datas!=null){
			for(Map.Entry<String, String> entry:datas.entrySet()){
				htmls.append( " data-"+entry.getKey()+"=\""+entry.getValue()+"\" ");
			}
		}
		
		htmls.append(" class=\""+styles.toString()+"\">");
		if(!StringUtils.isEmpty(button.getIcon())){
			htmls.append("<span class=\"youi-icon icon-"+button.getIcon()+"\"></span> ");
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
		return htmls.toString();
	}
}
