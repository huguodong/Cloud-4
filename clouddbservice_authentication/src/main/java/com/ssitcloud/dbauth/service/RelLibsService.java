package com.ssitcloud.dbauth.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.dbauth.entity.RelLibsEntity;

/**
 * <p>2016年4月5日 下午1:49:36
 * @author hjc
 */
public interface RelLibsService {
	/**
	 * 根据 master_lib_idx 或者 slave_lib_idx 查询。
	 * @param relLibsEntity
	 * @return
	 */
	List<RelLibsEntity> selectByIdx(RelLibsEntity relLibsEntity);
	
	/**
	 * 根据主馆idx查询出它的所属馆
	 * author huanghuang
	 * 2017年5月31日 下午3:41:10
	 * @param lib_idx
	 * @return
	 */
	List<RelLibsEntity> selectRelLibsByidx(int lib_idx);
	
	List<RelLibsEntity> selmasterLibsByIdx(Map<String,Object> map);
	

}
