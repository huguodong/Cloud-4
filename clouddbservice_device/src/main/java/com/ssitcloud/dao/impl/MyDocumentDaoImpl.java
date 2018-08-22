package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.MyDocumentDao;
import com.ssitcloud.entity.MyDocumentEntity;

@Repository
public class MyDocumentDaoImpl extends CommonDaoImpl implements
		MyDocumentDao {

	@Override
	public int insertMyDocument(MyDocumentEntity myDocumentEntity) {
		return this.sqlSessionTemplate.insert("mydocument.insertMyDocument", myDocumentEntity);
	}

	@Override
	public int updateMyDocument(MyDocumentEntity myDocumentEntity) {
		return this.sqlSessionTemplate.update("mydocument.updateMyDocument", myDocumentEntity);
	}

	@Override
	public int deleteMyDocument(MyDocumentEntity myDocumentEntity) {
		return this.sqlSessionTemplate.delete("mydocument.deleteMyDocument", myDocumentEntity);
	}

	@Override
	public MyDocumentEntity queryOneMyDocument(
			MyDocumentEntity myDocumentEntity) {
		return this.select("mydocument.selectMyDocument", myDocumentEntity);
	}

	@Override
	public List<MyDocumentEntity> queryMyDocuments(
			MyDocumentEntity myDocumentEntity) {
		return this.selectAll("mydocument.selectMyDocuments", myDocumentEntity);
	}

}
