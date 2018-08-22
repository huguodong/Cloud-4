package com.ssitcloud.business.devregister.service.impl;

import java.io.File;
import java.io.FileOutputStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.JSchUtils;
import com.ssitcloud.business.common.util.LogUtils;
import com.ssitcloud.business.devregister.param.DeviceRegisterParam;
import com.ssitcloud.business.devregister.service.FtpBusinessService;

@Service
public class FtpBusinessServiceImpl extends BasicServiceImpl implements FtpBusinessService {
	
	//云服务器脚本地址
	@Value("${SH_PATH}")
	public String SH_PATH;
	
	//ftp服务器脚本地址
	@Value("${FTP_SH_PATH}")
	public String FTP_SH_PATH;
	
	@Value("${ftp_ip}")
	public String ftp_ip;
	
	@Value("${ftp_username}")
	public String ftp_username;
	
	@Value("${ftp_passwd}")
	public String ftp_passwd;
	
	@Override
	public Boolean createFtpUser(DeviceRegisterParam param) {
		File fileD = new File(SH_PATH);
		if(!fileD.exists()  && !fileD.isDirectory()){
			fileD.mkdirs();  
		}
		
		String fileName = param.getDeviceId()+".sh";
		String path = SH_PATH+"/"+fileName;
		if(param.getDeviceCode().length()<8){
			return false;
		}
		String cmd = "useradd -s /sbin/nologin "+param.getDeviceId()+";echo "+param.getDeviceCode().substring(0, 8)+"|passwd --stdin "+param.getDeviceId();
		if(writeToFile(path,cmd)){
			try {
				// 连接
				JSchUtils.newInstance(ftp_ip, ftp_username, ftp_passwd);
				//文件上传
				JSchUtils.upload(FTP_SH_PATH, path);
				//授权
				JSchUtils.exec("chmod 777 "+FTP_SH_PATH+"/"+param.getDeviceId()+".sh");
				//执行
				JSchUtils.shell(FTP_SH_PATH+"/"+param.getDeviceId()+".sh \n");
				//删除
				JSchUtils.exec("rm -rf "+FTP_SH_PATH+"/"+param.getDeviceId()+".sh");
				return true;
			} catch (Exception e) {
				String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				LogUtils.error(methodName + "() 发生异常--" + e.getMessage());
				return false;
			}
		}
		return false;
	}

	public Boolean writeToFile(String path, String context){
		try{
			File file = new File(path);
			
			if(file.isFile() && file.exists()){
				file.delete();
			}
			
			file.createNewFile();
			FileOutputStream fop =  new FileOutputStream(file);
			byte[] contentInBytes = context.getBytes();
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
			
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public Boolean delFtpUser(String req) {
		JSONArray arr = JSONArray.fromObject(req);
		for(int i=0;i<arr.size();i++){
			JSONObject json = JSONObject.fromObject(arr.getString(i));
			String device_id = json.getString("device_id");
			try {
				// 连接
				JSchUtils.newInstance(ftp_ip, ftp_username, ftp_passwd);
				//删除用户
				JSchUtils.exec("userdel -r "+device_id);
				LogUtils.info("成功删除ftp用户:"+device_id);
				
			} catch (Exception e) {
				String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				LogUtils.error(methodName + "() 发生异常--" + e.getMessage());
				continue;
			}
		}
		return true;
	}
	
}
