package com.ssitcloud.devmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.controller.BasicController;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.devmgmt.service.LibraryService;

@RequestMapping(value={"librarylocal"})
@Controller
public class LibraryController extends BasicController{
	
	@Resource
	private LibraryService libraryService;
	
	/**
	 * #查出来所有的下属图书馆
	 * 包括父馆本身
	 * @param request
	 * @param req
	 * @return
	 */
	
	@RequestMapping(value={"querySlaveLibraryByMasterIdx"})
	@ResponseBody
	public ResultEntity querySlaveLibraryByMasterIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=libraryService.querySlaveLibraryByMasterIdx(req);
			
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 获取所有的不是slave的馆和其一级子馆
	 * 只有管理员 和维护员可以查看到所有馆及其子馆
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryAllMasterLibAndSlaveLib"})
	@ResponseBody
	public ResultEntity queryAllMasterLibAndSlaveLib(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			Operator oper=getCurrentUser();
			if(oper!=null){
				if(Operator.SSITCLOUD_ADMIN.equals(oper.getOperator_type())||Operator.SSITCLOUD_MANAGER.equals(oper.getOperator_type())){
					result=libraryService.queryAllMasterLibAndSlaveLib(req);
				}
			}else{
				result.setMessage("NO_PERMESSION");
				return result;
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 获取 libidx和 libid 的对应关系
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"getLibIdAndLibIdx"})
	@ResponseBody
	public ResultEntity getLibIdAndLibIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=libraryService.getLibIdAndLibIdx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 检查 是不是主馆
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"checkQuerySlaveLibraryByMasterIdx"})
	@ResponseBody
	public ResultEntity checkQuerySlaveLibraryByMasterIdx(HttpServletRequest request,String req){
		Operator oper=getCurrentUser();
		ResultEntity result=new ResultEntity();
		try {
			if(oper!=null){
				result=libraryService.querySlaveLibraryByMasterIdx("{\"library_idx\":"+oper.getLibrary_idx()+"}");
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
}
