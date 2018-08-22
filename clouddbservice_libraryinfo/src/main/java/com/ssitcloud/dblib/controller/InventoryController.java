package com.ssitcloud.dblib.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.ExceptionHelper;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.entity.InventoryEntity;
import com.ssitcloud.dblib.service.InventoryService;

@Controller
@RequestMapping(value={"inventory"})
public class InventoryController {
	@Resource
	private InventoryService inventoryService;
	
	/**
	 * 新增图书盘点日志InventoryEntity
	 * 格式
	 * json = {
	 * 		"lib_idx":"",
	 * 		"book_barcode":"",
	 * 		"operation":"",
	 * 		"operationresult":"",
	 * 		"operatetime":"",
	 * 		"shelflayer_barcode":"",
	 * 		"shelflayer_barcode_old":"",
	 * 		"remake":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:46:09
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"addInventory.do"})
	@ResponseBody
	public ResultEntity addInventory (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				InventoryEntity inventoryEntity = JsonUtils.fromJson(json, InventoryEntity.class);
				int ret = inventoryService.insertInventory(inventoryEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",inventoryEntity);
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
	 * 修改图书盘点日志InventoryEntity
	 * 格式
	 * json = {
	 * 		"lib_idx":"",
	 * 		"book_barcode":"",
	 * 		"operation":"",
	 * 		"operationresult":"",
	 * 		"operatetime":"",
	 * 		"shelflayer_barcode":"",
	 * 		"shelflayer_barcode_old":"",
	 * 		"remake":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:46:23
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"updateInventory.do"})
	@ResponseBody
	public ResultEntity updateInventory (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				InventoryEntity inventoryEntity = JsonUtils.fromJson(json, InventoryEntity.class);
				int ret = inventoryService.updateInventory(inventoryEntity);
				if (ret>0) {
					resultEntity.setValue(true, "success","",inventoryEntity);
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
	 * 删除图书盘点日志InventoryEntity
	 * 格式
	 * json = {
	 * 		"lib_idx":"",
	 * 		"book_barcode":"",
	 * 		"operation":"",
	 * 		"operationresult":"",
	 * 		"operatetime":"",
	 * 		"shelflayer_barcode":"",
	 * 		"shelflayer_barcode_old":"",
	 * 		"remake":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:46:39
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"deleteInventory.do"})
	@ResponseBody
	public ResultEntity deleteInventory (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				InventoryEntity inventoryEntity = JsonUtils.fromJson(json, InventoryEntity.class);
				int ret = inventoryService.deleteInventory(inventoryEntity);
				if (ret>=0) {
					resultEntity.setValue(true, "success");
				}else{
					resultEntity.setValue(false, "failed");
				}
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
	 * 查询一条图书盘点日志记录InventoryEntity
	 * 格式
	 * json = {
	 * 		"lib_idx":"",
	 * 		"book_barcode":"",
	 * 		"operation":"",
	 * 		"operationresult":"",
	 * 		"operatetime":"",
	 * 		"shelflayer_barcode":"",
	 * 		"shelflayer_barcode_old":"",
	 * 		"remake":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:46:52
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectInventory.do"})
	@ResponseBody
	public ResultEntity selectInventory (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				InventoryEntity inventoryEntity = JsonUtils.fromJson(json, InventoryEntity.class);
				inventoryEntity = inventoryService.queryOneInventory(inventoryEntity);
				resultEntity.setValue(true, "success","",inventoryEntity);
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
	 * 查询多条图书盘点日志记录InventoryEntity
	 * 格式
	 * json = {
	 * 		"lib_idx":"",
	 * 		"book_barcode":"",
	 * 		"operation":"",
	 * 		"operationresult":"",
	 * 		"operatetime":"",
	 * 		"shelflayer_barcode":"",
	 * 		"shelflayer_barcode_old":"",
	 * 		"remake":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:47:06
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectInventories.do"})
	@ResponseBody
	public ResultEntity selectInventories (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			InventoryEntity inventoryEntity = JsonUtils.fromJson(json, InventoryEntity.class);
			List<InventoryEntity> list = inventoryService.queryInventory(inventoryEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	
}
