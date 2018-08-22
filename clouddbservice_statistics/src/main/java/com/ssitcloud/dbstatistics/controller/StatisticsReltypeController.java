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
import com.ssitcloud.dbstatistics.entity.StatisticsReltypeEntity;
import com.ssitcloud.dbstatistics.service.StatisticsReltypeService;

@Controller
@RequestMapping("/statisticsReltype")
public class StatisticsReltypeController {
	@Resource
	private StatisticsReltypeService statisticsReltypeService;
	
	/**
	 * 查询一条记录
	 * 
	 * author lqw
	 * 2017年4月6日 
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/queryReltype"})
	@ResponseBody
	public ResultEntity queryReltype (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				StatisticsReltypeEntity statisticsReltypeEntity = JsonUtils.fromJson(json, StatisticsReltypeEntity.class);
				StatisticsReltypeEntity statisticsReltype = statisticsReltypeService.queryReltype(statisticsReltypeEntity);
				resultEntity.setValue(true, "success","",statisticsReltype);
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
	 *  author lqw
	 * 2017年4月6日 
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryReltypeList")
	@ResponseBody
	public ResultEntity queryReltypeList(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			StatisticsReltypeEntity statisticsReltypeEntity = new StatisticsReltypeEntity();
			List<StatisticsReltypeEntity> list = statisticsReltypeService.queryReltypeList(statisticsReltypeEntity);
			resultEntity.setValue(true, "success", "", list);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

}
