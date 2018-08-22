package com.ssitcloud.dbauth.service;

import java.util.List;

import com.ssitcloud.dbauth.entity.OperatorEntity;
import com.ssitcloud.dbauth.entity.PasswordHistoryEntity;

/**
 * <p>2016年4月5日 下午1:37:33
 * @author hjc
 *
 */
public interface PasswordHistoryService {
	
	/**
	 * 新增一条历史密码数据
	 * 
	 * <p>2016年4月5日 下午7:05:44
	 * <p>create by hjc
	 * @param passwordHistoryEntity 历史密码实体类，新增成功之后，自增password_idx有值
	 * @return 数据库操作即如果
	 */
	public abstract int addPasswordHistory(PasswordHistoryEntity passwordHistoryEntity);
	
	/**
	 * 根据操作员的信息查询历史密码
	 * 
	 * <p>2016年4月6日 下午3:11:56
	 * <p>create by hjc
	 * @param operatorEntity
	 * @return
	 */
	public abstract List<PasswordHistoryEntity> selPasswordHistoryByOperInfo(OperatorEntity operatorEntity);
	
	/**
	 * 根据历史密码password_idx删除历史密码信息
	 * 
	 * <p>2016年4月6日 下午8:14:17
	 * <p>create by hjc
	 * @param historyEntity 历史密码实体类
	 * @return 数据库操作结果
	 */
	public abstract int delPasswordHistoryByIdx(PasswordHistoryEntity historyEntity);
	
	/**
	 * 根据用户operator_id，historyCount获取用户的历史密码条数
	 *
	 * <p>2016年4月13日 下午5:00:52
	 * <p>create by hjc
	 * @param operator_idx 用户表的idx
	 * @param historyCount 获取最近历史密码的条数
	 * @return
	 */
	public abstract List<PasswordHistoryEntity> selPwdHistoryByOperIdx(Integer operator_idx,Integer historyCount);
	
	
}
