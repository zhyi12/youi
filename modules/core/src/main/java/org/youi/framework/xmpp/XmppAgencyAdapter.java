/**
 * 
 */
package org.youi.framework.xmpp;

import org.youi.framework.core.dataobj.tree.TreeNode;

/**
 * @author zhyi_12
 * XMPP组织机构适配器接口
 */
public interface XmppAgencyAdapter {

	/**
	 * 获取机构树
	 * @return
	 */
	public TreeNode getAgencyTree();
}
