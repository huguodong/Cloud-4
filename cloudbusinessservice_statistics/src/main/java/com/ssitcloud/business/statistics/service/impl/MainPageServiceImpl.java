package com.ssitcloud.business.statistics.service.impl;

import java.math.BigDecimal;
import java.net.InetAddress;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.Consts;
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
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssitcloud.authentication.entity.RelLibsEntity;
import com.ssitcloud.business.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.statistics.common.utils.HttpClientUtil;
import com.ssitcloud.business.statistics.common.utils.JsonUtils;
import com.ssitcloud.business.statistics.common.utils.ResourcesUtil;
import com.ssitcloud.business.statistics.common.utils.XMLUtils;
import com.ssitcloud.business.statistics.entity.CardissueTempEntity;
import com.ssitcloud.business.statistics.entity.CirculateTempEntity;
import com.ssitcloud.business.statistics.entity.FinanceTempEntity;
import com.ssitcloud.business.statistics.entity.StatisticsTempEntity;
import com.ssitcloud.business.statistics.service.ElasticsearchStatisticsService;
import com.ssitcloud.business.statistics.service.MainPageService;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class MainPageServiceImpl extends BasicServiceImpl implements MainPageService {
	@Resource
	ElasticsearchStatisticsService elasticsearchStatisticsService;

	private static Client client = null;

	private static final String T_ALL = "all";
	private static final String T_TODAY = "today";
	private static final String T_WEEK = "week";
	private static final String T_MONTH = "month";
	private static final String T_YEAR = "year";
	private static final String T_YESTERDAY = "yesterday";
	private static final String T_LASTMONTH = "lastMonth";
	private static final String T_LASTYEAR = "lastYear";

	// 查询子馆
	private static final String SEL_MASTERLIBS_BY_IDX = "selmasterLibsByIdx";
		
	@Override
	public ResultEntity countCardissueLog(String req) {
		String type = "today";
		JSONObject json = new JSONObject();
		if (StringUtils.hasText(req)) {
			json = JSONObject.fromObject(req);
			if (json.containsKey("type")) {
				type = json.optString("type", T_TODAY);
				// System.out.println("countCardissueLog type-->" + type);
			}
		}
		ResultEntity result = null;
		//modify by xiebf @20171215  之前部分查mysql的改成全部查es
		//if (T_YESTERDAY.equals(type) || T_LASTYEAR.equals(type) || T_LASTMONTH.equals(type)) {// 去mysql查询历史数据
			//result = postURL("countCardissueLog", req);
		//} else if (T_ALL.equals(type) || T_YEAR.equals(type) || T_MONTH.equals(type) || T_WEEK.equals(type) || T_TODAY.equals(type)) {// 去es查询实时数据
			result = new ResultEntity();
			String indexTab = json.optString("indexTab","*_cardissue_log");
			String library_idx = json.optString("library_idx");
			String device_id = json.optString("device_id");
			String device_idx = json.optString("device_idx");
			String chart_type = json.optString("chart_type","bar");
			String agg_type = json.optString("agg_type","opercmd");
			String operresult = json.optString("operresult");
			boolean forTotal = json.optBoolean("forTotal", false);
			String containSubLib = json.optString("containSubLib", "N");

			Map<String, String> param = new HashMap<String, String>();
			param.put("indexTab", indexTab);
			param.put("type", type);
			param.put("library_idx", library_idx);
			param.put("device_idx", device_idx);
			param.put("device_id", device_id);
			param.put("chart_type", chart_type);
			param.put("agg_type", agg_type);
			param.put("operresult", operresult);
			param.put("containSubLib", containSubLib);

			if (forTotal) {
				result = statisticsForTotal(param);
			} else {
				result = statistics(param);
			}
		//}
		return result;
	}

	@Override
	public ResultEntity countLoanLog(String req) {
		String type = "today";
		JSONObject json = new JSONObject();
		if (StringUtils.hasText(req)) {
			json = JSONObject.fromObject(req);
			if (json.containsKey("type")) {
				type = json.optString("type", T_TODAY);
				// System.out.println("countLoanLog type-->" + type);
			}
		}
		ResultEntity result = null;
		//modify by xiebf @20171215  之前部分查mysql的改成全部查es
		//if (T_YESTERDAY.equals(type) || T_LASTYEAR.equals(type) || T_LASTMONTH.equals(type)) {// 去mysql查询历史数据
			//result = postURL("countLoanLog", req);
		//} else if (T_ALL.equals(type) || T_YEAR.equals(type) || T_MONTH.equals(type) || T_WEEK.equals(type) || T_TODAY.equals(type)) {// 去es查询实时数据
			result = new ResultEntity();
			String indexTab = json.optString("indexTab","*_loan_log");
			String library_idx = json.optString("library_idx");
			String device_idx = json.optString("device_idx");
			String device_id = json.optString("device_id");
			String chart_type = json.optString("chart_type","bar");
			String agg_type = json.optString("agg_type","opercmd");
			String operresult = json.optString("operresult");
			boolean forTotal = json.optBoolean("forTotal", false);
			String containSubLib = json.optString("containSubLib", "N");

			Map<String, String> param = new HashMap<String, String>();
			param.put("indexTab", indexTab);
			param.put("type", type);
			param.put("library_idx", library_idx);
			param.put("device_idx", device_idx);
			param.put("device_id", device_id);
			param.put("chart_type", chart_type);
			param.put("agg_type", agg_type);
			param.put("operresult", operresult);
			param.put("containSubLib", containSubLib);

			if (forTotal) {
				result = statisticsForTotal(param);
			} else {
				result = statistics(param);
			}
		//}
		return result;
	}

	@Override
	public ResultEntity countFinanceLog(String req) {
		String type = "today";
		JSONObject json = new JSONObject();
		if (StringUtils.hasText(req)) {
			json = JSONObject.fromObject(req);
			if (json.containsKey("type")) {
				type = json.optString("type", T_TODAY);
				// System.out.println("countFinanceLog type-->" + type);
			}
		}
		ResultEntity result = null;
		//modify by xiebf @20171215  之前部分查mysql的改成全部查es
		//if (T_YESTERDAY.equals(type) || T_LASTYEAR.equals(type) || T_LASTMONTH.equals(type)) {// 去mysql查询历史数据
		//	result = postURL("countFinanceLog", req);
		//} else if (T_ALL.equals(type) || T_YEAR.equals(type) || T_MONTH.equals(type) || T_WEEK.equals(type) || T_TODAY.equals(type)) {// 去es查询实时数据
			result = new ResultEntity();
			String indexTab = json.optString("indexTab","*_finance_log");
			String library_idx = json.optString("library_idx");
			String device_idx = json.optString("device_idx");
			String device_id = json.optString("device_id");
			String chart_type = json.optString("chart_type","bar");
			String agg_type = json.optString("agg_type","opercmd");
			String operresult = json.optString("operresult");
			boolean forTotal = json.optBoolean("forTotal", false);
			String containSubLib = json.optString("containSubLib", "N");

			Map<String, String> param = new HashMap<String, String>();
			param.put("indexTab", indexTab);
			param.put("type", type);
			param.put("library_idx", library_idx);
			param.put("device_idx", device_idx);
			param.put("device_id", device_id);
			param.put("chart_type", chart_type);
			param.put("agg_type", agg_type);
			param.put("operresult", operresult);
			param.put("containSubLib", containSubLib);

			if (forTotal) {
				result = statisticsForTotal(param);
			} else {
				result = statistics(param);
			}
		//}
		return result;
	}

	/**
	 * 人流量统计 没有走elasticsearch，直接从mysql查询
	 */
	//{"type":type,"chart_type":"bar"}:{"type":type,"library_idx":lib_idx,"chart_type":"bar"};
	@Override
	public ResultEntity countVisitorLog(String req) {
		if (StringUtils.hasText(req)) {
			Map<String, String> param = JsonUtils.fromJson(req, Map.class);
			String library_idx = param.get("library_idx");
			String containSubLib = param.get("containSubLib");
			List<Integer> libIdx = new ArrayList<>();
			if (library_idx != null && !"".equals(library_idx.trim())) {
				libIdx.add(Integer.parseInt(library_idx));
				if(containSubLib != null && "Y".equals(containSubLib)){
					//查询获取当前图书馆及子馆数据
					List<RelLibsEntity> list = takeRelLibs(Integer.parseInt(library_idx));
					for(RelLibsEntity rel : list){
						libIdx.add(rel.getSlave_lib_id());
					}
				}
			}
			param.put("library_idx", StringUtils.collectionToCommaDelimitedString(libIdx));
			req = JsonUtils.toJson(param);
		}
		return postURL("countVisitorLog", req);
	}

	/**
	 * 查询ElasticSearch的数据
	 * 
	 * @param param
	 *            如：{indexTab=*_loan_log/*_finance_log/*_cardissue_log,*library_idx=168,*device_id=SCH_001,*type=all/year/month/week/today,*agg_type=opercmd/operresult,*chart_type=bar/pie,*operresult=
	 *            0/1} 带*参数表示可选
	 * @return
	 */
	private ResultEntity statistics(Map<String, String> param) {
		if (client == null)
			client = ElasticsearchStatisticsServiceImpl.getClient();

		String indexTab = param.get("indexTab");// 索引名（表名）
		String library_idx = param.get("library_idx");
		String device_id = param.get("device_id");
		String device_idx = param.get("device_idx");
		String type = param.get("type");
		String chart_type = param.get("chart_type");
		String agg_type = param.get("agg_type");
		String operresult = param.get("operresult");
		String containSubLib = param.get("containSubLib");

		SearchRequestBuilder responsebuilder = client.prepareSearch(indexTab)/* .setTypes("LOAN_LOG").setFrom(0).setSize(250) */;
		TermsBuilder aggregation = AggregationBuilders.terms("agg").field(agg_type + "_group")/*.size(10)*/;// opercmd_group、operresult_group

		if ("bar".equals(chart_type)) {
			DateHistogramBuilder dhb = AggregationBuilders.dateHistogram("time").field("opertime");
			if (T_ALL.equals(type)) {
				dhb.interval(DateHistogramInterval.YEAR).format("yyyy'年'");
			} else if (T_YEAR.equals(type)||T_LASTYEAR.equals(type)) {
				dhb.interval(DateHistogramInterval.MONTH).format("yyyyMM");
			} else if (T_MONTH.equals(type)||T_LASTMONTH.equals(type)) {
				dhb.interval(DateHistogramInterval.DAY).format("yyyyMMdd");
			} else if (T_WEEK.equals(type)) {
				dhb.interval(DateHistogramInterval.WEEK).format("yyyyMMdd").timeZone("Asia/Shanghai");
			} else if (T_TODAY.equals(type)||T_YESTERDAY.equals(type)) {
				dhb.interval(DateHistogramInterval.HOUR).format("HH'点'");
			}
			if (indexTab.indexOf("finance_log") != -1) {// 财经表单独处理
				dhb.subAggregation(AggregationBuilders.sum("sum_money").field("Money_group"));
			}else if(indexTab.indexOf("cardissue_log") != -1){// 办卡
				dhb.subAggregation(AggregationBuilders.terms("operresult_agg").field("operresult_group"));
			}
			dhb.subAggregation(aggregation);
			responsebuilder.addAggregation(dhb);
		} else if ("pie".equals(chart_type)) {
			if (indexTab.indexOf("finance_log") != -1) {// 财经表单独处理
				aggregation.subAggregation(AggregationBuilders.sum("sum_money").field("Money_group"));
			}
			if (indexTab.indexOf("cardissue_log") != -1) {
				if ("opercmd".equals(agg_type)) {
					agg_type = "Gender_group";
				}
				TermsBuilder gender = AggregationBuilders.terms("agg").field(agg_type);
				responsebuilder.addAggregation(gender);
			} else {
				responsebuilder.addAggregation(aggregation);
			}
		}

		BoolQueryBuilder builder = QueryBuilders.boolQuery();// 条件组合
		if (library_idx != null && !"".equals(library_idx.trim()) && !"0".equals(library_idx.trim())) {
			if ("Y".equals(containSubLib)) {// 统计子馆
				// 查询获取当前图书馆及子馆数据
				List<RelLibsEntity> list = takeRelLibs(Integer.parseInt(library_idx));
				if (list != null && !CollectionUtils.isEmpty(list)) {
					BoolQueryBuilder builder_ = QueryBuilders.boolQuery();// 条件组合
					builder_.should(QueryBuilders.matchPhraseQuery("library_idx_group", library_idx));
					for (RelLibsEntity rel : list) {
						builder_.should(QueryBuilders.matchPhraseQuery("library_idx_group", rel.getSlave_lib_id()));
					}
					builder.filter(builder_);
				} else {
					builder.filter(QueryBuilders.matchPhraseQuery("library_idx_group", library_idx));
				}
			} else {
				builder.filter(QueryBuilders.matchPhraseQuery("library_idx_group", library_idx));
			}
		}
		if (device_id != null && !"".equals(device_id.trim())) {
			builder.filter(QueryBuilders.matchPhraseQuery("device_id_group", device_id));// 条件2
		}
		if (device_idx != null && !"".equals(device_idx.trim())) {
			builder.filter(QueryBuilders.matchPhraseQuery("device_idx_group", device_idx));// 条件3
		}
		if (operresult != null && !"".equals(operresult.trim())) {
			builder.filter(QueryBuilders.matchPhraseQuery("operresult_group", operresult));// 条件4
		}

		if (!T_ALL.equals(type)) {// 查全部数据，则没有时间范围
			String[] times = getTime(type);
			// System.out.println("from:[" + startTime + "],to[" + endTime + "]");
			builder.filter(QueryBuilders.rangeQuery("opertime").format("yyyyMMdd").from(times[0]).to(times[1]));
		}

		responsebuilder.setQuery(builder).setSearchType(SearchType.COUNT).addSort("opertime", SortOrder.ASC);
		SearchResponse response = responsebuilder.setExplain(true).execute().actionGet();

//		 SearchHits hits = response.getHits();
//		 System.out.println("total=" + hits.getTotalHits());
//		 SearchHit[] searchHit = hits.getHits();
//		 for (int i = 0; i < searchHit.length; i++) {
//			 System.out.print(searchHit[i].getSource().get("library_idx"));
//			 System.out.print("\t" + searchHit[i].getSource().get("device_id"));
//			 System.out.print("\t" + searchHit[i].getSource().get("Money_group"));
//			 System.out.println("\t" + searchHit[i].getSource().get("opertime"));
//		 }

		if ("bar".equals(chart_type) && "opercmd".equals(agg_type)) {
			return createBarResult(indexTab, response);
		} else if ("pie".equals(chart_type)) {
			if ("operresult".equals(agg_type)) {// 按操作结果统计
				return createPieResultForResult(indexTab, response);
			} else {
				return createPieResult(indexTab, response);
			}
		}

		ResultEntity result = new ResultEntity();
		result.setValue(false, "统计出错");
		return result;
	}

	/**
	 * 统计操作类型生成饼图
	 * 
	 * @param indexTab
	 * @param response
	 * @return
	 */
	private ResultEntity createPieResult(String indexTab, SearchResponse response) {
		ResultEntity result = new ResultEntity();

		Terms agg = response.getAggregations().get("agg");
		int size = agg.getBuckets().size();
		if (size == 0) {
			result.setState(false);
			result.setMessage("没有数据");
			return result;
		}

		if (indexTab.indexOf("finance_log") != -1) {// 财经表需要单独把金额拿出来统计（相加）
			JSONArray opercmds = new JSONArray();
			JSONArray datas = new JSONArray();
			for (Terms.Bucket bt : agg.getBuckets()) {
				Sum sum = bt.getAggregations().get("sum_money");
				String key = bt.getKeyAsString();
				double total = round(sum.getValue());
				// System.out.println(bt.getKey() + "  " + bt.getDocCount() + " " + sum.getValue());
				JSONObject opercmd = new JSONObject();
				JSONObject data = new JSONObject();
				if ("00010401".equals(key)) {
					key = "现金收款";
				} else if ("00010402".equals(key)) {
					key = "划账支出";
				} else if ("00010403".equals(key)) {
					key = "ACS扣缴";
				} else if ("00010404".equals(key)) {
					key = "一卡通扣缴";
				} else if ("00010405".equals(key)) {
					key = "支付宝扣缴";
				} else if ("00010406".equals(key)) {
					key = "微信扣缴";
				}
				opercmd.put("name", key);
				opercmds.add(opercmd);
				data.put("name", key);
				data.put("value", total);
				datas.add(data);
			}
			StatisticsTempEntity temp = new StatisticsTempEntity();
			temp.setOpercmds(opercmds.toString());
			temp.setDatas(datas.toString());
			// System.out.println("finance_log-->" + JSONObject.fromObject(temp));
			result.setResult(temp);
			result.setState(true);
		} else if (indexTab.indexOf("loan_log") != -1) {
			JSONArray opercmds = new JSONArray();
			JSONArray datas = new JSONArray();
			for (Terms.Bucket entry : agg.getBuckets()) {
				String key = entry.getKeyAsString();
				long total = entry.getDocCount();
				// System.out.println(key + "  " + total);
				JSONObject opercmd = new JSONObject();
				JSONObject data = new JSONObject();
				if ("00010201".equals(key)) {
					key = "借书";
				} else if ("00010202".equals(key)) {
					key = "还书";
				} else if ("00010203".equals(key)) {
					key = "续借";
				} else {
					continue;
				}
				opercmd.put("name", key);
				opercmds.add(opercmd);
				data.put("name", key);
				data.put("value", total);
				datas.add(data);
			}
			StatisticsTempEntity temp = new StatisticsTempEntity();
			temp.setOpercmds(opercmds.toString());
			temp.setDatas(datas.toString());
			// System.out.println("loan_log-->" + JSONObject.fromObject(temp));
			result.setResult(temp);
			result.setState(true);
		} else if (indexTab.indexOf("cardissue_log") != -1) {
			JSONArray opercmds = new JSONArray();
			JSONArray datas = new JSONArray();
			for (Terms.Bucket entry : agg.getBuckets()) {
				String key = entry.getKeyAsString();
				if (key == null || "".equals(key.trim())) {
					key = "未知";
				} else if (!"男".equals(key.trim()) && !"女".equals(key.trim())) {
					key = "未知";
				}
				long total = entry.getDocCount();
				// System.out.println(key + "  " + total);
				JSONObject opercmd = new JSONObject();
				JSONObject data = new JSONObject();
				opercmd.put("name", key);
				opercmds.add(opercmd);
				data.put("name", key);
				data.put("value", total);
				datas.add(data);
			}
			StatisticsTempEntity temp = new StatisticsTempEntity();
			temp.setOpercmds(opercmds.toString());
			temp.setDatas(datas.toString());
			// System.out.println("cardissue_log-->" + JSONObject.fromObject(temp));
			result.setResult(temp);
			result.setState(true);
		}

		return result;
	}

	/**
	 * 统计操作结果生成饼图
	 * 
	 * @param indexTab
	 * @param response
	 * @return
	 */
	private ResultEntity createPieResultForResult(String indexTab, SearchResponse response) {
		ResultEntity result = new ResultEntity();

		Terms agg = response.getAggregations().get("agg");
		int size = agg.getBuckets().size();
		if (size == 0) {
			result.setState(false);
			result.setMessage("没有数据");
			return result;
		}

		JSONArray operresults = new JSONArray();
		JSONArray datas = new JSONArray();
		for (Terms.Bucket entry : agg.getBuckets()) {
			String key = entry.getKeyAsString();
			long total = entry.getDocCount();
			// System.out.println(key + "  " + total);
			JSONObject operresult = new JSONObject();
			JSONObject data = new JSONObject();
			if ("0".equals(key)) {
				key = "成功";
			} else {
				key = "失败";
			}
			operresult.put("name", key);
			operresults.add(operresult);
			data.put("name", key);
			data.put("value", total);
			datas.add(data);
		}
		StatisticsTempEntity temp = new StatisticsTempEntity();
		temp.setOpercmds(operresults.toString());
		temp.setDatas(datas.toString());
		// System.out.println("loan_log-->" + JSONObject.fromObject(temp));
		result.setResult(temp);
		result.setState(true);

		return result;
	}

	/**
	 * 统计操作类型生成柱状图
	 * 
	 * @param indexTab
	 * @param response
	 * @return
	 */
	private ResultEntity createBarResult(String indexTab, SearchResponse response) {
		ResultEntity result = new ResultEntity();

		Histogram times = response.getAggregations().get("time");
		int size = times.getBuckets().size();
		if (size == 0) {
			result.setState(false);
			result.setMessage("没有数据");
			return result;
		}
		if (indexTab.indexOf("finance_log") != -1) {// 财经表需要单独把金额拿出来统计（相加）
			String[] date = new String[size];
			double[] data = new double[size];
			int i = 0;
			for (Histogram.Bucket entry : times.getBuckets()) {
				String key = entry.getKeyAsString();
				date[i] = key;
				Terms agg = entry.getAggregations().get("agg");
				// System.out.println(key);
				double total = 0d;
				for (Terms.Bucket b : agg.getBuckets()) {
					Sum sum = entry.getAggregations().get("sum_money");
					double docCount = round(sum.getValue());
					// System.out.println(key + "  " + b.getKeyAsString() + " " + docCount);
					total = add(total, docCount);
				}
				data[i] = total;
				i++;
			}
			FinanceTempEntity temp = new FinanceTempEntity();
			temp.setDate(date);
			temp.setTotal(data);
			// System.out.println("finance_log-->" + JSONObject.fromObject(temp));
			result.setResult(temp);
			result.setState(true);
		} else if (indexTab.indexOf("loan_log") != -1) {
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
					// System.out.println(key + "  " + b.getKeyAsString() + "  " + count);
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
			CirculateTempEntity temp = new CirculateTempEntity();
			temp.setDate(date);
			temp.setLoan_total(loan_total);
			temp.setReturn_total(return_total);
			temp.setRenew_total(renew_total);
			// System.out.println("loan_log-->" + JSONObject.fromObject(temp));
			result.setResult(temp);
			result.setState(true);
		} else if (indexTab.indexOf("cardissue_log") != -1) {
			String[] date = new String[size];
			long[] success = new long[size];
			long[] failure = new long[size];
			int i = 0;
			for (Histogram.Bucket entry : times.getBuckets()) {
				String key = entry.getKeyAsString();
				date[i] = key;
				Terms agg = entry.getAggregations().get("agg");
				for (Terms.Bucket b : agg.getBuckets()) {
					Terms operresult = entry.getAggregations().get("operresult_agg");
					for (Terms.Bucket bt : operresult.getBuckets()) {
						String operresultStr = bt.getKeyAsString();
						long count = bt.getDocCount();
						// System.out.println(key + "  " + operresultStr + "  " + count);
						if ("0".equals(operresultStr)) {
							success[i] = count;
						} else if ("1".equals(operresultStr)) {
							failure[i] = count;
						}
					}
				}
				i++;
			}
			CardissueTempEntity temp = new CardissueTempEntity();
			temp.setDate(date);
			temp.setSuccess(success);
			temp.setFailure(failure);
			// System.out.println("cardissue_log-->" + JSONObject.fromObject(temp));
			result.setResult(temp);
			result.setState(true);
		}
		return result;
	}

	/**
	 * 查询统计
	 * 
	 * @param param
	 *            如：{indexTab=*_loan_log/*_finance_log/*_cardissue_log,*library_idx=168,*device_id=SCH_001,*type=all/year/month/week/today,*operresult=0/1,forTotal=true}
	 * @return
	 */
	private ResultEntity statisticsForTotal(Map<String, String> param) {
		if (client == null)
			client = ElasticsearchStatisticsServiceImpl.getClient();

		String indexTab = param.get("indexTab");// 索引名（表名）
		String library_idx = param.get("library_idx");
		String device_id = param.get("device_id");
		String device_idx = param.get("device_idx");
		String type = param.get("type");
		String operresult = param.get("operresult"); // 操作结果
		String containSubLib = param.get("containSubLib");

		SearchRequestBuilder responsebuilder = client.prepareSearch(indexTab)/* .setFrom(0).setSize(250) */;
		TermsBuilder aggregation = AggregationBuilders.terms("agg").field("opercmd_group");
		if (indexTab.indexOf("finance_log") != -1) {// 财经表单独处理
			aggregation.subAggregation(AggregationBuilders.sum("sum_money").field("Money_group"));
		}
		responsebuilder.addAggregation(aggregation);
		BoolQueryBuilder builder = QueryBuilders.boolQuery();// 条件组合

		if (library_idx != null && !"".equals(library_idx.trim()) && !"0".equals(library_idx.trim())) {
			if ("Y".equals(containSubLib)) {// 统计子馆
				// 查询获取当前图书馆及子馆数据
				List<RelLibsEntity> list = takeRelLibs(Integer.parseInt(library_idx));
				if (list != null && !CollectionUtils.isEmpty(list)) {
					BoolQueryBuilder builder_ = QueryBuilders.boolQuery();// 条件组合
					builder_.should(QueryBuilders.matchPhraseQuery("library_idx_group", library_idx));
					for (RelLibsEntity rel : list) {
						builder_.should(QueryBuilders.matchPhraseQuery("library_idx_group", rel.getSlave_lib_id()));
					}
					builder.filter(builder_);
				} else {
					builder.filter(QueryBuilders.matchPhraseQuery("library_idx_group", library_idx));
				}
			} else {
				builder.filter(QueryBuilders.matchPhraseQuery("library_idx_group", library_idx));
			}
		}
		if (device_id != null && !"".equals(device_id.trim())) {
			builder.filter(QueryBuilders.matchPhraseQuery("device_id_group", device_id));// 条件2
		}
		if (device_idx != null && !"".equals(device_idx.trim())) {
			builder.filter(QueryBuilders.matchPhraseQuery("device_idx_group", device_idx));// 条件3
		}
		if (operresult != null && !"".equals(operresult.trim())) {
			builder.filter(QueryBuilders.matchPhraseQuery("operresult_group", operresult));// 条件4
		}

		if (!T_ALL.equals(type)) {// 查全部数据，则没有时间范围
			String[] times = getTime(type);
			builder.filter(QueryBuilders.rangeQuery("opertime").format("yyyyMMdd").from(times[0]).to(times[1]));
		}

		responsebuilder.setQuery(builder).setSearchType(SearchType.COUNT).addSort("opertime", SortOrder.ASC);
		SearchResponse response = responsebuilder.execute().actionGet();
		Terms agg = response.getAggregations().get("agg");

		return createBarResult(indexTab, agg);
	}

	private ResultEntity createBarResult(String indexTab, Terms agg) {
		ResultEntity result = new ResultEntity();
		int size = agg.getBuckets().size();
		if (size == 0) {
			result.setState(false);
			result.setMessage("没有数据");
			return result;
		}

		if (indexTab.indexOf("finance_log") != -1) {// 财经表需要单独把金额拿出来统计（相加）
			JSONArray datas = new JSONArray();
			for (Terms.Bucket bt : agg.getBuckets()) {
				Sum sum = bt.getAggregations().get("sum_money");
				String key = bt.getKeyAsString();
				double total = round(sum.getValue());
				// System.out.println(bt.getKey() + "  " + bt.getDocCount() + " " + sum.getValue());
				JSONObject data = new JSONObject();
				if ("00010401".equals(key)) {
					key = "现金收款";
				} else if ("00010402".equals(key)) {
					key = "划账支出";
				} else if ("00010403".equals(key)) {
					key = "ACS扣缴";
				} else if ("00010404".equals(key)) {
					key = "一卡通扣缴";
				} else if ("00010405".equals(key)) {
					key = "支付宝扣缴";
				} else if ("00010406".equals(key)) {
					key = "微信扣缴";
				}
				data.put("name", key);
				data.put("value", total);
				datas.add(data);
			}
			result.setResult(datas);
			result.setState(true);
		} else if (indexTab.indexOf("loan_log") != -1) {
			JSONArray datas = new JSONArray();
			for (Terms.Bucket entry : agg.getBuckets()) {
				String key = entry.getKeyAsString();
				long total = entry.getDocCount();
				// System.out.println(key + "  " + total);
				JSONObject data = new JSONObject();
				if ("00010201".equals(key)) {
					key = "借书";
				} else if ("00010202".equals(key)) {
					key = "还书";
				} else if ("00010203".equals(key)) {
					key = "续借";
				} else {
					continue;
				}
				data.put("name", key);
				data.put("value", total);
				datas.add(data);
			}
			result.setResult(datas);
			result.setState(true);
		} else if (indexTab.indexOf("cardissue_log") != -1) {
			JSONArray datas = new JSONArray();
			for (Terms.Bucket entry : agg.getBuckets()) {
				String key = entry.getKeyAsString();
				long total = entry.getDocCount();
				// System.out.println(key + "  " + total);
				JSONObject data = new JSONObject();
				if ("00010301".equals(key)) {
					key = "办证";
				} else {
					continue;
				}
				data.put("name", key);
				data.put("value", total);
				datas.add(data);
			}
			result.setResult(datas);
			result.setState(true);
		}

		return result;
	}

	/**
	 * 求和
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	private double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 计算百分比
	 * 
	 * @param value
	 * @return
	 */
	private double round(double value) {
		return Math.round(value * 100) / 100.0;
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
		DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date d = new Date();
		if (T_YESTERDAY.equals(type)) {// 昨天
			GregorianCalendar gc = new GregorianCalendar();
			gc.add(Calendar.DAY_OF_MONTH, -1);
			startTime = sdf.format(gc.getTime());
			endTime = startTime;
		} else if (T_LASTMONTH.equals(type)) {// 上月
			DateFormat df = new SimpleDateFormat("yyyyMM");
			GregorianCalendar gc = new GregorianCalendar();
			gc.add(Calendar.MONTH, -1);
			startTime = df.format(gc.getTime()) + "01";
			int MaxDay = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
			endTime = df.format(gc.getTime()) + MaxDay;
		} else if (T_LASTYEAR.equals(type)) {// 去年
			DateFormat df = new SimpleDateFormat("yyyy");
			GregorianCalendar gc = new GregorianCalendar();
			gc.add(Calendar.YEAR, -1);
			startTime = df.format(gc.getTime()) + "0101";
			int MaxDay = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
			endTime = df.format(gc.getTime()) + "12" + MaxDay;
		} else if (T_YEAR.equals(type)) {// 查一年数据，今天到本年1月1号
			DateFormat df = new SimpleDateFormat("yyyy");
			startTime = df.format(d) + "0101";
			endTime = sdf.format(d);
		} else if (T_MONTH.equals(type)) {// 查一个月数据，今天到本月初
			DateFormat df = new SimpleDateFormat("yyyyMM");
			startTime = df.format(d) + "01";
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

	public static void main(String[] args) {
		try {
			TransportClient t = TransportClient.builder().build();
			String[] esIPArr = { "172.16.0.113"};
			TransportAddress[] ts = new TransportAddress[esIPArr.length];
			for (int i = 0; i < esIPArr.length; i++) {
				ts[i] = new InetSocketTransportAddress(InetAddress.getByName(esIPArr[i]), 9300);
			}
			for (TransportAddress t1 : ts) {
				t.addTransportAddress(t1);
			}
			client = t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> param = new HashMap<String, String>();
		// param.put("type", "week");
		param.put("type", T_YEAR);
		//param.put("indexTab", "*_loan_log");
		// param.put("indexTab", "*_finance_log");
		 param.put("indexTab", "*_cardissue_log");
		//param.put("library_idx", "189");
		//param.put("device_id", "M201711222123456");
		// param.put("tag", "test");
		param.put("chart_type", "bar");
		param.put("agg_type", "opercmd");
		//param.put("forTotal", "true");
		//param.put("operresult", "1");
		MainPageServiceImpl impl = new MainPageServiceImpl();
		ResultEntity result = impl.statistics(param);
		System.out.println(JSONObject.fromObject(result));
	}

	public List<RelLibsEntity> takeRelLibs(Integer key) {
		List<RelLibsEntity> list = new ArrayList<>();
		List<RelLibsEntity> rels = getRelLibs(list,key);
		while(true){
			if(rels != null && rels.size() > 0){
				list.addAll(rels);
				rels = getRelLibs(rels,key);
			}else{
				break;
			}
		}
		return list;
	}
	
	private List<RelLibsEntity> getRelLibs(List<RelLibsEntity> rels,Integer key){
		String libIdxStr = "";
		for(RelLibsEntity rel : rels){
			libIdxStr += rel.getSlave_lib_id() + ",";
		}
		if(libIdxStr.length() > 0){
			libIdxStr = libIdxStr.substring(0,libIdxStr.length()-1);
		}else{
			libIdxStr = libIdxStr + key;
		}
		List<RelLibsEntity> list = new ArrayList<>();
		String json = "{\"library_idx\":\"" + libIdxStr + "\"}";
		Map<String, String> map = new HashMap<>();
		map.put("json", json);
		ResultEntity devResult = new ResultEntity();
		if(requestURL==null)//调用时，得不到RequestURLListEntity的实例，故重新实例化一次
			requestURL = new RequestURLListEntity(XMLUtils.parseAll(ResourcesUtil.getInputStream("RequestURL.xml")));
		String reqURL=requestURL.getRequestURL(SEL_MASTERLIBS_BY_IDX);
		String res=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		devResult = JsonUtils.fromJson(res, ResultEntity.class);	
		if (devResult!=null&&devResult.getState()) {
			list = JsonUtils.fromJson(JsonUtils.toJson(devResult.getResult()), new TypeReference<List<RelLibsEntity>>() {});
		}
		return list;
	}
}
