package com.ssitcloud.mobile.entity;


public class DeviceRegionEntity extends RegionEntity {
	private int count;

	public DeviceRegionEntity() {
	}
	public DeviceRegionEntity(RegionEntity region) {
		this(region,0);
	}
	public DeviceRegionEntity(RegionEntity region,int count) {
		setRegi_idx(region.getRegi_idx());
		setRegi_code(region.getRegi_code());
		setRegi_nation(region.getRegi_nation());
		setRegi_province(region.getRegi_province());
		setRegi_city(region.getRegi_city());
		setRegi_area(region.getRegi_area());
		this.count = count;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
