package com.ssitcloud.dbauth.dao;

import java.util.Map;

import com.ssitcloud.dbauth.common.dao.CommonDao;
import com.ssitcloud.dbauth.entity.IpWhiteEntity;

/**
 * <p>2016年4月5日 上午11:45:53
 * @author hjc
 *
 */
public interface IpWhiteDao extends CommonDao{
	
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
	 * 根据历史密码表中的password_idx删除数据
	 * 
	 * <p>2016年4月6日 下午5:27:09
	 * <p>create by hjc
	 * @param ipWhiteEntity 历史密码实体类
	 * @return 数据库操作结果
	 */
	public abstract int delIpWhiteByOperIdx(IpWhiteEntity ipWhiteEntity);
	
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
	 * 根据operator_id查询白名单信息
	 * 
	 * <p>2016年4月11日 上午9:52:00
	 * <p>create by hjc
	 * @param operator_id
	 * @return 返回白名单实体类
	 */
	public abstract IpWhiteEntity selIpWhiteEntity(String operator_id);
	
	/**
	 * 更新ip白名单信息，根据operator_idx，主要是设备
	 *
	 * <p>2016年4月21日 上午10:44:04
	 * <p>create by hjc
	 * @param ipWhiteEntity
	 * @return
	 */
	public abstract int updIpWhite(IpWhiteEntity ipWhiteEntity);
	
	/**
	 * 根据操作员id查询白名单
	 *
	 * <p>2016年9月22日 上午10:13:13 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract IpWhiteEntity selIpWhiteByOperatorId(Map<String, Object> param);

}
