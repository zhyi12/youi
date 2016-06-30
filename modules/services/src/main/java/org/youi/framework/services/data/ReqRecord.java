/**
 * 
 */
package org.youi.framework.services.data;

import java.util.LinkedHashMap;

import org.youi.framework.core.dataobj.Domain;


/**
 * @author zhyi_12
 *
 */
public class ReqRecord extends LinkedHashMap<String,Object> implements Domain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7436175254341511032L;

	public ReqRecord(ReqContext<?> req) {
		this.putAll(req);
	}

}
