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
import com.ssitcloud.dbstatistics.entity.CirculationMonthDataEntity;
import com.ssitcloud.dbstatistics.service.CirculationMonthDataService;

@Controller
@RequestMapping("/cirmonth")
public class CirculationMonthDataController {

	@Resource
	private CirculationMonthDataService circulationMonthDataService;
	
	/**
	 * 新增一条记录
	 *
	 * <p>2017年2月8日 上午11:50:36 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertCirculationMonthData")
	@ResponseBody
	public ResultEntity insertCirculationMonthData(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				CirculationMonthDataEntity circulationMonthDataEntity = JsonUtils.fromJson(json, CirculationMonthDataEntity.class);
				circulationMonthDataEntity.setCirculateDate(circulationMonthDataEntity.getCirculateDate().replaceAll("-", ""));//替换字符串将横杠去掉
				CirculationMonthDataEntity tmp = circulationMonthDataService.queryCirculationMonthData(circulationMonthDataEntity);//插入月统计的时候，查询一下数据库，数据库里有则update，反之则insert
				if(tmp!=null){
					tmp.setLoan_cirMonthCount(circulationMonthDataEntity.getLoan_cirMonthCount());//借书统计的结果值更新
					tmp.setReturn_cirMonthCount(circulationMonthDataEntity.getReturn_cirMonthCount());//还书统计的结果值更新
					tmp.setRenew_cirMonthCount(circulationMonthDataEntity.getRenew_cirMonthCount());//续借统计的结果值更新
					tmp.setUpdateDate(DateUtils.currentDate());//更新时间
					int ret = circulationMonthDataService.updateCirculationMonthData(tmp);
					if (ret>0) {
						resultEntity.setValue(true, "","",tmp);
					}else{
						resultEntity.setValue(false, "failed");
					}
				}else{
					circulationMonthDataEntity.setCreateDate(DateUtils.currentDate());//创建时间
					int ret = circulationMonthDataService.insertCirculationMonthData(circulationMonthDataEntity);
					if (ret>0) {
						resultEntity.setValue(true, "","",circulationMonthDataEntity);
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
	@RequestMapping("/deleteCirculationMonthData")
	@ResponseBody
	public ResultEntity deleteCirculationMonthData(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				CirculationMonthDataEntity circulationMonthDataEntity = JsonUtils.fromJson(json, CirculationMonthDataEntity.class);
				int ret = circulationMonthDataService.deleteCirculationMonthData(circulationMonthDataEntity);
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
	@RequestMapping("/updateCirculationMonthData")
	@ResponseBody
	public ResultEntity updateCirculationMonthData(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				CirculationMonthDataEntity circulationMonthDataEntity = JsonUtils.fromJson(json, CirculationMonthDataEntity.class);
				int ret = circulationMonthDataService.updateCirculationMonthData(circulationMonthDataEntity);
				if (ret>0) {
					resultEntity.setValue(true, "","",circulationMonthDataEntity);
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
	@RequestMapping("/queryCirMonthData")
	@ResponseBody
	public ResultEntity queryCirMonthData(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				CirculationMonthDataEntity circulationMonthDataEntity = JsonUtils.fromJson(json, CirculationMonthDataEntity.class);
				CirculationMonthDataEntity newcirculationMonthDataEntity = circulationMonthDataService.queryCirculationMonthData(circulationMonthDataEntity);
				resultEntity.setValue(true, "success","",newcirculationMonthDataEntity);
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
	@RequestMapping("/queryCirMonthDataList")
	@ResponseBody
	public ResultEntity queryCirMonthDataList(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				CirculationMonthDataEntity circulationMonthDataEntity = JsonUtils.fromJson(json, CirculationMonthDataEntity.class);
				List<CirculationMonthDataEntity> list = circulationMonthDataService.queryCirculationMonthDataList(circulationMonthDataEntity);
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
	@RequestMapping("/getAllCirculationMonth")
	@ResponseBody
	public ResultEntity getAllCirculationMonth(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<CirculationMonthDataEntity> list = circulationMonthDataService.getAllCirculationMonth();
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
}
