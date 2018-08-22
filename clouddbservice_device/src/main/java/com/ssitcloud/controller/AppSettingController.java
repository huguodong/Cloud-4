package com.ssitcloud.controller;

import java.util.HashMap;
import java.util.List;
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
import com.ssitcloud.entity.AppSettingEntity;
import com.ssitcloud.entity.page.AppSettingPageEntity;
import com.ssitcloud.service.AppSettingService;

@Controller
@RequestMapping(value={"appSetting"})
public class AppSettingController {
	@Resource
	private AppSettingService appSettingService;
	
	/**
	 * 新增个人菜单设置AppSettingEntity
	 * 格式
	 * json = {
	 * 		"setting_idx":"",//个人菜单设置主键，自增
	 * 		"user_type":"",
	 * 		"lib_idx":"",
	 * 		"service_id":"",
	 * 		"setting_desc":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:14
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"addAppSetting"})
	@ResponseBody
	public ResultEntity addAppSetting (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				AppSettingEntity appSettingEntity = JsonUtils.fromJson(json, AppSettingEntity.class);
				int ret = appSettingService.insertAppSetting(appSettingEntity);
				String typeName = "";
				if(appSettingEntity.getUser_type().equals("1")){
					typeName = "馆员";
				}else if(appSettingEntity.getUser_type().equals("2")){
					typeName = "读者";
				}
				String message = "馆IDX："+appSettingEntity.getLib_idx()+"|app页面idx："+appSettingEntity.getSetting_idx()+"|app页面类型："+typeName+"||";
				if (ret>0) {
					resultEntity.setValue(true, "success","",appSettingEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
				resultEntity.setRetMessage(message);
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
	 * 修改个人菜单设置AppSettingEntity
	 * 格式
	 * json = {
	 * 		"setting_idx":"",//个人菜单设置主键，自增
	 * 		"user_type":"",
	 * 		"lib_idx":"",
	 * 		"service_id":"",
	 * 		"setting_desc":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:33
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"updateAppSetting"})
	@ResponseBody
	public ResultEntity updateAppSetting (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				AppSettingEntity appSettingEntity = JsonUtils.fromJson(json, AppSettingEntity.class);
				int ret = appSettingService.updateAppSetting(appSettingEntity);
				String typeName = "";
				if(appSettingEntity.getUser_type().equals("1")){
					typeName = "馆员";
				}else if(appSettingEntity.getUser_type().equals("2")){
					typeName = "读者";
				}
				String message = "馆IDX："+appSettingEntity.getLib_idx()+"|app页面idx："+appSettingEntity.getSetting_idx()+"|app页面类型："+typeName+"||";
				if (ret>0) {
					resultEntity.setValue(true, "success","",appSettingEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
				resultEntity.setRetMessage(message);
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
	 * 删除个人菜单设置AppSettingEntity
	 * 格式
	 * json = {
	 * 		"setting_idx":"",//个人菜单设置主键，自增
	 * 		"user_type":"",
	 * 		"lib_idx":"",
	 * 		"service_id":"",
	 * 		"setting_desc":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:32
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"deleteAppSetting"})
	@ResponseBody
	public ResultEntity deleteAppSetting (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				AppSettingEntity appSettingEntity = JsonUtils.fromJson(json, AppSettingEntity.class);
				int ret = appSettingService.deleteAppSetting(appSettingEntity);
				String typeName = "";
				if(appSettingEntity.getUser_type().equals("1")){
					typeName = "馆员";
				}else if(appSettingEntity.getUser_type().equals("2")){
					typeName = "读者";
				}
				String message = "馆IDX："+appSettingEntity.getLib_idx()+"|app页面idx："+appSettingEntity.getSetting_idx()+"|app页面类型："+typeName+"||";
				if (ret>0) {
					resultEntity.setValue(true, "success","",appSettingEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
				resultEntity.setRetMessage(message);
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
	 * 删除个人菜单设置AppSettingEntity 根据馆idx
	 * 格式
	 * json = {
	 * 		"setting_idx":"",//个人菜单设置主键，自增
	 * 		"user_type":"",
	 * 		"lib_idx":"",
	 * 		"service_id":"",
	 * 		"setting_desc":""
	 * }
	 * author lqw
	 * 2017年3月20日 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"deleteAppSettingBylibidx"})
	@ResponseBody
	public ResultEntity deleteAppSettingBylibidx(HttpServletRequest request){
		
		ResultEntity resultEntity = new ResultEntity();
		int ret = 0;
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				JSONArray jarr = JSONArray.fromObject(json);
				//String idxs="";
				String libidxs="";
				String types="";
				for (int i = 0,z=jarr.size(); i < z; i++) {
					AppSettingEntity appSettingEntity = new AppSettingEntity();
					appSettingEntity.setLib_idx(Integer.parseInt(jarr.getJSONObject(i).getString("lib_idx")));
					appSettingEntity.setUser_type(jarr.getJSONObject(i).getString("user_type"));
					ret = appSettingService.deleteAppSettingBylibidx(appSettingEntity);
					String typeName = "";
					if(appSettingEntity.getUser_type().equals("1")){
						typeName = "馆员";
					}else if(appSettingEntity.getUser_type().equals("2")){
						typeName = "读者";
					}
					//idxs +=appSettingEntity.getSetting_idx()+",";
					libidxs +=appSettingEntity.getLib_idx()+",";
					types +=typeName+",";
					if (ret > 0) {
						resultEntity.setValue(true, "success", "",appSettingEntity);
					} else {
						resultEntity.setValue(false, "failed");
					}
				}
//				if(idxs.length() >0){
//					idxs = idxs.substring(0, idxs.length()-1);
//				}
				if(libidxs.length() >0){
					libidxs = libidxs.substring(0, libidxs.length()-1);
				}
				if(types.length() >0){
					types = types.substring(0, types.length()-1);
				}
				String message = "馆IDX："+libidxs+"|app页面类型："+types+"||";
				resultEntity.setRetMessage(message);
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
	 * 查询一条个人菜单设置记录AppSettingEntity
	 * 格式
	 * json = {
	 * 		"setting_idx":"",//个人菜单设置主键，自增
	 * 		"user_type":"",
	 * 		"lib_idx":"",
	 * 		"service_id":"",
	 * 		"setting_desc":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectAppSetting"})
	@ResponseBody
	public ResultEntity selectAppSetting (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				AppSettingEntity appSettingEntity = JsonUtils.fromJson(json, AppSettingEntity.class);
				appSettingEntity = appSettingService.queryOneAppSetting(appSettingEntity);
				resultEntity.setValue(true, "success","",appSettingEntity);
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
	 * 查询多条个人菜单设置记录AppSettingPageEntity
	 * 格式
	 * json = {
	 * 		"setting_idx":"",//个人菜单设置主键，自增
	 * 		"user_type":"",
	 * 		"lib_idx":"",
	 * 		"service_id":"",
	 * 		"setting_desc":""
	 * 		pageCurrent:第几页，从开始
	 *		pageSize:每页记录
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectAppSettings"})
	@ResponseBody
	public ResultEntity selectAppSettings (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			AppSettingPageEntity appSettingEntity = JsonUtils.fromJson(json, AppSettingPageEntity.class);
			List<AppSettingEntity> list = appSettingService.queryAppSettings(appSettingEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询个人菜单设置记录AppSettingPage2Entity 
	 * 格式
	 * json = {
	 * 		"setting_idx":"",//个人菜单设置主键，自增
	 * 		"user_type":"",
	 * 		"lib_idx":"",
	 * 		"service_id":"",
	 * 		"setting_desc":""
	 * 		page:第几页，从开始
	 *		pageSize:每页记录
	 * }
	 * author lqw
	 * 2017年3月20日 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectAppSettingByPage"})
	@ResponseBody
	public ResultEntity selectAppSettingByPage (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			resultEntity = appSettingService.selectAppSettingByPage(json);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 查出符合条件的菜单名
	 * json={
	 * 	"lib_idx":"",
	 *  "user_type":"",
	 *  "service_ids":["020301","020302","020303"]
	 * 
	 * }
	 * add by shuangjunjie
	 * 2017年3月27日 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectMenuNamesByServiceIds"})
	@ResponseBody
	public ResultEntity selectMenuNamesByServiceIds (HttpServletRequest request, String req){
		ResultEntity resultEntity = new ResultEntity();
		try {
			req = request.getParameter("json");
			if(StringUtils.isNotBlank(req)){
				Map param = JsonUtils.fromJson(req, Map.class);
				Map<String,Object> map = new HashMap<>();
				
				List<Map> list = appSettingService.selectMenuNamesByServiceIds(param);
				if(list.size()>0 && list != null){
					map.put("menu", list);
					resultEntity.setValue(true, "", "", map);
				}else{
					resultEntity.setValue(false, "没有找到对应菜单", "", null);
				}
			}else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
}
