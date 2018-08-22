package com.ssitcloud.dbstatistics.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbstatistics.common.utils.ExceptionHelper;
import com.ssitcloud.dbstatistics.service.MainPageService;

@Controller
@RequestMapping(value = { "mainPage" })
public class MainPageController{
	@Resource
	private MainPageService mainPageService;
	
	/**
	 * 统计办证信息
	 */
	@RequestMapping("/countCardissueLog")
    @ResponseBody
	public ResultEntity countCardissueLog(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
        try {
        	
            result = mainPageService.countCardissueLog(req);
        } catch (Exception e) {
            ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
        return result;
	}

	/**
	 * 统计借还信息
	 * @return
	 */
	@RequestMapping("/countLoanLog")
    @ResponseBody
	public ResultEntity countLoanLog(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
        try {
            result = mainPageService.countLoanLog(req);
        } catch (Exception e) {
            ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
        return result;
	}

	/**
	 * 统计财经信息
	 * @return
	 */
	@RequestMapping("/countFinanceLog")
    @ResponseBody
	public ResultEntity countFinanceLog(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
        try {
            result = mainPageService.countFinanceLog(req);
        } catch (Exception e) {
            ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
        return result;
	}
	
	/**
	 * 人流量信息
	 * @return
	 */
	@RequestMapping("/countVisitorLog")
    @ResponseBody
	public ResultEntity countVisitorLog(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
        try {
            result = mainPageService.countVisitorLog(req);
        } catch (Exception e) {
            ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }
        return result;
	}
}
