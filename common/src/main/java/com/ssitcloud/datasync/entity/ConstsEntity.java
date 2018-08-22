package com.ssitcloud.datasync.entity;

/**
 * 容器名称
 * 
 * @author lbh
 *
 */
public class ConstsEntity {
	
	//名字唯一 设备端 上传日志MAP容器
	public static final String UPLOAD_RUNLOG_CONTAINER="uploadRunLogContainer";
	//名字唯一 设备端上传命令结果MAP容器
	public static final String CONTROL_RESULT_CONTAINER="controlResultContainer";
	//名字唯一 网页端发送命令MAP容器
	public static final String QUERY_RUNNING_LOG_ORDER="queryRunningLogOrder";
	//发送日志的control_info标记 为3
	public static final String QUERY_RUNNING_LOG_FLAG="3";
	
}
