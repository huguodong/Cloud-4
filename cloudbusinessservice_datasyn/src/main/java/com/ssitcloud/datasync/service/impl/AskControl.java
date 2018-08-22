package com.ssitcloud.datasync.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.datasync.entity.AskControlResultEntity;
import com.ssitcloud.datasync.entity.HeartBeatDeviceOrder;
import com.ssitcloud.datasync.entity.HeartBeatDownloadAppCardInfo;
import com.ssitcloud.datasync.service.DataSyncCommand;
import com.ssitcloud.devmgmt.entity.AppCardInfo;
import com.ssitcloud.devmgmt.entity.DeviceOrder;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;

@Component(value="askControl")
public class AskControl extends BasicServiceImpl implements DataSyncCommand{

	@Resource(name = "heartBeatDeviceOrder")
	private HeartBeatDeviceOrder heartBeatDeviceOrder;
	
	@Resource(name = "heartBeatDownloadAppCardInfo")
	private HeartBeatDownloadAppCardInfo heartBeatDownloadAppCardInfo;
	
	//权限缓存
	//@Resource(name="OPERCMD_CACHE")
	//private Ehcache permessionCache;
	
	private static final String DOWNLOADAPPCARDINFO = "downloadAppCardInfo";//下载读者卡信息的权限名称
	
	@Override
	public RespEntity execute(RequestEntity conditionInfo) {
		RespEntity resp = new RespEntity(conditionInfo);
		Map<String, Object> map = conditionInfo.getData();
		// key 为device_id
		String library_id = (String) map.get(LIB_ID);
		String device_id = (String) map.get(DEV_ID);
		//String operator_id=(String) map.get("operator_id");
		String key = device_id;
		//LogUtils.info("askControl key:" + key);
		if (key != null && heartBeatDeviceOrder.containsKey(key)) {
			ConcurrentLinkedQueue<DeviceOrder> deviceOrders = heartBeatDeviceOrder.get(key);
			if (deviceOrders != null && !deviceOrders.isEmpty()) {// 不为空
//				DeviceOrder order = deviceOrders.poll();// 取回和删除头部元素
				DeviceOrder order = deviceOrders.peek();//获取头元素 ，但不删除
				//LogUtils.info("heartBeatContainer--->[设备ID]:" + key + "---->" + "剩余命令数量"+ deviceOrders.size());
				if (order != null) {
					//	先判断指令是否会与读者操作冲突的，
					//如果有，判断是否有下载读者卡信息的权限，
					//如果有，再判断是否有读者操作在队列里面，
					//如果有,则不下发指令
					//1关机 2 重启 3获取日志4远程维护锁屏 5取消操作
					try {//加入try catch 防止这部分代码报错的时候指令不能下发
						if (!order.getControlInfoDesc().contains("3")) {//
							boolean isMatch=false;
							//从redis缓存中获取权限
							String cacheKey =RedisConstant.OPERCMD + library_id+"_"+device_id;
							String cacheOpercmdUrl = JedisUtils.getInstance().get(cacheKey);
							AntPathMatcher matcher=new AntPathMatcher();
							if(!StringUtils.isEmpty(cacheOpercmdUrl)){//key  判断有没有下载读者信息的权限，如果没有则跳过
								isMatch=matcher.match("*"+DOWNLOADAPPCARDINFO+"*",cacheOpercmdUrl);
								if(isMatch){
									//有权限   检测是不是有读者操作
									ConcurrentLinkedQueue<AppCardInfo> appCardInfos = heartBeatDownloadAppCardInfo.get(key);
									//如果有读者指令，判断读者指令是不是在
									long appTime = 0L;
									long devTime = order.getReceiveTime().getTime();
									for (AppCardInfo appCardInfo : appCardInfos) {
										appTime = appCardInfo.getReceiveTime().getTime();
										if (devTime <= appTime) {
											deviceOrders.poll();// 删除头部元素
											//调用消息推送接口，告诉发送指令消息的用户消息已经被取消
											return resp;//直接返回 ，不下发指令
										}
									}
								}
								
							}
						}
					} catch (Exception e) {
					}
					deviceOrders.poll();// 删除头部元素
					
					// 控制操作信,1关机 2 重启 3获取日志4远程维护锁屏 5取消操作
					// 获取日志需要重新考虑，需要填写获取日志的开始时间和结束时间和用户ID
					// 可能需要判断
					String ctrlInfo = order.getControl_info();
					if (StringUtils.hasText(ctrlInfo)) {
						String cmd = ctrlInfo.trim();
						AskControlResultEntity askControlResult = new AskControlResultEntity(
								device_id, library_id, cmd, order.getStart_time(),
								order.getEnd_time(), order.getOperator_id());
						resp.getData().setState(true);
						resp.getData().setResult(askControlResult);
					}
				}
			} else if (deviceOrders != null && deviceOrders.isEmpty()) {
				//已经没有命令
				//LogUtils.info(key + ":[设备ID]已经没有命令可取");
			} else {
				// deviceOrders is null
			}
		}
		return resp;
	}

}
