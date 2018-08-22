package com.ssitcloud.businessauth.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.authentication.entity.MetadataInfotypeEntity;
import com.ssitcloud.businessauth.service.MetadataInfotypeService;
import com.ssitcloud.businessauth.utils.JsonUtils;
import com.ssitcloud.businessauth.utils.LogUtils;
import com.ssitcloud.common.entity.ResultEntity;


/** 
 *
 * <p>2016年3月30日 下午2:06:29  
 * @author hjc 
 *
 */
@Controller
@RequestMapping("/metadata")
public class MetadataInfotypeController {

	@Resource
	MetadataInfotypeService metadataInfotypeService;
	/**
	 * 新增元数据
	 *
	 * <p>2016年3月30日 下午2:11:47 
	 * <p>create by hjc
	 * @param infotype
	 * @param request
	 * @return ResultEntity结果集
	 */
	@RequestMapping("/addMetadataInfotype")
	@ResponseBody
	public ResultEntity addMetadataInfotype(String infotype,HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		ObjectMapper mapper = new ObjectMapper();
		MetadataInfotypeEntity infotypeEntity = new MetadataInfotypeEntity();
		try {
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 获取所有的元数据
	 *
	 * <p>2016年3月30日 下午4:34:27 
	 * <p>create by hjc
	 * @param infotype
	 * @param request
	 * @return 结果集
	 */
	@RequestMapping("/selMetadataInfotype")
	@ResponseBody
	public ResultEntity getMetadataInfotype(String infotype, HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			/*Map<String, String> map = new HashMap<>();
			map.put("infotype", infotype);
			String result = metadataInfotypeService.getLibInfotype(map);
			resultEntity = JsonUtils.fromJson(result, ResultEntity.class);*/
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 根据元数据表的infotype_idx删除信息
	 * 
	 * <p>2016年4月6日 下午7:16:46
	 * <p>create by hjc
	 * @param infotype json参数如{infotype_idx："1"}
	 * @param request
	 * @return 
	 */
	@RequestMapping("/delMetadataInfotypeByIdx")
	@ResponseBody
	public ResultEntity delMetadataInfotypeByIdx(String infotype, HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		ObjectMapper mapper = new ObjectMapper();
		MetadataInfotypeEntity infotypeEntity = new MetadataInfotypeEntity();
		try {
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	@RequestMapping("/selLibInfotype")
	@ResponseBody
	public ResultEntity GetLibInfotype(String infotype, HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String, String> map = new HashMap<>();
			map.put("infotype", infotype);
			String result = metadataInfotypeService.getLibInfotype(map);
			resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
}
