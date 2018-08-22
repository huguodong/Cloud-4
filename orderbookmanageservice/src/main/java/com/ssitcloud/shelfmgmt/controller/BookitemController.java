package com.ssitcloud.shelfmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.shelfmgmt.service.BookitemService;

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
	
	@RequestMapping(value={"queryBookitemByCode"})
	@ResponseBody
	public ResultEntity queryBookitemByCode(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = bookitemService.queryBookitemByCode(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
