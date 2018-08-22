package com.ssitcloud.view.node.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.node.entity.ContainerEntity;
import com.ssitcloud.view.node.entity.HostEntity;
import com.ssitcloud.view.node.entity.page.ContainerPageEntity;
import com.ssitcloud.view.node.service.ContainerViewService;
import com.ssitcloud.view.node.service.HostViewService;

@Controller
@RequestMapping(value = { "container" })
public class ContainerViewController extends BasicController {
	@Resource
	private ContainerViewService containerViewService;
	@Resource
	private HostViewService hostViewService;

	/** 分页查询 */
	@RequestMapping(value = { "queryByPage" })
	@ResponseBody
	public ModelAndView queryContainerByPage(HttpServletRequest request, String req) {
		// return containerViewService.queryContainerByPage(req);
		Map<String, Object> model = new HashMap<>();
		model.put("oper", getCurrentUser());
		return new ModelAndView("/page/nodemgmt/container-manage", model);
	}

	/** 根据条件查询 */
	@RequestMapping(value = { "queryByParam" })
	@ResponseBody
	public ResultEntity queryContainerByParam(HttpServletRequest request, String req) {
		ContainerPageEntity entity = containerViewService.queryContainerByParam(req);
		ResultEntity result = new ResultEntity();
		if (entity != null) {
			result.setValue(true, "success", "", entity);
		} else {
			result.setState(false);
		}
		return result;
	}

	/** 根据id查询 */
	@RequestMapping(value = { "queryById" })
	@ResponseBody
	public ResultEntity queryContainerById(HttpServletRequest request, String req) {
		ContainerEntity entity = containerViewService.queryContainerById(req);
		ResultEntity result = new ResultEntity();
		if (entity != null) {
			result.setValue(true, "success", "", entity);
		} else {
			result.setState(false);
		}
		return result;
	}

	/** 删除 */
	@RequestMapping(value = { "delete" })
	@ResponseBody
	public ResultEntity deleteContainerById(HttpServletRequest request, String req) {
		return containerViewService.deleteContainerById(req);
	}

	/** 更新 */
	@RequestMapping(value = { "update" })
	@ResponseBody
	public ResultEntity updateContainer(HttpServletRequest request, String req) {
		return containerViewService.updateContainer(req);
	}

	/** 新增 */
	@RequestMapping(value = { "add" })
	@ResponseBody
	public ResultEntity addContainer(HttpServletRequest request, String req) {
		return containerViewService.addContainer(req);
	}

	/**
	 * 获取云主机列表
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "getHostList" })
	@ResponseBody
	public ResultEntity getHostList(HttpServletRequest request, String req) {
		List<HostEntity> list = hostViewService.queryHostByPage(req);
		ResultEntity entity = new ResultEntity();
		if (list != null) {
			entity.setResult(list);
			entity.setState(true);
		} else {
			entity.setState(false);
		}
		return entity;
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
		return containerViewService.start(req);
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
		return containerViewService.stop(req);
	}
}
