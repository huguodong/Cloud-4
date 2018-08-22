package com.ssitcloud.datasync.service.impl;

import io.netty.channel.ChannelHandlerContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.CloudSyncResponse;
import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.netty.server.RpcService;
import com.ssitcloud.common.netty.server.protocol.JsonUtil;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.system.BeanFactoryHelper;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.FtpUtils;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.StringUtil;
import com.ssitcloud.datasync.entity.DownloadOsbSynEntity;
import com.ssitcloud.datasync.entity.HeartBeatFileUploadState;
import com.ssitcloud.datasync.service.DataSynHandler;
import com.ssitcloud.datasync.service.OsbTableCommand;
import com.ssitcloud.devmgmt.entity.FileUploadState;

//@Component(value = "askDownloadOsbSync")
@RpcService(name="askDownloadOsbSync")
public class AskDownloadOsbSync extends BasicServiceImpl implements DataSynHandler {

	@Resource
	private HeartBeatFileUploadState heartBeatFileUploadState;

	public static final String URL_SELDEVICECODE = "selectDeviceCode";

	public static final String URL_UPLOADFILETOFASTDFS="uploadFileToFastDfs";

	public static final String URL_QUERYFILEMANAGERBYENTITY="queryFileManagerByEntity";

	@Override
	public CloudSyncResponse handle(ChannelHandlerContext handlerContext, CloudSyncRequest cloudSyncRequest) {
		CloudSyncResponse cloudSyncResponse = new CloudSyncResponse();
		cloudSyncResponse.setMsg_name("askDownloadOsbSync");
		cloudSyncResponse.setMsg_type("response");
		cloudSyncResponse.setRequestId(cloudSyncRequest.getRequestId());
		cloudSyncResponse.setStatus("1");
		cloudSyncResponse.setResult("askDownloadOsbSync is fail");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");

		List<Map<String,String>> datas = cloudSyncRequest.getData();
		Map<String,String> data = datas.get(0);
		String library_id=data.get("library_id");
		String device_id=data.get("device_id");
		String tableNames=data.get("table");
		String fileType = data.get("fileType");

		if(StringUtils.isEmpty(device_id)){

			cloudSyncResponse.setResult("请求参数格式不正确,必须参数包括library_id、device_id");
			return cloudSyncResponse;
		}

		String[] split = tableNames.split(",");
		List<String> tables =Arrays.asList(split);
		if (tables == null || tables.size() == 0) {
			tables = new ArrayList<>();
			tables.add("reader");
			tables.add("bookitem");
		}

		Map<String,String> map = new HashMap();
		map.put("library_id",library_id);
		map.put("device_id",device_id);

		if (StringUtils.hasLength(library_id) && StringUtils.hasLength(device_id)) {

			for (int i = 0; i < tables.size(); i++) {
					String tableName = tables.get(i);
					String fileName = tableName + "_" + library_id + "_" + device_id + "_" + sdf.format(new Date()) + ".txt";

					if ("zip".equals(fileType)) {
						fileName = fileName.replace(".txt", ".zip");
					}
					if (map.containsKey("table"))
						map.remove("table");
					map.put("table", tableName);

					if (map.containsKey("path"))
						map.remove("path");
					map.put("path", fileName );

					if (map.containsKey("fileType"))
						map.remove("fileType");
					map.put("fileType", fileType);

					String json = JsonUtils.toJson(map);
					DownloadOsbSynEntity osb = JsonUtils.fromJson(json, DownloadOsbSynEntity.class);

					OsbTableCommand command = null;

					try {
						command = BeanFactoryHelper.getBean("down_" + tableName, OsbTableCommand.class);
					} catch (Exception e) {
						command = BeanFactoryHelper.getBean("downLibraryInfoByFile", OsbTableCommand.class);
					}

					if (command != null) {
						ResultEntity execute = command.execute(osb);
						if(execute.getState()) {
							//设置返回结果集
							cloudSyncResponse.setStatus("0");
							cloudSyncResponse.setResult("askDownloadOsbSync is success");
							Map<String,String> responseDataMap = JsonUtil.jsonToObjectHashMap(JsonUtil.objectToJson(execute.getResult()), String.class, String.class);
							List<Map<String,String>> resultDataList = new ArrayList<>();
							resultDataList.add(responseDataMap);
							cloudSyncResponse.setData(resultDataList);
						}
					}
				}

		} else {
			cloudSyncResponse.setStatus("1");
			cloudSyncResponse.setResult("请求参数格式不正确,必须参数包括library_id、device_id");
		}
		return cloudSyncResponse;
	}

	public RespEntity execute(RequestEntity requestEntity) {
		RespEntity resp = new RespEntity(requestEntity);
		ResultEntity resEntity = new ResultEntity();
		Map<String, Object> map = requestEntity.getData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");

		try {
			String library_id = (String) map.get("library_id");
			String device_id = (String) map.get("device_id");
			String fileType = (String) map.get("fileType");
			ArrayList<String> tables = (ArrayList<String>) map.get("table");

			if (StringUtil.isEmpty(fileType)) {
				fileType = "zip";
			}

			if (tables == null || tables.size() == 0) {
				tables = new ArrayList<>();
				tables.add("reader");
				tables.add("bookitem");
			}

			String username = null;
			String passwd = null;

			if (StringUtils.hasLength(library_id) && StringUtils.hasLength(device_id)) {
				//获得ftp服务器的下载路径
				String ftpInfo = FtpUtils.createDirectory(username, passwd, "/download");
				if (StringUtils.hasLength(ftpInfo)) {
					resEntity.setState(true);
					resEntity.setResult(ftpInfo);
					for (int i = 0; i < tables.size(); i++) {
						String tableName = tables.get(i);
						String fileName = tableName + "_" + library_id + "_" + device_id + "_" + sdf.format(new Date()) + ".txt";
						if ("acs_protocols".equals(tableName)) {
							ftpInfo = ftpInfo + "AgentXml/";
							fileName = fileName.replace(".txt", ".xml");
						}
						if ("zip".equals(fileType)) {
							fileName = fileName.replace(".txt", ".zip");
						}
						addHeartBeatData(tableName, fileName, device_id, "1", null, ftpInfo);
						if (map.containsKey("table"))
							map.remove("table");
						map.put("table", tableName);

						if (map.containsKey("path"))
							map.remove("path");
						map.put("path", ftpInfo + fileName);

						if (map.containsKey("fileType"))
							map.remove("fileType");
						map.put("fileType", fileType);

						String json = JsonUtils.toJson(map);
						DownloadOsbSynEntity osb = JsonUtils.fromJson(json, DownloadOsbSynEntity.class);

						OsbTableCommand command = null;

						try {
							command = BeanFactoryHelper.getBean("down_" + tableName, OsbTableCommand.class);
						} catch (Exception e) {
							command = BeanFactoryHelper.getBean("downLibraryInfoByFile", OsbTableCommand.class);
						}

						if (command != null) {
							command.execute(osb);
						}

					}
				} else {
					resEntity.setState(false);
					resEntity.setMessage("访问ftp服务出错");
				}
			} else {
				resEntity.setState(false);
				resEntity.setMessage("请求参数格式不正确,必须参数包括library_id、device_id");
			}

			resp.setData(resEntity);
		} catch (Exception e) {
			ExceptionHelper.afterException(resp, Thread.currentThread(), e);
		}
		return resp;
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
