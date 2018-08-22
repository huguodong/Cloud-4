package com.ssitcloud.dblib.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.entity.UploadLibraryInfoResultEntity;
import com.ssitcloud.dblib.dao.LibraryInfoCommonDao;
import com.ssitcloud.dblib.entity.DownloadLibraryInfoEntity;
import com.ssitcloud.dblib.entity.UploadLibraryInfoEntity;
import com.ssitcloud.dblib.service.LibraryInfoService;

@Component("uploadLibraryInfo_common")
public class UploadLibaryInfoCommon implements LibraryInfoService<UploadLibraryInfoEntity>{
	
	@Resource
	private LibraryInfoCommonDao uploadLibraryInfoCommonDao;
	

	@Override
	public ResultEntity execute(
			UploadLibraryInfoEntity uploadLibraryInfoEntity) {
		
		ResultEntity result=new ResultEntity();
		try {
			String tableName = uploadLibraryInfoEntity.getTable();
			Map<String, Object> fields = uploadLibraryInfoEntity.getFields();
			List<Map<String, Object>> records = uploadLibraryInfoEntity.getRecords();
			String device_idx = uploadLibraryInfoEntity.getDevice_idx();
			String library_idx = uploadLibraryInfoEntity.getLibrary_idx();//
			String library_id = uploadLibraryInfoEntity.getLibrary_id();
			String device_id = uploadLibraryInfoEntity.getDevice_id();
			
			if(fields==null||records==null){
				result.setState(false);
				result.setMessage(Const.FAILED);
				result.setRetMessage("fields is null or records is null");
				return result;
			}
			records = comboFielsAndValue(uploadLibraryInfoEntity.getFields(), uploadLibraryInfoEntity.getRecords());
			
			//先根据主键查询出数据是否存在
			
			String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE TABLE_NAME='" + tableName + "' AND CONSTRAINT_NAME = 'PRIMARY'";
			List<LinkedHashMap<String, Object>> listKey = uploadLibraryInfoCommonDao.superManagerSelect(sql);
			List<String> listKeyName = new ArrayList<>();
			
			for (Map<String, Object> map : records) {
				try{
					//如果主键不存在直接做插入操作
					if(listKey == null || listKey.size() < 2){ //至少存在Lib_id 或者Lib_idx + 本地表主键
						result.setState(false);
						result.setMessage(Const.FAILED);
						result.setRetMessage("table missing key");
						return result;
					}else{
						//拼接主键查询数据
						String whereStr = "where ";
						
						for(LinkedHashMap<String, Object> key : listKey){
							String keyName = key.get("COLUMN_NAME").toString();
							listKeyName.add(keyName);
							if("lib_id".equals(keyName)){
								whereStr += "lib_id = '"+library_id +"' and ";
							}else if("lib_idx".equals(keyName)){
								whereStr += "lib_idx = '"+library_idx +"' and ";
							}else{
								whereStr += keyName + "='" + map.get(keyName) + "' and ";
							}
						}
						whereStr += "1=1";
								
						sql = "select count(1) from "+tableName +" "+ whereStr;
						Integer num = uploadLibraryInfoCommonDao.superManagerSelectCount(sql);
						if(num > 0){
							//更新
							String updateStr = "";
							for (String key : map.keySet()) {
								if(map.get(key) == null){
									updateStr += key + "= null,";
								}else{
									updateStr += key + "= '"+ map.get(key) +"',";
								}
							}
							updateStr += "updatetime = null";
							sql = "update "+ tableName +" set " + updateStr +" "+ whereStr;
							Integer numUpdt = uploadLibraryInfoCommonDao.superManagerUpdate(sql);
							if(numUpdt < 1){
								//更新失败
							}
						}else{
							//插入
							String insertPart1 = "";
							String insertPart2 = "";
							for (String key : map.keySet()) {
								insertPart1 += key + ",";
								if(map.get(key) == null){
									insertPart2 += "null,";
								}else{
									insertPart2 += "'" + map.get(key) + "',";
								}
							}
							insertPart1 += "updatetime,";
							insertPart2 += "null,";
							
							if(listKeyName.contains("lib_id")){
								insertPart1 += "lib_id";
								insertPart2 += "'"+library_id+"'";
							}else if(listKeyName.contains("lib_idx")){
								insertPart1 += "lib_idx";
								insertPart2 += "'"+library_idx+"'";
							}
							sql = "insert into "+ tableName + "(" +insertPart1+") values (" +insertPart2 +")";
							Integer numInsert = uploadLibraryInfoCommonDao.superManagerInsert(sql);
							if(numInsert < 1){
								//插入失败
							}
						}
						
						//针对上传的delete_rec记录数据
						if("delete_rec".equals(tableName)){
							sql = "delete from "+tableName+" "+whereStr;
							uploadLibraryInfoCommonDao.superManagerDelete(sql);
						}
					}
				}catch(Exception ex){
					continue;
				}
				result.setState(true);
			}
		} catch (Exception e) {
			result.setState(false);
			result.setMessage(Const.FAILED);
			result.setRetMessage("upload process device & cloud server table / field error");
		}
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
