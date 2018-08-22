package com.ssitcloud.business.statisticsmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.statisticsmgmt.service.StatisticsGroupService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value={"statisticsGroup"})
public class StatisticsGroupController {
	
	@Resource
	private StatisticsGroupService statisticsGroupService;
	
	/**
	 * 增加新的模板组
	 * author huanghuang
	 * 2017年2月20日 上午9:57:54
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"addStatisticsGroup"})
	@ResponseBody
	public ResultEntity addStatisticsGroup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("json");//开发时用来测试数据
			result=statisticsGroupService.addStatisticsGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 删除模板组
	 * author huanghuang
	 * 2017年2月20日 上午9:58:09
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"delStatisticsGroup"})
	@ResponseBody
	public ResultEntity delStatisticsGroup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("json");//开发时用来测试数据
			result=statisticsGroupService.delStatisticsGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 修改模板组
	 * author huanghuang
	 * 2017年2月20日 上午9:58:23
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"updStatisticsGroup"})
	@ResponseBody
	public ResultEntity updStatisticsGroup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("json");//开发时用来测试数据
			result=statisticsGroupService.updStatisticsGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查询单个模板组
	 * author huanghuang
	 * 2017年2月20日 上午9:58:35
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryOneStatisticsGroup")
	@ResponseBody
	public ResultEntity queryOneStatisticsGroup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("json");//开发时用来测试数据
			result = statisticsGroupService.queryOneStatisticsGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查询所有的模板组
	 * author huanghuang
	 * 2017年2月20日 上午9:58:35
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryStatisticsGroup")
	@ResponseBody
	public ResultEntity queryStatisticsGroup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("json");//开发时用来测试数据
			result = statisticsGroupService.queryStatisticsGroupByparam(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 分页查询所有的模板组
	 * author huanghuang
	 * 2017年2月20日 上午9:58:53
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selectStatisticsGroupByPage"})
	@ResponseBody
	public ResultEntity selectStatisticsGroupByPage(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("json");//开发时用来测试数据
			result=statisticsGroupService.queryStatisticsGroupPageByparam(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 新增
	 * author huanghuang
	 * 2017年2月20日 上午9:58:53
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"increaseStatisticsGroup"})
	@ResponseBody
	public ResultEntity increaseStatisticsGroup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			if(StringUtils.isBlank(req))
				req = request.getParameter("json");//开发时用来测试数据
			result=statisticsGroupService.increaseStatisticsGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 修改
	 * author huanghuang
	 * 2017年2月20日 上午9:58:53
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"modifyStatisticsGroup"})
	@ResponseBody
	public ResultEntity modifyStatisticsGroup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			if(StringUtils.isBlank(req))
				req = request.getParameter("json");//开发时用来测试数据
			result=statisticsGroupService.modifyStatisticsGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 删除
	 * author huanghuang
	 * 2017年2月20日 上午9:58:53
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"removeStatisticsGroup"})
	@ResponseBody
	public ResultEntity removeStatisticsGroup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			if(StringUtils.isBlank(req))
				req = request.getParameter("json");//开发时用来测试数据
			result=statisticsGroupService.removeStatisticsGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 查询
	 * author huanghuang
	 * 2017年2月20日 上午9:58:53
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"querysGroupByPageParam"})
	@ResponseBody
	public ResultEntity querysGroupByPageParam(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			if(StringUtils.isBlank(req))
				req = request.getParameter("json");//开发时用来测试数据
			result=statisticsGroupService.querysGroupByPageParam(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	
}
