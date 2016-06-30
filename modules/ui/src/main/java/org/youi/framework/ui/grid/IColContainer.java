/**
 * 
 */
package org.youi.framework.ui.grid;

import javax.servlet.jsp.tagext.Tag;


/**
 * <p>Title:SDC UI组件</p>
 *
 * <p>Description:</p>
 *
 * <p>Copyright:Copyright (c) 2011.11</p>
 *
 * <p>Company:iSoftstone</p>
 *
 * @author zhouyi
 * @version 1.0.0
 */
public interface IColContainer  extends Tag{
	/**
	 * 添加列
	 * @param gridCol
	 */
	public void addCol(GridCol gridCol);
}
