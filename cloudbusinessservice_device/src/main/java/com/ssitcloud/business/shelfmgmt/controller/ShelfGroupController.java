package com.ssitcloud.business.shelfmgmt.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.shelfmgmt.service.ShelfGroupService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping("/shelfGroup")
public class ShelfGroupController {

	@Resource
	ShelfGroupService shelfGroupService;
	
	@RequestMapping(value={"queryAllShelfGroup"})
	@ResponseBody
	public ResultEntity queryAllShelfGroup(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = shelfGroupService.queryAllShelfGroup(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = {"deleteShelfGroup" })
	@ResponseBody
	public ResultEntity deleteShelfGroup(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = shelfGroupService.deleteShelfGroup(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "updateShelfGroup" })
	@ResponseBody
	public ResultEntity updateShelfGroup(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = shelfGroupService.updateShelfGroup(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "addShelfGroup" })
	@ResponseBody
	public ResultEntity addShelfGroup(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = shelfGroupService.addShelfGroup(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
