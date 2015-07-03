/**
 * 
 */
package org.youi.framework.core.orm;

import java.util.List;

/**
 * @author Administrator
 * 
 */
public class PagerRecords implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8874497501408426939L;

	@SuppressWarnings("rawtypes")
	private List records;//记录集
	
	private int totalCount;//总记录条数

	private Pager pager;//当前分页信息
	
	@SuppressWarnings("rawtypes")
	public PagerRecords(List records, int totalCount) {
		this.records = records;
		this.totalCount = totalCount;
	}
	@SuppressWarnings("rawtypes")
	public List getRecords() {
		return records;
	}
	@SuppressWarnings("rawtypes")
	public void setRecords(List records) {
		this.records = records;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * @return the pager
	 */
	public Pager getPager() {
		return pager;
	}
	/**
	 * @param pager the pager to set
	 */
	public void setPager(Pager pager) {
		this.pager = pager;
	}
}
