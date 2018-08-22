package com.ssitcloud.dblib.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.ExceptionHelper;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.entity.ShelfGroupEntity;
import com.ssitcloud.dblib.service.ShelfGroupService;

@Controller
@RequestMapping("/shelfGroup")
public class ShelfGroupController {

	@Resource
	ShelfGroupService shelfGroupService;
	
	@RequestMapping(value={"queryAllShelfGroup"})
	@ResponseBody
	public ResultEntity queryAllShelfGroup(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			JSONObject jsonObject =JSONObject.fromObject(req);
			map.put("json", jsonObject.getString("json"));
			map.put("page", jsonObject.getString("page"));
			PageEntity shelfGroup = shelfGroupService.queryAllShelfGroup(map);
			result.setResult(shelfGroup);
			result.setMessage(Const.SUCCESS);
			result.setState(true);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "deleteShelfGroup" })
	@ResponseBody
	public ResultEntity deleteShelfGroup(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			JSONArray jsonarray = JSONArray.fromObject(req);
			List<ShelfGroupEntity> list = (List<ShelfGroupEntity>)JSONArray.toCollection(jsonarray, ShelfGroupEntity.class); 
			int re = 0;
			for(ShelfGroupEntity entity : list){
				re = shelfGroupService.deleteShelfGroup(entity);
			}
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
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
			JSONObject jsonObject =JSONObject.fromObject(req);
			ShelfGroupEntity shelfGroupEntity = JsonUtils.fromJson(jsonObject.getString("json1"), ShelfGroupEntity.class);
			int re = shelfGroupService.updateShelfGroup(shelfGroupEntity);
			result.setState(re >= 1 ? true : false);
			result.setRetMessage(re >= 1 ? Const.SUCCESS : "optimistic");
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
			JSONObject jsonObject =JSONObject.fromObject(req);
			ShelfGroupEntity shelfGroupEntity = JsonUtils.fromJson(jsonObject.getString("json1"), ShelfGroupEntity.class);
			int re = shelfGroupService.addShelfGroup(shelfGroupEntity);
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			if(e instanceof org.springframework.dao.DuplicateKeyException){
				String msg=e.getCause().getMessage();
				if(StringUtils.contains(msg, "Duplicate entry")){
					result.setMessage(Const.CHECK_FALSE);
					result.setRetMessage("组ID已经存在，请修改");
				}
			}else{
				result.setRetMessage(e.getCause().getMessage());
			}
		}
		return result;
	}
}
