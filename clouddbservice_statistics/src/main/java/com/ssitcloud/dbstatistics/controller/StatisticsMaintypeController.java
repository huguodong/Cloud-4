package com.ssitcloud.dbstatistics.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbstatistics.common.utils.ExceptionHelper;
import com.ssitcloud.dbstatistics.common.utils.JsonUtils;
import com.ssitcloud.dbstatistics.entity.StatisticsMaintypeEntity;
import com.ssitcloud.dbstatistics.service.StatisticsMaintypeService;

@Controller
@RequestMapping("/statisticsMaintype")
public class StatisticsMaintypeController {
	@Resource
	private StatisticsMaintypeService statisticsMaintypeService;
	
	/**
	 * 查询一条记录
	 * 格式
	 * json={
	 * 	maintype_idx:""
	 * }
	 * author huanghuang
	 * 2017年3月20日 下午1:56:14
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/queryStatisticsMaintype"})
	@ResponseBody
	public ResultEntity queryStatisticsMaintype (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				StatisticsMaintypeEntity statisticsMaintypeEntity = JsonUtils.fromJson(json, StatisticsMaintypeEntity.class);
				StatisticsMaintypeEntity statisticsMaintype = statisticsMaintypeService.queryStatisticsMaintype(statisticsMaintypeEntity);
				resultEntity.setValue(true, "success","",statisticsMaintype);
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

	/**
	 * 查询多条记录
	 * 格式
	 * json={
	 * 	type_value:""
	 * }
	 * author huanghuang
	 * 2017年3月20日 下午1:54:03
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryStatisticsMaintypeList")
	@ResponseBody
	public ResultEntity queryStatisticsMaintypeList(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			StatisticsMaintypeEntity statisticsMaintypeEntity = new StatisticsMaintypeEntity();
			List<StatisticsMaintypeEntity> list = statisticsMaintypeService
					.queryStatisticsMaintypeList(statisticsMaintypeEntity);
			resultEntity.setValue(true, "success", "", list);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

}
