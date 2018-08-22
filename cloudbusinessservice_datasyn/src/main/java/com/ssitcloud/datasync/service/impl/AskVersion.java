package com.ssitcloud.datasync.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.common.util.XMLUtils;
import com.ssitcloud.datasync.entity.HeartBeatMidVersionData;
import com.ssitcloud.datasync.entity.UpgradeStrategyEntity;
import com.ssitcloud.datasync.service.DataSyncCommand;
import com.ssitcloud.devmgmt.entity.TableChangeTriEntity;

@Component(value="askVersion")
public class AskVersion extends BasicServiceImpl implements DataSyncCommand{
	
	public static final String URL_ASK_VERSION="askVersion";
	
	@Resource(name="heartBeatMidVersionData")
	private HeartBeatMidVersionData heartBeatMidVersionData;
	
	@Override
	public RespEntity execute(RequestEntity conditionInfo) {
		RespEntity resp = new RespEntity(conditionInfo);
		Map<String, Object> map = conditionInfo.getData();
		if(map.containsKey("old_version")){
			String device_id=(String) map.get(DEV_ID);
			String lib_id=(String) map.get(LIB_ID);
			String old_version=(String) map.get("old_version");
			//String device_type=getDeivceTypeBydeviceId(deviceId);//获取设备类型
			UpgradeStrategyEntity updStrategy=XMLUtils.parseVersionXMl(old_version);
			if(updStrategy==null){
				ResultEntity res=new ResultEntity();
				res.setMessage("获取升级策略失败");
				resp.setData(res);
				return resp;
			}
			updStrategy.setDevice_id(device_id);
			updStrategy.setOld_version(old_version);
			updStrategy.setLibrary_id(lib_id);
			Map<String, String> params =new HashMap<>();
			params.put("req", JsonUtils.toJson(updStrategy));
			String result=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_ASK_VERSION), params, charset);
			if(JSONUtils.mayBeJSON(result)){
				resp.setData(JsonUtils.fromJson(result, ResultEntity.class));
				doClear(device_id);
			}else{
				ResultEntity res=new ResultEntity();
				res.setState(false);
				res.setRetMessage(result);
				resp.setData(res);
			}
		}else{
			ResultEntity result=new ResultEntity();
			result.setState(false);
			result.setMessage("缺少 old_version 字段");
			resp.setData(result);
		}
		return resp;
	}
	@Async
	public void doClear(String device_id){
		List<Integer> triIdxList=new ArrayList<>();
		if(!"null".equals(device_id)&&heartBeatMidVersionData.containsKey(device_id)){
			ConcurrentLinkedQueue<TableChangeTriEntity> queue=heartBeatMidVersionData.get(device_id);
			if(CollectionUtils.isNotEmpty(queue)){
				for(TableChangeTriEntity t:queue){
					Integer triIdx=t.getTri_idx();//如果涉及到有多个设备都使用的话，只要有一个设备返回了（表示内存中已经存在），则不直接设置requestTime，存在的风险是如果停电则可能部分设备更新失败，需要重新修改同步
					triIdxList.add(triIdx);
				}
				queue.clear();
				updateRequestTimeByTriIdxs(triIdxList);
			}
		
		}
	}
	
	/**
	 * 更新requestTime时间 
	 * @param triIdxList
	 */
	@Async
	public  void updateRequestTimeByTriIdxs(List<Integer> triIdxList){
		String res=postRetStr(URL_setRequestTimeByTriIdxs, JsonUtils.toJson(triIdxList));
		if(JSONUtils.mayBeJSON(res)){
			LogUtils.info("update requestTime tri_change_table数据返回结果:"+res);
		}
	}
	

}
