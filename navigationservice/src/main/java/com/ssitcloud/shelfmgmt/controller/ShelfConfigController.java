package com.ssitcloud.shelfmgmt.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.shelfmgmt.entity.ShelfConfigEntity;
import com.ssitcloud.shelfmgmt.service.ShelfConfigService;


@Controller
@RequestMapping("/shelfConfig")
public class ShelfConfigController {

	@Resource
	ShelfConfigService shelfConfigService;
	
	@RequestMapping("/main")
	public String main(HttpServletRequest request){
		return "/page/shelfmgmt/shelfconfig";
	}
	
	@RequestMapping(value={"queryAllShelfConfig"})
	@ResponseBody
	public ResultEntity queryAllShelfConfig(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = shelfConfigService.queryAllShelfConfig(req);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = {"deleteShelfConfig" })
	@ResponseBody
	public ResultEntity deleteShelfConfig(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			JSONArray jsonarray = JSONArray.fromObject(req);
			List<ShelfConfigEntity> list = (List<ShelfConfigEntity>)JSONArray.toCollection(jsonarray, ShelfConfigEntity.class); 
			result = shelfConfigService.deleteShelfConfig(list);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "updateShelfConfig" })
	@ResponseBody
	public ResultEntity updateShelfConfig(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			ShelfConfigEntity shelfConfigEntity = JsonUtils.fromJson(req, ShelfConfigEntity.class);
			result = shelfConfigService.updateShelfConfig(shelfConfigEntity);
		} catch (Exception e) {
			if(e instanceof org.springframework.dao.DuplicateKeyException){
				String msg=e.getCause().getMessage();
				if(StringUtils.contains(msg, "Duplicate entry")){
					result.setMessage("CHECK_FALSE");
					result.setRetMessage("组ID已经存在，请修改");
				}
			}else{
				result.setRetMessage(e.getCause().getMessage());
			}
		}
		return result;
	}
	
	@RequestMapping(value = { "addShelfConfig" })
	@ResponseBody
	public ResultEntity addShelfConfig(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			ShelfConfigEntity shelfConfigEntity = JsonUtils.fromJson(req, ShelfConfigEntity.class);
			result = shelfConfigService.addShelfConfig(shelfConfigEntity);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
