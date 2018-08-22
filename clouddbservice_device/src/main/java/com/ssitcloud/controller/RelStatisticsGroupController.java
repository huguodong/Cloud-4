package com.ssitcloud.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.RelStatisticsGroupEntity;
import com.ssitcloud.service.RelStatisticsGroupService;

@Controller
@RequestMapping(value={"relStatisticsGroup"})
public class RelStatisticsGroupController {
	@Resource
	private RelStatisticsGroupService relStatisticsGroupService;
	
	/**
	 * 新增统计模板与模板组对应关系RelStatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"rel_statistics_idx":"",//统计模板与模板组对应关系主键，自增
	 * 		"statistics_group_idx":"",
	 * 		"statistics_idx":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:14
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"addRelStatisticsGroup"})
	@ResponseBody
	public ResultEntity addRelStatisticsGroup (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				RelStatisticsGroupEntity relStatisticsGroupEntity = JsonUtils.fromJson(json, RelStatisticsGroupEntity.class);
				int ret = relStatisticsGroupService.insertRelStatisticsGroup(relStatisticsGroupEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",relStatisticsGroupEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
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
	 * 修改统计模板与模板组对应关系RelStatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"rel_statistics_idx":"",//统计模板与模板组对应关系主键，自增
	 * 		"statistics_group_idx":"",
	 * 		"statistics_idx":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:33
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"updateRelStatisticsGroup"})
	@ResponseBody
	public ResultEntity updateRelStatisticsGroup (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				RelStatisticsGroupEntity relStatisticsGroupEntity = JsonUtils.fromJson(json, RelStatisticsGroupEntity.class);
				int ret = relStatisticsGroupService.updateRelStatisticsGroup(relStatisticsGroupEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",relStatisticsGroupEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
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
	 * 删除统计模板与模板组对应关系RelStatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"rel_statistics_idx":"",//统计模板与模板组对应关系主键，自增
	 * 		"statistics_group_idx":"",
	 * 		"statistics_idx":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:32
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"deleteRelStatisticsGroup"})
	@ResponseBody
	public ResultEntity deleteRelStatisticsGroup (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				RelStatisticsGroupEntity relStatisticsGroupEntity = JsonUtils.fromJson(json, RelStatisticsGroupEntity.class);
				int ret = relStatisticsGroupService.deleteRelStatisticsGroup(relStatisticsGroupEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",relStatisticsGroupEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
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
	 * 查询一条统计模板与模板组对应关系记录RelStatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"rel_statistics_idx":"",//统计模板与模板组对应关系主键，自增
	 * 		"statistics_group_idx":"",
	 * 		"statistics_idx":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectRelStatisticsGroup"})
	@ResponseBody
	public ResultEntity selectRelStatisticsGroup (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				RelStatisticsGroupEntity relStatisticsGroupEntity = JsonUtils.fromJson(json, RelStatisticsGroupEntity.class);
				relStatisticsGroupEntity = relStatisticsGroupService.queryOneRelStatisticsGroup(relStatisticsGroupEntity);
				resultEntity.setValue(true, "success","",relStatisticsGroupEntity);
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
	 * 查询多条统计模板与模板组对应关系记录RelStatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"rel_statistics_idx":"",//统计模板与模板组对应关系主键，自增
	 * 		"statistics_group_idx":"",
	 * 		"statistics_idx":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectRelStatisticsGroups"})
	@ResponseBody
	public ResultEntity selectRelStatisticsGroups (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			RelStatisticsGroupEntity relStatisticsGroupEntity = JsonUtils.fromJson(json, RelStatisticsGroupEntity.class);
			List<RelStatisticsGroupEntity> list = relStatisticsGroupService.queryRelStatisticsGroups(relStatisticsGroupEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	
}
