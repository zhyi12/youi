/**
 * 
 */
package org.youi.framework.util;

import org.youi.framework.core.dataobj.Domain;

/**
 * @author Administrator
 *
 */
public class TransLogUtils {
	/**
	 * 保存对象日记
	 * @param save
	 * @return
	 */
	public static String getDomainSaveLog(
			Domain domain,String[][] propertyDescs){
		StringBuffer buffer = new StringBuffer();
		if(domain!=null){
			for(String[] propertyDesc:propertyDescs){
				if(propertyDesc.length==2){
					buffer.append("，");
					buffer.append(propertyDesc[1]);
					buffer.append("【");
					buffer.append(PropertyUtils.getPropertyValue(domain,propertyDesc[0]));
					buffer.append("】");
				}
			}
		}
		return buffer.length()>0?buffer.substring(1):buffer.toString();
	}
}
