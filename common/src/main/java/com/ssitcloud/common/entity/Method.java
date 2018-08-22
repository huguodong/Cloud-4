package com.ssitcloud.common.entity;



/*** 
 * 与设备端交互用的函数名
 
 * @package: com.ssitcloud.common.entity
 * @classFile: Method
 * @author: liuBh
 * @description: TODO
 */
public class Method {

	/**
	 * 
	1.String connetion() 连接指令,判断服务层是否连通<br/>
	 */
	public static final String METHOD_CONNECTION="connection";
	
	/**
	 * 2.String deviceLoginCheck(String proInfo) 登录验证,判断设备是否登录成功<br/>
	 */
	public static final String METHOD_DEVICE_LOGIN_CHECK="deviceLoginCheck";
	/**
	 * 3.String heartBeat(String conditionInfo) 心跳-查看云端是否存在控制操作<br/>
    conditionInfo输入内容如下：
    <table>
    	<tr><td>字段名称</td><td>类型</td><td>说明</td></tr>
    	<tr><td>device_id</td><td>String</td><td>设备ID</td></tr>
    	<tr><td>Library_id</td><td>String</td><td>馆ID</td></tr>
    </table>
	<p>（例：监控界面发送的指定设备的重启或关机指令）具体操作，
	为人为的在监控界面 发送关机命令，云端把该指令记录到内存中，
	然后等设备端通过发送heartBeat指令来监测是否有控制指令，
	如果存在控制指令则result 的内容为”control”:”1”;否则为”control”:”0”;<p/>	
	 */
	public static final String METHOD_HEART_BEAT="heartBeat";
	/**
	 * 4.String askControl(String conditionInfo)设备端访问要做什么操作的指令<br/>
	 *  conditionInfo输入内容如下：<br/>
		字段名称	类型	说明<br/>
		device_id	String	设备ID<br/>
		Library_id	String	馆ID<br/><br/>
		<B>返回数据结果result内容如下：<B/><br/>
		字段名称	类型	说明<br/>
			device_id	 String	设备ID<br/>
			Library_id	 String	馆ID<br/>
			Control_info String	控制操作信息 1关机 2 重启 3获取日志<br/>
	
	 */
	public static final String METHOD_ASK_CONTROL="askControl";
	/**
	 * 函数 String controlResult(String conditionInfo)
	 * conditionInfo输入内容如下：<br/>
	       字段名称			类型		说明<br/>
		device_id		String	设备ID<br/>
		Library_id		String	馆ID<br/>
		Controlresult	String	操作结果 1开机、关机、重启操作成功 2开机、关机、重启操作失败<br/>
		errInfo			String 	失败原因<br/>
		<B>返回数据结果result内容如下：</B><br/>
		字段名称	类型	说明<br/>
		device_id		String	设备ID<br/>
		Library_id		String	馆ID<br/>

	 */
	public static final String METHOD_CONTROL_RESULT="controlResult";
	/**
	 * 函数 String uploadRunLog(String conditionInfo)<br/>
	 * conditionInfo输入内容如下：<br/>
		字段名称	类型	说明<br/>
		device_id	String	设备ID<br/>
		Library_id	String	馆ID<br/>
		Pages	String	总页数<br/>
		nowPage	String	当前页<br/>
		Contents	String	数据内容<br/>
		返回数据结果result内容如下：<br/>
		字段名称	类型	说明<br/>
		device_id	String	设备ID<br/>
		Library_id	String	馆ID<br/>
		Controlresult	String	操作结果 1操作成功 2操作失败<br/>
		errInfo	String 	失败原因<br/>
	 */
	public static final String METHOD_UPLOAD_RUN_LOG="uploadRunLog";
	/**
	 * 函数  String cfgSynRequest(String conditionInfo) <br/>
	 * conditionInfo输入内容如下：<br/>
		字段名称	类型	说明<br/>
		device_id	String	设备ID<br/>
		Library_id	String	馆ID<br/>
		返回数据结果result内容如下：<br/>
		字段名称	类型	说明<br/>
		device_id	String	设备ID<br/>
		Library_id	String	馆ID<br/>
		requestResult	String	操作结果 1允许 0禁止<br/>
		disableInfo	String 	禁止原因<br/>

	 */
	public static final String METHOD_CFGSYN_REQUEST="cfgSynRequest";
	
	public static final String METHOD_ASK_CFG_SYNC="askCfgSync";
	
	/**
	 * 
	 * 
	 * 上传操作日志到Mongodb  
	 *
	 * 函数  String transData(String conditionInfo)<br/>
	 * conditionInfo输入内容如下：<br/>
		字段名称	类型	说明<br/>
		device_id	String	设备ID<br/>
		Library_id	String	馆ID<br/>
		Table	String	表名<br/>
		Fields	String	表字段<br/>
		Records	String	数据内容<br/>
		返回数据结果result内容如下：<br/>
		字段名称	类型	说明<br/>
		device_id	String	设备ID<br/>
		Library_id	String	馆ID<br/>

	 */
	public static final String METHOD_TRANSDATA="transData";
	/**
	 * 函数  String cfgSynResponse(String conditionInfo)<br/>
	 * conditionInfo输入内容如下：<br/>
		字段名称	类型	说明<br/>
		device_id	String	设备ID<br/>
		Library_id	String	馆ID<br/>
		返回数据结果result内容如下：<br/>
		字段名称	类型	说明<br/>
		device_id	String	设备ID<br/>
		Library_id	String	馆ID<br/>
		responseResult	String	操作结果 1成功 0失败<br/>
		transInfo	String 	数据传输消息<br/>

	 */
	public static final String METHOD_CFGSYN_RESPONSE="cfgSynResponse";
	
	/**
	 * 下载云端配置数据
	 */
	public static final String METHOD_DOWN_META_SYNC="downloadCfgSync";
	/**
	 * 终端配置数据上传
	 */
	public static final String METHOD_UP_CFG_SYNC="uploadcfgSyn";
	
	/**
	 * 升级包管理
	 */
	public static final String METHOD_ASK_VERSION="askVersion";
	/**
	 * 询问云端时间
	 */
	public static final String METHOD_ASK_CLOUDTIME="askCloudTime";
	
	/**
	 * 下载app私钥信息
	 */
	public static final String METHOD_DOWNLOAD_APP_VERSION_INFO = "downloadAppVersionInfo";
	
	
	/**
	 * 上传设备操作日志,主要是跟图书馆相关，例如，借还日志，财经日志，办证日志，以及各类流通日志等
	 * 
	 * 
	 * <p>函数 String transOperationLog(String conditionInfo)    
	 * <p>备注 ： 逐条发送，云端不支持JSON数组
	 * <p>conditionInfo输入内容如下:
	 *  <p>字段名称	                类型	说明
	    <br>device_id	String	设备ID
		<br>Library_id	String	馆ID
		<br>content	String	json数据内容（云端根据opercmd来区分库）
		
		<p>返回数据结果result内容如下：
		<br>字段名称	                类型	说明
		<br>device_id	String	设备ID
		<br>Library_id	String	馆ID
	 * 
	 * 
	 */
	public static final String METHOD_TRANS_OPERATION_LOG = "transOperationLog";
	
	public static final String METHOD_DOWNLOAD_ACSCFG = "downloadCfgSync";

	/***
	 * 2016年8月16日14:44:18
	 */
	public static final String[] METHODS=
		{
			METHOD_CONNECTION,
			METHOD_DEVICE_LOGIN_CHECK,
			METHOD_HEART_BEAT,
			METHOD_ASK_CONTROL,
			METHOD_CONTROL_RESULT,
			METHOD_UPLOAD_RUN_LOG,
			METHOD_ASK_CFG_SYNC,
			METHOD_DOWN_META_SYNC,
			METHOD_UP_CFG_SYNC,
			METHOD_ASK_VERSION,
			METHOD_ASK_CLOUDTIME,
			METHOD_TRANSDATA
		};
}
