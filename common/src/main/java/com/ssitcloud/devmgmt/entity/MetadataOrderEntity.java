package com.ssitcloud.devmgmt.entity;
/**
 * 监控指令元数据表
 * @comment
 *`order_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT '监控指令id',
 *`order_desc` varchar(100) NOT NULL COMMENT '监控指令描述',
 *`order_cmd` varchar(100) NOT NULL COMMENT '监控指令命令',
 *`order_os` int(11) NOT NULL COMMENT '监控指令操作系统 1windows 2linux 3unix',
 * 
 * @author hwl
 * @data 2016年4月9日
 */
public class MetadataOrderEntity {

	private int order_idx;
	private String  order_desc;
	private String  order_cmd;
	private int order_os;
	public int getOrder_idx() {
		return order_idx;
	}
	public void setOrder_idx(int order_idx) {
		this.order_idx = order_idx;
	}
	public String getOrder_desc() {
		return order_desc;
	}
	public void setOrder_desc(String order_desc) {
		this.order_desc = order_desc;
	}
	public String getOrder_cmd() {
		return order_cmd;
	}
	public void setOrder_cmd(String order_cmd) {
		this.order_cmd = order_cmd;
	}
	public int getOrder_os() {
		return order_os;
	}
	public void setOrder_os(int order_os) {
		this.order_os = order_os;
	}
	
	
	
	
}
