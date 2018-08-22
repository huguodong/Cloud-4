package com.ssitcloud.view.opermgmt.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.opermgmt.service.StatisticsGroupTemplateService;


@Controller
@RequestMapping(value={"/statisticsGroupTemplate"})
public class StatisticsGroupTemplateController {
	@Resource
	private StatisticsGroupTemplateService statisticsGroupTemplate;
	
	/**
	 * 查询一条记录StatisticsGroupTemplateEntity 
	 * lqw 2017年3月10日
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "findByIdStatisticsGroupTemplate" })
	@ResponseBody
	public ResultEntity findByIdStatisticsGroupTemplate(
			HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsGroupTemplate.findById(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

	/**
	 * 查询多条记录StatisticsGroupTemplateEntity 
	 * lqw 2017年3月10日
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/findAllStatisticsGroupTemplates" })
	@ResponseBody
	public ResultEntity findAllStatisticsGroupTemplates(
			HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = statisticsGroupTemplate.findAll(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}


}
