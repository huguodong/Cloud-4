package com.ssitcloud.business.mobile.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年2月28日 上午10:57:04
 */
public interface ElectronicCertificateServiceI {
	
	/**
	 * 查询ElectronicCertificate
	 * @param electronicCertificatePageJson ElectronicCertificatePageEntity搜索条件json
	 * @return
	 */
	ResultEntity selectElectronicCertificateS(String electronicCertificatePageJson);
	
	/**
	 * 统计ElectronicCertificate
	 * @param certificateEntityJson ElectronicCertificateEntity搜索条件json
	 * @return
	 */
	ResultEntity countElectronicCertificate(String certificateEntityJson);
	
	/**
	 * 根据读者idx和其他参数查询电子凭证
	 * @param data
	 * @return
	 */
	ResultEntity selectByReaderIdx(Map<String, Object> data);
	
	/**
	 * 设置已读电子凭证
	 * @param idxs 要更新的id列表
	 * @param state 要更新的状态
	 * @return
	 */
	ResultEntity setReaderElectronicCertificate(List<Integer> idxs,int state);

	/**
	 * 图书推荐服务
	 * @param map
	 * @return
	 */
	ResultEntity getRecommendList(Map<String, Object> map);
}
