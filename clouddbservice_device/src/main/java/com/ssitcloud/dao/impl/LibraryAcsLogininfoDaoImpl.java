package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.LibraryAcsLogininfoDao;
import com.ssitcloud.entity.LibraryAcsLogininfoEntity;

@Repository
public class LibraryAcsLogininfoDaoImpl extends CommonDaoImpl implements LibraryAcsLogininfoDao {

	@Override
	public List<LibraryAcsLogininfoEntity> queryLibraryAcsLogininfo(LibraryAcsLogininfoEntity libraryAcsLogininfo) {
		return this.sqlSessionTemplate.selectList("libraryAcsLogininfo.selectLibraryAcsLogininfos",libraryAcsLogininfo);
	}

}
