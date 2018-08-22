/**
 * 
 */
package com.ssitcloud.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.DeviceLogConfigEntity;

import com.ssitcloud.service.DeviceLogConfigService;

/**
 * @comment
 * 
 * @author hwl
 * @data 2016年4月11日
 */
@Controller
@RequestMapping("/devicelogconf")
public class DeviceLogConfigController {

	@Resource
	DeviceLogConfigService deviceLogConfigService;

	/**
	 * 新建设备运行日志配置数据 
	 * 格式(device_log_idx自增id，不用输入) 
	 * { 
	 * "device_log_idx":"",
	 * "device_idx":"" ,
	 * "runlog_level":"",
	 * "runlog_type":"" ,
	 * "runlog_filesize":"",
	 * "library_idx":"" 
	 *  }
	 * 
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "AddDeviceLogConfig" })
	@ResponseBody
	public ResultEntity AddDeviceLogConfig(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			String json = request.getParameter("json");
			int re = deviceLogConfigService.addDeviceLogConfig(JsonUtils
					.fromJson(json, DeviceLogConfigEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 更新设备运行日志配置数据 格式 { "device_log_idx":""，"device_idx":""，"runlog_level":""，
	 * "runlog_type":""，"runlog_filesize":""，"library_idx":"" }
	 * 
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "UpdDeviceLogConfig" })
	@ResponseBody
	public ResultEntity UpdDeviceLogConfig(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			String json = request.getParameter("json");
			int re = deviceLogConfigService.updateDeviceLogConfig(JsonUtils
					.fromJson(json, DeviceLogConfigEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 删除设备运行日志配置数据
	 * 
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "DelDeviceLogConfig" })
	@ResponseBody
	public ResultEntity DeleteDeviceLogConfig(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			String json = request.getParameter("json");
			int re = deviceLogConfigService.deleteDeviceLogConfig(JsonUtils.fromJson(json,  DeviceLogConfigEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 条件查询设备日志数据
	 * 
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "SelectDeviceLogConfig" })
	@ResponseBody
	public ResultEntity SelectDeviceLogConfig(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			List<DeviceLogConfigEntity> devicelogconfig = deviceLogConfigService
					.selectDeviceLogConfig(JsonUtils.fromJson(
							request.getParameter("json"),
							DeviceLogConfigEntity.class));
			result.setResult(devicelogconfig);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
