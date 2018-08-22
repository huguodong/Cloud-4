package com.ssitcloud.business.shelfmgmt.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.shelfmgmt.service.RelOperatorShelfGroupService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping("/relOperatorShelfGroup")
public class RelOperatorShelfGroupController {

	@Resource
	RelOperatorShelfGroupService relOperatorShelfGroupService;
	
	@RequestMapping(value = { "queryAllRelOperatorShelfGroup" })
	@ResponseBody
	public ResultEntity queryAllRelOperatorShelfGroup(HttpServletRequest request ,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = relOperatorShelfGroupService.queryAllRelOperatorShelfGroup(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "queryRelOperatorShelfGroupById" })
	@ResponseBody
	public ResultEntity queryRelOperatorShelfGroupById(HttpServletRequest request ,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = relOperatorShelfGroupService.queryRelOperatorShelfGroupById(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "deleteRelOperatorShelfGroup" })
	@ResponseBody
	public ResultEntity deleteRelOperatorShelfGroup(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = relOperatorShelfGroupService.deleteRelOperatorShelfGroup(req);;
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "updateRelOperatorShelfGroup" })
	@ResponseBody
	public ResultEntity updateRelOperatorShelfGroup(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = relOperatorShelfGroupService.updateRelOperatorShelfGroup(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "addRelOperatorShelfGroup" })
	@ResponseBody
	public ResultEntity addRelOperatorShelfGroup(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = relOperatorShelfGroupService.addRelOperatorShelfGroup(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
