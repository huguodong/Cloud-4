package com.ssitcloud.service.impl;



import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dao.DeviceServiceQueueDao;
import com.ssitcloud.service.DeviceServiceQueueService;

@Service
public class DeviceServiceQueueServiceImpl implements DeviceServiceQueueService {
	
	@Resource
	private DeviceServiceQueueDao deviceServiceQueueDao;
	/**
	 * 根据libidx获取特殊设备（3D导航、人流量、智能家具） 分组
	 * @return ResultEntity
	 */
	@Override
	public ResultEntity queryQueueIdbyServiceIdx(){
		ResultEntity result=new ResultEntity();
		//Map<Integer,String>//device_id逗号分割
		Map<Integer,String> libIdxAndDevIds=deviceServiceQueueDao.queryQueueIdbyServiceIdx();
		result.setState(true);
		result.setResult(libIdxAndDevIds);
		return result;
	}
}
