package com.ssitcloud.dbauth.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbauth.dao.LibraryInfoDao;
import com.ssitcloud.dbauth.entity.LibraryInfoEntity;
import com.ssitcloud.dbauth.service.LibraryInfoService;
import com.ssitcloud.dbauth.utils.LogUtils;

/**
 * <p>2016年4月5日 上午11:31:35
 * @author hjc
 *
 */
@Service
public class LibraryInfoServiceImpl implements LibraryInfoService{
	@Resource
	private LibraryInfoDao libraryInfoDao;

	@Override
	public int addLibraryInfo(LibraryInfoEntity libraryInfoEntity) {
		return libraryInfoDao.addLibraryInfo(libraryInfoEntity);
	}

	@Override
	public Boolean addLibraryInfoList(List<LibraryInfoEntity> libraryInfoEntities) {
		try {
			if (libraryInfoEntities!=null && libraryInfoEntities.size() >0) {
				for (LibraryInfoEntity libraryInfoEntity : libraryInfoEntities) {
					int ret = libraryInfoDao.addLibraryInfo(libraryInfoEntity);
					if(ret<=0) {
						throw new RuntimeException("新增图书馆信息出错");
					}
				}
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			LogUtils.error("批量新增图书馆信息出错",e);
			throw new RuntimeException(e);
		}
	}

    @Override
    public List<LibraryInfoEntity> selectByParam(LibraryInfoEntity param) {
        return libraryInfoDao.selectByParam(param);
    }
}
