package com.ssitcloud.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.entity.TableChangeTriEntity;
import com.ssitcloud.service.TableChangeTriService;
//tablechange/SelTableChangeTri
@Controller
@RequestMapping(value={"tablechange"})
public class TableChangeTriController {
	@Resource
	private TableChangeTriService changeTriService;
	/**
	 * 查询需要同步的数据
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"SelTableChangeTri"})
	@ResponseBody
	public ResultEntityF<List<TableChangeTriEntity>> queryAllChangedTable(HttpServletRequest request,String req){
		ResultEntityF<List<TableChangeTriEntity>>  result=new ResultEntityF<List<TableChangeTriEntity>>();
		List<TableChangeTriEntity> changes=changeTriService.queryAllgourpByTableOrderByCreatimeDesc();
		if(changes!=null){
			result.setResult(changes);
			result.setState(true);
		}
		return result;
	}
	/**
	 * 查询补丁数据
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value="SelTableChangeTriPatchInfo")
	@ResponseBody
	public ResultEntityF<List<TableChangeTriEntity>> SelTableChangeTriPatchInfo(HttpServletRequest request,String req){
		ResultEntityF<List<TableChangeTriEntity>>  result=new ResultEntityF<List<TableChangeTriEntity>>();
		List<TableChangeTriEntity> changes=changeTriService.selTableChangeTriPatchInfo();
		if(changes!=null){
			result.setResult(changes);
			result.setState(true);
		}
		return result;
	}
	/**
	 * req={
	 * 	LIST<Integer> 的json格式的数据
	 * }
	 * @author lbh
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value="setRequestTimeByTriIdxs")
	@ResponseBody
	public ResultEntity setRequestTimeByTriIdxs(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=changeTriService.setRequestTimeByTriIdxs(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * req={
	 * 	LIST<String> 的json格式的数据
	 * }
	 * @author lbh
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value="updateRequestTimeByTriTableName")
	@ResponseBody
	public ResultEntity updateRequestTimeByTriTableName(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=changeTriService.updateRequestTimeByTriTableName(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
}
