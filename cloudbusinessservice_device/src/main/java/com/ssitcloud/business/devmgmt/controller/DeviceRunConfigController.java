package com.ssitcloud.business.devmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.devmgmt.service.DeviceRunConfigService;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * 
 * @comment 
 * @date 2016年6月14日
 * @author hwl
 */
@Controller
@RequestMapping(value="runconf")
public class DeviceRunConfigController {

	@Resource
	DeviceRunConfigService deviceRunConfigService;
	
	@RequestMapping(value={"SelDeviceRunConf"})
	@ResponseBody
	public ResultEntity SelDeviceRunConf(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceRunConfigService.SelectRunConfig(json);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	@RequestMapping(value={"AddNewDeviceRunConf"})
	@ResponseBody
	public ResultEntity AddNewDeviceRunConf(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceRunConfigService.InsertRunConfig(json);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value={"UpdDeviceRunConf"})
	@ResponseBody
	public ResultEntity UpdDeviceRunConf(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceRunConfigService.UpdateRunConfig(json);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value={"DelDeviceRunConf"})
	@ResponseBody
	public ResultEntity DelDeviceRunConf(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceRunConfigService.DeleteRunConfig(json);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value={"SelFunctionByDeviceIdx"})
	@ResponseBody
	public ResultEntity SelFunctionByDeviceIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceRunConfigService.SelFunctionByDeviceIdx(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
}
