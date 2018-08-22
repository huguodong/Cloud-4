package com.ssitcloud.common.entity;

/**
 * 常量
 * 2017/3/4  修改新的命令码 lqw
 * @package: com.ssitcloud.common.entity
 * @classFile: Const
 * @author: liuBh
 * @description: TODO
 */
public class Const {
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILED = "FAILED";
	
	public static final String CHECK_FALSE="CHECK_FALSE";
	
	
	
	public static final Integer DEVICE_CONFIG_IS_MODEL=1;
	public static final Integer DEVICE_CONFIG_IS_DATA=0;
	
	//反过来的....
	public static final String DEVICE_XX_CONFIG_IS_MODEL="0";
	public static final String DEVICE_XX_CONFIG_IS_DATA="1";
	
	/********************************************************
	 * 以下为 系统日志 记录 操作代码，如果数据库log_message 中的 log_code有改动，则下面的代码也必须根则改动
	 * 
	 */
	/**
	 * 设备类型操作5501
	 * 修改新的命令码 0102020201  2017/3/4 lqw
	 */
	public static final String OPERCMD_ADD_DEVICE_TYPE="0102020201";
	public static final String OPERCMD_UPDATE_DEVICE_TYPE="0102020203";
	public static final String OPERCMD_DELETE_DEVICE_TYPE="0102020202";
	/***
	 * 监控配置模板操作5321
	 * 修改新的命令码 0102020501  2017/3/4 lqw
	 */
	public static final String OPERCMD_ADD_DEVICE_MONITOR_TEMP="0102020501";
	public static final String OPERCMD_UPDATE_DEVICE_MONITOR_TEMP="0102020503";
	public static final String OPERCMD_DELETE_DEVICE_MONITOR_TEMP="0102020502";
	/**
	 * 权限分组操作4901
	 * 修改新的命令码 0101010401  2017/3/4 lqw
	 */
	public static final String OPERCMD_ADD_SERV_GROUP="0101010401";
	public static final String OPERCMD_UPDATE_SERV_GROUP="0101010403";
	public static final String OPERCMD_DELETE_SERV_GROUP="0101010402";
	
	/**
	 * 设备分组操作5201
	 * 修改新的命令码 0102020301  2017/3/4 lqw
	 */
	public static final String OPERCMD_ADD_DEV_GROUP="0102020301";
	public static final String OPERCMD_UPDATE_DEV_GROUP="0102020303";
	public static final String OPERCMD_DELETE_DEV_GROUP="0102020302";
	/**
	 * 设备基本信息 操作 5101
	 * 修改新的命令码 0102020101  2017/3/4 lqw
	 */
	public static final String OPERCMD_ADD_DEVICE="0102020101";
	public static final String OPERCMD_UPDATE_DEVICE="0102020103";
	public static final String OPERCMD_DELETE_DEVICE="0102020102";
	
	/**
	 * 登出系统
	 * 修改新的命令码 0101010206  2017/3/4 lqw
	 */
	public static final String OPERCMD_LOG_OUT="0101010206";
	/**
	 * 数据库备份
	 * 修改新的命令码 0109010101  2017/3/4 lqw
	 */
	public static final String OPERCMD_BAKUP_DATABASE="0109010101";
	/**
	 * 数据库还原
	 * 修改新的命令码 0109010102  2017/3/4 lqw
	 */
	public static final String OPERCMD_RESOTRE_DATABASE="0109010102";
	
	
	/**
	 * 新增操作员 3101 修改3102 删除 3103
	 * 修改新的命令码 0101010101  2017/3/4 lqw
	 */
	public static final String OPERCMD_ADD_OPERATOR="0101010101";
	public static final String OPERCMD_UPDATE_OPERATOR="0101010103";
	public static final String OPERCMD_DELETE_OPERATOR="0101010102";
	/**
	 * 新增操作员分组3201
	 * 修改新的命令码 0101010201  2017/3/4 lqw
	 */
	public static final String OPERCMD_ADD_OPERGROUP="0101010201";
	public static final String OPERCMD_UPDATE_OPERGROUP="0101010203";
	public static final String OPERCMD_DELETE_OPERGROUP="0101010202";
	
	/**
	 * 3301 修改密码
	 * 修改新的命令码 0101010501  2017/3/4 lqw
	 */
	public static final String OPERCMD_CHANGE_PWD="0101010501";
	
	/**
	 * 新增读者流通类型 4201
	 * 修改新的命令码 0102010401  2017/3/4 lqw
	 */
	public static final String OPERCMD_ADD_READERTYPE="0102010401";
	public static final String OPERCMD_UPDATE_READERTYPE="0102010403";
	public static final String OPERCMD_DELETE_READERTYPE="0102010402";
	/**
	 * 图书馆服务模板 4401
	 * 修改新的命令码 0102010201  2017/3/4 lqw
	 */
	public static final String OPERCMD_ADD_LIBTEMP="0102010201";
	public static final String OPERCMD_UPDATE_LIBTEMP="0102010203";
	public static final String OPERCMD_DELETE_LIBTEMP="0102010202";
	/**
	 * ACS模板4301
	 * 修改新的命令码 0102010301  2017/3/4 lqw
	 */
	public static final String OPERCMD_ADD_SELFCONF="0102010301";
	public static final String OPERCMD_UPDATE_SELFCONF="0102010303";
	public static final String OPERCMD_DELETE_SELFCONF="0102010302";
	/**
	 * 硬件配置 逻辑名 模板5311
	 * 修改新的命令码 0102020601  2017/3/4 lqw
	 */
	public static final String OPERCMD_ADD_EXTCONF="0102020601";
	public static final String OPERCMD_UPDATE_EXTCONF="0102020603";
	public static final String OPERCMD_DELETE_EXTCONF="0102020602";
	/**
	 * 运行配置5331
	 * 修改新的命令码 0102020701  2017/3/4 lqw
	 */
	public static final String OPERCMD_ADD_RUNCONF="0102020701";
	public static final String OPERCMD_UPDATE_RUNCONF="0102020703";
	public static final String OPERCMD_DELETE_RUNCONF="0102020702";
	/**
	 * 密码模板 4701
	 * 修改新的命令码 0101010301  2017/3/4 lqw
	 */
	public static final String OPERCMD_ADD_SOXTEMP="0101010301";
	public static final String OPERCMD_UPDATE_SOXTEMP="0101010303";
	public static final String OPERCMD_DELETE_SOXTEMP="0101010302";
	
	
	//重置密码
	public static final String OPERCMD_RESET_PWD = "0101010103";
	//修改密码   修改新的命令码 0101010501  2017/3/4 lqw
	public static final String OPERCMD_UPD_PWD="0101010501";
	//升级模板配置 7100 修改0110010101
	public static final String OPERCMD_ADD_PATCHINFO = "0110010101";
	public static final String OPERCMD_UPD_PATCHINFO = "0110010103";
	public static final String OPERCMD_DEL_PATCHINFO = "0110010102";
	//6201
	public static final String OPERCMD_UPLOAD_BAKUP="6201";
	public static final String OPERCMD_DEL_DATABASE_BAKUP="6202";
	public static final String OPERCMD_DOWNLOAD_BAKUP="6203";
	
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
	public static final String PERMESSION_DEL_LIB_BASEINFO="0102010102";
	
	/**
	 * 还原数据库 权限
	 */
	public static final String PERMESSION_RESTORE_DATABASE="0102040300";
	/**
	 * 删除数据库备份 文件
	 */
	public static final String PERMESSION_DEL_DATABASE_BAKUP = "0102040600";
	/**
	 * 上传备份文件
	 */
	public static final String PERMESSION_UPLOAD_BAKUP = "0102040500";
	
	/**
	 * 用户管理 新增 修改 删除用户 
	 * 0102010101
	 * 0102010102
	 * 0102010103
	 */
	public static final String PERMESSION_ADD_OPERATOR = "0102010101";
	public static final String PERMESSION_UPD_OPERATOR = "0102010103";
	public static final String PERMESSION_DEL_OPERATOR = "0102010102";


	
	/**
	 * 监控管理
	 * 馆IDX｜维护指令｜设备ID组
	 */
	public static final String MONITOR_MANAGER="2110";
	
	
	
	/***
	 * 权限代码
	 ***/
	
//	//监控管理主页面
//	public static final String OPERCMD_MONITOR_MAINPAGE="0103000000";
//	//远程控制
//	public static final String OPERCMD_MONITOR_REMOTE_CONTROL="0103030000";
//	//关机
//	public static final String OPERCMD_MONITOR_SHUTDOWN="0103030100";
//	//重启
//	public static final String OPERCMD_MONITOR_RESTART="0103030200";
//	//下载日志
//	public static final String OPERCMD_MONITOR_DOWN_LOG="0103030300";
//	//锁定
//	public static final String OPERCMD_MONITOR_DEV_LOCK="0103030400";
//	//取消远程操作
//	public static final String OPERCMD_MONITOR_CANCAL_OPER="0103030500";
	
	//监控管理主页面
	public static final String OPERCMD_MONITOR_MAINPAGE="0103010101";
	//远程控制
	public static final String OPERCMD_MONITOR_REMOTE_CONTROL="01030201";
	//关机
	public static final String OPERCMD_MONITOR_SHUTDOWN="0103020101";
	//重启
	public static final String OPERCMD_MONITOR_RESTART="0103020102";
	//下载日志
	public static final String OPERCMD_MONITOR_DOWN_LOG="0103020103";
	//锁定
	public static final String OPERCMD_MONITOR_DEV_LOCK="0103020104";
	//取消远程操作
	public static final String OPERCMD_MONITOR_CANCAL_OPER="0103020105";
	
	
	/**
	 * 0107030101	新增个人常用菜单
     * 0107030102	删除个人常用菜单
	 * 0107030103	修改个人常用菜单
	 */
	public static final String PERMESSION_ADD_PERSETTING = "0107030101";
	public static final String PERMESSION_UPD_PERSETTING = "0107030103";
	public static final String PERMESSION_DEL_PERSETTING = "0107030102";
	/**
	 *  0107010201	新增查询统计模板组
		0107010202	删除查询统计模板组
		0107010203	修改查询统计模板组
	 */
	public static final String PERMESSION_ADD_STATGROUP = "0107010201";
	public static final String PERMESSION_UPD_STATGROUP = "0107010203";
	public static final String PERMESSION_DEL_STATGROUP = "0107010202";
	/**
	 *  0107010101	新增查询统计模板
		0107010102	删除查询统计模板
		0107010103	修改查询统计模板
	 */
	public static final String PERMESSION_ADD_STATTEM = "0107010101";
	public static final String PERMESSION_UPD_STATTEM = "0107010103";
	public static final String PERMESSION_DEL_STATTEM = "0107010102";
	/**
	 * 新增邮件参数 0111010101
	 * 修改邮件参数 0111010102
	 * 删除邮件参数 0111010103
	 */
	public static final String PERMESSION_ADD_EMAIL = "0111010101";	
	public static final String PERMESSION_UPD_EMAIL = "0111010103";	
	public static final String PERMESSION_DEL_EMAIL = "0111010102";
	/**
	 * 新增馆员APP页面功能
	 * 删除馆员APP页面功能
	 * 修改馆员APP页面功能
	 */
	public static final String PERMESSION_ADD_ADAPP = "0112010101";	
	public static final String PERMESSION_UPD_ADAPP = "0112010103";	
	public static final String PERMESSION_DEL_ADAPP = "0112010102";
	/**
	 * 删除读者APP页面功能
	 * 新增读者APP页面功能
	 * 修改读者APP页面功能
	 */
	public static final String PERMESSION_ADD_READAPP = "0112010201";	
	public static final String PERMESSION_UPD_READAPP = "0112010203";	
	public static final String PERMESSION_DEL_READAPP = "0112010202";	
	
	/**
	 *  0102020401	新增设备显示风格
		0102020402	删除设备显示风格
		0102020403	修改设备显示风格
	 */
	public static final String PERMESSION_ADD_DEVDISPLAY = "0102020401";	
	public static final String PERMESSION_UPD_DEVDISPLAY = "0102020403";	
	public static final String PERMESSION_DEL_DEVDISPLAY = "0102020402";
	
	
	public static final String LOAN_LOG = "loan_log"; //借还
	
	public static final String FINANCE_LOG = "finance_log";//财经
	
	public static final String CARDISSUE_LOG = "cardissue_log"; //办证

	public static final String STATISTICS = "statistics";//统计后缀
	
	public static final String DAY = "day";
	
	public static final String WEEK = "week";
	
	public static final String MONTH = "month";
	
	public static final String YEAR = "year";
	
	public static final String BOOKITEM = "bookitem";	

	
}
