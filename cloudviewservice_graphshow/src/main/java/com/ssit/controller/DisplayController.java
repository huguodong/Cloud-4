package com.ssit.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssit.Entity.CertificateEntity;
import com.ssit.Entity.CirculateEntity;
import com.ssit.Entity.PatronEntity;
import com.ssit.common.entity.ResultEntity;
import com.ssit.service.DisplayService;
import com.ssit.util.AESHelper;
import com.ssit.util.DateUtil;
import com.ssit.util.JsonUtils;
import com.ssit.util.PackParamUils;
import com.ssit.util.PropertiesUtil;
import com.ssit.util.WeatherUtil;

@Controller
public class DisplayController{
	private static Logger logger = LoggerFactory.getLogger(DisplayController.class);
	@Resource
	private DisplayService displayService;
	private String appkey;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "index" })
	public ModelAndView index(HttpServletRequest request) {
		Map<String, String> param = PackParamUils.packParam(request);

		appkey = param.get("appkey");
		if (StringUtils.isEmpty(appkey)) {
			appkey = PropertiesUtil.getPropertiesValue("appkey");
			param.put("appkey", appkey);
		}
		JSONObject resultObj = this.validateAppKey(appkey);
		boolean state = resultObj.optBoolean("state");
		if (!state) {
			return new ModelAndView("500", "error_msg", resultObj.optString("error_msg"));
		}
		JSONObject result = resultObj.optJSONObject("result");
		String index_page = PropertiesUtil.getPropertiesValue("page", "index1");
		String page = result.optString("index_page") == null ? index_page : result.optString("index_page");
		param.clear();
		param = JsonUtils.fromJson(result.toString(), Map.class);
		
		String library_idx = getLib_idx(appkey);
		param.put("library_idx", library_idx);
		
		return new ModelAndView(page, param);
	}

	/**
	 * 获取时间、天气
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "getTitle" })
	@ResponseBody
	public ResultEntity getTitle(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		String date = DateUtil.getDate(new Date()) + " " + DateUtil.getWeek(new Date()).getChineseName();
		Map<String, String> map = new HashMap<String, String>();
		map.put("date", date);
		String weather_city = request.getParameter("weather_city");
		if (!StringUtils.isEmpty(weather_city)) {
			map.putAll(WeatherUtil.getWeather(weather_city));
		}
		result.setState(true);
		result.setResult(map);
		return result;
	}

	/**
	 * 办证
	 * 
	 * @param request
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = { "countCertificate" })
	@ResponseBody
	public ResultEntity countCertificate(HttpServletRequest request) {
		Map<String, String> param = PackParamUils.packParam(request);
		param.put("appkey", appkey);
		ResultEntity result = displayService.countCertificateByParam(param);
		return result;
	}

	/**
	 * 流通（借、还、续借）
	 * 
	 * @param request
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = { "countCirculate" })
	@ResponseBody
	public ResultEntity countCirculateForTab(HttpServletRequest request) {
		Map<String, String> param = PackParamUils.packParam(request);
		param.put("appkey", appkey);
		ResultEntity result = new ResultEntity();
		String type = param.get("type");
		List<CirculateEntity> newlist = new ArrayList<CirculateEntity>();
		if (type.indexOf(",") != -1) {
			String[] types = type.split("\\,");
			for (String t : types) {
				param.put("type", t);
				ResultEntity result1 = displayService.countCirculateByParam(param);// 流通
				ResultEntity result2 = displayService.countCertificateByParam(param);// 办证
				if (result1.getState()) {
					CirculateEntity cir = (CirculateEntity) result1.getResult();
					CertificateEntity cer = (CertificateEntity) result2.getResult();
					cir.setType4_total(cer.getTotal());
					newlist.add(cir);
				}
			}
		} else {
			ResultEntity result1 = displayService.countCirculateByParam(param);// 流通
			ResultEntity result2 = displayService.countCertificateByParam(param);// 办证
			if (result1.getState()) {
				CirculateEntity cir = JsonUtils.fromJson(result1.getResult().toString(), CirculateEntity.class);
				CertificateEntity cer = JsonUtils.fromJson(result2.getResult().toString(), CertificateEntity.class);
				cir.setType4_total(cer.getTotal());
				newlist.add(cir);
			}
		}
		result.setState(true);
		result.setResult(newlist);
		return result;
	}

	/**
	 * 流通（借、还、续借）
	 * 
	 * @param request
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = { "countCirculateForBar" })
	@ResponseBody
	public ResultEntity countCirculateForBar(HttpServletRequest request) {
		Map<String, String> param = PackParamUils.packParam(request);
		param.put("appkey", appkey);
		return displayService.countCirculateForBar(param);
	}

	/**
	 * 财经
	 * 
	 * @param request
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = { "countFinance" })
	@ResponseBody
	public ResultEntity countFinance(HttpServletRequest request) {
		Map<String, String> param = PackParamUils.packParam(request);
		param.put("appkey", appkey);
		return displayService.countFinanceByParam(param);
	}

	/**
	 * 人流量
	 * 
	 * @param request
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = { "countPatron" })
	@ResponseBody
	public ResultEntity countPatron(HttpServletRequest request) {
		Map<String, String> param = PackParamUils.packParam(request);
		param.put("appkey", appkey);
		ResultEntity result = new ResultEntity();
		String type = param.get("type");
		List<PatronEntity> newlist = new ArrayList<PatronEntity>();
		if (type.indexOf(",") != -1) {
			String[] types = type.split("\\,");
			for (String t : types) {
				param.put("type", t);
				ResultEntity rs = displayService.countPatronByParam(param);
				if (rs.getState()) {
					PatronEntity entity = (PatronEntity) rs.getResult();
					newlist.add(entity);
				}
			}
			result.setState(true);
			result.setResult(newlist);
		} else {
			result = displayService.countPatronByParam(param);
			if (result.getState()) {
				PatronEntity entity = (PatronEntity) result.getResult();
				newlist.add(entity);
				result.setResult(newlist);
			}
		}
		return result;
	}

	/**
	 * 人流量实时统计
	 * 
	 * @param request
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = { "countPatronForRealtime" })
	@ResponseBody
	public ResultEntity countPatronForRealtime(HttpServletRequest request) {
		Map<String, String> param = PackParamUils.packParam(request);
		param.put("appkey", appkey);
		return displayService.countPatronForRealtime(param);
	}

	/**
	 * 图书借阅排行榜
	 * 
	 * @param request
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = { "bookRank" })
	@ResponseBody
	public ResultEntity bookRank(HttpServletRequest request) {
		Map<String, String> param = PackParamUils.packParam(request);
		param.put("appkey", appkey);
		return displayService.bookRankByParam(param);
	}

	@RequestMapping(value = { "rank" })
	public ModelAndView admin(HttpServletRequest request) {
		Map<String, String> param = PackParamUils.packParam(request);

		appkey = param.get("appkey");
		if (StringUtils.isEmpty(appkey)) {
			appkey = PropertiesUtil.getPropertiesValue("appkey");
			param.put("appkey", appkey);
		}
		JSONObject resultObj = this.validateAppKey(appkey);
		boolean state = resultObj.optBoolean("state");
		if (!state) {
			return new ModelAndView("500", "error_msg", resultObj.optString("error_msg"));
		}
		JSONObject result = resultObj.optJSONObject("result");
		param.clear();
		param = JsonUtils.fromJson(result.toString(), Map.class);
		
		String library_idx = getLib_idx(appkey);
		param.put("library_idx", library_idx);
		
		return new ModelAndView("readerRank", param);
	}

	/**
	 * 读者借阅排行榜
	 * 
	 * @param request
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = { "readerRank" })
	@ResponseBody
	public ResultEntity readerRank(HttpServletRequest request) {
		Map<String, String> param = PackParamUils.packParam(request);
		param.put("appkey", appkey);
		return displayService.readerRankByParam(param);
	}

	/**
	 * 图书馆信息
	 * 
	 * @param request
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = { "loadLibInfo" })
	@ResponseBody
	public ResultEntity loadLibInfo(HttpServletRequest request) {
		Map<String, String> param = PackParamUils.packParam(request);
		param.put("appkey", appkey);
		return displayService.loadLibInfo(param);
	}

	/**
	 * 验证appKey的正确性
	 * 
	 * @param app_key
	 * @return
	 */
	private JSONObject validateAppKey(String app_key) {
		JSONObject resultObj = new JSONObject();
		if (StringUtils.isEmpty(app_key)) {// 如果为空
			resultObj.clear();
			resultObj.put("state", false);
			resultObj.put("error_msg", "appkey不能为空");
			return resultObj;
		}
		Map<String, String> param =new HashMap<String, String>();
		param.put("app_key", app_key);// appKey
		param.put("isDisable", "1");// 在用状态
		ResultEntity entity = displayService.queryThirdPartyServiceByParams(param);
		boolean state = entity.getState();
		if (state) {
			Object result = entity.getResult();
			if (JSONUtils.isNull(result)||!JSONUtils.isArray(result)) {
				state = false;
			} else {
				JSONArray array=JSONArray.fromObject(result);
				if (array.isEmpty()||array.size()<=0) {
					state = false;
				}else{
					resultObj = array.optJSONObject(0);
					int library_idx = resultObj.optInt("library_idx");
					param.clear();
					param.put("library_idx", library_idx + "");
					entity = displayService.queryDisplayInfo(param);
					if(entity.getState()){
						if(entity.getResult()!=null){
							resultObj.clear();
							resultObj.put("state", true);
							resultObj.put("result", entity.getResult());
						}else{
							state = false;
						}
					}else{
						state = false;
					}
				}
			}
		}

		if (!state) {// 没有在数据库中查到
			String error_msg = entity.getMessage();
			if (error_msg == null || "".equals(error_msg)) {
				error_msg = "提供的appkey不合法";
			}
			resultObj.clear();
			resultObj.put("state", state);
			resultObj.put("error_msg", error_msg == null ? "提供的appkey不合法" : error_msg);
		}
		return resultObj;
	}
	
	/**
	 * 根据app_key解密获取图书馆idx
	 * @param app_key
	 * @return
	 */
	private String getLib_idx(String app_key) {
		String app_key_source = AESHelper.decrypt(app_key);
		String library_idx = null;
		if (!StringUtils.isEmpty(app_key_source) && app_key_source.indexOf("#") != -1) {
			library_idx = app_key_source.split("#")[0];
		}
		return library_idx;
	}

}
