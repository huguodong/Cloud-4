package com.ssitcloud.shelfmgmt.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.shelfmgmt.service.BookshelflayerService;

@Controller
@RequestMapping("/bookshelflayer")
public class BookshelflayerController {

	@Resource
	BookshelflayerService bookshelflayerService;
	
	@RequestMapping("/main")
	public String main(HttpServletRequest request){
		return "/page/shelfmgmt/bookshelfinfo";
	}
	
	@RequestMapping(value={"queryAllBookshelflayer"})
	@ResponseBody
	public ResultEntity queryAllBookshelflayer(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = bookshelflayerService.queryAllBookshelflayer(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "deleteBookshelflayer" })
	@ResponseBody
	public ResultEntity deleteBookshelflayer(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = bookshelflayerService.deleteBookshelflayer(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "updateBookshelflayer" })
	@ResponseBody
	public ResultEntity updateBookshelflayer(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = bookshelflayerService.updateBookshelflayer(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "addBookshelflayer" })
	@ResponseBody
	public ResultEntity addBookshelflayer(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = bookshelflayerService.addBookshelflayer(req);
		} catch (Exception e) {
			if(e instanceof org.springframework.dao.DuplicateKeyException){
				String msg=e.getCause().getMessage();
				if(StringUtils.contains(msg, "Duplicate entry")){
					result.setMessage("CHECK_FALSE");
					result.setRetMessage("层架标ID已经存在，请修改");
				}
			}else{
				result.setRetMessage(e.getCause().getMessage());
			}
		}
		return result;
	}
	
	@RequestMapping(value = { "exportBookshelflayer" })
	@ResponseBody
	public ResultEntity exportBookshelflayer(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = bookshelflayerService.exportBookshelflayer(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "uploadBookshelflayer" })
	@ResponseBody
	public ResultEntity uploadBookshelflayer(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = bookshelflayerService.exportBookshelflayer(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
