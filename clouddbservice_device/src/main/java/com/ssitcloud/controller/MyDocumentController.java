package com.ssitcloud.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.MyDocumentEntity;
import com.ssitcloud.entity.page.MyDocumentQueryEntity;
import com.ssitcloud.service.MyDocumentService;

@Controller
@RequestMapping(value={"myDocument"})
public class MyDocumentController {
	@Resource
	private MyDocumentService myDocumentService;
	
	/**
	 * 新增我的收藏夹MyDocumentEntity
	 * 格式
	 * json = {
	 * 		"document_idx":"",//我的收藏夹表主键，自增
	 * 		"reader_idx":"",
	 * 		"barcode":"",
	 * 		"document_desc":"",
	 * 		"createtime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:14
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"addMyDocument"})
	@ResponseBody
	public ResultEntity addMyDocument (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) { 
				MyDocumentEntity myDocumentEntity = JsonUtils.fromJson(json, MyDocumentEntity.class);
				int ret = myDocumentService.insertMyDocument(myDocumentEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",myDocumentEntity);
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
	 * 修改我的收藏夹MyDocumentEntity
	 * 格式
	 * json = {
	 * 		"document_idx":"",//我的收藏夹表主键，自增
	 * 		"reader_idx":"",
	 * 		"barcode":"",
	 * 		"document_desc":"",
	 * 		"createtime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:42:33
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"updateMyDocument"})
	@ResponseBody
	public ResultEntity updateMyDocument (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				MyDocumentEntity myDocumentEntity = JsonUtils.fromJson(json, MyDocumentEntity.class);
				int ret = myDocumentService.updateMyDocument(myDocumentEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",myDocumentEntity);
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
	 * 删除我的收藏夹MyDocumentEntity
	 * 格式
	 * json = {
	 * 		"document_idx":"",//我的收藏夹表主键，自增
	 * 		"reader_idx":"",
	 * 		"barcode":"",
	 * 		"document_desc":"",
	 * 		"createtime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:32
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"deleteMyDocument"})
	@ResponseBody
	public ResultEntity deleteMyDocument (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				MyDocumentEntity myDocumentEntity = JsonUtils.fromJson(json, MyDocumentEntity.class);
				int ret = myDocumentService.deleteMyDocument(myDocumentEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",myDocumentEntity);
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
	 * 查询一条我的收藏夹记录MyDocumentEntity
	 * 格式
	 * json = {
	 * 		"document_idx":"",//我的收藏夹表主键，自增
	 * 		"reader_idx":"",
	 * 		"barcode":"",
	 * 		"document_desc":"",
	 * 		"createtime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectMyDocument"})
	@ResponseBody
	public ResultEntity selectMyDocument (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				MyDocumentEntity myDocumentEntity = JsonUtils.fromJson(json, MyDocumentEntity.class);
				myDocumentEntity = myDocumentService.queryOneMyDocument(myDocumentEntity);
				resultEntity.setValue(true, "success","",myDocumentEntity);
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
	 * 查询多条我的收藏夹记录MyDocumentEntity
	 * 格式
	 * json = {
	 * 		"document_idx":"",//我的收藏夹表主键，自增
	 * 		"reader_idx":"",
	 * 		"barcode":"",
	 * 		"document_desc":"",
	 * 		"createtime":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectMyDocuments"})
	@ResponseBody
	public ResultEntity selectMyDocuments (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			MyDocumentQueryEntity myDocumentEntity = JsonUtils.fromJson(json, MyDocumentQueryEntity.class);
			List<MyDocumentEntity> list = myDocumentService.queryMyDocuments(myDocumentEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	
}
