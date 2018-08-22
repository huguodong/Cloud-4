package com.ssitcloud.common.manager;

import static com.ssitcloud.common.entity.Method.METHOD_CONNECTION;
import static com.ssitcloud.common.entity.Method.METHOD_DEVICE_LOGIN_CHECK;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Ehcache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.Method;
import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.system.BeanFactoryHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.util.Log;
import com.ssitcloud.common.util.RequestUtils;
import com.ssitcloud.datasync.entity.UploadcfgSynResultEntity;
import com.ssitcloud.datasync.service.CommonSendMessageAmqp;
import com.ssitcloud.datasync.service.DataSyncCommand;
import com.ssitcloud.datasync.service.impl.CommonSendMessageAmqpImpl;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;
/**
 * 用于处理设备端发送过来的请求<br/>
 * 根据operation进行相应的处理<br/>
 * @package: com.ssitcloud.common.manager
 * @classFile: DispacherManager
 * @author: liuBh
 * @description: TODO
 */
@Component
public class DispacherManager {
	
	//权限缓存
	//@Resource(name="OPERCMD_CACHE")
	//private Ehcache permessionCache;
	
	private AntPathMatcher matcher=new AntPathMatcher();
	
	@Value("${LIB_ID}")
	public String LIB_ID;
	@Value("${DEV_ID}")
	public String DEV_ID;
	
	List<String> methodList=Arrays.asList(Method.METHODS);
	
	private static Map<String,Long> rateMap=new ConcurrentHashMap<>(100);
	
	@Value("${limitTime}")
	private String limitTime;
	
	@Resource
	private CommonSendMessageAmqp commonSendMessageAmqp;
	/**
	 * 统一处理请求
	 * @methodName: doDispacher
	 * @param req
	 * @return
	 * @returnType: RespEntity
	 * @author: liuBh
	 * @param <T>
	 * @throws UnsupportedEncodingException 
	 */
	public  RespEntity doDispacherProxy(HttpServletRequest request) throws UnsupportedEncodingException{
		String req=request.getParameter("req");
		Log.DebugOnScr(req);
		if(req==null||req.equals("")){
			return new RespEntity(RespEntity.defaultServiceType, RespEntity.defaultTarget, RespEntity.defaultOperation, null);
		}
		//将JSON数据转成对象
		RequestEntity reqEntity=JsonUtils.fromJson(req, RequestEntity.class);
		reqEntity.setRequestIp(RequestUtils.getIpAddr(request));//IP
		reqEntity.setRequestPort(String.valueOf(request.getRemotePort()));//端口
		return doDispacher(reqEntity);
	}
	/**
	 * 按方法名处理请求处理
	 * @methodName: doDispacher
	 * @param reqEntity
	 * @return
	 * @returnType: RespEntity
	 * @author: liubh
	 * @createTime: 2016年4月19日 
	 * @updateTime: 2016年8月29日
	 * @description: TODO
	 */
	private  RespEntity doDispacher(RequestEntity reqEntity){
		
		RespEntity resp=new RespEntity(RespEntity.defaultServiceType, RespEntity.defaultTarget, RespEntity.defaultOperation, new ResultEntity());
		
		if(reqEntity==null){return resp;}
		
		String operation=reqEntity.getOperation();
		
		//operation 为null则返回
		if(StringUtils.isEmpty(operation)) return resp;
		
		if(METHOD_CONNECTION.equals(operation)){
			//connection直接返回，无需其他处理
			resp.getData().setState(true);
			return resp;
		}
		
		Map<String, Object> map=reqEntity.getData();
				
		if(map==null){return resp;}//
		
		if(!map.containsKey(LIB_ID)||!map.containsKey(DEV_ID)){
			return lackOfLibIdOrDevId(resp);
		}
		StringBuilder cycleKey=new StringBuilder();
		//检测调用接口的频率
		cycleKey.append(map.get(LIB_ID)).append(map.get(DEV_ID)).append(operation);
		//transData function 数据特殊处理
		if(Method.METHOD_TRANSDATA.equals(operation)){
			@SuppressWarnings("unchecked")
			Map<String, Object> contentMap=(Map<String, Object>)map.get("content");
			if(contentMap!=null){
				String suffix="";
				if(contentMap.containsKey("ext_state")){
					suffix="ext_state";
				}else if(contentMap.containsKey("soft_state")){
					suffix="soft_state";
				}else if(contentMap.containsKey("bookrack_state")){
					suffix="bookrack_state";
				}else if(contentMap.containsKey("bin_state")){
					suffix="bin_state";
				}
				cycleKey.append(suffix);
			}
		}
		if(!CheckVisitRate(cycleKey.toString())){
			resp.getData().setMessage("操作时间间隔过短，请稍后");
			resp.getData().setResult(53);
			return resp;
		}
		//如果 operation不符合条件 下面有抛出异常的
		//connection 和 deviceLoginCheck 不用验证
		if(METHOD_DEVICE_LOGIN_CHECK.equals(operation)){
			
			DataSyncCommand dataSyncExecute=BeanFactoryHelper.getBean(operation, DataSyncCommand.class);
				
			resp=dataSyncExecute.execute(reqEntity);
			
			return resp;
		}
		
		//下载app私钥信息处理
		if (Method.METHOD_DOWNLOAD_APP_VERSION_INFO.equals(operation)) {
			
			DataSyncCommand dataSyncExecute=BeanFactoryHelper.getBean(operation, DataSyncCommand.class);
			
			resp=dataSyncExecute.execute(reqEntity);
			
			return resp;
		}
		
		String key=map.get(LIB_ID)+"_"+map.get(DEV_ID);//权限key
		
		boolean isMatch=false;
		//heartBeat,askControl,controlResult,askCfgSync,uploadcfgSync,askCloudTime,downloadOsbSync,uploadOsbSync,askUploadOsbSync,
		//askDownloadOsbSync,transOperationLog,askVersion,transData,uploadRunLog,downloadCfgSync
		
		//从缓存中取出设备权限
		String  cacheOpercmdUrl  = JedisUtils.getInstance().get(RedisConstant.OPERCMD+key);
		if(!StringUtils.isEmpty(cacheOpercmdUrl)){//key
			isMatch=matcher.match("*"+operation+"*",cacheOpercmdUrl);
			if(!isMatch){
					//没有权限 函数名及权限检测
					resp.getData().setState(false);
					resp.getData().setMessage("没有权限或者函数名["+operation+"]不正确，请检查");
					return resp;
				}
				DataSyncCommand dataSyncExecute=BeanFactoryHelper.getBean(operation, DataSyncCommand.class);
				
				resp = dataSyncExecute.execute(reqEntity);
				return resp;
		}else{
			//过期或者没有权限
			resp.getData().setState(false);
			resp.getData().setMessage("权限过期，需要重新执行deviceLoginCheck()");
			return resp;
		}
	}
	/**
	 * 缺少library_id 或者devic_id 时提示
	 * @methodName: lackOfLibIdOrDevId
	 * @param resp
	 * @return
	 * @returnType: RespEntity
	 * @author: liuBh
	 */
	private RespEntity lackOfLibIdOrDevId(RespEntity resp){
		resp.getData().setState(false);
		UploadcfgSynResultEntity uploadcfgSynFailResult=new UploadcfgSynResultEntity("", "", "0", "缺少字段device_id或者library_id");
		resp.getData().setResult(uploadcfgSynFailResult);
		return resp;
	}
	
	@SuppressWarnings("unused")
	private List<String> GetMethodList(){
		if(methodList==null)
			methodList=Arrays.asList(Method.METHODS);
		return methodList;
	}
	/**
	 * 检查每台设备调用同一个的方法的频率
	 * @param cycleKey
	 * @return
	 */
	private boolean CheckVisitRate(String cycleKey){
		int liTime=2000;
		try{
			 liTime=Integer.parseInt(limitTime);
		}catch(Exception e){
			liTime=2000;
		}
		long curVisitTime=System.currentTimeMillis();
		if(rateMap.containsKey(cycleKey)&&rateMap.get(cycleKey)!=0){
			long lastVisitTime=rateMap.get(cycleKey);
			//当前时间-最后一次访问时间 小于2秒 则不给操作
			if(curVisitTime-lastVisitTime<liTime){//默认为2秒
				return false;
			}else{//没有超时
				rateMap.put(cycleKey,curVisitTime);
			}
		}else{
			rateMap.put(cycleKey,curVisitTime);
		}
		return true;
	}
	/**
	 * 清除 缓存
	 * @param req
	 * @return
	 */
	public ResultEntity upadtePermessionCacheForAllDevice(String req) {
		Set<String> keys = JedisUtils.getInstance().keys(RedisConstant.OPERCMD+"*");
		if(keys != null && !keys.isEmpty()){
			for(String key : keys){
				JedisUtils.getInstance().del(RedisConstant.OPERCMD+key);
			}
		}
		ResultEntity r=new ResultEntity();
		r.setState(true);
		return r;
	}
}
