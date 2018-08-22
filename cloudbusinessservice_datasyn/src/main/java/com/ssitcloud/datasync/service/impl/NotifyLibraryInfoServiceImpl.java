package com.ssitcloud.datasync.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.FtpUtils;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.utils.HttpClientUtil;
import com.ssitcloud.datasync.entity.DownloadOsbSynResultEntity;
import com.ssitcloud.datasync.entity.HeartBeatFileUploadState;
import com.ssitcloud.datasync.service.NotifyLibraryInfoService;
import com.ssitcloud.devmgmt.entity.FileUploadState;
@Service
public class NotifyLibraryInfoServiceImpl extends BasicServiceImpl implements NotifyLibraryInfoService{

	public static final String URL_SELDEVICECODE = "selectDeviceCode";
	
	@Resource
	private HeartBeatFileUploadState heartBeatFileUploadState;
	
	@Override
	public ResultEntity notifyLibraryInfo(String req) {
		ResultEntity result = new ResultEntity();
		Map<String, String> map = JsonUtils.fromJson(req,HashMap.class);
		String device_id = map.get("device_id");
		String fileType = map.get("fileType");
		String fileName = map.get("fileName");
		String filePath = map.get("filePath");
		String fps = map.get("fps");
		String tableName = map.get("tableName");
		String tableField = map.get("tableField");
		String fullPath = map.get("fullPath");
		String lib_id = map.get("lib_id");
		String ip = null;
		Integer port = null;
		String username = null;
		String passwd = null;
		
		if(StringUtils.isEmpty(fps)){
			addHeartBeatData(tableName, fileName, device_id, "0", tableField, fullPath);
			result.setState(false);
			result.setMessage("文件产生或传输失败!!!");
			return result;
		}
		
		Map<String, Object> m = new HashMap<>();
		m.put("device_id", device_id);
		Map<String, String> p = new HashMap<>();
		p.put("req", JsonUtils.toJson(m));
		String s = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_SELDEVICECODE), p, charset);
		
		/*if (JSONUtils.mayBeJSON(s)) {
			ResultEntity r =JsonUtils.fromJson(s, ResultEntity.class); 
			if(r.getState() && r.getResult() != null){
				username = device_id; 
				passwd = r.getResult().toString().substring(0, 8); 
			} 
		}*/
		 
		if ("zip".equals(fileType)) {
			fileName = fileName.replace(".txt", ".zip");
			fps = fps.replace(".txt", ".zip");
		}
		// 上传文件FTP服务
		String ftpPath = FtpUtils.uploadOsbDataToFTP(ip, port, username, passwd, filePath, new File(fps));
		if (ftpPath == null) {
			addHeartBeatData(tableName, fileName, device_id, "0", tableField, fullPath);
			result.setState(false);
			result.setMessage("文件上传失败!!!");
			return result;
		}
		DownloadOsbSynResultEntity osbresult = new DownloadOsbSynResultEntity(device_id, lib_id, tableName, DownloadOsbSynResultEntity.ResponseResult_success, ftpPath);
		result.setResult(osbresult);
		result.setState(true);

		addHeartBeatData(tableName, fileName, device_id, "2", tableField, fullPath);
		return result;
	}
	
	
	public void addHeartBeatData(String tableName, String fileName, String device_id, String control, String tableField, String filePath) {
		FileUploadState fus = new FileUploadState();
		fus.setTableName(tableName);
		fus.setFileName(fileName);
		fus.setState(control);
		fus.setTableField(tableField);
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
					} else {
						continue;
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
