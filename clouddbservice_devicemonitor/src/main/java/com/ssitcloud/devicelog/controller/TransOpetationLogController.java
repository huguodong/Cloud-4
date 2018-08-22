package com.ssitcloud.devicelog.controller;

import java.util.concurrent.Callable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.devicelog.service.TransDataService;
import com.ssitcloud.devicelog.service.impl.TransOperationLogServiceImpl;
/**
 * 用于处理设备端的请求信息
 * @package: com.ssitcloud.common.controller
 * @classFile: DataSyncController
 * @author: liuBh
 * @description: TODO
 */
@RestController
@RequestMapping(value={"transLog"})
public class TransOpetationLogController {
	
	@Resource
	private TransDataService transDataService;
	
	@Resource
	private TransOperationLogServiceImpl transOperationLogService;
	
	/**
	 * 异步处理操作日志接口
	 * req={"servicetype":"ssitcloud","target":"ssitcloud","operation":"transData","data":{"library_id":"${LibID}","device_id":"${DevId}","content":{"ext_state":{"Printer":"1","AuthorityReader":"0","CardDispenser":"1","CashAcceptor":"1","IdentityReader":"1","ItemRfidReader":"0","PlcSSL":{"PlcShelfTurnCom":"1","PlcMechanicalArmCom":"1","PlcReturnCom":"1","PlcLoadCom":"1"}}},"object":"state_data"}}
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"dealOperationLog"})
	@ResponseBody
	public Callable<RespEntity> dealOperationLog(HttpServletRequest request,final String req){
		 return new Callable<RespEntity>() { 
	        	@Override
	            public RespEntity call() throws Exception {
	        		
					return transOperationLogService.execute(req);
	            }  
	        };
	}
	/**
	 * 异步处理操作日志接口
	 * req={"servicetype":"ssitcloud","target":"ssitcloud","operation":"transData","data":{"library_id":"${LibID}","device_id":"${DevId}","content":{"ext_state":{"Printer":"1","AuthorityReader":"0","CardDispenser":"1","CashAcceptor":"1","IdentityReader":"1","ItemRfidReader":"0","PlcSSL":{"PlcShelfTurnCom":"1","PlcMechanicalArmCom":"1","PlcReturnCom":"1","PlcLoadCom":"1"}}},"object":"state_data"}}
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"dealTransData"})
	@ResponseBody
	public Callable<RespEntity> dealTransData(HttpServletRequest request,final String req){
		 return new Callable<RespEntity>() { 
	        	@Override
	            public RespEntity call() throws Exception {
	        		
					return transDataService.execute(req);  
	            }  
	        };
	}
	
}
