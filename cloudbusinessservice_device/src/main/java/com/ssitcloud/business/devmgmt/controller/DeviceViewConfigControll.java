package com.ssitcloud.business.devmgmt.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.devmgmt.service.DeviceViewConfigService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping("deviceViewConfig")
public class DeviceViewConfigControll {
	@Resource
	private DeviceViewConfigService viewConfigService;
	@RequestMapping("queryDeviceViewConfig")
	@ResponseBody
	public ResultEntity queryDeviceViewConfig(String req){
		
		return viewConfigService.queryDeviceViewConfig(req);
	}
	
	@RequestMapping("updateDeviceViewConfig")
	@ResponseBody
	public ResultEntity updateDeviceViewConfig(String req){
		return viewConfigService.updateDeviceViewConfig(req);
	}
	
	@RequestMapping("queryDeviceViewConfigSet")
	@ResponseBody
	public ResultEntity queryDeviceViewConfigSet(String req){
		return viewConfigService.queryDeviceViewConfigSet(req);
	}
	
	@RequestMapping("queryDeviceViewConfigFieldLabel")
	@ResponseBody
	public ResultEntity queryDeviceViewConfigFieldLabel(String req){
		return viewConfigService.queryDeviceViewConfigFieldLabel(req);
		
	}
	
	@RequestMapping("deleteDeviceViewConfigSet")
	public void deleteDeviceViewConfigSet(String req){
		viewConfigService.deleteDeviceViewConfigSet(req);
	}
	
	@RequestMapping("queryLabelByFieldset")
	@ResponseBody
	public ResultEntity queryLabelByFieldset(String req){
		
		return viewConfigService.queryLabelByFieldset(req);
	}
	
	

}
