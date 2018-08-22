package com.ssitcloud.business.devmgmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.devmgmt.service.DeviceServiceService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping("deviceService")
public class DeviceServiceController {
	
	@Autowired
	private DeviceServiceService deviceService;
	
	@RequestMapping("deviceServiceRegister")
	@ResponseBody
	public ResultEntity deviceServiceRegister(String req){
		return deviceService.deviceServiceRegister(req);
		
	}
	
	@RequestMapping("queryDeviceServiceByPage")
	@ResponseBody
	public ResultEntity queryDeviceServiceByPage(String req){
		
		return deviceService.queryDeviceServiceByPage(req);
		
	}
	
	@RequestMapping("queryDeviceServiceRegister")
	@ResponseBody
	public ResultEntity queryDeviceServiceRegister(String req){
		return deviceService.queryDeviceServiceRegister(req);
	}
	
	@RequestMapping("editDeviceServiceRegister")
	@ResponseBody
	public ResultEntity editDeviceServiceRegister(String req){
		return deviceService.editDeviceServiceRegister(req);
	}
	
	@RequestMapping("deleteDeviceService")
	@ResponseBody
	public ResultEntity deleteDeviceService(String req){
		return deviceService.deleteDeviceService(req);
	}
	
	@RequestMapping("queryDeviceServiceByParams")
	@ResponseBody
	public ResultEntity queryDeviceServiceByParams(String req){
		return deviceService.queryDeviceServiceByParams(req);
	}
	
	@RequestMapping("addCloudDbSyncConfig")
	@ResponseBody
	public ResultEntity addCloudDbSyncConfig(String req){
		return deviceService.addCloudDbSyncConfig(req);
	}
	
	@ResponseBody
	@RequestMapping("selectCloudDbSyncConfig")
	public ResultEntity selectCloudDbSyncConfig(String req){
		return deviceService.selectCloudDbSyncConfig(req);
	}
	
	@RequestMapping("updateDeviceServiceOperateTime")
	@ResponseBody
	public ResultEntity updateDeviceServiceOperateTime(String req){
		return deviceService.updateDeviceServiceOperateTime(req);
	}
}
