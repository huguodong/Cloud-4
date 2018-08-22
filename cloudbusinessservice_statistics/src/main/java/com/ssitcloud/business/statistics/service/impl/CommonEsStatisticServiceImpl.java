package com.ssitcloud.business.statistics.service.impl;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.exception.CouldNotConnectException;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.PutMapping;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.codehaus.jackson.type.TypeReference;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.IndexNotFoundException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.MetricsAggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ssitcloud.authentication.entity.RelLibsEntity;
import com.ssitcloud.business.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.statistics.common.utils.BuildTree;
import com.ssitcloud.business.statistics.common.utils.HttpClientUtil;
import com.ssitcloud.business.statistics.common.utils.JsonUtils;
import com.ssitcloud.business.statistics.common.utils.LogUtils;
import com.ssitcloud.business.statistics.common.utils.ResourcesUtil;
import com.ssitcloud.business.statistics.common.utils.StatisConstant;
import com.ssitcloud.business.statistics.common.utils.StatisticsUtils;
import com.ssitcloud.business.statistics.common.utils.Tree;
import com.ssitcloud.business.statistics.common.utils.TreeData;
import com.ssitcloud.business.statistics.common.utils.XMLUtils;
import com.ssitcloud.business.statistics.service.CommonEsStatisticService;
import com.ssitcloud.business.statistics.service.ElasticsearchStatisticsService;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.statistics.entity.ReaderCirculationEntity;

/**
 * 查询统计公共类
 * 
 * @author yyl
 * 
 */
@Service
public class CommonEsStatisticServiceImpl extends BasicServiceImpl implements
		CommonEsStatisticService {
	// 图书馆相关
	private static final String SEL_LIBRARY_BY_IDX_OR_ID = "selLibraryByIdxOrId";
	// 查询子馆
	private static final String SEL_MASTERLIBS_BY_IDX = "selmasterLibsByIdx";

	@Resource
	private ElasticsearchStatisticsService elasticsearchStatisticsService;

	@Autowired
	private JestClient jestClient;
	
	public JestClient getJestClient() {
		return jestClient;
	}
	/**
	 * 获取elasitcsearch连接客户端
	 * 
	 * @return
	 */
	public static Client getClient() {
		return ElasticsearchStatisticsServiceImpl.getClient();
	}

	/**
	 * 读者维度 走statistics的查询
	 */
	public ResultEntity statisticsForReader(String req) {
		ResultEntity result = new ResultEntity();
		String[] datasource = null;// 要查的elachseach索引
		List<List<Map.Entry<String, Long>>> infoIds = new ArrayList<List<Map.Entry<String, Long>>>();// 统计结果值
		JSONObject json = JSONObject.fromObject(req);
		String gTreeArr = json.getString("gTreeArr");// 传递树的组合
		String isContainSubLib = json.optString("isContainSubLib");
		// 获得对应馆的所有设备
		Integer libidx = json.getInt("library_idx");// 馆idx
		String libId = json.getString("lib_id").toLowerCase();// 馆id
		Integer operator_type = json.getInt("operator_type");// 操作员类型
		String group = json.getString("group");// 分组
		String s = json.getString("gcondition");// 组条件
		JSONObject condition = json.getJSONObject("condition");// 一般条件
		String nogroup = json.getString("nogroup").trim();// 不分组的字段
		String ds = json.getString("datasource");// 数据源
		
		int topN = json.optInt("topHits",StatisConstant.STATIC_TOPN);//TopN

		// 获取当前图书馆的子馆 start
		List<Integer> libIdxs = new ArrayList<Integer>();// 图书馆idx
		libIdxs.add(libidx);

		List<String> libIds = new ArrayList<String>();// 图书馆id
		libIds.add(libId);

		// 通过主馆查找子馆
		List<RelLibsEntity> rellibsArray = takeRelLibs(libidx);

		//0表示包含子馆，1为不包含子馆
		if("1".equals(isContainSubLib)){
			rellibsArray =null ;
		}
		// 组合主馆、子馆数据
		List<String> subLibs = mergeMasterLibAndSlaveLibData(rellibsArray,
				libidx, libIdxs);
		if (!CollectionUtils.isEmpty(subLibs)) {
			libIds.addAll(subLibs);
		}
		//封装索引
		datasource = setterElasticIndices(libIdxs, libIds, operator_type, ds);

		String[] grouparr = group.split(",");

		Map<String, String> map = null;
		// 组条件处理
		if (!s.equals("{}")) {
			JSONObject gcondition = JSONObject.fromObject(s);
			map = StatisticsUtils.JsonToMap(gcondition);
		}
		// 获取分组数据
		String gstr = gtreeForReader(datasource, grouparr, map, null, false,condition,topN)
				.toString();

		String[] gstrArr = gstr.split(";");
		int gIndex = 0;// 如果是第一层树就没有数据，则不再进行统计
		for (int gs = 0; gs < gstrArr.length; gs++) {
			String[] tmpArr = gstrArr[gs].split(",");
			if (tmpArr.length == 1) {
				break;
			}
			gIndex = gIndex + 1;
		}
		if (gIndex == 0) {// 如果是第一层树就没有数据，则不再进行统计
			return result;
		}
		String[] aggArr = new String[gIndex];
		for (int g1 = 0; g1 < gIndex; g1++) {
			aggArr[g1] = grouparr[g1];
		}
		int sIndex = gstr.length();
		if (gIndex != gstrArr.length)
			sIndex = StatisticsUtils.getCharacterPosition(gstr, gIndex);
		String newGstr = gstr.substring(0, sIndex);
		AbstractAggregationBuilder termsBuilder = null;
		String[] nogrouparr = null;
		if (StringUtils.isNotBlank(nogroup)) {// 不分组的数据不为空时，不分组
			nogrouparr = nogroup.split(",");
		}
		// 获取elasticsearch数据
		infoIds = getElasitcSearchDatas(termsBuilder, nogrouparr, aggArr, map,
				newGstr, datasource, gTreeArr, ds, condition,topN);
		LogUtils.info(ds
				+ "################################统计是elachseach的index查询后的结果##################################");
		System.out
				.println(ds
						+ "################################统计是elachseach的index查询后的结果##################################");
		result.setResult(infoIds);
		result.setState(true);
		return result;
	}

	/**
	 * @param libIdxs
	 *            图书馆IDx
	 * @param libIds
	 *            图书馆ID
	 * @param operator_type
	 * @param ds
	 *            ds 数据源：对应模板中的数据源
	 * @return 索引
	 */
	private String[] setterElasticIndices(List<Integer> libIdxs,
			List<String> libIds, Integer operator_type, String ds) {
		String[] datasource = null;// 要查的elachseach索引
		StringBuffer sb = new StringBuffer();
		if (operator_type == 1) {
			sb.append("*_" + ds + ",");
		} else {
			for (int tmp = 0; tmp < libIdxs.size(); tmp++) {
				sb.append(libIds.get(tmp) + "_*_" + ds + ",");
			}
		}

		if (sb.toString().endsWith(",")) {
			sb.deleteCharAt(sb.length() - 1);
		}
		datasource = sb.toString().split(",");
		return datasource;
	}

	/**
	 * 组合主馆、子馆数据
	 * 
	 * @param rellibsArray合并后的数据
	 * @param libidx
	 * @param libIdxs
	 * @return
	 */
	private List<String> mergeMasterLibAndSlaveLibData(List<RelLibsEntity> list,
			Integer libidx, List<Integer> libIdxs) {
		List<String> libIds = new ArrayList<String>();// 图书馆id
		if(list!=null){
			for(RelLibsEntity rel : list){
				Integer tmpidx = rel.getSlave_lib_id();
				JSONObject libJson = elasticsearchStatisticsService.takeLib(tmpidx);
				if(libJson.get("lib_id")!=null){
					libIds.add(libJson.getString("lib_id").toLowerCase());
					libIdxs.add(libJson.getInt("library_idx"));
				}
			}
		}
		return libIds;

	}

	/**
	 * 读者维度 通过数据源获得其名下的分组
	 * 
	 * @param indexTab
	 *            索引
	 * @param gArr
	 *            分组
	 * @param map
	 * @param functions
	 * @param groupFlag
	 * @param condition 查询条件
	 * @param topN
	 * @return
	 */
	public StringBuffer gtreeForReader(String[] indexTab, String[] gArr,
			Map<String, String> map, JSONObject functions, boolean groupFlag,JSONObject condition,int topN) {

		// es里有数据的馆和设备 start
		String[] aggArr = null;
		int libIndex = 0;
		if (gArr != null && gArr.length > 0) {
			for (int gg = 0, len = gArr.length; gg < len; gg++) {
				if ("library_idx".equals(gArr[gg])) {
					libIndex = gg;
				}
			}
		}
		// es里有数据的馆和设备 end

		String[] gArr2 = new String[gArr.length];
		if (gArr != null) {
			for (int r = 0, z = gArr.length; r < z; r++) {
				gArr2[r] = gArr[r];
				gArr2[r] += "_group";
			}
		}
		// 封装查询条件
		BoolQueryBuilder qb = null;
		//		if (!org.springframework.util.StringUtils.isEmpty(libIndex)
		//				&& libIndex > 0) {
//			qb = QueryBuilders.boolQuery();
//			qb.must(QueryBuilders.matchPhraseQuery("library_idx_group",
//					libIndex));
		//		}
		// 获取elasticsearch分组
		StringBuffer endResult = getElasticsearchGroupByDatas(indexTab, gArr,
				gArr2, map, functions, qb,topN);
		System.out.println("==========分组数据：" + endResult);
		return endResult;

	}

	/**
	 * 获取elasticsearch分组
	 * 
	 * @param indexTab
	 * @param gArr
	 * @param gArr2
	 * @param map
	 * @param functions
	 * @param qb
	 * @return
	 */
	private StringBuffer getElasticsearchGroupByDatas(String[] indexTab,
			String[] gArr, String[] gArr2, Map<String, String> map,
			JSONObject functions, BoolQueryBuilder qb,Integer topN) {
		Map<String,Map<String,String>> resultMap =  new HashMap<>();
		Map<String,Long> treeResult = new HashMap<>();
		StringBuffer result = new StringBuffer();
		if(topN ==0){
			topN = StatisConstant.STATIC_TOPN ;
		}
		
		if (gArr != null && gArr.length > 0) {
			String[] arrGroup = new String[gArr.length];
			for (int i =0;i<gArr.length;i++){
				arrGroup[i] =  gArr[i]+"_group";
			}
			@SuppressWarnings("rawtypes")
			AggregationBuilder[] aggregationBuilders = StatisticsUtils
					.aggGroup(gArr, map,topN);
			StatisticsUtils.assembleAgg(aggregationBuilders,
					aggregationBuilders.length - 1, null);// 组装分组
			AbstractAggregationBuilder termsBuilder  = aggregationBuilders[0];// 得到要分组的参数
			SearchRequestBuilder searchRequestBuilder = getClient().prepareSearch(
					indexTab).setQuery(qb);
			
			if (termsBuilder != null) {// 统计模板 modify by huanghuang
				// 添加分组信息
				searchRequestBuilder.addAggregation(termsBuilder);
				SearchResponse scrollResp = searchRequestBuilder.execute()
						.actionGet();// 返回的不完整的树
				if (scrollResp.getAggregations() == null) {
					return null;
				}
				Map<String, Aggregation> aggMap = scrollResp.getAggregations()
						.asMap();
				String gtmp = gArr[0];
				if (!StatisticsUtils.judgeDate(gtmp)) {
					gtmp += "_group";
				}
				Aggregation aggr = aggMap.get(gtmp);
				String[] arrflag = new String[gArr.length];
				StatisticsUtils.takeTreeTerms(aggr, arrGroup, 0, treeResult, arrflag, null, resultMap);
				if(MapUtils.isEmpty(resultMap)){
					System.out.println("--resultMap is null:"+resultMap+"---aggr:"+aggr+"---indexName:" + Arrays.toString(indexTab)+ "---arrGroup:"+arrGroup+"---treeResult:"+treeResult);
					return result;
				}
				for (int i =0;i<gArr.length;i++){
					String key =gArr[i]+"_group";
					result.append(gArr[i]);
					result.append(",");
					Set<Entry<String, String>> tree = resultMap.get(key).entrySet();
					for(Entry<String, String> childTree :tree){
						result.append(childTree.getKey());
						result.append(",");
					}
					if (null != result && result.length() > 1) {
						result.deleteCharAt(result.length() - 1);
					}
					result.append(";");
				}

			}
		}
		
		if (null != result && result.length() > 1) {
			result.deleteCharAt(result.length() - 1);
		}
		System.out.println("statistics查询后的result数据：" + result.toString());
		return result;
	}


	/**
	 * @param termsBuilder
	 *            terms条件
	 * @param nogrouparr
	 *            不分组条件
	 * @param aggArr
	 *            分组条件
	 * @param map
	 * @param newGstr
	 * @param indicesName
	 * @param gTreeArr
	 * @param ds
	 *            数据源
	 * @param condition
	 * @param topN topN
	 * @return
	 */
	private List<List<Entry<String, Long>>> getElasitcSearchDatas(
			AbstractAggregationBuilder termsBuilder, String[] nogrouparr,
			String[] aggArr, Map<String, String> map, String newGstr,
			String[] indicesName, String gTreeArr, String ds,
			JSONObject condition,int topN) {
		List<List<Map.Entry<String, Long>>> infoIds = new ArrayList<List<Map.Entry<String, Long>>>();// 统计结果值
		if (nogrouparr != null) {
			for (int ng = 0; ng < nogrouparr.length; ng++) {// 根据不是分组的查询数据
				String[] funValArr = nogrouparr[ng].split("\\|");
				String funVal = "";
				if (funValArr != null && funValArr.length > 2) {
					funVal = funValArr[2].trim();// 得到要处理的数值函数名
				}
				@SuppressWarnings("rawtypes")
				List<MetricsAggregationBuilder> subAggList = null;
				if(funValArr!=null&&funValArr.length>2){
					funVal = funValArr[2].trim();//得到要处理的数值函数名
				}
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
				AggregationBuilder[] aggregationBuilders = StatisticsUtils
						.aggGroup(aggArr, map,topN);
				StatisticsUtils.assembleAgg(aggregationBuilders,
						aggregationBuilders.length - 1, subAggList);// 组装分组
				termsBuilder = aggregationBuilders[0];// 得到要分组的参数
				BoolQueryBuilder bqb = ElasticsearchStatisticsServiceImpl
						.getBQB(condition, ds);// 组装查询条件
				List<Map.Entry<String, Long>> rInfo = null;
				try {
					rInfo = subSearchIndexForReader(map, nogrouparr[ng], bqb,
							termsBuilder, newGstr, indicesName, gTreeArr);
				} catch (IndexNotFoundException e) {// 走elachseach统计时，找不到elachseach的index
					LogUtils.error("走elachseach统计时，找不到elachseach的index", e);
				}
				infoIds.add(rInfo);
			}
		}
		return infoIds;
	}

	/**
	 * 直接走elachseach author yyl
	 * 
	 * @param map
	 * @param ceil
	 * @param qb
	 * @param termsBuilder
	 * @param fullTree
	 * @param tableName
	 * @return
	 */
	public static List<Map.Entry<String, Long>> subSearchIndexForReader(
			Map<String, String> map, String ceil, BoolQueryBuilder qb,
			AbstractAggregationBuilder termsBuilder, String fullTree,
			String[] tableName, String gTreeArr) {
		String[] cArr = null;
		String aggArr = "";// 数值函数的聚合属性
		if (ceil.indexOf("|") > -1) {
			cArr = ceil.split("\\|");
			if (cArr != null && cArr.length > 1) {
				if (cArr.length > 2 && cArr[2].trim().length() > 0
						&& StatisticsUtils.isAggGroup(cArr[2].trim())) {
					aggArr = cArr[0].trim();
				}
				String queryAttr = cArr[0].trim();
				String queryVal = cArr[1].trim();
				if (queryAttr.length() > 0 && queryVal.length() > 0) {
					qb.must(QueryBuilders.matchQuery(queryAttr + "_group",
							queryVal));
				}
			} else {
				qb.must(QueryBuilders.existsQuery(cArr[0].trim()));
			}
		}
		SearchRequestBuilder searchRequestBuilder = getClient().prepareSearch(
				tableName).setQuery(qb);
		Map<String, List<String>> newkv = new HashMap<String, List<String>>();
		String[] fullArr = fullTree.split(";");
		int glen = fullArr.length;// 分组的长度
		String[] rootArr = new String[fullArr.length];
		Map<String, Long> result = new HashMap<String, Long>();
		if (termsBuilder != null) {// 统计模板 modify by huanghuang
			// 添加分组信息
			searchRequestBuilder.addAggregation(termsBuilder);
			SearchResponse scrollResp = searchRequestBuilder.execute()
					.actionGet();// 返回的不完整的树
			if (scrollResp.getAggregations() == null) {
				return null;
			}
			Map<String, Aggregation> aggMap = scrollResp.getAggregations()
					.asMap();
			for (int f = 0; f < fullArr.length; f++) {
				List<String> v = new ArrayList<String>();
				String[] emptyArr = fullArr[f].split(",");
				rootArr[f] = emptyArr[0];
				for (int m = 1; m < emptyArr.length; m++) {
					v.add(emptyArr[m]);
				}
				String transStr = emptyArr[0];
				boolean flag = StatisticsUtils.judgeDate(transStr);
				if (!flag) {
					transStr = transStr + "_group";
				}
				newkv.put(transStr, v);
			}
			if (glen > 0) {// 统计有分组时
				String gtmp = rootArr[0];
				if (!StatisticsUtils.judgeDate(gtmp)) {
					gtmp += "_group";
				}
				Aggregation aggr = aggMap.get(gtmp);
				String[] arrflag = new String[rootArr.length];
				StatisticsUtils.takeTerms(aggr, rootArr, 0, result,
						arrflag, aggArr, map);
			}
			System.out.println("statistics查询后的result数据：" + result.toString());
			LogUtils.info("statistics查询后的result数据：" + result.toString());

		}
		String[][] array = new String[rootArr.length][];// 全排列的数组
		for (int ra = 0; ra < rootArr.length; ra++) {
			List<String> list = newkv.get(rootArr[ra]);
			// if(list.size()>0){
			String[] strings = new String[list.size()];
			list.toArray(strings);
			array[ra] = strings;
		}
		String[] num = new String[array.length];
		List<String> ls = new ArrayList<String>();// 得到树的全排列
		if (StringUtils.isNotBlank(gTreeArr)) {
			String[] gArr = gTreeArr.split(",");
			for (String g : gArr) {
				String[] n = g.split(";");
				ls.add(Arrays.toString(n));
			}
		} else {
			StatisticsUtils.fullPermutation(array, array.length, 0, num, ls);
		}
		Map<String, Long> newResult = new HashMap<String, Long>();
		if (map != null) {
			int index = 0;
			for (int m = 0; m < rootArr.length; m++) {
				if (StatisticsUtils.judgeDate(rootArr[m])) {
					index = m;
				}
			}
			if (StatisticsUtils.mapContainsDate(map, "t")
					&& StatisticsUtils.keyIndexOfDate(rootArr[index])) {
				StatisticsUtils.changeMap(index, newResult, result, "t");
			} else if (StatisticsUtils.mapContainsDate(map, "y")
					&& StatisticsUtils.keyIndexOfDate(rootArr[index])) {
				StatisticsUtils.changeMap(index, newResult, result, "y");
			} else if (StatisticsUtils.mapContainsDate(map, "m")
					&& StatisticsUtils.keyIndexOfDate(rootArr[index])) {
				StatisticsUtils.changeMap(index, newResult, result, "m");
			} else if (StatisticsUtils.mapContainsDate(map, "d")
					&& StatisticsUtils.keyIndexOfDate(rootArr[index])) {
				StatisticsUtils.changeMap(index, newResult, result, "d");
			} else if (StatisticsUtils.mapContainsDate(map, "w")
					&& StatisticsUtils.keyIndexOfDate(rootArr[index])) {
				StatisticsUtils.changeMap(index, newResult, result, "w");
			}
		}
		Map<String, Long> resultTree = new LinkedHashMap<String, Long>();
		for (String s : ls) {
			Long lVal = 0L;
			Set<Entry<String, Long>> rs = result.entrySet();
			if (!newResult.isEmpty()) {
				rs = newResult.entrySet();
			}
			Iterator<Entry<String, Long>> it = rs.iterator();
			while (it.hasNext()) {// 遍历实际得到的树
				Entry<String, Long> e = it.next();
				String key = e.getKey();
				if (s.equals(key)) {
					lVal = e.getValue();
				}
			}
			if (cArr != null && cArr.length > 3 && cArr[2] != null
					&& cArr[3] != null) {
				lVal = StatisticsUtils.delFunction(lVal, cArr);
			}
			resultTree.put(s, lVal);
		}

		List<Map.Entry<String, Long>> infoIds = new ArrayList<Map.Entry<String, Long>>(
				resultTree.entrySet());
		System.out.println("statistics查询后的infoIds数据：" + infoIds.toString());
		LogUtils.info("statistics查询后的infoIds数据：" + infoIds.toString());
		return infoIds;
	}

	/**
	 * 通过主馆查找子馆 author huanghuang 2017年5月31日 下午4:25:01
	 * 
	 * @param key
	 * @return
	 */
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
	/**
	 * 创建索引，并且设置主分片以及备用分片数量，一般情况下，主分片默认有一个备用分片
	 *
	 * <p>2017年11月08日 下午4:54:28 
	 * <p>create by yyl
	 * @param indexName 索引名称
	 * @param number_of_shards 主分片数量
	 * @param number_of_replicas  每个主分片对应的备用分片数量
	 * @return
	 */
	public boolean createIndex(String indexName, Integer number_of_shards,
			Integer number_of_replicas) {
		indexName = indexName.toLowerCase();
		try {
			if(number_of_shards == null){
				number_of_shards = 1;
			}
			if (number_of_replicas == null) {
				number_of_replicas = number_of_shards.intValue();
			}
			Settings.Builder settingBuilder = Settings.builder();
			settingBuilder.put("number_of_shards",number_of_shards);
			settingBuilder.put("number_of_replicas",number_of_replicas);
			
			CreateIndex createIndex = new CreateIndex.Builder(indexName).settings(settingBuilder.build()).build();
			JestResult jestResult = jestClient.execute(createIndex);
			
			if (jestResult != null) {
				if (!jestResult.isSucceeded()) {
					throw new RuntimeException("创建索引失败!" + jestResult.getErrorMessage());
				}
			}else {
				throw new RuntimeException("创建索引失败!");
			}
		} catch (Exception e) {
			LogUtils.error("创建索引【" + indexName + "】失败",e);
			return false;
		}
		return true;
	}

	/**
	 * 判断索引是否存在
	 * @param indexName
	 * @param typeName
	 * @return
	 */
	public boolean isExistsIndex(String indexName,String typeName ) {
		 Search search = new Search.Builder("").addIndex(indexName).addType(typeName).build();
		boolean falg = true;
		try {
			SearchResult searchResult = getJestClient().execute(search);
			 if (!searchResult.isSucceeded()  && searchResult.getErrorMessage().contains("no such index")) {
				 
				 falg = false ;
			 }
		} catch (IOException e) {
			
			 falg = false ;
			e.printStackTrace();
		}
		return falg;
	}

	/**
	 * 根据实体类映射es Mapping
	 * <p>2017年11月08日 下午4:54:28 
	 * <p>create by yyl
	 * @param indexName
	 * @param typeName
	 * @param obj
	 * @return
	 */
	public <T> String setTypeMapping(String indexName, String typeName,T obj) {
		String mappingStr ="";
		String upperTypeName = typeName.toUpperCase();
		try {
			if(obj!=null){
				XContentBuilder jsonBuilder = jsonBuilder();
				jsonBuilder = jsonBuilder.startObject()
						.field(upperTypeName)
						.startObject().field("properties")
						.startObject();
				for (Field field : obj.getClass().getDeclaredFields()) {
					String type = field.getGenericType().toString();
					type = type.substring(type.lastIndexOf(".")+1);
					if (field.getName().equals("admission_date") 
							|| field.getName().equals("graduation_date")
							||field.getName().equals("birth_date")) {
						jsonBuilder = jsonBuilder.field(field.getName()+ "_group")
								.startObject()
								.field("type","date")
								.field("index","not_analyzed")
								.field("format","yyyy-MM-dd")
								.endObject();
					}else if (field.getName().equals("opertime")) {
						jsonBuilder = jsonBuilder.field(field.getName()+ "_group")
								.startObject()
								.field("type","date")
								.field("index","not_analyzed")
								.field("format","yyyy-MM-dd HH:mm:ss")
								.endObject()
								.startObject(field.getName())
								.field("type","date")
								.field("index","not_analyzed")
								.field("format","yyyy-MM-dd HH:mm:ss")
								.endObject();
					}else{
						
						jsonBuilder = jsonBuilder
								.startObject(field.getName() + "_group")
								.field("type",type)
								.field("index","not_analyzed")
								.endObject();
					}
				}
				jsonBuilder = jsonBuilder.endObject().endObject().endObject();
				mappingStr =jsonBuilder.string();
			}
		} catch (Exception e) {
			throw new RuntimeException("createFineMapping is error", e);
		}
		return mappingStr;
	
	}
	/**
	 * 创建类型的映射，类似于定义关系型数据库的表结构，一旦定义，则不可修改
	 *
	 * <p>2017年3月13日 下午6:27:39 
	 * <p>create by hjc
	 * @param indexName
	 * @param typeName
	 * @param mappingStr
	 * @return
	 */
	public boolean createTypeMapping(String indexName, String typeName, String mappingStr){
		indexName = indexName.toLowerCase();
		String upperTypeName = typeName.toUpperCase();
		PutMapping putMapping = new PutMapping.Builder(indexName, upperTypeName, mappingStr).build();
		try {
			JestResult jestResult = jestClient.execute(putMapping);
			if (jestResult != null) {
				if (!jestResult.isSucceeded()) {
					throw new RuntimeException("创建mapping失败!" + jestResult.getErrorMessage());
				}
			}else {
				throw new RuntimeException("创建mapping失败!");
			}
		} catch (IOException e) {
			LogUtils.error("创建【" + indexName + "-" + typeName + "】mapping失败",e);
			return false;
		}
		return true;
	}
	
	/**
	 * 插入或者更新文档，如果保存的实体没有id，则es自动生成,
	 * <br>如果该ID的数据已经存在，则强制更新
	 * <br>新增成功之后，返回此条数据(包括自增ID)
	 * <br>elasticsearch 不存在更新操作，只有替换
	 * <p>2017年11月09日 
	 * <p>create by yyl
	 * @param indexName 索引名称
	 * @param typeName 类型名称
	 * @param entity 要保存的实体
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T saveOrUpdateDocument(String indexName, String typeName, T entity){
		if (indexName.contains("#")) {
			indexName = indexName.replaceAll("#", INSTEAD_OF_SHAPE);
		}
		indexName = indexName.toLowerCase();
		try {
			Index index = new Index.Builder(entity).index(indexName)
					.type(typeName).id(BeanUtils.getProperty(entity, "id")).build();
			DocumentResult documentResult = jestClient.execute(index);
			if (documentResult != null ) {
				if(documentResult.isSucceeded()){
					Get get = new Get.Builder(indexName, documentResult.getId()).type(typeName).build();
					DocumentResult documentResult2 = jestClient.execute(get);
					entity = (T) documentResult2.getSourceAsObject(entity.getClass());
				}else{
					throw new RuntimeException("saveOrUpdateDocument失败!" + documentResult.getErrorMessage());
				}
			}else {
				throw new RuntimeException("saveOrUpdateDocument失败!");
			}
		} catch (CouldNotConnectException e){
        	System.out.println("------->连接不上elasticsearch");
		} catch (Exception e) {
			if(entity instanceof ReaderCirculationEntity){
				ReaderCirculationEntity circulationEntity = (ReaderCirculationEntity) entity;
				System.out.println(circulationEntity.getId());
			}
			LogUtils.error("saveOrUpdateDocument失败",e);
		}
		return entity;
	}
	/**
	 * 获取查询模板数据
	 * @param qb
	 * @param termsBuilder
	 * @param nogrouparr
	 * @param sortArr 排序列
	 * @param pageNo
	 * @param pageSize
	 * @param indices
	 * @param functions
	 * @return
	 */
	public  JSONObject searchIndex(QueryBuilder qb,TermsBuilder termsBuilder,String[] fieldArr,String[] sortArr,int pageNo,int pageSize,String[] indices){

		JSONObject json = new JSONObject();
		
		SearchRequestBuilder searchRequestBuilder = getClient().prepareSearch(indices).setQuery(qb);
		if(sortArr !=null && sortArr.length>0){
			for(int i=0,z=sortArr.length;i<z;i++){
	        	String[] garr2 = sortArr[i].split(",");
	        	searchRequestBuilder.addSort(garr2[0], SortOrder.ASC);
			}
		}
		if(fieldArr !=null && fieldArr.length>0){
			searchRequestBuilder.addFields(fieldArr);
		}
		//执行搜索
        int size = 10000;
        SearchResponse scrollResp = searchRequestBuilder.setSize(size).setScroll(new TimeValue(30000)).execute().actionGet();
        
        System.out.println(" -----------scrollResp = " + scrollResp.toString());
	   return json;
	
	}
	/**
	 *  组装查询条件
	 * @param json
	 * @return
	 */
	public BoolQueryBuilder  getBqb(JSONObject condition){
		BoolQueryBuilder bqb = QueryBuilders.boolQuery();
		if (condition != null && condition.size() > 0) {
			Map<String, String> result = StatisticsUtils.JsonToMap(condition);
			for (Map.Entry<String, String> entry : result.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				bqb.must(QueryBuilders.termsQuery(key, value));
			}
		}
		return bqb;
	}
	/**
	 * 获取统计数据
	 */
	public ResultEntity getData(String req) {
		ResultEntity result = new ResultEntity();
		JSONObject json = null;
		if(StringUtils.isEmpty(req)){
			result.setState(false);
			result.setMessage(" request param is null,req : " + req);
			return result;
		}
		try{
			json = JSONObject.fromObject(req);
		}catch(Exception e){
			e.printStackTrace();
			result.setState(false);
			result.setMessage("json 格式转换异常,请求参数req : " + req);
			return result;
		}
		String indexName = json.getString("indexName");// 索引名称
		// 获得对应馆的所有设备
		String groupCondition = json.getString("groupCondition");// 分组条件
		JSONObject searchCondition = json.getJSONObject("searchCondition");//查询条件
		JSONObject timeConditin = json.getJSONObject("timeConditin");//查询条件
		JSONObject exclusiveConditon = null;//排除条件
		
		if(json.containsKey("exclusiveConditon")){
			exclusiveConditon =json.getJSONObject("exclusiveConditon");
		}
		
		int topN = json.optInt("topHits",StatisConstant.STATIC_TOPN);//TopN
		String []aggArr =null;
		if(!StringUtils.isEmpty(groupCondition)){
			aggArr = groupCondition.split(",");
		}
		// 获取elasticsearch数据
		result = queryElasticsearchDatas(indexName,aggArr,searchCondition,timeConditin,exclusiveConditon,topN);
		return result;
	}
	/**
	 * 获取elasticsearch数据
	 * @param indexName 索引名称
	 * @param groupCondition  分组条件
	 * @param searchCondition query条件
	 * @param JSONObject rangeQuery条件
	 * @param JSONObject exclusiveConditon 排除条件
	 * @param topN topN条件
	 * @param map map
	 * @return
	 */
	private ResultEntity queryElasticsearchDatas(String indexName,
			String[] aggArr, JSONObject searchCondition,JSONObject timeConditin,JSONObject exclusiveConditon,Integer topN) {
		
		@SuppressWarnings("rawtypes")
		AggregationBuilder[] aggregationBuilders = StatisticsUtils.aggGroup(aggArr,topN);
		StatisticsUtils.assembleAgg(aggregationBuilders,aggregationBuilders.length - 1, null);// 组装分组
		
		AbstractAggregationBuilder termsBuilder = null;
		
		//封装查询条件
		BoolQueryBuilder qb = setQueryCondition(searchCondition);
		
		//封装排除条件
		setExclusiveConditon(exclusiveConditon,qb);
		
		//封装时间范围查询
		setRangeQueryConditin(qb,timeConditin);
		
		termsBuilder = aggregationBuilders[0];// 得到要分组的参数
		
		ResultEntity result =query(qb, termsBuilder, aggArr, indexName);
		return result;
	}
	
	/**
	 * 封装时间范围查询条件
	 * @param qb
	 * @param timeConditin
	 */
	private void setRangeQueryConditin(BoolQueryBuilder boolQueryBuilder,
			JSONObject timeConditin) {
		try{
			
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) JSONObject.toBean(timeConditin, Map.class);
			
			if( boolQueryBuilder ==null){
				boolQueryBuilder = QueryBuilders.boolQuery();
			}
			
			if(!MapUtils.isEmpty(map)){
				boolQueryBuilder.must(QueryBuilders.rangeQuery(map.get("field")).format(map.get("format")).from(map.get("start")).to(map.get("end")));
			}
			
		}catch(Exception e ){
			e.printStackTrace();
			System.out.println(" josn switch exception:" + e.getMessage());
		}
		
	}
	/**
	 * 封装查询条件
	 * @param qb
	 * @param condition
	 */
	private BoolQueryBuilder setQueryCondition(JSONObject condition) {
		
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		try{
			
			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) JSONObject.toBean(condition, Map.class);
			
			
			if(!MapUtils.isEmpty(map)){
				for (String key : map.keySet()){
					boolQueryBuilder = boolQueryBuilder.must(QueryBuilders.matchPhraseQuery(key+"_group", map.get(key)));// 条件3
				}
			}
			
		}catch(Exception e ){
			e.printStackTrace();
			System.out.println(" josn switch exception:" + e.getMessage());
		}
		
		return boolQueryBuilder;
	}
	/**
	 * 获取elachseach 统计数据
	 * 
	 * @param map
	 * @param ceil
	 * @param qb
	 * @param termsBuilder
	 * @param fullTree
	 * @param tableName
	 * @return
	 */
	private  ResultEntity query(BoolQueryBuilder qb,
			AbstractAggregationBuilder termsBuilder, String[] rootArr,
			String tableName) {
		
		int glen = rootArr.length;// 分组的长度
		ResultEntity result = new ResultEntity();
		result.setState(true);
		List<TreeData> nodeDataList = new ArrayList<>();
		try{
			SearchRequestBuilder searchRequestBuilder = getClient().prepareSearch(
					tableName).setQuery(qb);
			
			if (termsBuilder != null) {
				searchRequestBuilder.addAggregation(termsBuilder);
				
				SearchResponse scrollResp = searchRequestBuilder.execute().actionGet();
				if (scrollResp.getAggregations() == null) {
					return null;
				}
				Map<String, Aggregation> aggMap = scrollResp.getAggregations()
						.asMap();
				
				if (glen > 0) {
					String gtmp = rootArr[0];
					
					Aggregation aggr = aggMap.get(gtmp);
					StatisticsUtils.takeTermTreeData(aggr, rootArr, 0, nodeDataList, 0);
				}
			}
			//封装树数据
			result = setTreeData(nodeDataList);
			//重置自增id为1
			new TreeData().setIncreamentId(1);
			
			
		}catch(Exception e ){
			e.printStackTrace();
			result.setMessage(" elasticsearch exception : " + e.getMessage());
			result.setState(false);
			return result;
		}
		
		return result;
	}
	/**
	 * 封装数据树
	 * @param nodeDataList
	 * @return
	 */
	private ResultEntity setTreeData(List<TreeData> nodeDataList) {
		ResultEntity result = new ResultEntity();
		result.setState(true);
		if(CollectionUtils.isEmpty(nodeDataList)){
			result.setMessage("没有数据");
			return result;
		}
		List<Tree<TreeData>> trees = new ArrayList<Tree<TreeData>>();
		for (TreeData treeData : nodeDataList) {
            Tree<TreeData> tree = new Tree<TreeData>();
            tree.setId(treeData.getId());
            tree.setParentId(treeData.getPid());
            tree.setText(treeData.getText());
            tree.setCount(treeData.getCount());
            tree.setName(treeData.getName());
            trees.add(tree);
        }

        Tree<TreeData> TreeResult = BuildTree.build(trees);
        result.setResult(TreeResult);
		return result;
	}
	
	/**
	 * 封装排除条件
	 * @param exclusiveConditon
	 * @param qb
	 */
	private void setExclusiveConditon(JSONObject exclusiveConditon,
			BoolQueryBuilder boolQueryBuilder) {
		if (null == exclusiveConditon) {
			return ;
		}
		try {

			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) JSONObject.toBean(
					exclusiveConditon, Map.class);

			if (!MapUtils.isEmpty(map)) {
				for (String key : map.keySet()) {
					boolQueryBuilder = boolQueryBuilder.mustNot(QueryBuilders
							.matchPhraseQuery(key + "_group", map.get(key)));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(" josn switch exception:" + e.getMessage());
		}
		
	}
}
