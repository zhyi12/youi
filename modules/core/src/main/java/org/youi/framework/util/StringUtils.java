/*

* @(#)StringUtils.java  1.0.0 下午04:11:47

* Copyright 2013 gicom, Inc. All rights reserved.

*/
package org.youi.framework.util;


/**
 * @功能描述
 * @作者  zhyi_12
 * @版本 1.0.0
 * @创建时间 Dec 15, 2010
 */
public class StringUtils extends org.springframework.util.StringUtils{
	
	private final static int FLAG_BEG = 1;
	private final static int FLAG_END = 9;
	/**
	 * null或空字符的串
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		return str==null||str.equals("");
	}

	/**
	 * 非null和空字符的串
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	/**
	 * 首字母大写
	 * @param str
	 * @return
	 */
	public static String upperCaseFirstLetter(String str){
		if(str==null||str.length()==0)return str;
		return str.substring(0,1).toUpperCase()+str.substring(1);
	}
	
	/**
	 * 首字母小写
	 * @param str
	 * @return
	 */
	public static String lowerCaseFirstLetter(String str){
		if(str==null||str.length()==0)return str;
		return str.substring(0,1).toLowerCase()+str.substring(1);
	}
	
	/**
	 * @param strs
	 * @return
	 */
	public static String findNotEmpty(String... strs){
		for(String str:strs){
			if(isNotEmpty(str)){
				return str;
			}
		}
		return null;
	}
	
	/**
	 * &#...;格式的10进制串转换成中文
	 * @param str
	 * @return
	 */
	public static String decodeXmlText(String str) {
		if(isEmpty(str)){
			return null2Empty(str);
		}
		
		StringBuffer results = new StringBuffer();
		int mflag = -1;
		int begPos = 0,endPos = 0;
		for(int i=0;i<str.length();i++){
			char c = str.charAt(i);
			if(i>0&&c=='#'&&str.substring(i-1,i+1).equals("&#")){
				//开始计算
				mflag = FLAG_BEG;
				begPos = i;
			}else if(c==';'&&mflag ==FLAG_BEG){
				mflag = FLAG_END;
				endPos = i;
			}
			if(mflag==FLAG_BEG){
				//跳过
			}else if(mflag==FLAG_END&&endPos>begPos){
				results.deleteCharAt(results.length()-1);
				results.append(String.valueOf((char) Integer.valueOf(str.substring(begPos+1,endPos), 10).intValue()));
				mflag = -1;
			}else{
				results.append(c);
			}
		}
		return results.toString();
	}

	/**
	 * null转空
	 * @param str
	 * @return
	 */
	public static String null2Empty(String str) {
		return str == null ? "" : str;
	}
}

