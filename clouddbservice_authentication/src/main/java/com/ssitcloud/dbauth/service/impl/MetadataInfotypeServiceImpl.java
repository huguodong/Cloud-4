package com.ssitcloud.dbauth.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbauth.dao.MetadataInfotypeDao;
import com.ssitcloud.dbauth.entity.MetadataInfotypeEntity;
import com.ssitcloud.dbauth.service.MetadataInfotypeService;

/** 
 *
 * <p>2016年3月30日 下午2:02:44  
 * @author hjc 
 *
 */
@Service
public class MetadataInfotypeServiceImpl implements MetadataInfotypeService {
	@Resource
	MetadataInfotypeDao infotypeDao;
	
	@Override
	public void addMetadataInfotype(
			MetadataInfotypeEntity metadataInfotypeEntity) {
		infotypeDao.addMetadataInfotype(metadataInfotypeEntity);
	}

	@Override
	public List<MetadataInfotypeEntity> selMetadataInfotype() {
		return infotypeDao.selMetadataInfotype();
	}

	@Override
	public int delMetadataInfotypeByIdx(MetadataInfotypeEntity metadataInfotypeEntity) {
		return infotypeDao.delMetadataInfotypeByIdx(metadataInfotypeEntity);
	}

	@Override
	public List<MetadataInfotypeEntity> sellibdataInfotype() {
		// TODO Auto-generated method stub
		return infotypeDao.selLibdataInfotype();
	}

	
}
