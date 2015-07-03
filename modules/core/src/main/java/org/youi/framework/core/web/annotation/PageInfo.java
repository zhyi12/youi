/**
 * 
 */
package org.youi.framework.core.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于描述DataDispatchController类
 * 控制映射的DataInDomain里属性的数据权限
 * @author Administrator
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageInfo {

	String pageId();//页面唯一编码
	
	String text();//页面描述文本

	String[] datas() default {};//页面数据
	
	String[] subPages() default {};//子页面

	String page() default "";//页面模版文件地址
	
	boolean menu() default true;//是否生成菜单
	
	boolean log() default false;//记录业务日记
}