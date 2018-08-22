package com.ssitcloud.business.statistics.service.impl;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

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
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.business.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.statistics.service.DeviceMonitorService;
import com.ssitcloud.business.statistics.service.ElasticsearchStatisticsService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class DeviceMonitorServiceImpl extends BasicServiceImpl implements DeviceMonitorService {
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

	@Override
	public ResultEntity countDatas(String req) {
		if (StringUtils.hasText(req) && JSONUtils.mayBeJSON(req)) {
			JSONObject json = JSONObject.fromObject(req);
			String type = json.optString("type");
			if ("c".equals(type)) {
				return countCardissueLog(json);
			} else {
				return countLoanLog(json);
			}
		}
		return new ResultEntity(false, "传入参数有误");
	}

	public ResultEntity countCardissueLog(JSONObject json) {
		json.put("indexTab", "*_cardissue_log");
		json.put("opercmd_groups", "00010301,");

		ResultEntity entity = new ResultEntity();
		JSONArray result = statistics(json);
		if (result.isEmpty()) {
			entity.setMessage("没有数据");
			entity.setState(false);
			return entity;
		}
		entity.setResult(result);
		entity.setState(true);
		return entity;
	}

	public ResultEntity countLoanLog(JSONObject json) {
		json.put("indexTab", "*_loan_log");
		json.put("opercmd_groups", "00010201,00010202,00010203");

		ResultEntity entity = new ResultEntity();
		JSONArray result = statistics(json);
		if (result.isEmpty()) {
			entity.setMessage("没有数据");
			entity.setState(false);
			return entity;
		}

		entity.setResult(result);
		entity.setState(true);
		return entity;
	}

	private JSONArray statistics(JSONObject param) {
		if (client == null)
			client = ElasticsearchStatisticsServiceImpl.getClient();

		String indexTab = param.optString("indexTab");// 索引名（表名）
		String library_idx = param.optString("library_idx");
		String device_idxs = param.optString("device_idxs");
		String opercmd_groups = param.optString("opercmd_groups");
		String time = param.optString("time", T_TODAY);
		String operresult = param.optString("operresult");
		String[] devices = null;
		if (device_idxs.indexOf(",") != -1) {
			devices = device_idxs.split("\\,");
		} else {
			devices = new String[1];
			devices[0] = device_idxs;
		}

		SearchRequestBuilder responsebuilder = client.prepareSearch(indexTab);
		TermsBuilder aggregation = AggregationBuilders.terms("agg").field("device_idx_group").size(devices.length);
		aggregation.subAggregation(AggregationBuilders.terms("sub_agg").field("opercmd_group"));
		responsebuilder.addAggregation(aggregation);

		BoolQueryBuilder builder = QueryBuilders.boolQuery();// 条件组合
		if (library_idx != null && !"".equals(library_idx.trim())) {
			builder = builder.must(QueryBuilders.matchQuery("library_idx_group", library_idx));
		}
		if (device_idxs != null && !"".equals(device_idxs.trim())) {
			builder = builder.must(QueryBuilders.termsQuery("device_idx_group", devices));
		}
		if (opercmd_groups != null && !"".equals(opercmd_groups.trim())) {
			builder = builder.must(QueryBuilders.termsQuery("opercmd_group", opercmd_groups.split("\\,")));
		}
		if (operresult != null && !"".equals(operresult.trim())) {
			builder = builder.must(QueryBuilders.matchQuery("operresult_group", operresult));
		}

		if (!T_ALL.equals(time)) {// 查全部数据，则没有时间范围
			String[] times = getTime(time);
			builder.must(QueryBuilders.rangeQuery("opertime").format("yyyyMMdd").from(times[0]).to(times[1]));
		}

		responsebuilder.setQuery(builder).setSearchType(SearchType.DEFAULT);
		SearchResponse response = responsebuilder.setExplain(true).execute().actionGet();

		Terms agg = response.getAggregations().get("agg");
		int size = agg.getBuckets().size();
		if (size == 0) {
			return new JSONArray();
		}
		return createResult(indexTab, agg);
	}

	private JSONArray createResult(String indexTab, Terms agg) {
		JSONArray data = new JSONArray();
		if (indexTab.indexOf("loan_log") != -1) {
			for (Terms.Bucket entry : agg.getBuckets()) {
				JSONObject subdata = new JSONObject();
				String key = entry.getKeyAsString();
				subdata.put("id", key);
				Terms sub = entry.getAggregations().get("sub_agg");
				for (Terms.Bucket bt : sub.getBuckets()) {
					String sub_key = bt.getKeyAsString();
					long total = bt.getDocCount();
					// System.out.println(key + "  " + sub_key + "  " + total);
					if ("00010201".equals(sub_key)) {
						subdata.put("loanData", total);
					} else if ("00010202".equals(sub_key)) {
						subdata.put("returnData", total);
					} else if ("00010203".equals(sub_key)) {
						subdata.put("renewData", total);
					} else {
						continue;
					}
				}
				data.add(subdata);
			}
		} else if (indexTab.indexOf("cardissue_log") != -1) {
			for (Terms.Bucket entry : agg.getBuckets()) {
				JSONObject subdata = new JSONObject();
				String key = entry.getKeyAsString();
				subdata.put("id", key);
				Terms sub = entry.getAggregations().get("sub_agg");
				for (Terms.Bucket bt : sub.getBuckets()) {
					String sub_key = bt.getKeyAsString();
					long total = bt.getDocCount();
					// System.out.println(key + "  " + sub_key + "  " + total);
					if ("00010301".equals(sub_key)) {
						subdata.put("cardData", total);
					} else {
						continue;
					}
				}
				data.add(subdata);
			}
		}

		return data;
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
			String[] esIPArr = { "172.16.0.119" };
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
		JSONObject param = new JSONObject();
		param.put("library_idx", "181");
		param.put("device_idxs","397,442,464,480,513,577,578,590,605,611,633,677,678,696,697,698,699,700,701,702,703,704,705,706,707,708,709,710,711,712,713,714,715,716,717,718,719,720,721,722,723,724,725,726,727,728,729,730,731,732,733,734,735,736,737,738,739,740,741,742,743,744,745,746,747,748,749,750,751,752,753,754,755,756,757,758,759,760,761,762,763,764,765,766,767,768,769,770,771,772,773,774,775,776,777,778,779,780,781,782,783,784,785,786,787,788,789,790,791,792,793,794,795,796,797,798,799,800,801,802,803,804,805,806,807,808,809,810,811,812,813,814,815,816,819,821,822,823,824,828,829,1240,1242,1243,1245,1250,1251,1252,1253,1254,1255,1256,1257,1463,1465,1467,1469,1477,1478");
		param.put("time", T_MONTH);
		param.put("type", "l");
		DeviceMonitorServiceImpl impl = new DeviceMonitorServiceImpl();
		ResultEntity entity = impl.countDatas(param.toString());
		System.out.println(JSONObject.fromObject(entity));
		
		param.put("type", "c");
		entity = impl.countDatas(param.toString());
		System.out.println(JSONObject.fromObject(entity));
	}
}
