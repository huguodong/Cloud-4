package com.ssitcloud.business.readermgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.readermgmt.service.ConfigFieldService;
import com.ssitcloud.common.entity.ResultEntity;
@Controller
@RequestMapping("/configfield")
public class ConfigFieldController {
	@Resource
	private ConfigFieldService configFieldService;
	
	@RequestMapping("/selectConfigFieldList")
	@ResponseBody
	public ResultEntity selectConfigFieldList(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			result = configFieldService.selectConfigFieldList(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value={"takeDataSource"})
	@ResponseBody
	public ResultEntity takeDataSource(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("req");
			result = configFieldService.takeDataSource(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value={"addConfigTemplate"})
	@ResponseBody
	public ResultEntity addConfigTemplate (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = configFieldService.insertConfigTemplate(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"selectConfigTemplatePage"})
	@ResponseBody
	public ResultEntity selectStatisticsTemplatePage (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = configFieldService.selectConfigTemplatePage(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"selectImportConfig"})
	@ResponseBody
	public ResultEntity selectImportConfig (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = configFieldService.queryOneImportConfig(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"updateImportTemplate"})
	@ResponseBody
	public ResultEntity updateStatisticsTemplate (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = configFieldService.updateImportTemplate(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"deleteImportTemplate"})
	@ResponseBody
	public ResultEntity deleteStatisticsTemplate (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = configFieldService.deleteImportTemplate(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
}
