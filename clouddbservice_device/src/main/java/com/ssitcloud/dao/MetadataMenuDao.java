package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.entity.MetadataMenuEntity;


/**
 * 
 *
 * <p>2017年2月14日 下午3:17:51  
 * @author hjc 
 *
 */
public interface MetadataMenuDao {
	
	/**
	 * 查询所有菜单
	 *
	 * <p>2017年2月14日 下午3:55:04 
	 * <p>create by hjc
	 * @param flags
	 * @return
	 */
	public List<MetadataMenuEntity> queryAll(String flags);
	
	/**
	 * 根据用户idx查询其菜单
	 *
	 * <p>2017年2月15日 下午6:59:35 
	 * <p>create by hjc
	 * @param operator_idx
	 * @param flags
	 * @return
	 */
	public List<MetadataMenuEntity> SelMenuByOperIdx(String operator_idx,String flags);
	/**
	 * 根据用户main_menu_code查询其菜单
	 *lqw
	 * <p>2017年3月20日 
	 * @return
	 */
	public List<MetadataMenuEntity> selectByCode(MetadataMenuEntity metadataMenuEntity);


}
