package com.ssitcloud.entity;
/**
 * 
 * @package com.ssitcloud.entity
 * @comment
 * @data 2016年4月23日
 * @author hwl
 */
public class ReaderTypeEntity{
	
	/** 表 reader_type 读者流通类型表
	 *  `type_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT '流通类型IDX',
	 * `library_idx` int(11) NOT NULL COMMENT '馆IDX',
	 *`type_distinction` varchar(2) NOT NULL COMMENT '类型区分 1读者流通类型  2设备维护卡',
	 *`type_id` varchar(50) NOT NULL COMMENT '类型代码',
	 *`type_name` varchar(50) NOT NULL COMMENT '类型名',
	 *`type_deposit` int(11) DEFAULT '0' COMMENT '押金',
	 *`card_fee` int(11) DEFAULT '0' COMMENT '工本费',
	 *`verification_fee` int(11) DEFAULT '0' COMMENT '验证费',
	 */
	private Integer type_idx;
	private Integer library_idx;
	private String type_distinction ;
	private String type_id;
	private String type_name;
	private Integer type_deposit;
	private Integer card_fee;
	private Integer verification_fee;
	private String lib_id;
	private Integer version_stamp;
	
	public String getLib_id() {
		return lib_id;
	}
	public void setLib_id(String lib_id) {
		this.lib_id = lib_id;
	}
	public Integer getType_idx() {
		return type_idx;
	}
	public void setType_idx(Integer type_idx) {
		this.type_idx = type_idx;
	}
	public Integer getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}
	public String getType_distinction() {
		return type_distinction;
	}
	public void setType_distinction(String type_distinction) {
		this.type_distinction = type_distinction;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public Integer getType_deposit() {
		return type_deposit;
	}
	public void setType_deposit(Integer type_deposit) {
		this.type_deposit = type_deposit;
	}
	public Integer getCard_fee() {
		return card_fee;
	}
	public void setCard_fee(Integer card_fee) {
		this.card_fee = card_fee;
	}
	public Integer getVerification_fee() {
		return verification_fee;
	}
	public void setVerification_fee(Integer verification_fee) {
		this.verification_fee = verification_fee;
	}
	public Integer getVersion_stamp() {
		return version_stamp;
	}
	public void setVersion_stamp(Integer version_stamp) {
		this.version_stamp = version_stamp;
	}
	

}
