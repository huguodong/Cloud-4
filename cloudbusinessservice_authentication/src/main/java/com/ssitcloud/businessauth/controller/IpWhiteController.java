package com.ssitcloud.businessauth.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.businessauth.service.IpWhiteService;
import com.ssitcloud.businessauth.utils.JsonUtils;
import com.ssitcloud.businessauth.utils.LogUtils;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * ip白名单处理类
 * <p>2016年4月5日 上午11:47:49
 * @author hjc
 *
 */
@Controller
@RequestMapping("/operator")
public class IpWhiteController {
	@Resource
	private IpWhiteService ipWhiteService;
	
	/**
	 * 新增IP白名单
	 * 
	 * <p>2016年4月5日 下午1:56:11
	 * <p>create by hjc
	 * @param ipInfo  json信息，如{operator_idx:1,ipaddr:"127.0.0.1"}
	 * @param request 
	 * @return
	 */
	@RequestMapping("/addIpWhite")
	@ResponseBody
	public ResultEntity addIpWhite(String json, HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("json", json);
			String response = ipWhiteService.addIpWhite(param);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				resultEntity.setValue(false, "请求无响应");
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
	 * 获取白名单
	 * 
	 * <p>2016年4月6日 下午3:17:29
	 * <p>create by hjc
	 * @param param
	 * @param request
	 * @return
	 */
	@RequestMapping("/selIpWhiteByIdx")
	@ResponseBody
	public ResultEntity selIpWhiteByIdx(String json,HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("json", json);
			String response = ipWhiteService.selIpWhiteByIdx(param);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				resultEntity.setValue(false, "请求无响应");
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
	 * 根据历史密码表中的password_idx删除历史密码信息
	 * 
	 * <p>2016年4月6日 下午5:23:00
	 * <p>create by hjc
	 * @param json json信息 如 {operator_idx："1"}
	 * @param request
	 * @return
	 */
	@RequestMapping("/delIpWhiteByOperIdx")
	@ResponseBody
	public ResultEntity delIpWhiteByOperIdx(String json,HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("json", json);
			String response = ipWhiteService.delIpWhiteByOperIdx(param);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				resultEntity.setValue(false, "请求无响应");
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
	 * 修改IP白名单，根据operator_idx修改
	 *
	 * <p>2016年4月21日 上午10:37:40
	 * <p>create by hjc
	 * @param json {operator_idx:"1",ipaddr:"127.0.0.1"}
	 * @param request
	 * @return
	 */
	@RequestMapping("/updIpWhite")
	@ResponseBody
	public ResultEntity updIpWhite(String json,HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			Map<String, String> param = new HashMap<String, String>();
			param.put("json", json);
			String response = ipWhiteService.updIpWhite(param);
			if (response!=null) {
				resultEntity = JsonUtils.fromJson(response, ResultEntity.class);
			}else {
				resultEntity.setValue(false, "请求无响应");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
}
