package com.ssitcloud.business.devmgmt.service;

import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;

public interface DeviceService {


	ResultEntity queryDeviceByPage(String req);

	ResultEntity QueryDevIdxByDevId(String req);

	String SelectDevice(Map<String, String> map);
	
	String DeleteDevice(String json);
	
	String SelectInfo(String json);
	
	String SelLibrary(String reqInfo);

	ResultEntity queryDeviceByParam(String req);
	
	ResultEntity queryServiceDeviceByParam(String req);
	
	ResultEntity UpdDevice(String req);
	/**
	 * 设备管理 点击 保存
	 * @param req
	 * @return
	 */
	ResultEntity UpdDeviceMgmtPage(String req);
	
	/**
	 * 海洋设备更新保存
	 */
	ResultEntity UpdHyDeviceMgmtPage(String req);
	/**
	查询设备有没有自定义参数配置
	 * @param req
	 * @param req={"device_idx":deviceIdx,"configName":configName}
	 * @return
	 */
	ResultEntity checkAllConfigDataByDevIdx(String req);

	ResultEntity compareMonitorConfig(String req);

	ResultEntity loadAcsLogininfo(String req);
	/**
	 *  根据 所有图书馆IDX获取所有的对应的device_id
	 * @param req
	 * @return
	 */
	ResultEntity queryDeviceIdbyLibIdx(String req);
	/**
	 * 获取图书馆的设备信息，进行批处理操作， 分页显示， 可以根据参数查询
	 *
	 * <p>2016年9月20日 下午1:55:20 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity getLibraryDevicesByPage(String req);
	/**
	 * 根据设备id查询设备ip
	 *
	 * <p>2016年9月21日 下午6:54:54 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryDeviceIps(String req);
	
	/**
	 * 发送请求到鉴权库，将旧馆设备转移到新馆，并且修改相关ip
	 *
	 * <p>2016年9月21日 下午6:16:41 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract String authTransferToLibrary(String req);
	
	
	/**
	 * 发送请求到设备库，将旧馆设备转移到新馆， 并且删除相关设备与设备组的关联
	 *
	 * <p>2016年9月21日 下午6:16:38 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract String devTransferToLibrary(String req);
	/**
	 * 设备库中将旧馆设备转移到新馆成功之后，保存日志
	 *
	 * <p>2016年9月21日 下午6:16:38 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract String devTransferToLibraryLog(String req);
	
	/**
	 * 获取所有的地区信息
	 *
	 * <p>2017年9月7日 上午10:43:26 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String queryAllRegion(String json);
	
	/**
	 * 获取设备的地点信息
	 *
	 * <p>2017年9月8日 下午3:43:54 
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String queryDeviceRegion(String json);
	
	/**
	 * 获取设备的地理坐标信息
	 *
	 * <p>2017年10月18日
	 * <p>create by liuwei
	 * @param json
	 * @return
	 */
	public abstract String GetLibPosition(String json);
	
	/**
	 * 获取设备的地理坐标信息
	 *
	 * <p>2017年10月18日
	 * <p>create by liuwei
	 * @param json
	 * @return
	 */
	public abstract String GetDevicePosition(String json);
	
	/**
	 * 保存设备或图书馆位置信息
	 * <p>2017年10月26日 
	 * <p>create by liuwei
	 */
	public abstract String saveDevicePosition(String json);
	
	/**
	 * 删除图书馆位置信息
	 * <p>2017年10月26日 
	 * <p>create by liuwei
	 */
	public abstract String deleteLibraryPosition(String json);

	/**
	 * 保存Ncip压缩文件更新时间到数据库
	 * <p>
	 * 2017年10月26日
	 * <p>
	 * create by liuwei
	 */
	public abstract String insertFlagUploadFlag(String json);
}
