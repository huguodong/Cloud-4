package com.ssitcloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.FunMaindataDao;
import com.ssitcloud.entity.FunMaindataEntity;
import com.ssitcloud.service.FunMaindataService;


@Service
public class FunMaindataServiceImpl implements FunMaindataService {
	@Resource
	private FunMaindataDao funMaindataDao;

	@Override
	public FunMaindataEntity queryOneFunMaindata(
			FunMaindataEntity funMaindataEntity) {
		return funMaindataDao.queryOneFunMaindata(funMaindataEntity);
			
	}

	@Override
	public List<FunMaindataEntity> queryFunMaindatas(
			FunMaindataEntity funMaindataEntity) {
		return funMaindataDao.queryFunMaindatas(funMaindataEntity);
		
	}



	

}
