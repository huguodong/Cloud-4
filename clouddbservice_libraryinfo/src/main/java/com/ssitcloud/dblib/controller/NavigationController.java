package com.ssitcloud.dblib.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.ExceptionHelper;
import com.ssitcloud.dblib.service.BookItemService;
import com.ssitcloud.dblib.service.NavigationService;

@Controller
@RequestMapping("/navigation")
public class NavigationController {
	@Resource
	private NavigationService navigationService;

	/**
	 * 查询图书馆馆藏数量
	 *
	 * <p>2017年10月23日 下午5:55:35 
	 * <p>create by liuwei
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryBookItemTotal")
	@ResponseBody
	public ResultEntity queryBookItemTotal(HttpServletRequest request,String req){
		ResultEntity resultEntity = new ResultEntity();
		try {
				resultEntity = navigationService.queryBookItemTotal(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询图书馆层架标数量
	 *
	 * <p>2017年10月23日 下午5:55:35 
	 * <p>create by liuwei
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryBookShelfLayerTotal")
	@ResponseBody
	public ResultEntity queryBookShelfLayerTotal(HttpServletRequest request,String req){
		ResultEntity resultEntity = new ResultEntity();
		try {
			 	resultEntity = navigationService.queryBookShelfLayerTotal(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	

	/**
	 * 查询图书馆层架数量
	 *
	 * <p>2017年10月23日 下午5:55:35 
	 * <p>create by liuwei
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryBookShelfTotal")
	@ResponseBody
	public ResultEntity queryBookShelfTotal(HttpServletRequest request,String req){
		ResultEntity resultEntity = new ResultEntity();
		try {
				resultEntity = navigationService.queryBookShelfTotal(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
}
