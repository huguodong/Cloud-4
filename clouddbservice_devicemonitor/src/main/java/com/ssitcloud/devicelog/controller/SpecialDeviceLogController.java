package com.ssitcloud.devicelog.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.devicelog.service.DeviceStateService;


/**
 * 特殊设备处理类：
 * @author yeyalin 
 *
 */
@Controller
@RequestMapping(value={"specialdevicelog"})
public class SpecialDeviceLogController {

	@Resource
	private DeviceStateService deviceStateService;
	/**
	 *  #String transData(String conditionInfo)    『mongoDB』
		#conditionInfo输入内容如下：
		#字段名称	类型	说明
		#device_id	String	设备ID
		#library_id	String	馆ID
		#object		String	对象（状态数据还是操作书架日志）state_data/oper_log

		{
			"data":{
			  "device_id": "RLL_001",
			  "library_id": "QJTSG",
			  "object": "state_data",
			  "content": {
				    "soft_state": {
				    
					      "Function": {
					        "00040103": "1",
					        "00040101": "0",
					        "00040102": "0",
					        "00040104": "0"
					      },
					      "Main": {
					        "StartupTime": "2017-05-04 14:09:26",
					        "UpdateTime": "2017-05-04 16:20:13"
					      }
				    },
				    
				    "ext_state": {
				      "RLL_DEV_1": "0",
				      "RLL_DEV_2": "1",
				      "RLL_DEV_3": "0",
				      "RLL_DEV_4": "0"
				    }
			  }
			}
	}

	* @methodName: stateReceive
	 * @param request
	 * @param req
	 * @return
	 * @returnType: ResultEntityF<Object>
	 * @author: liuBh
	 */
	@RequestMapping(value="transDataToSpecialDevice")
	@ResponseBody
	public ResultEntityF<Object> transData(HttpServletRequest request,String req){
		System.out.println("----------特殊设备操作日志接口,transDataToSpecialDevice,req=" + req);
		return deviceStateService.transDataToSpecialDevice(req);
	}
}
