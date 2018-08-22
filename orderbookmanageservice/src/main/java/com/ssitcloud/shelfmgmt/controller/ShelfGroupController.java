package com.ssitcloud.shelfmgmt.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.shelfmgmt.entity.ShelfGroupEntity;
import com.ssitcloud.shelfmgmt.service.ShelfGroupService;


@Controller
@RequestMapping("/shelfGroup")
public class ShelfGroupController {

	@Resource
	ShelfGroupService shelfGroupService;
	
	@RequestMapping("/main")
	public String main(HttpServletRequest request){
		return "/page/shelfmgmt/shelfgroup";
	}
	
	@RequestMapping(value={"queryAllShelfGroup"})
	@ResponseBody
	public ResultEntity queryAllShelfGroup(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = shelfGroupService.queryAllShelfGroup(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = {"deleteShelfGroup" })
	@ResponseBody
	public ResultEntity deleteShelfGroup(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			JSONArray jsonarray = JSONArray.fromObject(req);
			List<ShelfGroupEntity> list = (List<ShelfGroupEntity>)JSONArray.toCollection(jsonarray, ShelfGroupEntity.class); 
			result = shelfGroupService.deleteShelfGroup(list);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "updateShelfGroup" })
	@ResponseBody
	public ResultEntity updateShelfGroup(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = shelfGroupService.updateShelfGroup(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "addShelfGroup" })
	@ResponseBody
	public ResultEntity addShelfGroup(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = shelfGroupService.addShelfGroup(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value={"queryShelfGroupById"})
	@ResponseBody
	public ResultEntity queryShelfGroupById(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = shelfGroupService.queryshelfGroupById(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
