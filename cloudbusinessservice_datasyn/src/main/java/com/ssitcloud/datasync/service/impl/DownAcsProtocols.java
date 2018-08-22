package com.ssitcloud.datasync.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import com.ssitcloud.common.netty.server.protocol.JsonUtil;
import com.ssitcloud.common.util.*;
import com.ssitcloud.node.entity.FileManagerEntity;

import net.sf.json.util.JSONUtils;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.datasync.entity.DeviceAcsModuleEntity;
import com.ssitcloud.datasync.entity.DownloadOsbSynEntity;
import com.ssitcloud.datasync.entity.DownloadOsbSynResultEntity;
import com.ssitcloud.datasync.entity.HeartBeatFileUploadState;
import com.ssitcloud.datasync.entity.ProtocolInfo;
import com.ssitcloud.datasync.service.OsbTableCommand;
import com.ssitcloud.devmgmt.entity.FileUploadState;
import com.ssitcloud.library.service.LibraryService;


@Component(value = "down_acs_protocols")
public class DownAcsProtocols extends BasicServiceImpl implements OsbTableCommand<DownloadOsbSynEntity> {

	public static final String URL_DOWNLOAD_META_SYNC = "downloadCfgSync";

	public static final String URL_SELDEVICECODE = "selectDeviceCode";

	public static final String URL_selLibraryByIdxOrId = "selLibraryByIdxOrId";

	public static final String URL_UPLOADFILETOFASTDFS="uploadFileToFastDfs";

	public static final String URL_UPLOADACSCONFIG="uploadAcsConfig";

	@Value("${OSB_DOWN_PATH}")
	public String OSB_DOWN_PATH;

	@Value("${OSB_NCIPDIR_PATH}")
	public String OSB_NCIPDIR_PATH;

	@Resource
	private HeartBeatFileUploadState heartBeatFileUploadState;
	@Resource
	private LibraryService libServcie;
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

			Map<String, String> libraryIdMap = new HashMap<>();
			libraryIdMap.put("json", "{\"lib_id\":\"" + lib_id + "\"}");

			if (StringUtils.hasLength(lib_id)) {
				lib_idx = libServcie.getLibraryIdxById(lib_id)+"";
			}

			// 查询所有bookitem
			Map<String, String> params = new HashMap<>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("" + LIB_ID + "", lib_id);
			map.put("" + DEV_ID + "", device_id);
			map.put("table", "device_acs_logininfo");

			map.put("library_id", lib_idx);
			map.put("lib_id", lib_id);

			params.put("lib_idx",lib_idx);
			params.put("device_id", device_id);
			params.put("req", JsonUtils.toJson(map));

			String retstr = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_DOWNLOAD_META_SYNC), params, charset);

			String ds = OSB_DOWN_PATH + File.separator + tableName+ File.separator +lib_id+ File.separator +device_id;
			String fps = ds + File.separator + fileName;
			File file = new File(ds);

			if (!file.exists() && !file.isDirectory()) {
				LogUtils.info("成功创建目录" + ds);
				file.mkdirs();
			}
			if (writeToFile(fps, retstr, fileType)) {

				//上传文件到fastdfs
				String url = requestURLListEntity.getRequestURL("uploadFileToFastDfs");

				Map<String,String> fileManagerMap = new HashMap<>();
				FileManagerEntity entity = new FileManagerEntity();
				entity.setLibrary_id(lib_id);
				entity.setDevice_id(device_id);
				entity.setLibrary_idx(lib_idx);
				fileManagerMap.put("req", JsonUtils.toJson(entity));

				String resultFileUpload = HttpClientUtil.postUploadWithParam(url,fileManagerMap, new File(fps));
				ResultEntity resultEntity = JsonUtils.fromJson(resultFileUpload, ResultEntity.class);
				if(!resultEntity.getState()) {
					resultEntity.setState(false);
					resultEntity.setMessage("文件上传失败");
					return resultEntity;
				}
				FileManagerEntity fileManagerEntity = JsonUtil.jsonToObject(JsonUtil.objectToJson(resultEntity.getResult()), FileManagerEntity.class);

				if (fileManagerEntity.getFile_path() == null) {
					addHeartBeatData(tableName, fileName, device_id, "0", fileManagerEntity.getFile_path());
					result.setState(false);
					result.setMessage("文件上传失败!!!");
					return result;
				} else {
					addHeartBeatData(tableName, fileName, device_id, "2", fileManagerEntity.getFile_path());
				}

				DownloadOsbSynResultEntity osbresult = new DownloadOsbSynResultEntity(device_id, lib_id, tableName, DownloadOsbSynResultEntity.ResponseResult_success, fileManagerEntity.getFile_path());
				result.setResult(osbresult);
				result.setState(true);
			} else {
				addHeartBeatData(tableName, fileName, device_id, "0", null);
				result.setState(false);
				return result;
			}

		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[0].getMethodName(), e);
		}
		return result;
	}

	public Boolean writeToFile(String path, String retstr, String fileType) {
		try {
			File file = new File(path);

			if (file.isFile() && file.exists()) {
				file.delete();
			}

			file.createNewFile();
			LogUtils.info("成功创建文件" + file.getName());
			FileOutputStream fop = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fop, "UTF-8");

			if (JSONUtils.mayBeJSON(retstr)) {
				ResultEntity resEntity = JsonUtils.fromJson(retstr, ResultEntity.class);

				List<ProtocolInfo> protocolInfos;
				DeviceAcsModuleEntity deviceAcsModule = new DeviceAcsModuleEntity();
				ProtocolInfo protocolInfo;
				String xmlStr = "";
				String xmlInnerStr = "";
				Integer type;
				String protocolType = "";
				String modifyTime = "";
				Object resultData = resEntity.getResult();
				List<DeviceAcsModuleEntity> deviceAcsModules = JsonUtils.convertMap(resultData, new TypeReference<List<DeviceAcsModuleEntity>>() {
				});
				xmlStr += "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
				xmlStr += "<ever>";
				Iterator it = deviceAcsModules.iterator();
				while (it.hasNext()) {
					deviceAcsModule = (DeviceAcsModuleEntity) it.next();
					type = deviceAcsModule.getProtocol_type();
					// 如果type为1,则为sip2类型
					if (type == 1) {
						protocolType = "sip2";
					}
					// 如果type为2,则为ncip类型
					if (type == 2) {
						protocolType = "ncip";
					}
					protocolInfos = deviceAcsModule.getProtocolInfos();
					Iterator iterator = protocolInfos.iterator();
					while (iterator.hasNext()) {
						protocolInfo = (ProtocolInfo) iterator.next();
						xmlInnerStr += "<protocol";
						xmlInnerStr += "  commandkey=\"" + protocolInfo.getProtocol_description() + "\"";
						xmlInnerStr += "  commandname=\"" + protocolInfo.getProtocol_show() + "\"";
						xmlInnerStr += "  description=\"" + protocolInfo.getProtocol_field_name() + "\"";
						xmlInnerStr += ">";
						xmlInnerStr += protocolInfo.getProtocol_reqrule();
						xmlInnerStr += protocolInfo.getProtocol_resprule();
						xmlInnerStr += "";
						xmlInnerStr += "</protocol>";
						modifyTime = String.valueOf(protocolInfo.getUpdatetime());
					}

					xmlStr += "<protocols";
					xmlStr += "  acsType=\"" + protocolType + "\"";
					xmlStr += "  acsName=\"" + deviceAcsModule.getAcs_service_name() + "\"";
					xmlStr += "  modifiedTime=\"" + modifyTime + "\"";
					if (type == 2) {
						xmlStr += "  connector=\"IP=" + deviceAcsModule.getLogin_ip() + "|Port=" + deviceAcsModule.getLogin_port() + "|Account=" + deviceAcsModule.getLogin_username() + "|Pwd="
								+ deviceAcsModule.getLogin_pwd() + "\"";
					}
					if (type == 1) {
						xmlStr += "  connector=\"IP=" + deviceAcsModule.getLogin_ip() + "|Port=" + deviceAcsModule.getLogin_port() + "|Account=" + deviceAcsModule.getLogin_username() + "|Pwd="
								+ deviceAcsModule.getLogin_pwd() + "|LoginType=" + deviceAcsModule.getLogin_type() + "|ReLogin=" + deviceAcsModule.getLogin_count() + "|NeedLogin="
								+ deviceAcsModule.getLogin_check() + "|Charset=" + deviceAcsModule.getLogin_code() + "\"";
					}
					xmlStr += ">";
					xmlStr += xmlInnerStr;
					xmlStr += "</protocols>";
					xmlInnerStr = "";
				}
				xmlStr += "</ever>";
				osw.write(xmlStr);
			}
			osw.flush();
			osw.close();

			return true;
		} catch (Exception e) {
			LogUtils.error("过程 DownAgentXml 操作文件出错");
			return false;
		}
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
