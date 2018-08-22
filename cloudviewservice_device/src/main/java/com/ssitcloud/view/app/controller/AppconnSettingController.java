package com.ssitcloud.view.app.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.app.service.AppconnSettingService;
import com.ssitcloud.view.common.util.ExceptionHelper;


@Controller
@RequestMapping(value={"appconnsetting"})
public class AppconnSettingController {
	@Resource
	AppconnSettingService appconnSettingService;
	
	
	/**
	 * 页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"main"})
	public String main(HttpServletRequest request){
		return "/page/app/appconnsetting";
	}
	/**
	 * 插入AppconnSettingEntity
	 * author lqw
	 * 2017年7月21日 
	 * @param req
	 * @return
	 */
	@RequestMapping("insertAppconnSetting")
	@ResponseBody
	public ResultEntity insertAppconnSetting(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result = appconnSettingService.insertAppconnSetting(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
		
	}
	/**
	 * 更新AppconnSettingEntity
	 * author lqw
	 * 2017年7月21日 
	 * @param req
	 * @return
	 */
	@RequestMapping("updateAppconnSetting")
	@ResponseBody
	public ResultEntity updateAppconnSetting(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result = appconnSettingService.updateAppconnSetting(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
		
	}
	/**
	 * 删除AppconnSettingEntity
	 * author lqw
	 * 2017年7月21日 
	 * @param req
	 * @return
	 */
	@RequestMapping("deleteAppconnSetting")
	@ResponseBody
	public ResultEntity deleteAppconnSetting(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result = appconnSettingService.deleteAppconnSetting(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
		
	}
	/**
	 * 查询一条AppconnSettingEntity
	 * author lqw
	 * 2017年7月21日 
	 * @param req
	 * @return
	 */
	@RequestMapping("selectAppconnSetting")
	@ResponseBody
	public ResultEntity selectAppconnSetting(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result = appconnSettingService.selectAppconnSetting(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
		
	}
	
	/**
	 * 根据条件分页查询AppconnSettingEntity
	 * author lqw
	 * 2017年7月21日 
	 * @param appconnSettingEntity
	 * @return
	 */
	@RequestMapping("selectAppconnSettingByPage")
	@ResponseBody
	public ResultEntity selectAppconnSettingByPage(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result = appconnSettingService.selectAppconnSettingByPage(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
		
	}
	@RequestMapping("selectSysName")
	@ResponseBody
	public ResultEntity selectSysName(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			result = appconnSettingService.selectSysName();
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
		
	}
	
	@RequestMapping("selectAppconnDataBySysName")
	@ResponseBody
	public ResultEntity selectAppconnDataBySysName(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result = appconnSettingService.selectAppconnDataBySysName(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
		
	}
}
