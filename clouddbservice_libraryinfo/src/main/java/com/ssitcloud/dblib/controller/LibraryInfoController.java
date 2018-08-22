package com.ssitcloud.dblib.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.util.JSONUtils;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.factory.BeanFactoryHelper;
import com.ssitcloud.dblib.common.utils.ExceptionHelper;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.entity.DownloadLibraryInfoEntity;
import com.ssitcloud.dblib.entity.UploadLibraryInfoEntity;
import com.ssitcloud.dblib.service.LibraryInfoService;
import com.ssitcloud.dblib.service.TransferFileService;

@Controller
@RequestMapping("library")
public class LibraryInfoController {

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor_upload;
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor_download;
	
	@Resource
	private TransferFileService transferFileService;
	
	@Value("${OSB_DOWN_PATH}")
	public String OSB_DOWN_PATH;
	
	@RequestMapping("/uploadLibraryInfo")
	@ResponseBody
	public ResultEntity uploadLibraryInfo(HttpServletRequest request,String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if(JSONUtils.mayBeJSON(req)){
				UploadLibraryInfoEntity uploadLibraryInfoEntity = JsonUtils.fromJson(req,UploadLibraryInfoEntity.class);
				if (uploadLibraryInfoEntity == null) {return resultEntity;}
				String tableName = uploadLibraryInfoEntity.getTable();
				if(tableName!=null){
					LibraryInfoService libraryInfoService = null;
					try {
						libraryInfoService = BeanFactoryHelper.getBean("uploadLibraryInfo_"+tableName, LibraryInfoService.class);
					} catch (NoSuchBeanDefinitionException e) {
						//throw new RuntimeException(tableName +"不符合参数设置[reader/bookitem].");
						libraryInfoService = BeanFactoryHelper.getBean("uploadLibraryInfo_common", LibraryInfoService.class);
					}
					if(libraryInfoService!=null){
						resultEntity = libraryInfoService.execute(uploadLibraryInfoEntity);
					}
				}
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping("/notifyDoUploadLibraryInfoFile")
	@ResponseBody
	public ResultEntity notifyDoUploadLibraryInfoFile(HttpServletRequest request,String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if(JSONUtils.mayBeJSON(req)){
				Map<String, String> map = JsonUtils.fromJson(req,HashMap.class);
				try{
					taskExecutor_upload.execute(new DoUploadLibraryInfoFileTd(map));
					resultEntity.setState(true);
				}catch(Exception ex){
					System.out.println("线程池 name:taskExecutor_upload 异常...");
				}
				
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	@RequestMapping("/downloadLibraryInfo")
	@ResponseBody
	public ResultEntity downloadLibraryInfo(HttpServletRequest request,String req) {
		ResultEntity resultEntity = new ResultEntity();
		try {
			if(JSONUtils.mayBeJSON(req)){
				DownloadLibraryInfoEntity downloadLibraryInfoEntity = JsonUtils.fromJson(req,DownloadLibraryInfoEntity.class);
				if (downloadLibraryInfoEntity == null) {return resultEntity;}
				String tableName = downloadLibraryInfoEntity.getTable();
				if(tableName!=null){
					LibraryInfoService libraryInfoService = null;
					try {
						libraryInfoService = BeanFactoryHelper.getBean("downloadLibraryInfo_"+tableName, LibraryInfoService.class);
					} catch (NoSuchBeanDefinitionException e) {
						//throw new RuntimeException(tableName +"不符合参数设置[reader/bookitem].");
						libraryInfoService = BeanFactoryHelper.getBean("downloadLibraryInfo_common", LibraryInfoService.class);
					}
					if(libraryInfoService!=null){
						resultEntity = libraryInfoService.execute(downloadLibraryInfoEntity);
					}
				}
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
	
	
	@RequestMapping("/queryAllLibraryInfo")
	@ResponseBody
	public ResultEntity queryAllLibraryInfo(HttpServletRequest request,String req) {
		
		ResultEntity resultEntity = new ResultEntity();
		try {
			if(JSONUtils.mayBeJSON(req)){
				Map<String, String> map = JsonUtils.fromJson(req,HashMap.class);
				try{
					taskExecutor_download.execute(new DoDownloadLibraryInfoFileTd(transferFileService,map,OSB_DOWN_PATH));
				}catch(Exception ex){
					System.out.println("线程池 name:taskExecutor_download 异常...");
				}
				
				resultEntity.setState(true);
			}
		} catch (Exception e) {
			String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
			ExceptionHelper.afterException(resultEntity, methodName, e);
		}
		return resultEntity;
	}
}
