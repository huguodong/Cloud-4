package com.ssitcloud.datasync.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.concurrent.Callable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.manager.DispacherManager;
import com.ssitcloud.common.utils.LogUtils;
import com.ssitcloud.datasync.service.NotifyLibraryInfoService;
import com.ssitcloud.datasync.service.SyncContainerService;
/**
 * 用于处理设备端的请求信息
 * @package: com.ssitcloud.common.controller
 * @classFile: DataSyncController
 * @author: liuBh
 * @description: TODO
 */
@RestController
@RequestMapping(value={"sync"})
public class DataSyncController {
	
	@Resource
	private DispacherManager dispacherManager;
	@Resource
	private SyncContainerService syncContainerService;
	@Resource
	private NotifyLibraryInfoService notifyLibraryInfoService;
	/**
	 * 
	 * heartBeat:
	 * req={"servicetype":"ssitcloud","target":"ssitcloud","operation":"heartBeat","data":{"library_id":"0001","device_id":"0001"}}
	 * <p/>
	 * uploadcfgSync:
	 * req={"servicetype":"ssitcloud","target":"ssitcloud","operation":"uploadcfgSyn","data":{"device_id":"www","library_id":"1","table":"device_ext_config","fields":"logic_obj,ext_model,ext_comm_conf,ext_extend_conf","records":[{"ext_comm_conf":"xxxxxx","logic_obj":"01","ext_extend_conf":"xxx","ext_model":"df"}]}}
	 * 
	 * 
	 * @methodName: datasync
	 * @param request
	 * @param response
	 * @return
	 * @returnType: Callable<RespEntity>
	 * @author: liuBh
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value={"datasync"})
	@ResponseBody
	public RespEntity datasync(HttpServletRequest request,final String req,HttpServletResponse response) throws UnsupportedEncodingException {  
    	if(req!=null){
    		int lenght = req.getBytes("GBK").length;
    		System.out.println(req);
    		 RespEntity resp=dispacherManager.doDispacherProxy(request);
    		 return resp;
    	}
    	RespEntity resp=new RespEntity();
    	resp.getData().setMessage("格式不正确，需要参数，参数名req");
		return resp;
    }
	
	@RequestMapping(value={"QueryContainerInfo"})
	@ResponseBody
	public Callable<ResultEntity> QueryContainerInfo(HttpServletRequest request,final String req){
		 return new Callable<ResultEntity>() { 
	        	@Override
	            public ResultEntity call() throws Exception {
	        		
					return syncContainerService.QueryContainerInfo(req);  
	            }  
	        };
	}
	/**
	 * 清除缓存
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("upadtePermessionCacheForAllDevice")
	@ResponseBody
	public Callable<ResultEntity> upadtePermessionCacheForAllDevice(HttpServletRequest request,final String req){
		return new Callable<ResultEntity>() { 
        	@Override
            public ResultEntity call() throws Exception {
				return dispacherManager.upadtePermessionCacheForAllDevice(req);  
            }  
        };
	}
	
	@RequestMapping(value={"test"})
	@ResponseBody
	public Callable<ResultEntity> test(HttpServletRequest request,final String req){
		 return new Callable<ResultEntity>() { 
	        	@Override
	            public ResultEntity call() throws Exception {
	        		
					return syncContainerService.QueryContainerInfo(req);  
	            }  
	        };
	}
	
	@RequestMapping("transferToBusinessDatasync")
	@ResponseBody
	public ResultEntity transferToBusinessDatasync(HttpServletRequest request, String libId, String deviceId){

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
                    System.out.println("datasync 接口 transferToBusinessDatasync 接收到文件："+myFileName);
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
                        	if(deviceId != null){
                        		path += File.separator + deviceId;
                        	}else{
                        		return result;
                        	}
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
							try{
								file.transferTo(localFile);
							}catch(Exception ex){
								System.out.println("business datasync file transfer error! fileName:"+fileName);
							}
                            
                        }
                        String req = path+File.separator+fileName;
                        result.setResult(req);
                        result.setState(true);
                        System.out.println("datasync 接口 transferToBusinessDatasync 存储文件成功："+req);
                        }
                    }  
                }  
                //记录上传该文件后的时间  
                //long finaltime = System.currentTimeMillis();  
            }  
              
        return result;  
	}
	
	@RequestMapping("notifyLibraryInfo")
	@ResponseBody
	public ResultEntity notifyLibraryInfo(HttpServletRequest request, String req){
		ResultEntity result = new ResultEntity();
		result = notifyLibraryInfoService.notifyLibraryInfo(req);
		return result;
	}
}
