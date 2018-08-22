package com.ssitcloud.business.opermgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.opermgmt.service.OperatorGroupService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value={"operatorgroup"})
public class OperatorGroupController {
	
	@Resource
	private OperatorGroupService operatorGroupService;
	
	
	@RequestMapping(value={"queryOperGroupByparam"})
	@ResponseBody
	public ResultEntity queryOperGroupByparam(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=operatorGroupService.queryOperGroupByparam(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 操作员分组页面
	 * 增加新的分组
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"addOperGroup"})
	@ResponseBody
	public ResultEntity addOperGroup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=operatorGroupService.addOperGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 删除组
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"delOperGroup"})
	@ResponseBody
	public ResultEntity delOperGroup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=operatorGroupService.delOperGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 修改组
	 * @return
	 */
	@RequestMapping(value={"updOperGroup"})
	@ResponseBody
	public ResultEntity updOperGroup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=operatorGroupService.updOperGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查询所有的用户组信息，以及用户组对应的权限信息
	 *
	 * <p>2016年6月23日 下午7:19:45 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAllServiceGroup")
	@ResponseBody
	public ResultEntity queryAllServiceGroup(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			String req = request.getParameter("req");
			result = operatorGroupService.queryAllServiceGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	/**
	 * 查询图书馆的用户组信息，以及用户组对应的权限信息
	 *
	 * <p>2016年6月23日 下午7:19:45 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryLibraryServiceGroup")
	@ResponseBody
	public ResultEntity queryLibraryServiceGroup(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			String req = request.getParameter("req");
			result = operatorGroupService.queryLibraryServiceGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 根据用户idx查询其所在用户组信息
	 *
	 * <p>2016年6月28日 下午5:24:02 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryOperatorGroupByOperIdx")
	@ResponseBody
	public ResultEntity queryOperatorGroupByOperIdx(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			String req = request.getParameter("req");
			result = operatorGroupService.queryOperatorGroupByOperIdx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 更新用户的用户组
	 *
	 * <p>2016年7月14日 下午3:34:09 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateOperatorGroup")
	@ResponseBody
	public ResultEntity updateOperatorGroup(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		try {
			String req = request.getParameter("req");
			result = operatorGroupService.updateOperatorGroup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	
}
