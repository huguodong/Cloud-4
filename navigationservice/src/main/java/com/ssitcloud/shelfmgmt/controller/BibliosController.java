package com.ssitcloud.shelfmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.shelfmgmt.service.BibliosService;

@Controller
@RequestMapping("/biblios")
public class BibliosController {
	@Resource
	BibliosService bibliosService;
	
	@RequestMapping(value={"importMarcData"})
	@ResponseBody
	public ResultEntity importMarcData(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = bibliosService.importMarcData(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value={"queryMarcDataByItemCtrlNum"})
	@ResponseBody
	public ResultEntity queryMarcDataByItemCtrlNum(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = bibliosService.queryMarcDataByItemCtrlNum(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
