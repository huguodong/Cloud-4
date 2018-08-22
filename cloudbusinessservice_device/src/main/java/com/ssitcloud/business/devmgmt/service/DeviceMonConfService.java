package com.ssitcloud.business.devmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface DeviceMonConfService {

	ResultEntity queryDeviceMonitorByParam(String req);

	ResultEntity AddNewDeviceMonitorConfAndTemp(String req);

	ResultEntity UpdNewDeviceMonitorConfAndTemp(String req);
	/**
	 * 删除
	 * @param req
	 * @return
	 */
	ResultEntity DelNewDeviceMonitorConfAndTemp(String req);

	ResultEntity AddNewDeviceMonitorTemp(String req);

	ResultEntity SelPlcLogicObjectEntity();

	ResultEntity SelDevMonConfMetaLogObjByDeviceMonTplIdx(String req);

	ResultEntity queryAlertColor_bus(String req);

	/**
	 * 设备监控配置操作
	 * @comment
	 * @param json
	 * @return
	 * @data 2016年6月13日
	 * @author hwl
	 */
	ResultEntity UpdMonitor(String json);
	
	ResultEntity AddMonitor(String json);
	
	ResultEntity DelMonitor(String json);
	
	ResultEntity SelMonitor(String json);
	/**
	 * 获取当前页面的 设备设置的监控颜色
	 * @param req
	 * @return
	 */
	ResultEntity GetCurrentDevColorSet(String req);
}
