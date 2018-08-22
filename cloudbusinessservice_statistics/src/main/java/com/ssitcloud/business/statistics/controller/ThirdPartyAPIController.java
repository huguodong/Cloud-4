package com.ssitcloud.business.statistics.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.statistics.service.ThirdPartyAPIService;
import com.ssitcloud.common.util.PackParamUils;

@Controller
@RequestMapping(value = { "thirdPartyAPI" })
public class ThirdPartyAPIController {
	@Resource
	private ThirdPartyAPIService thirdPartyAPIService;

	/**
	 * 借阅排行榜
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("borrowOrderInfo")
	@ResponseBody
	public JSONObject borrowOrderInfo(HttpServletRequest request) {
		Map<String, String> param = PackParamUils.packParam(request);
		String rows = param.get("rows") == null ? "10" : param.get("rows");
		param.put("rows", rows);
		return thirdPartyAPIService.borrowOrderInfo(param);
	}

	/**
	 * 借书、还书统计
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("borrowBackCountInfo")
	public JSONObject borrowBackCountInfo(HttpServletRequest request) {
		Map<String, String> param = PackParamUils.packParam(request);
		return thirdPartyAPIService.borrowBackCountInfo(param);
	}

	/**
	 * 人流量统计
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("cameCountInfo")
	public JSONObject cameCountInfo(HttpServletRequest request) {
		Map<String, String> param = PackParamUils.packParam(request);
		return thirdPartyAPIService.cameCountInfo(param);
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
		String rows = param.get("rows") == null ? "10" : param.get("rows");
		param.put("rows", rows);
		return thirdPartyAPIService.readerRank(param);
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
		return thirdPartyAPIService.countCertificate(param);
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
		return thirdPartyAPIService.countLoanForBar(param);
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
		return thirdPartyAPIService.countRealTimeVisitor(param);
	}
	
	/**
	 * 实时人流量数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("loadLibInfo")
	@ResponseBody
	public JSONObject loadLibInfo(HttpServletRequest request){
		Map<String, String> param = PackParamUils.packParam(request);
		return thirdPartyAPIService.loadLibInfo(param);
	}
	
}
