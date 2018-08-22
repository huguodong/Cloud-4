package com.ssitcloud.view.statistics.persettingmgmt.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.statistics.common.controller.BasicController;
import com.ssitcloud.view.statistics.common.util.ExceptionHelper;
import com.ssitcloud.view.statistics.common.util.SystemLogUtil;
import com.ssitcloud.view.statistics.persettingmgmt.service.PersonalSettingService;



/**
 * 业务层 个人菜单设置  PersonalSettingController
 * 2017年2月23日 上午11:45
 * @author shuangjunjie
 *
 */
@Controller
@RequestMapping("/personalSetting")
public class PersonalSettingController  extends BasicController{

	@Resource
	private PersonalSettingService personalSettingService;
	
	
	/**
	 * 跳转到页面
	 */
	@RequestMapping("/main")
	public String gotSoxTempMain(){
		return "/page/personalcenter/usertemplate";
	}
	
	/**
	 * 新增个人菜单设置
	 *
	 * <p>2017年2月23日 下午2:02 
	 * <p>create by shuangjunjie
	 * json={
	 * 		"setting_idx":"",
	 * 		"operator_idx":"",
	 * 		"service_idx":"",
	 * 		"setting_url":"",
	 * 		"setting_desc":""
	 * }
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPersonalSetting")
	@ResponseBody
	public ResultEntity addPersonalSetting(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			req = request.getParameter("req");
			result = personalSettingService.insertPersonalSetting(req);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.PERMESSION_ADD_PERSETTING);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 根据idx删除个人菜单设置
	 *
	 * <p>2017年2月10日 下午3:50:39 
	 * <p>create by shuangjunjie
	 *json={
	 * 		"setting_idx":"",
	 * 		"operator_idx":"",
	 * 		"service_idx":"",
	 * 		"setting_url":"",
	 * 		"setting_desc":""
	 * }
	 * @param request
	 * @return
	 */
	@RequestMapping("/delPersonalSetting")
	@ResponseBody
	public ResultEntity delPersonalSetting(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			req = request.getParameter("req");
			result = personalSettingService.deletePersonalSetting(req);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.PERMESSION_DEL_PERSETTING);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 根据idx更新个人菜单设置
	 *
	 * <p>2017年2月10日 下午3:50:39 
	 * <p>create by shuangjunjie
	 * json={
	 * 		"setting_idx":"",
	 * 		"operator_idx":"",
	 * 		"service_idx":"",
	 * 		"setting_url":"",
	 * 		"setting_desc":""
	 * }
	 * @param request
	 * @return
	 */
	@RequestMapping("/updPersonalSetting")
	@ResponseBody
	public ResultEntity updPersonalSetting(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			req = request.getParameter("req");
			result = personalSettingService.updatePersonalSetting(req);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.PERMESSION_UPD_PERSETTING);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	/**
	 * 根据IDX查询单条个人菜单设置
	 *
	 * <p>2017年2月10日 下午3:50:39 
	 * <p>create by shuangjunjie
	 * json={
	 * 		"setting_idx":"",
	 * 		"operator_idx":"",
	 * 		"service_idx":"",
	 * 		"setting_url":"",
	 * 		"setting_desc":""
	 * }
	 * @param request
	 * @return
	 */
	@RequestMapping("/selPersonalSettingByIdx")
	@ResponseBody
	public ResultEntity selPersonalSettingByIdx(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			req = request.getParameter("req");
			result = personalSettingService.selectPersonalSetting(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 分页查询 个人菜单设置
	 * <p>2017年2月23日
	 * <p>create by shuangjunjie
	 * json={
	 * 		"setting_idx":"",
	 * 		"operator_idx":"",
	 * 		"service_idx":"",
	 * 		"setting_url":"",
	 * 		"setting_desc":""
	 * }
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/selectPersonalSettingByPage")
	@ResponseBody
	public ResultEntity selectPersonalSettingByPage(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			req = request.getParameter("req");
			result = personalSettingService.selectPersonalSettingByPage(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 获取当前操作员信息
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/getoper")
	@ResponseBody
	public Operator getOper(HttpServletRequest request){
		Operator oper=getCurrentUser();
		return oper;
	}
	
	/**
	 * 个人功能配置 查询模板
	 * 2017年3月27日
	 * author lqw
	 * @param req
	 * @return
	 */
	@RequestMapping("/selectStatisticsTemplates")
	@ResponseBody
	public ResultEntity selectStatisticsTemplates(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			String req = request.getParameter("req");
			result = personalSettingService.selectStatisticsTemplates(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 根据oper_idx查询操作员
	 * 2017年4月5日
	 * author lqw
	 * @param req
	 * @return
	 */
	@RequestMapping("/selOperatorByOperIdOrIdx")
	@ResponseBody
	public ResultEntity selOperatorByOperIdOrIdx(HttpServletRequest request){
		ResultEntity result = new ResultEntity();
		try {
			String req = request.getParameter("req");
			result = personalSettingService.selOperatorByOperIdOrIdx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
