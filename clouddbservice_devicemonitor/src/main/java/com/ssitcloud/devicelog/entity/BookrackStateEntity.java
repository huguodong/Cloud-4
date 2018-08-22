package com.ssitcloud.devicelog.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

//架
@Document(collection = "bookrack_state")
public class BookrackStateEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4970739749827863954L;
	@Id
	private String id;
	// 状态上报时间
	@Field(value = "updatetime", order = 1)
	private String updatetime;
	// 具体细节
	private List<BookrackStateDetail> detail;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public List<BookrackStateDetail> getDetail() {
		return detail;
	}

	public void setDetail(List<BookrackStateDetail> detail) {
		this.detail = detail;
	}
}
