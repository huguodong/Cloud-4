package com.ssitcloud.view.librarymgmt.controller;

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
import com.ssitcloud.view.librarymgmt.service.ReaderTypeService;
/**
 * 
 * @comment
 * @data 2016年5月17日
 * @author hwl
 */
@Controller
@RequestMapping(value = "ReaderType")
public class ReaderTypeController {

	@Resource
	ReaderTypeService readerTypeService;
	
	
	@RequestMapping(value={"SystemCard"})
	public String SystemCard(HttpServletRequest request){
		return "/page/librarymgmt/System-card";
	}
	@RequestMapping(value={"ReaderCard"})
	public String ReaderCard(HttpServletRequest request){
		return "/page/librarymgmt/system-readertype";
	}
	
	/**
	 * @comment 分页查询读者流通类型数据
	 * @param request
	 * @param json
	 * @param page
	 * @return
	 * @data 2016年5月17日`
	 * @author hwl
	 */
	@RequestMapping(value = {"SelectReaderType"})
	@ResponseBody
	public ResultEntity SelectReaderType(HttpServletRequest request,String json,String page){
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("json", request.getParameter("json"));
			map.put("page", request.getParameter("page"));
			String resps = readerTypeService.Selectreadertype(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	
	/**
	 * 根据图书馆id和读者类型模糊查询
	 * @comment
	 * @param request
	 * @param json
	 * @param page
	 * @return
	 * @data 2016年5月19日`
	 * @author hwl
	 */
	@RequestMapping(value = {"QueryReaderTypeByFuzzy"})
	@ResponseBody
	public ResultEntity QueryReaderTypeByFuzzy(HttpServletRequest request,String json,String page){
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("json", request.getParameter("json"));
			map.put("page", request.getParameter("page"));
			String resps = readerTypeService.SelectreadertypeByFuzzy(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
	}
	
	/**
	 * 
	 * @comment 修改读者流通类型
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年5月17日`
	 * @author hwl
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"UpdateReaderType"})
	@ResponseBody
	public ResultEntity UpdateReaderType(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map< String, Object > operMap = new HashMap<String,Object >();
			operMap = mapper.readValue(json, Map.class );
			operMap.put("client_ip", WebUtil.getIpAddr(request));
			operMap.put("client_port", request.getRemotePort());
			
			Map<String, String> map = new HashMap<>();
			map.put("json", JsonUtils.toJson(operMap));
			String resps = readerTypeService.Updatereadertype(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 
	 * @comment 添加读者流通类型
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年5月17日`
	 * @author hwl
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"InsertReaderType"})
	@ResponseBody
	public ResultEntity InsertReaderType(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map< String, Object > operMap = new HashMap<String,Object >();
			operMap = mapper.readValue(json, Map.class );
			operMap.put("client_ip", WebUtil.getIpAddr(request));
			operMap.put("client_port", request.getRemotePort());
			
			Map<String, String> map = new HashMap<>();
			map.put("json", JsonUtils.toJson(operMap));
			String resps = readerTypeService.Insertreadertype(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 
	 * @comment 删除读者流通类型
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年5月17日`
	 * @author hwl
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"DeleteReaderType"})
	@ResponseBody
	public ResultEntity DeleteReaderType(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map< String, Object > operMap = new HashMap<String,Object >();
			operMap = mapper.readValue(json, Map.class );
			operMap.put("client_ip", WebUtil.getIpAddr(request));
			operMap.put("client_port", request.getRemotePort());
			Map<String, String> map = new HashMap<>();
			map.put("json", JsonUtils.toJson(operMap));
			String resps = readerTypeService.Deletereadertype(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
