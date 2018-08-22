package com.ssitcloud.view.readermgmt.service.impl;


import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.LogUtils;
import com.ssitcloud.view.readermgmt.service.ReaderService;


@Service
public class ReaderServiceImpl extends BasicServiceImpl implements ReaderService{
	
	public static final String URL_uploadReaderCard = "uploadReaderCard";
	
	public static final String URL_uploadBiblios = "uploadBiblios";
	@Override
	public ResultEntity uploadReaderCard(CommonsMultipartFile multipartFile,String req) throws Exception {
		
		File file = createTempFile(multipartFile);
		if(file == null){
			ResultEntity entity = new ResultEntity();
			entity.setMessage("上传文件为空");
			entity.setState(false);
			return entity;
		}
		Map<String, String> map = new HashMap<>();
		map.put("req",req);
		String result=HttpClientUtil.postUploadWithParam(requestURL.getRequestURL(URL_uploadReaderCard), map,file);
		if(result!=null){
			ResultEntity resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
			if(!resultEntity.getState()) return new ResultEntity();
			Map<Object, Object> object = (Map<Object, Object>) resultEntity.getResult();
			Object errorlog = object.get("errorLog");
			if(errorlog != null){
				Map<String, String> errorArr =  (Map<String, String>) errorlog;
				if(errorArr.size() >0){
					String fileName = "failUploadReader.txt";
					//取得系统缓存目录
					String systemPath = System.getProperty("java.io.tmpdir");
					String path = systemPath+File.separator+"UPLOADREADERCARD"+File.separator;
					File localFile = new File(path+fileName);
					if(localFile.exists()){
						//如果存在，则删除
						try{
							FileUtils.forceDelete(localFile);
						}catch(Exception e){
							LogUtils.info(e);
							localFile.delete();
						}
					}
					if(!localFile.exists()){
						File file2 = new File(localFile.getParent());
						file2.mkdirs();
					}
					FileOutputStream fileOutputStream = new FileOutputStream(path+fileName, true);
					fileOutputStream.write("上传失败读者卡号:".getBytes("utf-8"));
					fileOutputStream.write("\r\n".getBytes("utf-8"));
					for(String cardNo : errorArr.keySet()){
						fileOutputStream.write(("卡号："+cardNo).getBytes("utf-8"));
						fileOutputStream.write("\r\n".getBytes("utf-8"));
						fileOutputStream.write(("失败原因："+errorArr.get(cardNo)).getBytes("utf-8"));
						fileOutputStream.write("\r\n".getBytes("utf-8"));
					}
					fileOutputStream.close();
					object.remove("errorLog");
					object.put("fileName",fileName);
					resultEntity.setResult(object);
				}
				
			}
			return resultEntity;
			
		}
		return new ResultEntity();
	}
	
	@Override
	public ResultEntity uploadBiblios(CommonsMultipartFile multipartFile,String req) throws Exception {
		
		File file = createTempFile(multipartFile);
		if(file == null){
			ResultEntity entity = new ResultEntity();
			entity.setMessage("上传文件为空");
			entity.setState(false);
			return entity;
		}
		Map<String, String> map = new HashMap<>();
		map.put("req",req);
		String result=HttpClientUtil.postUploadWithParam(requestURL.getRequestURL(URL_uploadBiblios), map,file);
		if(result!=null){
			ResultEntity resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
			if(!resultEntity.getState()) return new ResultEntity();
			Map<Object, Object> object = (Map<Object, Object>) resultEntity.getResult();
			Map<String,String> map1 = JsonUtils.fromJson(JsonUtils.toJson(object.get("errorLog")), Map.class);
			if(map1 !=null && map1.size() > 0){
					String fileName = "failUploadReader.txt";
					//取得系统缓存目录
					String systemPath = System.getProperty("java.io.tmpdir");
					String path = systemPath+File.separator+"UPLOADREADERCARD"+File.separator;
					File localFile = new File(path+fileName);
					if(localFile.exists()){
						//如果存在，则删除
						try{
							FileUtils.forceDelete(localFile);
						}catch(Exception e){
							LogUtils.info(e);
							localFile.delete();
						}
					}
					if(!localFile.exists()){
						File file2 = new File(localFile.getParent());
						file2.mkdirs();
					}
					FileOutputStream fileOutputStream = new FileOutputStream(path+fileName, true);
					for (Map.Entry<String, String> entry : map1.entrySet()) {  
						fileOutputStream.write(entry.getKey().getBytes("utf-8"));
						fileOutputStream.write(entry.getValue().getBytes("utf-8"));
						fileOutputStream.write("\r\n".getBytes("utf-8")); 
					}  
					//for(String cardNo : map1){
					//	fileOutputStream.write(cardNo.getBytes());
					//	fileOutputStream.write("\r\n".getBytes());
					//}
					fileOutputStream.close();
					object.remove("errorLog");
					object.put("fileName",fileName);
					resultEntity.setResult(object);
			}
			return resultEntity;
		}
		return new ResultEntity();
	}
	
	public File createTempFile(CommonsMultipartFile commonsMultipartFile){
		
		//取得文件名称
		String fileName = commonsMultipartFile.getOriginalFilename();
		if(fileName == null || fileName.length() <= 0){
			return null;
		}
		//取得系统缓存目录
		String systemPath = System.getProperty("java.io.tmpdir");
		//在本地创建空的文件
		String path = systemPath+File.separator+"UPLOADREADERCARD"+File.separator;
		File localFile = new File(path+fileName);
		if(localFile.exists()){
			//如果存在，则删除
			try{
				FileUtils.forceDelete(localFile);
			}catch(Exception e){
				LogUtils.info(e);
				System.gc();
				localFile.delete();
			}
		}
		
		if(!localFile.exists()){
			try{
        	File file2 = new File(localFile.getParent());
			file2.mkdirs();
			commonsMultipartFile.transferTo(localFile);
			}catch(Exception e){
				LogUtils.info(e);
			}
        }
		if(localFile.exists()){
    		return localFile;
        }
		return null;
	}
	
}
