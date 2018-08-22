package com.ssitcloud.operlog.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mongodb.MongoClient;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.system.MongoDB;
import com.ssitcloud.common.system.MongoInstance;
import com.ssitcloud.common.system.MongoInstanceManager;
import com.ssitcloud.common.util.CloseUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.operlog.bookenum.EnumClass;
import com.ssitcloud.operlog.dao.StatisticsLogDao;
import com.ssitcloud.operlog.service.FinanceLogService;
import com.ssitcloud.operlog.utils.StatisticsUtils;

@Service
public class FinanceLogServiceImpl extends BasicServiceImpl implements
		FinanceLogService {

	@Resource
	private StatisticsLogDao statisticsLogDao;
	@Resource(name = "mongoInstanceManager")
	private MongoInstanceManager mongoInstanceManager;
	
	@Resource(name="mongoDBImpl")
	private MongoDB mongoDB;

	private MongoClient client = MongoInstanceManager.mongoClient;
	/**
	 * 流通统计 2017年3月2号 lqw 
	 * whereInfo JSON格式要求：数组集合类型内部元素需加单引号 如
	 * opercmd:["'A'","'B'"] dbName：数据库名 task_type：统计类型（1年任务 2月任务 3周任务 4日任务）
	 * 年统计获取当前年份的前一年数据，月统计获取当前年月的前一个月数据，周统计当前周的前一周的数据（周一到周日） 日统计获取前一天的数据
	 * task_name：触发统计类型（21财经付款类型 22财经付款方式 23财经读者办证类型 24财经操作结果）
	 * 
	 */
	@Override
	public String countFinance(String whereInfo) {
		JSONObject params = JSONObject.fromObject(whereInfo);
		Bson timeAggBson = null;
		String statisticsKey = null;
		String task_name = null;
		String task_type = null;
		String dbName = null;
		String tableName = "finance_log";
		JSONArray finesubType = new JSONArray();  //子类统计数组
		MongoInstance instance = null;
		if (params.containsKey("task_name")) {
			task_name = params.get("task_name").toString();
		}
		if (params.containsKey("task_type")) {
			task_type = params.get("task_type").toString();
		}
		if (params.containsKey("deviceName")) {
			dbName = (String) params.get("deviceName");
			instance = instanceManager.getInstance(dbName);
			/*
			 * if (CollectionUtils.isEmpty(instances)) { return new
			 * ResultEntityF<>(false, "query fail", "mongoInstance is null",
			 * null).toJSONString(); }
			 */
			if (instance == null) {
				return JsonUtils.toJson(new ResultEntityF<>(false, "query fail",
						"mongoInstance is null", null));
			}
		}
		if (params.containsKey("finesubType")) {
			finesubType = JSONArray.fromObject(params.get("finesubType")
					.toString());
		}
//		MongoClient client = instanceManager.initClient(instance);
		if (QueryCountObjF.PURPOSE.equals(task_name)) {
			statisticsKey = "Purpose";
			//判断mongodb是否存在字段
			List<Document> doc = statisticsLogDao.sel(client, dbName,
					tableName, statisticsKey);
			if (doc == null || doc.isEmpty()) {
				return "[]";
			}
		} else if (QueryCountObjF.PAYWAY.equals(task_name)) {
			statisticsKey = "Payway";
			//判断mongodb是否存在字段
			List<Document> doc = statisticsLogDao.sel(client, dbName,
					tableName, statisticsKey);
			if (doc == null || doc.isEmpty()) {
				return "[]";
			}
		} else if (QueryCountObjF.AUTHTYPE.equals(task_name)) {
			statisticsKey = "AuthType";
			//判断mongodb是否存在字段
			List<Document> doc = statisticsLogDao.sel(client, dbName,
					tableName, statisticsKey);
			if (doc == null || doc.isEmpty()) {
				return "[]";
			}
		} else if (QueryCountObjF.OPERRESULT.equals(task_name)) {
			statisticsKey = "operresult";
		} else if (QueryCountObjF.CARDTYPE.equals(task_name)) {
			statisticsKey = "Cardtype";
			//判断mongodb是否存在字段
			List<Document> doc = statisticsLogDao.sel(client, dbName,
					tableName, statisticsKey);
			if (doc == null || doc.isEmpty()) {
				return "[]";
			}
		} else {
			return "[]";
		}
		/**
		 *  task_type任务类型 1年任务 2月任务 3周任务 4日任务
		 *  finesubType  统计子类数组,date 条件日期, countDate 统计日期, dateName 统计日期字段名,countType 统计结果字段名
		 */
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();// 日历对象
		switch (task_type) {
		case "1":
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy");
			int year = Integer.parseInt(df1.format(date)) - 1;
			return statByDate(finesubType, timeAggBson, client, dbName, tableName, year+"", year+"", statisticsKey, "fineDate", "finance1");
		case "2":
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM");
			calendar.setTime(date);// 设置当前日期
			calendar.add(Calendar.MONTH, -1);// 月份减一
			String yearMM = df2.format(calendar.getTime());
			return statByDate(finesubType, timeAggBson, client, dbName, tableName,yearMM, yearMM, statisticsKey, "fineDate", "finance2");
		case "3":
			SimpleDateFormat df3 = new SimpleDateFormat("yyyy-MM-dd");
			calendar.setTime(date);
			int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			if (w == 0) {
				w = 7;
			}
			StringBuffer sb = new StringBuffer();
			calendar.add(Calendar.DATE, -w);
			//得到上一周的日子  周一到周日
			String yearMMdd = df3.format(calendar.getTime());
			for (int i = 1; i < 7; i++) {
				String week = df3.format(calendar.getTime()) + "|";
				sb.append(week);
				calendar.add(Calendar.DATE, -1);
			}
			sb.deleteCharAt(sb.length() - 1);
			return statByDate(finesubType, timeAggBson, client, dbName, tableName,sb.toString(), yearMMdd, statisticsKey, "fineDate", "finance3");
		case "4":
			SimpleDateFormat df4 = new SimpleDateFormat("yyyy-MM-dd");
			calendar.setTime(date);// 设置当前日期
			calendar.add(Calendar.DATE, -1);// 日减一
			String ymd = df4.format(calendar.getTime());
			return statByDate(finesubType, timeAggBson, client, dbName, tableName,ymd, ymd, statisticsKey, "fineDate", "finance4");
		default:
			return "[]";
		}
	}

	/**
	 * 按传过来的时间统计
	 */
	public String statByDate(JSONArray finesubType,Bson timeAggBson,MongoClient client,String dbName,String tableName,String date,String countDate,String statisticsKey,String DateName,String countType){
		Map<String, String> countName = StatisticsUtils.getCountName();
		String datecount=countName.get(countType);
		String count = "0";
		JSONArray json = new JSONArray();
		int ty=0;
		//循环获取子类统计数组
		for (int i = 0; i < finesubType.size(); i++) {
			String value = finesubType.getJSONObject(i).getString("code");
			//把结果按一定格式封装成json
			count = statisticsLogDao.dataStatistics(timeAggBson, client,
					dbName, tableName, date, statisticsKey, value);
			json.add(toJSONObject(value, datecount, count,
					DateName,countDate));
			//计算子类统计数组 的结果总和
			ty+=Integer.parseInt(count);
		}
		//计算其他统计结果 并封装成json
		int sum = statisticsLogDao.total(client, dbName, tableName, date);
		int other=sum-ty;
		if(other >0){
			json.add(toJSONObject("other", datecount, other+"",
					DateName, countDate));
		}
		return json.toString();
	}
	/**
	 * 2017年3月1号 传入String转换成JSONObject lqw 
	 * 根据传入不同的字段名和对应的值封装成json
	 */
	public JSONObject toJSONObject(String i, String countName,
			String countValue, String dateKey, String dateValue) {
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("finesubType", i);
			jsonObj.put(countName, countValue);
			jsonObj.put(dateKey, dateValue);
			return jsonObj;
		} catch (Exception e) {
			return jsonObj;
		}
	}

	@Override
	public String queryFinance(String whereInfo) {
		// Map<String, Object> params = JsonUtils.fromJson(whereInfo,
		// Map.class);
		// List<MongoInstance> instances = getInstances(params);
		// if (CollectionUtils.isEmpty(instances)) {
		// return new ResultEntityF<>(false, "query fail",
		// "mongoInstance is null", null).toJSONString();
		// }
		// List<Bson> pipeline = new ArrayList<Bson>();
		// // QueryObjF queryObj = JsonUtils.fromJson(whereInfo,
		// QueryObjF.class);
		// Bson projections = Projections.include("opertime", "operator",
		// "opercmd", "operresult", "CardType", "AuthCardno", "AuthType",
		// "UniCardno", "Name", "Purpose", "Payway", "Money",
		// "FiscalTranID", "Barcode", "SumDay");
		// pipeline.add(Aggregates.project(Projections.fields(projections,
		// Projections.excludeId())));
		// if (params.containsKey("startTime") && params.containsKey("endTime"))
		// {
		// Bson time = Aggregates
		// .match(Filters.and(Filters.gte("opertime",
		// params.get("startTime").toString()), Filters.lte(
		// "opertime", params.get("endTime").toString())));
		// pipeline.add(time);
		// }
		// if (params.containsKey("operator")) {
		// Bson operator = Aggregates.match(Filters.regex("operator",
		// Pattern.compile(params.get("operator").toString())));
		// pipeline.add(operator);
		// }
		// if (params.containsKey("cardType")) {
		// pipeline.add(Aggregates.match(Filters.in("CardType",
		// params.get("cardType"))));
		// }
		// if (params.containsKey("authType")) {
		// pipeline.add(Aggregates.match(Filters.in("AuthType",
		// params.get("authType"))));
		// }
		// if (params.containsKey("operresult")) {
		// pipeline.add(Aggregates.match(Filters.in("operresult",
		// params.get("operresult"))));
		// }
		// if (params.containsKey("payway")) {
		// pipeline.add(Aggregates.match(Filters.in("Payway",
		// params.get("payway"))));
		// }
		// List<Document> docs = aggregate(instances,
		// Constant.TABLE_FINANCE_LOG,
		// pipeline);
		// // List<Document> docs = financeLogDao.aggregate(pipeline,
		// // Constant.TABLE_FINANCE_LOG);
		// return new ResultEntityF<List<Document>>(true, "query success", docs)
		// .toJSONString();
		// }
		return null;
	}
	@Override
	public ResultEntity queryAllFinanceLogFromMongodb(String req) {
		ResultEntity resultEntity = new ResultEntity();
		Assert.hasText(req, "queryAllFinanceLogFromMongodb method parameter must has content");
		String mongodbName = req;
		MongoInstance instance = mongoInstanceManager.getInstance(mongodbName.toUpperCase());
		if (instance != null) {
//			MongoClient client=null;
			try {
//				client=mongoDB.getDBClient(instance);
				Document filter=new Document();
				List<Document> list = statisticsLogDao.find(client,instance.getOperDatabase(), EnumClass.COLLECTION.finance_log.name(),filter);
				for (Document document : list) {
					document.put("id", document.get("_id").toString());
					document.remove("_id");
				}
				resultEntity.setValue(true, "success", "", list);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				//CloseUtil.close(client);
			}
		}else {
			resultEntity.setValue(true, "success", "", new ArrayList<>());
		}
		return resultEntity;
	}

	@JsonInclude(value = Include.NON_NULL)
	class QueryObjF {
		private List<String> cardType;
		private List<String> authType;
		private List<String> operresult;
		private List<String> payway;
		private String operator;
		private String startTime;
		private String endTime;

		public List<String> getCardType() {
			return cardType;
		}

		public void setCardType(List<String> cardType) {
			this.cardType = cardType;
		}

		public List<String> getAuthType() {
			return authType;
		}

		public void setAuthType(List<String> authType) {
			this.authType = authType;
		}

		public List<String> getOperresult() {
			return operresult;
		}

		public void setOperresult(List<String> operresult) {
			this.operresult = operresult;
		}

		public List<String> getPayway() {
			return payway;
		}

		public void setPayway(List<String> payway) {
			this.payway = payway;
		}

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		public String getStartTime() {
			return startTime;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

	}

	@JsonInclude(value = Include.NON_NULL)
	class QueryCountObjF {
		private String countType;
		private String startTime;
		private String endTime;
		private String operator;
		private List<String> params;

		public static final String BY_DEVICE = "1";
		public static final String BY_CARD_TYPE = "2";
		public static final String BY_AUTH_TYPE = "3";
		public final static String PURPOSE = "21";// 财经付款类型
		public final static String PAYWAY = "22";// 财经付款方式
		public final static String AUTHTYPE = "23";// 财经认证类型
		public final static String OPERRESULT = "24";// 财经操作结果
		public final static String CARDTYPE = "25";// 财经读者流通类型

		public String getStartTime() {
			return startTime;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		public List<String> getParams() {
			return params;
		}

		public void setParams(List<String> params) {
			this.params = params;
		}

		public String getCountType() {
			return countType;
		}

		public void setCountType(String countType) {
			this.countType = countType;
		}

	}

	
}