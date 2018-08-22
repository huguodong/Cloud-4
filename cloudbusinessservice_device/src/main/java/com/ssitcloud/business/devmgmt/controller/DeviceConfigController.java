package com.ssitcloud.business.devmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.devmgmt.service.DeviceConfigService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value={"deviceconf"})
public class DeviceConfigController {
	@Resource
	private DeviceConfigService deviceConfigService;
	
	/**
	 * 根据device_idx查询对应的device_config
	 * req={
	 * 	device_idx:"xxx"
	 * }
	 * @author LBH
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryDeviceConfigByDeviceIdx"})
	@ResponseBody
	public ResultEntity queryDeviceConfigByDeviceIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceConfigService.queryDeviceConfigByDeviceIdx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 
	 * 
	 * 通过查询 device-config-old 表 和 deivce_config 表 ，查询那个字段放生变化
	 * 发生变化的字段推理出放生变化的表名
	 * 
	 * 返回需要同步的表名 逗号分隔
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryDeviceConfigNewAndOldByDeviceIdx"})
	@ResponseBody
	public ResultEntity queryDeviceConfigNewAndOldByDeviceIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceConfigService.queryDeviceConfigNewAndOldByDeviceIdx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("queryMongoInstances")
	@ResponseBody
	public ResultEntity queryMongoInstances(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceConfigService.queryMongoInstances(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
