/**
 * 
 */
package org.youi.framework.services.data;

import java.io.Serializable;
import java.util.List;

import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.web.view.Message;


/**
 * @author zhyi_12
 *
 */
public class ResContext<T extends Domain> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1607997323023348578L;

	private T record;//单记录
	
	private Message message;//操作返回对象
	
	private int totalCount;//总记录数
	
	private List<T> records;//多记录
	
	private String instanceId;//流程实例ID
	
	private String trancode;//交易码
	
	public T getRecord() {
		return record;
	}

	public void setRecord(T record) {
		this.record = record;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<? extends Domain> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}
	
	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String toString(){
		StringBuffer buf = new StringBuffer();
		if(record!=null){
			buf.append("record:"+record.toString()+";");
		}
		if(records!=null){
			buf.append("records:"+records.toString()+";");
		}
		if(message!=null){
			buf.append("message:"+message.toString()+";");
		}
		return buf.toString();
	}

	public void setTrancode(String trancode) {
		this.trancode = trancode;
	}

	public String getTrancode() {
		return trancode;
	}
}