package com.ssitcloud.dbauth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.entity.SoxTemplateEntity;
import com.ssitcloud.dbauth.entity.page.SoxTempPageEntity;
import com.ssitcloud.dbauth.service.SoxTemplateService;
import com.ssitcloud.dbauth.utils.JsonUtils;
import com.ssitcloud.dbauth.utils.LogUtils;

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
	private SoxTemplateService soxService;
	
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
			if (!StringUtils.isBlank(soxInfo)) {
				soxTemplateEntity = mapper.readValue(soxInfo, SoxTemplateEntity.class);
				soxService.addSoxTemplateEntity(soxTemplateEntity);
				resultEntity.setValue(true,"success","",soxTemplateEntity);
				resultEntity.setRetMessage("密码模板IDX"+soxTemplateEntity.getSox_tpl_id()+"｜密码模板名:"+soxTemplateEntity.getSox_tpl_name());//馆IDX｜密码模板IDX｜密码模板名
			}else{
				resultEntity.setValue(false, "failed");
			}
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
			if (!StringUtils.isBlank(soxInfo)) {
				soxTemplateEntity = mapper.readValue(soxInfo, SoxTemplateEntity.class);
				int ret = soxService.delSoxTemplateById(soxTemplateEntity);
				if (ret >= 1) {
					resultEntity.setValue(true, "success");
				}else {
					resultEntity.setValue(false, "failed");
				}
			}else{
				resultEntity.setValue(false, "failed");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
			
		}
		return resultEntity;
	}
	
	
	@RequestMapping("/queryAllSoxTemp")
	@ResponseBody
	public ResultEntity queryAllSoxTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<SoxTemplateEntity> list = soxService.queryAllSoxTemp();
			resultEntity.setValue(true, "", "", list);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	//start author by lxp
	/**
	 * 根据模板主键查询模板信息
	 * @param sox_tpl_id 模板主键
	 * @return
	 */
	@RequestMapping("/querySoxTempById")
	@ResponseBody
	public ResultEntity querySoxTempById(Integer sox_tpl_id){
		ResultEntity resultEntity = new ResultEntity();
		if(sox_tpl_id == null){
			resultEntity.setValue(false, "没有设置sox_tpl_id参数");
			return resultEntity;
		}
		try{
			SoxTemplateEntity templ = soxService.getSoxTemplateEntityById(sox_tpl_id);
			resultEntity.setState(true);
			resultEntity.setResult(templ);
		}catch(Exception e){
			resultEntity.setValue(false, "查找模板出错，请查看日志");
			LogUtils.error("查找模板出错，请查看日志", e);
		}
		return resultEntity;
	}
	//end author by lxp
	/**
	 * 根据参数获取鉴权模板分类信息
	 *
	 * <p>2016年6月21日 下午5:48:22 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getSoxTempListByParam")
	@ResponseBody
	public ResultEntity getSoxTempListByParam(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		Map<String, Object> param = new HashMap<String, Object>();
		SoxTempPageEntity soxTempPageEntity = new SoxTempPageEntity();
		int page = soxTempPageEntity.getPage();
		int pageSize = soxTempPageEntity.getPageSize();
		String queryType = "";
		String keyword = "";
		try {
			String req = request.getParameter("req");
			if (!StringUtils.isBlank(req)) {
				param = JsonUtils.fromJson(req, Map.class);
			}
			if (param!=null && !param.isEmpty()) {
				if (param.get("pageSize") != null && !"".equals(param.get("pageSize").toString())) {
					pageSize = Integer.valueOf(param.get("pageSize").toString());
				}
				if (param.get("page") != null && !"".equals(param.get("page").toString())) {
					page = Integer.valueOf(param.get("page").toString());
				}
				keyword = param.get("keyword") == null ? keyword :  param.get("keyword").toString();
				queryType = param.get("queryType") == null ? queryType :  param.get("queryType").toString();
			}
			
			soxTempPageEntity.setPage(page);
			soxTempPageEntity.setPageSize(pageSize);
			soxTempPageEntity.setOperType(param.get("operType")+"");
			if (queryType.equals("sox_tpl_name")) {
				soxTempPageEntity.setSox_tpl_name(keyword);
			}
			
			soxTempPageEntity = soxService.getSoxTempListByParam(soxTempPageEntity);
			
			resultEntity.setValue(true, "success","",soxTempPageEntity);
			
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
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空");
			}
			resultEntity = soxService.addSoxTemp(req);
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
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空");
			}
			resultEntity = soxService.updateSoxTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 删除模板信息
	 *
	 * <p>2016年6月22日 下午5:09:25 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/delSoxTemp")
	@ResponseBody
	public ResultEntity delSoxTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空");
			}
			resultEntity = soxService.delSoxTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 批量删除模板
	 *
	 * <p>2016年6月22日 下午5:09:25 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/delMultiSoxTemp")
	@ResponseBody
	public ResultEntity delMultiSoxTemp(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if (StringUtils.isBlank(req) || req.equals("{}")) {
				resultEntity.setValue(false, "参数不能为空");
			}
			resultEntity = soxService.delMultiSoxTemp(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	
	
}
