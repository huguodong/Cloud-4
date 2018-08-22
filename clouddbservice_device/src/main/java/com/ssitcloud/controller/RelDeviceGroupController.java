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
import com.ssitcloud.entity.RelDeviceGroupEntity;
import com.ssitcloud.service.RelDeviceGroupService;

/**
 * 
 * @comment 设备分组关联表Controller
 * 
 * @author hwl
 * @data 2016年4月6日
 */
@Controller
@RequestMapping("/reldevicegroup")
public class RelDeviceGroupController {

	@Resource
	RelDeviceGroupService relDeviceGroupService;

	/**
	 * 增加设备分组关联数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "AddRelDeviceGroup" })
	@ResponseBody
	public ResultEntity AddRelDeviceGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = relDeviceGroupService.addRelDeviceGroup(JsonUtils.fromJson(
					request.getParameter("json"), RelDeviceGroupEntity.class));
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
	 * 更新设备分组关联数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "UpdRelDeviceGroup" })
	@ResponseBody
	public ResultEntity UpdRelDeviceGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = relDeviceGroupService.updRelDeviceGroup(JsonUtils.fromJson(
					request.getParameter("json"), RelDeviceGroupEntity.class));
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
	 * 删除设备分组关联数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "DeleteRelDeviceGroup" })
	@ResponseBody
	public ResultEntity DeleteRelDeviceGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = relDeviceGroupService.delRelDeviceGroup(JsonUtils.fromJson(
					request.getParameter("json"), RelDeviceGroupEntity.class));
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
	 * 根据条件查询设备分组关联数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "SelectRelDeviceGroup" })
	@ResponseBody
	public ResultEntity SelectRelDeviceGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			List<RelDeviceGroupEntity> rEntities = relDeviceGroupService
					.selbyidRelDeviceGroup(JsonUtils.fromJson(
							request.getParameter("json"),
							RelDeviceGroupEntity.class));
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
