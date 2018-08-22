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
import com.ssitcloud.entity.RelOperatorDeviceGroupEntity;
import com.ssitcloud.service.RelOperatorDeviceGroupService;

/**
 * 
 * @comment 操作员组和设备组关联表Controller
 * 
 * @author hwl
 * @data 2016年4月6日
 */

@Controller
@RequestMapping("/reloperatordevicegroup")
public class RelOperatorDeviceGroupController {

	@Resource
	RelOperatorDeviceGroupService relOperatorDeviceGroupService;

	/**
	 * 添加操作员组和设备组关联数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "AddRelOperDevGroup" })
	@ResponseBody
	public ResultEntity AddRelOperDevGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = relOperatorDeviceGroupService
					.addRelOperatorDeviceGroup(JsonUtils.fromJson(
							request.getParameter("json"),
							RelOperatorDeviceGroupEntity.class));
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
	 * 更新操作员组和设备组关联数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "UpdRelOperDevGroup" })
	@ResponseBody
	public ResultEntity UpdRelOperDevGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = relOperatorDeviceGroupService
					.updRelOperatorDeviceGroup(JsonUtils.fromJson(
							request.getParameter("json"),
							RelOperatorDeviceGroupEntity.class));
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
	 * 删除操作员组和设备组关联数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "DeleteRelOperDevGroup" })
	@ResponseBody
	public ResultEntity DeleteRelOperDevGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = relOperatorDeviceGroupService
					.delRelOperatorDeviceGroup(JsonUtils.fromJson(
							request.getParameter("json"),
							RelOperatorDeviceGroupEntity.class));
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
	 * 根据条件查询操作员组和设备组关联数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "SelectRelOperDevGroup" })
	@ResponseBody
	public ResultEntity SelectRelOperDevGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			List<RelOperatorDeviceGroupEntity> rEntities = relOperatorDeviceGroupService
					.selbyidRelOperatorDeviceGroup(JsonUtils.fromJson(
							request.getParameter("json"),
							RelOperatorDeviceGroupEntity.class));
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
