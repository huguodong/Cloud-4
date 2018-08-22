package com.ssitcloud.businessauth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.businessauth.service.SystemLogService;
import com.ssitcloud.businessauth.utils.JsonUtils;
import com.ssitcloud.businessauth.utils.LogUtils;
import com.ssitcloud.common.entity.ResultEntity;



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

	@RequestMapping("/SelectSystemLog")
	@ResponseBody
	public ResultEntity GetSystemLog(String json,HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			String logList = systemLogService.GetSystemloginfo(json);
			result = JsonUtils.fromJson(logList, ResultEntity.class);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	@RequestMapping("/SelectOperatorList")
	@ResponseBody
	public ResultEntity OperatorList(String json,HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			String logList = systemLogService.GetOperatorList(json);
			result = JsonUtils.fromJson(logList, ResultEntity.class);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	@RequestMapping("/AddOperationLog")
	@ResponseBody
	public ResultEntity AddOperationLog(String json,HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String response = systemLogService.addOperationLog(json);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				resultEntity.setValue(false, "连接超时");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	/**
	 * 获取log_message 操作类型
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOperationLogType")
	@ResponseBody
	public ResultEntity getOperationLogType(String req,HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			 resultEntity = systemLogService.getOperationLogType(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
}
