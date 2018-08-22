package com.ssitcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.service.ThirdPartyServiceService;

@Controller
@RequestMapping("thirdPartyService")
public class ThirdPartyServiceController{
	
	@Autowired
	private ThirdPartyServiceService thirdPartyService;
	
	@RequestMapping("queryThirdPartyServiceByPage")
	@ResponseBody
	public ResultEntity queryThirdPartyServiceByPage(String req){
		return thirdPartyService.queryThirdPartyServiceByPage(req);
	}
	
	@RequestMapping("editThirdPartyService")
	@ResponseBody
	public ResultEntity editThirdPartyService(String returnInfo,String req) {
		return thirdPartyService.editThirdPartyService(req);
	}

	@RequestMapping("deleteThirdPartyService")
	@ResponseBody
	public ResultEntity deleteThirdPartyService(String req){
		return thirdPartyService.deleteThirdPartyService(req);
	}
	
	@ResponseBody
	@RequestMapping("queryThirdPartyServiceByParams")
	public ResultEntity queryThirdPartyServiceByParams(String req){
		return thirdPartyService.queryThirdPartyServiceByParams(req);
	}
	
	@ResponseBody
	@RequestMapping("deleteDisplayInfo")
	public ResultEntity deleteDisplayInfo(String req) {
		return thirdPartyService.deleteDisplayInfo(req);
	}

	@ResponseBody
	@RequestMapping("editDisplayInfo")
	public ResultEntity editDisplayInfo(String req) {
		return thirdPartyService.editDisplayInfo(req);
	}

	@ResponseBody
	@RequestMapping("queryDisplayInfo")
	public ResultEntity queryDisplayInfo(String req) {
		return thirdPartyService.queryDisplayInfo(req);
	}

	@ResponseBody
	@RequestMapping("queryDisplayInfoList")
	public ResultEntity queryDisplayInfoList(String req) {
		return thirdPartyService.queryDisplayInfoList(req);
	}
}
