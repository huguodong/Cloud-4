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
import com.ssitcloud.dblib.entity.BookShelfLayerEntity;
import com.ssitcloud.dblib.entity.ExportBookshelfEntity;
import com.ssitcloud.dblib.service.BookShelfLayerService;

@Controller
@RequestMapping(value={"bookshelflayer"})
public class BookShelfLayerController {
	@Resource
	private BookShelfLayerService bookShelfLayerService;
	
	@RequestMapping(value={"queryAllBookshelflayer"})
	@ResponseBody
	public ResultEntity queryAllBookshelflayer(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			JSONObject jsonObject =JSONObject.fromObject(req);
			map.put("json", jsonObject.getString("json"));
			map.put("page", jsonObject.getString("page"));
			PageEntity pageEntity = bookShelfLayerService.queryAllBookshelflayer(map);
			result.setResult(pageEntity);
			result.setMessage(Const.SUCCESS);
			result.setState(true);
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
			JSONArray jsonarray = JSONArray.fromObject(req);
			List<BookShelfLayerEntity> list = (List<BookShelfLayerEntity>)JSONArray.toCollection(jsonarray, BookShelfLayerEntity.class); 
			int re = 1;
			for(BookShelfLayerEntity entity : list){
				re = bookShelfLayerService.deleteBookshelflayer(entity);
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
	
	@RequestMapping(value = { "updateBookshelflayer" })
	@ResponseBody
	public ResultEntity updateBookshelflayer(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			int re = bookShelfLayerService.updateBookshelflayer(JsonUtils.fromJson(req, BookShelfLayerEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setRetMessage(re >= 1 ? Const.SUCCESS : "optimistic");
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
			int re = bookShelfLayerService.addBookshelflayer(JsonUtils.fromJson(req, BookShelfLayerEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			if(e instanceof org.springframework.dao.DuplicateKeyException){
				String msg=e.getCause().getMessage();
				if(StringUtils.contains(msg, "Duplicate entry")){
					result.setMessage(Const.CHECK_FALSE);
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
			List<ExportBookshelfEntity> list = bookShelfLayerService.exportBookshelflayer(JsonUtils.fromJson(req, BookShelfLayerEntity.class));
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
	
	@RequestMapping(value = { "uploadBookshelflayer" })
	@ResponseBody
	public ResultEntity uploadBookshelflayer(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			int re = bookShelfLayerService.uploadBookshelflayer(req);
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
}
