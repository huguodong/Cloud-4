package com.ssitcloud.operlog.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.operlog.service.FinanceLogService;

@Controller
@RequestMapping("/financelog")
public class FinanceLogControler {
	@Resource
	private FinanceLogService financeLogService;
	
	@RequestMapping("/selectFinancelog")
	@ResponseBody
	public ResultEntity selectLoanlogYear(HttpServletRequest request,String req){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isNotBlank(req)) {
				resultEntity.setValue(true, "success","",financeLogService.countFinance(req));
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
	 * 
	 *
	 * <p>2017年5月15日 
	 * <p>create by lqw
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAllFinanceLogFromMongodb")
	@ResponseBody
	public ResultEntity queryAllFinanceLogFromMongodb(String req, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity = financeLogService.queryAllFinanceLogFromMongodb(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
}
