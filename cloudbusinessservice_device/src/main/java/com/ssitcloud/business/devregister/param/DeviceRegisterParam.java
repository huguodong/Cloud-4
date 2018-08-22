package com.ssitcloud.business.devregister.param;

import java.util.List;
import java.util.Map;

/**
 * 设备注册提交数据类
 * <p>2016年4月27日 下午5:50:53
 * @author hjc
 */

public class DeviceRegisterParam {
	
	/** 设备特征码 */
	private String deviceCode;
	/** 图书馆id */
	private String pId;
	private String libId;
	/** 图书馆idx */
	private String library_idx;
	/** 设备id */
	private String deviceId;
	/** 设备名称 */
	private String deviceName;
	/** 设备类型idx */
	private String deviceType;
	/** 设备描述 */
	private String deviceDesc;
	/** acs登录名 */
	private String acsName;
	/** acs密码 */
	private String acsPwd;
	/** 物流编号 */
	private String logisticsNumber;
	/** 流通地点 */
	private String circuleLocation;
	
	/** 硬件与逻辑配置模板idx */
	private String extTempId;
	/** 运行配置模板idx */
	private String runTempId;
	/** 监控模板idx */
	private String monitorTempId ;
	/** 数据同步模板idx */
	private String dbSyncTempId;
	
	/** 设备IP */
	private String ip;
	/** 设备端口 */
	private String port;
	/** 设备用户的idx */
	private String operator_idx;
	/** 操作用户idx */
	private String admin_idx;
	/** 外设模板自定义数据 */
	private String extTempSubmit;
	/** 运行模板自定义数据*/
	private String runTempSubmit;
	/** acs服务配置list */
	private List<Map<String, Object>> acsList;	
	
	/*** 设备地理位置信息 */
	private String region;
	
	
	
	
	
	
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public List<Map<String, Object>> getAcsList() {
		return acsList;
	}
	public void setAcsList(List<Map<String, Object>> acsList) {
		this.acsList = acsList;
	}
	public String getCirculeLocation() {
		return circuleLocation;
	}
	public void setCirculeLocation(String circuleLocation) {
		this.circuleLocation = circuleLocation;
	}
	public String getExtTempSubmit() {
		return extTempSubmit;
	}
	public void setExtTempSubmit(String extTempSubmit) {
		this.extTempSubmit = extTempSubmit;
	}
	public String getRunTempSubmit() {
		return runTempSubmit;
	}
	public void setRunTempSubmit(String runTempSubmit) {
		this.runTempSubmit = runTempSubmit;
	}
	public String getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(String library_idx) {
		this.library_idx = library_idx;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public String getLibId() {
		return libId;
	}
	public void setLibId(String libId) {
		this.libId = libId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getAcsName() {
		return acsName;
	}
	public void setAcsName(String acsName) {
		this.acsName = acsName;
	}
	public String getAcsPwd() {
		return acsPwd;
	}
	public void setAcsPwd(String acsPwd) {
		this.acsPwd = acsPwd;
	}
	public String getLogisticsNumber() {
		return logisticsNumber;
	}
	public void setLogisticsNumber(String logisticsNumber) {
		this.logisticsNumber = logisticsNumber;
	}
	public String getExtTempId() {
		return extTempId;
	}
	public void setExtTempId(String extTempId) {
		this.extTempId = extTempId;
	}
	public String getRunTempId() {
		return runTempId;
	}
	public void setRunTempId(String runTempId) {
		this.runTempId = runTempId;
	}
	public String getMonitorTempId() {
		return monitorTempId;
	}
	public void setMonitorTempId(String monitorTempId) {
		this.monitorTempId = monitorTempId;
	}
	public String getDbSyncTempId() {
		return dbSyncTempId;
	}
	public void setDbSyncTempId(String dbSyncTempId) {
		this.dbSyncTempId = dbSyncTempId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getOperator_idx() {
		return operator_idx;
	}
	public void setOperator_idx(String operator_idx) {
		this.operator_idx = operator_idx;
	}
	public String getDeviceDesc() {
		return deviceDesc;
	}
	public void setDeviceDesc(String deviceDesc) {
		this.deviceDesc = deviceDesc;
	}
	public String getAdmin_idx() {
		return admin_idx;
	}
	public void setAdmin_idx(String admin_idx) {
		this.admin_idx = admin_idx;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	
	
	
}
