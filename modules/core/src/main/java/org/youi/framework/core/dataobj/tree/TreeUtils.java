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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.util.ReflectionUtils;

/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jan 30, 2010
 */
public class TreeUtils {
	private final static Log logger = LogFactory.getLog(TreeUtils.class);
	
	private TreeUtils(){
		
	}
	/**
	 * 获得树节点属性的键值对
	 * @param domain
	 * @return
	 */
	public static Map<String,Object> getValueMapFromDomain(Domain domain){
		Map<String,Object> values = new HashMap<String,Object>();
		TreeAttribute treeAttribute;
		Object value;
		
		//使用对象名小写作为默认的group
		values.put("group", domain.getClass().getSimpleName().toLowerCase());
		//
		List<Method> methods = ReflectionUtils.annotationedGetterMethod(domain.getClass(), TreeAttribute.class);
		for(Method method:methods){
			treeAttribute = method.getAnnotation(TreeAttribute.class);
			if(treeAttribute!=null){
				value = getTreeAttributeMethodValue(domain,method);
				if(value!=null){
					values.put(treeAttribute.value(), value);
				}
			}
		}
		return values;
	}
	
	/**
	 * 把list对象转换为树结构
	 * @param list
	 * @param rootId
	 * @param rootText
	 * @param maxLevel 树的最大层次
	 * @return
	 */
	@SuppressWarnings({"unchecked","rawtypes"})
	public static HtmlTreeNode listToHtmlTree(List list,
			String rootId,
			String rootText,
			int maxLevel){
		return listToHtmlTree(list,rootId,rootText,maxLevel,null);
	}
	
	@SuppressWarnings({"unchecked","rawtypes"})
	public static HtmlTreeNode listToHtmlTree(List list,
			String rootId,
			String rootText){
		return listToHtmlTree(list,rootId,rootText,0,null);
	}
	
	/**
	 * @param list
	 * @param treeNodeConfig
	 * @return
	 */
	public static List<TreeNode> listToTreeNodes(List<? extends Domain> list,
			TreeNodeConfig treeNodeConfig){
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		for(Domain domain:list){
			treeNodes.add(new HtmlTreeNode(domain,treeNodeConfig));
		}
		return treeNodes;
	}
	
	public static HtmlTreeNode listToHtmlTree(List<? extends Domain> list,
			String rootId,
			String rootText,
			TreeNodeConfig treeNodeConfig){
		return listToHtmlTree(list,rootId,rootText,0,treeNodeConfig) ;
	}
	
	/**
	 * 把list对象转换为树结构
	 * @param list
	 * @param rootId
	 * @param rootText
	 * @param treeNodeConfig 树配置
	 * @return
	 */
	public static HtmlTreeNode listToHtmlTree(List<? extends Domain> list,
			String rootId,
			String rootText,
			int maxLevel,
			TreeNodeConfig treeNodeConfig){
		HtmlTreeNode root = new HtmlTreeNode(rootId,rootText,treeNodeConfig);
		root.setGroup("root");
		//组装树
		makeHtmlTree(root,
				listToTreeNodes(list,treeNodeConfig),
				0,
				maxLevel);
		return root;
		
	}
	
	
	/**
	 * @param root
	 * @param treeNodes
	 * @param parentId 此参数主要是避免
	 */
	private static void makeHtmlTree(TreeNode root, 
			List<TreeNode> treeNodes,
			int level,
			int maxLevel) {
		String parentId = root.getId();
		if(!treeNodes.isEmpty()){
			treeNodes.remove(root);//移除root
			List<TreeNode> newTreeNodesList = new ArrayList<TreeNode>(treeNodes);//新建一个存放删除了父节点元素的list
			if(maxLevel>0&&level>=maxLevel)return;
			for(TreeNode treeNode:treeNodes){
				if(isCurrentRootChild(parentId,treeNode)){
					root.addChild(treeNode);//如果匹配，则加入树
					makeHtmlTree(treeNode,newTreeNodesList,level+1,maxLevel);//继续需找下级节点
				}
			}
		}
	}
	
	/**
	 * 比较父节点id是否匹配
	 * @param parentId 父节点ID
	 * @param treeNode 当前比较的节点
	 * @return
	 */
	private static boolean isCurrentRootChild(String parentId,TreeNode treeNode){
		String nodeParentId = treeNode.getParentId();
		
		String fixedParentId = "".equals(parentId)?null:parentId;
		
		return (fixedParentId==null&&nodeParentId==null)||
				(fixedParentId!=null&&fixedParentId.equals(nodeParentId));
	}

	
	/**
	 * 从get方法中反射获得当前方法的返回值
	 * @return
	 */
	private static Object getTreeAttributeMethodValue(Domain domain,Method method){
		String methodName = method.getName();
		if(methodName.startsWith("get")){
			try {
				return method.invoke(domain, new Object[]{});
			} catch (IllegalArgumentException e) {
				logger.error("IllegalArgumentException:"+e.getMessage());
			} catch (IllegalAccessException e) {
				logger.error("IllegalAccessException:"+e.getMessage());
			} catch (InvocationTargetException e) {
				logger.error("InvocationTargetException:"+e.getMessage());
			}
		}else{
			logger.warn("请在get方法上使用TreeAttribute注释！");
		}
		return null;
	}
}
