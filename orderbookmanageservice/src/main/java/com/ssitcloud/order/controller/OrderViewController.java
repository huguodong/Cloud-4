package com.ssitcloud.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssitcloud.common.controller.BasicController;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.order.entity.OrderEntity;
import com.ssitcloud.order.entity.page.OrderPageEntity;
import com.ssitcloud.order.service.OrderViewService;

@Controller
@RequestMapping(value = { "order" })
public class OrderViewController extends BasicController {
	@Resource
	private OrderViewService orderViewService;

	/** 分页查询 */
	@RequestMapping(value = { "queryByPage" })
	@ResponseBody
	public ModelAndView queryOrderByPage(HttpServletRequest request, String req) {
		Map<String, Object> model = new HashMap<>();
		model.put("oper", getCurrentUser());
		return new ModelAndView("/page/ordermgmt/order-manage", model);
	}

	/** 根据条件查询 */
	@RequestMapping(value = { "queryByParam" })
	@ResponseBody
	public ResultEntity queryOrderByParam(HttpServletRequest request, String req) {
		OrderPageEntity entity = orderViewService.queryOrderByParam(req);
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
	public ResultEntity queryOrderById(HttpServletRequest request, String req) {
		OrderEntity entity = orderViewService.queryOrderById(req);
		ResultEntity result = new ResultEntity();
		if (entity != null) {
			result.setValue(true, "success", "", entity);
		} else {
			result.setState(false);
		}
		return result;
	}

	/** 更新 */
	@RequestMapping(value = { "update" })
	@ResponseBody
	public ResultEntity updateOrder(HttpServletRequest request, String req) {
		return orderViewService.updateOrder(req);
	}

	/** 新增 */
	@RequestMapping(value = { "add" })
	@ResponseBody
	public ResultEntity addOrder(HttpServletRequest request, String req) {
		return orderViewService.addOrder(req);
	}
	
	/**
	 * 批量返回图书架位信息
	 * **/
	@RequestMapping(value = { "getLocations" })
	@ResponseBody
	public ResultEntity getLocations(HttpServletRequest request, String req) {
		return orderViewService.getLocations(req);
	}
	
	/** 提供对外接口   
	 *  step1:插入预借信息
	 *  step2:操作开打密集书柜
	 *  step3:返回图书位置
	 * **/
	@RequestMapping(value = { "controlShelf" })
	@ResponseBody
	public ResultEntity controlShelf(HttpServletRequest request, String req) {
		return orderViewService.controlShelf(req);
	}
	
	/** 密集书架复位  */
	@RequestMapping(value = { "shelfReset" })
	@ResponseBody
	public ResultEntity shelfReset(HttpServletRequest request, String req) {
		return orderViewService.shelfReset(req);
	}
}
