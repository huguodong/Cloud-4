package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.entity.PatchInfoEntity;
import com.ssitcloud.entity.page.DevicePageEntity;
import com.ssitcloud.entity.page.PatchInfoPageEntity;

/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月11日
 */
public interface PatchInfoDao extends CommonDao {

	public abstract int insert(PatchInfoEntity patchInfoEntity);

	public abstract int delete(PatchInfoEntity patchInfoEntity);

	public abstract int update(PatchInfoEntity patchInfoEntity);

	public abstract List<PatchInfoEntity> select(
			PatchInfoEntity patchInfoEntity);

	List<PatchInfoEntity> selectListByVersions(List<String> version);
	PatchInfoPageEntity selectPage(PatchInfoPageEntity patchInfoPageEntity);

	public abstract PatchInfoEntity selectByIdx(Integer dataIdx);

	public abstract Integer selectPatchInfoCountByVersion(
			PatchInfoEntity patchInfoEntity);
}
