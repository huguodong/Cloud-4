package com.ssitcloud.view.devmgmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.devmgmt.service.SpecialDeviceTypeService;

@Controller
@RequestMapping("specialDeviceType")
public class SpecialDeviceTypeController {
	
	@Autowired
	private SpecialDeviceTypeService deviceType;
	
	@RequestMapping("querySpecialDeviceTypeByParams")
	@ResponseBody
	public ResultEntity querySpecialDeviceTypeByParams(String req){
		
		return deviceType.querySpecialDeviceTypeByParams(req);
		
	}
	
	@RequestMapping("main")
	public String main(){
		return "/page/specialdevice/specialDeviceType";
	}
	
	@RequestMapping("querySpecialDeviceTypeByPage")
	@ResponseBody
	public ResultEntity querySpecialDeviceTypeByPage(String req){
		
		return deviceType.querySpecialDeviceTypeByPage(req);
	}
	
	@RequestMapping("addSpecialDeviceType")
	@ResponseBody
	public ResultEntity addSpecialDeviceType(String req){
		return deviceType.addSpecialDeviceType(req);
	}
	
	@ResponseBody
	@RequestMapping("deleteSpecialDeviceType")
	public ResultEntity deleteSpecialDeviceType(String req){
		return deviceType.deleteSpecialDeviceType(req);
	}
	
	@ResponseBody
	@RequestMapping("editSpecialDeviceType")
	public ResultEntity editSpecialDeviceType(String req){
		return deviceType.editSpecialDeviceType(req);
	}
	

}