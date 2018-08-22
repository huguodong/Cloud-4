package com.ssitcloud.business.librarymgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.librarymgmt.service.LibraryMgmtService;
import com.ssitcloud.common.entity.ResultEntity;


@Controller
@RequestMapping(value={"librarymgmt"})
public class LibraryMgmtController {
	@Resource
	private LibraryMgmtService libraryMgmtService;
	/**
	 * 根据ID查询模板信息
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"selLibraryServiceTemplateByIdx"})
	@ResponseBody
	public ResultEntity selLibraryServiceTemplateByIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		
		try {
			result=libraryMgmtService.selLibraryServiceTemplateByIdx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		
		return result;
	}
	/**
	 * 获取所有的不是slave的馆和其一级子馆
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryAllMasterLibAndSlaveLib"})
	@ResponseBody
	public ResultEntity queryAllMasterLibAndSlaveLib(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=libraryMgmtService.queryAllMasterLibAndSlaveLib(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		
		return result;
	}
	/**
	 * 获取 libidx和 libid的对应关系
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"getLibIdAndLibIdx"})
	@ResponseBody
	public ResultEntity getLibIdAndLibIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=libraryMgmtService.getLibIdAndLibIdx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 根据当前登陆用户获取相应的图书馆信息。和分馆信息
	 * 如果是系统管理员 ，则 可以获取全部图书馆信息。
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"querylibInfoByCurUserEXT1"})
	@ResponseBody
	public ResultEntity querylibInfoByCurUserEXT1(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity(); 
		try {
			result=libraryMgmtService.querylibInfoByCurUserEXT1(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
		
		
	}
	
	/**
	 *保存图书馆的位置信息
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"saveLibPosition"})
	@ResponseBody
	public ResultEntity saveLibPosition(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity(); 
		try {
			result=libraryMgmtService.saveLibPosition(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
