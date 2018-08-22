package com.ssitcloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssitcloud.dao.LibraryAcsLogininfoDao;
import com.ssitcloud.entity.LibraryAcsLogininfoEntity;
import com.ssitcloud.service.LibraryAcsLogininfoServiceI;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年3月2日 下午3:26:21
 */
@Service
public class LibraryAcsLogininfoServiceImpl implements LibraryAcsLogininfoServiceI {

	@Autowired
	private LibraryAcsLogininfoDao libraryAcsLogininfoDao;
	
	@Override
	public List<LibraryAcsLogininfoEntity> selectLibraryAcsLogininfos(LibraryAcsLogininfoEntity libraryAcsLogininfo) {
		return libraryAcsLogininfoDao.queryLibraryAcsLogininfo(libraryAcsLogininfo);
	}

}
