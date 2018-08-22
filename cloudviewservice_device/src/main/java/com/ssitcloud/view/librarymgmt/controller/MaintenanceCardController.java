package com.ssitcloud.view.librarymgmt.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.WebUtil;
import com.ssitcloud.view.librarymgmt.service.MaintenanceCardService;
/**
 * 维护卡业务
 *
 * <p>2017年3月30日 下午4:24:52  
 * @author hjc 
 *
 */
@Controller
@RequestMapping(value = "maintenance")
public class MaintenanceCardController {
	
	@Resource
	private MaintenanceCardService maintenanceCardService;
	
	/**
	 * 新增维护卡信息
	 *
	 * <p>2017年3月30日 下午4:27:26 
	 * <p>create by hjc
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insertMaintenanceCard")
	@ResponseBody
	public ResultEntity insertMaintenanceCard(HttpServletRequest request, String json){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if(StringUtils.isBlank(json)){
				resultEntity.setValue(false, "参数不能为空", "", "");
				return resultEntity;
			}
			ObjectMapper mapper = new ObjectMapper();
			Map< String, Object > operMap = new HashMap<String,Object >();
			operMap = mapper.readValue(json, Map.class );
			operMap.put("client_ip", WebUtil.getIpAddr(request));
			operMap.put("client_port", request.getRemotePort());
			
			Map<String, String> map = new HashMap<>();
			map.put("json", JsonUtils.toJson(operMap));
			String resps = maintenanceCardService.insertMaintenanceCard(map);
			resultEntity = JsonUtils.fromJson(resps, ResultEntity.class);
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 新增维护卡信息
	 *
	 * <p>2017年3月30日 下午4:27:26 
	 * <p>create by hjc
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateMaintenanceCard")
	@ResponseBody
	public ResultEntity updateMaintenanceCard(HttpServletRequest request, String json){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if(StringUtils.isBlank(json)){
				resultEntity.setValue(false, "参数不能为空", "", "");
				return resultEntity;
			}
			ObjectMapper mapper = new ObjectMapper();
			Map< String, Object > operMap = new HashMap<String,Object >();
			operMap = mapper.readValue(json, Map.class );
			operMap.put("client_ip", WebUtil.getIpAddr(request));
			operMap.put("client_port", request.getRemotePort());
			
			Map<String, String> map = new HashMap<>();
			map.put("json", JsonUtils.toJson(operMap));
			String resps = maintenanceCardService.updateMaintenanceCard(map);
			resultEntity = JsonUtils.fromJson(resps, ResultEntity.class);
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	/**
	 * 
	 *
	 * <p>2017年3月30日 下午4:27:26 
	 * <p>create by hjc
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/deleteMaintenanceCard")
	@ResponseBody
	public ResultEntity deleteMaintenanceCard(HttpServletRequest request, String json){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if(StringUtils.isBlank(json)){
				resultEntity.setValue(false, "参数不能为空", "", "");
				return resultEntity;
			}
			ObjectMapper mapper = new ObjectMapper();
			Map< String, Object > operMap = new HashMap<String,Object >();
			operMap = mapper.readValue(json, Map.class );
			operMap.put("client_ip", WebUtil.getIpAddr(request));
			operMap.put("client_port", request.getRemotePort());
			
			Map<String, String> map = new HashMap<>();
			map.put("json", JsonUtils.toJson(operMap));
			String resps = maintenanceCardService.deleteMaintenanceCard(map);
			resultEntity = JsonUtils.fromJson(resps, ResultEntity.class);
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 分页模糊查询维护卡信息
	 *
	 * <p>2017年3月30日 下午8:30:15 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @param page
	 * @return
	 */
	@RequestMapping(value = {"queryMaintenanceCardByFuzzy"})
	@ResponseBody
	public ResultEntity queryMaintenanceCardByFuzzy(HttpServletRequest request,String json,String page){
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("json", request.getParameter("json"));
			map.put("page", request.getParameter("page"));
			String resps = maintenanceCardService.queryMaintenanceCardByFuzzy(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}

}
