package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.MetadataMenuDao;
import com.ssitcloud.entity.MetadataMenuEntity;
import com.ssitcloud.service.MetadataMenuService;
/**
 * 
 *
 * <p>2017年2月14日 下午3:21:35  
 * @author hjc 
 *
 */
@Service
public class MetadataMenuServiceImpl implements MetadataMenuService {

	@Resource
	private MetadataMenuDao menuDao;

	@Override
	public List<MetadataMenuEntity> queryAll(String flags) {
		return menuDao.queryAll(flags);
	}

	@Override
	public List<MetadataMenuEntity> SelMenuByOperIdx(String operator_idx,String flags) {
		if(operator_idx==null||"".equals(operator_idx))return null;
		return menuDao.SelMenuByOperIdx(operator_idx,flags);
	}

	@Override
	public List<MetadataMenuEntity> selectByCode(
			MetadataMenuEntity metadataMenuEntity) {
		return menuDao.selectByCode(metadataMenuEntity);
	}
	
	
}
