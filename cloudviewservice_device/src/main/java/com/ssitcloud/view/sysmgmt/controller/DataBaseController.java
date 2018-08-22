package com.ssitcloud.view.sysmgmt.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.util.JSONUtils;

import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.ssitcloud.common.entity.Const;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.controller.BasicController;
import com.ssitcloud.view.common.util.ExceptionHelper;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.common.util.LogUtils;
import com.ssitcloud.view.common.util.PropertiesUtil;
import com.ssitcloud.view.common.util.SystemLogUtil;
import com.ssitcloud.view.sysmgmt.service.DataBaseService;

/**
 * 
 * 数据库备份相关
 * @author LBH
 *
 */
@RequestMapping(value={"database"})
@Controller
public class DataBaseController extends BasicController{

	@Resource
	private DataBaseService dataBaseService;
	
	/**
	 * 系统数据备份 页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"main"})
	public ModelAndView systemDatabackup(HttpServletRequest request){
		Map<String,Object> model=new HashMap<>();
		
		return new ModelAndView("/page/sysmgmt/system-databackup", model);
	}
	//
	@RequestMapping(value={"mainext1"})
	public ModelAndView mainext1(HttpServletRequest request){
		Map<String,Object> model=new HashMap<>();
		return new ModelAndView("/page/sysmgmt/system-databackup-library", model);
	}
	
	/**
	 * 备份数据库
	 * req={
	 *  dbType:"MongoDB/MySQL", 其中一个
	 *  
	 * 
	 * }
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"backUp"})
	@ResponseBody
	public ResultEntity backUp(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=dataBaseService.backUp(req);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_BAKUP_DATABASE);
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
			result=dataBaseService.queryDbBakByparam(req);
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
	 * 删除备份文件
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"deleteBakup"})
	@ResponseBody
	public ResultEntity deleteOneBakup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=dataBaseService.deleteBakup(req);
			//增加删除日志
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_DEL_DATABASE_BAKUP);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 获取各个数据库最后更新时间
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"getLastBakUpTime"})
	@ResponseBody
	public ResultEntity getLastBakUpTime(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=dataBaseService.getLastBakUpTime(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 数据库还原
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value={"restoreBakup"})
	@ResponseBody
	public ResultEntity restoreBakup(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		String operType=getCurrentUser().getOperator_type();
		if(!Operator.SSITCLOUD_ADMIN.equals(operType)){
			if(!checkPermession(Const.PERMESSION_RESTORE_DATABASE)){
				result.setRetMessage("没有权限操作！");
				return result;
			}
		}
		try {
			result=dataBaseService.restoreBakup(req);
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_RESOTRE_DATABASE);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 检查下载的文件是否存在VIEW项目所在服务器,如果不在则线传输到VIEW SERVER
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"checkSQLFileExsits"})
	@ResponseBody
	public ResultEntity checkSQLFileExsits(HttpServletRequest request){
		ResultEntity result=new ResultEntity();
		String fileName=request.getParameter("fileName");
		String filePath=request.getParameter("filePath");
		File genFile=recvFile(filePath, fileName);
		if(genFile!=null&&genFile.exists()){
			result.setState(true);
		}
		return result;
	}
	
	/**
	 * 下载文件
	 * @param request
	 * @param req 
	 * ResponseEntity<byte[]>
	 */
	@RequestMapping(value={"downloadFile"})
	public synchronized void downloadFile(HttpServletRequest request,HttpServletResponse response,String req){
		String fileName=request.getParameter("fileName");
		String filePath=request.getParameter("filePath");
		/*Map<String,String> map=new HashMap<>();
		map.clear();
		Set<Entry<String,String>> set=map.entrySet();
		Iterator<Entry<String,String>> it=set.iterator();
		while(it.hasNext()){
			Entry<String,String> e=it.next();
			String key=e.getKey();
			String val=e.getValue();
		}*/
		//map.put("fileName", fileName);
		//map.put("filePath", filePath);
		//HashMap<String, String> m=new HashMap<>();
		//m.put("req", JsonUtils.toJson(map));
		//获取下载路径 并重定向
		//String url=dataBaseService.getUrl("downloadFile");
		//File file=HttpClientUtil.downloadBakFile(url, filePath,fileName,m);
		//File downFile=recvFile(filePath, fileName);
		//ResponseEntity<byte[]> respEntity = null;
		//HttpHeaders headers = new HttpHeaders(); 
		//headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
		//headers.setContentDispositionFormData("attachment", fileName); 
	    //需要保存的地址
		//如果在同一台服务器上感觉有问题，不好测试，则修改一下路径
    	String path=StringUtils.delete(filePath, fileName);
    	String downPath=path+File.separator+"view"+File.separator+fileName;
    	System.out.println("下载路径为："+downPath);
	    File downFile=new File(downPath);
	    ResultEntity result=new ResultEntity();
		try {
			if (downFile != null && downFile.exists()) {
				InputStream fis = new BufferedInputStream(new FileInputStream(downFile));
				response.setContentType("application/x-download");
				try {
					response.addHeader("Content-Disposition","attachment;filename="+ new String(fileName.getBytes(), "iso-8859-1"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				response.addHeader("Content-Length", "" + downFile.length());
				OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
				response.setContentType("application/octet-stream");
				byte[] buffer = new byte[1024 * 1024];
				int i = -1;
				while ((i = fis.read(buffer)) != -1) {
					toClient.write(buffer, 0, i);
				}
				toClient.flush();
				toClient.close();
				fis.close();
				result.setState(true);
	            result.setRetMessage(fileName);
			}else{
				  PrintWriter out = response.getWriter();   
	              out.print("<script>");   
	              out.print("alert(\"not find the file\")");   
	              out.print("</script>");
	              result.setState(false);
	              result.setRetMessage("没有找到文件");
			}
			SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_DOWNLOAD_BAKUP);
		} catch (Exception e) {
			LogUtils.error(e);
			e.printStackTrace();
		}
	}
	
	private static String GetBusUrl(){
		String developer_model=PropertiesUtil.getConfigPropValueAsText("developer_model");
		if("true".equals(developer_model)){
			return PropertiesUtil.getConfigPropValueAsText("cloudbusinessservice_device_url_developer");
		}
		if("false".equals(developer_model)){
			return PropertiesUtil.getConfigPropValueAsText("cloudbusinessservice_device_url");
		}
		return PropertiesUtil.getConfigPropValueAsText("cloudbusinessservice_device_url");	
	}
	//
	private static File recvFile(String filePath,String fileName){
		//如果在同一台服务器上感觉有问题，不好测试，则修改一下路径
    	String path=StringUtils.delete(filePath, fileName);
		File file = new File(path+File.separator+"view");
		File filep = new File(path);
		if(!filep.exists()){
		   if(!filep.mkdirs()){
			   throw new RuntimeException("创建路径失败");
		   }
	    }
	    if(!file.exists()){
		  boolean isMk=file.mkdir();
		  if(!isMk) throw new RuntimeException("创建文件夹失败");
	    }
	    //需要保存的地址
	    File relFile=new File(path+File.separator+"view"+File.separator+fileName);
	    if(relFile.exists()){
		    System.out.println("^^^^^^^^ relFile 存在 ^^^^^^^^");
		    //存在则,则直接返回供下载
		    return relFile;
	    }
	    
		String url=GetBusUrl();
		String ip=StringUtils.delete(url.split(":")[1], "//");
		System.out.println("cloudbusinessservice_device ip:"+ip);
		Socket socket = null;
		try {
			socket=new Socket(ip, 33456);
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
		DataOutputStream dos = null;
		BufferedOutputStream bos = null; //What can BufferedOutputStream help ? 
		BufferedInputStream dis=null;
		byte[] bytes = new byte[1024*1024];
		try {
			try {
				/*
				 * new a File with the filePath
				 * new a FileInputStream with the File to read the file by byte
				 * new a BufferedInputStream with the FileInputStream to use buffered stream
				 */
				dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				//首先发送文件名     客户端发送使用writeUTF方法，服务器端应该使用readUTF方法
				dos.writeUTF(filePath);//bus服务器，真实文件路径
				dos.flush();
				bos=new BufferedOutputStream(new FileOutputStream(relFile));
				dis=new BufferedInputStream(socket.getInputStream());
				int length = 0;
				while ((length = dis.read(bytes, 0, bytes.length)) > 0) {
					bos.write(bytes, 0, length);
					bos.flush();
				}
				return relFile;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				//使用完毕后，应关闭输入、输出流和socket
				if(dis != null) dis.close();
				if(bos != null) bos.close();
				if(dos != null) dos.close();
				if(socket != null) socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
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
                  
                    //如果名称不为"",说明该文件存在，否则说明该文件不存在  
                    if(myFileName!=null&&myFileName.trim() !=""){
                        //上传后的文件名  
                        String fileName = file.getOriginalFilename();  
                        //定义上传路径  
                        //String path = "E:/" + fileName;  
                        //保存到临时缓存中
                        String path=System.getProperty("java.io.tmpdir");
                        File localFile = new File(path+File.separator+fileName); 
                        //如果临时文件中存在则强制删除
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
                        if(localFile.exists()){
                        	if(FileUtil.isZipFile(localFile)){
                        		String res= HttpClientUtil.postUpload(dataBaseService.getUrl("bakUpload"), localFile,null);
                            	if(JSONUtils.mayBeJSON(res)){
                            		 result=JsonUtils.fromJson(res, ResultEntity.class);
                            		 //upload success
                            		 result.setRetMessage(localFile.getName());
                            		 SystemLogUtil.LogOperation(result, getCurrentUser(), request, Const.OPERCMD_UPLOAD_BAKUP);
                            		 
                            	}
                        	}else{
                        		result.setMessage("请上传压缩文件！文件格式说明：|一、前缀： | (1)ssitcloud_authentication_| (2)ssitcloud_device_| (3)ssitcloud_mongo_[数据库名称]_。|"+
                        						   "二、时间部分：年月日时分秒(yyyyMMddHHmmss格式)。|三、后缀：.zip。|"+" 文件格式例如 :ssitcloud_authentication_20160521174700.zip");
                        	}
                        }
                    }  
                }  
                //记录上传该文件后的时间  
                long finaltime = System.currentTimeMillis();
                System.out.println("上传耗时(秒)："+(finaltime-pre)/1000);
            }  
        }  
        return result;  
    }
	/**
	 * 获取在clouddbservice_devicemonitor 的client.json配置文件中定义的数据库名称<br>
	 * 返回的结果为 result为List<String>类型<br>
	 * @param request
	 * @param req
	 * @return
	 */
	//可以考虑按服务器分
	@RequestMapping("/getMongodbNames")
	@ResponseBody
	public ResultEntity getMongodbNames(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=dataBaseService.getMongodbNames(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 
	 * var libInfo=
	 * {
			"library_idx":library_idx,
			"library_id":library_id
		};
	 * 根据Library_idx备份
	 * @param request
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("bakupByLibraryIdx")
	@ResponseBody
	public ResultEntity bakupByLibraryIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			//校验用户角色权限
			Operator oper=getCurrentUser();
			if(Operator.SSITCLOUD_ADMIN.equals(oper.getOperator_type())
					||Operator.SSITCLOUD_MANAGER.equals(oper.getOperator_type())){
			}else{
				if(JSONUtils.mayBeJSON(req)){
					Map<String, Object> libInfo=JsonUtils.fromJson(req, Map.class);
					if(libInfo.containsKey("library_idx")){
						String library_idx=libInfo.get("library_idx").toString();
						if(!oper.getLibrary_idx().equals(library_idx)){
							result.setRetMessage("用户图书馆IDX和传入参数IDX不符");
							return result;
						}
					}
					if(libInfo.containsKey("library_id")){
						String library_id=libInfo.get("library_id").toString();
						if(!oper.getLib_id().equals(library_id)){
							result.setRetMessage("用户图书馆ID和传入参数ID不符");
							return result;
						}
					}
				}
			}
			result=dataBaseService.bakupByLibraryIdx(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}
	/**
	 * 
	 * @return
	 */
	@RequestMapping("restoreDataByLibraryIdx")
	@ResponseBody
	public ResultEntity restoreDataByLibraryIdx(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=dataBaseService.restoreDataByLibraryIdx(req);
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
	@RequestMapping("checkBakUpFileIfExsit")
	@ResponseBody
	public ResultEntity checkBakUpFileIfExsit(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=dataBaseService.checkBakUpFileIfExsit(req);
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
		ResultEntity result=new ResultEntity();
		try {
			result=dataBaseService.deleteLibBakup(req);
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
	@RequestMapping("getLastLibBakUpTime")
	@ResponseBody
	public ResultEntity getLastLibBakUpTime(HttpServletRequest request,String req){
		ResultEntity result=new ResultEntity();
		try {
			result=dataBaseService.getLastLibBakUpTime(req);
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return result;
	}

}
