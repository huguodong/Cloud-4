package com.ssitcloud.dblib.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.ExceptionHelper;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.entity.ReaderDefaultEntity;
import com.ssitcloud.dblib.service.ReaderDefaultService;

@Controller
@RequestMapping(value={"readerDefault"})
public class ReaderDefaultController {
	@Resource
	private ReaderDefaultService readerDefaultService;
	
	/**
	 * 新增读者默认信息ReaderDefaultEntity
	 * 格式
	 * json = {
	 * 		"default_idx":"",//读者默认信息主键，自增
	 * 		"reader_idx":"",
	 * 		"default_device":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:14
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"addReaderDefault.do"})
	@ResponseBody
	public ResultEntity addReaderDefault (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				ReaderDefaultEntity readerDefaultEntity = JsonUtils.fromJson(json, ReaderDefaultEntity.class);
				int ret = readerDefaultService.insertReaderDefault(readerDefaultEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",readerDefaultEntity);
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
	 * 修改读者默认信息ReaderDefaultEntity
	 * 格式
	 * json = {
	 * 		"default_idx":"",//读者默认信息主键，自增
	 * 		"reader_idx":"",
	 * 		"default_device":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:33
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"updateReaderDefault.do"})
	@ResponseBody
	public ResultEntity updateReaderDefault (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				ReaderDefaultEntity readerDefaultEntity = JsonUtils.fromJson(json, ReaderDefaultEntity.class);
				int ret = readerDefaultService.updateReaderDefault(readerDefaultEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",readerDefaultEntity);
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
	 * 删除读者默认信息ReaderDefaultEntity
	 * 格式
	 * json = {
	 * 		"default_idx":"",//读者默认信息主键，自增
	 * 		"reader_idx":"",
	 * 		"default_device":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:32
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"deleteReaderDefault.do"})
	@ResponseBody
	public ResultEntity deleteReaderDefault (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				ReaderDefaultEntity readerDefaultEntity = JsonUtils.fromJson(json, ReaderDefaultEntity.class);
				int ret = readerDefaultService.deleteReaderDefault(readerDefaultEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",readerDefaultEntity);
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
	 * 查询一条读者默认信息记录ReaderDefaultEntity
	 * 格式
	 * json = {
	 * 		"default_idx":"",//读者默认信息主键，自增
	 * 		"reader_idx":"",
	 * 		"default_device":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectReaderDefault.do"})
	@ResponseBody
	public ResultEntity selectReaderDefault (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				ReaderDefaultEntity readerDefaultEntity = JsonUtils.fromJson(json, ReaderDefaultEntity.class);
				readerDefaultEntity = readerDefaultService.queryOneReaderDefault(readerDefaultEntity);
				resultEntity.setValue(true, "success","",readerDefaultEntity);
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
	 * 查询多条读者默认信息记录ReaderDefaultEntity
	 * 格式
	 * json = {
	 * 		"default_idx":"",//读者默认信息主键，自增
	 * 		"reader_idx":"",
	 * 		"default_device":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectReaderDefaults.do"})
	@ResponseBody
	public ResultEntity selectReaderDefaults (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			ReaderDefaultEntity readerDefaultEntity = JsonUtils.fromJson(json, ReaderDefaultEntity.class);
			List<ReaderDefaultEntity> list = readerDefaultService.queryReaderDefaults(readerDefaultEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	
}
