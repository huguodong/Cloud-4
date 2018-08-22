package com.ssitcloud.businessauth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.businessauth.service.BakupService;
import com.ssitcloud.businessauth.utils.LogUtils;
import com.ssitcloud.common.entity.ResultEntity;

@RequestMapping("bakup")
@Controller
public class BakupController {

	@Resource
	private BakupService bakupService;
	
	
	@RequestMapping("bakupOnlyByLiraryIdxSsitAuth")
	@ResponseBody
	public ResultEntity bakupOnlyByLiraryIdxSsitAuth(HttpServletRequest request,String req){
		ResultEntity resultEntity=new ResultEntity();
		try {
			resultEntity= bakupService.bakupOnlyByLiraryIdxSsitAuth(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("restoreAuthDataByLibraryIdx")
	@ResponseBody
	public ResultEntity restoreAuthDataByLibraryIdx(HttpServletRequest request,
			String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = bakupService.restoreDataByLibraryIdx(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
}
