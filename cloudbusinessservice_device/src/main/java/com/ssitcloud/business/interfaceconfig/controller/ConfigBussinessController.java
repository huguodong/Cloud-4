package com.ssitcloud.business.interfaceconfig.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.interfaceconfig.service.JsonDataService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value = { "json" })
public class ConfigBussinessController {
	@Resource
	private JsonDataService jsonDataService;

	@RequestMapping(value = { "getDevTypeData" })
	@ResponseBody
	public ResultEntity getDevTypeData(String req) {
		ResultEntity entity = jsonDataService.getDevTypeData(req);
		return entity;
	}

	@RequestMapping(value = { "getLibDevData" })
	@ResponseBody
	public ResultEntity getLibDevData(String req) {
		ResultEntity entity = jsonDataService.getLibDevData(req);
		return entity;
	}

	@RequestMapping(value = { "getData" })
	@ResponseBody
	public ResultEntity getData(HttpServletRequest request, String req) {
		ResultEntity entity = jsonDataService.getData(req);

		return entity;
	}

	@RequestMapping(value = "getInitData")
	@ResponseBody
	public ResultEntity getInitData(String req) {
		ResultEntity entity = jsonDataService.getInitData(req); // [{"name":"passwordOptions","fieldset":"pwdMaxLenght","value":"{$P:8}"},{"name":"passwordOptions","fieldset":"pwdInputTimes","value":"{$P:4}"},{"name":"passwordOptions","fieldset":"pwdErrorDo","value":"{$P:0}"},{"name":"waitTime","fieldset":"AccountDetailInfoForRenew","value":"{$S:44}"}]

		return entity;
	}

	@RequestMapping(value = "saveData")
	@ResponseBody
	public ResultEntity saveData(String req) {
		return jsonDataService.saveData(req);
	}
}
