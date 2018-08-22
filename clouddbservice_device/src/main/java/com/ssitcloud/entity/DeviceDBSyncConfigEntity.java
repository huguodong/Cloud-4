package com.ssitcloud.entity;



/**
 * 
 * @package: com.ssitcloud.entity
 * @classFile: DeviceDBSyncConfigEntity
 * @author: liuBh
 * @description: TODO
 */
public class DeviceDBSyncConfigEntity {
	/**
	 * 
	 * 表名：device_dbsync_config
		device_dbsync_id	int(11) NOT NULL模板ID或设备ID
		library_idx	int(11) NOT NULL馆ID
		device_tpl_type	int(11) NOT NULL模板区分 0模板 1数据
		db_name	varchar(50) NOT NULL数据库名
		table_name	varchar(50) NOT NULL数据库表名
		issync	int(11) NULL是否同步
		sync_cycle	int(11) NULL同步周期
		last_sync_time	timestamp NULL最后同步时间
		last_modify_time	timestamp NULL最后修改时间
	 */
	private Integer device_dbsync_id;
	private Integer library_idx;
	private Integer device_tpl_type;
	private String db_name;
	private String table_name;
	private String sync_field_list;
	private String sync_type; //zip txt ??
	private int issync;
	private String sync_cycle;
	private String last_sync_time;
	private String last_modify_time;
	
	public static final Integer IS_MODEL=0;
	public static final Integer IS_NOT_MODEL=1;
	
	public DeviceDBSyncConfigEntity(){}
	
	public DeviceDBSyncConfigEntity(Integer device_dbsync_id){
		this.device_dbsync_id=device_dbsync_id;
	}

	public Integer getDevice_dbsync_id() {
		return device_dbsync_id;
	}
	public void setDevice_dbsync_id(Integer device_dbsync_id) {
		this.device_dbsync_id = device_dbsync_id;
	}
	public Integer getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}
	public Integer getDevice_tpl_type() {
		return device_tpl_type;
	}
	public void setDevice_tpl_type(Integer device_tpl_type) {
		this.device_tpl_type = device_tpl_type;
	}
	public String getDb_name() {
		return db_name;
	}
	public void setDb_name(String db_name) {
		this.db_name = db_name;
	}
	
	public int getIssync() {
		return issync;
	}

	public void setIssync(int issync) {
		this.issync = issync;
	}
	public String getSync_cycle() {
		return sync_cycle;
	}

	public void setSync_cycle(String sync_cycle) {
		this.sync_cycle = sync_cycle;
	}

	public String getLast_sync_time() {
		return last_sync_time;
	}

	public void setLast_sync_time(String last_sync_time) {
		this.last_sync_time = last_sync_time;
	}

	public String getLast_modify_time() {
		return last_modify_time;
	}

	public void setLast_modify_time(String last_modify_time) {
		this.last_modify_time = last_modify_time;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getSync_field_list() {
		return sync_field_list;
	}

	public void setSync_field_list(String sync_field_list) {
		this.sync_field_list = sync_field_list;
	}

	public String getSync_type() {
		return sync_type;
	}

	public void setSync_type(String sync_type) {
		this.sync_type = sync_type;
	}
	
}
