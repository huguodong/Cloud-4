package com.ssitcloud.datasync.entity;
/**
 * 
 * 
 * 	device_id		String	设备ID
	library_id		String	馆ID
	requestResult	String	操作结果 1允许 0禁止
	disableInfo		String 	禁止原因
	tables			String	云端发生变化的表名
		table		String	表名1
		table		String	表名2

	requestResult	String	操作结果 1允许 0禁止
	disableInfo	String 	禁止原因
	如果有数据时，需要返回相关待更新的数据体		

 * 数据同步消息
 * @package: com.ssitcloud.datasync.entity
 * @classFile: DataSyncMsg
 * @author: liubh
 * @createTime: 2016年4月22日 下午11:22:07
 * @description: TODO
 */
public class SyncDataEntity {
	private String device_id;//idx?
	private String library_id;
	private String tables;
	
}
