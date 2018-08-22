package com.ssitcloud.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.RelServiceGroupEntity;
import com.ssitcloud.service.RelServiceGroupService;

/**
 * 
 * @comment 业务分组关联表Controller
 * 
 * @author hwl
 * @data 2016年4月6日
 */
@Controller
@RequestMapping("/relservicegroup")
public class RelServiceGroupController {

	@Resource
	RelServiceGroupService relServiceGroupService;

	/**
	 * 添加业务分组关联数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "AddRelServGroup" })
	@ResponseBody
	public ResultEntity AddRelServGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re =relServiceGroupService.addRelServiceGroup(JsonUtils.fromJson(
					request.getParameter("json"), RelServiceGroupEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 更新业务分组关联数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "UpdRelServGroup" })
	@ResponseBody
	public ResultEntity UpdRelServGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re =relServiceGroupService.updRelServiceGroup(JsonUtils.fromJson(
					request.getParameter("json"), RelServiceGroupEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 删除业务分组关联数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "DeleteRelServGroup" })
	@ResponseBody
	public ResultEntity DeleteRelServGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re =relServiceGroupService.delRelServiceGroup(JsonUtils.fromJson(
					request.getParameter("json"), RelServiceGroupEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 条件查询业务分组关联数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "SelectRelServGroup" })
	@ResponseBody
	public ResultEntity SelectRelServGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			List<RelServiceGroupEntity> rEntities = relServiceGroupService
					.selbyidRelServiceGroup(JsonUtils.fromJson(
							request.getParameter("json"),
							RelServiceGroupEntity.class));
			result.setResult(rEntities);
			result.setMessage(Const.SUCCESS);
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
