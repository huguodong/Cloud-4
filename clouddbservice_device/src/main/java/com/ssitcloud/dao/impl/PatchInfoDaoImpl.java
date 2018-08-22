package com.ssitcloud.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.common.entity.PageEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.PatchInfoDao;
import com.ssitcloud.entity.PatchInfoEntity;
import com.ssitcloud.entity.ReaderTypeEntity;
import com.ssitcloud.entity.page.DevicePageEntity;
import com.ssitcloud.entity.page.PatchInfoPageEntity;

/**
 * 
 * @comment 版本信息表的增删改查
 * 
 * @author hwl
 * @data 2016年4月11日
 */
@Repository
public class PatchInfoDaoImpl extends CommonDaoImpl implements PatchInfoDao {

	@Override
	public int insert(PatchInfoEntity patchInfoEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("patchinfo.insert",
				patchInfoEntity);
	}

	@Override
	public int delete(PatchInfoEntity patchInfoEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("patchinfo.delete",
				patchInfoEntity);
	}

	@Override
	public int update(PatchInfoEntity patchInfoEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("patchinfo.update",
				patchInfoEntity);
	}

	@Override
	public List<PatchInfoEntity> select(PatchInfoEntity patchInfoEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("patchinfo.select",
				patchInfoEntity);
	}

	@Override
	public List<PatchInfoEntity> selectListByVersions(List<String> version) {

		return this.sqlSessionTemplate.selectList(
				"patchinfo.selectListByVersions", version);
	}
	@SuppressWarnings("unchecked")
	@Override
	public PatchInfoPageEntity selectPage(
			PatchInfoPageEntity patchInfoPageEntity) {
//		// 这里的total是总页数
//		PatchInfoPageEntity total = sqlSessionTemplate.selectOne(
//				"patchinfo.selectPageNum", patchInfoPageEntity);
//		patchInfoPageEntity.setTotal(total.getTotal());
		Integer mCount= sqlSessionTemplate.selectOne("patchinfo.count");
		patchInfoPageEntity.setTotal(mCount);
//		if(mCount>0)
//		{
//			if(mCount%patchInfoPageEntity.getPageSize()==0)
//			patchInfoPageEntity.setTotal(mCount%patchInfoPageEntity.getPageSize());
//			else
//				patchInfoPageEntity.setTotal(mCount%patchInfoPageEntity.getPageSize());
//		}	
		patchInfoPageEntity.setPatchInfo_num(mCount);

		patchInfoPageEntity.setDoAount(false);
		List<PatchInfoPageEntity> patchInfoList=sqlSessionTemplate.selectList("patchinfo.selectPage", patchInfoPageEntity);
		patchInfoPageEntity.setRows(patchInfoList);
		return patchInfoPageEntity;
	}

	@Override
	public PatchInfoEntity selectByIdx(Integer dataIdx) {
		return sqlSessionTemplate.selectOne("patchinfo.selectByIdx", dataIdx);
	}

	@Override
	public Integer selectPatchInfoCountByVersion(PatchInfoEntity patchInfoEntity) {
		return sqlSessionTemplate.selectOne("patchinfo.selectPatchInfoCountByVersion", patchInfoEntity);
	}

}
