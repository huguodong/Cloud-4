package com.ssitcloud.business.devmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.devmgmt.service.MetadataLogicObjService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value={"metalogicobj"})
public class MetadataLogicObjController {
	@Resource
	private MetadataLogicObjService metadataLogicObjService;
	/**
	 * 查询SelectMetadataLogicObj
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"SelectMetadataLogicObj"})
	@ResponseBody
	public ResultEntity SelectMetadataLogicObj(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=metadataLogicObjService.SelectMetadataLogicObj(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
