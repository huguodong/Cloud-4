package com.ssitcloud.view.librarymgmt.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.SystemLogUtil;
import com.ssitcloud.view.common.util.WebUtil;
import com.ssitcloud.view.librarymgmt.service.LibraryService;

/**
 * 图书馆管理Controller
 * @comment 
 * @date 2016年5月24日
 * @author hwl
 */
@Controller
@RequestMapping("/library")
public class LibraryController extends BasicController{
	
	@Resource LibraryService libraryService;
	/**
	 * 
	 * 图书馆管理主页面
	 * @author lbh
	 * @param request
	 * @return
	 */
	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest request){
		Map<String,Object> model=new HashMap<>();
		Operator oper=getCurrentUser();
		model.put("operator", oper);
		return new ModelAndView("/page/librarymgmt/librarymgmt",model);
	}
	
	/**虚拟馆入口**/
	@RequestMapping("/vmain")
	public ModelAndView vmain(HttpServletRequest request){
		Map<String,Object> model=new HashMap<>();
		Operator oper=getCurrentUser();
		model.put("operator", oper);
		return new ModelAndView("/page/librarymgmt/vlibrarymgmt",model);
	}
	
	/**
	 * 分页查询library数据
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年5月24日`
	 * @author hwl
	 */
	@RequestMapping("/selectLibraryInfo")
	@ResponseBody
	public ResultEntity selectLibraryInfo(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("json", request.getParameter("json"));
			String resps = libraryService.selectLibrary(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 查询所有主馆
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年5月28日`
	 * @author hwl
	 */
	@RequestMapping("/selectMasterLib")
	@ResponseBody
	public ResultEntity getMasterLib(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("json", request.getParameter("json"));
			String resps = libraryService.selectMasterlib(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 查询所有主从关系的数据
	 *
	 * <p>2017年11月21日 下午2:00:42 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryMasterSubRelations")
	@ResponseBody
	public ResultEntity queryMasterSubRelations(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("req", "{\"rel_type\":\"1\"}");
			String resps = libraryService.queryMasterSubRelations(map);
			resultEntity = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 根据当前登陆用户获取相应的图书馆信息。和分馆信息
	 * 如果是系统管理员 ，则 可以获取全部图书馆信息。
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2017年8月1日`
	 * @author 
	 */
	@RequestMapping("/querylibInfoByCurUserEXT1")
	@ResponseBody
	public ResultEntity querylibInfoByCurUserEXT1(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			String operator_type = getCurrentUser().getOperator_type();
			
			String params = "{\"operator_type\":\""+operator_type+"\"}";
			Map<String, String> map = new HashMap<>();
			map.put("req", params);
			String resps = libraryService.querylibInfoByCurUserEXT1(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	/**
	 * 添加library数据
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年5月24日`
	 * @author hwl
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/addLibraryInfo")
	@ResponseBody
	public ResultEntity addLibraryInfo(HttpServletRequest request,String json,String jsonPos){
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
			String resps = libraryService.addLibrary(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			
			Boolean flag = result.getState();
			if(flag==true){
				libraryService.saveLibPosition(jsonPos);
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 删除library数据
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年5月24日`
	 * @author hwl
	 */
	@RequestMapping("/deleteLibraryInfo")
	@ResponseBody
	public ResultEntity deleteLibraryInfo(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		if(!checkPermession(Const.PERMESSION_DEL_LIB_BASEINFO)){
			//没有权限
			result.setRetMessage("没有权限操作！");
			return result;
		}
		try {
			result = libraryService.deleteLibrary(json);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.PERMESSION_DEL_LIB_BASEINFO);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 更新library数据
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年5月24日`
	 * @author hwl
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateLibraryInfo")
	@ResponseBody
	public ResultEntity updateLibraryInfo(HttpServletRequest request,String json,String jsonPos){
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
			String resps = libraryService.updateLibrary(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			Boolean flag = result.getState();
			if(flag==true){
				libraryService.saveLibPosition(jsonPos);
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 查询图书馆信息元数据
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年5月26日`
	 * @author hwl
	 */
	@RequestMapping("/selectLibInfoType")
	@ResponseBody
	public ResultEntity selectMetaInfoType(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("infotype", request.getParameter("json"));
			String resps = libraryService.selectLibInfoType(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 条件查询图书馆及相关信息
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年5月28日`
	 * @author hwl
	 */
	@RequestMapping("/selectLibinfoByParam")
	@ResponseBody
	public ResultEntity GetLibinfoByParam(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("json", request.getParameter("json"));
			String resps = libraryService.selectLibInfoByParam(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping("/selLibraryByIdxOrId")
	@ResponseBody
	public ResultEntity selLibraryByIdxOrId(HttpServletRequest request,String json){
		
		Map<String, String> map = new HashMap<>();
		map.put("json", request.getParameter("json"));
		String result = libraryService.selLibraryByIdxOrId(map);
		return JsonUtils.fromJson(result, ResultEntity.class);
	}
	
	
	@RequestMapping("/libConfigPage")
	public ModelAndView libConfigPage(HttpServletRequest request) {
		// 获取配置模块数据
		Map<String, Object> model = new HashMap<>();
		Operator oper = getCurrentUser();
		model.put("operator", oper);
		return new ModelAndView("/page/librarymgmt/libInterfaceconfig", model);
	}
	
	@RequestMapping("/selActualLibraryMaster")
	@ResponseBody
	public ResultEntity selActualLibraryMaster(HttpServletRequest request,String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity = libraryService.selActualLibraryMaster(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
}
