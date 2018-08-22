package com.ssitcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DeviceServicePageEntity;
import com.ssitcloud.service.DeviceServiceService;

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
	public ResultEntity queryDeviceService(String req){
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
	
	@RequestMapping("updateDeviceServiceOperateTime")
	@ResponseBody
	public ResultEntity updateDeviceServiceOperateTime(String req){
		return deviceService.updateDeviceServiceOperateTime(req);
	}
	
	@RequestMapping("selectCountDeviceService")
	@ResponseBody
	public ResultEntity selectCountDeviceService(String req){
		return deviceService.selectCountDeviceService(req);
	}
	
	
}
