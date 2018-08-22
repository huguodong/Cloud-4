package com.ssitcloud.business.nodemgmt.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.Consts;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.nodemgmt.service.AcsConfigService;
import com.ssitcloud.business.nodemgmt.service.FileManagerService;
import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.utils.HttpClientUtil;
import com.ssitcloud.datasync.entity.DeviceAcsModuleEntity;
import com.ssitcloud.datasync.entity.ProtocolInfo;
import com.ssitcloud.devmgmt.entity.FileUploadState;
import com.ssitcloud.node.entity.FileManagerEntity;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;

import net.sf.json.util.JSONUtils;

@Service
public class AcsConfigServiceImpl extends BasicServiceImpl implements AcsConfigService{
	@Resource
	private FileManagerService fileService;

	/**
	 * 上传acs文件到文件服务器
	 */
	public ResultEntity uploadAcsConfig(String req) {
		ResultEntity entity = new ResultEntity();
		String basePath = System.getProperty("java.io.tmpdir")+File.separator+"ACSCONFIG";
		String fileType = "xml";
		try{
			if(StringUtils.isEmpty(req)) return entity;
			List<DeviceAcsModuleEntity> acsModuleEntities = JsonUtils.fromJson(req,new TypeReference<List<DeviceAcsModuleEntity>>(){});
			for(DeviceAcsModuleEntity acsModuleEntity : acsModuleEntities){
				String device_id = JedisUtils.getInstance().get(RedisConstant.DEVICE_KEYS+acsModuleEntity.getDevice_idx());
				if(StringUtils.isEmpty(device_id)) continue;
				String libarary_id = JedisUtils.getInstance().get(RedisConstant.LIBRARY_KEYS+acsModuleEntity.getLibrary_idx());
				String fileName = device_id+"."+fileType;
				String path = basePath + File.separator+libarary_id+File.separator+fileName;
				File file = writeToFile(path,acsModuleEntity);
				FileManagerEntity fileManagerEntity = new FileManagerEntity();
				fileManagerEntity.setFile_name(fileName);
				fileManagerEntity.setDevice_id(device_id);
				fileManagerEntity.setLibrary_id(libarary_id);
				fileManagerEntity.setFile_size(file.length());
				ResultEntity resultEntity = fileService.uploadFile(new FileInputStream(file),fileManagerEntity);
				return resultEntity;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return entity;
	}
	
	
	public File writeToFile(String path, DeviceAcsModuleEntity deviceAcsModule) {
		try {
			
			if(deviceAcsModule == null) return null;
			
			File file = new File(path);
			
			if(!file.exists()){
				File dir = new File(file.getParent());
				dir.mkdirs();
			}
			
			if (file.isFile() && file.exists()) {
				file.delete();
			}
			
			file.createNewFile();
			
			FileOutputStream fop = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fop, "UTF-8");
	
			ProtocolInfo protocolInfo;
			
			String xmlInnerStr = "";
			String modifyTime = "";
			String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ever>";
			String protocolType = "";
			if(deviceAcsModule.getProtocol_type() != null){
				protocolType = deviceAcsModule.getProtocol_type() == 1 ?"sip2":"ncip";
			}
			List<ProtocolInfo> protocolInfos = deviceAcsModule.getProtocolInfos();
			Iterator<ProtocolInfo> iterator = protocolInfos.iterator();
			while (iterator.hasNext()) {
				protocolInfo = (ProtocolInfo) iterator.next();
				xmlInnerStr += "<protocol";
				xmlInnerStr += "  commandkey=\"" + protocolInfo.getProtocol_description() + "\"";
				xmlInnerStr += "  commandname=\"" + protocolInfo.getProtocol_show() + "\"";
				xmlInnerStr += "  description=\"" + protocolInfo.getProtocol_field_name() + "\"";
				xmlInnerStr += ">";
				xmlInnerStr += protocolInfo.getProtocol_reqrule();
				xmlInnerStr += protocolInfo.getProtocol_resprule();
				xmlInnerStr += "";
				xmlInnerStr += "</protocol>";
				modifyTime = String.valueOf(protocolInfo.getUpdatetime());
			}

			xmlStr += "<protocols";
			xmlStr += "  acsType=\"" + protocolType + "\"";
			xmlStr += "  acsName=\"" + deviceAcsModule.getAcs_service_name() + "\"";
			xmlStr += "  modifiedTime=\"" + modifyTime + "\"";
					
			if (2 == deviceAcsModule.getProtocol_type()) {
				xmlStr += "  connector=\"IP=" + deviceAcsModule.getLogin_ip() + "|Port=" + deviceAcsModule.getLogin_port() + "|Account=" + deviceAcsModule.getLogin_username() + "|Pwd="
				+ deviceAcsModule.getLogin_pwd() + "\"";
			}
			if (1 == deviceAcsModule.getProtocol_type()) {
				xmlStr += "  connector=\"IP=" + deviceAcsModule.getLogin_ip() + "|Port=" + deviceAcsModule.getLogin_port() + "|Account=" + deviceAcsModule.getLogin_username() + "|Pwd="
				+ deviceAcsModule.getLogin_pwd() + "|LoginType=" + deviceAcsModule.getLogin_type() + "|ReLogin=" + deviceAcsModule.getLogin_count() + "|NeedLogin="
				+ deviceAcsModule.getLogin_check() + "|Charset=" + deviceAcsModule.getLogin_code() + "\"";
			}
			xmlStr += ">";
			xmlStr += xmlInnerStr;
			xmlStr += "</protocols>";
			xmlInnerStr = "";
			xmlStr += "</ever>";
			osw.write(xmlStr);
			osw.flush();
			osw.close();
			return file;
		} catch (Exception e) {
			return null;
		}
	}
	
}
