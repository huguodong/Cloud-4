package com.ssitcloud.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.StatisticsConfigEntity;
import com.ssitcloud.service.StatisticsConfigService;

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
	 * author huanghuang
	 * 2017年2月9日 下午1:42:14
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"addStatisticsConfig"})
	@ResponseBody
	public ResultEntity addStatisticsConfig (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				StatisticsConfigEntity statisticsConfigEntity = JsonUtils.fromJson(json, StatisticsConfigEntity.class);
				int ret = statisticsConfigService.insertStatisticsConfig(statisticsConfigEntity);
				int type = statisticsConfigEntity.getStatistics_tpl_type();
				String data = statisticsConfigEntity.getStatistics_tpl_value();
				JSONObject djson = JSONObject.fromObject(data);
				String typeName="";
				if(type == 1){
					typeName="查询模板";
				}else if(type == 2){
					typeName="统计模板";
				}
				String name = djson.getJSONObject("headerData").getString("templateName");
				String retMessage="|模板idx："+statisticsConfigEntity.getStatistics_tpl_idx()+"|模板名称："+name+"|模板类型："+typeName+"|";
				if (ret>0) {
					resultEntity.setValue(true, "success","",statisticsConfigEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
				resultEntity.setRetMessage(retMessage);
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
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
	 * author huanghuang
	 * 2017年2月9日 下午1:42:33
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"updateStatisticsConfig"})
	@ResponseBody
	public ResultEntity updateStatisticsConfig (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				StatisticsConfigEntity statisticsConfigEntity = JsonUtils.fromJson(json, StatisticsConfigEntity.class);
				int ret = statisticsConfigService.updateStatisticsConfig(statisticsConfigEntity);
				int type = statisticsConfigEntity.getStatistics_tpl_type();
				String data = statisticsConfigEntity.getStatistics_tpl_value();
				JSONObject djson = JSONObject.fromObject(data);
				String typeName="";
				if(type == 1){
					typeName="查询模板";
				}else if(type == 2){
					typeName="统计模板";
				}
				String name = djson.getJSONObject("headerData").getString("templateName");
				String retMessage="|模板idx："+statisticsConfigEntity.getStatistics_tpl_idx()+"|模板名称："+name+"|模板类型："+typeName+"|";
				if (ret>0) {
					resultEntity.setValue(true, "success","",statisticsConfigEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
				resultEntity.setRetMessage(retMessage);
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
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
	 * author huanghuang
	 * 2017年2月9日 下午1:43:32
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"deleteStatisticsConfig"})
	@ResponseBody
	public ResultEntity deleteStatisticsConfig (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				StatisticsConfigEntity statisticsConfigEntity = JsonUtils.fromJson(json, StatisticsConfigEntity.class);
				int ret = statisticsConfigService.deleteStatisticsConfig(statisticsConfigEntity);
				String retMessage="|模板idx："+statisticsConfigEntity.getStatistics_tpl_idx()+"|";
				if (ret>0) {
					resultEntity.setValue(true, "success","",statisticsConfigEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
				resultEntity.setRetMessage(retMessage);
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
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
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectStatisticsConfig"})
	@ResponseBody
	public ResultEntity selectStatisticsConfig (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				StatisticsConfigEntity statisticsConfigEntity = JsonUtils.fromJson(json, StatisticsConfigEntity.class);
				statisticsConfigEntity = statisticsConfigService.queryOneStatisticsConfig(statisticsConfigEntity);
				resultEntity.setValue(true, "success","",statisticsConfigEntity);
			}else {
				resultEntity.setValue(false, "参数不能为空");
			}
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
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectStatisticsConfigs"})
	@ResponseBody
	public ResultEntity selectStatisticsConfigs (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			StatisticsConfigEntity statisticsConfigEntity = JsonUtils.fromJson(json, StatisticsConfigEntity.class);
			List<StatisticsConfigEntity> list = statisticsConfigService.queryStatisticsConfigs(statisticsConfigEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	
}
