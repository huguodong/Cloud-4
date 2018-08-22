package com.ssitcloud.devicelog.service;

import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.service.BasicService;

public interface DeviceStateService extends BasicService {
	/**
	 * 接收设备端 状态数据 上传
	 * @param req
	 * @return
	 */
	ResultEntityF<Object> transData(String req);
	
	/**
	 * 接收跟图书馆业务相关的操作日志
	 *
	 * <p>2017年3月2日 上午11:27:43 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	ResultEntityF<Object> transOperationLog(String req);
	
	/**
	 * 查询设备状态 0 正常 1报警 2失效
	 * 
	 * @Description: TODO
	 * @param @param req
	 * @param @return   
	 * @return ResultEntityF<Object>  
	 * @throws
	 * @author lbh
	 * @date 2016年5月9日
	 */
	ResultEntityF<Object> queryDeviceState(String req);
	
	/**
	 * 书架信息状态
	 * @param req
	 * @return
	 */
	ResultEntityF<Object> selectBookrackState(String req);
	/**
	 * 
	 * @param req
	 * @return
	 */
	ResultEntityF<Object> selectBinState(String req);

	ResultEntityF<Object> selectDeviceExtState(String req);

	ResultEntityF<Object> selectSoftState(String req);

	ResultEntityF<Object> queryDeviceIdByState(String req);

	ResultEntityF<Object> getMongodbNames(String req);

	ResultEntityF<Object> getDBInstanceByDBName(String req);
	/**
	 * 接收设备端 状态数据 上传到特殊设备：人流量服务器、3D导航、智能家具
	 * @param req
	 * @return
	 */
	ResultEntityF<Object> transDataToSpecialDevice(String req);

}
