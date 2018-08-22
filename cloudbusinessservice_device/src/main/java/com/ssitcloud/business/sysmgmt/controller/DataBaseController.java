package com.ssitcloud.business.sysmgmt.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.util.JSONUtils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ssitcloud.business.common.util.ExceptionHelper;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.sysmgmt.service.DataBaseService;
import com.ssitcloud.business.sysmgmt.service.DataBaseV2Service;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DbBakUpPageEntity;

@RequestMapping(value={"database"})
@Controller
public class DataBaseController{

	@Resource
	private DataBaseService dataBaseService;
	@Resource
	private DataBaseV2Service dataBaseV2Service;
	
	
	/**
	 * 备份数据库
	 * req={
	 *  dbType:"MongoDB/MySQL", 其中一个
	 *  dbName:"数据库名"
	 * 
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"backUp"})
	@ResponseBody
	public ResultEntity backUpDataBase(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=dataBaseService.backUp(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 查询备份数据分页 （带参数）
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"queryDbBakByparam"})
	@ResponseBody
	public ResultEntity queryDbBakByparam(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=dataBaseService.queryDbBakByparam_bus(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("queryLibraryDbBakByparamExt")
	@ResponseBody
	public ResultEntity queryLibraryDbBakByparamExt(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=dataBaseService.queryLibraryDbBakByparamExt(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	/**
	 * 删除一个数据库备份
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"deleteBakup"})
	@ResponseBody
	public ResultEntity deleteBakup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=dataBaseService.deleteBakup_bus(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	//getLastBakUpTime
	/**
	 * 获取各个数据库最后备份时间
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"getLastBakUpTime"})
	@ResponseBody
	public ResultEntity getLastBakUpTime(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=dataBaseService.getLastBakUpTime_bus(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	//restoreBakup
	/**
	 * 恢复备份数据
	 * @param request
	 * @param req
	 * @return
	 */
	
	@RequestMapping(value={"restoreBakup"})
	@ResponseBody
	public ResultEntity restoreBakup(HttpServletRequest request,String req){
	
		ResultEntity result=new ResultEntity();
		try {
			result=dataBaseService.restoreBakup_bus(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 数据库数据下载
	 * 
	 * 暂时 废弃不用了
	 * @param request
	 * @param response
	 * @param req
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@Deprecated
	@RequestMapping(value={"downloadFile"})
	public void downloadFile(HttpServletRequest request,HttpServletResponse response,String req) throws ServletException, IOException{
		ResponseEntity<byte[]> respEntity = null;
		if(JSONUtils.mayBeJSON(req)){
			DbBakUpPageEntity dbBakUpPage=JsonUtils.fromJson(req,DbBakUpPageEntity.class);
			if(dbBakUpPage!=null){
				String fileName=dbBakUpPage.getFileName();
				String filePath=dbBakUpPage.getFilePath();
				HttpHeaders headers = new HttpHeaders(); 
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
				headers.setContentDispositionFormData("attachment", fileName); 
		       /*try {
					respEntity=new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(filePath)), headers, HttpStatus.CREATED);
				} catch (IOException e) {
					e.printStackTrace();
				} */
				File file = new File(filePath);// path是根据日志路径和文件名拼接出来的
			    String filename = file.getName();// 获取日志文件名称
			    InputStream fis = new BufferedInputStream(new FileInputStream(filePath));
			    byte[] buffer = new byte[fis.available()];
			    fis.read(buffer);
			    fis.close();
			    response.reset();
			    // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
			    response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.replaceAll(" ", "").getBytes("utf-8"),"iso8859-1"));
			    response.addHeader("Content-Length", "" + file.length());
			    OutputStream os = new BufferedOutputStream(response.getOutputStream());
			    response.setContentType("application/octet-stream");
			    os.write(buffer);// 输出文件
			    os.flush();
			    os.close();
			}
		}
		
		//return respEntity;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param req
	 * @return
	 */
	@RequestMapping("bakupByLibraryIdx")
	@ResponseBody
	public ResultEntity bakupByLibraryIdx(HttpServletRequest request,HttpServletResponse response,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=dataBaseV2Service.dataBaseBakupByLibraryIdxAndID(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("restoreDataByLibraryIdx")
	@ResponseBody
	public ResultEntity restoreDataByLibraryIdx(HttpServletRequest request,
			String req) {
		ResultEntity result = new ResultEntity();
		try {
			result = dataBaseV2Service.restoreDataByLibraryIdx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 检查文件是否存在，更新标记
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("checkBakUpFileIfExsit")
	@ResponseBody
	public ResultEntity checkBakUpFileIfExsit(HttpServletRequest request,
			String req){
		ResultEntity result = new ResultEntity();
		try {
			result = dataBaseV2Service.checkBakUpFileIfExsit(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("deleteLibBakup")
	@ResponseBody
	public ResultEntity deleteLibBakup(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result = dataBaseV2Service.deleteLibBakup(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	@RequestMapping("getLastLibBakUpTime")
	@ResponseBody
	public ResultEntity getLastLibBakUpTime(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result = dataBaseV2Service.getLastLibBakUpTime(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	
	
	/**
	 * 文件上传
	 * @param request
	 * @param response
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("/bakUpload")
	@ResponseBody
    public ResultEntity bakUpload(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException {  
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
                    	if(!dataBaseService.validateFileName(myFileName)){
                    		result.setMessage("请上传压缩文件！文件格式说明：|一、前缀： | (1)ssitcloud_authentication_| (2)ssitcloud_device_| (3)ssitcloud_mongo_[数据库名称]_。|"+
                        						   "二、时间部分：年月日时分秒(yyyyMMddHHmmss格式)。|三、后缀：.zip。|"+" 文件格式例如 :ssitcloud_authentication_20160521174700.zip");
                    		return result;
                    	}
                        //上传后的文件名  
                        String fileName =file.getOriginalFilename();  
                        //定义上传路径  
                        String path = dataBaseService.getDir()+File.separatorChar+fileName;
                        //String path = "E:/" + fileName;  
                        File localFile = new File(path);
                        if(!localFile.exists()){
                        	  file.transferTo(localFile);
                              if(new File(path).exists()){
                                  result.setState(true);
                              }	
                        }else{
                        	result.setMessage("文件名已经存在");
                        }
                    }  
                }  
                //记录上传该文件后的时间  
                //long finaltime = System.currentTimeMillis();  
            }  
              
        }  
        return result;  
    }
	
	
	@RequestMapping("queryBakDataInfoByIdx")
	@ResponseBody
	public ResultEntity queryBakDataInfoByIdx(HttpServletRequest request,String req){
		ResultEntity result = new ResultEntity();
		try {
			result = dataBaseV2Service.queryBakDataInfo(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
}
