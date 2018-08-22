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
import com.ssitcloud.dbstatistics.service.CirculationDayDataService;

@Controller
@RequestMapping("/cirday")
public class CirculationDayDataController {

	@Resource
	private CirculationDayDataService circulationDayDataService;
	
	/**
	 * 新增一条记录
	 *
	 * <p>2017年2月8日 上午11:50:36 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertCirculationDayData")
	@ResponseBody
	public ResultEntity insertCirculationDayData(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				CirculationDayDataEntity circulationDayDataEntity = JsonUtils.fromJson(json, CirculationDayDataEntity.class);
				circulationDayDataEntity.setCirculateDate(circulationDayDataEntity.getCirculateDate().replaceAll("-", ""));//替换字符串将横杠去掉
				CirculationDayDataEntity tmp = circulationDayDataService.queryCirculationDayData(circulationDayDataEntity);//插入日统计的时候，查询一下数据库，数据库里有则update，反之则insert
				if(tmp!=null){
					tmp.setLoan_cirDayCount(circulationDayDataEntity.getLoan_cirDayCount());//借书统计的结果值更新
					tmp.setRuturn_cirDayCount(circulationDayDataEntity.getRuturn_cirDayCount());//还书统计的结果值更新
					tmp.setRenew_cirDayCount(circulationDayDataEntity.getRenew_cirDayCount());//续借统计的结果值更新
					tmp.setUpdateDate(DateUtils.currentDate());//更新时间
					int ret = circulationDayDataService.updateCirculationDayData(tmp);
					if (ret>0) {
						resultEntity.setValue(true, "","",tmp);
					}else{
						resultEntity.setValue(false, "failed");
					}
				}else{
					circulationDayDataEntity.setCreateDate(DateUtils.currentDate());//创建时间
					int ret = circulationDayDataService.insertCirculationDayData(circulationDayDataEntity);
					if (ret>0) {
						resultEntity.setValue(true, "","",circulationDayDataEntity);
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
	@RequestMapping("/deleteCirculationDayData")
	@ResponseBody
	public ResultEntity deleteCirculationDayData(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				CirculationDayDataEntity circulationDayDataEntity = JsonUtils.fromJson(json, CirculationDayDataEntity.class);
				int ret = circulationDayDataService.deleteCirculationDayData(circulationDayDataEntity);
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
	@RequestMapping("/updateCirculationDayData")
	@ResponseBody
	public ResultEntity updateCirculationDayData(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				CirculationDayDataEntity circulationDayDataEntity = JsonUtils.fromJson(json, CirculationDayDataEntity.class);
				int ret = circulationDayDataService.updateCirculationDayData(circulationDayDataEntity);
				if (ret>0) {
					resultEntity.setValue(true, "","",circulationDayDataEntity);
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
				CirculationDayDataEntity circulationDayDataEntity = JsonUtils.fromJson(json, CirculationDayDataEntity.class);
				CirculationDayDataEntity newcirculationDayDataEntity = circulationDayDataService.queryCirculationDayData(circulationDayDataEntity);
				resultEntity.setValue(true, "success","",newcirculationDayDataEntity);
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
				CirculationDayDataEntity circulationDayDataEntity = JsonUtils.fromJson(json, CirculationDayDataEntity.class);
				List<CirculationDayDataEntity> list = circulationDayDataService.queryCirculationDayDataList(circulationDayDataEntity);
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
	@RequestMapping("/getAllCirculationDay")
	@ResponseBody
	public ResultEntity getAllCirculationDay(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<CirculationDayDataEntity> list = circulationDayDataService.getAllCirculationDay();
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
}
