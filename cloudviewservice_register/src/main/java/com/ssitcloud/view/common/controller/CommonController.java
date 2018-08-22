package com.ssitcloud.view.common.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.util.JSONUtils;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.utils.LogUtils;
import com.ssitcloud.devmgmt.entity.FileUploadState;
import com.ssitcloud.node.entity.FileManagerEntity;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.PropertiesUtil;
import com.ssitcloud.view.service.DeviceService;


@Controller
@RequestMapping("/common")
public class CommonController {
	
	@Resource
	private DeviceService deviceService;
	
	@RequestMapping(value = "uploadZip")
	@ResponseBody
	public ResultEntity uploadZip(HttpServletRequest request, HttpServletResponse response, String libIdx, String libId, String deviceId) throws IOException {
		PropertiesUtil propertites = new PropertiesUtil();
		String rootPath = "";
		String path = propertites.getValue("zipPath");
		// String path = System.getProperty("java.io.tmpdir");

		ResultEntity result = new ResultEntity();
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(servletContext);
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 记录上传过程起始时的时间，用来计算上传时间
				long pre = System.currentTimeMillis();
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					BufferedReader br = null;
					// 如果名称不为"",说明该文件存在，否则说明该文件不存在
					if (myFileName != null && myFileName.trim() != "") {
						// 上传后的文件名
						// 上传后的文件名
						String fileName = file.getOriginalFilename();

						// 如果图书馆目录不为空
						if (libId != "" && libId != null) {
							path += File.separator + libId;
							// 如果设备Id不为空
							if (deviceId != "" && deviceId != null && !("null".equals(deviceId))) {
								path += File.separator + deviceId;
							}
						}

						File localFile = new File(path + File.separator + fileName);
						System.out.println(path);
						if (localFile.exists()) {// 判断文件的存在
							System.out.println("文件存在！");
							if (localFile.isDirectory()) {// 判断文件的存在性
								System.out.println("文件存在！");
							} else {
								localFile.createNewFile();// 创建文件
								System.out.println("文件不存在，创建文件成功！");
							}
						} else {
							System.out.println("文件不存在！");
							File file2 = new File(localFile.getParent());
							file2.mkdirs();
							System.out.println("创建文件成功！");
							if (localFile.isDirectory()) {
								// 如果文件存在
								System.out.println("文件存在！");
							} else {
								// 如果文件不存在创建文件
								localFile.createNewFile();// 创建文件
								System.out.println("文件不存在，创建文件成功！");
							}
						}
						// 如果文件已经存在，先删掉后在上传
						if (localFile.exists()) {
							try {
								FileUtils.forceDelete(localFile);
							} catch (Exception e) {
								LogUtils.info(e);
								System.gc();
								localFile.delete();
							}
						}
						
						// 如果文件不存在，则将文件内容存入，并在数据库插入一条记录，作为sync同步的判断信息
						if (!localFile.exists()) {
							file.transferTo(localFile);
						}
						
						// 如果文件不存在，则将文件内容存入，并在数据库插入一条记录，作为sync同步的判断信息
						/*if (!localFile.exists()) {
							file.transferTo(localFile);
							// 转换提日期输出格式
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String modifyTime = dateFormat.format(new java.sql.Date(System.currentTimeMillis())).toString();
							String req = "{\"lib_idx\":" + libIdx + ",\"lib_id\":\"" + libId + "\",\"device_id\":\"" + deviceId + "\",\"issync\":0,\"table_name\":\"NcipTemplate\""
									+ ",\"sync_type\":\"zip\",\"last_modify_time\":\"" + modifyTime + "\"}";
							deviceService.insertFileUploadFlag(req);
						}*/
						
						String res= HttpClientUtil.postUploadLibDev(deviceService.getUrl("transferToBusinessDatasync"), localFile,libId,deviceId);
						System.out.println("registerview上传文件到bussinessDatasync层"+ res);
						
						if(JSONUtils.mayBeJSON(res)){
							ResultEntity rst =JsonUtils.fromJson(res, ResultEntity.class);
							if(rst != null && rst.getState()){
								// 转换提日期输出格式
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								String modifyTime = dateFormat.format(new java.sql.Date(System.currentTimeMillis())).toString();
								String req = "{\"lib_idx\":" + libIdx + ",\"lib_id\":\"" + libId + "\",\"device_id\":\"" + deviceId + "\",\"issync\":0,\"table_name\":\"NcipTemplate\""
										+ ",\"sync_type\":\"zip\",\"last_modify_time\":\"" + modifyTime + "\"}";
								deviceService.insertFileUploadFlag(req);
							}
							
						}

						//String req = path + File.separator + fileName;
						// result =
						// bookshelflayerService.uploadBookshelflayer(req);
						result.setMessage("保存成功!!");
						result.setState(true);
						// InputStream is = new FileInputStream(localFile);
					}
				}
			}
		}
		return result;
	}
	
	
	/**
	 * 上传ncip文件到文件服务器
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping("uploadFileToFileServer")
	public ResultEntity uploadFileToFileServer(@RequestParam("file") CommonsMultipartFile multiFile,HttpServletRequest request){
		String libId = request.getParameter("libId");
		String deviceId = request.getParameter("deviceId");
		File file = createTempFile(multiFile);
		if(file == null){
			ResultEntity entity = new ResultEntity();
			entity.setMessage("上传文件为空");
			entity.setState(false);
			return entity;
		}
		
		FileManagerEntity entity = new FileManagerEntity();
		entity.setLibrary_id(libId);
		entity.setDevice_id(deviceId);
		Map<String, String> map = new HashMap<>();
		map.put("req", JsonUtils.toJson(entity));
		
		String result=HttpClientUtil.postUploadWithParam(deviceService.getUrl("uploadFile"), map,file);
		
		if(!StringUtils.isEmpty(result)){
			ResultEntity resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
			if(resultEntity.getState()){
				Object object = resultEntity.getResult();
				if(object != null){
					FileManagerEntity fileManagerEntity = JsonUtils.fromJson(JsonUtils.toJson(object), FileManagerEntity.class);
					return downLoadFileURLToDevice(fileManagerEntity);
				}
			}
			
		}
		return new ResultEntity();
	}
	
	public ResultEntity downLoadFileURLToDevice(FileManagerEntity entity){
	    	
	    ResultEntity resultEntity = new ResultEntity();
	    	if(StringUtils.isEmpty(entity.getLibrary_id()) || StringUtils.isEmpty(entity.getDevice_id())){
	    		resultEntity.setMessage("lib_id or device_id is null");
	    		return resultEntity;
	    	}
	    	CloudSyncRequest<FileUploadState> request = new CloudSyncRequest<FileUploadState>();
	    	FileUploadState fileUploadState = new FileUploadState();
			String clientId = request.getCacheClient(entity.getLibrary_id(), entity.getDevice_id());//获取客户端的clicnetId
			request.setClientId(clientId);
			request.setOperation(FileManagerEntity.NCIPTEMPLATE);
			request.setTarget("device");
			request.setServicetype("ssitcloud");
			fileUploadState.setTableName(FileManagerEntity.NCIPTEMPLATE);
			fileUploadState.setFileName(entity.getFile_name());
			fileUploadState.setState("2");
			fileUploadState.setFilePath(entity.getFile_path());
			List<FileUploadState> list = new ArrayList<>();
			list.add(fileUploadState);
			request.setData(list);
			request.setDevice_id(entity.getDevice_id());
			request.setLib_id(entity.getLibrary_id());
			Map<String, String> map = new HashMap<>();
			map.put("req", JsonUtils.toJson(request));
			String result=HttpClientUtil.doPost(deviceService.getUrl("send"), map, "UTF-8");
			if(result != null){
				return JsonUtils.fromJson(result, ResultEntity.class);
			}
			return resultEntity;
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
		String path = systemPath+File.separator+"NCIPCONFIG"+File.separator;
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
