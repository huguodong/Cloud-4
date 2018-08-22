package com.ssitcloud.business.upgrade.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.upgrade.service.UpgradeService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value={"upgrade"})
public class UpgradeController {
	@Resource
	private UpgradeService upgradeService;
	
	@RequestMapping(value="queryUpgradeByParam")
	@ResponseBody
	public ResultEntity queryUpgradeByParam(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=upgradeService.selectPatchInfo(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	@RequestMapping(value="askVersion")
	@ResponseBody
	public ResultEntity askVersion(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=upgradeService.askVersion(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	@RequestMapping(value="addPatchInfo")
	@ResponseBody
	public ResultEntity addPatchInfo(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=upgradeService.addPatchInfo(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 单条删除
	 *
	 * <p>2016年7月30日 下午5:57:54 
	 * <p>create by hjc
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value="delPatchInfo")
	@ResponseBody
	public ResultEntity delPatchInfo(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=upgradeService.deletePatchInfo(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 批量删除
	 *
	 * <p>2016年7月30日 下午5:58:13 
	 * <p>create by hjc
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value="delMultiPatchInfo")
	@ResponseBody
	public ResultEntity delMultiPatchInfo(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=upgradeService.delMultiPatchInfo(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	@RequestMapping(value="updPatchInfo")
	@ResponseBody
	public ResultEntity updPatchInfo(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=upgradeService.updatePatchInfo(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
