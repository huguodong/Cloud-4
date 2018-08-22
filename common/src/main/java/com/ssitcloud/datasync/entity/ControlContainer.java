package com.ssitcloud.datasync.entity;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.ssitcloud.devmgmt.entity.DeviceOrder;

public class ControlContainer extends ConcurrentLinkedQueue<DeviceOrder>{
	private static final long serialVersionUID = 1L;

	public ControlContainer() {
		super();
	}

	public ControlContainer(Collection<? extends DeviceOrder> c) {
		super(c);
	}
	
}
