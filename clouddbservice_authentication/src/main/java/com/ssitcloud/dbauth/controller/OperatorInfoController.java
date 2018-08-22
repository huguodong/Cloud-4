package com.ssitcloud.dbauth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.entity.OperatorInfoEntity;
import com.ssitcloud.dbauth.service.OperatorInfoService;
import com.ssitcloud.dbauth.utils.LogUtils;

/**
 * <p>2016年4月5日 下午1:36:46
 * @author hjc
 *
 */
@Controller
@RequestMapping("/operator")
public class OperatorInfoController {
	@Resource
	private OperatorInfoService operatorInfoService;
	
	/**
	 * 新增一条用户信息，如手机号，地址，或其他
	 * 
	 * <p>2016年4月5日 下午6:14:47
	 * <p>create by hjc
	 * @param operInfo 一条数据参数，如{"operator_idx":"1",infotype_idx:"1",info_value:"phone"}
	 * @param request
	 * @return
	 */
	@RequestMapping("/addOperatorInfo")
	@ResponseBody
	public ResultEntity addOperatorInfo(String operInfo, HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		ObjectMapper mapper = new ObjectMapper();
		OperatorInfoEntity operatorInfoEntity = new OperatorInfoEntity();
		try {
			operatorInfoEntity = mapper.readValue(operInfo, OperatorInfoEntity.class);
			int ret = operatorInfoService.addOperatorInfo(operatorInfoEntity);
			if (ret >= 1) {
				resultEntity.setValue(true, "success","",operatorInfoEntity);
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
	 * 通过map集合查询符合条件的记录
	 *  格式
	 * json = {
	 * 		"library_idx":"",
	 * 		"infotype_idx":""
	 * }
	 * author huanghuang
	 * 2017年3月13日 下午5:26:16
	 * @param operInfo
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectOperatorInfos")
	@ResponseBody
	public ResultEntity selectOperatorInfos(String operInfo, HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			String json = request.getParameter("req");
			List<OperatorInfoEntity> list = new ArrayList<OperatorInfoEntity>();
			JSONObject condition = JSONObject.fromObject(json);
			Map<String, Object> params = jsonToMap(condition);
			list = operatorInfoService.selectOperatorInfos(params);
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
	 * JSONObject转Map
	 * author huanghuang
	 * 2017年3月13日 下午5:32:50
	 * @param jsonObject
	 * @return
	 * @throws JSONException
	 */
	public static Map<String,Object> jsonToMap(JSONObject jsonObject) throws JSONException {
		Map<String,Object> result = new HashMap<String,Object>();
		Iterator iterator = jsonObject.keys();
		String key = null;
		String value = null;
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			value = jsonObject.getString(key);
			result.put(key, value);
		}
		return result;
		
	}
}
