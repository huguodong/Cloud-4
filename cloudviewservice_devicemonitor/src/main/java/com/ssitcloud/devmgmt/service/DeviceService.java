package com.ssitcloud.devmgmt.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
	 * 
	 * @param json
	 * @return
	 */
	public abstract String QueryDevice(String json);

	
	String UpdateDeviceConfig(String json);
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
	
	ResultEntity queryServiceDeviceByParam(String req);
	
	ResultEntity queryControlResult(String req);
	/**
	 * 
	 * 
	 * 发送下载设备端日志请求
	 * @param req
	 * @return
	 */
	ResultEntity downloadRunLogOperation(String req);
	/**
	 * 检查是否可以下载日志
	 * @param req
	 * @return
	 * @throws IOException 
	 */
	ResultEntity checkDownLoadLog(String req) throws IOException;
	/**
	 * 获取当前页面的 设备设置的监控颜色
	 * @param req
	 * @return
	 */
	ResultEntity GetCurrentDevColorSet(String req);
	/**
	 * 获取当前页面设备的逻辑外设信息
	 * @param req
	 * @return
	 */
	ResultEntity GetDevExtModel(String req);
	/**
	 * devID数组 JSON
	 * @param req
	 * @return
	 */
	ResultEntity getLastHeatBeatTime(String req);
	/**
	 * 根据 device_idx获取设备的功能状态
	 * @param req
	 * @return
	 */
	ResultEntity SelFunctionByDeviceIdx(String req);
	/**
	 * 查询设备的相关业务数据，并显示到设备详情Tab页
	 * @param req
	 * @return
	 */
	ResultEntity QueryBizData(String req);
	/**
	 * 统计借还数据
	 * @param req
	 * @return
	 */
	ResultEntity countLoanLog(String req);
	/**
	 * 统计办证数据
	 * @param req
	 * @return
	 */
	ResultEntity countCardissueLog(String req);
	/**
	 * 统计财经数据
	 * @param req
	 * @return
	 */
	ResultEntity countFinanceLog(String req);
	/**
	 * 统计人流量数据
	 * @param req
	 * @return
	 */
	ResultEntity countVisitorLog(String req);
	/**
	 * 根据图书馆lib的地理位置
	 * @param request
	 * @param req
	 * @return
	 */
	ResultEntity getLibPosition(String req);
	/**
	 * 根据图书馆lib查询所有机器的地理位置
	 * @param request
	 * @param req
	 * @return
	 */
	ResultEntity getDevicePosition(String req);
	/**
	 * 查询所有图书馆信息
	 */
	ResultEntity querylibInfoByCurUser(String req);
	
	/**
	 * 查询图书馆所有设备
	 */
	ResultEntity SelectDevicesInfo(String req);
	
	ResultEntity selectDeviceCountByLibraryIdx(String req);
	
	ResultEntity selectCountDeviceService(String req);
	
	ResultEntity countDatas(String req);
	
	/**
	 * 获取设备部件监控状态错误码
	 */
	ResultEntity queryDevStatusCode();
}
