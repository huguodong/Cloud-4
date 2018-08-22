package com.ssitcloud.dbstatistics.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbstatistics.common.utils.JsonUtils;
import com.ssitcloud.dbstatistics.dao.CardissueLogStatisticsDao;
import com.ssitcloud.dbstatistics.dao.FinanceLogStatisticsDao;
import com.ssitcloud.dbstatistics.dao.LoanLogStatisticsDao;
import com.ssitcloud.dbstatistics.dao.VisitorLogStatisticsDao;
import com.ssitcloud.dbstatistics.entity.CardissueTempEntity;
import com.ssitcloud.dbstatistics.entity.CirculationTempEntity;
import com.ssitcloud.dbstatistics.entity.FinanceTempEntity;
import com.ssitcloud.dbstatistics.entity.StatisticsTempEntity;
import com.ssitcloud.dbstatistics.entity.VisitorTempEntity;
import com.ssitcloud.dbstatistics.service.MainPageService;
import com.ssitcloud.statistics.entity.StatisticsEntity;
import com.ssitcloud.statistics.entity.StatisticsLoanLogEntity;
import com.ssitcloud.statistics.entity.StatisticsVisitorEntity;

@Service
public class MainPageServiceImpl implements MainPageService {
	@Resource
	CardissueLogStatisticsDao cardissueLogStatisticsDao;
	@Resource
	FinanceLogStatisticsDao financeLogStatisticsDao;
	@Resource
	LoanLogStatisticsDao loanLogStatisticsDao;
	@Resource
	VisitorLogStatisticsDao visitorLogStatisticsDao;

	private static final String T_ALL = "all";
	private static final String T_REALTIME = "realtime";
	private static final String T_TODAY = "today";
	private static final String T_WEEK = "week";
	private static final String T_MONTH = "month";
	private static final String T_YEAR = "year";
	private static final String T_YESTERDAY = "yesterday";
	private static final String T_LASTMONTH = "lastMonth";
	private static final String T_LASTYEAR = "lastYear";

	@Override
	public ResultEntity countCardissueLog(String req) {
		ResultEntity result = new ResultEntity();
		if (StringUtils.hasText(req)) {
			JSONObject json = JSONObject.fromObject(req);
			String type = json.optString("type");
			String chart_type = json.containsKey("chart_type") ? json.optString("chart_type") : "bar";
			Map<String, Object> param = new HashMap<String, Object>();
			String libIdxsStr = json.optString("library_idx");
			List<String> lib_idx = new ArrayList<>();
			if (libIdxsStr!=null && !libIdxsStr.equals("")) {
				String[] idxs = libIdxsStr.split(",");
				for (int i = 0; i < idxs.length; i++) {
					lib_idx.add(idxs[i]);
				}
			}
			int device_idx = json.optInt("device_idx");
			param.put("lib_idx", lib_idx);
			param.put("device_idx", device_idx);
			param.put("chart_type", chart_type);
			param.put("newCardCountType", "34");// 性别
			List<StatisticsEntity> list = null;
			if (T_YESTERDAY.equals(type)) {
				String[] times = formater("yyyyMMdd", type);
				param.put("startTime", times[0]);
				param.put("endTime", times[1]);
				list = cardissueLogStatisticsDao.countCardissueLogForDay(param);
			} else if (T_LASTMONTH.equals(type)) {// 上月
				String[] times = formater("yyyyMM", type);
				param.put("startTime", times[0]);
				param.put("endTime", times[1]);
				list = cardissueLogStatisticsDao.countCardissueLogForMonth(param);
			} else if (T_LASTYEAR.equals(type)) {// 去年
				String[] times = formater("yyyy", type);
				param.put("startTime", times[0]);
				param.put("endTime", times[1]);
				list = cardissueLogStatisticsDao.countCardissueLogForYear(param);
			}
			if (list != null) {
				int size = list.size();
				if (size > 0) {
					if ("pie".equals(chart_type)) {// 饼图
						long total1 = 0;
						long total2 = 0;
						for (StatisticsEntity entity : list) {
							if ("1".equals(entity.getType())) {
								total1 += entity.getTotal();
							} else {
								total2 += entity.getTotal();
							}
						}
						StatisticsTempEntity temp = new StatisticsTempEntity();
						temp.setOpercmds("[{\"name\":\"男\"},{\"name\":\"女\"}]");
						temp.setDatas("[{\"name\":\"男\",\"value\":" + total1 + "},{\"name\":\"女\",\"value\":" + total2 + "}]");
						result.setResult(temp);
						result.setState(true);

					} else {
						CardissueTempEntity temp = new CardissueTempEntity();
						String[] date = new String[size];
						long[] data = new long[size];
						int index = 0;
						for (StatisticsEntity entity : list) {
							date[index] = entity.getTime();
							data[index] = entity.getTotal();
							index++;
						}
						temp.setDate(date);
						temp.setTotal(data);

						result.setState(true);
						result.setResult(temp);
					}
					return result;
				}
				result.setState(false);
				result.setMessage("没有数据");
				return result;
			}
		}
		result.setState(false);
		result.setMessage("统计办证数据失败!");
		return result;
	}

	@Override
	public ResultEntity countLoanLog(String req) {
		ResultEntity result = new ResultEntity();
		if (StringUtils.hasText(req)) {
			JSONObject json = JSONObject.fromObject(req);
			String type = json.optString("type");
			String chart_type = json.containsKey("chart_type") ? json.optString("chart_type") : "bar";
			Map<String, Object> param = new HashMap<String, Object>();
			String libIdxsStr = json.optString("library_idx");
			List<String> lib_idx = new ArrayList<>();
			if (libIdxsStr!=null && !libIdxsStr.equals("")) {
				String[] idxs = libIdxsStr.split(",");
				for (int i = 0; i < idxs.length; i++) {
					lib_idx.add(idxs[i]);
				}
			}
			int device_idx = json.optInt("device_idx");
			param.put("lib_idx", lib_idx);
			param.put("device_idx", device_idx);
			param.put("circulateCountType", "15");// 借还操作结果
			List<StatisticsLoanLogEntity> list = null;
			if (T_YESTERDAY.equals(type)) {
				String[] times = formater("yyyyMMdd", type);
				param.put("startTime", times[0]);
				param.put("endTime", times[1]);
				list = loanLogStatisticsDao.countLoanLogForDay(param);
			} else if (T_LASTMONTH.equals(type)) {// 上月
				String[] times = formater("yyyyMM", type);
				param.put("startTime", times[0]);
				param.put("endTime", times[1]);
				list = loanLogStatisticsDao.countLoanLogForMonth(param);
			} else if (T_LASTYEAR.equals(type)) {// 去年
				String[] times = formater("yyyy", type);
				param.put("startTime", times[0]);
				param.put("endTime", times[1]);
				list = loanLogStatisticsDao.countLoanLogForYear(param);
			}
			if (list != null) {
				int size = list.size();
				if (size > 0) {
					if ("pie".equals(chart_type)) {// 饼图
						long total1 = 0;
						long total2 = 0;
						long total3 = 0;
						for (StatisticsLoanLogEntity entity : list) {
							total1 += entity.getLoan_total();
							total2 += entity.getReturn_total();
							total3 += entity.getRenew_total();
						}
						StatisticsTempEntity temp = new StatisticsTempEntity();
						temp.setOpercmds("[{\"name\":\"借书\"},{\"name\":\"还书\"},{\"name\":\"续借\"}]");
						temp.setDatas("[{\"name\":\"借书\",\"value\":" + total1 + "},{\"name\":\"还书\",\"value\":" + total2 + "},{\"name\":\"续借\",\"value\":" + total3 + "}]");
						result.setResult(temp);
						result.setState(true);

					} else {
						CirculationTempEntity temp = new CirculationTempEntity();
						String[] date = new String[size];
						long[] data1 = new long[size];
						long[] data2 = new long[size];
						long[] data3 = new long[size];
						int index = 0;
						for (StatisticsLoanLogEntity entity : list) {
							date[index] = entity.getTime();
							data1[index] = entity.getLoan_total();
							data2[index] = entity.getReturn_total();
							data3[index] = entity.getRenew_total();
							index++;
						}
						temp.setDate(date);
						temp.setLoan_total(data1);
						temp.setReturn_total(data2);
						temp.setRenew_total(data3);

						result.setState(true);
						result.setResult(temp);
					}
					return result;
				}
				result.setState(false);
				result.setMessage("没有数据");
				return result;
			}
		}

		result.setState(false);
		result.setMessage("统计借还数据失败!");
		return result;
	}

	@Override
	public ResultEntity countFinanceLog(String req) {
		ResultEntity result = new ResultEntity();
		if (StringUtils.hasText(req)) {
			JSONObject json = JSONObject.fromObject(req);
			String type = json.optString("type");
			String chart_type = json.containsKey("chart_type") ? json.optString("chart_type") : "bar";
			Map<String, Object> param = new HashMap<String, Object>();
			String libIdxsStr = json.optString("library_idx");
			List<String> lib_idx = new ArrayList<>();
			if (libIdxsStr!=null && !libIdxsStr.equals("")) {
				String[] idxs = libIdxsStr.split(",");
				for (int i = 0; i < idxs.length; i++) {
					lib_idx.add(idxs[i]);
				}
			}
			int device_idx = json.optInt("device_idx");
			param.put("lib_idx", lib_idx);
			param.put("device_idx", device_idx);
			param.put("chart_type", chart_type);
			param.put("newCardCountType", "22");// 付款类型
			List<StatisticsEntity> list = null;
			if (T_YESTERDAY.equals(type)) {
				String[] times = formater("yyyyMMdd", type);
				param.put("startTime", times[0]);
				param.put("endTime", times[1]);
				list = financeLogStatisticsDao.countFinanceLogForDay(param);
			} else if (T_LASTMONTH.equals(type)) {// 上月
				String[] times = formater("yyyyMM", type);
				param.put("startTime", times[0]);
				param.put("endTime", times[1]);
				list = financeLogStatisticsDao.countFinanceLogForMonth(param);
			} else if (T_LASTYEAR.equals(type)) {// 去年
				String[] times = formater("yyyy", type);
				param.put("startTime", times[0]);
				param.put("endTime", times[1]);
				list = financeLogStatisticsDao.countFinanceLogForYear(param);
			}
			if (list != null) {
				int size = list.size();
				if (size > 0) {
					if ("pie".equals(chart_type)) {// 饼图
						long total1 = 0;
						long total2 = 0;
						long total3 = 0;
						long total4 = 0;
						long total5 = 0;
						long total6 = 0;
						for (StatisticsEntity entity : list) {
							if ("1".equals(entity.getType())) {
								total1 += entity.getTotal();
							} else if ("2".equals(entity.getType())) {
								total2 += entity.getTotal();
							} else if ("3".equals(entity.getType())) {
								total3 += entity.getTotal();
							} else if ("4".equals(entity.getType())) {
								total4 += entity.getTotal();
							} else if ("5".equals(entity.getType())) {
								total5 += entity.getTotal();
							} else if ("6".equals(entity.getType())) {
								total6 += entity.getTotal();
							}
						}
						StatisticsTempEntity temp = new StatisticsTempEntity();
						temp.setOpercmds("[{\"name\":\"现金收款\"},{\"name\":\"划账支出\"},{\"name\":\"ACS扣缴\"},{\"name\":\"一卡通扣缴\"},{\"name\":\"支付宝扣缴\"},{\"name\":\"微信扣缴\"}]");
						temp.setDatas("[{\"name\":\"现金收款\",\"value\":" + total1 + "},{\"name\":\"划账支出\",\"value\":" + total2 + "},{\"name\":\"ACS扣缴\",\"value\":" + total3
								+ "},{\"name\":\"一卡通扣缴\",\"value\":" + total4 + "},{\"name\":\"支付宝扣缴\",\"value\":" + total5 + "},{\"name\":\"微信扣缴\",\"value\":" + total6 + "}]");
						result.setResult(temp);
						result.setState(true);

					} else {
						FinanceTempEntity temp = new FinanceTempEntity();
						String[] date = new String[size];
						double[] data = new double[size];
						int index = 0;
						for (StatisticsEntity entity : list) {
							date[index] = entity.getTime();
							data[index] = entity.getTotal();
							index++;
						}
						temp.setDate(date);
						temp.setTotal(data);

						result.setState(true);
						result.setResult(temp);
					}
					return result;
				}
				result.setState(false);
				result.setMessage("没有数据");
				return result;
			}
		}

		result.setState(false);
		result.setMessage("统计财经数据失败!");
		return result;
	}

	@Override
	public ResultEntity countVisitorLog(String req) {
		ResultEntity result = new ResultEntity();
		if (StringUtils.hasText(req)) {
			JSONObject json = JSONObject.fromObject(req);
			String type = json.optString("type");
			// String chart_type = json.containsKey("chart_type")? json.optString("chart_type"):"bar";
			String device_type = json.containsKey("device_type") ? json.optString("device_type") : "Device";
			Map<String, Object> param = new HashMap<String, Object>();
			String libIdxsStr = json.optString("library_idx");
			List<String> lib_idx = new ArrayList<>();
			if (libIdxsStr != null && !"".equals(libIdxsStr.trim()) && !"0".equals(libIdxsStr.trim())) {
				String[] idxs = libIdxsStr.split(",");
				for (int i = 0; i < idxs.length; i++) {
					lib_idx.add(idxs[i]);
				}
			}
			String device_id = json.optString("device_id");
			param.put("lib_idx", lib_idx);
			param.put("device_id", device_id);
			param.put("device_type", device_type);
			List<StatisticsVisitorEntity> list = null;

			if (T_REALTIME.equals(type)) {// 当天实时数据
				String startTime = json.optString("startTime");
				String endTime = json.optString("endTime");

				if (startTime != null && endTime != null) {
					param.put("startTime", startTime);
					param.put("endTime", endTime);
				} else {
					String[] times = getTime(T_TODAY);
					param.put("startTime", times[0] + " 00:00:00");
					param.put("endTime", times[1] + " 23:59:59");
				}
				list = visitorLogStatisticsDao.countRealTimeVisitor(param);
			} else if (T_TODAY.equals(type)) {// 当天
				String[] times = getTime(type);
				param.put("startTime", times[0] + " 00:00:00");
				param.put("endTime", times[1] + " 23:59:59");
				list = visitorLogStatisticsDao.countVisitorLogForDay(param);
			} else if (T_YESTERDAY.equals(type)) {// 昨天
				String[] times = getTime(type);
				param.put("startTime", times[0] + " 00:00:00");
				param.put("endTime", times[1] + " 23:59:59");
				list = visitorLogStatisticsDao.countDayVisitorLogForWeekOrMonth(param);
			} else if (T_LASTMONTH.equals(type)) {// 上月
				String[] times = getTime(type);
				param.put("startTime", times[0] + " 00:00:00");
				param.put("endTime", times[1] + " 23:59:59");
				list = visitorLogStatisticsDao.countDayVisitorLogForWeekOrMonth(param);
			} else if (T_LASTYEAR.equals(type)) {// 去年
				String[] times = getTime(type);
				param.put("startTime", times[0] + " 00:00:00");
				param.put("endTime", times[1] + " 23:59:59");
				list = visitorLogStatisticsDao.countVisitorLogForYear(param);
			} else if (T_WEEK.equals(type)) {// 本周
				String[] times = getTime(type);
				param.put("startTime", times[0] + " 00:00:00");
				param.put("endTime", times[1] + " 23:59:59");
				/** modify by xiebf @20171216 修改成查询天统计的表，前提是天统计要保存起码一周的数据 **/
				list = visitorLogStatisticsDao.countDayVisitorLogForWeekOrMonth(param);
			} else if (T_MONTH.equals(type)) {// 当月
				String[] times = getTime(type);
				param.put("startTime", times[0] + " 00:00:00");
				param.put("endTime", times[1] + " 23:59:59");
				/** modify by xiebf @20171216 修改成查询天统计的表，前提是天统计要保存起码一月的数据 **/
				list = visitorLogStatisticsDao.countDayVisitorLogForWeekOrMonth(param);
			} else if (T_YEAR.equals(type)) {// 当年
				// 今年的历史数据
				String[] times = getTime(type);
				param.put("startTime", times[0] + " 00:00:00");
				param.put("endTime", times[1] + " 23:59:59");
				/** modify by xiebf @20171216 修改成查询月统计的表，前提是月统计要保存起码一年的数据 **/
				List<StatisticsVisitorEntity> monthList = visitorLogStatisticsDao.countMonthVisitorLogForYear(param);

				// 因为延迟一天，需要把今日的数据也加到里面
				times = getTime(T_TODAY);
				param.put("startTime", times[0] + " 00:00:00");
				param.put("endTime", times[1] + " 23:59:59");
				List<StatisticsVisitorEntity> dayList = visitorLogStatisticsDao.countVisitorLogForDay(param);
				list = toList(monthList, dayList, "yyyy-MM", T_YEAR);
			} else if (T_ALL.equals(type)) {// 历史所有
				// 查询所有历史数据（延迟一天）
				List<StatisticsVisitorEntity> yearList = visitorLogStatisticsDao.countVisitorLogForYear(param);

				// 需要把今日的数据也加到里面
				String[] times = getTime(T_TODAY);
				param.put("startTime", times[0] + " 00:00:00");
				param.put("endTime", times[1] + " 23:59:59");
				List<StatisticsVisitorEntity> dayList = visitorLogStatisticsDao.countVisitorLogForDay(param);
				list = toList(yearList, dayList, "yyyy", T_ALL);
			} else {
				String startTime = json.optString("startTime");
				String endTime = json.optString("endTime");

				if (startTime != null && endTime != null) {
					param.put("startTime", startTime);
					param.put("endTime", endTime);
				} else {
					String[] times = getTime(T_TODAY);
					param.put("startTime", times[0] + " 00:00:00");
					param.put("endTime", times[1] + " 23:59:59");
				}

				/** modify by xiebf @20171216 修改成查询天统计的表，前提是天统计要保存起码一周的数据 **/
				list = visitorLogStatisticsDao.countDayVisitorLogForWeekOrMonth(param);
			}
			if (list != null) {
				int size = list.size();
				if (size > 0) {
					VisitorTempEntity temp = new VisitorTempEntity();
					String[] date = new String[size];
					long[] data1 = new long[size];
					long[] data2 = new long[size];
					int index = 0;
					for (StatisticsVisitorEntity entity : list) {
						date[index] = entity.getTime();
						data1[index] = entity.getIn_total();
						data2[index] = entity.getOut_total();
						index++;
					}
					temp.setDate(date);
					temp.setIn_total(data1);
					temp.setOut_total(data2);

					result.setState(true);
					result.setResult(temp);
					return result;
				}
				result.setState(false);
				result.setMessage("没有数据");
				return result;
			}
		}
		result.setState(false);
		result.setMessage("统计人流量数据失败!");
		return result;
	}

	/**
	 * 格式化时间
	 * 
	 * @param format
	 * @param type
	 * @return
	 */
	private String[] formater(String format, String type) {
		DateFormat sdf = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		if (T_LASTMONTH.equalsIgnoreCase(type)) {
			c.add(Calendar.MONTH, -1);
		} else if (T_LASTYEAR.equalsIgnoreCase(type)) {
			c.add(Calendar.YEAR, -1);
		} else if (T_YESTERDAY.equalsIgnoreCase(type)) {
			c.add(Calendar.DAY_OF_MONTH, -1);
		}
		String startTime = sdf.format(c.getTime());
		return new String[] { startTime, startTime };
	}

	/**
	 * 获取开始时间、结束时间
	 * 
	 * @param type
	 * @param tag
	 * @return
	 */
	private String[] getTime(String type) {
		String startTime = null, endTime = null;
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		if (T_YESTERDAY.equals(type)) {// 昨天
			GregorianCalendar gc = new GregorianCalendar();
			gc.add(Calendar.DAY_OF_MONTH, -1);
			startTime = sdf.format(gc.getTime());
			endTime = startTime;
		} else if (T_LASTMONTH.equals(type)) {// 上月
			DateFormat df = new SimpleDateFormat("yyyy-MM");
			GregorianCalendar gc = new GregorianCalendar();
			gc.add(Calendar.MONTH, -1);
			startTime = df.format(gc.getTime()) + "-01";
			int MaxDay = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
			endTime = df.format(gc.getTime()) +"-"+ MaxDay;
		} else if (T_LASTYEAR.equals(type)) {// 去年
			DateFormat df = new SimpleDateFormat("yyyy");
			GregorianCalendar gc = new GregorianCalendar();
			gc.add(Calendar.YEAR, -1);
			startTime = df.format(gc.getTime()) + "-01-01";
			int MaxDay = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
			endTime = df.format(gc.getTime()) + "-12-" + MaxDay;
		} else if (T_YEAR.equals(type)) {// 查一年数据，今天到本年1月1号
			DateFormat df = new SimpleDateFormat("yyyy");
			startTime = df.format(d) + "-01-01";
			endTime = sdf.format(d);
		} else if (T_MONTH.equals(type)) {// 查一个月数据，今天到本月初
			DateFormat df = new SimpleDateFormat("yyyy-MM");
			startTime = df.format(d) + "-01";
			endTime = sdf.format(d);
		} else if (T_WEEK.equals(type)) {// 查一周数据，今天到周一
			GregorianCalendar gc = new GregorianCalendar();
			int dayOfWeek = gc.get(Calendar.DAY_OF_WEEK);
			switch (dayOfWeek) {
			case 1:// 星期日
				gc.add(Calendar.DAY_OF_MONTH, -6);
				startTime = sdf.format(gc.getTime());// 星期一
				break;
			case 2:// 星期一
				startTime = sdf.format(d);
				break;
			case 3:// 星期二
				gc.add(Calendar.DAY_OF_MONTH, -1);
				startTime = sdf.format(gc.getTime());// 星期一
				break;
			case 4:// 星期三
				gc.add(Calendar.DAY_OF_MONTH, -2);
				startTime = sdf.format(gc.getTime());// 星期一
				break;
			case 5:// 星期四
				gc.add(Calendar.DAY_OF_MONTH, -3);
				startTime = sdf.format(gc.getTime());// 星期一
				break;
			case 6:// 星期五
				gc.add(Calendar.DAY_OF_MONTH, -4);
				startTime = sdf.format(gc.getTime());// 星期一
				break;
			case 7:// 星期六
				gc.add(Calendar.DAY_OF_MONTH, -5);
				startTime = sdf.format(gc.getTime());// 星期一
				break;
			}
			endTime = sdf.format(d);
		} else if (T_TODAY.equals(type)) {// 查今天数据
			startTime = sdf.format(d);
			endTime = startTime;
		}
		return new String[] { startTime, endTime };
	}

	/**
	 * 合并历史数据跟当天数据
	 * 
	 * @param list1
	 * @param dayList
	 * @param format
	 * @return
	 */
	private List<StatisticsVisitorEntity> toList(List<StatisticsVisitorEntity> list1, List<StatisticsVisitorEntity> dayList, String format, String type) {
		List<StatisticsVisitorEntity> list = new ArrayList<StatisticsVisitorEntity>();
		String currTime = new SimpleDateFormat(format).format(new Date());
		StatisticsVisitorEntity temp = null;
		if (!dayList.isEmpty()) {
			long dayInTotal = 0L;
			long dayOutTotal = 0L;
			// 累加今天数据（有可能多条）
			for (StatisticsVisitorEntity day : dayList) {
				dayInTotal += day.getIn_total();
				dayOutTotal += day.getOut_total();
			}

			// 只有当天数据
			if (list1.isEmpty()) {
				temp = new StatisticsVisitorEntity();
				temp.setIn_total(dayInTotal);
				temp.setOut_total(dayOutTotal);
				temp.setTime(currTime);
				temp.setType(type);
				list.add(temp);
			} else {
				if (T_WEEK.equals(type) || T_MONTH.equals(type)) {// 本周&当月
					list.addAll(list1);// 先把历史数据加到列表

					temp = new StatisticsVisitorEntity();
					temp.setIn_total(dayInTotal);
					temp.setOut_total(dayOutTotal);
					temp.setTime(currTime);
					temp.setType(type);
					list.add(temp);
				} else if (T_YEAR.equals(type) || T_ALL.equals(type)) {// 当年&全部
					for (StatisticsVisitorEntity data : list1) {
						// 跟今天相同月/相同年的数据累加到当月或者本年度
						String time = data.getTime();
						if (time.equals(currTime)) {
							data.setIn_total(data.getIn_total() + dayInTotal);
							data.setOut_total(data.getOut_total() + dayOutTotal);
						}
						list.add(data);
					}
				}
			}
		} else {
			if (!list1.isEmpty()) {
				list = list1;
			}
		}
		return list;
	}
}