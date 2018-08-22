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
import com.ssitcloud.dbstatistics.entity.NewCardDayDataEntity;
import com.ssitcloud.dbstatistics.entity.NewCardMonthDataEntity;
import com.ssitcloud.dbstatistics.service.NewCardMonthDataService;

@Controller
@RequestMapping(value={"/newCardMonthData"})
public class NewCardMonthDataController {
	@Resource
	private NewCardMonthDataService newCardMonthDataService;
	
	/**
	 * 新增办证查询按月统计NewCardMonthDataEntity
	 * 格式
	 * json = {
	 * 		"newCard_idx":"",//办证查询按天统计主键，自增
	 * 		"lib_idx":"",
	 * 		"newCardCountType":"",
	 * 		"newCardsubType":"",
	 * 		"newCardMonthCount":"",
	 * 		"newCardDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:29:11
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/addNewCardMonthData"})
	@ResponseBody
	public ResultEntity addNewCardMonthData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				NewCardMonthDataEntity newCardMonthDataEntity = JsonUtils.fromJson(json, NewCardMonthDataEntity.class);
				newCardMonthDataEntity.setNewCardDate(newCardMonthDataEntity.getNewCardDate().replaceAll("-", ""));//替换字符串将横杠去掉
				NewCardMonthDataEntity tmp = newCardMonthDataService.queryOneNewCardMonthData(newCardMonthDataEntity);//插入月统计的时候，查询一下数据库，数据库里有则update，反之则insert
				if(tmp!=null){
					tmp.setNewCardMonthCount(newCardMonthDataEntity.getNewCardMonthCount());//统计的结果值更新
					tmp.setUpdateDate(DateUtils.currentDate());//更新时间
					int ret = newCardMonthDataService.updateNewCardMonthData(tmp);
					if (ret>0) {
						resultEntity.setValue(true, "","",tmp);
					}else{
						resultEntity.setValue(false, "failed");
					}
				}else{
					newCardMonthDataEntity.setCreateDate(DateUtils.currentDate());//创建时间
					int ret = newCardMonthDataService.insertNewCardMonthData(newCardMonthDataEntity);
					if (ret>0) {
						resultEntity.setValue(true, "","",newCardMonthDataEntity);
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
	 * 修改办证查询按月统计NewCardMonthDataEntity
	 * 格式
	 * json = {
	 * 		"newCard_idx":"",//办证查询按天统计主键，自增
	 * 		"lib_idx":"",
	 * 		"newCardCountType":"",
	 * 		"newCardsubType":"",
	 * 		"newCardMonthCount":"",
	 * 		"newCardDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:29:22
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/updateNewCardMonthData"})
	@ResponseBody
	public ResultEntity updateNewCardMonthData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				NewCardMonthDataEntity newCardMonthDataEntity = JsonUtils.fromJson(json, NewCardMonthDataEntity.class);
				int ret = newCardMonthDataService.updateNewCardMonthData(newCardMonthDataEntity);
				if (ret>0) {
					resultEntity.setValue(true, "","",newCardMonthDataEntity);
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
	 * 删除办证查询按月统计NewCardMonthDataEntity
	 * 格式
	 * json = {
	 * 		"newCard_idx":"",//办证查询按天统计主键，自增
	 * 		"lib_idx":"",
	 * 		"newCardCountType":"",
	 * 		"newCardsubType":"",
	 * 		"newCardMonthCount":"",
	 * 		"newCardDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:29:38
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/deleteNewCardMonthData"})
	@ResponseBody
	public ResultEntity deleteNewCardMonthData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				NewCardMonthDataEntity newCardMonthDataEntity = JsonUtils.fromJson(json, NewCardMonthDataEntity.class);
				int ret = newCardMonthDataService.deleteNewCardMonthData(newCardMonthDataEntity);
				if (ret>0) {
					resultEntity.setValue(true, "","",newCardMonthDataEntity);
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
	 * 查询一条办证查询按月统计记录NewCardMonthDataEntity
	 * 格式
	 * json = {
	 * 		"newCard_idx":"",//办证查询按天统计主键，自增
	 * 		"lib_idx":"",
	 * 		"newCardCountType":"",
	 * 		"newCardsubType":"",
	 * 		"newCardMonthCount":"",
	 * 		"newCardDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:29:50
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectNewCardMonthData"})
	@ResponseBody
	public ResultEntity selectNewCardMonthData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				NewCardMonthDataEntity newCardMonthDataEntity = JsonUtils.fromJson(json, NewCardMonthDataEntity.class);
				NewCardMonthDataEntity newNewCardMonthDataEntity = newCardMonthDataService.queryOneNewCardMonthData(newCardMonthDataEntity);
				resultEntity.setValue(true, "success","",newNewCardMonthDataEntity);
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
	 * 查询所有办证查询按月统计记录NewCardMonthDataEntity
	* 格式
	 * json = {
	 * 		"newCard_idx":"",//办证查询按天统计主键，自增
	 * 		"lib_idx":"",
	 * 		"newCardCountType":"",
	 * 		"newCardsubType":"",
	 * 		"newCardMonthCount":"",
	 * 		"newCardDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:30:06
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectNewCardMonthDatas"})
	@ResponseBody
	public ResultEntity queryNewCardMonthDatas (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				NewCardMonthDataEntity newCardMonthDataEntity = JsonUtils.fromJson(json, NewCardMonthDataEntity.class);
				List<NewCardMonthDataEntity> list = newCardMonthDataService.queryNewCardMonthDatas(newCardMonthDataEntity);
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
	@RequestMapping("/getAllNewCardMonth")
	@ResponseBody
	public ResultEntity getAllNewCardMonth(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<NewCardMonthDataEntity> list = newCardMonthDataService.getAllNewCardMonth();
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
}
