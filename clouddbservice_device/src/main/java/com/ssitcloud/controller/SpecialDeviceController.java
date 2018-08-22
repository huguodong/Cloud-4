package com.ssitcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.service.SpecialDeviceService;

@Controller
@RequestMapping("specialDevice")
public class SpecialDeviceController  {
	@Autowired
	private SpecialDeviceService specialDeviceService;
	
	@ResponseBody
	@RequestMapping("addSpecialDevice")
	public ResultEntity addSpecialDevice(String req){
		return specialDeviceService.addSpecialDevice(req);
	}
	
	@RequestMapping("querySpecialDeviceByPage")
	@ResponseBody
	public ResultEntity querySpecialDeviceByPage(String req){
		return specialDeviceService.querySpecialDeviceByPage(req);
	}
	@RequestMapping("querySpecialDeviceByParams")
	@ResponseBody
	public ResultEntity querySpecialDeviceByParams(String req){
		return specialDeviceService.querySpecialDeviceByParams(req);
	}
	
	@RequestMapping("editSpecialDevice")
	@ResponseBody
	public ResultEntity editSpecialDevice(String req){
		return specialDeviceService.editSpecialDevice(req);
	}
	
	@RequestMapping("deleteSpecialDevice")
	@ResponseBody
	public ResultEntity deleteSpecialDevice(String req){
		return specialDeviceService.deleteSpecialDevice(req);
	}
}
