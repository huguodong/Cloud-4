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
import com.ssitcloud.dblib.entity.BookShelfEntity;
import com.ssitcloud.dblib.entity.BookShelfInfoEntity;
import com.ssitcloud.dblib.service.BookShelfService;
import com.ssitcloud.dblib.service.TransferFileService;

@Controller
@RequestMapping("/bookshelf")
public class BookShelfController {
	@Resource
	private BookShelfService bookShelfService;
	
	@Resource
	private TransferFileService transferFileService;
	

	@RequestMapping(value={"queryAllBookshelf"})
	@ResponseBody
	public ResultEntity queryAllBookshelf(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			JSONObject jsonObject =JSONObject.fromObject(req);
			map.put("json", jsonObject.getString("json"));
			map.put("page", jsonObject.getString("page"));
			PageEntity pageEntity = bookShelfService.queryAllBookshelf(map);
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
	
	@RequestMapping(value = { "queryBookshelfById" })
	@ResponseBody
	public ResultEntity queryBookshelfById(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			BookShelfEntity  bookshelfEntity = bookShelfService.queryBookshelfById(JsonUtils.fromJson(
					req, BookShelfEntity.class));
			result.setResult(bookshelfEntity);
			result.setState(true );
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "deleteBookshelf" })
	@ResponseBody
	public ResultEntity deleteBookshelf(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			JSONArray jsonarray = JSONArray.fromObject(req);
			List<BookShelfEntity> list = (List<BookShelfEntity>)JSONArray.toCollection(jsonarray, BookShelfEntity.class); 
			int re = 0;
			for(BookShelfEntity entity:list){
				re = bookShelfService.deleteBookshelf(entity);
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
	
	@RequestMapping(value = { "updateBookshelf" })
	@ResponseBody
	public ResultEntity updateBookshelf(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			JSONObject jsonObject =JSONObject.fromObject(req);
			BookShelfEntity bookshelfEntity = JsonUtils.fromJson(jsonObject.getString("json1"), BookShelfEntity.class);
			String shelfinfo = jsonObject.getString("json2");
			JSONArray jsonarray = JSONArray.fromObject(shelfinfo);
			List<BookShelfInfoEntity> list = (List<BookShelfInfoEntity>)JSONArray.toCollection(jsonarray, BookShelfInfoEntity.class); 
			int re = bookShelfService.updateBookshelf(bookshelfEntity,list);
			result.setState(re >= 1 ? true : false);
			result.setRetMessage(re >= 1 ? Const.SUCCESS : "optimistic");
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "addBookshelf" })
	@ResponseBody
	public ResultEntity addBookshelf(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			JSONObject jsonObject =JSONObject.fromObject(req);
			BookShelfEntity bookshelfEntity = JsonUtils.fromJson(jsonObject.getString("json1"), BookShelfEntity.class);
			String shelfinfo = jsonObject.getString("json2");
			JSONArray jsonarray = JSONArray.fromObject(shelfinfo);
			List<BookShelfInfoEntity> list = (List<BookShelfInfoEntity>)JSONArray.toCollection(jsonarray, BookShelfInfoEntity.class); 
			int re = bookShelfService.addBookshelf(bookshelfEntity,list);
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			if(e instanceof org.springframework.dao.DuplicateKeyException){
				String msg=e.getCause().getMessage();
				if(StringUtils.contains(msg, "Duplicate entry")){
					result.setMessage(Const.CHECK_FALSE);
					result.setRetMessage("书架ID已经存在，请修改");
				}
			}else{
				result.setRetMessage(e.getCause().getMessage());
			}
		}
		return result;
	}
	
	@RequestMapping(value = { "updateShelfimage" })
	@ResponseBody
	public ResultEntity updateShelfimage(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			int re = bookShelfService.updateShelfimage(JsonUtils.fromJson(
					req, BookShelfEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "updateFloorimage" })
	@ResponseBody
	public ResultEntity updateFloorimage(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			int re = bookShelfService.updateFloorimage(JsonUtils.fromJson(
					req, BookShelfEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	@RequestMapping(value = { "uploadPoint" })
	@ResponseBody
	public ResultEntity uploadPoint(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			JSONObject jsonObject =JSONObject.fromObject(req);
			String lib_id = jsonObject.getString("libId");
			if(lib_id == null || "null".equals(lib_id)){
				lib_id = null;
			}
			String file = jsonObject.getString("filePath");
			int point_type = Integer.parseInt(jsonObject.getString("point_type"));
			int re = bookShelfService.uploadPoint(lib_id,file,point_type);
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
