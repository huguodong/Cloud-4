package com.ssitcloud.devicelog.entity;

import java.io.Serializable;

public class BinStateDetail implements Serializable {

	private static final long serialVersionUID = -5419151923952863995L;
	// 箱类型：还书箱、卡箱、钱箱
	private String binType;
	// 箱号
	private String binNo;
	// 箱的子类型 还书箱类型、钱箱类型
	private String binSubtype;
	// 箱内物品书目
	private String count;

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getBinSubtype() {
		return binSubtype;
	}

	public void setBinSubtype(String binSubtype) {
		this.binSubtype = binSubtype;
	}

	public String getBinNo() {
		return binNo;
	}

	public void setBinNo(String binNo) {
		this.binNo = binNo;
	}

	public String getBinType() {
		return binType;
	}

	public void setBinType(String binType) {
		this.binType = binType;
	}

	/**
	 * 重写toString (binType+binNo+binSubtype)
	 */
	@Override
	public String toString() {
		return binType + binNo + binSubtype;
	}

}
