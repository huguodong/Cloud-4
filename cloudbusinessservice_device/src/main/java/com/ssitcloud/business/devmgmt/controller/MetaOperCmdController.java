package com.ssitcloud.business.devmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.common.util.LogUtils;
import com.ssitcloud.business.devmgmt.service.MetaOperCmdService;
import com.ssitcloud.common.entity.ResultEntity;

//metaopercmd/SelectMetaOperCmd
@Controller
@RequestMapping("metaopercmd")
public class MetaOperCmdController {
	
	@Resource
	private MetaOperCmdService metaOperCmdService;
	
	@RequestMapping(value="SelectMetaOperCmd")
	@ResponseBody
	public ResultEntity SelectMetaOperCmd(){
		ResultEntity result=new ResultEntity();
		try {
			String reuslt=metaOperCmdService.SelMetaOperCmd();
			try {
				 LogUtils.info("SERVICE层 SelectMetaOperCmd");
				 LogUtils.info(reuslt);
			} catch (Exception e) {
			}
			
			return JsonUtils.fromJson(reuslt, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value="selectCmdType")
	@ResponseBody
	public ResultEntity selectCmdType(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			result=metaOperCmdService.selectCmdType();
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 查询维护卡一级权限
	 *
	 * <p>2017年3月30日 上午10:23:57 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping(value="selectCardCmdType")
	@ResponseBody
	public ResultEntity selectCardCmdType(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			result=metaOperCmdService.selectCardCmdType();
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value="selectDeviceOperLogCmd")
	@ResponseBody
	public ResultEntity selectDeviceOperLogCmd(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			result=metaOperCmdService.selectDeviceOperLogCmd();
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 权限分组页面 带参数查询、分页
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryServgroupByparam"})
	@ResponseBody
	public ResultEntity queryServgroupByparam(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=metaOperCmdService.queryServgroupByparam(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
}
