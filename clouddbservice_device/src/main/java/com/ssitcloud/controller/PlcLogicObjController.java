package com.ssitcloud.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.service.PlcLogicObjService;

@Controller
@RequestMapping(value={"plclogicobj"})
public class PlcLogicObjController {
	
	@Resource
	private PlcLogicObjService plcLogicObjService;

	@RequestMapping(value={"SelPlcLogicObjectEntity"})
	@ResponseBody
	public ResultEntity SelPlcLogicObjectEntity(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			result=plcLogicObjService.SelPlcLogicObjectEntity();
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
		
	}
}
