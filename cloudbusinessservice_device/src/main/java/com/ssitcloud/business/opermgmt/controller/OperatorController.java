package com.ssitcloud.business.opermgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.opermgmt.service.OperatorService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value={"/operator"})
public class OperatorController {
	
	@Resource
	private OperatorService operatorService;
	
	
	/**
	 * 分页查询操作员信息
	 *
	 * <p>2016年6月8日 上午9:44:53 
	 * <p>create by hjc
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"/queryOperatorByParam"})
	@ResponseBody
	public ResultEntity queryOperatorByParam(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=operatorService.queryOperatorByParam(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 新增操作员信息
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"/addOperator"})
	@ResponseBody
	public ResultEntity addOperator(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=operatorService.addOperator(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 删除操作员信息（设置成失效）
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"/delOperator"})
	@ResponseBody
	public ResultEntity delOperator(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			String req = request.getParameter("req");
			result=operatorService.delOperator(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 删除多个操作员信息（设置成失效）
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"/delMultiOperator"})
	@ResponseBody
	public ResultEntity delMultiOperator(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			String req = request.getParameter("req");
			result=operatorService.delMultiOperator(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 更新操作员信息
	 * @return
	 */
	@RequestMapping(value={"/updOperator"})
	@ResponseBody
	public ResultEntity updOperator(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=operatorService.updOperator(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 获取操作员详细信息
	 * 
	 * <p>2016年6月13日 上午11:04:06 
	 * <p>create by hjc
	 * @param request
	 * @param req {"library_idx":"1","operatorType":"1","operator_idx":"1","idx":"1"}
	 * @return
	 */
	@RequestMapping(value={"/queryOperatorDetailByIdx"})
	@ResponseBody
	public ResultEntity queryOperatorDetailByIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			//查询操作员的
			//根据要查询的操作员idx查询鉴权表中的数据
			result = operatorService.queryOperatorDetailByIdx(req);
			//根据要查询的操作员idx查询设备表中的权限信息
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查询所有的用户模板
	 *
	 * <p>2016年6月13日 下午7:15:21 
	 * <p>create by hjc
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"/queryAllSoxTemp"})
	@ResponseBody
	public ResultEntity queryAllSoxTemp(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result = operatorService.queryAllSoxTemp(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 根据登录用户的信息获取可以显示的操作员类型信息
	 *
	 * <p>2016年7月7日 下午1:35:38 
	 * <p>create by hjc
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"/queryOperatorTypes"})
	@ResponseBody
	public ResultEntity queryOperatorTypes(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result = operatorService.queryOperatorTypes(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 更新操作员信息
	 *
	 * <p>2016年7月5日 下午6:09:02 
	 * <p>create by hjc
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"/updateOperator"})
	@ResponseBody
	public ResultEntity updateOperator(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result = operatorService.updateOperator(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 获取所有的用户信息参数，以及可以新增的信息
	 *
	 * <p>2016年7月9日 上午11:24:20 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAllOperatorInfo")
	@ResponseBody
	public ResultEntity queryAllOperatorInfo(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			resultEntity = operatorService.queryAllOperatorInfo(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	/**
	 * 重置用户密码为888888
	 *
	 * <p>2016年7月14日 下午7:32:12 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/resetPassword")
	@ResponseBody
	public ResultEntity resetPassword(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if(StringUtils.isBlank(req) || req.equals("{}")){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = operatorService.resetPassword(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	/**
	 * 用户修改密码接口
	 *
	 * <p>2016年7月28日 下午4:03:22 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/changePassword")
	@ResponseBody
	public ResultEntity changePassword(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if(StringUtils.isBlank(req) || req.equals("{}")){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = operatorService.changePassword(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
	
	/**
	 * 检查密码格式
	 *
	 * <p>2016年12月20日 下午1:36:25 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkPwdFormat")
	@ResponseBody
	public ResultEntity checkPwdFormat(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String req = request.getParameter("req");
			if(StringUtils.isBlank(req) || req.equals("{}")){
				resultEntity.setState(false);
				resultEntity.setMessage("参数不能为空");
				return resultEntity;
			}
			resultEntity = operatorService.checkPwdFormat(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}
}
