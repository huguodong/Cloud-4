package com.ssitcloud.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.MetadataDeviceDbAndTableInfoEntity;
import com.ssitcloud.entity.MetadataDeviceDbEntity;
import com.ssitcloud.service.MetadataDeviceDbService;

@Controller
@RequestMapping("/metadevicedb")
public class MetadataDeviceDbController {

	@Resource
	MetadataDeviceDbService metadataDeviceDbService;

	
	@RequestMapping(value = { "SelectMetadataDeviceDb" })
	@ResponseBody
	public ResultEntity SelectMetadataLogicObj(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if(json==null){
				json=req;
			}
			List<MetadataDeviceDbEntity> list = metadataDeviceDbService.selectMetadataDeviceDb(JsonUtils.fromJson(json,
					MetadataDeviceDbEntity.class));
			result.setResult(list);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "SelectMetadataDeviceDbAndTableInfo" })
	@ResponseBody
	public ResultEntity SelectMetadataDeviceDbAndTableInfo(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			List<MetadataDeviceDbAndTableInfoEntity> list = metadataDeviceDbService.SelectMetadataDeviceDbAndTableInfo(JsonUtils.fromJson(req,
					MetadataDeviceDbEntity.class));
			result.setResult(list);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
