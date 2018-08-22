package com.ssitcloud.dbauth.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.dbauth.common.dao.CommonDao;
import com.ssitcloud.dbauth.entity.RelLibsEntity;

/**
 * <p>2016年4月5日 下午1:50:57
 * @author hjc
 *
 */
public interface RelLibsDao extends CommonDao{

	List<RelLibsEntity> selectRelLibsByidx(int lib_idx);
	
	int addNewRelLibs(RelLibsEntity relLibsEntity);
	
	int deleteRelLibs(int library_idx);
	
	List<RelLibsEntity> selectSlaveLibsByidx(int lib_idx);
	
	int updateRelLibs(RelLibsEntity relLibsEntity);
	
	int delRellibsBySlaveidx(int library_idx);
	
	List<RelLibsEntity> selectByIdx(RelLibsEntity relLibsEntity);
	
	int delRellibsByMasteridx(int library_idx);
	
	List<RelLibsEntity> selmasterLibsByIdx(Map<String,Object> map);
	
}
