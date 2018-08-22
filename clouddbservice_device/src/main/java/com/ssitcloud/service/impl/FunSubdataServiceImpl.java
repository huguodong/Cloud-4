package com.ssitcloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.FunSubdataDao;
import com.ssitcloud.entity.FunSubdataEntity;
import com.ssitcloud.service.FunSubdataService;


@Service
public class FunSubdataServiceImpl implements FunSubdataService {
	@Resource
	private FunSubdataDao funSubdataDao;


	@Override
	public FunSubdataEntity queryOneFunSubdata(
			FunSubdataEntity funSubdataEntity) {
		return funSubdataDao.queryOneFunSubdata(funSubdataEntity);
			
	}

	@Override
	public List<FunSubdataEntity> queryFunSubdatas(
			FunSubdataEntity funSubdataEntity) {
		return funSubdataDao.queryFunSubdatas(funSubdataEntity);
		
	}

	

}
