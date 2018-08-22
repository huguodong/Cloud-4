package com.ssitcloud.business.task.scheduled.task;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;

import com.ssitcloud.business.task.common.util.DateUtil;
import com.ssitcloud.business.task.common.util.JsonUtils;
import com.ssitcloud.business.task.common.util.LogUtils;
import com.ssitcloud.business.task.scheduled.entity.TimedTaskEntity;
import com.ssitcloud.business.task.scheduled.service.DevicemonitorService;
import com.ssitcloud.business.task.scheduled.service.StatisticsService;
import com.ssitcloud.business.task.scheduled.service.impl.DevicemonitorServiceImpl;
import com.ssitcloud.business.task.scheduled.service.impl.StatisticsServiceImpl;
import com.ssitcloud.common.entity.ResultEntity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 将mongodb查询的结果，写入statistics库里 author huanghuang 2017年2月25日 上午11:26:07
 */
class ReadWrite {

	private StatisticsService statisticsService = new StatisticsServiceImpl();
	private DevicemonitorService devicemonitorService = new DevicemonitorServiceImpl();
	Lock lock = new ReentrantLock();
	// Condition condition = lock.newCondition();
	final Condition read = lock.newCondition();// read 读数据
	final Condition write = lock.newCondition();// write 写数据
	private int flag = 1;// 1是读 2是写
	private String resultData = null;// mongodb查询得到的结果
	private TimedTaskEntity tmp = null;// 暂存定时任务
	private String deviceidx = null;// 暂存设备idx
	private Map<String, JSONArray> dynamicMap;

	public ReadWrite(Map<String, JSONArray> dynamicMap) {
		super();
		this.dynamicMap = dynamicMap;
	}

	public void read(TimedTaskEntity timedTask, String deviceName,
			String device_idx, Map<String, JSONArray> logStaticSourceMap) {
		lock.lock();
		try {
			while (flag != 1) {
				try {
					read.await();
				} catch (InterruptedException e) {
					LogUtils.info("读取mongodb数据时，锁定线程失败");
				}
			}
			ResultEntity result = null;
			boolean loanLogFlag = ("11".equals(timedTask.getTask_name())
					|| "12".equals(timedTask.getTask_name())
					|| "13".equals(timedTask.getTask_name())
					|| "14".equals(timedTask.getTask_name())
					|| "15".equals(timedTask.getTask_name())
					|| "16".equals(timedTask.getTask_name()) || "17"
					.equals(timedTask.getTask_name()));// 流通统计查询标识

			boolean fineLogFlag = ("21".equals(timedTask.getTask_name())
					|| "22".equals(timedTask.getTask_name())
					|| "23".equals(timedTask.getTask_name())
					|| "24".equals(timedTask.getTask_name()) || "25"
					.equals(timedTask.getTask_name()));// 财经统计查询标识

			boolean cardLogFlag = ("31".equals(timedTask.getTask_name())
					|| "32".equals(timedTask.getTask_name())
					|| "33".equals(timedTask.getTask_name())
					|| "34".equals(timedTask.getTask_name())
					|| "35".equals(timedTask.getTask_name()) || "36"
					.equals(timedTask.getTask_name()));// 办证统计查询标识
			
			JSONArray sonLogArr = new JSONArray();//子类型参数的jsonArra数组
			if (loanLogFlag) {// 流通统计查询
//				JSONArray loanArr = new JSONArray();
				switch (timedTask.getTask_name()) {
				case "11":
					sonLogArr = dynamicMap.get("readerType");
					break;
				case "12":
					sonLogArr = logStaticSourceMap.get("bookTypeJsonArr");
					break;
				case "13":
					sonLogArr = dynamicMap.get("bookLocation");
					break;
				case "14":
					sonLogArr = dynamicMap.get("bookMedia");
					break;
				case "15":
					sonLogArr = logStaticSourceMap.get("resultJsonArr");
					break;
				case "16":
					sonLogArr = logStaticSourceMap.get("timeJsonArr");
					break;
				case "17":
					sonLogArr = logStaticSourceMap.get("authJsonArr");
					break;
				default:
					break;
				}
				jsonArraySort(sonLogArr, "code", 4);// 对code自然排序
				result = devicemonitorService
						.queryLoanLog("{\"task_name\":\""
								+ timedTask.getTask_name()
								+ "\",\"task_type\":\""
								+ timedTask.getTask_type()
								+ "\",\"deviceName\":\"" + deviceName
								+ "\",\"cirsubType\":'\"" + sonLogArr + "\"'}");
				String r = (String) result.getResult();
				if (JsonUtils.checkJsonArray(r))// 验证是否符合jsonArray格式
					this.resultData = r;
				else
					this.resultData = "[]";

			} else if (fineLogFlag) {// 财经统计查询
//				JSONArray finArr = new JSONArray();
				switch (timedTask.getTask_name()) {
				case "21":
					sonLogArr = logStaticSourceMap.get("payTypeJsonArr");
					break;
				case "22":
					sonLogArr = logStaticSourceMap.get("moneyJsonArr");
					break;
				case "23":
					sonLogArr = logStaticSourceMap.get("authJsonArr");
					break;
				case "24":
					sonLogArr = logStaticSourceMap.get("resultJsonArr");
					break;
				case "25":
					sonLogArr = dynamicMap.get("readerType");
					break;
				default:
					break;
				}
				jsonArraySort(sonLogArr, "code", 4);// 对code自然排序
				result = devicemonitorService
						.queryFinLog("{\"task_name\":\""
								+ timedTask.getTask_name()
								+ "\",\"task_type\":\""
								+ timedTask.getTask_type()
								+ "\",\"deviceName\":\"" + deviceName
								+ "\",\"finesubType\":'\"" + sonLogArr + "\"'}");
				String r = (String) result.getResult();
				if (JsonUtils.checkJsonArray(r))// 验证是否符合jsonArray格式
					this.resultData = r;
				else
					this.resultData = "[]";
			} else if (cardLogFlag) {// 办证统计查询
//				JSONArray cardArr = new JSONArray();
				switch (timedTask.getTask_name()) {
				case "31":
					sonLogArr = dynamicMap.get("readerType");
					break;
				case "32":
					sonLogArr = logStaticSourceMap.get("authJsonArr");
					break;
				case "33":
					sonLogArr = logStaticSourceMap.get("ageJsonArr");
					break;
				case "34":
					sonLogArr = logStaticSourceMap.get("sexJsonArr");
					break;
				case "35":
					sonLogArr = logStaticSourceMap.get("timeJsonArr");
					break;
				case "36":
					sonLogArr = logStaticSourceMap.get("resultJsonArr");
					break;
				default:
					break;
				}
				jsonArraySort(sonLogArr, "code", 4);// 对code自然排序
				result = devicemonitorService.queryCardLog("{\"task_name\":\""
						+ timedTask.getTask_name() + "\",\"task_type\":\""
						+ timedTask.getTask_type() + "\",\"deviceName\":\""
						+ deviceName + "\",\"newCardsubType\":'\"" + sonLogArr
						+ "\"'}");
				String r = (String) result.getResult();
				if (JsonUtils.checkJsonArray(r))// 验证是否符合jsonArray格式
					this.resultData = r;
				else
					this.resultData = "[]";
			}
			this.tmp = timedTask;
			this.deviceidx = device_idx;
			flag = 2;
			write.signal();
		} finally {
			lock.unlock();
		}
	}

	public void write(BlockingQueue<JSONObject> q) {
		lock.lock();
		try {
			while (flag != 2) {
				try {
					write.await();
				} catch (InterruptedException e) {
					LogUtils.info("写入mongodb查询出的结果数据时，锁定线程失败");
				}
			}
			// JSONObject json = JSONObject.fromObject(resultData);

			boolean loanLogFlag = ("11".equals(tmp.getTask_name())
					|| "12".equals(tmp.getTask_name())
					|| "13".equals(tmp.getTask_name())
					|| "14".equals(tmp.getTask_name())
					|| "15".equals(tmp.getTask_name())
					|| "16".equals(tmp.getTask_name()) || "17".equals(tmp
					.getTask_name()));// 流通统计查询标识

			boolean fineLogFlag = ("21".equals(tmp.getTask_name())
					|| "22".equals(tmp.getTask_name())
					|| "23".equals(tmp.getTask_name())
					|| "24".equals(tmp.getTask_name()) || "25".equals(tmp
					.getTask_name()));// 财经统计查询标识

			boolean cardLogFlag = ("31".equals(tmp.getTask_name())
					|| "32".equals(tmp.getTask_name())
					|| "33".equals(tmp.getTask_name())
					|| "34".equals(tmp.getTask_name())
					|| "35".equals(tmp.getTask_name()) || "36".equals(tmp
					.getTask_name()));// 办证统计查询标识

			if (loanLogFlag) {// 流通统计结果插入
				switch (tmp.getTask_type()) {
				// 添加流通的年统计记录
				case 1:
					JSONArray jsonArray = JSONArray.fromObject(resultData);
					StringBuffer rFlag = new StringBuffer();
					if (jsonArray != null) {
						for (int i = 0; i < jsonArray.size(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							jsonObject.put("device_idx", deviceidx);
							jsonObject.put("lib_idx", tmp.getLib_idx());
							jsonObject.put("circulateCountType",
									tmp.getTask_name());
							/*
							 * jsonObject.put("circulateDate",
							 * TimestampToStr(tmp.getTask_starttime()));
							 */
							ResultEntity r = statisticsService
									.addLoanLogYear(jsonObject.toString());
							rFlag.append(r.getState());
						}
					}
					if (rFlag.toString().contains("false")) {// 添加操作时，存在失败，把任务放到阻塞队列，下一分钟重新执行
						q.add(JSONObject.fromObject(tmp));
					}
					break;
				// 添加流通的月统计记录
				case 2:
					JSONArray jsonArray2 = JSONArray.fromObject(resultData);
					StringBuffer rFlag2 = new StringBuffer();
					if (jsonArray2 != null) {
						for (int i = 0; i < jsonArray2.size(); i++) {
							JSONObject jsonObject = jsonArray2.getJSONObject(i);
							jsonObject.put("device_idx", deviceidx);
							jsonObject.put("lib_idx", tmp.getLib_idx());
							jsonObject.put("circulateCountType",
									tmp.getTask_name());
							/*
							 * jsonObject.put("circulateDate",
							 * TimestampToStr(tmp.getTask_starttime()));
							 */
							ResultEntity r = statisticsService
									.addLoanLogMonth(jsonObject.toString());
							rFlag2.append(r.getState());
						}
					}
					if (rFlag2.toString().contains("false")) {// 添加操作时，存在失败，把任务放到阻塞队列，下一分钟重新执行
						q.add(JSONObject.fromObject(tmp));
					}
					break;
				// 添加流通的周统计记录
				case 3:
					JSONArray jsonArray3 = JSONArray.fromObject(resultData);
					StringBuffer rFlag3 = new StringBuffer();
					if (jsonArray3 != null) {
						for (int i = 0; i < jsonArray3.size(); i++) {
							JSONObject jsonObject = jsonArray3.getJSONObject(i);
							jsonObject.put("device_idx", deviceidx);
							jsonObject.put("lib_idx", tmp.getLib_idx());
							jsonObject.put("circulateCountType",
									tmp.getTask_name());
							/*
							 * jsonObject.put("circulateDate",
							 * TimestampToStr(tmp.getTask_starttime()));
							 */
							ResultEntity r = statisticsService
									.addLoanLogWeek(jsonObject.toString());
							rFlag3.append(r.getState());
						}
					}
					if (rFlag3.toString().contains("false")) {// 添加操作时，存在失败，把任务放到阻塞队列，下一分钟重新执行
						q.add(JSONObject.fromObject(tmp));
					}
					break;
				// 添加流通的日统计记录
				case 4:
					JSONArray jsonArray4 = JSONArray.fromObject(resultData);
					StringBuffer rFlag4 = new StringBuffer();
					if (jsonArray4 != null) {
						for (int i = 0; i < jsonArray4.size(); i++) {
							JSONObject jsonObject = jsonArray4.getJSONObject(i);
							jsonObject.put("device_idx", deviceidx);
							jsonObject.put("lib_idx", tmp.getLib_idx());
							jsonObject.put("circulateCountType",
									tmp.getTask_name());
							jsonObject.put("cirWeek", DateUtil
									.dateToWeek(jsonArray4.getJSONObject(i)
											.getString("circulateDate")));
							/*
							 * jsonObject.put("circulateDate",
							 * TimestampToStr(tmp.getTask_starttime()));
							 */
							ResultEntity r = statisticsService
									.addLoanLogDay(jsonObject.toString());
							rFlag4.append(r.getState());
						}
					}
					if (rFlag4.toString().contains("false")) {// 添加操作时，存在失败，把任务放到阻塞队列，下一分钟重新执行
						q.add(JSONObject.fromObject(tmp));
					}
					break;
				default:
					break;
				}
			} else if (fineLogFlag) {// 财经统计结果插入
				switch (tmp.getTask_type()) {
				// 添加财经的年统计记录
				case 1:
					JSONArray jsonArray = JSONArray.fromObject(resultData);
					StringBuffer rFlag = new StringBuffer();
					if (jsonArray != null) {
						for (int i = 0; i < jsonArray.size(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							jsonObject.put("device_idx", deviceidx);
							jsonObject.put("lib_idx", tmp.getLib_idx());
							jsonObject.put("fineCountType", tmp.getTask_name());
							/*
							 * jsonObject.put("circulateDate",
							 * TimestampToStr(tmp.getTask_starttime()));
							 */
							ResultEntity r = statisticsService
									.addFinLogYear(jsonObject.toString());
							rFlag.append(r.getState());
						}
					}
					if (rFlag.toString().contains("false")) {// 添加操作时，存在失败，把任务放到阻塞队列，下一分钟重新执行
						q.add(JSONObject.fromObject(tmp));
					}
					break;
				// 添加财经的月统计记录
				case 2:
					JSONArray jsonArray2 = JSONArray.fromObject(resultData);
					StringBuffer rFlag2 = new StringBuffer();
					if (jsonArray2 != null) {
						for (int i = 0; i < jsonArray2.size(); i++) {
							JSONObject jsonObject = jsonArray2.getJSONObject(i);
							jsonObject.put("device_idx", deviceidx);
							jsonObject.put("lib_idx", tmp.getLib_idx());
							jsonObject.put("fineCountType", tmp.getTask_name());
							/*
							 * jsonObject.put("circulateDate",
							 * TimestampToStr(tmp.getTask_starttime()));
							 */
							ResultEntity r = statisticsService
									.addFinLogMonth(jsonObject.toString());
							rFlag2.append(r.getState());
						}
					}
					if (rFlag2.toString().contains("false")) {// 添加操作时，存在失败，把任务放到阻塞队列，下一分钟重新执行
						q.add(JSONObject.fromObject(tmp));
					}
					break;
				// 添加财经的周统计记录
				case 3:
					JSONArray jsonArray3 = JSONArray.fromObject(resultData);
					StringBuffer rFlag3 = new StringBuffer();
					if (jsonArray3 != null) {
						for (int i = 0; i < jsonArray3.size(); i++) {
							JSONObject jsonObject = jsonArray3.getJSONObject(i);
							jsonObject.put("device_idx", deviceidx);
							jsonObject.put("lib_idx", tmp.getLib_idx());
							jsonObject.put("fineCountType", tmp.getTask_name());
							/*
							 * jsonObject.put("circulateDate",
							 * TimestampToStr(tmp.getTask_starttime()));
							 */
							ResultEntity r = statisticsService
									.addFinLogWeek(jsonObject.toString());
							rFlag3.append(r.getState());
						}
					}
					if (rFlag3.toString().contains("false")) {// 添加操作时，存在失败，把任务放到阻塞队列，下一分钟重新执行
						q.add(JSONObject.fromObject(tmp));
					}
					break;
				// 添加财经的日统计记录
				case 4:
					JSONArray jsonArray4 = JSONArray.fromObject(resultData);
					StringBuffer rFlag4 = new StringBuffer();
					if (jsonArray4 != null) {
						for (int i = 0; i < jsonArray4.size(); i++) {
							JSONObject jsonObject = jsonArray4.getJSONObject(i);
							jsonObject.put("device_idx", deviceidx);
							jsonObject.put("lib_idx", tmp.getLib_idx());
							jsonObject.put("fineCountType", tmp.getTask_name());
							jsonObject.put("fineWeek", DateUtil
									.dateToWeek(jsonArray4.getJSONObject(i)
											.getString("fineDate")));
							/*
							 * jsonObject.put("circulateDate",
							 * TimestampToStr(tmp.getTask_starttime()));
							 */
							ResultEntity r = statisticsService
									.addFinLogDay(jsonObject.toString());
							rFlag4.append(r.getState());
						}
					}
					if (rFlag4.toString().contains("false")) {// 添加操作时，存在失败，把任务放到阻塞队列，下一分钟重新执行
						q.add(JSONObject.fromObject(tmp));
					}
					break;
				default:
					break;
				}
			} else if (cardLogFlag) {// 办证统计结果插入
				switch (tmp.getTask_type()) {
				// 添加办证的年统计记录
				case 1:
					JSONArray jsonArray = JSONArray.fromObject(resultData);
					StringBuffer rFlag = new StringBuffer();
					if (jsonArray != null) {
						for (int i = 0; i < jsonArray.size(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							jsonObject.put("device_idx", deviceidx);
							jsonObject.put("lib_idx", tmp.getLib_idx());
							jsonObject.put("newCardCountType",
									tmp.getTask_name());
							/*
							 * jsonObject.put("circulateDate",
							 * TimestampToStr(tmp.getTask_starttime()));
							 */
							ResultEntity r = statisticsService
									.addNewCardLogYear(jsonObject.toString());
							rFlag.append(r.getState());
						}
					}
					if (rFlag.toString().contains("false")) {// 添加操作时，存在失败，把任务放到阻塞队列，下一分钟重新执行
						q.add(JSONObject.fromObject(tmp));
					}
					break;
				// 添加办证的月统计记录
				case 2:
					JSONArray jsonArray2 = JSONArray.fromObject(resultData);
					StringBuffer rFlag2 = new StringBuffer();
					if (jsonArray2 != null) {
						for (int i = 0; i < jsonArray2.size(); i++) {
							JSONObject jsonObject = jsonArray2.getJSONObject(i);
							jsonObject.put("device_idx", deviceidx);
							jsonObject.put("lib_idx", tmp.getLib_idx());
							jsonObject.put("newCardCountType",
									tmp.getTask_name());
							/*
							 * jsonObject.put("circulateDate",
							 * TimestampToStr(tmp.getTask_starttime()));
							 */
							ResultEntity r = statisticsService
									.addNewCardLogMonth(jsonObject.toString());
							rFlag2.append(r.getState());
						}
					}
					if (rFlag2.toString().contains("false")) {// 添加操作时，存在失败，把任务放到阻塞队列，下一分钟重新执行
						q.add(JSONObject.fromObject(tmp));
					}
					break;
				// 添加办证的周统计记录
				case 3:
					JSONArray jsonArray3 = JSONArray.fromObject(resultData);
					StringBuffer rFlag3 = new StringBuffer();
					if (jsonArray3 != null) {
						for (int i = 0; i < jsonArray3.size(); i++) {
							JSONObject jsonObject = jsonArray3.getJSONObject(i);
							jsonObject.put("device_idx", deviceidx);
							jsonObject.put("lib_idx", tmp.getLib_idx());
							jsonObject.put("newCardCountType",
									tmp.getTask_name());
							/*
							 * jsonObject.put("circulateDate",
							 * TimestampToStr(tmp.getTask_starttime()));
							 */
							ResultEntity r = statisticsService
									.addNewCardLogWeek(jsonObject.toString());
							rFlag3.append(r.getState());
						}
					}
					if (rFlag3.toString().contains("false")) {// 添加操作时，存在失败，把任务放到阻塞队列，下一分钟重新执行
						q.add(JSONObject.fromObject(tmp));
					}
					break;
				// 添加办证的日统计记录
				case 4:
					JSONArray jsonArray4 = JSONArray.fromObject(resultData);
					StringBuffer rFlag4 = new StringBuffer();
					if (jsonArray4 != null) {
						for (int i = 0; i < jsonArray4.size(); i++) {
							JSONObject jsonObject = jsonArray4.getJSONObject(i);
							jsonObject.put("device_idx", deviceidx);
							jsonObject.put("lib_idx", tmp.getLib_idx());
							jsonObject.put("newCardCountType",
									tmp.getTask_name());
							jsonObject.put("newCardWeek", DateUtil
									.dateToWeek(jsonArray4.getJSONObject(i)
											.getString("newCardDate")));
							/*
							 * jsonObject.put("circulateDate",
							 * TimestampToStr(tmp.getTask_starttime()));
							 */
							ResultEntity r = statisticsService
									.addNewCardLogDay(jsonObject.toString());
							rFlag4.append(r.getState());
						}
					}
					if (rFlag4.toString().contains("false")) {// 添加操作时，存在失败，把任务放到阻塞队列，下一分钟重新执行
						q.add(JSONObject.fromObject(tmp));
					}
					break;
				default:
					break;
				}
			}
			flag = 1;
			read.signal();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 对jsonArray指定字段排序 author huanghuang 2017年3月8日 下午5:33:01
	 * 
	 * @param ja
	 *            jsonArray数组
	 * @param field
	 *            排序的字段
	 * @param subLen
	 *            截取长度
	 */
	@SuppressWarnings({ "unchecked" })
	private static void jsonArraySort(JSONArray ja, final String field,
			final int subLen) {
		Collections.sort(ja, new Comparator<JSONObject>() {
			public int compare(JSONObject o1, JSONObject o2) {
				String s1 = substringLast(o1.getString(field), subLen);
				String s2 = substringLast(o2.getString(field), subLen);
				return s1.toString().compareTo(s2.toString());
			}
		});
	}

	/**
	 * 截取最后字符规定长度的字符串，不足以0补充 author huanghuang 2017年3月8日 下午5:33:09
	 * 
	 * @param str
	 *            字符串
	 * @param subLen
	 *            截取长度
	 * @return
	 */
	private static String substringLast(String str, int subLen) {
		int leng = 0;
		if (StringUtils.isNotBlank(str)) {
			leng = str.length();
		}
		if (leng < subLen) {
			return append(str, leng, subLen);
		} else {
			return str.substring(leng - subLen);
		}
	}

	private static String append(String str, int start, int end) {
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < end; i++) {
			sb.append("0");
		}
		sb.append(str);
		return sb.toString();
	}

}
