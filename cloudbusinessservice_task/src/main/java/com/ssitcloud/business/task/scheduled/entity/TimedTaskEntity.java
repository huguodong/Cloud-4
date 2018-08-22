package com.ssitcloud.business.task.scheduled.entity;

import java.sql.Timestamp;
/**
 * 定时任务暂存实体
 * author huanghuang
 * 2017年3月2日 下午1:54:22
 */
public class TimedTaskEntity {

	private Integer task_idx;// int(11) NOT NULL 任务IDX
	private Integer lib_idx;// int(11) NULL 图书馆idx
	private Integer task_type;// int(3) NULL 任务类型 1年任务 2月任务 3周任务 4日任务
	private Timestamp task_starttime;// timestamp NOT NULL 任务启动时间
	private Integer oper_idx;// int(11) NULL 操作员IDX
	private String task_name;// varchar(50) NULL 任务名
	private String task_desc;// varchar(200) NULL 任务说明
	private String task_state;// varchar(2) NULL 状态 1启用 0停用
	private Timestamp creattime;// timestamp NOT NULL 创建时间
	private Timestamp updatetime;// timestamp NOT NULL 修改时间

	public Integer getTask_idx() {
		return task_idx;
	}

	public void setTask_idx(Integer task_idx) {
		this.task_idx = task_idx;
	}

	public Integer getLib_idx() {
		return lib_idx;
	}

	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}

	public Integer getTask_type() {
		return task_type;
	}

	public void setTask_type(Integer task_type) {
		this.task_type = task_type;
	}

	public Timestamp getTask_starttime() {
		return task_starttime;
	}

	public void setTask_starttime(Timestamp task_starttime) {
		this.task_starttime = task_starttime;
	}

	public Integer getOper_idx() {
		return oper_idx;
	}

	public void setOper_idx(Integer oper_idx) {
		this.oper_idx = oper_idx;
	}

	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

	public String getTask_desc() {
		return task_desc;
	}

	public void setTask_desc(String task_desc) {
		this.task_desc = task_desc;
	}

	public String getTask_state() {
		return task_state;
	}

	public void setTask_state(String task_state) {
		this.task_state = task_state;
	}

	public Timestamp getCreattime() {
		return creattime;
	}

	public void setCreattime(Timestamp creattime) {
		this.creattime = creattime;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}


}
