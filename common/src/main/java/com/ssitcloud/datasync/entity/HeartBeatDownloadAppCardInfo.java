package com.ssitcloud.datasync.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.ssitcloud.devmgmt.entity.AppCardInfo;


/**
 * 设备下载手机传过来的二维码信息
 *
 * <p>2017年4月8日 下午2:37:51  
 * @author hjc 
 *
 */
public class HeartBeatDownloadAppCardInfo extends ConcurrentHashMap<String, ConcurrentLinkedQueue<AppCardInfo>>{

	private static final long serialVersionUID = 1L;

	public HeartBeatDownloadAppCardInfo() {
		
	}
	public HeartBeatDownloadAppCardInfo(int initialCapacity, float loadFactor, int concurrencyLevel) {
		super(initialCapacity, loadFactor, concurrencyLevel);
	}
	public HeartBeatDownloadAppCardInfo(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}
	public HeartBeatDownloadAppCardInfo(int initialCapacity) {
		super(initialCapacity);
	}
	public HeartBeatDownloadAppCardInfo(Map<? extends String, ? extends ConcurrentLinkedQueue<AppCardInfo>> m) {
		super(m);
	}
	
}
