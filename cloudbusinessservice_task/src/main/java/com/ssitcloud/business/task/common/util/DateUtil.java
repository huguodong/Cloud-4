package com.ssitcloud.business.task.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	/**
	 * 
	 * author huanghuang 2017年2月27日 下午6:03:45
	 * 
	 * @param taskTime
	 *            启动任务时间
	 * @param type
	 *            任务类型
	 * @return
	 */
	public static boolean taskTimeEqSystemTime(long current, long taskTime, int type) {
		// long current = System.currentTimeMillis();
		switch (type) {
		case 1:
			return toYear(taskTime).equals(toYear(current));
		case 2:
			return toMonth(taskTime).equals(toMonth(current));
		case 3:
			return toWeek(taskTime).equals(toWeek(current));
		case 4:
			return toDay(taskTime).equals(toDay(current));
		default:
			return false;
		}
	}

	// 判定日相等
	public static String toDay(long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		Date d = new Date(date);
		return sdf.format(d);
	}

	// 判定周相等
	public static String toWeek(long date) {
		Date d = new Date(date);
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w] + toDay(date);
	}

	// 判定月相等
	public static String toMonth(long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("ddHHmm");
		Date d = new Date(date);
		return sdf.format(d);
	}

	// 判定年相等
	public static String toYear(long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmm");
		Date d = new Date(date);
		return sdf.format(d);
	}
	
	public static String TimestampToStr(Timestamp t) {// Timestamp转String得yyyyMMdd
		String tsStr = "";
		DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			tsStr = sdf.format(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tsStr;

	}

	/**
	 * 通过日期转星期几 author huanghuang 2017年3月2日 上午11:25:07
	 * 
	 * @param date
	 *            日期格式为yyMMdd
	 * @return
	 */
	public static Integer dateToWeek(String date) {
		final Integer dayNames[] = { 0, 1, 2, 3, 4, 5, 6 };
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		Date d = new Date();
		try {
			d = sdfInput.parse(date.replaceAll("-", ""));
		} catch (ParseException e) {

		}
		calendar.setTime(d);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek < 0)
			dayOfWeek = 0;
		return dayNames[dayOfWeek];
	}
}
