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
import com.ssitcloud.entity.RelOperatorServiceGroupEntity;
import com.ssitcloud.service.RelOperatorServiceGroupService;

/**
 * @comment 操作员组和业务组关联表controller
 * 
 * @author hwl
 * @data 2016-3-29下午3:46:48
 * 
 */
@Controller
@RequestMapping("/reloperatorservicegroup")
public class RelOperatorServiceGroupController {

	@Resource
	RelOperatorServiceGroupService reloperatorserviceGroupservice;

	/**
	 * 添加操作员组和业务组关联数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "AddRelOperServGroup" })
	@ResponseBody
	public ResultEntity AddRelOperServGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = reloperatorserviceGroupservice
					.addRelOperatorServiceGroup(JsonUtils.fromJson(
							request.getParameter("json"),
							RelOperatorServiceGroupEntity.class));
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
	 * 更新操作员组和业务组关联数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "UpdRelOperServGroup" })
	@ResponseBody
	public ResultEntity UpdRelOperServGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = reloperatorserviceGroupservice
					.updRelOperatorServiceGroup(JsonUtils.fromJson(
							request.getParameter("json"),
							RelOperatorServiceGroupEntity.class));
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
	 * 删除操作员组和业务组关联数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "DeleteRelOperServGroup" })
	@ResponseBody
	public ResultEntity DeleteRelOperServGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = reloperatorserviceGroupservice
					.delRelOperatorServiceGroup(JsonUtils.fromJson(
							request.getParameter("json"),
							RelOperatorServiceGroupEntity.class));
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
	 * 更新操作员组和业务组关联数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "SelectRelOperServGroup" })
	@ResponseBody
	public ResultEntity SelectRelOperServGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			List<RelOperatorServiceGroupEntity> rEntities = reloperatorserviceGroupservice
					.selbyidRelOperatorServiceGroup(JsonUtils.fromJson(
							request.getParameter("json"),
							RelOperatorServiceGroupEntity.class));
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
