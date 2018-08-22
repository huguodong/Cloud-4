package com.ssitcloud.business.mobile.service;

import java.util.List;
import java.util.Map;

/**
 * Biblios查询类
 * @author LXP
 * @version 创建时间：2017年2月28日 下午5:50:46
 */
public interface BibliosServiceI {
	
	/**
	 * 根据bib_idx查询Biblios
	 * @param bib_idx
	 * @return Biblios的map,若为null则获取数据失败
	 */
	Map<String, Object> selectBiblios(Integer bib_idx);
	
	Map<String, Object> queryBibliosForBCAndLib(Integer lib_id,String book_barcode);
}
