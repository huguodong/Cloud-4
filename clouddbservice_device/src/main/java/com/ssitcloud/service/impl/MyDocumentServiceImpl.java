package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.MyDocumentDao;
import com.ssitcloud.entity.MyDocumentEntity;
import com.ssitcloud.service.MyDocumentService;


@Service
public class MyDocumentServiceImpl implements MyDocumentService {
	@Resource
	private MyDocumentDao myDocumentDao;

	@Override
	public int insertMyDocument(
			MyDocumentEntity myDocumentEntity) {
		return myDocumentDao.insertMyDocument(myDocumentEntity);
	}

	@Override
	public int updateMyDocument(
			MyDocumentEntity myDocumentEntity) {
		return myDocumentDao.updateMyDocument(myDocumentEntity);
	}

	@Override
	public int deleteMyDocument(
			MyDocumentEntity myDocumentEntity) {
		return myDocumentDao.deleteMyDocument(myDocumentEntity);
	}

	@Override
	public MyDocumentEntity queryOneMyDocument(
			MyDocumentEntity myDocumentEntity) {
		return myDocumentDao.queryOneMyDocument(myDocumentEntity);
			
	}

	@Override
	public List<MyDocumentEntity> queryMyDocuments(
			MyDocumentEntity myDocumentEntity) {
		return myDocumentDao.queryMyDocuments(myDocumentEntity);
		
	}

	

}
