package com.ssitcloud.business.devmgmt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.devmgmt.service.MetaLogicObjService;
import com.ssitcloud.common.entity.ResultEntity;

@Controller
@RequestMapping(value={"metalogic"})
public class MetaLogicObjController {

	@Resource
	private MetaLogicObjService metaLogicObjService;
	
	/**
	 * 查询所有的逻辑设备
	 *
	 * <p>2016年5月7日 下午2:51:29 
	 * <p>create by hjc
	 * @param request
	 * @param json
	 * @return
	 */
	@RequestMapping(value="/SelectMetadataLogicObj")
	@ResponseBody
	public ResultEntity SelectMetadataLogicObj(HttpServletRequest request,String json){
		ResultEntity result =new ResultEntity();
		try {
			String response = metaLogicObjService.SelectMetadataLogicObj(json);
			if (response!=null) {
				result = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				result.setState(false);
				result.setMessage("连接服务器失败");
			}
		} catch (Exception e) {
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(result, methodName, e);
		}
		return result;
	}
	
}
