package com.ssitcloud.view.statistics.mainfieldmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;




import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.statistics.common.util.ExceptionHelper;
import com.ssitcloud.view.statistics.mainfieldmgmt.service.MainFieldService;

/**
 * 业务层 信息主字段表 MainFieldController
 * 2017年2月23日下午4:34
 * @author shuangjunjie
 *
 */
@Controller
@RequestMapping("/mainField")
public class MainFieldController {

	@Resource
	private MainFieldService mainFieldService;
	
	/**
	 * 新增 信息主字段表
	 * json = {
	 * 		"mainfield_idx":"",
	 * 		"mainfield_table":"表名",
	 * 		"mainfield_field":"字段",
	 * 		"mainfield_field_desc":"字段说明"
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = {"addMainField"})
	@ResponseBody
	public ResultEntity insertMainField(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			result = mainFieldService.insertMainField(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		
		return result;
	}
	
	/**
	 * 修改 信息主字段表
	 * json = {
	 * 		"mainfield_idx":"",
	 * 		"mainfield_table":"表名",
	 * 		"mainfield_field":"字段",
	 * 		"mainfield_field_desc":"字段说明"
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = {"updateMainField"})
	@ResponseBody
	public ResultEntity updateMainField(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			result = mainFieldService.updateMainField(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		
		return result;
	}
	
	/**
	 * 删除 信息主字段表
	 * json = {
	 * 		"mainfield_idx":"",
	 * 		"mainfield_table":"表名",
	 * 		"mainfield_field":"字段",
	 * 		"mainfield_field_desc":"字段说明"
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = {"deleteMainField"})
	@ResponseBody
	public ResultEntity deleteMainField(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			result = mainFieldService.deleteMainField(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		
		return result;
		
	}
	
	/**
	 * 查询 信息主字段表
	 * json = {
	 * 		"mainfield_idx":"",
	 * 		"mainfield_table":"表名",
	 * 		"mainfield_field":"字段",
	 * 		"mainfield_field_desc":"字段说明"
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = {"selectMainField"})
	@ResponseBody
	public ResultEntity selectMainField(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			result = mainFieldService.selectMainField(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 信息主字段表 分页查询
	 * json = {
	 * 		"mainfield_idx":"",
	 * 		"mainfield_table":"表名",
	 * 		"mainfield_field":"字段",
	 * 		"mainfield_field_desc":"字段说明"
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/selectMainFieldByPage")
	@ResponseBody
	public ResultEntity selectMainFieldByPage(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			result = mainFieldService.selectMainFieldByPage(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 查询全部
	 * <p>2017年4月14日
	 * <p>create by lqw
	 * @param mainFieldEntity
	 * @return
	 */
	@RequestMapping("/selectMainFieldList")
	@ResponseBody
	public ResultEntity selectMainFieldList(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		try {
			result = mainFieldService.selectMainFieldList(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
}
