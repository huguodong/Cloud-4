package com.ssitcloud.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.service.BakupService;

@Controller
@RequestMapping("bakup")
public class BakupController {
	@Resource
	private BakupService bakupService;
	/**
	 * 根据LiraryIdx备份
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("bakupOnlyByLiraryIdx")
	@ResponseBody
	public ResultEntity bakupOnlyByLiraryIdx(HttpServletRequest request,String req){
		ResultEntity result = null;
		try {
			result=bakupService.bakupOnlyByLiraryIdx(req);
		} catch (Exception e) {
			LogUtils.error(e);
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("bakupBySpecalTableDevice")
	@ResponseBody
	public ResultEntity bakupBySpecalTableDevice(HttpServletRequest request,String req){
		ResultEntity result = null;
		try {
			result=bakupService.bakupBySpecalTableDevice(req);
		} catch (Exception e) {
			LogUtils.error(e);
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 特殊 表 （模板等） 备份
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("bakupBasicListSsit")
	@ResponseBody
	public ResultEntity bakupBasicListSsit(HttpServletRequest request,String req){
		ResultEntity result = null;
		try {
			result=bakupService.bakupBasicListSsit(req);
		} catch (Exception e) {
			LogUtils.error(e);
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	/**
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("queryBakDataInfo")
	@ResponseBody
	public ResultEntity queryBakDataInfo(HttpServletRequest request,String req){
		ResultEntity result = null;
		try {
			result=bakupService.queryBakDataInfo(req);
		} catch (Exception e) {
			LogUtils.error(e);
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	/**
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("queryBakDataInfoByIdx")
	@ResponseBody
	public ResultEntity queryBakDataInfoByIdx(HttpServletRequest request,String req){
		ResultEntity result = null;
		try {
			result=bakupService.queryBakDataInfoByIdx(req);
		} catch (Exception e) {
			LogUtils.error(e);
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("insertBakDataInfo")
	@ResponseBody
	public ResultEntity insertBakDataInfo(HttpServletRequest request,String req){
		ResultEntity result = null;
		try {
			result=bakupService.insertBakDataInfo(req);
		} catch (Exception e) {
			LogUtils.error(e);
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("updBakDataInfoByIdx")
	@ResponseBody
	public ResultEntity updBakDataInfoByIdx(HttpServletRequest request,String req){
		ResultEntity result = null;
		try {
			result=bakupService.updBakDataInfoByIdx(req);
		} catch (Exception e) {
			LogUtils.error(e);
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("getTplIdxByLibraryIdx")
	@ResponseBody
	public ResultEntity getTplIdxByLibraryIdx(HttpServletRequest request,String req){
		ResultEntity result = null;
		try {
			result=bakupService.getTplIdxByLibraryIdx(req);
		} catch (Exception e) {
			LogUtils.error(e);
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 根据图书馆备份文件
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("restoreDataByLibraryIdx")
	@ResponseBody
	public ResultEntity restoreDataByLibraryIdx(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result=bakupService.restoreDataByLibraryIdx(req);
		} catch (Exception e) {
			LogUtils.error(e);
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 分页查询
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("queryLibraryDbBakByparamExt")
	@ResponseBody
	public ResultEntity queryLibraryDbBakByparamExt(HttpServletRequest request,String req){
		ResultEntity result = null;
		try {
			result=bakupService.queryLibraryDbBakByparamExt(req);
		} catch (Exception e) {
			LogUtils.error(e);
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("checkBakUpFileIfExsit")
	@ResponseBody
	public ResultEntity checkBakUpFileIfExsit(HttpServletRequest request,String req){
		ResultEntity result = null;
		try {
			result=bakupService.checkBakUpFileIfExsit(req);
		} catch (Exception e) {
			LogUtils.error(e);
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 删除
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("deleteLibBakup")
	@ResponseBody
	public ResultEntity deleteLibBakup(HttpServletRequest request,String req){
		ResultEntity result = null;
		try {
			result=bakupService.deleteLibBakup(req);
		} catch (Exception e) {
			LogUtils.error(e);
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("getLastLibBakUpTime")
	@ResponseBody
	public ResultEntity getLastLibBakUpTime(HttpServletRequest request,String req){
		ResultEntity result = null;
		try {
			result=bakupService.getLastLibBakUpTime(req);
		} catch (Exception e) {
			LogUtils.error(e);
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
}
