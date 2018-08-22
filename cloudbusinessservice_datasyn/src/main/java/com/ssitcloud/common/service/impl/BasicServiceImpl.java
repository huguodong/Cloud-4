package com.ssitcloud.common.service.impl;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.BasicService;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.redisutils.JedisUtils;

@Service
public class BasicServiceImpl implements BasicService{
	
	public static Map<String, Channel> handlerMap =new ConcurrentHashMap<>();
	
	@Resource(name = "requestURLListEntity")
	protected RequestURLListEntity requestURLListEntity;
	
	protected static final String URL_setRequestTimeByTriIdxs = "setRequestTimeByTriIdxs";
	
	protected final static String charset=Consts.UTF_8.toString();
	
	@Value("${LIB_ID}")
	public String LIB_ID;
	
	@Value("${DEV_ID}")
	public String DEV_ID;
	
	
	public String postRetStr(String urlId,String req){
		String url=requestURLListEntity.getRequestURL(urlId);
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		return HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
	}
	/**
	 * 更新requestTime时间 
	 * @param triIdxList
	 */
	public  void updateRequestTimeByTriIdxs(List<Integer> triIdxList){
		String res=postRetStr(URL_setRequestTimeByTriIdxs, JsonUtils.toJson(triIdxList));
		if(JSONUtils.mayBeJSON(res)){
			LogUtils.info("update requestTime tri_change_table数据返回结果:"+res);
		}
	}
	
	
	/**
	 * 根据设备id和图书馆id从redis缓存中获取channelId
	 * @param lib_id
	 * @param device_id
	 * @return
	 */
	protected String getClientId(String lib_id,String device_id){
		if(StringUtils.isEmpty(device_id) || StringUtils.isEmpty(lib_id)) 
			return "";
		return JedisUtils.getInstance().get("cliendId:"+lib_id+":"+device_id);
	}
	
	protected Channel getChannel(String lib_id,String device_id){
		String clientId = getClientId(lib_id, device_id);
		if(!StringUtils.isEmpty(clientId)){
			return handlerMap.get(clientId);
		}
		return null;
	}
	
	protected Channel getChannel(String clientId){
		if(!StringUtils.isEmpty(clientId)){
			return handlerMap.get(clientId);
		}
		return null;
	}
	
	protected ResultEntity pushMessage(Channel channel,Object data) {
		ResultEntity resultEntity = new ResultEntity();
		if(channel == null || !channel.isActive()){
			resultEntity.setMessage("服务端与设备端连接中断");
			return resultEntity;
		}
		channel.writeAndFlush(data);
		resultEntity.setState(true);
		return resultEntity;
	}
	
	
	
}
