package com.ssitcloud.operlog.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.operlog.service.CardissueLogService;
import com.ssitcloud.operlog.service.FinanceLogService;

@Controller
@RequestMapping("/cardissuelog")
public class CardissueLogControler {
	@Resource
	private CardissueLogService cardissueLogService;
	@RequestMapping("/selectCardissuelog")
	@ResponseBody
	public ResultEntity selectLoanlogYear(HttpServletRequest request,String req){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isNotBlank(req)) {
				resultEntity.setValue(true, "success","",cardissueLogService.countCardissue(req));
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
	 * <p>2017年5月12日 
	 * <p>create by lqw
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAllCardiLogFromMongodb")
	@ResponseBody
	public ResultEntity queryAllCardiLogFromMongodb(String req, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity = cardissueLogService.queryAllCardiLogFromMongodb(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
}
