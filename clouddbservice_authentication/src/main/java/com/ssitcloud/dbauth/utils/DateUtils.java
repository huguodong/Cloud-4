package com.ssitcloud.dbauth.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理工具类
 * <p>2016年4月9日 上午11:24:22
 * @author hjc
 *
 */
@SuppressWarnings("unused")
public class DateUtils {
	
	private static final String sdfs_format = "yyyy-MM-dd HH:mm:ss";
    private static ThreadLocal<DateFormat> sdfsthreadLocal = new ThreadLocal<DateFormat>(); 
 
    public static DateFormat getsdfsDateFormat()   
    {  
        DateFormat df = sdfsthreadLocal.get();  
        if(df==null){  
            df = new SimpleDateFormat(sdfs_format);  
            sdfsthreadLocal.set(df);  
        }  
        return df;  
    }
    
    private static final String sdf_format = "yyyy-MM-dd HH:mm:ss";
    private static ThreadLocal<DateFormat> sdfthreadLocal = new ThreadLocal<DateFormat>(); 
 
    public static DateFormat getsdfDateFormat()   
    {  
        DateFormat df = sdfthreadLocal.get();  
        if(df==null){  
            df = new SimpleDateFormat(sdf_format);  
            sdfthreadLocal.set(df);  
        }  
        return df;  
    } 
    
    private static final String day_format = "yyyy-MM-dd";
    private static ThreadLocal<DateFormat> daythreadLocal = new ThreadLocal<DateFormat>(); 
 
    public static DateFormat getdayDateFormat()   
    {  
        DateFormat df = daythreadLocal.get();  
        if(df==null){  
            df = new SimpleDateFormat(day_format);  
            daythreadLocal.set(df);  
        }  
        return df;  
    }
	
    
    private static final String hour_format = "yyyy-MM-dd";
    private static ThreadLocal<DateFormat> hourthreadLocal = new ThreadLocal<DateFormat>(); 
 
    public static DateFormat gethourDateFormat()   
    {  
        DateFormat df = hourthreadLocal.get();  
        if(df==null){  
            df = new SimpleDateFormat(hour_format);  
            hourthreadLocal.set(df);  
        }  
        return df;  
    }
	
	
	
	/**
	 * 将时间戳时间格式转换
	 * yyyy-MM-dd HH:mm:ss.S 转换成 yyyy-MM-dd HH:mm:ss
	 * <p>2016年4月9日 上午11:36:33
	 * <p>create by hjc
	 * @param date 字符串，格式为yyyy-MM-dd HH:mm:ss.S
	 * @return
	 * @throws ParseException 
	 */
	public static String timestampFormatChange(String date) throws ParseException{
		return getsdfDateFormat().format(getsdfsDateFormat().parse(date));
	}
	
	/**
	 * 将date格式的时间转换成String格式的时间
	 * 格式为：yyyy-MM-dd HH:mm:ss
	 * <p>2016年4月11日 下午2:51:25
	 * <p>create by hjc
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return getsdfDateFormat().format(date);
	}
	
	/**
	 * 将字符串的日期格式转换，转换后的格式精确到日
	 * 
	 * <p>2016年4月9日 下午2:36:20
	 * <p>create by hjc
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static Date dayFormat(String date){
		Date dt = null;
		try{
			dt = getdayDateFormat().parse(date);
		}catch(Exception ex){
			
		}
		return dt;
	}
	
	/**
	 * 将日期格式的日期转换，精确到年月日
	 * 
	 * <p>2016年4月9日 下午2:37:50
	 * <p>create by hjc
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date dayFormat(Date date) throws ParseException {
		return dayFormat(getdayDateFormat().format(date));
	}
	
	/**
	 * 将日期格式的日期转换，精确到 时分秒
	 * 
	 * <p>2016年4月9日 下午4:56:52
	 * <p>create by hjc
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date timeFormat(String date) throws ParseException {
		return getsdfDateFormat().parse(date);
	}
	
	/**
	 * 将日期格式的日期转换，精确到 时分秒
	 * 
	 * <p>2016年4月9日 下午4:57:26
	 * <p>create by hjc
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date timeFormat(Date date) throws ParseException {
		return timeFormat(getsdfDateFormat().format(date));
	}
	
	/**
	 * 时间格式转换，只要时分秒
	 * 
	 * <p>2016年4月11日 下午3:52:35
	 * <p>create by hjc
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date hourFormat(String date) throws ParseException {
		return gethourDateFormat().parse(date);
	}
	
	/**
	 * 获取当天0点时间
	 *
	 * <p>2016年12月20日 下午8:20:27 
	 * <p>create by hjc
	 * @return
	 * @throws ParseException
	 */
	public static Date midHour() throws ParseException {
		Calendar cal = Calendar.getInstance(); 
		cal.set(Calendar.HOUR_OF_DAY, 0); 
		cal.set(Calendar.SECOND, 0); 
		cal.set(Calendar.MINUTE, 0); 
		cal.set(Calendar.MILLISECOND, 0); 
		return gethourDateFormat().parse(gethourDateFormat().format(cal.getTime()));
	}
	/**
	 * 时间格式转换，只要时分秒
	 * 
	 * <p>2016年4月11日 下午3:54:33
	 * <p>create by hjc
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date hourFormat(Date date) throws ParseException {
		return hourFormat(gethourDateFormat().format(date));
	}
	
	/**
	 * 给日期date添加小时数
	 * 
	 * <p>2016年4月9日 下午5:04:20
	 * <p>create by hjc
	 * @param date
	 * @param hours
	 * @return 返回增加hours小时数之后的日期对象
	 */
	public static Date addHours(Date date,int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hours);
		return calendar.getTime();
	}
	
	/**
	 * 给日期date 添加天数
	 *
	 * <p>2016年12月20日 下午5:25:52 
	 * <p>create by hjc
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date,int days){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		return calendar.getTime();
	}
	
	/**
	 * 给日期date添加月数
	 *
	 * <p>2016年4月22日 下午7:58:01
	 * <p>create by hjc
	 * @param date
	 * @param months
	 * @return
	 */
	public static Date addMonths(Date date,int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}
	
	/**
	 * 判断时间date是否在from以及to之间,仅比较时分秒
	 * 
	 * <p>2016年4月11日 下午4:04:39
	 * <p>create by hjc
	 * @param date 要判断的时间
	 * @param from 时间段开始时间
	 * @param to 时间段结束时间
	 * @return
	 */
	public static int isBetweenTime(Date date,Date from, Date to){
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		Calendar c3 = Calendar.getInstance();
		c1.setTime(date);
		return 0;
	}
	
	public  static String timestampToYYYYMMDD(Timestamp time){
		if(time==null){
			return null;
		}
		return getdayDateFormat().format(time);
	}
	
	
	public static void main(String[] args) {
		try {
			Date date = new Date();
			Integer integer = 20;
			date = addMonths(date, integer);
		} catch (Exception e) {
		}
	}
	
	
	
}
