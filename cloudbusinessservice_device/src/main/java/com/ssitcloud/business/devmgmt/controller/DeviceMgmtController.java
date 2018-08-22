package com.ssitcloud.business.devmgmt.controller;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.devmgmt.service.DeviceMgmtService;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * 
 * @package com.ssitcloud.devmgmt.controller
 * @comment
 * @data 2016年4月14日
 * @author hwl
 */

@Controller
@RequestMapping("/DeviceMgmt")
public class DeviceMgmtController {

	@Resource
	DeviceMgmtService deviceMgmtService;
	
	/**
	 * 查询设备相关信息
	 * 根据查询条件进行查询(图书馆管理员根据馆id，根据需求添加设备类型，分组来查询)
	 * {
	 *	library_idx:"",
	 *	device_type:"",
	 *	device_group_name："",
	 * }
	 * 
	 * @methodName SelectDeviceMgmt
	 * @returnType ResultEntity
	 * @param request
	 * @param json
	 * @return
	 * @author hwl
	 */
	@RequestMapping("/SelectDeviceMgmt")
	@ResponseBody
	public ResultEntity SelectDeviceMgmt(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			
			String resps = deviceMgmtService.SelectDeviceMgmt(json);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	
	@RequestMapping("/DeleteDeviceMgmt")
	@ResponseBody
	public ResultEntity DeleteDeviceMgmt(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			
			String resps = deviceMgmtService.DeleteDeviceMgmt(json);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	
	/*
	@RequestMapping("/AddDeviceMgmt")
	@ResponseBody
	public ResultEntity AddDeviceMgmt(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			
			String resps = deviceMgmtService.AddDeviceMgmt(json);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}*/
}
