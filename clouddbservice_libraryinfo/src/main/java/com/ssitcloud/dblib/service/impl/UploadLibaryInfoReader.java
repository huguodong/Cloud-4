package com.ssitcloud.dblib.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.entity.UploadLibraryInfoResultEntity;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.entity.UploadLibraryInfoEntity;
import com.ssitcloud.dblib.service.LibraryInfoService;

/**
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
 * <p>2017年4月11日 下午4:41:19  
 * @author hjc 
 *
 */
@Component("uploadLibraryInfo_reader")
public class UploadLibaryInfoReader implements LibraryInfoService<UploadLibraryInfoEntity>{

	@Override
	public ResultEntity execute(
			UploadLibraryInfoEntity uploadLibraryInfoEntity) {
		ResultEntity result=new ResultEntity();
		try {
			Map<String, Object> fields = uploadLibraryInfoEntity.getFields();
			List<Map<String, Object>> records = uploadLibraryInfoEntity.getRecords();
			
			
			if(fields==null||records==null){
				result.setState(false);
				result.setMessage(Const.FAILED);
				result.setRetMessage("fields is null or records is null");
				return result;
			}
			records = comboFielsAndValue(uploadLibraryInfoEntity.getFields(), uploadLibraryInfoEntity.getRecords());
			
			for (Map<String, Object> map : records) {
				System.out.println(JsonUtils.toJson(map));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		result.setState(true);
		return result;
	}
	
	protected static List<Map<String, Object>> comboFielsAndValue(Map<String,Object> fields,List<Map<String,Object>> records){
		List<Map<String,Object>> rets=new ArrayList<>();
		for(Map<String,Object> record:records){
			Map<String,Object> newMap=new HashMap<>();
			for(Entry<String, Object> entry:fields.entrySet()){
				String key=entry.getKey();//字段 
				Object value=entry.getValue();//1 2 3 4 
				if (record.get(value) != null) {
					Object obj = record.get(value);
					newMap.put(key, String.valueOf(obj));
				}
			}
			rets.add(newMap);
		}
		return rets;
	}

	
}
