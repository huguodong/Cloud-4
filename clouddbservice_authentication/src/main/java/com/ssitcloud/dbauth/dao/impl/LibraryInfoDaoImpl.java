package com.ssitcloud.dbauth.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dbauth.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbauth.dao.LibraryInfoDao;
import com.ssitcloud.dbauth.entity.LibraryInfoEntity;
import com.ssitcloud.dbauth.entity.LibraryMetatypeInfoEntity;

/**
 * 图书馆操作数据库层
 * <p>2016年4月5日 上午11:24:16
 * @author hjc
 *
 */
@Repository
public class LibraryInfoDaoImpl extends CommonDaoImpl implements LibraryInfoDao {


	
	@Override
	public int addLibraryInfo(LibraryInfoEntity libraryInfoEntity) {
		return this.sqlSessionTemplate.insert("libraryInfo.addLibraryInfo",libraryInfoEntity);
	}

	@Override
	public List<LibraryMetatypeInfoEntity> selectLibraryInfoByidx(int library_idx) {
		return this.sqlSessionTemplate.selectList("libraryInfo.selectLibraryInfoByidx", library_idx);
	}

	@Override
	public int deleteLibInfo(int library_idx) {
		return this.sqlSessionTemplate.delete("libraryInfo.deleteByidx", library_idx);
	}

    @Override
    public List<LibraryInfoEntity> selectByParam(LibraryInfoEntity param) {
        return sqlSessionTemplate.selectList("libraryInfo.selectByParam",param);
    }
}
