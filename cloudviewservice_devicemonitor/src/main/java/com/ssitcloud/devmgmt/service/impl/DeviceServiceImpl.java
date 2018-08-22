package com.ssitcloud.devmgmt.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.util.JSONUtils;

import org.apache.commons.io.FileUtils;
import org.apache.http.Consts;
import org.apache.http.client.utils.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.I18nUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.devmgmt.entity.DevicePageEntity;
import com.ssitcloud.devmgmt.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.devmgmt.entity.MetadataOrderEntity;
import com.ssitcloud.devmgmt.service.DeviceService;

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


	public static final String URL_updateDevConf = "UpdDevConfig";
	public static final String URL_selectBinState="selectBinState";

	public static final String URL_selLibraryByIdxOrId="SelectLibrary";
	public static final String SEND_ORDER="recevieOrder";
	public static final String URL_selectDeviceExtState = "selectDeviceExtState";
	public static final String URL_selectSoftState = "selectSoftState";
	public static final String URL_selOperatorByOperIdOrIdx = "selOperatorByOperIdOrIdx";
	public static final String URL_queryDeviceByParam = "queryDeviceByParam";
	public static final String URL_queryServiceDeviceByParam = "queryServiceDeviceByParam";
	
	public static final String URL_queryControlResult = "queryControlResult";
	public static final String URL_downloadRunLogOperation = "downloadRunLogOperation";
	public static final String URL_GetCurrentDevColorSet = "GetCurrentDevColorSet";
	public static final String URL_GetDevExtModel = "GetDevExtModel";
	private static final String URL_GetLastHeatBeatTime = "GetLastHeatBeatTime";
	private static final String URL_SelFunctionByDeviceIdx = "SelFunctionByDeviceIdx";
	
	private static final String URL_QueryBizData = "QueryBizData";
	private static final String URL_CountLoanLog = "CountLoanLog";
	private static final String URL_CountCardissueLog = "CountCardissueLog";
	private static final String URL_CountFinanceLog = "CountFinanceLog";
	private static final String URL_CountVisitorLog = "CountVisitorLog";
	private static final String URL_GetDevicePosition = "GetDevicePosition";
	private static final String URL_GetLibPosition = "GetLibPosition";
	private static final String URL_QuerylibInfoByCurUser = "querylibInfoByCurUser";
	private static final String URL_SelectDevicesInfo = "SelectDevicesInfo";
	private static final String URL_selectDeviceCountByLibraryIdx = "selectDeviceCountByLibraryIdx";
	private static final String URL_selectCountDeviceService = "selectCountDeviceService";
	private static final String URL_countDatas = "countDatas";
	private static final String URL_queryDevStatusCode = "queryDevStatusCode";
	
	
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
		if(JSONUtils.mayBeJSON(result)){
			ResultEntityF<DevicePageEntity> rs=JsonUtils.fromJson(result, new TypeReference<ResultEntityF<DevicePageEntity>>() {});
			if(rs!=null){
				return rs.getResult();
			}
		}
		return null;
	}
	
	@Override
	public String sendOrder(String req){
		Map<String,String> map=new HashMap<>();
		map.put("req", req); 
//		String re=HttpClientUtil.doPost(requestURL.getRequestURL(SEND_ORDER), map, charset);
		String re=HttpClientUtil.doPostSSL(requestURL.getRequestURL(SEND_ORDER), map, charset);
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
		String re=HttpClientUtil.doPostSSL(requestURL.getRequestURL(URL_queryDeviceLog), map, charset);
//		String re=HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryDeviceLog), map, charset);
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
	public ResultEntity queryServiceDeviceByParam(String req) {
		return postUrl(URL_queryServiceDeviceByParam, req);
	}
	
	
	@Override
	public ResultEntity queryControlResult(String req) {
//		return postUrl(URL_queryControlResult, req);
		return postUrlSSL(URL_queryControlResult, req);
	}
	
	@Override
	public ResultEntity downloadRunLogOperation(String req) {
//		return postUrl(URL_downloadRunLogOperation, req);
		return postUrlSSL(URL_downloadRunLogOperation, req);
	}
	/**
	 * 查找是否存在可下载的文件，<br>
	 * 如果不存在则发送请求到同步程序，<br>
	 * 并在本地写入文件<br>
	 * req={
	 * 	"utcStartTime":"",
	 *  "utcEndTime":"",
	 *  "device_id":"",
	 * }
	 * @author lbh
	 * @param request
	 * @param req
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity checkDownLoadLog(String req) throws IOException {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			Map<String,Object> map=JsonUtils.fromJson(req, Map.class);
			if(map!=null){
				if(map.get("utcStartTime")==null){
					result.setValue(false, "", "下载开始时间不能为空", null);
					return result;
				}
				if(map.get("utcEndTime")==null){
					result.setValue(false, "", "下载结束时间不能为空", null);
					return result;
				}
				if(map.get("device_id")==null){
					result.setValue(false, "", "设备ID不能为空", null);
					return result;
				}
				//UTC时间
				DateTime sTime=new DateTime(DateUtils.parseDate(map.get("utcStartTime").toString())).withZone(DateTimeZone.UTC);
				DateTime eTime=new DateTime(DateUtils.parseDate(map.get("utcEndTime").toString())).withZone(DateTimeZone.UTC);
				int miusDay=Days.daysBetween(sTime, eTime).getDays();
				LogUtils.info("查询时间间隔:"+miusDay+"天");
				if(miusDay>7){
					result.setValue(false, "", "查询时间间隔不能超过7天", null);
					return result;
				}
				//定义存储的文件名
				String sStrTime=sTime.toString("yyyyMMddHHmmss");
				String eStrTime=eTime.toString("yyyyMMddHHmmss");
				String fileName=sStrTime+"_"+eStrTime+".log";
				//路径  /tmp/devLog/设备ID/开始时间_结束时间.log
				String filePath=System.getProperty("java.io.tmpdir")+File.separator+"devLog"+File.separator+map.get("device_id").toString()+File.separator+fileName;
				File file=new File(filePath);
				if(!file.exists()){
					if(!file.getParentFile().exists()){
						boolean isMk=file.getParentFile().mkdirs();
						if(isMk){
							boolean isCreated=file.createNewFile();
							if(isCreated){//创建文件成功
								
							}else{
								//创建失败
								LogUtils.error("创建本地临时文件失败！filePath："+filePath);
								return result;
							}
						}
					}
				}
				/**
				 * message 的情况有
				 * NO_RESULT （第一次查询的情况下）
				 * DELETE （在改变查询的时间的情况下）
				 */
				
				ResultEntity re=downloadRunLogOperation(req);
				if(re.getState()){
					if("DELETE".equals(re.getMessage())){
						//数据已经被清除掉了，需要重新等待时间，后才能开始下载
						LogUtils.info("下载选择的时间段发生变化，需重新等待下载");
						result.setValue(false, "DELETE_NEED_SEND_ORDER", "下载选择的时间段发生变化，需重新等待数据下载", null);
						return result;
					}else if(re.getResult()!=null){
						Map<String,Object> logMap=(Map<String, Object>) re.getResult();
						LogUtils.info("同步程序返回的日志数据："+logMap);
						if(logMap!=null){
							boolean isOverride=false;
							for(Entry<String, Object> e:logMap.entrySet()){
								Map<String,String> page=(Map<String, String>) e.getValue();
								String nowPage=page.get("nowPage");
								String contents=page.get("contents");
								if(!isOverride){
									//如果存在则覆盖
									LogUtils.info("覆盖原有日志文件："+filePath);
									FileUtils.writeStringToFile(file, contents+"\n第"+nowPage+"页\n", Consts.UTF_8, false);
									isOverride=true;
								}else{
									LogUtils.info("追加原有日志文件："+filePath);
									FileUtils.writeStringToFile(file, contents+"\n第"+nowPage+"页\n", Consts.UTF_8, true);
								}
								
							}
						}
						result.setState(true);
						result.setResult(file.getPath());
					}
				}else if("CUR_NO_RESULT".equals(re.getMessage())){
					//false 暂时没有找到数据
					result.setMessage("NEED_SEND_ORDER");
					result.setRetMessage("暂时没有数据，请稍后再次点击按钮");
				}else if("NO_RESULT".equals(re.getMessage())){
					//false 首次查询
					//sendOrder
					result.setMessage("NEED_SEND_ORDER");
					result.setRetMessage("请稍后再次点击按钮");
				}
			}
		}
		return result;
	}
	
	
	@Override
	public ResultEntity GetCurrentDevColorSet(String req) {
		return postUrl(URL_GetCurrentDevColorSet, req);
	}
	
	@Override
	public ResultEntity GetDevExtModel(String req) {
		return postUrl(URL_GetDevExtModel, req);
	}
	
	@Override
	public ResultEntity getLastHeatBeatTime(String req) {
		return postUrlSSL(URL_GetLastHeatBeatTime, req);
	}
	@Override
	public ResultEntity SelFunctionByDeviceIdx(String req) {
		return postUrl(URL_SelFunctionByDeviceIdx, req);
	}
	@Override
	public ResultEntity QueryBizData(String req) {
		return postUrl(URL_QueryBizData, req);
	}
	@Override
	public ResultEntity countLoanLog(String req) {
		return postUrl(URL_CountLoanLog, req);
	}
	@Override
	public ResultEntity countCardissueLog(String req) {
		return postUrl(URL_CountCardissueLog, req);
	}
	@Override
	public ResultEntity countFinanceLog(String req) {
		return postUrl(URL_CountFinanceLog, req);
	}
	@Override
	public ResultEntity countVisitorLog(String req) {
		return postUrl(URL_CountVisitorLog, req);
	}
	public ResultEntity getLibPosition(String req){
		return postUrl(URL_GetLibPosition, req);
	}
	@Override
	public ResultEntity getDevicePosition(String req) {
		return postUrl(URL_GetDevicePosition, req);
	}
	@Override
	public ResultEntity querylibInfoByCurUser(String req) {
		return postUrl(URL_QuerylibInfoByCurUser, req);
	}
	
	@Override
	public ResultEntity SelectDevicesInfo(String req) {
		ResultEntity resultEntity=new ResultEntity();
		Map<String,String> map=new HashMap<>();
		map.put("json",req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_SelectDevicesInfo), map, charset);
		if(JSONUtils.mayBeJSON(result)){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
	}
	
	@Override
	public ResultEntity selectDeviceCountByLibraryIdx(String req) {
		ResultEntity resultEntity=new ResultEntity();
		Map<String,String> map=new HashMap<>();
		map.put("req",req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_selectDeviceCountByLibraryIdx), map, charset);
		if(JSONUtils.mayBeJSON(result)){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return resultEntity;
	}
	
	@Override
	public ResultEntity selectCountDeviceService(String req) {
		ResultEntity resultEntity=new ResultEntity();
		Map<String,String> map=new HashMap<>();
		map.put("req",req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_selectCountDeviceService), map, charset);
		if(JSONUtils.mayBeJSON(result)){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return resultEntity;
	}
	
	@Override
	public ResultEntity countDatas(String req) {
		ResultEntity resultEntity=new ResultEntity();
		Map<String,String> map=new HashMap<>();
		map.put("req",req);
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_countDatas), map, charset);
		if(JSONUtils.mayBeJSON(result)){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return resultEntity;
	}
	
	public ResultEntity queryDevStatusCode(){
		ResultEntity resultEntity=new ResultEntity();
		Map<String,String> map=new HashMap<>();
		String result = HttpClientUtil.doPost(requestURL.getRequestURL(URL_queryDevStatusCode), map, charset);
		if(JSONUtils.mayBeJSON(result)){
			return JsonUtils.fromJson(result, ResultEntity.class);
		}
		return resultEntity;
	}
}
