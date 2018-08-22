package com.ssitcloud.dblib.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.ExceptionHelper;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.entity.BookShelfInfoEntity;
import com.ssitcloud.dblib.service.BookShelfInfoService;

@Controller
@RequestMapping(value={"bookshelfinfo"})
public class BookShelfInfoController {
	@Resource
	private BookShelfInfoService bookShelfInfoService;
	
	@RequestMapping(value={"queryAllBookshelfinfo"})
	@ResponseBody
	public ResultEntity queryAllBookshelfinfo(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			List<BookShelfInfoEntity> bookshelfinfo = bookShelfInfoService.queryAllBookshelfinfo();
			result.setResult(bookshelfinfo);
			result.setMessage(Const.SUCCESS);
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value={"queryBookshelfinfoById"})
	@ResponseBody
	public ResultEntity queryBookshelfinfoById(HttpServletRequest request, String req) {
		ResultEntity result = new ResultEntity();
		try {
			List<BookShelfInfoEntity> list = bookShelfInfoService.queryBookshelfinfoById(JsonUtils.fromJson(
					req, BookShelfInfoEntity.class));
			result.setResult(list);
			result.setMessage(Const.SUCCESS);
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "deleteBookshelfinfo" })
	@ResponseBody
	public ResultEntity deleteBookshelfinfo(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = bookShelfInfoService.deleteBookshelfinfo(JsonUtils.fromJson(
					request.getParameter("json"), BookShelfInfoEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "updateBookshelfinfo" })
	@ResponseBody
	public ResultEntity updateBookshelfinfo(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = bookShelfInfoService.updateBookshelfinfo(JsonUtils.fromJson(
					request.getParameter("json"), BookShelfInfoEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "addBookshelfinfo" })
	@ResponseBody
	public ResultEntity addBookshelfinfo(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			int re = bookShelfInfoService.addBookshelfinfo(JsonUtils.fromJson(
					request.getParameter("json"), BookShelfInfoEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			if(e instanceof org.springframework.dao.DuplicateKeyException){
				String msg=e.getCause().getMessage();
				if(StringUtils.contains(msg, "Duplicate entry")){
					result.setMessage(Const.CHECK_FALSE);
					result.setRetMessage("图书位置信息ID已经存在，请修改");
				}
			}else{
				result.setRetMessage(e.getCause().getMessage());
			}
		}
		return result;
	}
	
}
