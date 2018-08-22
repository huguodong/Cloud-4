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
import com.ssitcloud.entity.BookCirtypeEntity;
import com.ssitcloud.service.BookCirtypeService;

@Controller
@RequestMapping(value={"/bookCirtype"})
public class BookCirtypeController {
	@Resource
	private BookCirtypeService bookCirtypeService;
	
	/**
	 * 新增图书流通类型BookCirtypeEntity
	 * 格式
	 * json = {
	 * 		"cirtype_idx":"",//图书流通类型主键，自增
	 * 		"lib_idx":"",
	 * 		"cirtype_code":"",
	 * 		"cirtype_name":"",
	 * 		"cirtype_mark":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:14
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/addBookCirtype"})
	@ResponseBody
	public ResultEntity addBookCirtype (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				BookCirtypeEntity bookCirtypeEntity = JsonUtils.fromJson(json, BookCirtypeEntity.class);
				int ret = bookCirtypeService.insertBookCirtype(bookCirtypeEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",bookCirtypeEntity);
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
	 * 修改图书流通类型BookCirtypeEntity
	 * 格式
	 * json = {
	 * 		"cirtype_idx":"",//图书流通类型主键，自增
	 * 		"lib_idx":"",
	 * 		"cirtype_code":"",
	 * 		"cirtype_name":"",
	 * 		"cirtype_mark":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:33
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/updateBookCirtype"})
	@ResponseBody
	public ResultEntity updateBookCirtype (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				BookCirtypeEntity bookCirtypeEntity = JsonUtils.fromJson(json, BookCirtypeEntity.class);
				int ret = bookCirtypeService.updateBookCirtype(bookCirtypeEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",bookCirtypeEntity);
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
	 * 删除图书流通类型BookCirtypeEntity
	 * 格式
	 * json = {
	 * 		"cirtype_idx":"",//图书流通类型主键，自增
	 * 		"lib_idx":"",
	 * 		"cirtype_code":"",
	 * 		"cirtype_name":"",
	 * 		"cirtype_mark":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:32
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/deleteBookCirtype"})
	@ResponseBody
	public ResultEntity deleteBookCirtype (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				BookCirtypeEntity bookCirtypeEntity = JsonUtils.fromJson(json, BookCirtypeEntity.class);
				int ret = bookCirtypeService.deleteBookCirtype(bookCirtypeEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",bookCirtypeEntity);
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
	 * 查询一条图书流通类型记录BookCirtypeEntity
	 * 格式
	 * json = {
	 * 		"cirtype_idx":"",//图书流通类型主键，自增
	 * 		"lib_idx":"",
	 * 		"cirtype_code":"",
	 * 		"cirtype_name":"",
	 * 		"cirtype_mark":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectBookCirtype"})
	@ResponseBody
	public ResultEntity selectBookCirtype (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				BookCirtypeEntity bookCirtypeEntity = JsonUtils.fromJson(json, BookCirtypeEntity.class);
				bookCirtypeEntity = bookCirtypeService.queryOneBookCirtype(bookCirtypeEntity);
				resultEntity.setValue(true, "success","",bookCirtypeEntity);
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
	 * 查询多条图书流通类型记录BookCirtypeEntity
	 * 格式
	 * json = {
	 * 		"cirtype_idx":"",//图书流通类型主键，自增
	 * 		"lib_idx":"",
	 * 		"cirtype_code":"",
	 * 		"cirtype_name":"",
	 * 		"cirtype_mark":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectBookCirtypes"})
	@ResponseBody
	public ResultEntity selectBookCirtypes (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			BookCirtypeEntity bookCirtypeEntity = JsonUtils.fromJson(json, BookCirtypeEntity.class);
			List<BookCirtypeEntity> list = bookCirtypeService.queryBookCirtypes(bookCirtypeEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

}
