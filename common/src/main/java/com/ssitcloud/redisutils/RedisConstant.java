package com.ssitcloud.redisutils;

public class RedisConstant {
	
	/**过期时间1分钟（秒）*/
	public static final int ONE_MINUTE_EXPIRE_TIME = 60;
	
	/**过期时间5分钟（秒）*/
	public static final int FIVE_MINUTE_EXPIRE_TIME = 60*5;
	
	/**过期时间 10分钟（秒）*/
	public static final int TEN_MINUTE_EXPIRE_TIME = 60*10;
	
	/**过期时间 30分钟（秒）*/
	public static final int THIRTY_MINUTE_EXPIRE_TIME = 60*30;
	
	/**过期时间 60分钟（秒）*/
	public static final int ONE_HOUR_EXPIRE_TIME = 60*60;
	
	/**过期时间 2小时*/
	public static final int TWO_HOUR_EXPIRE_TIME = 60*60*2;
	
	/**过期时间 1天（24小时）*/
	public static final int ONE_DAY_EXPIRE_TIME = 60*60*24;
	
	/**过期时间 10天（24小时）*/
	public static final int TEN_DAY_EXPIRE_TIME = 60*60*24*10;
	
	/**过期时间 15天（24小时）*/
	public static final int FIFTEEN_DAY_EXPIRE_TIME = 60*60*24*15;
	
	/**过期时间 30天*/
	public static final int ONE_MONTH_EXPIRE_TIME = 60*60*24*30;
	
	/**图书馆缓存<br>
	 * 数据格式：散列表<br>
	 * key格式：表名称(library)<br>
	 * value：【lib_id : 图书馆json】
	 * */
	public static final String LIBRARY_KEY = "library";
	
	/**
	 * 根据lib_idx分组缓存图书馆下面的设备id<br>
	 * 数据格式:无序集合<br>
	 * key格式：library:deviceid:+图书馆idx,如library:deviceid:181<br>
	 * value格式:sch01
	 */
	public static final String LIBRARY_DEVICEID = "library:deviceid:";
	
	/**
	 * 缓存图书馆idx与id的键值对<br>
	 * 数据格式：String<br>
	 * key格式：library:keys:lib_idx<br>
	 * value格式：lib_id
	 */
	public static final String LIBRARY_KEYS = "library:keys:";
	
	/**设备权限缓存 ,<br>
	 * 数据格式String<br>
	 * key格式：opercmd:deviceid如：opercmd:sch01*/
	public static final String OPERCMD = "opercmd:";
	
	/**
	 * 缓存opercmd_table表中的数据<br>
	 * 数据格式:String<br>
	 * key格式：opercmd_table:opercmd_id<br>
	 * value格式：mongodb_table这一列的数据
	 */
	public static final String OPERCMD_TABLE = "opercmd_table:";
	
	/**
	 * 缓存mainfield表中的数据<br>
	 * 数据结构：无序集合<br>
	 * key格式:mainfield:mainfield_table<br>
	 * value:mainfield_field
	 */
	public static final String MAINFIELD = "mainfield:";
	
	/***
	 * 缓存设备<br>
	 * 数据格式：String<br>
	 * key格式：device:设备id<br>
	 * value格式：json数据
	 */
	public static final String DEVICE = "device:";
	
	/***
	 * 缓存设备idx与id<br>
	 * 数据格式：String<br>
	 * key格式：device:keys:device_idx<br>
	 * value格式：device_id
	 */
	public static final String DEVICE_KEYS = "device:keys:";
	
	/***
	 * 缓存设备类型<br>
	 * key格式：device_type:类型idx<br>
	 * 数据格式：json 格式
	 */
	public static final String DEVICE_TYPE = "device_type:";
	
	/**
	 * 缓存es里面最大的bookitem的idx<br>
	 * key格式：es_bookitem_idx
	 * value格式：idx
	 */
	public static final String ES_BOOKITEM_IDX = "es_bookitem_idx";
	
	/***
	 * 缓存channel clientId<br>
	 * cliendId:lib_id:device_id
	 */
	public static final String CLIENTID = "cliendId:";
	
	/**
	 * 缓存同步程序推送到设备端的数据的键，通过键然后去获取查询值<br>
	 * 数据结构：集合<br>
	 */
	public static final String CLOUDSYNCCACHEKEY = "cloudSyncCacheKey";
	
	/**
	 * 缓存同步程序推送到设备端的数据<br>
	 * 数据结构：String
	 * 键格式:cloudSyncCache:lib_id:deviceid:table (如果发送数据中的table为空，则用数据中的operation替代)
	 */
	public static final String CLOUDSYNCCACHE = "cloudSyncCache:";

}
