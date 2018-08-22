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
import com.ssitcloud.dbstatistics.entity.NewCardMonthDataEntity;
import com.ssitcloud.dbstatistics.entity.NewCardWeekDataEntity;
import com.ssitcloud.dbstatistics.service.NewCardWeekDataService;

@Controller
@RequestMapping(value={"/newCardWeekData"})
public class NewCardWeekDataController {
	@Resource
	private NewCardWeekDataService newCardWeekDataService;
	
	/**
	 * 新增办证查询按周统计NewCardWeekDataEntity
	 * 格式
	 * json = {
	 * 		"newCard_idx":"",//办证查询按周统计主键，自增
	 * 		"lib_idx":"",
	 * 		"newCardCountType":"",
	 * 		"newCardsubType":"",
	 * 		"newCardWeekCount":"",
	 * 		"newCardDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:30:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/addNewCardWeekData"})
	@ResponseBody
	public ResultEntity addNewCardWeekData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				NewCardWeekDataEntity newCardWeekDataEntity = JsonUtils.fromJson(json, NewCardWeekDataEntity.class);
				newCardWeekDataEntity.setNewCardDate(newCardWeekDataEntity.getNewCardDate().replaceAll("-", ""));//替换字符串将横杠去掉
				NewCardWeekDataEntity tmp = newCardWeekDataService.queryOneNewCardWeekData(newCardWeekDataEntity);//插入周统计的时候，查询一下数据库，数据库里有则update，反之则insert
				if(tmp!=null){
					tmp.setNewCardWeekCount(newCardWeekDataEntity.getNewCardWeekCount());//统计的结果值更新
					tmp.setUpdateDate(DateUtils.currentDate());//更新时间
					int ret = newCardWeekDataService.updateNewCardWeekData(tmp);
					if (ret>0) {
						resultEntity.setValue(true, "","",tmp);
					}else{
						resultEntity.setValue(false, "failed");
					}
				}else{
					newCardWeekDataEntity.setCreateDate(DateUtils.currentDate());//创建时间
					int ret = newCardWeekDataService.insertNewCardWeekData(newCardWeekDataEntity);
					if (ret>0) {
						resultEntity.setValue(true, "","",newCardWeekDataEntity);
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
	 * 修改办证查询按周统计NewCardWeekDataEntity
	 * 格式
	 * json = {
	 * 		"newCard_idx":"",//办证查询按周统计主键，自增
	 * 		"lib_idx":"",
	 * 		"newCardCountType":"",
	 * 		"newCardsubType":"",
	 * 		"newCardWeekCount":"",
	 * 		"newCardDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:30:59
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/updateNewCardWeekData"})
	@ResponseBody
	public ResultEntity updateNewCardWeekData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				NewCardWeekDataEntity newCardWeekDataEntity = JsonUtils.fromJson(json, NewCardWeekDataEntity.class);
				int ret = newCardWeekDataService.updateNewCardWeekData(newCardWeekDataEntity);
				if (ret>0) {
					resultEntity.setValue(true, "","",newCardWeekDataEntity);
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
	 * 删除办证查询按周统计NewCardWeekDataEntity
	 * 格式
	 * json = {
	 * 		"newCard_idx":"",//办证查询按周统计主键，自增
	 * 		"lib_idx":"",
	 * 		"newCardCountType":"",
	 * 		"newCardsubType":"",
	 * 		"newCardWeekCount":"",
	 * 		"newCardDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:31:12
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/deleteNewCardWeekData"})
	@ResponseBody
	public ResultEntity deleteNewCardWeekData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				NewCardWeekDataEntity newCardWeekDataEntity = JsonUtils.fromJson(json, NewCardWeekDataEntity.class);
				int ret = newCardWeekDataService.deleteNewCardWeekData(newCardWeekDataEntity);
				if (ret>0) {
					resultEntity.setValue(true, "","",newCardWeekDataEntity);
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
	 * 查询一条办证查询按周统计记录NewCardWeekDataEntity
	 * 格式
	 * json = {
	 * 		"newCard_idx":"",//办证查询按周统计主键，自增
	 * 		"lib_idx":"",
	 * 		"newCardCountType":"",
	 * 		"newCardsubType":"",
	 * 		"newCardWeekCount":"",
	 * 		"newCardDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:31:39
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectNewCardWeekData"})
	@ResponseBody
	public ResultEntity selectNewCardWeekData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				NewCardWeekDataEntity newCardWeekDataEntity = JsonUtils.fromJson(json, NewCardWeekDataEntity.class);
				NewCardWeekDataEntity newNewCardWeekDataEntity = newCardWeekDataService.queryOneNewCardWeekData(newCardWeekDataEntity);
				resultEntity.setValue(true, "success","",newNewCardWeekDataEntity);
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
	 * 查询一条办证查询按周统计记录NewCardWeekDataEntity
	 * 格式
	 * json = {
	 * 		"newCard_idx":"",//办证查询按周统计主键，自增
	 * 		"lib_idx":"",
	 * 		"newCardCountType":"",
	 * 		"newCardsubType":"",
	 * 		"newCardWeekCount":"",
	 * 		"newCardDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:31:55
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectNewCardWeekDatas"})
	@ResponseBody
	public ResultEntity queryNewCardWeekDatas (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				NewCardWeekDataEntity newCardWeekDataEntity = JsonUtils.fromJson(json, NewCardWeekDataEntity.class);
				List<NewCardWeekDataEntity> list = newCardWeekDataService.queryNewCardWeekDatas(newCardWeekDataEntity);
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
	@RequestMapping("/getAllNewCardWeek")
	@ResponseBody
	public ResultEntity getAllNewCardWeek(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<NewCardWeekDataEntity> list = newCardWeekDataService.getAllNewCardWeek();
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
}
