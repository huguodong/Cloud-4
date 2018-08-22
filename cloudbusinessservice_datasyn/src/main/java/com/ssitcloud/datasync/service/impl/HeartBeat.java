package com.ssitcloud.datasync.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.Log;
import com.ssitcloud.datasync.entity.HeartBeatChangeTableData;
import com.ssitcloud.datasync.entity.HeartBeatDeviceOrder;
import com.ssitcloud.datasync.entity.HeartBeatDownloadAppCardInfo;
import com.ssitcloud.datasync.entity.HeartBeatFileUploadState;
import com.ssitcloud.datasync.entity.HeartBeatLibraryInfoData;
import com.ssitcloud.datasync.entity.HeartBeatMidVersionData;
import com.ssitcloud.datasync.service.DataSyncCommand;
import com.ssitcloud.devmgmt.entity.AppCardInfo;
import com.ssitcloud.devmgmt.entity.DeviceOrder;
import com.ssitcloud.devmgmt.entity.FileUploadState;
import com.ssitcloud.devmgmt.entity.SyncConfigEntity;
import com.ssitcloud.devmgmt.entity.TableChangeTriEntity;

@Component(value="heartBeat")
public class HeartBeat extends BasicServiceImpl implements DataSyncCommand{

	@Resource(name = "heartBeatDeviceOrder")
	private HeartBeatDeviceOrder heartBeatDeviceOrder;
	
	@Resource(name="heartBeatChangeTableData")
	private HeartBeatChangeTableData heartBeatChangeTableData;
	
	@Resource(name="heartBeatLibraryInfoData")
	private HeartBeatLibraryInfoData heartBeatLibraryInfoData;
	
	@Resource(name="heartBeatMidVersionData")
	private HeartBeatMidVersionData heartBeatMidVersionData;
	
	@Resource(name="heartBeatFileUploadState")
	private HeartBeatFileUploadState heartBeatFileUploadState;
	
	@Resource
	private HeartBeatDownloadAppCardInfo heartBeatDownloadAppCardInfo;
	/**
	 * 存放 设备最后一次heartBeat时间
	 * 用于 device_monitor checktime out 
	 */
	private final ConcurrentMap<String, Long> timeKeeperMap=new ConcurrentHashMap<>(100);
	/**
	 * 保存各个设备的最后心跳时间
	 * @param devId
	 * @param curTime
	 */
	private void putLastHeartBeatTime(String devId, Long curTime){
		timeKeeperMap.put(devId, curTime);
	}

    public Long getLastHeartBeatTime(String devId) {
        if (devId == null) {
            return null;
        }
        return timeKeeperMap.get(devId);
    }

    /**
	 * more thing
	 * 需要监控到容器里面的的
	 * 
	 * 
	 * 
	 * 2016年12月1日 10:04:40 现在监控超时根据 HeartBeat状态确定，（原来有上传状态数据的时间判断）
	 */
	@Override
	public RespEntity execute(RequestEntity conditionInfo) {
		RespEntity resp = new RespEntity(conditionInfo);
		Map<String, Object> map = conditionInfo.getData();
		//换成IDX
		// key 由device_id组成，例如 SSL_0001
		String key =(String) map.get(DEV_ID);//机器指令的key
		Map<String, Object> result = new HashMap<>();
		String hasResControl="";
		Object[] obj= null;
		
		if(key!=null){
			putLastHeartBeatTime(key, System.currentTimeMillis());
		}
		if (key != null && heartBeatDeviceOrder.containsKey(key)) {
			//机器命令
			Queue<DeviceOrder> deviceOrders = heartBeatDeviceOrder.get(key);
			if (deviceOrders != null && deviceOrders.size() > 0) {// 表示存在操作
				hasResControl=hasResControl+"1,";//下发给设备的指令
			}
		}
		
		if(map.get(DEV_ID)!=null&&heartBeatChangeTableData.containsKey(map.get(DEV_ID))){
			Object dev_id=map.get(DEV_ID);
			
			Queue<TableChangeTriEntity> tableChanges = heartBeatChangeTableData.get(dev_id);
			if(tableChanges!=null&&tableChanges.size()>0){
				hasResControl=hasResControl+"2,";//同步信息，同步数据
			}
		}
		
		if(map.get(DEV_ID)!=null && heartBeatMidVersionData.containsKey(map.get(DEV_ID))) {
			Object dev_id=map.get(DEV_ID);
			
			Queue<TableChangeTriEntity> midVersions = heartBeatMidVersionData.get(dev_id);
			if(midVersions!=null&&midVersions.size()>0){
				hasResControl=hasResControl+"3,";//升级版本信息
			}
		}
		
		if(map.get(DEV_ID)!=null&&heartBeatFileUploadState.containsKey(map.get(DEV_ID))){
			Object dev_id=map.get(DEV_ID);
			Queue<FileUploadState> fileUploadRs = heartBeatFileUploadState.get(dev_id);
			if(fileUploadRs!=null&&fileUploadRs.size()>0){
				hasResControl=hasResControl+"4,";//
				obj = fileUploadRs.toArray();
			}
		}
		
		if (map.get(DEV_ID) != null && heartBeatDownloadAppCardInfo.containsKey(map.get(DEV_ID))) {
			Object dev_id = map.get(DEV_ID);
			Queue<AppCardInfo> appCardInfos = heartBeatDownloadAppCardInfo.get(dev_id);
			if (appCardInfos != null && appCardInfos.size() > 0) {
				hasResControl = hasResControl + "5,";//下载手机传过来的二维码信息
				obj = appCardInfos.toArray();
			}
		}
		
		if(map.get(DEV_ID)!=null&&heartBeatLibraryInfoData.containsKey(map.get(DEV_ID))){
			Object dev_id=map.get(DEV_ID);
			
			Queue<SyncConfigEntity> tableChanges = heartBeatLibraryInfoData.get(dev_id);
			if(tableChanges!=null&&tableChanges.size()>0){
				hasResControl=hasResControl+"6,";//同步信息，同步数据
			}
		}
	
		if("".equals(hasResControl.trim())){
			result.put("control", "0");
		}else{
			//去掉逗号
			result.put("control", hasResControl.substring(0,hasResControl.length()-1));
		}
		if(obj != null && obj.length > 0){
			result.put("transInfo", obj);
		}
		Log.DebugOnScr("访问 heartBeat DEVICE_ID:"+key+"----->"+hasResControl);
		resp.getData().setResult(result);
		resp.getData().setState(true);
		return resp;
	}

}
