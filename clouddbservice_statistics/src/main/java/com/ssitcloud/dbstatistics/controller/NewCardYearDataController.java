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
import com.ssitcloud.dbstatistics.entity.NewCardWeekDataEntity;
import com.ssitcloud.dbstatistics.entity.NewCardYearDataEntity;
import com.ssitcloud.dbstatistics.service.NewCardYearDataService;

@Controller
@RequestMapping(value={"/newCardYearData"})
public class NewCardYearDataController {
	@Resource
	private NewCardYearDataService newCardYearDataService;
	
	/**
	 * 新增办证查询按年统计NewCardYearDataEntity
	 * 格式
	 * json = {
	 * 		"newCard_idx":"",//办证查询按年统计主键，自增
	 * 		"lib_idx":"",
	 * 		"newCardCountType":"",
	 * 		"newCardsubType":"",
	 * 		"newCardYearCount":"",
	 * 		"newCardDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:32:39
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/addNewCardYearData"})
	@ResponseBody
	public ResultEntity addNewCardYearData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				NewCardYearDataEntity newCardYearDataEntity = JsonUtils.fromJson(json, NewCardYearDataEntity.class);
				NewCardYearDataEntity tmp = newCardYearDataService.queryOneNewCardYearData(newCardYearDataEntity);//插入年统计的时候，查询一下数据库，数据库里有则update，反之则insert
				if(tmp!=null){
					tmp.setNewCardYearCount(newCardYearDataEntity.getNewCardYearCount());//统计的结果值更新
					tmp.setUpdateDate(DateUtils.currentDate());//更新时间
					int ret = newCardYearDataService.updateNewCardYearData(tmp);
					if (ret>0) {
						resultEntity.setValue(true, "","",tmp);
					}else{
						resultEntity.setValue(false, "failed");
					}
				}else{
					newCardYearDataEntity.setCreateDate(DateUtils.currentDate());//创建时间
					int ret = newCardYearDataService.insertNewCardYearData(newCardYearDataEntity);
					if (ret>0) {
						resultEntity.setValue(true, "","",newCardYearDataEntity);
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
	 * 修改办证查询按年统计NewCardYearDataEntity
	 * 格式
	 * json = {
	 * 		"newCard_idx":"",//办证查询按年统计主键，自增
	 * 		"lib_idx":"",
	 * 		"newCardCountType":"",
	 * 		"newCardsubType":"",
	 * 		"newCardYearCount":"",
	 * 		"newCardDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:32:51
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/updateNewCardYearData"})
	@ResponseBody
	public ResultEntity updateNewCardYearData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				NewCardYearDataEntity newCardYearDataEntity = JsonUtils.fromJson(json, NewCardYearDataEntity.class);
				int ret = newCardYearDataService.updateNewCardYearData(newCardYearDataEntity);
				if (ret>0) {
					resultEntity.setValue(true, "","",newCardYearDataEntity);
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
	 * 删除办证查询按年统计NewCardYearDataEntity
	 * 格式
	 * json = {
	 * 		"newCard_idx":"",//办证查询按年统计主键，自增
	 * 		"lib_idx":"",
	 * 		"newCardCountType":"",
	 * 		"newCardsubType":"",
	 * 		"newCardYearCount":"",
	 * 		"newCardDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:33:04
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/deleteNewCardYearData"})
	@ResponseBody
	public ResultEntity deleteNewCardYearData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				NewCardYearDataEntity newCardYearDataEntity = JsonUtils.fromJson(json, NewCardYearDataEntity.class);
				int ret = newCardYearDataService.deleteNewCardYearData(newCardYearDataEntity);
				if (ret>0) {
					resultEntity.setValue(true, "","",newCardYearDataEntity);
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
	 * 查询一条办证查询按年统计记录NewCardYearDataEntity
	 * 格式
	 * json = {
	 * 		"newCard_idx":"",//办证查询按年统计主键，自增
	 * 		"lib_idx":"",
	 * 		"newCardCountType":"",
	 * 		"newCardsubType":"",
	 * 		"newCardYearCount":"",
	 * 		"newCardDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:33:18
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectNewCardYearData"})
	@ResponseBody
	public ResultEntity selectNewCardYearData (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				NewCardYearDataEntity newCardYearDataEntity = JsonUtils.fromJson(json, NewCardYearDataEntity.class);
				NewCardYearDataEntity newNewCardYearDataEntity = newCardYearDataService.queryOneNewCardYearData(newCardYearDataEntity);
				resultEntity.setValue(true, "success","",newNewCardYearDataEntity);
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
	 * 查询所有办证查询按年统计记录NewCardYearDataEntity
	 * 格式
	 * json = {
	 * 		"newCard_idx":"",//办证查询按年统计主键，自增
	 * 		"lib_idx":"",
	 * 		"newCardCountType":"",
	 * 		"newCardsubType":"",
	 * 		"newCardYearCount":"",
	 * 		"newCardDate":"",
	 * 		"createDate":"",
	 * 		"updateDate":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午2:33:36
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectNewCardYearDatas"})
	@ResponseBody
	public ResultEntity selectNewCardYearDatas (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			if (StringUtils.isNotBlank(json)) {
				NewCardYearDataEntity newCardYearDataEntity = JsonUtils.fromJson(json, NewCardYearDataEntity.class);
				List<NewCardYearDataEntity> list = newCardYearDataService.queryNewCardYearDatas(newCardYearDataEntity);
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
	@RequestMapping("/getAllNewCardYear")
	@ResponseBody
	public ResultEntity getAllNewCardYear(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<NewCardYearDataEntity> list = newCardYearDataService.getAllNewCardYear();
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	
}
