package com.ssitcloud.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.DeviceDBSyncConfDao;
import com.ssitcloud.dao.DeviceDao;
import com.ssitcloud.dao.DeviceExtConfDao;
import com.ssitcloud.dao.DeviceMonConfDao;
import com.ssitcloud.dao.DeviceRunConfDao;
import com.ssitcloud.entity.DeviceRegisterEntity;
import com.ssitcloud.service.DeviceRegsterService;

@Service
public class DeviceRegsterServiceImpl implements DeviceRegsterService {
	
	@Resource
	DeviceDao deviceDao;
	
	@Resource
	private DeviceExtConfDao deviceExtConfDao;
	
	@Resource
	private DeviceDBSyncConfDao dbSyncConfDao;
	
	@Resource
	private DeviceRunConfDao deviceRunConfDao;
	
	@Resource
	private DeviceMonConfDao deviceMonConfDao;
	
	@Override
	public ResultEntity doDeviceRegister(HttpServletRequest req){
		ResultEntity res=new ResultEntity();
		res.setState(true);
		res.setMessage(Const.SUCCESS);
		DeviceRegisterEntity reg=JsonUtils.fromJson(req.getParameter("json"), DeviceRegisterEntity.class);
		if(reg!=null){
			int addDeviceNum=deviceDao.insert(reg.getDevice());
			int addExtTempNum=deviceExtConfDao.insertDeviceExtTemp(reg.getDevice_ext_template());
			int addSyncTempNum=dbSyncConfDao.insertDeviceDBSyncTemp(reg.getDevice_dbsync_template());
			int addRunTempNum=deviceRunConfDao.insertDeviceRunTemp(reg.getDevice_run_template());
			int addMonTempNum=deviceMonConfDao.insertDeviceMonTemp(reg.getDevice_monitor_template());
			if(addDeviceNum<=0){
				throw new RuntimeException("doDeviceRegister:device insert fail");
			}
			if(addExtTempNum<=0){
				throw new RuntimeException("doDeviceRegister:device_ext_template insert fail");
			}
			if(addSyncTempNum<=0){
				throw new RuntimeException("doDeviceRegister:device_dbsync_template insert fail");
			}
			if(addRunTempNum<=0){
				throw new RuntimeException("doDeviceRegister:device_run_template insert fail");
			}
			if(addMonTempNum<=0){
				throw new RuntimeException("doDeviceRegister:device_monitor_template insert fail");
			}
			
			//下发配置数据
			
			
			
		}else{
			res.setState(false);
			res.setMessage(Const.FAILED);
		}
	
		return res;
	}
	
	
}	
