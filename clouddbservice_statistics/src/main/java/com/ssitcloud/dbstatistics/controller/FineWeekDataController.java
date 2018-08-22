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
import com.ssitcloud.dbstatistics.entity.FineWeekDataEntity;
import com.ssitcloud.dbstatistics.service.FineWeekDataService;


@Controller
@RequestMapping(value={"/fineWeekData"})
public class FineWeekDataController {
	@Resource
	private FineWeekDataService fineWeekDataService;
	
	/**
	 * 新增财经查询按周统计FineWeekDataEntity
	 * 格式
	 * json = {
	 * 		"fine_idx":"",//财经查询按周统计主键，自增
	 * 		"lib_idx":"",
	 * 		"fineCountType":"",
	 * 		"finesubType":"",
	 * 		"fineWeekCount":"",
	 * 		"fineDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:24:28
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/addFineWeekData"})
	@ResponseBody
	public ResultEntity addFineWeekData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FineWeekDataEntity fineWeekDataEntity = JsonUtils.fromJson(json, FineWeekDataEntity.class);
				fineWeekDataEntity.setFineDate(fineWeekDataEntity.getFineDate().replaceAll("-", ""));//替换字符串将横杠去掉
				FineWeekDataEntity tmp = fineWeekDataService.queryOneFineWeekData(fineWeekDataEntity);//插入周统计的时候，查询一下数据库，数据库里有则update，反之则insert
				if(tmp!=null){
					tmp.setFineWeekCount(fineWeekDataEntity.getFineWeekCount());//统计的结果值更新
					tmp.setUpdateDate(DateUtils.currentDate());//更新时间
					int ret = fineWeekDataService.updateFineWeekData(tmp);
					if (ret>0) {
						resultEntity.setValue(true, "","",tmp);
					}else{
						resultEntity.setValue(false, "failed");
					}
				}else{
					fineWeekDataEntity.setCreateDate(DateUtils.currentDate());//创建时间
					int ret = fineWeekDataService.insertFineWeekData(fineWeekDataEntity);
					if (ret>0) {
						resultEntity.setValue(true, "","",fineWeekDataEntity);
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
	 * 修改财经查询按周统计FineWeekDataEntity
	 * 格式
	 * json = {
	 * 		"fine_idx":"",//财经查询按周统计主键，自增
	 * 		"lib_idx":"",
	 * 		"fineCountType":"",
	 * 		"finesubType":"",
	 * 		"fineWeekCount":"",
	 * 		"fineDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:24:45
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/updateFineWeekData"})
	@ResponseBody
	public ResultEntity updateFineWeekData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FineWeekDataEntity fineWeekDataEntity = JsonUtils.fromJson(json, FineWeekDataEntity.class);
				int ret = fineWeekDataService.updateFineWeekData(fineWeekDataEntity);
				if (ret>0) {
					resultEntity.setValue(true, "","",fineWeekDataEntity);
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
	 * 删除财经查询按周统计FineWeekDataEntity
	 * 格式
	 * json = {
	 * 		"fine_idx":"",//财经查询按周统计主键，自增
	 * 		"lib_idx":"",
	 * 		"fineCountType":"",
	 * 		"finesubType":"",
	 * 		"fineWeekCount":"",
	 * 		"fineDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:24:57
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/deleteFineWeekData"})
	@ResponseBody
	public ResultEntity deleteFineWeekData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FineWeekDataEntity fineWeekDataEntity = JsonUtils.fromJson(json, FineWeekDataEntity.class);
				int ret = fineWeekDataService.deleteFineWeekData(fineWeekDataEntity);
				if (ret>0) {
					resultEntity.setValue(true, "","",fineWeekDataEntity);
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
	 * 查询一条财经查询按周统计记录FineWeekDataEntity
	 * 格式
	 * json = {
	 * 		"fine_idx":"",//财经查询按周统计主键，自增
	 * 		"lib_idx":"",
	 * 		"fineCountType":"",
	 * 		"finesubType":"",
	 * 		"fineWeekCount":"",
	 * 		"fineDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:25:13
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectFineWeekData"})
	@ResponseBody
	public ResultEntity selectFineWeekData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FineWeekDataEntity fineWeekDataEntity = JsonUtils.fromJson(json, FineWeekDataEntity.class);
				FineWeekDataEntity newFineWeekDataEntity = fineWeekDataService.queryOneFineWeekData(fineWeekDataEntity);
				resultEntity.setValue(true, "success","",newFineWeekDataEntity);
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
	 * 查询所有财经查询按周统计记录FineWeekDataEntity
	 * 格式
	 * json = {
	 * 		"fine_idx":"",//财经查询按周统计主键，自增
	 * 		"lib_idx":"",
	 * 		"fineCountType":"",
	 * 		"finesubType":"",
	 * 		"fineWeekCount":"",
	 * 		"fineDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:25:26
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectFineWeekDatas"})
	@ResponseBody
	public ResultEntity queryFineWeekDatas (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				FineWeekDataEntity fineWeekDataEntity = JsonUtils.fromJson(json, FineWeekDataEntity.class);
				List<FineWeekDataEntity> list = fineWeekDataService.queryFineWeekDatas(fineWeekDataEntity);
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
	@RequestMapping("/getAllFinanceWeek")
	@ResponseBody
	public ResultEntity getAllFinanceWeek(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<FineWeekDataEntity> list = fineWeekDataService.getAllFinanceWeek();
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	
}
