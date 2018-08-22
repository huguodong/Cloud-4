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
import com.ssitcloud.entity.BookLocationEntity;
import com.ssitcloud.service.BookLocationService;

@Controller
@RequestMapping(value={"/bookLocation"})
public class BookLocationController {
	@Resource
	private BookLocationService bookLocationService;
	
	/**
	 * 新增图书馆藏地BookLocationEntity
	 * 格式
	 * json = {
	 * 		"location_idx":"",//图书馆藏地主键，自增
	 * 		"lib_idx":"",
	 * 		"location_code":"",
	 * 		"location_name":"",
	 * 		"location_mark":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:14
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/addBookLocation"})
	@ResponseBody
	public ResultEntity addBookLocation (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				BookLocationEntity bookLocationEntity = JsonUtils.fromJson(json, BookLocationEntity.class);
				int ret = bookLocationService.insertBookLocation(bookLocationEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",bookLocationEntity);
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
	 * 修改图书馆藏地BookLocationEntity
	 * 格式
	 * json = {
	 * 		"location_idx":"",//图书馆藏地主键，自增
	 * 		"lib_idx":"",
	 * 		"location_code":"",
	 * 		"location_name":"",
	 * 		"location_mark":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:33
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/updateBookLocation"})
	@ResponseBody
	public ResultEntity updateBookLocation (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				BookLocationEntity bookLocationEntity = JsonUtils.fromJson(json, BookLocationEntity.class);
				int ret = bookLocationService.updateBookLocation(bookLocationEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",bookLocationEntity);
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
	 * 删除图书馆藏地BookLocationEntity
	 * 格式
	 * json = {
	 * 		"location_idx":"",//图书馆藏地主键，自增
	 * 		"lib_idx":"",
	 * 		"location_code":"",
	 * 		"location_name":"",
	 * 		"location_mark":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:32
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/deleteBookLocation"})
	@ResponseBody
	public ResultEntity deleteBookLocation (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				BookLocationEntity bookLocationEntity = JsonUtils.fromJson(json, BookLocationEntity.class);
				int ret = bookLocationService.deleteBookLocation(bookLocationEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",bookLocationEntity);
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
	 * 查询一条图书馆藏地记录BookLocationEntity
	 * 格式
	 * json = {
	 * 		"location_idx":"",//图书馆藏地主键，自增
	 * 		"lib_idx":"",
	 * 		"location_code":"",
	 * 		"location_name":"",
	 * 		"location_mark":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectBookLocation"})
	@ResponseBody
	public ResultEntity selectBookLocation (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				BookLocationEntity bookLocationEntity = JsonUtils.fromJson(json, BookLocationEntity.class);
				bookLocationEntity = bookLocationService.queryOneBookLocation(bookLocationEntity);
				resultEntity.setValue(true, "success","",bookLocationEntity);
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
	 * 查询多条图书馆藏地记录BookLocationEntity
	 * 格式
	 * json = {
	 * 		"location_idx":"",//图书馆藏地主键，自增
	 * 		"lib_idx":"",
	 * 		"location_code":"",
	 * 		"location_name":"",
	 * 		"location_mark":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectBookLocations"})
	@ResponseBody
	public ResultEntity selectBookLocations (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			BookLocationEntity bookLocationEntity = JsonUtils.fromJson(json, BookLocationEntity.class);
			List<BookLocationEntity> list = bookLocationService.queryBookLocations(bookLocationEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	

	
}
