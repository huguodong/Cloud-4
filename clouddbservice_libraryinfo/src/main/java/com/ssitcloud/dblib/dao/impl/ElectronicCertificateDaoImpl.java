package com.ssitcloud.dblib.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.dao.ElectronicCertificateDao;
import com.ssitcloud.dblib.entity.ElectronicCertificateEntity;
import com.ssitcloud.dblib.entity.page.ElectronicCertificatePageEntity;

@Repository
public class ElectronicCertificateDaoImpl extends CommonDaoImpl implements ElectronicCertificateDao{

	@Override
	public int insertElectronicCertificate(ElectronicCertificateEntity certificateEntity) {
		return this.sqlSessionTemplate.insert("electronicCertificate.insertElectronicCertificate",certificateEntity);
	}

	@Override
	public ElectronicCertificateEntity selectElectronicCertificate(ElectronicCertificateEntity certificateEntity) {
		return this.sqlSessionTemplate.selectOne("electronicCertificate.selectElectronicCertificate",certificateEntity);
	}

	@Override
	public List<ElectronicCertificateEntity> selectElectronicCertificateS(
			ElectronicCertificatePageEntity certificateEntity) {
		return this.sqlSessionTemplate.selectList("electronicCertificate.selectElectronicCertificateS",certificateEntity);
	}

	@Override
	public int countElectronicCertificate(ElectronicCertificateEntity certificateEntity) {
		return this.sqlSessionTemplate.selectOne("electronicCertificate.countElectronicCertificateS",certificateEntity);
	}

	@Override
	public int checkSameElectronicCertificate(
			ElectronicCertificateEntity certificateEntity) {
		return this.sqlSessionTemplate.selectOne("electronicCertificate.checkSameElectronicCertificate",certificateEntity);
	}

	@Override
	public List<ElectronicCertificateEntity> selectByReaderIdx(Map<String, Object> data) {
		return this.sqlSessionTemplate.selectList("electronicCertificate.selectByReaderIdx",data);
	}

	@Override
	public int updateElectronicState(Map<String, Object> data) {
		return this.sqlSessionTemplate.update("electronicCertificate.updateElectronicState",data);
	}
}
