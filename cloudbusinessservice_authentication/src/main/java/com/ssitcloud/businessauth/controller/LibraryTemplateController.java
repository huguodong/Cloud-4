package com.ssitcloud.businessauth.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.businessauth.service.LibraryTemplateService;
import com.ssitcloud.businessauth.utils.JsonUtils;
import com.ssitcloud.businessauth.utils.LogUtils;
import com.ssitcloud.common.entity.ResultEntity;


/**
 * 图书馆模板处理类
 * <p>2016年4月5日 上午11:18:30
 * @author hjc
 *
 */
@Controller
@RequestMapping("/library")
public class LibraryTemplateController {
	
	@Resource
	LibraryTemplateService libraryTemplateService;
	/**
	 * 新增图书馆模板
	 * 
	 * <p>2016年4月5日 下午2:27:57
	 * <p>create by hjc
	 * @param libTempInfo 图书馆模板信息，json格式，
	 * 			如{lib_service_tpl_desc:"描述",service_expire_date:2,
	 * 				max_device_count:1,max_operator_cout:1,max_sublib_count:2}
	 * @param request
	 * @return
	 */
	@RequestMapping("/addLibraryTemplate")
	@ResponseBody
	public ResultEntity addLibraryTemplate(String json,HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			String resps = libraryTemplateService.addlibtemp(json);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}

	/**
	 * 根据图书馆模板ID删除模板信息
	 * 
	 * <p>2016年4月6日 下午2:04:49
	 * <p>create by hjc
	 * @param libTempInfo 模板信息如{lib_service_tpl_id:"1"}
	 * @param request
	 * @return ResultEntity结果类
	 */
	@RequestMapping("/delLibraryTemplateById")
	@ResponseBody
	public ResultEntity delLibraryTemplateById(String req,HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			result = libraryTemplateService.deletelibtemp(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	@RequestMapping("/updateLibraryTemplate")
	@ResponseBody
	public ResultEntity updateLibraryTemplate(String req,HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			result = libraryTemplateService.updatelibtemp(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	@RequestMapping("/selectLibraryTemplate")
	@ResponseBody
	public ResultEntity selectLibraryTemplate(String json,HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("json", request.getParameter("json"));
			String resps = libraryTemplateService.selectlibtemp(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	@RequestMapping("/selectAllLibraryTemp")
	@ResponseBody
	public ResultEntity selectAllLibraryTemp(String json,HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("json", request.getParameter("json"));
			String resps = libraryTemplateService.selectAllLibtemp(map);
			result = JsonUtils.fromJson(resps, ResultEntity.class);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
	
	/**
	 * 查询图书馆的模板信息，以及所有 有效的设备用户的id
	 *
	 * <p>2016年6月2日 下午4:39:54 
	 * <p>create by hjc
	 * @param json
	 * @param request
	 * @return
	 */
	@RequestMapping("/selLibraryTempAndDeviceIds")
	@ResponseBody
	public ResultEntity selLibraryRegisterDevice(String json,HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			if(json!=null && !json.equals("")){
				String response = libraryTemplateService.selDeviceUserByLibraryIdx(json);
				if (response!=null) {
					result = JsonUtils.fromJson(response, ResultEntity.class);
				}else {
					result.setValue(false, "连接失败");
				}
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	/**
	 * 根据主键取得单个模板的信息
	 * req={
	 * 	"lib_service_tpl_id":"xxx"
	 * }
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping("/selLibraryServiceTemplateByIdx")
	@ResponseBody
	public ResultEntity selLibraryServiceTemplateByIdx(String req,HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			result=libraryTemplateService.selLibraryServiceTemplateByIdx(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			result.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return result;
	}
	
}
