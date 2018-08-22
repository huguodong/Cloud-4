package com.ssitcloud.common.system;

import java.util.Arrays;
import java.util.HashMap;
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
public class ScheduledMainField {
	
	@Resource(name = "requestURLListEntity")
	private RequestURLListEntity requestURLListEntity;
	
	//@Resource(name="MAINFIELD_CACHE")
	//private Cache cache;
	
	private final static String charset=Consts.UTF_8.toString();
	
	private static final String URL_GET_MAINFIELD = "getMainfieldCollection";
	
	/**
	 * 定时更新mainfield的字段到缓存中
	 *
	 * <p>2017年3月3日 下午6:30:37 
	 * <p>create by hjc
	 */
//	@SuppressWarnings("unchecked")
//	public void checkAndUpdateMainField(){
//		Assert.notNull(cache, "Need a cache. Please use setCache(Cache) create it.");  
//		System.out.println("###########################获取mainfield字段信息##################################");
//		String result = HttpClientUtil.doPost(requestURLListEntity.getRequestURL(URL_GET_MAINFIELD), new HashMap<String, String>(), charset);
//		if (StringUtils.isNotBlank(result)) {
//			ResultEntity resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
//			if (resultEntity.getState() && resultEntity.getResult() instanceof List) {
//				List<Map<String, Object>> list = (List<Map<String, Object>>) resultEntity.getResult();
//				for (Map<String, Object> map : list) {
//					String table = (String) map.get("mainfield_table");//对应mongodb的表名
//					String field = (String) map.get("field");
//					List<String> fieldlist = Arrays.asList(field.split("#"));
//					Element element = new Element(table, fieldlist);
//					cache.put(element);
//				}
//			}
//		}
//	}
	

}
