package com.ssitcloud.datasync.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class HeartBeatDataSyncMsg extends ConcurrentHashMap<String, ConcurrentLinkedQueue<SyncDataEntity>>{

	
	private static final long serialVersionUID = 1L;

	public HeartBeatDataSyncMsg() {
	}

	public HeartBeatDataSyncMsg(int initialCapacity, float loadFactor, int concurrencyLevel) {
		super(initialCapacity, loadFactor, concurrencyLevel);
	}

	public HeartBeatDataSyncMsg(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public HeartBeatDataSyncMsg(int initialCapacity) {
		super(initialCapacity);
	}

	public HeartBeatDataSyncMsg(
			Map<? extends String, ? extends ConcurrentLinkedQueue<SyncDataEntity>> m) {
		super(m);
	}
	
}
