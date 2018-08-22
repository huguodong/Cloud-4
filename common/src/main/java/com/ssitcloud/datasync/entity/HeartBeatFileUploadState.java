package com.ssitcloud.datasync.entity;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.ssitcloud.devmgmt.entity.FileUploadState;

public class HeartBeatFileUploadState extends ConcurrentHashMap<String, ConcurrentLinkedQueue<FileUploadState>>{

	private static final long serialVersionUID = 1L;

	public HeartBeatFileUploadState() {
		
	}
	public HeartBeatFileUploadState(int initialCapacity, float loadFactor, int concurrencyLevel) {
		super(initialCapacity, loadFactor, concurrencyLevel);
	}
	public HeartBeatFileUploadState(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}
	public HeartBeatFileUploadState(int initialCapacity) {
		super(initialCapacity);
	}
	public HeartBeatFileUploadState(Map<? extends String, ? extends ConcurrentLinkedQueue<FileUploadState>> m) {
		super(m);
	}
	
}
