package com.ssitcloud.view.sysmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.sysmgmt.service.SystemLogService;

/**
 * 系统日志Controller
 * @comment 
 * @date 2016年5月31日
 * @author hwl
 */
@RequestMapping("/systemlog")
@Controller
public class SystemLogController {

	@Resource
	SystemLogService systemLogService;
	
	@RequestMapping(value={"main"})
	public String main(HttpServletRequest request){
		return "/page/sysmgmt/system-log";
	}
	/**
	 * 查询系统日志信息
	 * @comment
	 * @param json
	 * @param request
	 * @return
	 * @data 2016年5月31日`
	 * @author hwl
	 */
	@RequestMapping("/SelectSystemLog")
	@ResponseBody
	public ResultEntity GetSystemLog(String json,HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			String logList = systemLogService.GetSystemloginfo(json);
			result = JsonUtils.fromJson(logList, ResultEntity.class);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查询操作员信息
	 * @comment
	 * @param json
	 * @param request
	 * @return
	 * @data 2016年5月31日
	 * @author hwl
	 */
	@RequestMapping("/SelectOperatorList")
	@ResponseBody
	public ResultEntity OperatorList(String json,HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			String logList = systemLogService.GetOperatorList(json);
			result = JsonUtils.fromJson(logList, ResultEntity.class);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 获取log_message 操作类型，并且设置下拉框
	 * 
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"getOperationLogType"})
	@ResponseBody
	public ResultEntity getOperationLogType(String req,HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			result = systemLogService.getOperationLogType(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
}
