package com.ssitcloud.common.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.util.FtpUtils;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.datasync.entity.DownloadOsbSynEntity;
import com.ssitcloud.datasync.entity.HeartBeatFileUploadState;
import com.ssitcloud.datasync.entity.HeartBeatLibraryInfoData;
import com.ssitcloud.datasync.service.OsbTableCommand;
import com.ssitcloud.devmgmt.entity.FileUploadState;
import com.ssitcloud.devmgmt.entity.SyncConfigEntity;

public class ScheduledLibraryInfoData {

	private final static String charset=Consts.UTF_8.toString();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");
	
	@Resource(name = "requestURLListEntity")
	private RequestURLListEntity requestURLListEntity;
	
	private static final String URL_Sel_LibraryInfo_Sync_Config="SelLibraryInfoSyncConfig";
	private static final String URL_QUERYSERVICEIDBYLIBID = "queryServiceIdbyLibId";
	private static final String URL_QUERYSERVICEIDBYLIBIDX = "queryServiceIdbyLibIdx";
	public static final String URL_SELDEVICECODE = "selectDeviceCode";
	
	@Resource(name="heartBeatLibraryInfoData")
	private HeartBeatLibraryInfoData heartBeatLibraryInfoData;
	
	@Resource
	private HeartBeatFileUploadState heartBeatFileUploadState;
	
	public void storeChangeTableData(){
		String res=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_Sel_LibraryInfo_Sync_Config), new HashMap<String, String>(), charset);
		if(res==null||"".equals(res)){
			LogUtils.error("获取不到libraryInfo sync_config 表信息!!!");
			return;
		}
		if(!JSONUtils.mayBeJSON(res)){
			LogUtils.error("获取不到libraryInfo sync_config 表信息!!!\n"+res+"\n");
			return;
		}
		ResultEntityF<List<SyncConfigEntity>> result=null;
		
		try {
			result=JsonUtils.fromJson(res, new TypeReference<ResultEntityF<List<SyncConfigEntity>>>() {});
		} catch (Exception e) {
			LogUtils.error("storeChangeTableData --->json 转化对象出错", e);
			return;
		}
	
		if(result!=null&&result.getState()==true){
			List<SyncConfigEntity> tableChanges=result.getResult();
			if(tableChanges!=null){
				Map<String,Boolean> clearFlagMap=new HashMap<>();
				Map<String,ArrayList<String>> map = new HashMap<>();
				for(SyncConfigEntity tableChange:tableChanges){
					String library_id=tableChange.getLib_id();
					String library_idx=String.valueOf(tableChange.getLib_idx());
					List<String> devIds = new ArrayList<>();
					
					//检查时间，lastSyncTime和当前时间比较
					Date lastSyncTime = tableChange.getLast_sync_time();
					Date lastModifyTime = tableChange.getLast_modify_time();
					Date now = new Date();
					
					Long midTime = 0l;
					if(lastSyncTime != null){
						midTime = now.getTime() - lastSyncTime.getTime();
					}else{
						midTime = now.getTime() - lastModifyTime.getTime();
					}
					
					String syncCyle = tableChange.getSync_cycle();
					if(StringUtils.isEmpty(syncCyle)){
						continue;
					}
					String syncNum = syncCyle.substring(0,syncCyle.length()-1);
					String syncDw = syncCyle.substring(syncCyle.length()-1,syncCyle.length());
					int i = 0;
					if("D".equals(syncDw)){
						i = 86400;
					}else if("H".equals(syncDw)){
						i = 3600;
					}else if("M".equals(syncDw)){
						i = 60;
					}else if("S".equals(syncDw)){
						i = 1;
					}
					Long syncTime = Long.parseLong(syncNum) * i;
					if(syncTime > midTime){
						//等待一个周期时间完成
						continue;
					}
					
					String syncType = tableChange.getSync_type();
					String devId = tableChange.getDevice_id();
					String tableField = tableChange.getSync_field_list();
					if(StringUtils.isEmpty(tableField)){
						continue;
					}
					String key = library_id + "#@#" + devId + "#@#" + syncType;
					if(StringUtils.isEmpty(syncType)){
						doHeartBeatChangeTableData(clearFlagMap, null, devId,tableChange);
					}else{
						//写文件，并上传到ftp服务器
						if(map.containsKey(key)){
							ArrayList<String> tables = map.get(key);
							tables.add(tableChange.getTable_name());
						}else{
							ArrayList<String> tables = new ArrayList<>();
							tables.add(tableChange.getTable_name());
							map.put(key, tables);
						}
					}
				}
				
				//定时准备下载文件
				System.out.println("定时准备下载文件... size="+map.size());
				for (String key : map.keySet()) {  
					String[] deviceInfo = key.split("#@#",3);
					boolean isSuccess = createFileUploadFtpServer(deviceInfo[0],deviceInfo[1],map.get(key),deviceInfo[2]);
					if(isSuccess){
						System.out.println("下载设备数据文件 libId:"+deviceInfo[0]+" deviceId:"+deviceInfo[1]+ " Success!");
					}
					else{
						System.out.println("下载设备数据文件 libId:"+deviceInfo[0]+" deviceId:"+deviceInfo[1]+ " Failed!");
					}
				}
			}
		}
	}
	
	private void doHeartBeatChangeTableData(Map<String,Boolean> clearFlagMap,Integer dataIdx,String device_id,SyncConfigEntity tableChange){
		
		if(clearFlagMap.containsKey(device_id)){
			//表示已经标记过，则不需要再次标记
			clearFlagMap.put(device_id, false);
		}else{
			//没有标记过,则需要清理，并且已经标记
			clearFlagMap.put(device_id, true);
		}
		//若果不存在则新建一个list,并保存
		ConcurrentLinkedQueue<SyncConfigEntity> tableChangeQueue=new ConcurrentLinkedQueue<>();
		if(heartBeatLibraryInfoData.containsKey(device_id)){
			//存在 device_id,将device_id队列取出来
			//循环的开始每个队列都要清理一次
			Queue<SyncConfigEntity> queue=heartBeatLibraryInfoData.get(device_id);
		
			if(clearFlagMap.get(device_id)==true&&!queue.isEmpty()){//没有清理过的队列，则循环第一次需要清理队列。
				queue.clear();
				clearFlagMap.put(device_id, false);
			}
			queue.add(tableChange);
			//由于间隔时间太短不能打印出来通过、queue.jsp观察
		}else{
			tableChangeQueue.add(tableChange);
			//第一次调用，没有队列，设置不清除
			clearFlagMap.put(device_id, false);
			heartBeatLibraryInfoData.put(device_id, tableChangeQueue);
		}
	}
	
	private boolean createFileUploadFtpServer(String library_id,String device_id,ArrayList<String> tables,String fileType){
		
		String username = null;
		String passwd = null;
		if(StringUtils.hasLength(library_id) && StringUtils.hasLength(device_id)){
			Map<String, Object> m = new HashMap<>();
			m.put("device_id", device_id);
			Map<String, String> p = new HashMap<>();
			p.put("req", JsonUtils.toJson(m));
			/*String s = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_SELDEVICECODE), p, charset);
			if (JSONUtils.mayBeJSON(s)) {
        		ResultEntity res = JsonUtils.fromJson(s, ResultEntity.class);
        		if(res.getState() && res.getResult() != null){
        			username = device_id;
        			passwd = res.getResult().toString().substring(0, 8);
        		}
			}*/
			String ftpInfo = FtpUtils.createDirectory(username,passwd,"/download");
			if(StringUtils.hasLength(ftpInfo)){
				for(int i=0;i<tables.size();i++){
					String tableName = tables.get(i);
					
					boolean isCreating = false;
					//判断是否正在准备下载文件....
					if(heartBeatFileUploadState.containsKey(device_id)){
						for(FileUploadState ety :heartBeatFileUploadState.get(device_id)){
							if(ety.getTableName().equals(tableName)){
								if("1".equals(ety.getState())){
									isCreating = true;
								}
								break;
							}
						}
					}
					if(isCreating){
						//系统正在准备下载的文件，此次不下载
						continue;
					}
					
					String fileName = tableName+"_"+library_id+"_"+device_id+"_"+sdf.format(new Date())+".txt";
					if("zip".equals(fileType)){
						fileName = fileName.replace(".txt", ".zip");
					}
					addHeartBeatData(tableName,fileName,device_id,"1",null,ftpInfo);
					Map<String, Object> map = new HashMap<>();
					if(map.containsKey("table")) map.remove("table");
					map.put("table", tableName);
					
					if(map.containsKey("path")) map.remove("path");
					map.put("path", ftpInfo+fileName);
					
					if(map.containsKey("fileType")) map.remove("fileType");
					map.put("fileType", fileType);
					
					if(map.containsKey("library_id")) map.remove("library_id");
					map.put("library_id", library_id);
					
					if(map.containsKey("device_id")) map.remove("device_id");
					map.put("device_id", device_id);
					
					String json = JsonUtils.toJson(map);
					DownloadOsbSynEntity osb = JsonUtils.fromJson(json,DownloadOsbSynEntity.class);
					
					OsbTableCommand command = null;
					
					try {
						command=BeanFactoryHelper.getBean("down_"+tableName,OsbTableCommand.class);
					} catch (Exception e) {
						command=BeanFactoryHelper.getBean("downLibraryInfoByFile",OsbTableCommand.class);
						//throw new RuntimeException("down_tableName:down_"+tableName+" is not found!!!! ");
					}
					
					if(command!=null){
						command.execute(osb);
					}
					
					//等待解决线程同步问题
					//Thread.sleep(1);
					return true;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
		return false;
	}
	
	public void addHeartBeatData(String tableName,String fileName,String device_id,String control,String tableField,String filePath){
		FileUploadState fus = new FileUploadState();
		fus.setTableName(tableName);
		fus.setFileName(fileName);
		fus.setState(control);
		fus.setFilePath(filePath);
		//加入
		if(heartBeatFileUploadState.containsKey(device_id)){
			int i = 1;
			for(FileUploadState ety :heartBeatFileUploadState.get(device_id)){
				if(ety.getTableName().equals(fus.getTableName()) && ety.getFileName().equals(fus.getFileName())){
				//if(ety.getTableName().equals(fus.getTableName())){
					//移除之前的缓存
					heartBeatFileUploadState.get(device_id).remove(ety);
					//增加上传完成
					heartBeatFileUploadState.get(device_id).add(fus);
					break;
				}else{
					if(i == heartBeatFileUploadState.get(device_id).size()){
						heartBeatFileUploadState.get(device_id).add(fus);
					}
				}
				i++;
			}
		}else{
			//若果不存在则新建一个list,并保存
			ConcurrentLinkedQueue<FileUploadState> orderQueue=new ConcurrentLinkedQueue<FileUploadState>();
			orderQueue.add(fus);
			heartBeatFileUploadState.put(device_id,orderQueue);
		}
	}
	
}
