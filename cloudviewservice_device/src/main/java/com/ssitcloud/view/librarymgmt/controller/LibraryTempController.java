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
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.SystemLogUtil;
import com.ssitcloud.view.common.util.WebUtil;
import com.ssitcloud.view.librarymgmt.service.LibraryTempService;

/**
 * 图书馆模版Controller
 * @comment 
 * @date 2016年5月20日
 * @author hwl
 */
@Controller
@RequestMapping(value = "libtempconf")
public class LibraryTempController extends BasicController{
	
	@Resource
	LibraryTempService libraryTempService;
	/**
	 * 图书馆管理页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"main"})
	public ModelAndView main(HttpServletRequest request){
		Map<String,Object> model=new HashMap<>();
		return new ModelAndView("/page/librarymgmt/libraryconfig", model);
	}
	
	/**
	 * 查询图书馆模版配置
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年5月20日`
	 * @author hwl
	 */
	@RequestMapping(value = {"Selectlibtplconf"})
	@ResponseBody
	public ResultEntity selectlibtemp(HttpServletRequest request,String json){
		
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("json", request.getParameter("json"));
			String resps = libraryTempService.selectLibraryTemp(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result ;
		
	}
	
	/**
	 * 更新图书馆模版配置
	 * 
	 * 同时需要修改图书馆的服务结束时间。
	 * 
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年5月20日`
	 * @author hwl
	 */
	
	@RequestMapping(value = {"Updatelibtplconf"})
	@ResponseBody
	public ResultEntity Updatelibtemp(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			result = libraryTempService.updateLibraryTemp(json);
			if(result.getState())
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_UPDATE_LIBTEMP);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 添加图书馆模版配置
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年5月20日`
	 * @author hwl
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"Insertlibtplconf"})
	@ResponseBody
	public ResultEntity Insertlibtemp(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map< String, Object > operMap = new HashMap<String,Object >();
			operMap = mapper.readValue(json, Map.class );
			operMap.put("client_ip", WebUtil.getIpAddr(request));
			operMap.put("client_port", request.getRemotePort());
			
			Map<String, String> map = new HashMap<>();
			map.put("json", JsonUtils.toJson(operMap));
			String resps = libraryTempService.addLibraryTemp(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 删除图书馆模版配置
	 * @comment 如果有其他表数据引用为外键，将会导致删除失败
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年5月20日`
	 * @author hwl
	 */
	@RequestMapping(value = {"Deletelibtplconf"})
	@ResponseBody
	public ResultEntity Deletelibtemp(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result = libraryTempService.deleteLibraryTemp(req);
			if(result.getState())
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_DELETE_LIBTEMP);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 查询所有的图书馆服务模版
	 * @comment
	 * @param request
	 * @param json
	 * @return
	 * @data 2016年5月26日`
	 * @author hwl
	 */
	@RequestMapping(value = {"selectAllTemp"})
	@ResponseBody
	public ResultEntity selectAllTemp(HttpServletRequest request,String json){
		ResultEntity result = new ResultEntity();
		try {
			String resps = libraryTempService.selectAllTemp(json);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 根据图书馆ID查询模板信息
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = {"selLibraryServiceTemplateByIdx"})
	@ResponseBody
	public ResultEntity selLibraryServiceTemplateByIdx(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			 result = libraryTempService.selLibraryServiceTemplateByIdx(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
}
