package com.ssitcloud.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.MetadataOrderEntity;
import com.ssitcloud.service.MetadataOrderService;

/**
 * 
 * @comment 监控指令元数据表Controller
 * 
 * @author hwl
 * @data 2016年4月9日
 */
@Controller
@RequestMapping("/metaorder")
public class MetadataOrderController {

	@Resource
	private MetadataOrderService metadataOrderService;
	
	/**
	 * 新增监控指令数据
	 * 数据格式
	 * {
	 * 		"order_idx":"",
	 * 		"order_desc":""，
	 * 		"order_cmd":""，
	 * 		"order_os":""
	 * }
	 * 
	 * @param req
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value={"AddMetadataOrder"})
	@ResponseBody
	public ResultEntity AddMetadataOrder(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			int re=metadataOrderService.addMetadataOrder(JsonUtils.fromJson(req.getParameter("json"), MetadataOrderEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);	
		}
		return result;
	}
	
	/**
	 * 更新监控指令数据
	 * 数据格式
	 * {
	 * 		"order_idx":"",
	 * 		"order_desc":""，
	 * 		"order_cmd":""，
	 * 		"order_os":""
	 * }
	 * @param req
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value={"UpdMetadataOrder"})
	@ResponseBody
	public ResultEntity UpdMetadataOrder(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			int re=metadataOrderService.updateMetadataOrder(JsonUtils.fromJson(req.getParameter("json"), MetadataOrderEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
	/**
	 * 删除监控指令元数据
	 * @param req
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value={"DelMetadataOrder"})
	@ResponseBody
	public ResultEntity DelMetadataOrder(HttpServletRequest req){
		ResultEntity result=new ResultEntity();
		try {
			String json=req.getParameter("json");
			int re=metadataOrderService.deleteMetadataOrder(JsonUtils.fromJson(json, MetadataOrderEntity.class));
			result.setState(re>=1?true:false);
			result.setMessage(re>=1?Const.SUCCESS:Const.FAILED);
			
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 条件查询监控指令元数据
	 * @param req
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value={"SelMetadataOrder"})
	@ResponseBody
	public ResultEntityF<List<MetadataOrderEntity>> SelMetadataOrder(HttpServletRequest req){
		ResultEntityF<List<MetadataOrderEntity>> result=new ResultEntityF<List<MetadataOrderEntity>>();
		try {
			List<MetadataOrderEntity> metaorder=metadataOrderService.selectMetadataOrder(JsonUtils.fromJson(req.getParameter("json"), MetadataOrderEntity.class));
			result.setResult(metaorder);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
