package com.ssitcloud.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.StatisticsGroupEntity;
import com.ssitcloud.entity.page.StatisticsGroupMgmtPageEntity;
import com.ssitcloud.service.StatisticsGroupService;

@Controller
@RequestMapping(value={"statisticsGroup"})
public class StatisticsGroupController {
	@Resource
	private StatisticsGroupService statisticsGroupService;
	
	/**
	 * 新增模板组StatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"statistics_group_idx":"",//模板组主键，自增
	 * 		"statistics_group_id":"",
	 * 		"statistics_group_name":"",
	 * 		"statistics_group_desc":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:14
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"addStatisticsGroup"})
	@ResponseBody
	public ResultEntity addStatisticsGroup (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				StatisticsGroupEntity statisticsGroupEntity = JsonUtils.fromJson(json, StatisticsGroupEntity.class);
				int ret = statisticsGroupService.insertStatisticsGroup(statisticsGroupEntity);
				String retMessage="|模板组idx："+statisticsGroupEntity.getStatistics_group_idx()+"|模板组名称："+statisticsGroupEntity.getStatistics_group_name()+"||";
				if (ret>0) {
					resultEntity.setValue(true, "success","",statisticsGroupEntity);
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
	 * 修改模板组StatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"statistics_group_idx":"",//模板组主键，自增
	 * 		"statistics_group_id":"",
	 * 		"statistics_group_name":"",
	 * 		"statistics_group_desc":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:33
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"updateStatisticsGroup"})
	@ResponseBody
	public ResultEntity updateStatisticsGroup (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				StatisticsGroupEntity statisticsGroupEntity = JsonUtils.fromJson(json, StatisticsGroupEntity.class);
				int ret = statisticsGroupService.updateStatisticsGroup(statisticsGroupEntity);
				String retMessage="|模板组idx："+statisticsGroupEntity.getStatistics_group_idx()+"|模板组名称："+statisticsGroupEntity.getStatistics_group_name()+"||";
				if (ret>0) {
					resultEntity.setValue(true, "success","",statisticsGroupEntity);
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
	 * 删除模板组StatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"statistics_group_idx":"",//模板组主键，自增
	 * 		"statistics_group_id":"",
	 * 		"statistics_group_name":"",
	 * 		"statistics_group_desc":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:32
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"deleteStatisticsGroup"})
	@ResponseBody
	public ResultEntity deleteStatisticsGroup (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				StatisticsGroupEntity statisticsGroupEntity = JsonUtils.fromJson(json, StatisticsGroupEntity.class);
				int ret = statisticsGroupService.deleteStatisticsGroup(statisticsGroupEntity);
				String retMessage="|模板组idx："+statisticsGroupEntity.getStatistics_group_idx()+"||";
				if (ret>0) {
					resultEntity.setValue(true, "success","",statisticsGroupEntity);
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
	 * 查询一条模板组记录StatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"statistics_group_idx":"",//模板组主键，自增
	 * 		"statistics_group_id":"",
	 * 		"statistics_group_name":"",
	 * 		"statistics_group_desc":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectStatisticsOneGroup"})
	@ResponseBody
	public ResultEntity selectStatisticsOneGroup (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				StatisticsGroupEntity statisticsGroupEntity = JsonUtils.fromJson(json, StatisticsGroupEntity.class);
				statisticsGroupEntity = statisticsGroupService.queryOneStatisticsGroup(statisticsGroupEntity);
				resultEntity.setValue(true, "success","",statisticsGroupEntity);
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
	 * 查询多条模板组记录StatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"statistics_group_idx":"",//模板组主键，自增
	 * 		"statistics_group_id":"",
	 * 		"statistics_group_name":"",
	 * 		"statistics_group_desc":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectStatisticsGroups"})
	@ResponseBody
	public ResultEntity selectStatisticsGroups (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			StatisticsGroupEntity statisticsGroupEntity = JsonUtils.fromJson(json, StatisticsGroupEntity.class);
			List<StatisticsGroupEntity> list = statisticsGroupService.queryStatisticsGroups(statisticsGroupEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 分页查询模板组记录StatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"statistics_group_idx":"",//模板组主键，自增
	 * 		"statistics_group_id":"",
	 * 		"statistics_group_name":"",
	 * 		"statistics_group_desc":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectStatisticsGroupByPage"})
	@ResponseBody
	public ResultEntity selectStatisticsGroupByPage (HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			String json = request.getParameter("req");
			StatisticsGroupMgmtPageEntity sGroupEntity = null;
			if(JSONUtils.mayBeJSON(json)){
				sGroupEntity = JsonUtils.fromJson(json, StatisticsGroupMgmtPageEntity.class);
			}
			StatisticsGroupMgmtPageEntity statisticsGroupMgmtPage = statisticsGroupService.selectStatisticsGroupByPage(sGroupEntity);
			result.setResult(statisticsGroupMgmtPage);
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 新增操作
	 * req={
			"statistics_group_id":"",
			"statistics_group_name":"",
			"statistics_group_desc":"",
			"selectTIDs":"1,3,5,6",
			"statisticsTIDs":"1,3,5,6"
		
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"increaseStatisticsGroup"})
	@ResponseBody
	public ResultEntity increaseStatisticsGroup(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			if(StringUtils.isBlank(req))
				req = request.getParameter("json");
			result=statisticsGroupService.addStatisticsGroup(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 修改操作
	 * req={
			"statistics_group_idx":"",
			"statistics_group_id":"",
			"statistics_group_name":"",
			"statistics_group_desc":"",
			"selectTIDs":"1,3,5,6",
			"statisticsTIDs":"1,3,5,6"
		
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"modifyStatisticsGroup"})
	@ResponseBody
	public ResultEntity modifyStatisticsGroup(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			if(StringUtils.isBlank(req))
				req = request.getParameter("json");
			result=statisticsGroupService.updStatisticsGroup(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 删除操作
	 * req={
			"gidx":""
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"removeStatisticsGroup"})
	@ResponseBody
	public ResultEntity removeStatisticsGroup(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			if(StringUtils.isBlank(req))
				req = request.getParameter("json");
			result=statisticsGroupService.delStatisticsGroup(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 分页查询模板组记录StatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"statistics_group_idx":"",//模板组主键，自增
	 * 		"statistics_group_id":"",
	 * 		"statistics_group_name":"",
	 * 		"statistics_group_desc":"",
	 * 		"statistics_idx_str":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"querysGroupByPageParam"})
	@ResponseBody
	public ResultEntity querysGroupByPageParam (HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			if(StringUtils.isBlank(req))
				req = request.getParameter("json");
			StatisticsGroupMgmtPageEntity sGroupEntity = null;
			if(JSONUtils.mayBeJSON(req)){
				sGroupEntity = JsonUtils.fromJson(req, StatisticsGroupMgmtPageEntity.class);
			}
			StatisticsGroupMgmtPageEntity statisticsGroupMgmtPage = statisticsGroupService.querysGroupByPage(sGroupEntity);
			result.setResult(statisticsGroupMgmtPage);
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	
	
}
