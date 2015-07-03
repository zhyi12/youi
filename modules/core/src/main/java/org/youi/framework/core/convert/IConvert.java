/**
 * 
 */
package org.youi.framework.core.convert;

/**
 * @author zhyi_12
 *
 */
public interface IConvert<T> {

	public T get(T value);
	
	public String getName();

	public String toJson();

}
