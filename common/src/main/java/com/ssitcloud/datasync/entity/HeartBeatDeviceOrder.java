package com.ssitcloud.datasync.entity;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.ssitcloud.devmgmt.entity.DeviceOrder;
/**
 * key =device_id+Library_id   value={"control":"1"}/{"control":"0"}
 * @package: com.ssitcloud.datasync.entity
 * @classFile: HeartBeatContainer
 * @author: liubh
 * @createTime: 2016年4月19日 下午11:18:51
 * @description: TODO
 */
public class HeartBeatDeviceOrder extends ConcurrentHashMap<String, ConcurrentLinkedQueue<DeviceOrder>>{

	private static final long serialVersionUID = 1L;

	public HeartBeatDeviceOrder() {
		
	}
	public HeartBeatDeviceOrder(int initialCapacity, float loadFactor, int concurrencyLevel) {
		super(initialCapacity, loadFactor, concurrencyLevel);
	}
	public HeartBeatDeviceOrder(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}
	public HeartBeatDeviceOrder(int initialCapacity) {
		super(initialCapacity);
	}
	public HeartBeatDeviceOrder(Map<? extends String, ? extends ConcurrentLinkedQueue<DeviceOrder>> m) {
		super(m);
	}
	
}
