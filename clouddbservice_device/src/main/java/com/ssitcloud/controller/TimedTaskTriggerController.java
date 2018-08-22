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
import com.ssitcloud.entity.TimedTaskTriggerEntity;
import com.ssitcloud.service.TimedTaskTriggerService;

@Controller
@RequestMapping(value={"/timedTaskTrigger"})
public class TimedTaskTriggerController {
	@Resource
	private TimedTaskTriggerService timedTaskTriggerService;
	
	/**
	 * 新增定时任务触发器TimedTaskTriggerEntity
	 * 格式
	 * json = {
	 * 		"tri_idx":"",//定时任务触发器主键，自增
	 * 		"table_name":"",
	 * 		"data_idx":"",
	 * 		"change_state":"",
	 * 		"create_time":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:14
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/addTimedTaskTrigger"})
	@ResponseBody
	public ResultEntity addTimedTaskTrigger (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				TimedTaskTriggerEntity timedTaskTriggerEntity = JsonUtils.fromJson(json, TimedTaskTriggerEntity.class);
				int ret = timedTaskTriggerService.insertTimedTaskTrigger(timedTaskTriggerEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",timedTaskTriggerEntity);
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
	 * 修改定时任务相关配置TimedTaskTriggerEntity
	 * 格式
	 * json = {
	 * 		"tri_idx":"",//定时任务触发器主键，自增
	 * 		"table_name":"",
	 * 		"data_idx":"",
	 * 		"change_state":"",
	 * 		"create_time":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:33
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/updateTimedTaskTrigger"})
	@ResponseBody
	public ResultEntity updateTimedTaskTrigger (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				TimedTaskTriggerEntity timedTaskTriggerEntity = JsonUtils.fromJson(json, TimedTaskTriggerEntity.class);
				int ret = timedTaskTriggerService.updateTimedTaskTrigger(timedTaskTriggerEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",timedTaskTriggerEntity);
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
	 * 删除定时任务相关配置TimedTaskTriggerEntity
	 * 格式
	 * json = {
	 * 		"tri_idx":"",//定时任务触发器主键，自增
	 * 		"table_name":"",
	 * 		"data_idx":"",
	 * 		"change_state":"",
	 * 		"create_time":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:32
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/deleteTimedTaskTrigger"})
	@ResponseBody
	public ResultEntity deleteTimedTaskTrigger (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				TimedTaskTriggerEntity timedTaskTriggerEntity = JsonUtils.fromJson(json, TimedTaskTriggerEntity.class);
				int ret = timedTaskTriggerService.deleteTimedTaskTrigger(timedTaskTriggerEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",timedTaskTriggerEntity);
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
	 * 查询一条定时任务相关配置记录TimedTaskTriggerEntity
	 * 格式
	 * json = {
	 * 		"tri_idx":"",//定时任务触发器主键，自增
	 * 		"table_name":"",
	 * 		"data_idx":"",
	 * 		"change_state":"",
	 * 		"create_time":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectTimedTaskTrigger"})
	@ResponseBody
	public ResultEntity selectTimedTaskTrigger (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				TimedTaskTriggerEntity timedTaskTriggerEntity = JsonUtils.fromJson(json, TimedTaskTriggerEntity.class);
				timedTaskTriggerEntity = timedTaskTriggerService.queryOneTimedTaskTrigger(timedTaskTriggerEntity);
				resultEntity.setValue(true, "success","",timedTaskTriggerEntity);
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
	 * 查询多条定时任务相关配置记录TimedTaskTriggerEntity
	 * 格式
	 * json = {
	 * 		"tri_idx":"",//定时任务触发器主键，自增
	 * 		"table_name":"",
	 * 		"data_idx":"",
	 * 		"change_state":"",
	 * 		"create_time":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectTimedTaskTriggers"})
	@ResponseBody
	public ResultEntity selectTimedTaskTriggers (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			TimedTaskTriggerEntity timedTaskTriggerEntity = JsonUtils.fromJson(json, TimedTaskTriggerEntity.class);
			List<TimedTaskTriggerEntity> list = timedTaskTriggerService.queryTimedTaskTriggers(timedTaskTriggerEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	
}
