package com.ssitcloud.view.emailmgmt.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.SystemLogUtil;
import com.ssitcloud.view.emailmgmt.service.EmailParamService;

/**
 * 邮件提醒 业务层EmailParamController
 * 
 * @date 2017年2月21日
 * @author shuangjunjie
 * 
 */

@Controller
@RequestMapping("/emailParam")
public class EmailParamController extends BasicController{

	@Resource
	EmailParamService emailParamService;
	
	
	/**
	 * 页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"main"})
	public String main(HttpServletRequest request){
		return "/page/emailmgmt/emailadmin";
	}

	/**
	 * 新增邮件 author shuangjunjie json = { "email_idx": "27", "lib_id": "LIB001",
	 * "email_smtp": "13333", "email_port": "1", "email_account": "1",
	 * "email_password": "1", "email_code": "1", "email_use_flg": "1" }
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "insertEmailParam" })
	@ResponseBody
	public ResultEntity insertEmailParam(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = emailParamService.insertEmailParam(req); // 插入一条邮件记录
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.PERMESSION_ADD_EMAIL);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

	/**
	 * 修改邮件 json = { "email_idx": "27", "lib_id": "LIB001", "email_smtp":
	 * "13333", "email_port": "1", "email_account": "1", "email_password": "1",
	 * "email_code": "1", "email_use_flg": "1" } author shuangjunjie
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "updateEmailParam" })
	@ResponseBody
	public ResultEntity updateEmailParam(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = emailParamService.updateEmailParam(req);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.PERMESSION_UPD_EMAIL);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

	/**
	 * 删除邮件 json = { "email_idx": "27", "lib_id": "LIB001", "email_smtp":
	 * "13333", "email_port": "1", "email_account": "1", "email_password": "1",
	 * "email_code": "1", "email_use_flg": "1" } author shuangjunjie
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "deleteEmailParam" })
	@ResponseBody
	public ResultEntity deleteEmailParam(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = emailParamService.deleteEmailParam(req);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.PERMESSION_DEL_EMAIL);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

	/**
	 * 查询一条邮件 json = { "email_idx": "27", "lib_id": "LIB001", "email_smtp":
	 * "13333", "email_port": "1", "email_account": "1", "email_password": "1",
	 * "email_code": "1", "email_use_flg": "1" } author shuangjunjie
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "selectEmailParam" })
	@ResponseBody
	public ResultEntity selectEmailParam(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = emailParamService.selectEmailParam(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

	/**
	 * 查询多条邮件 json = { "email_idx": "27", "lib_id": "LIB001", "email_smtp":
	 * "13333", "email_port": "1", "email_account": "1", "email_password": "1",
	 * "email_code": "1", "email_use_flg": "1" } author shuangjunjie
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "selectEmailParams" })
	@ResponseBody
	public ResultEntity selectEmailParams(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = emailParamService.selectEmailParams(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 分页查询邮件 json = { "page":"1", "pageSize":"10" } author shuangjunjie
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "selectEmailParamByPage" })
	@ResponseBody
	public ResultEntity selectEmailParamByPage(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			String page = request.getParameter("page");
			result = emailParamService.selectEmailParamByPage(page);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread()
					.getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

}
