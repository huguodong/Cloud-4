package com.ssitcloud.devmgmt.entity;

import java.sql.Timestamp;

/**
 * 
 * @comment 人流量统计vo
 * 
 * @author yeyalin
 * @data 2017年10月10日
 */
public class RllCountVO {

	/* 实时数据id */
	private Integer everyday_idx;
	/**
	 * 每周统计数据id
	 */
	private Integer everyweek_idx;
	/**
	 * 每月统计数据id
	 */
	private Integer everymonth_idx;
	/**
	 * 每年统计数据id
	 */
	private Integer everyyear_idx;
	/* 图书馆id */
	private String lib_id;
	/* 图书馆id */
	private Integer lib_idx;
	/* 设备类型id */
	private Integer device_idx;
	/* 设备类型id */
	private String  device_id;
	
	/* 设备类型device_type */
	private String  device_type;
	/**
	 * 进馆人数
	 */
	private Integer in_count;
	/**
	 * 出馆人数
	 */
	private Integer out_count;
	/**
	 * 统计时间
	 */
	private Timestamp update_time;
	/**
	 * 开始时间
	 */
	private Timestamp start_time;
	/**
	 * 结束时间
	 */
	private Timestamp end_time;

	/**
	 * 统计时间
	 */
	private Long updateTime;
	/**
	 * 开始时间
	 */
	private Long startTime;
	/**
	 * 结束时间
	 */
	private Long endTime;
	/**
	 * 最后更新时间
	 */
	private String lastUpdateTime;

	public Integer getEveryday_idx() {
		return everyday_idx;
	}

	public void setEveryday_idx(Integer everyday_idx) {
		this.everyday_idx = everyday_idx;
	}

	public Integer getEveryweek_idx() {
		return everyweek_idx;
	}

	public void setEveryweek_idx(Integer everyweek_idx) {
		this.everyweek_idx = everyweek_idx;
	}

	public Integer getEverymonth_idx() {
		return everymonth_idx;
	}

	public void setEverymonth_idx(Integer everymonth_idx) {
		this.everymonth_idx = everymonth_idx;
	}

	public Integer getEveryyear_idx() {
		return everyyear_idx;
	}

	public void setEveryyear_idx(Integer everyyear_idx) {
		this.everyyear_idx = everyyear_idx;
	}

	public Integer getLib_idx() {
		return lib_idx;
	}

	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}

	public Integer getDevice_idx() {
		return device_idx;
	}

	public void setDevice_idx(Integer device_idx) {
		this.device_idx = device_idx;
	}

	public Integer getIn_count() {
		return in_count;
	}

	public void setIn_count(Integer in_count) {
		this.in_count = in_count;
	}

	public Integer getOut_count() {
		return out_count;
	}

	public void setOut_count(Integer out_count) {
		this.out_count = out_count;
	}

	public Timestamp getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}

	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}

	public Timestamp getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		if (updateTime != null) {
			this.updateTime = updateTime;
			this.update_time = new Timestamp(updateTime);
		}
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		if (startTime != null) {
			this.startTime = startTime;
			this.start_time = new Timestamp(startTime);
		}
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		if (endTime != null) {
			this.endTime = endTime;
			this.end_time = new Timestamp(endTime);
		}
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLib_id() {
		return lib_id;
	}

	public void setLib_id(String lib_id) {
		this.lib_id = lib_id;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	
	
}
