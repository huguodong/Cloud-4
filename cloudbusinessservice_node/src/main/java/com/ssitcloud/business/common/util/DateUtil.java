package com.ssitcloud.business.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;


public class DateUtil {

	private static DateFormat format1=new SimpleDateFormat("yyyyMMdd"); 
	private static DateFormat format2=new SimpleDateFormat("HH:mm:ss");
	//5/14/2014 16:02:50
	private static SimpleDateFormat  format3=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	private static SimpleDateFormat  format4=new SimpleDateFormat("yyyy-MM-dd");
	private static DateFormat format5=new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat  format6=new SimpleDateFormat("yyyy-MM-dd");
	private static DateFormat format7= new SimpleDateFormat("yyyyMMddHHmmss");
	private static DateFormat format8= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String dateToYYYYmmddHHmmss(Date date){
		return format7.format(date);
	}
	public static String getDate(Date date)
	{
		if(null == date) return "";
		return format1.format(date);
		
	}
	public static String dateToString(Date date){
		return format4.format(date);
	}
	public static String getDateFromDateStr(String date){
		return getDate(stringToDate(date));
	}
	public static String getTimeFromDateStr(String date){
		return getTime(stringToDate(date));
	}
	public static String getDateYYYY_MM_DD(Date date)
	{
		if(null == date) return "";
		return format5.format(date);
		
	}
	public static String getTime(Date date)
	{
		if(null == date) return "";
		return format2.format(date);
	}
	
	public static String longMillisToString(long time){
		return format8.format(time);
	}
	
	/**
	 * 将完整实践转换为毫秒
	 * 2014-06-19 12:12:12
	 * @param time 格式 2014-06-19 12:12:12
	 * @return
	 */
	public static long stringToMillis(String time){
		if(StringUtils.isBlank(time)) return 0;
		try {
			Date date = format4.parse(time);
			return date.getTime();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 将日期转换为毫秒
	 * @param datestr 格式2014-06-19
	 * @return
	 */
	public static long dateStrToMillis(String datestr){
		if(StringUtils.isBlank(datestr)) return 0;
		try {
			Date date = format6.parse(datestr);
			return date.getTime();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 将日期转换为毫秒
	 * @param datestr 格式2014-06-19
	 * @return
	 */
	public static long dateStrToMillisFormat8(String datestr){
		if(StringUtils.isBlank(datestr)) return 0;
		try {
			Date date = format8.parse(datestr);
			return date.getTime();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static Date stringToDate(String time){
		if(StringUtils.isBlank(time)) return null;
		try {
			return format4.parse(time);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static void main(String args[]){
		try {
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
