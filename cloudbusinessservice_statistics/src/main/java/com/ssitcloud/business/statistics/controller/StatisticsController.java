package com.ssitcloud.business.statistics.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.statistics.common.utils.ExceptionHelper;
import com.ssitcloud.business.statistics.service.StatisticsService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value = { "statistics" })
public class StatisticsController {
	@Resource
	private StatisticsService statisticsService;

	@RequestMapping(value = { "selectStaticsType" })
	@ResponseBody
	public ResultEntity selectStaticsType(HttpServletRequest request,
			String req) {
		ResultEntity result = new ResultEntity();
		try {
			req = request.getParameter("req");
			result = statisticsService.selectStaticsType(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value = { "queryStatisticsMaintypeList" })
	@ResponseBody
	public ResultEntity queryStatisticsMaintypeList(HttpServletRequest request,
			String req) {
		ResultEntity result = new ResultEntity();
		try {
			req = request.getParameter("req");
			result = statisticsService.queryStatisticsMaintypeList(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value = { "queryReltype" })
	@ResponseBody
	public ResultEntity queryReltype(HttpServletRequest request,
			String req) {
		ResultEntity result = new ResultEntity();
		try {
			req = request.getParameter("req");
			result = statisticsService.queryReltype(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value = { "queryReltypeList" })
	@ResponseBody
	public ResultEntity queryReltypeList(HttpServletRequest request,
			String req) {
		ResultEntity result = new ResultEntity();
		try {
			req = request.getParameter("req");
			result = statisticsService.queryReltypeList(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
