package com.ssitcloud.datasync.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.ssitcloud.devmgmt.entity.PatchInfoEntity;
import com.ssitcloud.devmgmt.entity.TableChangeTriEntity;

public class HeartBeatMidVersionData extends ConcurrentHashMap<String, ConcurrentLinkedQueue<TableChangeTriEntity>>{

	
	private static final long serialVersionUID = 1L;

	public HeartBeatMidVersionData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HeartBeatMidVersionData(int initialCapacity, float loadFactor,
			int concurrencyLevel) {
		super(initialCapacity, loadFactor, concurrencyLevel);
		// TODO Auto-generated constructor stub
	}

	public HeartBeatMidVersionData(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
		// TODO Auto-generated constructor stub
	}

	public HeartBeatMidVersionData(int initialCapacity) {
		super(initialCapacity);
		// TODO Auto-generated constructor stub
	}

	public HeartBeatMidVersionData(
			Map<? extends String, ? extends ConcurrentLinkedQueue<TableChangeTriEntity>> m) {
		super(m);
		// TODO Auto-generated constructor stub
	}
	
}
