package com.ssitcloud.business.statistics.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.business.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.statistics.common.utils.HttpClientUtil;
import com.ssitcloud.business.statistics.common.utils.JsonUtils;
import com.ssitcloud.business.statistics.common.utils.Tree;
import com.ssitcloud.business.statistics.common.utils.TreeData;
import com.ssitcloud.business.statistics.service.CommonEsStatisticService;
import com.ssitcloud.business.statistics.service.ElasticsearchStatisticsService;
import com.ssitcloud.business.statistics.service.LoanLogService;
import com.ssitcloud.business.statistics.service.RecommendService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.ReaderCardEntity;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;
import com.ssitcloud.statistics.entity.BookRankRoleEntity;

@Service
public class RecommendServiceImpl extends BasicServiceImpl implements RecommendService {
	@Resource
	ElasticsearchStatisticsService elasticsearchStatisticsService;
	@Resource
	CommonEsStatisticService commonEsStatisticService;
	@Resource
	private LoanLogService loanLogService;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private GregorianCalendar gc = new GregorianCalendar();
	private String format = "yyyyMMddHHmmss";
	private static Client client = null;
	private JedisUtils redis = JedisUtils.getInstance();

	/**
	 * 推荐服务
	 * 
	 * 假设我们知道读者所在图书馆、读者年龄、读者卡号、读者性别，读者手机号， 
	 * 由此可以查询该读者符合图书馆定义的推荐规则中的topN的图书分类， 
	 * 然后算出年龄段， 把年龄段、性别、图书分类传入ES查询符合条件的，
	 * 并剔除该读者的借还排行榜记录， 把排行榜的书名与馆藏库进行比对，
	 * 剔除不在馆的图书， 根据推荐规则形成推荐列表保存到Redis，
	 * 并推荐到手机app或手机短信
	 * 
	 * @param param
	 */
	public ResultEntity recommend(Map<String, String> param) {
		ResultEntity resultEntity = new ResultEntity();
		if (!param.containsKey("cardNo") || !param.containsKey("library")) {
			resultEntity.setState(false);
			resultEntity.setMessage("参数不完整");
			resultEntity.setRetMessage("请检查是否有【library】和【cardNo】两个参数");
			return resultEntity;
		}
		// 读者卡号
		String cardNo = param.get("cardNo");
		// 图书馆idx
		String library_idx = param.get("library_idx");
		// 图书馆id
		String library_id = param.get("library");

		if (library_id == null || library_idx == null) {
			Map<String, String> map = new HashMap<String, String>();
			JSONObject lib_param = new JSONObject();
			if (library_idx != null) {
				lib_param.put("library_idx", library_idx);
				map.put("json", lib_param.toString());
			} else if (library_id != null) {
				lib_param.put("lib_id", library_id);
				map.put("json", lib_param.toString());
			}
			String resp = HttpClientUtil.doPost(requestURL.getRequestURL("selLibraryByIdxOrId"), map, Consts.UTF_8.toString());
			ResultEntity libResultEntity = JsonUtils.fromJson(resp, ResultEntity.class);
			if (libResultEntity.getState()) {
				LibraryEntity libraryInfo = JsonUtils.fromJson(JsonUtils.toJson(libResultEntity.getResult()), LibraryEntity.class);
				if (libraryInfo != null) {
					library_id = libraryInfo.getLib_id();
					library_idx = libraryInfo.getLibrary_idx() + "";
				}
			}
		}

		if (library_idx == null) {
			resultEntity.setState(false);
			resultEntity.setMessage("获取图书馆主键失败");
			return resultEntity;
		}

		if ("LZULIB".equals(library_id)) {// 兰州大学的实际卡号 = 卡号除去最后一位
			cardNo = cardNo.substring(0, cardNo.length() - 1);
			param.put("cardNo", cardNo);
		}

		// 向mysql数据库查询读者卡信息
		JSONObject params = new JSONObject();
		params.put("actual_card_no", cardNo);// 实际卡号
		params.put("lib_idx", library_idx);
		// 查询读者信息
		ResultEntity readerResultEntity = loanLogService.selectReaderCardByParams(params.toString());
		ReaderCardEntity readerCard = null;
		if (readerResultEntity.getState()) {
			List<LinkedHashMap<String, String>> readerCards = JsonUtils.fromJson(JsonUtils.toJson(readerResultEntity.getResult()), List.class);
			if (readerCards != null && !readerCards.isEmpty()) {
				LinkedHashMap<String, String> val = readerCards.get(0);
				readerCard = JsonUtils.fromJson(JsonUtils.toJson(val), ReaderCardEntity.class);
			}
			params.clear();
		}
		if (readerCard == null) {
			resultEntity.setState(false);
			resultEntity.setMessage("无法查询到读者信息");
			return resultEntity;
		}

		Date birth = readerCard.getBirth();
		int age = 0;
		if (birth != null) {
			age = getAge(birth);
		}
		// 年龄段
		String ageGroup = getAgeGroup(age);
		param.put("age", ageGroup);

		// 性别
		String gender = readerCard.getGender();
		param.put("gender", gender);

		// 读者手机号，发送短信使用
		String mobile = readerCard.getMobile();

		// 计算半年的借还数据
		String start_time = getTime() + "000000";
		String end_time = sdf.format(new Date()) + "235959";
		param.put("start_time", start_time);
		param.put("end_time", end_time);

		// 获取推荐规则
		int topN = 2, newScale = 5, oldScale = 5;
		double baseLine = 0.5;
		JSONObject lib_idx_json = new JSONObject();
		lib_idx_json.put("library_idx", library_idx);
		Map<String, String> roleParam = new HashMap<String, String>();
		roleParam.put("req", lib_idx_json.toString());
		ResultEntity entity = findRankRoleByParam(roleParam);
		if (entity.getState()) {
			BookRankRoleEntity rankRole = null;
			JSONObject contentObj = null;
			if (null == entity.getResult() || "".equals(entity.getResult())) {
				System.out.println("没有配置推荐规则,将使用默认规则");
			}else{
				rankRole = JsonUtils.fromJson(JsonUtils.toJson(entity.getResult()), BookRankRoleEntity.class);
				contentObj = JSONObject.fromObject(rankRole.getRole_content());
				topN = contentObj.optInt("topN", 2);// 前几个分类
				newScale = contentObj.optInt("newScale", 5);// 新书比例
				oldScale = contentObj.optInt("oldScale", 5);// 经典书比例
			}
		}
		baseLine = (double) newScale / (double) (newScale + oldScale);

		// 用户画像
		JSONObject userProfile = getUserProfile(param, "callNumber,Title", topN);
		boolean state = userProfile.optBoolean("state");
		String callNoStr = "";
		List<String> books = null;
		if (state) {
			JSONObject userInfo = userProfile.optJSONObject("result");

			JSONArray callNos = userInfo.optJSONArray("callNo");
			JSONArray array = userInfo.optJSONArray("bookList");
			books = (List<String>) array.toCollection(array, List.class);
			callNoStr = array2String(callNos);

		}

		// 图书推荐列表
		param.put("callNo", callNoStr);// 22大类分类号
		param.put("library_idx", library_idx);
		JSONArray rankLoanList = rankLoanByPararm(param);
		if (rankLoanList == null || rankLoanList.isEmpty()) {
			resultEntity.setState(false);
			resultEntity.setMessage("无法查询到借阅排行榜信息");
			return resultEntity;
		}

		int count = topN * 5;
		int newBookSize = Integer.parseInt(Math.round(count * baseLine) + "");// 按照新书比例计算出一共取多少本新书
		int oldBookSize = count - newBookSize;// 推荐总数减去新书数量算出经典书数量

		List<String> tempList = new ArrayList<String>();
		Set<String> newBookList = new LinkedHashSet<String>();
		Set<String> oldBookList = new LinkedHashSet<String>();
		for (int i = 0; i < rankLoanList.size(); i++) {
			JSONObject obj = rankLoanList.optJSONObject(i);
			JSONArray bookArray = obj.optJSONArray("bookList");
			if (bookArray == null || bookArray.isEmpty()) {
				resultEntity.setState(false);
				resultEntity.setMessage("没有推荐信息");
				return resultEntity;
			}

			for (int j = 0; j < bookArray.size(); j++) {
				String name = bookArray.optJSONObject(j).optString("name").trim();
				Map<String, String> map = new HashMap<String, String>();
				map.put("title", name);
				map.put("lib_idx", library_idx);
				map.put("state", "1");// 在架 (图书状态 0-已分派,1-已上架,2-已借出,3-已下架)
				ResultEntity resultEn = postURL("queryBookInfoForRecommend", JsonUtils.toJson(map));// 获取馆藏数据
				if (resultEn.getState()) {// 查询数据
					List<LinkedHashMap<String, Object>> list = JsonUtils.fromJson(JsonUtils.toJson(resultEn.getResult()), List.class);
					if(list.isEmpty()){
						tempList.add(name);
					}
					for (LinkedHashMap<String, Object> item : list) {
						Object newArrival = item.get("newArrival");
						boolean isNewArrival = Boolean.parseBoolean(String.valueOf(newArrival));// 判断是否是新书（一个礼拜内上架的表示新书）
						if (isNewArrival) {// 新书
							newBookList.add(name);
						} else {// 旧书
							oldBookList.add(name);
						}
					}
				}else{
					tempList.add(name);
				}
			}
		}
		List<String> recommendBookList = null;
		if (newBookList.isEmpty() && oldBookList.isEmpty() && !tempList.isEmpty()) {
			recommendBookList = new ArrayList<String>();
			recommendBookList.addAll(tempList.subList(0, count));
		} else {
			List<String> newList = receiveDefectList(newBookList, books);
			List<String> oldList = receiveDefectList(oldBookList, books);
			recommendBookList = getList(newList,oldList,count,newBookSize,oldBookSize);
		}
		
		if (recommendBookList.size() > 0) {
			// 修改by XieBaofu @20180511 把发送短信迁移到view层直接发送
			JSONObject info = new JSONObject();
			info.put("cardNo",cardNo);
			info.put("mobile", mobile);
			info.put("bookList", recommendBookList);

			resultEntity.setState(true);
			resultEntity.setResult(info);

			//this.sendSMS(mobile,recommendBookList);//发送短信
		} else {
			resultEntity.setState(false);
			resultEntity.setMessage("没有推荐信息");
		}
		return resultEntity;

	}

	/**
	 * 根据条件获取图书馆借还排行榜
	 * 
	 * @param param
	 * @return
	 */
	private JSONArray rankLoanByPararm(Map<String, String> param) {
		if (client == null)
			client = ElasticsearchStatisticsServiceImpl.getClient();
		String library_idx = param.get("library_idx");
		String operresult = param.get("operresult") == null ? "0" : param.get("operresult");
		String cardNo = param.get("cardNo");// 卡号
		String gender = param.get("gender");// 性别
		String[] ages = string2Array(param.get("age"));// 年龄段
		String[] callNos = string2Array(param.get("callNo"));// 22大类的图书分类

		int rows = param.get("rows") == null ? 50 : Integer.parseInt(param.get("rows"));

		// 查询时间
		String start_time = param.get("start_time");
		String end_time = param.get("end_time");

		// 索引信息
		SearchRequestBuilder responsebuilder = client.prepareSearch("*_loan_log");
		// 分组信息
		TermsBuilder aggregation = AggregationBuilders.terms("agg").field("callNumber_group");
		TermsBuilder dhb = AggregationBuilders.terms("subAgg").field("Title_group").size(rows);
		aggregation.subAggregation(dhb);

		responsebuilder.addAggregation(aggregation);

		BoolQueryBuilder builder = QueryBuilders.boolQuery();// 条件组合
		if (StringUtils.hasText(library_idx) && !"0".equals(library_idx)) {// 条件1

			if (library_idx.indexOf(",") != -1) {
				BoolQueryBuilder builder_ = QueryBuilders.boolQuery();
				String[] idxs = library_idx.split("\\,");// 有子馆的
				for (String idx : idxs) {
					builder_ = builder_.should(QueryBuilders.matchPhraseQuery("library_idx_group", idx));
				}
				builder = builder.filter(builder_);
			} else {
				builder = builder.filter(QueryBuilders.matchPhraseQuery("library_idx_group", library_idx));
			}
		}
		
		builder = builder/* .filter(QueryBuilders.matchPhraseQuery("opercmd_group", "00010201")) */.filter(QueryBuilders.matchPhraseQuery("operresult_group", operresult));// 条件2--借书成功记录
		builder = builder.mustNot(QueryBuilders.matchPhraseQuery("Title_group", "查询不到图书"));// 条件3--过滤查不到书目的记录

		if (StringUtils.hasText(gender)) {// 条件5--性别
			BoolQueryBuilder builder_ = QueryBuilders.boolQuery().should(QueryBuilders.matchPhraseQuery("Gender_group", "")).should(QueryBuilders.matchPhraseQuery("Gender_group", gender));
			builder = builder.should(builder_);
		}
		if (ages != null && StringUtils.hasText(ages[0]) && StringUtils.hasText(ages[1])) {// 条件6--年龄段
			BoolQueryBuilder builder_ = QueryBuilders.boolQuery()
				.should(QueryBuilders.matchPhraseQuery("peopleAge", ""))
			    .should(QueryBuilders.rangeQuery("peopleAge").from(ages[0]).to(ages[1]));
			builder = builder.should(builder_);
		}
		
		builder = builder.mustNot(QueryBuilders.matchPhraseQuery("operator_group", cardNo));// 条件7--过滤读者的借还记录
		builder = builder.filter(QueryBuilders.rangeQuery("opertime").format(format).from(start_time).to(end_time));// 条件8--时间条件

		if (callNos != null && callNos.length > 1) {
			BoolQueryBuilder builder_ = QueryBuilders.boolQuery();
			for (String callNo : callNos) {
				builder_ = builder_.should(QueryBuilders.matchPhraseQuery("callNumber_group", callNo));// 条件8--过滤符合图书分类的记录
			}
			builder = builder.filter(builder_);
		}

		responsebuilder.setQuery(builder).setSearchType(SearchType.COUNT);
		SearchResponse response = responsebuilder.setExplain(true).execute().actionGet();

		Terms agg = response.getAggregations().get("agg");
		int size = agg.getBuckets().size();
		if (size == 0) {
			return new JSONArray();
		}

		JSONArray msg = new JSONArray();
		for (Terms.Bucket abt : agg.getBuckets()) {
			JSONObject callNo = new JSONObject();
			callNo.put("callNo", abt.getKey());
			Terms ageTerms = abt.getAggregations().get("subAgg");
			JSONArray bookList = new JSONArray();
			for (Terms.Bucket bt : ageTerms.getBuckets()) {
				JSONObject book = new JSONObject();
				book.put("name", bt.getKey());
				book.put("count", bt.getDocCount());
				bookList.add(book);
			}
			callNo.put("bookList", bookList);
			msg.add(callNo);
		}
		return msg;
	}

	/**
	 * 获取用户画像
	 * 
	 * @param param
	 * @return
	 */
	private JSONObject getUserProfile(Map<String, String> param, String groupCondition, int topN) {
		JSONObject obj = new JSONObject();
		if (!param.containsKey("library_idx") && !param.containsKey("library")) {
			obj.put("state", false);
			obj.put("message", "参数传入错误");
			return obj;
		}
		obj.clear();
		String library_idx = param.get("library_idx");
		String library = param.get("library");
		String reader_cardNo = param.get("cardNo");
		obj.put("indexName", "*_loan_log");

		obj.put("groupCondition", groupCondition);// 分组callNumber,

		JSONObject searchCondition = new JSONObject();// 查询条件
		if (StringUtils.hasText(library_idx))
			searchCondition.put("library_idx", library_idx);
		if (StringUtils.hasText(library))
			searchCondition.put("lib_id", library);
		// searchCondition.put("opercmd", "00010201");// 借书
		searchCondition.put("operresult", "0");// 操作成功的数据
		searchCondition.put("operator", reader_cardNo);// 读者卡号
		obj.put("searchCondition", searchCondition);
		obj.put("topHits", 0);
		JSONObject timeConditin = new JSONObject();// 时间条件
		// 取消时间限制
		// timeConditin.put("field", "opertime");
		// String start_time = param.get("start_time");
		// String end_time = param.get("end_time");
		// if(StringUtils.hasText(start_time))timeConditin.put("start", start_time);
		// if(StringUtils.hasText(end_time))timeConditin.put("end", end_time);
		// if(StringUtils.hasText(start_time)&&StringUtils.hasText(end_time))timeConditin.put("format", format);
		obj.put("timeConditin", timeConditin);
		ResultEntity entity = commonEsStatisticService.getData(obj.toString());
		if (entity.getState()) {
			Tree<TreeData> object = (Tree<TreeData>) entity.getResult();
			if (object != null) {
				List<Tree<TreeData>> list = object.getChildren();
				List<String> callNoList = new ArrayList<String>();
				Set<String> bookList = new LinkedHashSet<String>();
				for (Tree<TreeData> data : list) {
					callNoList.add(data.getText());
					List<Tree<TreeData>> childrenList = data.getChildren();
					if (childrenList != null && !childrenList.isEmpty()) {
						for (Tree<TreeData> children : childrenList) {
							String text = children.getText();
							bookList.add(text.trim());
						}
					}
				}
				obj.clear();
				if (!callNoList.isEmpty() && !bookList.isEmpty()) {
					obj.put("state", true);
					JSONObject userProfile = new JSONObject();
					callNoList = topN < callNoList.size() ? callNoList.subList(0, topN) : callNoList;
					userProfile.put("callNo", callNoList);
					userProfile.put("bookList", bookList);
					obj.put("result", userProfile);
				} else {
					obj.put("state", false);
					obj.put("message", "查询没有数据");
				}
			}
		}
		return obj;
	}

	@Override
	public ResultEntity editRankRole(Map<String, String> map) {
		String resp = HttpClientUtil.doPost(requestURL.getRequestURL("editRankRole"), map, Consts.UTF_8.toString());
		ResultEntity result = null;
		if (JSONUtils.mayBeJSON(resp)) {
			result = JsonUtils.fromJson(resp, ResultEntity.class);
		} else {
			result = new ResultEntity();
			result.setState(false);
			result.setMessage("编辑图书推荐规则失败");
		}
		return result;
	}

	@Override
	public ResultEntity deleteRankRole(Map<String, String> map) {
		String resp = HttpClientUtil.doPost(requestURL.getRequestURL("deleteRankRole"), map, Consts.UTF_8.toString());
		ResultEntity result = null;
		if (JSONUtils.mayBeJSON(resp)) {
			result = JsonUtils.fromJson(resp, ResultEntity.class);
		} else {
			result = new ResultEntity();
			result.setState(false);
			result.setMessage("删除图书推荐规则失败");
		}
		return result;
	}

	@Override
	public ResultEntity findRankRoleByIdx(Map<String, String> map) {
		String resp = HttpClientUtil.doPost(requestURL.getRequestURL("findRankRoleByIdx"), map, Consts.UTF_8.toString());
		ResultEntity result = null;
		if (JSONUtils.mayBeJSON(resp)) {
			result = JsonUtils.fromJson(resp, ResultEntity.class);
		} else {
			result = new ResultEntity();
			result.setState(false);
			result.setMessage("查询图书推荐规则失败");
		}
		return result;
	}

	public ResultEntity findRankRoleByParam(Map<String, String> map) {
		String resp = HttpClientUtil.doPost(requestURL.getRequestURL("findRankRoleByParam"), map, Consts.UTF_8.toString());
		ResultEntity result = null;
		if (JSONUtils.mayBeJSON(resp)) {
			result = JsonUtils.fromJson(resp, ResultEntity.class);
		} else {
			result = new ResultEntity();
			result.setState(false);
			result.setMessage("查询图书推荐规则失败");
		}
		return result;
	}

	@Override
	public ResultEntity findRankRoleList(Map<String, String> map) {
		String resp = HttpClientUtil.doPost(requestURL.getRequestURL("findRankRoleList"), map, Consts.UTF_8.toString());
		ResultEntity result = null;
		if (JSONUtils.mayBeJSON(resp)) {
			result = JsonUtils.fromJson(resp, ResultEntity.class);
		} else {
			result = new ResultEntity();
			result.setState(false);
			result.setMessage("查询图书推荐规则失败");
		}
		return result;
	}

	/**
	 * 获取半年前的今天
	 * 
	 * @return
	 */
	private String getTime() {
		gc.setTime(new Date()); // 设置时间为当前时间
		gc.add(Calendar.YEAR, -1); // 去年
		return sdf.format(gc.getTime());
	}

	/**
	 * 知道出生日期算年龄
	 * 
	 * @param birthDay
	 * @return
	 */
	private int getAge(Date birthDay) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			// 错误
			return -1;
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth)
					age--;
			} else {
				age--;
			}
		}
		return age;
	}

	/**
	 * 根据年龄算出年龄段
	 * 
	 */
	private String getAgeGroup(int age) {
		int[] ageGroup = new int[2];
		if (0 < age && age <= 10) {
			ageGroup[0] = 1;
			ageGroup[1] = 10;
		} else if (10 < age && age <= 20) {
			ageGroup[0] = 10;
			ageGroup[1] = 20;
		} else if (20 < age && age <= 30) {
			ageGroup[0] = 20;
			ageGroup[1] = 30;
		} else if (30 < age && age <= 40) {
			ageGroup[0] = 30;
			ageGroup[1] = 40;
		} else if (40 < age && age <= 50) {
			ageGroup[0] = 40;
			ageGroup[1] = 50;
		} else if (50 < age && age <= 60) {
			ageGroup[0] = 50;
			ageGroup[1] = 60;
		} else if (60 < age && age <= 70) {
			ageGroup[0] = 60;
			ageGroup[1] = 70;
		} else if (70 < age && age <= 80) {
			ageGroup[0] = 70;
			ageGroup[1] = 80;
		} else if (80 < age && age <= 90) {
			ageGroup[0] = 80;
			ageGroup[1] = 90;
		} else if (90 < age && age <= 100) {
			ageGroup[0] = 90;
			ageGroup[1] = 99;
		} else {
			ageGroup[0] = 1;
			ageGroup[1] = 99;
		}
		return ageGroup[0] + "," + ageGroup[1];
	}

	/**
	 * 转换数组为字符串
	 * 
	 * @param array
	 * @return
	 */
	private String array2String(JSONArray array) {
		if (array != null) {
			String result = "";
			for (int i = 0; i < array.size(); i++) {
				result += array.optString(i) + ",";
			}
			if (result.endsWith("\\,"))
				result = result.substring(0, result.length() - 1);
			return result;
		}
		return "";
	}

	/**
	 * 转换数组为字符串
	 * 
	 * @param array
	 * @return
	 */
	private String[] string2Array(String text) {
		String[] array = null;
		if (StringUtils.hasText(text)) {
			if (text.indexOf(",") != -1) {
				array = text.split("\\,");
			} else {
				array = new String[1];
				array[0] = text;
			}
		}
		return array;
	}

	/**
	 * @方法描述：获取两个ArrayList的差集
	 * @param firstList
	 *            第一个ArrayList
	 * @param secondList
	 *            第二个ArrayList
	 * @return resultList 差集ArrayList
	 */
	private List<String> receiveDefectList(Set<String> firstList, List<String> secondList) {
		if (firstList == null || firstList.isEmpty()) {
			return new LinkedList<String>();
		}
		LinkedList<String> result = new LinkedList<String>(firstList);// 大集合用linkedlist
		if (secondList == null || secondList.isEmpty()) {
			return result;
		}
		Set<String> set = new LinkedHashSet<String>(secondList);
		Iterator<String> iter = result.iterator();// 采用Iterator迭代器进行数据的操作
		while (iter.hasNext()) {
			if (set.contains(iter.next())) {
				iter.remove();
			}
		}
		return result;
	}
	
	/**
	 * 从两个列表中获取一定量的数据
	 * @param newList
	 * @param oldList
	 * @param total
	 * @param newSize
	 * @param oldSize
	 * @return
	 */
	private List<String> getList(List<String> newList, List<String> oldList, int total, int newSize, int oldSize) {
		List<String> list = new ArrayList<String>();
		if (newList.size() > newSize) {
			list.addAll(newList.subList(0, newSize));
			newList = newList.subList(newSize, newList.size());//剩余多少本
			newSize = 0;
		} else {
			list.addAll(newList);
			newSize = newSize - newList.size();//需要补多少本
			newList.clear();
		}
		if (oldList.size() > oldSize) {
			list.addAll(oldList.subList(0, oldSize));
			oldList = oldList.subList(oldSize, oldList.size());//剩余多少本
			oldSize = 0;
		} else {
			list.addAll(oldList);
			oldSize = oldSize - oldList.size();//需要补多少本
			oldList.clear();
		}

		if (list.size() < total) {
			if (oldList.size() > 0 && newSize > 0)
				list.addAll(oldList.subList(0, newSize));
			if (newList.size() > 0 && oldSize > 0)
				list.addAll(newList.subList(0, oldSize));
		}
		return list;
	}

	
	public static void main(String[] args) {
		Set<String> newList = new LinkedHashSet<String>();
		newList.add("朱自清文集01");
		newList.add("朱自清文集02");
		newList.add("朱自清文集03");
		newList.add("朱自清文集04");
		newList.add("人间草木01");
		newList.add("朱自清文集06");
		newList.add("丑陋的中国人");
		newList.add("朱自清文集05");
		newList.add("朱自清文集08");

		Set<String> oldList = new LinkedHashSet<String>();
		oldList.add("朱自清文集11");
		oldList.add("朱自清文集12");
		oldList.add("朱自清文集13");
		oldList.add("朱自清文集14");
		oldList.add("人间草木02");
		oldList.add("朱自清文集16");
		oldList.add("丑陋的中国人");
		oldList.add("朱自清文集15");
		oldList.add("朱自清文集18");
		
		List<String> books = new ArrayList<String>();
		books.add("朱自清文集11");
		books.add("人间草木02");
		books.add("朱自清文集13");
		
		RecommendServiceImpl impl = new RecommendServiceImpl();
		List<String> newBookList = impl.receiveDefectList(newList, books);
		System.out.println(JsonUtils.toJson(newBookList));
		List<String> oldBookList = impl.receiveDefectList(oldList, books);
		System.out.println(JsonUtils.toJson(oldBookList));

		List<String> list = impl.getList(newBookList, oldBookList, 10, 5, 5);
		System.out.println(JsonUtils.toJson(list));
	}
}
