package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.entity.SelfCheckProtocolEntity;
import com.ssitcloud.service.SelfCheckConfService;

@Service
public class SelfCheckConfServiceImpl implements SelfCheckConfService{
	
	@Resource(name="commonDao")
	private CommonDao commonDao;
	
	@Override
	public int insertSelfCheckProtocol(SelfCheckProtocolEntity selfCheckProtocol){
		if(selfCheckProtocol==null) return 0;
		return commonDao.getSqlSession().insert("deviceSelfCheckConf.insertSelfCheckConf", selfCheckProtocol);
		
	}
	@Override
	public int updateSelfCheckProtocol(SelfCheckProtocolEntity selfCheckProtocol){
		if(selfCheckProtocol==null)return 0;
		return commonDao.getSqlSession().update("deviceSelfCheckConf.updaetSelfCheckConf", selfCheckProtocol);
	}
	@Override
	public int DelSelfCheckProtocol(SelfCheckProtocolEntity selfCheckProtocol){
		if(selfCheckProtocol==null) return 0;
		return commonDao.getSqlSession().delete("deviceSelfCheckConf.deleteSelfCheckConf", selfCheckProtocol);
	}
	@Override
	public List<SelfCheckProtocolEntity> selectList(SelfCheckProtocolEntity selfCheckProtocol){
		return commonDao.getSqlSession().selectList("deviceSelfCheckConf.querySelfCheckConf", selfCheckProtocol);
	}
	@Override
	public List<SelfCheckProtocolEntity> selectListExactly(SelfCheckProtocolEntity selfCheckProtocol){
		return commonDao.getSqlSession().selectList("deviceSelfCheckConf.querySelfCheckConfExactly", selfCheckProtocol);
	}
	
}
