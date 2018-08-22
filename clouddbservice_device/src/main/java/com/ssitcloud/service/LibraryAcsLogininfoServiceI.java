package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.entity.LibraryAcsLogininfoEntity;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年3月2日 下午3:24:45
 */
public interface LibraryAcsLogininfoServiceI {
	List<LibraryAcsLogininfoEntity> selectLibraryAcsLogininfos(LibraryAcsLogininfoEntity libraryAcsLogininfo);
}
