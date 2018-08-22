package com.ssitcloud.view.devmgmt.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DevicePageEntity;
import com.ssitcloud.devmgmt.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.devmgmt.entity.MetadataOrderEntity;

public interface DeviceService {

	/**
	 * 查询数据库的设备指令
	 */
	List<MetadataOrderEntity> queryMetadataOrder();
	/**
	 * 发送设备开关机命令到同步程序
	 */
	String sendOrder(String req);
	
	DevicePageEntity SelectDeviceByPage(String req);
	
	
	List<MetadataDeviceTypeEntity> queryDeviceType();
	/**
	 * 查询设备日志
	 * 
	 */
	String queryDeviceLog(String req);
	
	ResultEntity selectDeviceState(String req);

	String SelDeviceinfo(Map<String, String> map);
	
	String DeleteDeviceinfo(String req);

	
	/**
	 * 获取所有的硬件模板信息
	 *
	 * <p>2016年4月25日 下午6:43:54
	 * <p>create by hjc
	 * @return
	 */
	public abstract String getAllExtTempList(String json);
	
	/**
	 * 获取所有的硬件配置模板信息
	 *
	 * <p>2016年4月26日 下午6:48:35
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String getAllRunTempList(String json);
	
	/**
	 * 获取所有的硬件配置模板信息
	 *
	 * <p>2016年4月26日 下午6:48:35
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String getAllMonitorTempList(String json);
	
	/**
	 * 获取所有数据同步配置模板
	 *
	 * <p>2016年4月26日 下午6:48:35
	 * <p>create by hjc
	 * @param json
	 * @return
	 */
	public abstract String getAllDbSyncTempList(String json);
	/**
	 * 
	 * @param json
	 * @return
	 */
	public abstract String QueryDevice(String json);

	public abstract String SelectDBsync(String json);
	
	public abstract String UpdateDBsync(String json);
	
	public abstract String InsertDBsync(String json);
	
	String DeleteDBsyncConfig(String json);
	
	String UpdateDeviceConfig(String json);
	
	/**
	 * 设备监控配置操作
	 * @comment
	 * @param json
	 * @return
	 * @data 2016年6月13日
	 * @author hwl
	 */
	String UpdateMonitor(String json);
	
	String InsertMonitor(String json);
	
	String SelectMonitor(String json);
	
	String DeleteMonitor(String json);
	
	/**
	 * 设备外设配置操作
	 * @comment
	 * @param json
	 * @return
	 * @data 2016年6月14日
	 * @author hwl
	 */
	String SelectExt(String json);
	
	String InsertExtdata(String json);
	
	String UpdateExtdata(String json);
	
	String DeleteExtdata(String json);
	
	/**
	 * 设备运行配置操作
	 * @comment
	 * @param json
	 * @return
	 * @data 2016年6月14日
	 * @author hwl
	 */
	String SelectRun(String json);
	
	String DeleteRundata(String json);
	
	String InsertRundata(String json);
	
	String UpdateRundata(String json);
	
	/**
	 * 根据 Idx Or Id 获取图书馆信息
	 * @Description: TODO
	 * @param @param req
	 * @param @return   
	 * @return ResultEntity  
	 * @throws
	 * @author lbh
	 * @date 2016年5月7日
	 */
	ResultEntity selLibraryByIdxOrId(String req);

	ResultEntity selectBookrackState(String req);
	
	ResultEntity selectBinState(String req);
	
	ResultEntity selectDeviceExtState(String req);
	
	
	ResultEntity selectSoftState(String req);
	
	
	ResultEntity selOperatorByOperIdOrIdx(String req);
	/**
	 * 带参数 查询 设备
	 * 格式 req=
	 * {	
	 *   "machineType":machineType,    //设备类型
		 "machineState":machineState,  //设备状态
		 "keyWord":keyWord 			   //关键字 （设备名）
	   }
	 * 
	 * @param req
	 * @author lbh
	 * @return
	 */
	ResultEntity queryDeviceByParam(String req);
	
	ResultEntity queryControlResult(String req);

	public abstract String selAllExtModelAndLogicObj(String json);
	/**
	 * 需要修改到的表有
		device_config[变更模板（不是模板改数据或者数据该模板）]
		device[设备类型/图书馆ID/设备物流编号/设备流通地点/设备描述/设备名称/设备ID]
		device_group[设备分组]
	 * @param req
	 * @return
	 */
	ResultEntity deviceUpd(String req);
	/**
	 * 海洋设备更新
	 * @param req
	 * @return
	 */
	ResultEntity hydeviceUpd(String req);
	/**
	 * 查询设备有没有自定义参数配置
	 * @param req={"device_idx":deviceIdx,"configName":configName}
	 * @return
	 */
	ResultEntity checkAllConfigDataByDevIdx(String req);
	
	ResultEntity compareMonitorConfig(String req);
	/**
	 * 
	 * @param req
	 * @return
	 */
	ResultEntity getACSTempList(String req);
	/**
	 * 根据device_idx加载设备的ACS logininfo 信息
	 * 
	 * req={deviceIdx:"....."}
	 * @param req
	 * @return
	 */
	ResultEntity loadAcsLogininfo(String req);
	
	/**
	 * 获取图书馆的设备信息，进行批处理操作， 分页显示， 可以根据参数查询
	 *
	 * <p>2016年9月21日 上午10:37:45 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity getLibraryDevicesByPage(String req);
	
	/**
	 * 根据设备id 查询设备ip
	 *
	 * <p>2016年9月21日 下午6:52:56 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryDeviceIps(String req);
	
	/**
	 * 将图书馆的设备转移到另一个图书馆
	 *
	 * <p>2016年9月21日 上午10:37:55 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity transferToNewLib(String req);
	
	/**
	 * 查询所有的地区信息
	 *
	 * <p>2017年9月7日 上午10:20:34 
	 * <p>create by hjc
	 * @return
	 */
	public abstract ResultEntity queryAllRegion(String req);
	
	/**
	 * 
	 *
	 * <p>2017年9月8日 下午3:33:55 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity queryDeviceRegion(String req);
	/**
	 * 获取设备的地理坐标信息
	 *
	 * <p>2017年9月8日 下午3:43:54 
	 * <p>create by liuwei
	 * @param json
	 * @return
	 */
	public abstract String GetDevicePosition(String json);
	
	public abstract ResultEntity saveDevicePosition(String req);
	
	public abstract String deleteLibraryPosition(String req);
	
	public abstract String getLibPosition(String json);
	
	public abstract ResultEntity insertFileUploadFlag(String req);
	
	public String devicesRegister(CommonsMultipartFile commonsMultipartFile,String req)throws Exception;
}
