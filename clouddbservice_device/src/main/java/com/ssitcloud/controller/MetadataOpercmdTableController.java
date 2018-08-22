package com.ssitcloud.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.entity.MetadataOpercmdTableEntity;
import com.ssitcloud.service.MetadataOpercmdTableService;


/**
 * 设备上传数据命令码对应mongodb库对照
 *
 * <p>2017年8月28日 下午4:32:27  
 * @author hjc 
 *
 */
@Controller
@RequestMapping("/metaopercmdtable")
public class MetadataOpercmdTableController {

	@Resource
	private MetadataOpercmdTableService metadataOpercmdTableService;
	
	
	/**
	 * 获取所有的命令码与表对照数据
	 *
	 * <p>2017年8月29日 上午10:36:44 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryAllOpercmdTable")
	@ResponseBody
	public ResultEntity queryAllOpercmdTable(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<MetadataOpercmdTableEntity> list = metadataOpercmdTableService.queryAllOpercmdTable();
			resultEntity.setValue(true, "", "", list);
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	
	
}
