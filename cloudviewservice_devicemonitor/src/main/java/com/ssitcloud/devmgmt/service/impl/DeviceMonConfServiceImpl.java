package com.ssitcloud.devmgmt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ColorEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.exception.CommonException;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.devmgmt.service.DeviceMonConfService;

@Service
public class DeviceMonConfServiceImpl extends BasicServiceImpl implements DeviceMonConfService{

	public static final String URL_queryDeviceMonitorByParam="queryDeviceMonitorByParam";

	private static final String URL_AddNewDeviceMonitorConfAndTemp = "AddNewDeviceMonitorConfAndTemp";

	private static final String URL_UpdNewDeviceMonitorConfAndTemp = "UpdNewDeviceMonitorConfAndTemp";

	private static final String URL_DelNewDeviceMonitorConfAndTemp = "DelNewDeviceMonitorConfAndTemp";

	private static final String URL_AddNewDeviceMonitorTemp = "AddNewDeviceMonitorTemp";

	private static final String URL_SelectMetadataLogicObj = "SelectMetadataLogicObj";

	private static final String URL_SelPlcLogicObjectEntity = "SelPlcLogicObjectEntity";

	private static final String URL_SelDevMonConfMetaLogObjByDeviceMonTplIdx = "SelDevMonConfMetaLogObjByDeviceMonTplIdx";

	private static final String URL_queryAlertColor = "queryAlertColor";
	
	@Value("${device.color}")
	private String color;

	@Override
	public ResultEntity queryDeviceMonitorByParam_view(String req) {
	
		return postUrl(URL_queryDeviceMonitorByParam,req);
	}

	@Override
	public ResultEntity queryMonitorColorConf(String req) {
		ResultEntity result=new ResultEntity();
		List<ColorEntity> colors=new ArrayList<>();
		if(color!=null){
			try {
				String[] levels=color.split("\\|");
				for(String level:levels){
					String[] colorArr=level.split("\\:");
					ColorEntity color=new ColorEntity(colorArr[1],colorArr[0],colorArr[2]);
					colors.add(color);
				}
				result.setResult(colors);
				result.setState(true);
			} catch (Exception e) {
				throw new CommonException("config.properties device.color 配置出现问题，正确配置如 green:0|yellow:1|red:2");
			}
		}
		return result;
	}

	@Override
	public ResultEntity AddNewDeviceMonitorConfAndTemp_view(String req) {
		
		return postUrl(URL_AddNewDeviceMonitorConfAndTemp,req);
	}

	@Override
	public ResultEntity UpdNewDeviceMonitorConfAndTemp_view(String req) {
		return postUrl(URL_UpdNewDeviceMonitorConfAndTemp,req);
	}
	
	@Override
	public ResultEntity DelNewDeviceMonitorConfAndTemp_view(String req) {
		return postUrl(URL_DelNewDeviceMonitorConfAndTemp,req);
	}

	@Override
	public ResultEntity AddDeviceConfigTemplate(String req) {
		return postUrl(URL_AddNewDeviceMonitorTemp,req);
	}

	@Override
	public ResultEntity SelMetadataLogicObject(String req) {
		return postUrl(URL_SelectMetadataLogicObj,req);
	}

	@Override
	public ResultEntity SelPlcLogicObjectEntity() {
		return postUrl(URL_SelPlcLogicObjectEntity,null);
	}

	@Override
	public ResultEntity SelDevMonConfMetaLogObjByDeviceMonTplIdx(String req) {
		return postUrl(URL_SelDevMonConfMetaLogObjByDeviceMonTplIdx,req);
	}

	@Override
	public ResultEntity queryAlertColor(String req) {
		return postUrl(URL_queryAlertColor,req);
	}

}