package com.ssit.service.impl;

import java.text.DateFormat;
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
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ssit.Entity.BookEntity;
import com.ssit.Entity.CertificateEntity;
import com.ssit.Entity.CirculateEntity;
import com.ssit.Entity.CirculateTempEntity;
import com.ssit.Entity.FinanceEntity;
import com.ssit.Entity.LibInfoEntity;
import com.ssit.Entity.PatronEntity;
import com.ssit.Entity.PatronTempEntity;
import com.ssit.Entity.ReaderEntity;
import com.ssit.common.entity.DateStyle;
import com.ssit.common.entity.ResultEntity;
import com.ssit.dao.DisplayDao;
import com.ssit.service.DisplayService;
import com.ssit.util.DateUtil;
import com.ssit.util.HttpClientUtil;
import com.ssit.util.JsonUtils;
import com.ssit.util.PropertiesUtil;

@Service
public class DisplayServiceImpl implements DisplayService {
	private static Logger logger = LoggerFactory.getLogger(DisplayServiceImpl.class);
	//查询cloudbusinessservice_statistics
	private static final String URL_borrowOrderInfo = "/thirdPartyAPI/borrowOrderInfo";// 借阅排行榜
	private static final String URL_borrowBackCountInfo = "/thirdPartyAPI/borrowBackCountInfo";// 流通统计
	private static final String URL_cameCountInfo = "/thirdPartyAPI/cameCountInfo";// 人流量统计(进、在馆)
	private static final String URL_readerRank = "/thirdPartyAPI/readerRank";// 读者借阅排行榜
	private static final String URL_countCertificate = "/thirdPartyAPI/countCertificate";// 办证统计
	private static final String URL_countLoanForBar = "/thirdPartyAPI/countLoanForBar";// 流通分时统计
	private static final String URL_countRealTimeVisitor = "/thirdPartyAPI/countRealTimeVisitor";// 人流量实时统计
	private static final String URL_loadLibInfo = "/thirdPartyAPI/loadLibInfo";// 人流量实时统计
	//查询cloudbusinessservice_device
	private static final String URL_queryThirdPartyService = "/thirdPartyService/queryThirdPartyServiceByParams";// 查询第三方服务相关
	private static final String URL_queryDisplayInfo = "/thirdPartyService/queryDisplayInfo";// 查询大屏展示相关

	private String api_url = PropertiesUtil.getPropertiesValue("api_url");
	private String server_url = PropertiesUtil.getPropertiesValue("server_url");

	@Resource
	DisplayDao displayDao;

	/**
	 * 查询办证数据
	 */
	@Override
	public ResultEntity countCertificateByParam(Map<String, String> param) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			StringBuilder sb = new StringBuilder(api_url);
			sb.append(URL_countCertificate);
			getTime(param, sb);

			String type = param.get("type");
			String response = HttpClientUtil.sendHttpGet(sb.toString());
			if (logger.isInfoEnabled()) {
				logger.info("查询{}办证数据访问返回结果:{}", type, response);
			}
			if (JSONUtils.mayBeJSON(response)) {
				JSONObject obj = JSONObject.fromObject(response);
				int result = obj.optInt("result");
				if (1 == result) {
					long total = obj.optLong("total");
					CertificateEntity entity = new CertificateEntity();
					entity.setTotal(total);
					entity.setType(type);

					resultEntity.setState(true);
					resultEntity.setResult(entity);
					return resultEntity;
				}
			}
		} catch (Exception e) {
			logger.error("查询办证数据出错啦:", e);
		}
		resultEntity.setState(false);
		resultEntity.setMessage("查询办证数据失败");
		return resultEntity;
	}

	/**
	 * 查询流通数据
	 */
	@Override
	public ResultEntity countCirculateByParam(Map<String, String> param) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			StringBuilder sb = new StringBuilder(api_url);
			sb.append(URL_borrowBackCountInfo);
			getTime(param, sb);

			String type = param.get("type");
			String response = HttpClientUtil.sendHttpGet(sb.toString());
			if (logger.isInfoEnabled()) {
				logger.info("查询{}流通数据访问返回结果:{}", type, response);
			}
			if (JSONUtils.mayBeJSON(response)) {
				JSONObject obj = JSONObject.fromObject(response);
				int result = obj.optInt("result");
				if (1 == result) {
					CirculateEntity entity = new CirculateEntity();
					entity.setType(type);
					entity.setTime("");

					long borrow_count = obj.optLong("borrow_count");
					long back_count = obj.optLong("back_count");
					long renew_count = obj.optLong("renew_count");

					entity.setType1_total(borrow_count);
					entity.setType2_total(back_count);
					entity.setType3_total(renew_count);

					resultEntity.setState(true);
					resultEntity.setResult(entity);
					return resultEntity;
				}
			}
		} catch (Exception e) {
			logger.error("查询流通数据出错啦:", e);
		}
		resultEntity.setState(false);
		resultEntity.setMessage("查询流通数据失败");
		return resultEntity;
	}

	/**
	 * 查询流通数据并显示成柱状图
	 */
	@Override
	public ResultEntity countCirculateForBar(Map<String, String> param) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			StringBuilder sb = new StringBuilder(api_url);
			sb.append(URL_countLoanForBar);
			getTime(param, sb);

			String response = HttpClientUtil.sendHttpGet(sb.toString());
			if (logger.isInfoEnabled()) {
				logger.info("查询实时流通数据访问返回结果:{}", response);
			}
			if (JSONUtils.mayBeJSON(response)) {
				JSONObject obj = JSONObject.fromObject(response);
				int result = obj.optInt("result");
				if (1 == result) {
					CirculateTempEntity entity = new CirculateTempEntity();

					JSONArray date = obj.optJSONArray("date");
					JSONArray borrow_count = obj.optJSONArray("borrow_count");
					JSONArray back_count = obj.optJSONArray("back_count");
					JSONArray renew_count = obj.optJSONArray("renew_count");

					if (date != null)
						entity.setDate(date.toArray());
					if (borrow_count != null)
						entity.setType1_total(borrow_count.toArray());
					if (back_count != null)
						entity.setType2_total(back_count.toArray());
					if (renew_count != null)
						entity.setType3_total(renew_count.toArray());

					resultEntity.setState(true);
					resultEntity.setResult(entity);
					return resultEntity;
				}
			}
		} catch (Exception e) {
			logger.error("查询流通数据出错啦:", e);
		}
		resultEntity.setState(false);
		resultEntity.setMessage("查询流通数据失败");
		return resultEntity;
	}

	/**
	 * 查询财经数据
	 */
	@Override
	public ResultEntity countFinanceByParam(Map<String, String> param) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			StringBuilder sb = new StringBuilder(api_url);
			sb.append("");
			getTime(param, sb);

			String type = param.get("type");
			String response = HttpClientUtil.sendHttpGet(sb.toString());
			if (logger.isInfoEnabled()) {
				logger.info("查询{}财经数据访问返回结果:{}", type, response);
			}
			if (JSONUtils.mayBeJSON(response)) {
				JSONObject obj = JSONObject.fromObject(response);
				int result = obj.optInt("result");
				if (1 == result) {
					FinanceEntity entity = new FinanceEntity();
					entity.setFinType(type);
					entity.setName("");
					entity.setTotal(0);

					resultEntity.setState(true);
					resultEntity.setResult(entity);
					return resultEntity;
				}
			}
		} catch (Exception e) {
			logger.error("查询财经数据出错啦:", e);
		}
		resultEntity.setState(false);
		resultEntity.setMessage("查询财经数据失败");
		return resultEntity;
	}

	/**
	 * 查询图书借阅排行榜
	 */
	@Override
	public ResultEntity bookRankByParam(Map<String, String> param) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String rows = param.get("rows");
			StringBuilder sb = new StringBuilder(api_url);
			sb.append(URL_borrowOrderInfo);
			getTime(param, sb);
			if (StringUtils.isNotEmpty(rows)) {
				sb.append("&rows=").append(rows);
			}

			List<BookEntity> all = new ArrayList<BookEntity>();
			String response = HttpClientUtil.sendHttpGet(sb.toString());
			if (logger.isInfoEnabled()) {
				logger.info("图书借阅排行榜访问返回结果:{}", response);
			}

			if (JSONUtils.mayBeJSON(response)) {
				JSONObject obj = JSONObject.fromObject(response);
				int result = obj.optInt("result");
				if (1 == result) {
					JSONArray arr = obj.optJSONArray("msg");
					for (int i = 0; i < arr.size(); i++) {
						JSONObject o = arr.getJSONObject(i);
						String bookName = o.optString("book_name");
						long total = o.optLong("bnum");
						BookEntity entity = new BookEntity();
						entity.setName(bookName);
						entity.setTotal(total);
						all.add(entity);
					}

					resultEntity.setState(true);
					resultEntity.setResult(all);
					return resultEntity;
				}
			}
		} catch (Exception e) {
			logger.error("查询图书借阅排行榜出错啦:", e);
		}
		resultEntity.setState(false);
		resultEntity.setMessage("查询图书借阅排行榜失败");
		return resultEntity;
	}

	/**
	 * 查询读者借阅排行榜
	 */
	@Override
	public ResultEntity readerRankByParam(Map<String, String> param) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String rows = param.get("rows");
			StringBuilder sb = new StringBuilder(api_url);
			sb.append(URL_readerRank);
			getTime(param, sb);
			if (StringUtils.isNotEmpty(rows)) {
				sb.append("&rows=").append(rows);
			}

			List<ReaderEntity> all = new ArrayList<ReaderEntity>();
			String response = HttpClientUtil.sendHttpGet(sb.toString());
			if (logger.isInfoEnabled()) {
				logger.info("读者借阅排行榜访问返回结果:{}", response);
			}
			if (JSONUtils.mayBeJSON(response)) {
				JSONObject obj = JSONObject.fromObject(response);
				int result = obj.optInt("result");
				if (1 == result) {
					JSONArray arr = obj.optJSONArray("msg");
					for (int i = 0; i < arr.size(); i++) {
						JSONObject o = arr.getJSONObject(i);
						String name = o.optString("name");
						String cardNo = o.optString("cardno");
						long total = o.optLong("bnum");
						ReaderEntity entity = new ReaderEntity();
						entity.setName(name);
						entity.setCardNo(cardNo);
						entity.setTotal(total);
						all.add(entity);
					}

					resultEntity.setState(true);
					resultEntity.setResult(all);
					return resultEntity;
				}
			}
		} catch (Exception e) {
			logger.error("查询读者借阅排行榜出错啦:", e);
		}
		resultEntity.setState(false);
		resultEntity.setMessage("查询读者借阅排行榜失败");
		return resultEntity;
	}

	/**
	 * 人流量统计
	 */
	@Override
	public ResultEntity countPatronByParam(Map<String, String> param) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			StringBuilder sb = new StringBuilder(api_url);
			sb.append(URL_cameCountInfo);
			getTime(param, sb);

			String type = param.get("type");
			String response = HttpClientUtil.sendHttpGet(sb.toString());
			if (logger.isInfoEnabled()) {
				logger.info("查询{}人流量访问返回结果:{}", type, response);
			}
			if (JSONUtils.mayBeJSON(response)) {
				JSONObject obj = JSONObject.fromObject(response);
				int result = obj.optInt("result");
				if (1 == result) {
					long here_total = obj.optLong("count_by_here");
					long in_total = obj.optLong("count_by_all");

					PatronEntity entity = new PatronEntity();
					entity.setType(type);
					entity.setIn_total(in_total);
					entity.setOut_total(0);
					entity.setHere_total(here_total);

					resultEntity.setState(true);
					resultEntity.setResult(entity);
					return resultEntity;
				}
			}
		} catch (Exception e) {
			logger.error("人流量统计出错啦:", e);
		}
		resultEntity.setState(false);
		resultEntity.setMessage("人流量统计失败");
		return resultEntity;
	}

	/**
	 * 人流量实时统计
	 */
	@Override
	public ResultEntity countPatronForRealtime(Map<String, String> param) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			StringBuilder sb = new StringBuilder(api_url);
			sb.append(URL_countRealTimeVisitor);
			getTime(param, sb);

			String response = HttpClientUtil.sendHttpGet(sb.toString());
			if (logger.isInfoEnabled()) {
				logger.info("人流量访问返回结果:{}", response);
			}
			if (JSONUtils.mayBeJSON(response)) {
				JSONObject obj = JSONObject.fromObject(response);
				int result = obj.optInt("result");
				PatronTempEntity entity = new PatronTempEntity();
				if (1 == result) {
					JSONArray in_total = obj.optJSONArray("in_total");
					JSONArray out_total = obj.optJSONArray("out_total");
					JSONArray date = obj.optJSONArray("date");

					if (date != null)
						entity.setDate(date.toArray());
					if (in_total != null)
						entity.setIn_total(in_total.toArray());
					if (out_total != null)
						entity.setOut_total(out_total.toArray());

				}
				// System.out.println(JSONObject.fromObject(entity));
				resultEntity.setState(true);
				resultEntity.setResult(entity);
				return resultEntity;
			}
		} catch (Exception e) {
			logger.error("人流量统计出错啦:", e);
		}
		resultEntity.setState(false);
		resultEntity.setMessage("人流量统计失败");
		return resultEntity;
	}

	/**
	 * 图书馆信息查询
	 */
	@Override
	public ResultEntity loadLibInfo(Map<String, String> param) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			StringBuilder sb = new StringBuilder(api_url);
			sb.append(URL_loadLibInfo);
			getTime(param, sb);

			String response = HttpClientUtil.sendHttpGet(sb.toString());
			if (logger.isInfoEnabled()) {
				logger.info("图书馆信息查询接口:{}", response);
			}
			if (JSONUtils.mayBeJSON(response)) {
				JSONObject obj = JSONObject.fromObject(response);
				int result = obj.optInt("result");
				LibInfoEntity entity = new LibInfoEntity();
				if (1 == result) {
					int libs = obj.optInt("libs");
					int devices = obj.optInt("devices");
					entity.setLibs(libs);
					entity.setDevices(devices);
				}
				// System.out.println(JSONObject.fromObject(entity));
				resultEntity.setState(true);
				resultEntity.setResult(entity);
				return resultEntity;
			}
		} catch (Exception e) {
			logger.error("图书馆信息查询出错啦:", e);
		}
		resultEntity.setState(false);
		resultEntity.setMessage("图书馆信息查询失败");
		return resultEntity;
	}

	@Override
	public ResultEntity countPatronByDoors(Map<String, String> param) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 参数处理
	 * 
	 * @param param
	 * @param sb
	 */
	private void getTime(Map<String, String> param, StringBuilder sb) {
		String currentDay = DateUtil.DateToString(new Date(), DateStyle.YYYYMMDD);
		String startDay = null, endDay = null;
		String type = param.get("type");
		String appkey = param.get("appkey");
		String library_idx = param.get("library_idx");
		if ("yesterday".equals(type)) {// 昨日
			startDay = DateUtil.addDay(currentDay, -1);
			endDay = startDay;
		} else if ("lastWeek".equals(type)) {// 上周
			int weekNum = DateUtil.getDayOfWeek(currentDay);
			startDay = DateUtil.addDay(currentDay, -7 - weekNum);
			endDay = DateUtil.addDay(currentDay, -1 - weekNum);
		} else if ("lastMonth".equals(type)) {// 上月
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, -1);
			SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
			String time = format.format(c.getTime());
			startDay = time + "01";
			endDay = time + c.getActualMaximum(Calendar.DAY_OF_MONTH);
		} else if ("lastQuarter".equals(type)) {// 上季度
			DateFormat df = new SimpleDateFormat("yyyy");
			Calendar c = Calendar.getInstance();
			int month = c.get(Calendar.MONTH);
			switch (month) {
			case Calendar.JANUARY:
			case Calendar.FEBRUARY:
			case Calendar.MARCH:
				startDay = df.format(c.getTime()) + "1001";
				endDay = df.format(c.getTime()) + "1231";
				break;
			case Calendar.APRIL:
			case Calendar.MAY:
			case Calendar.JUNE:
				startDay = df.format(c.getTime()) + "0101";
				endDay = df.format(c.getTime()) + "0331";
				break;
			case Calendar.JULY:
			case Calendar.AUGUST:
			case Calendar.SEPTEMBER:
				startDay = df.format(c.getTime()) + "0401";
				endDay = df.format(c.getTime()) + "0631";
				break;
			case Calendar.OCTOBER:
			case Calendar.NOVEMBER:
			case Calendar.DECEMBER:
				startDay = df.format(c.getTime()) + "0701";
				endDay = df.format(c.getTime()) + "0931";
				break;
			default:
				break;
			}
		} else if ("lastYear".equals(type)) {// 去年
			startDay = DateUtil.getYear(DateUtil.addYear(currentDay, -1)) + "0101";
			endDay = DateUtil.getYear(DateUtil.addYear(currentDay, -1)) + "1231";
		} else if ("today".equals(type)) {
			startDay = currentDay;
			endDay = startDay;
		} else if ("week".equals(type)) {
			int weekNum = DateUtil.getDayOfWeek(currentDay);
			startDay = DateUtil.addDay(currentDay, -weekNum);
			endDay = currentDay;
		} else if ("month".equals(type)) {
			startDay = DateUtil.getStartDayOfMonth(currentDay, DateStyle.YYYYMMDD);
			endDay = currentDay;
		} else if ("quarter".equals(type)) {// 本季度
			DateFormat df = new SimpleDateFormat("yyyy");
			Calendar c = Calendar.getInstance();
			int month = c.get(Calendar.MONTH);
			switch (month) {
			case Calendar.JANUARY:
			case Calendar.FEBRUARY:
			case Calendar.MARCH:
				startDay = df.format(c.getTime()) + "0101";
				break;
			case Calendar.APRIL:
			case Calendar.MAY:
			case Calendar.JUNE:
				startDay = df.format(c.getTime()) + "0401";
				break;
			case Calendar.JULY:
			case Calendar.AUGUST:
			case Calendar.SEPTEMBER:
				startDay = df.format(c.getTime()) + "0701";
				break;
			case Calendar.OCTOBER:
			case Calendar.NOVEMBER:
			case Calendar.DECEMBER:
				startDay = df.format(c.getTime()) + "1001";
				break;
			default:
				break;
			}
			endDay = currentDay;
		} else if ("year".equals(type)) {
			startDay = DateUtil.getYear(new Date()) + "0101";
			endDay = currentDay;
		} else if ("all".equals(type)) {
			startDay = "20150101";
			endDay = currentDay;
		} else if ("realtime".equals(type)) {// 人流量实时统计
			startDay = currentDay;
			endDay = startDay;
		}

		sb.append("?appkey=").append(appkey).append("&library_idx=").append(library_idx).append("&start_time=").append(startDay).append("000000").append("&end_time=").append(endDay).append("235959");
	}

	/**
	 * 查询第三方服务相关
	 */
	@Override
	public ResultEntity queryThirdPartyServiceByParams(Map<String, String> param) {
		StringBuilder sb = new StringBuilder(server_url);
		sb.append(URL_queryThirdPartyService);
		Map<String, String> map = new HashMap<String, String>();
		map.put("req", JsonUtils.toJson(param));
		String response = HttpClientUtil.sendHttpPost(sb.toString(), map);
		if (logger.isInfoEnabled()) {
			logger.info("查询第三方服务结果:{}", response);
		}
		ResultEntity entity = new ResultEntity();
		if (JSONUtils.mayBeJSON(response)) {
			entity = JsonUtils.fromJson(response, ResultEntity.class);
		} else {
			entity.setResult(false);
			entity.setMessage("查询第三方服务相关失败");
		}
		return entity;
	}

	@Override
	public ResultEntity queryDisplayInfo(Map<String, String> param) {
		StringBuilder sb = new StringBuilder(server_url);
		sb.append(URL_queryDisplayInfo);
		Map<String, String> map = new HashMap<String, String>();
		map.put("req", JsonUtils.toJson(param));
		String response = HttpClientUtil.sendHttpPost(sb.toString(), map);
		if (logger.isInfoEnabled()) {
			logger.info("查询大屏展示相关结果:{}", response);
		}
		ResultEntity entity = new ResultEntity();
		if (JSONUtils.mayBeJSON(response)) {
			entity = JsonUtils.fromJson(response, ResultEntity.class);
		} else {
			entity.setResult(false);
			entity.setMessage("查询大屏展示相关失败");
		}
		return entity;
	}
}
