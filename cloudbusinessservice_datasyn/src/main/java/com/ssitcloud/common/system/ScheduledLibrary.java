package com.ssitcloud.common.system;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.springframework.util.Assert;

import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;

/**
 * 定时更新mainfield的字段到缓存中
 *
 * <p>2017年3月3日 下午2:16:39  
 * @author hjc 
 *
 */
public class ScheduledLibrary {
	
	@Resource(name = "requestURLListEntity")
	private RequestURLListEntity requestURLListEntity;
	
	/*@Resource(name="LIBRARY_CACHE")
	private Cache cache;*/
	
	private final static String charset=Consts.UTF_8.toString();
	
	private static final String URL_GETLIBIDANDLIBIDX = "getLibIdAndLibIdx";
	
	/*@SuppressWarnings("unchecked")
	public void checkAndUpdateLibrary(){
		Assert.notNull(cache, "Need a cache. Please use setCache(Cache) create it.");  
		System.out.println("###########################获取library信息##################################");
		String result = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_GETLIBIDANDLIBIDX),  new HashMap<String, String>(), charset);
		if (StringUtils.isNotBlank(result)) {
			ResultEntity resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
			if (resultEntity.getState() && resultEntity.getResult() instanceof LinkedHashMap) {
				LinkedHashMap<String, LinkedHashMap<String, String>> link = (LinkedHashMap<String, LinkedHashMap<String, String>>) resultEntity.getResult();
				Iterator iter = link.entrySet().iterator(); 
				while (iter.hasNext()) { 
					Map.Entry entry = (Map.Entry) iter.next(); 
					Object key = entry.getKey(); 
					Map<String,String> map = JsonUtils.fromJson(JsonUtils.toJson(entry.getValue()), Map.class); 
					String library_idx = String.valueOf(map.get("library_idx"));//对应mongodb的表名
					String lib_id = String.valueOf(map.get("lib_id"));
					Element element = new Element(lib_id, library_idx);
					cache.put(element);
				} 
			}
		}
	}
	*/

}
