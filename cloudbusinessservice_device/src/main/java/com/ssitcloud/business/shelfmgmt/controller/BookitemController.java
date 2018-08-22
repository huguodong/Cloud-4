package com.ssitcloud.business.shelfmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.shelfmgmt.service.BookitemService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping("/bookitem")
public class BookitemController {
	@Resource
	BookitemService bookitemService;
	
	@RequestMapping(value={"importBookitem"})
	@ResponseBody
	public ResultEntity importBookitem(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = bookitemService.importBookitem(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	@RequestMapping(value={"queryAllBookitemAndBookInfoByCode"})
	@ResponseBody
	public ResultEntity queryAllBookitemAndBookInfoByCode(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = bookitemService.queryAllBookitemAndBookInfoByCode(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
