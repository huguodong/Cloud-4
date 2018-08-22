package com.ssitcloud.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.BookMediatypeEntity;
import com.ssitcloud.service.BookMediatypeService;

@Controller
@RequestMapping(value={"/bookMediatype"})
public class BookMediatypeController {
	@Resource
	private BookMediatypeService bookMediatypeService;
	
	/**
	 * 新增图书载体类型BookMediatypeEntity
	 * 格式
	 * json = {
	 * 		"media_idx":"",//图书载体类型主键，自增
	 * 		"lib_idx":"",
	 * 		"media_code":"",
	 * 		"media_name":"",
	 * 		"media_mark":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:14
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/addBookMediatype"})
	@ResponseBody
	public ResultEntity addBookMediatype (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				BookMediatypeEntity bookMediatypeEntity = JsonUtils.fromJson(json, BookMediatypeEntity.class);
				int ret = bookMediatypeService.insertBookMediatype(bookMediatypeEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",bookMediatypeEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 修改图书载体类型BookMediatypeEntity
	 * 格式
	 * json = {
	 * 		"media_idx":"",//图书载体类型主键，自增
	 * 		"lib_idx":"",
	 * 		"media_code":"",
	 * 		"media_name":"",
	 * 		"media_mark":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:33
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/updateBookMediatype"})
	@ResponseBody
	public ResultEntity updateBookMediatype (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				BookMediatypeEntity bookMediatypeEntity = JsonUtils.fromJson(json, BookMediatypeEntity.class);
				int ret = bookMediatypeService.updateBookMediatype(bookMediatypeEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",bookMediatypeEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 删除图书载体类型BookMediatypeEntity
	 * 格式
	 * json = {
	 * 		"media_idx":"",//图书载体类型主键，自增
	 * 		"lib_idx":"",
	 * 		"media_code":"",
	 * 		"media_name":"",
	 * 		"media_mark":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:32
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/deleteBookMediatype"})
	@ResponseBody
	public ResultEntity deleteBookMediatype (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				BookMediatypeEntity bookMediatypeEntity = JsonUtils.fromJson(json, BookMediatypeEntity.class);
				int ret = bookMediatypeService.deleteBookMediatype(bookMediatypeEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",bookMediatypeEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
			}else{
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询一条图书载体类型记录BookMediatypeEntity
	* 格式
	 * json = {
	 * 		"media_idx":"",//图书载体类型主键，自增
	 * 		"lib_idx":"",
	 * 		"media_code":"",
	 * 		"media_name":"",
	 * 		"media_mark":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectBookMediatype"})
	@ResponseBody
	public ResultEntity selectBookMediatype (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				BookMediatypeEntity bookMediatypeEntity = JsonUtils.fromJson(json, BookMediatypeEntity.class);
				bookMediatypeEntity = bookMediatypeService.queryOneBookMediatype(bookMediatypeEntity);
				resultEntity.setValue(true, "success","",bookMediatypeEntity);
			}else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询多条图书载体类型记录BookMediatypeEntity
	 * 格式
	 * json = {
	 * 		"media_idx":"",//图书载体类型主键，自增
	 * 		"lib_idx":"",
	 * 		"media_code":"",
	 * 		"media_name":"",
	 * 		"media_mark":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectBookMediatypes"})
	@ResponseBody
	public ResultEntity selectBookMediatypes (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			BookMediatypeEntity bookMediatypeEntity = JsonUtils.fromJson(json, BookMediatypeEntity.class);
			List<BookMediatypeEntity> list = bookMediatypeService.queryBookMediatypes(bookMediatypeEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	

	
}
