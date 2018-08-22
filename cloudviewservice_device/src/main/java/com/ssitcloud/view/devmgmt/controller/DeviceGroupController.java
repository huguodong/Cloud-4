package com.ssitcloud.view.devmgmt.controller;



import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.SystemLogUtil;
import com.ssitcloud.view.devmgmt.service.DeviceGroupService;

/**
 * 设备分组视图控制
 * 
 * @package: com.ssitcloud.devmgmt.controller
 * @classFile: DeviceGroupController
 * @author: liuBh
 * @description: TODO
 */
@Controller
@RequestMapping("/devicegroup")
public class DeviceGroupController extends BasicController{

	@Resource
	DeviceGroupService deviceGroupService;
	
	@RequestMapping(value = { "main" })
	public String main(HttpServletRequest request){
		
		return "/page/devmgmt/devicegroup";
	}
	/**
	 * 添加设备组数据
	 * 
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "AddDeviceGroup" })
	@ResponseBody
	public ResultEntity AddDeviceGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			String resps = deviceGroupService.AddDeviceGroup(request.getParameter("json"));
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_ADD_DEV_GROUP);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 更新设备组数据
	 * 
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "UpdDeviceGroup" })
	@ResponseBody
	public ResultEntity UpdDeviceGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			String resps = deviceGroupService.UpdDeviceGroup(request.getParameter("json"));
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_UPDATE_DEV_GROUP);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 删除设备组数据
	 * 
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "DeleteDeviceGroup" })
	@ResponseBody
	public ResultEntity DeleteDeviceGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			String resps = deviceGroupService.DeleteDeviceGroup(request.getParameter("json"));
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_DELETE_DEV_GROUP);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 根据条件查询设备组数据
	 * 
	 * @param request
	 * @return
	 * @author hwl
	 */
	
	
	@RequestMapping(value = { "SelectDeviceGroup" })
	@ResponseBody
	public ResultEntity SelectDeviceGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("json", request.getParameter("json"));
			map.put("page", request.getParameter("page"));
			
			/*Subject currentUser = SecurityUtils.getSubject();
			String  object = currentUser.getPrincipal().toString();
			Operator operator = JsonUtils.fromJson(object, Operator.class);
			
			if (operator.getOperator_type().equals(3)) {
				HashMap<String, String> jsonMap = JsonUtils.fromJson(req, HashMap.class);
				jsonMap.put("library_idx", operator.getLibrary_idx());
				req = JsonUtils.toJson(jsonMap);
			}*/
			String resps = deviceGroupService.SelDeviceGroup(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "SelectGroup" })
	@ResponseBody
	public ResultEntity SelectGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			String resps = deviceGroupService.SelectGroup(request.getParameter("json"));
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 根据条件查询 设备分组 不分页
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"SelectGroupByParam"})
	@ResponseBody
	public ResultEntity SelectGroupByParam(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			 result = deviceGroupService.SelectGroupByParam(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
		
	}
	/**
	 * 根据设备IDX查询设备所在分组信息
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selectGroupByDeviceIdx"})
	@ResponseBody
	public ResultEntity selectGroupByDeviceIdx(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			 result = deviceGroupService.selectGroupByDeviceIdx(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
