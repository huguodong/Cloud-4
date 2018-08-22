package com.ssitcloud.view.devmgmt.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.WebUtil;
import com.ssitcloud.view.devmgmt.service.DBSyncService;

/**
 * 数据同步Controller
 * @comment 
 * @date 2016年6月1日
 * @author hwl
 */
@RequestMapping("/dbsync")
@Controller
public class DBSyncConfigController {

	@Resource
	DBSyncService dbSyncService;
	
	@RequestMapping("/main")
	public String main(HttpServletRequest request){
		return "/page/devmgmt/databasesync_config";
	}
	
	/**
	 * 查询数据库同步模版配置
	 * @comment
	 * @param json
	 * @param request
	 * @return
	 * @data 2016年6月1日
	 * @author hwl
	 */
	@RequestMapping("/SelectDBsynctemp")
	@ResponseBody
	public ResultEntity SelectDBsynctemp(String json , HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("json", request.getParameter("json"));
			String resps = dbSyncService.selDBsyncConfig(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
		
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/AddNewDBsynctemp")
	@ResponseBody
	public ResultEntity AddNewDBsynctemp(String json , HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			//获取客户端ip和端口
			ObjectMapper mapper = new ObjectMapper();
			Map< String, Object > operMap = new HashMap<String,Object >();
			operMap = mapper.readValue(json, Map. class );
			operMap.put("client_ip", WebUtil.getIpAddr(request));
			operMap.put("client_port", request.getRemotePort());
			
			Map<String, String> map = new HashMap<>();
			map.put("json", JsonUtils.toJson(operMap));
			String resps = dbSyncService.addDBsyncConfig(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
		
	}
	/**
	 * 更新数据库同步配置
	 * @param json
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/UpdDBsynctemp")
	@ResponseBody
	public ResultEntity UpdDBsynctemp(String json , HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			//获取客户端ip和端口
			ObjectMapper mapper = new ObjectMapper();
			Map< String, Object > operMap = new HashMap<String,Object >();
			operMap = mapper.readValue(json, Map. class );
			operMap.put("client_ip", WebUtil.getIpAddr(request));
			operMap.put("client_port", request.getRemotePort());
			
			Map<String, String> map = new HashMap<>();
			map.put("json", JsonUtils.toJson(operMap));
			String resps = dbSyncService.updDBsyncConfig(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
		
	}
	/**
	 * 删除数据库同步配置
	 * @param json
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/DelDBsynctemp")
	@ResponseBody
	public ResultEntity DelDBsynctemp(String json , HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			//获取客户端ip和端口
			ObjectMapper mapper = new ObjectMapper();
			Map< String, Object > operMap = new HashMap<String,Object >();
			operMap = mapper.readValue(json, Map. class );
			operMap.put("client_ip", WebUtil.getIpAddr(request));
			operMap.put("client_port", request.getRemotePort());
			
			Map<String, String> map = new HashMap<>();
			map.put("json", JsonUtils.toJson(operMap));
			String resps = dbSyncService.delDBsyncConfig(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
