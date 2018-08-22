package com.ssitcloud.dblib.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.dao.BibliosDao;
import com.ssitcloud.dblib.dao.BookItemDao;
import com.ssitcloud.dblib.dao.LibraryInfoCommonDao;
import com.ssitcloud.dblib.entity.DownloadLibraryInfoEntity;
import com.ssitcloud.dblib.entity.ReturnResultEntity;
import com.ssitcloud.dblib.service.LibraryInfoService;

@Component("downloadLibraryInfo_bookitem")
public class DownloadLibraryInfoBookItem implements LibraryInfoService<DownloadLibraryInfoEntity>{
	
	@Resource
	private BibliosDao bibliosDao;
	
	@Resource
	private BookItemDao bookItemDao;
	
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
			String sql = "SELECT last_sync_time,sync_field_list FROM cloud_dbsync_config WHERE table_name = '"+tableName+"' AND lib_idx = '"+library_idx+"' AND lib_id = '"+library_id+"' limit 1";
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
			
			//去除不存在的列
			/*fieldList.remove("subsidiary");
			fieldList.remove("uploadtime");
			fieldList.remove("device_id");*/
			
			String whereStr = "WHERE lib_idx = '"+library_idx+"' ";
			if(last_sync_time != null && last_sync_time.length() > 0 && !"null".equals(last_sync_time)){
				whereStr += "AND updatetime >= '"+last_sync_time+"' AND updatetime < '"+dateString+"'";
			}
			
			//查询表数据数量
			sql = "SELECT COUNT(1) FROM t_bookitem_biblios " +whereStr;
			
			Integer num = uploadLibraryInfoCommonDao.superManagerSelectCount(sql);
			
			if(num == 0){
				result.setState(false);
				result.setMessage(Const.FAILED);
				result.setRetMessage(tableName + "data is not updated");
				return result;
			}
			
			//将不包含主键的列加进来，加上updatetime字段
			List<String> newfieldList = new ArrayList<>();
			newfieldList.addAll(fieldList);
			//newfieldList.addAll(listKeyName);
			//newfieldList.add("updatetime");
			String newFieldlistStr = StringUtils.join(newfieldList.toArray(), ",");  
			//String orderbyFieldStr = "updatetime,"+StringUtils.join(listKeyName.toArray(), ",");  
			
			//每次下载20条
			int iPageData = 20;
            //int iPageCount = num / 20 + (num % 20 == 0 ? 0 : 1);//计算页数20条分一页
            int iStart = page * iPageData;
            Boolean isContinue = true;
            if (iStart + 20 > num)
            {
                iPageData = num - iStart;
                isContinue = false;
            }
            
			sql = "SELECT "+newFieldlistStr+" FROM t_bookitem_biblios " +whereStr+" LIMIT " + iStart + "," + iPageData;
			
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
				//处理记录表
				sql = "UPDATE cloud_dbsync_config SET last_sync_time = '"+dateString+"' WHERE table_name = '"+tableName+"'";
				uploadLibraryInfoCommonDao.superManagerUpdate(sql);
			}
		} catch (Exception e) {
			result.setState(false);
			result.setMessage(Const.FAILED);
			result.setRetMessage("download process device & cloud server table / field error");
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
