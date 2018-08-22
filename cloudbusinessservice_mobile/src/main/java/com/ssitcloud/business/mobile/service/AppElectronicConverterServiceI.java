package com.ssitcloud.business.mobile.service;

import com.ssitcloud.mobile.entity.AppElectronicEntity;
import com.ssitcloud.mobile.entity.ElectronicCertificateEntity;

/**
 * 用户转换电子凭证实体到app电子凭证实体
 * @author LXP
 * @version 创建时间：2017年3月31日 上午9:51:08
 */
public interface AppElectronicConverterServiceI {
	/**
	 * 转换电子凭证类到app电子凭证类
	 * @param elec
	 * @return
	 */
	AppElectronicEntity converter(ElectronicCertificateEntity elec);
}
