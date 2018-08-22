package com.ssitcloud.entity;

import java.util.Date;
import java.util.List;

import com.ssitcloud.common.entity.DatagridPageEntity;

public class BakupDataEntity extends DatagridPageEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer idx;//主键
	
	private String file_name;//文件名
	
	private String file_path;//路径
	
	private Integer bakup_flg;//备份是否成功
	
	private Integer is_exist;//备份文件是否存在
	
	private double file_size;//文件大小
	
	private Date restore_time;//最新还原时间
	
	private Date create_time;//创建时间
	
	private String md5;//md5
	
	private Integer library_idx;
	
	private String create_time_start;
	
	private String create_time_end;
	
	private List<Integer> library_idx_arr;
	
	public Integer getIdx() {
		return idx;
	}
	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public Integer getBakup_flg() {
		return bakup_flg;
	}
	public void setBakup_flg(Integer bakup_flg) {
		this.bakup_flg = bakup_flg;
	}
	public Integer getIs_exist() {
		return is_exist;
	}
	public void setIs_exist(Integer is_exist) {
		this.is_exist = is_exist;
	}
	public double getFile_size() {
		return file_size;
	}
	public void setFile_size(double file_size) {
		this.file_size = file_size;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public Integer getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}
	public String getCreate_time_start() {
		return create_time_start;
	}
	public void setCreate_time_start(String create_time_start) {
		this.create_time_start = create_time_start;
	}
	public String getCreate_time_end() {
		return create_time_end;
	}
	public void setCreate_time_end(String create_time_end) {
		this.create_time_end = create_time_end;
	}
	public List<Integer> getLibrary_idx_arr() {
		return library_idx_arr;
	}
	public void setLibrary_idx_arr(List<Integer> library_idx_arr) {
		this.library_idx_arr = library_idx_arr;
	}
	public Date getRestore_time() {
		return restore_time;
	}
	public void setRestore_time(Date restore_time) {
		this.restore_time = restore_time;
	}
	
	
	
}
