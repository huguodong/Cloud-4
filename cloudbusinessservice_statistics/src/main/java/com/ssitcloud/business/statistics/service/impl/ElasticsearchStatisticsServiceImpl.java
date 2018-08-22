package com.ssitcloud.business.statistics.service.impl;




import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.IndexNotFoundException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.MetricsAggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.authentication.entity.RelLibsEntity;
import com.ssitcloud.business.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.statistics.common.utils.HttpClientUtil;
import com.ssitcloud.business.statistics.common.utils.JsonUtils;
import com.ssitcloud.business.statistics.common.utils.LogUtils;
import com.ssitcloud.business.statistics.common.utils.ResourcesUtil;
import com.ssitcloud.business.statistics.common.utils.StatisConstant;
import com.ssitcloud.business.statistics.common.utils.StatisticsUtils;
import com.ssitcloud.business.statistics.common.utils.XMLUtils;
import com.ssitcloud.business.statistics.service.CommonEsStatisticService;
import com.ssitcloud.business.statistics.service.ElasticsearchStatisticsService;
import com.ssitcloud.business.statistics.service.StatisticsService;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.UserRolePermessionEntity;
import com.ssitcloud.devmgmt.entity.DeviceEntity;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;




@Service
public class ElasticsearchStatisticsServiceImpl extends BasicServiceImpl implements
		ElasticsearchStatisticsService {
	protected static ObjectMapper mapper = JsonFactory.create();
	private volatile static Client client;
	
	//设备相关
	public static final String URL_QUERYDEVICES="selectDevice";
	//图书馆相关
	private static final String SEL_LIBRARY_BY_IDX_OR_ID = "selLibraryByIdxOrId";
	//查询子馆
	private static final String SEL_MASTERLIBS_BY_IDX = "selmasterLibsByIdx";
	
	private static final String URL_SELECTAUTBYSQL = "selectAutBySql";
	
	
	@Resource
	private StatisticsService statisticsservice;

	@Resource
	private CommonEsStatisticService commonEsStatisticService;

    private static String esIP;

    private static int esPort;

    public static Client getClient() {
        if(client == null){
            synchronized (ElasticsearchStatisticsServiceImpl.class){
                if(client == null){
                    client = getInstance();
                }
            }
        }
        return client;
    }

    @Value("${esIP}")
    public void setEsIP(String esIP) {
        ElasticsearchStatisticsServiceImpl.esIP = esIP;
    }

    @Value("${esPort}")
    public void setesPort(int esPort) {
        ElasticsearchStatisticsServiceImpl.esPort = esPort;
    }

    /**
	 * 走statistics的查询
	 */
	public ResultEntity statistics(String req) {
		ResultEntity result = new ResultEntity();
		String indexType = "";//索引的类型
		List<List<Map.Entry<String, Long>>> infoIds = new ArrayList<List<Map.Entry<String, Long>>>();//查询出的结果集
		JSONObject json = JSONObject.fromObject(req);
		String ds = json.getString("datasource");//数据源
		JSONObject condition = json.getJSONObject("condition");//查询条件
		String gTreeArr = json.getString("gTreeArr");//传递树的组合
		String isContainSubLib = json.optString("isContainSubLib");
		//获得对应馆的所有设备 start
		Integer libidx = json.getInt("library_idx");
		String libId = json.getString("lib_id").toLowerCase();
        Integer operator_type = json.getInt("operator_type");
    	String group = json.getString("group");//分组
		String s = json.getString("gcondition");//分组条件
		String nogroup = json.getString("nogroup").trim();//不分组
		int topN = json.optInt("topHits",StatisConstant.STATIC_TOPN);//TopN
		// 读者维度统计
		if (!org.springframework.util.StringUtils.isEmpty(ds) && ds.endsWith("reader_circulation_log")) {
			return commonEsStatisticService.statisticsForReader(req);
		}
		//获取当前图书馆的子馆 start
		List<Integer> libIdxs = new ArrayList<Integer>();//图书馆idx
		libIdxs.add(libidx);
		List<String> libIds = new ArrayList<String>();//图书馆id
		libIds.add(libId);
		if("0".equals(isContainSubLib)){
			List<RelLibsEntity> list = takeRelLibs(libidx);
			if(list!=null){
				for(RelLibsEntity rel : list){
					Integer tmpidx = rel.getSlave_lib_id();
					JSONObject libJson = takeLib(tmpidx);
					if(libJson.get("lib_id")!=null){
						libIdxs.add(libJson.getInt("library_idx"));
						libIds.add(libJson.getString("lib_id").toLowerCase());
					}
				}
			}
		}
		
		//获取当前图书馆的子馆 start
		String[] datasource = null;//要查的elachseach索引
		StringBuffer sb = new StringBuffer();
		if(operator_type == 1){
		    sb.append("*_"+ds+"_statistics"+",");
        }else{
            for(int tmp=0;tmp<libIdxs.size();tmp++){
            	sb.append(libIds.get(tmp) + "_*_"+ds+"_statistics"+",");
                /*Integer libIdx = libIdxs.get(tmp);
                JSONArray devJsonArr = takeDev(libIdx);
                if(devJsonArr!=null){
                    for (int d = 0; d < devJsonArr.size(); d++) {
                    }
                }*/
            }
        }

		if(sb.toString().endsWith(",")){
			sb.deleteCharAt(sb.length()-1);
		}
		datasource = sb.toString().split(",");
		LogUtils.info("(走statistics)当前馆要查询的elachseach的索引名称为######"+Arrays.toString(datasource)+"#####");
//		String[] datasource = new String[1];
//		datasource[0] = "hhtest_sch001_finance_log_statistics";
		//获得对应馆的所有设备 end
		//int rIndex = 1;//判断走statistics后，是否需要再走elachseach标识
		String[] grouparr = group.split(",");
		Map<String,String> map =null;
		if(!s.equals("{}")){
			JSONObject gcondition = JSONObject.fromObject(s);
			map = StatisticsUtils.JsonToMap(gcondition);//组条件转map
		}
		String gstr = "";
		try{
			String[] treeDataSource = new String[datasource.length];//查询elachseach的树原形
			for (int d = 0; d < datasource.length; d++) {
				treeDataSource[d] = datasource[d].replaceAll("_statistics", "");
			}
			gstr = commonEsStatisticService.gtreeForReader(treeDataSource,grouparr,map,null,false,condition,topN).toString();
		}catch(IndexNotFoundException e){//走statistics时，找不到elachseach的index则走elachseach
			LogUtils.error("走statistics查询左边目录树时，找不到elachseach的index则走elachseach",e);
			return subStatistics(req);
		}
		String[] gstrArr = gstr.split(";");
		int gIndex = 0;
		for(int gs=0;gs<gstrArr.length;gs++){
			String[] tmpArr = gstrArr[gs].split(",");
			//rIndex = rIndex*(tmpArr.length-1);
			if(tmpArr.length==1){
				break;
			}
			gIndex = gIndex+1;
		}
		String[] aggArr = new String[gIndex];
		for(int g1=0;g1<gIndex;g1++){
			aggArr[g1] = grouparr[g1];
		}
		//流通统计相关 start
		MetricsAggregationBuilder<?> loanAgg = null;//借书
		MetricsAggregationBuilder<?> ruturnAgg = null;//还书
		MetricsAggregationBuilder<?> renewAgg = null;//续借
		//流通统计相关end
		//办证统计相关 start
		MetricsAggregationBuilder<?> cardissueAgg = null;
		//办证统计相关end
		//财经统计相关 start
		MetricsAggregationBuilder<?> financeAgg = null;
		//财经统计相关end
		@SuppressWarnings("rawtypes")
		List<MetricsAggregationBuilder> aggList = new ArrayList<MetricsAggregationBuilder>();//
		boolean statisticsExistDate = aggArr!=null&&StatisticsUtils.isContains(aggArr,"opertime");//存在年月日周统计判断标识
		if(statisticsExistDate){
			if(map!=null){//组不为空时
				if(map.containsKey("y#opertime")){
					if("loan_log".equals(ds)){
						loanAgg = AggregationBuilders.sum("loanSum").field("loan_cirYearCount");
						ruturnAgg = AggregationBuilders.sum("returnSum").field("return_cirYearCount");
						renewAgg = AggregationBuilders.sum("renewSum").field("renew_cirYearCount");
					}else if("cardissue_log".equals(ds)){
						cardissueAgg = AggregationBuilders.sum("cardissueSum").field("newCardYearCount");
					}else if("finance_log".equals(ds)){
						financeAgg = AggregationBuilders.sum("financeSum").field("fineYearCount");
					}
					indexType = "YEAR";
				}else if(map.containsKey("m#opertime")){
					if("loan_log".equals(ds)){
						loanAgg = AggregationBuilders.sum("loanSum").field("loan_cirMonthCount");
						ruturnAgg = AggregationBuilders.sum("returnSum").field("return_cirMonthCount");
						renewAgg = AggregationBuilders.sum("renewSum").field("renew_cirMonthCount");
					}else if("cardissue_log".equals(ds)){
						cardissueAgg = AggregationBuilders.sum("cardissueSum").field("newCardMonthCount");
					}else if("finance_log".equals(ds)){
						financeAgg = AggregationBuilders.sum("financeSum").field("fineMonthCount");
					}
					indexType = "MONTH";
				}else if(map.containsKey("d#opertime")){
					if("loan_log".equals(ds)){
						loanAgg = AggregationBuilders.sum("loanSum").field("loan_cirDayCount");
						ruturnAgg = AggregationBuilders.sum("returnSum").field("ruturn_cirDayCount");
						renewAgg = AggregationBuilders.sum("renewSum").field("renew_cirDayCount");
					}else if("cardissue_log".equals(ds)){
						cardissueAgg = AggregationBuilders.sum("cardissueSum").field("newCardDayCount");
					}else if("finance_log".equals(ds)){
						financeAgg = AggregationBuilders.sum("financeSum").field("fineDayCount");
					}
					indexType = "DAY";
				}else if(map.containsKey("w#opertime")){
					if("loan_log".equals(ds)){
						loanAgg = AggregationBuilders.sum("loanSum").field("loan_cirWeekCount");
						ruturnAgg = AggregationBuilders.sum("returnSum").field("return_cirWeekCount");
						renewAgg = AggregationBuilders.sum("renewSum").field("renew_cirWeekCount");
					}else if("cardissue_log".equals(ds)){
						cardissueAgg = AggregationBuilders.sum("cardissueSum").field("newCardWeekCount");
					}else if("finance_log".equals(ds)){
						financeAgg = AggregationBuilders.sum("financeSum").field("fineWeekCount");
					}
					indexType = "WEEK";
				}
			}
		}
		if(statisticsExistDate&&aggArr.length==1){//按时间分组
			String[] sgroup = null;
			if(ds!=null){
				if("loan_log".equals(ds)){//流通统计
					sgroup = new String[]{"circulateDate"};
				}else if("cardissue_log".equals(ds)){//办证统计
					sgroup = new String[]{"newCardDate"};
				}else if("finance_log".equals(ds)){//财经统计
					sgroup = new String[]{"fineDate"};
				}
			}
			@SuppressWarnings("rawtypes")
			AggregationBuilder[] aggregationBuilders = new AggregationBuilder[1];
			if(sgroup!=null){
				aggregationBuilders[0] = AggregationBuilders.terms(sgroup[0]).field(sgroup[0]);
				if(StringUtils.isBlank(indexType)){//没有按日期统计时，直接走elachseach
					return subStatistics(req);
				}else{
					if("loan_log".equals(ds)){
						aggList.add(loanAgg);
						aggList.add(ruturnAgg);
						aggList.add(renewAgg);
					}else if("cardissue_log".equals(ds)){
						aggList.add(cardissueAgg);
					}else if("finance_log".equals(ds)){
						aggList.add(financeAgg);
					}
					String[] nogrouparr = null;
					if(StringUtils.isNotBlank(nogroup)){//不分组的数据不为空时，不分组
						nogrouparr = nogroup.split(",");
						//rIndex = rIndex*nogrouparr.length;
					}
					AbstractAggregationBuilder termsBuilder = null;
					int sIndex = gstr.length();
					if(gIndex!=gstrArr.length)
						sIndex = StatisticsUtils.getCharacterPosition(gstr, gIndex);
					String newGstr = gstr.substring(0, sIndex);
					if(nogrouparr!=null){
						StatisticsUtils.assembleAgg(aggregationBuilders, aggregationBuilders.length-1,aggList);
						termsBuilder = aggregationBuilders[0];//得到要分组的参数
						for(int ng=0;ng<nogrouparr.length;ng++){//根据不是分组的查询数据
							String tmp = nogrouparr[ng];
							String[] arr = tmp.split("\\|");
							String key = arr[0];
							String code = "";
							if(arr!=null&&arr.length>1){
								code = arr[1];
							}
							key = StatisticsUtils.ngChange(key, code, ds);
							BoolQueryBuilder conditionQB = statisticsCondition(condition,indexType,ds);//得到statistics查询条件
							List<Map.Entry<String, Long>> rInfo = null;
							try{
								rInfo = oneSearchIndex(tmp,map,key,conditionQB,termsBuilder,newGstr,/*staticGArr,*/datasource,indexType,libIdxs,grouparr,ds,operator_type,gTreeArr);
								if(rInfo==null){
									return subStatistics(req);
								}
							}catch(IndexNotFoundException e){//走statistics时，找不到elachseach的index则走elachseach
								LogUtils.error("走statistics时，找不到elachseach的index则走elachseach",e);
								return subStatistics(req);
							}
							infoIds.add(rInfo);
						}
					}
				}
				if(infoIds.isEmpty()){//statistics查不出数据时，走elachseach
					return subStatistics(req);
				}else{
					int index = 0;//如果查询出来的都是没有数据的，则走一遍elachseach
					int rIndex = 0;
					for(List<Map.Entry<String, Long>> infoId:infoIds){
						rIndex += infoId.size();
						for(Map.Entry<String, Long> me:infoId){
							Long v = me.getValue();
							if(v==0){
								index = index+1;
							}
						}
					}
					if(index==rIndex){//如果走完statistics后，所有结果都为零则再走elachseach
						return subStatistics(req);
					}else{
						LogUtils.info(ds+"################################统计是statistics的年月日周index查询后的结果##################################");
						result.setResult(infoIds);
						result.setState(true);
						return result;
					}
				}
			}else{
				return subStatistics(req);
			}
		}if(statisticsExistDate&&aggArr.length>1){//当分组条件有opertime且分组是一个以上的走statistics
			int sIndex = gstr.length();
			if(gIndex!=gstrArr.length)
				sIndex = StatisticsUtils.getCharacterPosition(gstr, gIndex);
			String newGstr = gstr.substring(0, sIndex);
			AbstractAggregationBuilder termsBuilder = null;
			//String[] sgroup = null;
			/*String ds = "";//数据源
			if(json.get("datasource")!=null){
				ds = json.getString("datasource");
			}*/
			/*if(json.get("datasource")!=null){
				ds = json.getString("datasource");
				if("loan_log".equals(ds)){//流通统计
					sgroup = new String[]{"lib_idx","device_idx","cirsubType","circulateDate"};
				}else if("cardissue_log".equals(ds)){//办证统计
					sgroup = new String[]{"lib_idx","device_idx","newCardsubType","newCardDate"};
				}else if("finance_log".equals(ds)){//财经统计
					sgroup = new String[]{"lib_idx","device_idx","finesubType","fineDate"};
				}
			}*/
			int tmpFlag = 0;
			StringBuffer groupSb = new StringBuffer();//statistics要分组的数据
			//if(aggArr.length<5){//当分组在四个以内，先查statistics
				for(int g=0;g<aggArr.length;g++){
					String tmpStr = StatisticsUtils.toStatisticsKey(aggArr[g],ds);//转化为对应的年月日周index
					tmpFlag = tmpFlag+1;
					groupSb.append(tmpStr+",");
					/*if(StatisticsUtils.isContains(sgroup,tmpStr)){
					}*/
				}
			//}
			if(groupSb.toString().endsWith(",")){
				groupSb.deleteCharAt(groupSb.length()-1);
			}
			String[] staticGArr = groupSb.toString().split(",");//要分组的内容
			if(tmpFlag==staticGArr.length&&StatisticsUtils.checkRepeat(staticGArr)){//如果分组里都是中间表里的数据，则查询statistics表
				@SuppressWarnings("rawtypes")
				AggregationBuilder[] aggregationBuilders = new AggregationBuilder[staticGArr.length];
				for(int g=0;g<staticGArr.length;g++){
					boolean f = ("circulateDate").equals(staticGArr[g])||("newCardDate").equals(staticGArr[g])||("fineDate").equals(staticGArr[g]);
					if(f){//如果是日期
						aggregationBuilders[g] = AggregationBuilders.terms(staticGArr[g]).field(staticGArr[g]).size(200);
					}else{
						aggregationBuilders[g] = AggregationBuilders.terms(staticGArr[g]+"_group").field(staticGArr[g]+"_group").size(200);
					}
				}
				if(StringUtils.isBlank(indexType)){//没有按日期统计时，直接走elachseach
					return subStatistics(req);
				}else{
					if("loan_log".equals(ds)){
						aggList.add(loanAgg);
						aggList.add(ruturnAgg);
						aggList.add(renewAgg);
					}else if("cardissue_log".equals(ds)){
						aggList.add(cardissueAgg);
					}else if("finance_log".equals(ds)){
						aggList.add(financeAgg);
					}
					String[] nogrouparr = null;
					if(StringUtils.isNotBlank(nogroup)){//不分组的数据不为空时，不分组
						nogrouparr = nogroup.split(",");
						//rIndex = rIndex*nogrouparr.length;
					}
					if(nogrouparr!=null){
						StatisticsUtils.assembleAgg(aggregationBuilders, aggregationBuilders.length-1,aggList);
						termsBuilder = aggregationBuilders[0];//得到要分组的参数
						for(int ng=0;ng<nogrouparr.length;ng++){//根据不是分组的查询数据
							String tmp = nogrouparr[ng];
							String[] arr = tmp.split("\\|");
							String key = arr[0];
							String code = "";
							if(arr!=null&&arr.length>1){
								code = arr[1];
							}
							key = StatisticsUtils.ngChange(key, code, ds);
							BoolQueryBuilder conditionQB = statisticsCondition(condition,indexType,ds);//得到statistics查询条件
							List<Map.Entry<String, Long>> rInfo = null;
							try{
//								rInfo = firstSearchIndex(map,key,conditionQB,termsBuilder,newGstr,/*staticGArr,*/datasource,indexType,libidx,grouparr,ds);
								rInfo = firstSearchIndex(tmp,map,key,conditionQB,termsBuilder,newGstr,/*staticGArr,*/datasource,indexType,libIdxs,grouparr,ds,operator_type,gTreeArr);
								if(rInfo==null){
									return subStatistics(req);
								}
							}catch(IndexNotFoundException e){//走statistics时，找不到elachseach的index则走elachseach
								LogUtils.error("走statistics时，找不到elachseach的index则走elachseach",e);
								return subStatistics(req);
							}
							infoIds.add(rInfo);
						}
					}
				}
			}
			
			if(infoIds.isEmpty()){//statistics查不出数据时，走elachseach
				return subStatistics(req);
			}else{
				int index = 0;//如果查询出来的都是没有数据的，则走一遍elachseach
				int rIndex = 0;
				for(List<Map.Entry<String, Long>> infoId:infoIds){
					rIndex += infoId.size();
					for(Map.Entry<String, Long> me:infoId){
						Long v = me.getValue();
						if(v==0){
							index = index+1;
						}
					}
				}
				if(index==rIndex){//如果走完statistics后，所有结果都为零则再走elachseach
					return subStatistics(req);
				}else{
					LogUtils.info(ds+"################################统计是statistics的年月日周index查询后的结果##################################");
					result.setResult(infoIds);
					result.setState(true);
					return result;
				}
			}
		}else{
			return subStatistics(req);
		}
	}
	public static List<Map.Entry<String, Long>> oneSearchIndex(String ngKey,Map<String,String> map,String ceil,BoolQueryBuilder qb,AbstractAggregationBuilder termsBuilder,String fullTree,/*String[] rootArr,*/ String[] tableName,String indexType,/*Integer libidx,*/List<Integer> libIdxs,String[] grouparr,String ds,Integer operator_type,String gTreeArr) {
		String[] cArr = null;
		if(ceil.indexOf("|")>-1){
			cArr = ceil.split("\\|");
			String queryAttr = cArr[0].trim();
			if(cArr!=null&&cArr.length>1){
				String queryVal = cArr[1].trim();
				if(queryAttr.length()>0&&queryVal.length()>0){
					if(queryVal.indexOf("-")>-1){//有范围
						String[] rangeArr = queryVal.split("-");
						qb.must(QueryBuilders.rangeQuery(queryAttr).from(rangeArr[0]).to(rangeArr[1]).includeLower(true).includeUpper(true));
					}else{//没有范围
						qb.must(QueryBuilders.matchQuery(queryAttr, queryVal));
					}
//					qb.must(QueryBuilders.matchQuery(queryAttr, queryVal));
				}
			}else{
				qb.must(QueryBuilders.existsQuery(cArr[0].trim()));
			}
		}
		if(operator_type!=1){
			for(Integer libidx : libIdxs){
				qb.should(QueryBuilders.matchQuery("lib_idx", libidx));//查询特定馆
			}
		}
		SearchRequestBuilder searchRequestBuilder = getClient().prepareSearch(tableName).setTypes(indexType).setQuery(qb);
		if(grouparr!=null){
			//对应statistics表里
			String nogName = "";
			if(ngKey.indexOf("|")>-1){
				nogName = ngKey.substring(0, ngKey.indexOf("|"));
			}
			switch (ds) {
			case "loan_log":
				if("Cardtype".equals(nogName)){//读者流通类型
					qb.must(QueryBuilders.matchQuery("circulateCountType", "11"));
				}else if("Callno".equals(nogName)){//图书22大类
					qb.must(QueryBuilders.matchQuery("circulateCountType", "12"));
				}else if("CurrentLocation".equals(nogName)){//图书馆藏地
					qb.must(QueryBuilders.matchQuery("circulateCountType", "13"));
				}else if("MediaType".equals(nogName)){//图书载体类型
					qb.must(QueryBuilders.matchQuery("circulateCountType", "14"));
				}else if("operresult".equals(nogName)){//操作结果
					qb.must(QueryBuilders.matchQuery("circulateCountType", "15"));
				}else if("AuthType".equals(nogName)){//认证类型
					qb.must(QueryBuilders.matchQuery("circulateCountType", "17"));
				}else if("opertime".equals(nogName)){//操作时间点
					qb.must(QueryBuilders.matchQuery("circulateCountType", "16"));
				}
				break;
			case "cardissue_log":
				if("Cardtype".equals(nogName)){//读者流通类型
					qb.must(QueryBuilders.matchQuery("newCardCountType", "31"));
				}else if("AuthType".equals(nogName)){//认证类型
					qb.must(QueryBuilders.matchQuery("newCardCountType", "32"));
				}else if("Birth".equals(nogName)){//年龄段类型
					qb.must(QueryBuilders.matchQuery("newCardCountType", "33"));
				}else if("Gender".equals(nogName)){//办证性别
					qb.must(QueryBuilders.matchQuery("newCardCountType", "34"));
				}else if("operresult".equals(nogName)){//操作结果
					qb.must(QueryBuilders.matchQuery("newCardCountType", "36"));
				}else if("opertime".equals(nogName)){//操作时间点
					qb.must(QueryBuilders.matchQuery("newCardCountType", "35"));
				}
				break;
			case "finance_log":
				if("Purpose".equals(nogName)){//财经付款用途
					qb.must(QueryBuilders.matchQuery("fineCountType", "21"));
				}else if("Payway".equals(nogName)){//财经付款方式
					qb.must(QueryBuilders.matchQuery("fineCountType", "22"));
				}else if("AuthType".equals(nogName)){//财经读者办证类型
					qb.must(QueryBuilders.matchQuery("fineCountType", "23"));
				}else if("operresult".equals(nogName)){//财经操作结果
					qb.must(QueryBuilders.matchQuery("fineCountType", "24"));
				}else if("Cardtype".equals(nogName)){//财经读者流通类型
					qb.must(QueryBuilders.matchQuery("fineCountType", "25"));
				}
				break;

			default:
				break;
			}
		}
		Map<String, List<String>> newkv = new HashMap<String, List<String>>();
		String[] fullArr =  fullTree.split(";");
		int glen = fullArr.length;//分组的长度
		String[] rootArr = new String[fullArr.length];
		Map<String,Long> result = new HashMap<String,Long>();
		if(termsBuilder !=null){//统计模板
			//添加分组信息
			searchRequestBuilder.addAggregation(termsBuilder);
			SearchResponse scrollResp = searchRequestBuilder.execute().actionGet();//返回的不完整的树
			if(scrollResp.getAggregations()==null){//如果走年月日周的查询不到则返回空
				return null;
			}
			Map<String, Aggregation> aggMap = scrollResp.getAggregations().asMap();
			for(int f=0;f<fullArr.length;f++){
				List<String> v = new ArrayList<String>();
				String[] emptyArr = fullArr[f].split(",");
				rootArr[f] = StatisticsUtils.toStatisticsKey(emptyArr[0],ds);
				for(int m=1;m<emptyArr.length;m++){
					v.add(emptyArr[m]);
				}
				newkv.put(rootArr[f], v);
			}
			if(glen>0){//统计有分组时
				Aggregation aggr = aggMap.get(rootArr[0]);
				String[] arrflag = new String[rootArr.length];
				StatisticsUtils.firstTakeTerms(aggr,rootArr,0,result,arrflag,ds);
			}
				
		}

		String[][] array = new String[rootArr.length][];//全排列的数组
		for(int ra=0;ra<rootArr.length;ra++){
			List<String> list = newkv.get(rootArr[ra]);
			String[] strings = new String[list.size()];
			list.toArray(strings);
			array[ra] = strings;
		}
		String[] num = new String[array.length];
		List<String> ls = new ArrayList<String>();//得到树的全排列
		if(StringUtils.isNotBlank(gTreeArr)){
        	String[] gArr = gTreeArr.split(",");
        	for(String g : gArr){
        		String[] n = g.split(";");
        		ls.add(Arrays.toString(n));
        	}
        }else{
        	StatisticsUtils.fullPermutation(array,array.length, 0, num,ls);
        }
        Map<String,Long> newResult = new HashMap<String,Long>();
        if(map!=null){
/*        	int index = 0;
        	for(int m=0;m<rootArr.length;m++){
        		if("circulateDate".equals(rootArr[m])||"newCardDate".equals(rootArr[m])||"fineDate".equals(rootArr[m])){
        			index  = m;
        		}
        	}*/
	    	if(map.containsKey("y#opertime")){
	    		StatisticsUtils.changeMap(0,newResult,result,"y");
	    	}else if(map.containsKey("m#opertime")){
	    		StatisticsUtils.changeMap(0,newResult,result,"m");
	    	}else if(map.containsKey("d#opertime")){
	    		StatisticsUtils.changeMap(0,newResult,result,"d");
	    	}else if(map.containsKey("w#opertime")){
	    		StatisticsUtils.changeMap(0,newResult,result,"w");
	    	}
	    }
        Map<String, Long> resultTree = new LinkedHashMap<String, Long>();
        if(ngKey.indexOf("|")>-1){
			cArr = ngKey.split("\\|");
        }
        for(String s:ls){
        	Long lVal = 0L;
        	Set<Entry<String, Long>> rs = result.entrySet();
        	if(!newResult.isEmpty()){
        		rs = newResult.entrySet();
        	}
        	Iterator<Entry<String, Long>> it = rs.iterator();
        	while(it.hasNext()){//遍历实际得到的树
        		Entry<String, Long> e = it.next();
        		String key = e.getKey();
        		if(s.equals(key)){
        			lVal = e.getValue();
        		}
        	}
        	if(cArr!=null&&cArr.length>3&&cArr[2]!=null&&cArr[3]!=null){
        		lVal = StatisticsUtils.delFunction(lVal, cArr);
        	}
        	resultTree.put(s, lVal);
        }
        
        List<Map.Entry<String, Long>> infoIds =
        	    new ArrayList<Map.Entry<String, Long>>(resultTree.entrySet());
        //排序
	   /* Collections.sort(infoIds, new Comparator<Map.Entry<String, Long>>() {   
	        public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {      
	            return (o1.getKey()).toString().compareTo(o2.getKey());
	        }
	    });*/
	    return infoIds;
	}
	
	
	/**
	 * 走statistics的查询
	 * author huanghuang
	 * 2017年5月10日 上午10:50:37
	 * @param map
	 * @param ceil
	 * @param qb
	 * @param termsBuilder
	 * @param fullTree
	 * @param tableName
	 * @return
	 */
	public static List<Map.Entry<String, Long>> firstSearchIndex(String ngKey,Map<String,String> map,String ceil,BoolQueryBuilder qb,AbstractAggregationBuilder termsBuilder,String fullTree,/*String[] rootArr,*/ String[] tableName,String indexType,/*Integer libidx,*/List<Integer> libIdxs,String[] grouparr,String ds,Integer operator_type,String gTreeArr) {
		String[] cArr = null;
		if(ceil.indexOf("|")>-1){
			cArr = ceil.split("\\|");
			String queryAttr = cArr[0].trim();
			if(cArr!=null&&cArr.length>1){
				String queryVal = cArr[1].trim();
				if(queryAttr.length()>0&&queryVal.length()>0){
					if(queryVal.indexOf("-")>-1){//有范围
						String[] rangeArr = queryVal.split("-");
						qb.must(QueryBuilders.rangeQuery(queryAttr).from(rangeArr[0]).to(rangeArr[1]).includeLower(true).includeUpper(true));
					}else{//没有范围
						qb.must(QueryBuilders.matchQuery(queryAttr, queryVal));
					}
				}
			}else{
				qb.must(QueryBuilders.existsQuery(cArr[0].trim()));
			}
		}
//		qb.must(QueryBuilders.matchQuery("lib_idx", 163));//查询特定馆
//		qb.must(QueryBuilders.matchQuery("lib_idx", libidx));//查询特定馆
		if(operator_type!=1){
			for(Integer libidx : libIdxs){
				qb.should(QueryBuilders.matchQuery("lib_idx", libidx));//查询特定馆
			}
		}
		SearchRequestBuilder searchRequestBuilder = getClient().prepareSearch(tableName).setTypes(indexType).setQuery(qb);
		if(grouparr!=null){
			//对应statistics表里
			switch (ds) {
			case "loan_log":
				if(StatisticsUtils.isContains(grouparr,"Cardtype")){//读者流通类型
					qb.must(QueryBuilders.matchQuery("circulateCountType", "11"));
				}else if(StatisticsUtils.isContains(grouparr,"Callno")){//图书22大类
					qb.must(QueryBuilders.matchQuery("circulateCountType", "12"));
				}else if(StatisticsUtils.isContains(grouparr,"CurrentLocation")){//图书馆藏地
					qb.must(QueryBuilders.matchQuery("circulateCountType", "13"));
				}else if(StatisticsUtils.isContains(grouparr,"MediaType")){//图书载体类型
					qb.must(QueryBuilders.matchQuery("circulateCountType", "14"));
				}else if(StatisticsUtils.isContains(grouparr,"operresult")){//操作结果
					qb.must(QueryBuilders.matchQuery("circulateCountType", "15"));
				}else if(StatisticsUtils.isContains(grouparr,"AuthType")){//认证类型
					qb.must(QueryBuilders.matchQuery("circulateCountType", "17"));
				}else if(StatisticsUtils.isContains(grouparr,"opertime")){//操作时间点
					qb.must(QueryBuilders.matchQuery("circulateCountType", "16"));
				}
				break;
			case "cardissue_log":
				if(StatisticsUtils.isContains(grouparr,"Cardtype")){//读者流通类型
					qb.must(QueryBuilders.matchQuery("newCardCountType", "31"));
				}else if(StatisticsUtils.isContains(grouparr,"AuthType")){//认证类型
					qb.must(QueryBuilders.matchQuery("newCardCountType", "32"));
				}else if(StatisticsUtils.isContains(grouparr,"Birth")){//年龄段类型
					qb.must(QueryBuilders.matchQuery("newCardCountType", "33"));
				}else if(StatisticsUtils.isContains(grouparr,"Gender")){//办证性别
					qb.must(QueryBuilders.matchQuery("newCardCountType", "34"));
				}else if(StatisticsUtils.isContains(grouparr,"operresult")){//操作结果
					qb.must(QueryBuilders.matchQuery("newCardCountType", "36"));
				}else if(StatisticsUtils.isContains(grouparr,"opertime")){//操作时间点
					qb.must(QueryBuilders.matchQuery("newCardCountType", "35"));
				}
				break;
			case "finance_log":
				if(StatisticsUtils.isContains(grouparr,"Purpose")){//财经付款用途
					qb.must(QueryBuilders.matchQuery("fineCountType", "21"));
				}else if(StatisticsUtils.isContains(grouparr,"Payway")){//财经付款方式
					qb.must(QueryBuilders.matchQuery("fineCountType", "22"));
				}else if(StatisticsUtils.isContains(grouparr,"AuthType")){//财经读者办证类型
					qb.must(QueryBuilders.matchQuery("fineCountType", "23"));
				}else if(StatisticsUtils.isContains(grouparr,"operresult")){//财经操作结果
					qb.must(QueryBuilders.matchQuery("fineCountType", "24"));
				}else if(StatisticsUtils.isContains(grouparr,"Cardtype")){//财经读者流通类型
					qb.must(QueryBuilders.matchQuery("fineCountType", "25"));
				}
				break;

			default:
				break;
			}
		}
		Map<String, List<String>> newkv = new HashMap<String, List<String>>();
		String[] fullArr =  fullTree.split(";");
		int glen = fullArr.length;//分组的长度
		String[] rootArr = new String[fullArr.length];
		Map<String,Long> result = new HashMap<String,Long>();
		if(termsBuilder !=null){//统计模板
			//添加分组信息
			searchRequestBuilder.addAggregation(termsBuilder);
			SearchResponse scrollResp = searchRequestBuilder.execute().actionGet();//返回的不完整的树
			if(scrollResp.getAggregations()==null){
				return null;
			}
			Map<String, Aggregation> aggMap = scrollResp.getAggregations().asMap();
			for(int f=0;f<fullArr.length;f++){
				List<String> v = new ArrayList<String>();
				String[] emptyArr = fullArr[f].split(",");
				String transStr = StatisticsUtils.toStatisticsKey(emptyArr[0],ds);
				boolean flag = ("circulateDate").equals(transStr)||("newCardDate").equals(transStr)||("fineDate").equals(transStr)||transStr.endsWith("_group");
				if(!flag){
					rootArr[f] = transStr+"_group";
        		}else{
        			rootArr[f] =  transStr;
        		}
				//rootArr[f] = StatisticsUtils.toStatisticsKey(emptyArr[0],ds);
				for(int m=1;m<emptyArr.length;m++){
					v.add(emptyArr[m]);
				}
				newkv.put(rootArr[f], v);
			}
			if(glen>0){//统计有分组时
				Aggregation aggr = aggMap.get(rootArr[0]);
				String[] arrflag = new String[rootArr.length];
				StatisticsUtils.firstTakeTerms(aggr,rootArr,0,result,arrflag,ds);
			}
				
		}

		String[][] array = new String[rootArr.length][];//全排列的数组
		for(int ra=0;ra<rootArr.length;ra++){
			List<String> list = newkv.get(rootArr[ra]);
			String[] strings = new String[list.size()];
			list.toArray(strings);
			array[ra] = strings;
		}
		String[] num = new String[array.length];
    	List<String> ls = new ArrayList<String>();//得到树的全排列
    	if(StringUtils.isNotBlank(gTreeArr)){
        	String[] gArr = gTreeArr.split(",");
        	for(String g : gArr){
        		String[] n = g.split(";");
        		ls.add(Arrays.toString(n));
        	}
        }else{
        	StatisticsUtils.fullPermutation(array,array.length, 0, num,ls);
        }
        Map<String,Long> newResult = new HashMap<String,Long>();
        if(map!=null){
        	int index = 0;
        	for(int m=0;m<rootArr.length;m++){
        		if(rootArr[m].indexOf("circulateDate")>-1||rootArr[m].indexOf("newCardDate")>-1||rootArr[m].indexOf("fineDate")>-1){
        			index  = m;
        		}
        	}
	    	if(map.containsKey("y#opertime")&&StatisticsUtils.keyIndexOfDate(rootArr[index])){
	    		StatisticsUtils.changeMap(index,newResult,result,"y");
	    	}else if(map.containsKey("m#opertime")&&StatisticsUtils.keyIndexOfDate(rootArr[index])){
	    		StatisticsUtils.changeMap(index,newResult,result,"m");
	    	}else if(map.containsKey("d#opertime")&&StatisticsUtils.keyIndexOfDate(rootArr[index])){
	    		StatisticsUtils.changeMap(index,newResult,result,"d");
	    	}else if(map.containsKey("w#opertime")&&StatisticsUtils.keyIndexOfDate(rootArr[index])){
	    		StatisticsUtils.changeMap(index,newResult,result,"w");
	    	}
	    }
  
        Map<String, Long> resultTree = new LinkedHashMap<String, Long>();
        if(ngKey.indexOf("|")>-1){
			cArr = ngKey.split("\\|");
        }
        for(String s:ls){
        	Long lVal = 0L;
        	Set<Entry<String, Long>> rs = result.entrySet();
        	if(!newResult.isEmpty()){
        		rs = newResult.entrySet();
        	}
        	Iterator<Entry<String, Long>> it = rs.iterator();
        	while(it.hasNext()){//遍历实际得到的树
        		Entry<String, Long> e = it.next();
        		String key = e.getKey();
        		if(s.equals(key)){
        			lVal = e.getValue();
        		}
        	}
        	if(cArr!=null&&cArr.length>3&&cArr[2]!=null&&cArr[3]!=null){
        		lVal = StatisticsUtils.delFunction(lVal, cArr);
        	}
        	resultTree.put(s, lVal);
        }
        
        List<Map.Entry<String, Long>> infoIds =
        	    new ArrayList<Map.Entry<String, Long>>(resultTree.entrySet());
        //排序
/*	    Collections.sort(infoIds, new Comparator<Map.Entry<String, Long>>() {   
	        public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {      
	            return (o1.getKey()).toString().compareTo(o2.getKey());
	        }
	    });*/
	    return infoIds;
	}
	/**
	 * 直接走elachseach
	 * author huanghuang
	 * 2017年5月10日 上午10:51:59
	 * @param req
	 * @return
	 */
	public ResultEntity subStatistics(String req) {
		ResultEntity result = new ResultEntity();
		String[] datasource = null;//要查的elachseach索引
		List<List<Map.Entry<String, Long>>> infoIds = new ArrayList<List<Map.Entry<String, Long>>>();//统计结果值
		JSONObject json = JSONObject.fromObject(req);
		String gTreeArr = json.getString("gTreeArr");//传递树的组合
		//获得对应馆的所有设备 start
		Integer libidx = json.getInt("library_idx");//馆idx
		String libId = json.getString("lib_id").toLowerCase();//馆id
        Integer operator_type = json.getInt("operator_type");//操作员类型
        String group = json.getString("group");//分组
        String s = json.getString("gcondition");//组条件
        JSONObject condition = json.getJSONObject("condition");//一般条件
        String nogroup = json.getString("nogroup").trim();//不分组的字段
        String ds = json.getString("datasource");//数据源
        int topN = json.optInt("topHits",StatisConstant.STATIC_TOPN);//TopN
		//获取当前图书馆的子馆 start
		List<Integer> libIdxs = new ArrayList<Integer>();//图书馆idx
		libIdxs.add(libidx);
		List<String> libIds = new ArrayList<String>();//图书馆id
		libIds.add(libId);
		List<RelLibsEntity> list =  new ArrayList<RelLibsEntity>();
		String isContainSubLib = json.optString("isContainSubLib");//0:包含子馆；1：不含子馆
		if("0".equals(isContainSubLib)){
			list = takeRelLibs(libidx);
		}
		if(!CollectionUtils.isEmpty(list)){
			for(RelLibsEntity rel : list){
				Integer tmpidx = rel.getSlave_lib_id();
				JSONObject libJson = takeLib(tmpidx);
				if(libJson.get("lib_id")!=null){
					libIdxs.add(libJson.getInt("library_idx"));
					libIds.add(libJson.getString("lib_id").toLowerCase());
				}
			}
		}
		//获取当前图书馆的子馆 start
//		String[] datasource = null;//要查的elachseach索引
		StringBuffer sb = new StringBuffer();
		if(operator_type == 1){
		    sb.append("*_"+ds+",");
        }else{
            for(int tmp=0;tmp<libIdxs.size();tmp++){
            	sb.append(libIds.get(tmp) + "_*_"+ds+",");
                /*Integer libIdx = libIdxs.get(tmp);
                JSONArray devJsonArr = takeDev(libIdx);
                if(devJsonArr!=null){
                    for (int d = 0; d < devJsonArr.size(); d++) {
                    }
                }*/
            }
        }

		if(sb.toString().endsWith(",")){
			sb.deleteCharAt(sb.length()-1);
		}
		datasource = sb.toString().split(",");
//		JSONArray devJsonArr = takeDev(libidx);
//		if(devJsonArr!=null){
//			datasource = new String[devJsonArr.size()];
//			for (int d = 0; d < devJsonArr.size(); d++) {
//				datasource[d] = libId + "_" + devJsonArr.getJSONObject(d).getString("device_id").replaceAll("#", "").toLowerCase()+"_"+json.getString("datasource");
//			}
//		}
		//获得对应馆的所有设备 end
		LogUtils.info("(走elachseach)当前馆要查询的elachseach的索引名称为######"+Arrays.toString(datasource)+"#####");
		String[] grouparr = group.split(",");
		Map<String,String> map =null;
		if(!s.equals("{}")){
			JSONObject gcondition = JSONObject.fromObject(s);
			map = StatisticsUtils.JsonToMap(gcondition);
		}
		String gstr = commonEsStatisticService.gtreeForReader(datasource,grouparr,map,null,false,condition,topN).toString();
		String[] gstrArr = gstr.split(";");
		int gIndex = 0;//如果是第一层树就没有数据，则不再进行统计
		for(int gs=0;gs<gstrArr.length;gs++){
			String[] tmpArr = gstrArr[gs].split(",");
			if(tmpArr.length==1){
				break;
			}
			gIndex = gIndex+1;
		}
		if(gIndex==0){//如果是第一层树就没有数据，则不再进行统计
			return result;
		}
		String[] aggArr = new String[gIndex];
		for(int g1=0;g1<gIndex;g1++){
			aggArr[g1] = grouparr[g1];
		}
		int sIndex = gstr.length();
		if(gIndex!=gstrArr.length)
			sIndex = StatisticsUtils.getCharacterPosition(gstr, gIndex);
		String newGstr = gstr.substring(0, sIndex);
		AbstractAggregationBuilder termsBuilder = null;
		String[] nogrouparr = null;
		if(StringUtils.isNotBlank(nogroup)){//不分组的数据不为空时，不分组
			nogrouparr = nogroup.split(",");
		}
		if(nogrouparr!=null){
			for(int ng=0;ng<nogrouparr.length;ng++){//根据不是分组的查询数据
				String[] funValArr =  nogrouparr[ng].split("\\|");
				String funVal = "";
				if(funValArr!=null&&funValArr.length>2){
					funVal = funValArr[2].trim();//得到要处理的数值函数名
				}
				@SuppressWarnings("rawtypes")
				List<MetricsAggregationBuilder> subAggList = null;
				if(StringUtils.isNotBlank(funVal)){//组装数值函数
					subAggList = new ArrayList<>();
					if("sum".equals(funVal)){
						subAggList.add(AggregationBuilders.sum(funValArr[0]).field(funValArr[0]));
					}else if("average".equals(funVal)){
						subAggList.add(AggregationBuilders.avg(funValArr[0]).field(funValArr[0]));
					}else if("max".equals(funVal)){
						subAggList.add(AggregationBuilders.max(funValArr[0]).field(funValArr[0]));
					}else if("min".equals(funVal)){
						subAggList.add(AggregationBuilders.min(funValArr[0]).field(funValArr[0]));
					}else if("count".equals(funVal)){
						if(!funValArr[0].endsWith("_group")){
							subAggList.add(AggregationBuilders.count(funValArr[0]).field(funValArr[0]+"_group"));
						}else{
							subAggList.add(AggregationBuilders.count(funValArr[0]).field(funValArr[0]));
						}
					}
				}
				@SuppressWarnings("rawtypes")
				AggregationBuilder[] aggregationBuilders = StatisticsUtils.aggGroup(aggArr,map,topN);
				StatisticsUtils.assembleAgg(aggregationBuilders, aggregationBuilders.length-1,subAggList);//组装分组
				termsBuilder = aggregationBuilders[0];//得到要分组的参数
				BoolQueryBuilder bqb = getBQB(condition,ds);//组装查询条件
				List<Map.Entry<String, Long>> rInfo = null;
				try{
					rInfo = subSearchIndex(map,nogrouparr[ng],bqb,termsBuilder,newGstr,datasource,gTreeArr);
				}catch(IndexNotFoundException e){//走elachseach统计时，找不到elachseach的index
					LogUtils.error("走elachseach统计时，找不到elachseach的index",e);
				}
				infoIds.add(rInfo);
			}
		}
		LogUtils.info(ds+"################################统计是elachseach的index查询后的结果##################################");
		result.setResult(infoIds);
		result.setState(true);
		return result;
	
	}
	
	/**
	 * 组装statistics查询条件
	 */
	public static BoolQueryBuilder statisticsCondition(JSONObject json,String flag,String ds){
		BoolQueryBuilder bqb = QueryBuilders.boolQuery();
		if(json !=null && json.size()>0){
			Map<String,String> result = StatisticsUtils.JsonToMapNotCaseInsensitiveMap(json);
			for(Map.Entry<String,String> entry : result.entrySet()){
				//String key=entry.getKey();
				String key = StatisticsUtils.toStatisticsKey(entry.getKey(),ds);
				String value = entry.getValue();
				if(value!=null&&value.indexOf(",") !=-1){
				    bqb.must(QueryBuilders.termsQuery(key+"_group",value.split(",")));
				}else if(value!=null&&value.indexOf("~") !=-1){
					String[] arr = value.split("~");
					String arr1 = null;
					String arr2 = null;
					if("circulateDate".equals(key)||"newCardDate".equals(key)||"fineDate".equals(key)){
						arr1 = StatisticsUtils.subDateStr(arr[0].trim().replaceAll("-", ""), flag);
						if(arr!=null&&arr.length>1){
							arr2 = StatisticsUtils.subDateStr(arr[1].trim().replaceAll("-", ""), flag);
						}
					}
					if(arr1 ==null || arr1.length() <=0){
						bqb.must(QueryBuilders.rangeQuery(key).to(arr2).includeLower(true).includeUpper(true));
					}else if(arr2 ==null || arr2.length() <=0){
						bqb.must(QueryBuilders.rangeQuery(key).from(arr1).includeLower(true).includeUpper(true));
					}else{
						bqb.must(QueryBuilders.rangeQuery(key).from(arr1).to(arr2).includeLower(true).includeUpper(true));
					}
				}else{
				    if(value !=null && value.length() >0){
				        if(value.indexOf("#box") !=-1){
                            bqb.must(QueryBuilders.matchPhraseQuery(key+"_group",value.split("#")[0]));
                        }else{
                            bqb.must(QueryBuilders.matchPhraseQuery(key,value));
                        }
                    }

				}
			}
		}
		return bqb;
		
		/*BoolQueryBuilder bqb = QueryBuilders.boolQuery();
		if(json !=null && json.size()>0){
			Map<String,String> result = StatisticsUtils.JsonToMap(json);
			for(Map.Entry<String,String> entry : result.entrySet()){
				String key=StatisticsUtils.toStatisticsKey(entry.getKey(),ds);
				String value = entry.getValue();
				if(value.indexOf(",") !=-1){
				    bqb.must(QueryBuilders.termsQuery(key,value.split(",")));
				}else if(value.indexOf("~") !=-1){
					String[] arr = value.split("~");
					if("circulateDate".equals(key)||"newCardDate".equals(key)||"fineDate".equals(key)){
						arr[0] = StatisticsUtils.subDateStr(arr[0].replaceAll("-", ""), flag);
						arr[1] = StatisticsUtils.subDateStr(arr[1].replaceAll("-", ""), flag);
					}
					bqb.must(QueryBuilders.rangeQuery(key).from(arr[0]).to(arr[1]).includeLower(true).includeUpper(true));
				}else{
					bqb.must(QueryBuilders.termsQuery(key,value));
				}
			}
		}
		return bqb;*/
	}
	/**
	 * 直接走elachseach
	 * author huanghuang
	 * 2017年5月10日 上午10:52:50
	 * @param map
	 * @param ceil
	 * @param qb
	 * @param termsBuilder
	 * @param fullTree
	 * @param tableName
	 * @return
	 */
	public static List<Map.Entry<String, Long>> subSearchIndex(Map<String,String> map,String ceil,BoolQueryBuilder qb,AbstractAggregationBuilder termsBuilder,String fullTree,String[] tableName,String gTreeArr) {
		String[] cArr = null;
		String aggArr = "";//数值函数的聚合属性
		if(ceil.indexOf("|")>-1){
			cArr = ceil.split("\\|");
			if(cArr!=null&&cArr.length>1){
				if(cArr.length>2&&cArr[2].trim().length()>0&&StatisticsUtils.isAggGroup(cArr[2].trim())){
					aggArr = cArr[0].trim();
				}
				String queryAttr = cArr[0].trim();
				String queryVal = cArr[1].trim();
				if(queryAttr.length()>0&&queryVal.length()>0){
					if("Birth".equalsIgnoreCase(queryAttr)){//出生年月特殊处理
						/*Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
						String curDate = sdf.format(date);*/
						Integer startInt = 0;
						Integer endInt = 0;
						if(queryVal.indexOf("-")>-1){//有年龄范围
							String[] ageArr = queryVal.split("-");
							if(StatisticsUtils.strIsNum(ageArr[0])){
								startInt = Integer.parseInt(ageArr[0]);
							}
							if(StatisticsUtils.strIsNum(ageArr[1])){
								endInt = Integer.parseInt(ageArr[1]);
							}
						}else{//没有年龄范围
							if(StatisticsUtils.strIsNum(queryVal)){
								endInt = Integer.parseInt(queryVal);
							}
						}
//						qb.must(QueryBuilders.rangeQuery(queryAttr).format("yyyy").from(Integer.parseInt(curDate)-endInt).to(Integer.parseInt(curDate)-startInt).includeLower(true).includeUpper(true));
						qb.must(QueryBuilders.rangeQuery("peopleAge").from(startInt).to(endInt).includeLower(true).includeUpper(true));
					}else if("Callno".equalsIgnoreCase(queryAttr)){
						qb.must(QueryBuilders.matchQuery("callNumber", queryVal));
					}else{
						qb.must(QueryBuilders.matchQuery(queryAttr, queryVal));
					}
				}
			}else{
				qb.must(QueryBuilders.existsQuery(cArr[0].trim()));
			}
		}
		SearchRequestBuilder searchRequestBuilder = getClient().prepareSearch(tableName).setQuery(qb);
		Map<String, List<String>> newkv = new HashMap<String, List<String>>();
		String[] fullArr =  fullTree.split(";");
		int glen = fullArr.length;//分组的长度
		String[] rootArr = new String[fullArr.length];
		Map<String,Long> result = new HashMap<String,Long>();
		if(termsBuilder !=null){//统计模板 modify by huanghuang
			//添加分组信息
			searchRequestBuilder.addAggregation(termsBuilder);
			SearchResponse scrollResp = searchRequestBuilder.execute().actionGet();//返回的不完整的树
			if(scrollResp.getAggregations()==null){
				return null;
			}
			Map<String, Aggregation> aggMap = scrollResp.getAggregations().asMap();
			for(int f=0;f<fullArr.length;f++){
				List<String> v = new ArrayList<String>();
				String[] emptyArr = fullArr[f].split(",");
				rootArr[f] = emptyArr[0];
				for(int m=1;m<emptyArr.length;m++){
					v.add(emptyArr[m]);
				}
				String transStr = emptyArr[0];
				boolean flag = StatisticsUtils.judgeDate(transStr);
				if("Callno".equalsIgnoreCase(transStr)){
					if(map!=null&&map.get("Callno")!=null){
						transStr = "callNumber";
					}else{
						transStr = transStr+"_group";
					}
				}else if("Birth".equalsIgnoreCase(transStr)){
					if(map!=null&&map.get("Birth")!=null){
						transStr = "peopleAge";
					}else{
						transStr = transStr+"_group";
					}
				}else if(!flag){
					transStr = transStr+"_group";
        		}
				newkv.put(transStr, v);
			}
			if(glen>0){//统计有分组时
				String gtmp = rootArr[0];
				if("Callno".equalsIgnoreCase(gtmp)){
					if(map!=null&&map.get("Callno")!=null){
						gtmp = "callNumber";
					}else{
						gtmp += "_group";
					}
				}else if("Birth".equalsIgnoreCase(gtmp)){
					if(map!=null&&map.get("Birth")!=null){
						gtmp = "peopleAge";
					}else{
						gtmp += "_group";
					}
				} else if(!StatisticsUtils.judgeDate(gtmp)){
					gtmp += "_group";
				}
				Aggregation aggr = aggMap.get(gtmp);
				String[] arrflag = new String[rootArr.length];
				StatisticsUtils.takeTerms(aggr,rootArr,0,result,arrflag,aggArr,map);
			}
			System.out.println("statistics查询后的result数据："+result.toString());
			LogUtils.info("statistics查询后的result数据："+result.toString());
				
		}
//		String[][] tarray = new String[rootArr.length][];//全排列的数组
//		for(int ra=0;ra<rootArr.length;ra++){
//			List<String> list = newkv.get(rootArr[ra]);
//			String[] strings = new String[list.size()];
//			list.toArray(strings);
//			tarray[ra] = strings;
//		}
//		int i = 0;
//		for(int t=0;t<tarray.length;t++){
//			if(tarray[t].length!=0){
//				i = i+1;
//			}
//		}
		String[][] array = new String[rootArr.length][];//全排列的数组
		for(int ra=0;ra<rootArr.length;ra++){
			List<String> list = newkv.get(rootArr[ra]);
//			if(list.size()>0){
				String[] strings = new String[list.size()];
				list.toArray(strings);
				array[ra] = strings;
//			}else{
//				break;
//			}
		}
		String[] num = new String[array.length];
    	List<String> ls = new ArrayList<String>();//得到树的全排列
        if(StringUtils.isNotBlank(gTreeArr)){
        	String[] gArr = gTreeArr.split(",");
        	for(String g : gArr){
        		String[] n = g.split(";");
        		ls.add(Arrays.toString(n));
        	}
        }else{
        	StatisticsUtils.fullPermutation(array,array.length, 0, num,ls);
        }
        Map<String,Long> newResult = new HashMap<String,Long>();
        if(map!=null){
        	int index = 0;
        	for(int m=0;m<rootArr.length;m++){
        		if(StatisticsUtils.judgeDate(rootArr[m])){
        			index  = m;
        		}
        	}
	    	if(StatisticsUtils.mapContainsDate(map, "t")&&StatisticsUtils.keyIndexOfDate(rootArr[index])){
	    		StatisticsUtils.changeMap(index,newResult,result,"t");
	    	}else if(StatisticsUtils.mapContainsDate(map, "y")&&StatisticsUtils.keyIndexOfDate(rootArr[index])){
	    		StatisticsUtils.changeMap(index,newResult,result,"y");
	    	}else if(StatisticsUtils.mapContainsDate(map, "m")&&StatisticsUtils.keyIndexOfDate(rootArr[index])){
	    		StatisticsUtils.changeMap(index,newResult,result,"m");
	    	}else if(StatisticsUtils.mapContainsDate(map, "d")&&StatisticsUtils.keyIndexOfDate(rootArr[index])){
	    		StatisticsUtils.changeMap(index,newResult,result,"d");
	    	}else if(StatisticsUtils.mapContainsDate(map, "w")&&StatisticsUtils.keyIndexOfDate(rootArr[index])){
	    		StatisticsUtils.changeMap(index,newResult,result,"w");
	    	}
	    }
        Map<String, Long> resultTree = new LinkedHashMap<String, Long>();
        for(String s : ls){
        	Long lVal = 0L;
        	Set<Entry<String, Long>> rs = result.entrySet();
        	if(!newResult.isEmpty()){
        		rs = newResult.entrySet();
        	}
        	Iterator<Entry<String, Long>> it = rs.iterator();
        	while(it.hasNext()){//遍历实际得到的树
        		Entry<String, Long> e = it.next();
        		String key = e.getKey();
        		if(s.equals(key)){
        			lVal = e.getValue();
        		}
        	}
        	if(cArr!=null&&cArr.length>3&&cArr[2]!=null&&cArr[3]!=null){
        		lVal = StatisticsUtils.delFunction(lVal, cArr);
        	}
        	resultTree.put(s, lVal);
        }
        
        List<Map.Entry<String, Long>> infoIds =
        	    new ArrayList<Map.Entry<String, Long>>(resultTree.entrySet());
        //排序
	   /* Collections.sort(infoIds, new Comparator<Map.Entry<String, Long>>() {   
	        public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {      
	            return (o1.getKey()).toString().compareTo(o2.getKey());
	        }
	    });*/
		LogUtils.info("statistics查询后的infoIds数据："+infoIds.toString());
		System.out.println("statistics查询后的infoIds数据："+infoIds.toString());
	    return infoIds;
	}
	
	@Override
	public String exportSelect(String req) {

		JSONObject json = JSONObject.fromObject(req);
//		int pageNo=1;
//		int pageSize=10;
//		if(json.containsKey("pageNo")){
//			pageNo=json.getInt("pageNo");
//		}
//		if(json.containsKey("pageSize")){
//			pageSize=json.getInt("pageSize");
//		}
		JSONObject functions=null;
		if(json.containsKey("functions")){
			if(json.get("functions") !=null && json.get("functions") !="{}"){
				functions=json.getJSONObject("functions");
			}
		}
		//获得对应馆的所有设备 start
		Integer libidx = json.getInt("library_idx");
		String libId = json.getString("lib_id").toLowerCase();
        Integer operator_type = json.getInt("operator_type");
        int exportpage = 1;
        if(json.containsKey("exportpage")){
            exportpage = json.getInt("exportpage");
        }
        int topN = json.optInt("topHits",StatisConstant.STATIC_TOPN);//TopN
		//获取当前图书馆的子馆 start
		List<Integer> libIdxs = new ArrayList<Integer>();//图书馆idx
		libIdxs.add(libidx);
		List<String> libIds = new ArrayList<String>();//图书馆id
		libIds.add(libId);
		List<RelLibsEntity> list = takeRelLibs(libidx);
		if(list!=null){
			for(RelLibsEntity rel : list){
				Integer tmpidx = rel.getSlave_lib_id();
				JSONObject libJson = takeLib(tmpidx);
				if(libJson.get("lib_id")!=null){
					libIdxs.add(libJson.getInt("library_idx"));
					libIds.add(libJson.getString("lib_id").toLowerCase());
				}
			}
		}
		//获取当前图书馆的子馆 start
//		String[] datasource = null;//要查的elachseach索引
		StringBuffer sb = new StringBuffer();
		String[] datasourceArr = null;
		if(operator_type == 1){
		    sb.append("*_"+json.getString("datasource")+",");
        }else{
            for(int tmp=0;tmp<libIdxs.size();tmp++){
            	sb.append(libIds.get(tmp) + "_*_"+json.getString("datasource")+",");
                /*Integer libIdx = libIdxs.get(tmp);
                JSONArray devJsonArr = takeDev(libIdx);
                if(devJsonArr!=null){
                    for (int d = 0; d < devJsonArr.size(); d++) {
                    }
                }*/
            }
        }

		if(sb.toString().endsWith(",")){
			sb.deleteCharAt(sb.length()-1);
		}

		datasourceArr = sb.toString().split(",");
//		JSONArray devJsonArr = takeDev(libidx);
//		String[] datasourceArr = null;
//		if(devJsonArr!=null){
//			datasourceArr = new String[devJsonArr.size()];
//			for (int d = 0; d < devJsonArr.size(); d++) {
//				datasourceArr[d] = libId + "_" + devJsonArr.getJSONObject(d).getString("device_id").replaceAll("#", "").toLowerCase()+"_"+json.getString("datasource");
//			}
//		}
		//获得对应馆的所有设备 end
//		String datasource = "hhtest_sch_001_"+json.getString("datasource");
//		String datasource = "hhtest_sch001_loan_testlog";
		String group = json.getString("group");
		String[] grouparr = group.split(",");
		String s = json.getString("gcondition");
		Map<String,String> map =null;
		if(!s.equals("{}")){
			JSONObject gcondition = JSONObject.fromObject(s);
			map = StatisticsUtils.JsonToMapNotCaseInsensitiveMap(gcondition);
		}
		JSONObject condition = json.getJSONObject("condition");//一般条件
		String gstr = gtree(datasourceArr,grouparr,map,functions,false,condition,topN).toString();
		String[] garr = gstr.split(";");
		String nogroup = json.getString("nogroup")+","+group;
		String[] nogrouparr = nogroup.split(",");
		BoolQueryBuilder bqb = getBQB(condition,json.getString("datasource"));
		if(map !=null){
			for(Map.Entry<String,String> entry : map.entrySet()){
				String key=entry.getKey();
				if(entry.getValue() !=null && !entry.getValue().isEmpty()){
					String[] value = entry.getValue().split(",");
					boolean existDate = StatisticsUtils.keyIndexOfDate(key);
					if(existDate){
						
					}else{
						bqb.must(QueryBuilders.termsQuery(key,value));
					}
				}
			}
		}
		QueryBuilder qb = bqb;
        if(json.containsKey("logoid")){
        	String sl = json.getString("logoid");
        	String[] logoid = sl.split(";");
        	int num = locationData(qb, null, nogrouparr, garr, datasourceArr, logoid,functions);
			return num+"";
		}
        int size = 1000;
		//JSONObject result = exportSearchIndex(qb,null,nogrouparr,garr,/*(pageNo-1)*pageSize,pageSize,*/datasourceArr,functions,exportpage);
        JSONObject result = searchIndex(qb,null,nogrouparr,garr,exportpage,size,datasourceArr,functions);
		return result.toString();
	
	}

	/*private JSONObject exportSearchIndex(QueryBuilder qb, Object object,
			String[] nogrouparr, String[] garr, *//*int pageNo, int pageSize,*//*
			String[] tableName,JSONObject functions,int exportpage) {
	    int size = 9999;
	    int page = 0;
	    if(exportpage >0){
	        page = exportpage;
        }
		JSONObject json = new JSONObject();
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		SearchRequestBuilder searchRequestBuilder = getClient().prepareSearch(tableName).setQuery(qb);
		for(int i=0,z=garr.length;i<z;i++){
        	String[] garr2 = garr[i].split(",");
        	searchRequestBuilder.addSort(garr2[0], SortOrder.ASC);
		}
		if(nogrouparr !=null && nogrouparr.length>0){
			searchRequestBuilder.addFields(nogrouparr);
		}
		//执行搜索
		SearchResponse scrollResp = searchRequestBuilder.setFrom(page).setSize(size).execute().actionGet();
		Map<String, Object> map = new HashMap<String, Object>();
		sb.append("\"total\":{");
		long total = scrollResp.getHits().getTotalHits();
		sb.append("\"total\"").append(":").append("\""+total+"\"");
		sb.append("},");
		sb.append("\"result\":[");
		 for(final SearchHit hit:scrollResp.getHits()){
		   sb.append("{");
           final Iterator<SearchHitField> iterator = hit.iterator(); 
           while(iterator.hasNext()){
	          final SearchHitField hitfield = iterator.next();
	          map.put(hitfield.getName(),hitfield.getValue());
	          String strval =hitfield.getValue().toString();
              if(functions !=null){
            	  if(functions.containsKey(hitfield.getName())){
            		  JSONObject jf = functions.getJSONObject(hitfield.getName());
            		  String kString = jf.getString("key");
            		  String vaString = jf.getString("value");
            		  strval = verFunction(hitfield.getValue().toString(),kString,vaString);
            	  }
              }
              sb.append("\""+hitfield.getName()+"\"").append(":").append("\""+strval+"\"");
              sb.append(",");
            }
           if(sb.substring(sb.length()-1).equals(",")){
  			 sb.deleteCharAt(sb.length()-1);
  		   }
           sb.append("},");
		}
		 if(sb.substring(sb.length()-1).equals(",")){
			 sb.deleteCharAt(sb.length()-1);
		}
	    sb.append("]}");
	    json = JSONObject.fromObject(sb.toString());
	   return json;
	
	
	}*/

	@Override
	public String query(String req) {
		JSONObject json = JSONObject.fromObject(req);
		int pageNo=1;
		int pageSize=10;
		if(json.containsKey("pageNo")){
			pageNo=json.getInt("pageNo");
		}
		if(json.containsKey("pageSize")){
			pageSize=json.getInt("pageSize");
		}
		JSONObject functions=null;
		if(json.containsKey("functions")){
			if(json.get("functions") !=null && json.get("functions") !="{}"){
				functions=json.getJSONObject("functions");
			}
		}
		//获得对应馆的所有设备 start
		Integer libidx = json.getInt("library_idx");
		String libId = json.getString("lib_id").toLowerCase();
        Integer operator_type = json.getInt("operator_type");
        
		//获取当前图书馆的子馆 start
		List<Integer> libIdxs = new ArrayList<Integer>();//图书馆idx
		libIdxs.add(libidx);
		List<String> libIds = new ArrayList<String>();//图书馆id
		libIds.add(libId);
		List<RelLibsEntity> list = takeRelLibs(libidx);
		if(list!=null){
			for(RelLibsEntity rel : list){
				Integer tmpidx = rel.getSlave_lib_id();
				JSONObject libJson = takeLib(tmpidx);
				if(libJson.get("lib_id")!=null){
					libIdxs.add(libJson.getInt("library_idx"));
					libIds.add(libJson.getString("lib_id").toLowerCase());
				}
			}
		}
		//获取当前图书馆的子馆 start
//		String[] datasource = null;//要查的elachseach索引
        String[] datasourceArr = null;
        StringBuffer sb = new StringBuffer();
        // 读者维度查询
        boolean isReaderSearch = false;
		if (null != json && !org.springframework.util.StringUtils.isEmpty(json
				.getString("datasource"))
				&& json.getString("datasource").endsWith(
						"reader_circulation_log")) {
			isReaderSearch =true; 
		}
		
        if(operator_type == 1){
            sb.append("*_"+json.getString("datasource")+",");
        }else{
        	// 读者维度查询
    		if (isReaderSearch) {
    			for(int tmp=0;tmp<libIdxs.size();tmp++){
                	sb.append(libIds.get(tmp) + "*_"+json.getString("datasource")+",");
                }
    		}else {
    			
    			for(int tmp=0;tmp<libIdxs.size();tmp++){
    				sb.append(libIds.get(tmp) + "_*_"+json.getString("datasource")+",");
    				/*Integer libIdx = libIdxs.get(tmp);
                JSONArray devJsonArr = takeDev(libIdx);
                if(devJsonArr!=null){
                    for (int d = 0; d < devJsonArr.size(); d++) {
                    }
                }*/
    			}
    		}
        }
        if(sb.toString().endsWith(",")){
            sb.deleteCharAt(sb.length()-1);
        }
        datasourceArr = sb.toString().split(",");
//		JSONArray devJsonArr = takeDev(libidx);
//		String[] datasourceArr = null;
//		if(devJsonArr!=null){
//			datasourceArr = new String[devJsonArr.size()];
//			for (int d = 0; d < devJsonArr.size(); d++) {
//				datasourceArr[d] = libId + "_" + devJsonArr.getJSONObject(d).getString("device_id").replaceAll("#", "").toLowerCase()+"_"+json.getString("datasource");
//			}
//		}
		//获得对应馆的所有设备 end
//		String datasource = "hhtest_sch_001_"+json.getString("datasource");
		String group = "";
		String[] grouparr = null;
		if(json.getString("group") !=null && json.getString("group").length() >0){
			group = json.getString("group");
			grouparr = group.split(",");
		}
		String s = json.getString("gcondition");
		Map<String,String> map =null;
		if(!s.equals("{}")){
			JSONObject gcondition = json.getJSONObject("gcondition");
			map = StatisticsUtils.JsonToMapNotCaseInsensitiveMap(gcondition);
		}
		String[] garr = null;
		String gstr = "";
		JSONObject condition = json.getJSONObject("condition");//一般条件
		int topN = json.optInt("topHits",StatisConstant.STATIC_TOPN);//TopN
		// 读者维度查询
		if (isReaderSearch) {
			gstr = commonEsStatisticService.gtreeForReader(datasourceArr,grouparr,map,functions,false,condition,topN).toString();
		}else {
			
			gstr = gtree(datasourceArr,grouparr,map,functions,false,condition,topN).toString();
		}
		if(!org.springframework.util.StringUtils.isEmpty(gstr)){
			garr = gstr.split(";");
		}
		String nogroup = json.getString("nogroup")+","+group;
		String[] nogrouparr = nogroup.split(",");
		BoolQueryBuilder bqb = getBQB(condition,json.getString("datasource"));
		if(map !=null){
			for(Map.Entry<String,String> entry : map.entrySet()){
				String key=entry.getKey();
				if(entry.getValue() !=null && !entry.getValue().isEmpty()){
					String[] value = entry.getValue().split(",");
					if(value !=null && value.length >0){
						boolean existDate = StatisticsUtils.keyIndexOfDate(key);
						if(existDate){
							
						}else{
							bqb.must(QueryBuilders.termsQuery(key+"_group",value));
						}
					}
				}
			}
		}
		
		QueryBuilder qb = bqb;
        if(json.containsKey("logoid")){
        	String sl = json.getString("logoid");
        	String[] logoid = sl.split(";");
        	int num = locationData(qb, null, nogrouparr, garr, datasourceArr, logoid,functions);
			return num+"";
		}
		JSONObject result = searchIndex(qb,null,nogrouparr,garr,pageNo,pageSize,datasourceArr,functions);
		return result.toString();
	}
	/**
	 * 获取查询模板数据
	 * @param qb
	 * @param tableName
	 */
	public static JSONObject searchIndex(QueryBuilder qb,TermsBuilder termsBuilder,String[] nogrouparr,String[] garr,int pageNo,int pageSize,String[] tableName,JSONObject functions) {
		JSONObject json = new JSONObject();
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		//System.out.println("=========滚动搜索数据,qb：" + qb + "======,tableName:" + Arrays.toString(tableName));setPostFilter
		//SearchRequestBuilder searchRequestBuilder = getClient().prepareSearch(tableName).setQuery(qb);
		SearchRequestBuilder searchRequestBuilder = getClient().prepareSearch(tableName).setPostFilter(qb);
		if(garr !=null && garr.length>0){
			for(int i=0,z=garr.length;i<z;i++){
	        	String[] garr2 = garr[i].split(",");
	        	searchRequestBuilder.addSort(garr2[0], SortOrder.ASC);
			}
		}
		if(nogrouparr !=null && nogrouparr.length>0){
			searchRequestBuilder.addFields(nogrouparr);
		}
		//执行搜索
		int scrollSize = 10000;
        int size = pageNo * pageSize;
        Long start = System.currentTimeMillis();
        SearchResponse scrollResp = searchRequestBuilder.setSize(scrollSize).setSearchType(SearchType.QUERY_THEN_FETCH).setScroll(new TimeValue(30000)).execute().actionGet();
        System.out.println("======elasticsearch,耗时：" + (System.currentTimeMillis()-start)+ "ms");
		int begIndex = (pageNo-1)*pageSize;
        int total = (int)scrollResp.getHits().getTotalHits();
        int sliceNum = size%scrollSize==0?(size/scrollSize):(size/scrollSize)+1;
        
        Map<String, Object> map = new HashMap<String, Object>();
        sb.append("\"total\":{");
        sb.append("\"total\"").append(":").append("\""+total+"\"");
        sb.append("},");
        sb.append("\"result\":[");
        start = System.currentTimeMillis();
        
        System.out.println("======sliceNum:" + sliceNum);
        
        for (int n = 1;n < sliceNum;n++){
        	
        	if(begIndex  > n * scrollSize){
        		scrollResp = getClient().prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(30000)).execute().actionGet();
        	}
        }
        System.out.println("======elasticsearch,滚动耗时：" + (System.currentTimeMillis()-start) + "ms");
        int t = (sliceNum-1) * scrollSize;
        start = System.currentTimeMillis();
        for(final SearchHit hit:scrollResp.getHits()){
        	 if(t >= begIndex){
                 sb.append("{");
                 final Iterator<SearchHitField> iterator = hit.iterator();
                 while(iterator.hasNext()){
                     final SearchHitField hitfield = iterator.next();
                     map.put(hitfield.getName(),hitfield.getValue());
                     String strval =hitfield.getValue().toString();
                     if(functions !=null){
                         if(functions.containsKey(hitfield.getName())){
                             JSONObject jf = functions.getJSONObject(hitfield.getName());
                             String kString = jf.getString("key");
                             String vaString = jf.getString("value");
                             strval = verFunction(hitfield.getValue().toString(),kString,vaString);
                         }
                     }
                     sb.append("\""+hitfield.getName()+"\"").append(":").append("\""+strval+"\"");
                     sb.append(",");
                 }
                 if(sb.substring(sb.length()-1).equals(",")){
                     sb.deleteCharAt(sb.length()-1);
                 }
                 sb.append("},");

         }
         t+=1;
         if(t > (pageSize*pageNo-1)){
             break;
         }
        }
        
		if (sb.substring(sb.length() - 1).equals(",")) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]}");
		json = JSONObject.fromObject(sb.toString());

        System.out.println("======滚动查询到时数据："+json);
        System.out.println("======滚动查询到时数据,耗时：" + (System.currentTimeMillis()-start) + "ms");
	   return json;
	}
	/**
	 * 定位指定数据
	 * @return Stirng
	 */
	public static int locationData(QueryBuilder qb,TermsBuilder termsBuilder,String[] nogrouparr,String[] garr,String[] tableName,String[] logoid,JSONObject functions) {
		SearchRequestBuilder searchRequestBuilder = getClient().prepareSearch(tableName).setQuery(qb);
		if(garr !=null && garr.length>0){
			for(int i=0,z=garr.length;i<z;i++){
	        	String[] garr2 = garr[i].split(",");
	        	searchRequestBuilder.addSort(garr2[0], SortOrder.ASC);
			}
		}
		
		String[] logoarr = new String[logoid.length-1];
		for(int i=0,z=logoid.length-1;i<z;i++){
        	String[] garr2 = garr[i].split(",");
        	logoarr[i] = garr2[0];
		}
		if(nogrouparr !=null && nogrouparr.length>0){
			searchRequestBuilder.addFields(nogrouparr);
		}
		//执行搜索
		SearchResponse scrollResp1 = searchRequestBuilder.execute().actionGet();
		int total = (int) scrollResp1.getHits().getTotalHits();
//		int pageSize=1000;
//		int sum = total%pageSize==0?total/pageSize:(int)(total/pageSize) + 1;
		int num=0;
//		for(int s=0;s<sum;s++){
			SearchResponse scrollResp = searchRequestBuilder.setSize(total).setScroll(new TimeValue(1000)).execute().actionGet();
			Map<String, Object> map = new HashMap<String, Object>();
			 for(final SearchHit hit:scrollResp.getHits()){
			   num+=1;
	           final Iterator<SearchHitField> iterator = hit.iterator(); 
	           while(iterator.hasNext()){
	           final SearchHitField hitfield = iterator.next();
	              String strval =hitfield.getValue().toString();
	              if(functions !=null){
	            	  if(functions.containsKey(hitfield.getName())){
	            		  JSONObject jf = functions.getJSONObject(hitfield.getName());
	            		  String kString = jf.getString("key");
	            		  String vaString = jf.getString("value");
	            		  strval = verFunction(hitfield.getValue().toString(),kString,vaString);
	            	  }
	              }
	              map.put(hitfield.getName(),strval);
	            }
	           int t=0;
	           for(int i=0,z=logoarr.length;i<z;i++){
	        	   String str = map.get(logoarr[i]).toString();
	        	   if(StatisticsUtils.judgeDate(logoarr[i])){
	        		   if(logoid[logoarr.length].equals("t")){
	        			   str = str.substring(11,13);
	        		   }else if(logoid[logoarr.length].equals("y")){
	        			   str = str.substring(0,4);
	        		   }else if(logoid[logoarr.length].equals("m")){
	        			   str = str.substring(5,7);
	        		   }else if(logoid[logoarr.length].equals("d")){
	        			   str = str.substring(8,10);
	        		   }else if(logoid[logoarr.length].equals("w")){
	        			   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        			   Calendar c = Calendar.getInstance();
	        			   try {
							  c.setTime(format.parse(str.substring(0,11)));
							  int dayForWeek = 0;
		        			   if(c.get(Calendar.DAY_OF_WEEK) == 1){
		        			    dayForWeek = 0;
		        			   }else{
		        			    dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		        			   }
		        			   str = dayForWeek+"";
							} catch (ParseException e) {
								e.printStackTrace();
							}
	        			   
	        		   }else{
	        			   str = str.substring(0,10);
	        		   }
	        	   } 
	        	   if(str.toUpperCase().equals(logoid[i].toUpperCase())){
	        		   t+=1;
	        	   }
	           }
	           if(t ==logoid.length-1){
	        	   return num;
	           }
			}
//		}
		
	   return 0;
	}
	
	/**
	 * 组装查询条件
	 */
	public static BoolQueryBuilder getBQB(JSONObject json,String datasource){
		BoolQueryBuilder bqb = QueryBuilders.boolQuery();
	    if(json.isNullObject()){
	    	return bqb ;
	    }
		if(json !=null && json.size()>0){
			Map<String,String> result = StatisticsUtils.JsonToMapNotCaseInsensitiveMap(json);
			for(Map.Entry<String,String> entry : result.entrySet()){
				String key=entry.getKey();
				String value = entry.getValue();
				if(value!=null&&value.indexOf(",") !=-1){
				    bqb.must(QueryBuilders.termsQuery(key+"_group",value.split(",")));
				}else if(value!=null&&value.indexOf("~") !=-1){
					String[] arr = value.split("~");
					String arr1=arr[0].trim();
					String arr2 = null;
					if(arr!=null&&arr.length>1){
						arr2=arr[1].trim();
					}
					if(arr1 ==null || arr1.length() <=0){
						bqb.must(QueryBuilders.rangeQuery(key).format("yyyy-MM-dd").to(arr2).includeLower(true).includeUpper(true));
					}else if(arr2 ==null || arr2.length() <=0){
						bqb.must(QueryBuilders.rangeQuery(key).format("yyyy-MM-dd").from(arr1).includeLower(true).includeUpper(true));
					}else{
						bqb.must(QueryBuilders.rangeQuery(key).format("yyyy-MM-dd").from(arr1).to(arr2).includeLower(true).includeUpper(true));
					}
				}else{
				    if(value !=null && value.length() >0){
				        if(value.indexOf("#box") !=-1){
                            bqb.must(QueryBuilders.matchPhraseQuery(key+"_group",value.split("#")[0]));
                        }else{
                            bqb.must(QueryBuilders.matchPhraseQuery(key,value));
                        }
                    }

				}
			}
		}
		return bqb;
	}
	
	
	/**
	 * 通过数据源获得其名下的分组
	 * author huanghuang
	 * 2017年4月10日 上午9:55:00
	 * @param indexTab 索引名
	 * @param gArr 组
	 * @param groupFlag
	 * @param condition 查询条件
	 * @param topN
	 * @return
	 */
	@Validated
	public StringBuffer gtree(String[] indexTab,String[] gArr,Map<String,String> map,JSONObject functions,boolean groupFlag,JSONObject condition, int topN){
		String[] libArr = null;//图书馆的数组
		String[] devArr = null;//设备的数组
		//es里有数据的馆和设备 start
		String[] aggArr = null;
		int libIndex = 0;
		int devIndex = 0;
		if(gArr !=null && gArr.length>0){
			for(int gg=0,len=gArr.length;gg<len;gg++){
				if("library_idx".equals(gArr[gg])){
					libIndex = gg;
				}else if("device_idx".equals(gArr[gg])){
					devIndex = gg;
				}
			}
		}
		if(StatisticsUtils.isContains(gArr,"library_idx")&&StatisticsUtils.isContains(gArr,"device_idx")){
			if(libIndex>devIndex){//图书馆在设备之后
				aggArr = new String[]{"device_idx_group","library_idx_group"};
			}else{
				aggArr = new String[]{"library_idx_group","device_idx_group"};
			}
		}else if(StatisticsUtils.isContains(gArr,"library_idx")){
			aggArr = new String[]{"library_idx_group"};
		}else if(StatisticsUtils.isContains(gArr,"device_idx")){
			aggArr = new String[]{"device_idx_group"};
		}
		Map<String,Long> libToDevMap = null;
		if(aggArr!=null){
			libToDevMap = takeLibToDev(indexTab,aggArr);
		}
		Set<String> libSet = new HashSet<>();
		Set<String> devSet = new HashSet<>();
		if(libToDevMap!=null&&!libToDevMap.isEmpty()){
			Iterator<Map.Entry<String, Long>> iterator = libToDevMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Long> entry = iterator.next();
				String keys = entry.getKey();
				String[] key = keys.replace("[", "").replace("]", "").split(",");
				if(StatisticsUtils.isContains(gArr,"library_idx")&&StatisticsUtils.isContains(gArr,"device_idx")){
					if(libIndex>devIndex){//图书馆在设备之后
						devSet.add(key[1].trim()+"_"+key[0].trim());
						libSet.add(key[1].trim());
					}else{
						libSet.add(key[0].trim());
						devSet.add(key[0].trim()+"_"+key[1].trim());
					}
				}else if(StatisticsUtils.isContains(gArr,"library_idx")){
					libSet.add(key[0].trim());
				}else if(StatisticsUtils.isContains(gArr,"device_idx")){
					devSet.add(key[0].trim());
				}
			}
			libArr = new String[libSet.size()];
			libArr = (String[]) libSet.toArray(libArr);
			devArr = new String[devSet.size()];
			devArr = (String[]) devSet.toArray(devArr);
		}
		//System.out.println("==========图书馆数据分组："+JSONArray.fromObject(libArr));
		//System.out.println("==========设备数据分组："+JSONArray.fromObject(devArr));
		//es里有数据的馆和设备 end
	    String[] gArr2 = new String[gArr.length];
	    if(gArr !=null){
	        for(int r=0,z=gArr.length;r<z;r++){
                gArr2[r] = gArr[r];
	            if(!StatisticsUtils.judgeDate(gArr[r])){
                    gArr2[r] += "_group";
                }
            }
        }
		StringBuffer result = new StringBuffer();
		QueryBuilder qb = null;
		
		if(gArr !=null && gArr.length>0){
			for(int g=0;g<gArr.length;g++){
				int t=0;
				String bs="";
				if(map !=null){
					boolean existed = map.containsKey("country")||map.containsKey("province")||map.containsKey("city")||map.containsKey("area");
					result.append(gArr[g]+",");
					for(Map.Entry<String,String> entry : map.entrySet()){
						String key = entry.getKey();
						if(key.endsWith(gArr[g]) && !"regdate".equalsIgnoreCase(key)){//map的key等于arr的值时
							boolean existDate = StatisticsUtils.keyIndexOfDate(key);
							if(existDate){
								bs = key.split("#")[0];
								key = key.split("#")[1];
							}
//							String vs = entry.getValue();
//							if(!vs.isEmpty() && vs!=null){
								int lib = 0;
								int dev = 0;
								for(int gg=0,len=gArr.length;gg<len;gg++){
									if("library_idx".equals(gArr[gg])){
										lib = gg;
									}else if("device_idx".equals(gArr[gg])){
										dev = gg;
									}
								}
								String value = entry.getValue();
								if(value !=null && !value.isEmpty()){
									String[] values = value.split(",");
									if(key.equals(gArr[g])){
										t=1;
										String[] sortArr = new String[values.length];
										if(lib>dev){//图书馆在设备之后
											if(map.containsKey("library_idx")&&map.containsKey("device_idx")){
												int libLen = map.get("library_idx")==null?0: map.get("library_idx").split(",").length;
												int devLen = map.get("device_idx")==null?0: map.get("device_idx").split(",").length;
												sortArr = new String[libLen*devLen];
											}
										}
										 int ii = 0;
										 boolean noAddFlag = true;
										for(int i=0,z=values.length;i<z;i++){
											noAddFlag = true;
											String si = values[i];
											if(!StatisticsUtils.judgeDate(key)){
												if(functions !=null){
													if(functions.containsKey(gArr[g])){
														JSONObject jf = functions.getJSONObject(gArr[g]);
														String kString = jf.getString("key");
														String vaString = jf.getString("value");
														si = verFunction(si,kString,vaString);
													}
												}
											}
											//result.append(si).append(",");
											if(map.containsKey("library_idx")&&map.containsKey("device_idx")&&existed&&groupFlag){
												if(lib<dev){//图书馆在设备之前
													if("library_idx".equals(key)){
														String sql = "select info_value from library_info where infotype_idx='24' and library_idx="+si;
														ResultEntity r = selectAutBySql(sql);
														String areaCode = "";
														if(r!=null){
															@SuppressWarnings("unchecked")
															ArrayList<LinkedHashMap<String,String>> l =  (ArrayList<LinkedHashMap<String, String>>) r.getResult();
															if(l!=null&&!l.isEmpty()){
																LinkedHashMap<String,String> lhm =  l.get(0);
																areaCode = lhm.get("info_value")==null ? "" : (lhm.get("info_value") + "");
															}
														}
														sortArr[ii] = areaCode+"_"+si.toString();
													}else if("device_idx".equals(key)){
														String dev_id = JedisUtils.getInstance().get(RedisConstant.DEVICE_KEYS+si);
														DeviceEntity deviceEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE+dev_id,DeviceEntity.class);
														//Element devElement = devCache.get(si);
														if(deviceEntity!=null){
															@SuppressWarnings("unchecked")
															//Map<String, Object> devMap = (Map<String, Object>) devElement.getObjectValue();
															Integer devIdx = 0;
															devIdx = deviceEntity.getDevice_idx();
															//String device_idx = devMap.get("device_idx")==null ? "" : (devMap.get("device_idx") + "");
															/*if(StatisticsUtils.strIsNum(device_idx)){
																devIdx = Integer.parseInt(device_idx);
															}*/
															String areaCode = statisticsservice.retAreaCode(devIdx);
//														String areaCode = devMap.get("logistics_number")==null ? "" : (devMap.get("logistics_number") + "");
															String library_idx = devMap.get("library_idx")==null ? "" : (devMap.get("library_idx") + "");
															sortArr[ii] = areaCode+"_"+library_idx+"_"+si.toString();
														}
														//System.out.println("sortArr-->"+JSONArray.fromObject(sortArr));
													}else{
														sortArr[ii] = si.toString();
													}
												}else{
													if("library_idx".equals(key)){
														String sql = "select info_value from library_info where infotype_idx='24' and library_idx="+si;
														ResultEntity r = selectAutBySql(sql);
														String areaCode = "";
														if(r!=null){
															@SuppressWarnings("unchecked")
															ArrayList<LinkedHashMap<String,String>> l =  (ArrayList<LinkedHashMap<String, String>>) r.getResult();
															if(l!=null&&!l.isEmpty()){
																LinkedHashMap<String,String> lhm =  l.get(0);
																areaCode = lhm.get("info_value")==null ? "" : (lhm.get("info_value") + "");
															}
														}
														
														String libid = JedisUtils.getInstance().get(RedisConstant.LIBRARY_KEYS+si);
														//Element libElement = libCache.get(si);
														List<LibraryEntity> libraryEntities = JedisUtils.getInstance().hmget(RedisConstant.LIBRARY_KEY, LibraryEntity.class, libid);
														//LibraryEntity libraryEntity = (LibraryEntity) libElement.getObjectValue();
														LibraryEntity libraryEntity = libraryEntities.get(0);
														//List<Map<String, Object>> devList = libraryEntity.getDeviceList();
														Integer devIdx = null;
														Set<String> devids = JedisUtils.getInstance().smembers(RedisConstant.LIBRARY_DEVICEID+libraryEntity.getLibrary_idx());
														if(devids!=null&&devids.size()>0){
															for(String devid : devids){
																DeviceEntity deviceEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE+devid,DeviceEntity.class);
																if(deviceEntity!=null){
																	devIdx = deviceEntity.getDevice_idx();
																	sortArr[ii] = areaCode+"_"+devIdx+"_"+si.toString();
																	noAddFlag = false;
																	ii++;
																	if(ii>sortArr.length-1){
																		sortArr = Arrays.copyOf(sortArr, sortArr.length+1);
																	}
																}
															}
														}
														
													}else if("device_idx".equals(key)){
														String dev_id = JedisUtils.getInstance().get(RedisConstant.DEVICE_KEYS+si);
														//Element devElement = devCache.get(si);
														DeviceEntity deviceEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE+dev_id,DeviceEntity.class);
														if(deviceEntity!=null){
															//@SuppressWarnings("unchecked")
															//Map<String, Object> devMap = (Map<String, Object>) devElement.getObjectValue();
															Integer devIdx = 0;
															devIdx = deviceEntity.getDevice_idx();
															/*if(StatisticsUtils.strIsNum(device_idx)){
																devIdx = Integer.parseInt(device_idx);
															}*/
															String areaCode = statisticsservice.retAreaCode(devIdx);
															//String areaCode = devMap.get("logistics_number")==null ? "" : (devMap.get("logistics_number") + "");
															sortArr[ii] = areaCode+"_"+si.toString();
														}
														
													}else{
														sortArr[ii] = si.toString();
													}
												}
												
											}else if("library_idx".equals(key)&&existed&&groupFlag){
												String sql = "select info_value from library_info where infotype_idx='24' and library_idx="+si;
												ResultEntity r = selectAutBySql(sql);
												String areaCode = "";
												if(r!=null){
													@SuppressWarnings("unchecked")
													ArrayList<LinkedHashMap<String,String>> l =  (ArrayList<LinkedHashMap<String, String>>) r.getResult();
													if(l!=null&&!l.isEmpty()){
														LinkedHashMap<String,String> lhm =  l.get(0);
														areaCode = lhm.get("info_value")==null ? "" : (lhm.get("info_value") + "");
													}
												}
												sortArr[ii] = areaCode+"_"+si.toString();
											}else if("device_idx".equals(key)&&existed&&groupFlag){
												String dev_id = JedisUtils.getInstance().get(RedisConstant.DEVICE_KEYS+si);
												//Element devElement = devCache.get(si);
												DeviceEntity deviceEntity = JedisUtils.getInstance().get(RedisConstant.DEVICE+dev_id,DeviceEntity.class);
												if(deviceEntity!=null){
													//@SuppressWarnings("unchecked")
													//Map<String, Object> devMap = (Map<String, Object>) devElement.getObjectValue();
													Integer devIdx = deviceEntity.getDevice_idx();
													//String device_idx = devMap.get("device_idx")==null ? "" : (devMap.get("device_idx") + "");
													/*if(StatisticsUtils.strIsNum(device_idx)){
														devIdx = Integer.parseInt(device_idx);
													}*/
													String areaCode = statisticsservice.retAreaCode(devIdx);
													sortArr[ii] = areaCode+"_"+si.toString();
												}
												
											}else{//没有区域
												if(map.containsKey("library_idx")&&map.containsKey("device_idx")){
													if("library_idx".equals(key)){
														sortArr[ii] = si.toString();
													}else if("device_idx".equals(key)){
														for(String device:devArr){
															String ss[]=device.split("_");
															if(ss[1].equals(si.toString())){
																sortArr[ii] = ss[0]+"_"+si.toString();
															}
														}
													}else{
														sortArr[ii] = si.toString();
													}
												}else{
													sortArr[ii] = si.toString();
												}
											}
											if(noAddFlag){
												ii++;
												if(sortArr!=null&&ii>sortArr.length-1){
													sortArr = Arrays.copyOf(sortArr, sortArr.length+1);
												}
											}
										}
										//排序
										sortArr = removeArrayNullToNewArray(sortArr);
//										String [] str = new String[sortArr.length];
//										System.arraycopy(sortArr, 0, str, 0, sortArr.length);
//										Arrays.sort(str);
										for(String s : sortArr){
											if(StringUtils.isNotBlank(s)){
												result.append(s+",");
											}
										}
										if(result.length()>1){
											result.deleteCharAt(result.length()-1);
										}
										break;
									}
								}else{
									t=1;
									result.append(value);
									if(result.toString().endsWith(",")){
										result.deleteCharAt(result.length()-1);
									}
									break;
								}
//							}
						}
						
					}
					if(t ==0 ){
						if(StatisticsUtils.judgeDate(gArr[g])){
							Set<String> set = new TreeSet<String>();
					        DateHistogramBuilder dateAgg = AggregationBuilders.dateHistogram(gArr[g]);
					        dateAgg.field(gArr[g]);
					        if(bs.equals("y")){
			        			   dateAgg.interval(DateHistogramInterval.YEAR);
							       dateAgg.format("yyyy");
			        		   }else if(bs.equals("m")){
			        			   dateAgg.interval(DateHistogramInterval.MONTH);
							       dateAgg.format("MM");
			        		   }else if(bs.equals("d")){
			        			   dateAgg.interval(DateHistogramInterval.DAY);
							       dateAgg.format("dd");
			        		   }else if(bs.equals("w")){
			        			   dateAgg.interval(DateHistogramInterval.DAY);
							       dateAgg.format("e");
			        		   }else if(bs.equals("t")){
			        			   dateAgg.interval(DateHistogramInterval.HOUR);
							       dateAgg.format("HH");
			        		   }else{
			        			   dateAgg.interval(DateHistogramInterval.DAY);
			   			           dateAgg.format("yyyy-MM-dd");
			        		   }
					        SearchResponse searchResponse = getClient().prepareSearch(indexTab)
					        		.addSort(gArr[g],SortOrder.ASC)
									.setQuery(qb)
									.addAggregation(dateAgg)
									.execute().actionGet();
							 Aggregation aggr = searchResponse.getAggregations().get(gArr[g]);
							 List<? extends Histogram.Bucket> bucketList = ((Histogram) aggr).getBuckets();
							 for (Histogram.Bucket bucket : bucketList) {
								 if(bucket.getDocCount()!=0){
									 Object key = bucket.getKeyAsString();
									 if(bs.equals("w")){
										 if("7".equals(key)){
											 key = 0;
										 }
									 }
									 set.add(key+"");
									 
								 }
							 }
							 Iterator it= set.iterator();
							 String[] sortArr = new String[set.size()];
							 int i = 0;
							 while(it.hasNext())
					         {
							 Object o=it.next();
//							 if(functions !=null){
//									if(functions.containsKey(gArr[g])){
//						            	  JSONObject jf = functions.getJSONObject(gArr[g]);
//						            	  String kString = jf.getString("key");
//						            	  String vaString = jf.getString("value");
//						            	  o = verFunction(o.toString(),kString,vaString);
//						              }
//								}
								//result.append(o+",");
							 	sortArr[i] = o.toString();
								i++;
					         }
								//排序
//								String [] str = new String[sortArr.length];
//								System.arraycopy(sortArr, 0, str, 0, sortArr.length);
//								Arrays.sort(str);
								for(String s : sortArr){
									if(StringUtils.isNotBlank(s)){
										result.append(s+",");
									}
								}
								if(result.length()>1){
									result.deleteCharAt(result.length()-1);
								}
							/* if(result.length()>1){
									result.deleteCharAt(result.length()-1);
							}*/
						}else{
							TermsBuilder tb = AggregationBuilders.terms(gArr2[g]).field(gArr2[g]).size(topN);
							SearchResponse searchResponse = getClient().prepareSearch(indexTab)
									.addSort(gArr[g],SortOrder.ASC)
									.setQuery(qb)
									.addAggregation(tb)
									.execute().actionGet();
							Terms terms = searchResponse.getAggregations().get(gArr2[g]);
							Collection<Terms.Bucket> buckets = terms.getBuckets();
							String[] sortArr = new String[buckets.size()];
							int i = 0;
							for (Terms.Bucket bucket : buckets) {
								Object key = bucket.getKeyAsString();
								if(functions !=null){
									if(functions.containsKey(gArr2[g])){
						            	  JSONObject jf = functions.getJSONObject(gArr2[g]);
						            	  String kString = jf.getString("key");
						            	  String vaString = jf.getString("value");
						            	  key = verFunction(key.toString(),kString,vaString);
						              }
								}
								//result.append(key+",");
								sortArr[i] = key.toString();
								i++;
							}
							//排序
//							String [] str = new String[sortArr.length];
//							System.arraycopy(sortArr, 0, str, 0, sortArr.length);
//							Arrays.sort(str);
							for(String s : sortArr){
								if(StringUtils.isNotBlank(s)){
									result.append(s+",");
								}
							}
							if(result.length()>1){
								result.deleteCharAt(result.length()-1);
							}
						}
						
					}
					result.append(";");
				}else{
					result.append(gArr[g]+",");
					if(StatisticsUtils.judgeDate(gArr[g])){
				        DateHistogramBuilder dateAgg = AggregationBuilders.dateHistogram(gArr[g]);
				        dateAgg.field(gArr[g]);
				        dateAgg.interval(DateHistogramInterval.DAY);
				        dateAgg.format("yyyy-MM-dd");
				        SearchResponse searchResponse = getClient().prepareSearch(indexTab)
				        		.addSort(gArr[g],SortOrder.ASC)
								.setQuery(qb)
								.addAggregation(dateAgg)
								.execute().actionGet();
						 Aggregation aggr = searchResponse.getAggregations().get(gArr[g]);
						 List<? extends Histogram.Bucket> bucketList = ((Histogram) aggr).getBuckets();
						 for (Histogram.Bucket bucket : bucketList) {
							 if(bucket.getDocCount()!=0){
								 Object key = bucket.getKeyAsString();
//								 if(functions !=null){
//										if(functions.containsKey(gArr[g])){
//							            	  JSONObject jf = functions.getJSONObject(gArr[g]);
//							            	  String kString = jf.getString("key");
//							            	  String vaString = jf.getString("value");
//							            	  key = verFunction(key.toString(),kString,vaString);
//							              }
//									}
								 result.append(key+",");
							 }
						 }
						 if(result.length()>1){
								result.deleteCharAt(result.length()-1);
						}
					}else{
						TermsBuilder td = AggregationBuilders.terms(gArr2[g]).field(gArr2[g]).size(topN);
				        SearchResponse searchResponse = getClient().prepareSearch(indexTab)
				        		.addSort(gArr[g],SortOrder.ASC)
								.setQuery(qb)
								.addAggregation(td)
								.execute().actionGet();
				        Terms terms = searchResponse.getAggregations().get(gArr2[g]);
						Collection<Terms.Bucket> buckets = terms.getBuckets();
						String[] sortArr = new String[buckets.size()];
						int i = 0;
						for (Terms.Bucket bucket : buckets) {
							Object key = bucket.getKeyAsString();
							if(functions !=null){
								if(functions.containsKey(gArr2[g])){
					            	  JSONObject jf = functions.getJSONObject(gArr2[g]);
					            	  String kString = jf.getString("key");
					            	  String vaString = jf.getString("value");
					            	  key = verFunction(key.toString(),kString,vaString);
					              }
							}
							//result.append(key+",");
							sortArr[i] = key.toString();
							i++;
						}
						//排序
//						String [] str = new String[sortArr.length];
//						System.arraycopy(sortArr, 0, str, 0, sortArr.length);
//						Arrays.sort(str);
						for(String s : sortArr){
							if(StringUtils.isNotBlank(s)){
								result.append(s+",");
							}
						}
						if(result.length()>1){
							result.deleteCharAt(result.length()-1);
						}
					}
					result.append(";");
				}
			}
		}
		if(result.length()>1){
			result.deleteCharAt(result.length()-1);
		}
		/*StringBuffer endResult = new StringBuffer();
        String[] r1 = result.toString().split(";");
        for(int o=0,z=r1.length;o<z;o++){
            String[] r2 = r1[o].split(",");
            String gname = r2[0];
            if(gname.equals("library_idx")){
                StringBuffer resultLib = new StringBuffer();
                resultLib.append("library_idx,");
               for(int p=1;p<r2.length;p++){
                   String[] r3 = r2[p].split("_");
                   String gval = r3[r3.length -1];
                   int t=0;
                   for(int q=0;q<libArr.length;q++){
                      if(gval.equals(libArr[q])){
                         t=1;
                      }
                   }
                   if(t == 1){
                       resultLib.append(r2[p]+",");
                   }
               }
                if(resultLib.length()>1){
                    resultLib.deleteCharAt(resultLib.length()-1);
                }
                endResult.append(resultLib+";");
            }else if(gname.equals("device_idx")){
                StringBuffer resultDer = new StringBuffer();
                resultDer.append("device_idx,");
                for(int p=1;p<r2.length;p++){
                    String[] r3 = r2[p].split("_");
                    String gval = r3[r3.length -1];
                    int t=0;
                    for(int q=0;q<devArr.length;q++){
                        if(gval.equals(devArr[q])){
                            t=1;
                        }
                    }
                    if(t == 1){
                        resultDer.append(r2[p]+",");
                    }
                }
                if(resultDer.length()>1){
                    resultDer.deleteCharAt(resultDer.length()-1);
                }
                endResult.append(resultDer+";");
            }else{
                endResult.append(r1[o]+";");
            }
        }
        if(endResult.length()>1){
            endResult.deleteCharAt(endResult.length()-1);
        }
        System.out.println("==========分组数据："+endResult);
        return endResult;*/
		return result;
		
	}
	
	private static Map<String,Long> takeLibToDev(String[] tableName,String[] aggArr){
		@SuppressWarnings("rawtypes")
		AggregationBuilder[] aggregationBuilders = StatisticsUtils.addGroup(aggArr);
		StatisticsUtils.assembleAgg(aggregationBuilders, aggregationBuilders.length-1,null);//组装分组
		AbstractAggregationBuilder termsBuilder = aggregationBuilders[0];//得到要分组的参数
		BoolQueryBuilder qb = QueryBuilders.boolQuery();
		SearchRequestBuilder searchRequestBuilder = getClient().prepareSearch(tableName).setQuery(qb);
		searchRequestBuilder.addAggregation(termsBuilder);
		SearchResponse scrollResp = searchRequestBuilder.execute().actionGet();//返回有数据的树
		Map<String,Long> result = new HashMap<String,Long>();
		if(scrollResp.getAggregations()!=null){
			Map<String, Aggregation> aggMap = scrollResp.getAggregations().asMap();
			String gtmp = aggArr[0];
			Aggregation aggr = aggMap.get(gtmp);
			String[] arrflag = new String[aggArr.length];
			StatisticsUtils.takeTerms(aggr,aggArr,0,result,arrflag,"",null);
		}
		return result;
	}

	/**
	 * 获取实例
	 * @return
	 */
	public static Client getInstance() {
//		Settings settings = Settings.builder()
//		        .put("cluster.name", "elasticsearch").build();
		//TransportClient client = null;
		Client client = null;
		try {
            System.out.println("es地址："+esIP+"端口"+esPort);
            TransportClient t = TransportClient.builder().build();
            String[] esIPArr = esIP.split(",");
            TransportAddress [] ts = new TransportAddress[esIPArr.length];
            for(int i=0;i<esIPArr.length;i++){
            	ts[i] = new InetSocketTransportAddress(InetAddress.getByName(esIPArr[i]), esPort);
            }
            for (TransportAddress t1 : ts) {
                t.addTransportAddress(t1);
            }
            client = t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return client;
	}

	/**
	 * 关闭资源
	 */
	public static void closeClient() {
		client.close();
	}
	
	/**
	 * 图书馆的缓存
	 * author huanghuang
	 * 2017年5月9日 上午9:06:38
	 * @param key library_idx
	 * @return
	 */
	private Map<Integer, JSONObject> libMap = new HashMap<Integer, JSONObject>();// 当map里存在图书馆时，则不用请求数据库
	@Override
	public JSONObject takeLib(Integer key) {
		JSONObject value = null;
		value = libMap.get(key);
		if (value == null) {
			// 通过libidx得到图书馆
			String libidx = "{\"library_idx\":\"" + key + "\"}";
			Map<String, String> param = new HashMap<>();
			param.put("json", libidx);
			ResultEntity libResult = new ResultEntity();
			if(requestURL==null)//在线程里重新调用时，得不到RequestURLListEntity的实例，故重新实例化一次 modify by huanghuang 20170227
				requestURL = new RequestURLListEntity(XMLUtils.parseAll(ResourcesUtil.getInputStream("RequestURL.xml")));
			String reqURL=requestURL.getRequestURL(SEL_LIBRARY_BY_IDX_OR_ID);
			String res=HttpClientUtil.doPost(reqURL, param, Consts.UTF_8.toString());
			libResult = JsonUtils.fromJson(res, ResultEntity.class);
			if (libResult!=null&&libResult.getState()) {
				JSONArray libArray = JSONArray
						.fromObject(libResult.getResult());
				value = libArray.getJSONObject(0);
			}
			if (value == null) {
				LogUtils.error("=====>通过lib_idx查找不到对应的图书馆");
				throw new RuntimeException("=====>通过lib_idx查找不到对应的图书馆");
			}
			libMap.put(key, value);
		}

		return value;
	}

	/**f
	 * 设备的缓存
	 * author huanghuang
	 * 2017年5月9日 上午9:07:09
	 * @param key library_idx
	 * @return
	 */
	private Map<Integer, JSONArray> devMap = new HashMap<Integer, JSONArray>();// 当map里存在图书馆设备时，则不用请求数据库
	@Override
	public JSONArray takeDev(Integer key) {
		JSONArray value = null;
		value = devMap.get(key);
		if (value == null) {
			// 通过libidx得到所有设备
			String json = "{\"library_idx\":\"" + key + "\"}";
			Map<String, String> map = new HashMap<>();
			map.put("json", json);
			ResultEntity devResult = new ResultEntity();
			if(requestURL==null)//在线程里重新调用时，得不到RequestURLListEntity的实例，故重新实例化一次 modify by huanghuang 20170227
				requestURL = new RequestURLListEntity(XMLUtils.parseAll(ResourcesUtil.getInputStream("RequestURL.xml")));
			String reqURL=requestURL.getRequestURL(URL_QUERYDEVICES);
			String res=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
			devResult = JsonUtils.fromJson(res, ResultEntity.class);	
			if (devResult!=null&&devResult.getState()) {
				value = JSONArray.fromObject(devResult.getResult());
			}
			if (value == null) {
				LogUtils.error("=====>通过lib_idx查找不到对应的图书馆");
				throw new RuntimeException("=====>通过lib_idx查找不到对应的设备");
			}
			devMap.put(key, value);

		}

		return value;
	}
	public static String verFunction(String tdname,String key,String val){
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
			String[] vs = val.split(",");
			if("substr".equals(key)){
				if(tdname !=null && tdname.length()>0){
					if("#".equals(vs[0])){
						int staInt = Integer.parseInt(vs[1]);
						int endInt = Integer.parseInt(vs[2]);
						if(endInt >tdname.length()){
							endInt = tdname.length();
						}
						if(endInt <0){
							endInt=tdname.length();
						}
						if(staInt <0){
							staInt=0;
						}
						if(staInt>tdname.length()){
							staInt=tdname.length();
						}
						tdname = tdname.substring(staInt,endInt);
					}else{
						if(tdname.equals(vs[0].substring(1))){
							tdname =  tdname.substring(Integer.parseInt(vs[1]),Integer.parseInt(vs[2]));
						}
					}
				}else{
					tdname="";
				}
			}else if("concat".equals(key)){
				if("#".equals(vs[0])){
					tdname = tdname+vs[1];
				}else if("#".equals(vs[1])){
					tdname = vs[0]+tdname;
				}else if(!"#".equals(vs[0]) && vs[0].indexOf("#")>-1){
					if(tdname.equals(vs[0].substring(1))){
						tdname = tdname+vs[1];
					}
				}else if(!"#".equals(vs[1]) && vs[1].indexOf("#")>-1){
					if(tdname.equals(vs[1].substring(1))){
						tdname = vs[0]+tdname;
					}
				}
			}else if("replace".equals(key)){
				if("#".equals(vs[0])){
					if(vs[1].equals("#")){
						tdname = tdname.replace(tdname, vs[2]);
					}else{
						tdname = tdname.replace(vs[1], vs[2]);
					}
				}else{
					if(tdname.equals(vs[0].substring(1))){
						if(vs[1].equals("#")){
							tdname = tdname.replace(tdname, vs[2]);
						}else{
							tdname = tdname.replace(vs[1], vs[2]);
						}
					}
				}
			}else if("if".equals(key)){
				String[] vsarr = val.split(";");
				for(int vr=0;vr<vsarr.length;vr++){
					try {
						String[] vss=vsarr[vr].split(",");
						if(vss[0].equals(">")){
							if(Float.parseFloat(tdname) > Float.parseFloat(vss[1])){
								tdname=vss[2];
								break;
							}
						}else if(vss[0].equals("=")){
							if(tdname.equals(vss[1])){
								tdname=vss[2];
								break;
							}
						}else if(vss[0].equals("<")){
							if(Float.parseFloat(tdname) < Float.parseFloat(vss[1])){
								tdname=vss[2];
								break;
							}
						}else if(vss[0].equals(">=")){
							if(Float.parseFloat(tdname) >= Float.parseFloat(vss[1])){
								tdname=vss[2];
								break;
							}
						}else if(vss[0].equals("<=")){
							if(Float.parseFloat(tdname) <= Float.parseFloat(vss[1])){
								tdname=vss[2];
								break;
							}
						}
					}catch (Exception e) {
						tdname="if函数设置错误!";
						break;
					}
				}
			}else if("adddate".equals(key)){
				try {
					Date date = formatter.parse(tdname);
					Calendar c = Calendar.getInstance();
					c.setTime(date);
					c.add(Calendar.DATE,Integer.parseInt(vs[1]));
					date = c.getTime();
					if("#".equals(vs[0])){
						tdname = formatter.format(date);
					}else{
						if(tdname.indexOf(vs[0].substring(1)) >-1){
						tdname = formatter.format(date);
					}
				}
				} catch (ParseException e) {
					tdname="日期函数设置错误！";
//					e.printStackTrace();
				}
			}else if("nowdate".equals(key)){
				if("#".equals(vs[0])){
					tdname = formatter2.format(new Date());
				}else{
					if(tdname.indexOf(vs[0].substring(1)) >-1){
					 tdname = formatter2.format(new Date());
				    }
			   }
			}else if("nowtime".equals(key)){
				if("#".equals(vs[0])){
					tdname = formatter.format(new Date());
				}else{
					if(tdname.indexOf(vs[0].substring(1)) >-1){
					 tdname = formatter.format(new Date());
				    }
			   }
			}else if("subdate".equals(key)){
				try {
					Date date1 = formatter2.parse(tdname);
					Date date2 = new Date();
					if(!vs[0].equals("*")){
						date2 = formatter2.parse(vs[0]);
					}
					int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
					tdname=days+"";
					if("#".equals(vs[1])){
						tdname=days+"";
					}else{
						if(tdname.indexOf(vs[1].substring(1)) >-1){
							tdname=days+"";
					    }
				   }
				} catch (ParseException e) {
					tdname="日期函数设置错误！";
//					e.printStackTrace();
				}
			}else if("add".equals(key)){
				try {
					float t = Float.parseFloat(tdname);
					float v= Float.parseFloat(vs[1]);
					if("#".equals(vs[0])){
						tdname = (t+v)+"";
					}else{
						if(tdname.equals(vs[0].substring(1))){
							tdname = (t+v)+"";
						}
					}
				} catch (Exception e) {
					tdname="四则函数设置错误！";
				}
			}else if("subtract".equals(key)){
				try {
					float t = Float.parseFloat(tdname);
					if("#".equals(vs[0])){
						float v= Float.parseFloat(vs[1]);
						tdname = (t-v)+"";
					}else if("#".equals(vs[1])){
						float v= Float.parseFloat(vs[0]);
						tdname = (v-t)+"";
					}else if(!"#".equals(vs[0]) && vs[0].indexOf("#")>-1){
						if(tdname.equals(vs[0].substring(1))){
							float v= Float.parseFloat(vs[1]);;
							tdname = (t-v)+"";
						}
					}else if(!"#".equals(vs[1]) && vs[1].indexOf("#")>-1){
						if(tdname.equals(vs[1].substring(1))){
							float v= Float.parseFloat(vs[0]);
							tdname = (v-t)+"";
						}
					}
				}catch (Exception e) {
					tdname="四则函数设置错误！";
				}
			}else if("multiply".equals(key)){
				try {
					float t = Float.parseFloat(tdname);
					float v= Float.parseFloat(vs[1]);
					if("#".equals(vs[0])){
						tdname = (t*v)+"";
					}else{
						if(tdname.equals(vs[0].substring(1))){
							tdname = (t*v)+"";
						}
					}
				} catch (Exception e) {
					tdname="四则函数设置错误！";
				}
			}else if("divide".equals(key)){
				try {
					float t = Float.parseFloat(tdname);
					if("#".equals(vs[0])){
						float v= Float.parseFloat(vs[1]);
						tdname = (t/v)+"";
					}else if("#".equals(vs[1])){
						float v= Float.parseFloat(vs[0]);
						tdname = (v/t)+"";
					}else if(!"#".equals(vs[0]) && vs[0].indexOf("#")>-1){
						if(tdname.equals(vs[0].substring(1))){
							float v= Float.parseFloat(vs[1]);
							tdname = (t/v)+"";
						}
					}else if(!"#".equals(vs[1]) && vs[1].indexOf("#")>-1){
						if(tdname.equals(vs[1].substring(1))){
							float v= Float.parseFloat(vs[0]);
							tdname = (v/t)+"";
						}
					}
				}catch (Exception e) {
					tdname="四则函数设置错误！";
				}
			}
		return tdname;
	}
	/**
	 * 通过主馆查找子馆
	 * author huanghuang
	 * 2017年5月31日 下午4:25:01
	 * @param key
	 * @return
	 */
	@Override
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
	
    @Override
    public ResultEntity selectAutBySql(String req) {
        return postURL(URL_SELECTAUTBYSQL, req);
    }
    
    private String[] removeArrayNullToNewArray(String[] strArray) {
    	if(strArray!=null){
    		List<String> strList= Arrays.asList(strArray);
    		List<String> strListNew=new ArrayList<>();
    		for (int i = 0; i <strList.size(); i++) {
    			if (strList.get(i)!=null&&!strList.get(i).equals("")){
    				strListNew.add(strList.get(i));
    			}
    		}
    		String[] strNewArray = strListNew.toArray(new String[strListNew.size()]);
    		return   strNewArray;
    	}
    	return strArray;
    }

	@Override
	public String[] libArr(String[] indexTab,String[] gArr) {
		//es里有数据的馆和设备 start
		String[] libArr = null;
		String[] aggArr = null;
		int libIndex = 0;
		int devIndex = 0;
		if(gArr !=null && gArr.length>0){
			for(int gg=0,len=gArr.length;gg<len;gg++){
				if("library_idx".equals(gArr[gg])){
					libIndex = gg;
				}else if("device_idx".equals(gArr[gg])){
					devIndex = gg;
				}
			}
		}
		if(StatisticsUtils.isContains(gArr, "library_idx")&&StatisticsUtils.isContains(gArr,"device_idx")){
			if(libIndex>devIndex){//图书馆在设备之后
				aggArr = new String[]{"device_idx_group","library_idx_group"};
			}else{
				aggArr = new String[]{"library_idx_group","device_idx_group"};
			}
		}else if(StatisticsUtils.isContains(gArr,"library_idx")){
			aggArr = new String[]{"library_idx_group"};
		}else if(StatisticsUtils.isContains(gArr,"device_idx")){
			aggArr = new String[]{"device_idx_group"};
		}
		Map<String,Long> libToDevMap = null;
		if(aggArr!=null){
			libToDevMap = takeLibToDev(indexTab,aggArr);
		}
		Set<String> libSet = new HashSet<>();
		if(libToDevMap!=null&&!libToDevMap.isEmpty()){
			Iterator<Map.Entry<String, Long>> iterator = libToDevMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Long> entry = iterator.next();
				String keys = entry.getKey();
				String[] key = keys.replace("[", "").replace("]", "").split(",");
				if(StatisticsUtils.isContains(gArr,"library_idx")&&StatisticsUtils.isContains(gArr,"device_idx")){
					if(libIndex>devIndex){//图书馆在设备之后
						libSet.add(key[1].trim());
					}else{
						libSet.add(key[0].trim());
					}
				}else if(StatisticsUtils.isContains(gArr,"library_idx")){
					libSet.add(key[0].trim());
				}else if(StatisticsUtils.isContains(gArr,"device_idx")){
				}
			}
			libArr = new String[libSet.size()];
			libArr = (String[]) libSet.toArray(libArr);
		}
//		System.out.println("==========图书馆数据分组："+libArr.toString());
		//es里有数据的馆和设备 end
        return libArr;
	}

	@Override
	public String[] devArr(String[] indexTab,String[] gArr) {
		//es里有数据的馆和设备 start
		String[] devArr = null;
		String[] aggArr = null;
		int libIndex = 0;
		int devIndex = 0;
		if(gArr !=null && gArr.length>0){
			for(int gg=0,len=gArr.length;gg<len;gg++){
				if("library_idx".equals(gArr[gg])){
					libIndex = gg;
				}else if("device_idx".equals(gArr[gg])){
					devIndex = gg;
				}
			}
		}
		if(StatisticsUtils.isContains(gArr,"library_idx")&&StatisticsUtils.isContains(gArr,"device_idx")){
			if(libIndex>devIndex){//图书馆在设备之后
				aggArr = new String[]{"device_idx_group","library_idx_group"};
			}else{
				aggArr = new String[]{"library_idx_group","device_idx_group"};
			}
		}else if(StatisticsUtils.isContains(gArr,"library_idx")){
			aggArr = new String[]{"library_idx_group"};
		}else if(StatisticsUtils.isContains(gArr,"device_idx")){
			aggArr = new String[]{"device_idx_group"};
		}
		Map<String,Long> libToDevMap = null;
		if(aggArr!=null){
			libToDevMap = takeLibToDev(indexTab,aggArr);
		}
		Set<String> devSet = new HashSet<>();
		if(libToDevMap!=null&&!libToDevMap.isEmpty()){
			Iterator<Map.Entry<String, Long>> iterator = libToDevMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Long> entry = iterator.next();
				String keys = entry.getKey();
				String[] key = keys.replace("[", "").replace("]", "").split(",");
				if(StatisticsUtils.isContains(gArr,"library_idx")&&StatisticsUtils.isContains(gArr,"device_idx")){
					if(libIndex>devIndex){//图书馆在设备之后
						devSet.add(key[0].trim());
					}else{
						devSet.add(key[1].trim());
					}
				}else if(StatisticsUtils.isContains(gArr,"library_idx")){
					
				}else if(StatisticsUtils.isContains(gArr,"device_idx")){
					devSet.add(key[0].trim());
				}
			}
			devArr = new String[devSet.size()];
			devArr = (String[]) devSet.toArray(devArr);
		}
//        System.out.println("==========设备数据分组："+devArr.toString());
        //es里有数据的馆和设备 end
        return devArr;
	}
    

}
