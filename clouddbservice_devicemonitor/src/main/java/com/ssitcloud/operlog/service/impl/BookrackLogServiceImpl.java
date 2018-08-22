/*package com.ssitcloud.operlog.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.system.MongoInstance;
import com.ssitcloud.common.util.DateUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.operlog.service.BookrackLogService;

@Service
public class BookrackLogServiceImpl extends BasicServiceImpl implements
		BookrackLogService {

	@SuppressWarnings("unchecked")
	@Override
	public String countBookUsed(String whereInfo) {
		Map<String, Object> params = JsonUtils.fromJson(whereInfo, Map.class);
		String countType = "";
		if (params.containsKey("countType")) {
			countType = params.get("countType").toString();
		}
		List<MongoInstance> instances = getInstances(params);
		if (CollectionUtils.isEmpty(instances)) {
			return new ResultEntityF<>(false, "query fail",
					"mongoInstance is null", null).toJSONString();
		}

		// QueryObjBookrack queryObj =
		// JsonUtils.fromJson(whereInfo,QueryObjBookrack.class);
		Bson time = null;
		if (params.containsKey("startTime") && params.containsKey("endTime")) {
			time = Aggregates.match(Filters.and(
					Filters.gte("opertime", params.get("startTime")),
					Filters.lte("opertime", params.get("endTime"))));
		}

		// 22大类统计
		if (QueryObjBookrack.BY_CLASSNO.equals(countType)) {
			List<Bson> pipeline = new ArrayList<>();
			List<Bson> pipelineT = new ArrayList<>();
			if (time != null) {
				pipeline.add(time);
				pipelineT.add(time);
			}
			// 较大的时间 当前时间-50
			if (params.containsKey("onBookrackStartTime")) {
				int onRackStartTime = Integer.parseInt(params.get(
						"onBookrackStartTime").toString());
				String endTime = new DateTime().minusDays(onRackStartTime)
						.toString(DateUtil.format_2);
				pipeline.add(Aggregates.match(Filters.lte("opertime", endTime)));
				pipelineT
						.add(Aggregates.match(Filters.lte("opertime", endTime)));

			}
			// 20160208171853
			// 20150603171853
			// 较小的时间 当前时间-300
			if (params.containsKey("onBookrackEndTime")) {
				int onRackEndTime = Integer.parseInt(params.get(
						"onBookrackEndTime").toString());
				String startTime = new DateTime().minusDays(onRackEndTime)
						.toString(DateUtil.format_2);
				pipeline.add(Aggregates.match(Filters
						.gte("opertime", startTime)));
				pipelineT.add(Aggregates.match(Filters.gte("opertime",
						startTime)));
			}

			pipeline.add(Aggregates.match(Filters.and(Filters.eq("State", "1"),
					Filters.exists("operator", true),
					Filters.nin("operator", new String[] { "", "null" }))));
			pipeline.add(Aggregates.group("$ClassNo",
					Accumulators.sum("count", 1)));
			pipelineT
					.add(Aggregates.match(Filters.and(Filters.eq("State", "1"))));
			pipelineT.add(Aggregates.group("$ClassNo",
					Accumulators.sum("count", 1)));
			pipeline.add(Aggregates.sort(Sorts.ascending("_id")));
			pipelineT.add(Aggregates.sort(Sorts.ascending("_id")));
			// List<Document> docs=bookrackLogDao.aggregate(pipeline,
			// "bookrack_log");
			// List<Document> docsT=bookrackLogDao.aggregate(pipelineT,
			// "bookrack_log");
			List<Document> docs = aggregate(instances, "bookrack_log", pipeline);
			List<Document> docsT = aggregate(instances, "bookrack_log",
					pipelineT);

			return new ResultEntityF<ReturnRes>(true, "query sucess",
					new ReturnRes(docs, docsT)).toJSONString();
		}
		// 设备统计
		if (QueryObjBookrack.BY_DEVICE.equals(countType)) {
			List<Bson> pipeline = new ArrayList<>();
			List<Bson> pipelineT = new ArrayList<>();

			if (time != null) {
				pipeline.add(time);
				pipelineT.add(time);
			}
			// 较小的时间
			if (params.containsKey("onBookrackStartTime")) {
				int onRackStartTime = Integer.parseInt(params.get(
						"onBookrackStartTime").toString());
				String endTime = new DateTime().minusDays(onRackStartTime)
						.toString(DateUtil.format_2);
				pipeline.add(Aggregates.match(Filters.lte("opertime", endTime)));
				pipelineT
						.add(Aggregates.match(Filters.lte("opertime", endTime)));

			}
			// 较大的时间
			if (params.containsKey("onBookrackEndTime")) {
				int onRackEndTime = Integer.parseInt(params.get(
						"onBookrackStartTime").toString());
				String startTime = new DateTime().minusDays(onRackEndTime)
						.toString(DateUtil.format_2);
				pipeline.add(Aggregates.match(Filters
						.gte("opertime", startTime)));
				pipelineT.add(Aggregates.match(Filters.gte("opertime",
						startTime)));
			}

			pipeline.add(Aggregates.match(Filters.and(Filters.eq("State", "1"),
					Filters.exists("operator", true),
					Filters.nin("operator", new String[] { "", "null" }))));
			pipeline.add(Aggregates.group("sum", Accumulators.sum("count", 1)));
			// List<Document> docs =
			// bookrackLogDao.aggregate(pipeline,"bookrack_log");
			List<Document> docs = aggregate(instances, "bookrack_log", pipeline);
			pipelineT
					.add(Aggregates.match(Filters.and(Filters.eq("State", "1"))));
			pipelineT
					.add(Aggregates.group("sum", Accumulators.sum("count", 1)));
			pipelineT.add(Aggregates.sort(Sorts.ascending("_id")));
			// List<Document> docsT =
			// bookrackLogDao.aggregate(pipelineT,"bookrack_log");
			List<Document> docsT = aggregate(instances, "bookrack_log",
					pipelineT);
			return new ResultEntityF<ReturnRes>(true, "query sucess",
					new ReturnRes(docs, docsT)).toJSONString();
		}
		return new ResultEntityF<Object>(false, "no match countType", null)
				.toJSONString();
	}

}

class ReturnRes {
	// 图书流通数量
	private List<Document> cir;
	// 藏书总数
	private List<Document> total;

	public ReturnRes() {

	}

	public ReturnRes(List<Document> cir, List<Document> total) {
		this.cir = cir;
		this.total = total;
	}

	public List<Document> getTotal() {
		return total;
	}

	public void setTotal(List<Document> total) {
		this.total = total;
	}

	public List<Document> getCir() {
		return cir;
	}

	public void setCir(List<Document> cir) {
		this.cir = cir;
	}

}

@JsonInclude(value = Include.NON_NULL)
class QueryObjBookrack {
	private String countType;
	private String startTime;
	private String endTime;
	private String onRackStartTime;
	private String onRackEndTime;
	private List<String> params;
	public static final String BY_CLASSNO = "1";
	public static final String BY_DEVICE = "2";// 设备
	public static final String BY_TIME_SEG = "3";// 时长

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

	public String getOnRackStartTime() {
		return onRackStartTime;
	}

	public void setOnRackStartTime(String onRackStartTime) {
		this.onRackStartTime = onRackStartTime;
	}

	public String getOnRackEndTime() {
		return onRackEndTime;
	}

	public void setOnRackEndTime(String onRackEndTime) {
		this.onRackEndTime = onRackEndTime;
	}

}*/