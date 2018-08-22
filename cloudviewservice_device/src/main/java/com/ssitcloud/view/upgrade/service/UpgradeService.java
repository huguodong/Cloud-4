package com.ssitcloud.view.upgrade.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface UpgradeService {
	/**
	 * 查询升级信息
	 *
	 * <p>2016年6月12日 下午7:56:18 
	 * <p>create by gcy
	 * @param req
	 * @return
	 */
	
	public abstract ResultEntity queryUpgradeByParam(String req);
	/**
	 * 增加升级信息
	 *
	 * <p>2016年6月12日 下午7:56:18 
	 * <p>create by gcy
	 * @param req
	 * @return
	 */
	public abstract ResultEntity addPatchInfo(String req);
	/**
	 * 删除升级信息
	 *
	 * <p>2016年6月12日 下午7:56:18 
	 * <p>create by gcy
	 * @param req
	 * @return
	 */
	public abstract ResultEntity delPatchInfo(String req);
	/**
	 * 批量删除升级信息
	 *
	 * <p>2016年7月30日 下午5:57:09 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity delMultiPatchInfo(String req);
	/**
	 * 修改升级信息
	 *
	 * <p>2016年6月12日 下午7:56:18 
	 * <p>create by gcy
	 * @param req
	 * @return
	 */
	public abstract ResultEntity updPatchInfo(String req);

}
