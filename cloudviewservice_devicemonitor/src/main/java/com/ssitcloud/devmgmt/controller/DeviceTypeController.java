package com.ssitcloud.devmgmt.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.devmgmt.service.DeviceTypeService;

/**
 * 
 * @package com.ssitcloud.devmgmt.controller
 * @comment
 * @data 2016年4月18日
 * @author hwl
 */
@Controller
@RequestMapping("/metadevicetype")
public class DeviceTypeController {
		
	@Resource
	DeviceTypeService deviceTypeService;
		
	@RequestMapping("/SelectMetaDeviceType")
	@ResponseBody
	public ResultEntity SelectDeviceMgmt(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("json", request.getParameter("json"));
			map.put("page", request.getParameter("page"));
			String resps = deviceTypeService.SelDeviceType(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	@RequestMapping("/SelectType")
	@ResponseBody
	public ResultEntity SelectType(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			
			String resps = deviceTypeService.QueryType(request.getParameter("json"));
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	
	@RequestMapping("/DeleteMetaDeviceType")
	@ResponseBody
	public ResultEntity DeleteDeviceMgmt(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			
			String resps = deviceTypeService.DeleteDeviceType(request.getParameter("json"));
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	
	@RequestMapping("/AddMetaDeviceType")
	@ResponseBody
	public ResultEntity AddDeviceMgmt(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			
			String resps = deviceTypeService.AddDeviceType(request.getParameter("json"));
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	
	@RequestMapping("/UpdMetaDeviceType")
	@ResponseBody
	public ResultEntity UpdDeviceMgmt(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			
			String resps = deviceTypeService.UpdDeviceType(request.getParameter("json"));
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	/**
	 * 查询 设备类型 按device_type 分组
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selAllDeviceTypeGroupByType"})
	@ResponseBody
	public ResultEntity selAllDeviceTypeGroupByType(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			result = deviceTypeService.selAllDeviceTypeGroupByType();
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	
}
