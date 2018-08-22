package com.ssitcloud.request.entity;

import java.sql.Timestamp;

import org.springframework.util.StringUtils;


/**
 * 接口请求传输数据表
 * @author soft4
 *
 */
@SuppressWarnings("rawtypes")
public class InterfaceRequestDto extends InterfaceRequestEntity{
	/**
	 * 开始时间
	 */
	private Timestamp startTime;
	
	/**
	 * 结束时间;
	 */
	private Timestamp endTime;
	
	/**
	 * 查询使用：开始时间
	 */
	private String startDate;
	
	/**
	 * 查询使用： 结束时间;
	 */
	private String endDate;

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
		
		if(!StringUtils.isEmpty(startDate)){
			
			this.startTime = Timestamp.valueOf(startDate);
		}
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
		
		if(!StringUtils.isEmpty(endDate)){
			
			this.endTime = Timestamp.valueOf(endDate);
		}
	}
	
	
	
}
