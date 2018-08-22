package com.ssitcloud.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.Consts;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.AcsConfigDao;
import com.ssitcloud.dao.DeviceAcsLoginInfoDao;
import com.ssitcloud.dao.DeviceDao;
import com.ssitcloud.datasync.entity.DeviceAcsModuleEntity;
import com.ssitcloud.datasync.entity.ProtocolInfo;
import com.ssitcloud.entity.ACSProtocolEntity;
import com.ssitcloud.entity.DeviceAcsLoginInfoEntity;
import com.ssitcloud.entity.LibSelfcheckProtocolConfigEntity;
import com.ssitcloud.entity.MetadataProtocolEntity;
import com.ssitcloud.entity.ProtocolConfigDataObjEX1Entity;
import com.ssitcloud.entity.ProtocolConfigDataObjEntity;
import com.ssitcloud.entity.ProtocolConfigTemplateEntity;
import com.ssitcloud.entity.SelfCheckProtocolEntity;
import com.ssitcloud.entity.page.LibSelfcheckProtocolPageEntity;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;
import com.ssitcloud.service.AcsConfigService;
import com.ssitcloud.service.DeviceAcsLoginInfoService;

@Service
public class AcsConfigServiceImpl implements AcsConfigService{

	@Resource
	private AcsConfigDao acsConfigDao;
	@Resource
	private DeviceAcsLoginInfoDao acsLoginInfoDao;
	@Resource
	private DeviceDao deviceDao;
	@Resource
	private DeviceAcsLoginInfoDao loginInfoDao;
	@Resource
	private DeviceAcsLoginInfoService loginInfoService;
	@Resource(name="requestURL")
	protected  RequestURLListEntity requestURL;
	@Override
	public LibSelfcheckProtocolPageEntity queryAcsConfigByparam(String req) {
		
		if(JSONUtils.mayBeJSON(req)){
			LibSelfcheckProtocolPageEntity libSelfcheckProtocolPage=JsonUtils.fromJson(req, LibSelfcheckProtocolPageEntity.class);
			if(libSelfcheckProtocolPage!=null){
				LibSelfcheckProtocolPageEntity libSelfcheckProtocol=acsConfigDao.queryAcsConfigByparam(libSelfcheckProtocolPage);
				return libSelfcheckProtocol;
			}
		}
		return null;
	}

	@Override
	public ProtocolConfigTemplateEntity queryAcsConfigByparamEX1(String req) {
		if(JSONUtils.mayBeJSON(req)){
			ProtocolConfigTemplateEntity protocolConfigTemplate=JsonUtils.fromJson(req, ProtocolConfigTemplateEntity.class);
			if(protocolConfigTemplate!=null){
				ProtocolConfigTemplateEntity q=acsConfigDao.queryAcsConfigByparamEX1(protocolConfigTemplate);
				return q;
			}
		}
		return null;
	}
	
	@Override
	public List<LibSelfcheckProtocolConfigEntity> queryAllProtocolConfig(
			String req) {
		return acsConfigDao.queryAllProtocolConfig();
	}

	@Override
	public ResultEntity addProtocolConfig(String req) {
		ResultEntity result=new ResultEntity();
		int insertNum=0;
		if(JSONUtils.mayBeJSON(req)){
			ProtocolConfigDataObjEntity protocolConfigDataObj=JsonUtils.fromJson(req, ProtocolConfigDataObjEntity.class);
			if(protocolConfigDataObj!=null){
				Integer libraryIdx=protocolConfigDataObj.getLibrary_idx();
				Integer protocolType=protocolConfigDataObj.getProtocol_type();
				String protocol_tpl_desc=protocolConfigDataObj.getProtocol_tpl_desc();
				Assert.notNull(libraryIdx, "图书管IDX不能为空");
				Assert.notNull(protocolType, "指令类型不能为空[SIP2/NCIP]");
				Assert.notNull(protocol_tpl_desc, "模板名成不能为空");
				
				ProtocolConfigTemplateEntity protocolConfigTemplate=new ProtocolConfigTemplateEntity();
				protocolConfigTemplate.setLibrary_idx(libraryIdx);
				protocolConfigTemplate.setProtocol_type(protocolType);
				protocolConfigTemplate.setProtocol_tpl_desc(protocol_tpl_desc);
				try {
					insertNum=acsConfigDao.addProtocolConfigTemplate(protocolConfigTemplate);
				} catch (org.springframework.dao.DuplicateKeyException e) {
					String msg=e.getRootCause().getMessage();
					AntPathMatcher matcher=new AntPathMatcher();
					if(matcher.match("*protocol_tpl_desc*", msg)){
						throw new RuntimeException("组名已经被占用，请修改");
					}else{
						throw new RuntimeException(e);
					}			
				}
				if(insertNum<=0){
					throw new RuntimeException("新增模板失败");
				}
				Integer protocol_tpl_idx=protocolConfigTemplate.getProtocol_tpl_idx();
				Assert.notNull(protocol_tpl_idx, "protocol_tpl_idx不能为NULL");
				List<SelfCheckProtocolEntity> selfCheckProtocols=protocolConfigDataObj.getProtocol_config_arr();
				if(selfCheckProtocols!=null){
					for(SelfCheckProtocolEntity selfCheckProtocolEntity:selfCheckProtocols){
						selfCheckProtocolEntity.setProtocol_idx(protocol_tpl_idx);
					}
					insertNum=acsConfigDao.addProtocolConfigBatch(selfCheckProtocols);
					if(insertNum!=selfCheckProtocols.size()){
						throw new RuntimeException("新增模板数据失败");
					}
				}
				result.setState(true);
				//馆IDX｜协议IDX｜协议内容
				result.setRetMessage("馆IDX:"+libraryIdx+"｜协议IDX:"+protocol_tpl_idx+"｜协议名称:"+protocol_tpl_desc+"||");
			}
		}
		return result;
	}
	
	@Override
	public ResultEntity addProtocolConfigEX1(String req) {
		ResultEntity result=new ResultEntity();
		int insertNum=0;
		if(JSONUtils.mayBeJSON(req)){
			ProtocolConfigDataObjEX1Entity protocolConfigDataObj=JsonUtils.fromJson(req, ProtocolConfigDataObjEX1Entity.class);
			if(protocolConfigDataObj!=null){
				Integer libraryIdx=protocolConfigDataObj.getLibrary_idx();
				Integer protocolType=protocolConfigDataObj.getProtocol_type();
				String protocol_tpl_desc=protocolConfigDataObj.getProtocol_tpl_desc();
				Assert.notNull(libraryIdx, "图书管IDX不能为空");
				Assert.notNull(protocolType, "指令类型不能为空[SIP2/NCIP]");
				Assert.notNull(protocol_tpl_desc, "模板名成不能为空");
				
				ProtocolConfigTemplateEntity protocolConfigTemplate=new ProtocolConfigTemplateEntity();
				protocolConfigTemplate.setLibrary_idx(libraryIdx);
				protocolConfigTemplate.setProtocol_type(protocolType);
				protocolConfigTemplate.setProtocol_tpl_desc(protocol_tpl_desc);
				try {
					insertNum=acsConfigDao.addProtocolConfigTemplate(protocolConfigTemplate);
				} catch (org.springframework.dao.DuplicateKeyException e) {
					String msg=e.getRootCause().getMessage();
					AntPathMatcher matcher=new AntPathMatcher();
					if(matcher.match("*protocol_tpl_desc*", msg)){
						throw new RuntimeException("模板名称已经被占用，请修改");
					}else{
						throw new RuntimeException(e);
					}			
				}
				if(insertNum<=0){
					throw new RuntimeException("新增模板失败");
				}
				Integer protocol_tpl_idx=protocolConfigTemplate.getProtocol_tpl_idx();
				Assert.notNull(protocol_tpl_idx, "protocol_tpl_idx不能为NULL");
				List<ACSProtocolEntity> selfCheckProtocols=protocolConfigDataObj.getProtocol_config_arr();
				List<ACSProtocolEntity> selfCheckProtocolsToSave=new ArrayList<>();
				if(selfCheckProtocols!=null){
					Timestamp now=new Timestamp(System.currentTimeMillis());
					for(ACSProtocolEntity selfCheckProtocolEntity:selfCheckProtocols){
						if(!StringUtils.hasText(selfCheckProtocolEntity.getProtocol_reqrule())
								&&!StringUtils.hasText(selfCheckProtocolEntity.getProtocol_resprule())
								&&!StringUtils.hasText(selfCheckProtocolEntity.getProtocol_description()))
						{
							continue;
						}
						selfCheckProtocolEntity.setProtocol_idx(protocol_tpl_idx);
						selfCheckProtocolEntity.setCreatetime(now);
						selfCheckProtocolEntity.setUpdatetime(now);
						selfCheckProtocolsToSave.add(selfCheckProtocolEntity);
					}
					insertNum=acsConfigDao.addProtocolConfigEx1Batch(selfCheckProtocolsToSave);
					if(insertNum!=selfCheckProtocolsToSave.size()){
						throw new RuntimeException("新增模板数据失败");
					}
				}
				//插入dev_dbsync_config,更新该library中所有设备acs的xml修改时间
				Map<String,Object> map = new HashMap<String,Object>();
				Map<String,Object> insertMap = new HashMap<String,Object>();
				
				map.put("lib_idx", libraryIdx);
				map.put("table_name", "acs_protocols");
				
				int count = deviceDao.queryFileUploadFlag(map);
				
				// 转换提日期输出格式
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String modifyTime = dateFormat.format(new java.sql.Date(System.currentTimeMillis())).toString();
				insertMap.put("table_name", "acs_protocols");
				insertMap.put("lib_idx", libraryIdx);
				insertMap.put("last_modify_time",modifyTime);
				
				if(count==0){
					//没有该图书馆libraryid则不需要操作 
				}else{
					deviceDao.updateFileUploadFlag(insertMap);
				}
				
				//查询是否存在使用该acs模板的设备
				List<DeviceAcsLoginInfoEntity>  acsLoginInfoEntities = loginInfoDao.queryAcsInfoByProtocolTplIdx(protocol_tpl_idx);
				if(acsLoginInfoEntities != null && !acsLoginInfoEntities.isEmpty()){
					//将数据上传到文件服务器
					for(DeviceAcsLoginInfoEntity infoEntity : acsLoginInfoEntities){
						String device_id = JedisUtils.getInstance().get(RedisConstant.DEVICE_KEYS+infoEntity.getDevice_idx());
						List<DeviceAcsModuleEntity> acsModuleEntities = loginInfoService.loadAcsXml(libraryIdx+"",device_id);
						//将acs数据传到文件服务器
						if(acsModuleEntities != null && !acsModuleEntities.isEmpty()){
							postUrl("uploadAcsConfig", JsonUtils.toJson(acsModuleEntities));
						}
					}
					
				}
				
				
				result.setState(true);
				//馆IDX｜协议IDX｜协议内容
				result.setRetMessage("馆IDX:"+libraryIdx+"｜协议IDX:"+protocol_tpl_idx+"｜协议名称:"+protocol_tpl_desc+"||");
			}
		}
		return result;
	}
	
	
	@Override
	public ResultEntity delProtocolConfig(String req) {
		ResultEntity result=new ResultEntity();
		int delNum=0;
		StringBuilder sb=new StringBuilder();
		if(JSONUtils.mayBeJSON(req)){
			List<SelfCheckProtocolEntity> selfCheckProtocols= JsonUtils.fromJson(req, new TypeReference<List<SelfCheckProtocolEntity>>() {});
			if(CollectionUtils.isNotEmpty(selfCheckProtocols)){
				for(SelfCheckProtocolEntity selfCheckProtocol:selfCheckProtocols){
						delNum=acsConfigDao.delProtocolConfig(selfCheckProtocol);
						if(delNum<=0){
							//删除不能成功，可能不存在
							sb.append(selfCheckProtocol.getProtocol_idx()).append(",");
						}
				}
			}
			result.setState(true);
			if(sb.length()>0){
				result.setRetMessage(sb.substring(0, sb.length()-1));
			}
		}
		return result;
	}
	/**
	 * ACS 模板数据 更新操作 <p>
	 * 
	 * 先删除该模板的数据后
	 * 重新插入数据
	 * 
	 */
	@Override
	public ResultEntity updProtocolConfig(String req) {
		ResultEntity result=new ResultEntity();
		int updNum=0;
		if(JSONUtils.mayBeJSON(req)){
			ProtocolConfigDataObjEntity protocolConfigDataObj=JsonUtils.fromJson(req, ProtocolConfigDataObjEntity.class);
			if(protocolConfigDataObj!=null){
				Integer libraryIdx=protocolConfigDataObj.getLibrary_idx();
				Integer protocolType=protocolConfigDataObj.getProtocol_type();
				Integer protocolTplIdx=protocolConfigDataObj.getProtocol_tpl_idx();
				String protocol_tpl_desc=protocolConfigDataObj.getProtocol_tpl_desc();
				Assert.notNull(libraryIdx, "图书管IDX不能为空");
				Assert.notNull(protocolType, "指令类型不能为空[SIP2/NCIP]");
				Assert.notNull(protocolTplIdx, "模板IDX不能为空");
				
				ProtocolConfigTemplateEntity protocolConfigTemplate=new ProtocolConfigTemplateEntity();
				protocolConfigTemplate.setLibrary_idx(libraryIdx);
				protocolConfigTemplate.setProtocol_type(protocolType);
				protocolConfigTemplate.setProtocol_tpl_idx(protocolTplIdx);
				updNum=acsConfigDao.updProtocolConfigTemplate(protocolConfigTemplate);
				if(updNum<=0){
					throw new RuntimeException("修改模板失败");
				}
				Integer protocol_tpl_idx=protocolConfigTemplate.getProtocol_tpl_idx();
				List<SelfCheckProtocolEntity> selfCheckProtocolsLocal=acsConfigDao.queryProtocolConfigByTplIdx(new SelfCheckProtocolEntity(protocol_tpl_idx));
				int delNum=acsConfigDao.delProtocolConfig(new SelfCheckProtocolEntity(protocol_tpl_idx));
				if(delNum!=selfCheckProtocolsLocal.size()){
					throw new RuntimeException("修改模板过程中删除模板数据失败");
				}
				Assert.notNull(protocol_tpl_idx, "protocol_tpl_idx不能为NULL");
				List<SelfCheckProtocolEntity> selfCheckProtocols=protocolConfigDataObj.getProtocol_config_arr();
				if(selfCheckProtocols!=null){
					for(SelfCheckProtocolEntity selfCheckProtocolEntity:selfCheckProtocols){
						selfCheckProtocolEntity.setProtocol_idx(protocol_tpl_idx);
					}
					updNum=acsConfigDao.addProtocolConfigBatch(selfCheckProtocols);
					if(updNum!=selfCheckProtocols.size()){
						throw new RuntimeException("新增模板数据失败");
					}
				}
				result.setState(true);
				//馆IDX｜协议IDX｜协议内容
				result.setRetMessage("馆IDX:"+libraryIdx+"｜协议IDX:"+protocol_tpl_idx+"｜协议名称:"+protocol_tpl_desc+"||");
			}	
			/*
				SelfCheckProtocolEntity selfCheckProtocol=JsonUtils.fromJson(req, SelfCheckProtocolEntity.class);
				updNum=acsConfigDao.updProtocolConfig(selfCheckProtocol);
				if(updNum<=0){
					result.setValue(false, "修改失败");
				}else{
					result.setState(true);
				}
			*/
		}
		return result;
	}
	
	@Transactional
	@Override
	public ResultEntity updProtocolConfigEX1(String req) {
		ResultEntity result=new ResultEntity();
		int updNum=0;
		if(JSONUtils.mayBeJSON(req)){
			ProtocolConfigDataObjEX1Entity protocolConfigDataObj=JsonUtils.fromJson(req, ProtocolConfigDataObjEX1Entity.class);
			if(protocolConfigDataObj!=null){
				Integer libraryIdx=protocolConfigDataObj.getLibrary_idx();
				Integer protocolType=protocolConfigDataObj.getProtocol_type();
				Integer protocolTplIdx=protocolConfigDataObj.getProtocol_tpl_idx();
				String protocol_tpl_desc=protocolConfigDataObj.getProtocol_tpl_desc();
				Assert.notNull(libraryIdx, "图书管IDX不能为空");
				Assert.notNull(protocolType, "指令类型不能为空[SIP2/NCIP]");
				Assert.notNull(protocolTplIdx, "模板IDX不能为空");
				Assert.notNull(protocol_tpl_desc, "模板名不能为空");
				ProtocolConfigTemplateEntity protocolConfigTemplate=new ProtocolConfigTemplateEntity();
				protocolConfigTemplate.setLibrary_idx(libraryIdx);
				protocolConfigTemplate.setProtocol_type(protocolType);
				protocolConfigTemplate.setProtocol_tpl_idx(protocolTplIdx);
				protocolConfigTemplate.setProtocol_tpl_desc(protocol_tpl_desc);
				protocolConfigTemplate.setVersion_stamp(protocolConfigDataObj.getVersion_stamp());
				updNum=acsConfigDao.updProtocolConfigTemplate(protocolConfigTemplate);
				if(updNum<=0){
					result.setState(false);
					result.setRetMessage("optimistic");
					return result;
				}
				Integer protocol_tpl_idx=protocolConfigTemplate.getProtocol_tpl_idx();
				
				List<ACSProtocolEntity> selfCheckProtocolsLocal=acsConfigDao.queryProtocolConfigByTplIdxEX1(new ACSProtocolEntity(protocol_tpl_idx));
				List<ACSProtocolEntity> selfCheckProtocolsToSave=new ArrayList<>();
				int delNum=acsConfigDao.delProtocolConfigEx1(new ACSProtocolEntity(protocol_tpl_idx));
				if(delNum!=selfCheckProtocolsLocal.size()){
					throw new RuntimeException("修改模板过程中删除模板数据失败");
				}
				Assert.notNull(protocol_tpl_idx, "protocol_tpl_idx不能为NULL");
				List<ACSProtocolEntity> selfCheckProtocols=protocolConfigDataObj.getProtocol_config_arr();
				if(selfCheckProtocols!=null){
					Timestamp now=new Timestamp(System.currentTimeMillis());
					for(ACSProtocolEntity selfCheckProtocolEntity:selfCheckProtocols){
						if(!StringUtils.hasText(selfCheckProtocolEntity.getProtocol_reqrule())
								&&!StringUtils.hasText(selfCheckProtocolEntity.getProtocol_resprule())
								&&!StringUtils.hasText(selfCheckProtocolEntity.getProtocol_description()))
						{
							continue;
						}
						selfCheckProtocolEntity.setProtocol_idx(protocol_tpl_idx);
						selfCheckProtocolEntity.setCreatetime(now);
						selfCheckProtocolEntity.setUpdatetime(now);
						selfCheckProtocolsToSave.add(selfCheckProtocolEntity);
					}
					
					updNum=acsConfigDao.addProtocolConfigEx1Batch(selfCheckProtocolsToSave);
					if(updNum!=selfCheckProtocolsToSave.size()){
						throw new RuntimeException("新增模板数据失败");
					}
					
					
				}
				//插入dev_dbsync_config,更新该library中所有设备acs的xml修改时间
				Map<String,Object> map = new HashMap<String,Object>();
				Map<String,Object> insertMap = new HashMap<String,Object>();
				
				map.put("lib_idx", libraryIdx);
				map.put("table_name", "acs_protocols");
				
				int count = deviceDao.queryFileUploadFlag(map);
				
				// 转换提日期输出格式
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String modifyTime = dateFormat.format(new java.sql.Date(System.currentTimeMillis())).toString();
				insertMap.put("table_name", "acs_protocols");
				insertMap.put("lib_idx", libraryIdx);
				insertMap.put("last_modify_time",modifyTime);
				
				if(count==0){
					//没有该图书馆libraryid则不需要操作 
				}else{
					deviceDao.updateFileUploadFlag(insertMap);
				}
				
				//查询是否存在使用该acs模板的设备
				List<DeviceAcsLoginInfoEntity>  acsLoginInfoEntities = loginInfoDao.queryAcsInfoByProtocolTplIdx(protocolTplIdx);
				if(acsLoginInfoEntities != null && !acsLoginInfoEntities.isEmpty()){
					
					//将数据上传到文件服务器
					for(DeviceAcsLoginInfoEntity infoEntity : acsLoginInfoEntities){
						String device_id = JedisUtils.getInstance().get(RedisConstant.DEVICE_KEYS+infoEntity.getDevice_idx());
						List<DeviceAcsModuleEntity> acsModuleEntities = loginInfoService.loadAcsXml(libraryIdx+"",device_id);
						//将acs数据传到文件服务器
						if(acsModuleEntities != null && !acsModuleEntities.isEmpty()){
							postUrl("uploadAcsConfig", JsonUtils.toJson(acsModuleEntities));
						}
					}	
				}
				
				result.setState(true);
				//馆IDX｜协议IDX｜协议内容
				result.setRetMessage("馆IDX:"+libraryIdx+"｜协议IDX:"+protocol_tpl_idx+"｜协议名称:"+protocol_tpl_desc+"||");
			}	
		}
		return result;
	}
	
	
	public void parseToXml(List<DeviceAcsModuleEntity> acsModuleEntities){
		
		try{
		
			FileOutputStream fop = new FileOutputStream(new File(""));
			OutputStreamWriter osw = new OutputStreamWriter(fop, "UTF-8");
			
			String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ever>";
			String xmlInnerStr = "";
			
			Iterator<DeviceAcsModuleEntity> it = acsModuleEntities.iterator();
			String protocolType = "";
			while (it.hasNext()) {
				DeviceAcsModuleEntity deviceAcsModule =  it.next();
				Integer type = deviceAcsModule.getProtocol_type();
				// 如果type为1,则为sip2类型
				if (type == 1) {
					protocolType = "sip2";
				}
				// 如果type为2,则为ncip类型
				if (type == 2) {
					protocolType = "ncip";
				}
				
				List<ProtocolInfo> protocolInfos = deviceAcsModule.getProtocolInfos();
				Iterator<ProtocolInfo> iterator = protocolInfos.iterator();
				while (iterator.hasNext()) {
					ProtocolInfo protocolInfo = iterator.next();
					xmlInnerStr += "<protocol";
					xmlInnerStr += "  commandkey=\"" + protocolInfo.getProtocol_description() + "\"";
					xmlInnerStr += "  commandname=\"" + protocolInfo.getProtocol_show() + "\"";
					xmlInnerStr += "  description=\"" + protocolInfo.getProtocol_field_name() + "\"";
					xmlInnerStr += ">";
					xmlInnerStr += protocolInfo.getProtocol_reqrule();
					xmlInnerStr += protocolInfo.getProtocol_resprule();
					xmlInnerStr += "";
					xmlInnerStr += "</protocol>";
					
				}
	
				xmlStr += "<protocols";
				xmlStr += "  acsType=\"" + protocolType + "\"";
				xmlStr += "  acsName=\"" + deviceAcsModule.getAcs_service_name() + "\"";
				
				if (type == 2) {
					xmlStr += "  connector=\"IP=" + deviceAcsModule.getLogin_ip() + "|Port=" + deviceAcsModule.getLogin_port() + "|Account=" + deviceAcsModule.getLogin_username() + "|Pwd="
							+ deviceAcsModule.getLogin_pwd() + "\"";
				}
				if (type == 1) {
					xmlStr += "  connector=\"IP=" + deviceAcsModule.getLogin_ip() + "|Port=" + deviceAcsModule.getLogin_port() + "|Account=" + deviceAcsModule.getLogin_username() + "|Pwd="
							+ deviceAcsModule.getLogin_pwd() + "|LoginType=" + deviceAcsModule.getLogin_type() + "|ReLogin=" + deviceAcsModule.getLogin_count() + "|NeedLogin="
							+ deviceAcsModule.getLogin_check() + "|Charset=" + deviceAcsModule.getLogin_code() + "\"";
				}
				xmlStr += ">";
				xmlStr += xmlInnerStr;
				xmlStr += "</protocols>";
				xmlInnerStr = "";
			}
			xmlStr += "</ever>";
			osw.write(xmlStr);
			osw.flush();
			osw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public ResultEntity queryProtocolConfigByTplIdx(String req) {
		ResultEntity result=new ResultEntity();
		//只有一个数据 protocol_idx
		if(JSONUtils.mayBeJSON(req)){
			SelfCheckProtocolEntity selfCheckProtocol=JsonUtils.fromJson(req, SelfCheckProtocolEntity.class);
			if(selfCheckProtocol!=null){
				List<SelfCheckProtocolEntity> selfCheckProtocols=acsConfigDao.queryProtocolConfigByTplIdx(selfCheckProtocol);
				if(selfCheckProtocols!=null){
					result.setResult(selfCheckProtocols);
					result.setState(true);
				}
			}
		}
		return result;
	}
	//需要从界面获取以下参数
	//protocol_idx 外键IDX
	//library_idx 图书馆IDX
	//protocol_type 协议类型
	//protocol_library_idx 字段名
	@Override
	public ResultEntity queryProtocolConfigByTplIdxEX1(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			ACSProtocolEntity aCSProtocolEntity=JsonUtils.fromJson(req, ACSProtocolEntity.class);
			if(aCSProtocolEntity!=null){
				List<ACSProtocolEntity> aCSProtocolEntitys=acsConfigDao.queryProtocolConfigByTplIdxEX1(aCSProtocolEntity);
				if(aCSProtocolEntitys!=null){
					result.setResult(aCSProtocolEntitys);
					result.setState(true);
				}
			}
		}
		return result;
	}
	/**
	 * NOTE：
	 * MYSQL myisam 引擎不支持级联删除 和外键 需要先删除模板数据表数据 再删除模板数据
	 * MYSQL innoDB 引擎支持级联删除和外键  可以直接删除模板数据
	 */
	@Override
	public ResultEntity delProtocolConfigTemplate(String req) {
		ResultEntity result=new ResultEntity();
		int delNum=0;
		StringBuilder sb=new StringBuilder();
		StringBuilder idx=new StringBuilder("协议IDX:");
		if(JSONUtils.mayBeJSON(req)){
			List<ProtocolConfigTemplateEntity> protocolConfigTemplates= JsonUtils.fromJson(req, new TypeReference<List<ProtocolConfigTemplateEntity>>() {});
			if(CollectionUtils.isNotEmpty(protocolConfigTemplates)){
				for(ProtocolConfigTemplateEntity protocolConfigTemplate:protocolConfigTemplates){
					Integer protocol_tpl_idx=protocolConfigTemplate.getProtocol_tpl_idx();
					
					List<DeviceAcsLoginInfoEntity> deviceAcsLoginInfos= acsLoginInfoDao.queryAcsInfoByProtocolTplIdx(protocol_tpl_idx);
					if(CollectionUtils.isNotEmpty(deviceAcsLoginInfos)){
						//表示存在外键
						sb.append(protocol_tpl_idx).append(",");
						continue;//跳过删除环节
					}
					if(protocol_tpl_idx!=null){
						acsConfigDao.delProtocolConfig(new SelfCheckProtocolEntity(protocol_tpl_idx));
					}
					delNum=acsConfigDao.delProtocolConfigTemplate(protocolConfigTemplate);
					if(delNum>0){
						idx.append(protocol_tpl_idx).append(",");
					}
				}
			}
			
			result.setState(true);
			if(sb.length()>0){
				result.setState(false);
				result.setRetMessage(sb.substring(0, sb.length()-1)+"（可能需要先清除对应馆设备的ACS登录信息）");
			}else{
				result.setRetMessage(idx.toString().substring(0, idx.toString().length()-1));
			}
		}
		return result;
	}
	
	@Transactional
	@Override
	public ResultEntity delProtocolConfigTemplateEX1(String req) {
		ResultEntity result=new ResultEntity();
		int delNum=0;
		StringBuilder sb=new StringBuilder();
		StringBuilder idx=new StringBuilder("协议IDX:");
		if(JSONUtils.mayBeJSON(req)){
			List<ProtocolConfigTemplateEntity> protocolConfigTemplates= JsonUtils.fromJson(req, new TypeReference<List<ProtocolConfigTemplateEntity>>() {});
			if(CollectionUtils.isNotEmpty(protocolConfigTemplates)){
				for(ProtocolConfigTemplateEntity protocolConfigTemplate:protocolConfigTemplates){
					Integer protocol_tpl_idx=protocolConfigTemplate.getProtocol_tpl_idx();
					
					List<DeviceAcsLoginInfoEntity> deviceAcsLoginInfos= acsLoginInfoDao.queryAcsInfoByProtocolTplIdx(protocol_tpl_idx);
					if(CollectionUtils.isNotEmpty(deviceAcsLoginInfos)){
						//表示存在外键
						sb.append(protocol_tpl_idx).append(",");
						continue;//跳过删除环节
					}
					if(protocol_tpl_idx!=null){
						acsConfigDao.delSelfcheckProtocolConfigByIdx(protocol_tpl_idx);
					}
					delNum=acsConfigDao.delProtocolConfigTemplate(protocolConfigTemplate);
					if(delNum>0){
						idx.append(protocol_tpl_idx).append(",");
					}else{
						result.setState(false);
						result.setRetMessage("optimistic");
						return result;
					}
				}
			}
			
			if(sb.length()>0){
				result.setState(false);
				result.setRetMessage(sb.substring(0, sb.length()-1)+"（可能需要先清除对应馆设备的ACS登录信息）");
			}else{
				result.setState(true);
				result.setRetMessage(idx.toString().substring(0, idx.toString().length()-1));
			}
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity getAscTempList(String req) {
		ResultEntity resultEntity=new ResultEntity();
		try {
			Map<String, Object> param = JsonUtils.fromJson(req, Map.class);
			if(param.get("library_idx")==null || "".equals(param.get("library_idx").toString())){
				resultEntity.setValue(false, "参数不能为空!");
			}
			ProtocolConfigTemplateEntity templateEntity = new ProtocolConfigTemplateEntity();
			templateEntity.setLibrary_idx(Integer.valueOf(param.get("library_idx").toString()));
			List<ProtocolConfigTemplateEntity> list = acsConfigDao.getAscTempList(templateEntity);
			resultEntity.setValue(true, "message", "", list);
		} catch (Exception e) {
			//获取当前方法名称
			ExceptionHelper.afterException(resultEntity, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resultEntity;
	}

	@Override
	public List<MetadataProtocolEntity> queryMetadataProtocol(String req) {
		MetadataProtocolEntity MetadataProtocolEntity=new MetadataProtocolEntity();
		return acsConfigDao.queryMetadataProtocol(MetadataProtocolEntity);
	}
	//req=protocol_tpl_desc
	@Override
	public ResultEntity queryProtocolConfigTemplate(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			boolean isUpdOper=false;
			Integer protocol_tpl_idx=null;
			ProtocolConfigTemplateEntity pcte=JsonUtils.fromJson(req, ProtocolConfigTemplateEntity.class);
			if(pcte!=null && pcte.getProtocol_tpl_idx()!=null){
				isUpdOper=true;
				protocol_tpl_idx=pcte.getProtocol_tpl_idx();
				pcte.setProtocol_tpl_idx(null);
			}
			List<ProtocolConfigTemplateEntity> protocolConfigTemplates=acsConfigDao.queryProtocolConfigTemplateExactly(pcte);
			//更新操作 只查询 图书馆IDX 和模板名称 
			if(CollectionUtils.isNotEmpty(protocolConfigTemplates)){
				if(isUpdOper){
					//表明是更新操作 //重复条件1， 
					for(ProtocolConfigTemplateEntity pc:protocolConfigTemplates){
						if(pc.getProtocol_tpl_idx()!=protocol_tpl_idx){
							result.setState(true);
							result.setResult(pc);
							break;
						}
					}
				}else{
					result.setState(true);
					result.setResult(protocolConfigTemplates.get(0));
				}
				
			}
		}
		return result;
	}
	
	
	public ResultEntity postUrl(String postUrl,String req){
		ResultEntity resultEntity=new ResultEntity();
		String url=requestURL.getRequestURL(postUrl);
		Map<String,String> map=new HashMap<>();
		map.put("req",req);
		String result=HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
		if(JSONUtils.mayBeJSON(result)){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
	}
	
}
