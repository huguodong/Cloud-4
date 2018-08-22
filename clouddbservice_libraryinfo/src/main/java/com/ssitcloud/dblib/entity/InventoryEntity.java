package com.ssitcloud.dblib.entity;

/**
 * 图书盘点日志实体
 * author huanghuang
 * 2017年2月13日 上午11:30:45
 */
public class InventoryEntity {
	private String lib_idx;//varchar(32) NOT NULL 图书ID
	private String book_barcode;//varchar(32) NOT NULL 图书条码
	private Integer operation;//int(11) NOT NULL 操作类型
	private Integer operationresult;//int(11) NOT NULL 操作结果
	private String operatetime;//varchar(20) NOT NULL 操作变更时间
	private String shelflayer_barcode;//varchar(32) NULL 层架标号
	private String shelflayer_barcode_old;//varchar(32) NULL 旧的层架标号
	private String remake;//varchar(1000) NULL json格式备注
	public String getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(String lib_idx) {
		this.lib_idx = lib_idx;
	}
	public String getBook_barcode() {
		return book_barcode;
	}
	public void setBook_barcode(String book_barcode) {
		this.book_barcode = book_barcode;
	}
	public Integer getOperation() {
		return operation;
	}
	public void setOperation(Integer operation) {
		this.operation = operation;
	}
	public Integer getOperationresult() {
		return operationresult;
	}
	public void setOperationresult(Integer operationresult) {
		this.operationresult = operationresult;
	}
	public String getOperatetime() {
		return operatetime;
	}
	public void setOperatetime(String operatetime) {
		this.operatetime = operatetime;
	}
	public String getShelflayer_barcode() {
		return shelflayer_barcode;
	}
	public void setShelflayer_barcode(String shelflayer_barcode) {
		this.shelflayer_barcode = shelflayer_barcode;
	}
	public String getShelflayer_barcode_old() {
		return shelflayer_barcode_old;
	}
	public void setShelflayer_barcode_old(String shelflayer_barcode_old) {
		this.shelflayer_barcode_old = shelflayer_barcode_old;
	}
	public String getRemake() {
		return remake;
	}
	public void setRemake(String remake) {
		this.remake = remake;
	}
	
	
}
