/*
 * YOUI框架
 * Copyright 2010 the original author or authors.
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
package org.youi.framework.ui.layout;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.ArrayUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.youi.framework.util.StringUtils;


/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Dec 26, 2010
 */
public class BootstrapLayout extends AbstractLayout{
	public static final String LAYOUT_NORTH = "north";
	public static final String LAYOUT_WEST = "west";
	public static final String LAYOUT_CENTER = "center";
	public static final String LAYOUT_EAST = "east";
	public static final String LAYOUT_SOUTH = "south";
	
	private String bodyPanelKey = LAYOUT_CENTER;//页面主体面板
	
	private static final String[] panelKeys = {LAYOUT_NORTH,LAYOUT_WEST,LAYOUT_CENTER,LAYOUT_EAST,LAYOUT_SOUTH};
	private static final String[] tablePanelKeys = {LAYOUT_WEST,LAYOUT_CENTER,LAYOUT_EAST};
	
	private Map<String, Panel> panels;
	
	public BootstrapLayout(){
		panels = new HashMap<String, Panel>();
	}
	
	protected void initLayout(Document document, String decorator){
		super.initLayout(document, decorator);
		parsePanelFromDoc();
	}
	
	
	public BootstrapLayout(String decorator,Document document){
		super(decorator,document);
		panels = new HashMap<String, Panel>();
		//加载xml布局配置
		parsePanelFromDoc();
	}
	
	@SuppressWarnings("unchecked")
	private void parsePanelFromDoc() {
		if(document!=null){
			List<Element> panelElements = document.selectNodes("layout/panel");
			for(Element element:panelElements){
				addPanel(element);
			}
		}
	}
	
	private void addPanel(Element element){
		String position = element.attributeValue("position");
		String isBody = element.attributeValue("isBody");
		String jsp = element.attributeValue("jsp");
		if(position==null||ArrayUtils.indexOf(panelKeys, position)==-1)return;
		
		if(isBody!=null){
			bodyPanelKey = position;
		}
		Panel panel = new Panel();
		panel.setJsp(jsp);
		panel.setWidth(element.attributeValue("width"));
		panels.put(position, panel);
	}

	public String getStartHtml(PageContext pageContext){
		StringBuffer htmls = new StringBuffer();
		//script
		htmls.append("<script>$(function(){$('body',document).bootstrapLayout({")
			.append("});")
			.append("});</script>");
		
		int counts = panelKeys.length;
		String panelKey;
		for(int i=0;i<counts;i++){
			panelKey = panelKeys[i];
			if(LAYOUT_WEST.equals(panelKey)){
				htmls.append("<div class=\"layout-md\">");
			}
			if(bodyPanelKey.equals(panelKey)){
				break;
			}
			
			htmls.append(loadPanel(pageContext,panelKey));
			panelKey = null;
		}
		
		
		Panel bodyPanel = panels.get(bodyPanelKey);
		if(bodyPanel!=null){
			htmls.append("<div class=\"layout-panel panel-").append(bodyPanelKey).append("\">");
			
			//内容包装
			htmls.append(buildContentPrefix());
			/**
			 *<div class="main-navigator">	
			 * 	<div class="main-navigator-right">
			 * 		<div id="system-title" class="main-navigator-content"></div>	
			 * 	</div>
			 *</div>
			 *
			 *<div class="main-body">
			 *	<div class="main-body-right">
			 * body内容
			 *	</div>
			 *</div>
			 *
			 *<div class="main-footer">
			 *	<div class="main-footer-right">
			 *		<div class="main-footer-content"></div>
			 *	</div>
			 *</div>
			 *
			*/
		}
		return htmls.toString();
	}
	
	public String getEndHtml(PageContext pageContext){
		StringBuffer htmls = new StringBuffer();
		
		Panel bodyPanel = panels.get(bodyPanelKey);
		if(bodyPanel!=null){
			htmls.append(buildContentPostfix());
			htmls.append("</div>");
		}
		
		int counts = panelKeys.length;
		String panelKey;
		
		int bodyPanelIndex = ArrayUtils.indexOf(panelKeys, bodyPanelKey);
		for(int i=bodyPanelIndex+1;i<counts;i++){
			panelKey = panelKeys[i];
			htmls.append(loadPanel(pageContext,panelKey));
			if(LAYOUT_EAST.equals(panelKey)){
				htmls.append("</div>");
			}
			panelKey = null;
		}
		
		return htmls.toString();
	}
	
	private String loadPanel(PageContext pageContext,String panelKey) {
		StringBuffer htmls = new StringBuffer();
		String basePath = "/decorators/"+decorator+"/";
		if(panels.containsKey(panelKey)){
			Panel panel = panels.get(panelKey);
			String jspFile = panel.getJsp();
			if(ArrayUtils.indexOf(tablePanelKeys, panelKey)!=-1){
//				htmls.append("<td class=\"td-"+(panelKey)+"\" ");
//				if(StringUtils.isNotEmpty(panel.getWidth())){
//					htmls.append("width=\""+panel.getWidth()+"\"");
//				}
//				htmls.append(" valign=\"top\">");
			}
			htmls.append("<div  class=\"layout-panel panel-").append(panelKey).append("\"");
			if(StringUtils.isNotEmpty(panel.getWidth())){
				htmls.append(" style=\"width:"+panel.getWidth()+"px;\"");
			}
			htmls.append(">");
			if(jspFile!=null){
				try {
					htmls.append(acquireString(pageContext, null, basePath+"/"+jspFile));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JspException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			htmls.append("</div>");
			if(ArrayUtils.indexOf(tablePanelKeys, panelKey)!=-1){
//				htmls.append("</td>");
//				if(LAYOUT_WEST.equals(panelKey)){
//					htmls.append("<td class=\"page-split-y\" valign=\"top\"><div class=\"split-top\"></div><div class=\"split-center\"></div></td>");
//				}
			}
		}
		return htmls.toString();
	}

	/**
	 * body panel 前缀html
	 * @return
	 */
	protected String buildContentPrefix(){
		StringBuffer htmls = new StringBuffer();
		htmls
		.append("<div class=\"main-header\">")
		.append(" 	<div class=\"main-header-right\">")
		.append("		<div class=\"main-header-content\"></div>")
		.append("	</div>")
		.append("</div>")
		.append("<div class=\"main-body\">")
		.append("	<div class=\"main-body-right\">")
		.append("		<div class=\"youi-page-content adapt-width\">")
		.append("		<div class=\"tabs-header page-tabs-header\"><ul class=\"tabs-items\"/></div>")
		
		.append("");
		return htmls.toString();
	}
	
	/**
	 * body panel 后缀html
	 * @return
	 */
	protected String buildContentPostfix(){
		StringBuffer htmls = new StringBuffer();
		htmls.append("	</div></div>")
		.append("</div>")
		.append("<div class=\"main-footer\">")
		.append("	<div class=\"main-footer-right\">")
		.append("		<div class=\"main-footer-content\"></div>")
		.append("	</div>")
		.append("</div>");
		return htmls.toString();
	}
}
