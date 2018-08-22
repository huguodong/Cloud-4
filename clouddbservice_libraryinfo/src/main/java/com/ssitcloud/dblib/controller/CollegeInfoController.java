package com.ssitcloud.dblib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.service.CollegeInfoService;

@Controller
@RequestMapping("collegeInfo")
public class CollegeInfoController {
	
	@Autowired
	private CollegeInfoService collegeInfoService;
	
	@RequestMapping("queryCollegeInfo")
	@ResponseBody
	public ResultEntity queryCollegeInfo(String req){
		return collegeInfoService.queryColleageInfo(req);
	}

}
