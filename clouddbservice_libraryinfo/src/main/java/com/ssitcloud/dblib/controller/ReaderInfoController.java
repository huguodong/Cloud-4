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
import com.ssitcloud.dblib.entity.ReaderInfoEntity;
import com.ssitcloud.dblib.service.ReaderInfoService;

@Controller
@RequestMapping("/readerinfo")
public class ReaderInfoController {
	@Resource
	private ReaderInfoService readerInfoService;
	
	
	/**
	 * 新增记录
	 *
	 * <p>2017年2月7日 下午3:55:35 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertReaderInfo")
	@ResponseBody
	public ResultEntity insertReaderInfo(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				ReaderInfoEntity readerinfoEntity = JsonUtils.fromJson(json, ReaderInfoEntity.class);
				int ret = readerInfoService.insertReaderInfo(readerinfoEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",readerinfoEntity);
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
	 * 删除一条记录
	 *
	 * <p>2017年2月7日 下午4:48:46 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteReaderInfo")
	@ResponseBody
	public ResultEntity deleteReaderInfo(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				ReaderInfoEntity readerinfoEntity = JsonUtils.fromJson(json, ReaderInfoEntity.class);
				int ret = readerInfoService.deleteReaderInfo(readerinfoEntity);
				if (ret>=0) {
					resultEntity.setValue(true, "success");
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
	 * 更新记录
	 *
	 * <p>2017年2月7日 下午4:49:04 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateReaderInfo")
	@ResponseBody
	public ResultEntity updateReaderInfo(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				ReaderInfoEntity readerinfoEntity = JsonUtils.fromJson(json, ReaderInfoEntity.class);
				int ret = readerInfoService.updateReaderInfo(readerinfoEntity);
				if (ret>=1) {
					resultEntity.setValue(true, "success");
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
	 * 查询单条记录
	 *
	 * <p>2017年2月7日 下午4:50:05 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryReaderInfo")
	@ResponseBody
	public ResultEntity queryReaderInfo(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				ReaderInfoEntity readerinfoEntity = JsonUtils.fromJson(json, ReaderInfoEntity.class);
				readerinfoEntity = readerInfoService.queryReaderInfo(readerinfoEntity);
				resultEntity.setValue(true, "success","",readerinfoEntity);
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
	 * 查询多条记录
	 *
	 * <p>2017年2月7日 下午4:49:49 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryReaderInfoList")
	@ResponseBody
	public ResultEntity queryReaderInfoList(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			ReaderInfoEntity readerinfoEntity = JsonUtils.fromJson(json, ReaderInfoEntity.class);
			List<ReaderInfoEntity> list = readerInfoService.queryReaderInfoList(readerinfoEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
}
