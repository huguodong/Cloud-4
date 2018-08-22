package com.ssitcloud.business.upgrade.service;

import com.ssitcloud.common.entity.ResultEntity;

public interface UpgradeService {
	/**
	 * 按条件查询升级信息
	 * @param req
	 * @return
	 */
	ResultEntity selectPatchInfo(String req);
	/**
	 * 查看版本信息
	 * @param req
	 * @return
	 */
	ResultEntity askVersion(String req);
	/**
	 * 新增版本信息
	 * @param req
	 * @return
	 */

	ResultEntity addPatchInfo(String req);
/**
 * 删除版本信息
 * @param req
 * @return
 */
	ResultEntity deletePatchInfo(String req);
	/**
	 * 批量删除
	 *
	 * <p>2016年7月30日 下午5:58:35 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	ResultEntity delMultiPatchInfo(String req);
	/**
	 * 修改版本信息
	 * @param req
	 * @return
	 */
	ResultEntity updatePatchInfo(String req);
}
