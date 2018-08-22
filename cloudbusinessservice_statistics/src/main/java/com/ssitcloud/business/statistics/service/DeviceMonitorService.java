package com.ssitcloud.business.statistics.service;


import com.ssitcloud.common.entity.ResultEntity;


public interface DeviceMonitorService {
	/**
	 * 统计ElasticSearch数据，区分办证、借还，只提供给设备监控使用
	 * @param req
	 * @return
	 */
	ResultEntity countDatas(String req);
}
