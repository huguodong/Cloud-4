package com.ssitcloud.view.node.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.node.entity.HostEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.node.entity.page.HostPageEntity;
import com.ssitcloud.view.node.service.HostViewService;

@Controller
@RequestMapping(value = { "host" })
public class HostViewController extends BasicController {

	@Resource
	private HostViewService hostViewService;

	/** 分页查询 */
	@RequestMapping(value = { "queryByPage" })
	@ResponseBody
	public ModelAndView queryHostByPage(HttpServletRequest request, String req) {
		// return hostViewService.queryHostByPage(req);
		Map<String, Object> model = new HashMap<>();
		model.put("oper", getCurrentUser());
		return new ModelAndView("/page/nodemgmt/host-manage", model);
	}

	/** 条件查询 */
	@RequestMapping(value = { "queryByParam" })
	@ResponseBody
	public ResultEntity queryHostByParam(HttpServletRequest request, String req) {
		HostPageEntity entity = hostViewService.queryHostByParam(req);
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
	public ResultEntity queryHostById(HttpServletRequest request, String req) {
		HostEntity entity = hostViewService.queryHostById(req);
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
	public ResultEntity deleteHostById(HttpServletRequest request, String req) {
		return hostViewService.deleteHostById(req);
	}

	/** 更新 **/
	@RequestMapping(value = { "update" })
	@ResponseBody
	public ResultEntity updateHost(HttpServletRequest request, String req) {
		return hostViewService.updateHost(req);
	}

	/** 新增 **/
	@RequestMapping(value = { "add" })
	@ResponseBody
	public ResultEntity addHost(HttpServletRequest request, String req) {
		return hostViewService.addHost(req);
	}
}
