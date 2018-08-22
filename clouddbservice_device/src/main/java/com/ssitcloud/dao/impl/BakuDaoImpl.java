package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.BakuDao;
import com.ssitcloud.entity.BakupDataEntity;

@Repository
public class BakuDaoImpl  extends CommonDaoImpl implements BakuDao {

	@Override
	public List<BakupDataEntity> queryBakDataInfo(Map<String, Object> m) {
		return getSqlSession().selectList("bakuDao.queryBakDataInfo", m);
	}
	@Override
	public BakupDataEntity queryBakDataInfoByIdx(Map<String, Object> m) {
		return getSqlSession().selectOne("bakuDao.queryBakDataInfoByIdx", m);
	}
	@Override
	public int delBakDataInfoByIdx(Map<String, Object> m){
		return getSqlSession().delete("bakuDao.delBakDataInfoByIdx", m);
	}
	@Override
	public int updBakDataInfoByIdx(Map<String, Object> m){
		return getSqlSession().delete("bakuDao.updBakDataInfoByIdx", m);
	}
	@Override
	public int updBakDataInfoByFilePath(Map<String, Object> m){
		return getSqlSession().delete("bakuDao.updBakDataInfoByFilePath", m);
	}
	@Override
	public int insertBakDataInfo(BakupDataEntity m) {
		return getSqlSession().insert("bakuDao.insertBakDataInfo", m);
	}
	@Override
	public List<BakupDataEntity> queryBakDataInfoByPage(BakupDataEntity bakupData) {
		return getSqlSession().selectList("bakuDao.queryBakDataInfoByPage", bakupData);
	}
	@Override
	public int updBakDataInfoToExist(List<Integer> needUpdToExistList) {
		return getSqlSession().update("bakuDao.updBakDataInfoToExist", needUpdToExistList);
	}
	@Override
	public int updBakDataInfoToNotExist(List<Integer> needUpdToNotExistList) {
		return getSqlSession().update("bakuDao.updBakDataInfoToNotExist", needUpdToNotExistList);
	}
	@Override
	public int delBakDataInfoByPath(List<String> list) {
		return getSqlSession().delete("bakuDao.delBakDataInfoByPath", list);
	}
	@Override
	public Map<String, Object> getLastLibBakUpTime(Map<String, Object> map) {
		return getSqlSession().selectOne("bakuDao.getLastLibBakUpTime", map);
	}
	
}
