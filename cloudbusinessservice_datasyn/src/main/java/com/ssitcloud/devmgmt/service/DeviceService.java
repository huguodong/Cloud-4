package com.ssitcloud.devmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface DeviceService {
	/**
	 * 1.将机器开关等机命令心跳信息存储到heartbeat 
	 * 2.将指令存储到队列中
	 * @methodName: transferData
	 * @param json
	 * @return
	 * @returnType: RespEntity
	 * @author: liuBh
	 */
	ResultEntity transforOrderData(String json);
	/**
	 * 1.同步数据心跳 
	 * 2.将同步数据存储在队列中
	 * @methodName: transforChangeTableData
	 * @param req
	 * @return
	 * @returnType: RespEntity
	 * @author: liubh
	 * @createTime: 2016年4月22日 
	 * @description: TODO
	 */
	ResultEntity transforChangeTableData(String req);
	
	
	ResultEntity queryDeviceLog(String req);
	
	/**
	 * 轮询  设备端 命令执行 结果
	 * req={"device_id":"...","library_id":""} //operator_id 可加上
	 * @param req 
	 * @return
	 */
	
	ResultEntity queryControlResult(String req);
	
	/**
	 *下载日志请求 
	 * @param req
	 * @return
	 */
	ResultEntity downloadRunLogOperation(String req);
	/**
	 * result 返回数组
	 * @param req
	 * @return
	 */
	ResultEntity getLastHeatBeatTime(String req);
	
	/**
	 * 接收手机发送过来的数据
	 *
	 * <p>2017年4月8日 下午4:13:19 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	ResultEntity transforAppData(String req);
	
	public ResultEntity sendOrderToDevice(String req);
	
	public ResultEntity downloadConfig(String req);

}
