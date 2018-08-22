/*package com.ssitcloud.operlog.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.system.Constant;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.operlog.dao.OperlogDao;
import com.ssitcloud.operlog.entity.LoanLogEntity;
import com.ssitcloud.operlog.service.OperlogService;

@Service
public class OperlogServiceImpl extends BasicServiceImpl implements
		OperlogService {

	@Resource
	private OperlogDao operlogDao;

	@Override
	public String countLoanLog(String whereInfo) {

		QueryObjectz queryObj = JsonUtils.fromJson(whereInfo,
				QueryObjectz.class);

		AggregationOperation timeArea = Aggregation.match(Criteria
				.where("opertime").gte(queryObj.getStartTime())
				.lte(queryObj.getEndTime()));
		AggregationOperation opercmd = Aggregation.match(Criteria.where(
				"opercmd").in(queryObj.getOpercmd()));

		AggregationOperation mo = Aggregation.match(Criteria.where("operator")
				.regex(Pattern.compile(queryObj.getCardNo())));

		// 22大类
		if (QueryObjectz.BY_CLASS.equals(queryObj.getCountType())) {
			AggregationOperation matchClassNo = Aggregation.match(Criteria
					.where("classNo").in(queryObj.getParams()));
			AggregationOperation group = Aggregation
					.group("classNo", "opercmd").count().as("count");
			AggregationOperation sort = Aggregation.sort(Sort.Direction.ASC,
					"_id.classNo");

			Aggregation agg = null;
			if (mo != null) {
				agg = Aggregation.newAggregation(mo, timeArea, matchClassNo,
						opercmd, group, sort);
			}
			agg = Aggregation.newAggregation(timeArea, matchClassNo, opercmd,
					group, sort);
			List<ResultObjz> objs = operlogDao.aggregate(agg,
					Constant.TABLE_LOAN_LOG, ResultObjz.class);
			return new ResultEntityF<>(true, "query success", objs)
					.toJSONString();
		}
		// 按月统计
		if (QueryObjectz.BY_TIME.equals(queryObj.getCountType())) {
			List<Bson> pipeline = new ArrayList<Bson>();
			MongoCollection<Document> collection = operlogDao
					.getCollectionVersion3(Constant.TABLE_LOAN_LOG);

			List<ResultObjz> result = new ArrayList<ResultObjz>(100);

			BasicDBObject project = new BasicDBObject(
					"$project",
					new BasicDBObject("opertime", 1)
							.append("opercmd", 1)
							.append("operator", 1)
							.append("month",
									new BasicDBObject(
											"$substr",
											new Object[] {
													new BasicDBObject(
															"$ifNull",
															new String[] {
																	"$ItemLoanDate",
																	"$ItemReturnDate" }),
													4, 2 })));
			pipeline.add(project);
			if (mo != null) {
				Pattern cardNoPattern = Pattern.compile(queryObj.getCardNo(),
						Pattern.CASE_INSENSITIVE);
				BasicDBObject cardNo = new BasicDBObject("$match",
						new BasicDBObject("operator", cardNoPattern));
				pipeline.add(cardNo);
			}
			if (timeArea != null) {
				BasicDBObject timeAreaDBObject = new BasicDBObject("$match",
						new BasicDBObject("opertime", new BasicDBObject("$gte",
								queryObj.getStartTime()).append("$lte",
								queryObj.getEndTime())));
				pipeline.add(timeAreaDBObject);
			}
			BasicDBObject opercmds = new BasicDBObject("$match",
					new BasicDBObject("opercmd", new BasicDBObject("$in",
							queryObj.getOpercmd().toArray())));
			BasicDBObject group = new BasicDBObject("$group",
					new BasicDBObject("_id", new BasicDBObject("opercmd",
							"$opercmd").append("month", "$month")).append(
							"count", new BasicDBObject("$sum", 1)));
			BasicDBObject sort = new BasicDBObject("$sort", new BasicDBObject(
					"month", 1));
			pipeline.add(opercmds);
			pipeline.add(group);
			pipeline.add(sort);

			AggregateIterable<Document> aiter = collection.aggregate(pipeline);
			MongoCursor<Document> mc = aiter.iterator();
			while (mc.hasNext()) {
				Document doc = mc.next();
				ResultObjz res = new ResultObjz();
				res.setOpercmd(((Document) doc.get("_id")).getString("opercmd"));
				res.setMonth(((Document) doc.get("_id")).getString("month"));
				res.setCount(doc.getInteger("count"));
				result.add(res);
			}
			mc.close();
			return new ResultEntityF<>(true, "query success", result)
					.toJSONString();
		}
		// 按操作结果统计
		if (QueryObjectz.BY_RESULT.equals(queryObj.getCountType())) {
			AggregationOperation operresult = Aggregation.match(Criteria.where(
					"operresult").in(queryObj.getParams()));
			AggregationOperation group = Aggregation
					.group("operresult", "opercmd").count().as("count");
			Aggregation agg = null;
			if (mo != null) {
				agg = Aggregation.newAggregation(mo, timeArea, operresult,
						opercmd, group);
			} else {
				agg = Aggregation.newAggregation(timeArea, operresult, opercmd,
						group);
			}
			List<ResultObjz> objs = operlogDao.aggregate(agg,
					Constant.TABLE_LOAN_LOG, ResultObjz.class);
			return new ResultEntityF<>(true, "query success", objs)
					.toJSONString();
		}
		// 按读者证类型统计
		if (QueryObjectz.BY_AUTHTYPE.equals(queryObj.getCountType())) {
			List<String> cirTypeList = queryObj.getParams();
			AggregationOperation cirTypeMatch = Aggregation.match(Criteria
					.where("cirType").in(cirTypeList));
			AggregationOperation group = Aggregation
					.group("opercmd", "cirType", "cirName").count().as("count");
			Aggregation agg = null;
			if (mo != null) {
				agg = Aggregation.newAggregation(mo, timeArea, opercmd,
						cirTypeMatch, group);
			} else {
				agg = Aggregation.newAggregation(timeArea, opercmd,
						cirTypeMatch, group);
			}
			List<ResultObjz> objs = operlogDao.aggregate(agg,
					Constant.TABLE_LOAN_LOG, ResultObjz.class);
			return new ResultEntityF<>(true, "query success", objs)
					.toJSONString();
		}
		// 按照读者年龄统计
		if (QueryObjectz.BY_READER_AGE.equals(queryObj.getCountType())) {
			ProjectionOperation project = Aggregation.project("opercmd", "sex",
					"operator", "opertime");
			MatchOperation match = Aggregation.match(Criteria.where("sex").in(
					queryObj.getParams()));
			GroupOperation group = Aggregation.group("sex", "opercmd").count()
					.as("count");
			Aggregation agg = null;
			if (mo != null) {
				agg = Aggregation.newAggregation(project, mo, timeArea,
						opercmd, match, group);
			} else {
				agg = Aggregation.newAggregation(project, timeArea, opercmd,
						match, group);
			}
			List<ResultObjz> objs = operlogDao.aggregate(agg,
					Constant.TABLE_LOAN_LOG, ResultObjz.class);
			return new ResultEntityF<>(true, "query success", objs)
					.toJSONString();

		}

		// 按读者性别统计
		if (QueryObjectz.BY_READER_GENDER.equals(queryObj.getCountType())) {
			ProjectionOperation project = Aggregation.project("opercmd", "sex",
					"operator", "opertime");
			MatchOperation match = Aggregation.match(Criteria.where("sex").in(
					queryObj.getParams()));
			GroupOperation group = Aggregation.group("sex", "opercmd").count()
					.as("count");
			Aggregation agg = null;
			if (mo != null) {
				agg = Aggregation.newAggregation(project, mo, timeArea,
						opercmd, match, group);
			} else {
				agg = Aggregation.newAggregation(project, timeArea, opercmd,
						match, group);
			}
			List<ResultObjz> objs = operlogDao.aggregate(agg,
					Constant.TABLE_LOAN_LOG, ResultObjz.class);
			return new ResultEntityF<>(true, "query success", objs)
					.toJSONString();

		}
		// 按图书馆藏地点统计
		if (QueryObjectz.BY_RER_LOC.equals(queryObj.getCountType())) {
			ProjectionOperation project = Aggregation.project("opercmd",
					"PermanentLocation", "operator", "opertime");
			MatchOperation match = Aggregation.match(Criteria.where(
					"PermanentLocation").in(queryObj.getParams()));
			GroupOperation group = Aggregation
					.group("opercmd", "PermanentLocation").count().as("count");
			Aggregation agg = null;
			if (mo != null) {
				agg = Aggregation.newAggregation(project, mo, timeArea,
						opercmd, match, group);
			} else {
				agg = Aggregation.newAggregation(project, timeArea, opercmd,
						match, group);
			}
			List<ResultObjz> objs = operlogDao.aggregate(agg,
					Constant.TABLE_LOAN_LOG, ResultObjz.class);
			return new ResultEntityF<>(true, "query success", objs)
					.toJSONString();
		}
		// 按流通类型统计
		if (QueryObjectz.BY_CIR_TYPE.equals(queryObj.getCountType())) {
			ProjectionOperation project = Aggregation.project("opercmd",
					"CirculationType", "operator", "opertime");
			MatchOperation match = Aggregation.match(Criteria.where(
					"CirculationType").in(queryObj.getParams()));
			GroupOperation group = Aggregation
					.group("opercmd", "CirculationType").count().as("count");
			Aggregation agg = null;
			if (mo != null) {
				agg = Aggregation.newAggregation(project, mo, timeArea,
						opercmd, match, group);
			} else {
				agg = Aggregation.newAggregation(project, timeArea, opercmd,
						match, group);
			}
			List<ResultObjz> objs = operlogDao.aggregate(agg,
					Constant.TABLE_LOAN_LOG, ResultObjz.class);
			return new ResultEntityF<>(true, "query success", objs)
					.toJSONString();
		}
		// 按媒体类型统计
		if (QueryObjectz.BY_MEDIA_TYPE.equals(queryObj.getCountType())) {
			ProjectionOperation project = Aggregation.project("opercmd",
					"MediaType", "operator", "opertime");
			MatchOperation match = Aggregation.match(Criteria
					.where("MediaType").in(queryObj.getParams()));
			GroupOperation group = Aggregation.group("opercmd", "MediaType")
					.count().as("count");
			Aggregation agg = null;
			if (mo != null) {
				agg = Aggregation.newAggregation(project, mo, timeArea,
						opercmd, match, group);
			} else {
				agg = Aggregation.newAggregation(project, timeArea, opercmd,
						match, group);
			}
			List<ResultObjz> objs = operlogDao.aggregate(agg,
					Constant.TABLE_LOAN_LOG, ResultObjz.class);
			return new ResultEntityF<>(true, "query success", objs)
					.toJSONString();
		}
		// 按设备地点统计,即统计全部
		if (QueryObjectz.BY_DEVICE.equals(queryObj.getCountType())) {
			List<Bson> pipeline = new ArrayList<Bson>();
			MongoCollection<Document> collection = operlogDao
					.getCollectionVersion3(Constant.TABLE_LOAN_LOG);
			List<ResultObjz> result = new ArrayList<ResultObjz>(100);
			BasicDBObject project = new BasicDBObject("$project",
					new BasicDBObject("opercmd", 1).append("operator", 1)
							.append("opertime", 1));
			pipeline.add(project);
			if (mo != null) {
				Pattern cardNoPattern = Pattern.compile(queryObj.getCardNo(),
						Pattern.CASE_INSENSITIVE);
				BasicDBObject cardNo = new BasicDBObject("$match",
						new BasicDBObject("operator", cardNoPattern));
				pipeline.add(cardNo);
			}
			if (timeArea != null) {
				BasicDBObject timeAreaDBObject = new BasicDBObject("$match",
						new BasicDBObject("opertime", new BasicDBObject("$gte",
								queryObj.getStartTime()).append("$lte",
								queryObj.getEndTime())));
				pipeline.add(timeAreaDBObject);
			}
			BasicDBObject opercmdByDevice = new BasicDBObject("$match",
					new BasicDBObject("opercmd", new BasicDBObject("$in",
							queryObj.getOpercmd().toArray())));

			BasicDBObject groupByDevice = new BasicDBObject("$group",
					new BasicDBObject("_id", new BasicDBObject("opercmd",
							"$opercmd")).append("count", new BasicDBObject(
							"$sum", 1)));
			pipeline.add(opercmdByDevice);
			pipeline.add(groupByDevice);
			AggregateIterable<Document> aite = collection.aggregate(pipeline);
			MongoCursor<Document> mc = aite.iterator();
			while (mc.hasNext()) {
				Document doc = mc.next();
				ResultObjz res = new ResultObjz();
				res.setOpercmd(((Document) doc.get("_id")).getString("opercmd"));
				res.setCount(doc.getInteger("count"));
				result.add(res);
			}
			mc.close();
			return new ResultEntityF<>(true, "query success", result)
					.toJSONString();

		}
		return new ResultEntityF<>(false, "not match countType", null)
				.toJSONString();
	}

	@Override
	public boolean addLoanLog(String loanlogInfo) {
		LoanLogEntity loanLog = JsonUtils.fromJson(loanlogInfo,
				LoanLogEntity.class);
		operlogDao.insertOne(Constant.TABLE_LOAN_LOG, loanLog);
		return true;
	}

}

*//**
 * 查询封装类
 * 
 * @author lbh
 * 
 *         2016年3月23日
 *//*
@JsonInclude(value = Include.NON_NULL)
class QueryObjectz {
	private String countType;// 统计类型
	private String startTime;// 开始时间
	private String endTime;// 结束时间
	private String cardNo;// 读者证号
	private List<String> params;// 统计分类参数
	// private List<String> cirType;//读者证类型 10001:50元B级普通读者
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

*//**
 * 结果暂存一个类
 * 
 * @author lbh
 * 
 *         2016年3月23日
 *//*
@JsonInclude(value = Include.NON_NULL)
class ResultObjz {

	private String classNo;
	private String opercmd;// 流通类型 续借 借书 还书
	private int count;// 计数
	private String operresult;// 操作结果
	private String month;// 月份
	private String cirName;// 读者证类型名称
	private String cirType;// 读者证类型
	private String PermanentLocation;// 馆藏地点
	private String CirculationType;// 图书流通类型
	private String MediaType;// 媒介类型 光盘、图书
	private String sex;// 读者性别

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

}*/