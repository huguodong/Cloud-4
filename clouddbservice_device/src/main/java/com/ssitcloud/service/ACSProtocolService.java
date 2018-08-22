package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.ACSProtocolEntity;

/**
 * ACSProtocol配置信息
 * @author Administrator
 *
 */
public interface ACSProtocolService {

	/**
	 * 更新
	 * @param acsProtocolEntity
	 * @return
	 */
	public abstract ResultEntity UpdOneByIdx(ACSProtocolEntity acsProtocolEntity);
	/**
	 * 删除一个
	 * @param acsProtocolEntity
	 * @return
	 */
	public abstract ResultEntity DelOneByIdx(ACSProtocolEntity acsProtocolEntity);
	/**
	 * 删除多个
	 * @param acsProtocolEntitys
	 * @return
	 */
	public abstract ResultEntity DelByIdxS(List<ACSProtocolEntity> acsProtocolEntitys);
	/**
	 * 新增一个
	 * @param acsProtocolEntity
	 * @return
	 */
	public abstract ResultEntity IncOne(ACSProtocolEntity acsProtocolEntity);
	/**
	 * 新增多个
	 * @param acsProtocolEntity
	 * @return
	 */
	public abstract ResultEntity IncMany(List<ACSProtocolEntity> acsProtocolEntity);
	/**
	 * 根据条件查询
	 * @param acsProtocolEntity
	 * @return
	 */
	public abstract ResultEntity SelList(ACSProtocolEntity acsProtocolEntity);
	/**
	 * 分页查询
	 * @param acsProtocolEntity
	 * @return
	 */
	public abstract ResultEntity SelPage(ACSProtocolEntity acsProtocolEntity);
}
