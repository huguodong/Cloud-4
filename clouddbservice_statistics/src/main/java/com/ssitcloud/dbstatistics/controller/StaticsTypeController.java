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
import com.ssitcloud.dbstatistics.entity.CirculationDayDataEntity;
import com.ssitcloud.dbstatistics.entity.StaticsTypeEntity;
import com.ssitcloud.dbstatistics.service.StaticsTypeService;

@Controller
@RequestMapping("/stattype")
public class StaticsTypeController {
	@Resource
	private StaticsTypeService staticsTypeService;
	
	/**
	 * 查询多条记录
	 *
	 * <p>2017年3月1日 
	 * <p>create by lqw
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryStaticsTypeList")
	@ResponseBody
	public ResultEntity queryStaticsTypeList(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
				String req = request.getParameter("req");
				
				StaticsTypeEntity staticsTypeEntity = JsonUtils.fromJson(req, StaticsTypeEntity.class);
				if(staticsTypeEntity == null){
					staticsTypeEntity = new StaticsTypeEntity();
				}
				List<StaticsTypeEntity> list = staticsTypeService.queryStaticsTypeList(staticsTypeEntity);
				resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

}
