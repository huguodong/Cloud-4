package com.ssitcloud.business.sysmgmt.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.sysmgmt.service.DataBatchService;

/**
 * 基础数据批处理
 *
 * <p>2017年5月24日 上午10:45:48  
 * @author hjc 
 *
 */
@Controller
@RequestMapping("/batch")
public class DataBatchController {
	
	@Resource
	private DataBatchService dataBatchService;
	
	/**
	 * 上传文件的url
	 */
	private static final String URL_batchDataUpload = "batchDataUpload";
	
	/**
	 * 将上传文件传输到library_info
	 *
	 * <p>2017年6月8日 下午1:40:24 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/batchDataUpload")
	@ResponseBody
	public ResultEntity batchDataUpload(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			ServletContext servletContext = request.getSession().getServletContext();
			String json = request.getParameter("json");
			Map<String, String> libParam = new HashMap<String, String>();
			libParam.put("json", json);
			//
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(servletContext);
			if (multipartResolver.isMultipart(request)) {
				//request
				MultipartHttpServletRequest multRequest = (MultipartHttpServletRequest) request;
				//
				Iterator<String> it = multRequest.getFileNames();
				while (it.hasNext()) {
					MultipartFile file = multRequest.getFile(it.next());
					if (file!=null) {
						String filename = file.getOriginalFilename();
						String path = System.getProperty("java.io.tmpdir"); //临时存储路径
						//临时路径加上项目名称
						path = path + File.separator + request.getContextPath().replace("/", "");
						//盘点文件夹是否存在,不存在则创建
						File dirPath = new File(path);
						if(!dirPath.exists()){
							dirPath.mkdirs();
						}
						File localFile = new File(path + File.separator + filename);
						if (localFile.exists()) {
							try {
								FileUtils.forceDelete(localFile);
							} catch (IOException e) {
								System.gc();
								localFile.delete();
							}
						}
						
						file.transferTo(localFile);
						
						///将文件上传到libraryInfo 的db层
						String result = HttpClientUtil.postUploadWithParam(dataBatchService.getUrl(URL_batchDataUpload), libParam, localFile);
						System.out.println("bussiness:"+result);
						
						//删除文件
						deleteFile(localFile);
						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultEntity;
	}
	
	/**
	 * 删除文件
	 *
	 * <p>2017年8月25日 下午6:41:55 
	 * <p>create by hjc
	 * @param file
	 */
	private void deleteFile(File file){
		if (file.exists()) {
			try {
				FileUtils.forceDelete(file);
			} catch (IOException e) {
				System.gc();
				file.delete();
			}
		}
	}
	
}
