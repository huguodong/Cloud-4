package com.ssitcloud.datasync.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class HeartBeatTransOperLogState extends ConcurrentHashMap<String, ConcurrentLinkedQueue<Map<String, Object>>>{

	private static final long serialVersionUID = 1L;

	public HeartBeatTransOperLogState() {
		
	}
	public HeartBeatTransOperLogState(int initialCapacity, float loadFactor, int concurrencyLevel) {
		super(initialCapacity, loadFactor, concurrencyLevel);
	}
	public HeartBeatTransOperLogState(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}
	public HeartBeatTransOperLogState(int initialCapacity) {
		super(initialCapacity);
	}
	public HeartBeatTransOperLogState(Map<? extends String, ? extends ConcurrentLinkedQueue<Map<String, Object>>> m) {
		super(m);
	}
	
}
