package com.ssitcloud.datasync.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 存放的是<IDX---ID>  集合
 * @author lbh
 *
 */
public class DeviceIdx2IdContainer extends ConcurrentHashMap<Integer, String>{

	private static final long serialVersionUID = 1L;

	public DeviceIdx2IdContainer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeviceIdx2IdContainer(int initialCapacity, float loadFactor,
			int concurrencyLevel) {
		super(initialCapacity, loadFactor, concurrencyLevel);
		// TODO Auto-generated constructor stub
	}

	public DeviceIdx2IdContainer(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
		// TODO Auto-generated constructor stub
	}

	public DeviceIdx2IdContainer(int initialCapacity) {
		super(initialCapacity);
		// TODO Auto-generated constructor stub
	}

	public DeviceIdx2IdContainer(Map<? extends Integer, ? extends String> m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

}
