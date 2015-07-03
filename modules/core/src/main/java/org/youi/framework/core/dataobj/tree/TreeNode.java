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

import java.util.List;

import org.youi.framework.core.dataobj.Domain;

/**
 * @功能描述 树节点接口
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Jan 30, 2010
 */
public interface TreeNode extends Domain {
	
	public final static String SOURCE_DATA_STYLE = "source-data";
	/**
	 * 获得父树节点
	 * @return
	 */
	public TreeNode getParent();
	
	/**
	 * 父节点id标识
	 * @return
	 */
	public String getParentId();
	
	/**
	 * 获得树子节点集合
	 * @return
	 */
	public List<TreeNode> getChildren();
	
	/**
	 * 增加子节点
	 * @param treeNode
	 */
	public void addChild(TreeNode treeNode);
	
	/**
	 * 删除子节点
	 * @param treeNode
	 */
	public void removeChild(TreeNode treeNode);
	
	/**
	 * 树节点的唯一标识
	 * @return
	 */
	public String getId();
	
	/**
	 * 树节点的编码
	 * @return
	 */
	public String getCode();
	
	/**
	 * 树节点的显示文本
	 * @return
	 */
	public String getText();
	
	/**
	 * 设置树节点文本
	 * @param text
	 */
	public void setText(String text);

	/**
	 * 设置树group
	 * @param string
	 */
	public void setGroup(String string);

	/**
	 * 设置数Id
	 * @param id
	 */
	public void setId(String id);

	/**
	 * @param string
	 */
	public void setCheck(String string);
	
	public void setSrc(String src);

	/**
	 * @return
	 */
	public Domain getDomain();
	
	public void setDomain(Domain domain); 
	
	public void setExpanded(boolean expanded);
	
	public boolean getExpanded();
	
	public void setLevel(int level);
	
	public int getLevel();
	
	public String getHref();
	
	public void setHref(String href);
}
