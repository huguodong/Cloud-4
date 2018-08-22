package com.ssitcloud.business.mobile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.business.mobile.fatory.AppElectronicConverterFatory;
import com.ssitcloud.business.mobile.service.AppElectronicConverterServiceI;
import com.ssitcloud.business.mobile.service.LibraryCacheServiceI;
import com.ssitcloud.business.mobile.service.MetadataOpercmdCacheServiceI;
import com.ssitcloud.devmgmt.entity.MetadataOpercmdEntity;
import com.ssitcloud.mobile.entity.AppElectronicEntity;
import com.ssitcloud.mobile.entity.ElectronicCertificateEntity;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年3月31日 上午10:04:36
 */
@Service
public class AppElectronicConverterServiceImpl implements AppElectronicConverterServiceI {
	
	@Autowired
	@Qualifier("libraryCacheService")
	private LibraryCacheServiceI libraryCacheService;
	
	@Autowired
	@Qualifier("MetadataOpercmdCacheService")
	private MetadataOpercmdCacheServiceI metadataOpercmdCacheService;
	
	@Override
	public AppElectronicEntity converter(ElectronicCertificateEntity elec) {
		MetadataOpercmdEntity cmd = metadataOpercmdCacheService.get(elec.getElectronic_type());
		LibraryEntity library = libraryCacheService.get(elec.getLib_idx());
		
		return AppElectronicConverterFatory.converter(elec, cmd , library );
	}

	
}
