package com.ssitcloud.dblib.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.ExceptionHelper;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.entity.ReaderCardEntity;
import com.ssitcloud.dblib.service.ReaderCardService;

@Controller
@RequestMapping(value={"/readercard"})
public class ReaderCardController {
	@Resource
	private ReaderCardService readerCardService;
	
	/**
	 * 新增读者关联卡信息ReaderCardEntity
	 * 格式
	 * json = {
	 * 		"reader_idx":"",
	 * 		"lib_idx":"",
	 * 		"card_no":"",
	 * 		"createTime":"",
	 * 		"updateTime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:14
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/addReaderCard"})
	@ResponseBody
	public ResultEntity addReaderCard (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				ReaderCardEntity readerCardEntity = JsonUtils.fromJson(json, ReaderCardEntity.class);
				int ret = readerCardService.insertReaderCard(readerCardEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",readerCardEntity);
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
	 * 修改读者关联卡信息ReaderCardEntity
	 * 格式
	 * json = {
	 * 		"reader_idx":"",
	 * 		"lib_idx":"",
	 * 		"card_no":"",
	 * 		"createTime":"",
	 * 		"updateTime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:33
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/updateReaderCard"})
	@ResponseBody
	public ResultEntity updateReaderCard (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				ReaderCardEntity readerCardEntity = JsonUtils.fromJson(json, ReaderCardEntity.class);
				int ret = readerCardService.updateReaderCard(readerCardEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",readerCardEntity);
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
	 * 删除读者关联卡信息ReaderCardEntity
	 * 格式
	 * json = {
	 * 		"reader_idx":"",
	 * 		"lib_idx":"",
	 * 		"card_no":"",
	 * 		"createTime":"",
	 * 		"updateTime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:32
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/deleteReaderCard"})
	@ResponseBody
	public ResultEntity deleteReaderCard (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				ReaderCardEntity readerCardEntity = JsonUtils.fromJson(json, ReaderCardEntity.class);
				int ret = readerCardService.deleteReaderCard(readerCardEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",readerCardEntity);
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
	 * 查询一条读者关联卡信息记录ReaderCardEntity
	 * 格式
	 * json = {
	 * 		"reader_idx":"",
	 * 		"lib_idx":"",
	 * 		"card_no":"",
	 * 		"createTime":"",
	 * 		"updateTime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectReaderCard"})
	@ResponseBody
	public ResultEntity selectReaderCard (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				ReaderCardEntity readerCardEntity = JsonUtils.fromJson(json, ReaderCardEntity.class);
				readerCardEntity = readerCardService.queryOneReaderCard(readerCardEntity);
				resultEntity.setValue(true, "success","",readerCardEntity);
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
	 * 查询多条读者关联卡信息记录ReaderCardEntity
	 * 格式
	 * json = {
	 * 		"reader_idx":"",
	 * 		"lib_idx":"",
	 * 		"card_no":"",
	 * 		"createTime":"",
	 * 		"updateTime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/selectReaderCards"})
	@ResponseBody
	public ResultEntity selectReaderCards (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			ReaderCardEntity readerCardEntity = JsonUtils.fromJson(json, ReaderCardEntity.class);
			List<ReaderCardEntity> list = readerCardService.queryReaderCards(readerCardEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"/queryReaderCards"})
	@ResponseBody
	public ResultEntity queryReaderCards (HttpServletRequest request,String req){
		ResultEntity resultEntity = new ResultEntity();
		try {
			ReaderCardEntity readerCardEntity = JsonUtils.fromJson(req, ReaderCardEntity.class);
			List<ReaderCardEntity> list = readerCardService.queryReaderCards(readerCardEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"/insertReaderCard"})
	@ResponseBody
	public ResultEntity insertReaderCard (HttpServletRequest request,String req){
		ResultEntity resultEntity = new ResultEntity();
		try {
			ReaderCardEntity readerCardEntity = JsonUtils.fromJson(req, ReaderCardEntity.class);
			int ret = readerCardService.insertReaderCard(readerCardEntity);
			if (ret>0) {
				resultEntity.setValue(true, "success","",readerCardEntity);
			}else{
				resultEntity.setValue(false, "failed");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping(value={"/uptReaderCard"})
	@ResponseBody
	public ResultEntity uptReaderCard (HttpServletRequest request,String req){
		ResultEntity resultEntity = new ResultEntity();
		try {
			ReaderCardEntity readerCardEntity = JsonUtils.fromJson(req, ReaderCardEntity.class);
			int ret = readerCardService.updateReaderCard(readerCardEntity);
			if (ret>0) {
				resultEntity.setValue(true, "success","",readerCardEntity);
			}else{
				resultEntity.setValue(false, "failed");
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping("selectReaderCardByParams")
	@ResponseBody
	public ResultEntity selectReaderCardByParams(String req){
		ResultEntity resultEntity = new ResultEntity();
		try {
			ReaderCardEntity readerCardEntity = JsonUtils.fromJson(req, ReaderCardEntity.class);
			List<ReaderCardEntity> cardEntities = readerCardService.selectReaderCardByParams(readerCardEntity);
			resultEntity.setValue(true, "success","",cardEntities);
			
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping("uploadReaderCard")
	@ResponseBody
	public ResultEntity uploadReaderCard(@RequestParam("file") CommonsMultipartFile file,String req) throws Exception{
		
		return readerCardService.uploadReaderCard(file,req);
	}
	
	
	
	
	
}
