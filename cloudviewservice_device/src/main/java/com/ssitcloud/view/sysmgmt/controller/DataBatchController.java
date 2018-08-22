package com.ssitcloud.view.sysmgmt.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.util.ExcelEventUtil;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.VerifyFileType;
import com.ssitcloud.view.sysmgmt.service.DataBatchService;

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
	 * 主页面
	 *
	 * <p>2017年5月24日 下午2:27:41 
	 * <p>create by hjc
	 * @return
	 */
	@RequestMapping("/main")
	public String toMain(){
		return "/page/sysmgmt/data-batch";
	}
	
	
	/**
	 * 数据文件上传
	 * 主要是书目数据以及读者数据上传
	 * 文件格式 txt文本以及excel文件
	 *
	 * <p>2017年5月27日 上午10:23:17 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/fileUpload")
	@ResponseBody
	public ResultEntity batchDataUpload(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		try {
			String encoding = request.getParameter("encoding");//编码格式
			String libidx = request.getParameter("libidx");//所属馆idx, 上传书目数据不需要填写图书馆
			String datatype = request.getParameter("datatype");//上传的数据类型，1：馆藏带书目 2：书目 3：读者数据
			String update = request.getParameter("update");//是否更新原有数据 1：更新 ， 2：不更新
			String operator = "admin";//获取操作员信息
			
			
			if ( !"biblios".equals(datatype) && !"bookitem".equals(datatype) && !"reader".equals(datatype)) {
				resultEntity.setValue(false, "请选择数据上传的类型");
				return resultEntity;
			}
			if(!"1".equals(update) && !"2".equals(update)){
				update = "1";
			}
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
			ServletContext servletContext = webApplicationContext.getServletContext();
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
						//临时路径加上项目名称  在tomcat的保存路径为  %TOMCAT_HOME%/temp/cloudviewservice_device/
						path = path + File.separator + request.getContextPath().replace("/", "");
						//判断文件夹是否存在,不存在则创建
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
						System.out.println(localFile.getAbsolutePath());
						//加上判断是txt 或者 excel文档
						String filetype = filename.substring(filename.lastIndexOf(".")+1);
						
						//excel 文档处理
						if ("xls".equals(filetype) || "xlsx".equals(filetype) ) {
//							ExcelEventUtil eventUtil = new ExcelEventUtil();
//							List<String[]> rows = eventUtil.processOneSheet(localFile.getAbsolutePath());
//							System.out.println(rows.size());
//							System.out.println(rows.get(0).length);
//							rows.remove(0);//删除第一行  字段头
//							//将文件上传到device 的service层
//							HttpClientUtil.postUpload(dataBatchService.getUrl(URL_batchDataUpload), localFile);
//							resultEntity.setValue(true, "", "", rows);
							resultEntity.setValue(false, "暂不支持excel数据上传");
							return resultEntity;
						}
						
						//txt文件
						if ("txt".equals(filetype) || "csv".equals(filetype)) {
							resultEntity = dataBatchService.handleTxt(localFile, path, encoding, libidx, operator, datatype, update);
						}
					}
				}
			}
		} catch (OLE2NotOfficeXmlFileException e) {
			resultEntity.setValue(false, "请上传格式为.xlsx的文档", "", "");
		} catch (NotOfficeXmlFileException e) {
			resultEntity.setValue(false, "请上传有效的office文档", "", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultEntity;
	}
	
	
	/**
	 * 返回上传进度
	 *
	 * <p>2017年9月5日 上午9:05:58 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/getUploadProcessing")
	@ResponseBody
	public ResultEntity getUploadProcessing(HttpServletRequest request){
		ResultEntity resultEntity = new ResultEntity();
		Map<String, Object> param = new HashMap<>();
		param.put("operator", "admin");
		
		resultEntity = dataBatchService.getCurrentProcessed(param);
		
		return resultEntity;
	}
	
	/**
	 * 中断上传操作
	 *
	 * <p>2017年9月5日 下午3:54:27 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/interruptUploading")
	@ResponseBody
	public ResultEntity interruptUploading(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		Map<String, Object> param = new HashMap<>();
		param.put("operator", "admin");
		
		resultEntity = dataBatchService.interruptUploading(param);
		
		return resultEntity;
	}
	
	/**
	 * 删除上传任务
	 *
	 * <p>2017年9月5日 下午3:54:27 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteUploading")
	@ResponseBody
	public ResultEntity deleteUploading(HttpServletRequest request) {
		ResultEntity resultEntity = new ResultEntity();
		Map<String, Object> param = new HashMap<>();
		param.put("operator", "admin");
		
		resultEntity = dataBatchService.deleteUploading(param);
		
		return resultEntity;
	}
	
	/**
	 * 下载日志文件
	 *
	 * <p>2017年9月25日 下午6:07:23 
	 * <p>create by hjc
	 * @param request
	 * @return
	 */
	@RequestMapping("/downloadLogFile")
	public ResponseEntity<byte[]> downloadLogFile(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		param.put("operator", "admin");
		return dataBatchService.downloadLogFile(param);
		
	}
	
	
	
	
}
