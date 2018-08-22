package com.ssitcloud.business.mobile.service;

import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * 馆员APP 业务层 OperatorService
 * @author shuangjunjie
 * 2017年2月24日 下午4:04
 *
 */
public interface OperatorService {

	/**
	 * 查询 操作员个人信息
	 * author shuangjunjie
	 * 2017年2月24日 下午4:04
	 * @param req
	 * @return
	 */
	ResultEntity selectOperaotrByIdxOrId(String req);
	
	/**
	 * 修改 操作员个人信息
	 * author shuangjunjie
	 * 2017年2月24日 下午4:04
	 * @param req
	 * @return
	 */
	ResultEntity updateOperator(String req);
	
	/**
	 * 修改 操作员密码
	 * author shuangjunjie
	 * 2017年3月1日 上午10:27
	 * @param req
	 * @return
	 */
	ResultEntity updateOperAppPwdByIdx(String req);
	
	/**
	 * 修改 操作员密码
	 * author shuangjunjie
	 * 2017年2月24日 下午4:04
	 * @param req
	 * @return
	 */
	ResultEntity changePassword(String req);
	
	/**
	 * 找回操作员密码
	 * author shuangjunjie
	 * 2017年2月24日 下午4:04
	 * @param req
	 * @return
	 */
	ResultEntity retrievePassword(String req);
	
	/**
	 * 登录验证
	 * author shuangjunjie
	 * 2017年2月27日 上午11:17
	 * @param req
	 * @return
	 */
	ResultEntity loginCheck(String req);
	
	/**
	 * 检查密码格式
	 * author shuangjunjie
	 * 2017年2月27日 上午11:43
	 * @param req
	 * @return
	 */
	ResultEntity checkPwdFormat(String req);
	
	/**
	 * 通过参数查出operator_id
	 * author shuangjunjie
	 * 2017年3月2日 下午4:25
	 * @param req
	 * @return
	 */
	ResultEntity selectOperatorIdByParam(String req);
	
	/**
	 * 获取设备对应的外设信息
	 * author shuangjunjie
	 * 2017年2月28日 上午11:15
	 * @param req
	 * @return
	 */
	ResultEntity getDevExtModel(String req);
	
	/**
	 * 检测 设备外设 状态
	 * author shuangjunjie
	 * 2017年2月28日 上午11:28
	 * @param req
	 * @return
	 */
	ResultEntity selectDeviceExtState(String req);
	
	/**
	 * 获取功能名
	 * author shuangjunjie
	 * 2017年3月9日 上午11:01
	 * @param req
	 * @return
	 */
	ResultEntity selFunctionByDeviceIdx(String req);
	
	/**
	 * 获取功能状态信息
	 * author shuangjunjie
	 * 2017年2月28日 上午11:33
	 * @param req
	 * @return
	 */
	ResultEntity selectSoftState(String req);
	
	/**
	 * 获取设备上的书架状态信息
	 * author shuangjunjie
	 * 2017年2月28日 上午11:41
	 * @param req
	 * @return
	 */
	ResultEntity selectBookrackState(String req);
	
	/**
	 * 获取箱子信息
	 * author shuangjunjie
	 * 2017年2月28日 下午2:05
	 * @param req
	 * @return
	 */
	ResultEntity selectBinState(String req);
	
	/**
	 * 获取 设备列表
	 * @param req
	 * @return
	 */
	ResultEntity selectDeviceByPage(String req);
	
	/**
	 * 带参数查询设备
	 * @param req
	 * @return
	 */
	ResultEntity queryDeviceByParam(String req);
	
	/**
	 * 根据device_id查出设备类型
	 * @param req
	 * @return
	 */
	ResultEntity selectDevicTypeByDeviceId(String req);
	
	/**
	 * 根据device_idx查出device_id
	 * @param req
	 * @return
	 */
	ResultEntity selectDevIdByIdx(String req);
	
	/**
	 * 根据device_type查出meta_devicetype_idx
	 * author shuangjunjie
	 * 2017年3月6日 下午6:30
	 * @param req
	 * @return
	 */
	ResultEntity selectMetaDevTypeIdxByType(String req);
	
	/**
	 * 获取设备的最后心跳时间
	 * author shuangjunjie
	 * 2017年3月14日 上午10:40
	 * @param req
	 * @return
	 */
	ResultEntity getLastHeatBeatTime(String req);
	
	/**
	 * 获取设备状态
	 * author shuangjunjie
	 * 2017年3月14日 下午4:35
	 * @param req
	 * @return
	 */
	ResultEntity selectDeviceState(String req);
	
	/**
	 * 分页查询 设备信息（主馆及其子馆）
	 * @param map
	 * @return
	 */
	ResultEntity SelectDeviceMgmtByLibraryIdxs(Map<String, String> map);
	
	/**
	 * 设备维护（发送指令）
	 * @param req
	 * @return
	 */
	ResultEntity sendOrder(String req);
	
	/**
	 * 消息提醒（故障提醒）
	 * @param req
	 * @return
	 */
	ResultEntity selectDeviceTroublesByLibIdxs(String req);
	
	/**
	 * 更新消息提醒
	 * @param req
	 * @return
	 */
	ResultEntity updateDeviceTroubles(String req);
	
	/**
	 * 根据图书馆idx查出对应区域
	 * @param req
	 * @return
	 */
	ResultEntity selRegionIdxsByLibIdxs(String req);
	
	/**
	 * 发送找回密码验证码
	 * @param mobile
	 * @return 
	 */
	ResultEntity sendFoundPwdVcode(String mobile);
	
	/**
	 * 通过验证码修改密码
	 */
	ResultEntity changePwdByVcode(Map<String, Object> data);
}
