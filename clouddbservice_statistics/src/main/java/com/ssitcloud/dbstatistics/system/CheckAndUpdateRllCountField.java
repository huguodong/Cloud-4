package com.ssitcloud.dbstatistics.system;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import com.ssitcloud.dbstatistics.rllcount.service.RllCountService;
import com.ssitcloud.devmgmt.entity.RllCountEntity;

public class CheckAndUpdateRllCountField {
	@Resource
	private RllCountService rllCountService;

	/**
	 * 每天凌点定时检查实时数据表visitors_everyday,最多只保留一个月的数据 每周数据统计规则：统计上一周每天的人流量
	 * 每月数据统计规则：统计上一月每天的人流量，并删除上月每天实时数据 每年数据统计规则：统计上一年每月的人流量
	 */
	public void checkAndUpdateRllCount() {
		countPriorDayDatas();
		checkAndUpdateRllCountByWeek();
		checkAndUpdateRllCountByMonth();
		checkAndUpdateRllCountByYear();
		
		//一个月以前的每天的日数据
		//deleteDayDataOfPriorMonth();
	}
	/**
	 * 每周数据统计规则：统计上一周每天的人流量
	 */
	public void checkAndUpdateRllCountByWeek() {
		
		//获取当前日前一天的这一周的日统计数据
		String priorDayForWeekStartTime = getPriorDayForWeekStartDate();
		String priorDayForWeekEndTime = getPriorDayForWeekEndDate();
		
		Timestamp start_time = Timestamp.valueOf(priorDayForWeekStartTime);
		Timestamp end_time = Timestamp.valueOf(priorDayForWeekEndTime);
		
		RllCountEntity rllCountEntity = new RllCountEntity();
		rllCountEntity.setStart_time(start_time);
		rllCountEntity.setEnd_time(end_time);
		
		//删除旧数据
		int delCount = rllCountService.deleteVisitorsEveryWeekByUpdateTime(rllCountEntity);
		System.out.println("【周数据统计,删除总计】：" + delCount);
		
		List<RllCountEntity> priorDayOfWeekRllCounts  = rllCountService.selectVisitorsEveryDay(rllCountEntity);
		//统计前一天的这一周的日统计数据
		List<RllCountEntity> newRllCountLists = countPriorRllCounts(priorDayOfWeekRllCounts,start_time ,end_time,true);
		//保存新的统计数据
		int saveCount = rllCountService.insertVisitorsEveryWeek(newRllCountLists);
		
		System.out.println("【周数据统计,新增总计】：" + saveCount);
	}

	/**
	 * 每月数据统计规则：统计上一月每天的人流量，并删除上月每天实时数据
	 */
	public void checkAndUpdateRllCountByMonth() {
		// 获取当前日期前一天所在月开始时间
		String priorDayStartTime = getPriorDayForMonthStartDate();
		String priorDayEndTime = getPriorDayForMonthEndDate();
		
		RllCountEntity rllCountEntity = new RllCountEntity();
		Timestamp start_time = Timestamp.valueOf(priorDayStartTime);
		Timestamp end_time = Timestamp.valueOf(priorDayEndTime);
		rllCountEntity.setEnd_time(end_time);
		rllCountEntity.setStart_time(start_time);
		
		//删除旧数据
		int delCount = rllCountService.deleteVisitorsEveryMonthByUpdateTime(rllCountEntity);
		
		System.out.println("【月数据统计,删除总计】：" + delCount);
		
		//获取当前日期前一天所在月时间段内统计数据
		List<RllCountEntity> priorDayOfMonthRllCounts =rllCountService.selectVisitorsEveryDay(rllCountEntity);
		
		//统计当前日期前一天所在月时间段内周数据
		List<RllCountEntity> newRllCountLists = countPriorRllCounts(priorDayOfMonthRllCounts,start_time ,end_time ,true);
		
		//保存统计当前日期前一天所在月时间段内周数据
		
		int saveCount = rllCountService.insertVisitorsEveryMonth(newRllCountLists);
		
		System.out.println("【月数据统计,新增总计】：" + saveCount);
		
		
	}

	/**
	 * 每年数据统计规则：统计上一年每月的人流量
	 */
	private void checkAndUpdateRllCountByYear() {
		// 获取当前日期前一天所在年开始时间
		String priorDayStartTime = getPriorDayForYearStartDate();
		String priorDayEndTime = getPriorDayForYearEndDate();
		
		RllCountEntity rllCountEntity = new RllCountEntity();
		Timestamp start_time = Timestamp.valueOf(priorDayStartTime);
		Timestamp end_time = Timestamp.valueOf(priorDayEndTime);
		rllCountEntity.setEnd_time(end_time);
		rllCountEntity.setStart_time(start_time);
		
		//删除旧数据
		int delCount = rllCountService.deleteVisitorsEveryYearByUpdateTime(rllCountEntity);
		System.out.println("【年数据统计,删除总计】：" + delCount);
		
		//获取当前日期前一天所在年时间段内月统计数据
		List<RllCountEntity> priorDayOfMonthRllCounts =rllCountService.selectVisitorsEveryMonth(rllCountEntity);
		
		//统计当前日期前一天所在月时间段内月数据
		List<RllCountEntity> newRllCountLists = countPriorRllCounts(priorDayOfMonthRllCounts,start_time ,end_time,true);
		
		//保存统计当前日期前一天所在月时间段内月数据
		int saveCount = rllCountService.insertVisitorsEveryYear(newRllCountLists);
		
		System.out.println("【年数据统计,新增总计】：" + saveCount);
	}

	
	/**
	 * 删除当前日期一个月以前日数据
	 */
	private void deleteDayDataOfPriorMonth() {
		// 获取上个月的起始日期
		String priorMonthDate = getPriorMonthDate() + " 00:00:00";
		RllCountEntity rllCountEntity = new RllCountEntity();
		Timestamp start_time = Timestamp.valueOf(priorMonthDate);
		rllCountEntity.setStart_time(start_time);
		// 删除上个月的以前的数据
		int deleteCount = rllCountService.deleteVisitorsEveryDayByTime(rllCountEntity);
		System.out.println("   【删除上个月实时数据,删除总数为】： " + deleteCount);

	}
	/**
	 * 获取前一个月的日期
	 * @return
	 */
	private String getPriorMonthDate(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        Calendar c = Calendar.getInstance();  
        c.setTime(new Date());  
        c.add(Calendar.MONTH, -1);  
        Date m = c.getTime();  
        String mon = format.format(m);  
        return mon;
	}
	/**
	 * 获取昨天的日期
	 * @return
	 */
	private String getPriorDayDate(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();  
		c.setTime(new Date());
		c.add(Calendar.DATE, -1);
		Date d = c.getTime();
		String day = format.format(d);
		return day;
	}
	/**
	 * 获取昨天的周开始时间日期
	 * @return
	 */
	private String getPriorDayForWeekStartDate(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -1);
		Date d = calendar.getTime();
		
		calendar.setTime(d);
		calendar.add(Calendar.WEEK_OF_YEAR, 0);
		
		
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {// 如果当前不是星期一
			calendar.add(Calendar.DATE, -1);// 减一天
		}
		StringBuffer endStra = new StringBuffer().append(
				format.format(calendar.getTime())).append(" 00:00:00");
		
		
		return endStra.toString();
	}
	/**
	 * 获取昨天的周结束时间日期
	 * @return
	 */
	private String getPriorDayForWeekEndDate(){
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -1);
		Date d = calendar.getTime();
		
		calendar.setTime(d);
		calendar.add(Calendar.WEEK_OF_YEAR, 0);
		
		
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {// 如果当前不是星期一
			calendar.add(Calendar.DATE, -1);// 减一天
		}
		
		calendar.set(Calendar.DAY_OF_WEEK,
				calendar.getMaximum(Calendar.DAY_OF_WEEK));
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			calendar.add(Calendar.DATE, 1);
		}
		StringBuffer endStra = new StringBuffer().append(
				dft.format(calendar.getTime())).append(" 23:59:59");
		return endStra.toString();
	}
	/**
	 * 获取昨天的月开始时间
	 * @return
	 */
	private String getPriorDayForMonthStartDate(){
		
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -1);
		Date d = calendar.getTime();
		
		calendar.setTime(d);
		
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		StringBuffer endStra = new StringBuffer().append(
				dft.format(calendar.getTime())).append(" 00:00:00");
		
		return endStra.toString();
	}
	/**
	 * 获取昨天的月结束时间
	 * @return
	 */
	private String getPriorDayForMonthEndDate(){
		
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -1);
		Date d = calendar.getTime();
		
		calendar.setTime(d);
		
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		StringBuffer endStra = new StringBuffer().append(
				dft.format(calendar.getTime())).append(" 23:59:59");
		
		return endStra.toString();
	}
	/**
	 * 获取昨天的年开始时间
	 * @return
	 */
	private String getPriorDayForYearStartDate(){
		
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -1);
		Date d = calendar.getTime();
		
		calendar.setTime(d);
		
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, 0);
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		StringBuffer endStra = new StringBuffer().append(
				dft.format(calendar.getTime())).append(" 00:00:00");
		
		return endStra.toString();
	}
	/**
	 * 获取昨天的年结束时间
	 * @return
	 */
	private String getPriorDayForYearEndDate(){
		
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -1);
		Date d = calendar.getTime();
		
		calendar.setTime(d);
		
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, 0);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
		StringBuffer endStra = new StringBuffer().append(
				dft.format(calendar.getTime())).append(" 23:59:59");
		
		return endStra.toString();
	}

	/**
	 * 统计前一天的数据
	 */
	private void countPriorDayDatas() {
		// 昨天的日期
		String priorDayDate = getPriorDayDate();
		String startTime = priorDayDate + " 00:00:00";
		// 昨天的结束日期
		String endTime = priorDayDate + " 23:59:59";

		RllCountEntity rllCountEntity = new RllCountEntity();

		Timestamp start_time = Timestamp.valueOf(startTime);
		Timestamp end_time = Timestamp.valueOf(endTime);
		rllCountEntity.setStart_time(start_time);
		rllCountEntity.setEnd_time(end_time);
		// 1.获取昨天日数据
		List<RllCountEntity> priorRllCountLists = rllCountService.selectVisitorsEveryDay(rllCountEntity);
		//整理需要删除旧数据
		List<RllCountEntity> delRllCountLists =dealDatas(priorRllCountLists) ;
		// 2.统计昨天日数据
		List<RllCountEntity> newRllCountLists = countPriorRllCounts(priorRllCountLists,null,null,false);
		
		// 3.保存昨天统计数据
		int savaPriorDatas = rllCountService.insertVisitorsEveryDayBatch(newRllCountLists);
		
		System.out.println("   【保存昨天统计数据,总数为】： " + savaPriorDatas);
		
		// 4.删除旧的日统计数据
		
		int deleteCount = deletePriorDatas(delRllCountLists);
		
		System.out.println("   【删除旧的日统计数据,总数为】： " + deleteCount);
		
	}
	/**
	 * 删除旧的日统计数据
	 * @param newRllCountLists
	 */
	private int  deletePriorDatas(List<RllCountEntity> newRllCountLists) {
		//整理数据
		List<RllCountEntity> delRllCountLists =dealDatas(newRllCountLists);
		//删除数据
		return rllCountService.deleteVisitorsEveryDayBatchById(delRllCountLists);
	}
	/**
	 *  整理数据
	 * @param newRllCountLists
	 * @return
	 */
	private List<RllCountEntity> dealDatas(List<RllCountEntity> newRllCountLists) {
		List<RllCountEntity> delRllCountLists = new ArrayList<>();
		// 整理数据
		if (newRllCountLists == null || newRllCountLists.size() <= 0) {
			return delRllCountLists;
		}
		for (RllCountEntity vo : newRllCountLists) {
			RllCountEntity delRllCountVo = new RllCountEntity();
			delRllCountVo.setEveryday_idx(vo.getEveryday_idx());
			delRllCountVo.setEveryweek_idx(vo.getEveryweek_idx());
			delRllCountVo.setEverymonth_idx(vo.getEverymonth_idx());
			delRllCountVo.setEveryyear_idx(vo.getEveryyear_idx());
			delRllCountLists.add(delRllCountVo);
		}
		return delRllCountLists;
	}
	/**
	 * 按图书馆按设备统计数据
	 * @param priorRllCountLists
	 * @param start_time
	 * @param end_time
	 * @param flag 是否需要跟新updatetime
	 * @return
	 */
	private List<RllCountEntity> countPriorRllCounts( List<RllCountEntity> priorRllCountLists,Timestamp start_time ,Timestamp end_time,boolean flag) {
		List<RllCountEntity> newRllCountLists = new ArrayList<>();
		if(priorRllCountLists==null || priorRllCountLists.size()<=0){
			return newRllCountLists;
		}
		//合并数据
		Map <String ,List<RllCountEntity>> rllCountMap= mergrRllCountData(priorRllCountLists);
		//统计数据
		newRllCountLists =countRllCountData(rllCountMap,start_time,end_time,flag);
		return newRllCountLists;
	}
	/**
	 * 统计数据
	 * @param rllCountMap
	 * @param start_time
	 * @param end_time
	 * @param flag 是否需要跟新updatetime
	 * @return
	 */
	private List<RllCountEntity> countRllCountData(
			Map<String, List<RllCountEntity>> rllCountMap,Timestamp start_time ,Timestamp end_time,boolean flag) {
		
		List<RllCountEntity> newRllCountLists = new ArrayList<>();
		// 昨天的日期
		String priorDayDate = getPriorDayDate() + " 23:59:59";
		Timestamp yestadayTime =Timestamp.valueOf(priorDayDate);
		
		for (Map.Entry<String ,List<RllCountEntity>> entry : rllCountMap.entrySet()) {  
			String key = entry.getKey();
			List<RllCountEntity> temList = rllCountMap.get(key);
			RllCountEntity newRllCountVo = new RllCountEntity();
			Integer inCount = 0;
			Integer outCount = 0;
			int  libx =0;
			int device_idx =0 ;
			String libId="";
			String deviceId = "";
			String devicetType = "";
			Timestamp update_time =new Timestamp(System.currentTimeMillis()) ;
			
			for (RllCountEntity vo : temList) {
				inCount += vo.getIn_count();
				outCount += vo.getOut_count();
				
				if (vo.getLib_idx() != null) {

					libx=vo.getLib_idx();
				}
				if(vo.getLib_id()!=null){
					
					libId =vo.getLib_id();
				}
				if (vo.getDevice_id() != null) {

					deviceId = vo.getDevice_id();
				}
				if(vo.getDevice_idx()!=null){
					
					device_idx  = vo.getDevice_idx();
				}
				if(vo.getUpdate_time()!=null){
					
					update_time  = vo.getUpdate_time();
				}
				if(vo.getDevice_type()!=null){
					
					devicetType  = vo.getDevice_type();
				}

			}
			newRllCountVo.setDevice_type(devicetType);
			newRllCountVo.setIn_count(inCount);
			newRllCountVo.setOut_count(outCount);
			newRllCountVo.setLib_id(libId);
			newRllCountVo.setLib_idx(libx);
			newRllCountVo.setDevice_id(deviceId);
			newRllCountVo.setDevice_idx(device_idx);
			if(flag){
				
				newRllCountVo.setUpdate_time(yestadayTime);
			}else{
				newRllCountVo.setUpdate_time(update_time);
			}
			if(start_time!=null){
				newRllCountVo.setStart_time(start_time);
			}
			if(end_time!=null){
				newRllCountVo.setEnd_time(end_time);
			}
			
			newRllCountLists.add(newRllCountVo);
		}
		
		return newRllCountLists;
	}
	/**
	 * 合并数据
	 * @param rllCountLists
	 * @return
	 */
	private Map<String, List<RllCountEntity>> mergrRllCountData(List<RllCountEntity> rllCountLists) {
		Map <String ,List<RllCountEntity>> rllCountMap =new HashMap<>();
		
		for (RllCountEntity vo : rllCountLists) {
			List<RllCountEntity> newRllCountMaps = new ArrayList<>();
			int libx = vo.getLib_idx();
			String deviceId = vo.getDevice_id();
			String key = deviceId+"-##-"+libx;
			if(rllCountMap.containsKey(key)){
				rllCountMap.get(key).add(vo);
			}
			if(!rllCountMap.containsKey(key)){
				newRllCountMaps.add(vo);
				rllCountMap.put(key,newRllCountMaps);
			}
		}
		return rllCountMap;
	}
}
