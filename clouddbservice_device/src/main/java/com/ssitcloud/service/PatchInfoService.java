package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.AskVersionResultEntity;
import com.ssitcloud.entity.PatchInfoEntity;
import com.ssitcloud.entity.UpgradeStrategyEntity;
import com.ssitcloud.entity.page.DevicePageEntity;
import com.ssitcloud.entity.page.PatchInfoPageEntity;

/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月11日
 */
public interface PatchInfoService {

	/**
	 * 添加版本信息
	 * @param patchInfoEntity
	 * @return
	 * @author hwl
	 */
	public abstract int addPatchInfo(PatchInfoEntity patchInfoEntity);

	/**
	 * 删除版本信息
	 * @param id
	 * @return
	 * @author hwl
	 */
	public abstract int deletePatchInfo(PatchInfoEntity patchInfoEntity);

	/**
	 * 修改版本信息
	 * @param patchInfoEntity
	 * @return
	 * @author hwl
	 */
	public abstract int updatePatchInfo(PatchInfoEntity patchInfoEntity);

	/**
	 * 条件查询版本信息
	 * @param patchInfoEntity
	 * @return
	 * @author hwl
	 */
	public abstract List<PatchInfoEntity> selectPatchInfo(
			PatchInfoEntity patchInfoEntity);
	/**分页查询升级模板信息
	 * @param page
	 * @return
	 */
	public abstract PatchInfoPageEntity selPatchInfoByPage(PatchInfoPageEntity page);
	public abstract AskVersionResultEntity askVersion(
			UpgradeStrategyEntity fromJson);
	
	/**
	 * 删除单条版本信息
	 *
	 * <p>2016年7月30日 下午5:48:55 
	 * <p>create by hjc
	 * @param idx
	 * @return
	 */
	public abstract ResultEntity deleteSinglePatchInfo(String idx);
	/**
	 * 批量删除版本信息
	 *
	 * <p>2016年7月30日 下午5:48:55 
	 * <p>create by hjc
	 * @param idx
	 * @return
	 */
	public abstract ResultEntity delMultiPatchInfo(String json);

	public abstract ResultEntity SetDeviceIdsLibDistinctInHeartBeatByPathInfoIdx(
			String req);

	public abstract ResultEntity SetDeviceIdsDevTypeDistinctInHeartBeatByPathInfoIdx(
			String req);
	/**
	 * 查询 该版本的 记录数
	 * @param patchInfoEntity
	 * @return
	 */
	public abstract Integer selectPatchInfoCountByVersion(PatchInfoEntity patchInfoEntity);
}
