package com.ssitcloud.view.shelfmgmt.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.shelfmgmt.service.MakeshelfService;

@Controller
@RequestMapping("/makeshelf")
public class MakeshelfController {

	@Resource
	MakeshelfService makeshelfService;
	
	@RequestMapping("/main")
	public String main(HttpServletRequest request){
		return "/page/shelfmgmt/makeshelf";
	}
	
	@RequestMapping(value={"queryAllMakeShelfRecord"})
	@ResponseBody
	public ResultEntity queryAllMakeShelfRecord(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = makeshelfService.queryAllMakeShelfRecord(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "makeshelf" })
	@ResponseBody
	public ResultEntity makeshelf(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = makeshelfService.makeshelf(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "deleteMakeShelfRecord" })
	@ResponseBody
	public ResultEntity deleteMakeShelfRecord(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = makeshelfService.deleteMakeShelfRecord(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
