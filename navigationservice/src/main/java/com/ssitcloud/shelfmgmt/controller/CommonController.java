package com.ssitcloud.shelfmgmt.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExportExcelUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.shelfmgmt.service.BookshelflayerService;

@Controller
@RequestMapping("/common")
public class CommonController {

	@Resource
	BookshelflayerService bookshelflayerService;
	
	
	@RequestMapping(value={"exportExcel"})
	@ResponseBody
	public void exportCountData(HttpServletRequest request, HttpServletResponse response,String req,String headers,String title,String fileName) throws Exception {
		
		try{
			ResultEntity result = bookshelflayerService.exportBookshelflayer(req);
			if(result!=null && result.getState()){
				List<Object> list = (List<Object>) result.getResult();
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
				response.setCharacterEncoding("utf-8");
				response.setContentType("application/x-download");//("application/vnd.ms-excel");//("application/x-download");//设置response内容的类型
				String cdisp = "attachment;filename=\""+new String(fileName.getBytes("gb2312"),"ISO8859_1")+"-"+dateFormat.format(new Date())+".xls\"";
		        response.setHeader("Content-disposition",cdisp);//设置头部信息
		        
		        ObjectMapper mapper=new ObjectMapper();
		        Map headerMap = mapper.readValue(request.getParameter("headers"), Map.class);
				ExportExcelUtils.exportExcel(title,headerMap, list, response.getOutputStream());
			}
		}catch(Exception ex){
			System.out.println("导出书架数据异常...");
		}
	}
	
	@RequestMapping(value="upload")
    @ResponseBody
    public ResultEntity upload(HttpServletRequest request,HttpServletResponse response) throws IOException{
		ResultEntity result=new ResultEntity();
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();   
        ServletContext servletContext = webApplicationContext.getServletContext(); 
		//创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(servletContext);  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                //记录上传过程起始时的时间，用来计算上传时间  
                long pre =  System.currentTimeMillis();  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){  
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    BufferedReader br = null;
                    //如果名称不为"",说明该文件存在，否则说明该文件不存在  
                    if(myFileName!=null&&myFileName.trim() !=""){
                        //上传后的文件名  
                    	//上传后的文件名  
                        String fileName = file.getOriginalFilename();  
                        //定义上传路径  
                        //String path = "E:/" + fileName;  
                        //保存到临时缓存中
                        String path=System.getProperty("java.io.tmpdir");
                        File localFile = new File(path+File.separator+fileName); 
                        if(localFile.exists()){
                        	try {
                        		FileUtils.forceDelete(localFile);
							} catch (Exception e) {
								LogUtils.info(e);
								System.gc();
								localFile.delete();
							}
                        }
                        if(!localFile.exists()){
                            file.transferTo(localFile);
                        }
                        String req = path+File.separator+fileName;
                        result = bookshelflayerService.uploadBookshelflayer(req);
                    	//InputStream is = new FileInputStream(localFile);
                    }  
                }  
            }  
        }  
        return result; 
    }
	
	@RequestMapping(value="uploadImg")
    @ResponseBody
    public ResultEntity uploadImg(HttpServletRequest request,HttpServletResponse response) throws IOException{
		ResultEntity result=new ResultEntity();
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();   
        ServletContext servletContext = webApplicationContext.getServletContext(); 
		//创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(servletContext);  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                //记录上传过程起始时的时间，用来计算上传时间  
                long pre =  System.currentTimeMillis();  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){  
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    BufferedReader br = null;
                    //如果名称不为"",说明该文件存在，否则说明该文件不存在  
                    if(myFileName!=null&&myFileName.trim() !=""){
                        //上传后的文件名  
                    	//上传后的文件名  
                        String fileName = file.getOriginalFilename();  
                        //定义上传路径  
                        //String path = "E:/" + fileName;  
                        //保存到临时缓存中
                        String path=System.getProperty("java.io.tmpdir");
                        File localFile = new File(path+File.separator+fileName); 
                        if(localFile.exists()){
                        	try {
                        		FileUtils.forceDelete(localFile);
							} catch (Exception e) {
								LogUtils.info(e);
								System.gc();
								localFile.delete();
							}
                        }
                        if(!localFile.exists()){
                            file.transferTo(localFile);
                        }
                        String req = fileName;
                        result.setResult(req);
                        result.setState(true);
                    }  
                }  
            }  
        }  
        return result; 
    }
	
	@RequestMapping(value="uploadPoint")
    @ResponseBody
    public ResultEntity uploadPoint(HttpServletRequest request,HttpServletResponse response) throws IOException{
		ResultEntity result=new ResultEntity();
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();   
        ServletContext servletContext = webApplicationContext.getServletContext(); 
		//创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(servletContext);  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                //记录上传过程起始时的时间，用来计算上传时间  
                long pre =  System.currentTimeMillis();  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){  
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    BufferedReader br = null;
                    //如果名称不为"",说明该文件存在，否则说明该文件不存在  
                    if(myFileName!=null&&myFileName.trim() !=""){
                        //上传后的文件名  
                    	//上传后的文件名  
                        String fileName = file.getOriginalFilename();  
                        //定义上传路径  
                        //String path = "E:/" + fileName;  
                        //保存到临时缓存中
                        String path=System.getProperty("java.io.tmpdir");
                        File localFile = new File(path+File.separator+fileName); 
                        if(localFile.exists()){
                        	try {
                        		FileUtils.forceDelete(localFile);
							} catch (Exception e) {
								LogUtils.info(e);
								System.gc();
								localFile.delete();
							}
                        }
                        if(!localFile.exists()){
                            file.transferTo(localFile);
                        }
                        String req = path+File.separator+fileName;
                        result.setResult(req);
                        result.setState(true);
                    }  
                }  
            }  
        }  
        return result; 
    }
}
