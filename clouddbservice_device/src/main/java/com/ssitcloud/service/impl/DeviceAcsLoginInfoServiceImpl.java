package com.ssitcloud.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.DeviceAcsLoginInfoDao;
import com.ssitcloud.dao.impl.DeviceDaoImpl;
import com.ssitcloud.datasync.entity.DeviceAcsModuleEntity;
import com.ssitcloud.datasync.entity.Ever;
import com.ssitcloud.datasync.entity.ProtocolInfo;
import com.ssitcloud.entity.DeviceAcsLoginInfoEntity;
import com.ssitcloud.service.DeviceAcsLoginInfoService;

@Service
public class DeviceAcsLoginInfoServiceImpl implements DeviceAcsLoginInfoService {
	@Resource
	private DeviceAcsLoginInfoDao acsLoginInfoDao;

	@Resource
	private DeviceDaoImpl DeviceDao;

	/***
	 * req={deviceIdx:"xxx"}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity loadAcsLogininfo(String req) {
		ResultEntity resultEntity=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			Map<String, Object> map=JsonUtils.fromJson(req, Map.class);
			if(map.get("deviceIdx")!=null){
				Integer deviceIdx=Integer.parseInt(map.get("deviceIdx").toString());
				List<DeviceAcsLoginInfoEntity> deviceAcsLoginInfos=acsLoginInfoDao.loadAcsLogininfoByDeviceIdx(deviceIdx);
				resultEntity.setState(true);
				resultEntity.setResult(deviceAcsLoginInfos);
			}
		}
		return resultEntity;
	}

	@Override
	public List<DeviceAcsModuleEntity> loadAcsXml(String lib_idx, String device_id) {
		Integer device_idx, libraryIdx, protocol_tpl_idx;
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		List<ProtocolInfo> protocolInfos = new ArrayList();
		DeviceAcsModuleEntity deviceAcsModule = new DeviceAcsModuleEntity();
		// List<AcsCfgProtocols> acsCfgProtocols = new ArrayList<>();
		Ever ever = new Ever();

		// 将device_id转成device_idx
		/*
		 * DeviceEntity deviceEntity = new DeviceEntity();
		 * deviceEntity.setDevice_id(device_id);
		 * deviceEntity.setLibrary_idx(Integer.valueOf(lib_id));
		 */
		/* String device_idx = DeviceDao.selectDeviceIdx(deviceEntity); */

		param.put("library_idx", lib_idx);
		param.put("device_id", device_id);
		// 查询设备的模块
		List<DeviceAcsModuleEntity> deviceAcsModules = acsLoginInfoDao.queryDeviceAcsModules(param);

		Iterator it = deviceAcsModules.iterator();
		while (it.hasNext()) {
			deviceAcsModule = (DeviceAcsModuleEntity) it.next();
			device_idx = deviceAcsModule.getDevice_idx();
			libraryIdx = deviceAcsModule.getLibrary_idx();
			protocol_tpl_idx = deviceAcsModule.getProtocol_tpl_idx();
			map.put("device_idx", device_idx);
			map.put("libraryIdx", libraryIdx);
			map.put("protocol_tpl_idx", protocol_tpl_idx);
			protocolInfos = acsLoginInfoDao.queryDeviceAcsDetailModules(map);
			deviceAcsModule.setProtocolInfos(protocolInfos);
			// System.out.println(deviceAcsModule.toString());
			// protocolInfos.removeAll(protocolInfos);
		}
		System.out.println(deviceAcsModules.toString());
		// AcsCfgProtocols protocols = new AcsCfgProtocols();

		return deviceAcsModules;
	}
}
