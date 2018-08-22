package com.ssitcloud.view.statistics.statisticsmgmt.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.statistics.common.util.ExceptionHelper;
import com.ssitcloud.view.statistics.statisticsmgmt.service.StatisticsConfigService;

@Controller
@RequestMapping(value={"statisticsConfig"})
public class StatisticsConfigController {
	@Resource
	private StatisticsConfigService statisticsConfigService;
	
	/**
	 * 新增统计查询模板详情StatisticsConfigEntity
	 * 格式
	 * json = {
	 * 		"statistics_tpl_idx":"",//统计查询模板详情主键，自增
	 * 		"statistics_tpl_type":"",
	 * 		"statistics_tpl_value":""
	 * }
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"addStatisticsConfig"})
	@ResponseBody
	public ResultEntity addStatisticsConfig (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsConfigService.insertStatisticsConfig(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 修改统计查询模板详情StatisticsConfigEntity
	 * 格式
	 * json = {
	 * 		"statistics_tpl_idx":"",//统计查询模板详情主键，自增
	 * 		"statistics_tpl_type":"",
	 * 		"statistics_tpl_value":""
	 * }
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"updateStatisticsConfig"})
	@ResponseBody
	public ResultEntity updateStatisticsConfig (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsConfigService.updateStatisticsConfig(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 删除统计查询模板详情StatisticsConfigEntity
	 * 格式
	 * json = {
	 * 		"statistics_tpl_idx":"",//统计查询模板详情主键，自增
	 * 		"statistics_tpl_type":"",
	 * 		"statistics_tpl_value":""
	 * }
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"deleteStatisticsConfig"})
	@ResponseBody
	public ResultEntity deleteStatisticsConfig (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsConfigService.deleteStatisticsConfig(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询一条统计查询模板详情记录StatisticsConfigEntity
	 * 格式
	 * json = {
	 * 		"statistics_tpl_idx":"",//统计查询模板详情主键，自增
	 * 		"statistics_tpl_type":"",
	 * 		"statistics_tpl_value":""
	 * }
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectStatisticsConfig"})
	@ResponseBody
	public ResultEntity selectStatisticsConfig (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsConfigService.queryOneStatisticsConfig(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询多条统计查询模板详情记录StatisticsConfigEntity
	 * 格式
	 * json = {
	 * 		"statistics_tpl_idx":"",//统计查询模板详情主键，自增
	 * 		"statistics_tpl_type":"",
	 * 		"statistics_tpl_value":""
	 * }
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectStatisticsConfigs"})
	@ResponseBody
	public ResultEntity selectStatisticsConfigs (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsConfigService.queryStatisticsConfigs(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	
}
