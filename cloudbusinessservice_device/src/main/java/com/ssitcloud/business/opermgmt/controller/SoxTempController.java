package com.ssitcloud.business.opermgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.opermgmt.service.SoxTempService;
import com.ssitcloud.common.entity.ResultEntity;

/** 
 *
 * <p>2016年6月21日 下午4:31:22  
 * @author hjc 
 *
 */
@Controller
@RequestMapping("/soxtemp")
public class SoxTempController {
	@Resource
	private SoxTempService soxTempService;
	
	/**
	 * 根据参数获取鉴权模板分类信息
	 *
	 * <p>2016年6月21日 下午4:46:48 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/getSoxTempListByParam")
	@ResponseBody
	public ResultEntity getSoxTempListByParam(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req)) {
				req = "";
			}
			resultEntity = soxTempService.getSoxTempListByParam(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 新增模板数据
	 *
	 * <p>2016年6月22日 下午3:42:14 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/addSoxTemp")
	@ResponseBody
	public ResultEntity addSoxTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = soxTempService.addSoxTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 更新模板数据
	 *
	 * <p>2016年6月22日 下午3:42:10 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateSoxTemp")
	@ResponseBody
	public ResultEntity updateSoxTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = soxTempService.updateSoxTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 删除模板
	 *
	 * <p>2016年6月22日 下午4:59:52 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/delSoxTemp")
	@ResponseBody
	public ResultEntity delSoxTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isBlank(json) || json.equals("{}")) {
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = soxTempService.delSoxTemp(json);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 删除多个模板
	 *
	 * <p>2016年6月22日 下午4:59:55 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/delMultiSoxTemp")
	@ResponseBody
	public ResultEntity delMultiSoxTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isBlank(json) || json.equals("{}")) {
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = soxTempService.delMultiSoxTemp(json);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
}
