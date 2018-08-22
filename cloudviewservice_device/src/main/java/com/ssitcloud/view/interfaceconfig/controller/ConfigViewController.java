package com.ssitcloud.view.interfaceconfig.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.interfaceconfig.service.JsonDataService;

@Controller
@RequestMapping(value = { "json" })
public class ConfigViewController extends BasicController {
	@Resource
	private JsonDataService jsonDataService;

	@RequestMapping(value = { "getDevTypeData" })
	@ResponseBody
	public ResultEntity getDevTypeData(HttpServletRequest request, String library_idx) {
		/*
		 * JSONObject jsonObj = JSONObject.fromObject(library_idx);
		 * 
		 * String lib_idx = jsonObj.getString("library_idx"); String param =
		 * "{\"library_idx\":\"" + lib_idx + "\"}";
		 */
		ResultEntity entity = jsonDataService.getDevTypeData(library_idx);
		return entity;
	}

	@RequestMapping(value = { "getLibDevData" })
	@ResponseBody
	public ResultEntity getLibDevData(HttpServletRequest request, String libdeviceId) {
		/*
		 * JSONObject jsonObj = JSONObject.fromObject(library_idx);
		 * 
		 * String lib_idx = jsonObj.getString("library_idx"); String param =
		 * "{\"library_idx\":\"" + lib_idx + "\"}";
		 */
		ResultEntity entity = jsonDataService.getLibDevData(libdeviceId);
		return entity;
	}

	@RequestMapping(value = { "getData" })
	@ResponseBody
	public ResultEntity getData(String libdeviceParam) {
		ResultEntity entity = jsonDataService.getData(libdeviceParam);
		return entity;
	}

	@RequestMapping(value = "getInitData")
	@ResponseBody
	public ResultEntity getInitData(HttpServletRequest request, String library_device) {
		Operator oper = getCurrentUser();
		/*
		 * JSONObject jsonObj = JSONObject.fromObject(library_device);
		 * 
		 * String lib_idx = jsonObj.getString("lib_id"); String deviceId =
		 * jsonObj.getString("operator_id"); String param = "{\"lib_id\":\"" +
		 * lib_idx + "\",\"operator_id\":\"" + deviceId +
		 * "\",\"operator_name\":\"" + oper.getOperator_name() +
		 * "\",\"operator_type\":\"" + oper.getOperator_type() + "\"}";
		 */

		ResultEntity entity = jsonDataService.getInitData(library_device); // [{"name":"passwordOptions","fieldset":"pwdMaxLenght","value":"{$P:8}"},{"name":"passwordOptions","fieldset":"pwdInputTimes","value":"{$P:4}"},{"name":"passwordOptions","fieldset":"pwdErrorDo","value":"{$P:0}"},{"name":"waitTime","fieldset":"AccountDetailInfoForRenew","value":"{$S:44}"}]

		return entity;
	}

	@RequestMapping(value = "saveData")
	@ResponseBody
	public ResultEntity saveData(HttpServletRequest request, String jsonStr, String libId, String deviceType, String deviceIdx) {
		jsonStr = jsonStr.replaceAll("\"", "\\\\\"");
		Operator oper = getCurrentUser();
		// jsonStr = "{\"lib_id\":\"" + libId + "\",\"deviceId\":\"" + deviceId
		// + "\",\"deviceType\":\"" + deviceType + "\",\"operator_id\":\"" +
		// deviceId + "\",\"operator_name\":\""
		// + oper.getOperator_name() + "\",\"operator_type\":\"" +
		// oper.getOperator_type() + "\",\"json\":\"" + jsonStr + "\"}";
		jsonStr = "{\"lib_id\":" + libId + ",\"deviceIdx\":" + deviceIdx + ",\"operator_id\":\"" + deviceIdx + "\",\"operator_name\":\"" + oper.getOperator_name() + "\",\"operator_type\":\""
				+ oper.getOperator_type() + "\",\"json\":\"" + jsonStr + "\"}";
		return jsonDataService.saveData(jsonStr);
	}
}
