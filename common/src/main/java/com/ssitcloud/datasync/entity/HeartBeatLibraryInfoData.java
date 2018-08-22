package com.ssitcloud.datasync.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.ssitcloud.devmgmt.entity.SyncConfigEntity;


public class HeartBeatLibraryInfoData extends ConcurrentHashMap<String, ConcurrentLinkedQueue<SyncConfigEntity>>{

	private static final long serialVersionUID = 1L;

	public HeartBeatLibraryInfoData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HeartBeatLibraryInfoData(int initialCapacity, float loadFactor,
			int concurrencyLevel) {
		super(initialCapacity, loadFactor, concurrencyLevel);
		// TODO Auto-generated constructor stub
	}

	public HeartBeatLibraryInfoData(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
		// TODO Auto-generated constructor stub
	}

	public HeartBeatLibraryInfoData(int initialCapacity) {
		super(initialCapacity);
		// TODO Auto-generated constructor stub
	}

	public HeartBeatLibraryInfoData(
			Map<? extends String, ? extends ConcurrentLinkedQueue<SyncConfigEntity>> m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

}
