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
import com.ssitcloud.dbstatistics.entity.NewCardDayDataEntity;
import com.ssitcloud.dbstatistics.service.NewCardDayDataService;

@Controller
@RequestMapping(value={"/newCardDayData"})
public class NewCardDayDataController {
	@Resource
	private NewCardDayDataService newCardDayDataService;
	
	/**
	 * 新增办证查询按天统计NewCardDayDataEntity
	 * 格式
	 * json = {
	 * 		"newCard_idx":"",//办证查询按天统计主键，自增
	 * 		"lib_idx":"",
	 * 		"newCardCountType":"",
	 * 		"newCardsubType":"",
	 * 		"newCardDayCount":"",
	 * 		"newCardDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":"",
	 * 		"newCardWeek":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:27:41
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/addNewCardDayData"})
	@ResponseBody
	public ResultEntity addNewCardDayData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				NewCardDayDataEntity newCardDayDataEntity = JsonUtils.fromJson(json, NewCardDayDataEntity.class);
				newCardDayDataEntity.setNewCardDate(newCardDayDataEntity.getNewCardDate().replaceAll("-", ""));//替换字符串将横杠去掉
				NewCardDayDataEntity tmp = newCardDayDataService.queryOneNewCardDayData(newCardDayDataEntity);//插入日统计的时候，查询一下数据库，数据库里有则update，反之则insert
				if(tmp!=null){
					tmp.setNewCardDayCount(newCardDayDataEntity.getNewCardDayCount());//统计的结果值更新
					tmp.setUpdateDate(DateUtils.currentDate());//更新时间
					int ret = newCardDayDataService.updateNewCardDayData(tmp);
					if (ret>0) {
						resultEntity.setValue(true, "","",tmp);
					}else{
						resultEntity.setValue(false, "failed");
					}
				}else{
					newCardDayDataEntity.setCreateDate(DateUtils.currentDate());//创建时间
					int ret = newCardDayDataService.insertNewCardDayData(newCardDayDataEntity);
					if (ret>0) {
						resultEntity.setValue(true, "","",newCardDayDataEntity);
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
	 * 修改办证查询按天统计NewCardDayDataEntity
	* 格式
	 * json = {
	 * 		"newCard_idx":"",//办证查询按天统计主键，自增
	 * 		"lib_idx":"",
	 * 		"newCardCountType":"",
	 * 		"newCardsubType":"",
	 * 		"newCardDayCount":"",
	 * 		"newCardDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":"",
	 * 		"newCardWeek":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:27:54
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/updateNewCardDayData"})
	@ResponseBody
	public ResultEntity updateNewCardDayData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				NewCardDayDataEntity newCardDayDataEntity = JsonUtils.fromJson(json, NewCardDayDataEntity.class);
				int ret = newCardDayDataService.updateNewCardDayData(newCardDayDataEntity);
				if (ret>0) {
					resultEntity.setValue(true, "","",newCardDayDataEntity);
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
	 * 删除办证查询按天统计NewCardDayDataEntity
	* 格式
	 * json = {
	 * 		"newCard_idx":"",//办证查询按天统计主键，自增
	 * 		"lib_idx":"",
	 * 		"newCardCountType":"",
	 * 		"newCardsubType":"",
	 * 		"newCardDayCount":"",
	 * 		"newCardDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":"",
	 * 		"newCardWeek":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:28:08
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/deleteNewCardDayData"})
	@ResponseBody
	public ResultEntity deleteNewCardDayData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				NewCardDayDataEntity newCardDayDataEntity = JsonUtils.fromJson(json, NewCardDayDataEntity.class);
				int ret = newCardDayDataService.deleteNewCardDayData(newCardDayDataEntity);
				if (ret>0) {
					resultEntity.setValue(true, "","",newCardDayDataEntity);
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
	 * 查询一条办证查询按天统计记录NewCardDayDataEntity
	 * 格式
	 * json = {
	 * 		"newCard_idx":"",//办证查询按天统计主键，自增
	 * 		"lib_idx":"",
	 * 		"newCardCountType":"",
	 * 		"newCardsubType":"",
	 * 		"newCardDayCount":"",
	 * 		"newCardDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":"",
	 * 		"newCardWeek":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:28:21
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectNewCardDayData"})
	@ResponseBody
	public ResultEntity selectNewCardDayData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				NewCardDayDataEntity newCardDayDataEntity = JsonUtils.fromJson(json, NewCardDayDataEntity.class);
				NewCardDayDataEntity newNewCardDayDataEntity = newCardDayDataService.queryOneNewCardDayData(newCardDayDataEntity);
				resultEntity.setValue(true, "success","",newNewCardDayDataEntity);
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
	 * 查询所有办证查询按天统计记录NewCardDayDataEntity
	 * 格式
	 * json = {
	 * 		"newCard_idx":"",//办证查询按天统计主键，自增
	 * 		"lib_idx":"",
	 * 		"newCardCountType":"",
	 * 		"newCardsubType":"",
	 * 		"newCardDayCount":"",
	 * 		"newCardDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":"",
	 * 		"newCardWeek":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:28:33
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectNewCardDayDatas"})
	@ResponseBody
	public ResultEntity queryNewCardDayDatas (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				NewCardDayDataEntity newCardDayDataEntity = JsonUtils.fromJson(json, NewCardDayDataEntity.class);
				List<NewCardDayDataEntity> list = newCardDayDataService.queryNewCardDayDatas(newCardDayDataEntity);
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
	@RequestMapping("/getAllNewCardDay")
	@ResponseBody
	public ResultEntity getAllNewCardDay(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<NewCardDayDataEntity> list = newCardDayDataService.getAllNewCardDay();
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	
	
}
