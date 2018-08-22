package com.ssitcloud.business.common.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.service.BasicService;
import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping("permission")
public class PermessionController {
	@Resource(name="basicServiceImpl")
	private BasicService basicService;
	
	/**
	 * 通过用户IDX获取权限
	 * @param req
	 * @param json
	 * @return
	 */
	@RequestMapping("SelPermissionbyOperIdx")
	@ResponseBody
	public ResultEntity SelPermissionbyOperIdx(HttpServletRequest req,String json){
		ResultEntity result=new ResultEntity();
		try {
			String reuslt=basicService.queryPermession(json);
			return JsonUtils.fromJson(reuslt, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 获取设备 用户的权限
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("SelUserCmdsByIdxAndRestriDevice")
	@ResponseBody
	public ResultEntity SelUserCmdsByIdxAndRestriDevice(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			String reuslt=basicService.queryPermessionByDevice(req);
			return JsonUtils.fromJson(reuslt, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 云平台管理员获取所有权限
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"SelPermissionBySsitCloudAdmin"})
	@ResponseBody
	public ResultEntity SelPermissionBySsitCloudAdmin(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=basicService.SelPermissionBySsitCloudAdmin(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
}
