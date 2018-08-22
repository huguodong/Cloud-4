package com.ssitcloud.devmgmt.service.impl;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.apache.http.client.utils.DateUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.type.TypeReference;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.ssitcloud.common.entity.CloudSyncRequest;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.entity.UserRolePermessionEntity;
import com.ssitcloud.common.netty.server.handler.CloudSyncHandlerAdapter;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.ExceptionHelper;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.datasync.entity.AskControlResultEntity;
import com.ssitcloud.datasync.entity.ConstsEntity;
import com.ssitcloud.datasync.entity.ControlResultEntity;
import com.ssitcloud.datasync.entity.DeviceIdx2IdContainer;
import com.ssitcloud.datasync.entity.HeartBeatChangeTableData;
import com.ssitcloud.datasync.entity.HeartBeatDataSyncMsg;
import com.ssitcloud.datasync.entity.HeartBeatDeviceOrder;
import com.ssitcloud.datasync.entity.HeartBeatDownloadAppCardInfo;
import com.ssitcloud.datasync.entity.UploadRunLogEntity;
import com.ssitcloud.datasync.service.impl.HeartBeat;
import com.ssitcloud.devmgmt.entity.AppCardInfo;
import com.ssitcloud.devmgmt.entity.DeviceOrder;
import com.ssitcloud.devmgmt.entity.TableChangeTriEntity;
import com.ssitcloud.devmgmt.service.DeviceService;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;


@Service
public class DeviceServiceImpl extends BasicServiceImpl implements DeviceService{
	@Resource(name = "heartBeatDeviceOrder")
	private HeartBeatDeviceOrder heartBeatDeviceOrder;
	@Resource
	private HeartBeatDataSyncMsg heartBeatDataSyncMsg;
	//同步数据专用容器
	@Resource
	private HeartBeatChangeTableData  heartBeatChangeTableData;
	//传输手机数据容器
	@Resource
	private HeartBeatDownloadAppCardInfo heartBeatDownloadAppCardInfo;
	
	@Resource
	private DeviceIdx2IdContainer deviceIdx2IdContainer;
	
	private final static String charset=Consts.UTF_8.toString();
	
	@Resource(name = "requestURLListEntity")
	protected RequestURLListEntity requestURLListEntity;
	
	@Resource(name="hazelcastInstance")
	private HazelcastInstance hazelcastInstance;
	
	
	private static final String URL_SELOPERATORBYOPERIDORIDX = "selOperatorByOperIdOrIdx";
	
	private static final String URL_SelUserCmdsByIdxAndRestriDevice="SelUserCmdsByIdxAndRestriDevice";
	
	private static final String DOWNLOADAPPCARDINFO = "downloadAppCardInfo";//下载读者卡信息的权限名称
	
	private ConcurrentMap<String, String> queryTime=new ConcurrentHashMap<String, String>(100);
	//@Resource
	//private ControlContainer controlContainer;
	@Resource(name="heartBeat")
	private HeartBeat heartBeat;
	
	
	public ResultEntity transforOrderData(String json){
		ResultEntity resp=new ResultEntity();
		try {
			List<DeviceOrder> orders=JsonUtils.fromJson(json, new TypeReference<List<DeviceOrder>>() {});
			if(orders==null) return resp;
			
			IMap<String,ConcurrentLinkedQueue<DeviceOrder>> queryRunLogOrder=hazelcastInstance.getMap(ConstsEntity.QUERY_RUNNING_LOG_ORDER);
			//devIdList 保存 已经存在的相同命令的 device_id
			List<String> devIdList=new ArrayList<>();
			//存放 isAlreadyHasQueryOrder ：
			Map<String,Object> resMessage=new HashMap<String, Object>();
			StringBuilder sbDevId=new StringBuilder("设备ID组:");
			String sbOrder=null;
			Integer library_idx=null;
			for(DeviceOrder order:orders){
				String device_id=order.getDevice_id();
				String operator_id=order.getOperator_id();
				String key=device_id;
				if(StringUtils.hasText(device_id)){
					if(ConstsEntity.QUERY_RUNNING_LOG_FLAG.equals(order.getControl_info())){//设备日志查询
						Date sd=DateUtils.parseDate(order.getStart_time());
						Date ed=DateUtils.parseDate(order.getEnd_time());
						order.setStart_time(new DateTime(sd).withZone(DateTimeZone.UTC).toString());
						order.setEnd_time(new DateTime(ed).withZone(DateTimeZone.UTC).toString());
			
							key=device_id+"#"+operator_id;
							//查询日志的命令
							if(queryRunLogOrder.containsKey(key)){
								ConcurrentLinkedQueue<DeviceOrder> queue=queryRunLogOrder.get(key);
								if(queue.isEmpty()){
									queue.add(order);
									queryRunLogOrder.put(key, queue);
								}else{
									//队列原来存在命令则
									resp.setMessage("isAlreadyHasQueryOrder");
								}
							}else{
								ConcurrentLinkedQueue<DeviceOrder> queue=new ConcurrentLinkedQueue<DeviceOrder>();
								queue.add(order);
								queryRunLogOrder.put(key, queue);
							}
					}
					
					order.setReceiveTime(new Date()); //设置指令的接收时间
					
					if(heartBeatDeviceOrder.containsKey(device_id)){
						if(heartBeatDeviceOrder.get(device_id).contains(order)){
							//客户端发送的命令在队列中已经存在，则不再加入队列
							resp.setMessage("isAlreadyHasSameOrders");
							devIdList.add(device_id);//add device_id
							continue;
						}
						//如果存在则获取该设备的队列
						heartBeatDeviceOrder.get(device_id).add(order);
						if(sbOrder==null){
							sbOrder=order.getControlInfoDesc();
						}
						if(library_idx==null){
							library_idx=order.getLibrary_idx();
						}
						sbDevId.append(device_id).append(",");
						//resp.setRetMessage(retMessage);// 馆IDX｜维护指令｜设备ID组
					}else{
						//若果不存在则新建一个list,并保存
						ConcurrentLinkedQueue<DeviceOrder> orderQueue=new ConcurrentLinkedQueue<DeviceOrder>();
						orderQueue.add(order);
						heartBeatDeviceOrder.put(device_id,orderQueue);
						if(sbOrder==null){
							sbOrder=order.getControlInfoDesc();
						}
						if(library_idx==null){
							library_idx=order.getLibrary_idx();
						}
						sbDevId.append(device_id).append(",");
					}
				}
			}
			resp.setRetMessage("馆IDX:"+library_idx+"|维护指令:"+sbOrder+"|"+sbDevId.toString().substring(0, sbDevId.length()-1));
			resp.setResult(devIdList);
			resp.setState(true);
			LogUtils.info("heartBeatContainer size:"+heartBeatDeviceOrder.size());
		} catch (Exception e) {
			ExceptionHelper.afterException(resp, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resp;
	}
	
	
	/***
	 * 发送指令到设备端：1关机 2 重启 4远程维护锁屏 
	 * @param json [{"device_id":"QJSCH004","control":"1","control_info":"1","library_idx":"167"}]
	 * @return
	 */
	public ResultEntity sendOrderToDevice(String json){
		
		ResultEntity resultEntity = new ResultEntity();
		List<DeviceOrder> orders=JsonUtils.fromJson(json, new TypeReference<List<DeviceOrder>>() {});
		if(orders==null || orders.isEmpty()) return resultEntity;
		DeviceOrder deviceOrder = orders.get(0);
		
		if(deviceOrder == null || StringUtils.isEmpty(deviceOrder.getControl_info())){
			resultEntity.setMessage("指令为空");
			return resultEntity;
		}
		
		CloudSyncRequest<Map<String,String>> cloudSyncRequest = new CloudSyncRequest<Map<String,String>>();
		cloudSyncRequest.setServicetype("ssitcloud");
		cloudSyncRequest.setTarget("device");
		cloudSyncRequest.setOperation("controlResult");
		
		String lib_id = "";
		if(deviceOrder.getLibrary_idx() != null){
			lib_id = JedisUtils.getInstance().get(RedisConstant.LIBRARY_KEYS+deviceOrder.getLibrary_idx());
		}
		if(StringUtils.isEmpty(lib_id) || StringUtils.isEmpty(deviceOrder.getDevice_id())){
			resultEntity.setMessage("获取设备Id或图书馆Id失败");
			return resultEntity;
		}
		
		
		AskControlResultEntity askControlResult = new AskControlResultEntity(
				deviceOrder.getDevice_id(), lib_id,deviceOrder.getControl_info(), deviceOrder.getStart_time(),
				deviceOrder.getEnd_time(), deviceOrder.getOperator_id());
		
		
		List<Map<String,String>> data = new ArrayList<>();
		Map<String,String> dataMap = JsonUtils.convertMap(askControlResult,new TypeReference<Map<String,String>>(){});
		data.add(dataMap);
		cloudSyncRequest.setData(data);
		cloudSyncRequest.setClientId(cloudSyncRequest.getCacheClient(lib_id, deviceOrder.getDevice_id()));
		cloudSyncRequest.setDevice_id(deviceOrder.getDevice_id());
		cloudSyncRequest.setLib_id(lib_id);
		CloudSyncHandlerAdapter handler = new CloudSyncHandlerAdapter();
		handler.sendRequest(cloudSyncRequest, cloudSyncRequest.getClientId());
		return resultEntity;
		
	}
	
	
	
	/*
	 *  
	 */
	@Override
	public ResultEntity transforChangeTableData(String req) {
		ResultEntity resp=new ResultEntity();
		try {
			ResultEntityF<List<TableChangeTriEntity>> result=JsonUtils.fromJson(req, new TypeReference<ResultEntityF<List<TableChangeTriEntity>>>() {});
			if(result!=null&&result.getState()==true){
				List<TableChangeTriEntity> tableChanges=result.getResult();
				if(tableChanges!=null){
					for(TableChangeTriEntity tableChange:tableChanges){
						 Integer dataIdx=tableChange.getData_idx();
						 if(dataIdx!=null&&deviceIdx2IdContainer.containsKey(dataIdx)){
							String device_id=deviceIdx2IdContainer.get(dataIdx);
							if(heartBeatChangeTableData.containsKey(device_id)){
								//存在
								heartBeatChangeTableData.get(device_id).add(tableChange);
							}else{
								//若果不存在则新建一个list,并保存
								ConcurrentLinkedQueue<TableChangeTriEntity> tableChangeQueue=new ConcurrentLinkedQueue<>();
								tableChangeQueue.add(tableChange);
								heartBeatChangeTableData.put(device_id, tableChangeQueue);
							}
						 }
					}
				}
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(resp, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resp;
	}
	
	@Override
	public ResultEntity queryDeviceLog(String req) {
		ResultEntity result=new ResultEntity();
		//req--->{device_id,libray_id,....startTime,endTime,curPage}
		JsonNode node=JsonUtils.readTree(req);
		String device_id=node.get("device_id").asText();
		String startTime=node.get("start_time").asText();
		String endTime=node.get("end_time").asText();
		String operator_id=node.get("operator_id").asText();
		//String curPage=node.get("current_page").asText();//当前页
		//int pageSize=node.get("page_size").asInt(2);//每页显示内容
		String key=device_id;
		if(queryTime.containsKey(key)){
			//查询时间 存在当前设备Id
			String startTime_endTime=queryTime.get(key);
			IMap<String,TreeMap<String,UploadRunLogEntity>> container=hazelcastInstance.getMap(ConstsEntity.UPLOAD_RUNLOG_CONTAINER);
			if(container!=null){
				if((startTime+"_"+endTime).trim().equals(startTime_endTime)){
					//时间相同则，返回数据
					if(container.containsKey(key)){
						Map<String,UploadRunLogEntity> set=container.get(key);
						//if(set.containsKey(curPage)){
							//List<UploadRunLogEntity> page=new ArrayList<>();
							//for(int i=0;i<pageSize;i++){
							//page.add(set.get(curPage));
						    //}
							result.setResult(set);
						//}
						result.setMessage("SUCCESS");
						result.setState(true);
						//result.setResult(set.remove(o));
					}
				}else{
					//查询日志参数时间不相同则删除原来时间,并且清空日志数据
					queryTime.put(key, (startTime+"_"+endTime).trim());
					container.remove(key);
					result.setState(true);
					result.setMessage("DELETE");
					result.setResult(null);
				}
			}
		}else{//没有找到时间，则加入时间
			queryTime.put(key, (startTime+"_"+endTime).trim());
		}
		
		return result;
	}
	
	//  reset shutdown queryRunningLog 
	//
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity queryControlResult(String req) {
		ResultEntity result=new ResultEntity();
		List<Map<String,String>> deviceIdList=JsonUtils.fromJson(req, List.class);
		List<ControlResultEntity> resultList=new ArrayList<>();
		//JsonNode node=JsonUtils.readTree(req);
		if(deviceIdList!=null){
			IMap<String, ConcurrentLinkedQueue<ControlResultEntity>> controlResultContainer=hazelcastInstance.getMap(ConstsEntity.CONTROL_RESULT_CONTAINER);
			//String device_id=node.get("device_id").asText();
			//String operator_id=node.get("operator_id").asText();
			for(Map<String,String> orderMap:deviceIdList){
				String key=orderMap.get("device_id");
				if(controlResultContainer!=null){
					ConcurrentLinkedQueue<ControlResultEntity> queue=controlResultContainer.get(key);
					if(queue!=null){
						while(!queue.isEmpty()){
							ControlResultEntity controlResultEntity=queue.poll();
							resultList.add(controlResultEntity);
						}
						controlResultContainer.put(key, queue);
					}
				}
			}
			if(resultList.size()==0){
				result.setMessage("CURRENT_NO_RESULT");
			}else if(resultList.size()>0){
				result.setMessage("HAS_RESULT");
			}
			result.setState(true);
			result.setResult(resultList);
			
		}
		return result;
	}
	
	/**
	 * 和查询是一样的
	 * 
	 * req={
    		"utcStartTime":utcStartTime,
    		"utcEndTime":utcEndTime,
    		"device_id":device_id,
    		"operator_idx":"....." 
    	};
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity downloadRunLogOperation(String req) {
		ResultEntity result=new ResultEntity();
		if(JSONUtils.mayBeJSON(req)){
			Map<String, String> map=JsonUtils.fromJson(req, Map.class);
			if(map!=null){
				if(!map.containsKey("device_id")){
					result.setValue(false,"","设备ID不能为空");
					return result;
				}
				if(!map.containsKey("operator_id")){
					result.setValue(false,"","用户ID不能为空");
					return result;
				}
				String utcStartTime=map.get("utcStartTime");
				String utcEndTime=map.get("utcEndTime");
				
				//转成当前服务器时间
				Date sd=DateUtils.parseDate(utcStartTime);
				Date ed=DateUtils.parseDate(utcEndTime);
				if(sd==null){
					result.setValue(false,"","开始时间不能为空");
					return result;
				}
				if(ed==null){
					result.setValue(false,"","结束时间不能为空");
					return result;
				}
				
				String sTime=new DateTime(sd).withZone(DateTimeZone.UTC).toString();
				String eTime=new DateTime(ed).withZone(DateTimeZone.UTC).toString();
				
				String device_id=map.get("device_id");
				String operator_id=map.get("operator_id");
				String key=device_id;
				//表示存在，这个账号查询这个设备的
				if(queryTime.containsKey(key)){
					//查询时间 存在当前设备Id
					String sTime_eTime=queryTime.get(key);
					IMap<String,TreeMap<String,UploadRunLogEntity>> container=hazelcastInstance.getMap(ConstsEntity.UPLOAD_RUNLOG_CONTAINER);
					if(container!=null){
						//查询的时间间隔相同的时候
						if((sTime+"_"+eTime).trim().equals(sTime_eTime)){
							//时间相同则，返回数据
							if(container.containsKey(key)){
								Map<String,UploadRunLogEntity> treeMap=container.get(key);
								result.setResult(treeMap);
								result.setState(true);
							}else{
								//没有存在key，则是没有数据
								result.setMessage("CUR_NO_RESULT");
								result.setValue(false,"","暂无数据");
							}
						}else{
							//查询日志参数时间不相同则删除原来时间,并且清空原来容器中的日志数据
							queryTime.put(key, (sTime+"_"+eTime).trim());
							container.remove(key);
							result.setState(true);
							result.setMessage("DELETE");
							result.setResult(null);
						}
					}
				}else{
					//没有找到key，则加入时间
					//应该为首次查询或者下载数据
					queryTime.put(key, (sTime+"_"+eTime).trim());
					result.setState(false);
					result.setMessage("NO_RESULT");
					result.setResult(null);
				}
			}
		}
		return result;
	}
	/**
	 * req=[
	 * {
	 *  devId:"",
	 *  libid:""
	 * },
	 * {
	 * 
	 * },```,``,``,``,]
	 * 
	 * 返回设备数组的最后一次heartBeat时间
	 */
	@Override
	public ResultEntity getLastHeatBeatTime(String req) {
		ResultEntity result=new ResultEntity();
		List<Map<String,Object>> arr=new ArrayList<>();
		if(JSONUtils.mayBeJSON(req)){
			List<String> arrDev=JsonUtils.fromJson(req, new TypeReference<List<String>>() {});
			if(arrDev!=null && arrDev.size()>0){
				for(String m:arrDev){
					Map<String,Object> mr=new HashMap<>();
					//String devId=m.get("devId");
					String devId=m;
					//如果查询不到时间（NULL） 则 说明 没有heartbeat 比如程序刚重启，则 全部超时？反正没有数据
					Long l=heartBeat.getLastHeartBeatTime(devId);
					if(l==null){
						l=0L;
					}
					mr.put("lastHeartBeatTime", l);
					mr.put("devId", devId);
					arr.add(mr);
				}
			}
		}
		result.setState(true);
		result.setResult(arr);
		return result;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultEntity transforAppData(String json) {
		ResultEntity resp=new ResultEntity();
		try {
			AppCardInfo cardInfo = JsonUtils.fromJson(json, AppCardInfo.class);

			if(cardInfo == null) return resp;
			
			String device_id = cardInfo.getDevice_id();
			boolean goon = false;
			
			if(StringUtils.hasText(device_id)){
				//根据设备ID获取  鉴权数据库中设备的operator_idx,然后查询这个设备的权限
				Map<String, String> param = new HashMap<>();
				param.put("req", "{\"operator_id\":\""+device_id+"\"}");
				String operResult = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_SELOPERATORBYOPERIDORIDX), param, charset);
				if (StringUtils.hasText(operResult)) {
					ResultEntity operEntity = JsonUtils.fromJson(operResult, ResultEntity.class);
					if (operEntity.getState()) {
						//验证通过,返回operator ，然后查询权限 配置更改-->mysql 上传 mongodb 下载升级包权限
						Object resObj=operEntity.getResult();
						Map<String,Object> operatorMap = null;
						if(resObj instanceof Map){
							operatorMap=(Map<String, Object>) operEntity.getResult();//Map类型
						}else{
							LogUtils.info(resObj.toString());
						}
						Map<String, String> params = new HashMap<>();
						params.put("req", JsonUtils.toJson(operatorMap));
						String resultOpercmd=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_SelUserCmdsByIdxAndRestriDevice), params, charset);
						
						if(JSONUtils.mayBeJSON(resultOpercmd)){
							AntPathMatcher matcher=new AntPathMatcher();
							boolean isMatch = false;
							ResultEntityF<List<UserRolePermessionEntity>> res=JsonUtils.fromJson(resultOpercmd, new TypeReference<ResultEntityF<List<UserRolePermessionEntity>>>() {});
							if(res!=null){
								List<UserRolePermessionEntity> permessions=res.getResult();
								if(permessions!=null){
									String cmdUrls="";
									for(UserRolePermessionEntity permession:permessions){
										String opercmdUrl=permession.getOpercmd_url();
										if(StringUtils.hasText(opercmdUrl)){
											cmdUrls+=opercmdUrl+",";
										}
									}
									if(StringUtils.hasText(cmdUrls)){
										cmdUrls=cmdUrls.substring(0, cmdUrls.length()-1);
									}
									isMatch=matcher.match("*"+DOWNLOADAPPCARDINFO+"*", cmdUrls);
									if(isMatch){
										//如果有下载读者卡信息的权限，判断发送给设备的控制指令中有没有  关机 重启 等指令， 如果有，删除，并且通知到页面
										if (heartBeatDeviceOrder.containsKey(device_id)) {
											ConcurrentLinkedQueue<DeviceOrder> deviceOrders = heartBeatDeviceOrder.get(device_id);
											if (deviceOrders != null && !deviceOrders.isEmpty()) {// 不为空
												for (DeviceOrder deviceOrder : deviceOrders) {
													if (deviceOrder.getControl_info().contains("1") || deviceOrder.getControl_info().contains("2")) {//
														deviceOrders.remove(deviceOrder);//删除指令
														//然后通知到页面
														
													}
												}
												System.out.println(deviceOrders.size());
											}
										}
									}else {
										//没有下载读者卡信息的权限
										resp.setState(false);
										resp.setMessage("设备没有下载读者卡信息权限");
										return resp;
									}
								}else{
									//没有获取到设备的权限
									resp.setState(false);
									resp.setMessage("没有获取到设备的权限");
									return resp;
								}
							}
						}else{
							//没有获取到设备的权限
							resp.setState(false);
							resp.setMessage("没有获取到设备的权限");
							return resp;
						}
						
					}
				}
				if(heartBeatDownloadAppCardInfo.containsKey(device_id)){
					//判断是不是跟之前的指令一样，如果一样的话则不发送
					//系统组成(1 IOS  2 Android)|密钥版本号|图书馆ID|读者证号|图书列表(多个条码号之间用逗号分隔)|密文
					
					cardInfo.setReceiveTime(new Date());//设置指令接收时间
					
					if(StringUtils.hasText(cardInfo.getApp_data())){
						ConcurrentLinkedQueue<AppCardInfo> cardQueue = heartBeatDownloadAppCardInfo.get(device_id);
						for (AppCardInfo info : cardQueue) {
							if (info.equals(cardInfo)) {
								goon = true;
								break;
							}
						}
						if(!goon){
							//如果存在则获取该设备的队列
							heartBeatDownloadAppCardInfo.get(device_id).add(cardInfo);
						}
					}
				}else{
					//若果不存在则新建一个list,并保存
					ConcurrentLinkedQueue<AppCardInfo> cardQueue = new ConcurrentLinkedQueue<AppCardInfo>();
					cardQueue.add(cardInfo);
					heartBeatDownloadAppCardInfo.put(device_id,cardQueue);
				}
			}
			
			resp.setState(true);
			LogUtils.info("heartBeatDownloadAppCardInfo size:" + heartBeatDownloadAppCardInfo.size());
		} catch (Exception e) {
			ExceptionHelper.afterException(resp, Thread.currentThread().getStackTrace()[1].getMethodName(), e);
		}
		return resp;
	}


	@Override
	public ResultEntity downloadConfig(String req) {
		ResultEntity entity = new ResultEntity();
		if(!StringUtils.isEmpty(req)){
			System.out.println(req);
			CloudSyncRequest cloudSyncRequest = JsonUtils.fromJson(req, CloudSyncRequest.class);
			cloudSyncRequest.setServicetype("ssitcloud");
			cloudSyncRequest.setTarget("device");
			cloudSyncRequest.setOperation("downloadCfgSync");
			
			CloudSyncHandlerAdapter handler = new CloudSyncHandlerAdapter();
			entity =handler.sendRequest(cloudSyncRequest, cloudSyncRequest.getClientId());
		}
		return entity;
	}
	
	
	
}
