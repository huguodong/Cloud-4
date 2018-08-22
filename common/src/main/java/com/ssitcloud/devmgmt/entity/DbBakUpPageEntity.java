package com.ssitcloud.devmgmt.entity;

import com.ssitcloud.common.entity.DatagridPageEntity;

public class DbBakUpPageEntity extends DatagridPageEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String startDate;
	
	private String endDate;
	
	/**文件ＩＤ号**/
	private String dataNo;
	/** 文件名*/
	private String fileName;
	/** 文件大小*/
	private String fileSize;
	/** 文件生成日期*/
	private String createTime;
	/** 文件下载路径*/
	private String filePath;
	/** 删除路径*/
	private String deletePath;
	/** 还原路径*/
	private String restorePath;
	/**操作标识**/
	private String flexOpt;
	/**最后修改时间**/
	private String maxModifyTime;
	
	
	public String getDataNo() {
		return dataNo;
	}
	public void setDataNo(String dataNo) {
		this.dataNo = dataNo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getDeletePath() {
		return deletePath;
	}
	public void setDeletePath(String deletePath) {
		this.deletePath = deletePath;
	}
	public String getRestorePath() {
		return restorePath;
	}
	public void setRestorePath(String restorePath) {
		this.restorePath = restorePath;
	}
	public String getFlexOpt() {
		return flexOpt;
	}
	public void setFlexOpt(String flexOpt) {
		this.flexOpt = flexOpt;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getMaxModifyTime() {
		return maxModifyTime;
	}
	public void setMaxModifyTime(String maxModifyTime) {
		this.maxModifyTime = maxModifyTime;
	}
	
}
