package com.ssitcloud.datasync.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.util.JSONUtils;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.common.entity.UserRolePermessionEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.LogUtils;
import com.ssitcloud.datasync.service.DataSyncCommand;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;


/**
 * deviceLoginCheck 执行类
 * @package: com.ssitcloud.datasync.service.impl
 * @classFile: DeviceLoginCheck
 * @author: liuBh
 * @description: TODO
 */
@Component(value="deviceLoginCheck")
public class DeviceLoginCheck extends BasicServiceImpl implements DataSyncCommand {
	
	
	public static final String URL_deviceLoginCheck="deviceLoginCheck";
	
	public static final String URL_SelUserCmdsByIdxAndRestriDevice="SelUserCmdsByIdxAndRestriDevice";
	
	public static final String NO_PERMESSION="NO_PERMESSION";
	
	//(name="OPERCMD_CACHE")
	//private Ehcache cache;
	
	/**
	 * 字段名称 类型 说明
	 * <p/>
	 * device_id RequestEntity 设备ID
	 * <p/>
	 * Library_id RequestEntity 馆ID
	 * <p/>
	 * Device_ip RequestEntity 设备所在机器IP
	 * <p/>
	 * req={ “servicetype”: ”ssitcloud”, ”target”:”ext_rfid”,
	 * ”operation”:”loginCheck”,
	 * ”data”:{“device_id”:”SSL001”,”library_id”:”002”} }
	 * 
	 * 
	 *  * {reqHead}+data:"{operator_id:"",ip:"192.168.1.11",port:"",faild_times:""}"
	 * @methodName: deviceLoginCheck
	 * @param proInfo
	 * @return
	 * @author: liuBh
	 * @description: TODO
	 */
	@SuppressWarnings("unchecked")
	@Override
	public RespEntity execute(RequestEntity requestEntity) {
		RespEntity resp = new RespEntity(requestEntity);
		ResultEntity r=new ResultEntity();
		Map<String, Object> map = requestEntity.getData();
		Map<String,String> params=new HashMap<>();
		Map<String,Object> data=new HashMap<>();
		String device_id=(String) map.get(DEV_ID);
		String library_id=(String) map.get(LIB_ID);
		String cache_key=library_id+"_"+device_id;//缓存 key
		
		//检查是否缓存中有数据
		String cacheOpercmdUrl = JedisUtils.getInstance().get(RedisConstant.OPERCMD+cache_key);
		if(!StringUtils.isEmpty(cacheOpercmdUrl)){
			//JedisUtils.getInstance().expire(RedisConstant.OPERCMD+cache_key, RedisConstant.TEN_SECOND_EXPIRE_TIME);
			r.setState(true);
			resp.setData(r);
			return resp;
		}
		
		//device_id + ip  = key 存放 device_id
		//String ipCache_key=device_id+requestEntity.getRequestIp();
		data.put("operator_id",device_id);
		data.put("ip", requestEntity.getRequestIp());
		data.put("port", requestEntity.getRequestPort());
		data.put("faild_times", 0);
		requestEntity.setData(data);
		params.put("req", JsonUtils.toJson(requestEntity));
		String result=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_deviceLoginCheck), params, charset);
		if(StringUtils.hasLength(result)){
			resp=JsonUtils.fromJson(result, RespEntity.class);
			if(resp!=null&&resp.getData()!=null){
					ResultEntity resultEntity=resp.getData();
					boolean state=resultEntity.getState();
					if(state){
						//验证通过,返回operator ，然后查询权限 配置更改-->mysql 上传 mongodb 下载升级包权限
						Object resObj=resultEntity.getResult();
						Map<String,Object> operatorMap = null;
						if(resObj instanceof Map){
							operatorMap=(Map<String, Object>) resultEntity.getResult();//Map类型
						}else{
							LogUtils.info(resObj.toString());
						}
						params.put("req", JsonUtils.toJson(operatorMap));
						String resultOpercmd=HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_SelUserCmdsByIdxAndRestriDevice), params, charset);
						
						if(JSONUtils.mayBeJSON(resultOpercmd)){
							ResultEntityF<List<UserRolePermessionEntity>> res=JsonUtils.fromJson(resultOpercmd, new TypeReference<ResultEntityF<List<UserRolePermessionEntity>>>() {});
							if(res!=null){
								List<UserRolePermessionEntity> permessions=res.getResult();
								if(permessions!=null){
									String cmdUrls="";
									for(UserRolePermessionEntity permession:permessions){
										String opercmdUrl=permession.getOpercmd_url();
										if(StringUtils.hasText(opercmdUrl)){
											//
											cmdUrls+=opercmdUrl+",";
										}
									}
									if(StringUtils.hasText(cmdUrls)){
										cmdUrls=cmdUrls.substring(0, cmdUrls.length()-1);
									}
										
									//Element element=new Element(cache_key, cmdUrls);
									//Element ipElement=new Element(ipCache_key, device_id);
									
									//cache.put(element);
									
									//cache.put(ipElement);
									
									//将权限保存到redis中
									JedisUtils.getInstance().setex(RedisConstant.OPERCMD+cache_key, RedisConstant.TWO_HOUR_EXPIRE_TIME, cmdUrls);
									r.setState(true);
									
									resp.setData(r);
								}else{
									//没有权限 不缓存
									resp.getData().setMessage("没有权限");
									
									resp.getData().setState(false);
								}
							}
						}else{
							System.out.println(resultOpercmd);
						}
					}else{
						//state==false 验证不通过，获取错误码.....
						LogUtils.info("请求登陆信息 返回state==false:"+resultEntity.getResult());
					}
				}else{
					LogUtils.info("请求登陆信息 返回结果:"+result);
				}
		}
		return resp;
	}

}
