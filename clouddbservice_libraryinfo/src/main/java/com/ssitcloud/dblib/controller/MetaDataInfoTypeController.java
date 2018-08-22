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
import com.ssitcloud.dblib.entity.MetaDataInfoTypeEntity;
import com.ssitcloud.dblib.service.MetaDataInfoTypeService;

@Controller
@RequestMapping(value={"metaDataInfoType"})
public class MetaDataInfoTypeController {
	@Resource
	private MetaDataInfoTypeService metaDataInfoTypeService;
	
	/**
	 * 查询一条读者信息原始字段记录MetaDataInfoTypeEntity
	 * 格式
	 * json = {
	 * 		"infotype_idx":"",//读者信息原始字段主键，自增
	 * 		"info_type_desc":""
	 * }
	 * author huanghuang
	 * 2017年2月9日 下午1:43:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectMetaDataInfoType.do"})
	@ResponseBody
	public ResultEntity selectMetaDataInfoType (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			if (StringUtils.isNotBlank(json)) {
				MetaDataInfoTypeEntity metaDataInfoTypeEntity = JsonUtils.fromJson(json, MetaDataInfoTypeEntity.class);
				metaDataInfoTypeEntity = metaDataInfoTypeService.queryOneMetaDataInfoType(metaDataInfoTypeEntity);
				resultEntity.setValue(true, "success","",metaDataInfoTypeEntity);
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
	 * 查询多条读者信息原始字段记录MetaDataInfoTypeEntity
	 * author huanghuang
	 * 2017年2月9日 下午1:44:03
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"selectMetaDataInfoTypes.do"})
	@ResponseBody
	public ResultEntity selectMetaDataInfoTypes (HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("json");
			MetaDataInfoTypeEntity metaDataInfoTypeEntity = JsonUtils.fromJson(json, MetaDataInfoTypeEntity.class);
			List<MetaDataInfoTypeEntity> list = metaDataInfoTypeService.queryMetaDataInfoTypes(metaDataInfoTypeEntity);
			resultEntity.setValue(true, "success","",list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	
}
