package com.ssitcloud.datasync.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class HeartBeatTransDataState extends ConcurrentHashMap<String, ConcurrentLinkedQueue<Map<String, Object>>>{

	private static final long serialVersionUID = 1L;

	public HeartBeatTransDataState() {
		
	}
	public HeartBeatTransDataState(int initialCapacity, float loadFactor, int concurrencyLevel) {
		super(initialCapacity, loadFactor, concurrencyLevel);
	}
	public HeartBeatTransDataState(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}
	public HeartBeatTransDataState(int initialCapacity) {
		super(initialCapacity);
	}
	public HeartBeatTransDataState(Map<? extends String, ? extends ConcurrentLinkedQueue<Map<String, Object>>> m) {
		super(m);
	}
	
}
