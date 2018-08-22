package com.ssitcloud.business.devmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.devmgmt.service.DeviceExtLogicService;
import com.ssitcloud.common.entity.ResultEntity;

/** 
 * 设备管理里的硬件与逻辑名模板配置
 * <p>2016年5月19日 上午11:49:37  
 * @author hjc 
 *
 */
@Controller
@RequestMapping("/extlogic")
public class DeviceExtLogicController {
	
	@Resource
	private DeviceExtLogicService deviceExtLogicService;
	
	/**
	 * 
	 *
	 * <p>2016年5月19日 下午1:35:36 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/SelExtTempListByParam")
	@ResponseBody
	public ResultEntity SelExtTempListByParam(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			
			String json = request.getParameter("json");
			String response = deviceExtLogicService.SelExtTempListByParam(json);
			if(response!=null){
				result = JsonUtils.fromJson(response, ResultEntity.class);
			}else{
				result.setState(false);
				result.setMessage("连接失败");
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	/**
	 * 新增外设模板信息
	 *
	 * <p>2016年6月15日 下午5:13:24 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/addExtTemp")
	@ResponseBody
	public ResultEntity addExtTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = deviceExtLogicService.addExtTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 更新外设逻辑模板信息
	 *
	 * <p>2016年6月15日 下午5:12:23 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/updateExtTemp")
	@ResponseBody
	public ResultEntity updateExtTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = deviceExtLogicService.updateExtTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 删除模板信息
	 *
	 * <p>2016年6月16日 下午2:56:36 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/delExtTemp")
	@ResponseBody
	public ResultEntity delExtTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = deviceExtLogicService.delExtTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 删除模板信息
	 *
	 * <p>2016年6月16日 下午2:56:36 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/delMultiExtTemp")
	@ResponseBody
	public ResultEntity delMultiExtTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = deviceExtLogicService.delMultiExtTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
}
