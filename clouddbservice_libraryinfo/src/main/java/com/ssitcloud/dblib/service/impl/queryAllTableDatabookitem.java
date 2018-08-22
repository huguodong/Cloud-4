package com.ssitcloud.dblib.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.dao.LibraryInfoCommonDao;
import com.ssitcloud.dblib.entity.DownloadLibraryInfoEntity;
import com.ssitcloud.dblib.service.LibraryInfoService;

@Component("queryAllTableData_bookitem")
public class queryAllTableDatabookitem implements LibraryInfoService<DownloadLibraryInfoEntity>{
	
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
			
			String whereStr = "WHERE lib_idx = '"+library_idx+"' ";
			if(last_sync_time != null && last_sync_time.length() > 0 && !"null".equals(last_sync_time)){
				//whereStr += "AND updatetime >= '"+last_sync_time+"' AND updatetime < '"+dateString+"'";
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
			
			sql = "SELECT "+newFieldlistStr+" FROM t_bookitem_biblios " +whereStr;
			
			List<LinkedHashMap<String, Object>> listData = uploadLibraryInfoCommonDao.superManagerSelect(sql);
			
			if(listData==null||listData.size()==0){return null;}
			
			result.setResult(JsonUtils.toJson(listData));
			result.setState(true);
			
			//处理记录表
			sql = "UPDATE cloud_dbsync_config SET last_sync_time = '"+dateString+"' WHERE table_name = '"+tableName+"' AND lib_idx = '"+library_idx+"' AND lib_id = '"+library_id+"' AND device_id = '"+device_id +"'";
			uploadLibraryInfoCommonDao.superManagerUpdate(sql);
			
		} catch (Exception e) {
			result.setState(false);
			result.setMessage(Const.FAILED);
			result.setRetMessage("download all data process device & cloud server table / field error");
		}
		return result;
	}

}
