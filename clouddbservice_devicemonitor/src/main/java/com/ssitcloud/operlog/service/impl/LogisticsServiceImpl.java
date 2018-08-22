/*package com.ssitcloud.operlog.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.operlog.bookenum.EnumClass;
import com.ssitcloud.operlog.dao.LogisticsDao;
import com.ssitcloud.operlog.service.LogisticsService;

@Service
public class LogisticsServiceImpl extends BasicServiceImpl implements
		LogisticsService {

	@Resource
	private LogisticsDao logisticsDao;

	*//**
	 * 
	 * 函数说明：物流统计 A、根据条件统计物流信息(bookbox_log,cashbox_log,cardbox_log)
	 * B、whereInfo图书使用查询条件,tableName表名 C、whereInfo的条件大致分为图书流转，现金流转，卡箱补卡
	 * 
	 * @param @param whereInfo
	 * @param @param tableName
	 * @param @return
	 * @return String
	 * @throws
	 * @author lbh
	 * @date 2016年3月29日
	 *//*
	@Override
	public String countLogistics(String whereInfo, String tableName) {
		QuertCountObjL quertObj = JsonUtils.fromJson(whereInfo,
				QuertCountObjL.class);

		if (EnumClass.COLLECTION.cashbox_log.name().equals(tableName)) {
			return countCashBox(quertObj);
		}
		if (EnumClass.COLLECTION.cardbox_log.equals(tableName)) {
			return countCardBox(quertObj);
		}

		return null;
	}

	// 现金流转
	private String countCashBox(QuertCountObjL queryObj) {
		Bson time = null;
		if (queryObj.getStartTime() != null && queryObj.getEndTime() != null) {
			time = Aggregates.match(Filters.and(
					Filters.gte("opertime", queryObj.getStartTime()),
					Filters.lte("opertime", queryObj.getEndTime())));
		}
		// 根据设备进行统计
		if (QuertCountObjL.BY_DEVICE.equals(queryObj.getCountType())) {
			List<Bson> pipiLine = new ArrayList<Bson>();
			if (time != null) {
				pipiLine.add(time);
			}
			pipiLine.add(Aggregates.group("sum",
					Accumulators.sum("count", "$Money")));
			List<Document> docs = logisticsDao.aggregate(pipiLine,
					EnumClass.COLLECTION.cashbox_log.name());
			return new ResultEntityF<List<Document>>(true, "query success",
					docs).toJSONString();
		}

		return null;
	}

	// 卡箱补卡
	private String countCardBox(QuertCountObjL quertObj) {
		return null;
	}

	@Override
	public String queryLogistics(String whereInfo, String tableName) {
		QuertCountObjL queryObj = JsonUtils.fromJson(whereInfo,
				QuertCountObjL.class);

		if (EnumClass.COLLECTION.cashbox_log.name().equals(tableName)) {
			return queryCashBox(queryObj);
		}
		if (EnumClass.COLLECTION.cardbox_log.equals(tableName)) {
			return queryCardBox(queryObj);
		}

		return null;
	}

	// 查询卡箱
	private String queryCardBox(QuertCountObjL queryObj) {
		Bson time = null;
		if (queryObj.getStartTime() != null && queryObj.getEndTime() != null) {
			time = Aggregates.match(Filters.and(
					Filters.gte("opertime", queryObj.getStartTime()),
					Filters.lte("opertime", queryObj.getEndTime())));
		}
		List<Bson> pipiLine = new ArrayList<Bson>();
		if (time != null) {
			pipiLine.add(time);
		}
		pipiLine.add(Aggregates.sort(Sorts.descending("Cardcount")));
		// pipiLine.add(Aggregates.skip(10));
		pipiLine.add(Aggregates.limit(10));
		List<Document> docs = logisticsDao.aggregate(pipiLine,
				EnumClass.COLLECTION.cardbox_log.name());
		return new ResultEntityF<List<Document>>(true, "query success", docs)
				.toJSONString();
	}

	private String queryCashBox(QuertCountObjL queryObj) {

		return null;
	}
}

class QuertCountObjL {
	private String countType;
	private String tableName;
	private String startTime;
	private String endTime;
	public static final String BY_DEVICE = "1";

	public String getCountType() {
		return countType;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
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
*/