package com.ssitcloud.devmgmt.entity;

import java.sql.Timestamp;
import java.util.Map;

/**
 * 
 * @comment 人流量统计vo对应消息队列中的信息
 * 
 * @author yeyalin
 * @data 2017年10月10日
 */
public class RllCountMsgVO {

	/* 图书馆id */
	private String lib_id;
	/* 图书馆id */
	private Integer lib_idx;
	/* 设备类型id */
	private String device_id;
	/**
	 * 进馆人数
	 */
	private Integer in_count;
	/**
	 * 出馆人数
	 */
	private Integer out_count;

	/**
	 * 总进馆人数
	 */
	private Integer in_TotalCount;
	/**
	 * 总出馆人数
	 */
	private Integer out_TotalCount;

	/**
	 * 统计时间
	 */
	private Long countTime;
	/**
	 * 采集设备统计信息统计时间
	 */
	private Map<String, Object> series;

	/**
	 * 统计时间
	 */
	private Long updateTime;

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

	public Integer getIn_TotalCount() {
		return in_TotalCount;
	}

	public void setIn_TotalCount(Integer in_TotalCount) {
		this.in_TotalCount = in_TotalCount;
		this.in_count = in_TotalCount;
	}

	public Integer getOut_TotalCount() {
		return out_TotalCount;
	}

	public void setOut_TotalCount(Integer out_TotalCount) {
		this.out_TotalCount = out_TotalCount;
		this.out_count = out_TotalCount;
	}

	public Long getCountTime() {
		return countTime;
	}

	public void setCountTime(Long countTime) {
		this.countTime = countTime;
		this.updateTime=countTime;
	}

	public Map<String, Object> getSeries() {
		return series;
	}

	public void setSeries(Map<String, Object> series) {
		this.series = series;
	}


	public Integer getLib_idx() {
		return lib_idx;
	}

	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "RllCountMsgVO [lib_id=" + lib_id + ", lib_idx=" + lib_idx
				+ ", device_id=" + device_id + ", in_count=" + in_count
				+ ", out_count=" + out_count + ", in_TotalCount="
				+ in_TotalCount + ", out_TotalCount=" + out_TotalCount
				+ ", countTime=" + countTime + ", series=" + series
				+ ", updateTime=" + updateTime + "]";
	}

	
}
