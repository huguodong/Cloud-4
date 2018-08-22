package com.ssitcloud.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

public class FtpUtils {
	
	public static Integer defaultPort=21;
	public static String defaultUsername="admin";
	public static String defaultPassword="admin";
	public static String defaultftp="106.75.49.132";
	public static String versionXml="escalation_strategy.xml";
	public static String remotePath="/admin";
	public static String localPath="/usr/project/escalation_strategy/";
	static{
		try {
			Properties prop=PropertiesLoaderUtils.loadProperties(new EncodedResource(new ClassPathResource("config/static-config.properties")));
			defaultPort=Integer.parseInt(prop.get("defaultPort").toString());
			defaultUsername=prop.get("defaultUsername").toString();
			defaultPassword=prop.get("defaultPassword").toString();
			defaultftp=prop.get("defaultftp").toString();
			versionXml=prop.get("versionXml").toString();
			remotePath=prop.get("remotePath").toString();
			localPath=prop.get("localPath").toString();
		} catch (IOException e) {
			LogUtils.error("config/static-config.properties FTP 配置异常！！！",e);
		}
	}
	
	public static void main(String[] args) {
		String sss="D:\\SoftWare\\jboss-as-7.1.0.Final\\standalone\\deployments\\cloudbusinessservice_datasyn.war\\WEB-INF\\classes\\config";
		downVersionXmlFromFtp();
	}
	/**
	 * 将升级策略下载到classpath下 config的文件夹中
	 * @methodName: downVersionXmlFromFtp
	 * @return
	 * @returnType: boolean
	 * @author: liuBh
	 */
	public static boolean downVersionXmlFromFtp(){
		//LogUtils.info("下载XML保存路径："+ResourcesUtil.getFile("/config").getPath());

		boolean b=downFile(defaultftp, defaultPort, defaultUsername, defaultPassword, remotePath, versionXml, ResourcesUtil.getFile("/config").getPath(),versionXml);
		return b;
	}
	/**
	 * 
	 * @param version
	 * @return Filepath
	 */
	public static synchronized String downVersionXmlFromFtp(String version){
		//LogUtils.info("下载XML保存路径："+ResourcesUtil.getFile("/config").getPath());
		String path = localPath+version;
		File f=new File(path);
		if(!f.exists()){
			if(!f.mkdirs()){
				LogUtils.error("创建路径失败:"+f.getPath());
				return null; 
			}
		}
		//File ff=new File(path+"/"+versionXml);
		boolean b=downFile(defaultftp, defaultPort, defaultUsername, defaultPassword, remotePath+"/"+version, versionXml, path,versionXml);
		if(b){
			System.out.println("downloadFile:"+path+"/"+versionXml);
			return path+"/"+versionXml;
		}
		return null;
	}
	/**   
     * Description: 从FTP服务器下载文件   
     * @Version1.0   
     * @param url FTP服务器hostname   
     * @param port FTP服务器端口   
     * @param username FTP登录账号   
     * @param password FTP登录密码   
     * @param Path FTP服务器上的相对路径   
     * @param fileName 要下载的文件名   
     * @param localPath 下载后保存到本地的路径   
     * @return   
     */    
    public static boolean downFile(  
            String url, //FTP服务器hostname  
            int port,//FTP服务器端口  
            String username, //FTP登录账号  
            String password, //FTP登录密码  
            String path,//FTP服务器上的相对路径   
            String fileName,//要下载的文件名  
            String localPath,//下载后保存到本地的路径  
            String localFileName
            ) {    
        boolean success = false;    
        FTPClient ftp = new FTPClient();
        try {    
            int reply;    
            //这个方法的意思就是每次数据连接之前，ftp client告诉ftp server开通一个端口来传输数据。
            //为什么要这样做呢，因为ftp server可能每次开启不同的端口来传输数据，但是在linux上或者其他服务器上面，
            //由于安全限制，可能某些端口没有开启，所以就出现阻塞。
            ftp.enterLocalActiveMode();
            ftp.setRemoteVerificationEnabled(false);
            
            ftp.connect(url, port);  
            
            //下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
            ftp.setControlEncoding("UTF-8");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode("zh");
     
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器     
            boolean isLogin=ftp.login(username, password);//登录     
            if(!isLogin){
            	LogUtils.info("is Login 登陆失败[username:"+username+",password:"+password+"]");
            }
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {    
                ftp.disconnect();    
                return success;    
            }    
            ftp.changeWorkingDirectory(path);//转移到FTP服务器目录    
            ftp.enterLocalPassiveMode();
            FTPFile[] fs = ftp.listFiles(fileName); 
            File localFile = new File(localPath+"/"+localFileName);
            if (fs.length != 1) {  
                System.out.println("远程文件不唯一"); 
                ftp.disconnect();    
                return false;  
            }  
            long lRemoteSize = fs[0].getSize();  
            if (localFile.exists()) {  
                OutputStream out = new FileOutputStream(localFile, true);  
                System.out.println("本地文件大小为:" + localFile.length());  
                if (localFile.length() > lRemoteSize) {  
                    System.out.println("本地文件大小大于远程文件大小，下载中止");  
                    ftp.disconnect();    
                    return false;  
                }else{
                	ftp.setRestartOffset(localFile.length());  
                    if(ftp.retrieveFile(fs[0].getName(), out)){
                    	success = true;
                    }else{
                    	//断点续传失败，删除文件重新下载
                    	localFile.delete();
                    	out = new FileOutputStream(localFile);  
                    	success = ftp.retrieveFile(fs[0].getName(), out);  
                    }
                    out.close();  
                }
                
            } else {  
                OutputStream out = new FileOutputStream(localFile);  
                success = ftp.retrieveFile(fs[0].getName(), out);  
                out.close();  
            }  
            ftp.logout();    
        } catch (IOException e) {    
        	LogUtils.error("ftp.download 下载文件失败  原因："+e.getMessage());    
        } finally {    
            if (ftp.isConnected()) {    
                try {    
                    ftp.disconnect();    
                } catch (IOException ioe) {
                	LogUtils.error("ftp.disconnect ",ioe);
                }    
            }    
        }    
        return success;    
    }

    
    public static synchronized String downOsbDataFromFTP(String ip,Integer port,String username,String passwd,String path,String fileName,String localPath,String localFileName){
    	
    	if(StringUtils.isEmpty(ip) || StringUtils.isEmpty(port)){
    		ip = defaultftp;
    		port = defaultPort;
    	}
    	if(StringUtils.isEmpty(username) || StringUtils.isEmpty(passwd)){
    		username = defaultUsername;
    		passwd = defaultPassword;
    	}
    	
		File f=new File(localPath);
		if(!f.exists()){
			if(!f.mkdirs()){
				LogUtils.error("创建路径失败:"+f.getPath());
				return null; 
			}
		}
		String linuxpath = path.substring(1);
		boolean b=downFile(ip, port, username, passwd, linuxpath, fileName, localPath, localFileName);
		if(b){
			System.out.println("downloadFile:"+localPath+"/"+localFileName);
			return localPath+"/"+localFileName;
		}
		return null;
	}
    
    
    public static synchronized String uploadOsbDataToFTP(String ip,Integer port ,String username,String passwd,String path,File file){
    	
    	if(StringUtils.isEmpty(ip) || StringUtils.isEmpty(port)){
    		ip = defaultftp;
    		port = defaultPort;
    	}
    	if(StringUtils.isEmpty(username) || StringUtils.isEmpty(passwd)){
    		username = defaultUsername;
    		passwd = defaultPassword;
    	}
    	String linuxpath = path.substring(1);
		boolean b=uploadFile(defaultftp, defaultPort, username, passwd, linuxpath, file.getName(), file);
		if(b){
			System.out.println("uploadOsbDataToFTP:"+path+"/"+file.getName());
			return path+"/"+file.getName();
		}
		return null;
	}
    
    public static boolean uploadFile(  
            String url, //FTP服务器hostname  
            int port,//FTP服务器端口  
            String username, //FTP登录账号  
            String password, //FTP登录密码  
            String path,//FTP服务器上的相对路径   
            String fileName,
            File file
            ) {    
        boolean success = false;    
        FTPClient ftp = new FTPClient();
        try {    
            int reply;    
            //这个方法的意思就是每次数据连接之前，ftp client告诉ftp server开通一个端口来传输数据。
            //为什么要这样做呢，因为ftp server可能每次开启不同的端口来传输数据，但是在linux上或者其他服务器上面，
            //由于安全限制，可能某些端口没有开启，所以就出现阻塞。
            ftp.enterLocalActiveMode();
            ftp.setRemoteVerificationEnabled(false);
            
            ftp.connect(url, port);  
            
            //下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
            ftp.setControlEncoding("UTF-8");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode("zh");
     
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器     
            boolean isLogin=ftp.login(username, password);//登录     
            if(!isLogin){
            	LogUtils.info("is Login 登陆失败[username:"+username+",password:"+password+"]");
            }
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {    
                ftp.disconnect();    
                return success;    
            }
            //http://blog.csdn.net/youkimra/article/details/5288894
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.changeWorkingDirectory(path);//转移到FTP服务器目录     
            
            // 检查远程是否存在文件 
            FTPFile[] files = ftp.listFiles(fileName);  
            if (files.length == 1) { 
            	long remoteSize = files[0].getSize();
        		long localSize = file.length();
        		if(remoteSize==localSize){
        			System.out.println("ftp目录已存在文件");
        			ftp.disconnect();    
        			return false;
        		}else if(remoteSize > localSize){
        			System.out.println("已上传的文件大于本地文件");
        			ftp.disconnect();    
        			return false;
        		}
        		// 尝试移动文件内读取指针,实现断点续传  
                InputStream is = new FileInputStream(file);  
                if (is.skip(remoteSize) == remoteSize) {  
                    ftp.setRestartOffset(remoteSize);  
                    if (ftp.storeFile(fileName, is)) {  
                    	is.close();
                    	ftp.disconnect();    
                        return true;
                    }  
                }  
     
                // 如果断点续传没有成功，则删除服务器上文件，重新上传  
                if (!ftp.deleteFile(fileName)) {  
                	System.out.println("删除失败");
                   return false;
                }  
                is = new FileInputStream(file);  
                if (ftp.storeFile(fileName, is)) {  
                	is.close();  
                	ftp.disconnect();    
                    return true;
                } else {  
                	is.close();  
                	ftp.disconnect();    
                    return false;
                }
            }
            
            FileInputStream input = new FileInputStream(file);
            ftp.enterLocalPassiveMode();
            if(ftp.storeFile(fileName, input)){
            	success = true; 
            };
            input.close();
            ftp.logout();      
        } catch (IOException e) {    
        	LogUtils.error("ftp.upload 上传文件失败  原因："+e.getMessage());    
        } finally {    
            if (ftp.isConnected()) {    
                try {    
                    ftp.disconnect();    
                } catch (IOException ioe) {
                	LogUtils.error("ftp.disconnect ",ioe);
                }    
            }    
        }    
        return success;    
    }
    
    
    public static synchronized String createDirectory(String username,String passwd,String path){
    	
    	if(StringUtils.isEmpty(username) || StringUtils.isEmpty(passwd)){
    		username = defaultUsername;
    		passwd = defaultPassword;
    	}
    	
    	path = remotePath+path;
    	String linuxpath = path.substring(1);//linux环境ftp目录格式第一个不带/
        FTPClient ftp = new FTPClient();
        try {    
            int reply;    
            //这个方法的意思就是每次数据连接之前，ftp client告诉ftp server开通一个端口来传输数据。
            //为什么要这样做呢，因为ftp server可能每次开启不同的端口来传输数据，但是在linux上或者其他服务器上面，
            //由于安全限制，可能某些端口没有开启，所以就出现阻塞。
            ftp.enterLocalActiveMode();
            ftp.setRemoteVerificationEnabled(false);
            
            ftp.connect(defaultftp, defaultPort);  
            
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器     
            boolean isLogin=ftp.login(username, passwd);//登录     
            if(!isLogin){
            	LogUtils.info("is Login 登陆失败[username:"+username+",password:"+passwd+"]");
            	return null;
            }
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {    
                ftp.disconnect();    
                return null;    
            }
            //http://blog.csdn.net/youkimra/article/details/5288894
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            if(!ftp.changeWorkingDirectory(linuxpath)){
            	String[] dd = linuxpath.split("/");
        		String ttt = dd[0];
            	for(int i =0;i<dd.length;i++){
            		if(!ftp.changeWorkingDirectory(dd[i])){
            			if(ftp.makeDirectory(dd[i])){
            				if(!ftp.changeWorkingDirectory(dd[i])){//转移到FTP服务器目录
            					return null;
            				}
            			}else{
            				LogUtils.error("创建ftp服务目录失败，目录路径:/"+ttt);
            				return null;
            			}
            		}
            		ttt = ttt + "/" + dd[i];
            	}
            }
            return "ftp://"+defaultftp+path+"/";
        } catch (IOException e) {    
        	LogUtils.error("请求连接ftp服务失败  原因："+e.getMessage());    
        	return null;
        } finally {    
            if (ftp.isConnected()) {    
                try {    
                    ftp.disconnect();    
                } catch (IOException ioe) {
                	LogUtils.error("ftp.disconnect ",ioe);
                }    
            }    
        }    
	}
    
}
