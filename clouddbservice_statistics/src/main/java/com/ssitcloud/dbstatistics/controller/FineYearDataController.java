package com.ssitcloud.dbstatistics.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbstatistics.common.utils.DateUtils;
import com.ssitcloud.dbstatistics.common.utils.ExceptionHelper;
import com.ssitcloud.dbstatistics.common.utils.JsonUtils;
import com.ssitcloud.dbstatistics.entity.FineYearDataEntity;
import com.ssitcloud.dbstatistics.service.FineYearDataService;


@Controller
@RequestMapping(value={"/fineYearData"})
public class FineYearDataController {
	@Resource
	private FineYearDataService fineYearDataService;
	
	/**
	 * 新增财经查询按年统计FineYearDataEntity
	 * 格式
	 * json = {
	 * 		"fine_idx":"",//财经查询按年统计主键，自增
	 * 		"lib_idx":"",
	 * 		"fineCountType":"",
	 * 		"finesubType":"",
	 * 		"fineYearCount":"",
	 * 		"fineDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:26:11
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/addFineYearData"})
	@ResponseBody
	public ResultEntity addFineYearData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FineYearDataEntity fineYearDataEntity = JsonUtils.fromJson(json, FineYearDataEntity.class);
				FineYearDataEntity tmp = fineYearDataService.queryOneFineYearData(fineYearDataEntity);//插入年统计的时候，查询一下数据库，数据库里有则update，反之则insert
				if(tmp!=null){
					tmp.setFineYearCount(fineYearDataEntity.getFineYearCount());//统计的结果值更新
					tmp.setUpdateDate(DateUtils.currentDate());//更新时间
					int ret = fineYearDataService.updateFineYearData(tmp);
					if (ret>0) {
						resultEntity.setValue(true, "","",tmp);
					}else{
						resultEntity.setValue(false, "failed");
					}
				}else{
					fineYearDataEntity.setCreateDate(DateUtils.currentDate());//创建时间
					int ret = fineYearDataService.insertFineYearData(fineYearDataEntity);
					if (ret>0) {
						resultEntity.setValue(true, "","",fineYearDataEntity);
					}else{
						resultEntity.setValue(false, "failed");
					}
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
	 * 修改财经查询按年统计FineYearDataEntity
	 * 格式
	 * json = {
	 * 		"fine_idx":"",//财经查询按年统计主键，自增
	 * 		"lib_idx":"",
	 * 		"fineCountType":"",
	 * 		"finesubType":"",
	 * 		"fineYearCount":"",
	 * 		"fineDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:26:27
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/updateFineYearData"})
	@ResponseBody
	public ResultEntity updateFineYearData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FineYearDataEntity fineYearDataEntity = JsonUtils.fromJson(json, FineYearDataEntity.class);
				int ret = fineYearDataService.updateFineYearData(fineYearDataEntity);
				if (ret>0) {
					resultEntity.setValue(true, "","",fineYearDataEntity);
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
	 * 删除财经查询按年统计FineYearDataEntity
	 * 格式
	 * json = {
	 * 		"fine_idx":"",//财经查询按年统计主键，自增
	 * 		"lib_idx":"",
	 * 		"fineCountType":"",
	 * 		"finesubType":"",
	 * 		"fineYearCount":"",
	 * 		"fineDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:26:39
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/deleteFineYearData"})
	@ResponseBody
	public ResultEntity deleteFineYearData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FineYearDataEntity fineYearDataEntity = JsonUtils.fromJson(json, FineYearDataEntity.class);
				int ret = fineYearDataService.deleteFineYearData(fineYearDataEntity);
				if (ret>0) {
					resultEntity.setValue(true, "","",fineYearDataEntity);
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
	 * 查询一条财经查询按年统计记录FineYearDataEntity
	* 格式
	 * json = {
	 * 		"fine_idx":"",//财经查询按年统计主键，自增
	 * 		"lib_idx":"",
	 * 		"fineCountType":"",
	 * 		"finesubType":"",
	 * 		"fineYearCount":"",
	 * 		"fineDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:26:54
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectFineYearData"})
	@ResponseBody
	public ResultEntity selectFineYearData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FineYearDataEntity fineYearDataEntity = JsonUtils.fromJson(json, FineYearDataEntity.class);
				FineYearDataEntity newFineYearDataEntity = fineYearDataService.queryOneFineYearData(fineYearDataEntity);
				resultEntity.setValue(true, "success","",newFineYearDataEntity);
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
	 * 查询所有财经查询按年统计记录FineYearDataEntity
	 * 格式
	 * json = {
	 * 		"fine_idx":"",//财经查询按年统计主键，自增
	 * 		"lib_idx":"",
	 * 		"fineCountType":"",
	 * 		"finesubType":"",
	 * 		"fineYearCount":"",
	 * 		"fineDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:27:07
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectFineYearDatas"})
	@ResponseBody
	public ResultEntity queryFineYearDatas (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FineYearDataEntity fineYearDataEntity = JsonUtils.fromJson(json, FineYearDataEntity.class);
				List<FineYearDataEntity> list = fineYearDataService.queryFineYearDatas(fineYearDataEntity);
				resultEntity.setValue(true, "success","",list);
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
	 * 获取所有的日统计数据
	 *
	 * <p>2017年4月1日 下午4:05:15 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllFinanceYear")
	@ResponseBody
	public ResultEntity getAllFinanceYear(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<FineYearDataEntity> list = fineYearDataService.getAllFinanceYear();
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	
	
}
