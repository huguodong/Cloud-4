package com.ssitcloud.business.devmgmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.devmgmt.service.SpecialDeviceService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping("specialDevice")
public class SpecialDeviceController {
	@Autowired
	private SpecialDeviceService  specailDeviceService;
	
	@RequestMapping("addSpecialDevice")
	@ResponseBody
	public ResultEntity addSpecialDevice(String req){
		
		return specailDeviceService.addSpecialDevice(req);
	}
	
	@RequestMapping("querySpecialDeviceByPage")
	@ResponseBody
	public ResultEntity querySpecialDeviceByPage(String req){
		return specailDeviceService.querySpecialDeviceByPage(req);
	}
	
	@RequestMapping("querySpecialDeviceByParams")
	@ResponseBody
	public ResultEntity querySpecialDeviceByParams(String req){
		return specailDeviceService.querySpecialDeviceByParams(req);
	}
	
	@RequestMapping("editSpecialDevice")
	@ResponseBody
	public ResultEntity editSpecialDevice(String req){
		return specailDeviceService.editSpecialDevice(req);
	}
	
	@ResponseBody
	@RequestMapping("deleteSpecialDevice")
	public ResultEntity deleteSpecialDevice(String req){
		return specailDeviceService.deleteSpecialDevice(req);
	}

}
