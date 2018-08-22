package com.ssitcloud.business.app.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.app.service.MyDocumentServiceI;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.common.util.LogUtils;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.MyDocumentEntity;

import net.sf.json.util.JSONUtils;

@Controller
@RequestMapping("/myDocument")
public class MyDocumentController {
	@Autowired
	private MyDocumentServiceI myDocumentService;
	
	/**
	 * 插入MyDocument
	 * @param request 应该包含json={MyDocumentEntity实体}
	 * @return
	 */
	@RequestMapping("/insertMyDocument")
	@ResponseBody
	public ResultEntity insertMyDocument(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		
		String json = request.getParameter("json");
		if(json == null || !JSONUtils.mayBeJSON(json)){
			resultEntity.setValue(false, "没有json参数或者json格式不正确");
			return resultEntity;
		}
		//检查实体是否为合格的实体
		try{
			MyDocumentEntity check = JsonUtils.fromJson(json, MyDocumentEntity.class);
			check.setCreatetime(new Timestamp(System.currentTimeMillis()));
			json = JsonUtils.toJson(check);
		}catch(Exception e){
			resultEntity.setValue(false, "解析json出错，请检查json格式");
			return resultEntity;
		}
		try{
			ResultEntity resultData = myDocumentService.insertMyDocument(json);
			resultEntity = resultData;
		}catch(Exception e){
			LogUtils.error(e);
			resultEntity.setState(false);
			resultEntity.setMessage(e.getMessage());
		}
		return resultEntity;
	}
	
	/**
	 * 通过主键查询MyDocument
	 * @param document_idx 主键id
	 * @return
	 */
	@RequestMapping("/selectMyDocumentByPk")
	@ResponseBody
	public ResultEntity selectMyDocumentByPk(Integer document_idx){
		ResultEntity resultEntity = new ResultEntity();
		if(document_idx == null){
			resultEntity.setValue(false, "没有查询的主键参数");
			return resultEntity;
		}
		try{
			MyDocumentEntity documentEntity = myDocumentService.queryOneMyDocument(document_idx);
	
			resultEntity.setState(true);
			resultEntity.setResult(documentEntity);
		}catch(Exception e){
			LogUtils.error(e);
			resultEntity.setState(false);
			resultEntity.setMessage(e.getMessage());
		}
		
		return resultEntity;
	}
	
	/**
	 * 模糊查询MyDocument
	 * @param 应该包含json={MyDocumentQueryEntity实体}
	 * @return
	 */
	@RequestMapping("/selectMyDocuments")
	@ResponseBody
	public ResultEntity selectMyDocuments(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		String json = request.getParameter("json");
		if(json == null || !JSONUtils.mayBeJSON(json)){
			resultEntity.setValue(false, "请检查提交参数");
			return resultEntity;
		}
		
		try{
			List<MyDocumentEntity> data = myDocumentService.queryMyDocuments(json);
			resultEntity.setState(true);
			resultEntity.setResult(data);
		}catch(Exception e){
			LogUtils.error(e);
			resultEntity.setState(false);
			resultEntity.setMessage(e.getMessage());
		}
		
		return resultEntity;
	}
	
	/**
	 * 通过主键删除MyDocument
	 * @param document_idx 主键id
	 * @return
	 */
	@RequestMapping("/deleteMyDocumentByPk")
	@ResponseBody
	public ResultEntity deleteMyDocumentByPk(Integer document_idx){
		ResultEntity resultEntity = new ResultEntity();
		if(document_idx == null){
			resultEntity.setValue(false, "没有查询的主键参数");
			return resultEntity;
		}
		
		try{
			boolean result = myDocumentService.deleteMyDocument(document_idx);
			resultEntity.setState(result);
		}catch(Exception e){
			LogUtils.error(e);
			resultEntity.setState(false);
			resultEntity.setMessage(e.getMessage());
		}
		
		return resultEntity;
	}
	
	/**
	 * 收藏夹没有更新，不开放接口
	 */
//	/**
//	 * 更新MyDocument
//	 * @param request 应该包含json={MyDocumentEntity实体，必须设置主键id}
//	 * @return
//	 */
//	@RequestMapping("/updateMyDocument")
//	@ResponseBody
//	public ResultEntity updateMyDocument(HttpServletRequest request){
//		ResultEntity resultEntity = new ResultEntity();
//		
//		String json = request.getParameter("json");
//		if(json == null || !JSONUtils.mayBeJSON(json)){
//			resultEntity.setValue(false, "没有json参数或者json格式不正确");
//			return resultEntity;
//		}
//		//检查实体是否为合格的实体
//		try{
//			MyDocumentEntity check = JsonUtils.fromJson(json, MyDocumentEntity.class);
//			if(check.getDocument_idx() == null){
//				resultEntity.setValue(false, "没有设置主键");
//				return resultEntity;
//			}
//		}catch(Exception e){
//			resultEntity.setValue(false, "解析json出错，请检查json格式");
//			return resultEntity;
//		}
//		
//		return myDocumentService.updateMyDocument(json);
//	}
}
