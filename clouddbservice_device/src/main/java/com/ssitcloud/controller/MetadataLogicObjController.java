package com.ssitcloud.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.entity.MetadataLogicObjEntity;
import com.ssitcloud.service.MetadataLogicObjService;

/**
 * 
 * @comment 逻辑对象元数据表Controller
 * 
 * @author hwl
 * @data 2016年4月11日
 */
@Controller
@RequestMapping("/metalogicobj")
public class MetadataLogicObjController {

	@Resource
	MetadataLogicObjService metadataLogicObjService;

	/**
	 * 新建逻辑对象元数据 
	 * 格式(meta_log_obj_idx自增id，不用输入) 
	 * { 
	 * "meta_log_obj_idx":"",
	 * "logic_obj":"" ,
	 * "logic_obj_desc":""
	 *  }
	 * 
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "AddMetadataLogicObj" })
	@ResponseBody
	public ResultEntity AddMetadataLogicObj(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			String json = request.getParameter("json");
			int re = metadataLogicObjService.addMetadataLogicObj(JsonUtils
					.fromJson(json, MetadataLogicObjEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 更新逻辑对象元数据 
	 * 格式
	 * { 
	 * "meta_log_obj_idx":"",
	 * "logic_obj":"" ,
	 * "logic_obj_desc":""
	 *  }
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "UpdateMetadataLogicObj" })
	@ResponseBody
	public ResultEntity UpdateMetadataLogicObj(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			String json = request.getParameter("json");
			int re = metadataLogicObjService.updateMetadataLogicObj(JsonUtils
					.fromJson(json, MetadataLogicObjEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 删除逻辑对象元数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "DeleteMetadataLogicObj" })
	@ResponseBody
	public ResultEntity DeleteMetadataLogicObj(HttpServletRequest request) {
		ResultEntity result = new ResultEntity();
		try {
			String json = request.getParameter("json");
		    int re = metadataLogicObjService.deleteMetadataLogicObj(JsonUtils.fromJson(json, MetadataLogicObjEntity.class));
			result.setState(re >= 1 ? true : false);
			result.setMessage(re >= 1 ? Const.SUCCESS : Const.FAILED);
			
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}

	/**
	 * 条件查询逻辑对象元数据
	 * @param request
	 * @return
	 * @author hwl
	 */
	@RequestMapping(value = { "SelectMetadataLogicObj" })
	@ResponseBody
	public ResultEntity SelectMetadataLogicObj(HttpServletRequest request,String req) {
		ResultEntity result = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if(json==null){
				json=req;
			}
			List<MetadataLogicObjEntity> list = metadataLogicObjService
					.selectMetadataLogicObj(JsonUtils.fromJson(json,
							MetadataLogicObjEntity.class));
			result.setResult(list);
			result.setState(true);
			result.setMessage(Const.SUCCESS);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
}
