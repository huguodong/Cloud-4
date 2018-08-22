package com.ssitcloud.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.service.AppVersionInfoService;


/**
 * app私钥版本信息controller
 *
 * <p>2017年4月6日 下午3:47:58  
 * @author hjc 
 *
 */
@Controller
@RequestMapping("appinfo")
public class AppVersionInfoController {
	
	@Resource
	private AppVersionInfoService appVersionInfoService;
	
	/**
	 * 获取所有可用的私钥信息，
	 * result返回的字段
	 * 
	 *  app_type	int	系统类型 1安卓 2苹果
		key_version	String	密钥版本号
		privatekey	String	私钥
		createtime	timestamp	创建时间

	 *
	 * <p>2017年4月6日 下午4:37:45 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllUsefulAppVersionInfo")
	@ResponseBody
	public ResultEntity getAllUsefulAppVersionInfo(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			List<Map<String, Object>> list = appVersionInfoService.getAllUsefulAppVersionInfo();
			resultEntity.setValue(true, "", "", list);
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}

}
