package com.ssitcloud.view.devmgmt.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.SystemLogUtil;
import com.ssitcloud.view.devmgmt.service.DeviceRunTempService;

/** 
 *	设备运行模板配置
 * <p>2016年6月18日 下午3:11:46  
 * @author hjc 
 *
 */
@Controller
@RequestMapping("/devicerun")
public class DeviceRunTempController extends BasicController{
	@Resource
	private DeviceRunTempService deviceRunTempService;
	
	
	@RequestMapping("/main")
	public String gotoMain(){
		return "/page/devmgmt/device-runtemp-manage";
	}
	
	
	/**
	 * 根据参数获取设备的运行模板数据，返回分页数据
	 *
	 * <p>2016年6月18日 下午3:34:14 
	 * <p>create by hjc
	 * @param json
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/getRunTempListByParam")
	@ResponseBody
	public ResultEntity getExtTempListByParam(String json,String page,HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (StringUtils.isBlank(json)) {
				map.put("json", "");
			}else {
				map.put("json", json);
			}
			String response = deviceRunTempService.selRunTempListByParam(map);
			if(response!=null){
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				resultEntity.setState(false);
				resultEntity.setMessage("连接失败");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 新增设备运行模板
	 *
	 * <p>2016年6月20日 下午3:17:38 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/addRunTemp")
	@ResponseBody
	public ResultEntity addRunTemp(HttpServletRequest request,String json){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(json) || json.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空", "", "");
				return resultEntity;
			}
			//获取操作员的信息
			resultEntity = deviceRunTempService.addRunTemp(json);
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.OPERCMD_ADD_RUNCONF);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 更新设备运行模板
	 *
	 * <p>2016年6月20日 下午3:18:08 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/updateRunTemp")
	@ResponseBody
	public ResultEntity updateRunTemp(HttpServletRequest request,String json){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(json) || json.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空", "", "");
				return resultEntity;
			}
			//获取操作员的信息
			resultEntity = deviceRunTempService.updateRunTemp(json);
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.OPERCMD_UPDATE_RUNCONF);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 删除单条模板数据
	 *
	 * <p>2016年6月20日 下午7:03:23 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/delRunTemp")
	@ResponseBody
	public ResultEntity delRunTemp(HttpServletRequest request,String json){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(json) || json.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空", "", "");
				return resultEntity;
			}
			//获取操作员的信息
			
			resultEntity = deviceRunTempService.delRunTemp(json);
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.OPERCMD_DELETE_RUNCONF);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 批量删除运行模板配置
	 *
	 * <p>2016年6月20日 下午7:08:40 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/delMultiRunTemp")
	@ResponseBody
	public ResultEntity delMultiRunTemp(HttpServletRequest request,String json){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(json) || json.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空", "", "");
				return resultEntity;
			}
			//获取操作员的信息
			resultEntity = deviceRunTempService.delMultiRunTemp(json);
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.OPERCMD_DELETE_RUNCONF);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	

}
