package com.ssitcloud.business.statisticsmgmt.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.statisticsmgmt.service.RelOperatorStatisticsGroupService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value={"relOperatorStatisticsGroup"})
public class RelOperatorStatisticsGroupController {
	@Resource
	private RelOperatorStatisticsGroupService relOperatorStatisticsGroupService;
	
	/**
	 * 新增操作员组与模板组关联RelOperatorStatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"rel_oper_statistics_idx":"",//操作员组与模板组关联主键，自增
	 * 		"operator_group_idx":"",
	 * 		"statistics_group_idx":""
	 * }
	 * author lqw
	 * 2017年3月13日 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"addRelOperatorStatisticsGroup"})
	@ResponseBody
	public ResultEntity addRelOperatorStatisticsGroup (HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=relOperatorStatisticsGroupService.insertRelOperatorStatisticsGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 修改操作员组与模板组关联RelOperatorStatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"rel_oper_statistics_idx":"",//操作员组与模板组关联主键，自增
	 * 		"operator_group_idx":"",
	 * 		"statistics_group_idx":""
	 * }
	 * author lqw
	 * 2017年3月13日 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"updateRelOperatorStatisticsGroup"})
	@ResponseBody
	public ResultEntity updateRelOperatorStatisticsGroup (HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=relOperatorStatisticsGroupService.updateRelOperatorStatisticsGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 删除操作员组与模板组关联RelOperatorStatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"rel_oper_statistics_idx":"",//操作员组与模板组关联主键，自增
	 * 		"operator_group_idx":"",
	 * 		"statistics_group_idx":""
	 * }
	 * author lqw
	 * 2017年3月13日 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"deleteRelOperatorStatisticsGroup"})
	@ResponseBody
	public ResultEntity deleteRelOperatorStatisticsGroup (HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=relOperatorStatisticsGroupService.deleteRelOperatorStatisticsGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查询一条操作员组与模板组关联记录RelOperatorStatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"rel_oper_statistics_idx":"",//操作员组与模板组关联主键，自增
	 * 		"operator_group_idx":"",
	 * 		"statistics_group_idx":""
	 * }
	 * author lqw
	 * 2017年3月13日 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectRelOperatorStatisticsGroup"})
	@ResponseBody
	public ResultEntity selectRelOperatorStatisticsGroup (HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=relOperatorStatisticsGroupService.queryOneRelOperatorStatisticsGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查询多条操作员组与模板组关联记录RelOperatorStatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"rel_oper_statistics_idx":"",//操作员组与模板组关联主键，自增
	 * 		"operator_group_idx":"",
	 * 		"statistics_group_idx":""
	 * }
	 * author lqw
	 * 2017年3月13日 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectRelOperatorStatisticsGroups"})
	@ResponseBody
	public ResultEntity selectRelOperatorStatisticsGroups (HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=relOperatorStatisticsGroupService.queryRelOperatorStatisticsGroups(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	
}
