package com.ssitcloud.business.devmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.devmgmt.service.DeviceMonConfService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value={"monconf"})
public class DeviceMonConfController {
	
	@Resource
	private DeviceMonConfService deviceMonConfService;
	
	@RequestMapping(value={"queryDeviceMonitorByParam"})
	@ResponseBody
	public ResultEntity queryDeviceMonitorByParam(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.queryDeviceMonitorByParam(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	@RequestMapping(value={"AddNewDeviceMonitorTemp"})
	@ResponseBody
	public ResultEntity AddNewDeviceMonitorTemp(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.AddNewDeviceMonitorTemp(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		
		return result;
	}
	
	@RequestMapping(value={"AddNewDeviceMonitorConfAndTemp"})
	@ResponseBody
	public ResultEntity AddNewDeviceMonitorConfAndTemp(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.AddNewDeviceMonitorConfAndTemp(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		
		return result;
	}
	/**
	 * 修改
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"UpdNewDeviceMonitorConfAndTemp"})
	@ResponseBody
	public ResultEntity UpdNewDeviceMonitorConfAndTemp(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.UpdNewDeviceMonitorConfAndTemp(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 删除 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"DelNewDeviceMonitorConfAndTemp"})
	@ResponseBody
	public ResultEntity DelNewDeviceMonitorConfAndTemp(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.DelNewDeviceMonitorConfAndTemp(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value={"SelDevMonConfMetaLogObjByDeviceMonTplIdx"})
	@ResponseBody
	public ResultEntity SelDevMonConfMetaLogObjByDeviceMonTplIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.SelDevMonConfMetaLogObjByDeviceMonTplIdx(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 查询设备颜色
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryAlertColor"})
	@ResponseBody
	public ResultEntity queryAlertColor(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.queryAlertColor_bus(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 更新操作(删除后在新增)
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年6月13日
	 * @author hwl
	 */
	@RequestMapping(value={"UpdDeviceMonitor"})
	@ResponseBody
	public ResultEntity UpdDeviceMonitor(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.UpdMonitor(json);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value={"AddDeviceMonitor"})
	@ResponseBody
	public ResultEntity AddDeviceMonitor(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.AddMonitor(json);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value={"DelDeviceMonitor"})
	@ResponseBody
	public ResultEntity DelDeviceMonitor(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.DelMonitor(json);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 查询当前自定义监控配置数据
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年6月14日
	 * @author hwl
	 */
	@RequestMapping(value={"SelDeviceMonitorConf"})
	@ResponseBody
	public ResultEntity SelDeviceMonitorConf(HttpServletRequest request,String json){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.SelMonitor(json);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 获取当前页面的 设备设置的监控颜色
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"GetCurrentDevColorSet"})
	@ResponseBody
	public ResultEntity GetCurrentDevColorSet(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceMonConfService.GetCurrentDevColorSet(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
}
