package com.ssitcloud.dbauth.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.entity.OperationLogEntity;
import com.ssitcloud.dbauth.entity.OperatorEntity;
import com.ssitcloud.dbauth.entity.page.SystemLogPageEntity;
import com.ssitcloud.dbauth.service.OperationLogService;
import com.ssitcloud.dbauth.utils.JsonUtils;
import com.ssitcloud.dbauth.utils.LogUtils;

/**
 * 系统日志Controller
 * @comment 
 * @date 2016年5月31日
 * @author hwl
 */
@RequestMapping("/systemlog")
@Controller
public class OperationLogController {

	@Resource
	OperationLogService operationLogService;
	

	@RequestMapping("/SelectSystemLog")
	@ResponseBody
	public ResultEntity GetOperationLog(String json, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			SystemLogPageEntity systemLogPageEntity = JsonUtils.fromJson(json, SystemLogPageEntity.class);
			SystemLogPageEntity logPageEntity = operationLogService.getOperationLog(systemLogPageEntity);
			resultEntity.setValue(true, "success","",logPageEntity);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/SelectOperatorInfo")
	@ResponseBody
	public ResultEntity SelectOperatorInfo(String json, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			OperatorEntity operatorEntity = JsonUtils.fromJson(json, OperatorEntity.class);
			List<OperatorEntity> list = operationLogService.getOperatorInfo(operatorEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	@RequestMapping("/AddOperationLog")
	@ResponseBody
	public ResultEntity AddOperationLog(String json,HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(json)) {
				resultEntity.setValue(false, "json is null");
				return resultEntity;
			}
			OperationLogEntity operlog = JsonUtils.fromJson(json, OperationLogEntity.class);
			int ret = operationLogService.addOperationLog(operlog);
			if(ret<=0){
				throw new RuntimeException("无效的图书馆模板id");
			}
			resultEntity.setValue(true, "success");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return resultEntity;
	}
	/**
	 * 获取log_message 中的log_desc和 log_code
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOperationLogType")
	@ResponseBody
	public ResultEntity getOperationLogType(String req,HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity=operationLogService.getOperationLogType();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return resultEntity;
	}
}
