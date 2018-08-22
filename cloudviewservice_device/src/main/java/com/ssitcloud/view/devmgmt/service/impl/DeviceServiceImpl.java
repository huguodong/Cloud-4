package com.ssitcloud.view.devmgmt.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.devmgmt.entity.DevicePageEntity;
import com.ssitcloud.devmgmt.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.devmgmt.entity.MetadataOrderEntity;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.ExportExcelUtils;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.common.util.I18nUtil;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.devmgmt.service.DeviceService;

@Service
public class DeviceServiceImpl extends BasicServiceImpl implements DeviceService{
	
	public static final String URL_SelMetadataOrder="SelMetadataOrder";
	public static final String URL_SelectDeviceByPage="SelectDeviceByPage";
	public static final String URL_SelectInfo="SelectInfo";
	public static final String URL_SelDeviceInfo="SelectDevice";
	public static final String URL_DELDeviceInfo="DeleteDevice";
	public static final String URL_queryDeviceType="queryDeviceType";
	public static final String URL_queryDeviceLog="queryDeviceLog";
	public static final String URL_selectDeviceState="selectDeviceState";
	public static final String URL_selectBookrackState="selectBookrackState";

	public static final String URL_selectDBsync = "SelDeviceDbsync";
	public static final String URL_updateDBsync = "UpdDeviceDbsync";
	public static final String URL_insertDBsync = "AddNewDeviceDbsync";
	public static final String URL_deleteDBsync = "DelDeviceDbsync";
	public static final String URL_updateDevConf = "UpdDevConfig";
	
	public static final String URL_selectMonitor = "SelDeviceMonitorConf";
	public static final String URL_updateMonitor = "UpdDeviceMonitor";
	public static final String URL_insertMonitor = "AddDeviceMonitor";
	public static final String URL_deleteMonitor = "DelDeviceMonitor";
	
	public static final String URL_insertExt = "AddNewDeviceExtConfig";
	public static final String URL_deleteExt = "DelDeviceExtConfig";
	public static final String URL_updateExt = "DelAndAddExtConfig";
	public static final String URL_selectEXT = "SelDeviceExtConfig";
	
	public static final String URL_insertRun = "AddNewDeviceRunConf";
	public static final String URL_deleteRun = "DelDeviceExtConfig";
	public static final String URL_updateRun = "UpdDeviceRunConf";
	public static final String URL_selectRun = "SelDeviceRunConf";
	
	public static final String URL_selectBinState="selectBinState";

	public static final String SEL_ALL_EXTTEMPLIST = "SelAllExtTempList";
	public static final String SEL_ALL_RUNTEMPLIST = "SelAllRunTempList";
	public static final String SEL_ALL_MONITOR_TEMPLIST = "SelAllMonitorTempList";
	public static final String SEL_ALL_DBSYNC_TEMPLIST = "SelAllDBSyncTempList";

	public static final String URL_selLibraryByIdxOrId="selLibraryByIdxOrId";
	public static final String SEND_ORDER="recevieOrder";
	public static final String URL_selectDeviceExtState = "selectDeviceExtState";
	public static final String URL_selectSoftState = "selectSoftState";
	public static final String URL_selOperatorByOperIdOrIdx = "selOperatorByOperIdOrIdx";
	public static final String URL_queryDeviceByParam = "queryDeviceByParam";
	
	public static final String URL_queryControlResult = "queryControlResult";
 
	private static final String SEL_ALL_EXTMODEL_AND_LOGICOBJ = "selAllExtModelAndLogicObj";
	private static final String URL_UpdDeviceMgmtPage = "UpdDeviceMgmtPage";
	private static final String URL_UpdHyDeviceMgmtPage = "UpdHyDeviceMgmtPage";
	private static final String URL_checkAllConfigDataByDevIdx = "checkAllConfigDataByDevIdx";
	private static final String URL_compareMonitorConfig = "compareMonitorConfig";
	private static final String URL_getACSTempList = "getACSTempList";
	private static final String URL_loadAcsLogininfo = "loadAcsLogininfo";
	private static final String URL_getLibraryDevicesByPage = "getLibraryDevicesByPage";
	private static final String URL_queryDeviceIps = "queryDeviceIps";
	private static final String URL_transferToNewLib = "transferToNewLib";
	private static final String URL_queryAllRegion = "queryAllRegion";
	private static final String URL_queryDeviceRegion = "queryDeviceRegion";
	private static final String URL_getDevicePosition = "getDevicePosition";
	private static final String URL_saveDevicePostion = "saveDevicePosition";
	private static final String URL_deleteLibraryPosition = "deleteLibraryPosition";
	private static final String URL_getLibPosition = "getLibPosition";
	private static final String URL_INSERTFILEUPLOADFLAG = "insertFileUploadFlag";
	private static final String URL_devicesRegister = "devicesRegister";
	
	
	@Override
	public List<MetadataOrderEntity> queryMetadataOrder() {
		Map<String, String> map=new HashMap<>();
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_SelMetadataOrder), map, charset);
		if(result!=null){
			ResultEntityF<List<MetadataOrderEntity>> rs=JsonUtils.fromJson(result, new TypeReference<ResultEntityF<List<MetadataOrderEntity>>>() {
			});
			if(rs!=null){
				List<MetadataOrderEntity> orders=rs.getResult();
				for(MetadataOrderEntity order:orders){
					String jsonOrder_desc=order.getOrder_desc();
					order.setOrder_desc(I18nUtil.converLanguage(jsonOrder_desc));
				}
				return rs.getResult();
			}
		}
		return null;
	}
	@Override
	public DevicePageEntity SelectDeviceByPage(String req){
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(requestURL.getRequestURL(URL_SelectDeviceByPage), map, charset);
		if(result!=null){
			ResultEntityF<DevicePageEntity> rs=JsonUtils.fromJson(result, new TypeReference<ResultEntityF<DevicePageEntity>>() {});
			if(rs!=null){
				return rs.getResult();
			}
		}
		return null;
	}
	
	@Override
	public String sendOrder(String req){
		//JsonUtils.fromJson(req, valueType);
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String re=HttpClientUtil.doPost(requestURL.getRequestURL(SEND_ORDER), map, charset);
		return re;
	}
	@Override
	public List<MetadataDeviceTypeEntity> queryDeviceType() {
		return null;
	}
	

	@Override
	public String SelDeviceinfo(Map<String, String> map) {
		String reqURL=requestURL.getRequestURL(URL_SelDeviceInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	@Override
	public String DeleteDeviceinfo(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("json", req);
		String reqURL=requestURL.getRequestURL(URL_DELDeviceInfo);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	
	

	@Override
	public String queryDeviceLog(String req) {
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String re=HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryDeviceLog), map, charset);
		return re;
	}
	
	@Override
	public ResultEntity selectDeviceState(String req) {
		ResultEntity result=new ResultEntity();
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String re=HttpClientUtil.doPost(requestURL.getRequestURL(URL_selectDeviceState), map, charset);
		if(re!=null){
			result=JsonUtils.fromJson(re, ResultEntity.class);
			result.setState(true);
		}
		return result;
	}
	
	@Override
	public ResultEntity selLibraryByIdxOrId(String req) {
		ResultEntity result=new ResultEntity();
		Map<String,String> map=new HashMap<>();
		map.put("json", req);
		String re=HttpClientUtil.doPost(requestURL.getRequestURL(URL_selLibraryByIdxOrId), map, charset);
		if(re!=null){
			result=JsonUtils.fromJson(re, ResultEntity.class);
		}
		return result;
		
	}

	@Override
	public String getAllExtTempList(String json) {
		String reqURL=requestURL.getRequestURL(SEL_ALL_EXTTEMPLIST);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	
	@Override
	public String getAllRunTempList(String json) {
		String reqURL=requestURL.getRequestURL(SEL_ALL_RUNTEMPLIST);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}


	@Override
	public String getAllMonitorTempList(String json) {
		String reqURL=requestURL.getRequestURL(SEL_ALL_MONITOR_TEMPLIST);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	@Override
	public String getAllDbSyncTempList(String json) {
		String reqURL=requestURL.getRequestURL(SEL_ALL_DBSYNC_TEMPLIST);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	@Override
	public String QueryDevice(String json) {
		String reqURL=requestURL.getRequestURL(URL_SelectInfo);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	@Override
	public ResultEntity selectBookrackState(String req) {
		ResultEntity result=new ResultEntity();
		String reqURL=requestURL.getRequestURL(URL_selectBookrackState);
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String res=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if(res!=null){
			result=JsonUtils.fromJson(res, ResultEntity.class);
		}
		return result;
	}

	@Override
	public String SelectDBsync(String json) {
		String reqURL=requestURL.getRequestURL(URL_selectDBsync);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	@Override
	public String UpdateDBsync(String json) {
		String reqURL=requestURL.getRequestURL(URL_updateDBsync);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	@Override
	public String InsertDBsync(String json) {
		String reqURL=requestURL.getRequestURL(URL_insertDBsync);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String DeleteDBsyncConfig(String json) {
		String reqURL=requestURL.getRequestURL(URL_deleteDBsync);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	@Override
	public String UpdateDeviceConfig(String json) {
		String reqURL=requestURL.getRequestURL(URL_updateDevConf);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	@Override
	public ResultEntity selectBinState(String req) {
		ResultEntity result=new ResultEntity();
		String reqURL=requestURL.getRequestURL(URL_selectBinState);
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String res=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if(res!=null){
			result=JsonUtils.fromJson(res, ResultEntity.class);
		}
		return result;
	}
	@Override
	public ResultEntity selectDeviceExtState(String req) {
		return postUrl(URL_selectDeviceExtState, req);
	}
	
	
	/*private ResultEntity postUrl(String urlId,String req){
		ResultEntity result=new ResultEntity();
		String reqURL=requestURL.getRquestURL(urlId);
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String res=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if(res!=null){
			result=JsonUtils.fromJson(res, ResultEntity.class);
		}
		return result;
	}*/
	
	@Override
	public ResultEntity selectSoftState(String req) {
		return postUrl(URL_selectSoftState, req);
	}
	@Override
	public ResultEntity selOperatorByOperIdOrIdx(String req) {
		return postUrl(URL_selOperatorByOperIdOrIdx, req);
	}
	
	@Override
	public ResultEntity queryDeviceByParam(String req) {
		return postUrl(URL_queryDeviceByParam, req);
	}
	
	@Override
	public ResultEntity queryControlResult(String req) {
		return postUrl(URL_queryControlResult, req);
	}
	@Override
	public String UpdateMonitor(String json) {
		String reqURL=requestURL.getRequestURL(URL_updateMonitor);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	@Override
	public String InsertMonitor(String json) {
		String reqURL=requestURL.getRequestURL(URL_insertMonitor);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	@Override
	public String DeleteMonitor(String json) {
		String reqURL=requestURL.getRequestURL(URL_deleteMonitor);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	@Override
	public String SelectMonitor(String json) {
		String reqURL=requestURL.getRequestURL(URL_selectMonitor);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	@Override
	public String UpdateExtdata(String json) {
		String reqURL=requestURL.getRequestURL(URL_updateExt);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	@Override
	public String InsertExtdata(String json) {
		String reqURL=requestURL.getRequestURL(URL_insertExt);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	@Override
	public String DeleteExtdata(String json) {
		String reqURL=requestURL.getRequestURL(URL_deleteExt);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	@Override
	public String SelectExt(String json) {
		String reqURL=requestURL.getRequestURL(URL_selectEXT);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	@Override
	public String UpdateRundata(String json) {
		String reqURL=requestURL.getRequestURL(URL_updateRun);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	@Override
	public String InsertRundata(String json) {
		String reqURL=requestURL.getRequestURL(URL_insertRun);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	@Override
	public String DeleteRundata(String json) {
		String reqURL=requestURL.getRequestURL(URL_deleteRun);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	@Override
	public String SelectRun(String json) {
		String reqURL=requestURL.getRequestURL(URL_selectRun);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	@Override
	public String selAllExtModelAndLogicObj(String json) {
		String reqURL=requestURL.getRequestURL(SEL_ALL_EXTMODEL_AND_LOGICOBJ);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
	
	@Override
	public ResultEntity deviceUpd(String req) {
		return postUrl(URL_UpdDeviceMgmtPage, req);
	}
	@Override
	public ResultEntity hydeviceUpd(String req) {
		return postUrl(URL_UpdHyDeviceMgmtPage, req);
	}
	@Override
	public ResultEntity checkAllConfigDataByDevIdx(String req) {
		return  postUrl(URL_checkAllConfigDataByDevIdx, req);
	}
	
	@Override
	public ResultEntity compareMonitorConfig(String req) {
		return postUrl(URL_compareMonitorConfig, req);
	}
	@Override
	public ResultEntity getACSTempList(String req) {
		return postUrl(URL_getACSTempList, req,"json");
	}
	@Override
	public ResultEntity loadAcsLogininfo(String req) {
		return postUrl(URL_loadAcsLogininfo, req);
	}
	@Override
	public ResultEntity getLibraryDevicesByPage(String req) {
		return postUrl(URL_getLibraryDevicesByPage, req);
	}
	@Override
	public ResultEntity queryDeviceIps(String req) {
		return postUrl(URL_queryDeviceIps, req);
	}
	@Override
	public ResultEntity transferToNewLib(String req) {
		return postUrl(URL_transferToNewLib, req);
	}

	@Override
	public ResultEntity queryAllRegion(String req) {
		return postUrl(URL_queryAllRegion, req);
	}
	@Override
	public ResultEntity queryDeviceRegion(String req) {
		return postUrl(URL_queryDeviceRegion, req);
	}

	@Override
	public String GetDevicePosition(String json) {
		String reqURL= requestURL.getRequestURL(URL_getDevicePosition);
		Map<String, String > map = new HashMap<>();
		map.put("req", json);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}	
	
	@Override
	public ResultEntity saveDevicePosition(String req) {
		return postUrl(URL_saveDevicePostion, req);
	}
	@Override
	public String deleteLibraryPosition(String json) {
		String reqURL= requestURL.getRequestURL(URL_deleteLibraryPosition);
		Map<String, String > map = new HashMap<>();
		map.put("req", json);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}
	
	@Override
	public String getLibPosition(String json) {
		String reqURL= requestURL.getRequestURL(URL_getLibPosition);
		Map<String, String > map = new HashMap<>();
		map.put("req", json);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result ;
	}	
	
	@Override
	public ResultEntity insertFileUploadFlag(String req) {
		return postUrl(URL_INSERTFILEUPLOADFLAG, req);
	}
	/*
	 * java
	 */
	@Override
	public String devicesRegister(CommonsMultipartFile multipartFile,String req) throws Exception {
		ResultEntity result = new ResultEntity();
		InputStreamReader isr = null;
		BufferedReader br = null;
		String message=null;
		String str = "";
		String str1 = "";
		String[] strs=null;
		int lineCount = 0;
		
		CommonsMultipartFile cFile = (CommonsMultipartFile) multipartFile;
        DiskFileItem fileItem = (DiskFileItem) cFile.getFileItem();
        InputStream fis = fileItem.getInputStream();
        
        //fis = new FileInputStream("c:\\小志.txt");// FileInputStream
        // 从文件系统中的某个文件中获取字节
        isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
        br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象
        while ((str = br.readLine()) != null) {
        	//strs[lineCount]=str;
        	//lineCount++;
        	result = postUrl(URL_devicesRegister, str);
        	//System.out.println("***** "+str);
        	message = result.getMessage();
        	if(!"success".equals(message)&&!"".equals(message)&&message!=null){
        		str1 += result.getMessage()+"\r\n";
        	}
        }
        
		return str1;
	}

	
}
