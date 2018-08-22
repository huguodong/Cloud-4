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
import com.ssitcloud.entity.TimedTaskEntity;
import com.ssitcloud.service.TimedTaskService;

@Controller
@RequestMapping(value={"timedTask"})
public class TimedTaskController {
	@Resource
	private TimedTaskService timedTaskService;
	
	/**
	 * 新增定时任务相关配置TimedTaskEntity
	 * 格式
	 * json = {
	 * 		"task_idx":"",//定时任务相关配置主键，自增
	 * 		"lib_idx":"",
	 * 		"task_type":"",
	 * 		"task_starttime":"",
	 * 		"oper_idx":"",
	 * 		"task_name":"",
	 * 		"task_desc":"",
	 * 		"task_state":"",
	 * 		"creattime":"",
	 * 		"updatetime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:14
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"addTimedTask"})
	@ResponseBody
	public ResultEntity addTimedTask (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				TimedTaskEntity timedTaskEntity = JsonUtils.fromJson(json, TimedTaskEntity.class);
				int ret = timedTaskService.insertTimedTask(timedTaskEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",timedTaskEntity);
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
	 * 修改定时任务相关配置TimedTaskEntity
	 * 格式
	 * json = {
	 * 		"task_idx":"",//定时任务相关配置主键，自增
	 * 		"lib_idx":"",
	 * 		"task_type":"",
	 * 		"task_starttime":"",
	 * 		"oper_idx":"",
	 * 		"task_name":"",
	 * 		"task_desc":"",
	 * 		"task_state":"",
	 * 		"creattime":"",
	 * 		"updatetime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:33
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"updateTimedTask"})
	@ResponseBody
	public ResultEntity updateTimedTask (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				TimedTaskEntity timedTaskEntity = JsonUtils.fromJson(json, TimedTaskEntity.class);
				int ret = timedTaskService.updateTimedTask(timedTaskEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",timedTaskEntity);
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
	 * 删除定时任务相关配置TimedTaskEntity
	 * 格式
	 * json = {
	 * 		"task_idx":"",//定时任务相关配置主键，自增
	 * 		"lib_idx":"",
	 * 		"task_type":"",
	 * 		"task_starttime":"",
	 * 		"oper_idx":"",
	 * 		"task_name":"",
	 * 		"task_desc":"",
	 * 		"task_state":"",
	 * 		"creattime":"",
	 * 		"updatetime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:32
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"deleteTimedTask"})
	@ResponseBody
	public ResultEntity deleteTimedTask (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				TimedTaskEntity timedTaskEntity = JsonUtils.fromJson(json, TimedTaskEntity.class);
				int ret = timedTaskService.deleteTimedTask(timedTaskEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",timedTaskEntity);
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
	 * 查询一条定时任务相关配置记录TimedTaskEntity
	 * 格式
	 * json = {
	 * 		"task_idx":"",//定时任务相关配置主键，自增
	 * 		"lib_idx":"",
	 * 		"task_type":"",
	 * 		"task_starttime":"",
	 * 		"oper_idx":"",
	 * 		"task_name":"",
	 * 		"task_desc":"",
	 * 		"task_state":"",
	 * 		"creattime":"",
	 * 		"updatetime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectTimedTask"})
	@ResponseBody
	public ResultEntity selectTimedTask (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				TimedTaskEntity timedTaskEntity = JsonUtils.fromJson(json, TimedTaskEntity.class);
				timedTaskEntity = timedTaskService.queryOneTimedTask(timedTaskEntity);
				resultEntity.setValue(true, "success","",timedTaskEntity);
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
	 * 查询多条定时任务相关配置记录TimedTaskEntity
	 * 格式
	 * json = {
	 * 		"task_idx":"",//定时任务相关配置主键，自增
	 * 		"lib_idx":"",
	 * 		"task_type":"",
	 * 		"task_starttime":"",
	 * 		"oper_idx":"",
	 * 		"task_name":"",
	 * 		"task_desc":"",
	 * 		"task_state":"",
	 * 		"creattime":"",
	 * 		"updatetime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectTimedTasks"})
	@ResponseBody
	public ResultEntity selectTimedTasks (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			TimedTaskEntity timedTaskEntity = JsonUtils.fromJson(json, TimedTaskEntity.class);
			List<TimedTaskEntity> list = timedTaskService.queryTimedTasks(timedTaskEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	
}
