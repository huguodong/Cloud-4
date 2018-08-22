package com.ssitcloud.business.devmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.devmgmt.service.DeviceMonConfService;
import com.ssitcloud.common.entity.ResultEntity;

@RequestMapping(value={"plclogicobj"})
@Controller
public class PlcLogicObjController {
	/**
	 * 获取PLC_logic_obj 信息
	 */
	@Resource
	private DeviceMonConfService deviceMonConfService;
	
	@RequestMapping(value={"SelPlcLogicObjectEntity"})
	@ResponseBody
	public ResultEntity SelPlcLogicObjectEntity(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.SelPlcLogicObjectEntity();
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
