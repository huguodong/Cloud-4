package com.ssitcloud.dbauth.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbauth.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbauth.dao.RelLibsDao;
import com.ssitcloud.dbauth.entity.RelLibsEntity;

/**
 * <p>2016年4月5日 下午1:51:29
 * @author hjc
 *
 */
@Repository
public class RelLibsDaoImpl extends CommonDaoImpl implements RelLibsDao{

	@Override
	public List<RelLibsEntity> selectRelLibsByidx(int lib_idx) {
		
		return this.sqlSessionTemplate.selectList("relLibs.selRelLibsByIdx", lib_idx);
	}

	@Override
	public int addNewRelLibs(RelLibsEntity relLibsEntity) {
		
		return this.sqlSessionTemplate.insert("relLibs.addRelLibs", relLibsEntity);
	}

	@Override
	public int deleteRelLibs(int library_idx) {
		
		return this.sqlSessionTemplate.delete("relLibs.deleteByidx", library_idx);
	}

	@Override
	public List<RelLibsEntity> selectSlaveLibsByidx(int lib_idx) {
		
		return this.sqlSessionTemplate.selectList("relLibs.selslaveLibsByIdx", lib_idx);
	}

	@Override
	public int updateRelLibs(RelLibsEntity relLibsEntity) {
		return this.sqlSessionTemplate.update("relLibs.updrellibsBySlaveidx", relLibsEntity);
	}

	@Override
	public int delRellibsBySlaveidx(int library_idx) {
		return this.sqlSessionTemplate.delete("relLibs.deleteBySlaveidx",library_idx);
	}

	@Override
	public List<RelLibsEntity> selectByIdx(RelLibsEntity relLibsEntity) {
		return this.sqlSessionTemplate.selectList("relLibs.selectByIdx",relLibsEntity);
	}
	
	@Override
	public int delRellibsByMasteridx(int library_idx) {
		return this.sqlSessionTemplate.delete("relLibs.deleteByMasteridx",library_idx);
	}

	@Override
	public List<RelLibsEntity> selmasterLibsByIdx(Map<String,Object> map) {
		
		return this.sqlSessionTemplate.selectList("relLibs.selmasterLibsByIdx", map);
	}
}
