package com.ssitcloud.dblib.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.dao.LibraryInfoCommonDao;
import com.ssitcloud.dblib.entity.DownloadLibraryInfoEntity;
import com.ssitcloud.dblib.entity.ReturnResultEntity;
import com.ssitcloud.dblib.service.LibraryInfoService;

@Component("downloadLibraryInfo_common")
public class DownloadLibraryInfoCommon implements LibraryInfoService<DownloadLibraryInfoEntity>{
	
	@Resource
	private LibraryInfoCommonDao uploadLibraryInfoCommonDao;
	

	@Override
	public ResultEntity execute(
			DownloadLibraryInfoEntity downloadLibraryInfoEntity) {
		ResultEntity result=new ResultEntity();
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String dateString = formatter.format(date);
		try {
			String tableName = downloadLibraryInfoEntity.getTable();
			String device_idx = downloadLibraryInfoEntity.getDevice_idx();
			String library_idx = downloadLibraryInfoEntity.getLibrary_idx();//
			String library_id = downloadLibraryInfoEntity.getLibrary_id();
			String device_id = downloadLibraryInfoEntity.getDevice_id();
			Integer page = downloadLibraryInfoEntity.getPage();
			
			
			//查询cloud_dbsyc_config表中记录时间
			String sql = "SELECT last_sync_time,sync_field_list FROM cloud_dbsync_config WHERE table_name = '"+tableName+"' AND lib_idx = '"+library_idx+"' AND lib_id = '"+library_id+"' AND device_id = '"+device_id+"' limit 1";
			List<LinkedHashMap<String, Object>> listSyc = uploadLibraryInfoCommonDao.superManagerSelect(sql);
			
			if(listSyc == null || listSyc.size() == 0){
				result.setState(false);
				result.setMessage(Const.FAILED);
				result.setRetMessage(tableName + "data is not updated");
				return result;
			}
			
			String last_sync_time = String.valueOf(listSyc.get(0).get("last_sync_time"));
			String fieldlistStr = String.valueOf(listSyc.get(0).get("sync_field_list"));
			
			if(fieldlistStr==null||fieldlistStr.length()==0){
				result.setState(false);
				result.setMessage(Const.FAILED);
				result.setRetMessage("fieldlist is null cannot download data");
				return result;
			}
			
			List<String> fieldList = Arrays.asList(fieldlistStr.split(","));  
			
			String whereStr = "WHERE lib_id = '"+library_id+"' ";
			if(last_sync_time != null && last_sync_time.length() > 0 && !"null".equals(last_sync_time)){
				//whereStr += "AND updatetime >= '"+last_sync_time+"' AND updatetime < '"+dateString+"'";
				whereStr += "AND updatetime >= '"+last_sync_time+"' AND updatetime < '"+dateString+"'";
			}
			
			//查询表数据数量
			sql = "SELECT COUNT(1) FROM "+tableName+" " +whereStr;
			
			Integer num = uploadLibraryInfoCommonDao.superManagerSelectCount(sql);
			
			if(num == 0){
				result.setState(false);
				result.setMessage(Const.FAILED);
				result.setRetMessage(tableName + "data is not updated");
				return result;
			}
			
			//获取表主键
			sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE TABLE_NAME='" + tableName + "' AND CONSTRAINT_NAME = 'PRIMARY'";
			List<LinkedHashMap<String, Object>> listKey = uploadLibraryInfoCommonDao.superManagerSelect(sql);
			List<String> listKeyName = new ArrayList<>();
			for(LinkedHashMap<String, Object> key : listKey){
				String keyName = key.get("COLUMN_NAME").toString();
				listKeyName.add(keyName);
			}
			
			//将不包含主键的列加进来，加上updatetime字段
			List<String> newfieldList = new ArrayList<>();
			newfieldList.addAll(fieldList);
			//newfieldList.addAll(listKeyName);
			//newfieldList.add("updatetime");
			String newFieldlistStr = StringUtils.join(newfieldList.toArray(), ",");  
			//String orderbyFieldStr = "updatetime,"+StringUtils.join(listKeyName.toArray(), ",");  
			
			String orderbyFieldStr = "";
			orderbyFieldStr += "updatetime,";
            if (null != listKeyName && listKeyName.size() > 0)
            {
                for (String key : listKeyName)//循环获得的数据
                {
                	orderbyFieldStr += (key + ",");
                }
                orderbyFieldStr = orderbyFieldStr.substring(0, orderbyFieldStr.length() - 1);
            }
            
			//每次下载20条
			int iPageData = 20;
            //int iPageCount = num / 20 + (num % 20 == 0 ? 0 : 1);//计算页数20条分一页
            int iStart = page * iPageData;
            Boolean isContinue = true;
            if (iStart + 20 >= num)
            {
                iPageData = num - iStart;
                isContinue = false;
            }
            
			sql = "SELECT "+newFieldlistStr+" FROM "+tableName+" " +whereStr+" ORDER BY "+orderbyFieldStr+" asc LIMIT " + iStart + "," + iPageData;
			
			List<LinkedHashMap<String, Object>> listData = uploadLibraryInfoCommonDao.superManagerSelect(sql);
			
			String currentUpdatetime = "";
			//String keyValueStr = "";
			
			//处理数据、
			ReturnResultEntity ret=new ReturnResultEntity();
			List< Map<String,Object>> records=new ArrayList<>();
			if(listData==null||listData.size()==0){return null;}
			Map<String,String> fieldMap = new HashMap<String, String>();
			int i=1;
			for (String key : newfieldList){
				if(fieldList.contains(key)){
					String fieldId=String.valueOf(i);
					fieldMap.put(key, fieldId);
				}
				i++;
			}
			int k = 1;
			for(LinkedHashMap<String, Object> rst : listData){
				Map<String,Object> record=new HashMap<>();
				for (String key : newfieldList){
					String fieldId=fieldMap.get(key);
					Object param = rst.get(key);
					if(param==null){//如果为空则替换成"null"
						param="null";
					}
					if(fieldList.contains(key)){
						record.put(fieldId, String.valueOf(param));
						/*if (param instanceof Integer) {// 判断变量的类型
							int value = ((Integer) param).intValue();
							record.put(fieldId, value);
						} else if (param instanceof String) {
							String value = (String) param;
							record.put(fieldId, value);
						} else if (param instanceof Double) {
							double value = ((Double) param).doubleValue();
							record.put(fieldId, value);
						} else if (param instanceof Float) {
							float value = ((Float) param).floatValue();
							record.put(fieldId, value);
						} else if (param instanceof Long) {
							long value = ((Long) param).longValue();
							record.put(fieldId, value);
						} else if (param instanceof Boolean) {
							boolean value = ((Boolean) param).booleanValue();
							record.put(fieldId, value);
						} else if (param instanceof Date) {
							Date value = (Date) param;
							record.put(fieldId, value);
						}*/
					}
					//if(k == listData.size()){
						//获取最后一条数据内容
						//if("updatetime".equals(key)){
						//	currentUpdatetime = String.valueOf(param);
						//}
						/*if(listKeyName.contains(key)){
							keyValueStr += String.valueOf(param);
						}*/
					//}
				}
				records.add(record);
				k++;
			}
			
			ret.setFields(fieldMap);
			ret.setRecords(records);
			ret.setTable(tableName);
			
			//String retStr = JsonUtils.toJson(listData);
			//retStr = "\"table\":\""+tableName+"\",\"result\":"+retStr+"";
			
			result.setResult(ret);
			result.setState(true);
			
			if(isContinue){
				result.setMessage(String.valueOf(page+1));
			}else{
				
				//针对delete_rec，清空这张表已经下发的数据
				if("delete_rec".equals(tableName)){
					sql = "DELETE FROM "+tableName+" " +whereStr;
					uploadLibraryInfoCommonDao.superManagerDelete(sql);
				}
				
				//处理记录表
				sql = "UPDATE cloud_dbsync_config SET last_sync_time = '"+dateString+"' WHERE table_name = '"+tableName+"' AND lib_idx = '"+library_idx+"' AND lib_id = '"+library_id+"' AND device_id = '"+device_id +"'";
				uploadLibraryInfoCommonDao.superManagerUpdate(sql);
			}
		} catch (Exception e) {
			result.setState(false);
			result.setMessage(Const.FAILED);
			result.setRetMessage("download process device & cloud server table / field error");
		}
		return result;
	}

}
