package com.ssitcloud.business.opermgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.opermgmt.service.ServGroupService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping("/servicegroup")
public class ServiceGroupController {
	@Resource
	private ServGroupService servGroupService;
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
		ResultEntity result=new ResultEntity();
		try {
			result=servGroupService.addservgroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
		
	}
	/**
	 * 删除权限组操作
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"delservgroup"})
	@ResponseBody
	public ResultEntity delservgroup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=servGroupService.delservgroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 修改权限组操作
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"updservgroup"})
	@ResponseBody
	public ResultEntity updservgroup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=servGroupService.updservgroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

}
