package com.ssitcloud.businessauth.controller;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.authentication.entity.OperatorInfoEntity;
import com.ssitcloud.businessauth.utils.LogUtils;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * <p>2016年4月5日 下午1:36:46
 * @author hjc
 *
 */
@Controller
@RequestMapping("/operator")
public class OperatorInfoController {
	
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
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
}
