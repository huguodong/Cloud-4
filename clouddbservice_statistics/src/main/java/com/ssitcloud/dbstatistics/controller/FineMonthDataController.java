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
import com.ssitcloud.dbstatistics.entity.FineMonthDataEntity;
import com.ssitcloud.dbstatistics.service.FineMonthDataService;

@Controller
@RequestMapping("/finemonth")
public class FineMonthDataController {

	@Resource
	private FineMonthDataService fineMonthDataService;
	
	/**
	 * 新增一条记录
	 *
	 * <p>2017年2月8日 上午11:50:36 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertFineMonthData")
	@ResponseBody
	public ResultEntity insertFineMonthData(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FineMonthDataEntity fineMonthDataEntity = JsonUtils.fromJson(json, FineMonthDataEntity.class);
				fineMonthDataEntity.setFineDate(fineMonthDataEntity.getFineDate().replaceAll("-", ""));//替换字符串将横杠去掉
				FineMonthDataEntity tmp = fineMonthDataService.queryFineMonthData(fineMonthDataEntity);//插入月统计的时候，查询一下数据库，数据库里有则update，反之则insert
				if(tmp!=null){
					tmp.setFineMonthCount(fineMonthDataEntity.getFineMonthCount());//统计的结果值更新
					tmp.setUpdateDate(DateUtils.currentDate());//更新时间
					int ret = fineMonthDataService.updateFineMonthData(tmp);
					if (ret>0) {
						resultEntity.setValue(true, "","",tmp);
					}else{
						resultEntity.setValue(false, "failed");
					}
				}else{
					fineMonthDataEntity.setCreateDate(DateUtils.currentDate());//创建时间
					int ret = fineMonthDataService.insertFineMonthData(fineMonthDataEntity);
					if (ret>0) {
						resultEntity.setValue(true, "","",fineMonthDataEntity);
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
	 * 删除一条记录
	 *
	 * <p>2017年2月8日 下午1:36:36 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteFineMonthData")
	@ResponseBody
	public ResultEntity deleteFineMonthData(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FineMonthDataEntity fineMonthDataEntity = JsonUtils.fromJson(json, FineMonthDataEntity.class);
				int ret = fineMonthDataService.deleteFineMonthData(fineMonthDataEntity);
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
	 * 删除一条记录
	 *
	 * <p>2017年2月8日 下午1:36:36 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateFineMonthData")
	@ResponseBody
	public ResultEntity updateFineMonthData(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FineMonthDataEntity fineMonthDataEntity = JsonUtils.fromJson(json, FineMonthDataEntity.class);
				int ret = fineMonthDataService.updateFineMonthData(fineMonthDataEntity);
				if (ret>0) {
					resultEntity.setValue(true, "","",fineMonthDataEntity);
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
	 * 查询一条记录
	 *
	 * <p>2017年2月8日 下午1:36:36 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryFineMonthData")
	@ResponseBody
	public ResultEntity queryFineMonthData(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FineMonthDataEntity fineMonthDataEntity = JsonUtils.fromJson(json, FineMonthDataEntity.class);
				FineMonthDataEntity newFineMonthDataEntity = fineMonthDataService.queryFineMonthData(fineMonthDataEntity);
				resultEntity.setValue(true, "success","",newFineMonthDataEntity);
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
	 * 查询多条记录
	 *
	 * <p>2017年2月8日 下午1:36:36 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryFineMonthDataList")
	@ResponseBody
	public ResultEntity queryFineMonthDataList(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FineMonthDataEntity fineMonthDataEntity = JsonUtils.fromJson(json, FineMonthDataEntity.class);
				List<FineMonthDataEntity> list = fineMonthDataService.queryFineMonthDataList(fineMonthDataEntity);
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
	 * 获取所有的月统计数据
	 *
	 * <p>2017年4月1日 下午4:05:15 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllFinanceMonth")
	@ResponseBody
	public ResultEntity getAllFinanceMonth(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<FineMonthDataEntity> list = fineMonthDataService.getAllFinanceMonth();
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
}
