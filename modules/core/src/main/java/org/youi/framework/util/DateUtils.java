package org.youi.framework.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	/**
	 * 返回格式：2007-08-14
	 * @return
	 */
	public static String getToday(){
		String time = "";
		time = getToday("yyyy-MM-dd");
		return time;
	}
	/**
	 * 
	 * @param format 根据指定的格式时间类型返回当前时间
	 * @return
	 */
	public static String getToday(String format){
		return getDateStr(Calendar.getInstance().getTime(),format);
	}
	
	/**
	 * 日期转字符
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateStr(Date date,String format){
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	/**
	 * @param millis
	 * @return
	 */
	public static Date parseMills(long millis){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
	
	/**
	 * 考试年份编码
	 * @return
	 */
	public static String getYearCode(){
		Calendar cal = Calendar.getInstance();
    	int year = cal.get(Calendar.YEAR);
    	String yearStr = new Integer(year).toString();
    	return yearStr.substring(2,4);//当前年份后两位
	}
	
	public static void main(String[] args){
		String dateStr = DateUtils.getDateStr(parseMills(1350994695000l), "yyyy-MM-dd HH:mm:ss");
		System.out.println(dateStr);
		
	}
}
