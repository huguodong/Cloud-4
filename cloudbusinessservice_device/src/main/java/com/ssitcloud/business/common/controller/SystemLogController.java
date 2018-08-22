package com.ssitcloud.business.common.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.service.BasicService;
import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.sysmgmt.service.SystemLogService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value={"systemLog"})
public class SystemLogController {
	
	private static final String URL_AddOperationLog = "AddOperationLog";
	
	@Resource(name="basicServiceImpl")
	private BasicService basicService;
	
	@Resource
	private SystemLogService systemLogService;
	
	/**
	 * 增加操作 日志
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping(value={"AddOperationLog"})
	@ResponseBody
	public ResultEntity AddOperationLog(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			result=basicService.postUrl(URL_AddOperationLog,json,"json");
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * log_message query  return log_desc and log_code field
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"getOperationLogType"})
	@ResponseBody
	public ResultEntity getOperationLogType(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=systemLogService.getOperationLogType(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	
}
