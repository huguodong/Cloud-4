package com.ssitcloud.dbauth.dao;

import java.util.Map;

import com.ssitcloud.dbauth.common.dao.CommonDao;
import com.ssitcloud.dbauth.entity.RSAEntiy;

/**
 * <p>2016年4月13日 下午5:58:31
 * @author hjc
 */
public interface RSADao extends CommonDao{
	
	/**
	 * 根据rsa_idx查询RSAEntiy
	 *
	 * <p>2016年4月13日 下午6:11:40
	 * <p>create by hjc
	 * @param rsaEntiy
	 * @return
	 */
	public abstract RSAEntiy selRsaEntityByIdx(RSAEntiy rsaEntiy);
	
	/**
	 * 取表中第一条数据
	 *
	 * <p>2016年4月13日 下午6:11:40
	 * <p>create by hjc
	 * @return
	 */
	public abstract RSAEntiy selRsaEntityTop();
	
	/**
	 * 根据publicKey 查询RSAEntiy
	 *
	 * <p>2016年4月13日 下午6:11:44
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract RSAEntiy selRsaEntityByPublicKey(Map<String, String> param);

}
