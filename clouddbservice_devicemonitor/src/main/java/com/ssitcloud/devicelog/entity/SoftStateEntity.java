package com.ssitcloud.devicelog.entity;
/**
 * 软状态
 * 命令：数字字符串
 * 额外信息：小写字母
 * @package: com.ssitcloud.devicelog.entity
 * @classFile: SoftStateEntity
 * @author: liuBh
 * @description: TODO
 */
public class SoftStateEntity {
	private String opercmd;
	private String state;
	public String getOpercmd() {
		return opercmd;
	}
	public void setOpercmd(String opercmd) {
		this.opercmd = opercmd;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
