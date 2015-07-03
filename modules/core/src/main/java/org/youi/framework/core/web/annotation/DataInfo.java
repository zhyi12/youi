/**
 * 
 */
package org.youi.framework.core.web.annotation;

import java.lang.annotation.*;

/**
 * <p>Title: 用于描述DataDispatchController类</p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) Apr 28, 2008</p>
 * <p>Company: </p>
 * @author zhouyi
 * @version 1.0
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataInfo {
	String text();//文本描述
	
	String functionId();//编码
	
	boolean log() default false;//记录业务日记
}
