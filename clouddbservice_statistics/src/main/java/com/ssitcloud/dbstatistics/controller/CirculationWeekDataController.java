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
import com.ssitcloud.dbstatistics.entity.CirculationDayDataEntity;
import com.ssitcloud.dbstatistics.entity.CirculationWeekDataEntity;
import com.ssitcloud.dbstatistics.entity.CirculationWeekDataEntity;
import com.ssitcloud.dbstatistics.service.CirculationWeekDataService;

@Controller
@RequestMapping("/cirweek")
public class CirculationWeekDataController {

	@Resource
	private CirculationWeekDataService circulationWeekDataService;
	
	/**
	 * 新增一条记录
	 *
	 * <p>2017年2月8日 上午11:50:36 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertCirculationWeekData")
	@ResponseBody
	public ResultEntity insertCirculationWeekData(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				CirculationWeekDataEntity circulationWeekDataEntity = JsonUtils.fromJson(json, CirculationWeekDataEntity.class);
				circulationWeekDataEntity.setCirculateDate(circulationWeekDataEntity.getCirculateDate().replaceAll("-", ""));//替换字符串将横杠去掉
				CirculationWeekDataEntity tmp = circulationWeekDataService.queryCirculationWeekData(circulationWeekDataEntity);//插入月统计的时候，查询一下数据库，数据库里有则update，反之则insert
				if(tmp!=null){
					tmp.setLoan_cirWeekCount(circulationWeekDataEntity.getLoan_cirWeekCount());//借书统计的结果值更新
					tmp.setReturn_cirWeekCount(circulationWeekDataEntity.getReturn_cirWeekCount());//还书统计的结果值更新
					tmp.setRenew_cirWeekCount(circulationWeekDataEntity.getRenew_cirWeekCount());//续借统计的结果值更新
					tmp.setUpdateDate(DateUtils.currentDate());//更新时间
					int ret = circulationWeekDataService.updateCirculationWeekData(tmp);
					if (ret>0) {
						resultEntity.setValue(true, "","",tmp);
					}else{
						resultEntity.setValue(false, "failed");
					}
				}else{
					circulationWeekDataEntity.setCreateDate(DateUtils.currentDate());//创建时间
					int ret = circulationWeekDataService.insertCirculationWeekData(circulationWeekDataEntity);
					if (ret>0) {
						resultEntity.setValue(true, "","",circulationWeekDataEntity);
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
	@RequestMapping("/deleteCirculationWeekData")
	@ResponseBody
	public ResultEntity deleteCirculationWeekData(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				CirculationWeekDataEntity circulationWeekDataEntity = JsonUtils.fromJson(json, CirculationWeekDataEntity.class);
				int ret = circulationWeekDataService.deleteCirculationWeekData(circulationWeekDataEntity);
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
	@RequestMapping("/updateCirculationWeekData")
	@ResponseBody
	public ResultEntity updateCirculationWeekData(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				CirculationWeekDataEntity circulationWeekDataEntity = JsonUtils.fromJson(json, CirculationWeekDataEntity.class);
				int ret = circulationWeekDataService.updateCirculationWeekData(circulationWeekDataEntity);
				if (ret>0) {
					resultEntity.setValue(true, "","",circulationWeekDataEntity);
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
	@RequestMapping("/queryCirDayData")
	@ResponseBody
	public ResultEntity queryCirDayData(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				CirculationWeekDataEntity circulationWeekDataEntity = JsonUtils.fromJson(json, CirculationWeekDataEntity.class);
				CirculationWeekDataEntity newcirculationWeekDataEntity = circulationWeekDataService.queryCirculationWeekData(circulationWeekDataEntity);
				resultEntity.setValue(true, "success","",newcirculationWeekDataEntity);
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
	@RequestMapping("/queryCirDayDataList")
	@ResponseBody
	public ResultEntity queryCirDayDataList(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				CirculationWeekDataEntity circulationWeekDataEntity = JsonUtils.fromJson(json, CirculationWeekDataEntity.class);
				List<CirculationWeekDataEntity> list = circulationWeekDataService.queryCirculationWeekDataList(circulationWeekDataEntity);
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
	 * 获取所有的周统计数据
	 *
	 * <p>2017年4月1日 下午4:05:15 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllCirculationWeek")
	@ResponseBody
	public ResultEntity getAllCirculationWeek(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<CirculationWeekDataEntity> list = circulationWeekDataService.getAllCirculationWeek();
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
}
