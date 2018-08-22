package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.entity.LibraryAcsLogininfoEntity;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年3月2日 下午3:22:11
 */
public interface LibraryAcsLogininfoDao {

	List<LibraryAcsLogininfoEntity> queryLibraryAcsLogininfo(LibraryAcsLogininfoEntity libraryAcsLogininfo);
	
}
