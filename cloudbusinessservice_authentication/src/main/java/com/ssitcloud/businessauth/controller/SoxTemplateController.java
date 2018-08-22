package com.ssitcloud.businessauth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.authentication.entity.SoxTemplateEntity;
import com.ssitcloud.businessauth.service.SoxTemplateService;
import com.ssitcloud.businessauth.utils.LogUtils;
import com.ssitcloud.common.entity.ResultEntity;

/** 
 *  
 * <p>2016年3月24日 下午4:09:30 
 * @author hjc 
 *
 */
@Controller
@RequestMapping("/operator")
public class SoxTemplateController {
	
	@Resource
	private SoxTemplateService soxTemplateService;
	
	/**
	 * 新增模板
	 *
	 * <p>2016年3月31日 下午4:55:11 
	 * <p>create by hjc
	 * @param soxInfo
	 * @param request
	 * @return ResultEntity结果集
	 */
	@RequestMapping("/addSoxTemplate")
	@ResponseBody
	public ResultEntity addSoxTemplate(String soxInfo,HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		ObjectMapper mapper = new ObjectMapper();
		SoxTemplateEntity soxTemplateEntity = new SoxTemplateEntity();
		try {
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
			
		}
		return resultEntity;
	}
	
	
	/**
	 * 根据sox模板ID删除模板信息
	 * 
	 * <p>2016年4月6日 上午11:20:22
	 * <p>create by hjc
	 * @param soxInfo 模板信息 如{sox_tpl_id:"1"}
	 * @param request
	 * @return 
	 */
	@RequestMapping("/delSoxTemplateById")
	@ResponseBody
	public ResultEntity delSoxTemplateById(String soxInfo,HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		ObjectMapper mapper = new ObjectMapper();
		SoxTemplateEntity soxTemplateEntity = new SoxTemplateEntity();
		try {
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
			
		}
		return resultEntity;
	}
	
	
	/**
	 * 查询所有的操作员模板
	 *
	 * <p>2016年6月13日 下午7:30:11 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAllSoxTemp")
	@ResponseBody
	public ResultEntity queryAllSoxTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = soxTemplateService.queryAllSoxTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 根据参数获取鉴权模板分类信息
	 *
	 * <p>2016年6月21日 下午4:46:48 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/getSoxTempListByParam")
	@ResponseBody
	public ResultEntity getSoxTempListByParam(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (!StringUtils.hasText(req)) {
				req = "";
			}
			resultEntity = soxTemplateService.getSoxTempListByParam(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 新增模板数据
	 *
	 * <p>2016年6月22日 下午3:42:14 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/addSoxTemp")
	@ResponseBody
	public ResultEntity addSoxTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (!StringUtils.hasText(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空");
			}
			resultEntity = soxTemplateService.addSoxTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 更新模板数据
	 *
	 * <p>2016年6月22日 下午3:42:10 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateSoxTemp")
	@ResponseBody
	public ResultEntity updateSoxTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (!StringUtils.hasText(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空");
			}
			resultEntity = soxTemplateService.updateSoxTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 删除模板
	 *
	 * <p>2016年6月22日 下午4:59:52 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/delSoxTemp")
	@ResponseBody
	public ResultEntity delSoxTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (!StringUtils.hasText(json) || json.equals("{}")) {
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = soxTemplateService.delSoxTemp(json);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 删除多个模板
	 *
	 * <p>2016年6月22日 下午4:59:55 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/delMultiSoxTemp")
	@ResponseBody
	public ResultEntity delMultiSoxTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (!StringUtils.hasText(json) || json.equals("{}")) {
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = soxTemplateService.delMultiSoxTemp(json);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	
	
	
}
