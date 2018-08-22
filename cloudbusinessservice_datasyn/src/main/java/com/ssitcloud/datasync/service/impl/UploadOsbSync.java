package com.ssitcloud.datasync.service.impl;

import io.netty.channel.ChannelHandlerContext;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.CloudSyncResponse;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.netty.server.RpcService;
import com.ssitcloud.common.netty.server.protocol.JsonUtil;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.ZipUtils;
import com.ssitcloud.datasync.entity.UploadOsbSynResultEntity;
import com.ssitcloud.datasync.service.DataSynHandler;
import com.ssitcloud.library.service.LibraryService;
import com.ssitcloud.node.entity.FileManagerEntity;

//@Component(value="uploadOsbSync")
@RpcService(name="uploadOsbSync")
public class UploadOsbSync extends BasicServiceImpl implements DataSynHandler {

	public static final String URL_SELDEVICECODE = "selectDeviceCode";
	
	public static final String URL_selDeviceIdx = "selDeviceIdx";
	
	public static final String URL_selLibraryByIdxOrId = "selLibraryByIdxOrId";

	public static final String URL_UPLOADL_IBRARY_INFO = "uploadLibraryInfo";
	
	public static final String URL_NOTIFYDOUPLOADLIBRARYINFOFILE = "notifyDoUploadLibraryInfoFile";

	public static final String URL_UPLOADFILETOFASTDFS="uploadFileToFastDfs";

	public static final String URL_QUERYFILEMANAGERBYENTITY="queryFileManagerByEntity";
	
	@Value("${OSB_UP_PATH}")
	public String OSB_UP_PATH;

	@Resource
	private LibraryService libServcie;

	@Override
	public CloudSyncResponse handle(ChannelHandlerContext handlerContext, CloudSyncRequest cloudSyncRequest) {
		CloudSyncResponse cloudSyncResponse = new CloudSyncResponse();

		cloudSyncResponse.setMsg_name("downloadOsbSync");
		cloudSyncResponse.setMsg_type("response");
		cloudSyncResponse.setRequestId(cloudSyncRequest.getRequestId());
		cloudSyncResponse.setStatus("1");
		cloudSyncResponse.setResult("uploadOsbSync is fail");

		List<Map<String,String>> datas = cloudSyncRequest.getData();
		Map<String,String> data = datas.get(0);
		String library_id=data.get("library_id");
		String device_id=data.get("device_id");
		String tableName=data.get("table");
		String tableField = data.get("tableField");
		String path=data.get("path");

		if(StringUtils.isEmpty(library_id) || StringUtils.isEmpty(device_id) || StringUtils.isEmpty(tableName) || StringUtils.isEmpty(path)){
			cloudSyncResponse.setResult("请求参数格式不正确,必须参数包括library_id、device_id、table、path");
			return cloudSyncResponse;
		}
		//从fastdfs获得文件
		Map<String,String> fileManagerMap = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssS");

		String localFileName = tableName+"_"+library_id+"_"+device_id+"_"+sdf.format(new Date())+".txt";

		String localPath = OSB_UP_PATH+ File.separator +tableName+"/upload";

		File f=new File(localPath);
		if(!f.exists()){
			if(!f.mkdirs()){
				cloudSyncResponse.setResult("创建本地路径失败");
				return cloudSyncResponse;
			}
		}
		String localFilePath =  localPath + File.separator + localFileName;

		Map<String, String> paramsfileManager = new HashMap<>();
		fileManagerMap.put("file_path",path);
		fileManagerMap.put("file_localPath",localFilePath);
		paramsfileManager.put("req",JsonUtils.toJson(fileManagerMap));
		String resultFileDown = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_QUERYFILEMANAGERBYENTITY), paramsfileManager, charset);
		ResultEntity res = JsonUtils.fromJson(resultFileDown, ResultEntity.class);
		if(!res.getState()) {
			cloudSyncResponse.setResult("文件下载失败");
			return cloudSyncResponse;
		}

		FileManagerEntity fileManagerEntity = JsonUtil.jsonToObject(JsonUtil.objectToJson(res.getResult()), FileManagerEntity.class);
		localFilePath = fileManagerEntity.getFile_localPath();
		String fileType = localFilePath.substring(localFilePath.lastIndexOf(".") + 1);
		localFileName = localFilePath.substring(localFilePath.lastIndexOf(File.separator) + 1);
		if("zip".equals(fileType)){
			//解压缩
            try {
                ZipUtils.zipOut(localPath,localFileName, localFilePath);
            } catch (Exception e) {
                cloudSyncResponse.setStatus("0300");
                cloudSyncResponse.setResult("文件解压异常");
                return cloudSyncResponse;
            }
        }

		String lib_idx = "";
		Map<String,String> libraryIdMap=new HashMap<>();
		libraryIdMap.put("json", "{\"lib_id\":\""+library_id+"\"}");
		if(StringUtils.hasLength(library_id)){
			lib_idx = libServcie.getLibraryIdxById(library_id)+"";
		}

		String device_idx = "";
		Map<String,String> deviceIdMap=new HashMap<>();
		deviceIdMap.put("req", "{\"device_id\":\""+device_id+"\"}");
		if(StringUtils.hasLength(device_id)){
			String resDevice=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_selDeviceIdx), deviceIdMap, charset);
			if (JSONUtils.mayBeJSON(resDevice)) {
				ResultEntity resDeviceIdx = JsonUtils.fromJson(resDevice, ResultEntity.class);
				if(resDeviceIdx.getState() && resDeviceIdx.getResult() != null){
					device_idx = resDeviceIdx.getResult().toString();
				}
			}
		}

		File file = new File(localFilePath);

		if(file.exists() && file.isFile()){
			String url = requestURLListEntity.getRequestURL("uploadLibraryInfoFile");
			String resFile= HttpClientUtil.postUpload(url, file,library_id,device_id);
			if(JSONUtils.mayBeJSON(resFile)){
				ResultEntity rst = JsonUtils.fromJson(resFile, ResultEntity.class);
				if(rst.getState()){
					//通知db开始处理数据
					String dbFilePath = String.valueOf(rst.getResult());
					Map<String,String> map1 = new HashMap<>();
					map1.put("filePath", dbFilePath);
					map1.put("tableField", tableField);
					map1.put("library_id", library_id);
					map1.put("lib_idx", lib_idx);
					map1.put("device_idx", device_idx);
					map1.put("tableName", tableName);
					Map<String,String> params = new HashMap<>();
					params.put("req", JsonUtils.toJson(map1));
					String restStr = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_NOTIFYDOUPLOADLIBRARYINFOFILE), params, charset);
					if (JSONUtils.mayBeJSON(restStr)) {
						ResultEntity rt = JsonUtils.fromJson(restStr, ResultEntity.class);
						if(rt.getState()){
							cloudSyncResponse = returnResult(true, device_id, library_id, tableName, cloudSyncResponse);
						}
					}
				}
			}
		}else{
			cloudSyncResponse.setResult("指定文件不存在");

		}

		return cloudSyncResponse;
	}

	private CloudSyncResponse returnResult(Boolean b ,String device_id,String lib_id,String tableName,CloudSyncResponse cloudSyncResponse){
		List<Map<String,String>> responseData = new ArrayList<>();
		Map<String,String> map = new HashMap<>();
		UploadOsbSynResultEntity osbresult = null;
		if(b){
			osbresult = new UploadOsbSynResultEntity(device_id, lib_id, tableName, UploadOsbSynResultEntity.ResponseResult_success, "");

			 map = JsonUtil.jsonToObjectHashMap(JsonUtil.objectToJson(osbresult), String.class, String.class);

			cloudSyncResponse.setResult("uploadOsbSync is success");
			cloudSyncResponse.setStatus("0");
		}else{
			osbresult = new UploadOsbSynResultEntity(device_id, lib_id, tableName, UploadOsbSynResultEntity.ResponseResult_fail, "");
			map = JsonUtil.jsonToObjectHashMap(JsonUtil.objectToJson(osbresult), String.class, String.class);
		}
		responseData.add(map);
		cloudSyncResponse.setData(responseData);
		return cloudSyncResponse;
	}

}
