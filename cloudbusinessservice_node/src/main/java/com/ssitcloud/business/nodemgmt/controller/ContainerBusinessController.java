package com.ssitcloud.business.nodemgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.nodemgmt.service.ContainerBusinessService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value = { "container" })
public class ContainerBusinessController {
	
	@Resource
	private ContainerBusinessService capacityBusinessService;

	/** 分页查询方法 */
	@RequestMapping(value = { "queryByPage" })
	@ResponseBody
	public ResultEntity queryContainerByPage(HttpServletRequest request, String req) {
		return capacityBusinessService.queryContainerByPage(req);
	}

	/** 根据条件查询 */
	@RequestMapping(value = { "queryByParam" })
	@ResponseBody
	public ResultEntity queryContainerByParam(HttpServletRequest request, String req) {
		return capacityBusinessService.queryContainerByParam(req);
	}

	/** 根据ID查询 */
	@RequestMapping(value = { "queryById" })
	@ResponseBody
	public ResultEntity queryContainerById(HttpServletRequest request, String req) {
		return capacityBusinessService.queryContainerById(req);
	}

	/** 删除 */
	@RequestMapping(value = { "delete" })
	@ResponseBody
	public ResultEntity deleteContainerById(HttpServletRequest request, String req) {
		return capacityBusinessService.deleteContainerById(req);
	}

	/** 更新 */
	@RequestMapping(value = { "update" })
	@ResponseBody
	public ResultEntity updateContainer(HttpServletRequest request, String req) {
		return capacityBusinessService.updateContainer(req);
	}

	/** 新增 */
	@RequestMapping(value = { "add" })
	@ResponseBody
	public ResultEntity addContainer(HttpServletRequest request, String req) {
		return capacityBusinessService.addContainer(req);
	}
	
	/**
	 * 运行容器
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "start" })
	@ResponseBody
	public ResultEntity start(HttpServletRequest request, String req) {
		return capacityBusinessService.start(req);
	}

	/**
	 * 停止容器运行
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "stop" })
	@ResponseBody
	public ResultEntity stop(HttpServletRequest request, String req) {
		return capacityBusinessService.stop(req);
	}

}
