package com.ssitcloud.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dao.DeviceDisplayConfigDao;
import com.ssitcloud.entity.DeviceDisplayConfig;
import com.ssitcloud.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.entity.page.DeviceDisplayConfigPage;
import com.ssitcloud.service.DeviceDisplayConfigService;

/**
 * 
 * @package com.ssitcloud.devmgmt.service.impl
 * @comment
 * @data 2016年4月18日
 */
@Service
public class DeviceDisplayConfigServiceImpl implements DeviceDisplayConfigService {
	@Resource
	protected DeviceDisplayConfigDao deviceDisplayConfigDao;

	@Override
	public DeviceDisplayConfigPage queryAll(DeviceDisplayConfigPage entity) {
		return deviceDisplayConfigDao.queryAll(entity);
	}

	@Override
	public int delete(String req) {
		if ("null".equals(String.valueOf(req)) || "".equals(req.trim())) {
			return 0;
		}
		List<Integer> list = new ArrayList<Integer>();
		if (req.indexOf(",") != -1) {
			String[] ss = req.split("\\,");
			for (String s : ss) {
				list.add(Integer.parseInt(s));
			}
		} else {
			list.add(Integer.parseInt(req));
		}
		return deviceDisplayConfigDao.delete(list);
	}

	@Override
	public DeviceDisplayConfig findById(DeviceDisplayConfig page) {
		int display_type_idx = page.getDisplay_type_idx();
		return deviceDisplayConfigDao.findById(display_type_idx);
	}

	@Override
	public DeviceDisplayConfig insert(String req) {
		if ("null".equals(String.valueOf(req)) || "".equals(req.trim())) {
			return null;
		}
		JSONObject json = JSONObject.fromObject(req);
		DeviceDisplayConfig entity = (DeviceDisplayConfig) JSONObject.toBean(json, DeviceDisplayConfig.class);
		return deviceDisplayConfigDao.insert(entity);
	}

	@Override
	public ResultEntity update(String req) {
		ResultEntity result = new ResultEntity();
		if ("null".equals(String.valueOf(req)) || "".equals(req.trim())) {
			return null;
		}
		JSONObject json = JSONObject.fromObject(req);
		DeviceDisplayConfig entity = (DeviceDisplayConfig) JSONObject.toBean(json, DeviceDisplayConfig.class);
		String retMessage="|风格类型idx："+entity.getDisplay_type_idx()+"|风格类型名："+entity.getDisplay_type_name()+"|设备类型idx："+entity.getDevice_type_idx()+"|";
		int num =  deviceDisplayConfigDao.update(entity);
		if(num == 1){
			result.setState(true);
			result.setMessage("success");
		}else{
			result.setState(false);
			result.setMessage("optimistic");
		}
		result.setRetMessage(retMessage);
		return result;
	}

	@Override
	public DeviceDisplayConfig findByTypeId(DeviceDisplayConfig page) {
		int device_type_idx = page.getDevice_type_idx();
		page = deviceDisplayConfigDao.findByTypeId(device_type_idx);
		return page;
	}

	@Override
	public List<MetadataDeviceTypeEntity> getDeviceTypes(String req) {
		return deviceDisplayConfigDao.getDeviceTypes(req);
	}
}
