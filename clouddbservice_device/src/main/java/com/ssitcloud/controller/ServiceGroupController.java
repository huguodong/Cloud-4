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
import com.ssitcloud.entity.ServiceGroupEntity;
import com.ssitcloud.service.ServiceGroupService;

/**
 * @comment 业务组表Controller
 * 
 * @author hwl
 * @data 2016年4月5日
 */

@Controller
@RequestMapping("/servicegroup")
public class ServiceGroupController {

	@Resource
	ServiceGroupService servicegroupservice;


	/**
	 * 根据条件查询业务组数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "SelectServiceGroup" })
	@ResponseBody
	public ResultEntity SelectServiceGroup(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			List<ServiceGroupEntity> sEntities = servicegroupservice
					.selbyidServiceGroup(JsonUtils.fromJson(
							request.getParameter("json"),
							ServiceGroupEntity.class));
			result.setResult(sEntities);
			result.setMessage(Const.SUCCESS);
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 新增操作
	 * req={
			"service_group_id":service_group_id,
			"service_group_name":service_group_name,
			"service_group_desc":service_group_desc,
			"meta_opercmd_idx_str":1,3,5,6
			"library_idx":1
		
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"addservgroup"})
	@ResponseBody
	public ResultEntity addservgroup(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result=servicegroupservice.addservgroup(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	/**
	 * 删除操作
	 * req={
	 * 	service_group_idx:"..."
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"delservgroup"})
	@ResponseBody
	public ResultEntity delservgroup(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result=servicegroupservice.delservgroup(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 修改操作
	 * req={
	 * 		"service_group_idx":service_group_idx,
			"service_group_id":service_group_id,
			"service_group_name":service_group_name,
			"service_group_desc":service_group_desc,
			"meta_opercmd_idx_str":1,3,5,6
			"library_idx":1
		
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"updservgroup"})
	@ResponseBody
	public ResultEntity updservgroup(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result=servicegroupservice.updservgroup(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
