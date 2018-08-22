package com.ssitcloud.business.devmgmt.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.devmgmt.service.DeviceTypeService;
import com.ssitcloud.common.entity.ResultEntity;
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
	
	/**
	 * 
	 * @methodName 
	 * @returnType ResultEntity
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping("/SelectMetaDeviceType")
	@ResponseBody
	public ResultEntity SelectDeviceType(HttpServletRequest request){
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
			String resps = deviceTypeService.SelectType(request.getParameter("json"));
			
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	
	/**
	 * 
	 * @methodName 
	 * @returnType ResultEntity
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping("/DeleteMetaDeviceType")
	@ResponseBody
	public ResultEntity DeleteDeviceType(HttpServletRequest request){
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
	public ResultEntity AddDeviceType(HttpServletRequest request){
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
	public ResultEntity UpdDeviceType(HttpServletRequest request){
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
	 * 查询 设备类型 按 device_type 分组
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selAllDeviceTypeGroupByType"})
	@ResponseBody
	public ResultEntity selAllDeviceTypeGroupByType(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result = deviceTypeService.selAllDeviceTypeGroupByType();			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
		
	}
	/**
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryDeviceTypeLogicObj"})
	@ResponseBody
	public ResultEntity queryDeviceTypeLogicObj(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result = deviceTypeService.queryDeviceTypeLogicObj(req);			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}

	
	/**
	 * 根据名字查设备类型
	 * @param request
	 * @param req
	 * @return
	 * @author dell 20161124
	 */
	@RequestMapping(value={"queryDeviceTypeByName"})
	@ResponseBody
	public ResultEntity queryDeviceTypeByName(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result = deviceTypeService.queryDeviceTypeByName(req);			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
}
