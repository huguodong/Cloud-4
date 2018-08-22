package com.ssitcloud.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceMonConfigEntity;
import com.ssitcloud.entity.DeviceMonTempEntity;
import com.ssitcloud.entity.page.DeviceMonConfigPageEntity;
import com.ssitcloud.entity.page.DeviceMonTempPageEntity;
/**
 * 
 * @package: com.ssitcloud.service
 * @classFile: DeviceMonConfService
 * @author: liuBh
 * @description: TODO
 */
public interface DeviceMonConfService {
	/*
	 *操作监控配置 
	 *
	 */
	int insertDeviceMonConfig(DeviceMonConfigEntity deviceMonConfig);

	int updateDeviceMonConfig(DeviceMonConfigEntity deviceMonConfig);

	int deleteDeviceMonConfig(DeviceMonConfigEntity deviceMonConfigEntity);
	
	/**
	 * 查询的实体对象必须存在主键
	 * @methodName: selectOne
	 * @param deviceMonConfig
	 * @return
	 * @returnType: DeviceMonConfigEntity
	 * @author: liuBh
	 */
	DeviceMonConfigEntity selectOne(DeviceMonConfigEntity deviceMonConfig);

	List<DeviceMonConfigEntity> selectList(DeviceMonConfigEntity deviceMonConfig);
	
	
	/*
	 * 监控模板设置
	 */
	int insertDeviceMonTemp(DeviceMonTempEntity deviceMonTemp);

	int updateDeviceMonTemp(DeviceMonTempEntity deviceMonTemp);

	int deleteDeviceMonTemp(DeviceMonTempEntity deviceMonTemp);
	
	/**
	 * 查询的实体对象必须存在主键
	 * @methodName: selectOne
	 * @param deviceMonTemp
	 * @return
	 * @returnType: DeviceMonTempEntity
	 * @author: liuBh
	 */
	DeviceMonTempEntity selectOne(DeviceMonTempEntity deviceMonTemp);

	List<DeviceMonTempEntity> selectList(DeviceMonTempEntity deviceMonTemp);

	/**
	 * 获取所有的监控配置模板
	 *
	 * <p>2016年4月27日 上午11:04:40
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<Map<String, Object>> selAllMonitorTempList(Map<String, String> param );
	
	/**
	 * 获取 监控配置 和元数据 信息 
	 * @param req
	 * @author lbh
	 * @return
	 */
	DeviceMonTempPageEntity queryDeviceMonitorByParam(String req);

	ResultEntity AddNewDeviceMonitorConfAndTemp(String req);


	ResultEntity UpdNewDeviceMonitorConfAndTemp(String req);

	int DelNewDeviceMonitorConfAndTemp(String req);

	ResultEntity SelDevMonConfMetaLogObjByDeviceMonTplIdx(String req);

	ResultEntity queryAlertColor(String req);

	
	int DeleteMonitor(DeviceMonConfigEntity deviceMonConfigEntity);
	/**
	 * 更新操作
	 * @param json
	 * @return
	 */
	ResultEntity UpdateMonitor(String json);
	/**
	 * 获取当前页面的 设备设置的监控颜色
	 * @param req
	 * @return
	 */
	ResultEntity GetCurrentDevColorSet(String req);
	
	public List<DeviceMonConfigEntity> getDeviceMonConfig(DeviceConfigEntity configEntity);
	
	
}
