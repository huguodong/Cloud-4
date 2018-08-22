package com.ssitcloud.dblib.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.ExceptionHelper;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.entity.RegisterIdentifiedcodeEntity;
import com.ssitcloud.dblib.service.RegisterIdentifiedcodeService;

@Controller
@RequestMapping("/registerCode")
public class RegisterIdentifiedcodeController {

	@Autowired
	private RegisterIdentifiedcodeService service;
	
	@RequestMapping("/addCode")
	@ResponseBody
	public ResultEntity addCode(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				RegisterIdentifiedcodeEntity identifiedcodeEntity = JsonUtils.fromJson(json, RegisterIdentifiedcodeEntity.class);
				boolean ret = service.insert(identifiedcodeEntity);
				if (ret) {
					resultEntity.setValue(true, "success");
				}else{
					resultEntity.setValue(false, "failed");
				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping("/selectCode")
	@ResponseBody
	public ResultEntity selectCode(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
				String email=(String) map.get("email");
				if(email != null){
					RegisterIdentifiedcodeEntity code = service.selectCode(email);
					resultEntity.setValue(true, "success", "", code);
				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping("/deleteCodeByPk")
	@ResponseBody
	public ResultEntity deleteCodeByPk(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
				String email=(String) map.get("email");
				if(email != null){
					service.deleteByPk(email);
					resultEntity.setValue(true, "success", "", null);
				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
}
