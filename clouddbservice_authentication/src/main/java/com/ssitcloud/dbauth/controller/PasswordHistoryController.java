package com.ssitcloud.dbauth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.entity.OperatorEntity;
import com.ssitcloud.dbauth.entity.PasswordHistoryEntity;
import com.ssitcloud.dbauth.service.PasswordHistoryService;
import com.ssitcloud.dbauth.utils.LogUtils;

/**
 * <p>2016年4月5日 下午1:52:38
 * @author hjc
 *
 */
@Controller
@RequestMapping("/operator")
public class PasswordHistoryController {
	@Resource
	private PasswordHistoryService pHistoryService;
	
	/**
	 * 新增历史密码数据
	 * 
	 * <p>2016年4月5日 下午7:00:49
	 * <p>create by hjc
	 * @param passwordInfo 历史密码数据，如{operator_idx："1",password:"ASLDJsdaskdjqwi123asldjaLJ",modifyTime:1459855105000}
	 * @param request
	 * @return ResultEntity 结果集
	 */
	@RequestMapping("/addPasswordHistory")
	@ResponseBody
	public ResultEntity addPasswordHistory(String passwordInfo, HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		ObjectMapper mapper = new ObjectMapper();
		PasswordHistoryEntity historyEntity = new PasswordHistoryEntity();
		try {
			historyEntity = mapper.readValue(passwordInfo, PasswordHistoryEntity.class);
			int ret = pHistoryService.addPasswordHistory(historyEntity);
			if (ret >= 1) {
				resultEntity.setState(true);
				resultEntity.setMessage("success");
				resultEntity.setResult(historyEntity);
			}else {
				resultEntity.setState(false);
				resultEntity.setMessage("failed");
			}
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setState(false);
			resultEntity.setMessage("failed");
			resultEntity.setRetMessage(methodName+"()异常"+e.getMessage());
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	
	/**
	 * 
	 * 
	 * <p>2016年4月6日 下午2:14:25
	 * <p>create by hjc
	 * @param operInfo
	 * @param request
	 * @return
	 */
	@RequestMapping("/selPasswordHistorysByOperInfo")
	@ResponseBody
	public ResultEntity selPasswordHistorys(String operInfo, HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		ObjectMapper mapper = new ObjectMapper();
		OperatorEntity operatorEntity = new OperatorEntity();
		try {
			operatorEntity = mapper.readValue(operInfo, OperatorEntity.class);
			int ret = 0;
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setState(false);
			resultEntity.setMessage("failed");
			resultEntity.setRetMessage(methodName+"()异常"+e.getMessage());
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
	/**
	 * 根据历史密码的password_idx删除密码信息
	 * 
	 * <p>2016年4月6日 下午8:10:36
	 * <p>create by hjc
	 * @param passwordInfo json参数 如{password_idx："1"}
	 * @param request
	 * @return
	 */
	@RequestMapping("/delPasswordHistoryByIdx")
	@ResponseBody
	public ResultEntity delPasswordHistoryByIdx(String passwordInfo,HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		ObjectMapper mapper = new ObjectMapper();
		PasswordHistoryEntity historyEntity = new PasswordHistoryEntity(); 
		try {
			historyEntity = mapper.readValue(passwordInfo, PasswordHistoryEntity.class);
			
		} catch (Exception e) {
			//获取当前方法名称
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			resultEntity.setValue(false, "failed", methodName+"()异常"+e.getMessage(), "");
			LogUtils.error(methodName+"()异常", e);
		}
		return resultEntity;
	}
	
}
