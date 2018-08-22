package com.ssitcloud.datasync.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.datasync.entity.DownloadOsbSynEntity;
import com.ssitcloud.datasync.entity.HeartBeatFileUploadState;
import com.ssitcloud.datasync.service.OsbTableCommand;
import com.ssitcloud.library.service.LibraryService;


@Component(value = "downLibraryInfoByFile")
public class DownLibraryInfoByFile extends BasicServiceImpl implements OsbTableCommand<DownloadOsbSynEntity> {

	public static final String URL_QUERYALLLIBRARYINFO = "queryAllLibraryInfo";

	public static final String URL_selLibraryByIdxOrId = "selLibraryByIdxOrId";

	
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
			String path = downosbSyn.getPath();
			String fileType = downosbSyn.getFileType();
			// String tableField = downosbSyn.getTableField();
			String lib_idx = "";

			String fullPath = path.substring(0, path.lastIndexOf("/") + 1);
			path = path.substring(path.indexOf("://") + 3, path.length());
			String filePath = path.substring(path.indexOf("/"), path.lastIndexOf("/"));
			String fileName = path.substring(path.lastIndexOf("/") + 1, path.length());
			if ("zip".equals(fileType)) { // 压缩格式 先把数据下载到txt之后压缩成zip上传
				fileName = fileName.replace(".zip", ".txt");
			}

			Map<String, String> libraryIdMap = new HashMap<>();
			libraryIdMap.put("json", "{\"lib_id\":\"" + lib_id + "\"}");
			if (StringUtils.hasLength(lib_id)) {
				lib_idx = libServcie.getLibraryIdxById(lib_id)+"";
				/*if(librarycache != null){
					Element e = librarycache.get(lib_id);
					if(e!=null){
						lib_idx = (String) e.getObjectValue();
					}
				}*/
			}

			// 查询所有bookitem
			Map<String, Object> map = new HashMap<>();
			map.put("library_idx", lib_idx);
			map.put("library_id", lib_id);
			map.put("device_id", device_id);
			map.put("table", tableName);
			map.put("fileName", fileName);
			map.put("fullPath", fullPath);
			map.put("fileType", fileType);
			map.put("filePath", filePath);
			
			Map<String, String> params = new HashMap<>();
			params.put("req", JsonUtils.toJson(map));

			String retstr = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_QUERYALLLIBRARYINFO), params, charset);
			if(JSONUtils.mayBeJSON(retstr)){
	   		 	result=JsonUtils.fromJson(retstr, ResultEntity.class);
			}

		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[0].getMethodName(), e);
		}
		return result;
	}
}
