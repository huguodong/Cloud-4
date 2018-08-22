package com.ssitcloud.business.devmgmt.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.devmgmt.entity.TableChangeTriEntity;
import com.ssitcloud.business.devmgmt.service.TableChangeTriSerive;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;

@RequestMapping(value={"tablechange"})
@Controller
public class TableChangeTriController {
	
	@Resource
	private TableChangeTriSerive tableChangeTriSerive;

	@RequestMapping(value={"SelTableChangeTri"})
	@ResponseBody
	public ResultEntityF<List<TableChangeTriEntity>> SelTableChangeTri(HttpServletRequest request,String req){
		//排除 patchInfo 表
		return tableChangeTriSerive.queryAllTableChanges(req);
	}
	@RequestMapping(value={"SelTableChangeTriPatchInfo"})
	@ResponseBody
	public ResultEntityF<List<TableChangeTriEntity>> SelTableChangeTriPatchInfo(HttpServletRequest request,String req){
		return tableChangeTriSerive.selTableChangeTriPatchInfo(req);
	}
	/**
	 * req={
	 * 	 List<Integer> list 类型的json数据
	 * }
	 * @author lbh
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"setRequestTimeByTriIdxs"})
	@ResponseBody
	public ResultEntityF<Object> setRequestTimeByTriIdxs(HttpServletRequest request,String req){
		ResultEntityF<Object> result=new ResultEntityF<>();
		try {
			result=tableChangeTriSerive.setRequestTimeByTriIdxs(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 req={
	 * 	 List<String> list 类型的json数据   tablename list
	 * }
	 * @author lbh
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"updateRequestTimeByTriTableName"})
	@ResponseBody
	public ResultEntity updateRequestTimeByTriTableName(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=tableChangeTriSerive.updateRequestTimeByTriTableName(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
