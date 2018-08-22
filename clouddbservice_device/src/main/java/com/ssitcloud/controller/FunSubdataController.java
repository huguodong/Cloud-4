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
import com.ssitcloud.entity.FunSubdataEntity;
import com.ssitcloud.service.FunSubdataService;

@Controller
@RequestMapping(value={"funSubdata"})
public class FunSubdataController {
	@Resource
	private FunSubdataService funSubdataService;
	
	/**
	 * 查询一条主函数记录FunSubdataEntity
	 * 格式
	 * json = {
	 * 		"main_idx":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectFunSubdata"})
	@ResponseBody
	public ResultEntity selectFunSubdata (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FunSubdataEntity funSubdataEntity = JsonUtils.fromJson(json, FunSubdataEntity.class);
				funSubdataEntity = funSubdataService.queryOneFunSubdata(funSubdataEntity);
				resultEntity.setValue(true, "success","",funSubdataEntity);
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
	 * 查询多条主函数记录FunSubdataEntity
	 * 格式
	 * json = {
	 * 		"main_idx":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectFunSubdatas"})
	@ResponseBody
	public ResultEntity selectFunSubdatas (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			FunSubdataEntity funSubdataEntity = JsonUtils.fromJson(json, FunSubdataEntity.class);
			List<FunSubdataEntity> ls = funSubdataService.queryFunSubdatas(funSubdataEntity);
			resultEntity.setValue(true, "success","",ls);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
}
