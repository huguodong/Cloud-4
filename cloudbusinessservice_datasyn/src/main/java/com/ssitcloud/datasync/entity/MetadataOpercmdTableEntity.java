package com.ssitcloud.datasync.entity;

/**
 * 上传日志 命令码对照mongodb库表
 *
 * <p>2017年8月28日 下午3:00:42  
 * @author hjc 
 *
 */
public class MetadataOpercmdTableEntity {
	
	private Integer table_idx;//int(11) NOT NULL主键ＩＤ
	private String opercmd_id;//varchar(10) NOT NULL设备端命令码
	private String mongodb_table;//varchar(30) NOT NULLmongoDB表名
	private String electronic_type;//varchar(10) NOT NULL操作类型 1借书 2还书 3续借 4交押金 5交预付款 6交欠款 7从预付款扣除 8其它
	
	
	public Integer getTable_idx() {
		return table_idx;
	}
	public void setTable_idx(Integer table_idx) {
		this.table_idx = table_idx;
	}
	public String getOpercmd_id() {
		return opercmd_id;
	}
	public void setOpercmd_id(String opercmd_id) {
		this.opercmd_id = opercmd_id;
	}
	public String getMongodb_table() {
		return mongodb_table;
	}
	public void setMongodb_table(String mongodb_table) {
		this.mongodb_table = mongodb_table;
	}
	public String getElectronic_type() {
		return electronic_type;
	}
	public void setElectronic_type(String electronic_type) {
		this.electronic_type = electronic_type;
	}
	
	

}
