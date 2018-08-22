package upgrades.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtils {
	
	public static Integer defaultPort=21;
	public static String defaultUsername="admin";
	public static String defaultPassword="admin";
	public static String ftphost="106.75.49.132";
	public static String versionXml="escalation_strategy.xml";
	public static String remotePath="/admin";
	public static String localPath="/usr/project/escalation_strategy/";
	static{
		ResourceBundle bundle=ResourceBundle.getBundle("config");
		//保存在本地路径
		localPath=bundle.getString("localPath");
		defaultUsername=bundle.getString("defaultUsername");
		defaultPassword=bundle.getString("defaultPassword");
		//ftp path
		ftphost=bundle.getString("ftphost");
	}
	/**
	 * 
	 * @param files
	 * @return
	 */
	public static boolean  uploadFileUpgrades(File[] files,String PatchVersion){
		return uploadFile(ftphost, defaultPort, defaultUsername, defaultPassword, remotePath, files, localPath,PatchVersion);
	}
	
	/**   
     * Description: 从FTP服务器下载文件   
     * @Version1.0   
     * @param url FTP服务器hostname   
     * @param port FTP服务器端口   
     * @param username FTP登录账号   
     * @param password FTP登录密码   
     * @param remotePath FTP服务器上的相对路径   
     * @param fileName 要下载的文件名   
     * @param localPath 下载后保存到本地的路径   
     * @return   
     */    
    public static boolean uploadFile(  
            String url, //FTP服务器hostname  
            int port,//FTP服务器端口  
            String username, //FTP登录账号  
            String password, //FTP登录密码  
            String remotePath,//FTP服务器上的相对路径   
            File[] files,//要upload的文件名  
            String localPath,//下载后保存到本地的路径
            String PatchVersion
            ) {    
        boolean success = false;    
        FTPClient ftp = new FTPClient();
        InputStream in = null;
        try {    
            int reply;    
            //这个方法的意思就是每次数据连接之前，ftp client告诉ftp server开通一个端口来传输数据。
            //为什么要这样做呢，因为ftp server可能每次开启不同的端口来传输数据，但是在linux上或者其他服务器上面，
            //由于安全限制，可能某些端口没有开启，所以就出现阻塞。
            ftp.enterLocalPassiveMode();
            ftp.setRemoteVerificationEnabled(false);
            ftp.connect(url, port);  
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器     
            boolean isLogin=ftp.login(username, password);//登录     
            if(!isLogin){
            	return false;
            }
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {    
                ftp.disconnect();    
                return success;    
            }
            //上传
            if(files!=null && files.length>0){
            	for(File f:files){
            	  if(f.isHidden()){
            		  continue;
            	  }
        		  in=new FileInputStream(f);
        		  System.out.println(remotePath+"/"+PatchVersion+"/");
        		  if (!ftp.changeWorkingDirectory(remotePath+"/"+PatchVersion+"/")) {
        			  if(!ftp.makeDirectory(remotePath+"/"+PatchVersion+"/")){
        				  System.out.println("上传数据到FTP失败：");
        			  }
        		  }
        		  ftp.setControlEncoding("UTF-8");//这里设置编码
        		  ftp.changeWorkingDirectory(remotePath+"/"+PatchVersion+"/");
        		  boolean isUp=ftp.storeFile(new String(f.getName().getBytes("UTF-8"),"iso-8859-1"), in);
        		  if(!isUp){
        			  System.out.println("上传数据到FTP失败："+f.getPath());
        		  }
            	}
            }
            ftp.logout();    
            success = true;    
        } catch (IOException e) {
        	e.printStackTrace();
        } finally {    
            if (ftp.isConnected()) {    
                try {    
                    ftp.disconnect();    
                } catch (IOException ioe) {
                	ioe.printStackTrace();
                }    
            }  
            if(in!=null){
            	try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
            
        }    
        return success;    
    }  
}
