package com.ssitcloud.dbauth.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbauth.dao.RelLibsDao;
import com.ssitcloud.dbauth.entity.RelLibsEntity;
import com.ssitcloud.dbauth.service.RelLibsService;

/**
 * <p>2016年4月5日 下午1:50:02
 * @author hjc
 *
 */
@Service
public class RelLibsServiceImpl implements RelLibsService{
	@Resource
	private RelLibsDao relLibsDao;
	
	@Override
	public List<RelLibsEntity> selectByIdx(RelLibsEntity relLibsEntity){
		return relLibsDao.selectByIdx(relLibsEntity);
	}

	@Override
	public List<RelLibsEntity> selectRelLibsByidx(int lib_idx) {
		return relLibsDao.selectRelLibsByidx(lib_idx);
	}
	
	@Override
	public List<RelLibsEntity> selmasterLibsByIdx(Map<String,Object> map) {
		return relLibsDao.selmasterLibsByIdx(map);
	}
}
