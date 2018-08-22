package com.ssitcloud.business.mobile.service;

import java.util.Map;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年3月2日 下午3:53:10
 */
public interface LibraryAcsLogininfoServiceI {
	
	/**
	 * 根据图书馆idx查询acs配置信息，
	 * @param lib_idx 图书馆的idx
	 * @return 配置信息的map，若没有查询到返回null
	 */
	Map<String, Object> selectLibraryAcsLogininfo(Integer lib_idx);
}
