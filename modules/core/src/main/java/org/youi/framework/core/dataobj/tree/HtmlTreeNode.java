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
package org.youi.framework.core.dataobj.tree;

import java.util.Map;
import java.util.Set;

import org.springframework.util.Assert;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.util.PropertyUtils;
import org.youi.framework.util.StringUtils;



/**
 * @功能描述 html树节点
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jan 30, 2010
 */
public class HtmlTreeNode extends AbstractTreeNode implements TreeNode {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1879870009832140690L;

	private String src;//树路径 
	
	
	private String href;//链接路径
	
	private String tooltips;//tooltips
	
	private String check;//复选标识check checked parchecked
	
	private Domain domain;//树节点映射的实体对象
	
	private boolean expanded;//自动展开
	
	private String icon;//图标
	
	/**
	 * 构造函数
	 * @param id
	 * @param text
	 */
	public HtmlTreeNode(String id, String text) {
		super(id, text);
	}
	
	/**
	 * 构造函数
	 * @param id
	 * @param text
	 */
	public HtmlTreeNode(String id, String text,TreeNodeConfig treeNodeConfig) {
		super(id, text);
		if(treeNodeConfig!=null)treeNodeConfig.render(this);
	}
	/**
	 * 自动解析Domain的TreeAttribute的构造函数
	 * @param domain
	 */
	public HtmlTreeNode(Domain domain) {
		this(domain,null);
	}
	
	/**
	 * @param domain
	 * @param treeNodeConfig
	 */
	public HtmlTreeNode(Domain domain,TreeNodeConfig treeNodeConfig) {
		super(null,null);
		Assert.notNull(domain, "domain对象不能为空！");
		//根据Domain的注释自动映射
		injectFromDomain(domain);
		this.domain = domain;
		if(treeNodeConfig!=null)treeNodeConfig.render(this);
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
	 * @return the tooltips
	 */
	public String getTooltips() {
		return tooltips;
	}

	/**
	 * @param tooltips the tooltips to set
	 */
	public void setTooltips(String tooltips) {
		this.tooltips = tooltips;
	}

	/**
	 * @return the check
	 */
	public String getCheck() {
		return check;
	}

	/**
	 * @param check the check to set
	 */
	public void setCheck(String check) {
		this.check = check;
	}
	
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * 值注入
	 * @param domain
	 */
	private void injectFromDomain(Domain domain){
		Map<String, Object> values = TreeUtils.getValueMapFromDomain(domain);
		if(!values.containsKey(TreeAttribute.TREE_ATTR_ID)){//如果没有找到id属性映射，使用domain的toString
			values.put(TreeAttribute.TREE_ATTR_ID, domain.toString());
		}
//		Assert.isTrue(values.containsKey("id"), 
//				domain.getClass().getName()+": 必须有get方法注释为（TreeAttribute(\"id\"),并且具有返回值！）");
		Set<String> attributes =  values.keySet();
		for(String attribute:attributes){
			this.setTreeAttributeValue(attribute, values.get(attribute));
		}
	}
	
	/**
	 * @param attribute
	 * @param value
	 */
	private void setTreeAttributeValue(String attribute,Object value){
		if(value==null)value = "";
		if(attribute.equals(TreeAttribute.TREE_ATTR_ID)){//树节点id
			setId(value.toString());
		}else if(attribute.equals(TreeAttribute.TREE_ATTR_PARENT)){//树节点parent对象
			if(value!=null&&value instanceof Domain) {
				Map<String, Object> parentValues = 
					TreeUtils.getValueMapFromDomain((Domain)value);
				Object parentIdObject = parentValues.get(TreeAttribute.TREE_ATTR_ID);
				if(parentIdObject!=null){
					setParentId(parentIdObject.toString());
				}
			}
		}else if(attribute.equals(TreeAttribute.TREE_ATTR_PID)){//树节点parentId
			setParentId(value.toString());
		}else if(attribute.equals(TreeAttribute.TREE_ATTR_CODE)){//树节点编码
			setCode(value.toString());
		}else if(attribute.equals(TreeAttribute.TREE_ATTR_TEXT)){//树节点文本
			setText(value.toString());
		}else if(attribute.equals(TreeAttribute.TREE_ATTR_GOURP)){//树节点组
			setGroup(value.toString());
		}else if(attribute.equals("src")){//树节点src
			setSrc(value.toString());
		}else if(attribute.equals("href")){//树节点链接
			setHref(value.toString());
		}else if(attribute.equals("num")){//树节点序号
			if(value instanceof Integer){
				this.setNum((Integer)value);
			}
		}else{
			//skip
		}
	}

	/**
	 * TODO 使用模版生成html
	 * 返回html树节点的html文本
	 * @return
	 */
	public String toHtml(boolean isLast,boolean isCheck){
		StringBuffer htmls = new StringBuffer();
		if(id==null)return "";
		String htmlText = text==null?id:text;//html文本
		String htmlTooltips = tooltips==null?htmlText:tooltips;//html的tooltips
		StringBuffer treeNodeClasses = new StringBuffer("treeNode");//html树的节点样式
		String treeTriggerHtml = "",nodeHref = href;
		
		if(group!=null){//节点分组标识
			treeNodeClasses.append(" "+group);//树节点分组
		}
		
		if(icon!=null){//
			treeNodeClasses.append(" use-icon");
		}
		
		if(isCheck){
			check = "check";
		}
		
		if(StringUtils.isEmpty(href)){
			href="javascript:void(0)";
		}
		
		if(check!=null){//复选标识
			treeNodeClasses.append(" check");
			if(!check.equals("check")){
				treeNodeClasses.append(" "+check);
			}
		}
		//末尾节点标识
		if(isLast){
			treeNodeClasses.append(" last");
		}
		// 标识为可展开
		if(children.size()>0||src!=null){
			treeNodeClasses.append(" expandable");
			if(isLast){
				treeNodeClasses.append(" lastExpandable");//ie6样式
			}
			treeTriggerHtml = "<div class=\"tree-trigger\"></div>";
			if(expanded){
				treeNodeClasses.append(" expanded");
			}
		}
		
		htmls.append("<li ");
		if(src!=null){
			htmls.append(" src=\""+src+"\" ");
		}
		if(code!=null){
			htmls.append(" code=\""+code+"\" ");
		}
		htmls.append(" class=\""+treeNodeClasses.toString()+"\" id=\""+id+"\">");
		htmls.append(	treeTriggerHtml);
		
		htmls.append(	"<span");
		if(StringUtils.isNotEmpty(code)){
			htmls.append(" id=\""+code+"\"");
		}
		htmls.append(	" title=\""+htmlTooltips+"\" class=\"tree-span "+treeNodeClasses.toString()+"\">");
		
		if(icon!=null){//节点分组标识
			htmls.append("<span class=\"youi-icon icon-"+icon+"\"></span>");
		}
		
		htmls.append("<a");
		
		if(getDomain()!=null&&StringUtils.isNotEmpty(href)){
			Object target = PropertyUtils.getSimplePropertyValue(getDomain(), "target");
			if("_blank".equals(target)||"subpage".equals(target)){
				htmls.append(" target=\""+target+"\"");
			}
//			nodeHref = href+"?pageId="+this.getId();  
		}
		
		if(StringUtils.isNotEmpty(nodeHref)){
			htmls.append(" href=\""+nodeHref+"\"");
		}
		
		htmls.append(" class=\"tree-a page-link\" >");
		htmls.append(		htmlText);//html树节点的显示文本
		htmls.append(	"</a></span>");
		//子节点
		htmls.append(	childrenHtml(isCheck));//处理子节点
		htmls.append("</li>");
		return htmls.toString();
	}
	
	/**
	 * 生成子节点的html
	 * @return
	 */
	private String childrenHtml(boolean isCheck) {
		StringBuffer htmls = new StringBuffer();
		if(children!=null&&children.size()>0){
			boolean isLastChild;
			htmls.append("<ul ");
			if(!expanded){
				htmls.append(" style=\"display:none;\" ");
			}
			htmls.append(">");
			int index = 0;
			for(TreeNode child:children){
				isLastChild = ++index==children.size();
				htmls.append(((HtmlTreeNode)child).toHtml(isLastChild,isCheck));
			}
			htmls.append("</ul>");
		}
		return htmls.toString();
	}
	
	/**
	 * @return the domain
	 */
	public Domain getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public String toString(){
		return toHtml(true,false);
	}

	public boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
}
