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
import com.ssitcloud.dbstatistics.entity.FineDayDataEntity;
import com.ssitcloud.dbstatistics.service.FineDayDataService;

@Controller
@RequestMapping("/fineday")
public class FineDayDataController {

	@Resource
	private FineDayDataService fineDayDataService;
	
	/**
	 * 新增一条记录
	 *
	 * <p>2017年2月8日 上午11:50:36 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertFineDayData")
	@ResponseBody
	public ResultEntity insertFineDayData(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FineDayDataEntity fineDayDataEntity = JsonUtils.fromJson(json, FineDayDataEntity.class);
				fineDayDataEntity.setFineDate(fineDayDataEntity.getFineDate().replaceAll("-", ""));//替换字符串将横杠去掉
				FineDayDataEntity tmp = fineDayDataService.queryFineDayData(fineDayDataEntity);//插入日统计的时候，查询一下数据库，数据库里有则update，反之则insert
				if(tmp!=null){
					tmp.setFineDayCount(fineDayDataEntity.getFineDayCount());//统计的结果值更新
					tmp.setUpdateDate(DateUtils.currentDate());//更新时间
					int ret = fineDayDataService.updateFineDayData(tmp);
					if (ret>0) {
						resultEntity.setValue(true, "","",tmp);
					}else{
						resultEntity.setValue(false, "failed");
					}
				}else{
					fineDayDataEntity.setCreateDate(DateUtils.currentDate());//创建时间
					int ret = fineDayDataService.insertFineDayData(fineDayDataEntity);
					if (ret>0) {
						resultEntity.setValue(true, "","",fineDayDataEntity);
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
	@RequestMapping("/deleteFineDayData")
	@ResponseBody
	public ResultEntity deleteFineDayData(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FineDayDataEntity fineDayDataEntity = JsonUtils.fromJson(json, FineDayDataEntity.class);
				int ret = fineDayDataService.deleteFineDayData(fineDayDataEntity);
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
	@RequestMapping("/updateFineDayData")
	@ResponseBody
	public ResultEntity updateFineDayData(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FineDayDataEntity fineDayDataEntity = JsonUtils.fromJson(json, FineDayDataEntity.class);
				int ret = fineDayDataService.updateFineDayData(fineDayDataEntity);
				if (ret>0) {
					resultEntity.setValue(true, "","",fineDayDataEntity);
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
	@RequestMapping("/queryFineDayData")
	@ResponseBody
	public ResultEntity queryFineDayData(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FineDayDataEntity fineDayDataEntity = JsonUtils.fromJson(json, FineDayDataEntity.class);
				FineDayDataEntity newFineDayDataEntity = fineDayDataService.queryFineDayData(fineDayDataEntity);
				resultEntity.setValue(true, "success","",newFineDayDataEntity);
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
	@RequestMapping("/queryFineDayDataList")
	@ResponseBody
	public ResultEntity queryFineDayDataList(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FineDayDataEntity fineDayDataEntity = JsonUtils.fromJson(json, FineDayDataEntity.class);
				List<FineDayDataEntity> list = fineDayDataService.queryFineDayDataList(fineDayDataEntity);
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
	@RequestMapping("/getAllFinanceDay")
	@ResponseBody
	public ResultEntity getAllFinanceDay(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<FineDayDataEntity> list = fineDayDataService.getAllFinanceDay();
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
}
