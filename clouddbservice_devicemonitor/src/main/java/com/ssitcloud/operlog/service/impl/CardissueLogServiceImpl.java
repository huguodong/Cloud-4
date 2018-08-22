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

import org.apache.commons.collections.CollectionUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;
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
import com.ssitcloud.operlog.service.CardissueLogService;
import com.ssitcloud.operlog.utils.StatisticsUtils;

@Service
public class CardissueLogServiceImpl extends BasicServiceImpl implements
		CardissueLogService {
	@Resource
	private StatisticsLogDao statisticsLogDao;
	@Resource(name = "mongoInstanceManager")
	private MongoInstanceManager mongoInstanceManager;
	
	@Resource(name="mongoDBImpl")
	private MongoDB mongoDB;
	
	private MongoClient client = MongoInstanceManager.mongoClient;

	/**
	 * 流通统计 2017年3月3号 lqw 
	 * whereInfo JSON格式要求：数组集合类型内部元素需加单引号 如
	 * opercmd:["'A'","'B'"] dbName：数据库名 task_type：统计类型（1年任务 2月任务 3周任务 4日任务）
	 * 年统计获取当前年份的前一年数据，月统计获取当前年月的前一个月数据，周统计当前周的前一周的数据（周一到周日） 日统计获取前一天的数据
	 * task_name：触发统计类型（31读者流通类型 32认证类型 33年龄段类型 34办证性别 35操作时间点 36操作结果）
	 * 
	 */
	@Override
	public String countCardissue(String whereInfo) {
		JSONObject params = JSONObject.fromObject(whereInfo);
		Bson timeAggBson = null;
		String statisticsKey = null;
		String task_name = null;
		String task_type = null;
		String dbName = null;
		String tableName = "cardissue_log";
		JSONArray newCardsubType = new JSONArray();  //子类统计数组
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
		if (params.containsKey("newCardsubType")) {
			newCardsubType = JSONArray.fromObject(params.get("newCardsubType")
					.toString());
		}
//		MongoClient client = instanceManager.initClient(instance);
		if (QueryCountObj.READER_CIRCULATION_TYPE.equals(task_name)) {
			statisticsKey = "Cardtype";
			//判断mongodb是否存在字段
			List<Document> doc = statisticsLogDao.sel(client, dbName,
					tableName, statisticsKey);
			if (doc == null || doc.isEmpty()) {
				return "[]";
			}
		} else if (QueryCountObj.AUTHENTICATION_TYPE.equals(task_name)) {
			statisticsKey = "AuthType";
			//判断mongodb是否存在字段
			List<Document> doc = statisticsLogDao.sel(client, dbName,
					tableName, statisticsKey);
			if (doc == null || doc.isEmpty()) {
				return "[]";
			}
		} else if (QueryCountObj.AGE_OF_ACCREDITATION.equals(task_name)) {
			/**
			 *  task_type任务类型 1年任务 2月任务 3周任务 4日任务
			 *  newCardsubType  统计子类数组,date 条件日期, countDate 统计日期, dateName 统计日期字段名,countType 统计结果字段名
			 */
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();// 日历对象
			switch (task_type) {
			case "1":
				SimpleDateFormat df1 = new SimpleDateFormat("yyyy");
				int year = Integer.parseInt(df1.format(date)) - 1;
				return statByAge(newCardsubType, timeAggBson, client, dbName, tableName, year+"", year+"", statisticsKey, "newCardDate", "newCard1");
			case "2":
				SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM");
				calendar.setTime(date);// 设置当前日期
				calendar.add(Calendar.MONTH, -1);// 月份减一
				String yearMM = df2.format(calendar.getTime());
				return statByAge(newCardsubType, timeAggBson, client, dbName, tableName, yearMM, yearMM, statisticsKey, "newCardDate", "newCard2");
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
				for (int j = 1; j < 7; j++) {
					String week = df3.format(calendar.getTime()) + "|";
					sb.append(week);
					calendar.add(Calendar.DATE, -1);
				}
				sb.deleteCharAt(sb.length() - 1);
				return statByAge(newCardsubType, timeAggBson, client, dbName, tableName, sb.toString(),yearMMdd, statisticsKey, "newCardDate", "newCard3");
			case "4":
				SimpleDateFormat df4 = new SimpleDateFormat("yyyy-MM-dd");
				calendar.setTime(date);// 设置当前日期
				calendar.add(Calendar.DATE, -1);// 日减一
				String ymd = df4.format(calendar.getTime());
				return statByAge(newCardsubType, timeAggBson, client, dbName, tableName, ymd,ymd, statisticsKey, "newCardDate", "newCard4");
			default:
				return "[]";
			}
		} else if (QueryCountObj.SEX_OF_HANDLING.equals(task_name)) {
			statisticsKey = "Gender";
			//判断mongodb是否存在字段
			List<Document> doc = statisticsLogDao.sel(client, dbName,
					tableName, statisticsKey);
			if (doc == null || doc.isEmpty()) {
				return "[]";
			}
		} else if (QueryCountObj.OPERATING_TIME_POINT.equals(task_name)) {
			timeAggBson = Aggregates.project(Projections.fields(Projections
					.include("opertime"), new BasicDBObject("hours",
					new BasicDBObject("$substr", new Object[] { "$opertime",
							11, 2 }))));
			statisticsKey = "hours";
		} else if (QueryCountObj.OPERATING_RESULTS.equals(task_name)) {
			statisticsKey = "operresult";
		} else {
			return "[]";
		}

		/**
		 *  task_type任务类型 1年任务 2月任务 3周任务 4日任务
		 *  newCardsubType  统计子类数组,date 条件日期, countDate 统计日期, dateName 统计日期字段名,countType 统计结果字段名
		 */
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();// 日历对象
		switch (task_type) {
		case "1":
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy");
			int year = Integer.parseInt(df1.format(date)) - 1;
			return statByDate(newCardsubType, timeAggBson, client, dbName, tableName, year+"", year+"", statisticsKey, "newCardDate", "newCard1");
		case "2":
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM");
			calendar.setTime(date);// 设置当前日期
			calendar.add(Calendar.MONTH, -1);// 月份减一
			String yearMM = df2.format(calendar.getTime());
			return statByDate(newCardsubType, timeAggBson, client, dbName, tableName, yearMM, yearMM, statisticsKey, "newCardDate", "newCard2");
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
			return statByDate(newCardsubType, timeAggBson, client, dbName, tableName, sb.toString(), yearMMdd, statisticsKey, "newCardDate", "newCard3");
		case "4":
			SimpleDateFormat df4 = new SimpleDateFormat("yyyy-MM-dd");
			calendar.setTime(date);// 设置当前日期
			calendar.add(Calendar.DATE, -1);// 日减一
			String ymd = df4.format(calendar.getTime());
			return statByDate(newCardsubType, timeAggBson, client, dbName, tableName, ymd, ymd, statisticsKey, "newCardDate", "newCard4");
		default:
			return "[]";
		}
	}

	/**
	 * 2017年3月1号 传入String转换成JSONObject lqw 
	 * 根据传入不同的字段名和对应的值封装成json
	 */
	public JSONObject toJSONObject(String i, String countName,
			String countValue, String dateKey, String dateValue) {
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("newCardsubType", i);
			jsonObj.put(countName, countValue);
			jsonObj.put(dateKey, dateValue);
			return jsonObj;
		} catch (Exception e) {
			return jsonObj;
		}
	}
	
	/**
	 * 按传过来的时间统计
	 */
	public String statByDate(JSONArray newCardsubType,Bson timeAggBson,MongoClient client,String dbName,String tableName,String date,String countDate,String statisticsKey,String DateName,String countType){
		Map<String, String> countName = StatisticsUtils.getCountName();
		String datecount=countName.get(countType);
		String count = "0";
		JSONArray json = new JSONArray();
		int ty=0;
		//循环获取子类统计数组
		for (int i = 0; i < newCardsubType.size(); i++) {
			String value = newCardsubType.getJSONObject(i).getString("code");
			count = statisticsLogDao.dataStatistics(timeAggBson, client,
					dbName, tableName, date, statisticsKey, value);
			//把结果按一定格式封装成json
			json.add(toJSONObject(value, datecount, count,
					DateName,countDate));
			//计算子类统计数组 的结果总和
			ty+=Integer.parseInt(count);
		}
		//获取统计总数
		int sum = statisticsLogDao.total(client, dbName, tableName, date);
		int other=sum-ty;
		//计算其他统计结果 并封装成json
		if(other >0){
			json.add(toJSONObject("other", datecount, other+"",
					DateName, countDate));
		}
		return json.toString();
	}

	/**
	 * 按年龄段统计
	 */
	public String statByAge(JSONArray newCardsubType,Bson timeAggBson,MongoClient client,String dbName,String tableName,String date,String countDate,String statisticsKey,String DateName,String countType){
		Map<String, String> countName = StatisticsUtils.getCountName();
		String datecount=countName.get(countType);
		String count = "0";
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();// 日历对象
		JSONArray json = new JSONArray();
		int ty = 0;
		//循环获取子类统计数组
		for (int i = 0; i < newCardsubType.size(); i++) {
			//得到开始时间和结束时间
			int t = 0;
			if (i >= 1) {
				t = Integer.parseInt(newCardsubType
						.getJSONObject(i - 1).getString("code"));
			}
			int value = Integer.parseInt(newCardsubType
					.getJSONObject(i).getString("code"));
			int paragraph = (t - value) > 0 ? (t - value) : (value - t);
			String endAge = df.format(cal.getTime());
			cal.add(Calendar.YEAR, -paragraph);
			String startAge = df.format(cal.getTime());
			//得到结果封装json
			count = statisticsLogDao.ageGroup(timeAggBson, client,
					dbName, tableName, date, startAge, endAge);
			json.add(toJSONObject(value + "", datecount,
					count, DateName, countDate));
			ty += Integer.parseInt(count);
		}
		//计算其他类型结果封装json
		int sum = statisticsLogDao.total(client, dbName, tableName,date);
		int other = sum - ty;
		if (other > 0) {
			json.add(toJSONObject("other", datecount, other
					+ "", DateName, countDate));
		}
		return json.toString();
	}
	@Override
	public String queryCardissue(String whereInfo) {
		// Map<String, Object> params = JsonUtils.fromJson(whereInfo,
		// Map.class);
		//
		// List<MongoInstance> instances = getInstances(params);
		// if (CollectionUtils.isEmpty(instances)) {
		// return new ResultEntityF<>(false, "query fail",
		// "mongoInstance is null", null).toJSONString();
		// }
		// // QueryObj queryObj = JsonUtils.fromJson(whereInfo, QueryObj.class);
		// List<Bson> pipeline = new ArrayList<>();
		//
		// if (params.containsKey("startTime") && params.containsKey("endTime"))
		// {
		// Bson timeAggBson = Aggregates.match(and(
		// gte("opertime", params.get("startTime")),
		// lte("opertime", params.get("endTime"))));
		// pipeline.add(timeAggBson);
		// }
		// if (params.containsKey("authType")) {
		// pipeline.add(Aggregates.match(in("AuthType",
		// params.get("authType"))));
		// }
		// if (params.containsKey("cardType")) {
		// pipeline.add(Aggregates.match(in("CardType",
		// params.get("cardType"))));
		// }
		// if (params.containsKey("gender")) {
		// pipeline.add(Aggregates.match(in("Gender", params.get("gender"))));
		// }
		// if (params.containsKey("endAge") && params.containsKey("startAge")) {
		// pipeline.add(Aggregates.match(and(
		// gte("Age", params.get("startAge")),
		// lte("Age", params.get("endAge")))));
		// }
		// if (params.containsKey("ethniGroup")) {
		// pipeline.add(Aggregates.match(Filters.regex("EthnicGroup",
		// Pattern.compile(params.get("ethniGroup").toString()))));
		// }
		// // List<Document> docs =
		// // cardissueLogDao.aggregate(pipeline,Constant.TABLE_CARDIUSSE_LOG);
		// List<Document> docs = aggregate(instances,
		// Constant.TABLE_CARDIUSSE_LOG, pipeline);
		// return new ResultEntityF<List<Document>>(true, "not match countType",
		// docs).toJSONString();
		// }
		Map<String, Object> params = JsonUtils.fromJson(whereInfo, Map.class);
		List<String> dbNames;
		List<MongoInstance> instances = new ArrayList<>(256);
		if (params.containsKey("dbNames")) {
			dbNames = (List<String>) params.get("dbNames");
			instances = instanceManager.getInstances(dbNames.toArray());
			if (CollectionUtils.isEmpty(instances)) {
				return JsonUtils.toJson(new ResultEntityF<>(false, "query fail",
						"mongoInstance is null", null));
			}
		}
		return null;
	}
	
	@Override
	public ResultEntity queryAllCardiLogFromMongodb(String req) {
		ResultEntity resultEntity = new ResultEntity();
		Assert.hasText(req, "queryAllCardiLogFromMongodb method parameter must has content");
		String mongodbName = req;
		MongoInstance instance = mongoInstanceManager.getInstance(mongodbName.toUpperCase());
		if (instance != null) {
//			MongoClient client=null;
			try {
//				client=mongoDB.getDBClient(instance);
				Document filter=new Document();
				List<Document> list = statisticsLogDao.find(client,instance.getOperDatabase(), EnumClass.COLLECTION.cardissue_log.name(),filter);
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
	class QueryObj {

		private String startTime;// 开始时间
		private String endTime;// 结束时间
		private int startAge;
		private int endAge;
		private List<String> gender;
		private List<String> authType;
		private List<String> cardType;
		private String ethnicGroup;

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

		public int getStartAge() {
			return startAge;
		}

		public void setStartAge(int startAge) {
			this.startAge = startAge;
		}

		public int getEndAge() {
			return endAge;
		}

		public void setEndAge(int endAge) {
			this.endAge = endAge;
		}

		public List<String> getGender() {
			return gender;
		}

		public void setGender(List<String> gender) {
			this.gender = gender;
		}

		public List<String> getAuthType() {
			return authType;
		}

		public void setAuthType(List<String> authType) {
			this.authType = authType;
		}

		public List<String> getCardType() {
			return cardType;
		}

		public void setCardType(List<String> cardType) {
			this.cardType = cardType;
		}

		public String getEthnicGroup() {
			return ethnicGroup;
		}

		public void setEthnicGroup(String ethnicGroup) {
			this.ethnicGroup = ethnicGroup;
		}

	}

	@JsonInclude(value = Include.NON_NULL)
	class QueryCountObj {
		private String countType;
		private List<String> params;
		private String startTime;// 开始时间
		private String endTime;// 结束时间
		private int startAge;
		private int endAge;
		public final static String BY_CARD_TYPE = "1";
		public final static String BY_AUTH_TYPE = "2";
		public final static String BY_AGE = "3";
		public final static String BY_GENDER = "4";
		public final static String BY_DEVICE = "5";
		public final static String BY_TIME = "6";
		public final static String BY_TIME_SEG = "7";

		public final static String READER_CIRCULATION_TYPE = "31";// 读者流通类型
		public final static String AUTHENTICATION_TYPE = "32";// 认证类型
		public final static String AGE_OF_ACCREDITATION = "33";// 年龄段类型
		public final static String SEX_OF_HANDLING = "34";// 办证性别
		public final static String OPERATING_TIME_POINT = "35";// 操作时间点
		public final static String OPERATING_RESULTS = "36";// 操作结果

		public String getCountType() {
			return countType;
		}

		public void setCountType(String countType) {
			this.countType = countType;
		}

		public List<String> getParams() {
			return params;
		}

		public void setParams(List<String> params) {
			this.params = params;
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

		public int getEndAge() {
			return endAge;
		}

		public void setEndAge(int endAge) {
			this.endAge = endAge;
		}

		public int getStartAge() {
			return startAge;
		}

		public void setStartAge(int startAge) {
			this.startAge = startAge;
		}
	}
}