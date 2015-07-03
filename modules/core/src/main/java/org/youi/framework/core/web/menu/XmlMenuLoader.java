/*

* @(#)XmlMenuLoader.java  1.0.0 下午11:47:04

* Copyright 2013 gicom, Inc. All rights reserved.

*/
package org.youi.framework.core.web.menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.youi.framework.core.dataobj.tree.HtmlTreeNode;
import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.core.dataobj.tree.TreeNodeConfig;
import org.youi.framework.core.dataobj.tree.TreeUtils;

/**
 * <p></p>
 * @author 
 * @version 1.0.0
 * @see    
 * @since 
 */
public class XmlMenuLoader {
	private ResourceLoader resourceLoader;
	
	public XmlMenuLoader(ResourceLoader resourceLoader){
		this.resourceLoader = resourceLoader;
	}
	
	public HtmlTreeNode load(String resourcePath){
		
		Resource resource = 
			resourceLoader.getResource(resourcePath);
		if(resource!=null){
			Document doc = parseDocument(resource);
			
			if(doc!=null){
				return TreeUtils.listToHtmlTree(getXmlMenus(doc), 
						null, resourcePath,new TreeNodeConfig(){
							public void render(TreeNode treeNode) {
								treeNode.setGroup("menu-item");
							}
				});
			}
		}
		return null;
	}
	

	/**
	 * @param menuResource
	 * @return
	 */
	public List<IMenu> getMenus(String menuResource) {
		Resource resource = 
			resourceLoader.getResource(menuResource);
		if(resource!=null){
			Document doc = parseDocument(resource);
			
			if(doc!=null){
				return this.getXmlMenus(doc);
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private List<IMenu> getXmlMenus(Document doc){
		List<IMenu> menus = new ArrayList<IMenu>();
		if(doc!=null){
			List<Element> menuElements = doc.selectNodes("//menu");
			for(Element element:menuElements){
				IMenu menu = new DefaultMenu();
				menu.setMenuId(element.attributeValue("id"));
				menu.setMenuCaption(element.attributeValue("text"));
				menu.setMenuSrc(element.attributeValue("href"));
				menu.setMenuStyle(element.attributeValue("icon"));
				if(element.getParent().getName().equals("menu")){
					menu.setParentMenuId(element.getParent().attributeValue("id"));
				}
				menu.setTarget(element.attributeValue("target"));
				menus.add(menu);
			}
		}
		return menus;
	}
	
	private Document parseDocument(Resource resource){
		Document doc = null;
		SAXReader saxReader = new SAXReader();
		
		try {
			doc = saxReader.read(resource.getInputStream());
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}
}
