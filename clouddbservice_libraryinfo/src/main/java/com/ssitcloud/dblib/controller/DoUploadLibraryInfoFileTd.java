package com.ssitcloud.dblib.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.factory.BeanFactoryHelper;
import com.ssitcloud.dblib.entity.UploadLibraryInfoEntity;
import com.ssitcloud.dblib.service.LibraryInfoService;

public class DoUploadLibraryInfoFileTd implements Runnable{
	
	private Map<String, String> map = new HashMap<>();
	
	public DoUploadLibraryInfoFileTd(Map<String, String> map) {
		this.map = map;
	}

	@Override
	public void run() {
		String filePath = map.get("filePath");
		String tableField = map.get("tableField");
		String library_id = map.get("library_id");
		String lib_idx = map.get("lib_idx");
		String device_idx = map.get("device_idx");
		String tableName = map.get("tableName");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("DoUploadLibraryInfoFileTd 开始处理文件:"+filePath + "startTime:"+formatter.format(new Date()));	
		File file = new File(filePath);
		UploadLibraryInfoEntity ret=new UploadLibraryInfoEntity();
		Map<String,Object> fieldMap = new HashMap<>();
		List<Map<String,Object>> records = new ArrayList<>();
		if(file.exists() && file.isFile()){
			try{
				InputStreamReader read = new InputStreamReader(new FileInputStream(file),"UTF-8");
				BufferedReader bufferedReader = new BufferedReader(read);
			
				String lineTxt = null;
				String[] fields = tableField.split(",");
				for(int i=0;i<fields.length;i++){
					fieldMap.put(fields[i], String.valueOf(i+1));
				}
                while ((lineTxt = bufferedReader.readLine()) != null) {
                	String[] valueStr = lineTxt.split("\\#@#",fields.length);
                	Map<String,Object> valueMap = new HashMap<>();
                	for(int i=0;i<valueStr.length;i++){
                		valueMap.put(String.valueOf(i+1), valueStr[i]);
					}
                	records.add(valueMap);
                }
                
                ret.setLibrary_id(library_id);
                ret.setLibrary_idx(lib_idx);
                ret.setDevice_id(device_idx);
                ret.setDevice_idx(device_idx);
                ret.setTable(tableName);
                ret.setFields(fieldMap);
                ret.setRecords(records);
                
				if(tableName!=null){
					LibraryInfoService libraryInfoService = null;
					try {
						libraryInfoService = BeanFactoryHelper.getBean("uploadLibraryInfo_"+tableName, LibraryInfoService.class);
					} catch (NoSuchBeanDefinitionException e) {
						//throw new RuntimeException(tableName +"不符合参数设置[reader/bookitem].");
						libraryInfoService = BeanFactoryHelper.getBean("uploadLibraryInfo_common", LibraryInfoService.class);
					}
					if(libraryInfoService!=null){
						ResultEntity resultEntity = libraryInfoService.execute(ret);
					}
				}
				
				bufferedReader.close();
				read.close();
				System.out.println("DoUploadLibraryInfoFileTd 结束处理文件:"+filePath+ "endTime:"+formatter.format(new Date()));	
			}catch(Exception e){
				System.out.println("do upload library info file error" + e.toString());
			}
		}else{
			System.out.println("do upload library info file file is not exist.");
		}
	}
}
