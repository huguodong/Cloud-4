package com.ssitcloud.dbstatistics.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public static String currentDate(){
		Calendar calendar = Calendar.getInstance();  
	    calendar.setTime(new Date());//初始化 Calendar 对象  
	    return (new SimpleDateFormat("yyyyMMdd")).format(calendar.getTime());  
	}
}
