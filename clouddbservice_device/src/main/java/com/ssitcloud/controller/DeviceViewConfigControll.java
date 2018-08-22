package com.ssitcloud.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.service.DeviceViewConfigService;

@Controller
@RequestMapping("deviceViewConfig")
public class DeviceViewConfigControll {
	@Resource
	private DeviceViewConfigService deviceViewConfigService;
	
	@RequestMapping("queryDeviceViewConfig")
	@ResponseBody
	public ResultEntity queryDeviceViewConfig(String req){
		
		return deviceViewConfigService.queryDeviceViewData(req);
		
	}
	
	
	/*@RequestMapping("updateDeviceViewConfig")
	@ResponseBody
	public ResultEntity updateDeviceViewConfig(String req){
		
		return deviceViewConfigService.updateDeviceViewConfig(req);
	}*/
	
	@RequestMapping("queryDeviceViewConfigSet")
	@ResponseBody
	public ResultEntity queryDeviceViewConfigSet(String req){
		return deviceViewConfigService.queryDeviceViewConfigSet(req);
	}
	
	@RequestMapping("deleteDeviceViewConfigSet")
	@ResponseBody
	public ResultEntity deleteDeviceViewConfigSet(String req){
		return deviceViewConfigService.deleteDeviceViewConfigSet(req);
	}
	
	@RequestMapping("insertDeviceViewConfigSet")
	@ResponseBody
	public ResultEntity insertDeviceViewConfigSet(String req){
		return deviceViewConfigService.insertDeviceViewConfigSet(req);
	}

}
