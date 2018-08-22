package com.ssitcloud.business.devmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.devmgmt.service.DevcieStateService;
import com.ssitcloud.common.entity.ResultEntity;
/**
 * 设备状态信息获取（主要从mongodb获取数据）
 * @author lbh
 *
 * 2016年5月9日
 */
@RequestMapping(value={"devicestate"})
@Controller
public class DeviceStateController {

	@Resource
	private DevcieStateService deviceStateService;
	/**
	 * 获取设备外设状态
	 * req ={["device_id":"1","device_id":"2",......]}
	 * @Description: TODO
	 * @param @param request
	 * @param @param req
	 * @param @return   
	 * @return ResultEntity  
	 * @throws
	 * @author lbh
	 * @date 2016年5月9日
	 */
	@RequestMapping(value={"selectDeviceState"})
	@ResponseBody
	public ResultEntity selectDeviceState(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceStateService.selectDeviceState(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		
		return result;
	}
	/**
	 * 获取书架状态信息
	 * req ={["device_id1","device_id2",......]}
	 * @Description: TODO
	 * @param @param request
	 * @param @param req
	 * @param @return   
	 * @return ResultEntity  
	 * @throws
	 * @author lbh
	 * @date 2016年5月9日
	 */
	@RequestMapping(value={"selectBookrackState"})
	@ResponseBody
	public ResultEntity selectBookrackState(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceStateService.selectBookrackState(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		
		return result;
	}
	/**
	 * req ={["device_id1","device_id2",......]}
	 * @Description: TODO
	 * @param @param request
	 * @param @param req
	 * @param @return   
	 * @return ResultEntity  
	 * @throws
	 * @author lbh
	 * @date 2016年5月9日
	 */
	@RequestMapping(value={"selectBinState"})
	@ResponseBody
	public ResultEntity selectBinState(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceStateService.selectBinState(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		
		return result;
	}
	/**
	 * req ={["device_id1","device_id2",......]}
	 * @Description: TODO
	 * @param @param request
	 * @param @param req
	 * @param @return   
	 * @return ResultEntity  
	 * @throws
	 * @author lbh
	 * @date 2016年5月10日
	 */
	@RequestMapping(value={"selectDeviceExtState"})
	@ResponseBody
	public ResultEntity selectDeviceExtState(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceStateService.selectDeviceExtState(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		
		return result;
	}
	/**
	 * 获取功能状态信息
	 * req ={["device_id1","device_id2",......]}
	 * @Description: TODO
	 * @param @param request
	 * @param @param req
	 * @param @return   
	 * @return ResultEntity  
	 * @throws
	 * @author lbh
	 * @date 2016年5月10日
	 */
	@RequestMapping(value={"selectSoftState"})
	@ResponseBody
	public ResultEntity selectSoftState(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceStateService.selectSoftState(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		
		return result;
	}
	/**
	 * 获取数据库名， 数据库IP
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"getMongodbNames"})
	@ResponseBody
	public ResultEntity getMongodbNames(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=deviceStateService.getMongodbNames(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		
		return result;
	}
	
}
