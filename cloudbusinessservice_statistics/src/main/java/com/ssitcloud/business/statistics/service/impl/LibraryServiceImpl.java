package com.ssitcloud.business.statistics.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.authentication.entity.LibraryEntity;
import com.ssitcloud.business.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.statistics.common.utils.HttpClientUtil;
import com.ssitcloud.business.statistics.common.utils.JsonUtils;
import com.ssitcloud.business.statistics.common.utils.LogUtils;
import com.ssitcloud.business.statistics.service.LibraryService;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;

@Service
public class LibraryServiceImpl extends BasicServiceImpl implements LibraryService{
	
	private static final String URL_GETALLLIBRARYLIST = "getAllLibraryList";
	
	private static final String URL_GETALLDEVICEBYLIBIDX = "getAllDeviceByLibidx";
	
	@Resource(name="LIBRARY_CACHE")
	private Cache libCache;
	
	@Resource(name="DEVICE_CACHE")
	private Cache devCache;
	

	@SuppressWarnings("unchecked")
	@Override
	public void getAllLibraryAndDeviceList() {
		try {
			//获取所有图书馆数据
			String libResult = HttpClientUtil.doPost(requestURL.getRequestURL(URL_GETALLLIBRARYLIST), new HashMap<String,String>(), Consts.UTF_8.toString());
			if (StringUtils.isNotBlank(libResult) ) {
				ResultEntity resultEntity = JsonUtils.fromJson(libResult, ResultEntity.class);
				
				if (resultEntity.getResult() instanceof List) {
					List<Map<String, Object>> list = JsonUtils.fromJson(JsonUtils.toJson(resultEntity.getResult()), List.class);
					for (Map<String, Object> map : list) {
						LibraryEntity libraryEntity = new LibraryEntity();
						//获取idx 查询每一个图书馆的设备
						String library_idx = map.get("library_idx")+"";
						String lib_id = map.get("lib_id")+"";
						String lib_name = map.get("lib_name")+"";
						
						libraryEntity.setLibrary_idx(Integer.parseInt(library_idx));
						libraryEntity.setLib_id(lib_id);
						libraryEntity.setLib_name(lib_name);
						
						Map<String, String> param = new HashMap<>();
						param.put("req", "{\"library_idx\":\""+library_idx+"\"}");
						
						//根据library_idx获取所有device的数据
						String deviceResult = HttpClientUtil.doPost(requestURL.getRequestURL(URL_GETALLDEVICEBYLIBIDX), param, Consts.UTF_8.toString());
						List<Map<String, Object>> devList = new ArrayList<>();
						if (StringUtils.isNotBlank(deviceResult)) {
							ResultEntity deviceResultEntity = JsonUtils.fromJson(deviceResult, ResultEntity.class);
							if (deviceResultEntity.getResult() instanceof List) {
								devList = JsonUtils.fromJson(JsonUtils.toJson(deviceResultEntity.getResult()), List.class);
								
								for (Map<String, Object> map2 : devList) {
									String deviceIdx = map2.get("device_idx")+"";
									Element devElement = new Element(deviceIdx, map2);
									devCache.put(devElement);
								}
							}
						}
						libraryEntity.setDeviceList(devList);
						
						Element element = new Element(library_idx, libraryEntity);
						libCache.put(element);
					}
				}
			}
		} catch (Exception e) {
			LogUtils.error("缓存图书馆以及设备数据出错",e);
		}
		
	}
	
	
	

	
	
}
