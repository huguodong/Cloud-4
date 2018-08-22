package com.ssitcloud.business.devmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.devmgmt.service.DeviceExtConfigService;
import com.ssitcloud.common.entity.ResultEntity;
/**
 * 设备外设配置Controller
 * @comment 
 * @date 2016年6月14日
 * @author hwl
 */

@Controller
@RequestMapping("/extconf")
public class DeviceExtConfigController {
	
	@Resource 
	DeviceExtConfigService deviceExtConfigService;
	
	@RequestMapping(value={"SelDeviceExtConfig"})
	@ResponseBody
	public ResultEntity SelDeviceExtConfig(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceExtConfigService.SelectExt(json);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value={"AddNewDeviceExtConfig"})
	@ResponseBody
	public ResultEntity AddNewDeviceExtConfig(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceExtConfigService.InsertExtdata(json);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value={"DelAndAddExtConfig"})
	@ResponseBody
	public ResultEntity DelAndAddExtConfig(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceExtConfigService.UpdateExtdata(json);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value={"DelDeviceExtConfig"})
	@ResponseBody
	public ResultEntity DelDeviceExtConfig(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceExtConfigService.DeleteExtdata(json);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	/**
	 * req,一个数组device_ids
	 * 获取设备对应的外设信息
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "GetDevExtModel" })
	@ResponseBody
	public ResultEntity GetDevExtModel(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result=deviceExtConfigService.GetDevExtModel(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
}
