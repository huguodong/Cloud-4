package com.ssitcloud.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * APP意见反馈回复实体
 * author huanghuang
 * 2017年2月10日 上午11:06:53
 */
@JsonRootName(value="feedback_reply")
public class FeedbackReplyEntity {
	private Integer reply_idx;//int(11) NOT NULL 回复ID
	private Integer feedback_idx;//int(11) NULL 反馈ID
	private Integer operator_idx;//int(11) NULL 回复人idx（系统管理员）
	private String reply_value;//varchar(500) NULL 回复消息
	private Timestamp createtime;//timestamp NOT NULL 回复时间
	
	public Integer getReply_idx() {
		return reply_idx;
	}
	public void setReply_idx(Integer reply_idx) {
		this.reply_idx = reply_idx;
	}
	public Integer getFeedback_idx() {
		return feedback_idx;
	}
	public void setFeedback_idx(Integer feedback_idx) {
		this.feedback_idx = feedback_idx;
	}
	public Integer getOperator_idx() {
		return operator_idx;
	}
	public void setOperator_idx(Integer operator_idx) {
		this.operator_idx = operator_idx;
	}
	public String getReply_value() {
		return reply_value;
	}
	public void setReply_value(String reply_value) {
		this.reply_value = reply_value;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	
	
	
}
