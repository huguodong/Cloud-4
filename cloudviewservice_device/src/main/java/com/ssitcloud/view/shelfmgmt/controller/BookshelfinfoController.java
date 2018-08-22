package com.ssitcloud.view.shelfmgmt.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.shelfmgmt.service.BookshelfinfoService;

@Controller
@RequestMapping(value={"/bookshelfinfo"})
public class BookshelfinfoController {

	@Resource
	BookshelfinfoService bookshelfinfoService;
	
	
	@RequestMapping(value={"queryAllBookshelfinfo"})
	@ResponseBody
	public ResultEntity queryAllBookshelfinfo(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result=bookshelfinfoService.queryAllBookshelfinfo(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value={"queryBookshelfinfoById"})
	@ResponseBody
	public ResultEntity queryBookshelfinfoById(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result=bookshelfinfoService.queryBookshelfinfoById(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value = { "deleteBookshelfinfo" })
	@ResponseBody
	public ResultEntity deleteBookshelfinfo(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result=bookshelfinfoService.deleteBookshelfinfo(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value = { "updateBookshelfinfo" })
	@ResponseBody
	public ResultEntity updateBookshelfinfo(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result=bookshelfinfoService.updateBookshelfinfo(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value = { "addBookshelfinfo" })
	@ResponseBody
	public ResultEntity addBookshelfinfo(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result=bookshelfinfoService.addBookshelfinfo(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
