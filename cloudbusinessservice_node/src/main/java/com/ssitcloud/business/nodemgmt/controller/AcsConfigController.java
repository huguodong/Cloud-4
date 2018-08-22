package com.ssitcloud.business.nodemgmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.nodemgmt.service.AcsConfigService;
import com.ssitcloud.common.entity.ResultEntity;

@RequestMapping("acsConfig")
@Controller
public class AcsConfigController {
	
	@Autowired
	private AcsConfigService acsConfigService;
	
	@RequestMapping("uploadAcsConfig")
	@ResponseBody
	public ResultEntity uploadAcsConfig(String req){
		ResultEntity entity = acsConfigService.uploadAcsConfig(req);
		return entity;
	}

}
