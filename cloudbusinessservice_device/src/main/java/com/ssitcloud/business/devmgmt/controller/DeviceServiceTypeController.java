package com.ssitcloud.business.devmgmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.devmgmt.service.DeviceServiceTypeService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping("deviceServiceType")
public class DeviceServiceTypeController {
	@Autowired
	private DeviceServiceTypeService serviceType;
	
	@RequestMapping("queryDeviceServiceTypeByParams")
	@ResponseBody
	public ResultEntity queryDeviceServiceTypeByParams(String req){
		return serviceType.queryDeviceServiceTypeByParams(req);
		
	}
	
	@RequestMapping("queryDeviceServiceTypeByPage")
	@ResponseBody
	public ResultEntity queryDeviceServiceTypeByPage(String req){
		return serviceType.queryDeviceServiceTypeByPage(req);
	}
	
	@RequestMapping("addDeviceServiceType")
	@ResponseBody
	public ResultEntity addDeviceServiceType(String req){
		return serviceType.addDeviceServiceType(req);
	}
	
	@ResponseBody
	@RequestMapping("deleteDeviceServiceType")
	public ResultEntity deleteDeviceServiceType(String req){
		return serviceType.deleteDeviceServiceType(req);
	}
	
	@ResponseBody
	@RequestMapping("editDeviceServiceType")
	public ResultEntity editDeviceServiceType(String req){
		return serviceType.editDeviceServiceType(req);
	}
	
	
}
