package com.ssitcloud.businessauth.service;

import java.util.Map;

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
	public abstract String addIpWhite(Map<String, String> param);
	
	/**
	 * 根据operator_idx获取ip白名单
	 *
	 * <p>2016年4月22日 下午3:45:57
	 * <p>create by hjc
	 * @param ipWhiteEntity
	 * @return
	 */
	public abstract String selIpWhiteByIdx(Map<String, String> param);
	
	/**
	 * 根据历史密码表中的password_idx删除数据
	 * 
	 * <p>2016年4月6日 下午5:27:09
	 * <p>create by hjc
	 * @param ipWhiteEntity 历史密码实体类
	 * @return 数据库操作结果
	 */
	public abstract String delIpWhiteByOperIdx(Map<String, String> param);
	
	/**
	 * 更新ip白名单信息，根据operator_idx，主要是设备
	 *
	 * <p>2016年4月21日 上午10:44:04
	 * <p>create by hjc
	 * @param ipWhiteEntity
	 * @return
	 */
	public abstract String updIpWhite(Map<String, String> param);
}
