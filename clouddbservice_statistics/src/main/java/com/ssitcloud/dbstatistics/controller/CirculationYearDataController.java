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
import com.ssitcloud.dbstatistics.entity.CirculationYearDataEntity;
import com.ssitcloud.dbstatistics.service.CirculationYearDataService;

@Controller
@RequestMapping("/ciryear")
public class CirculationYearDataController {

	@Resource
	private CirculationYearDataService circulationYearDataService;

	/**
	 * 新增一条记录
	 * 
	 * <p>
	 * 2017年2月8日 上午11:50:36
	 * <p>
	 * create by hjc
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertCirculationYearData")
	@ResponseBody
	public ResultEntity insertCirculationYearData(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				CirculationYearDataEntity circulationYearDataEntity = JsonUtils
						.fromJson(json, CirculationYearDataEntity.class);
				CirculationYearDataEntity tmp = circulationYearDataService
						.queryCirculationYearData(circulationYearDataEntity);// 插入年统计的时候，查询一下数据库，数据库里有则update，反之则insert
				if (tmp != null) {
					tmp.setLoan_cirYearCount(circulationYearDataEntity
							.getLoan_cirYearCount());// 借书统计的结果值更新
					tmp.setReturn_cirYearCount(circulationYearDataEntity
							.getReturn_cirYearCount());// 还书统计的结果值更新
					tmp.setRenew_cirYearCount(circulationYearDataEntity
							.getRenew_cirYearCount());// 续借统计的结果值更新
					tmp.setUpdateDate(DateUtils.currentDate());// 更新时间
					int ret = circulationYearDataService
							.updateCirculationYearData(tmp);
					if (ret > 0) {
						resultEntity.setValue(true, "", "", tmp);
					} else {
						resultEntity.setValue(false, "failed");
					}
				} else {
					circulationYearDataEntity.setCreateDate(DateUtils
							.currentDate());// 创建时间
					int ret = circulationYearDataService
							.insertCirculationYearData(circulationYearDataEntity);
					if (ret > 0) {
						resultEntity.setValue(true, "", "",
								circulationYearDataEntity);
					} else {
						resultEntity.setValue(false, "failed");
					}

				}
			} else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

	/**
	 * 删除一条记录
	 * 
	 * <p>
	 * 2017年2月8日 下午1:36:36
	 * <p>
	 * create by hjc
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteCirculationYearData")
	@ResponseBody
	public ResultEntity deleteCirculationYearData(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				CirculationYearDataEntity circulationYearDataEntity = JsonUtils
						.fromJson(json, CirculationYearDataEntity.class);
				int ret = circulationYearDataService
						.deleteCirculationYearData(circulationYearDataEntity);
				if (ret >= 0) {
					resultEntity.setValue(true, "success");
				} else {
					resultEntity.setValue(false, "failed");
				}
			} else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

	/**
	 * 删除一条记录
	 * 
	 * <p>
	 * 2017年2月8日 下午1:36:36
	 * <p>
	 * create by hjc
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateCirculationYearData")
	@ResponseBody
	public ResultEntity updateCirculationYearData(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				CirculationYearDataEntity circulationYearDataEntity = JsonUtils
						.fromJson(json, CirculationYearDataEntity.class);
				int ret = circulationYearDataService
						.updateCirculationYearData(circulationYearDataEntity);
				if (ret > 0) {
					resultEntity.setValue(true, "", "",
							circulationYearDataEntity);
				} else {
					resultEntity.setValue(false, "failed");
				}
			} else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

	/**
	 * 查询一条记录
	 * 
	 * <p>
	 * 2017年2月8日 下午1:36:36
	 * <p>
	 * create by hjc
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryCirDayData")
	@ResponseBody
	public ResultEntity queryCirDayData(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				CirculationYearDataEntity circulationYearDataEntity = JsonUtils
						.fromJson(json, CirculationYearDataEntity.class);
				CirculationYearDataEntity newcirculationYearDataEntity = circulationYearDataService
						.queryCirculationYearData(circulationYearDataEntity);
				resultEntity.setValue(true, "success", "",
						newcirculationYearDataEntity);
			} else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

	/**
	 * 查询多条记录
	 * 
	 * <p>
	 * 2017年2月8日 下午1:36:36
	 * <p>
	 * create by hjc
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryCirDayDataList")
	@ResponseBody
	public ResultEntity queryCirDayDataList(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				CirculationYearDataEntity circulationYearDataEntity = JsonUtils
						.fromJson(json, CirculationYearDataEntity.class);
				List<CirculationYearDataEntity> list = circulationYearDataService
						.queryCirculationYearDataList(circulationYearDataEntity);
				resultEntity.setValue(true, "success", "", list);
			} else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 获取所有的年统计数据
	 *
	 * <p>2017年4月1日 下午4:05:15 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllCirculationYear")
	@ResponseBody
	public ResultEntity getAllCirculationYear(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<CirculationYearDataEntity> list = circulationYearDataService.getAllCirculationYear();
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
}
