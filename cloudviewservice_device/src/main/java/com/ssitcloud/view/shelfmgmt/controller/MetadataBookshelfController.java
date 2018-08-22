package com.ssitcloud.view.shelfmgmt.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.shelfmgmt.service.MetadataBookshelfService;

@Controller
@RequestMapping("/metadataBookshelf")
public class MetadataBookshelfController {

	@Resource
	MetadataBookshelfService metadataBookshelfService;
	
	@RequestMapping(value={"queryAllMetadataBookshelf"})
	@ResponseBody
	public ResultEntity queryAllMetadataBookshelf(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = metadataBookshelfService.queryAllMetadataBookshelf(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "deleteMetadataBookshelf" })
	@ResponseBody
	public ResultEntity deleteMetadataBookshelf(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = metadataBookshelfService.deleteMetadataBookshelf(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "updateMetadataBookshelf" })
	@ResponseBody
	public ResultEntity updateMetadataBookshelf(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = metadataBookshelfService.updateMetadataBookshelf(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "addMetadataBookshelf" })
	@ResponseBody
	public ResultEntity addMetadataBookshelf(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = metadataBookshelfService.addMetadataBookshelf(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
