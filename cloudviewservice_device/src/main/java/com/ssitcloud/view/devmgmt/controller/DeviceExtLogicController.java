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
import com.ssitcloud.view.devmgmt.service.DeviceExtLogicService;

/** 
 *
 * <p>2016年5月18日 下午6:42:29  
 * @author hjc 
 *
 */
@Controller
@RequestMapping("/deviceext")
public class DeviceExtLogicController extends BasicController{
	@Resource
	private DeviceExtLogicService deviceExtLogicService;
	
	@RequestMapping("/main")
	public String gotoMain(){
		return "/page/devmgmt/device-exttemp-manage";
	}
	
	
	
	/** 
	 * 获取设备的类型
	 *
	 * <p>2016年4月25日 下午2:42:56
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDeviceTypes")
	@ResponseBody
	public ResultEntity getDeviceTypes(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String response = deviceExtLogicService.SelAllMetadataDeviceType("");
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
	 * 根据参数获取设备的硬件与逻辑模板的数据，返回分页数据
	 *
	 * <p>2016年5月18日 下午7:29:20 
	 * <p>create by hjc
	 * @param json
	 * @param Page
	 * @param request
	 * @return
	 */
	@RequestMapping("/getExtTempListByParam")
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
			String response = deviceExtLogicService.selExtTempListByParam(map);
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
	 * 获取所有的logic_obj和ext_model的元数据
	 *
	 * <p>2016年6月14日 下午7:37:48 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/getAllLogicObjAndExtModel")
	@ResponseBody
	public ResultEntity getAllLogicObjAndExtModel(HttpServletRequest request,String json){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(json)) {
				json = "";
			}
			String response = deviceExtLogicService.selExtTempListByParam(json);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				resultEntity.setValue(false, "连接服务器失败","","");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
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
	public ResultEntity addExtTemp(HttpServletRequest request,String json){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(json) || json.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空", "", "");
				return resultEntity;
			}
			//获取操作员的信息
			resultEntity = deviceExtLogicService.addExtTemp(json);
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.OPERCMD_ADD_EXTCONF);
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
	public ResultEntity updateExtTemp(HttpServletRequest request,String json){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(json) || json.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空", "", "");
				return resultEntity;
			}
			//获取操作员的信息
			resultEntity = deviceExtLogicService.updateExtTemp(json);
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.OPERCMD_UPDATE_EXTCONF);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 删除外设逻辑模板信息
	 *
	 * <p>2016年6月16日 下午2:54:32 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/delExtTemp")
	@ResponseBody
	public ResultEntity delExtTemp(HttpServletRequest request,String json){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(json) || json.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空", "", "");
				return resultEntity;
			}
			//获取操作员的信息
			
			resultEntity = deviceExtLogicService.delExtTemp(json);
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.OPERCMD_DELETE_EXTCONF);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 批量删除外设逻辑模板信息
	 *
	 * <p>2016年6月16日 下午2:54:32 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping("/delMultiExtTemp")
	@ResponseBody
	public ResultEntity delMultiExtTemp(HttpServletRequest request,String json){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isBlank(json) || json.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空", "", "");
				return resultEntity;
			}
			//获取操作员的信息
			
			resultEntity = deviceExtLogicService.delMultiExtTemp(json);
			SystemLogUtil.LogOperation(resultEntity, getCurrentUser(), request, Const.OPERCMD_DELETE_EXTCONF);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
}
