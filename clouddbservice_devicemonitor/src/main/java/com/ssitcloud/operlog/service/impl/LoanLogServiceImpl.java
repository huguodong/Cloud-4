package com.ssitcloud.operlog.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
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
import com.ssitcloud.common.system.Constant;
import com.ssitcloud.common.system.MongoDB;
import com.ssitcloud.common.system.MongoInstance;
import com.ssitcloud.common.system.MongoInstanceManager;
import com.ssitcloud.common.util.CloseUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.operlog.bookenum.EnumClass;
import com.ssitcloud.operlog.dao.StatisticsLogDao;
import com.ssitcloud.operlog.service.LoanLogService;
import com.ssitcloud.operlog.utils.StatisticsUtils;

@Service
public class LoanLogServiceImpl extends BasicServiceImpl implements
		LoanLogService {
	// 调用clouddbservice_statistics
	// private static final String URL_QUERSTATICS = "selectStaticsType";
	// @Resource(name = "requestURL")
	// protected RequestURLListEntity requestURL;
	private static final int SORT_DESC = -1;
	private static final int SORT_ASC = 1;
	
	@Resource(name = "mongoInstanceManager")
	private MongoInstanceManager mongoInstanceManager;
	
	@Resource(name="mongoDBImpl")
	private MongoDB mongoDB;

	private MongoClient client = MongoInstanceManager.mongoClient;
	
	@Resource
	private StatisticsLogDao statisticsLogDao;
	Map<String, String> map = new HashMap<>();
	public LoanLogServiceImpl(){
		map.put("loan_Count", "00010201");//借书
		map.put("return_Count", "00010202");//还书
		map.put("renew_Count", "00010203");//续借
	}

	/**
	 * 流通统计 2017年2月25号 lqw 
	 * whereInfo JSON格式要求：数组集合类型内部元素需加单引号 如
	 * opercmd:["'A'","'B'"] dbName：数据库名 task_type：统计类型（1年任务 2月任务 3周任务 4日任务）
	 * 年统计获取当前年份的前一年数据，月统计获取当前年月的前一个月数据，周统计当前周的前一周的数据（周一到周日） 日统计获取前一天的数据
	 * task_name：流通类型（11读者流通类型、12图书22大类、13图书馆藏地 14图书载体类型 15操作结果 16操作时间点）
	 *
	 */
	@Override
	public String countLoanLog(String whereInfo) {
		JSONObject params = JSONObject.fromObject(whereInfo);
		Bson timeAggBson = null;
		String statisticsKey = "";
		String task_name = "";
		String task_type = "";
		String dbName = "";
		String tableName = "loan_log";   
		JSONArray cirsubType = new JSONArray();  //子类统计数组
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
		if (params.containsKey("cirsubType")) {
			cirsubType = JSONArray.fromObject(params.get("cirsubType")
					.toString());
		}
//		MongoClient client = instanceManager.initClient(instance);
		if (QueryCountObject.READER_CIRCULATION_TYPE.equals(task_name)) {
			statisticsKey = "Cardtype";
			//判断mongodb是否存在字段
			List<Document> doc = statisticsLogDao.sel(client, dbName,
					tableName, statisticsKey);
			if (doc == null || doc.isEmpty()) {
				return "[]";
			}
		} else if (QueryCountObject.BOOK_CATEGORY.equals(task_name)) {
			//判断mongodb是否存在字段
			List<Document> doc = statisticsLogDao.sel(client, dbName,
					tableName, "Callno");
			if (doc == null || doc.isEmpty()) {
				return "[]";
			}
			timeAggBson = Aggregates.project(Projections.fields(Projections
					.include("opertime","opercmd"), new BasicDBObject("cate",
					new BasicDBObject("$substr",
							new Object[] { "$Callno", 0, 1}))));
			statisticsKey = "cate";
		} else if (QueryCountObject.LIBRARY_LOCATION.equals(task_name)) {
			statisticsKey = "CurrentLocation";
			//判断mongodb是否存在字段
			List<Document> doc = statisticsLogDao.sel(client, dbName,
					tableName, statisticsKey);
			if (doc == null || doc.isEmpty()) {
				return "[]";
			}
		} else if (QueryCountObject.BOOK_CARRIER_TYPE.equals(task_name)) {
			statisticsKey = "MediaType";
			//判断mongodb是否存在字段
			List<Document> doc = statisticsLogDao.sel(client, dbName,
					tableName, statisticsKey);
			if (doc == null || doc.isEmpty()) {
				return "[]";
			}
		} else if (QueryCountObject.OPERATING_RESULTS.equals(task_name)) {
			statisticsKey = "operresult";
		} else if (QueryCountObject.OPERATING_TIME_POINT.equals(task_name)) {
			timeAggBson = Aggregates.project(Projections.fields(Projections
					.include("opertime","opercmd"), new BasicDBObject("hours",
					new BasicDBObject("$substr", new Object[] { "$opertime",
							11, 2 }))));
			statisticsKey = "hours";
		} else if (QueryCountObject.AUTHENTICATION_TYPE.equals(task_name)) {
			statisticsKey = "AuthType";
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
		 *  cirsubType  统计子类数组,date 条件日期, countDate 统计日期, dateName 统计日期字段名,countType 统计结果字段名
		 */
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();// 日历对象
		switch (task_type) {
		case "1":
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy");
			int year = Integer.parseInt(df1.format(date)) - 1;
			return statByDate(cirsubType, timeAggBson, client, dbName, tableName, year+"", year+"", statisticsKey, "circulateDate","cir1");
		case "2":
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM");
			calendar.setTime(date);// 设置当前日期
			calendar.add(Calendar.MONTH, -1);// 月份减一
			String yearMM = df2.format(calendar.getTime());
			return statByDate(cirsubType, timeAggBson, client, dbName, tableName, yearMM, yearMM, statisticsKey, "circulateDate","cir2");
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
			return statByDate(cirsubType, timeAggBson, client, dbName, tableName, sb.toString(), yearMMdd, statisticsKey, "circulateDate","cir3");
		case "4":
			SimpleDateFormat df4 = new SimpleDateFormat("yyyy-MM-dd");
			calendar.setTime(date);// 设置当前日期
			calendar.add(Calendar.DATE, -1);// 日减一
			String ymd = df4.format(calendar.getTime());
			return statByDate(cirsubType, timeAggBson, client, dbName, tableName, ymd, ymd, statisticsKey, "circulateDate","cir4");
		default:
			return "[]";
		}
	}


	/**
	 * 2017年3月1号 传入String转换成JSONObject lqw 根据传入不同的字段名和对应的值封装成json
	 */
	public JSONObject toJSONObject(String i, String loan_countName,
			String loan_countValue,String return_countName,String return_countValue,String renew_countName,String renew_countValue, String dateKey, String dateValue) {
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("cirsubType", i);
			jsonObj.put(loan_countName, loan_countValue);
			jsonObj.put(return_countName, return_countValue);
			jsonObj.put(renew_countName, renew_countValue);
			jsonObj.put(dateKey, dateValue);
			return jsonObj;
		} catch (Exception e) {
			return jsonObj;
		}
	}

	
	/**
	 * 按传过来的时间统计
	 * 分三部分 借书、还书、续借统计（命令码 opercmd）
	 */
	public String statByDate(JSONArray cirsubType,Bson timeAggBson,MongoClient client,String dbName,String tableName,String date,String countDate,String statisticsKey,String DateName,String countType){
		Map<String, String> countName = StatisticsUtils.getCountName();
		String loan_datecount=countName.get("loan"+countType);
		String return_datecount=countName.get("return"+countType);
		String renew_datecount=countName.get("renew"+countType);
		String count = "0";
		JSONArray json = new JSONArray();
		int loan = 0;
		int ret = 0;
		int rew = 0;
		//循环获取子类统计数组
		for (int i = 0; i < cirsubType.size(); i++) {
			String value = cirsubType.getJSONObject(i).getString("code");
			Map<String, String> m = new HashMap<>();
			//循环借、还、续
			for (String key : map.keySet()) {
				   count = statisticsLogDao.dataStatistics(timeAggBson, client,
				   dbName, tableName, date, statisticsKey, value,map.get(key));
				   //把统计结果存入map集合 
				   m.put(key, count);
				  }
			//把结果按一定格式封装成json
			json.add(toJSONObject(value,loan_datecount, m.get("loan_Count"),return_datecount, m.get("return_Count"),renew_datecount, m.get("renew_Count"),
					DateName, countDate));
			//计算借、还、续 按子类统计数组得到的总数
			loan+=Integer.parseInt(m.get("loan_Count"));
			ret+=Integer.parseInt(m.get("return_Count"));
			rew+=Integer.parseInt(m.get("renew_Count"));
		}
		//获取借、还、续 统计总数
		int suml = statisticsLogDao.total(client, dbName, tableName, date,map.get("loan_Count"));
		int sumr = statisticsLogDao.total(client, dbName, tableName, date,map.get("return_Count"));
		int sumw = statisticsLogDao.total(client, dbName, tableName, date,map.get("renew_Count"));
		int other1=suml-loan;
		int other2=sumr-ret;
		int other3=sumw-rew;
		//计算其他类型总计结果并封装成json
		if(other1 >0 || other2 >0 || other3 >0){
			json.add(toJSONObject("other",loan_datecount, other1+"",return_datecount,other2+"",renew_datecount, other3+"",
					DateName, countDate));
		}
		return json.toString();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean addLoanLog(String loanlogInfo) {
		Map<String, Object> params = JsonUtils.fromJson(loanlogInfo, Map.class);
		String dbName = "";
		MongoInstance instance = null;
		if (params.containsKey("dbName")) {
			dbName = params.get("dbName").toString();
			instance = instanceManager.getInstance(dbName);
			if (instance == null) {
				return false;
			}
		}
		insertOneJSON(loanlogInfo, instance, Constant.TABLE_LOAN_LOG);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryLoanLog(String whereInfo) {
		// queryLoanLog
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
		// return queryStart(params, instances, "loanLog.queryLoanLog");
	}
	
	

	@Override
	public ResultEntity queryAllLoanLogFromMongodb(String req) {
		ResultEntity resultEntity = new ResultEntity();
		Assert.hasText(req, "queryAllLoanLogFromMongodb method parameter must has content");
		String mongodbName = req;
		MongoInstance instance = mongoInstanceManager.getInstance(mongodbName.toUpperCase());
		if (instance != null) {
//			MongoClient client=null;
			try {
//				client=mongoDB.getDBClient(instance);
				Document filter=new Document();
				List<Document> list = statisticsLogDao.find(client,instance.getOperDatabase(), EnumClass.COLLECTION.loan_log.name(),filter);
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
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity queryLoanLogFromMongodb(String req) {
		ResultEntity resultEntity = new ResultEntity();
		Assert.hasText(req, "queryLoanLogFromMongodb method parameter must has content");
		Map<String, String> map = JsonUtils.fromJson(req, Map.class);
		String mongodbName = map.get("mongodbName");
		String id = map.get("id");
		int skip = 0;
		int limit = 5000;//单次取5000条
		MongoInstance instance = mongoInstanceManager.getInstance(mongodbName.toUpperCase());
		if (instance != null) {
//			MongoClient client=null;
			try {
//				client=mongoDB.getDBClient(instance);
				Document filter = new Document();
				Document projection = new Document();
				Document sort = new Document();
				if (StringUtils.isNotBlank(id)) {
					Document gt = new Document();
					gt.put("$gt", new ObjectId(id));
					filter.put("_id", gt);
				}
				sort.put("_id", SORT_ASC);
				List<Document> list = statisticsLogDao.find(client,instance.getOperDatabase(), EnumClass.COLLECTION.loan_log.name(),filter, projection, sort, limit , skip);
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
	
	
	/**
	 * 分页查询
	 * 参数：id ：数据库的中的id。用来标志上一次查询的截止位置，下一次查询从该位置开始 
	 * 			如果id为空则表示第一次查询或第一页的数据
	 * 		limit :每次返回的数据量，默认2000
	 * 		mongodbName：数据库名称
	 * 		
	 * @param req
	 * @return
	 */
	public ResultEntity queryLoanLogByPage(String req){
		ResultEntity resultEntity = new ResultEntity();
		if(StringUtils.isBlank(req)){
			resultEntity.setState(false);
			resultEntity.setMessage("参数为空");
			return resultEntity;
		}
		int defaultLimit = 5000;//默认每次去2000条数据
		int skip = 0;//不采用跳转的方式来分页
		
		//数据库名称
		Map<String, String> map = JsonUtils.fromJson(req, Map.class);
		String mongodbName = map.get("mongodbName");
		
		//数据量大小
		Object limit = map.get("limit");
		if(limit != null && limit.toString().length() > 0){
			defaultLimit = Integer.parseInt(limit.toString());
		}
		
		//上一次获取数据的标志位。如果id为空，表示一次查询数据库，从数据库的第一个位置开始查询
		//否则从该位置开始获取
		String id = map.get("id");
		
		MongoInstance instance = mongoInstanceManager.getInstance(mongodbName.toUpperCase());
		
		if (instance != null) {
//			MongoClient client=null;
			try {
//				client=mongoDB.getDBClient(instance);
				//添加过滤条件
				Document filter = new Document();
				Document sort = new Document();
				Document projection = new Document();
				if (StringUtils.isNotBlank(id)) {
					Document gt = new Document();
					gt.put("$gt", new ObjectId(id));
					filter.put("_id", gt);
				}
				sort.put("_id", SORT_ASC);
				List<Document> list = statisticsLogDao.find(client,instance.getOperDatabase(), EnumClass.COLLECTION.loan_log.name(),filter, projection, sort, defaultLimit , skip);
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
	






	/***
	 * 流通查询
	 * 
	 * @author lbh
	 * 
	 *         2016年3月25日
	 **/
	@JsonInclude(value = Include.NON_NULL)
	class QueryObject {
		private List<String> opercmd; // 流通类型
		private List<String> operresult; // 操作结果
		private List<String> cirType; // 读者证类型
		private List<String> PermanentLocation; // 馆藏地点
		private List<String> CirculationType; // 图书流通类型
		private List<String> MediaType; // 媒介类型
		private List<String> classNo; // 22大类
		private String name; // 读者名字
		private String cardNo; // 读者证号
		private String Barcode; // 图书条码
		private String Title; // 图书题名
		private String Callno; // 索取号
		private String startTime; // 开始时间
		private String endTime; // 结束时间

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCardNo() {
			return cardNo;
		}

		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}

		public String getBarcode() {
			return Barcode;
		}

		public void setBarcode(String barcode) {
			Barcode = barcode;
		}

		public String getTitle() {
			return Title;
		}

		public void setTitle(String title) {
			Title = title;
		}

		public String getCallno() {
			return Callno;
		}

		public void setCallno(String callno) {
			Callno = callno;
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

		public List<String> getOpercmd() {
			return opercmd;
		}

		public void setOpercmd(List<String> opercmd) {
			this.opercmd = opercmd;
		}

		public List<String> getOperresult() {
			return operresult;
		}

		public void setOperresult(List<String> operresult) {
			this.operresult = operresult;
		}

		public List<String> getCirType() {
			return cirType;
		}

		public void setCirType(List<String> cirType) {
			this.cirType = cirType;
		}

		public List<String> getPermanentLocation() {
			return PermanentLocation;
		}

		public void setPermanentLocation(List<String> permanentLocation) {
			PermanentLocation = permanentLocation;
		}

		public List<String> getCirculationType() {
			return CirculationType;
		}

		public void setCirculationType(List<String> circulationType) {
			CirculationType = circulationType;
		}

		public List<String> getMediaType() {
			return MediaType;
		}

		public void setMediaType(List<String> mediaType) {
			MediaType = mediaType;
		}

		public List<String> getClassNo() {
			return classNo;
		}

		public void setClassNo(List<String> classNo) {
			this.classNo = classNo;
		}

	}

	/***
	 * 流通统计查询封装类
	 * 
	 * @author lbh
	 * 
	 *         2016年3月23日
	 **/
	@JsonInclude(value = Include.NON_NULL)
	class QueryCountObject {
		private String countType; // 统计类型
		private String startTime; // 开始时间
		private String endTime; // 结束时间
		private String cardNo; // 读者证号
		private List<String> params; // 统计分类参数
		private List<String> opercmd; // 流通类型 续借 借书 还书

		public final static String BY_CLASS = "1";
		public final static String BY_TIME = "2";
		public final static String BY_RESULT = "3";
		public final static String BY_AUTHTYPE = "4";
		public final static String BY_READER_AGE = "5";
		public final static String BY_READER_GENDER = "6";
		public final static String BY_RER_LOC = "7";
		public final static String BY_CIR_TYPE = "8";
		public final static String BY_MEDIA_TYPE = "9";
		public final static String BY_DEVICE = "10";
		public final static String READER_CIRCULATION_TYPE = "11";// 读者流通类型
		public final static String BOOK_CATEGORY = "12";// 图书22大类
		public final static String LIBRARY_LOCATION = "13";// 图书馆藏地
		public final static String BOOK_CARRIER_TYPE = "14";// 图书载体类型
		public final static String OPERATING_RESULTS = "15";// 操作结果
		public final static String OPERATING_TIME_POINT = "16";// 操作时间点
		public final static String AUTHENTICATION_TYPE = "17";// 认证类型

		public String getCountType() {
			return countType;
		}

		public void setCountType(String countType) {
			this.countType = countType;
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

		public String getCardNo() {
			return cardNo;
		}

		public void setCardNo(String cardNo) {
			this.cardNo = cardNo;
		}

		public List<String> getParams() {
			return params;
		}

		public void setParams(List<String> params) {
			this.params = params;
		}

		public List<String> getOpercmd() {
			return opercmd;
		}

		public void setOpercmd(List<String> opercmd) {
			this.opercmd = opercmd;
		}

	}

	/***
	 * 统计结果暂存一个类
	 * 
	 * @author lbh
	 * 
	 *         2016年3月23日
	 **/
	@JsonInclude(value = Include.NON_NULL)
	class ResultObj {

		private String classNo;
		private String opercmd; // 流通类型 续借 借书 还书
		private int count; // 计数
		private String operresult; // 操作结果
		private String month; // 月份
		private String cirName; // 读者证类型名称
		private String cirType; // 读者证类型
		private String PermanentLocation; // 馆藏地点
		private String CirculationType; // 图书流通类型
		private String MediaType; // 媒介类型 光盘、图书
		private String sex; // 读者性别

		public String getClassNo() {
			return classNo;
		}

		public void setClassNo(String classNo) {
			this.classNo = classNo;
		}

		public String getOpercmd() {
			return opercmd;
		}

		public void setOpercmd(String opercmd) {
			this.opercmd = opercmd;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public String getOperresult() {
			return operresult;
		}

		public void setOperresult(String operresult) {
			this.operresult = operresult;
		}

		public String getMonth() {
			return month;
		}

		public void setMonth(String month) {
			this.month = month;
		}

		public String getCirType() {
			return cirType;
		}

		public void setCirType(String cirType) {
			this.cirType = cirType;
		}

		public String getCirName() {
			return cirName;
		}

		public void setCirName(String cirName) {
			this.cirName = cirName;
		}

		public String getPermanentLocation() {
			return PermanentLocation;
		}

		public void setPermanentLocation(String permanentLocation) {
			PermanentLocation = permanentLocation;
		}

		public String getCirculationType() {
			return CirculationType;
		}

		public void setCirculationType(String circulationType) {
			CirculationType = circulationType;
		}

		public String getMediaType() {
			return MediaType;
		}

		public void setMediaType(String mediaType) {
			MediaType = mediaType;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

	}
}