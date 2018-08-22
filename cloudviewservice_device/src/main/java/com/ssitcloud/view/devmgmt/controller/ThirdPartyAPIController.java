package com.ssitcloud.view.devmgmt.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.PackParamUils;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.AESHelper;
import com.ssitcloud.view.devmgmt.service.ThirdPartyServiceService;

@Controller
@RequestMapping("api/v2")
public class ThirdPartyAPIController extends BasicController {

	@Autowired
	private ThirdPartyServiceService thirdPartyService;

	/**
	 * 图书借阅排行榜
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("borrowOrderInfo")
	@ResponseBody
	public JSONObject borrowOrderInfo(HttpServletRequest request) {
		Map<String, String> param = PackParamUils.packParam(request);
		String app_key = param.get("appkey");
		JSONObject resultObj = new JSONObject();
		boolean result = validateAppKey(resultObj, app_key);
		if (!result) return resultObj;
		
		String library_idx = getLib_idx(app_key);
		param.put("library_idx", library_idx);
		return thirdPartyService.borrowOrderInfo(param);
	}
	
	/**
	 * 读者借阅排行榜
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("readerRank")
	@ResponseBody
	public JSONObject readerRank(HttpServletRequest request) {
		Map<String, String> param = PackParamUils.packParam(request);
		String app_key = param.get("appkey");
		JSONObject resultObj = new JSONObject();
		boolean result = validateAppKey(resultObj, app_key);
		if (!result) return resultObj;
		
		String library_idx = getLib_idx(app_key);
		param.put("library_idx", library_idx);
		return thirdPartyService.readerRank(param);
	}

	/**
	 * 办证数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("countCertificate")
	@ResponseBody
	public JSONObject countCertificate(HttpServletRequest request) {
		Map<String, String> param = PackParamUils.packParam(request);
		String app_key = param.get("appkey");
		JSONObject resultObj = new JSONObject();
		boolean result = validateAppKey(resultObj, app_key);
		if (!result) return resultObj;
		
		String library_idx = getLib_idx(app_key);
		param.put("library_idx", library_idx);
		return thirdPartyService.countCertificate(param);
	}
	
	/**
	 * 借还书系统（借还总量）
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("borrowBackCountInfo")
	public JSONObject borrowBackCountInfo(HttpServletRequest request) {
		Map<String, String> param = PackParamUils.packParam(request);
		String app_key = param.get("appkey");
		JSONObject resultObj = new JSONObject();
		boolean result = validateAppKey(resultObj, app_key);
		if (!result) return resultObj;
		
		String library_idx = getLib_idx(app_key);
		param.put("library_idx", library_idx);
		return thirdPartyService.borrowBackCountInfo(param);
	}

	/**
	 * 到馆统计（传入时间内的到馆总人数及今天当前在馆人数）
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("cameCountInfo")
	public JSONObject cameCountInfo(HttpServletRequest request) {
		Map<String, String> param = PackParamUils.packParam(request);
		String app_key = param.get("appkey");
		JSONObject resultObj = new JSONObject();
		boolean result = validateAppKey(resultObj, app_key);
		if (!result) return resultObj;

		String library_idx = getLib_idx(app_key);
		param.put("library_idx", library_idx);

		return thirdPartyService.cameCountInfo(param);
	}
	
	
	/**
	 * 借还数据分时统计
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("countLoanForBar")
	@ResponseBody
	public JSONObject countLoanForBar(HttpServletRequest request) {
		Map<String, String> param = PackParamUils.packParam(request);
		String app_key = param.get("appkey");
		JSONObject resultObj = new JSONObject();
		boolean result = validateAppKey(resultObj, app_key);
		if (!result) return resultObj;

		String library_idx = getLib_idx(app_key);
		param.put("library_idx", library_idx);
		
		return thirdPartyService.countLoanForBar(param);
	}
	
	/**
	 * 实时人流量数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("countRealTimeVisitor")
	@ResponseBody
	public JSONObject countRealTimeVisitor(HttpServletRequest request){
		Map<String, String> param = PackParamUils.packParam(request);
		String app_key = param.get("appkey");
		JSONObject resultObj = new JSONObject();
		boolean result = validateAppKey(resultObj, app_key);
		if (!result) return resultObj;

		String library_idx = getLib_idx(app_key);
		param.put("library_idx", library_idx);
		return thirdPartyService.countRealTimeVisitor(param);
	}
	
	/**
	 * 获取图书馆数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("loadLibInfo")
	@ResponseBody
	public JSONObject loadLibInfo(HttpServletRequest request){
		Map<String, String> param = PackParamUils.packParam(request);
		String app_key = param.get("appkey");
		JSONObject resultObj = new JSONObject();
		boolean result = validateAppKey(resultObj, app_key);
		if (!result) return resultObj;
		
		String library_idx = getLib_idx(app_key);
		param.put("library_idx", library_idx);
		return thirdPartyService.loadLibInfo(param);
	}

	/**
	 * 验证appKey的正确性
	 * 
	 * @param resultObj
	 * @param app_key
	 * @return
	 */
	private boolean validateAppKey(JSONObject resultObj, String app_key) {
		if (StringUtils.isEmpty(app_key)) {// 如果为空
			resultObj.clear();
			resultObj.put("result", 0);
			resultObj.put("error_msg", "必要参数不能为空");
			return false;
		}
		resultObj.put("app_key", app_key);//appKey
		resultObj.put("isDisable", 1);//在用状态
		ResultEntity entity = thirdPartyService.queryThirdPartyServiceByParams(resultObj.toString());
		boolean state = entity.getState();
		if (state) {
			Object result = entity.getResult();
			if (JSONUtils.isNull(result)) {
				state = false;
			} else {
				JSONArray array = JSONArray.fromObject(result);
				if (array.isEmpty()) {
					state = false;
				}
			}
		}

		if (!state) {// 没有在数据库中查到
			resultObj.clear();
			resultObj.put("result", 0);
			resultObj.put("error_msg", "提供的appkey不合法");
			return false;
		}
		return true;
	}

	/**
	 * 根据app_key解密获取图书馆idx
	 * @param app_key
	 * @return
	 */
	private String getLib_idx(String app_key) {
		String app_key_source = AESHelper.decrypt(app_key);
		String library_idx = null;
		if (StringUtils.hasText(app_key_source) && app_key_source.indexOf("#") != -1) {
			library_idx = app_key_source.split("#")[0];
		}
		return library_idx;
	}
	
	/**
	 * 根据规则推荐图书信息到读者手机上
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("getRecommendList")
	@ResponseBody
	public ResultEntity getRecommendListForSMS(HttpServletRequest request, String req){
		JSONObject obj = null;
		String cardNo = null;
		if (JSONUtils.mayBeJSON(req)) {
			obj = JSONObject.fromObject(req);
			cardNo = obj.optString("cardNo");
		}
		if (cardNo == null) {
			return new ResultEntity(false, "读者卡号不能为空");
		}
		return thirdPartyService.getRecommendListForSMS(obj.toString());
	}
	
	
	/**
	 * 获取图书推荐
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("getRecommendInfo")
	@ResponseBody
	public ResultEntity getRecommendList(HttpServletRequest request, String req){
		JSONObject obj = null;
		String cardNo = null;
		if (JSONUtils.mayBeJSON(req)) {
			obj = JSONObject.fromObject(req);
			cardNo = obj.optString("cardNo");
		}
		if (cardNo == null) {
			return new ResultEntity(false, "读者卡号不能为空");
		}
		return thirdPartyService.getRecommendList(obj.toString());
	}
	
	/**
	 * 获取热门图书
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("getBookListByTopN")
	@ResponseBody
	public ResultEntity getBookListByTopN(HttpServletRequest request, String req){
		return thirdPartyService.getBookListByTopN(req);
	}


}
