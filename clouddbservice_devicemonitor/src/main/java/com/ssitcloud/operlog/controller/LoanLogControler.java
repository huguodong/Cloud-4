package com.ssitcloud.operlog.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.operlog.service.LoanLogService;

@Controller
@RequestMapping("loanlog")
public class LoanLogControler {
	
	@Resource
	private LoanLogService loanLogService;
	
	@RequestMapping("selectLoanlog")
	@ResponseBody
	public ResultEntity selectLoanlog(HttpServletRequest request,String req){
		ResultEntity resultEntity = new ResultEntity();
		try {
			if (StringUtils.isNotBlank(req)) {
				resultEntity.setValue(true, "success","",loanLogService.countLoanLog(req));
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
	 * 
	 *
	 * <p>2017年3月22日 下午2:31:01 
	 * <p>create by hjc
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAllLoanLogFromMongodb")
	@ResponseBody
	public ResultEntity queryAllLoanLogFromMongodb(String req, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity = loanLogService.queryAllLoanLogFromMongodb(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询mongodb数据（增量）
	 *
	 * <p>2017年11月29日 上午9:40:09 
	 * <p>create by hjc
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryLoanLogFromMongodb")
	@ResponseBody
	public ResultEntity queryLoanLogFromMongodb(String req, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity = loanLogService.queryLoanLogFromMongodb(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping("/queryLoanLogByPage")
	@ResponseBody
	public ResultEntity queryLoanLogByPage(String req, HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			resultEntity = loanLogService.queryLoanLogByPage(req);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
}
