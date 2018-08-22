package com.ssitcloud.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.TableChangeTriDao;
import com.ssitcloud.entity.TableChangeTriEntity;

@Repository
public class TableChangeTriDaoImpl extends CommonDaoImpl implements TableChangeTriDao{

	@Override
	public int insertOne(TableChangeTriEntity changerTri) {
		// TODO Auto-generated method stub
		return getSqlSession().insert("tableChangeTri.insertOne", changerTri);
	}

	@Override
	public int updateOne(TableChangeTriEntity changerTri) {
		// TODO Auto-generated method stub
		return getSqlSession().update("tableChangeTri.updateOne", changerTri);
	}

	@Override
	public List<TableChangeTriEntity> selectList(TableChangeTriEntity changerTri) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("tableChangeTri.selectList", changerTri);
	}

	@Override
	public TableChangeTriEntity selectOne(TableChangeTriEntity changerTri) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("tableChangeTri.selectOne", changerTri);
	}
	@Override
	public List<TableChangeTriEntity> selectBycreatTimeDescByIdx(TableChangeTriEntity changerTri) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("tableChangeTri.selectTableNamesOrderByCreatimeDescByIdx",changerTri);
	}
	@Override
	public List<TableChangeTriEntity> selectPatchInfoChangesOrderByCreatimeDescByIdx(TableChangeTriEntity changerTri){
		return getSqlSession().selectList("tableChangeTri.selectPatchInfoChangesOrderByCreatimeDescByIdx", changerTri);
	}
	
	@Override
	public int deleteOne(TableChangeTriEntity changerTri) {
		// TODO Auto-generated method stub
		return getSqlSession().delete("tableChangeTri.deleteOne", changerTri);
	}

	@Override
	public int deleteDateWhereisOutof(int day) {
		// 获取系统时间 -day ,小于这个时间的都要删掉。
		DateTime newTime=new DateTime().minusDays(day);
		return getSqlSession().delete("tableChangeTri.deleteDateWhereisOutof", newTime.toString("yyyy-MM-dd"));
	}

	@Override
	public int updataRequestTime(Map<String, Object> params) {
		return getSqlSession().update("tableChangeTri.updataRequestTime", params);
	}

	@Override
	public int updateInTriIdx(List<Integer> idxList) {
		return getSqlSession().update("tableChangeTri.updataRequestTimeByIdxList", idxList);
	}

	@Override
	public int updateBytableNameList(List<String> tableNameList) {
		// TODO Auto-generated method stub
		return getSqlSession().update("tableChangeTri.updateBytableNameList", tableNameList);
	}

	

}
