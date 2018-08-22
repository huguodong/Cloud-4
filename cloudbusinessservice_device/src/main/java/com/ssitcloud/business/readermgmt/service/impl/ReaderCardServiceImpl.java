package com.ssitcloud.business.readermgmt.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.readermgmt.service.ConfigFieldService;
import com.ssitcloud.business.readermgmt.service.ReaderService;
import com.ssitcloud.common.entity.ResultEntity;


@Service
public class ReaderCardServiceImpl extends BasicServiceImpl implements ReaderService{

	public static final String URL_uploadReaderCard = "uploadReaderCard";
	
	public static final String URL_uploadBiblios = "uploadBiblios";
	
	@Autowired
	private ConfigFieldService fieldService;
	public ResultEntity uploadReaderCard(CommonsMultipartFile multipartFile,String req) {
		
		File file = createTempFile(multipartFile);
		if(file == null){
			ResultEntity entity = new ResultEntity();
			entity.setMessage("上传文件为空");
			entity.setState(false);
			return entity;
		}
		//获取模板
		Map<String, String> map = new HashMap<>();
		ResultEntity entity = fieldService.queryOneImportConfig(req);
		map.put("req", JsonUtils.toJson(entity));
		
		String result=HttpClientUtil.postUploadWithParam(requestURL.getRequestURL(URL_uploadReaderCard), map,file);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return new ResultEntity();
		
	}
	
	public ResultEntity uploadBiblios(CommonsMultipartFile multipartFile,String req) {
		
		File file = createTempFile(multipartFile);
		if(file == null){
			ResultEntity entity = new ResultEntity();
			entity.setMessage("上传文件为空");
			entity.setState(false);
			return entity;
		}
		//获取模板
		Map<String, String> map = new HashMap<>();
		ResultEntity entity = fieldService.queryOneImportConfig(req);
		map.put("req", JsonUtils.toJson(entity));
		
		String result=HttpClientUtil.postUploadWithParam(requestURL.getRequestURL(URL_uploadBiblios), map,file);
		if(result!=null){
			return JsonUtils.fromJson(result, ResultEntity.class);
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
		File localFile = new File(path+File.separator+fileName);
		if(localFile.exists()){
			//如果存在，则删除
			try{
				FileUtils.forceDelete(localFile);
			}catch(Exception e){
				localFile.delete();
			}
		}
		
		if(!localFile.exists()){
			try{
        	File file2 = new File(localFile.getParent());
			file2.mkdirs();
			commonsMultipartFile.transferTo(localFile);
			}catch(Exception e){
				e.printStackTrace();
			}
        }
		
		if(localFile.exists()){
    		return localFile;
        }
		return null;
	}

}
