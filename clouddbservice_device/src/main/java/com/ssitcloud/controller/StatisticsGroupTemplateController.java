package com.ssitcloud.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.StatisticsGroupTemplateEntity;
import com.ssitcloud.service.StatisticsGroupTemplateService;

@Controller
@RequestMapping(value = { "/statisticsGroupTemplate" })
public class StatisticsGroupTemplateController {

	@Resource
	private StatisticsGroupTemplateService statisticsGroupTemplateService;

	/**
	 * 查询一条记录StatisticsGroupTemplateEntity 格式 json = {
	 * "statistics_tpl_idx"//模板ID "statistics_tpl_desc"//模板名
	 * "statistics_group_idx"// 模板组IDX "statistics_group_id"// 模板组ID
	 * "statistics_group_name"// 模板组名 "statistics_group_desc"// 模板组描述 } author
	 * lqw 2017年3月10日
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/selectStatisticsGroupTemplate" })
	@ResponseBody
	public ResultEntity selectStatisticsGroupTemplate(
			HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				StatisticsGroupTemplateEntity sgte = JsonUtils.fromJson(json,
						StatisticsGroupTemplateEntity.class);
				List<StatisticsGroupTemplateEntity> list = statisticsGroupTemplateService.findById(sgte);
				resultEntity.setValue(true, "success", "", list);
			} else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

	/**
	 * 查询多条记录StatisticsGroupTemplateEntity 格式 json = {
	 * "statistics_tpl_idx"//模板ID "statistics_tpl_desc"//模板名
	 * "statistics_group_idx"// 模板组IDX "statistics_group_id"// 模板组ID
	 * "statistics_group_name"// 模板组名 "statistics_group_desc"// 模板组描述 } author
	 * lqw 2017年3月10日
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/selectStatisticsGroupTemplates" })
	@ResponseBody
	public ResultEntity selectStatisticsGroupTemplates(
			HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			StatisticsGroupTemplateEntity sgte = JsonUtils.fromJson(json,
					StatisticsGroupTemplateEntity.class);
			List<StatisticsGroupTemplateEntity> list = statisticsGroupTemplateService
					.findAll(sgte);
			resultEntity.setValue(true, "success", "", list);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

}
