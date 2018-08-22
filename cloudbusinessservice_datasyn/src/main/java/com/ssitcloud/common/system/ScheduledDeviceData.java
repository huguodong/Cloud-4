package com.ssitcloud.common.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.util.FtpUtils;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.datasync.entity.DownloadOsbSynEntity;
import com.ssitcloud.datasync.entity.HeartBeatFileUploadState;
import com.ssitcloud.datasync.service.OsbTableCommand;
import com.ssitcloud.devmgmt.entity.FileUploadState;
import com.ssitcloud.devmgmt.entity.SyncConfigEntity;

public class ScheduledDeviceData {
	private final static String charset = Consts.UTF_8.toString();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");

	@Resource(name = "requestURLListEntity")
	private RequestURLListEntity requestURLListEntity;

	private static final String URL_CheckFileUploadFlag = "checkFileUploadFlag";
	
	private static final String URL_insertFileUploadFlag = "insertFileUploadFlag";
	

	@Resource
	private HeartBeatFileUploadState heartBeatFileUploadState;

	public void checkFileChangeTableData() {
		String res = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_CheckFileUploadFlag),new HashMap<String, String>(), charset);
		if (res == null || "".equals(res)) {
			LogUtils.error("获取不到dev_dbsync_config表信息!!!");
			return;
		}
		if (!JSONUtils.mayBeJSON(res)) {
			LogUtils.error("获取不到dev_dbsync_config表信息!!!\n" + res + "\n");
			return;
		}

		ResultEntityF<List<SyncConfigEntity>> result = null;
		try {
			result = JsonUtils.fromJson(res, new TypeReference<ResultEntityF<List<SyncConfigEntity>>>() {
			});
		} catch (Exception e) {
			LogUtils.error("storeChangeTableData --->json 转化对象出错", e);
			return;
		}
		if (result != null && result.getState() == true) {
			List<SyncConfigEntity> tableChanges = result.getResult();
			if (tableChanges != null) {
				Map<String, Boolean> clearFlagMap = new HashMap<>();
				Map<String, ArrayList<String>> map = new HashMap<>();
				for (SyncConfigEntity tableChange : tableChanges) {
					String library_id = tableChange.getLib_id();
					String device_id = tableChange.getDevice_id();
					String syncType = tableChange.getSync_type();

					String key = library_id + "#@#" + device_id + "#@#" + syncType;
					// 写文件，并上传到ftp服务器
					if (map.containsKey(key)) {
						ArrayList<String> tables = map.get(key);
						tables.add(tableChange.getTable_name());
					} else {
						ArrayList<String> tables = new ArrayList<>();
						tables.add(tableChange.getTable_name());
						map.put(key, tables);
					}
				}
				// 定时准备下载文件
				System.out.println("定时准备下载文件... size=" + map.size());
				for (String key : map.keySet()) {
					String[] deviceInfo = key.split("#@#", 3);
					boolean isSuccess = createFileUploadFtpServer(deviceInfo[0], deviceInfo[1], map.get(key), deviceInfo[2]);
					if (isSuccess) {
						System.out.println("下载设备数据文件 libId:" + deviceInfo[0] + " deviceId:" + deviceInfo[1] + " Success!");
					} else {
						System.out.println("下载设备数据文件 libId:" + deviceInfo[0] + " deviceId:" + deviceInfo[1] + " Failed!");
					}
				}
			}
		}
	}

	private boolean createFileUploadFtpServer(String library_id, String device_id, ArrayList<String> tables, String fileType) {

		String username = null;
		String passwd = null;
		if (StringUtils.hasLength(library_id) && StringUtils.hasLength(device_id)) {
			Map<String, Object> m = new HashMap<>();
			m.put("device_id", device_id);
			Map<String, String> p = new HashMap<>();
			p.put("req", JsonUtils.toJson(m));
			/*
			 * String s =
			 * HttpClientUtil.doPost(requestURLListEntity.getRequestURL
			 * (URL_SELDEVICECODE), p, charset); if (JSONUtils.mayBeJSON(s)) {
			 * ResultEntity res = JsonUtils.fromJson(s, ResultEntity.class);
			 * if(res.getState() && res.getResult() != null){ username =
			 * device_id; passwd = res.getResult().toString().substring(0, 8); }
			 * }
			 */
			String ftpInfo = FtpUtils.createDirectory(username, passwd, "/download");
			if (StringUtils.hasLength(ftpInfo)) {
				for (int i = 0; i < tables.size(); i++) {
					String tableName = tables.get(i);
					String fileName = tableName + "_" + library_id + "_" + device_id + "_" + sdf.format(new Date()) + ".txt";
					if ("acs_protocols".equals(tableName)) {
						ftpInfo = ftpInfo + "AgentXml/";
						fileName = fileName.replace(".txt", ".xml");
					}

					if ("NcipTemplate".equals(tableName)) {
						ftpInfo = ftpInfo + "AgentXml/";
						fileName = fileName.replace(".txt", ".zip");
					}

					if ("zip".equals(fileType)) {
						fileName = fileName.replace(".txt", ".zip");
					}
					addHeartBeatData(tableName, fileName, device_id, "1", null, ftpInfo);
					Map<String, Object> map = new HashMap<>();
					if (map.containsKey("table"))
						map.remove("table");
					map.put("table", tableName);

					if (map.containsKey("path"))
						map.remove("path");
					map.put("path", ftpInfo + fileName);

					if (map.containsKey("fileType"))
						map.remove("fileType");
					map.put("fileType", fileType);

					if (map.containsKey("library_id"))
						map.remove("library_id");
					map.put("library_id", library_id);

					if (map.containsKey("device_id"))
						map.remove("device_id");
					map.put("device_id", device_id);

					/*
					 * requestEntity.setData(map); new
					 * DownloadOsbDataAndUploadToFtp(requestEntity).start();
					 */

					String json = JsonUtils.toJson(map);
					DownloadOsbSynEntity osb = JsonUtils.fromJson(json, DownloadOsbSynEntity.class);

					OsbTableCommand command = null;

					try {
						command = BeanFactoryHelper.getBean("down_" + tableName, OsbTableCommand.class);
					} catch (Exception e) {
						// command=BeanFactoryHelper.getBean("downLibraryInfoByFile",OsbTableCommand.class);
						// RuntimeException("down_tableName:down_"+tableName+" is not found!!!! ");
					}

					if (command != null) {
						command.execute(osb);
					}

					// 等待解决线程同步问题
					// Thread.sleep(1);
					// 获取当前时间，用于设置last_sync_time 这个字段
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			        String time= sdf.format(new Date());
			        
			        String req ="{\"last_sync_time\":\""+time+"\",\"lib_id\":\""+library_id+"\",\"device_id\":\""+device_id+"\",\"table_name\":\""+tableName+"\"}";
			        Map<String,String> paramMap = new HashMap<String,String>();
			        paramMap.put("req",req);
					// 用记录的时间赋值last_sync_time
			        HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_insertFileUploadFlag),paramMap,charset);
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
		return false;
	}

	public void addHeartBeatData(String tableName, String fileName, String device_id, String control, String tableField, String filePath) {
		FileUploadState fus = new FileUploadState();
		fus.setTableName(tableName);
		fus.setFileName(fileName);
		fus.setState(control);
		fus.setFilePath(filePath);
		// 加入
		if (heartBeatFileUploadState.containsKey(device_id)) {
			int i = 1;
			for (FileUploadState ety : heartBeatFileUploadState.get(device_id)) {
				 if(ety.getTableName().equals(fus.getTableName()) &&
						 ety.getFileName().equals(fus.getFileName())){
				//if (ety.getTableName().equals(fus.getTableName())) {
					// 移除之前的缓存
					heartBeatFileUploadState.get(device_id).remove(ety);
					// 增加上传完成
					heartBeatFileUploadState.get(device_id).add(fus);
					break;
				} else {
					if (i == heartBeatFileUploadState.get(device_id).size()) {
						heartBeatFileUploadState.get(device_id).add(fus);
					}
				}
				i++;
			}
		} else {
			// 若果不存在则新建一个list,并保存
			ConcurrentLinkedQueue<FileUploadState> orderQueue = new ConcurrentLinkedQueue<FileUploadState>();
			orderQueue.add(fus);
			heartBeatFileUploadState.put(device_id, orderQueue);
		}
	}
}
