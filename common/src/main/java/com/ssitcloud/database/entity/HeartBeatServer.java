package com.ssitcloud.database.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class HeartBeatServer extends ConcurrentHashMap<String, ConcurrentLinkedQueue<Server>>{

	private static final long serialVersionUID = 1L;

	public HeartBeatServer() {
		
	}
	public HeartBeatServer(int initialCapacity, float loadFactor, int concurrencyLevel) {
		super(initialCapacity, loadFactor, concurrencyLevel);
	}
	public HeartBeatServer(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}
	public HeartBeatServer(int initialCapacity) {
		super(initialCapacity);
	}
	public HeartBeatServer(Map<? extends String, ? extends ConcurrentLinkedQueue<Server>> m) {
		super(m);
	}
	
}
