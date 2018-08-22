package com.ssitcloud.common.entity;

/**
 * 常量
 * 
 * @package: com.ssitcloud.common.entity
 * @classFile: Const
 * @author: liuBh
 * @description: TODO
 */
public class Const {
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILED = "FAILED";
	
	/********************************************************
	 * 以下为 系统日志 记录 操作代码，如果数据库log_message 中的 log_code有改动，则下面的代码也必须根则改动
	 * 
	 */
	/**
	 * 设备类型操作
	 */
	public static final String OPERCMD_ADD_DEVICE_TYPE="5501";
	public static final String OPERCMD_UPDATE_DEVICE_TYPE="5502";
	public static final String OPERCMD_DELETE_DEVICE_TYPE="5503";
	/***
	 * 监控配置模板操作
	 */
	public static final String OPERCMD_ADD_DEVICE_MONITOR_TEMP="5321";
	public static final String OPERCMD_UPDATE_DEVICE_MONITOR_TEMP="5322";
	public static final String OPERCMD_DELETE_DEVICE_MONITOR_TEMP="5323";
	/**
	 * 权限分组操作4901
	 */
	public static final String OPERCMD_ADD_SERV_GROUP="3401";
	public static final String OPERCMD_UPDATE_SERV_GROUP="3402";
	public static final String OPERCMD_DELETE_SERV_GROUP="3403";
	
	/**
	 * 设备分组操作5201
	 */
	public static final String OPERCMD_ADD_DEV_GROUP="5201";
	public static final String OPERCMD_UPDATE_DEV_GROUP="5202";
	public static final String OPERCMD_DELETE_DEV_GROUP="5203";
	/**
	 * 设备基本信息 操作 5101
	 */
	public static final String OPERCMD_ADD_DEVICE="5101";
	public static final String OPERCMD_UPDATE_DEVICE="5102";
	public static final String OPERCMD_DELETE_DEVICE="5103";
	
	/**
	 * 登出系统
	 */
	public static final String OPERCMD_LOG_OUT="5002";
	/**
	 * 数据库备份
	 */
	public static final String OPERCMD_BAKUP_DATABASE="6200";
	/**
	 * 数据库还原
	 */
	public static final String OPERCMD_RESOTRE_DATABASE="6300";
	
	/**
	 * 新增操作员分组3201
	 */
	public static final String OPERCMD_ADD_OPERGROUP="3201";
	public static final String OPERCMD_UPDATE_OPERGROUP="3202";
	public static final String OPERCMD_DELETE_OPERGROUP="3203";
	/**
	 * 图书馆服务模板 4401
	 */
	public static final String OPERCMD_ADD_LIBTEMP="4401";
	public static final String OPERCMD_UPDATE_LIBTEMP="4402";
	public static final String OPERCMD_DELETE_LIBTEMP="4403";
	/**
	 * ACS模板
	 */
	public static final String OPERCMD_ADD_SELFCONF="4301";
	public static final String OPERCMD_UPDATE_SELFCONF="4302";
	public static final String OPERCMD_DELETE_SELFCONF="4303";
	/**
	 * 硬件配置 逻辑名 模板5311
	 */
	public static final String OPERCMD_ADD_EXTCONF="5311";
	public static final String OPERCMD_UPDATE_EXTCONF="5312";
	public static final String OPERCMD_DELETE_EXTCONF="5313";
	/**
	 * 运行配置5331
	 */
	public static final String OPERCMD_ADD_RUNCONF="5331";
	public static final String OPERCMD_UPDATE_RUNCONF="5332";
	public static final String OPERCMD_DELETE_RUNCONF="5333";
	/**
	 * 密码模板 4701
	 */
	public static final String OPERCMD_ADD_SOXTEMP="4701";
	public static final String OPERCMD_UPDATE_SOXTEMP="4702";
	public static final String OPERCMD_DELETE_SOXTEMP="4703";
	
	/*******************系统日志结束***********************************/
	
	
	/*************************************************************
	 * 权限 代码
	 ************************************************************/
	
	
	/**
	 * 新增图书馆基本信息
	 */
	public static final String PERMESSION_ADD_LIB_BASEINFO="0102020101";
	/**
	 * 修改图书馆基本信息
	 */
	public static final String PERMESSION_UPD_LIB_BASEINFO="0102020102";
	/**
	 * 删除图书馆基本信息
	 */
	public static final String PERMESSION_DEL_LIB_BASEINFO="0102020103";
	
	/**
	 * 还原数据库 权限
	 */
	public static final String PERMESSION_RESTORE_DATABASE="0102040300";

	
}
