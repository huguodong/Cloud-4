package com.ssitcloud.common.system;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.NativeMemoryConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.memory.MemorySize;
import com.hazelcast.memory.MemoryUnit;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.util.ResourcesUtil;
import com.ssitcloud.common.util.XMLUtils;
import com.ssitcloud.datasync.entity.DeviceIdx2IdContainer;
import com.ssitcloud.datasync.entity.HeartBeatChangeTableData;
import com.ssitcloud.datasync.entity.HeartBeatDataSyncMsg;
import com.ssitcloud.datasync.entity.HeartBeatDeviceOrder;
import com.ssitcloud.datasync.entity.HeartBeatDownloadAppCardInfo;
import com.ssitcloud.datasync.entity.HeartBeatFileUploadState;
import com.ssitcloud.datasync.entity.HeartBeatLibraryInfoData;
import com.ssitcloud.datasync.entity.HeartBeatMidVersionData;
import com.ssitcloud.datasync.entity.HeartBeatTransDataState;
import com.ssitcloud.datasync.entity.HeartBeatTransOperLogState;

@Configuration
public class AppConf {

	@Bean(name="heartBeatDeviceOrder")
	public HeartBeatDeviceOrder heartBeatDeviceOrder(){
		return new HeartBeatDeviceOrder(100);
	}
	@Bean(name="heartBeatDataSyncMsg")
	public HeartBeatDataSyncMsg heartBeatDataSyncMsg(){
		return new HeartBeatDataSyncMsg(100);
	}
	@Bean(name="deviceIdx2IdContainer")
	public DeviceIdx2IdContainer deviceIdx2IdContainer(){
		return new DeviceIdx2IdContainer(100);
	}
	@Bean(name="libIdxAndArrayDeviceIdContainer")
	public ConcurrentMap<Integer, List<String>> libIdxAndArrayDeviceIdContainer(){
		return new ConcurrentHashMap<>(50);
	}
	
	@Bean(name="heartBeatLibraryInfoData")
	public HeartBeatLibraryInfoData heartBeatLibraryInfoData(){
		return new HeartBeatLibraryInfoData(100);
	}
	
	@Bean(name="heartBeatChangeTableData")
	public HeartBeatChangeTableData heartBeatChangeTableData(){
		return new HeartBeatChangeTableData(100);
	}
	
	@Bean(name="heartBeatMidVersionData")
	public HeartBeatMidVersionData heartBeatMidVersionData(){
		return new HeartBeatMidVersionData(100);
	}
	@Bean(name="heartBeatFileUploadState")
	public HeartBeatFileUploadState heartBeatFileUploadState(){
		return new HeartBeatFileUploadState(100);
	}
	@Bean(name="heartBeatTransDataState")
	public HeartBeatTransDataState HeartBeatTransDataState(){
		return new HeartBeatTransDataState(100);
	}
	@Bean(name="heartBeatTransOperLogState")
	public HeartBeatTransOperLogState HeartBeatTransOperLogState(){
		return new HeartBeatTransOperLogState(100);
	}
	//手机发送过来的3二维码数据
	@Bean(name="heartBeatDownloadAppCardInfo")
	public HeartBeatDownloadAppCardInfo heartBeatDownloadAppCardInfo(){
		return new HeartBeatDownloadAppCardInfo(100);
	}
	@Bean(name="hazelcastInstance")
	public HazelcastInstance hazelcastInstance(){
		MemorySize memorySize = new MemorySize(512, MemoryUnit.MEGABYTES);
		NativeMemoryConfig nativeMemoryConfig =
		                new NativeMemoryConfig()
		                        .setAllocatorType(NativeMemoryConfig.MemoryAllocatorType.POOLED)
		                        .setSize(memorySize)
		                        .setEnabled(true)
		                        .setMinBlockSize(16)
		                        .setPageSize(1 << 20);
		Config config=new Config();
		config.setNativeMemoryConfig(nativeMemoryConfig);
		return Hazelcast.newHazelcastInstance(config);
	}
	@Bean(name="libIdxAndArrayServiceIdContainer")
	public ConcurrentMap<Integer, List<String>> libIdxAndArrayServiceIdContainer(){
		return new ConcurrentHashMap<>(50);
	}
	@Bean(name="libIdAndArrayServiceIdContainer")
	public ConcurrentMap<Integer, List<String>> libIdAndArrayServiceIdContainer(){
		return new ConcurrentHashMap<>(50);
	}
	@Bean(name="serviceIdxAndArrayserviceIdContainer")
	public ConcurrentMap<Integer, List<String>> serviceIdxAndArrayserviceIdContainer(){
		return new ConcurrentHashMap<>(50);
	}
	/**
	 * <p>
	 * 随着spring启动，将RequestURL.xml中的Url信息装入RequestURLListEntity中
	 * </p>
	 * 
	 * 使用方法 <br/>
	 * @Resource(name="requestURL")<br/>
	 * RequestURLListEntity requestURLListEntity;<br/>
	 * <br/>
	 * 获取requestURLListEntity对象，再通过ID属性获取对应的requestURL<br/>
	 * 
	 * @methodName: requestURL
	 * @return
	 * @throws Exception
	 * @returnType: RequestURLListEntity
	 * @author: liuBh
	 */
	@Bean(name = "requestURLListEntity")
	public RequestURLListEntity requestURL() throws Exception {
		Map<String, String> map = XMLUtils.parseAll(ResourcesUtil.getInputStream("config/RequestURL.xml"));
		return new RequestURLListEntity(map);
	}
}
