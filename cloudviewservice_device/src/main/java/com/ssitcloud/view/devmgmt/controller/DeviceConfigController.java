package com.ssitcloud.view.devmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.devmgmt.service.DeviceConfigService;

@Controller
@RequestMapping(value={"devconf"})
public class DeviceConfigController extends BasicController{
	@Resource
	private DeviceConfigService deviceConfigService;
	
	
	/**
	 * 根据device_idx查询对应的device_config
	 * req={
	 * 	device_idx:"xxx"
	 * }
	 * @author LBH
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryDeviceConfigByDeviceIdx"})
	@ResponseBody
	public ResultEntity queryDeviceConfigByDeviceIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceConfigService.queryDeviceConfigByDeviceIdx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("queryMongoInstances")
	@ResponseBody
	public ResultEntity queryMongoInstances(HttpServletRequest request,String req){
		ResultEntity res = null;
		Operator oper=getCurrentUser();
		if(oper!=null && Operator.SSITCLOUD_ADMIN.equals(oper.getOperator_type())){
			res=deviceConfigService.queryMongoInstances(req);
		}
		return res;
	}
}
