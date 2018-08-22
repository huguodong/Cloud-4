package com.ssitcloud.mobile.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * APP中意见反馈实体
 * author huanghuang
 * 2017年2月10日 上午11:01:55
 */
@JsonRootName(value="feedBack")
public class FeedbackEntity {
	private Integer feedback_idx;//int(11) NOT NULL 反馈ID
	private Integer reader_idx;//int(11) NULL 读者证idx
	private String feedback_type;//varchar(2) NULL 反馈问题类型
	private String user_type;//varchar(2) NULL 用户类型代码 1馆员 2读者
	private String feedback_value;//varchar(500) NULL 反馈问题
	private Timestamp createtime;//timestamp NOT NULL 反馈时间
	
	public Integer getFeedback_idx() {
		return feedback_idx;
	}
	public void setFeedback_idx(Integer feedback_idx) {
		this.feedback_idx = feedback_idx;
	}
	public Integer getReader_idx() {
		return reader_idx;
	}
	public void setReader_idx(Integer reader_idx) {
		this.reader_idx = reader_idx;
	}
	public String getFeedback_type() {
		return feedback_type;
	}
	public void setFeedback_type(String feedback_type) {
		this.feedback_type = feedback_type;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getFeedback_value() {
		return feedback_value;
	}
	public void setFeedback_value(String feedback_value) {
		this.feedback_value = feedback_value;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
	
	
}
