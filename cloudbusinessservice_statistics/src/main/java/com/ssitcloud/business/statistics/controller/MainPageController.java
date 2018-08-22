package com.ssitcloud.business.statistics.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.statistics.common.utils.ExceptionHelper;
import com.ssitcloud.business.statistics.service.DeviceMonitorService;
import com.ssitcloud.business.statistics.service.MainPageService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value = { "mainPage" })
public class MainPageController{
	@Resource
	private MainPageService mainPageService;
	@Resource
	private DeviceMonitorService deviceMonitorService;
	
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
	
	
	/**
	 * 统计ElasticSearch数据，区分办证、借还，只提供给设备监控使用
	 * @return
	 */
	@RequestMapping("/countDatas")
	@ResponseBody
	public ResultEntity countDatas(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = deviceMonitorService.countDatas(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
}
