package com.ssitcloud.devmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface DeviceMonConfService {


	ResultEntity queryDeviceMonitorByParam_view(String req);
	
	/**
	 * 返回 result 中为  hashMap {green:0,yellow:1,red:2}
	 * @param req
	 * @return
	 */
	ResultEntity queryMonitorColorConf(String req);
	/**
	 * 新增
	 * @param req
	 * @return
	 */
	ResultEntity AddNewDeviceMonitorConfAndTemp_view(String req);
	/**
	 * 修改
	 * @param req
	 * @return
	 */
	ResultEntity UpdNewDeviceMonitorConfAndTemp_view(String req);
	/**
	 * 删除
	 * @param req
	 * @return
	 */
	ResultEntity DelNewDeviceMonitorConfAndTemp_view(String req);
	
	/**
	 * 新增监控配置模板
	 * @param req
	 * @return
	 */
	ResultEntity AddDeviceConfigTemplate(String req);

	ResultEntity SelMetadataLogicObject(String req);

	ResultEntity SelPlcLogicObjectEntity();

	ResultEntity SelDevMonConfMetaLogObjByDeviceMonTplIdx(String req);

	ResultEntity queryAlertColor(String req);

}
