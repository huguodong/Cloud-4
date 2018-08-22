package com.ssitcloud.business.devmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.devmgmt.service.DeviceRunTempService;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * 设备管理运行模板配置
 *
 * <p>2016年6月18日 下午3:43:20  
 * @author hjc 
 *
 */
@Controller
@RequestMapping("/runtemp")
public class DeviceRunTempController {
	
	@Resource
	private DeviceRunTempService deviceRunTempService;
	
	/**
	 * 	根据参数查询运行模板配置分页信息
	 *
	 * <p>2016年5月19日 下午1:35:36 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/selRunTempListByParam")
	@ResponseBody
	public ResultEntity selRunTempListByParam(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			
			String json = request.getParameter("json");
			String response = deviceRunTempService.SelRunTempListByParam(json);
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
	@RequestMapping("/addRunTemp")
	@ResponseBody
	public ResultEntity addRunTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = deviceRunTempService.addRunTemp(req);
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
	@RequestMapping("/updateRunTemp")
	@ResponseBody
	public ResultEntity updateRunTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = deviceRunTempService.updateRunTemp(req);
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
	@RequestMapping("/delRunTemp")
	@ResponseBody
	public ResultEntity delRunTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = deviceRunTempService.delRunTemp(req);
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
	@RequestMapping("/delMultiRunTemp")
	@ResponseBody
	public ResultEntity delMultiRunTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = deviceRunTempService.delMultiRunTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
}
