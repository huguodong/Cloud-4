package com.ssitcloud.dbauth.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.dao.LibraryDao;
import com.ssitcloud.dbauth.dao.LibraryExtendInfoDao;
import com.ssitcloud.dbauth.entity.LibraryExtendInfoEntity;
import com.ssitcloud.dbauth.service.LibraryExtendInfoService;

/**
 * 图书馆扩展信息处理类
 * <p>2017年4月26日 上下午1:58
 * @author shuangjunjie
 *
 */
@Transactional
@Service
public class LibraryExtendInfoServiceImpl implements LibraryExtendInfoService{
	@Resource
	private LibraryDao libraryDao;
	
	@Resource
	private LibraryExtendInfoDao libraryExtendInfoDao;

	@Override
	public LibraryExtendInfoEntity selRegionIdxByLibIdx(LibraryExtendInfoEntity libraryExtendInfoEntity) {
		return libraryExtendInfoDao.selRegionIdxByLibIdx(libraryExtendInfoEntity);
	}

	@Override
	public List<LibraryExtendInfoEntity> selRegionIdxsByLibIdxs(Map map) {
		return libraryExtendInfoDao.selRegionIdxsByLibIdxs(map);
	}
}
