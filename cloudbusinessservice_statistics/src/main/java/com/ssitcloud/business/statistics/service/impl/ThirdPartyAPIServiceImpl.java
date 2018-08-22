package com.ssitcloud.business.statistics.service.impl;

import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.codehaus.jackson.type.TypeReference;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.authentication.entity.RelLibsEntity;
import com.ssitcloud.business.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.statistics.common.utils.JsonUtils;
import com.ssitcloud.business.statistics.service.ElasticsearchStatisticsService;
import com.ssitcloud.business.statistics.service.MainPageService;
import com.ssitcloud.business.statistics.service.ThirdPartyAPIService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class ThirdPartyAPIServiceImpl extends BasicServiceImpl implements ThirdPartyAPIService {
	@Resource
	ElasticsearchStatisticsService elasticsearchStatisticsService;
	@Resource
	MainPageService mainPageService;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private String format = "yyyyMMddHHmmss";
	private GregorianCalendar gc = new GregorianCalendar();
	private static Client client = null;
	static {
		if (client == null)
			client = ElasticsearchStatisticsServiceImpl.getClient();
	}

	@Override
	public JSONObject borrowOrderInfo(Map<String, String> param) {
		String library_idx = param.get("library_idx");
		String operresult = param.get("operresult") == null ? "0" : param.get("operresult");
		int rows = param.get("rows") == null ? 10 : Integer.parseInt(param.get("rows"));

		JSONObject obj = new JSONObject();
		String start_time = param.get("start_time");
		if (StringUtils.hasText(start_time)) {
			start_time = parse(start_time, format);
			if (StringUtils.isEmpty(start_time)) {
				obj.put("result", 0);
				obj.put("error_msg", "开始时间格式错误");
				return obj;
			}
		} else {
			start_time = sdf.format(new Date()) + "000000";
		}
		String end_time = param.get("end_time");
		if (StringUtils.hasText(end_time)) {
			end_time = parse(end_time, format);
			if (StringUtils.isEmpty(end_time)) {
				obj.put("result", 0);
				obj.put("error_msg", "结束时间格式错误");
				return obj;
			}
		} else {
			end_time = sdf.format(new Date()) + "235959";
		}

		SearchRequestBuilder responsebuilder = client.prepareSearch("*_loan_log");
		TermsBuilder aggregation = AggregationBuilders.terms("agg").field("opercmd_group");
		TermsBuilder dhb = AggregationBuilders.terms("bookName").field("Title_group").size(rows);
		TermsBuilder dhb2 = AggregationBuilders.terms("ISBN").field("ISBN_group").size(1);
		dhb.subAggregation(dhb2);
		aggregation.subAggregation(dhb);

		responsebuilder.addAggregation(aggregation);

		BoolQueryBuilder builder = QueryBuilders.boolQuery();// 条件组合
		if(StringUtils.hasText(library_idx)&&!"0".equals(library_idx)){// 条件1
			library_idx = selmasterLibsByIdx(library_idx);
			if (library_idx.indexOf(",") != -1) {
				BoolQueryBuilder builder_ = QueryBuilders.boolQuery();
				String[] idxs = library_idx.split("\\,");//有子馆的
				for (String idx : idxs) {
					builder_.should(QueryBuilders.matchPhraseQuery("library_idx_group", idx));
				}
				 builder.filter(builder_);
			} else {
				builder.filter(QueryBuilders.matchPhraseQuery("library_idx_group", library_idx));
			}
		}
		builder.filter(QueryBuilders.matchPhraseQuery("opercmd_group", "00010201"));// 条件2
		builder.mustNot(QueryBuilders.matchPhraseQuery("Title_group", "查询不到图书"));// 条件3
		builder.filter(QueryBuilders.matchPhraseQuery("operresult_group", operresult));// 条件4
		builder.filter(QueryBuilders.rangeQuery("opertime").format(format).from(start_time).to(end_time));// 条件5

		SearchResponse response = null;
		try{
			responsebuilder.setQuery(builder).setSearchType(SearchType.COUNT).addSort("opertime", SortOrder.ASC);
			response = responsebuilder.setExplain(true).execute().actionGet();
		}catch(Exception e){
			e.printStackTrace();
			obj.put("result", 0);
			obj.put("error_msg", "查询失败");

			return obj;
		}

		Terms agg = response.getAggregations().get("agg");
		int size = agg.getBuckets().size();
		if (size == 0) {
			//logger.warn("图书借阅排行没有数据");
			obj.put("result", 1);
			obj.put("msg", new JSONArray());

			return obj;
		}

		JSONArray msg = new JSONArray();
		for (Terms.Bucket bt : agg.getBuckets()) {
			Terms bookName = bt.getAggregations().get("bookName");
			for (Terms.Bucket b : bookName.getBuckets()) {
				Terms ISBN = b.getAggregations().get("ISBN");
				for (Terms.Bucket bb : ISBN.getBuckets()) {
					//System.out.println(b.getKey() + ";" + bb.getKey() + ";" + bb.getDocCount());
					JSONObject book = new JSONObject();
					book.put("book_name", b.getKey());
					book.put("isbn", bb.getKey());
					book.put("bnum", bb.getDocCount());
					msg.add(book);
				}
			}
		}
		obj.put("result", 1);
		obj.put("msg", msg);
		//System.out.println(msg.toString());
		return obj;
	}

	@Override
	public JSONObject borrowBackCountInfo(Map<String, String> param) {
		String library_idx = param.get("library_idx");
		String operresult = param.get("operresult") == null ? "0" : param.get("operresult");
		JSONObject obj = new JSONObject();
		String start_time = param.get("start_time");
		if (StringUtils.hasText(start_time)) {
			start_time = parse(start_time, format);
			if (StringUtils.isEmpty(start_time)) {
				obj.put("result", 0);
				obj.put("error_msg", "开始时间格式错误");
				return obj;
			}
		} else {
			start_time = sdf.format(new Date()) + "000000";
		}
		String end_time = param.get("end_time");
		if (StringUtils.hasText(end_time)) {
			end_time = parse(end_time, format);
			if (StringUtils.isEmpty(end_time)) {
				obj.put("result", 0);
				obj.put("error_msg", "结束时间格式错误");
				return obj;
			}
		} else {
			end_time = sdf.format(new Date()) + "235959";
		}

		SearchRequestBuilder responsebuilder = client.prepareSearch("*_loan_log").addAggregation(AggregationBuilders.terms("agg").field("opercmd_group"));
		BoolQueryBuilder builder = QueryBuilders.boolQuery();// 条件组合
		if(StringUtils.hasText(library_idx)&&!"0".equals(library_idx)){// 条件1
			library_idx = selmasterLibsByIdx(library_idx);
			if (library_idx.indexOf(",") != -1) {
				BoolQueryBuilder builder_ = QueryBuilders.boolQuery();
				String[] idxs = library_idx.split("\\,");//有子馆的
				for (String idx : idxs) {
					builder_.should(QueryBuilders.matchPhraseQuery("library_idx_group", idx));
				}
				builder.filter(builder_);
			} else {
				builder.filter(QueryBuilders.matchPhraseQuery("library_idx_group", library_idx));
			}
		}
		builder.should(QueryBuilders.matchPhraseQuery("opercmd_group", "00010201"));// 条件2
		builder.should(QueryBuilders.matchPhraseQuery("opercmd_group", "00010202"));// 条件3
		builder.should(QueryBuilders.matchPhraseQuery("opercmd_group", "00010203"));// 条件3
		builder.filter(QueryBuilders.matchPhraseQuery("operresult_group", operresult));// 条件4
		builder.filter(QueryBuilders.rangeQuery("opertime").format(format).from(start_time).to(end_time));// 条件5

		SearchResponse response = null;
		try{
			responsebuilder.setQuery(builder).setSearchType(SearchType.COUNT).addSort("opertime", SortOrder.ASC);
			response = responsebuilder.setExplain(true).execute().actionGet();
		}catch(Exception e){
			e.printStackTrace();
			obj.put("result", 0);
			obj.put("error_msg", "查询失败");

			return obj;
		}

		Terms agg = response.getAggregations().get("agg");
		int size = agg.getBuckets().size();
		if (size == 0) {
			//logger.warn("借还统计没有数据");
			obj.put("result", 1);
			obj.put("borrow_count", 0);
			obj.put("back_count", 0);
			obj.put("renew_count", 0);
			return obj;
		}
		for (Terms.Bucket bt : agg.getBuckets()) {
			String key = bt.getKeyAsString();
			long count = bt.getDocCount();
			// System.out.println(key + ";" + count);
			if ("00010201".equals(key)) {
				obj.put("borrow_count", count);
			} else if ("00010202".equals(key)) {
				obj.put("back_count", count);
			} else if ("00010203".equals(key)) {
				obj.put("renew_count", count);
			}
		}
		obj.put("result", 1);
		return obj;
	}
	
	@Override
	public JSONObject countLoanForBar(Map<String, String> param) {
		String library_idx = param.get("library_idx");
		String operresult = param.get("operresult") == null ? "0" : param.get("operresult");
		JSONObject obj = new JSONObject();
		String start_time = param.get("start_time");
		if (StringUtils.hasText(start_time)) {
			start_time = parse(start_time, format);
			if (StringUtils.isEmpty(start_time)) {
				obj.put("result", 0);
				obj.put("error_msg", "开始时间格式错误");
				return obj;
			}
		} else {
			start_time = sdf.format(new Date()) + "000000";
		}
		String end_time = param.get("end_time");
		if (StringUtils.hasText(end_time)) {
			end_time = parse(end_time, format);
			if (StringUtils.isEmpty(end_time)) {
				obj.put("result", 0);
				obj.put("error_msg", "结束时间格式错误");
				return obj;
			}
		} else {
			end_time = sdf.format(new Date()) + "235959";
		}

		SearchRequestBuilder responsebuilder = client.prepareSearch("*_loan_log");
		TermsBuilder aggregation = AggregationBuilders.terms("agg").field("opercmd_group")/*.size(10)*/;// opercmd_group、operresult_group
		
		DateHistogramBuilder dhb = AggregationBuilders.dateHistogram("time").field("opertime").interval(DateHistogramInterval.HOUR).format("HH'点'");
		dhb.subAggregation(aggregation);
		responsebuilder.addAggregation(dhb);
		
		BoolQueryBuilder builder = QueryBuilders.boolQuery();// 条件组合
		if(StringUtils.hasText(library_idx)&&!"0".equals(library_idx)){// 条件1
			library_idx = selmasterLibsByIdx(library_idx);
			if (library_idx.indexOf(",") != -1) {
				BoolQueryBuilder builder_ = QueryBuilders.boolQuery();
				String[] idxs = library_idx.split("\\,");//有子馆的
				for (String idx : idxs) {
					builder_.should(QueryBuilders.matchPhraseQuery("library_idx_group", idx));
				}
				builder.filter(builder_);
			} else {
				builder.filter(QueryBuilders.matchPhraseQuery("library_idx_group", library_idx));
			}
		}
		builder.should(QueryBuilders.matchPhraseQuery("opercmd_group", "00010201"));// 条件2
		builder.should(QueryBuilders.matchPhraseQuery("opercmd_group", "00010202"));// 条件3
		builder.should(QueryBuilders.matchPhraseQuery("opercmd_group", "00010203"));// 条件3
		builder.filter(QueryBuilders.matchPhraseQuery("operresult_group", operresult));// 条件4
		builder.filter(QueryBuilders.rangeQuery("opertime").format(format).from(start_time).to(end_time));// 条件5

		SearchResponse response = null;
		try{
			responsebuilder.setQuery(builder).setSearchType(SearchType.COUNT).addSort("opertime", SortOrder.ASC);
			response = responsebuilder.setExplain(true).execute().actionGet();
		}catch(Exception e){
			e.printStackTrace();
			obj.put("result", 0);
			obj.put("error_msg", "查询失败");

			return obj;
		}

		Histogram times = response.getAggregations().get("time");
		int size = times.getBuckets().size();
		if (size == 0) {
			//logger.warn("借还统计没有数据");
			obj.put("result", 1);
			obj.put("borrow_count", 0);
			obj.put("back_count", 0);
			obj.put("renew_count", 0);
			return obj;
		}
		String[] date = new String[size];
		long[] loan_total = new long[size];
		long[] return_total = new long[size];
		long[] renew_total = new long[size];
		int i = 0;
		for (Histogram.Bucket entry : times.getBuckets()) {
			String key = entry.getKeyAsString();
			date[i] = key;
			Terms agg = entry.getAggregations().get("agg");
			for (Terms.Bucket b : agg.getBuckets()) {
				String cmd = b.getKeyAsString();
				long count = b.getDocCount();
				//System.out.println(key + "  " + b.getKeyAsString() + "  " + count);
				if ("00010201".equals(cmd)) {// 借书
					loan_total[i] = count;
				}
				if ("00010202".equals(cmd)) {// 还书
					return_total[i] = count;
				}
				if ("00010203".equals(cmd)) {// 续借
					renew_total[i] = count;
				}
			}
			i++;
		}
		obj.put("date", date);
		obj.put("borrow_count", loan_total);
		obj.put("back_count", return_total);
		obj.put("renew_count", renew_total);
		obj.put("result", 1);
		return obj;
	}
	

	@Override
	public JSONObject cameCountInfo(Map<String, String> param) {
		String library_idx = param.get("library_idx");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		JSONObject obj = new JSONObject();
		int flag = 0;
		String start_time = param.get("start_time");
		if (StringUtils.hasText(start_time)) {
			start_time = parse(start_time, "yyyy-MM-dd HH:mm:ss");
			if (StringUtils.isEmpty(start_time)) {
				obj.put("result", 0);
				obj.put("error_msg", "开始时间格式错误");
				return obj;
			}
		} else {
			start_time = sdf.format(new Date()) + " 00:00:00";
			flag++;
		}
		String end_time = param.get("end_time");
		if (StringUtils.hasText(end_time)) {
			end_time = parse(end_time, "yyyy-MM-dd HH:mm:ss");
			if (StringUtils.isEmpty(end_time)) {
				obj.put("result", 0);
				obj.put("error_msg", "结束时间格式错误");
				return obj;
			}
		} else {
			end_time = sdf.format(new Date()) + " 23:59:59";
			flag++;
		}

		JSONObject req = new JSONObject();
		//library_idx = selmasterLibsByIdx(library_idx);
		req.put("containSubLib", "Y");//是否查询子馆
		req.put("library_idx", library_idx);
		req.put("startTime", start_time);
		req.put("endTime", end_time);

		ResultEntity result = mainPageService.countVisitorLog(req.toString());
		if (result.getState()) {
			String str = String.valueOf(result.getResult());
			if (JSONUtils.mayBeJSON(str)) {
				JSONObject json = JSONObject.fromObject(str);
				JSONArray array = json.optJSONArray("in_total");
				if (null == array) {
					obj.put("result", 0);
					obj.put("error_msg", "查询失败");
					return obj;
				}
				long inTotal = 0;
				long here = 0;
				for (int i = 0; i < array.size(); i++) {
					inTotal += array.optLong(i);
				}

				if (flag >= 2) {// 没有传入时间，默认查当天数据时执行
					long outTotal = 0;
					JSONArray array2 = json.optJSONArray("out_total");
					if (array2 != null) {
						for (int i = 0; i < array2.size(); i++) {
							outTotal += array2.optLong(i);
						}
						here = inTotal - outTotal;
						if (here <= 0) {
							here = 0;
						}
					}
				} else {// 传入的时间不是今天
					start_time = sdf.format(new Date()) + " 00:00:00";
					end_time = sdf.format(new Date()) + " 23:59:59";
					req.put("startTime", start_time);
					req.put("endTime", end_time);
					result = mainPageService.countVisitorLog(req.toString());
					if (result.getState()) {
						str = String.valueOf(result.getResult());
						if (JSONUtils.mayBeJSON(str)) {
							json = JSONObject.fromObject(str);
							JSONArray array1 = json.optJSONArray("in_total");
							JSONArray array2 = json.optJSONArray("out_total");
							if (null != array1 && null != array2) {
								long t1 = 0, t2 = 0;
								for (int i = 0; i < array1.size(); i++) {
									t1 += array1.optLong(i);
								}
								for (int i = 0; i < array2.size(); i++) {
									t2 += array2.optLong(i);
								}
								here = t1 - t2;
								if (here <= 0) {
									here = 0;
								}
							}
						}
					}
				}
				obj.put("result", 1);
				obj.put("count_by_here", here);
				obj.put("count_by_all", inTotal);
				return obj;
			}
		}
		//logger.warn("人流量统计没有数据");
		obj.put("result", 0);
		obj.put("error_msg", "没有数据");
		return obj;
	}
	
	@Override
	public JSONObject countRealTimeVisitor(Map<String, String> param) {
		String library_idx = param.get("library_idx");
		String type = param.get("type") == null ? "realtime" : param.get("type");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		JSONObject obj = new JSONObject();
		String start_time = param.get("start_time");
		if (StringUtils.hasText(start_time)) {
			start_time = parse(start_time, "yyyy-MM-dd HH:mm:ss");
			if (StringUtils.isEmpty(start_time)) {
				obj.put("result", 0);
				obj.put("error_msg", "开始时间格式错误");
				return obj;
			}
		} else {
			start_time = sdf.format(new Date()) + " 00:00:00";
		}
		String end_time = param.get("end_time");
		if (StringUtils.hasText(end_time)) {
			end_time = parse(end_time, "yyyy-MM-dd HH:mm:ss");
			if (StringUtils.isEmpty(end_time)) {
				obj.put("result", 0);
				obj.put("error_msg", "结束时间格式错误");
				return obj;
			}
		} else {
			end_time = sdf.format(new Date()) + " 23:59:59";
		}
		
		JSONObject req = new JSONObject();
		//library_idx = selmasterLibsByIdx(library_idx);
		req.put("containSubLib", "Y");//是否查询子馆,Y-查，N-不查
		req.put("library_idx", library_idx);
		req.put("type", type);
		req.put("startTime", start_time);
		req.put("endTime", end_time);
		
		ResultEntity result = mainPageService.countVisitorLog(req.toString());
		if (result.getState()) {
			obj = JSONObject.fromObject(result.getResult());
			obj.put("result", 1);
			return obj;
		}
		//logger.warn("人流量统计没有数据");
		obj.put("result", 0);
		obj.put("error_msg", "没有数据");
		return obj;
	}

	@Override
	public JSONObject readerRank(Map<String, String> param) {
		String library_idx = param.get("library_idx");
		String operresult = param.get("operresult") == null ? "0" : param.get("operresult");
		int rows = param.get("rows") == null ? 10 : Integer.parseInt(param.get("rows"));

		JSONObject obj = new JSONObject();
		String start_time = param.get("start_time");
		if (StringUtils.hasText(start_time)) {
			start_time = parse(start_time, format);
			if (StringUtils.isEmpty(start_time)) {
				obj.put("result", 0);
				obj.put("error_msg", "开始时间格式错误");
				return obj;
			}
		} else {
			start_time = sdf.format(new Date()) + "000000";
		}
		//logger.info("start_time-->"+start_time);
		String end_time = param.get("end_time");
		if (StringUtils.hasText(end_time)) {
			end_time = parse(end_time, format);
			if (StringUtils.isEmpty(end_time)) {
				obj.put("result", 0);
				obj.put("error_msg", "结束时间格式错误");
				return obj;
			}
		} else {
			end_time = sdf.format(new Date()) + "235959";
		}
		SearchRequestBuilder responsebuilder = client.prepareSearch("*_loan_log");
		TermsBuilder aggregation = AggregationBuilders.terms("agg").field("opercmd_group");
		TermsBuilder dhb = AggregationBuilders.terms("readerName").field("Name_group").size(rows);
		TermsBuilder dhb2 = AggregationBuilders.terms("operator").field("operator_group").size(1);
		dhb.subAggregation(dhb2);
		aggregation.subAggregation(dhb);

		responsebuilder.addAggregation(aggregation);
		
		BoolQueryBuilder builder = QueryBuilders.boolQuery();// 条件组合
		if(StringUtils.hasText(library_idx)&&!"0".equals(library_idx)){// 条件1
			library_idx = selmasterLibsByIdx(library_idx);
			library_idx = selmasterLibsByIdx(library_idx);
			if (library_idx.indexOf(",") != -1) {
				BoolQueryBuilder builder_ = QueryBuilders.boolQuery();
				String[] idxs = library_idx.split("\\,");//有子馆的
				for (String idx : idxs) {
					 builder_.should(QueryBuilders.matchPhraseQuery("library_idx_group", idx));
				}
				builder.filter(builder_);
			} else {
				builder.filter(QueryBuilders.matchPhraseQuery("library_idx_group", library_idx));
			}
		}
		builder.filter(QueryBuilders.matchPhraseQuery("opercmd_group", "00010201"));// 条件2
		builder.mustNot(QueryBuilders.matchPhraseQuery("Name_group", ""));// 条件3
		builder.filter(QueryBuilders.matchPhraseQuery("operresult_group", operresult));// 条件4
		builder.filter(QueryBuilders.rangeQuery("opertime").format(format).from(start_time).to(end_time));// 条件5
		
		SearchResponse response =null;
		try{
			responsebuilder.setQuery(builder).setSearchType(SearchType.COUNT).addSort("opertime", SortOrder.ASC);
			response = responsebuilder.setExplain(true).execute().actionGet();
		}catch(Exception e){
			e.printStackTrace();
			obj.put("result", 0);
			obj.put("error_msg", "查询失败");

			return obj;
		}
		
		Terms agg = response.getAggregations().get("agg");
		int size = agg.getBuckets().size();
		//logger.info("读者借阅排行查询条数-->"+size);
		if (size == 0) {
			//logger.warn("读者借阅排行没有数据");
			obj.put("result", 1);
			obj.put("msg", new JSONArray());

			return obj;
		}

		JSONArray msg = new JSONArray();
		for (Terms.Bucket bt : agg.getBuckets()) {
			Terms bookName = bt.getAggregations().get("readerName");
			for (Terms.Bucket b : bookName.getBuckets()) {
				Terms operator = b.getAggregations().get("operator");
				for (Terms.Bucket bb : operator.getBuckets()) {
					//System.out.println(b.getKey() + ";" + bb.getKey() + ";" + bb.getDocCount());
					if("".equals(b.getKey()))continue;
					JSONObject book = new JSONObject();
					book.put("name", b.getKey());
					book.put("cardno", bb.getKey());
					book.put("bnum", bb.getDocCount());
					msg.add(book);
				}
			}
		}
		//logger.info("读者借阅排行结果-->"+msg);
		obj.put("result", 1);
		obj.put("msg", msg);
		//System.out.println(msg.toString());
		return obj;
	}

	@Override
	public JSONObject countCertificate(Map<String, String> param) {
		String library_idx = param.get("library_idx");
		String operresult = param.get("operresult") == null ? "0" : param.get("operresult");
		JSONObject obj = new JSONObject();
		String start_time = param.get("start_time");
		if (StringUtils.hasText(start_time)) {
			start_time = parse(start_time, format);
			if (StringUtils.isEmpty(start_time)) {
				obj.put("result", 0);
				obj.put("error_msg", "开始时间格式错误");
				return obj;
			}
		} else {
			start_time = sdf.format(new Date()) + "000000";
		}
		String end_time = param.get("end_time");
		if (StringUtils.hasText(end_time)) {
			end_time = parse(end_time, format);
			if (StringUtils.isEmpty(end_time)) {
				obj.put("result", 0);
				obj.put("error_msg", "结束时间格式错误");
				return obj;
			}
		} else {
			end_time = sdf.format(new Date()) + "235959";
		}

		SearchRequestBuilder responsebuilder = client.prepareSearch("*_cardissue_log").addAggregation(AggregationBuilders.terms("agg").field("opercmd_group"));
		BoolQueryBuilder builder = QueryBuilders.boolQuery();// 条件组合
		if(StringUtils.hasText(library_idx)&&!"0".equals(library_idx)){// 条件1
			library_idx = selmasterLibsByIdx(library_idx);
			if (library_idx.indexOf(",") != -1) {
				BoolQueryBuilder builder_ = QueryBuilders.boolQuery();
				String[] idxs = library_idx.split("\\,");//有子馆的
				for (String idx : idxs) {
					builder_.should(QueryBuilders.matchPhraseQuery("library_idx_group", idx));
				}
				builder.filter(builder_);
			} else {
				builder.filter(QueryBuilders.matchPhraseQuery("library_idx_group", library_idx));
			}
		}
		
		builder.filter(QueryBuilders.matchPhraseQuery("operresult_group", operresult));// 条件4
		builder.filter(QueryBuilders.rangeQuery("opertime").format(format).from(start_time).to(end_time));// 条件5

		SearchResponse response = null;
		try{
			responsebuilder.setQuery(builder).setSearchType(SearchType.COUNT).addSort("opertime", SortOrder.ASC);
			response = responsebuilder.setExplain(true).execute().actionGet();
		}catch(Exception e){
			e.printStackTrace();
			obj.put("result", 0);
			obj.put("error_msg", "查询失败");

			return obj;
		}

		Terms agg = response.getAggregations().get("agg");
		int size = agg.getBuckets().size();
		if (size == 0) {
			//logger.warn("办证统计没有数据");
			obj.put("result", 1);
			obj.put("total", 0);
			return obj;
		}
		long total=0;
		for (Terms.Bucket bt : agg.getBuckets()) {
			total += bt.getDocCount();
		}
		obj.put("result", 1);
		obj.put("total", total);
		return obj;
	}
	
	@Override
	public JSONObject loadLibInfo(Map<String, String> param) {
		String library_idx = param.get("library_idx");
		String excludedLibrary = param.get("excludedLibrary");
		if (null == excludedLibrary || "".equals(excludedLibrary.trim())) {
			excludedLibrary = "HHTEST";
		}
		JSONObject obj = new JSONObject();

		SearchRequestBuilder responsebuilder = client.prepareSearch();
		TermsBuilder aggregation = AggregationBuilders.terms("libInfo").field("lib_id_group").size(0);
		TermsBuilder dhb = AggregationBuilders.terms("deviceInfo").field("device_id_group").size(0);
		aggregation.subAggregation(dhb);
		
		responsebuilder.addAggregation(aggregation);
		
		BoolQueryBuilder builder = QueryBuilders.boolQuery();// 条件组合
		if(StringUtils.hasText(library_idx)&&!"0".equals(library_idx)){// 条件1
			library_idx = selmasterLibsByIdx(library_idx);
			if (library_idx.indexOf(",") != -1) {
				String[] idxs = library_idx.split("\\,");//有子馆的
				builder.must(QueryBuilders.termsQuery("library_idx_group", idxs));
			} else {
				builder.must(QueryBuilders.matchQuery("library_idx_group", library_idx));
			}
		}
		builder.mustNot(QueryBuilders.matchQuery("lib_id_group", excludedLibrary));//排除不统计的馆，默认是HHTEST

		SearchResponse response = null;
		try{
			responsebuilder.setQuery(builder).setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
			response = responsebuilder.setExplain(true).setSize(0).execute().actionGet();
		}catch(Exception e){
			e.printStackTrace();
			obj.put("result", 0);
			obj.put("error_msg", "查询失败");

			return obj;
		}
		
		Terms agg = response.getAggregations().get("libInfo");
		int size = agg.getBuckets().size();
		if (size == 0) {
			//logger.warn("图书馆统计没有数据");
			obj.put("result", 1);
			obj.put("libs", 0);
			obj.put("devices", 0);
			return obj;
		}
		long libs=0,devices=0;
		for (Terms.Bucket bt : agg.getBuckets()) {
			//System.out.println(bt.getKeyAsString());
			libs++;
			Terms deviceInfo = bt.getAggregations().get("deviceInfo");
			for (Terms.Bucket di : deviceInfo.getBuckets()) {
				//System.out.println("++++++ "+di.getKeyAsString());
				devices++;
			}
		}
		obj.put("result", 1);
		obj.put("libs", libs);
		obj.put("devices", devices);
		return obj;
	}

	private String parse(String time, String dFormat) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			Date d = df.parse(time);
			gc.setTime(d);
			df = new SimpleDateFormat(dFormat);
			return df.format(gc.getTime());
		} catch (ParseException e) {
			System.err.println("传入的时间格式错误");
		}
		return null;
	}
	
	/**
	 * 查询主馆及子馆
	 */
	private String selmasterLibsByIdx(String library_idx) {
		if(!StringUtils.hasText(library_idx))return library_idx;
		List<RelLibsEntity> list = new ArrayList<RelLibsEntity>();
		List<RelLibsEntity> result = getRelLibs(list,library_idx);
		for(RelLibsEntity entity:result){
			library_idx += "," + entity.getSlave_lib_id();
		}
		return library_idx;
	}
	
	private List<RelLibsEntity> getRelLibs(List<RelLibsEntity> list, String library_idx) {
		String json = "{\"library_idx\":\"" + library_idx + "\"}";
		ResultEntity devResult = postURL("selmasterLibsByIdx", json);
		if (devResult.getState()) {
			List<RelLibsEntity> result = JsonUtils.fromJson(JsonUtils.toJson(devResult.getResult()), new TypeReference<List<RelLibsEntity>>() {});
			list.addAll(result);
			for(RelLibsEntity entity:result){
				library_idx = entity.getSlave_lib_id() + "";
				getRelLibs(list, library_idx);
			}
		}
		return list;
	}
	

	public static void main(String[] args) {
		try {
			TransportClient t = TransportClient.builder().build();
			String[] esIPArr = { "172.16.0.115" };
			TransportAddress[] ts = new TransportAddress[esIPArr.length];
			for (int i = 0; i < esIPArr.length; i++) {
				ts[i] = new InetSocketTransportAddress(InetAddress.getByName(esIPArr[i]), 9300);
			}
			for (TransportAddress t1 : ts) {
				t.addTransportAddress(t1);
			}
			client = t;
			
					
			Map<String, String> param = new HashMap<String, String>();
			//param.put("indexTab", "*_loan_log");
			//param.put("library_idx", "189");
			//param.put("agg_type", "opercmd");
			//param.put("agg_value", "00010201");
			//param.put("start_time", "20171214000000");
			//param.put("end_time", "20171214235959");
			//param.put("operresult", "1");
			//param.put("rows", "10");

			//JSONObject obj1 = new ThirdPartyAPIServiceImpl().readerRank(param);
			//System.out.println(obj1.toString());
			//JSONObject obj2 = new ThirdPartyAPIServiceImpl().countCertificate(param);
			//System.out.println(obj2.toString());
			//JSONObject obj3 = new ThirdPartyAPIServiceImpl().countLoanForBar(param);
			//System.out.println(obj3.toString());
			
			JSONObject obj4 = new ThirdPartyAPIServiceImpl().loadLibInfo(param);
			System.out.println(obj4.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
