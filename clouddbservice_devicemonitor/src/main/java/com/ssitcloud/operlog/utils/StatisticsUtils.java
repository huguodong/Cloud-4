package com.ssitcloud.operlog.utils;

import java.util.HashMap;
import java.util.Map;
/**
 * 流通、财经、办证统计工具类
 * 第一统计结果的表的字段名
 * @author lqw  2017年3月9号
 *
 */
public class StatisticsUtils {
	
	public static Map<String, String> getCountName(){
		Map<String, String> countName = new HashMap<>();
		countName.put("loancir1", "loan_cirYearCount");
		countName.put("returncir1", "return_cirYearCount");
		countName.put("renewcir1", "renew_cirYearCount");
		
		countName.put("loancir2", "loan_cirMonthCount");
		countName.put("returncir2", "return_cirMonthCount");
		countName.put("renewcir2", "renew_cirMonthCount");
		
		countName.put("loancir3", "loan_cirWeekCount");
		countName.put("returncir3", "return_cirWeekCount");
		countName.put("renewcir3", "renew_cirWeekCount");
		
		countName.put("loancir4", "loan_cirDayCount");
		countName.put("returncir4", "ruturn_cirDayCount");
		countName.put("renewcir4", "renew_cirDayCount");
		
		countName.put("finance1", "fineYearCount");
		countName.put("finance2", "fineMonthCount");
		countName.put("finance3", "fineWeekCount");
		countName.put("finance4", "fineDayCount");
		
		countName.put("newCard1", "newCardYearCount");
		countName.put("newCard2", "newCardMonthCount");
		countName.put("newCard3", "newCardWeekCount");
		countName.put("newCard4", "newCardDayCount");
		return countName;
	}

}
