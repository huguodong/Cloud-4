package com.ssitcloud.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.entity.DeviceTroubleEntity;
import com.ssitcloud.entity.page.DeviceTroublePageEntity;

public interface DeviceTroubleService {
	
	/**
	 * 故障通知DeviceTroubleEntity插入
	 * author huanghuang
	 * 2017年2月9日 下午1:36:42
	 * @param deviceTroubleEntity
	 * @return
	 */
	public abstract int insertDeviceTrouble(DeviceTroubleEntity deviceTroubleEntity);
	
	/**
	 * 故障通知DeviceTroubleEntity修改
	 * author huanghuang
	 * 2017年2月9日 下午1:36:58
	 * @param deviceTroubleEntity
	 * @return
	 */
	public abstract int updateDeviceTrouble(DeviceTroubleEntity deviceTroubleEntity);
	
	/**
	 * 故障通知DeviceTroubleEntity删除
	 * author huanghuang
	 * 2017年2月9日 下午1:37:20
	 * @param deviceTroubleEntity
	 * @return
	 */
	public abstract int deleteDeviceTrouble(DeviceTroubleEntity deviceTroubleEntity);
	
	/**
	 * 故障通知DeviceTroubleEntity单个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:36
	 * @param deviceTroubleEntity
	 * @return
	 */
	public abstract DeviceTroubleEntity queryOneDeviceTrouble(DeviceTroubleEntity deviceTroubleEntity);
	
	/**
	 * 故障通知DeviceTroubleEntity多个查询
	 * author huanghuang
	 * 2017年2月9日 下午1:37:49
	 * @param params
	 * @return
	 */
	public abstract List<DeviceTroubleEntity> queryDeviceTroubles(DeviceTroubleEntity deviceTroubleEntity);
	
	/**
	 * 通过 图书馆idxs查出对应 故障信息
	 * add by shuangjunjie
	 * 2017年4月19日
	 * @param pageEntity
	 * @return
	 */
	public abstract DeviceTroublePageEntity selectDeviceTroublesByLibIdxs(DeviceTroublePageEntity pageEntity);
	
	/**
	 * 故障通知DeviceTroubleEntity更新
	 * author shuangjunjie
	 * 2017年2月9日 下午1:36:58
	 * @return
	 */
	public abstract int updateDeviceTroubles(Map map);
}
