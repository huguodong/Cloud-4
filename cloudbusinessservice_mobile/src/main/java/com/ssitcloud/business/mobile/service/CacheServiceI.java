package com.ssitcloud.business.mobile.service;

/**
 * 缓存服务类
 * @author LXP
 * @version 创建时间：2017年3月31日 下午2:29:18
 * @param <K>
 * @param <V>
 */
public interface CacheServiceI<K,V> {
	V get(K k);
	
	/**
	 * 同步更新缓存
	 */
	void update();
	
	/**
	 * 异步更新缓存
	 */
	void updateAsync();
}
