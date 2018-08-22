package com.ssitcloud.dblib.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.dblib.entity.ElectronicCertificateEntity;
import com.ssitcloud.dblib.entity.page.ElectronicCertificatePageEntity;

public interface ElectronicCertificateDao {
	/**
	 * 插入
	 */
	int insertElectronicCertificate(ElectronicCertificateEntity certificateEntity);
	
	ElectronicCertificateEntity selectElectronicCertificate(ElectronicCertificateEntity certificateEntity);
	
	List<ElectronicCertificateEntity> selectElectronicCertificateS(ElectronicCertificatePageEntity certificateEntity);
	
	int countElectronicCertificate(ElectronicCertificateEntity certificateEntity);
	
	
	/**
	 * 检查相同的数据有多少个
	 * lib_idx electronic_type cardno barno title controller_time 这几个参数要一致才算same
	 *
	 * <p>2017年3月4日 下午5:24:26 
	 * <p>create by hjc
	 * @param certificateEntity
	 * @return
	 */
	int checkSameElectronicCertificate(ElectronicCertificateEntity certificateEntity );
	
	List<ElectronicCertificateEntity> selectByReaderIdx(Map<String, Object> data);
	
	int updateElectronicState(Map<String, Object> data);
}
