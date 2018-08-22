package com.ssitcloud.order.entity;

/**
 * 
 * @comment 表名borrowprivileges
 * 
 * @author min
 * @data 2017年3月1日
 */
public class OrderEntity{
	private int idx;
	//图书条码号
	private String book_barcode;
	//读者证号
	private String readerid;
	//预借状态
	private int state;
	//图书包裹号
	private String packetid;
	//预借逾期时间
	private String overduedate;
	//更新时间
	private String updatetime;

	public OrderEntity() {
	}
	

	public int getIdx() {
		return idx;
	}


	public void setIdx(int idx) {
		this.idx = idx;
	}


	public String getBook_barcode() {
		return book_barcode;
	}

	public void setBook_barcode(String book_barcode) {
		this.book_barcode = book_barcode;
	}

	public String getReaderid() {
		return readerid;
	}

	public void setReaderid(String readerid) {
		this.readerid = readerid;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getPacketid() {
		return packetid;
	}

	public void setPacketid(String packetid) {
		this.packetid = packetid;
	}

	public String getOverduedate() {
		return overduedate;
	}

	public void setOverduedate(String overduedate) {
		this.overduedate = overduedate;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}


	@Override
	public String toString() {
		return "OrderEntity [idx=" + idx + ", book_barcode=" + book_barcode
				+ ", readerid=" + readerid + ", state=" + state + ", packetid="
				+ packetid + ", overduedate=" + overduedate + ", updatetime="
				+ updatetime + "]";
	}


}
