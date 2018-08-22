package com.ssitcloud.business.shelfmgmt.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

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

import com.ssitcloud.business.sysmgmt.service.DataBaseService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.utils.LogUtils;

@Controller
@RequestMapping("/common")
public class CommonController {
	
	@Resource
	private DataBaseService dataBaseService;
	
	
	@RequestMapping(value="uploadShelfInfoExcel")
    @ResponseBody
    public ResultEntity upload(HttpServletRequest request,HttpServletResponse response, String libId) throws IOException{
		ResultEntity result=new ResultEntity();
		//创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
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
                  
                    //如果名称不为"",说明该文件存在，否则说明该文件不存在  
                    if(myFileName!=null&&myFileName.trim() !=""){
                        //上传后的文件名  
                    	//上传后的文件名  
                        String fileName = file.getOriginalFilename();  
                        //定义上传路径  
                        //String path = "E:/" + fileName;  
                        //保存到临时缓存中
                        String path=System.getProperty("java.io.tmpdir");
                        if(libId != null){
                        	path += File.separator + libId;
                        }else{
                        	return result;
                        }
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
                        	File file2 = new File(localFile.getParent());
							file2.mkdirs();
                            file.transferTo(localFile);
                        }
                        String req = path+File.separator+fileName;
                        result.setResult(req);
                        result.setState(true);
                        }
                    }  
                }  
                //记录上传该文件后的时间  
                //long finaltime = System.currentTimeMillis();  
            }  
              
        return result;  
		
    }
	
	
	@RequestMapping(value="uploadPoint")
    @ResponseBody
    public ResultEntity uploadPoint(HttpServletRequest request,HttpServletResponse response, String libId) throws IOException{
		ResultEntity result=new ResultEntity();
		//创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
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
                  
                    //如果名称不为"",说明该文件存在，否则说明该文件不存在  
                    if(myFileName!=null&&myFileName.trim() !=""){
                        //上传后的文件名  
                    	//上传后的文件名  
                        String fileName = file.getOriginalFilename();  
                        //定义上传路径  
                        //String path = "E:/" + fileName;  
                        //保存到临时缓存中
                        String path=System.getProperty("java.io.tmpdir");
                        if(libId != null){
                        	path += File.separator + libId;
                        }else{
                        	return result;
                        }
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
                        	File file2 = new File(localFile.getParent());
							file2.mkdirs();
                            file.transferTo(localFile);
                        }
                        String req = path+File.separator+fileName;
                        result.setResult(req);
                        result.setState(true);
                        }
                    }  
                }  
                //记录上传该文件后的时间  
                //long finaltime = System.currentTimeMillis();  
            }  
              
        return result;  
		
    }
}
