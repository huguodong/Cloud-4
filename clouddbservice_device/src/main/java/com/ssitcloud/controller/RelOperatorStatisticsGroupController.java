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
import com.ssitcloud.entity.RelOperatorStatisticsGroupEntity;
import com.ssitcloud.service.RelOperatorStatisticsGroupService;

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
	 * author huanghuang
	 * 2017年2月9日 下午1:42:14
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"addRelOperatorStatisticsGroup"})
	@ResponseBody
	public ResultEntity addRelOperatorStatisticsGroup (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				RelOperatorStatisticsGroupEntity relOperatorStatisticsGroupEntity = JsonUtils.fromJson(json, RelOperatorStatisticsGroupEntity.class);
				int ret = relOperatorStatisticsGroupService.insertRelOperatorStatisticsGroup(relOperatorStatisticsGroupEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",relOperatorStatisticsGroupEntity);
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
	 * 修改操作员组与模板组关联RelOperatorStatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"rel_oper_statistics_idx":"",//操作员组与模板组关联主键，自增
	 * 		"operator_group_idx":"",
	 * 		"statistics_group_idx":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:33
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"updateRelOperatorStatisticsGroup"})
	@ResponseBody
	public ResultEntity updateRelOperatorStatisticsGroup (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				RelOperatorStatisticsGroupEntity relOperatorStatisticsGroupEntity = JsonUtils.fromJson(json, RelOperatorStatisticsGroupEntity.class);
				int ret = relOperatorStatisticsGroupService.updateRelOperatorStatisticsGroup(relOperatorStatisticsGroupEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",relOperatorStatisticsGroupEntity);
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
	 * 删除操作员组与模板组关联RelOperatorStatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"rel_oper_statistics_idx":"",//操作员组与模板组关联主键，自增
	 * 		"operator_group_idx":"",
	 * 		"statistics_group_idx":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:32
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"deleteRelOperatorStatisticsGroup"})
	@ResponseBody
	public ResultEntity deleteRelOperatorStatisticsGroup (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				RelOperatorStatisticsGroupEntity relOperatorStatisticsGroupEntity = JsonUtils.fromJson(json, RelOperatorStatisticsGroupEntity.class);
				int ret = relOperatorStatisticsGroupService.deleteRelOperatorStatisticsGroup(relOperatorStatisticsGroupEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",relOperatorStatisticsGroupEntity);
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
	 * 查询一条操作员组与模板组关联记录RelOperatorStatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"rel_oper_statistics_idx":"",//操作员组与模板组关联主键，自增
	 * 		"operator_group_idx":"",
	 * 		"statistics_group_idx":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectRelOperatorStatisticsGroup"})
	@ResponseBody
	public ResultEntity selectRelOperatorStatisticsGroup (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				RelOperatorStatisticsGroupEntity relOperatorStatisticsGroupEntity = JsonUtils.fromJson(json, RelOperatorStatisticsGroupEntity.class);
				relOperatorStatisticsGroupEntity = relOperatorStatisticsGroupService.queryOneRelOperatorStatisticsGroup(relOperatorStatisticsGroupEntity);
				resultEntity.setValue(true, "success","",relOperatorStatisticsGroupEntity);
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
	 * 查询多条操作员组与模板组关联记录RelOperatorStatisticsGroupEntity
	 * 格式
	 * json = {
	 * 		"rel_oper_statistics_idx":"",//操作员组与模板组关联主键，自增
	 * 		"operator_group_idx":"",
	 * 		"statistics_group_idx":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectRelOperatorStatisticsGroups"})
	@ResponseBody
	public ResultEntity selectRelOperatorStatisticsGroups (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			RelOperatorStatisticsGroupEntity relOperatorStatisticsGroupEntity = JsonUtils.fromJson(json, RelOperatorStatisticsGroupEntity.class);
			List<RelOperatorStatisticsGroupEntity> list = relOperatorStatisticsGroupService.queryRelOperatorStatisticsGroups(relOperatorStatisticsGroupEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	
}
