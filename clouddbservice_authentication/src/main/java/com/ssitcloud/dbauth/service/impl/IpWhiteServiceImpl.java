package com.ssitcloud.dbauth.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbauth.dao.IpWhiteDao;
import com.ssitcloud.dbauth.entity.IpWhiteEntity;
import com.ssitcloud.dbauth.service.IpWhiteService;

/**
 * <p>2016年4月5日 上午11:45:24
 * @author hjc
 *
 */
@Service
public class IpWhiteServiceImpl implements IpWhiteService{
	@Resource
	private IpWhiteDao ipWhiteDao;

	@Override
	public int addIpWhite(IpWhiteEntity ipWhiteEntity) {
		return ipWhiteDao.addIpWhite(ipWhiteEntity);
	}

	@Override
	public IpWhiteEntity selIpWhiteByIdx(IpWhiteEntity ipWhiteEntity) {
		return ipWhiteDao.selIpWhiteByIdx(ipWhiteEntity);
	}

	@Override
	public int delIpWhiteByOperIdx(IpWhiteEntity ipWhiteEntity) {
		return ipWhiteDao.delIpWhiteByOperIdx(ipWhiteEntity);
	}

	@Override
	public int updIpWhite(IpWhiteEntity ipWhiteEntity) {
		return ipWhiteDao.updIpWhite(ipWhiteEntity);
	}
	
	
}
