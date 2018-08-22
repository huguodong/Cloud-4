package com.ssitcloud.dbauth.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.entity.MetadataInfotypeEntity;
import com.ssitcloud.dbauth.service.MetadataInfotypeService;
import com.ssitcloud.dbauth.utils.LogUtils;

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
	private MetadataInfotypeService infotypeService;

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
			if (!StringUtils.isBlank(infotype)) {
				infotypeEntity = mapper.readValue(infotype, MetadataInfotypeEntity.class);
				infotypeService.addMetadataInfotype(infotypeEntity);
				if (infotypeEntity.getInfotype_idx()!=null) {
					resultEntity.setValue(true, "success","",infotypeEntity);
				}else{
					resultEntity.setValue(false, "failed");
				}
			}else{
				resultEntity.setValue(false, "failed");
			}
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
			List<MetadataInfotypeEntity> list = infotypeService.selMetadataInfotype();
			resultEntity.setValue(true, "success","",list);
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
			infotypeEntity = mapper.readValue(infotype, MetadataInfotypeEntity.class);
			int ret = infotypeService.delMetadataInfotypeByIdx(infotypeEntity);
			if (ret >= 1) {
				resultEntity.setValue(true, "success");
			}else {
				resultEntity.setValue(false, "failed");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	/**
	 * 
	 * @comment
	 * @param infotype
	 * @param request
	 * @return
	 * @data 2016年5月26日`
	 * @author hwl
	 */
	@RequestMapping("/selLibInfotype")
	@ResponseBody
	public ResultEntity GetLibInfotype(String infotype, HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<MetadataInfotypeEntity> list = infotypeService.sellibdataInfotype();
			
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
}
