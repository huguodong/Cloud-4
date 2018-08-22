package com.ssitcloud.business.shelfmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.shelfmgmt.service.BookshelfService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping("/bookshelf")
public class BookshelfController {

	@Resource
	BookshelfService bookshelfService;
	
	@RequestMapping(value={"queryAllBookshelf"})
	@ResponseBody
	public ResultEntity queryAllBookshelf(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = bookshelfService.queryAllBookshelf(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value={"queryBookshelfById"})
	@ResponseBody
	public ResultEntity queryBookshelfById(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = bookshelfService.queryBookshelfById(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value = { "deleteBookshelf" })
	@ResponseBody
	public ResultEntity deleteBookshelf(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = bookshelfService.deleteBookshelf(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value = { "updateBookshelf" })
	@ResponseBody
	public ResultEntity updateBookshelf(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = bookshelfService.updateBookshelf(req);

		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value = { "addBookshelf" })
	@ResponseBody
	public ResultEntity addBookshelf(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = bookshelfService.addBookshelf(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value = { "updateShelfimage" })
	@ResponseBody
	public ResultEntity updateShelfimage(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = bookshelfService.updateShelfimage(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value = { "updateFloorimage" })
	@ResponseBody
	public ResultEntity updateFloorimage(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = bookshelfService.updateFloorimage(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value = { "uploadPoint" })
	@ResponseBody
	public ResultEntity uploadPoint(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = bookshelfService.uploadPoint(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
