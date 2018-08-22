package com.ssitcloud.view.devmgmt.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.SpecialDeviceEntity;
import com.ssitcloud.devmgmt.entity.SpecialDevicePageEntity;

import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.devmgmt.service.SpecialDeviceService;
@Controller
@RequestMapping("specialDevice")
public class SpecialDeviceController extends BasicController {
	
	@Autowired
	private SpecialDeviceService specialDeviceService;
	
	@RequestMapping("main")
	public ModelAndView main(String req){
		ModelAndView andView = new ModelAndView();
		andView.setViewName("/page/specialdevice/specialDeviceManager");
		
		andView.addObject("specialDevice", req);
		return andView;
	}
	
	@RequestMapping("addSpecialDevice")
	public ModelAndView addSpecialDevice(HttpServletRequest request,String req){
		ModelAndView andView = new ModelAndView();
		andView.addObject("specialDevice", req);
		
		andView.setViewName("/page/specialdevice/addSpecialDevice");
		return andView;
	}
	
	@RequestMapping("addSpecialDeviceSubmit")
	@ResponseBody
	public ResultEntity addSpecailDeviceSubmit(String req){
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
	public ModelAndView editSpecialDevice(String returnInfo,String req){
		 ModelAndView andView = new ModelAndView();
		 andView.setViewName("/page/specialdevice/editSpecialDevice");
		 SpecialDeviceEntity deviceEntity = JsonUtils.fromJson(req, SpecialDeviceEntity.class);
		 andView.addObject("deviceEntity",deviceEntity);
		 andView.addObject("specialDevice", returnInfo);
		 return andView;
	}
	
	@RequestMapping("editSpecialDeviceSubmit")
	@ResponseBody
	public ResultEntity editSpecialDeviceSubmit(String req){
		return specialDeviceService.editSpecialDevice(req);
	}
	
	@ResponseBody
	@RequestMapping("deleteSpecialDevice")
	public ResultEntity deleteSpecialDevice(String req){
		return specialDeviceService.deleteSpecialDevice(req);
		
	}
	
	

}
