package com.ssitcloud.datasync.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import com.ssitcloud.common.netty.server.protocol.JsonUtil;
import com.ssitcloud.common.util.*;
import com.ssitcloud.node.entity.FileManagerEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.datasync.entity.DownloadOsbSynEntity;
import com.ssitcloud.datasync.entity.DownloadOsbSynResultEntity;
import com.ssitcloud.datasync.entity.HeartBeatFileUploadState;
import com.ssitcloud.datasync.service.OsbTableCommand;
import com.ssitcloud.devmgmt.entity.FileUploadState;

@Component(value = "down_NcipTemplate")
public class DownNcipTemplate extends BasicServiceImpl implements OsbTableCommand<DownloadOsbSynEntity> {

	public static final String URL_DOWNLOAD_META_SYNC = "downloadCfgSync";

	public static final String URL_SELDEVICECODE = "selectDeviceCode";

	public static final String URL_selLibraryByIdxOrId = "selLibraryByIdxOrId";

	@Value("${OSB_DOWN_PATH}")
	public String OSB_DOWN_PATH;

	@Value("${OSB_NCIPDIR_PATH}")
	public String OSB_NCIPDIR_PATH;

	@Resource
	private HeartBeatFileUploadState heartBeatFileUploadState;

	@Override
	public ResultEntity execute(DownloadOsbSynEntity downosbSyn) {

		ResultEntity result = new ResultEntity();
		try {
			String lib_id = downosbSyn.getLibrary_id();
			String device_id = downosbSyn.getDevice_id();
			String tableName = downosbSyn.getTable();
			String fileType = downosbSyn.getFileType();
			String fileName = downosbSyn.getPath();
			String lib_idx = "";

			String localPath=System.getProperty("java.io.tmpdir");
			String ds = localPath + File.separator + lib_id+ File.separator +device_id;
			String fps = ds + File.separator + fileName;
			File file = new File(ds);

			if (!file.exists() && !file.isDirectory()) {
				LogUtils.info("成功创建目录" + ds);
				file.mkdirs();
			}

			Map<String,String> fileManagerMap = new HashMap<>();
			FileManagerEntity entity = new FileManagerEntity();
			entity.setLibrary_id(lib_id);
			entity.setDevice_id(device_id);
			entity.setLibrary_idx(lib_idx);
			fileManagerMap.put("req", JsonUtils.toJson(entity));

			String url = requestURLListEntity.getRequestURL("uploadFileToFastDfs");
			String resultFileUploadFps = HttpClientUtil.postUploadWithParam(url, fileManagerMap,new File(fps));
			ResultEntity resultEntityFps = JsonUtils.fromJson(resultFileUploadFps, ResultEntity.class);
			if(!resultEntityFps.getState()) {
				resultEntityFps.setState(false);
				resultEntityFps.setMessage("文件上传失败");
				return resultEntityFps;
			}
			FileManagerEntity fileManagerEntity = JsonUtil.jsonToObject(JsonUtil.objectToJson(resultEntityFps.getResult()), FileManagerEntity.class);

			// 固定文件上传到ftp
			File otherFile = new File(ds);
			if (!otherFile.exists() && !otherFile.isDirectory()) {
				LogUtils.info("成功创建目录" + ds);
				otherFile.mkdirs();
			}
			File newFile,oldFile;
			if (otherFile.isDirectory()) {
				String[] filelist = otherFile.list();

				if (filelist.length == 1) {
					oldFile = new File(ds + File.separator + filelist[0]);
					newFile = new File(ds + File.separator + fileName);
					//上传文件到fastdfs
					if (oldFile.renameTo(newFile)) {
						String resultFileUpload = HttpClientUtil.postUploadWithParam(url,fileManagerMap,newFile);

						if (!newFile.isDirectory()) {
							newFile.delete();
						}
						ResultEntity resultEntity = JsonUtils.fromJson(resultFileUpload, ResultEntity.class);
						if(!resultEntity.getState()) {
							resultEntity.setState(false);
							resultEntity.setMessage("文件上传失败");
							return resultEntity;
						}
						FileManagerEntity fileManagerEntityOther = JsonUtil.jsonToObject(JsonUtil.objectToJson(resultEntity.getResult()), FileManagerEntity.class);

						if (fileManagerEntityOther.getFile_path() == null) {
							addHeartBeatData(tableName, fileName, device_id, "0", fileManagerEntity.getFile_path());
							result.setState(false);
							result.setMessage("文件上传失败!!!");
							return result;
						} else {
							addHeartBeatData(tableName, fileName, device_id, "2", fileManagerEntity.getFile_path());
						}
					} else {
						addHeartBeatData(tableName, fileName, device_id, "0", null);
						result.setState(false);
						return result;
					}
				}
			}

			DownloadOsbSynResultEntity osbresult = new DownloadOsbSynResultEntity(device_id, lib_id, tableName, DownloadOsbSynResultEntity.ResponseResult_success, fileManagerEntity.getFile_path());
			result.setResult(osbresult);
			result.setState(true);

		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[0].getMethodName(), e);
		}
		return result;
	}

	public void addHeartBeatData(String tableName, String fileName, String device_id, String control, String filePath) {
		FileUploadState fus = new FileUploadState();
		fus.setTableName(tableName);
		fus.setFileName(fileName);
		fus.setState(control);
		fus.setFilePath(filePath);
		// 加入
		if (heartBeatFileUploadState.containsKey(device_id)) {
			int i = 1;
			for (FileUploadState ety : heartBeatFileUploadState.get(device_id)) { // 判断队列中是否有缓存中设备id为SCH_M的所有缓存FileUploadState
				// if(ety.getTableName().equals(fus.getTableName()) &&
				// //其属性包括了TableName、FilePath
				// ety.getFileName().equals(fus.getFileName())){
				if (ety.getTableName().equals(fus.getTableName())) { // 如果新添加到缓存中的FileUploadState中的表名TableName与遍历
					// 移除之前的缓存 //出来的TableName一样，则先移除之前存在的FileUploadState
					heartBeatFileUploadState.get(device_id).remove(ety); // 然后将新的FileUploadState中文件路径添加到缓存中
					fus.setFilePath(ety.getFilePath());
					// 增加上传完成
					heartBeatFileUploadState.get(device_id).add(fus);
					break;
				} else { // 如果新添加到缓存中的FileUploadState中的表明TableName与遍历
					if (i == heartBeatFileUploadState.get(device_id).size()) { // 出来的TableName不一样，而且是与整个设备id为SCH_M的所有FileUploadState
						heartBeatFileUploadState.get(device_id).add(fus); // 则将它加入到缓存中
					} else {
						i++;
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
