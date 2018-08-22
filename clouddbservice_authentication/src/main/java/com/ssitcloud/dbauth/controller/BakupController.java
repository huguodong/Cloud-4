package com.ssitcloud.dbauth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.service.BakupService;
import com.ssitcloud.dbauth.utils.LogUtils;

@Controller
@RequestMapping("bakup")
public class BakupController {
	@Resource
	private BakupService bakupService;
	/**
	 * 备份数据
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("bakupOnlyByLiraryIdxSsitAuth")
	@ResponseBody
	public ResultEntity bakupOnlyByLiraryIdxSsitAuth(HttpServletRequest request,String req){
		ResultEntity res=new ResultEntity();
		try {
			res= bakupService.bakupOnlyByLiraryIdxSsitAuth(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			res.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return res;
	}
	/**
	 * 根据馆IDX还原数据
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("restoreDataByLibraryIdx")
	@ResponseBody
	public ResultEntity restoreDataByLibraryIdx(HttpServletRequest request,String req){
		ResultEntity res=new ResultEntity();
		try {
			res= bakupService.restoreDataByLibraryIdx(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			res.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return res;
	}
	/**
	 * 获取改变的 IDX的值
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("GetChangedIDXByIdxNameAndLibraryInfo")
	@ResponseBody
	public ResultEntity GetChangedIDXByIdxNameAndLibraryInfo(HttpServletRequest request,String req){
		ResultEntity res=new ResultEntity();
		try {
			res= bakupService.GetChangedIDXByIdxNameAndLibraryInfo(req);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			res.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return res;
	}
}
