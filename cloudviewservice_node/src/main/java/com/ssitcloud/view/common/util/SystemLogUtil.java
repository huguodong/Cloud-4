package com.ssitcloud.view.common.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.Consts;
import org.springframework.scheduling.annotation.Async;

import com.ssitcloud.common.entity.OperationLogEntity;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.system.BeanFactoryHelper;


/**
 * 
 * 	系统日志 记录工具类
 *   
 * 
 * @author lbh
 *
 */
public class SystemLogUtil {
	public static final String charset=Consts.UTF_8.toString();
	private static final String URL_AddOperationLog = "AddOperationLog";
	private static RequestURLListEntity requestURLList=BeanFactoryHelper.getBean("requestURLListEntity",RequestURLListEntity.class);
	
	/**
	 *   
	 *    operator_idx;
		  client_ip;
		  client_port;
		  operation_cmd;
		  operation_result;
		  operation;
		  error_code;
	 * @param result
	 * @param operator
	 */
	@Async
	public static void LogOperation(ResultEntity result,Operator operator,HttpServletRequest request,String opercmd){
		try {
			OperationLogEntity operationLog=new OperationLogEntity();
			if(result==null) return;
			if(operator==null) return;
			//状态为true并且错误详细信息为空
			operationLog.setOperator_idx(Integer.parseInt(operator.getOperator_idx()));
			String client_ip=WebUtil.getIpAddress(request);
			int port=request.getRemotePort();
			operationLog.setClient_ip(client_ip);
			operationLog.setClient_port(String.valueOf(port));
			operationLog.setOperation_cmd(opercmd);
			operationLog.setOperation_result(String.valueOf(result.getState()));
			operationLog.setOperation(result.getRetMessage());//retMessage
			LogOperation(JsonUtils.toJson(operationLog));
		} catch (Exception e) {
			LogUtils.error("LogOperation 发生异常！！！！",e);
			//不抛出异常，不影响正常的操作
		}
	}
	/**
	 * json格式参照 OperationLogEntity.java
	 * 
	 * 记录用户的操作 日志
	 * @param json
	 * @return
	 */
	private static void LogOperation(String json){
		Map<String, String> map=new HashMap<>();
		map.put("json", json);
		HttpClientUtil.doPost(requestURLList.getRequestURL(URL_AddOperationLog), map, charset);
	}
	
}
