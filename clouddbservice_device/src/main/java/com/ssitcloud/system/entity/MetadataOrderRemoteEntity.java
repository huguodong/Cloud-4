package com.ssitcloud.system.entity;
/**
 * 监控指令元数据表
 * @comment
 *`order_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT '监控指令id',
 *`order_desc` varchar(100) NOT NULL COMMENT '监控指令描述',
 *`order_cmd` varchar(100) NOT NULL COMMENT '监控指令命令',
 *`order_os` int(11) NOT NULL COMMENT '监控指令操作系统 1windows 2linux 3unix',
 * 设备端通过接口 传输数据 使用
 * 
 * @author lbh
 * @data 2016年4月9日
 */
public class MetadataOrderRemoteEntity {

	private Integer order_id;
	private String  order_desc;
	private String  order_cmd;
	
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
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	
	
	
	
}
