package com.ssitcloud.dbauth.service;

import com.ssitcloud.dbauth.entity.IpWhiteEntity;

/**
 * <p>2016年4月5日 上午11:45:10
 * @author hjc
 *
 */
public interface IpWhiteService {
	/**
	 * 新增设备白名单
	 * 
	 * <p>2016年4月5日 下午2:14:43
	 * <p>create by hjc
	 * @param ipWhiteEntity
	 * @return
	 */
	public abstract int addIpWhite(IpWhiteEntity ipWhiteEntity);
	
	/**
	 * 根据operator_idx获取ip白名单
	 *
	 * <p>2016年4月22日 下午3:45:57
	 * <p>create by hjc
	 * @param ipWhiteEntity
	 * @return
	 */
	public abstract IpWhiteEntity selIpWhiteByIdx(IpWhiteEntity ipWhiteEntity);
	
	/**
	 * 根据历史密码表中的password_idx删除数据
	 * 
	 * <p>2016年4月6日 下午5:27:09
	 * <p>create by hjc
	 * @param ipWhiteEntity 历史密码实体类
	 * @return 数据库操作结果
	 */
	public abstract int delIpWhiteByOperIdx(IpWhiteEntity ipWhiteEntity);
	
	/**
	 * 更新ip白名单信息，根据operator_idx，主要是设备
	 *
	 * <p>2016年4月21日 上午10:44:04
	 * <p>create by hjc
	 * @param ipWhiteEntity
	 * @return
	 */
	public abstract int updIpWhite(IpWhiteEntity ipWhiteEntity);
}
