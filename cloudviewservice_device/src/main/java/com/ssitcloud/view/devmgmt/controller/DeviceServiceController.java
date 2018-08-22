package com.ssitcloud.view.devmgmt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DeviceServiceEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.devmgmt.service.DeviceServiceService;

@Controller
@RequestMapping("deviceService")
public class DeviceServiceController extends BasicController {
	
	@Autowired
	private DeviceServiceService deviceService;
	
	@RequestMapping("main")
	public String main(HttpServletRequest request,String req){
		if(req != null&& req.length() >0){
			request.setAttribute("returnInfo", req);
		}
		return "/page/deviceservice/deviceServiceManager";
	}
	
	@RequestMapping("deviceServiceRegister")
	public String deviceServiceRegister(HttpServletRequest request,String req){
		request.setAttribute("returnInfo", req);
		return "/page/deviceservice/deviceServiceRegister";
	}
	
	@RequestMapping("queryDeviceServiceByPage")
	@ResponseBody
	public ResultEntity queryDeviceServiceByPage(String req){
		return deviceService.queryDeviceServiceByPage(req);
	}
	
	
	@RequestMapping("deviceServiceRegisterSubmit")
	@ResponseBody
	public ResultEntity deviceServiceRegisterSubmit(String req){
		return deviceService.deviceServiceRegister(req);
	}
	
	@RequestMapping("editDeviceServiceRegister")
	public ModelAndView editDeviceServiceRegister(String returnInfo,String req) {
		DeviceServiceEntity deviceServiceEntity = JsonUtils.fromJson(req, DeviceServiceEntity.class);
		ModelAndView andView = new ModelAndView();
		andView.setViewName("/page/deviceservice/editDeviceServiceRegister");
		andView.addObject("deviceServiceEntity", deviceServiceEntity);
		andView.addObject("returnInfo", returnInfo);
		return andView;
	}
	
	@RequestMapping("editDeviceServiceRegisterSubmit")
	@ResponseBody
	public ResultEntity editDeviceServiceRegisterSubmit(String req){
		return deviceService.editDeviceServiceRegister(req);
	}
	
	@RequestMapping("queryDeviceServiceRegister")
	@ResponseBody
	public ResultEntity queryDeviceServiceRegister(String req){
		return deviceService.queryDeviceServiceRegister(req);
	}
	
	@RequestMapping("deleteDeviceService")
	@ResponseBody
	public ResultEntity deleteDeviceService(String req){
		return deviceService.deleteDeviceService(req);
	}
	
	@ResponseBody
	@RequestMapping("queryDeviceServiceByParams")
	public ResultEntity queryDeviceServiceByParams(String req){
		return deviceService.queryDeviceServiceByParams(req);
	}
	
	/***
	 * 将数据库基础配置信息下发到设备端
	 * 
	 * */
	@ResponseBody
	@RequestMapping("addCloudDbSyncConfig")
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
