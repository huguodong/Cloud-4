package com.ssitcloud.business.emailmgmt.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.emailmgmt.service.EmailParamService;
import com.ssitcloud.common.entity.ResultEntity;
/**
 * 邮件提醒 业务层EmailParamController
 * @date 2017年2月21日
 * @author shuangjunjie
 *
 */

@Controller
@RequestMapping("/emailParam")
public class EmailParamController {

	@Resource
	EmailParamService emailParamService;
	
	
	/**
	 * 新增邮件
	 * author shuangjunjie
	 * json = {
	 * 		"email_idx": "27",
	 * 		"lib_id": "LIB001",
	 * 		"email_smtp": "13333",
	 * 		"email_port": "1",
	 * 		"email_account": "1",
	 * 		"email_password": "1",
	 * 		"email_code": "1",
	 * 		"email_use_flg": "1"
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = {"insertEmailParam"})
	@ResponseBody
	public ResultEntity insertEmailParam(HttpServletRequest request , String req){
		ResultEntity result=new ResultEntity();
		try {
			result = emailParamService.insertEmailParam(req);	//插入一条邮件记录
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 修改邮件
	 * json = {
	 * 		"email_idx": "27",
	 * 		"lib_id": "LIB001",
	 * 		"email_smtp": "13333",
	 * 		"email_port": "1",
	 * 		"email_account": "1",
	 * 		"email_password": "1",
	 * 		"email_code": "1",
	 * 		"email_use_flg": "1"
	 * }
	 * author shuangjunjie
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = {"updateEmailParam"})
	@ResponseBody
	public ResultEntity updateEmailParam(HttpServletRequest request , String req){
		ResultEntity result=new ResultEntity();
		try {
			result = emailParamService.updateEmailParam(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	/**
	 * 删除邮件
	 * json = {
	 * 		"email_idx": "27",
	 * 		"lib_id": "LIB001",
	 * 		"email_smtp": "13333",
	 * 		"email_port": "1",
	 * 		"email_account": "1",
	 * 		"email_password": "1",
	 * 		"email_code": "1",
	 * 		"email_use_flg": "1"
	 * }
	 * author shuangjunjie
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = {"deleteEmailParam"})
	@ResponseBody
	public ResultEntity deleteEmailParam(HttpServletRequest request , String req){
		ResultEntity result=new ResultEntity();
		try {
			result = emailParamService.deleteEmailParam(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	/**
	 * 查询一条邮件
	 * json = {
	 * 		"email_idx": "27",
	 * 		"lib_id": "LIB001",
	 * 		"email_smtp": "13333",
	 * 		"email_port": "1",
	 * 		"email_account": "1",
	 * 		"email_password": "1",
	 * 		"email_code": "1",
	 * 		"email_use_flg": "1"
	 * }
	 * author shuangjunjie
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = {"selectEmailParam"})
	@ResponseBody
	public ResultEntity selectEmailParam(HttpServletRequest request , String req){
		ResultEntity result=new ResultEntity();
		ResultEntity lbResult=new ResultEntity();
		LibraryEntity libraryEntity = new LibraryEntity();
		try {
//			req = request.getParameter("json");//开发时用来测试数据
			Map<String,Object> map = JsonUtils.fromJson(req, Map.class);
			libraryEntity.setLib_id(map.get("lib_id").toString());
			lbResult = emailParamService.SelLibrary(JsonUtils.toJson(libraryEntity));
			if(lbResult.getState() && null != lbResult.getResult()){
				LibraryEntity lEntity =JsonUtils.fromJson(JsonUtils.toJson(lbResult.getResult()), LibraryEntity.class);
				map.remove("lib_id");
				map.put("lib_idx", lEntity.getLibrary_idx());
				result = emailParamService.selectEmailParam(JsonUtils.toJson(map));
			}else{
				result.setRetMessage("没有对应的图书馆，请输入正确的馆ID");
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查询多条邮件
	 * json = {
	 * 		"email_idx": "27",
	 * 		"lib_id": "LIB001",
	 * 		"email_smtp": "13333",
	 * 		"email_port": "1",
	 * 		"email_account": "1",
	 * 		"email_password": "1",
	 * 		"email_code": "1",
	 * 		"email_use_flg": "1"
	 * }
	 * author shuangjunjie
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = {"selectEmailParams"})
	@ResponseBody
	public ResultEntity selectEmailParams(HttpServletRequest request , String req){
		ResultEntity result=new ResultEntity();
		ResultEntity lbResult=new ResultEntity();
		LibraryEntity libraryEntity = new LibraryEntity();
		try {
//			req = request.getParameter("json");//开发时用来测试数据
			Map<String,Object> map = JsonUtils.fromJson(req, Map.class);
			libraryEntity.setLib_id(map.get("lib_id").toString());
			lbResult = emailParamService.SelLibrary(JsonUtils.toJson(libraryEntity));
			if(lbResult.getState() && null != lbResult.getResult()){
				LibraryEntity lEntity =JsonUtils.fromJson(JsonUtils.toJson(lbResult.getResult()), LibraryEntity.class);
				map.remove("lib_id");
				map.put("lib_idx", lEntity.getLibrary_idx());
				result = emailParamService.selectEmailParams(JsonUtils.toJson(map));
			}else{
				result.setRetMessage("没有对应的图书馆，请输入正确的馆ID");
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	/**
	 * 分页查询邮件
	 * json = {
	 * 		"page":"1",
	 * 		"pageSize":"10"
	 * }
	 * author shuangjunjie
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = {"selectEmailParamByPage"})
	@ResponseBody
	public ResultEntity selectEmailParamByPage(HttpServletRequest request ,String req){
		ResultEntity result = new ResultEntity();
		try {
//			req = request.getParameter("json");//开发时用来测试数据
			result = emailParamService.selectEmailParamByPage(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		
		return result;
	}
	
}
