package com.ssitcloud.business.sysmgmt.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.sf.json.util.JSONUtils;

import org.apache.commons.io.FileUtils;
import org.apache.http.Consts;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.JaxbToXmlUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.common.util.Md5CaculateUtil;
import com.ssitcloud.business.sysmgmt.service.DataBaseService;
import com.ssitcloud.business.sysmgmt.service.DataBaseV2Service;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.BakupEntity;

@Service
public class DataBaseV2ServiceImpl extends BasicServiceImpl implements DataBaseV2Service{
	@Resource
	private DataBaseService dataBaseService;
	/**
	 * req 参数：{"lirary_idx":1}
	 */
	private static final String URL_bakupOnlyByLiraryIdx = "bakupOnlyByLiraryIdx";
	
	private static final String URL_bakupOnlyByLiraryIdxSsitAuth="bakupOnlyByLiraryIdxSsitAuth";
	/**
	 * 只需要library_idx就可以备份数据的表 SsitDevice库
	 */
	final static List<String> tableNameListByLibIdxSsitDevice=new ArrayList<>();
	/**
	 * 只需要library_idx就可以备份数据的表 SsitAuth库
	 */
	final static List<String> tableNameListByLibIdxSsitAuth=new ArrayList<>();
	
	final static List<String> tableNameSpecialSet=new ArrayList<>();
	/**
	 * 鉴权库需要特殊备份的表
	 */
	final static List<String> tableNameSpecialSetAuth=new ArrayList<>();
	
	/**
	 * 基础数据表
	 */
	final static List<String> tableNameBasicList=new ArrayList<>();
	
	private static final String URL_bakupBySpecalTableDevice = "bakupBySpecalTableDevice";
	private static final String URL_queryBakDataInfo = "queryBakDataInfo";
	private static final String URL_insertBakDataInfo = "insertBakDataInfo";
	private static final String URL_bakupBasicListSsit = "bakupBasicListSsit";
	private static final String URL_restoreAuthDataByLibraryIdx = "restoreAuthDataByLibraryIdx";
	private static final String URL_checkBakUpFileIfExsit = "checkBakUpFileIfExsit";
	private static final String URL_deleteLibBakup = "deleteLibBakup";
	private static final String URL_getLastLibBakUpTime = "getLastLibBakUpTime";
	
	private static void tableNameListByLibIdxSsitAuthAdd(String tablename){
		if(!tableNameListByLibIdxSsitAuth.contains(tablename)){
			tableNameListByLibIdxSsitAuth.add(tablename);
		}
	}
	private static void tableNameListByLibIdxSsitDeviceAdd(String tablename){
		if(!tableNameListByLibIdxSsitDevice.contains(tablename)){
			tableNameListByLibIdxSsitDevice.add(tablename);
		}
	}
	private static void tableNameSpecialSetAdd(String tablename){
		if(!tableNameSpecialSet.contains(tablename)){
			tableNameSpecialSet.add(tablename);
		}
	}
	private static void tableNameBasicListAdd(String tablename){
		if(!tableNameBasicList.contains(tablename)){
			tableNameBasicList.add(tablename);
		}
	}
	static{
		/************************鉴权库********************/
		
		//1
		tableNameListByLibIdxSsitAuthAdd("operator");
		//2
		tableNameListByLibIdxSsitAuthAdd("library");
		//---------------------------------------------//
		tableNameListByLibIdxSsitAuthAdd("library_info");
		tableNameListByLibIdxSsitAuthAdd("library_service_template");
		tableNameListByLibIdxSsitAuthAdd("metadata_infotype");
		tableNameListByLibIdxSsitAuthAdd("password_history");
		tableNameListByLibIdxSsitAuthAdd("operator_info");
		tableNameListByLibIdxSsitAuthAdd("ip_white");
		tableNameListByLibIdxSsitAuthAdd("sox_template");
		
		/***********************************************/
		//顺序 ：有外键的表的在后面备份 ,删除的时候 从后面删，新增的时候从前面增
		
		//基础数据表
		tableNameBasicListAdd("metadata_devicetype");
		tableNameBasicListAdd("metadata_logic_obj");
		tableNameBasicListAdd("metadata_ext_model");
		
		//模板
		tableNameSpecialSetAdd("device_dbsync_template");
		tableNameSpecialSetAdd("device_ext_template");
		tableNameSpecialSetAdd("device_monitor_template");
		tableNameSpecialSetAdd("device_run_template");
		
		//其他 有 library_idx的表
		tableNameListByLibIdxSsitDeviceAdd("device"); 
		tableNameListByLibIdxSsitDeviceAdd("device_config");
		
		tableNameListByLibIdxSsitDeviceAdd("device_group");
		tableNameListByLibIdxSsitDeviceAdd("service_group");
		tableNameListByLibIdxSsitDeviceAdd("operator_group");
		
		tableNameListByLibIdxSsitDeviceAdd("protocol_config_template");
		tableNameListByLibIdxSsitDeviceAdd("device_acs_logininfo");
		tableNameListByLibIdxSsitDeviceAdd("device_selfcheck_protocol_config");
		
		tableNameListByLibIdxSsitDeviceAdd("reader_type");
		tableNameListByLibIdxSsitDeviceAdd("operator_maintenance_info");//这个必须和reader_type在一起 
		
		tableNameListByLibIdxSsitDeviceAdd("rel_device_group");
		tableNameListByLibIdxSsitDeviceAdd("rel_operator_group");
		tableNameListByLibIdxSsitDeviceAdd("rel_service_group");
		tableNameListByLibIdxSsitDeviceAdd("rel_shelf_group");
		tableNameListByLibIdxSsitDeviceAdd("rel_operator_device_group");
		tableNameListByLibIdxSsitDeviceAdd("rel_operator_service_group");
		tableNameListByLibIdxSsitDeviceAdd("rel_operator_shelf_group");
		
		tableNameListByLibIdxSsitDeviceAdd("device_dbsync_config");
		tableNameListByLibIdxSsitDeviceAdd("device_ext_config");
		tableNameListByLibIdxSsitDeviceAdd("device_monitor_config");
		tableNameListByLibIdxSsitDeviceAdd("device_run_config");
	}
	
	/**
	 * 
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity dataBaseBakupByLibraryIdxAndID(String req) {
		ResultEntity result = new ResultEntity();
		if (!JSONUtils.mayBeJSON(req)) {
			return result;
		}
		Map<String, Object> map = JsonUtils.fromJson(req, Map.class);//library_idx and library_id
		String bakupDir=dataBaseService.getDir();//备份文件保存路径
		String library_id=(String) map.get("library_id");
		String childDirName="libidx"+File.separator+library_id;
		if(bakupDir==null){
			result.setRetMessage("bakupDir is null");
			return result;
		}
		File f=new File(bakupDir+File.separator+childDirName);
		if(!f.exists()){
			if(!f.mkdirs()){
				result.setRetMessage("mkdirs failed");
				return result;
			}
		}
		String t=System.currentTimeMillis()+".xml";//file name
		File bakFile=new File(bakupDir+File.separator+childDirName+File.separator+t);
		if(!bakFile.exists()){
			try {
				System.out.println(bakFile.getPath());
				if(!bakFile.createNewFile()){
					return result;
				}
			} catch (IOException e) {
				e.printStackTrace();
				return result;
			}
		}
		if(!bakupOnlyByLiraryIdx(map,bakFile))
			return result;
		result.setState(true);
		return result;
	}
	@SuppressWarnings("unchecked")
	private boolean bakupToFile(String databaseName,String urlID,List<String> tableNameList,File bakFile,Map<String,Object> map){
		boolean success=true;
		try {
			FileUtils.write(bakFile,"\n<database name=\""+databaseName+"\">\n","UTF-8",true);
		} catch (IOException e1) {
			e1.printStackTrace();
			success=false;
			return success;
		}
		for(int i=0;i<tableNameList.size();i++){
			String tableName=tableNameList.get(i);
			//System.out.println("tablename--->"+tableName);
			map.put("tableName", tableName);
			String ret=postUrlRetStr(urlID, JsonUtils.toJson(map));
			//~~~防止一次性数据量过大,但是请求会很频繁？~~~
			if(ret==null){
				success=false;
				break;
			}
			//<meta name="author" content="mycodewind，mycodewind@qq.com"/>
			//返回的是List 类型 的 表数据
			ResultEntity result=JsonUtils.fromJson(ret, ResultEntity.class);
			List<String> toXML=new ArrayList<>();
			if(result!=null && result.getState()){
				List<Object> resultList= (List<Object>) result.getResult();
				for(Object m:resultList){
					toXML.add(JsonUtils.toJson(m));
				}
			}
			BakupEntity b=new BakupEntity();
			b.setTablename(tableName);
			b.setRow(toXML);
			String s=JaxbToXmlUtil.convertToXml(b, "UTF-8");
			try {
				FileUtils.write(bakFile,s,"UTF-8",true);
				FileUtils.write(bakFile,"\n","UTF-8",true);
			} catch (IOException e) {
				e.printStackTrace();
				success=false;
				return success;
			}
		}
		try {
			FileUtils.write(bakFile,"</database>\n","UTF-8",true);
		} catch (IOException e) {
			e.printStackTrace();
			success=false;
		}
		return success;
	}
	
	/**
	 * 备份并生成文件
	 *
	 * @param map
	 * @param bakFile
	 * @return
	 */
	private boolean bakupOnlyByLiraryIdx(Map<String, Object> map,File bakFile){
		boolean success=true;
		try {
			FileUtils.write(bakFile,"<?xml version=\'1.0\' encoding=\'UTF-8\'?>\n",Consts.UTF_8,true);
			FileUtils.write(bakFile,"<tables>",Consts.UTF_8,true);
		} catch (IOException e1) {
			e1.printStackTrace();
			success=false;
			return success;
		}
		Integer library_idx=Integer.parseInt(map.get("library_idx").toString());
		success=bakupToFile("ssitcloud_authentication",URL_bakupOnlyByLiraryIdxSsitAuth, tableNameListByLibIdxSsitAuth, bakFile, map);
		if(success)
			success=bakupToFile("ssitcloud_device",URL_bakupBasicListSsit, tableNameBasicList, bakFile, map);
		if(success)
			success=bakupBySpecalTableDevice("ssitcloud_device",library_idx,bakFile);
		if(success)
		    success=bakupToFile("ssitcloud_device",URL_bakupOnlyByLiraryIdx, tableNameListByLibIdxSsitDevice, bakFile, map);
		if(!success){
			//需要删除备份
		}
		try {
			FileUtils.write(bakFile,"\n</tables>",Consts.UTF_8,true);
		} catch (IOException e) {
			e.printStackTrace();
			success=false;
			return success;
		}
		//备份成功，在db_bakup表新增数据
		Map<String,Object> entity=new HashMap<>();
		entity.put("file_name", bakFile.getName());
		entity.put("file_path", bakFile.getAbsolutePath());
		entity.put("bakup_flg", 1);
		entity.put("is_exist", 1);
		entity.put("file_size", bakFile.length()/1024.00/1024.00);
		//entity.put("create_time", 1);由数据库写入
		entity.put("md5", Md5CaculateUtil.MD5(bakFile));//
		entity.put("library_idx", library_idx);
		ResultEntity res=insertBakDataInfo(entity);
		if(res==null || res.getState()==false){
			//?insert failed? 删除备份文件提示备份失败？
		}
		return success;
	}
	
	private ResultEntity insertBakDataInfo(Map<String,Object> entity){
		return postUrl(URL_insertBakDataInfo, JsonUtils.toJson(entity));
	}
	
	/**
	 * 
	 * 需要单独 根据条件 备份的表   device_config
	 * device_dbsync_config、       【根据馆IDX备份】
	 * device_dbsync_template、 【如果该馆的设备有用到这个模板的，则这个模板要备份，模板数据也要备份】
	 * device_ext_config、                 【根据馆IDX备份】
	 * device_ext_template、           【如果该馆的设备有用到这个模板的，则这个模板要备份，模板数据也要备份】
	 * device_monitor_config、     【根据馆IDX备份】
	 * device_monitor_template 【如果该馆的设备有用到这个模板的，则这个模板要备份，模板数据也要备份】
	 * device_run_config、                 【根据馆IDX备份】
	 * device_run_template、           【如果该馆的设备有用到这个模板的，则这个模板要备份，模板数据也要备份】
	 * operator_maintenance_info、 ------>引用 reader_type
	 * @param databaseName 
	 * 
	 * **/
	
	private boolean bakupBySpecalTableDevice(String databaseName, Integer library_idx,File bakFile){
		//step 1  首先需要根据library_idx查询device_config表中使用模板的的IDX
		//step 2  然后备份对应IDX的 device_xxx_template数据。
		//step 3  备份device_xxx_config表对应ID和属于模板类型的数据
		// ~转换 报错  则说明数据问题，不予与备份~
		boolean success=false;
		Map<Object, Object> m=new HashMap<>();
		m.put("library_idx", library_idx);
		try {
			FileUtils.write(bakFile,"\n<database name=\""+databaseName+"\">\n","UTF-8",true);
		} catch (IOException e1) {
			e1.printStackTrace();
			return success;
		}
		for(String table_name:tableNameSpecialSet){
			m.put("table_name", table_name);
			ResultEntity res=postUrl(URL_bakupBySpecalTableDevice, JsonUtils.toJson(m));
			if(res!=null && res.getState()){
				Object o=res.getResult();
				String str=JsonUtils.toJson(o);
				JsonNode node=JsonUtils.readTree(str);
				JsonNode configNode=node.get(table_name+"_config_list");
				JsonNode tempNode=node.get(table_name+"_list");
				List<String> toXML=new ArrayList<>();
				List<String> toConfigNodeXML=new ArrayList<>();
				if(tempNode!=null){
					Iterator<JsonNode> tempIt=tempNode.getElements();
					while(tempIt.hasNext()){
						toXML.add(tempIt.next().toString());
					}
				}
				if(configNode!=null){
					Iterator<JsonNode> configNodeIt=configNode.getElements();
					while(configNodeIt.hasNext()){
						toConfigNodeXML.add(configNodeIt.next().toString());
					}
				}
				BakupEntity b=new BakupEntity();
				b.setTablename(table_name);
				b.setRow(toXML);
				String config_name=null;
				if("device_run_template".equals(table_name)){
					config_name="device_run_config";
				}else if("device_ext_template".equals(table_name)){
					config_name="device_ext_config";
				}else if("device_monitor_template".equals(table_name)){
					config_name="device_monitor_config";
				}else if("device_dbsync_template".equals(table_name)){
					config_name="device_dbsync_config";
				}
				//考虑在 做成XML的时候同时做成sql脚本
				String s=JaxbToXmlUtil.convertToXml(b, "UTF-8");
				b.setTablename(config_name);
				b.setRow(toConfigNodeXML);
				String s2=JaxbToXmlUtil.convertToXml(b, "UTF-8");
				try {
					FileUtils.write(bakFile,s,"UTF-8",true);
					FileUtils.write(bakFile,"\n","UTF-8",true);
					FileUtils.write(bakFile,s2,"UTF-8",true);
					success=true;
				} catch (IOException e) {
					e.printStackTrace();
					success=false;
				}	
			}else{
				//终端操作，【删除文档】,这个图书馆没有使用模板？
				//success=false;
			}
		}
		try {
			FileUtils.write(bakFile,"\n</database>\n","UTF-8",true);
		} catch (IOException e) {
			success=false;
			e.printStackTrace();
			return success;
		}
		return success;
	}
	/**
	 * 还原操作
	 * 1.组织sql脚本
	 * 2.调用还原
	 * var libInfo={
	 * "library_idx":16,
	 * "library_id":"LIB001"
	 * ,"idx":"要还原数据的IDX",
	 * "file_name":""
	 * };
	 */
	@Override
	public ResultEntity restoreDataByLibraryIdx(String req) {
		ResultEntity result=null;
		result=postUrlLongtime(URL_restoreAuthDataByLibraryIdx, req);
		return result;
	}
	
	public static void main(String[] args) throws JAXBException {
		//Map<Object, Object> m=new HashMap<>();
		//m.put("device_idx", 1);
		//BakupEntity b=new BakupEntity();
		//b.setTablename("device");
		 /* 
		  * JAXBContext context = JAXBContext.newInstance(BakupEntity.class);    // 获取上下文对象  
	        Marshaller marshaller = context.createMarshaller(); // 根据上下文获取marshaller对象  
	        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");  // 设置编码字符集  
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // 格式化XML输出，有分行和缩进  
	        //marshaller.marshal(b,System.out);   // 打印到控制台  
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	        marshaller.marshal(b, baos);  
	        String xmlObj = new String(baos.toByteArray());         // 生成XML字符串  
	        System.out.println(xmlObj); 
	        */ 
	       // String s=JaxbToXmlUtil.convertToXml(b, "UTF-8");
	        //System.out.println(s);
	        //JAXBContext context = JAXBContext.newInstance(BakupEntity.class); 
	        //Unmarshaller marshaller = context.createUnmarshaller();
	        
	    	List<Integer> s=new ArrayList<>();
	    	s.add(1);
	    	s.add(31);
	    	s.add(14);
	    	s.add(2);
	    	String c=StringUtils.collectionToCommaDelimitedString(s);
	    	System.out.println(c);
	}
	/**
	 * 参数 idx or other
	 */
	@Override
	public ResultEntity queryBakDataInfo(String req) {
		return postUrl(URL_queryBakDataInfo, req);
	}
	@Override
	public ResultEntity checkBakUpFileIfExsit(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			List<Map<String,Object>> pathList=	JsonUtils.fromJson(req, new TypeReference<List<Map<String,Object>>>() {
			});
			List<String> needUpdToExistList=new ArrayList<>();
			List<String> needUpdToNotExistList=new ArrayList<>();
			Map<String,Object> map=new HashMap<>();
			if(!CollectionUtils.isEmpty(pathList)){
				for(Map<String,Object> bfile:pathList){
					if(bfile.containsKey("file_path") && bfile.containsKey("is_exist")){
						String file_path=bfile.get("file_path").toString();
						Integer is_exist=Integer.parseInt(bfile.get("is_exist").toString());
						if(!new File(file_path).exists()
								&& is_exist==1){//存在 但是 实际不存在，需要更新
							needUpdToNotExistList.add(file_path);
						}else if(new File(file_path).exists() && is_exist!=1){
							//不存在 实际存在，需要更新
							needUpdToExistList.add(file_path);
						}
					}
				}
				if(needUpdToExistList.size()>0){
					map.put("needUpdToExistList", needUpdToExistList);
				}
				if(needUpdToNotExistList.size()>0){
					map.put("needUpdToNotExistList", needUpdToNotExistList);
				}
				result=postUrl(URL_checkBakUpFileIfExsit, JsonUtils.toJson(map));
			}
		}
		
		return result;
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity deleteLibBakup(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			List<String> m=JsonUtils.fromJson(req, List.class);
			List<String> toDeleteList=new ArrayList<>();
			if(m!=null){
				for(String file_path:m){
					File file=new File(file_path);
					if(file.exists()){
						if(file.delete()){
							toDeleteList.add(file_path);
						}
					}else{
						toDeleteList.add(file_path);
					}
				}
				result=postUrl(URL_deleteLibBakup, JsonUtils.toJson(toDeleteList));
			}
		}
		return result;
	}
	@Override
	public ResultEntity getLastLibBakUpTime(String req) {
		return postUrl(URL_getLastLibBakUpTime, req);
	}


}
