package com.ssitcloud.dblib.service;

import com.ssitcloud.common.entity.ResultEntity;


/**
 * 上传的表信息 有  reader  ，bookitem
 *
 * <p>2017年4月11日 下午4:23:30  
 * @author hjc 
 *
 */
public interface LibraryInfoService<T> {
	
	/**
	 * 根据不同的实现，对应不同的上传配置信息
	 * @param downcfgSync
	 * @return
	 */
	ResultEntity execute(T t);
	


}
