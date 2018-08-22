package com.ssitcloud.common.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.service.MongoInstanceService;

@Controller
@RequestMapping("mongoInstance")
public class MongoInstanceController {

	@Resource
	private MongoInstanceService mongoInstanceService;
	
	
	/**
	 * 查询
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("queryMongoInstances")
	@ResponseBody
	public ResultEntityF<Object> queryMongoInstances(HttpServletRequest request,String req){
		return mongoInstanceService.queryMongoInstances(req);
	}
}
