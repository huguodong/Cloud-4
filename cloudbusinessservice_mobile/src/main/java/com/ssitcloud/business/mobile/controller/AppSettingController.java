package com.ssitcloud.business.mobile.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.service.AppSettingServiceI;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.AppSettingEntity;
import com.ssitcloud.mobile.entity.AppSettingPageEntity;

/**
 * 负责服务层的app_setting的增删查改，没有验证用户权限
 * @author lxp
 *
 */
@Controller
@RequestMapping("/appSetting")
public class AppSettingController {
	@Autowired
	private AppSettingServiceI appsettingService;
	
	/**
	 * 模糊查询AppSettingPageEntity
	 * @param request，需要提供查询参数“json={AppSettingPageEntity查询实体}”
	 * @return ResultEntity,state中表示是否成功，result中存放模糊查询列表（List<AppSettingEntity>）
	 */
	@RequestMapping("/queryAppSettings")
	@ResponseBody
	public ResultEntity queryAppSettings(HttpServletRequest request){
		ResultEntity resultEnity = new ResultEntity();
		String json = request.getParameter("json");

		try{
			AppSettingPageEntity checkAse = JsonUtils.fromJson(json, AppSettingPageEntity.class);
			if(checkAse == null){
				checkAse = new AppSettingPageEntity();
			}
//			if(checkAse.getLib_idx() == null){
//				resultEnity.setState(false);
//				resultEnity.setMessage("没有lib_idx参数");
//				return resultEnity;
//			}
//			if(checkAse.getUser_type() == null){
//				resultEnity.setState(false);
//				resultEnity.setMessage("没有user_type参数");
//				return resultEnity;
//			}
			//检查页面分页配置是否非法
			boolean change = false;
			if(checkAse.getPageCurrent() == null || checkAse.getPageCurrent()<1){
				checkAse.setPageCurrent(1);
				change = true;
			}
			if(checkAse.getPageSize() == null || checkAse.getPageSize()<1){
				checkAse.setPageSize(Integer.MAX_VALUE);
				change = true;
			}
			json = change?JsonUtils.toJson(checkAse):json;
		}catch(Exception e){
			resultEnity.setState(false);
			resultEnity.setMessage("提交参数解析失败，请检查提交参数");
		}
		try{
			List<AppSettingEntity> resultList = appsettingService.queryAppSettingS(json);
			resultEnity.setState(resultList!=null);
			resultEnity.setResult(resultList);
		}catch(Exception e){
			resultEnity.setState(false);
			resultEnity.setMessage(e.getMessage());
		}
		return resultEnity;
	}
	
	
	/**
	 * 根据多个service_id查出appSetting及对应菜单
	 * json = {
	 * 		"lib_idx":"",
	 * 		"user_type":"",
	 * 		"service_ids":["020301","020302","020303"],
	 * 		"operator_idx":"273"
	 * }
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectAppSettingAndMenuNames")
	@ResponseBody
	public ResultEntity selectMenuNamesByServiceIds(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		String json = request.getParameter("json");
		String operatorIdx = null;
		try {
			if(StringUtils.isBlank(json) || json.equals("{}")){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			Map jMap = JsonUtils.fromJson(json, Map.class);
			
			if(jMap.containsKey("operator_idx")){
				operatorIdx = jMap.get("operator_idx").toString();
				jMap.remove("operator_idx");
			}
            ResultEntity temp;
			//获取菜单
            temp = appsettingService.selectMenuNamesByServiceIds(JsonUtils.toJson(jMap));
			List<Map<String,Object>> menus;
			if(!temp.getState() || temp.getResult() == null){
                menus = new ArrayList<>(0);
			}else{
			    menus = (List<Map<String, Object>>) ((Map<String, Object>) temp.getResult()).get("menu");
            }
            //获取权限
            temp = appsettingService.checkPermission("{\"operator_idx\":\""+operatorIdx+"\"}");
            Map<String,Object> permMap;
            if(!temp.getState() || temp.getResult() == null){
                permMap = new HashMap<>();
            }else{
                permMap = (Map<String, Object>) temp.getResult();
            }

            permMap.put("menu",menus);
			resultEntity.setValue(true, "", "", permMap);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			e.printStackTrace();
		}
		
		return resultEntity;
	}
}
