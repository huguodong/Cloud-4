package com.ssitcloud.view.statistics.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.statistics.common.controller.BasicController;
import com.ssitcloud.view.statistics.common.util.ExceptionHelper;
import com.ssitcloud.view.statistics.common.util.SystemLogUtil;
import com.ssitcloud.view.statistics.service.StatisticsGroupService;
import com.ssitcloud.view.statistics.service.StatisticsTemplateService;

@Controller
@RequestMapping(value = { "statisticsGroup" })
public class StatisticsGroupController extends BasicController{
	@Resource
	private StatisticsTemplateService statisticsTemplateService;
	@Resource
	private StatisticsGroupService statisticsGroupService;
	@RequestMapping(value={"main"})
	public ModelAndView main(HttpServletRequest request){
		Map<String,Object> model=new HashMap<>();
		return new ModelAndView("/page/statisticstemplate/template-group", model);
	}
	
	@RequestMapping(value={"selectStatisticsTemplates"})
	@ResponseBody
	public ResultEntity selectStatisticsTemplates (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			JSONObject reqJson = new JSONObject();
			reqJson.put("daFlag", false);
			resultEntity = statisticsTemplateService.selectStatisticsTemplatePage(reqJson.toString());
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 新增
	 * author huanghuang
	 * 2017年2月20日 上午9:58:53
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"addTemplateGroup"})
	@ResponseBody
	public ResultEntity addTemplateGroup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			req = request.getParameter("req");//开发时用来测试数据
			result=statisticsGroupService.increaseStatisticsGroup(req);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.PERMESSION_ADD_STATGROUP);
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
			req = request.getParameter("req");//开发时用来测试数据
			result=statisticsGroupService.modifyStatisticsGroup(req);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.PERMESSION_UPD_STATGROUP);
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
			req = request.getParameter("req");//开发时用来测试数据
			result=statisticsGroupService.removeStatisticsGroup(req);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.PERMESSION_DEL_STATGROUP);
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
			req = request.getParameter("req");//开发时用来测试数据
			result=statisticsGroupService.querysGroupByPageParam(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
