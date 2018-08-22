package com.ssitcloud.datasync.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.codehaus.jackson.JsonNode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.datasync.entity.UploadLinraryInfoEntity;
import com.ssitcloud.datasync.service.DataSyncCommand;
import com.ssitcloud.library.service.LibraryService;

/**
 * 字段名称	类型	说明
	device_id	String	设备ID
	Library_id	String	馆ID
	Table	String	表名(bookitem、reader)
	Fields	String	表字段
	Records	String	数据内容
	
	返回数据结果result内容如下：
	字段名称	类型	说明
	device_id	String	设备ID
	Library_id	String	馆ID
	responseResult	String	操作结果 1成功 0失败
	transInfo	String 	数据传输消息

	
	设备上传图书馆藏数据 或者读者数据
	
	数据demo
	示例：
	
	bookitem表：
	{
	    "servicetype":"ssitcloud",
	    "target":"ssitcloud",
	    "operation":"uploadLibraryInfo",
	    "data":{
	        "library_id":"SZLIB",
	        "device_id":"SSL_001",
	        "table":"bookitem",
	        "fields":{
	            "book_barcode":"1",
	            "book_uid":"2",
	            "ISBN":"3",
	            "title":"4",
	            "author":"5",
	            "publish":"6",
	            "callNo":"7",
	            "shelflayer_barcode":"8",
	            "update_uid_flagtiny":"9",
	            "state":"10",
	            "updatetime":"11",
	            "statemodcount":"12"
	        },
	        "records":[
	            {
	                "1":"04400500000100",
	                "2":"3120",
	                "3":"001349",
	                "4":"标题101",
	                "5":"张荣",
	                "6":"人民出版社",
	                "7":"R274/2",
	                "8":"3120",
	                "9":"1",
	                "10":"1",
	                "11":"2016-11-16 14:04:22",
	                "12":"1"
	            }
	        ]
	    }
	}
	
	reader表：
	{
	    "servicetype":"ssitcloud",
	    "target":"ssitcloud",
	    "operation":"uploadLibraryInfo",
	    "data":{
	        "library_id":"SZLIB",
	        "device_id":"SSL_001",
	        "table":"reader",
	        "fields":{
	            "idx":"1",
	            "card_no":"2",
	            "card_type":"3",
	            "valid":"4",
	            "expire_date":"5",
	            "privilege_fee":"6",
	            "name":"7",
	            "password":"8",
	            "user_id":"9",
	            "birth":"10",
	            "gender":"11",
	            "address":"12",
	            "age":"13"
	        },
	        "records":[
	            {
	                "1":"1",
	                "2":"10010010010",
	                "3":"1",
	                "4":"1",
	                "5":"20200101",
	                "6":"210",
	                "7":"刘杰",
	                "8":"123456",
	                "9":"1001002016010011",
	                "10":"2010-01-01",
	                "11":"1",
	                "12":"深圳南山科技园",
	                "13":"23"
	            }
	        ]
	    }
	}

 *
 * <p>2017年4月10日 下午3:21:48  
 * @author hjc 
 *
 */
@Component(value="uploadLibraryInfo")
public class UploadLibraryInfo extends BasicServiceImpl implements DataSyncCommand{
	
	private static final String UPLOAD_TABLE = "table";
	private static final String UPLOAD_FIELDS = "fields";
	private static final String UPLOAD_RECORDS = "records";
	
	private static final String URL_UPLOADL_IBRARY_INFO = "uploadLibraryInfo";
	/*private static final String URL_QUERYDEVICESERVICEBYPARAMS = "queryDeviceServiceByParams";*/
	
	/*@Resource(name="LIBRARY_CACHE")
	private Cache librarycache;*/
	@Resource
	private LibraryService libServcie;
	@Override
	public RespEntity execute(RequestEntity conditionInfo) {
		RespEntity resp = new RespEntity(conditionInfo);
		try {
			Map<String, Object> map = conditionInfo.getData();
			if (map.containsKey(UPLOAD_TABLE) && map.containsKey(UPLOAD_FIELDS) && map.containsKey(UPLOAD_RECORDS)) {
				Map<String, String> params = new HashMap<>();
				String lib_id = map.get(LIB_ID) + "";
				String lib_idx = "";
				if (StringUtils.hasLength(lib_id)) {
					lib_idx = libServcie.getLibraryIdxById(lib_id)+"";
					/*if(librarycache != null){
						Element e = librarycache.get(lib_id);
						if(e!=null){
							lib_idx = (String) e.getObjectValue();
						}
					}*/
				}
				try {
					map.put("library_id", lib_idx);
					map.put("lib_id", lib_id);
					if (!StringUtils.isEmpty(lib_idx)) {
						params.put("req", JsonUtils.toJson(map));
					}else{
						resp.getData().setState(false);
						resp.getData().setRetMessage("查询不到图书馆或者设备信息");
						return resp;
					}
				} catch (Exception e) {
					e.printStackTrace();
					resp.getData().setRetMessage(e.getMessage());
					return resp;
				}
				String result = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_UPLOADL_IBRARY_INFO), params, charset);
				if (StringUtils.hasLength(result)) {// ResultEntity.java
					resp.setData(JsonUtils.fromJson(result, ResultEntity.class));
				}
				
			}else{//缺少字段
				resp.getData().setState(false);
				UploadLinraryInfoEntity uploadLinraryInfoEntity = new UploadLinraryInfoEntity(
						map.get(DEV_ID) + "", map.get(LIB_ID) + "", "0", "缺少字段table或者fields或者records");
				resp.getData().setResult(uploadLinraryInfoEntity);
			}
			
		} catch (Exception e) {
			ExceptionHelper.afterException(resp, Thread.currentThread(), e);
		}

		return resp;
	}
}
