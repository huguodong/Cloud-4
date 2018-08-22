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
import com.ssitcloud.entity.FunMaindataEntity;
import com.ssitcloud.service.FunMaindataService;

@Controller
@RequestMapping(value={"funMaindata"})
public class FunMaindataController {
	@Resource
	private FunMaindataService funMaindataService;
	
	/**
	 * 查询一条主函数记录FunMaindataEntity
	 * 格式
	 * json = {
	 * 		"main_idx":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectFunMaindata"})
	@ResponseBody
	public ResultEntity selectFunMaindata (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FunMaindataEntity funMaindataEntity = JsonUtils.fromJson(json, FunMaindataEntity.class);
				funMaindataEntity = funMaindataService.queryOneFunMaindata(funMaindataEntity);
				resultEntity.setValue(true, "success","",funMaindataEntity);
			}else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询多条主函数记录FunMaindataEntity
	 * 格式
	 * json = {
	 * 		"main_idx":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectFunMaindatas"})
	@ResponseBody
	public ResultEntity selectFunMaindatas (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			FunMaindataEntity funMaindataEntity = JsonUtils.fromJson(json, FunMaindataEntity.class);
			List<FunMaindataEntity> ls = funMaindataService.queryFunMaindatas(funMaindataEntity);
			resultEntity.setValue(true, "success","",ls);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
}
