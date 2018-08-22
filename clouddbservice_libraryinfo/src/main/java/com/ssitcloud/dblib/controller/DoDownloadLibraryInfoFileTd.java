package com.ssitcloud.dblib.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONArray;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.factory.BeanFactoryHelper;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.common.utils.LogUtils;
import com.ssitcloud.dblib.common.utils.ZipUtils;
import com.ssitcloud.dblib.entity.DownloadLibraryInfoEntity;
import com.ssitcloud.dblib.service.LibraryInfoService;
import com.ssitcloud.dblib.service.TransferFileService;

public class DoDownloadLibraryInfoFileTd implements Runnable{
	
	private TransferFileService transferFileService;
	
	private Map<String, String> map = new HashMap<>();
	
	private String OSB_DOWN_PATH;
	
	public DoDownloadLibraryInfoFileTd(TransferFileService transferFileService,Map<String, String> map,String OSB_DOWN_PATH) {
		this.transferFileService = transferFileService;
		this.map = map;
		this.OSB_DOWN_PATH = OSB_DOWN_PATH;
	}
	
	@Override
	public void run() {
		
		try {
			String lib_idx = map.get("library_idx");
			String lib_id = map.get("library_id");
			String device_id = map.get("device_id");
			String tableName = map.get("table");
			String fileName = map.get("fileName");
			String fullPath = map.get("fullPath");
			String fileType = map.get("fileType");
			String filePath = map.get("filePath");
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println("DoDownloadLibraryInfoFileTd 开始处理文件:"+filePath + "startTime:"+formatter.format(new Date()));
			
			DownloadLibraryInfoEntity downloadLibraryInfoEntity = new DownloadLibraryInfoEntity();
			downloadLibraryInfoEntity.setLibrary_idx(lib_idx);
			downloadLibraryInfoEntity.setLibrary_id(lib_id);
			downloadLibraryInfoEntity.setDevice_id(device_id);
			downloadLibraryInfoEntity.setTable(tableName);
			
			if(tableName!=null){
				LibraryInfoService libraryInfoService = null;
				try {
					libraryInfoService = BeanFactoryHelper.getBean("queryAllTableData_"+tableName, LibraryInfoService.class);
				} catch (NoSuchBeanDefinitionException e) {
					//throw new RuntimeException(tableName +"不符合参数设置[reader/bookitem].");
					libraryInfoService = BeanFactoryHelper.getBean("queryAllTableData_common", LibraryInfoService.class);
				}
				if(libraryInfoService!=null){
					ResultEntity res = libraryInfoService.execute(downloadLibraryInfoEntity);
					//生成文件
					JSONArray list = null;

					if (res.getState() == true && res.getResult() != null) {
						list = JSONArray.fromObject(res.getResult());
					}
					String tableField = "";
					if (list == null || list.size() == 0) {
						//通知结果 失败
						Map<String,String> map_ = new HashMap<>();
						map_.put("device_id", device_id);
						map_.put("fileType", fileType);
						map_.put("fileName", fileName);
						map_.put("filePath", filePath);
						map_.put("fps", null);
						map_.put("tableName", tableName);
						map_.put("tableField", tableField);
						map_.put("fullPath", fullPath);
						map_.put("lib_id", lib_id);
						String req = JsonUtils.toJson(map_);
						
						ResultEntity rst = transferFileService.notifyLibraryInfo(req);
						return;
					}

					// 获取字段
					LinkedHashMap<String, Object> hashMap = JsonUtils.fromJson(JsonUtils.toJson(list.get(0)), new TypeReference<LinkedHashMap<String, Object>>() {
					});
					for (String key : hashMap.keySet()) {
						tableField = tableField + key + ",";
					}
					tableField = tableField.substring(0, tableField.length() - 1);

					String ds = OSB_DOWN_PATH + "/" + tableName + "/download";
					String fps = ds + "/" + fileName;
					File file = new File(ds);

					if (!file.exists() && !file.isDirectory()) {
						LogUtils.info("成功创建目录" + ds);
						file.mkdirs();
					}

					if (writeToFile(fps, list, fileType)) {
						Map<String, Object> m = new HashMap<>();
						m.put("device_id", device_id);
						Map<String, String> p = new HashMap<>();
						p.put("req", JsonUtils.toJson(m));
						//String s = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_SELDEVICECODE), p, charset);
						
						if ("zip".equals(fileType)) {
							fileName = fileName.replace(".txt", ".zip");
							fps = fps.replace(".txt", ".zip");
						}
						
						Map<String,String> map = new HashMap<>();
						map.put("filePath", fps);
						map.put("libId", lib_id);
						map.put("deviceId", device_id);
						//上传到business层
						ResultEntity rst = transferFileService.transfer(map);
						if(rst != null && rst.getState()){
						
							fps = String.valueOf(rst.getResult());
			   		 		fps = fps.replace("\\", "/\\");
			   		 	
							Map<String,String> map_ = new HashMap<>();
							map_.put("device_id", device_id);
							map_.put("fileType", fileType);
							map_.put("fileName", fileName);
							map_.put("filePath", filePath);
							map_.put("fps", fps);
							map_.put("tableName", tableName);
							map_.put("tableField", tableField);
							map_.put("fullPath", fullPath);
							map_.put("lib_id", lib_id);
							String req = JsonUtils.toJson(map_);
							ResultEntity rt = transferFileService.notifyLibraryInfo(req);
						}
					}else{
						Map<String,String> map_ = new HashMap<>();
						map_.put("device_id", device_id);
						map_.put("fileType", fileType);
						map_.put("fileName", fileName);
						map_.put("filePath", filePath);
						map_.put("fps", null);
						map_.put("tableName", tableName);
						map_.put("tableField", tableField);
						map_.put("fullPath", fullPath);
						map_.put("lib_id", lib_id);
						String req = JsonUtils.toJson(map_);
						ResultEntity rst = transferFileService.notifyLibraryInfo(req);
					}
				}
			}
			System.out.println("DoDownloadLibraryInfoFileTd 结束处理文件:"+filePath + "endTime:"+formatter.format(new Date()));
		} catch (Exception e) {
			System.out.println("云端数据生成下载文件过程出错"+e.toString());
		}
	}
	
	public Boolean writeToFile(String path, JSONArray list, String fileType) {
		try {
			File file = new File(path);

			if (file.isFile() && file.exists()) {
				file.delete();
			}

			file.createNewFile();
			LogUtils.info("成功创建文件" + file.getName());
			FileOutputStream fop = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fop, "UTF-8");
			if (list != null && list.size() > 0) {
				for (Object obj : list) {
					LinkedHashMap<String, Object> hashMap = JsonUtils.fromJson(JsonUtils.toJson(obj), new TypeReference<LinkedHashMap<String, Object>>() {
					});
					StringBuffer sb = new StringBuffer();
					int k = 1;
					for (String key : hashMap.keySet()) {
						Object param = hashMap.get(key);
						if (param == null) {// 如果为空则替换成"null"
							param = "null";
						}
						String value = String.valueOf(param);
						sb.append(value);
						if (k < hashMap.size()) {
							sb.append("#@#");
						} else {
							sb.append("\r\n");
						}
						k++;
					}
					osw.write(sb.toString());
				}
			}
			osw.flush();
			osw.close();
			if ("zip".equals(fileType)) {
				path = path.replace(".txt", ".zip");
				// 压缩
				ZipUtils.zipIn(path, file);
			}

			return true;
		} catch (Exception e) {
			LogUtils.error("过程 downLibraryInfoByFile 操作文件出错");
			return false;
		}
	}
}
