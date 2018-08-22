package com.ssitcloud.dblib.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.entity.ElectronicCertificateEntity;
import com.ssitcloud.dblib.entity.page.ElectronicCertificatePageEntity;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年2月28日 上午10:57:04
 */
public interface ElectronicCertificateServiceI {
	/**
	 * 插入
	 */
	int insertElectronicCertificate(ElectronicCertificateEntity certificateEntity);
	
	ElectronicCertificateEntity selectElectronicCertificate(ElectronicCertificateEntity certificateEntity);
	
	List<ElectronicCertificateEntity> selectElectronicCertificateS(ElectronicCertificatePageEntity certificateEntity);
	
	int countElectronicCertificate(ElectronicCertificateEntity certificateEntity);
	
	/**
	 * 检查相同的数据有多少个
	 * lib_idx, electronic_type, cardno, barno, title, controller_time 这几个参数要一致才算same
	 *
	 * <p>2017年3月4日 下午5:24:26 
	 * <p>create by hjc
	 * @param certificateEntity
	 * @return
	 */
	int checkSameElectronicCertificate(ElectronicCertificateEntity certificateEntity );
	
	/**
	 * 根据用户主键查询电子凭证
	 * @param data
	 * @return
	 */
	ResultEntity selectByReaderIdx(Map<String, Object> data);
	
	ResultEntity updateState(Map<String, Object> data);
}
