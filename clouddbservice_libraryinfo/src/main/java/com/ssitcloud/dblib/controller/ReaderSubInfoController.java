package com.ssitcloud.dblib.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.ExceptionHelper;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.entity.ReaderSubInfoEntity;
import com.ssitcloud.dblib.service.ReaderSubInfoService;

@Controller
@RequestMapping(value={"readersubInfo"})
public class ReaderSubInfoController {
	@Resource
	private ReaderSubInfoService bookShelfInfoService;
	
	/**
	 * 新增读者关联基本信息ReaderSubInfoEntity
	 * 格式
	 * json = {
	 * 		"reader_idx":"",
	 * 		"infotype_idx":"",
	 * 		"infotype_value":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:14
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"addReaderSubInfo.do"})
	@ResponseBody
	public ResultEntity addReaderSubInfo (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				ReaderSubInfoEntity readerSubInfoEntity = JsonUtils.fromJson(json, ReaderSubInfoEntity.class);
				int ret = bookShelfInfoService.insertReaderSubInfo(readerSubInfoEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",readerSubInfoEntity);
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
	 * 修改读者关联基本信息ReaderSubInfoEntity
	 * 格式
	 * json = {
	 * 		"reader_idx":"",
	 * 		"infotype_idx":"",
	 * 		"infotype_value":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:33
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"updateReaderSubInfo.do"})
	@ResponseBody
	public ResultEntity updateReaderSubInfo (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				ReaderSubInfoEntity readerSubInfoEntity = JsonUtils.fromJson(json, ReaderSubInfoEntity.class);
				int ret = bookShelfInfoService.updateReaderSubInfo(readerSubInfoEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",readerSubInfoEntity);
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
	 * 删除读者关联基本信息ReaderSubInfoEntity
	 * 格式
	 * json = {
	 * 		"reader_idx":"",
	 * 		"infotype_idx":"",
	 * 		"infotype_value":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:32
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"deleteReaderSubInfo.do"})
	@ResponseBody
	public ResultEntity deleteReaderSubInfo (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				ReaderSubInfoEntity readerSubInfoEntity = JsonUtils.fromJson(json, ReaderSubInfoEntity.class);
				int ret = bookShelfInfoService.deleteReaderSubInfo(readerSubInfoEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",readerSubInfoEntity);
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
	 * 查询一条读者关联基本信息记录ReaderSubInfoEntity
	 * 格式
	 * json = {
	 * 		"reader_idx":"",
	 * 		"infotype_idx":"",
	 * 		"infotype_value":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectReaderSubInfo.do"})
	@ResponseBody
	public ResultEntity selectReaderSubInfo (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				ReaderSubInfoEntity readerSubInfoEntity = JsonUtils.fromJson(json, ReaderSubInfoEntity.class);
				readerSubInfoEntity = bookShelfInfoService.queryOneReaderSubInfo(readerSubInfoEntity);
				resultEntity.setValue(true, "success","",readerSubInfoEntity);
			}else {
				resultEntity.setValue(false, "参数不能为空");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	/**
	 * 查询多条读者关联基本信息记录ReaderSubInfoEntity
	 * 格式
	 * json = {
	 * 		"reader_idx":"",
	 * 		"infotype_idx":"",
	 * 		"infotype_value":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectReaderSubInfos.do"})
	@ResponseBody
	public ResultEntity selectReaderSubInfos (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			ReaderSubInfoEntity readerSubInfoEntity = JsonUtils.fromJson(json, ReaderSubInfoEntity.class);
			List<ReaderSubInfoEntity> list = bookShelfInfoService.queryReaderSubInfos(readerSubInfoEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	//start author lxp --添加一次性插入功能，保证事务
	/**
	 * 删除-->插入对外接口
	 * @param request json=ResultEntity==》{
	 * 						result:List<ReaderSubInfoEntity>--reader_idx必须一致
	 * 					   }
	 * @return state表示执行状态，message包含简单错误信息
	 */
	@RequestMapping("/deleteAndInsert")
	@ResponseBody
	public ResultEntity deleteAndInsert(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if(json == null){
				resultEntity.setValue(false, "没有传入参数");
				return resultEntity;
			}
			ResultEntity argsEntity = JsonUtils.fromJson(json, ResultEntity.class);
			if(argsEntity.getResult() == null || !(argsEntity.getResult() instanceof List)){
				resultEntity.setValue(false, "没有传入参数");
				return resultEntity;
			}
			List<Map<String,Object>> resultList = (List<Map<String, Object>>) argsEntity.getResult();
			for(int i=0,z=resultList.size();i<z;++i){
				Map<String, Object> map = resultList.get(i);
				
				Object idx = map.get("reader_idx");
				Object infotype_idx = map.get("infotype_idx");
				Object infotype_value = map.get("infotype_value");
				ReaderSubInfoEntity rsie = new ReaderSubInfoEntity();
				rsie.setReader_idx((Integer) idx);
				rsie.setInfotype_value((String) infotype_value);
				rsie.setInfotype_idx((Integer) infotype_idx);
				//首次删除
				if(i == 0){
					ReaderSubInfoEntity deleteRsie = new ReaderSubInfoEntity();
					deleteRsie.setReader_idx((Integer) idx);
					bookShelfInfoService.deleteReaderSubInfo(deleteRsie);
				}
				//插入
				bookShelfInfoService.insertReaderSubInfo(rsie);
			}
			resultEntity.setState(true);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
}
