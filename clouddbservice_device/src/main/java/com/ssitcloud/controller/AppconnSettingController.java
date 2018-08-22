package com.ssitcloud.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.AppconnSettingEntity;
import com.ssitcloud.service.AppconnSettingService;

@Controller
@RequestMapping(value={"appconnSetting"})
public class AppconnSettingController {
	@Resource
	AppconnSettingService appconnSettingService;
	
	/**
	 * 新增appconnSettingEntity
	 * 格式
	 * json = {
	 * 		"conn_id":"",
	 * 		"lib_idx":"",
	 * 		"conn_type":"",
	 * 		"conn_url":"",
	 * 		"conn_port":"",
	 *      "conn_user":"",
	 * 		"conn_pwd":""
	 * }
	 * author lqw
	 * 2017年7月21日
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"addAppconnSetting"})
	@ResponseBody
	public ResultEntity addAppconnSetting (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				AppconnSettingEntity appconnSettingEntity = JsonUtils.fromJson(json, AppconnSettingEntity.class);
				int ret = appconnSettingService.insertAppconnSetting(appconnSettingEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",appconnSettingEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 更新appconnSettingEntity
	 * 格式
	 * json = {
	 * 		"conn_id":"",
	 * 		"lib_idx":"",
	 * 		"conn_type":"",
	 * 		"conn_url":"",
	 * 		"conn_port":"",
	 *      "conn_user":"",
	 * 		"conn_pwd":""
	 * }
	 * author lqw
	 * 2017年7月21日
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"updateAppconnSetting"})
	@ResponseBody
	public ResultEntity updateAppconnSetting (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				AppconnSettingEntity appconnSettingEntity = JsonUtils.fromJson(json, AppconnSettingEntity.class);
				int ret = appconnSettingService.updateAppconnSetting(appconnSettingEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",appconnSettingEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 删除appconnSettingEntity
	 * 格式
	 * json = {
	 * 		"conn_id":"",
	 * 		"lib_idx":"",
	 * 		"conn_type":"",
	 * 		"conn_url":"",
	 * 		"conn_port":"",
	 *      "conn_user":"",
	 * 		"conn_pwd":""
	 * }
	 * author lqw
	 * 2017年7月21日
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"deleteAppconnSetting"})
	@ResponseBody
	public ResultEntity deleteAppconnSetting (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		int ret = 0;
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				JSONArray jarr = JSONArray.fromObject(json);
				
				for (int i = 0,z=jarr.size(); i < z; i++) {
					AppconnSettingEntity appconnSettingEntity = new AppconnSettingEntity();
					appconnSettingEntity.setLib_idx(Integer.parseInt(jarr.getJSONObject(i).getString("lib_idx")));
					ret = appconnSettingService.deleteAppconnSetting(appconnSettingEntity);
					if (ret > 0) {
						resultEntity.setValue(true, "success", "",appconnSettingEntity);
					} else {
						resultEntity.setValue(false, "failed");
					}
				}
				resultEntity.setMessage("ONE");
			} else {
				resultEntity.setRetMessage("删除失败!");
			}
		} catch (Exception e) {
			resultEntity.setResult(ret);
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	
	/**
	 * 根据馆ID查询appconnSettingEntity
	 * 格式
	 * json = {
	 * 		"conn_id":"",
	 * 		"lib_idx":"",
	 * 		"conn_type":"",
	 * 		"conn_url":"",
	 * 		"conn_port":"",
	 *      "conn_user":"",
	 * 		"conn_pwd":""
	 * }
	 * author lqw
	 * 2017年7月21日
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectAppconnSetting"})
	@ResponseBody
	public ResultEntity selectAppconnSetting (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				AppconnSettingEntity appconnSettingEntity = JsonUtils.fromJson(json, AppconnSettingEntity.class);
				resultEntity = appconnSettingService.selectAppconnSetting(appconnSettingEntity);
			}else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	
	/**
	 * 分页查询appconnSettingEntity
	 * 格式
	 * json = {
	 * 		"conn_id":"",
	 * 		"lib_idx":"",
	 * 		"conn_type":"",
	 * 		"conn_url":"",
	 * 		"conn_port":"",
	 *      "conn_user":"",
	 * 		"conn_pwd":""
	 * }
	 * author lqw
	 * 2017年7月21日
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectAppconnSettingByPage"})
	@ResponseBody
	public ResultEntity selectAppconnSettingByPage (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			resultEntity = appconnSettingService.selectAppconnSettingByPage(json);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping("/lib_settings")
	@ResponseBody
	public ResultEntity lib_settings(String json){
		ResultEntity resultEntity = new ResultEntity();
		
		try{
			if(json == null){
				throw new IllegalArgumentException("args is null");
			}
			Map<String, Object> map = JsonUtils.fromJson(json, Map.class);
			Integer idx = (Integer) map.get("lib_idx");
			if(idx == null){
				throw new IllegalArgumentException("lib_idx is null");
			}
			
			Map<String, Object> data = appconnSettingService.selectRealValue(idx);
			resultEntity.setResult(data);
			resultEntity.setState(true);
			return resultEntity;
		}catch (Exception e) {
			resultEntity.setState(false);
			resultEntity.setMessage(e.getMessage());
			return resultEntity;
		}
	}
	
	@RequestMapping(value={"selectSysName"})
	@ResponseBody
	public ResultEntity selectSysName (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity = appconnSettingService.selectSysName();
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	@RequestMapping(value={"selectAppconnDataBySysName"})
	@ResponseBody
	public ResultEntity selectAppconnDataBySysName(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			resultEntity = appconnSettingService.selectAppconnDataBySysName(json);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
}
